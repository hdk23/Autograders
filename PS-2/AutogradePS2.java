import java.io.PrintStream;
import java.util.ArrayList;

/**
 * @author Henry Kim, Fall 2021, CS 10 Problem Set 2 PointQuadTree Autograder
 * Driver Class
 */

public class AutogradePS2 {
    // test values (one set at center of each quadrant, another set just slightly off
    static int[][] quadrantCenters = {{600, 150}, {200, 150}, {200, 450}, {600, 450}};
    static int diff = 10;
    static Dot origin = new Dot(400, 300);
    static int testX1 = 0;
    static int testY1 = 0;
    static int testX2 = 800;
    static int testY2 = 600;
    static PointQuadtree<Dot> tree = new PointQuadtree<Dot>(origin, testX1, testY1, testX2, testY2); // start with A

    /**
     * add the points in a quadrant center's quadrants
     * @param dots ArrayList to add dots to
     * @param center the coordinates of the quadrant center
     */
    public static void addPoints(ArrayList<Dot> dots, int[] center) {
        dots.add(new Dot(center[0] + AutogradePS2.diff, center[1] - AutogradePS2.diff));  // quadrant center's q1
        dots.add(new Dot(center[0] - AutogradePS2.diff, center[1] - AutogradePS2.diff));  // quadrant center's q2
        dots.add(new Dot(center[0] - AutogradePS2.diff, center[1] + AutogradePS2.diff));  // quadrant center's q3
        dots.add(new Dot(center[0] + AutogradePS2.diff, center[1] + AutogradePS2.diff));  // quadrant center's q4
    }

    /**
     * add the points in a quadrant center's quadrants
     * @param tree to add dots to
     * @param center the coordinates of the quadrant center
     */
    public static void addPoints(PointQuadtree<Dot> tree, int[] center) {
        tree.insert(new Dot(center[0] + AutogradePS2.diff, center[1] - AutogradePS2.diff));  // quadrant center's q1
        tree.insert(new Dot(center[0] - AutogradePS2.diff, center[1] - AutogradePS2.diff));  // quadrant center's q2
        tree.insert(new Dot(center[0] - AutogradePS2.diff, center[1] + AutogradePS2.diff));  // quadrant center's q3
        tree.insert(new Dot(center[0] + AutogradePS2.diff, center[1] + AutogradePS2.diff));  // quadrant center's q4
    }


    /**
     * method that runs all test methods
     * @param term you're grading the assignment (e.g. 21F, 22W)
     * @param folderName from Canvas (e.g. kimhenry for Henry Kim)
     * @param name student's name
     * @return the number of points earned by the autograded sections (out of 45)
     */
    public static double gradePS2(String term, String folderName, String name, String solutionImagePath, String studentImagePath) {
        long start = System.currentTimeMillis();
        try {
            PrintStream fileOut = new PrintStream("Grading/"+term+"/PS/PS-2/"+ folderName +"/PS-2 Scoring Sheet " + name + ".txt");
            System.setOut(fileOut);
        }
        catch (Exception e) {

        }
        System.out.println("Problem Set 2 Autograder");
        System.out.print("NAME: ");
        System.out.println(name);
        double score = 0;
        score += TestSize.testSize();
        score += TestAllPoints.testAllPoints();
        score += TestInsert.testInsert();
        score += TestFindInCircle.testFindInCircle();
        score += TestHandleMousePress.testHandleMousePress(solutionImagePath, studentImagePath);

        System.out.println("TOTAL: " + score + "/45");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + " ms to grade these parts");
        System.out.println("TIME TO EXAMINE THE REST");
        return score;
    }

    public static void main(String[]args) {
        gradePS2("22W","test", "Nate Kim", "Autograders/PS-2/drawTreeSolution.png", "Autograders/PS-2/studentCopy.png");
    }
}
