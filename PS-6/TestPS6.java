import java.awt.image.BufferedImage;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestPS6 {
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
     * method that checks two images pixel by pixel to determine whether they are identical within specified ranges
     * @param image1 first image
     * @param image2 second image
     * @param x1 top left corner x value
     * @param y1 top left corner y value
     * @param x2 bottom right corner x value
     * @param y2 bottom right corner y value
     * @return whether the two images are identical in the specified region
     */
    public static boolean comparePartialImages(BufferedImage image1, BufferedImage image2, int x1, int y1, int x2, int y2) {
        int errorTolerance = Math.abs((y2-y1))* Math.abs((x2-x1))/ 1000;  // allow 0.1% tolerance for borders
        int diff = 0;
        for (int row = y1; row < y2; row++) {
            for (int col = x1; col < x2; col++) {
                if (image1.getRGB(col, row) != image2.getRGB(col, row))
                    diff++;
                if (diff > errorTolerance)
                    return false;
            }
        }
        return true;
    }

    /**
     * method that checks how similar two images are
     * @param image1 first image
     * @param image2 second image
     * @param x1 top left corner x value
     * @param y1 top left corner y value
     * @param x2 bottom right corner x value
     * @param y2 bottom right corner y value
     * @return the percentage that the images are similar
     */
    public static double similarity(BufferedImage image1, BufferedImage image2, int x1, int y1, int x2, int y2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight())
            return 0;

        int possiblePoints = Math.abs((y2-y1))* Math.abs((x2-x1));
        double samePixels = possiblePoints;
        for (int row = y1; row < y2; row++) {
            for (int col = x1; col < x2; col++) {
                if (image1.getRGB(col, row) != image2.getRGB(col, row))
                    samePixels--;
            }
        }
        return 100.0 * samePixels / possiblePoints;
    }
}
