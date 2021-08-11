#!/bin/sh

# 如果某条结果不是true，则返回
set -e

log(){
    time=$(date "+%Y-%m-%d %H:%M:%S")
    echo $time $*
}

##################### 1、备份数据库 ####################
backup_path="/home/mysql/backup"
need_dump_database="ishou_system"

# 查询所有库
databases=$(mysql -uroot -p$MYSQL_ROOT_PASSWORD -h127.0.0.1 -e "show databases;")
log "all databases:"$databases

log "need dump databases:"$need_dump_database;

# 导出库
time=$(date "+%Y%m%d_%H%M%S");
mysqldump -uroot -p$MYSQL_ROOT_PASSWORD -h127.0.0.1 --databases $need_dump_database > $backup_path/$need_dump_database"_"$time.sql;

log "数据库备份完成！"

#################### 2、执行sql ####################
# 指明sql脚本所在目录
sql_path="/scripts";
log "进入脚本目录"$sql_path

# 读取sql
filelist=`ls $sql_path/*.sql`
for file_name in $filelist
do
    mysql -uroot -p$MYSQL_ROOT_PASSWORD -h127.0.0.1 --default-character-set=utf8mb4 -e "source $file_name;"
    log "执行sql: $file_name 完成";
done

log "数据库初始化完成！"
