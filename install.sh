#!/bin/bash

total=7

print(){
    echo ""
    echo "========================================"
    echo "=========== Installed $1 of $total ==========="
    echo "========================================"
    echo ""
}

cd ManageJson-c; mvn clean install
print 1
cd ../PrepareGame-c/; mvn clean install
print 2
cd ../Monitor-c/; mvn clean install
print 3
cd ../AnalyseSyntax-c/; mvn clean install
print 4
cd ../GenerateAnswer-c/; mvn clean install
print 5
cd ../ProcessInteraction-c/; mvn clean install
print 6
cd ../DesignerTool-c/; mvn clean install
print 7
