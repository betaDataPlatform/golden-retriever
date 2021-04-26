#!/bin/bash
root=$(cd "$(dirname "$0")";pwd)
cd $root/../..
PID=$(cat project.pid)
kill -9 $PID