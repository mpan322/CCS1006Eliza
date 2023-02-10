#!/bin/bash
find -name "*.java" > sources.txt
javac @sources.txt
rm "sources.txt"

java Main
find -name "*.class" > classFiles.txt
for f in $(cat classFiles.txt) ; do
    rm "$f"
done
rm "classFiles.txt"