import java.util.ArrayList;
import java.util.List;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestGetPath {
    /**
     * tests the getPath method in the GraphLib class
     * @return the number of points earned for the getPath method
     */
    public static double testGetPath() {
        System.out.println("TESTING GET PATH");
        double points = 0.0;
        int index = 0;
        boolean matches;
        boolean nullCaught = false;
        Graph<String, String> bfsGraph;
        String[] testCases = {"complete graph", "central graph", "tree graph", "cliques graph", "isolated graph"};
        ArrayList<Graph<String, String>> testGraphs = TestGraphs.testGraphs();
        for (Graph<String, String> graph: testGraphs) {
            matches = true;
            for (String vertex: graph.vertices())  {
                bfsGraph = GraphLib.bfs(graph, vertex);
                for (String bfsVertex: bfsGraph.vertices()) {
                    List<String> solutionList = GraphLib.getPath(bfsGraph, bfsVertex);
                    List<String> studentList = BaconGraph.getPath(bfsGraph, bfsVertex);
                    try {
                        if (solutionList == null || solutionList.size() == 0) {
                            matches = matches && (studentList == null || studentList.size() == 0);
                        }
                        else {
                            matches = matches && solutionList.size() == studentList.size() && studentList.containsAll(solutionList);
                        }
                    }
                    catch (NullPointerException e) {
                        if (!nullCaught) {  // only penalize once——avoid double jeopardy
                            points += AutogradeCommon.displayMessage(false, 1, "having a NullPointerException");
                            points -= 1;
                            nullCaught = true;
                        }
                    }
                }
            }
            points += AutogradeCommon.displayMessage(matches, 2, "not correctly returning paths for a(n) " + testCases[index]);
            index++;
        }
        System.out.println(points+"/10");
        return points;
    }

    public static void main(String[]args) {
        testGetPath();
    }
}
