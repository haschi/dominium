#!/usr/bin/env bash
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker images | awk '$0 ~ /haushaltsbuch/ { print $3 }' | xargs docker rmi -f
docker images | awk '$0 ~ /none/ { print $3 }' | xargs docker rmi -f