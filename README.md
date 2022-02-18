# CS 10 Autograders
## Henry Kim, CS 10 TA, Fall 2021

---
## Purpose/Background
The on-campus housing crisis in Fall 2021 caused by COVID-19 has caused many upperclassmen that usually TA for CS courses to study abroad or be off. 
This led to a "TA shortage" with larger sections. While previous years had 7-8 students per section, this term had an average of 9-10. 
To handle more students, I sought to grade more efficiently. 
With that, I came up with these autograders that will hopefully save us all some time in the long run.

Some scaffolds required additional getters/setters for the autograders to work.
I have indicated such methods in each subdirectory's README. 

For autograding purposes, any methods previously made private should be made public.
This is to call methods of a class from an outside class.

Each autograder consists of unit testing/integration testing functions. 
Points are allocated based on which tests pass.
Autograders for Problem Sets 2 and 4 have been used for my section in Fall 2021 as a beta test run.

## General Testing Procedure
1. Run the appropriate preprocessing steps
    1. Start timing
    2. Set up the file that will record the student's results
    3. Print the student's name
    4. Initialize objects and run any applicable methods
2. Run each unit test method and tally points
    1. Print the section/method being tested
    2. Initialize the number of points to 0 (or the applicable value)
    3. Generate the necessary objects and run the appropriate methods
    4. Run the `AutogradeCommon` class's `displayMessage` method by passing the following:
        1. the condition you expect to evaluate as true (`boolean condition`)
        2. the number of points allocated to that test case (`double points`)
        3. the error message to print if the code fails a test case
    5. `displayMessage` returns:
        1. the `points` value if the code passes the test
        2. 0 if the code fails the test
3. See the results
    1. Print the total points earned from the autograded sections
    2. Report the time it took to perform all tests (in milliseconds)
    3. Exit

## Using Autograders to Test Student Code in Office Hours
Some students have found the autograders particularly helpful for identifying bugs. 

For example, the `testInsert` method in Problem Set 2 checks whether each `x1`, `y1`, `x2`, and `y2` values are set correctly.
Students in previous terms struggled to debug the nuanced differences in ordering the parameters. 
The autograder can detect such bugs instantaneously.

You can also use the autograders to check whether the student's programs behave as intended.

## For Manual Inspection
To standardize grading for the manually inspected parts, a more specific rubric is provided for each criterion.
### Evaluating Efficiency
* The time it takes to perform all tests could be a rough indicator of the code's efficiency.
* Up to X points can be deducted for inefficient code

### Evaluating Structure
* **(4 points)** Good decomposition of and within methods
* **(3 points)** Proper use of instance and local variables
    * -1 point per occurrence of using an instance variable where a local variable is appropriate and vice versa
* **(3 points)** Proper use of parameters
    * -1 point per occurrence of improper parameter use (extraneous, missing)

### Evaluating Style
* **(3 points)** Comments within methods and for new methods
    * -0.5 point per method missing substantial comments within the method (up to 4)
    * -0.5 point if missing @author comments at the top
    * -0.5 point if missing Javadoc comments overall
* **(4 points)** Good names for variables, methods, parameters
    * -1 point per vague or unnecessarily long variable name
* **(3 points)** Layout (blank lines, indentation, no line wraps)
    * 1 point for using blank lines to separate segments of code
        * -0.5 point if there could be more blank lines
        * -1 point if blank lines only used to separate functions
    * 1 point for using proper indentation throughout the code
        * -0.5 point if there's a section with very improper indentation
        * -1 point if there are multiple sections with very improper indentation
    * 1 point for making sure lines are less than 120 characters each
        * no penalty first the first two
        * -0.5 point for third occurrence
        * -1 point if four or more

## Shell Scripts
This directory also includes two shell scripts used to automate file management from Canvas.

Before running the shell scripts, I recommend dragging these shell scripts out of this directory and placing them in the top-level `CS 10` directory.

### `createdir.sh`
This shell script initializes your CS 10 workspace so that you have a `Grading` directory for each term.

Within each term's folder, you'll find a folder for each assignment.

To run the shell script, type `./createdir.sh` in the terminal from the top-level `CS 10` directory.

### `split.sh`
This shell script takes the files you downloaded from Canvas and sorts them into a subdirectory for each student in your section.

It also unzips any zipped solutions.

Before running this script, make sure you've updated the following values in the shell script:

Drag and drop the `submissions` folder from Canvas into IntelliJ. You may need to do it twice if it doesn't work the first time.

```bash
section="goldmanethan guptapranit huangmendersalexander jainshreya janumalaangeline jensentigerlily mehdiadam meisejoshua pantnitesh wescottvarun"
term=21F
assignment=SA-0
submissionFolderName="submissions"
```

To run the shell script, type `./split.sh` in the terminal from the top-level `CS 10` directory.
