import { useEffect, useState } from 'react';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';

interface Props {
  connected: boolean;
  reconnecting: boolean;
  roomId: string | null;
  onJoinRoom: (roomId: string, playerName: string) => void;
}

function joinKey(roomId: string) {
  return `mechbattle-join-${roomId.toUpperCase()}`;
}

export function RoomPage({ connected, reconnecting, roomId, onJoinRoom }: Props) {
  const { roomId: urlRoomId } = useParams<{ roomId: string }>();
  const [searchParams] = useSearchParams();
  const isGuest = searchParams.get('guest') === '1';
  const navigate = useNavigate();
  const [playerName, setPlayerName] = useState('Игрок 2');
  const [joinSent, setJoinSent] = useState(false);

  useEffect(() => {
    if (!isGuest || !urlRoomId) return;
    try {
      const saved = sessionStorage.getItem('mechbattle-session');
      if (saved) {
        const session = JSON.parse(saved) as { role?: string };
        if (session.role === 'host') {
          sessionStorage.removeItem('mechbattle-session');
        }
      }
    } catch {
      // ignore
    }
  }, [isGuest, urlRoomId]);

  useEffect(() => {
    if (!isGuest || !urlRoomId || roomId || joinSent) return;

    const savedName = sessionStorage.getItem(joinKey(urlRoomId));
    if (savedName) {
      setJoinSent(true);
      onJoinRoom(urlRoomId.toUpperCase(), savedName);
    }
  }, [isGuest, urlRoomId, roomId, joinSent, onJoinRoom]);

  useEffect(() => {
    if (isGuest || !connected || !urlRoomId || roomId) return;

    const saved = sessionStorage.getItem('mechbattle-session');
    if (saved) {
      try {
        const session = JSON.parse(saved) as { roomId: string };
        if (session.roomId === urlRoomId.toUpperCase()) {
          return;
        }
      } catch {
        // ignore
      }
    }
  }, [isGuest, connected, urlRoomId, roomId]);

  const handleJoin = (e: React.FormEvent) => {
    e.preventDefault();
    if (!urlRoomId || !playerName.trim()) return;
    sessionStorage.setItem(joinKey(urlRoomId), playerName.trim());
    setJoinSent(true);
    onJoinRoom(urlRoomId.toUpperCase(), playerName.trim());
  };

  if (roomId) {
    return null;
  }

  if (isGuest && !joinSent) {
    return (
      <div className="panel waiting">
        <h2>Вход в комнату {urlRoomId?.toUpperCase()}</h2>
        <form className="join-form" onSubmit={handleJoin}>
          <label>
            Ваше имя:
            <input
              value={playerName}
              onChange={(e) => setPlayerName(e.target.value)}
              autoFocus
              disabled={!connected}
            />
          </label>
          <button type="submit" className="primary" disabled={!connected || !playerName.trim()}>
            Войти в игру
          </button>
        </form>
        {!connected && (
          <p className="hint bad">
            {reconnecting ? 'Переподключение к серверу...' : 'Ожидание сервера...'}
          </p>
        )}
      </div>
    );
  }

  return (
    <div className="panel waiting">
      <h2>Комната {urlRoomId?.toUpperCase()}</h2>
      <p>
        {connected
          ? joinSent
            ? 'Подключение к комнате...'
            : 'Ожидание сервера...'
          : reconnecting
            ? 'Переподключение к серверу...'
            : 'Ожидание сервера...'}
      </p>
      {!connected && !reconnecting && (
        <p className="hint bad">
          Сервер не отвечает. Запустите: <code>cd server && mvn spring-boot:run</code>
        </p>
      )}
      {isGuest && (
        <button className="secondary" onClick={() => navigate('/')}>
          На главную
        </button>
      )}
    </div>
  );
}

interface WaitingProps {
  roomId: string;
  onLeave?: () => void;
}

export function WaitingRoom({ roomId, onLeave }: WaitingProps) {
  const shareUrl = `${window.location.origin}/room/${roomId}?guest=1`;

  return (
    <div className="panel waiting">
      <h2>Комната {roomId}</h2>
      <p>Ожидание второго игрока...</p>
      <p className="hint">
        Откройте эту ссылку во <strong>второй вкладке</strong> или в режиме инкогнито.
        Код комнаты: <strong>{roomId}</strong>
        <br />
        <a href={shareUrl} target="_blank" rel="noreferrer">
          {shareUrl}
        </a>
        <button className="copy-btn" onClick={() => navigator.clipboard.writeText(shareUrl)}>
          Копировать
        </button>
      </p>
      {onLeave && (
        <button className="secondary" onClick={onLeave}>
          Выйти и создать новую комнату
        </button>
      )}
    </div>
  );
}
