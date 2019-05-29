#!/bin/bash -e
docker build --no-cache -f Dockerfile -t picos/sailfish-gateway --rm=true ../..
docker push picos/sailfish-gateway
