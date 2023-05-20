#!/bin/sh

kubectl apply -f ../deployment
kubectl apply -f ../../HandymanService/deployment
kubectl apply -f ../../LandscapeService/deployment
kubectl apply -f ../../RancherService/deployment
