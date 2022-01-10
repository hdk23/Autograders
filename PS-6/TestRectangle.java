import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.ServerSocket;
import java.util.Locale;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestRectangle {
    /**
     * tests the methods in the Rectangle class
     * @return the number of points earned for the Rectangle class
     */
    public static double testRectangle() {
        double points = 0.0;
        System.out.println("TESTING RECTANGLE");
        Rectangle r1 = new Rectangle(100, 100, Color.RED);
        points += AutogradeCommon.displayMessage(r1 != null, 0.5, "not having a constructor that takes x1 and y1");
        Rectangle r2 = new Rectangle(100, 100, 200, 200, Color.BLUE);
        points += AutogradeCommon.displayMessage(r2 != null, 0.5, "not having a constructor that takes x1 and y1");

        // getter and setter
        points += AutogradeCommon.displayMessage(r1.getColor() == Color.RED && r2.getColor() == Color.BLUE,
                0.5, "not getting the color in the getColor method correctly");
        r1.setColor(Color.GREEN);
        r2.setColor(Color.YELLOW);
        points += AutogradeCommon.displayMessage( r1.getColor() == Color.GREEN && r2.getColor() == Color.YELLOW,
                0.5, "not setting the color in the setColor method correctly");

        // testing setCorners and contains
        r2.setCorners(100, 150, 200, 250);
        points += AutogradeCommon.displayMessage( r2.contains(150, 150),
                0.5, "not setting corners correctly");

        r2.setCorners(200, 250, 100, 150);
        points += AutogradeCommon.displayMessage( r2.contains(150, 150),
                0.5, "not handling the case that x1/y1 are greater than x2/y2");

        // testing toString
        String s = r2.toString().toLowerCase().strip();
        boolean allComponents = s.toLowerCase().contains("r") && s.contains("100") && s.contains("150") &&
                s.contains("200") && s.contains("250") &&
                (s.contains(r2.getColor().toString()) || s.contains(""+r2.getColor().getRGB())) &&
                s.indexOf("100") < s.indexOf("150") && s.indexOf("150") < s.indexOf("200") &&
                s.indexOf("200") < s.indexOf("250");
        points += AutogradeCommon.displayMessage( allComponents, 1, "not writing the toString method correctly");

        // testing moveBy and contains
        boolean prev = r2.contains(150, 200);
        r2.moveBy(100, 100);
        points += AutogradeCommon.displayMessage( prev && !r2.contains(150, 200),
                0.5, "not moving away from the rectangle correctly");

        r2.moveBy(-100, -100);
        points += AutogradeCommon.displayMessage( r2.contains(150, 200),
                0.5, "not moving toward the rectangle correctly");

        System.out.println(points +"/5\n");
        return points;
    }

    public static void main(String[]args) {
        testRectangle();
    }
}
