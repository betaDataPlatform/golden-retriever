#!/bin/bash
source /etc/profile
export
root=$(cd "$(dirname "$0")";pwd)
cd $root/../..
nohup java -Dloader.path=lib -Dlogging.config=./config/logback.xml -Xms150M -Xmx400M -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=26009 -jar lib/boot/application-0.0.1-SNAPSHOT.jar --spring.profiles.active=timescale >/dev/null 2>log &
echo $! > project.pid