import java.util.ArrayList;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestBFS {
    /**
     * tests BFS on a tree graph (described in README.md)
     * @return the number of points earned for the test case (0 or 3)
     */
    public static double testComplete() {
        Graph<String, String> graph;
        Graph<String, String> bfsGraph;
        boolean expectedResults = true;
        graph = TestGraphs.completeGraph();

        for (String vertex: graph.vertices())  {
            bfsGraph = BaconGraph.bfs(graph, vertex);
            expectedResults = bfsGraph.numEdges() == 5 && bfsGraph.outDegree(vertex) == 0;
            if (!expectedResults)
                break;
        }
        return AutogradeCommon.displayMessage(expectedResults, 3, "not having a correct bfs on a complete graph");
    }

    /**
     * tests BFS on an isolated graph (described in README.md)
     * @return the number of points earned for the test case (0 or 3)
     */
    public static double testIsolated() {
        Graph<String, String> graph;
        Graph<String, String> bfsGraph;
        boolean expectedResults = true;
        graph = TestGraphs.isolatedGraph();

        for (String vertex: graph.vertices())  {
            bfsGraph = BaconGraph.bfs(graph, vertex);
            expectedResults = bfsGraph.numEdges() == 0;
            if (!expectedResults)
                break;
        }
        return AutogradeCommon.displayMessage(expectedResults, 3, "not having a correct bfs on an isolated graph");
    }

    /**
     * tests BFS on a central graph (described in README.md)
     * @return the number of points earned for the test case (0 or 3)
     */
    public static double testCentral() {
        Graph<String, String> graph;
        Graph<String, String> bfsGraph;
        boolean expectedResults = true;

        graph = TestGraphs.aCenterGraph();
        for (String vertex: graph.vertices())  {
            bfsGraph = BaconGraph.bfs(graph, vertex);
            for (String bfsVertex: bfsGraph.vertices()) {
                if (bfsVertex.equals(vertex))
                    expectedResults = expectedResults && bfsGraph.outDegree(bfsVertex) == 0;
                else if (bfsVertex.equals("A")) {
                    expectedResults = expectedResults && bfsGraph.hasEdge("A", vertex);
                }
                else {
                    expectedResults = expectedResults && bfsGraph.hasEdge(bfsVertex, "A");
                }
            }
            if (!expectedResults)
                break;
        }
        return AutogradeCommon.displayMessage(expectedResults, 3, "not having a correct bfs on a graph with one central vertex");
    }

    /**
     * tests BFS on a clique graph (described in README.md)
     * @return the number of points earned for the test case (0 or 3)
     */
    public static double testCliques() {
        Graph<String, String> graph;
        Graph<String, String> bfsGraph;
        boolean expectedResults = true;
        graph = TestGraphs.cliqueGraph();
        String[][] cliques = {{"A", "B", "C"}, {"D", "E", "F"}};
        for (String[] clique: cliques)  {
            for (String vertex: clique) {
                bfsGraph = BaconGraph.bfs(graph, vertex);
                expectedResults = expectedResults && bfsGraph.numVertices() == 3;

                for (String v: clique) {
                    expectedResults = expectedResults && bfsGraph.hasVertex(v);
                }

                for (String bfsVertex: bfsGraph.vertices()) {
                    if (bfsVertex.equals(vertex))
                        expectedResults = expectedResults && bfsGraph.outDegree(bfsVertex) == 0;
                    else {
                        expectedResults = expectedResults && bfsGraph.hasEdge(bfsVertex, vertex);
                    }
                }
            }

            if (!expectedResults)
                break;
        }
        return AutogradeCommon.displayMessage(expectedResults, 3, "not having a correct bfs on a graph with two disjoint cliques");
    }

    /**
     * tests BFS on a tree graph (described in README.md)
     * @return the number of points earned for the test case (0 or 3)
     */
    public static double testTree() {
        Graph<String, String> graph;
        Graph<String, String> bfsGraph;
        boolean expectedResults = true;

        graph = TestGraphs.treeGraph();
        String[]vertices = {"A", "B", "C", "D", "E", "F"};

        for (int index = 0; index < vertices.length; index++) {
            bfsGraph = BaconGraph.bfs(graph, vertices[index]);
            expectedResults = expectedResults && bfsGraph.numEdges() == 5;
            expectedResults = expectedResults && bfsGraph.outDegree(vertices[index]) == 0;

            for (int index2 = 0; index2 < vertices.length; index2++) {
                if (vertices[index].equals(vertices[index2])) {
                    expectedResults = expectedResults && bfsGraph.outDegree(vertices[index]) == 0;}
                else if (index - index2 == 1 || index2 - index == 1) {
                    expectedResults = expectedResults && bfsGraph.hasEdge(vertices[index2], vertices[index]) ;
                }
            }

            if (!expectedResults)
                break;
        }
        return AutogradeCommon.displayMessage(expectedResults, 3, "not having a correct bfs on a tree graph A-B-C-D-E-F");
    }

    /**
     * tests the bfs method in the GraphLib class
     * @return the number of points earned for the bfs method
     */
    public static double testBFS() {
        System.out.println("TESTING BFS");
        double points = 0.0;
        points += testComplete();  // testing a complete graph
        points += testIsolated();  // testing an isolated graph
        points += testCentral();  // testing a graph with a central vertex
        points += testCliques();  // testing a graph with two cliques
        points += testTree();  // testing a tree graph
        System.out.println(points + "/15");
        return points;
    }

    public static void main(String[]args) {
        testBFS();
    }
}
