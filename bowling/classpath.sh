#!/bin/bash
NEEDED="junit4 commons-beanutils commons-logging antlr commons-collections"
for JAR in $NEEDED
do
    CLASSPATH=$CLASSPATH:/usr/share/java/$JAR.jar
done
CLASSPATH=$CLASSPATH:build
export CLASSPATH
