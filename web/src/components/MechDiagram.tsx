import type { Armor } from '../types';

interface Props {
  armor: Armor;
  maxArmor: Armor;
  label?: string;
  variant?: 'you' | 'enemy';
}

function partColor(current: number, max: number): string {
  if (max <= 0) return '#374151';
  if (current <= 0) return '#1f2937';
  const ratio = current / max;
  if (ratio > 0.6) return '#22c55e';
  if (ratio > 0.3) return '#eab308';
  return '#ef4444';
}

function Part({
  d,
  current,
  max,
  title,
}: {
  d: string;
  current: number;
  max: number;
  title: string;
}) {
  const destroyed = current <= 0;
  return (
    <path
      d={d}
      fill={partColor(current, max)}
      stroke={destroyed ? '#6b7280' : '#94a3b8'}
      strokeWidth={1.5}
      strokeDasharray={destroyed ? '4 2' : undefined}
      opacity={destroyed ? 0.45 : 1}
    >
      <title>{title}: {current}/{max}</title>
    </path>
  );
}

/** Схема меха в стиле BattleTech — голова, корпус, руки, ноги */
export function MechDiagram({ armor, maxArmor, label, variant = 'you' }: Props) {
  const accent = variant === 'you' ? '#3b82f6' : '#dc2626';

  return (
    <div className="mech-diagram">
      {label && <div className="mech-diagram-label">{label}</div>}
      <svg viewBox="0 0 80 120" className="mech-diagram-svg" aria-label="Схема повреждений меха">
        {/* Ноги */}
        <Part
          d="M 22 78 L 28 78 L 30 108 L 20 108 Z"
          current={armor.legs / 2}
          max={maxArmor.legs / 2}
          title="Левая нога"
        />
        <Part
          d="M 52 78 L 58 78 L 60 108 L 50 108 Z"
          current={armor.legs / 2}
          max={maxArmor.legs / 2}
          title="Правая нога"
        />
        {/* Корпус */}
        <Part
          d="M 24 42 L 56 42 L 58 78 L 22 78 Z"
          current={armor.body}
          max={maxArmor.body}
          title="Корпус"
        />
        {/* Левая рука */}
        <Part
          d="M 8 44 L 22 46 L 20 72 L 6 68 Z"
          current={armor.leftArm}
          max={maxArmor.leftArm}
          title="Левая рука"
        />
        {/* Правая рука */}
        <Part
          d="M 58 46 L 72 44 L 74 68 L 60 72 Z"
          current={armor.rightArm}
          max={maxArmor.rightArm}
          title="Правая рука"
        />
        {/* Голова */}
        <Part
          d="M 30 18 L 50 18 L 52 40 L 28 40 Z"
          current={armor.head}
          max={maxArmor.head}
          title="Голова"
        />
        {/* Контур / cockpit */}
        <rect x="34" y="24" width="12" height="8" rx="2" fill={accent} opacity="0.85" />
        <circle cx="40" cy="12" r="3" fill={accent} opacity="0.6" />
      </svg>
      <div className="mech-diagram-legend">
        <span><i className="dot green" /> &gt;60%</span>
        <span><i className="dot yellow" /> &gt;30%</span>
        <span><i className="dot red" /> крит.</span>
        <span><i className="dot gray" /> уничтожено</span>
      </div>
    </div>
  );
}
