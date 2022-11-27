@echo off
echo "[1] Start-and-poll Docker Swarm in the background till it can be started... "
start /B docker-polling-start-activemq-swarm.bat
echo "[2] Start Docker EE in the background ... "
"C:\Program Files\Docker\Docker\Docker Desktop.exe"
set /p id="Finish"
