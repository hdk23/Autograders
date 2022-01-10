import java.util.ArrayList;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestAllPoints {
    /**
     * @param dots test case answer for TestAllPoints
     * @return whether the tree traverses the dots correctly
     */
    public static boolean samePoints(PointQuadtree<Dot> tree, ArrayList<Dot> dots) {
        boolean same = true;
        for (int index = 0; index < dots.size(); index++) {
            if (dots.get(index).getX() != tree.allPoints().get(index).getX() || dots.get(index).getY() != tree.allPoints().get(index).getY()) {
                same = false;
                break;
            }
        }
        return same;
    }

    /**
     * factored out into separate method to add many points in a particular order: pre-order
     * @return the list of dots
     */
    public static ArrayList<Dot> depth2Preorder(){
        ArrayList<Dot> dots = new ArrayList<>();
        dots.add(new Dot(400, 300));  // root
        for (int[] center: AutogradePS2.quadrantCenters) {
            dots.add(new Dot(center[0], center[1]));  // center
            AutogradePS2.addPoints(dots, center);
        }
        return dots;
    }

    /**
     * alternate traversal: post-order
     * @return the list of dots
     */
    public static ArrayList<Dot> depth2Postorder(){
        ArrayList<Dot> dots = new ArrayList<>();
        for (int[] center: AutogradePS2.quadrantCenters) {
            AutogradePS2.addPoints(dots, center);
            dots.add(new Dot(center[0], center[1]));  // center
        }
        dots.add(new Dot(400, 300));  // root
        return dots;
    }

    /**
     * tests the student's PointQuadtree class's allPoints method
     * 5 points total
     * +1 point for working at depth = 0
     * +2 points for working at depth = 1
     * +2 points for working at depth = 2
     * @return the number of points earned from this section
     */
    public static double testAllPoints() {
        double points = 0;
        PointQuadtree<Dot> tree = new PointQuadtree<>(new Dot(400, 300), 0, 0, 800, 600);;

        System.out.println("TESTING ALLPOINTS");
        try {
            ArrayList<Dot> dots = new ArrayList<Dot>();  // pre-order
            ArrayList<Dot> dots2 = new ArrayList<Dot>();  // post-order
            dots.add(new Dot(400, 300));
            Dot root = tree.allPoints().get(0);

            points += AutogradeCommon.displayMessage(root.getX() == dots.get(0).getX() &&
                    root.getY() == dots.get(0).getY(), 1, "not working at depth = 0");
            for (int[]center: AutogradePS2.quadrantCenters){
                dots.add(new Dot(center[0], center[1]));
                dots2.add(new Dot(center[0], center[1]));
            }

            dots2.add(new Dot(400, 300));

            for (int[] center: AutogradePS2.quadrantCenters) {
                Dot newDot = new Dot(center[0], center[1]);
                tree.insert(newDot);
            }

            // compare the number of dots and the order of points——both pre-order and post-order accepted
            points += AutogradeCommon.displayMessage(tree.allPoints().size() == dots.size() &&
                    (samePoints(tree, dots) || samePoints(tree, dots2)), 2, "not traversing correctly at depth = 1");

            for (int[] center: AutogradePS2.quadrantCenters) {
                AutogradePS2.addPoints(tree, center);
            }

            dots = depth2Preorder();
            dots2 = depth2Postorder();
            points += AutogradeCommon.displayMessage(tree.allPoints().size() == dots.size() &&
                    (samePoints(tree, dots) || samePoints(tree, dots2)), 2, "not traversing correctly at depth = 2");
        }
        catch (NullPointerException e) {
            points += AutogradeCommon.displayMessage( false, 1, ""+e);
            points -= 1;
        }
        catch (IndexOutOfBoundsException e) {
            points += AutogradeCommon.displayMessage( false, 0.5, ""+e);
            points -= 0.5;
        }
        catch (Exception e) {
            System.err.println("EXCEPTION " + e);
        }
        System.out.println(points +"/5\n");
        return Math.max(points, 0);
    }

    public static void main(String[]args) {
        testAllPoints();
    }
}
