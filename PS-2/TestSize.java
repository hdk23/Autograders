/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestSize {
    /**
     * tests the student's PointQuadtree class's size method
     * 5 points total
     * +1 point for checking whether the children are null
     * +1 point for initializing the size to 1 (to count the root, incorrect if initialized to 0)
     * +2 points for adding each child's size (non-recursive, 0.5 point per quadrant)
     * +1 point for handling recursive cases
     * @return the number of points earned from this section
     */
    public static double testSize() {
        double points = 0;
        PointQuadtree<Dot> tree = AutogradePS2.tree;
        System.out.println("TESTING SIZE");
        int expectedSize = 0;

        // 1 point for children null check
        boolean nullChecked = true;
        try { tree.size(); }
        catch (NullPointerException e) { nullChecked = false; }
        finally {
            points += AutogradeCommon.displayMessage(nullChecked, 1, "not checking whether the children are null");
        }

        // 1 point for initializing the size to 1 (and returning something)
        try {
            double pointsEarned = AutogradeCommon.displayMessage(tree.size() == 1, 1, "counting the root node");
            if (pointsEarned != 0)
                expectedSize = 1;
            points += pointsEarned;
        }
        catch (NullPointerException e) { } // to prevent triggers from the previous NullPointerException

        // 2 points for adding each child's size (non-recursive), 0.5 point for each quadrant
        try {
            for (int index = 0; index < AutogradePS2.quadrantCenters.length; index++) {
                tree.insert(new Dot(AutogradePS2.quadrantCenters[index][0], AutogradePS2.quadrantCenters[index][1]));
                expectedSize++;
                double pointsEarned = AutogradeCommon.displayMessage(tree.size() == expectedSize, 0.5, "not counting points in quadrant " + (index+1));
                if (pointsEarned == 0)
                    expectedSize--;  // avoid double jeopardy from mistakes carrying from previous quadrant
                points += pointsEarned;
            }
        }
        catch (NullPointerException e) { }

        // 1 point for handling multiple levels
        boolean works = true;
        try {
            for (int[] center: AutogradePS2.quadrantCenters) {
                AutogradePS2.addPoints(tree, center);
            }

            for (int quadrant = 1; quadrant <= 4; quadrant++)
                works = works && (tree.getChild(quadrant).size() == 5); // prevent double jeopardy from previous part

            points += AutogradeCommon.displayMessage( works, 1, "not handling sizes of trees with multiple levels");

        }
        catch (NullPointerException e) { }
        System.out.println(points +"/5\n");
        return points;
    }

    public static void main(String[]args) {
        testSize();
    }
}
