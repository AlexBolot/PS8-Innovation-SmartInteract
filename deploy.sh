#!/bin/bash

total=7

print(){
    echo ""
    echo "========================================"
    echo "=========== Deployed  $1 of $total ==========="
    echo "========================================"
    echo ""
}

cd ManageJson-c; mvn clean deploy
print 1
cd ../PrepareGame-c/; mvn clean deploy
print 2
cd ../Monitor-c/; mvn clean deploy
print 3
cd ../AnalyseSyntax-c/; mvn clean deploy
print 4
cd ../GenerateAnswer-c/; mvn clean deploy
print 5
cd ../ProcessInteraction-c/; mvn clean deploy
print 6
cd ../DesignerTool-c/; mvn clean deploy
print 7
