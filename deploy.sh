#!/bin/bash
set -e

docker image prune -f

docker-compose up -d rabbitMq



