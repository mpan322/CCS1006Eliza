#!/bin/bash
find -name "*.java" > sources.txt
javac @sources.txt
cd "./Eliza/src/"
cat ./Tests/TestShellScripts/test.txt | java Main
find -name "*.class" > classFiles.txt
for f in $(cat classFiles.txt) ; do
    rm "$f"
done


