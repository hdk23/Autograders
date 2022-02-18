import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.nio.Buffer;
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
        for (int attempt = 0; attempt < 10; attempt++) {
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
        rf.recolorImage();

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
        if (points < 4) {
            System.out.println((int) (4-points) + " regions have not been colored correctly.");
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

    private static boolean isNeighbor(Point p1, Point p2) {
        return !p1.equals(p2) && Math.pow((p1.x - p2.x),2) <= 1 && Math.pow((p1.y - p2.y),2) <= 1;
    }

    private static ArrayList<ArrayList<Point>> solutionFinder(RegionFinder rf, Color targetColor) {
        ArrayList<ArrayList<Point>> regions = new ArrayList<ArrayList<Point>>();
        BufferedImage image = AutogradeCommon.imageHelper("Autograders/PS-1/testlargestregion.png");
        // Keep track of which pixels have already been visited
        BufferedImage visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Look at all the pixels.
        for (int y=0; y<image.getHeight(); y++) {
            for (int x=0; x<image.getWidth(); x++) {
                // Start a new region if it's an unvisited pixel of the correct color
                if (visited.getRGB(x, y)>0 || !RegionFinder.colorMatch(targetColor, new Color(image.getRGB(x, y))))
                    continue;
                ArrayList<Point> region = new ArrayList<Point>();

                // Start visiting from the point
                ArrayList<Point> toVisit = new ArrayList<Point>(); // using it like a Stack
                toVisit.add(new Point(x, y));
                while (!toVisit.isEmpty()) {
                    // Add unvisited points to the region, and visit their correctly-colored neighbors
                    Point p = toVisit.remove(toVisit.size()-1);
                    if (visited.getRGB(p.x, p.y)==0) {
                        // Indicate that the point has been visited
                        visited.setRGB(p.x, p.y, 1);

                        // Add point to the current region
                        region.add(p);

                        // Find other points to visit of the current point's correctly-colored neighbors
                        for (int ny = Math.max(0, p.y-1); ny <= Math.min(image.getHeight()-1, p.y+1); ny++) {
                            for (int nx = Math.max(0, p.x-1); nx <= Math.min(image.getWidth()-1, p.x+1); nx++) {
                                if (visited.getRGB(nx, ny)==0 && (ny!=p.y || nx!=p.x) && RegionFinder.colorMatch(targetColor, new Color(image.getRGB(nx,ny)))) {
                                    toVisit.add(new Point(nx, ny));
                                }
                            }
                        }
                    }
                }

                // Big enough to be worth keeping?
                if (region.size() >= rf.getMinRegion()) {
                    regions.add(region);
                }
            }
        }
        return regions;
    }

    private static ArrayList<ArrayList<Point>> solutionFinderV2(RegionFinder rf, Color targetColor) {
        ArrayList<ArrayList<Point>> regions = new ArrayList<ArrayList<Point>>();
        BufferedImage image = AutogradeCommon.imageHelper("Autograders/PS-1/testlargestregion.png");
        // Keep track of which pixels have already been visited
        BufferedImage visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Look at all the pixels.
        for (int y=0; y<image.getHeight(); y++) {
            for (int x=0; x<image.getWidth(); x++) {
                // Start a new region if it's an unvisited pixel of the correct color
                if (visited.getRGB(x, y)>0 || !RegionFinder.colorMatch(targetColor, new Color(image.getRGB(x, y))))
                    continue;
                ArrayList<Point> region = new ArrayList<Point>();

                // Start visiting from the point
                ArrayList<Point> toVisit = new ArrayList<Point>(); // using it like a Stack
                toVisit.add(new Point(x, y));
                while (!toVisit.isEmpty()) {
                    // Add unvisited points to the region, and visit their correctly-colored neighbors
                    Point p = toVisit.remove(0);
                    if (visited.getRGB(p.x, p.y)==0) {
                        // Indicate that the point has been visited
                        visited.setRGB(p.x, p.y, 1);

                        // Add point to the current region
                        region.add(p);

                        // Find other points to visit of the current point's correctly-colored neighbors
                        for (int ny = Math.max(0, p.y-1); ny <= Math.min(image.getHeight()-1, p.y+1); ny++) {
                            for (int nx = Math.max(0, p.x-1); nx <= Math.min(image.getWidth()-1, p.x+1); nx++) {
                                if (visited.getRGB(nx, ny)==0 && (ny!=p.y || nx!=p.x) && RegionFinder.colorMatch(targetColor, new Color(image.getRGB(nx,ny)))) {
                                    toVisit.add(new Point(nx, ny));
                                }
                            }
                        }
                    }
                }

                // Big enough to be worth keeping?
                if (region.size() >= rf.getMinRegion()) {
                    regions.add(region);
                }
            }
        }
        return regions;
    }

    public static double testFindRegions(RegionFinder rf) {
        System.out.println("TESTING FIND REGIONS");
        double points = 0;
        rf.setImage(AutogradeCommon.imageHelper("Autograders/PS-1/testlargestregion.png"));
        Color targetColor = new Color(18, 49, 43);
        long start = System.currentTimeMillis();
        rf.findRegions(targetColor);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        long start2 = System.currentTimeMillis();
        ArrayList<ArrayList<Point>> solutionRegions2 = solutionFinderV2(rf, targetColor);
        long finish2 = System.currentTimeMillis();
        long timeElapsed2 = finish2 - start2;

        System.out.println("Your region finder took "+ timeElapsed + " ms.");
        System.out.println("In comparison, the solution region finder took "+ timeElapsed2 + " ms.");
        System.out.println("Your region finder performs "+ timeElapsed2/timeElapsed + " time(s) as quickly as the solution.");
        points += AutogradeCommon.displayMessage(timeElapsed < timeElapsed2 * timeElapsed2, 2, "having a major inefficiency in region finder");


        ArrayList<ArrayList<Point>> solutionRegions = solutionFinder(rf, targetColor);

        boolean samePoint = true;
        boolean neighboring = true;
        boolean correctColor = true;
        for (int i = 0; i < solutionRegions.size(); i++) {
            for (int j = 1; j < solutionRegions.get(i).size(); j++) {
                if (samePoint)
                    samePoint = solutionRegions.get(i).get(j).equals(rf.getRegions().get(i).get(j)) || solutionRegions2.get(i).get(j).equals(rf.getRegions().get(i).get(j));
                if (neighboring)
                    neighboring = isNeighbor(rf.getRegions().get(i).get(j-1), rf.getRegions().get(i).get(j));
                if (correctColor)
                    correctColor = RegionFinder.colorMatch(targetColor, new Color(rf.getImage().getRGB(rf.getRegions().get(i).get(j).x, rf.getRegions().get(i).get(j).y)));
                if (!samePoint && !neighboring && !correctColor)
                    break;
            }
        }

        points += AutogradeCommon.displayMessage(samePoint, 6, "starting region growth at appropriate pixels");
        points += AutogradeCommon.displayMessage(neighboring, 6, "having points in regions that don't neighbor each other");
        points += AutogradeCommon.displayMessage(correctColor, 6, "having points in regions that don't match the color");
        System.out.println(points +"/20\n");
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

        cp.getRegionFinder().setImage(AutogradeCommon.imageHelper("Autograders/PS-1/baker-1280-720.jpg"));

        // test using each image—start with webcam
        try {
            cp.handleKeyPress('w');
            cp.handleKeyPress('i');
            points += AutogradeCommon.displayMessage(true, 1, "not drawing the image for webcam mode");
        }
        catch (Exception e) {
            points += AutogradeCommon.displayMessage(false, 1, "not drawing the image for webcam mode");
        }

        // recolored
        try {
            cp.handleKeyPress('w');
            cp.getRegionFinder().setImage(AutogradeCommon.imageHelper("Autograders/PS-1/baker-1280-720.jpg"));
            int x = (int) (Math.random() * cp.getRegionFinder().getImage().getWidth());
            int y = (int) (Math.random() * cp.getRegionFinder().getImage().getHeight());
            cp.handleMousePress(x, y);
            cp.processImage();
            cp.handleKeyPress('r');
            cp.handleKeyPress('o');
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
                cp.getRegionFinder().setImage(AutogradeCommon.imageHelper("Autograders/PS-1/baker-1280-720.jpg"));
                int x = (int) (Math.random() * cp.getRegionFinder().getImage().getWidth());
                int y = (int) (Math.random() * cp.getRegionFinder().getImage().getHeight());
                cp.handleMousePress(x, y);
                cp.processImage();
                cp.handleKeyPress('p');
                points += AutogradeCommon.displayMessage(true, 2, "not drawing the image for painting mode");
            }
            catch (Exception e) {
                System.err.println(""+e);
                points += AutogradeCommon.displayMessage(false, 2, "not drawing the image for painting mode");
            }

        } while (cp.getRegionFinder().largestRegion() == null);

        try {
            cp.handleKeyPress('s');
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
            try {
                if (i == 0) {
                    cp.getRegionFinder().setImage(null);
                    x = 0;
                    y = 0;
                    cp.handleMousePress(x, y);
                }
                else {
                    cp.setImage(AutogradeCommon.imageHelper("Autograders/PS-1/baker-1280-720.jpg"));
                    cp.getRegionFinder().setImage(AutogradeCommon.imageHelper("Autograders/PS-1/baker-1280-720.jpg"));
                    x = (int) (Math.random() * cp.getRegionFinder().getImage().getWidth());
                    y = (int) (Math.random() * cp.getRegionFinder().getImage().getHeight());
                    cp.handleMousePress(x, y);
                    points += AutogradeCommon.displayMessage(cp.getTargetColor().getRGB() == cp.getRegionFinder().getImage().getRGB(x, y),
                            1, "not handling press correctly at " + x + ", " + y);
                }
            }
            catch (NullPointerException e) {
                System.out.println(e);
                if (!nullPenalized) {
                    nullPenalized = true;
                    points--;
                    points += AutogradeCommon.displayMessage(false, 1, "not checking whether the image is null");
                }
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
        cp.setImage(AutogradeCommon.imageHelper("Autograders/PS-1/baker-1280-720.jpg"));
        // check whether target color is null
        cp.setTargetColor(null);
        try {
            cp.processImage();
            points += AutogradeCommon.displayMessage(true, 1, "not checking whether the target color is null");
        }
        catch (NullPointerException e) {
            points += AutogradeCommon.displayMessage(false, 1, "not checking whether the target color is null");
        }

        cp.setTargetColor(targetColor);  // rare to have a bright yellow region in a room——change if needed

        // check whether largest region is null
        try {
            cp.handleKeyPress('w');
            cp.processImage();
            points += AutogradeCommon.displayMessage(true, 1, "not checking whether the largest region is null");
        }
        catch (NullPointerException e) {
            points += AutogradeCommon.displayMessage(false, 1, "not checking whether the largest region is null");
        }
        // run until a largest region found to test the other parts
        try {
            cp.setImage(AutogradeCommon.imageHelper("Autograders/PS-1/baker-1280-720.jpg"));
            do {
                int x = (int) (Math.random() * cp.getRegionFinder().getImage().getWidth());
                int y = (int) (Math.random() * cp.getRegionFinder().getImage().getHeight());
                try {
                    cp.setTargetColor(new Color(cp.getRegionFinder().getImage().getRGB(x, y)));
                }
                catch (Exception e){

                }
            }
            while (cp.getRegionFinder().largestRegion() == null);
            System.out.println("336");
//            cp.handleKeyPress('w');
            cp.processImage();

            // check whether image set
            System.out.println("344");
            points += AutogradeCommon.displayMessage(rf.getRegions().size() != 0, 2, "not calling findRegions");
            points += AutogradeCommon.displayMessage(rf.getRecoloredImage() != null &&
                    !AutogradeCommon.compareImages(rf.getRecoloredImage(), rf.getImage()), 2, "not calling recolorImage");

            // check whether largest region points are colored
            int diffPixels = 0;
            int threshold = cp.getRegionFinder().getImage().getHeight() * cp.getRegionFinder().getImage().getWidth() / 1000;
            boolean paintColorSet = true;
            for (Point p : rf.largestRegion()) {
                if (cp.getPainting().getRGB(p.x, p.y) != cp.getPaintColor().getRGB()) {
                    diffPixels++;
                    if (diffPixels == threshold) {
                        paintColorSet = false;
                        break;
                    }
                }
            }
            points += AutogradeCommon.displayMessage(paintColorSet, 4, "not setting the points in the largest region to the paint color on the painting");
        }
        catch (NullPointerException e) {
            System.out.println("365"+e);
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
            PrintStream fileOut = new PrintStream("Grading/"+term+"/PS/PS-1/"+ folderName +"/PS-1 Scoring Sheet " + name + ".txt");
            System.setOut(fileOut);
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
        score += testFindRegions(rf);
        CamPaint cp = new CamPaint();
        cp.setRecoloredFilePath("Autograders/PS-1/recolored.png");
        cp.setPaintingFilePath("Autograders/PS-1/painting.png");
        cp.setWebcamFilePath("Autograders/PS-1/webcam.png");
        score += testHandleMousePress(cp);  // 5	Setting tracking color
//        score += testProcessImage(cp, targetColor);  // 10	Updating the painting according to the paintbrush
//        score += testDraw(cp);  // 5	Drawing the appropriate image
        System.out.println("TOTAL: " + score +"/55\n");
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
        gradePS1("22W","extra", "Andrew Cho", new Color(255, 255, 0));
        System.exit(0);
    }
}
