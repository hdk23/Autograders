
/**
 * @author Henry Kim. Dartmouth CS 10 TA, Fall 2021
 */
public class AutogradePS4 {
    /**
     * method that runs all test methods
     * @param term you're grading the assignment (e.g. 21F, 22W)
     * @param folderName from Canvas (e.g. kimhenry for Henry Kim)
     * @param name student's name
     * @return the number of points earned by the autograded sections (out of 40)
     */
    public static double gradePS4(String term, String folderName, String name) {
        long start = System.currentTimeMillis();
        try {
//            PrintStream fileOut = new PrintStream("Grading/"+term+"/PS/PS-4/"+ folderName +"/PS-4 Scoring Sheet " + name + ".txt");
//            System.setOut(fileOut);
        }
        catch (Exception e) {

        }
        System.out.println("Problem Set 4 Autograder");
        System.out.print("NAME: ");
        System.out.println(name);
        double score = 0;
        score += TestBFS.testBFS();
        score += TestGetPath.testGetPath();
        score += TestMissingVertices.testMissingVertices();
        score += TestAverageSeparation.testAverageSeparation();
        System.out.println("TOTAL: " + score + "/40");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + " ms to grade these parts");
        System.out.println("TIME TO EXAMINE THE REST");
        return score;
    }

    public static void main(String[]args) {
        gradePS4("22W","kimhenry", "Henry Kim");
    }
}