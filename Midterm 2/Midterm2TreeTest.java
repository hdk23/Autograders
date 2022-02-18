import java.util.ArrayList;

/**
 * @author Henry Kim
 */
public class Midterm2TreeTest {

    /**
     * @param num the value to store in the binary tree
     * @return a tree with the parameter as the root
     */
    public static BinaryTree<Integer> numTree(int num) {
        return new BinaryTree<Integer>(num);
    }


    /**
     * @param num the type of binary tree to initialize (2: root with left, 3: root with right: 4: root with left and right)
     * @return a tree with the corresponding left and right subtrees
     */
    public static BinaryTree<Integer> initializeTree(int num) {
        if (num == 2) {
            return new BinaryTree<>(0, numTree(1), null);
        } else if (num == 3) {
            return new BinaryTree<>(0, null, numTree(2));
        } else if (num == 4) {
            return new BinaryTree<>(0, numTree(1), numTree(2));
        } else {
            return null;
        }
    }

    /**
     * inserts a value into a tree (specifically for trees of a height of 3 with nodes 0-6
     * @param tree to insert the value
     * @param num the value to insert
     */
    public static void insertTree(BinaryTree<Integer> tree, int num) {
        if (num == 3) {
            tree.getLeft().setLeft(numTree(3));
        } else if (num == 4) {
            tree.getLeft().setRight(numTree(4));
        } else if (num == 5) {
            tree.getRight().setLeft(numTree(5));
        } else if (num == 6) {
            tree.getRight().setRight(numTree(6));
        }
    }

    /**
     * creates trees for the test cases
     * @param trees a list of trees to store the test cases
     * @param treeType the type of tree to create for each test case
     * @param values the values to insert into the tree
     */
    public static void createTree(ArrayList<BinaryTree<Integer>> trees, int treeType, int[]values) {
        BinaryTree<Integer> tree = initializeTree(treeType);
        for (Integer i: values) {
            insertTree(tree, i);
        }
        trees.add(tree);
    }


    /**
     * @return the test case trees in a list
     * all possible trees of heights 0-3 included
     * 1 null tree
     * 1 tree of height 1
     * 3 trees of height 2
     * 21 trees of height 3
     */
    public static ArrayList<BinaryTree<Integer>> getTestCases() {
        ArrayList<BinaryTree<Integer>> trees = new ArrayList<>();

        // test cases 0-4: heights 0-2 trees
        trees.add(null);
        trees.add(new BinaryTree<>(0));
        for (int num = 2; num <= 4; num++) {
            createTree(trees, num, new int[]{});
        }

        // test cases 5-8: height 3 trees with one node in each level
        createTree(trees, 2,  new int[]{3});  // test case 5
        createTree(trees, 2,  new int[]{4});  // test case 6
        createTree(trees, 3,  new int[]{5});  // test case 7
        createTree(trees, 3,  new int[]{6});  // test case 8

        // test cases 9-12: height 3 trees with levels 0-1 full and 1 node in level 2
        createTree(trees, 4,  new int[]{3});  // test case 9
        createTree(trees, 4,  new int[]{4});  // test case 10
        createTree(trees, 4,  new int[]{5});  // test case 11
        createTree(trees, 4,  new int[]{6});  // test case 12

        // test cases 13-14: height 3 trees with 1 node in level 1, 2 nodes in level 2
        createTree(trees, 2,  new int[]{3, 4});  // test case 13
        createTree(trees, 3,  new int[]{5, 6});  // test case 14

        // test cases 15-20: height 3 trees with levels 0-1 full and 2 nodes in level 2
        createTree(trees, 4,  new int[]{3, 4});  // test case 15
        createTree(trees, 4,  new int[]{3, 5});  // test case 16
        createTree(trees, 4,  new int[]{3, 6});  // test case 17
        createTree(trees, 4,  new int[]{4, 5});  // test case 18
        createTree(trees, 4,  new int[]{4, 6});  // test case 19
        createTree(trees, 4,  new int[]{5, 6});  // test case 20

        // test cases 21-24: height 3 trees with levels 0-1 full and 3 nodes in level 2
        createTree(trees, 4,  new int[]{3, 4, 5});  // test case 21
        createTree(trees, 4,  new int[]{3, 4, 6});  // test case 22
        createTree(trees, 4,  new int[]{3, 5, 6});  // test case 23
        createTree(trees, 4,  new int[]{4, 5, 6});  // test case 24

        // test case 25: full binary tree of height 3
        createTree(trees, 4,  new int[]{3, 4, 5, 6});  // test case 25

        return trees;
    }

    /**
     * @return the expected test results for each test case
     * hard-coded for exam security
     */
    public static ArrayList<Boolean> getExpectedResults() {
        ArrayList<Boolean> expectedResults = new ArrayList<>();
        expectedResults.add(false);  // test case 0
        expectedResults.add(true);  // test case 1
        expectedResults.add(true);  // test case 2
        expectedResults.add(false);  // test case 3
        expectedResults.add(true);  // test case 4

        expectedResults.add(false);  // test case 5
        expectedResults.add(false);  // test case 6
        expectedResults.add(false);  // test case 7
        expectedResults.add(false);  // test case 8

        expectedResults.add(true);  // test case 9
        expectedResults.add(false);  // test case 10
        expectedResults.add(false);  // test case 11
        expectedResults.add(false);  // test case 12

        expectedResults.add(false);  // test case 13
        expectedResults.add(false);  // test case 14

        expectedResults.add(true);  // test case 15
        expectedResults.add(false);  // test case 16
        expectedResults.add(false);  // test case 17
        expectedResults.add(false);  // test case 18
        expectedResults.add(false);  // test case 19
        expectedResults.add(false);  // test case 20

        expectedResults.add(true);  // test case 21
        expectedResults.add(false);  // test case 22
        expectedResults.add(false);  // test case 23
        expectedResults.add(false);  // test case 24

        expectedResults.add(true);  // test case 25
        return expectedResults;
    }

    public static void main(String[] args) {
        getTestCases();
    }
}
