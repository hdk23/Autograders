import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestPolyline {
    /**
     * tests the methods in the Polyline class
     * @param directoryPath for the file that contains the points of the hardcoded Polyline
     * @return the number of points earned for the Polyline class
     */
    public static double testPolyline(String directoryPath) {
        double points = 0.0;
        System.out.println("TESTING POLYLINE");
        Polyline pl1 = new Polyline(new Point(400, 400), Color.RED);
        points += AutogradeCommon.displayMessage(pl1 != null, 0.5, "not having a constructor that takes a single point");
        ArrayList<Point> polyPoints = new ArrayList<Point>();
        try {
            BufferedReader input = new BufferedReader(new FileReader(directoryPath + "/polyline.txt"));
            String line;
            while ((line = input.readLine()) != null) {
                String[] coords = line.split(" ");
                for (int i = 0; i < coords.length; i += 2) {
                    Point p = new Point(Integer.parseInt(coords[i]), Integer.parseInt(coords[i+1]));
                    polyPoints.add(p);
                }
            }
        }
        catch (Exception e) {

        }
        Polyline pl2 = new Polyline(polyPoints, Color.BLUE);
        points += AutogradeCommon.displayMessage(pl2 != null, 0.5, "not having a constructor that takes a list of points");

        // getter and setter
        points += AutogradeCommon.displayMessage(pl1.getColor() == Color.RED && pl2.getColor() == Color.BLUE,
                0.5, "not getting the color in the getColor method correctly");
        pl1.setColor(Color.GREEN);
        pl2.setColor(Color.YELLOW);
        points += AutogradeCommon.displayMessage( pl1.getColor() == Color.GREEN && pl2.getColor() == Color.YELLOW,
                0.5, "not setting the color in the setColor method correctly");

        // toString
        String s = pl2.toString().toLowerCase().strip();
        boolean allComponents = s.toLowerCase().contains("p") && s.contains("493") && s.contains("166") &&
                s.contains("494") && s.contains("164") &&
                (s.contains(pl2.getColor().toString()) || s.contains(""+pl2.getColor().getRGB()));
        points += AutogradeCommon.displayMessage( allComponents, 1, "not writing the toString method correctly");

        // moveBy and contains
        points += AutogradeCommon.displayMessage(!pl2.contains(511, 151),
                0.5, "claiming that a point not on the polyline is part of the polyline (middle of a star)");

        points += AutogradeCommon.displayMessage(pl2.contains(500, 146), 0.5, "claiming that a point on the polyline is not part of the polyline");

        pl2.moveBy(-11, -5);
        points += AutogradeCommon.displayMessage(!pl2.contains(500, 146),
                0.5, "not correctly moving the shape such that a point no longer on the shape is considered part of the shape");

        points += AutogradeCommon.displayMessage(!pl2.contains(511, 151),
                0.5, "not correctly moving the shape such that a point now on the shape is considered not part of the shape");

        System.out.println(points +"/5\n");
        return points;
    }

    public static void main(String[]args) {
        testPolyline("Autograders/PS-6");
    }
}
