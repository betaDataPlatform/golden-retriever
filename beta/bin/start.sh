#!/bin/bash
source /etc/profile
export
root=$(cd "$(dirname "$0")";pwd)
cd $root/../..
export LD_LIBRARY_PATH=$root/../../gammu/lib64
nohup java -Dloader.path=lib -Dlogging.config=./config/logback.xml -Xms150M -Xmx400M -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=26003 -jar lib/boot/app-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev >/dev/null 2>log &
echo $! > project.pid