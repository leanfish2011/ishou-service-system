# 1、构建Java程序，得到jar包
# 2、将jar包拷贝到docker目录中
# 3、构建镜像

#!/bin/bash

# 整个项目父路径
project_path_prefix="/home/tim/git/ishou"

# 版本
version="v1.0"

# 生成的jar包后缀
jar_name_suffix="0.0.1-SNAPSHOT.jar"

function build_image()
{
    project_path=$project_path_prefix"/"$1
    jar_name=$3"-"$jar_name_suffix
    echo $project_path
    echo $jar_name
    cd $project_path
    latest_commit_id=$(git rev-parse --short HEAD)
    branch=$(git symbolic-ref --short -q HEAD)

    cd $project_path"/"$2
    rm -rf target
    cd $project_path"/"$3
    rm -rf target

    cd ../
    mvn package
    cp $project_path"/"$3"/target/"$jar_name $project_path"/docker"
    cd $project_path"/docker"

    time=$(date "+%Y%m%d_%H%M%S")
    tag=$version"_"$branch"_"$time"_"$latest_commit_id
    docker_name=$1":"$tag
    sudo docker build -t $docker_name .
}

function test(){
    echo $project_path_prefix
}

# 根据各个项目修改
project_name="ishou-service-system"
sdk_name="system-sdk"
service_name="system-service"

# 入口
build_image $project_name $sdk_name $service_name
