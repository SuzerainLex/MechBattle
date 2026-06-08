import { useNavigate } from 'react-router-dom';

interface Props {
  connected: boolean;
  onCreateRoom: (name: string) => void;
}

export function Lobby({ connected, onCreateRoom }: Props) {
  const navigate = useNavigate();

  const handleCreate = () => {
    const name = prompt('Ваше имя:', 'Игрок 1');
    if (name) onCreateRoom(name);
  };

  const handleJoin = () => {
    const roomId = prompt('Код комнаты:');
    if (roomId) {
      navigate(`/room/${roomId.toUpperCase()}?guest=1`);
    }
  };

  return (
    <div className="lobby panel">
      <h1>MechBattle Online</h1>
      <p className="subtitle">Пошаговые бои мехов 1 на 1 в браузере</p>
      <p className={`connection ${connected ? 'ok' : 'bad'}`}>
        {connected ? 'Подключено к серверу' : 'Подключение к серверу...'}
      </p>
      {!connected && (
        <p className="hint bad">
          Если долго не подключается — запустите сервер:{' '}
          <code>cd server && mvn spring-boot:run</code>
        </p>
      )}
      <div className="lobby-actions">
        <button className="primary" disabled={!connected} onClick={handleCreate}>
          Создать игру
        </button>
        <button className="secondary" disabled={!connected} onClick={handleJoin}>
          Войти по коду
        </button>
      </div>
      <ul className="rules">
        <li>Создайте комнату и отправьте ссылку другу</li>
        <li>Выберите меха, настройте оружие в мастерской</li>
        <li>Поле 12×12, пошаговый бой с прицелом 0–10</li>
      </ul>
    </div>
  );
}
