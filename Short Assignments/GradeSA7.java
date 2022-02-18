import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * autogrades short assignment 7 in approximately 0.1 seconds
 * make sure you have the student copy GraphLib and the solution copy GraphLib2
 * @author Henry Kim, Dartmouth CS 10 TA, Winter 2022
 */

public class GradeSA7 {
    public static void main(String[]args) {
        long start = System.currentTimeMillis();
        double score = 0.0;

        Graph<String, String> relationships = new AdjacencyMapGraph<String, String>();

        relationships.insertVertex("A");
        relationships.insertVertex("B");
        relationships.insertVertex("C");
        relationships.insertVertex("D");
        relationships.insertVertex("E");

        relationships.insertUndirected("A","B","");
        relationships.insertUndirected("A","C","");
        relationships.insertDirected("A","D","");
        relationships.insertDirected("A","E","");
        relationships.insertUndirected("B","C","");
        relationships.insertDirected("C","D","");
        relationships.insertDirected("E","B","");
        relationships.insertDirected("E","C","");
        System.out.println("The graph:");
        System.out.println(relationships);

        List<String> list;
        // test walk from same vertex——should be mostly different from each other
        HashMap<String, Integer> endVertices = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            String key = GraphLib.randomWalk(relationships,  "A", 1).get(1);
            if (!endVertices.containsKey(key))
                endVertices.put(key, 1);
            else
                endVertices.put(key, endVertices.get(key)+1);
        }
        score += AutogradeCommon.displayMessage(endVertices.entrySet().size() == 4, 0.5, "not randomly walking on the same vertex each iteration");


        boolean validPath = true;
        List<String> path;
        do {
            path = GraphLib.randomWalk(relationships, "A",10);
        } while (path.size() > relationships.numVertices());

//        path = new ArrayList<>();
        for (int i = 1; i < path.size(); i++) {
            if (!relationships.hasEdge(path.get(i-1), path.get(i))) {
                validPath = false;
                break;
            }
        }

        score += AutogradeCommon.displayMessage(validPath, 0.5, "not producing a valid path for a path with more steps than the number of vertices");

        score += AutogradeCommon.displayMessage(
                        GraphLib.randomWalk(relationships,  "F",1).size() == 0, 0.5,
                "not returning null or an empty list when a walk is attempted on a nonexistent vertex");
        System.out.println();


        // test on new graph
        relationships = new AdjacencyMapGraph<String, String>();

        String[] possible = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for (String s: possible) {
            relationships.insertVertex(s);
        }

        for (int i = 0; i < 52; i++) {
            int begin = (int) (Math.random() * possible.length);
            int end = (int) (Math.random() * possible.length);
            relationships.insertUndirected(possible[begin],possible[end],"");
        }
//        System.out.println(relationships);

        List<String> orderedVertices = GraphLib.verticesByInDegree(relationships);  // from student's work
        List<String> orderedVertices2 = GraphLib2.verticesByInDegree(relationships);  // from TA solution

        score += AutogradeCommon.displayMessage(orderedVertices.equals(orderedVertices2), 0.5, "not sorting vertices by indegree correctly");

        System.out.println(score +"/2");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + " ms to grade SA-7");
        System.exit(0);
    }
}
