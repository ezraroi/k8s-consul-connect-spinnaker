#!/bin/bash

kubectl apply -f ./../05-tracing/01-jaegertracing_namespace.yaml
kubectl apply -f ./../05-tracing/02-jaegertracing.io_jaegers_crd.yaml -n observability
kubectl apply -f ./../05-tracing/03-jaegertracing_combinied.yaml -n observability
kubectl apply -f ./../05-tracing/04-jaegertracing_roles.yaml -n observability
kubectl apply -f ./../05-tracing/all_in_one.yaml