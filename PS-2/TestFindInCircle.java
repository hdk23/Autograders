/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestFindInCircle {
    /** BORROWED FROM CS 10 Problem Set 2 Scaffold
     * A simple testing procedure, making sure actual is expected, and printing a message if not
     * @param x		query x coordinate
     * @param y		query y coordinate
     * @param r		query circle radius
     * @param expectedCircleRectangle	how many times Geometry.circleIntersectsRectangle is expected to be called
     * @param expectedInCircle			how many times Geometry.pointInCircle is expected to be called
     * @param expectedHits				how many points are expected to be found
     * @return  0 if passed; 1 if failed
     */
    private static int testFind(PointQuadtree<Dot> tree, int x, int y, int r, int expectedCircleRectangle, int expectedInCircle, int expectedHits) {
        Geometry.resetNumInCircleTests();
        Geometry.resetNumCircleRectangleTests();
        int errs = 0;
        int num = tree.findInCircle(x, y, r).size();
        String which = "("+x+","+y+")@"+r;
        if (Geometry.getNumCircleRectangleTests() != expectedCircleRectangle) {
            errs++;
            System.err.println(which+": wrong # circle-rectangle, got "+Geometry.getNumCircleRectangleTests()+" but expected "+expectedCircleRectangle);
        }
        if (Geometry.getNumInCircleTests() != expectedInCircle) {
            errs++;
            System.err.println(which+": wrong # in circle, got "+Geometry.getNumInCircleTests()+" but expected "+expectedInCircle);
        }
        if (num != expectedHits) {
            errs++;
            System.err.println(which+": wrong # hits, got "+num+" but expected "+expectedHits);
        }
        return errs;
    }

    /**
     * from my PS-2 test code, tests the student's PointQuadtree class's findInCircle method using given test case examples and new test cases
     * 10 points total: 1 point per test case
     * @return the number of points earned from this section
     */
    public static double tests(){
        PointQuadtree<Dot> tree = new PointQuadtreeSolution<Dot>(new Dot(300,400), 0,0,800,600); // start with A
        tree.insert(new Dot(150,450)); // B
        tree.insert(new Dot(250,550)); // C
        tree.insert(new Dot(450,200)); // D
        tree.insert(new Dot(200,250)); // E
        tree.insert(new Dot(350,175)); // F
        tree.insert(new Dot(500,125)); // G
        tree.insert(new Dot(475,250)); // H
        tree.insert(new Dot(525,225)); // I
        tree.insert(new Dot(490,215)); // J
        tree.insert(new Dot(700,550)); // K
        tree.insert(new Dot(310,410)); // L

        tree = (PointQuadtree<Dot>) tree;
        int bad = 0;

        // provided test cases
        bad += testFind(tree, 150,450,10,6,3,1); 	// rect for A [D] [E] [B [C]] [K]; circle for A, B, C; find B
        bad += testFind(tree, 500,125,10,8,3,1);	// rect for A [D [G F H]] [E] [B] [K]; circle for A, D, G; find G
        bad += testFind(tree, 300,400,15,10,6,2);	// rect for A [D [G F H]] [E] [B [C]] [K [L]]; circle for A,D,E,B,K,L; find A,L
        bad += testFind(tree, 495,225,50,10,6,3);	// rect for A [D [G F H [I [J]]]] [E] [B] [K]; circle for A,D,G,H,I,J; find H,I,J
        bad += testFind(tree, 0,0,900,12,12,12);

        // my test cases
        bad += testFind(tree, 700, 550, 10, 6,3,1); // rect for A [D] [E] [B] [K [L]]; circle for A, K, L; find K
        bad += testFind(tree, 711, 561, 10, 6,2,0); // rect for A [D] [E] [B [C]] [K]; circle for A, K; outside of circle
        bad += testFind(tree, 200, 500, 75, 6,3,2); // rect for A [D] [E] [B [C]] [K]; circle for A, B, C; find B, C
        bad += testFind(tree, 200, 500, 25, 6,3,0); // rect for A [D] [E] [B [C]] [K]; circle for A, B, C;
        bad += testFind(tree, 100, 100, 100, 5,2,0); // rect for A [D] [E] [B] [K]; circle for A, E; in the middle of nowhere
        return Math.max(10.0-bad/2.0, 0);
    }


    /**
     * tests the student's PointQuadtree class's findInCircle method
     * 10 points total
     * @return the number of points earned from this section
     */
    public static double testFindInCircle() {
        double points = 0;
        System.out.println("TESTING FIND IN CIRCLE");
        try {
            points = tests();
        }
        catch (NullPointerException e) {
            points += AutogradeCommon.displayMessage( false, 1, "" + e);
            points -= 1;
        }
        catch (IndexOutOfBoundsException e) {
            points += AutogradeCommon.displayMessage( false, 0.5, "" + e);
            points -= 0.5;
        }
        catch (Exception e) {
            System.err.println("EXCEPTION " + e);
        }

        points = Math.max(points, 0);
        System.out.println(points +"/10\n");
        return points;
    }
}
