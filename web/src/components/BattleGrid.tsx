import { MechIcon } from './MechIcon';
import { ShotEffect } from './ShotEffect';
import { cellsInRange, hasLineOfSight, MAP_TYPE_LABELS, TERRAIN_MARK } from '../battleUtils';
import type { CombatEvent, TerrainTile } from '../types';

const SIZE = 12;

interface PlayerOnMap {
  id: string;
  name: string;
  mech: string | null;
  x: number;
  y: number;
}

interface Props {
  players: PlayerOnMap[];
  terrain: TerrainTile[];
  mapType: string;
  yourPlayerId: string;
  activePlayerId: string | null;
  selectedWeaponRange: number | null;
  showRange: boolean;
  shotEvent: CombatEvent | null;
}

export function BattleGrid({
  players,
  terrain,
  mapType,
  yourPlayerId,
  activePlayerId,
  selectedWeaponRange,
  showRange,
  shotEvent,
}: Props) {
  const terrainMap = new Map(terrain.map((t) => [`${t.x},${t.y}`, t.type]));
  const me = players.find((p) => p.id === yourPlayerId);
  const enemy = players.find((p) => p.id !== yourPlayerId);
  const isMyTurn = activePlayerId === yourPlayerId;

  const rangeCells =
    showRange && selectedWeaponRange != null && me
      ? cellsInRange(me.x, me.y, selectedWeaponRange)
      : new Set<string>();

  const losOk =
    me && enemy && selectedWeaponRange != null
      ? hasLineOfSight(me.x, me.y, enemy.x, enemy.y, terrainMap)
      : true;

  const cells = [];
  for (let y = SIZE; y >= 0; y--) {
    for (let x = 0; x <= SIZE; x++) {
      const key = `${x},${y}`;
      const terrainType = terrainMap.get(key) ?? 'OPEN';
      const occupant = players.find((p) => p.mech && p.x === x && p.y === y);
      const isYou = occupant?.id === yourPlayerId;
      const inRange = rangeCells.has(key);
      const isTarget = enemy && enemy.x === x && enemy.y === y;

      let cellClass = 'cell';
      if (terrainType !== 'OPEN') cellClass += ` cell-${terrainType.toLowerCase()}`;
      if (inRange && isMyTurn) cellClass += ' cell-in-range';
      if (isTarget && inRange && isMyTurn) cellClass += losOk ? ' cell-los-ok' : ' cell-los-blocked';
      if (occupant) cellClass += isYou ? ' cell-you' : ' cell-enemy';

      cells.push(
        <div key={key} className={cellClass} title={`${x}:${y} ${terrainType}`}>
          {TERRAIN_MARK[terrainType] && (
            <span className="terrain-mark">{TERRAIN_MARK[terrainType]}</span>
          )}
          {occupant && <MechIcon mech={occupant.mech} variant={isYou ? 'you' : 'enemy'} />}
        </div>
      );
    }
  }

  return (
    <div className="battle-map-wrap">
      <p className="map-type-label">
        Карта: <strong>{MAP_TYPE_LABELS[mapType] ?? mapType}</strong>
      </p>
      <div className="battle-grid-container">
        <div className="battle-grid" style={{ gridTemplateColumns: `repeat(${SIZE + 1}, 1fr)` }}>
          {cells}
        </div>
        <ShotEffect event={shotEvent} gridSize={SIZE} />
      </div>
      {showRange && selectedWeaponRange != null && me && enemy && isMyTurn && (
        <p className={`los-indicator ${losOk ? 'ok' : 'bad'}`}>
          {losOk ? '✓ Прямая видимость есть' : '✗ Обзор заблокирован препятствием'}
        </p>
      )}
      <p className="map-legend hint">
        ▧ здание · 🌲 лес (укрытие) · ≋ вода (2 ОД) · ✦ заводской мусор
      </p>
    </div>
  );
}
