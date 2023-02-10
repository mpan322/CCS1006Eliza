#!/bin/bash

cd ..
echo $1
pwd
echo
cat ./Tests/TestShellScripts/$1 | java Main
