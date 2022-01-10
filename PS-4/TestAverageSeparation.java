import java.util.ArrayList;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestAverageSeparation {
    /**
     * tests the averageSeparation method in the GraphLib class
     * @return the number of points earned for the averageSeparation method
     */
    public static double testAverageSeparation() {
        System.out.println("TESTING AVERAGE SEPARATION");
        double points = 0.0;
        int index = 0;
        boolean matches;
        Graph<String, String> bfsGraph;
        String[] testCases = {"complete graph", "central graph", "tree graph", "cliques graph", "isolated graph"};
        ArrayList<Graph<String, String>> testGraphs = TestGraphs.testGraphs();
        for (Graph<String, String> graph: testGraphs) {
            matches = true;
            for (String vertex: graph.vertices())  {
                bfsGraph = GraphLib.bfs(graph, vertex);
                for (String bfsVertex: bfsGraph.vertices()) {
                    matches = matches && (GraphLib.averageSeparation(bfsGraph, bfsVertex) == BaconGraph.averageSeparation(bfsGraph, bfsVertex) ||
                            GraphLib.averageSeparation(bfsGraph, bfsVertex) == BaconGraph.averageSeparationV2(bfsGraph, bfsVertex));
                }
            }
            points += AutogradeCommon.displayMessage(matches, 2, "not correctly calculating average separation for a " + testCases[index]);
            index++;
        }
        System.out.println(points+"/10");
        return points;
    }
    public static void main(String[]args) {
        testAverageSeparation();
    }
}
