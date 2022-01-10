#!/bin/bash
#
# createdir.sh - create subdirectories for each assignment
#
# execution:
# $ ./createdir.sh
#
# Author: Henry Kim
# Date: Aug 14, 2021
# Course: CS 10, Fall 2021
#

# TODO: REPLACE TERM WITH CURRENT TERM
term=22W
subdir="../Grading/$term"

# Create a Grading directory
if ! [ -d "../Grading" ]; then
  mkdir "../Grading"
fi

# Create a directory for the term
# Assumes that a TA will likely serve for multiple terms
mkdir "$subdir"
mkdir "$subdir/SA"
mkdir "$subdir/PS"

# Create a directory for each short assignment
for i in {0..9}
do
  mkdir "$subdir/SA/SA-${i}"
done

# Create a directory for each problem set
for i in {1..6}
do
  mkdir "$subdir/PS/PS-${i}"
done
