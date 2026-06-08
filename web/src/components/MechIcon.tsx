interface Props {
  mech: string | null;
  variant: 'you' | 'enemy';
}

const MECH_SHAPES: Record<string, { body: string; accent: string }> = {
  THOR: {
    body: 'M12 2 L16 7 L20 8 L17 13 L18 20 L12 18 L6 20 L7 13 L4 8 L8 7 Z',
    accent: '#64748b',
  },
  MADCAT: {
    body: 'M12 3 L15 6 L19 7 L16 11 L17 19 L12 17 L7 19 L8 11 L5 7 L9 6 Z',
    accent: '#a855f7',
  },
  LOCUST: {
    body: 'M12 4 L14 8 L18 9 L15 12 L14 18 L12 16 L10 18 L9 12 L6 9 L10 8 Z',
    accent: '#22c55e',
  },
  ATLAS: {
    body: 'M12 1 L17 6 L21 8 L18 14 L19 22 L12 19 L5 22 L6 14 L3 8 L7 6 Z',
    accent: '#f59e0b',
  },
};

export function MechIcon({ mech, variant }: Props) {
  const fill = variant === 'you' ? '#2563eb' : '#b91c1c';
  const stroke = variant === 'you' ? '#93c5fd' : '#fca5a5';
  const shape = mech ? MECH_SHAPES[mech] : null;

  return (
    <svg viewBox="0 0 24 24" className="mech-icon" aria-hidden>
      <path
        d={shape?.body ?? 'M12 3 L14 8 L18 9 L15 12 L16 18 L12 16 L8 18 L9 12 L6 9 L10 8 Z'}
        fill={fill}
        stroke={stroke}
        strokeWidth="1.2"
      />
      <circle cx="12" cy="10" r="2" fill={shape?.accent ?? '#e2e8f0'} opacity="0.9" />
    </svg>
  );
}
