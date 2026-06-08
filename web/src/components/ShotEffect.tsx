import { useEffect, useState } from 'react';
import type { CombatEvent } from '../types';

interface Props {
  event: CombatEvent | null;
  gridSize: number;
}

export function ShotEffect({ event, gridSize }: Props) {
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    if (!event || event.type !== 'SHOT') return;
    setVisible(true);
    const t = setTimeout(() => setVisible(false), 600);
    return () => clearTimeout(t);
  }, [event]);

  if (!visible || !event) return null;

  const cols = gridSize + 1;
  const cellPct = 100 / cols;

  const toPct = (coord: number) => (coord + 0.5) * cellPct;

  const x1 = toPct(event.fromX);
  const y1 = toPct(gridSize - event.fromY);
  const x2 = toPct(event.toX);
  const y2 = toPct(gridSize - event.toY);

  const isLaser = event.weaponCategory === 'LASER';
  const color = event.critical ? '#fbbf24' : event.hit ? (isLaser ? '#ef4444' : '#f97316') : '#94a3b8';

  return (
    <svg className="shot-effect" viewBox="0 0 100 100" preserveAspectRatio="none">
      <line
        x1={x1}
        y1={y1}
        x2={x2}
        y2={y2}
        stroke={color}
        strokeWidth={event.critical ? 1.2 : 0.6}
        strokeLinecap="round"
        className={isLaser ? 'shot-laser' : 'shot-bullet'}
      />
      {event.hit && (
        <circle cx={x2} cy={y2} r={event.critical ? 2.5 : 1.5} fill={color} className="shot-impact" />
      )}
    </svg>
  );
}
