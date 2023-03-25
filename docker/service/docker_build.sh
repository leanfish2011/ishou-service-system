#!/bin/bash

# 根据各个项目修改
project_name="ishou-service-system"
sdk_name="system-sdk"
service_name="system-service"

# 引入脚本
source ../../shell/docker_build_service.sh

# 入口
build_image $project_name $sdk_name $service_name
