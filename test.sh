#!/bin/bash
set -e
mvn clean test 
docker image prune -f