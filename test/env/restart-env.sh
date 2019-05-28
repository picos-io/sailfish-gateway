#!/bin/sh -e
docker-compose down -v
docker-compose pull
docker-compose up -d
