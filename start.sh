#!/usr/bin/bash

mvn clean package
docker build -t game-library .
docker compose up