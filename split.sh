#!/bin/bash
#
# split.sh - split canvas submissions into directories
# place the "submissions" folder as a subdirectory of the "Grading" directory.
#
# execution:
# $ ./split.sh
#
# Author: Henry Kim
# Date: Aug 6, 2021
# Course: CS 10, Fall 2021
#

function sortDirectories()
{
  # place in PS or SA directory based on second parameter
  cd ..
  firstchar=$(echo "$2" | cut -c1-1)
  if [ $firstchar == 'P' ]; then
    dir="PS"
  else
    dir="SA"
  fi

  for f in "$3"/*
  do
    # create a folder with just the student's lastnamefirstname
      filename=$(basename "$f")
      extension="${f##*.}"
      name=$(echo $filename | cut -d "_" -f 1)
      cd "Grading/$1/$dir/$2" || exit
      if ! [ -d "./$name" ]; then
        mkdir "./$name"
      fi
      cd ../../../..
      if [ $extension == 'zip' ]; then
#        # unzip folder and remove the compressed version
          unzip "$f" -d "Grading/$1/$dir/$2/$name"
          rm -f "$f"
#          # assuming this shell script runs on a Mac
#          # not sure what this looks like for Windows
          rm -rf "Grading/$1/$dir/$2/$name"/__MACOSX
      else
        mv "$f" "Grading/$1/$dir/$2/$name"
      fi
  done
  # removes the empty submissions folder once done
  rm -d "$3"
}

function keepSectionOnly()
{
  firstchar=$(echo "$2" | cut -c1-1)
  if [ "$firstchar" == 'P' ]; then
   dir="PS"
  else
   dir="SA"
  fi

  if [[ "$section" != "" ]]; then
   for d in Grading/"$1"/"$dir"/"$2"/*
   do
     name=$(echo $d | cut -d "/" -f 5)
  #    echo $name
     if [[ "$section" != *"$name"* ]]; then
  #      echo "REMOVING $d FROM SUBMISSIONS"
       rm -rf "$d"
     else
       echo "$name is in your section"
     fi
   done
  fi
}

function renameSectionFiles()
{
   firstchar=$(echo "$2" | cut -c1-1)
   if [ "$firstchar" == 'P' ]; then
     dir="PS"
   else
     dir="SA"
   fi

  for d in "Grading"/"$1"/"$dir"/"$2"/*
  do
    for f in "$d"/*
    do
      if [ -d "$f" ]; then
        for nestedFile in "$f"/*
        do
          late=$(echo "$nestedFile" | cut -d "_" -f 2)
          if [[ $late == "LATE" ]]; then
            fileName=$(echo "$fileName" | cut -d "/" -f 1,3-5,7-)
            echo 'LATE' > "$d/late.txt"
          else
            fileName=$(echo "$fileName" | cut -d "/" -f -5,7-)
          fi
          mv "$nestedFile" "$fileName"
        done
        rm -r "$f"
      else
        late=$(echo "$f" | cut -d "_" -f 2)
        if [[ $late == "LATE" ]]; then
          newName=$(echo "$f" | cut -d "_" -f 5-)
          echo 'LATE' > "$d/late.txt"
        else
          newName=$(echo "$f" | cut -d "_" -f 4-)
        fi
        mv "$f" "$d"/"$newName"
      fi
    done
  done
}

# TODO: YOU CHANGE THIS PART
term=22W
section=""
section="desirrichard fickalexander khalilkarim mbesamuthoni middelbergnicholas odigbochukwuka petrindamien rinconmarco seokbryant yangcamille"
assignment=SA-7
submissionFolderName="submissions"

# commands——DO NOT TOUCH
sortDirectories $term $assignment $submissionFolderName
keepSectionOnly $term $assignment $submissionFolderName
renameSectionFiles $term $assignment $submissionFolderName