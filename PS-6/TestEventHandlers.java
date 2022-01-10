import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestEventHandlers {
    /**
     * tests the event handler methods in the Editor class
     * @param directoryPath for writing images
     * @return the number of points earned for the event handler methods
     */
    public static double testEventHandlers(String directoryPath) {
        double points = 0;
        try {
            Editor e1 = new Editor();
            System.out.println("TESTING EVENT HANDLERS");
            // draw mode: test rectangle
            e1.setShapeType("rectangle");
            e1.setColor(Color.BLUE);
            e1.handlePress(new Point(100, 100));
            points += AutogradeCommon.displayMessage(e1.getDrawFrom().equals(new Point(100, 100)) && e1.getCurr() instanceof Rectangle, 0.5, "not creating a rectangle when handling press");
            e1.handleDrag(new Point(200, 200));
            points += AutogradeCommon.displayMessage(e1.getCurr().contains(200, 200), 0.5, "not handling drag for a rectangle correctly");
            e1.handleRelease();
            points += AutogradeCommon.displayMessage(e1.getCurr() == null, 0.5, "not setting current shape to null for a rectangle");

            // test ellipse
            e1.setShapeType("ellipse");
            e1.setColor(Color.GREEN);
            e1.handlePress(new Point(300, 300));
            points += AutogradeCommon.displayMessage(e1.getDrawFrom().equals(new Point(300, 300)) && e1.getCurr() instanceof Ellipse, 0.5, "not creating an ellipse when handling press");
            e1.handleDrag(new Point(400, 400));
            points += AutogradeCommon.displayMessage(e1.getCurr().contains(385, 385) && !e1.getCurr().contains(386, 386), 0.5, "not handling drag for an ellipse correctly");
            e1.handleRelease();
            points += AutogradeCommon.displayMessage(e1.getCurr() == null, 0.5, "not setting current shape to null for ellipse");

            // test segment
            e1.setShapeType("segment");
            e1.setColor(Color.RED);
            e1.handlePress(new Point(500, 200));
            points += AutogradeCommon.displayMessage(e1.getDrawFrom().equals(new Point(500, 200)) && e1.getCurr() instanceof Segment, 0.5, "not creating a segment when handling press");
            e1.handleDrag(new Point(600, 300));
            points += AutogradeCommon.displayMessage(e1.getCurr().contains(600, 300), 0.5, "not handling drag for a segment correctly");
            e1.handleRelease();
            points += AutogradeCommon.displayMessage(e1.getCurr() == null, 0.5, "not setting current shape to null for segment");

            // test freehand/polyline
            e1.setShapeType("freehand");
            e1.setColor(Color.MAGENTA);
            e1.handlePress(new Point(400, 500));
            boolean polyDragCorrect = true;
            points += AutogradeCommon.displayMessage(e1.getDrawFrom().equals(new Point(400, 500)) && e1.getCurr() instanceof Polyline, 0.5, "not creating a polyline when handling press");

            // generates a v-shaped polyline
            for (int x = 400; x < 450; x++) {
                e1.handleDrag(new Point(x, x + 100));
                polyDragCorrect = polyDragCorrect && e1.getCurr().contains(x, x+100);
            }

            for (int x = 450; x < 500; x++) {
                e1.handleDrag(new Point(x, 550 + 550 - x - 100));
                polyDragCorrect = polyDragCorrect && e1.getCurr().contains(x, 550 + 550 - x - 100);
            }
            points += AutogradeCommon.displayMessage(polyDragCorrect, 0.5, "not handling drag for a polyline correctly");
            e1.handleRelease();
            points += AutogradeCommon.displayMessage(e1.getCurr() == null, 0.5, "not setting current shape to null for polyline");

            // testing no shape mode
            e1.setShapeType("ellipse");
            e1.setMode(Editor.Mode.MOVE);
            e1.handlePress(new Point(700, 700));
            points += AutogradeCommon.displayMessage(e1.getMovingId() <= 0 && e1.getMoveFrom() == null, 0.5, "trying to move a shape when the cursor isn't on a shape");
            e1.handleRelease();

            // set up for comparing images
            BufferedImage studentImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = studentImage.createGraphics();
            e1.paint(graphics2D);
            ImageIO.write(studentImage,"png", new File(directoryPath + "/studentoriginal.png"));

            // compare images before and after moving off of a shape
            e1.setMode(Editor.Mode.MOVE);
            e1.handlePress(new Point(600, 600));  // click in the middle of nowhere
            e1.handleDrag(new Point(650, 650));
            e1.handleRelease();
            BufferedImage studentImage2 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
            graphics2D = studentImage2.createGraphics();
            e1.paint(graphics2D);
            ImageIO.write(studentImage2,"png", new File(directoryPath + "/postmove.png"));
            points += AutogradeCommon.displayMessage(TestPS6.compareImages(studentImage, studentImage2), 0.5, "moving a shape when not clicked on");

            // compare images before and after recoloring off of a shape
            e1.setMode(Editor.Mode.RECOLOR);
            e1.handlePress(new Point(700, 700));
            e1.handleRelease();
            Thread.sleep(500);

            studentImage2 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
            graphics2D = studentImage2.createGraphics();
            e1.paint(graphics2D);
            ImageIO.write(studentImage2,"png", new File(directoryPath + "/postrecolor.png"));
            points += AutogradeCommon.displayMessage(TestPS6.compareImages(studentImage, studentImage2), 0.5, "recoloring a shape when not clicked on");

            // compare images before and after deleting off of a shape
            e1.setMode(Editor.Mode.DELETE);
            e1.handlePress(new Point(700, 700));
            e1.handleRelease();
            Thread.sleep(500);

            studentImage2 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
            graphics2D = studentImage2.createGraphics();
            e1.paint(graphics2D);
            ImageIO.write(studentImage2,"png", new File(directoryPath + "/postdelete.png"));
            points += AutogradeCommon.displayMessage(TestPS6.compareImages(studentImage, studentImage2), 0.5, "deleting a shape when not clicked on");

            // compare images before and after recoloring a shape
            e1.setMode(Editor.Mode.RECOLOR);
            e1.setColor(Color.CYAN);
            e1.handlePress(new Point(150, 150));  // recolor the rectangle
            e1.handleRelease();
            Thread.sleep(500);

            studentImage2 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
            graphics2D = studentImage2.createGraphics();
            e1.paint(graphics2D);
            ImageIO.write(studentImage2,"png", new File(directoryPath + "/postrecolor2.png"));
            BufferedImage solutionImage = DrawingGUI.loadImage(directoryPath + "/postrecolor2solution.png");
            points += AutogradeCommon.displayMessage(TestPS6.comparePartialImages(studentImage2, solutionImage, 100, 100, 200, 200), 0.5, "not recoloring a shape when clicked on");

            // move the rectangle
            e1.setMode(Editor.Mode.MOVE);
            e1.handlePress(new Point(150, 150));  // move the rectangle
            e1.handleDrag(new Point(650, 650));
            points += AutogradeCommon.displayMessage(e1.getMoveFrom().equals(new Point(650, 650)), 0.5, "not setting moveFrom when handling drag for move mode");
            e1.handleRelease();
            Thread.sleep(500);

            studentImage2 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
            graphics2D = studentImage2.createGraphics();
            e1.paint(graphics2D);
            ImageIO.write(studentImage2,"png", new File(directoryPath + "/postmove2.png"));
            solutionImage = DrawingGUI.loadImage(directoryPath + "/postmove2solution.png");
            points += AutogradeCommon.displayMessage(TestPS6.comparePartialImages(studentImage2, solutionImage, 100, 100, 200, 200) &&
                    TestPS6.comparePartialImages(studentImage2, solutionImage, 600, 600, 700, 700), 0.5, "not moving a shape when clicked on");

            // compare images before and after deleting a shape
            e1.setMode(Editor.Mode.DELETE);
            e1.handlePress(new Point(650, 650));  // recolor the rectangle
            e1.handleRelease();
            Thread.sleep(500);

            studentImage2 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
            graphics2D = studentImage2.createGraphics();
            e1.paint(graphics2D);
            ImageIO.write(studentImage2,"png", new File(directoryPath + "/postdelete2.png"));
            solutionImage = DrawingGUI.loadImage(directoryPath + "/postdelete2solution.png");
            points += AutogradeCommon.displayMessage(TestPS6.comparePartialImages(studentImage2, solutionImage, 100, 100, 200, 200) &&
                    TestPS6.comparePartialImages(studentImage2, solutionImage, 600, 600, 700, 700), 0.5, "not deleting a shape when clicked on");
        }
        catch (Exception e) {
            System.err.println(e);
        }

        System.out.println(points +"/10\n");
        return points;
    }

    public static void main (String[]args) {
        testEventHandlers("Autograders/PS-6");
    }
}
