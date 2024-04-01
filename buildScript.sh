#!/bin/bash
set -e

## Build Monomart docker image
echo "Building Monomart jar file"
./gradlew clean bootJar

echo "Building Monomart docker image"
docker build -t monomart .

## Build AGW docker image
echo "Building AGW jar file"
cd agw
./gradlew clean bootJar

echo "Building AGW docker image"
docker build -t monomart-agw .
cd ..

## Build Inventory docker image
echo "Building Inventory jar file"
cd inventory-app
./gradlew clean bootJar

echo "Building Inventory docker image"
docker build -t monomart-inventory .
cd ..
