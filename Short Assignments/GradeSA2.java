//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//
///**
// * autogrades short assignment 2 in approximately 1-2 seconds
// * @author Henry Kim, Dartmouth CS 10 TA, Winter 2022
// */
//
//public class GradeSA2 {
//    public static void main(String[]args) {
//        double points = 0;
//        long start = System.currentTimeMillis();
//
//        Pollock p = new Pollock();
//        Color prevColor = Color.black;
//        boolean randomColors = true;
//        try {
//            for (WanderingPixel b : p.getBlobs()) {
//                if (prevColor.equals(b.getColor())) {
//                    randomColors = false;
//                    break;
//                }
//            }
//        }
//        catch (Exception e) {
//            System.err.println(""+e);
//            randomColors = false;
//        }
//        finally {
//            points += AutogradeCommon.displayMessage(randomColors, 1,
//                    "not setting colors randomly");
//        }
//
//        try {
//            p.handleTimer();
//        }
//        catch (NullPointerException e) {
//
//        }
//
//        BufferedImage result = p.getResult();
//        int colorsSet = 0;
//        for (WanderingPixel b: p.getBlobs()) {
//            boolean bounds = (b.getX() >= 0 && b.getX() <= 800 && b.getY() >= 0 && b.getY() <= 600);
//            if (bounds && result.getRGB((int) b.getX(), (int) b.getY()) == b.getColor().getRGB()) {
//                colorsSet++;
//            }
//        }
//
//        points += AutogradeCommon.displayMessage(colorsSet >= 1000 && colorsSet <= 1250, 1,
//                "not setting colors correctly in handleTimer");
//
//        System.out.println(points +"/2");
//        long finish = System.currentTimeMillis();
//        long timeElapsed = finish - start;
//        System.out.println(timeElapsed + " ms to grade SA-2");
//        System.exit(0);
//    }
//}
