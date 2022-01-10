# CS 10 Problem Set 4 Autograder
## Henry Kim, CS 10 TA, Fall 2021

---

## Included In This Directory
* `AutogradePS4.java` - Java code that autogrades an assignment by running unit tests and integration tests
* `BaconGraph.java` - Solution for GraphLib, not included in Git repository for security reasons
* `TestAverageSeparation.java` - Unit testing for the `averageSeparation` method
* `TestBFS.java` - Unit testing for the `bfs` method
* `TestGetPath.java` - Unit testing for the `getPath` method
* `TestGraphs.java` - Hard-coded test graphs
* `TestMissingVertices.java` - Unit testing for the `missingVertices` method

## Methods Added for Autograding
None

## Test Cases
1. Complete Graph (all vertices directly connected to one another)
2. Isolated Graph (no vertices connected to each other)
3. Clique Graph (two disjoint cycles interconnected with one another)
4. Central graph (one node exclusively connected directly to all others)
5. Tree Graph (A-B-C-D-E-F graph with F and A not directly connected)

## Input/Output Redirection
Test cases from tests.txt or tests2.txt used to automate testing
Output redirected from stdout to file for records

## Parts Requiring Manual Inspection
**10**	Read data files and create costar graph
**5**	Search for best center of universe by path length
**15**	Interactive interface (via input redirection)

## Test Methods in `AutogradePS4.java` or Related Classes
### testBFS (15 points)
**3 points** for each test case

### testGetPath (10 points)
**2 points** for each test case

### testMissingVertices (5 points)
**1 point** for each test case

### testAverageSeparation (10 points)
**2 points** for each test case