#!/bin/bash -e
TOKEN=$(curl -s -X POST -d "username=demouser&password=hello-oauth2&grant_type=password&client_id=gateway&client_secret=gateway-secret" http://127.0.0.1:9999/oauth/token | jq .access_token )
echo "$TOKEN"
curl -s -X GET -H "Authorization: Bearer ${TOKEN//\"/}" http://127.0.0.1:8080/helloworld/foo
