# CS 10 Problem Set 2 Autograder
## Henry Kim, CS 10 TA, Fall 2021

---

## Included In This Directory
* `AutogradePS2.java` - Java code that autogrades an assignment by running unit tests and integration tests
* `drawTreeSolution.jpg` - A copy of what the GUI should produce by running `DotTreeGUI` using the test cases
* `TestAllPoints.java` - Unit testing for the `allPoints` method
* `TestFindInCircle.java` - Unit testing for the `findInCircle` method
* `TestHandleMousePress.java` - Unit testing for the `handleMousePress` method and the `DotTreeGUI`
* `TestInsert.java` - Unit testing for the `insert` method
* `TestSize.java` - Unit testing for the `size` method

## Methods Added for Autograding
### In `DotTreeGUI.java`
```java
    /**
     * added for autograding
     * @return tree
     */
    public PointQuadtree<Dot> getTree() {
        return tree;
    }

    /**
     * added for autograding
     * @return found
     */
    public List<Dot> getFound() {
        return found;
    }

```

## Parts Requiring Manual Inspection
Sections related to CollisionGUI (25 points)

5	CollisionGUI find colliders: build the tree

10	CollisionGUI find colliders: find all of them

10	CollisionGUI draw to show colliders

## Test Methods in `AutogradePS2.java` or Related Classes
### testSize (5 points)
1. Initialize a tree with the test coordinates using `Point`s in `values`
2. **(1 point)** Checking whether the children are null 
3. **(1 point)** Initializing the size to 1 (to count the root, incorrect if initialized to 0)
4. Increment the `expectedSize` variable if size set to 0 to prevent double jeopardy
5. **(2 points)** Adding each child's size (non-recursive, 0.5 point per quadrant)
6. **(1 point)** Handling recursive cases using `Point`s in `values2`

### testAllPoints (5 points)
1. **(1 point)** Working at depth = 0 
2. **(2 points)** Working at depth = 1 
3. **(2 points)** Working at depth = 2

### testInsert (10 points)
1. **(4 points, 1 point each)** Setting each child's boundaries and inserting the initial children correctly 
2. **(4 points, 1 point each)** Inserting into each child's quadrants correctly 
3. **(2 points, 0.5 point each)** Handling edge cases between quadrants

### testFindInCircle (10 points)
1. **(5 points, 1 point each)** Passing test cases provided by the scaffold
2. **(5 points, 1 point each)** Passing test cases from my problem set

### testHandleMousePress (5 points)
1. **(1 point)** Checking null 
2. **(1 point)** Adding the first dot
3. **(1 point)** Adding multiple dots (tested up to depth 2 in each quadrant of each child) 
4. **(1 point)** Finding a single dot in isolation 
5. **(0.5 point)** Finding a cluster of dots 
6. **(0.5 point)**  Reporting no dots found when clicked in the middle of nowhere

### testDraw (10 points, included in testHandleMousePress)
score = Only evaluate the non-empty pixels in the grid of lines and dots

Formula: `Math.round(Math.sqrt((totalPixels-diffPixels)/totalPixels)*20.0)/2.0;']`