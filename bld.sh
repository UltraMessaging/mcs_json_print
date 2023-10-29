#!/bin/sh
# bld.sh

if [ ! -f "lbm.sh" ]; then :
  echo "Must create 'lbm.sh' file (use 'lbm.sh.example' as guide)." >&2
  exit 1
fi

. ./lbm.sh

rm -rf *.class *.jar

# Build json print module.
javac -cp ./LBMMonCallbacks.class:$L/MCS/lib/MCS.jar:$L/MCS/lib/UMS_6.15.jar:$L/MCS/lib/UMSMON_PROTO3.jar:$L/MCS/lib/um-mondb-common.jar:$L/MCS/lib/protobuf-java-util-4.0.0-rc-2.jar:$L/MCS/lib/protobuf-java-4.0.0-rc-2.jar:$L/MCS/lib/gson-2.8.5.jar:$L/MCS/lib/java-getopt-1.0.13.jar:$L/MCS/lib/log4j-api-2.14.1.jar:$L/MCS/lib/log4j-core-2.14.1.jar:$L/MCS/lib/guava-24.1.1-jre.jar JsonPrint.java
if [ "$?" -ne 0 ]; then echo "`date` Error" >&2; exit 1; fi

jar cf JsonPrint.jar *.class
if [ "$?" -ne 0 ]; then echo "`date` Error" >&2; exit 1; fi
