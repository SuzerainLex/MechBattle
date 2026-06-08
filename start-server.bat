@echo off
echo Starting MechBattle server on port 8080...
cd /d "%~dp0server"
mvn -q spring-boot:run
