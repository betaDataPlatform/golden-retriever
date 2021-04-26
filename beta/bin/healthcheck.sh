#!/bin/bash
result_code=`curl -I -m 5 -o /dev/null -s -w %{http_code} http://localhost:26002`
if [[ $result_code -ne 200 ]] ; then
   echo 1
else
    echo 0
fi
