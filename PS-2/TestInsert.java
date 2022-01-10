
/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestInsert {
    /**
     * @param testQuad quadrant to test
     * @return whether each dot inserts correctly for the given quadrant
     */
    public static boolean quadWorks(int testQuad) {
        for (int quadrant = 1; quadrant <= 4; quadrant++) {
            if (!AutogradePS2.tree.getChild(testQuad).hasChild(quadrant)) {
                return false;
            }
        }
        return true;
    }

    /**
     * tests the student's PointQuadtree class's insert method
     * 10 points total
     * +4 points (1 point each) for setting each child's boundaries and inserting the initial children correctly
     * +4 points (1 point each) for inserting into each child's quadrants correctly
     * +2 points (0.5 point each) for handling edge cases between quadrants: insert in either quadrant but not both
     * @return the number of points earned from this section
     */
    public static double testInsert() {
        double points = 0;
        PointQuadtree<Dot> tree = AutogradePS2.tree;
        System.out.println("TESTING INSERT");
        try {
            // setting each child's boundaries
            for (int[] center: AutogradePS2.quadrantCenters) {
                tree.insert(new Dot(center[0], center[1]));
            }

            int[][] coordValues = {
                {(int) tree.getPoint().getX(), tree.getY1(), tree.getX2(), (int) tree.getPoint().getY()},
                {tree.getX1(), tree.getY1(), (int) tree.getPoint().getX(), (int) tree.getPoint().getY()},
                {tree.getX1(), (int) tree.getPoint().getY(), (int) tree.getPoint().getX(), tree.getY2()},
                {(int) tree.getPoint().getX(), (int) tree.getPoint().getY(), tree.getX2(), tree.getY2()}
            };

            for (int quad = 1; quad <= 4; quad++) {
                PointQuadtree<Dot> child = tree.getChild(quad);
                int[] childCoords = {child.getX1(), child.getY1(), child.getX2(), child.getY2()};
                String[]coords = {"X1", "Y1", "X2", "Y2"};
                for (int index = 0; index < 4; index++) {
                    points += AutogradeCommon.displayMessage(childCoords[index] == coordValues[quad-1][index], 0.25,
                            "not setting quadrant " + quad +"'s " + coords[index] + " value correctly");
                }
            }

            // insert points into the tree quadrant by quadrant
            int quad = 1;
            for (int[] center: AutogradePS2.quadrantCenters) {
                tree.insert(new Dot(center[0], center[1]));  // center
                AutogradePS2.addPoints(tree, center);
                points += AutogradeCommon.displayMessage(quadWorks(quad), 1, "not inserting into quadrant " + quad++ + "'s children correctly");
            }

            // Edge Cases
            int[][] edgeValues = {{400, 150}, {200, 300}, {400, 450}, {600, 300}};
            for (int compQuad = 1; compQuad <= 4; compQuad++) {
                tree = new PointQuadtree<>(new Dot(400, 300), 0, 0, 800, 600);

                tree.insert(new Dot(edgeValues[compQuad-1][0], edgeValues[compQuad-1][1]));
                points += AutogradeCommon.displayMessage(tree.hasChild(compQuad) ^ tree.hasChild(compQuad%4+1) , 0.5,
                        "not handling the edge case between quadrants " + compQuad + " and " + (compQuad%4+1));
            }
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

        System.out.println(points +"/10\n");
        return points;
    }

    public static void main(String[]args) {
        testInsert();
    }
}
