import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestHandleMousePress {
    protected static BufferedImage studentImage;
    protected static BufferedImage solutionImage;
    protected static int totalPixels = 0;
    protected static double diffPixels = 0;
    protected static boolean nullThrown = false;
    protected static boolean indexOBThrown = false;

    /**
     * checks exceptions in rows
     * @param lower bound for checking
     * @param upper bound for checking
     * @return points deducted from exceptions
     */
    public static double checkRowException(int lower, int upper) {
        try {
            for (int row = lower; row <= upper; row++) {
                for (int col = 0; col < AutogradePS2.testX2; col++) {
                    if (!(studentImage.getRGB(col, row) == -1118482 && solutionImage.getRGB(col, row) == -1118482)){
                        totalPixels++;
                        if (studentImage.getRGB(col, row) != solutionImage.getRGB(col, row)) {
                            diffPixels++;
                        }
                    }
                }
            }
        }
        catch (NullPointerException e) {
            if (!nullThrown) {
                nullThrown = true;
                AutogradeCommon.displayMessage( false, 1, "" + e);
                return -1;
            }
        }
        catch (IndexOutOfBoundsException e) {
            if (!indexOBThrown) {
                indexOBThrown = true;
                AutogradeCommon.displayMessage( false, 0.5, "" + e);
                return -0.5;
            }
        }
        catch (Exception e) {
            System.err.println("EXCEPTION " + e);
        }
        return 0;
    }

    /**
     * checks exceptions in cols
     * @param lower bound for checking
     * @param upper bound for checking
     * @return points deducted from exceptions
     */
    public static double checkColException(int lower, int upper) {
        try {
            for (int col = lower; col <= upper; col++) {
                for (int row = 0; row < AutogradePS2.testY2; row++) {
                    if (!(studentImage.getRGB(col, row) == -1118482 && solutionImage.getRGB(col, row) == -1118482)){
                        totalPixels++;
                        if (studentImage.getRGB(col, row) != solutionImage.getRGB(col, row)) {
                            diffPixels++;
                        }
                    }
                }
            }
        }
        catch (NullPointerException e) {
            if (!nullThrown) {
                nullThrown = true;
                AutogradeCommon.displayMessage( false, 1, "" + e);
                return -1;
            }
        }
        catch (IndexOutOfBoundsException e) {
            if (!indexOBThrown) {
                indexOBThrown = true;
                AutogradeCommon.displayMessage( false, 0.5, "" + e);
                return -0.5;
            }
        }
        catch (Exception e) {
            System.err.println("EXCEPTION " + e);
        }
        return 0;
    }



    /**
     * tests the student's DotTreeGUI class's handleMousePress method
     * 5 points total
     * +1 for checking null
     * +1 for being able to add at the first dot
     * +1 for being able to add multiple dots (tested up to depth 2 in each quadrant of each child)
     * +1 for being able to find a single dot in isolation
     * +0.5 for being able to find a cluster of dots
     * +0.5 for being able to report no dots found when clicked in the middle of nowhere
     *
     * also tests the student's DotTreeGUI class's drawTree method
     * 10 points for draw tree -> only evaluate the grid of lines and dots, 10000-diffPixels/1000 = score
     * @param solutionImagePath path to the solution image
     * @param studentImagePath path for the student image
     * @return the number of points earned from this section
     */
    public static double testHandleMousePress(String solutionImagePath, String studentImagePath) {
        System.out.println("TESTING HANDLE MOUSE PRESS");
        double points = 0.0;
        DotTreeGUI gui = new DotTreeGUI();
        
        try {
            // test whether the q mode handles null
            try {
                gui.handleKeyPress('q');
                gui.handleMousePress(400, 300);
                points += 0.5;
            }
            catch (NullPointerException e) {
                points += AutogradeCommon.displayMessage( false, 0.5, "Q mode null check: " + e);
            }

            try {
                gui.handleKeyPress('a');
                gui.handleMousePress(400, 300);
                points += 0.5;
            }
            catch (NullPointerException e) {
                points += AutogradeCommon.displayMessage( false, 0.5, "A mode null check: " + e);
            }
            gui.handleKeyPress('a');

            PointQuadtree<Dot> tree = gui.getTree();
            int size = tree.allPoints().size();

            String message = " ";
            if (size == 0) {
                message += "(first dot not added)";
            }
            else if (size == 2) {
                message += "(first dot added twice) -> insert only if tree is not null";
            }
            points += AutogradeCommon.displayMessage(size == 1, 1, "not adding the first dot correctly"+ message);


            // Quadrants 1-4
            for (int[] center: AutogradePS2.quadrantCenters) {
                gui.handleMousePress(center[0], center[1]);
            }

            // quadrant centers' children
            for (int[] center: AutogradePS2.quadrantCenters) {
                gui.handleMousePress(center[0] + AutogradePS2.diff, center[1] - AutogradePS2.diff);  // quadrant center's q1
                gui.handleMousePress(center[0] - AutogradePS2.diff, center[1] - AutogradePS2.diff);  // quadrant center's q2
                gui.handleMousePress(center[0] - AutogradePS2.diff, center[1] + AutogradePS2.diff);  // quadrant center's q3
                gui.handleMousePress(center[0] + AutogradePS2.diff, center[1] + AutogradePS2.diff);  // quadrant center's q4
            }

            points += AutogradeCommon.displayMessage(tree.allPoints().size() == size + 20, 1, "not being able to add multiple points correctly");

            gui.handleKeyPress('q');
            gui.handleMousePress(600, 150);
            points += AutogradeCommon.displayMessage(gui.getFound().size() == 1, 1, "not being able to find a single dot in isolation correctly");

            gui.handleKeyPress('+');
            gui.handleMousePress(210, 440);
            points += AutogradeCommon.displayMessage(gui.getFound().size() == 4, 0.5, "not being able to find a cluster of close dots correctly");

            gui.handleKeyPress('+');
            gui.handleMousePress(200, 300);
            points += AutogradeCommon.displayMessage(gui.getFound().size() == 0, 0.5, "reporting a click in the middle of nowhere as having something found");

        }
        catch (NullPointerException e) {
            points += AutogradeCommon.displayMessage( false, 1, "" + e);
        }
        catch (IndexOutOfBoundsException e) {
            points += AutogradeCommon.displayMessage( false, 0.5, ""+ e);
        }
        catch (Exception e) {
            System.err.println("EXCEPTION " + e);
        }

        System.out.println(points +"/5\n");

        System.out.println("TESTING DRAW TREE");
        try {
            gui.handleKeyPress('c');
            solutionImage = DrawingGUI.loadImage(solutionImagePath);
            studentImage = new BufferedImage(AutogradePS2.testX2, AutogradePS2.testY2, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = studentImage.createGraphics();
            gui.paint(graphics2D);
            ImageIO.write(studentImage,"png", new File(studentImagePath));
        }
        catch (Exception e) {
            System.err.println("EXCEPTION " + e);
        }

        // check rows——only check the grid for efficiency
        checkRowException(135, 165);
        checkRowException(295, 305);
        checkRowException(435, 465);

        // check the cols——only check the grid for efficiency
        checkColException(185, 215);
        checkColException(395, 405);
        checkColException(585, 615);

        System.out.println(diffPixels + " pixels do not match out of " + totalPixels);
        double imagePoints = Math.round(Math.sqrt((totalPixels-diffPixels)/totalPixels)*20.0)/2.0;
        System.out.println(imagePoints +"/10\n");
        points += imagePoints;

        return points;
    }

    public static void main(String[]args) {
        testHandleMousePress("Autograders/PS-2/drawTreeSolution.png", "Autograders/PS-2/studentCopy.png");
    }
}
