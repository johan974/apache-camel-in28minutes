@echo off

:repeat
docker ps -a >> output.txt || ( timeout /t 10 && goto :repeat; )
echo " ... Docker started ... "
timeout /t 7
echo "[4] Starting a Docker Swarm with Postgres ... "
docker stack deploy -c docker-compose.yml devhome
echo " ... Docker Swarm started ... "
