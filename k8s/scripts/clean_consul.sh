#!/bin/bash

helm uninstall hashicorp
kubectl delete pvc -l chart=consul-helm
