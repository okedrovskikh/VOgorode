#!/bin/sh 

docker build -t handyman-service:latest ../../HandymanService
docker build -t landscape-service:latest ../../LandscapeService
docker build -t rancher-service:latest ../../RancherService
