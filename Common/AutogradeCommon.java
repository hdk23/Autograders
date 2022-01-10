import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * AutogradeCommon class for methods common to all autograders
 * @author Henry Kim, Dartmouth CS 10 TA, Fall 2021
 */
public class AutogradeCommon {
    /**
     * method that prints an error message for a failed test case
     * @param condition the expected value of a successful test case
     * @param points the number of points that the test case is worth
     * @param error the error message that the student sees for a failed test case
     * @return the number of points earned for the test case (either 0 or the parameter points)
     */
    public static double displayMessage(boolean condition, double points, String error){
        if (!condition) {
            String message = String.format("-%.2f for %s", points, error);
            System.out.println(message);
            return 0;
        }
        return points;
    }

    /**
     * method that checks two images pixel by pixel to determine whether they are identical
     * @param image1 first image
     * @param image2 second image
     * @return whether the two images are identical
     */
    public static boolean compareImages(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight())
            return false;

        int errorTolerance = image1.getWidth() * image1.getHeight() / 100;  // allow 1% tolerance for borders
        int diff = 0;
        for (int row = 0; row < image1.getHeight(); row++) {
            for (int col = 0; col < image1.getWidth(); col++) {
                if (image1.getRGB(col, row) != image2.getRGB(col, row))
                    diff++;
                if (diff > errorTolerance)
                    return false;
            }
        }
        return true;
    }

    /**
     * helper method to read an image
     * @param fileName of the image
     * @return an image produced by reading the file
     */
    public static BufferedImage imageHelper(String fileName){
        BufferedImage img;
        try {
            img = ImageIO.read(new File(fileName));
            return img;
        } catch (IOException e) {
            return null;
        }
    }

}