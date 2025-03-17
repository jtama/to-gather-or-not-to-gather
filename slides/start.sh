#!/bin/bash

# Start Docker Compose
podman compose up -d

# Wait for the connect service to be up
echo "Waiting for connect service to be up..."
until [ "$(curl -s -o /dev/null -w ''%{http_code}'' http://localhost:8083)" == "200" ]; do
  sleep 5
done

# Send the content of the JSON file to the endpoint
echo "Sending JSON content to the endpoint..."
curl -X POST -H "Content-Type: application/json" --data @postgres-debezium-connector.json http://localhost:8083/connectors