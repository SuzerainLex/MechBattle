import { useEffect, useState } from 'react';
import { Route, Routes, useNavigate } from 'react-router-dom';
import { Lobby } from './components/Lobby';
import { MechSelect } from './components/MechSelect';
import { Workshop } from './components/Workshop';
import { BattleScreen } from './components/BattleScreen';
import { RoomPage, WaitingRoom } from './components/RoomPage';
import { useGameSocket } from './useGameSocket';
import { useSounds } from './useSounds';
import type { CombatEvent } from './types';
import './App.css';

function GameRoutes() {
  const sounds = useSounds();
  const navigate = useNavigate();
  const [lastCombatEvent, setLastCombatEvent] = useState<CombatEvent | null>(null);

  const socket = useGameSocket((result) => {
    sounds.startMusic();
    if (result.sound) sounds.play(result.sound);
    if (result.success) sounds.playHitOnMessage(result.message);
    if (result.event) setLastCombatEvent(result.event);
  });

  useEffect(() => {
    if (socket.roomId) {
      const guest = window.location.search.includes('guest=1') ? '?guest=1' : '';
      navigate(`/room/${socket.roomId}${guest}`, { replace: true });
    }
  }, [socket.roomId, navigate]);

  const handleLeave = () => {
    socket.leaveRoom();
    navigate('/', { replace: true });
  };

  const phase = socket.gameState?.phase;

  const roomContent = (
    <>
      {socket.roomId && !socket.gameState && socket.isHost && (
        <WaitingRoom roomId={socket.roomId} onLeave={handleLeave} />
      )}
      {socket.roomId && !socket.gameState && !socket.isHost && (
        <div className="panel waiting">
          <h2>Комната {socket.roomId}</h2>
          <p>Подключение к игре...</p>
        </div>
      )}
      {phase === 'MECH_SELECT' && socket.gameState && (
        <MechSelect gameState={socket.gameState} onSelectMech={socket.selectMech} />
      )}
      {phase === 'WORKSHOP' && socket.gameState && (
        <Workshop
          gameState={socket.gameState}
          onEquip={socket.equipWeapon}
          onRemove={socket.removeWeapons}
          onReady={socket.ready}
        />
      )}
      {(phase === 'BATTLE' || phase === 'FINISHED') && socket.gameState && (
        <BattleScreen
          gameState={socket.gameState}
          onMove={socket.move}
          onAttack={socket.attack}
          onSkipTurn={socket.skipTurn}
          lastCombatEvent={lastCombatEvent}
        />
      )}
    </>
  );

  return (
    <div className="app">
      {socket.error && <div className="toast error">{socket.error}</div>}
      {socket.lastMessage && !socket.error && (
        <div className="toast info">{socket.lastMessage}</div>
      )}

      <Routes>
        <Route
          path="/"
          element={
            socket.roomId ? (
              socket.gameState ? null : socket.isHost ? (
                <WaitingRoom roomId={socket.roomId} onLeave={handleLeave} />
              ) : (
                <div className="panel waiting">
                  <h2>Комната {socket.roomId}</h2>
                  <p>Подключение к игре...</p>
                </div>
              )
            ) : (
              <Lobby connected={socket.connected} onCreateRoom={socket.createRoom} />
            )
          }
        />
        <Route
          path="/room/:roomId"
          element={
            !socket.roomId ? (
              <RoomPage
                connected={socket.connected}
                reconnecting={socket.reconnecting}
                roomId={socket.roomId}
                onJoinRoom={socket.joinRoom}
              />
            ) : (
              roomContent
            )
          }
        />
      </Routes>
    </div>
  );
}

export default function App() {
  return <GameRoutes />;
}
