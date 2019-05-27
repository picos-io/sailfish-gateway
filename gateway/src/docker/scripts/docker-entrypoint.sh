#!/bin/bash
##-------------------------------------------------------------------
## @copyright 2017 SoteriaNetworks.com
## All Rights Reserved.
##
## File : docker-entrypoint.sh
## Author : developer@soterianetworks.com
## Description :
## --
## Created : <2017-03-28>
## Updated: Time-stamp: <2017-05-18 10:52:50>
##-------------------------------------------------------------------
set -e

# start service accordingly
/wait_for.sh "nc -z -v -w 5 ${DB_HOST} ${DB_PORT}" 300

JAR_VERSION=1.0.0
echo "Using JAVA_OPTS=$JAVA_OPTS"
echo "CMD Args: $@"
java $JAVA_OPTS -jar "/gateway-${JAR_VERSION}-SNAPSHOT.jar" "$@"

## File : docker-entrypoint.sh ends
