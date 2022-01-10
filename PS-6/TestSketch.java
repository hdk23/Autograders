import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestSketch {
    /**
     * tests the Sketch class
     * @return the number of points earned for the Sketch class
     */
    public static double testSketch() {
        double points = 0;
        try {
            Editor e1 = new Editor();
            System.out.println("TESTING SKETCH");
            e1.setShapeType("rectangle");
            e1.setColor(Color.RED);
            e1.handlePress(new Point(100, 100));
            e1.handleDrag(new Point(200, 200));
            e1.handleRelease();

            e1.setColor(Color.YELLOW);
            e1.handlePress(new Point(100, 100));
            e1.handleDrag(new Point(200, 200));
            e1.handleRelease();

            e1.setColor(Color.GREEN);
            e1.handlePress(new Point(100, 100));
            e1.handleDrag(new Point(200, 200));
            e1.handleRelease();

            e1.setColor(Color.BLUE);
            e1.handlePress(new Point(100, 100));
            e1.handleDrag(new Point(200, 200));
            e1.handleRelease();
            Thread.sleep(500);
            e1.handlePress(new Point(150, 150));
            Color[]colors = {Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED};
            e1.handleRelease();
            int movingId = Integer.MAX_VALUE;
            for (int i = 0; i < colors.length; i++) {
                e1.setMode(Editor.Mode.MOVE);
                e1.handlePress(new Point(150, 150));
                points += AutogradeCommon.displayMessage(e1.getMovingId() < movingId, 2.5, "failing step " + (i+1) + " of identifying the selected shape");
                movingId = e1.getMovingId();
                e1.handleRelease();

                e1.setMode(Editor.Mode.DELETE);
                e1.handlePress(new Point(150, 150));
                e1.handleRelease();
                Thread.sleep(500);
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }

        System.out.println(points +"/10\n");
        return points;
    }

    public static void main(String[]args) {
        testSketch();
    }
}
