import java.util.ArrayList;

/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class TestGraphs {
    /**
     * @return an isolated graph with no edges
     */
    public static Graph<String, String> isolatedGraph() {
        Graph<String, String> graph = new AdjacencyMapGraph<>();
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");
        return graph;
    }

    /**
     * @return a complete graph with every possible edge
     */
    public static Graph<String, String> completeGraph() {
        Graph<String, String> graph = isolatedGraph();
        String[]vertices = {"A", "B", "C", "D", "E", "F"};

        for (int index1 = 0; index1 < vertices.length; index1++) {
            for (int index2 = index1+1; index2 < vertices.length; index2++) {
                graph.insertUndirected(vertices[index1], vertices[index2], "");
            }
        }
        return graph;
    }

    /**
     * @return a graph with two fully connected cliques disjoint from each other
     */
    public static Graph<String, String> cliqueGraph() {
        Graph<String, String> graph = isolatedGraph();
        String[]vertices = {"A", "B", "C", "D", "E", "F"};
        graph.insertUndirected("A", "B", "");
        graph.insertUndirected("B", "C", "");
        graph.insertUndirected("C", "A", "");

        graph.insertUndirected("D", "E", "");
        graph.insertUndirected("E", "F", "");
        graph.insertUndirected("F", "D", "");
        return graph;
    }

    /**
     * @return a graph with vertex A connected to all other vertices and no other edges
     */
    public static Graph<String, String> aCenterGraph() {
        Graph<String, String> graph = isolatedGraph();
        String[]vertices = {"A", "B", "C", "D", "E", "F"};

        for (int index = 1; index < vertices.length; index++) {
            graph.insertUndirected(vertices[0], vertices[index], "");
        }
        return graph;
    }

    /**
     * @return a graph with vertex A connected to B, B to C, ... E to F (with F and A not directly connected)
     */
    public static Graph<String, String> treeGraph() {
        Graph<String, String> graph = isolatedGraph();
        String[]vertices = {"A", "B", "C", "D", "E", "F"};

        for (int index = 0; index < vertices.length-1; index++) {
            graph.insertUndirected(vertices[index], vertices[index+1], "");
        }
        return graph;
    }

    /**
     * @return an ArrayList of test graphs defined above
     */
    public static ArrayList<Graph<String, String>> testGraphs() {
        ArrayList<Graph<String, String>> graphs = new ArrayList<>();
        graphs.add(completeGraph());
        graphs.add(aCenterGraph());
        graphs.add(cliqueGraph());
        graphs.add(treeGraph());
        graphs.add(isolatedGraph());
        return graphs;
    }
}
