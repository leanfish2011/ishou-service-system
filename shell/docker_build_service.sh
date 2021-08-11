# 1、构建Java程序，得到jar包
# 2、将jar包拷贝到docker目录中
# 3、构建镜像
# 说明：工程下需要增加shell目录，此脚本放在shell目录下

#!/bin/bash

# 产品版本
product_version="v2.0"

# 生成的jar包后缀
jar_name_suffix=".jar"

function build_image()
{
    work_path=$(pwd)
    echo "当前目录："$work_path

    cd ../../
    project_path=$(pwd)
    echo "当前项目路径："$project_path

    jar_name=$3""$jar_name_suffix
    echo "待生成镜像的jar包："$jar_name

    # 获取当前分支名称
    branch=$(git symbolic-ref --short -q HEAD)
    echo "当前分支名称："$branch

    # 获取当前分支最后commitId
    latest_commit_id=$(git rev-parse --short HEAD)
    echo "当前分支最后commitId："$latest_commit_id

    # 清理target
    cd $project_path"/"$2
    rm -rf target
    echo "清理"$(pwd)

    cd $project_path"/"$3
    rm -rf target
    echo "清理"$(pwd)

    # 构建
    cd ../
    mvn package
    echo "构建完成"

    # 将jar包拷贝到docker目录中
    cp $project_path"/"$3"/target/"$jar_name $project_path"/docker/service"
    cd $project_path"/docker/service"
    ls

    # 构建镜像名称 工程名称:版本号_分支名称_日期_时间_commitid
    time=$(date "+%Y%m%d_%H%M%S")
    tag=$product_version"_"$branch"_"$time"_"$latest_commit_id
    docker_name=$1":"$tag

    # 执行Dockerfile生成镜像
    sudo docker build -t $docker_name .
    echo "镜像生成完成！"
}

function test(){
    work_path=$(pwd)
    echo "当前工作目录："$work_path

    cd ../
    project_path=$(pwd)
    echo "当前项目路径："$project_path
}

# test
