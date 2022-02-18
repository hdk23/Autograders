import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Midterm2Tree {

   /**
    * @param tree the tree to identify whether it is complete
    * @return whether a tree is "complete"
    */
   public static boolean isCompleteTree(BinaryTree tree) {
      // solution omitted due to exam security
      return false;
   }

   public static void main(String[] args) throws IOException {
      ArrayList<BinaryTree<Integer>> trees = Midterm2TreeTest.getTestCases();
      ArrayList<Boolean> expectedResults = Midterm2TreeTest.getExpectedResults();
      String name = "TEST"; // TODO: enter student's name
      boolean isStatic = true;

      long start = System.currentTimeMillis();
      // name files by starting with time executed
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
      LocalDateTime now = LocalDateTime.now();
      try {
         // write to a text file instead of printing to stdout
         PrintStream fileOut = new PrintStream("Midterm 2/Midterm 2 Part 4 "+ dtf.format(now) + " " + name + ".txt");
         System.setOut(fileOut);
      }
      catch (Exception e) {

      }
      // append results to an aggregate csv file for future analysis
      BufferedWriter b = new BufferedWriter(new FileWriter("Midterm 2/midterm2part4grades.csv", true));
      b.write(dtf.format(now)+",");
      b.write(name + ",");
      System.out.println("Midterm 2 Part 4 Grading");
      System.out.print("NAME: ");
      System.out.println(name+"\n");
      double total = 16; double points; int passed = 0;
      if (!isStatic) {
         total -= 0.5;
         System.out.println("-0.5 for not implementing a static method");
      }

      // run all test cases
      for (int i = 0; i < expectedResults.size(); i++) {
         boolean correct = true;
         if (i < 5)  // weight of test case
            points = 1;
         else
            points = 0.5;
         try {
            if (expectedResults.get(i) != isCompleteTree(trees.get(i))){
               correct = false;
               System.out.println("-" + points + " Test Case " + i + " Failed: Expected " + expectedResults.get(i) + " Result: " + isCompleteTree(trees.get(i)));
            }
         } catch (Exception e) {
            correct = false;
            System.out.println("-" + points + " Test Case " + i + " Failed: Expected " + expectedResults.get(i) + " Result: " + e.getMessage());
         } finally {
            if (correct) {
               passed++;
            } else {
               total -= points;
            }
         }
      }
      System.out.println(passed + " out of " + trees.size() + " test cases passed.");
      System.out.println("TOTAL: " + total + "/16");
      b.write( total+"\n");
      b.close();
      long finish = System.currentTimeMillis();
      long timeElapsed = finish - start;

      System.out.println(timeElapsed + " ms to run all test cases.");
   }
}
