#!/bin/bash
find -name "*.java" > sources.tx
javac @sources.txt
cd "./Eliza/src/"
cat ./Tests/TestShellScripts/$1 | java Main
find -name "*.class" > classFiles.txt
for f in $(cat classFiles.txt) ; do
    rm "$f"
done


