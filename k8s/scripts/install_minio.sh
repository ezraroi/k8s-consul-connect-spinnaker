#!/bin/bash

helm install minio --set accessKey=minio,secretKey=minio1234 stable/minio