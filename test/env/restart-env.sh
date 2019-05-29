#!/bin/sh -e
docker-compose down
docker-compose pull
docker-compose up -d
