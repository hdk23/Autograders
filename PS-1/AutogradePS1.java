import java.awt.*;
import java.util.ArrayList;


/**
 * autogrades most of CS 10 Problem Set 1, consists of several unit testing and integration testing methods
 * @author Henry Kim, Dartmouth CS 10 TA, Fall 2021
 */
public class AutogradePS1 {
    
    /**
     * finds regions of a random color——runs a new color until there are regions found
     * @param rf RegionFinder
     */
    public static void findRandomColorRegions(RegionFinder rf) {
        do {
            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            rf.findRegions(new Color(r, g, b));
        } while (rf.getRegions().size() == 0);
    }


    /**
     * tests the colorMatch method in the RegionFinder class
     * @param rf RegionFinder to test on
     * @return the number of points earned for the colorMatch method
     */
    public static double testColorMatch(RegionFinder rf) {
        System.out.println("TESTING COLOR MATCH");
        double points = 0;
        int r1 = (int) (Math.random() * 220);
        int g1 = (int) (Math.random() * 220);
        int b1 = (int) (Math.random() * 220);
        Color sameColor1 = new Color(r1, g1, b1);
        Color sameColor2 = new Color(r1, g1, b1);
        points += AutogradeCommon.displayMessage(RegionFinder.colorMatch(sameColor1, sameColor2), 1, "not matching the same colors");

        Color similarColor = new Color(r1+5, g1+10, b1+10);
        points += AutogradeCommon.displayMessage(RegionFinder.colorMatch(sameColor1, similarColor), 2, "not matching similar enough colors");

        Color diffColor = new Color(r1+35, g1+35, b1+35);
        points += AutogradeCommon.displayMessage(!RegionFinder.colorMatch(sameColor1, diffColor), 2, "matching colors with no matching components");

        System.out.println(points +"/5\n");
        return points;
    }


    /**
     * tests whether big enough groups are kept as regions
     * @param rf RegionFinder to test on
     * @return the number of points earned for this criterion
     */
    public static double testBigEnoughRegions(RegionFinder rf){
        System.out.println("TESTING WHETHER BIG-ENOUGH GROUPS OF POINTS KEPT AS REGIONS");
        rf.setImage(AutogradeCommon.imageHelper("Autograders/PS-1/baker.jpg"));
        double points = 5;
        findRandomColorRegions(rf);
        for (ArrayList<Point> region: rf.getRegions()){
            if (region.size() < rf.getMinRegion()) {
                points--;
            }

            if (points < 0) {
                System.out.println("Too many regions have been detected as being too small.");
                points = 0;
                break;
            }
        }
        if (points != 5)
            System.out.println((5-points) + " regions detected as being too small");
        System.out.println(points +"/5\n");
        return points;
    }

    /**
     * tests the largestRegion method in the RegionFinder class
     * @param rf RegionFinder to test on
     * @return the number of points earned for the largestRegion method
     */
    public static double testLargestRegion(RegionFinder rf) {
        System.out.println("TESTING LARGEST REGION");
        double points = 0;
        rf.setImage(AutogradeCommon.imageHelper("Autograders/PS-1/testlargestregion.png"));
        rf.findRegions(new Color(18, 49, 43));
        points += AutogradeCommon.displayMessage(rf.largestRegion() == null || rf.largestRegion().size() == 0, 1, "returning a largest region when no regions are large enough");

        rf.findRegions(new Color(0, 105, 62));
        points += AutogradeCommon.displayMessage(rf.largestRegion() != null, 7, "returning null when there is a large enough region");
        points += AutogradeCommon.displayMessage(rf.largestRegion().size() >= 10000, 1, "returning the largest region with an incorrect size");

        rf.setImage(AutogradeCommon.imageHelper("Autograders/PS-1/baker.jpg"));
        boolean correctSize = true;
        for (int attempt = 0; attempt < 100; attempt++) {
            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            rf.findRegions(new Color(r, g, b));
            if (rf.largestRegion() != null && rf.largestRegion().size() < rf.getMinRegion()) {
                correctSize = false;
                break;
            }
        }

        points += AutogradeCommon.displayMessage(correctSize, 1, "returning a largest region smaller than minRegion");
        System.out.println(points +"/10\n");
        return points;
    }

    /**
     * tests the largestRegion method in the RegionFinder class
     * @param rf RegionFinder to test on
     * @return the number of points earned for the largestRegion method
     */
    public static double testRecolorImage(RegionFinder rf) {
        System.out.println("TESTING RECOLOR IMAGE");
        double points = 4;
        int previousColor = 0;
        boolean randomColors = true;
        ArrayList<Integer> rgbs = new ArrayList<>();
        rf.setImage(AutogradeCommon.imageHelper("Autograders/PS-1/baker.jpg"));
        findRandomColorRegions(rf);
        try {
            Thread.sleep(500);
            rf.recolorImage();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (ArrayList<Point> region: rf.getRegions()){
            int x = (int) region.get(0).getX();
            int y = (int) region.get(0).getY();
            int regionRGB = rf.getRecoloredImage().getRGB(x, y);
            rgbs.add(regionRGB);
            for (Point p: region) {
                int p_x = (int) p.getX();
                int p_y = (int) p.getY();
                if (rf.getRecoloredImage().getRGB(p_x, p_y) != regionRGB) {
                    points--;
                    break;
                }
            }
            if (points < 0) {
                System.out.println("Too many regions have not been colored correctly.");
                points = 0;
                break;
            }
            if (previousColor == regionRGB) {
                randomColors = false;
            }
        }
        points += AutogradeCommon.displayMessage(randomColors, 2, "not setting each region to a different color");
        // make sure colors are different each time
        findRandomColorRegions(rf);

        // try-catch for stopping if index out of bounds
        int index = 0;
        try {
            for (ArrayList<Point> region: rf.getRegions()){
                int x = (int) region.get(0).getX();
                int y = (int) region.get(0).getY();
                int regionRGB = rf.getRecoloredImage().getRGB(x, y);
                if (regionRGB == rgbs.get(index)) {
                    randomColors = false;
                    break;
                }
            }
            index++;
        }
        catch (Exception e) {

        }
        points += AutogradeCommon.displayMessage(randomColors, 2, "not setting colors randomly each iteration");

        // need to check original image and whether colors are randomly generated
        points += AutogradeCommon.displayMessage(AutogradeCommon.compareImages(AutogradeCommon.imageHelper("Autograders/PS-1/baker.jpg"),
                rf.getImage()), 2, "touching the original image");
        System.out.println(points +"/10\n");
        return points;
    }

    /**
     * tests the draw method in the CamPaint class
     * @param cp CamPaint to test on
     * @return the number of points earned for the draw method
     */
    public static double testDraw(CamPaint cp) {
        System.out.println("TESTING DRAW");
        double points = 0;

        cp.setRecoloredFilePath("Autograders/PS-1/recoloredStudentCopy.png");
        cp.setPaintingFilePath("Autograders/PS-1/paintingStudentCopy.png");
        cp.setWebcamFilePath("Autograders/PS-1/webcamStudentCopy.png");

        // test using each image—start with webcam
        try {
            cp.handleKeyPress('w');
            Thread.sleep(500);
            cp.handleKeyPress('i');
            Thread.sleep(500);
            points += AutogradeCommon.displayMessage(true, 1, "not drawing the image for webcam mode");
            Thread.sleep(500);
        }
        catch (Exception e) {
            points += AutogradeCommon.displayMessage(false, 1, "not drawing the image for webcam mode");
        }

        // recolored
        try {
            cp.handleKeyPress('w');
            Thread.sleep(500);
            cp.getRegionFinder().setImage(cp.image);
            Thread.sleep(500);
            int x = (int) (Math.random() * cp.getRegionFinder().getImage().getWidth());
            int y = (int) (Math.random() * cp.getRegionFinder().getImage().getHeight());
            Thread.sleep(500);
            cp.handleMousePress(x, y);
            Thread.sleep(500);
            cp.processImage();
            Thread.sleep(500);
            cp.handleKeyPress('r');
            Thread.sleep(500);
            cp.handleKeyPress('o');
            Thread.sleep(500);
            points += AutogradeCommon.displayMessage(true, 2, "not drawing the image for recolor mode");
        }
        catch (Exception e) {
            System.out.println(""+e);
            points += AutogradeCommon.displayMessage(false, 2, "not drawing the image for recolor mode");
        }

        // painting
        cp.handleKeyPress('w');
        do {
            try {
                Thread.sleep(500);
                cp.getRegionFinder().setImage(cp.image);
                int x = (int) (Math.random() * cp.getRegionFinder().getImage().getWidth());
                int y = (int) (Math.random() * cp.getRegionFinder().getImage().getHeight());
                Thread.sleep(500);
                cp.handleMousePress(x, y);
                Thread.sleep(500);
                cp.processImage();
                Thread.sleep(500);
                cp.handleKeyPress('p');
                Thread.sleep(500);

                Thread.sleep(500);
                points += AutogradeCommon.displayMessage(true, 2, "not drawing the image for painting mode");
                Thread.sleep(500);
            }
            catch (Exception e) {
                System.err.println(""+e);
                points += AutogradeCommon.displayMessage(false, 2, "not drawing the image for painting mode");
            }

        } while (cp.getRegionFinder().largestRegion() == null);

        try {
            Thread.sleep(500);
            cp.handleKeyPress('s');
            Thread.sleep(500);
        }
        catch (Exception e) {

        }

        System.out.println(points +"/5\n");
        return points;
    }


    /**
     * tests the handleMousePress method in the CamPaint class
     * @param cp CamPaint to test on
     * @return the number of points earned for the handleMousePress method
     */
    public static double testHandleMousePress(CamPaint cp) {
        System.out.println("TESTING HANDLE MOUSE PRESS");
        double points = 1;  // 1 point for checking null image, deducted if null pointer exception caught
        boolean nullPenalized = false;
        int x, y;
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                cp.getRegionFinder().setImage(null);
                x = 0;
                y = 0;
            }
            else {
                cp.handleKeyPress('w');
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                cp.getRegionFinder().setImage(cp.image);
                x = (int) (Math.random() * cp.getRegionFinder().getImage().getWidth());
                y = (int) (Math.random() * cp.getRegionFinder().getImage().getHeight());
            }

            try {
                cp.handleMousePress(x, y);
            }
            catch (NullPointerException e) {
                if (!nullPenalized) {
                    nullPenalized = true;
                    points--;
                    points += AutogradeCommon.displayMessage(false, 1, "not checking whether the image is null");
                }
            }
            finally {
                if (i != 0)
                    points += AutogradeCommon.displayMessage(cp.getTargetColor().getRGB() == cp.image.getRGB(x, y),
                            1, "not handling press correctly at " + x + ", " + y);
            }
        }
        System.out.println(points +"/5\n");
        return points;
    }

    /**
     * tests the draw method in the CamPaint class
     * @param cp CamPaint to test on
     * @param targetColor a color that's not in the TA's room
     * @return the number of points earned for the draw method
     */
    public static double testProcessImage(CamPaint cp, Color targetColor) {
        System.out.println("TESTING PROCESS IMAGE");
        double points = 0;
        RegionFinder rf = cp.getRegionFinder();
        // check whether target color is null
        try {
            Thread.sleep(1000);
            cp.setTargetColor(null);

            Thread.sleep(3000);
            try {
                cp.processImage();
                Thread.sleep(1000);
                points += AutogradeCommon.displayMessage(true, 1, "not checking whether the target color is null");
            }
            catch (NullPointerException e) {
                points += AutogradeCommon.displayMessage(false, 1, "not checking whether the target color is null");
            }
        }
        catch (InterruptedException e) {

        }
        cp.setTargetColor(targetColor);  // rare to have a bright yellow region in a room——change if needed

        // check whether largest region is null
        try {
            Thread.sleep(500);
            cp.handleKeyPress('w');
            Thread.sleep(500);
            cp.processImage();
            Thread.sleep(500);
            points += AutogradeCommon.displayMessage(true, 1, "not checking whether the largest region is null");
            Thread.sleep(500);
        }
        catch (NullPointerException | InterruptedException e) {
            points += AutogradeCommon.displayMessage(false, 1, "not checking whether the largest region is null");
        }

        // run until a largest region found to test the other parts
        try {
            do {

                cp.handleKeyPress('w');
                int x = (int) (Math.random() * cp.image.getWidth());
                int y = (int) (Math.random() * cp.image.getHeight());
                cp.setTargetColor(new Color(cp.image.getRGB(x, y)));
                try {
                    Thread.sleep(500);
                    cp.handleKeyPress('w');
                    Thread.sleep(500);
                    cp.processImage();
                }
                catch (Exception e) {

                }
            }
            while (rf.largestRegion() == null);

            // check whether image set
            points += AutogradeCommon.displayMessage(rf.getRegions().size() != 0, 2, "not calling findRegions");
            points += AutogradeCommon.displayMessage(rf.getRecoloredImage() != null &&
                    !AutogradeCommon.compareImages(rf.getRecoloredImage(), rf.getImage()), 2, "not calling recolorImage");

            // check whether largest region points are colored
            int diffPixels = 0;
            boolean paintColorSet = true;
            for (Point p : rf.largestRegion()) {
                if (cp.getPainting().getRGB(p.x, p.y) != cp.getPaintColor().getRGB()) {
                    diffPixels++;
                    if (diffPixels == cp.image.getHeight() * cp.image.getWidth() / 1000) {
                        paintColorSet = false;
                        break;
                    }
                }
            }
            points += AutogradeCommon.displayMessage(paintColorSet, 4, "not setting the points in the largest region to the paint color on the painting");
        }
        catch (NullPointerException e) {

        }

        System.out.println(points +"/10\n");
        return points;
    }


    /**
     * method that runs all test methods
     * @param term you're grading the assignment (e.g. 21F, 22W)
     * @param folderName from Canvas (e.g. kimhenry for Henry Kim)
     * @param name student's name
     * @param targetColor a color not in the TA's room (used by processImage)
     * @return the number of points earned by the autograded sections (out of 50)
     */
    public static double gradePS1(String term, String folderName, String name, Color targetColor) {
        RegionFinder rf = new RegionFinder();
        long start = System.currentTimeMillis();

        try {
//            PrintStream fileOut = new PrintStream("Grading/"+term+"/PS/PS-1/"+ folderName +"/PS-1 Scoring Sheet " + name + ".txt");
//            System.setOut(fileOut);
        }
        catch (Exception e) {

        }
        System.out.println("Problem Set 1 Autograder");
        System.out.print("NAME: ");
        System.out.println(name);
        double score = 0;

        score += testColorMatch(rf);  // 5	Matching color
        score += testBigEnoughRegions(rf);  // 5	Keeping big-enough groups of points as the regions
        score += testLargestRegion(rf);  // 10	Finding the largest region
        score += testRecolorImage(rf);  // 10	Recoloring image based on detected regions
        CamPaint cp = new CamPaint();
        cp.setRecoloredFilePath("Autograders/PS-1/recolored.png");
        cp.setPaintingFilePath("Autograders/PS-1/painting.png");
        cp.setWebcamFilePath("Autograders/PS-1/webcam.png");
        score += testHandleMousePress(cp);  // 5	Setting tracking color
        score += testProcessImage(cp, targetColor);  // 10	Updating the painting according to the paintbrush
        score += testDraw(cp);  // 5	Drawing the appropriate image
        System.out.println("TOTAL: " + score +"/50\n");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + " ms to grade these parts");
        System.out.println("TIME TO EXAMINE THE REST");
        return score;
    }


    /**
     * main method to run tests——call each function separately to test individually
     */
    public static void main(String[] args) {
        gradePS1("22W","kimhenry", "Henry Kim", new Color(255, 255, 0));
        System.exit(0);
    }
}
