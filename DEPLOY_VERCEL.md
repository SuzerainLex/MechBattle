# Деплой клиента на Vercel

Фронтенд (React) — **Vercel**.  
Сервер (WebSocket) — **Railway** (Vercel не поддерживает Java WebSocket).

---

## Быстрый старт (через сайт)

### 1. Сервер на Railway (если ещё нет)

1. [railway.app](https://railway.app) → Deploy **SuzerainLex/MechBattle**
2. **Root Directory:** `/` (корень репо) — или `server` для альтернативного Dockerfile
3. **Settings → Build:** Builder = **Dockerfile** (не Nixpacks/Railpack)
4. **Generate Domain** → URL вида `https://xxx.up.railway.app`

### 2. Клиент на Vercel

1. [vercel.com/new](https://vercel.com/new) → Import **SuzerainLex/MechBattle**
2. **Root Directory:** `web` ← обязательно!
3. **Environment Variables** → Production:

   | Name | Value |
   |------|--------|
   | `VITE_WS_URL` | `wss://ВАШ-RAILWAY-URL.up.railway.app/ws` |

4. **Deploy**
5. Скопируйте URL Vercel, например `https://mechbattle.vercel.app`

### 3. CORS на Railway

В Railway → Variables:

```
APP_CORS_ORIGINS=https://mechbattle.vercel.app
```

Restart сервера.

### 4. Игра

Откройте URL Vercel → Создать игру → ссылка с `?guest=1` другу.

---

## Через CLI

```bash
cd web
npm install
npx vercel login
npx vercel --prod
# При запросе VITE_WS_URL: wss://YOUR-RAILWAY.up.railway.app/ws
```

Переменную можно задать в [Vercel Dashboard](https://vercel.com) → Project → Settings → Environment Variables, затем **Redeploy**.

---

## Проверка

- Сайт открывается, внизу «Подключено к серверу»
- Если «Ошибка WebSocket» — проверьте `VITE_WS_URL` (должен быть **wss://**, не ws://)
- После смены `VITE_WS_URL` нужен **Redeploy** на Vercel (переменная вшивается при сборке)
