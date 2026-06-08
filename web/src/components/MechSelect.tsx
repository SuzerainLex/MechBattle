import { MECHS } from '../types';
import type { GameState } from '../types';

interface Props {
  gameState: GameState;
  onSelectMech: (mech: string) => void;
}

export function MechSelect({ gameState, onSelectMech }: Props) {
  const me = gameState.players.find((p) => p.id === gameState.yourPlayerId);
  const opponent = gameState.players.find((p) => p.id !== gameState.yourPlayerId);
  const shareUrl = `${window.location.origin}/room/${gameState.roomId}`;

  return (
    <div className="panel mech-select">
      <h2>Выбор меха</h2>
      <p className="hint">
        Комната <strong>{gameState.roomId}</strong>. Ссылка:{' '}
        <a href={shareUrl}>{shareUrl}</a>
        <button className="copy-btn" onClick={() => navigator.clipboard.writeText(shareUrl)}>
          Копировать
        </button>
      </p>
      <div className="mech-grid">
        {MECHS.map((mech) => (
          <button
            key={mech.id}
            className={`mech-card ${me?.mech === mech.id ? 'selected' : ''}`}
            onClick={() => onSelectMech(mech.id)}
          >
            <strong>{mech.name}</strong>
            <span>{mech.desc}</span>
          </button>
        ))}
      </div>
      <p className="status">
        Вы: {me?.mech ?? 'не выбран'}
        <br />
        Соперник: {opponent?.mech ?? 'ожидание...'}
      </p>
      {me?.mech && opponent?.mech && (
        <p className="hint">Оба выбрали мехов — откроется мастерская</p>
      )}
    </div>
  );
}
