#!/bin/bash
set -e
docker image prune -f
docker-compose up -d rabbitMq
sleep 20
docker-compose up -d account-service token-service payment-service bank-service

