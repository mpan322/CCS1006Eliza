# !/bin/bash
find -name "*.java" > javac
# rm "sources.txt"

cd "./Eliza/src/Tests"

java "Tester"

# ./TestShellScripts/inputIntoJava.sh test.txt

cd ../../
find -name "*.class" > classFiles.txt
for f in $(cat classFiles.txt) ; do
    rm "$f"
done
rm "classFiles.txt"