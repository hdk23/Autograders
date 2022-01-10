import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * autogrades short assignment 8 in approximately 2-3 seconds
 * should add the corresponding getters and setters
 * @author Henry Kim, Dartmouth CS 10 TA, Winter 2022
 */

public class GradeSA8 {
    
    public static double testNullCheck(EditorOne e1) {
        double points = 0.0;
        Point p = new Point(300, 300);
        try {
            e1.setMode(EditorOne.Mode.MOVE);
            e1.handleDrag(p);
            points += AutogradeCommon.displayMessage(true, 0.1, "not checking whether moveFrom is null in handleDrag");
        } catch (NullPointerException e) {
            System.err.println(""+e);
            points += AutogradeCommon.displayMessage(false, 0.1, "not checking whether moveFrom is null");
        }

        try {
            e1.setMode(EditorOne.Mode.RECOLOR);
            e1.handlePress(p);
            e1.setMode(EditorOne.Mode.MOVE);
            e1.handlePress(p);
            e1.setMode(EditorOne.Mode.DELETE);
            e1.handlePress(p);
            e1.handleDrag(p);
            points += AutogradeCommon.displayMessage(true, 0.4, "not checking for null shape in handlePress");
        }
        catch (Exception e) {
            System.err.println(""+e);
            points += AutogradeCommon.displayMessage(false, 0.4, "not checking for null shape in handlePress");
        }
        return points;
    }
    
    public static double testDraw(EditorOne e1) {
        double points = 0.0;
        e1.setMode(EditorOne.Mode.DRAW);
        Point corner = new Point(300, 300);
        try {
            e1.handlePress(corner);
            points += AutogradeCommon.displayMessage(e1.getEllipse() != null, 0.1, "not setting shape to a new ellipse in handlePress");
            points += AutogradeCommon.displayMessage(e1.getDrawFrom().equals(corner), 0.1, "not setting drawFrom to the selected point in handlePress");
            e1.handleDrag(new Point(400, 400));
            points += AutogradeCommon.displayMessage(e1.getEllipse().contains( 385, 385) && !e1.getEllipse().contains( 386, 386), 0.1,
                    "not dragging the shape correctly");
            e1.handleRelease();
        } catch (Exception e) {
            System.err.println(""+e);
        }

        return points;
    }

    public static double testMove(EditorOne e1) {
        double points = 0.0;
        Point center = new Point(350, 350);
        Point newPoint = new Point(450, 450);
        e1.setMode(EditorOne.Mode.MOVE);

        try {
            e1.handlePress(center);

            points += AutogradeCommon.displayMessage(e1.getMoveFrom().equals(center), 0.1, "not setting drawFrom to the selected point in handlePress");
            e1.handleDrag(newPoint);
            points += AutogradeCommon.displayMessage(e1.getMoveFrom().equals(newPoint), 0.1, "not setting moveFrom to the selected point in handleDrag");
            points += AutogradeCommon.displayMessage(e1.getEllipse().contains( 485, 485) && !e1.getEllipse().contains( 486, 486), 0.1,
                    "not moving the shape correctly in handleDrag");
            e1.handleRelease();
            points += AutogradeCommon.displayMessage(e1.getMoveFrom() == null, 0.1,
                    "not setting moveFrom back to null in handleRelease for move mode");
        } catch (Exception e) {
            System.err.println(""+e);
        }
        return points;
    }

    public static double testRecolor(EditorOne e1) {
        double points = 0.0;
        e1.setMode(EditorOne.Mode.RECOLOR);
        e1.setColor(Color.GREEN);
        try {
            e1.handlePress(new Point(486, 486));
            points += AutogradeCommon.displayMessage(e1.getEllipse().getColor() == Color.BLACK, 0.1, "recoloring a shape when not clicked on");
            e1.handlePress(new Point(485, 485));
            points += AutogradeCommon.displayMessage(e1.getEllipse().getColor() == Color.GREEN, 0.1, "not recoloring a shape when clicked on");
        } catch (Exception e) {
            System.err.println(""+e);
        }
        return points;

    }

    public static double testImage(EditorOne e1) {
        double points = 0.0;
        BufferedImage solutionImage = DrawingGUI.loadImage("Autograders/Short Assignments/sa8SolutionCopy.png");
        BufferedImage studentImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = studentImage.createGraphics();
        e1.paint(graphics2D);
        try {
            ImageIO.write(studentImage,"png", new File("pictures/sa8StudentCopy.png"));

        } catch (Exception e) {

        }
        points += AutogradeCommon.displayMessage(AutogradeCommon.compareImages(solutionImage, studentImage), 0.4,
                "not drawing the shape correctly");
        return points;
    }

    public static double testDelete(EditorOne e1) {
        double points = 0.0;
        e1.setMode(EditorOne.Mode.DELETE);
        try {
            e1.handlePress(new Point(486, 486));
            points += AutogradeCommon.displayMessage(e1.getEllipse() != null, 0.1, "deleting a shape when not clicked on");
            e1.handlePress(new Point(485, 485));
            points += AutogradeCommon.displayMessage(e1.getEllipse() == null, 0.1, "not deleting a shape when clicked on");
        } catch (Exception e) {
            System.err.println(""+e);
        }
        return points;
    }

    public static void main(String[]args) {
        long start = System.currentTimeMillis();
        EditorOne e1 = new EditorOne();
        double score = 0.0;
        score += testNullCheck(e1);
        score += testDraw(e1);
        score += testMove(e1);
        score += testRecolor(e1);
        score += testImage(e1);
        score += testDelete(e1);
        System.out.println((int)(score*10)/10.0 +"/2");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + " ms to grade SA-8");
        System.exit(0);
    }
}
