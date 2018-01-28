#!/bin/bash

if [ -f "openchat.pid" ];
then
   PID=`cat "openchat.pid"`
   echo "Stopping system process pid:$PID"
   kill $PID
   rm -rf openchat.pid
else
   echo "File system.pid does not exist - nothing to stop"
fi