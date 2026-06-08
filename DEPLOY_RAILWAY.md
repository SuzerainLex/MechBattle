# Деплой MechBattle Online

## 1. Сервер → Railway

1. Откройте [railway.app](https://railway.app) → **New Project** → **Deploy from GitHub repo**
2. Выберите репозиторий `SuzerainLex/MechBattle`
3. **Root Directory:** оставьте **`/`** (корень) — там есть `Dockerfile` и `railway.toml`  
   *Альтернатива:* Root Directory = `server` (тогда используется `server/Dockerfile`)
4. Railway подхватит Docker-сборку (не Railpack/Nixpacks)
5. **Variables** (пока без Vercel — можно `*` для теста, потом замените):

   | Переменная | Значение |
   |------------|----------|
   | `APP_CORS_ORIGINS` | `https://ВАШ-ПРОЕКТ.vercel.app` |

   `PORT` Railway задаёт сам — не трогайте.

6. **Settings → Networking → Generate Domain** → получите URL вида  
   `https://mechbattle-production-xxxx.up.railway.app`
7. Проверка: `https://ВАШ-URL.up.railway.app/health` → `{"status":"ok"}`

WebSocket: `wss://ВАШ-URL.up.railway.app/ws`

---

## 2. Клиент → Vercel

1. [vercel.com](https://vercel.com) → **Add New Project** → импорт `SuzerainLex/MechBattle`
2. **Root Directory:** `web`
3. **Environment Variable:**

   | Имя | Значение |
   |-----|----------|
   | `VITE_WS_URL` | `wss://ВАШ-URL.up.railway.app/ws` |

4. Deploy
5. В Railway обновите `APP_CORS_ORIGINS` на точный URL Vercel (без `/` в конце)
6. Redeploy сервера на Railway (или Restart)

---

## 3. Игра

1. Откройте сайт Vercel
2. **Создать игру** → скопируйте ссылку с `?guest=1`
3. Отправьте другу — играйте из любой сети

---

## Локально

```bash
cd server && mvn spring-boot:run
cd web && npm install && npm run dev
```
