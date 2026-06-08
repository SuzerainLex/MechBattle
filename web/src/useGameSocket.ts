import { useCallback, useEffect, useRef, useState } from 'react';
import type { GameState, ServerMessage, CombatEvent } from './types';
import { WS_URL } from './types';
import { normalizeGameState } from './normalizeGameState';

const SESSION_KEY = 'mechbattle-session';
const RECONNECT_MS = 2000;

type PlayerRole = 'host' | 'guest';

interface SavedSession {
  roomId: string;
  playerId: string;
  role: PlayerRole;
}

function loadSession(): SavedSession | null {
  try {
    const raw = sessionStorage.getItem(SESSION_KEY);
    return raw ? (JSON.parse(raw) as SavedSession) : null;
  } catch {
    return null;
  }
}

function saveSession(session: SavedSession) {
  sessionStorage.setItem(SESSION_KEY, JSON.stringify(session));
}

function isGuestJoinUrl(): boolean {
  return new URLSearchParams(window.location.search).get('guest') === '1';
}

function roomIdFromPath(): string | null {
  const match = window.location.pathname.match(/\/room\/([^/]+)/i);
  return match ? match[1].toUpperCase() : null;
}

function guestJoinName(roomId: string): string | null {
  return sessionStorage.getItem(`mechbattle-join-${roomId.toUpperCase()}`);
}

type ActionHandler = (result: {
  success: boolean;
  message: string;
  sound?: string;
  event?: CombatEvent;
}) => void;

export function useGameSocket(onAction?: ActionHandler) {
  const wsRef = useRef<WebSocket | null>(null);
  const onActionRef = useRef(onAction);
  onActionRef.current = onAction;
  const intentionalClose = useRef(false);
  const pendingJoin = useRef<{ roomId: string; playerName: string } | null>(null);
  const reconnectTimer = useRef<ReturnType<typeof setTimeout> | null>(null);
  const joiningAsGuest = useRef(false);

  const [connected, setConnected] = useState(false);
  const [reconnecting, setReconnecting] = useState(false);
  const [isHost, setIsHost] = useState(false);
  const [playerId, setPlayerId] = useState<string | null>(null);
  const [roomId, setRoomId] = useState<string | null>(null);
  const [gameState, setGameState] = useState<GameState | null>(null);
  const [lastMessage, setLastMessage] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const send = useCallback((type: string, payload: Record<string, unknown> = {}) => {
    if (wsRef.current?.readyState === WebSocket.OPEN) {
      wsRef.current.send(JSON.stringify({ type, payload }));
    }
  }, []);

  const sendJoin = useCallback((ws: WebSocket, roomId: string, playerName: string) => {
    joiningAsGuest.current = true;
    ws.send(JSON.stringify({ type: 'JOIN_ROOM', payload: { roomId, playerName } }));
  }, []);

  const restoreRoom = useCallback(
    (ws: WebSocket) => {
      if (pendingJoin.current) {
        const { roomId, playerName } = pendingJoin.current;
        sendJoin(ws, roomId, playerName);
        return;
      }

      const urlRoomId = roomIdFromPath();
      const saved = loadSession();

      // На guest-ссылке никогда не rejoin как host
      if (isGuestJoinUrl()) {
        if (saved?.roomId === urlRoomId && saved.role === 'guest') {
          ws.send(
            JSON.stringify({
              type: 'REJOIN_ROOM',
              payload: { roomId: saved.roomId, playerId: saved.playerId },
            })
          );
          return;
        }
        const name = urlRoomId ? guestJoinName(urlRoomId) : null;
        if (urlRoomId && name) {
          pendingJoin.current = { roomId: urlRoomId, playerName: name };
          sendJoin(ws, urlRoomId, name);
        }
        return;
      }

      if (saved?.role === 'host') {
        ws.send(
          JSON.stringify({
            type: 'REJOIN_ROOM',
            payload: { roomId: saved.roomId, playerId: saved.playerId },
          })
        );
        return;
      }

      if (saved?.role === 'guest') {
        ws.send(
          JSON.stringify({
            type: 'REJOIN_ROOM',
            payload: { roomId: saved.roomId, playerId: saved.playerId },
          })
        );
      }
    },
    [sendJoin]
  );

  useEffect(() => {
    let mounted = true;

    function connect() {
      if (!mounted) return;

      intentionalClose.current = false;
      const ws = new WebSocket(WS_URL);
      wsRef.current = ws;

      ws.onopen = () => {
        if (!mounted) return;
        setConnected(true);
        setReconnecting(false);
        setError(null);
        restoreRoom(ws);
      };

      ws.onclose = () => {
        if (!mounted) return;
        setConnected(false);
        wsRef.current = null;

        if (!intentionalClose.current) {
          setReconnecting(true);
          reconnectTimer.current = setTimeout(connect, RECONNECT_MS);
        }
      };

      ws.onerror = () => {
        if (!mounted || intentionalClose.current) return;
        setError('Ошибка WebSocket — проверьте, что сервер запущен на порту 8080');
      };

      ws.onmessage = (event) => {
        const data = JSON.parse(event.data) as ServerMessage;
        switch (data.type) {
          case 'CONNECTED':
            setPlayerId(data.playerId || null);
            break;
          case 'ROOM_CREATED':
            pendingJoin.current = null;
            joiningAsGuest.current = false;
            setIsHost(true);
            setRoomId(data.roomId);
            setPlayerId(data.playerId);
            saveSession({ roomId: data.roomId, playerId: data.playerId, role: 'host' });
            setLastMessage(`Комната создана: ${data.roomId}`);
            setError(null);
            break;
          case 'ROOM_JOINED': {
            pendingJoin.current = null;
            const role: PlayerRole =
              joiningAsGuest.current || isGuestJoinUrl()
                ? 'guest'
                : (loadSession()?.role ?? 'host');
            joiningAsGuest.current = false;
            setIsHost(role === 'host');
            setRoomId(data.roomId);
            setPlayerId(data.playerId);
            saveSession({ roomId: data.roomId, playerId: data.playerId, role });
            setLastMessage(`Вы вошли в комнату ${data.roomId}`);
            setError(null);
            break;
          }
          case 'STATE':
            pendingJoin.current = null;
            setGameState(normalizeGameState(data.state));
            setError(null);
            break;
          case 'ACTION_RESULT':
            setLastMessage(data.message);
            setError(data.success ? null : data.message);
            onActionRef.current?.({
              success: data.success,
              message: data.message,
              sound: data.sound,
              event: data.event,
            });
            break;
          case 'ERROR':
            joiningAsGuest.current = false;
            if (data.message.includes('переподключиться') || data.message.includes('не найдена')) {
              sessionStorage.removeItem(SESSION_KEY);
              pendingJoin.current = null;
              setRoomId(null);
              setGameState(null);
              setPlayerId(null);
              setIsHost(false);

              const urlRoomId = roomIdFromPath();
              const guestName = urlRoomId ? guestJoinName(urlRoomId) : null;
              if (isGuestJoinUrl() && urlRoomId && guestName && wsRef.current?.readyState === WebSocket.OPEN) {
                pendingJoin.current = { roomId: urlRoomId, playerName: guestName };
                sendJoin(wsRef.current, urlRoomId, guestName);
                setError('Повторный вход в комнату...');
                break;
              }
            }
            setError(data.message);
            break;
        }
      };
    }

    connect();

    return () => {
      mounted = false;
      intentionalClose.current = true;
      if (reconnectTimer.current) {
        clearTimeout(reconnectTimer.current);
      }
      wsRef.current?.close();
    };
  }, [restoreRoom, sendJoin]);

  const createRoom = useCallback(
    (playerName: string) => {
      sessionStorage.removeItem(SESSION_KEY);
      pendingJoin.current = null;
      joiningAsGuest.current = false;
      send('CREATE_ROOM', { playerName });
    },
    [send]
  );

  const joinRoom = useCallback(
    (id: string, playerName: string) => {
      const payload = { roomId: id.toUpperCase(), playerName };
      pendingJoin.current = payload;
      joiningAsGuest.current = true;
      sessionStorage.removeItem(SESSION_KEY);

      sessionStorage.setItem(`mechbattle-join-${payload.roomId}`, playerName);

      if (wsRef.current?.readyState === WebSocket.OPEN) {
        sendJoin(wsRef.current, payload.roomId, payload.playerName);
      } else if (wsRef.current?.readyState !== WebSocket.CONNECTING) {
        setReconnecting(true);
      }
    },
    [sendJoin]
  );

  const selectMech = useCallback((mech: string) => send('SELECT_MECH', { mech }), [send]);
  const equipWeapon = useCallback(
    (weapon: string, slot: string) => send('EQUIP_WEAPON', { weapon, slot }),
    [send]
  );
  const removeWeapons = useCallback(
    (category: string) => send('REMOVE_WEAPONS', { category }),
    [send]
  );
  const ready = useCallback(() => send('READY'), [send]);
  const move = useCallback((direction: string) => send('MOVE', { direction }), [send]);
  const attack = useCallback(
    (weaponId: string, aim: number) => send('ATTACK', { weaponId, aim }),
    [send]
  );
  const skipTurn = useCallback(() => send('SKIP_TURN'), [send]);

  const leaveRoom = useCallback(() => {
    sessionStorage.removeItem(SESSION_KEY);
    pendingJoin.current = null;
    joiningAsGuest.current = false;
    setRoomId(null);
    setGameState(null);
    setPlayerId(null);
    setIsHost(false);
    setError(null);
  }, []);

  return {
    connected,
    reconnecting,
    isHost,
    playerId,
    roomId,
    gameState,
    lastMessage,
    error,
    createRoom,
    joinRoom,
    selectMech,
    equipWeapon,
    removeWeapons,
    ready,
    move,
    attack,
    skipTurn,
    leaveRoom,
  };
}
