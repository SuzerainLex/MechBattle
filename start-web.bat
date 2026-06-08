@echo off
echo Starting MechBattle web client...
cd /d "%~dp0web"
call npm run dev
