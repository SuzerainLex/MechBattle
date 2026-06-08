import { useState } from 'react';
import { getMePlayer } from '../normalizeGameState';
import type { GameState, WeaponCatalogItem } from '../types';

interface Props {
  gameState: GameState;
  onEquip: (weapon: string, slot: string) => void;
  onRemove: (category: string) => void;
  onReady: () => void;
}

function WeaponRow({
  item,
  onEquip,
}: {
  item: WeaponCatalogItem;
  onEquip: (weapon: string, slot: string) => void;
}) {
  const [slot, setSlot] = useState(item.handMounted ? 'right' : 'body');

  return (
    <div className="weapon-row">
      <div className="weapon-info">
        <strong>{item.name}</strong>
        <span>
          Урон {item.damage} | Дальн. {item.range} | Вес {item.weight} | ОД {item.cost} | Перегрев{' '}
          {item.heat}
        </span>
      </div>
      {item.handMounted ? (
        <select value={slot} onChange={(e) => setSlot(e.target.value)}>
          <option value="right">Правая рука</option>
          <option value="left">Левая рука</option>
        </select>
      ) : (
        <span className="slot-label">Корпус</span>
      )}
      <button onClick={() => onEquip(item.key, item.handMounted ? slot : 'body')}>
        Установить
      </button>
    </div>
  );
}

export function Workshop({ gameState, onEquip, onRemove, onReady }: Props) {
  const me = getMePlayer(gameState);
  const opponent = gameState.players.find((p) => p.id !== gameState.yourPlayerId);
  const shareUrl = `${window.location.origin}/room/${gameState.roomId}`;

  if (!me) {
    return (
      <div className="panel waiting">
        <p>Загрузка мастерской...</p>
      </div>
    );
  }

  const guns = (gameState.catalog ?? []).filter((c) => c.category === 'GUN');
  const lasers = gameState.catalog.filter((c) => c.category === 'LASER');
  const rockets = gameState.catalog.filter((c) => c.category === 'ROCKET');

  const overweight = me.weaponWeight > me.maxWeight;

  return (
    <div className="panel workshop">
      <h2>Мастерская — комната {gameState.roomId}</h2>
      <p className="hint">
        Ссылка для друга:{' '}
        <a href={shareUrl} onClick={(e) => e.preventDefault()}>
          {shareUrl}
        </a>
        <button className="copy-btn" onClick={() => navigator.clipboard.writeText(shareUrl)}>
          Копировать
        </button>
      </p>

      <div className="workshop-layout">
        <div className="loadout-panel">
          <h3>Ваш loadout</h3>
          <p>
            Вес: <strong className={overweight ? 'bad' : ''}>{me.weaponWeight}</strong> /{' '}
            {me.maxWeight}
          </p>
          <p>
            Пушки: {me.guns}/{me.gunSockets} | Лазеры: {me.lasers}/{me.laserSockets} | Ракеты:{' '}
            {me.rockets}/{me.rocketSockets}
          </p>
          <ul className="installed-list">
            {me.weapons.length === 0 && <li>Оружие не установлено</li>}
            {me.weapons.map((w) => (
              <li key={w.id}>
                {w.name} — {w.slot} ({w.weight}т)
              </li>
            ))}
          </ul>
          <div className="remove-row">
            <button onClick={() => onRemove('GUN')}>Снять пушки</button>
            <button onClick={() => onRemove('LASER')}>Снять лазеры</button>
            <button onClick={() => onRemove('ROCKET')}>Снять ракеты</button>
            <button onClick={() => onRemove('ALL')}>Снять всё</button>
          </div>
          <p className="status">
            Соперник: {opponent?.ready ? '✓ готов' : 'настраивает...'}
            {me.ready && ' | Вы готовы'}
          </p>
          <button
            className="primary"
            disabled={me.ready || me.weapons.length === 0 || overweight}
            onClick={onReady}
          >
            Готов к бою
          </button>
          {overweight && <p className="bad">Превышен вес — снимите оружие</p>}
        </div>

        <div className="catalog-panel">
          <h3>Пушки</h3>
          {guns.map((item) => (
            <WeaponRow key={item.key} item={item} onEquip={onEquip} />
          ))}
          <h3>Лазеры</h3>
          {lasers.map((item) => (
            <WeaponRow key={item.key} item={item} onEquip={onEquip} />
          ))}
          <h3>Ракеты</h3>
          {rockets.map((item) => (
            <WeaponRow key={item.key} item={item} onEquip={onEquip} />
          ))}
        </div>
      </div>
    </div>
  );
}
