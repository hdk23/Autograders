import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * autogrades short assignment 3 in approximately 2-3 seconds
 * should add the corresponding getters and setters (particularly for brushDown)
 * @author Henry Kim, Dartmouth CS 10 TA, Winter 2022
 */
public class GradeSA3 {
    public static boolean checkLocalEdit(ImageProcessor1 ip, int cx, int cy, int radius) {
        BufferedImage originalImage = DrawingGUI.loadImage("pictures/baker.jpg");
        int diffCount = 0, pixelCount = 0;

        for (int row = 0; row < originalImage.getHeight(); row++) {
            for (int col = 0; col < originalImage.getWidth(); col++) {
                if (row >= cy-radius && row <= cy+radius && col >= cx-radius && col <= cx+radius) {
                    if (originalImage.getRGB(col, row) == ip.getImage().getRGB(col, row)) {
                        diffCount++;
                    }
                }
                else {
                    if (originalImage.getRGB(col, row) != ip.getImage().getRGB(col, row)) {
                        diffCount++;
                    }
                }
                pixelCount++;
            }
        }
        return diffCount < pixelCount/500;
    }

    public static void main(String[]args) {
        long start = System.currentTimeMillis();
        double score = 0.0;

        BufferedImage originalImage = AutogradeCommon.imageHelper("pictures/baker.jpg");
        ImageProcessor1 ip = new ImageProcessor1(originalImage);
        int radius = 100;
        // TODO: update as necessary
        int cx = 400, cy = 300;
        ip.localBlur(cx, cy, radius, 10);

        boolean local = checkLocalEdit(ip, cx, cy, radius);
        score += AutogradeCommon.displayMessage(local, 1, "not editing the image locally");

        // reset image
        ip.setImage(originalImage);
        ImageProcessingGUI1 ipg = new ImageProcessingGUI1(ip);
        ipg.setBrushDown(false);
        ipg.handleMouseMotion(cx, cy);
        score += AutogradeCommon.displayMessage(AutogradeCommon.compareImages(originalImage, ipg.getProc().getImage()), 0.5, "editing the image when the brush is up");

        ipg.setBrushDown(true);
        ipg.handleMouseMotion(cx, cy);
        local = checkLocalEdit(ip, cx, cy, radius);
        score += AutogradeCommon.displayMessage(local, 0.5, "not editing the image locally via ImageProcessingGUI");

        BufferedImage studentImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = studentImage.createGraphics();
        ipg.paint(graphics2D);
        try {
            ImageIO.write(studentImage,"png", new File("pictures/sa3StudentCopy.png"));

        } catch (Exception e) {

        }

        System.out.println(score +"/2");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + " ms to grade SA-3");
        System.exit(0);
    }
}
