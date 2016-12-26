#!/bin/bash

javac MyBot.java
if [ $? -eq 0 ]
then
    javac RandomBot.java
    ./halite -d "30 30" "java MyBot" "java RandomBot"
fi
