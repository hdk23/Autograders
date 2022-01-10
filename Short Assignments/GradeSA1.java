/**
 * autogrades short assignment 1 in approximately 1-2 seconds
 * should add the following code to BlobGUI.java before running the autograder
    public char getBlobType() {
        return blobType;
    }
 * @author Henry Kim, Dartmouth CS 10 TA, Winter 2022
 */
public class GradeSA1 {
    public static void main(String[]args) {
        double points = 0;
        long start = System.currentTimeMillis();
        // 1. Define a new subclass of Blob named PurposefulWanderer.
        // Define a constructor that takes initial x and y values and invokes the super constructor with them.
        // The constructor should also set the number of steps between velocity changes, to be a random number between 4 and 10.

        int x = (int) (Math.random() * 400);
        int y = (int) (Math.random() * 400);
        Blob pw = new PurposefulWanderer(x, y);
        points += AutogradeCommon.displayMessage(pw.getX() == x && pw.getY() == y, 0.25,
                "not defining PurposefulWanderer as a subclass of Blob nor calling the super constructor");

        // 2. Give it instance variables maxSteps that indicates the number of steps between velocity changes and
        // steps that indicates how many of those it has currently gone since the last change respectively.
        // Write a getter method for each variable
        PurposefulWanderer pw2 = new PurposefulWanderer(x, y);
        int minFound = 10, maxFound = 4;
        for (int i = 0; i < 100; i++) {
            pw2 = new PurposefulWanderer(x, y);
            int maxSteps = pw2.getMaxSteps();
            if (maxSteps < minFound) {
                minFound = maxSteps;
            }
            else if (maxSteps > maxFound) {
                maxFound= maxSteps;
            }
        }
        int min = 4, max = 9;

        points += AutogradeCommon.displayMessage(minFound == min, 0.25, "not defining the minimum as " + min);
        points += AutogradeCommon.displayMessage(maxFound == max, 0.25, "not defining the maximum as " + max);
        points += AutogradeCommon.displayMessage(pw2.getSteps() >= 0 && pw2.getSteps() <= pw2.getMaxSteps(), 0.25,
                "not defining the getSteps method");

        // Override the step method so that random new values are assigned to dx and dy each time the required number of steps has been taken.
        boolean xCorrect = true, yCorrect = true, dxCorrect = true, dyCorrect = true, stepsCorrect = true;

        for (int i = 0; i < pw2.getMaxSteps() * 10; i++) {
            double prevx = pw2.x, prevy = pw2.y, prevdx = pw2.dx, prevdy = pw2.dy;
            int prevSteps = pw2.getSteps();
            pw2.step();
            if (prevSteps == pw2.getMaxSteps()) {
                if (pw2.getSteps() != 0 && pw2.getSteps() != 1) {
                    System.out.println("You should reset steps to 0 after you reached the maximum number of steps");
                    stepsCorrect = false;
                }
                if (pw2.dx == prevdx || pw2.dx < -1 || pw2.dx > 1) {
                    dxCorrect = false;
                    if (pw2.dx == prevdx) {
                        System.out.println("dx should be set to a different value each time");
                    }
                    if (pw2.dx < -1) {
                        System.out.println("dx is too small");
                    }
                    if (pw2.dx > 1) {
                        System.out.println("dx is too big");
                    }
                }

                if (pw2.dy == prevdy || pw2.dy < -1 || pw2.dy > 1) {
                    dyCorrect = false;
                    if (pw2.dy == prevdy) {
                        System.out.println("dy should be set to a different value each time");
                    }
                    if (pw2.dy < -1) {
                        System.out.println("dy is too small");
                    }
                    if (pw2.dy > 1) {
                        System.out.println("dy is too big");
                    }
                }
            }
            else {
                if (pw2.x != prevx + prevdx) {
                    xCorrect = false;
                }
                if (pw2.y != prevy + prevdy) {
                    yCorrect = false;
                }
                if (pw2.getSteps() != prevSteps + 1) {
                    stepsCorrect = false;
                }
            }
        }
        points += AutogradeCommon.displayMessage(xCorrect && yCorrect, 0.25, "not updating x or y by dx or dy after each step correctly");
        points += AutogradeCommon.displayMessage(dxCorrect && dyCorrect, 0.25, "not updating dx or dy when maxSteps reached correctly");
        points += AutogradeCommon.displayMessage(stepsCorrect, 0.25, "not incrementing steps after each step");

        // Modify the BlobGUI to allow creation of an instance of your class.
        BlobGUI gui = new BlobGUI();
        gui.handleKeyPress('n');
        points += AutogradeCommon.displayMessage(gui.getBlobType() == 'n', 0.25,
                "not modifying the BlobGUI to allow creation of an instance of your class");
        System.out.println(points +"/2");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + " ms to grade SA-1");
        System.exit(0);
    }
}
