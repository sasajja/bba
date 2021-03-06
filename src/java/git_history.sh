#!/bin/sh

set -e

# mkdir repo

# cp -r 1/trunk/* repo/
cd repo
# git init
# git remote add origin git@github.com:paul-hammant/little_java_branch_by_abstraction_demo.git
git reset --hard 06889352960adc5fd73bb16c9fc5a324223f0632
git tag -d 1
git tag -d 2
git tag -d 3
git tag -d 4
git tag -d 5
# git add .
# git commit -m "As Jooby's 'quickstart project generator' made it"
git tag 1 -m "As Jooby's 'quickstart project generator' made it"
cd ..
cp -r 2/trunk/* repo/
cd repo
echo "target\nlog/\n*.iml\n" > .gitignore
git add .
git commit -m "Hair color added that's stringified (regretably in hindsight)"
git tag 2 -m "Hair color added that's stringified (regretably in hindsight)"
cd ..
cp -r 3/trunk/* repo/
cd repo
git add .
git commit -m "Extract hard-coded string version of hair color to a factory - introducing the abstraction (and go live)"
git tag 3 -m "Extract hard-coded string version of hair color to a factory - introducing the abstraction (and go live)"
cd ..
cp -r 4/trunk/* repo/
cd repo
git add .
git commit -m "New second enum-based impl of 'hair color' (with tests) without deleting the old impl (and go live)"
git tag 4 -m "New second enum-based impl of 'hair color' (with tests) without deleting the old impl (and go live)"
cd ..
cp -r 8/trunk/* repo/
cd repo
git add .
git rm conf/application_release4.conf
git rm src/main/java/com/mycompany/Release3.java
git commit -m "Remove the hair color abstraction (some time after the release as we're passed roll back now, and go live)"
git tag 5 -m "Remove the hair color abstraction (some time after the release as we're passed roll back now, and go live)"
git gc
git push -f
git push --tags -f
cd ..

