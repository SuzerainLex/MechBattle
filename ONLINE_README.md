# MechBattle Online

Браузерная онлайн-версия: React + WebSocket + Spring Boot.

## Возможности

- Комнаты с ссылкой `/room/ABC123`
- Выбор меха и полная мастерская (пушки, лазеры, ракеты)
- Пошаговый бой на поле 12×12
- Звуки выстрелов, шагов, попаданий
- Деплой на Railway (сервер) + Vercel (клиент)

## Быстрый старт (локально)

### Сервер

```bash
cd server
mvn spring-boot:run
```

Порт: **8080**, WebSocket: `ws://localhost:8080/ws`

### Клиент

```bash
cd web
npm install
npm run dev
```

Откройте http://localhost:5173

## Как играть

1. **Создать игру** → получите ссылку вида `http://localhost:5173/room/A1B2C3`
2. Отправьте ссылку другу
3. Оба выбирают меха
4. В **мастерской** устанавливают оружие (следите за весом и слотами)
5. **Готов к бою** → начинается бой

## WebSocket API

| Команда | Payload |
|---------|---------|
| `CREATE_ROOM` | `{ playerName }` |
| `JOIN_ROOM` | `{ roomId, playerName }` |
| `SELECT_MECH` | `{ mech: "THOR" \| "MADCAT" \| "LOCUST" \| "ATLAS" }` |
| `EQUIP_WEAPON` | `{ weapon: "MINIGUN", slot: "left" \| "right" \| "body" }` |
| `REMOVE_WEAPONS` | `{ category: "GUN" \| "LASER" \| "ROCKET" \| "ALL" }` |
| `READY` | — |
| `MOVE` | `{ direction: "NORTH" \| "SOUTH" \| "WEST" \| "EAST" }` |
| `ATTACK` | `{ weaponId, aim: 0-10 }` |
| `SKIP_TURN` | — |

## Деплой

### Сервер → Railway

1. Создайте проект на [Railway](https://railway.app)
2. Подключите репозиторий, укажите **Root Directory: `server`**
3. Railway подхватит `Dockerfile`
4. Переменные окружения:
   - `APP_CORS_ORIGINS` = `https://your-app.vercel.app`
   - `PORT` — Railway задаёт автоматически
5. После деплоя URL будет вида `https://mechbattle-server.up.railway.app`
6. WebSocket: `wss://mechbattle-server.up.railway.app/ws`

Проверка: `GET /health` → `{ "status": "ok" }`

### Клиент → Vercel

1. Создайте проект на [Vercel](https://vercel.com)
2. **Root Directory: `web`**
3. Переменная окружения:
   - `VITE_WS_URL` = `wss://mechbattle-server.up.railway.app/ws`
4. `vercel.json` уже настроен для SPA-роутинга (`/room/:id`)

### Docker (всё локально)

```bash
docker compose up --build
```

- Сервер: http://localhost:8080
- Клиент: http://localhost:5173 (nginx)

## Переменные окружения

| Переменная | Где | Описание |
|------------|-----|----------|
| `VITE_WS_URL` | web | URL WebSocket сервера |
| `APP_CORS_ORIGINS` | server | Разрешённые origin через запятую |
| `PORT` | server | Порт (Railway) |

## Структура

```
server/     Java Spring Boot + WebSocket
web/        React + Vite + TypeScript
docker-compose.yml
```

Консольная версия (оригинал): `java -jar MechBattle.jar`
