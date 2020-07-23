#!/bin/bash

kubectl apply -f ./../07-prometheus/namespace.yaml
kubectl apply -f ./../07-prometheus/grafana.yaml
helm install prometheus stable/prometheus --namespace monitoring
kubectl apply -f ./../07-prometheus/prom-confMap.yaml
