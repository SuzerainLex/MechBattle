interface Props {
  heat: number;
  radiator: number;
}

export function HeatMeter({ heat, radiator }: Props) {
  const ratio = radiator > 0 ? Math.min(heat / radiator, 1.5) : 0;
  const pct = Math.min(ratio * 100, 150);
  const shutdown = heat > radiator;
  const warning = heat > radiator * 0.75 && !shutdown;

  let barClass = 'heat-fill ok';
  if (warning) barClass = 'heat-fill warn';
  if (shutdown) barClass = 'heat-fill danger';

  return (
    <div className="heat-meter">
      <div className="heat-label">
        <span>Перегрев</span>
        <span>{heat} / {radiator}</span>
      </div>
      <div className="heat-track">
        <div className={barClass} style={{ width: `${Math.min(pct, 100)}%` }} />
        <div className="heat-threshold" style={{ left: '100%' }} title="Лимит радиатора" />
      </div>
      {shutdown && <p className="heat-alert">⚠ SHUTDOWN — стрельба заблокирована!</p>}
      {warning && !shutdown && <p className="heat-warn">Температура высокая</p>}
    </div>
  );
}
