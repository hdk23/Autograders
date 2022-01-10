import java.util.ArrayList;
import java.util.Set;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestMissingVertices {
    /**
     * tests the missingVertices method in the GraphLib class
     * @return the number of points earned for the missingVertices method
     */
    public static double testMissingVertices() {
        System.out.println("TESTING MISSING VERTICES");
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
                Set<String> solutionSet = GraphLib.missingVertices(graph, bfsGraph);
                Set<String> studentSet = BaconGraph.missingVertices(graph, bfsGraph);
                try {
                    if (solutionSet == null || solutionSet.size() == 0) {
                        matches = (studentSet == null || studentSet.size() == 0);
                    }
                    else {
                        matches = (solutionSet.size() == studentSet.size() && studentSet.containsAll(solutionSet));
                    }
                }
                catch (NullPointerException e) {
                    if (!nullCaught) { // only penalize once——avoid double jeopardy
                        points += AutogradeCommon.displayMessage(true, -1, "having a NullPointerException");
                    }
                    nullCaught = true;
                }
            }
            points += AutogradeCommon.displayMessage(matches, 1, "not correctly returning paths for a(n) " + testCases[index]);
            index++;
        }
        System.out.println(points + "/5");
        return points;
    }

    public static void main(String[]args) {
        testMissingVertices();
    }
}
