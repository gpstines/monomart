#!/bin/bash
set -e

## Build Monomart docker image
echo "************** Building Monomart jar file **************"
./gradlew clean bootJar

echo "************** Building Monomart docker image **************"
docker build -t monomart-final .

## Build AGW docker image
echo "************** Building AGW jar file **************"
cd agw
./gradlew clean bootJar

echo "************** Building AGW docker image **************"
docker build -t monomart-agw-final .
cd ..

## Build Inventory docker image
echo "************** Building Inventory jar file **************"
cd inventory
./gradlew clean bootJar

echo "************** Building Inventory docker image **************"
docker build -t monomart-inventory-final .
cd ..

## Build Commerce docker image
echo "************** Building Commerce jar file **************"
cd commerce
./gradlew clean bootJar

echo "************** Building Commerce docker image **************"
docker build -t monomart-commerce-final .
cd ..

## Build Config Server docker image
echo "************** Building Config Server jar file **************"
cd config-server
./gradlew clean bootJar

echo "************** Building Config Server docker image **************"
docker build -t monomart-config-final .
cd ..

## Build Config Server docker image
echo "Building Config Server jar file"
cd config-server
./gradlew clean bootJar

echo "Building Config Server docker image"
docker build -t monomart-config .
cd ..
