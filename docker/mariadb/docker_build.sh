#!/bin/bash

# 项目信息
db_name="ishou_system"

# 引入脚本
source ../../shell/docker_build_init.sh

# 调用引入脚本中方法
build_image $db_name
