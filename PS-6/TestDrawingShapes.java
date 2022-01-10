import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestDrawingShapes {
    /**
     * tests drawing shapes
     * @param directoryPath for writing images
     * @return the number of points earned for drawing shapes
     */
    public static double testDrawingShapes(String directoryPath) {
        double points = 0.0;
        try {
            EditorSolution e1 = new EditorSolution();
            System.out.println("TESTING DRAWING SHAPES AND PARTIAL SHAPES");
            e1.setShapeType("rectangle");
            e1.setColor(Color.RED);
            e1.handlePress(new Point(400, 400));
            e1.handleDrag(new Point(500, 500));
            e1.handleRelease();

            e1.setColor(Color.YELLOW);
            e1.handlePress(new Point(450, 400));
            e1.handleDrag(new Point(550, 500));
            e1.handleRelease();

            e1.setColor(Color.GREEN);
            e1.handlePress(new Point(400, 500));
            e1.handleDrag(new Point(500, 550));
            e1.handleRelease();

            e1.setColor(Color.BLUE);
            e1.handlePress(new Point(450, 450));
            e1.handleDrag(new Point(550, 550));
            e1.handleRelease();
            Thread.sleep(500);

            // set up for comparing images
            BufferedImage solutionImage = DrawingGUI.loadImage(directoryPath + "/testdrawingshapessolution.png");
            BufferedImage studentImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = studentImage.createGraphics();
            e1.paint(graphics2D);
            ImageIO.write(studentImage,"png", new File(directoryPath + "/testdrawingshapes.png"));
            points = (int)(TestPS6.similarity(solutionImage, studentImage, 400, 400, 550, 550) * 2) / 20.0;
            System.out.println(points +"/10\n");

            // compare client images
            System.out.println("TESTING CONSISTENCY OF SKETCHES");
            EditorSolution e2 = new EditorSolution();
            BufferedImage studentImage2 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
            graphics2D = studentImage2.createGraphics();
            e2.paint(graphics2D);
            Thread.sleep(500);
            ImageIO.write(studentImage2,"png", new File(directoryPath + "/testdrawingshapes2.png"));
            double points2 = (int)(TestPS6.similarity(studentImage, studentImage2, 400, 400, 550, 550)) / 20.0;
            System.out.println(points2 +"/5\n");
            points += points2;
        }
        catch (Exception e) {
            System.err.println(e);
        }
        return points;
    }

    public static void main(String[]args) {
        testDrawingShapes("Autograders/PS-6");
    }
}
