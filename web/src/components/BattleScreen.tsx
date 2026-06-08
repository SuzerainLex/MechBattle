import { useEffect, useState } from 'react';
import { BattleGrid } from './BattleGrid';
import { MechDiagram } from './MechDiagram';
import { HeatMeter } from './HeatMeter';
import { getMePlayer } from '../normalizeGameState';
import type { CombatEvent, GameState, Player } from '../types';

interface Props {
  gameState: GameState;
  onMove: (direction: string) => void;
  onAttack: (weaponId: string, aim: number) => void;
  onSkipTurn: () => void;
  lastCombatEvent: CombatEvent | null;
}

function PlayerPanel({ player, label, variant }: { player: Player; label: string; variant: 'you' | 'enemy' }) {
  return (
    <div className="player-panel">
      <h3>{label}: {player.name}</h3>
      <p className="mech-name">{player.mech ?? '—'}</p>
      <MechDiagram armor={player.armor} maxArmor={player.maxArmor} variant={variant} />
      <HeatMeter heat={player.heat} radiator={player.radiator} />
      <p>Позиция: {player.x}:{player.y}</p>
      <p>Очки действий: {player.actionPoints} / {player.maxActionPoints}</p>
    </div>
  );
}

export function BattleScreen({ gameState, onMove, onAttack, onSkipTurn, lastCombatEvent }: Props) {
  const [selectedWeapon, setSelectedWeapon] = useState<string | null>(null);
  const [aim, setAim] = useState(5);
  const [shotEvent, setShotEvent] = useState<CombatEvent | null>(null);

  useEffect(() => {
    if (lastCombatEvent?.type === 'SHOT') {
      setShotEvent(lastCombatEvent);
    }
  }, [lastCombatEvent]);

  const me = getMePlayer(gameState);
  const enemy = gameState.players.find((p) => p.id !== gameState.yourPlayerId);
  const isMyTurn = gameState.activePlayerId === gameState.yourPlayerId;
  const finished = gameState.phase === 'FINISHED';

  if (!me || !enemy) {
    return (
      <div className="panel waiting">
        <p>Загрузка боя...</p>
      </div>
    );
  }

  const selected = me.weapons.find((w) => w.id === selectedWeapon);
  const canShoot = selected?.canFire ?? false;

  return (
    <div className="battle-screen">
      <header className="battle-header">
        <h2>Бой — комната {gameState.roomId}</h2>
        {finished ? (
          <p className="turn-banner victory">
            {gameState.winnerId === gameState.yourPlayerId ? 'Вы победили!' : 'Вы проиграли'}
          </p>
        ) : (
          <p className={`turn-banner ${isMyTurn ? 'my-turn' : ''}`}>
            {isMyTurn ? 'Ваш ход' : `Ход соперника (${enemy.name})`}
          </p>
        )}
      </header>

      <div className="battle-layout">
        <PlayerPanel player={me} label="Вы" variant="you" />
        <BattleGrid
          players={gameState.players}
          terrain={gameState.terrain ?? []}
          mapType={gameState.mapType ?? 'STANDARD'}
          yourPlayerId={gameState.yourPlayerId}
          activePlayerId={gameState.activePlayerId}
          selectedWeaponRange={selected?.range ?? null}
          showRange={isMyTurn && !finished}
          shotEvent={shotEvent}
        />
        <PlayerPanel player={enemy} label="Враг" variant="enemy" />
      </div>

      {!finished && isMyTurn && (
        <div className="actions panel">
          <h3>Действия</h3>
          <div className="action-row">
            <span>Движение:</span>
            <button onClick={() => onMove('NORTH')}>↑ Север</button>
            <button onClick={() => onMove('SOUTH')}>↓ Юг</button>
            <button onClick={() => onMove('WEST')}>← Запад</button>
            <button onClick={() => onMove('EAST')}>→ Восток</button>
          </div>

          <div className="action-row weapon-row">
            <span>Атака:</span>
            {me.weapons.map((w) => (
              <button
                key={w.id}
                className={`weapon-btn ${selectedWeapon === w.id ? 'selected' : ''}`}
                disabled={!w.canFire}
                title={w.canFire ? `${w.name} — дальн. ${w.range}` : w.disabledReason}
                onClick={() => {
                  setSelectedWeapon(w.id);
                }}
              >
                {w.name}
                {!w.inRange && ' 📏'}
                {w.inRange && !w.hasLineOfSight && ' 🧱'}
              </button>
            ))}
          </div>

          <div className="action-row">
            <label>
              Прицел 0–10:
              <input type="range" min={0} max={10} value={aim} disabled={!canShoot}
                onChange={(e) => setAim(Number(e.target.value))} />
              <strong>{aim}</strong>
            </label>
            <button disabled={!canShoot} onClick={() => selectedWeapon && onAttack(selectedWeapon, aim)}>
              Стрелять
            </button>
          </div>

          {selected && !selected.canFire && <p className="hint bad">⚠ {selected.disabledReason}</p>}

          <button className="secondary" onClick={onSkipTurn}>Пропустить ход</button>
        </div>
      )}

      <div className="log panel">
        <h3>Лог боя</h3>
        <ul>
          {[...gameState.log].reverse().slice(0, 15).map((line, i) => (
            <li key={i} className={line.includes('КРИТ') ? 'log-crit' : undefined}>{line}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}
