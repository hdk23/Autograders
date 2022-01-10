/**
 * autogrades short assignment 4 in approximately 0.1 seconds
 * should add getters for head, tail, and size before running the autograder
 * @author Henry Kim, Dartmouth CS 10 TA, Winter 2022
 */

public class GradeSA4 {
    public static double testAdd() {
        double points = 0.0;
        try {
            SinglyLinkedHT<String> list1 = new SinglyLinkedHT<>();
            list1.add(0, "a");
            points += AutogradeCommon.displayMessage(list1.getHead().getData().equals("a") &&
                    list1.getTail().getData().equals("a") && list1.getSize() == 1, 0.1, "not adding at index 0 to a list correctly");

            list1.add(0, "c");
            list1.add(1, "b");
            points += AutogradeCommon.displayMessage(list1.getHead().getData().equals("c") &&
                    list1.getTail().getData().equals("a") && list1.getSize() == 3, 0.1, "not adding at a nonzero index to a list correctly");
        }
        catch (Exception e) {
            System.err.println(""+e);
        }

        return points;
    }

    public static double testRemove() {
        double points = 0.0;
        SinglyLinkedHT<String> list1 = new SinglyLinkedHT<>();
        try {
            list1.add(0, "a");
            list1.add(0, "b");

            list1.remove(1);
            points += AutogradeCommon.displayMessage(list1.getHead().getData().equals("b") &&
                    list1.getTail().getData().equals("b") && list1.getSize() == 1, 0.1, "not removing a nonzero element correctly");

            list1.remove(0);
            points += AutogradeCommon.displayMessage(list1.getHead() == null &&
                    list1.getTail() == null && list1.getSize() == 0, 0.1, "not removing a nonzero element correctly");
        }
        catch (Exception e) {
            System.err.println(""+e);
        }

        return points;
    }

    public static double testAppend() {
        double points = 0.0;
        SinglyLinkedHT<String> list1 = new SinglyLinkedHT<>();
        SinglyLinkedHT<String> list2 = new SinglyLinkedHT<>();

        try {
            list2.add(0, "a");
            list2.add(0, "b");
            list2.add(0, "c");
            list1.append(list2);
            points += AutogradeCommon.displayMessage(list1.getHead().getData().equals("c") &&
                    list1.getTail().getData().equals("a") && list1.getSize() == 3, 0.2, "not appending a nonempty list to an empty list correctly");

            // append empty list to nonempty list
            list1 = new SinglyLinkedHT<>();
            list2.append(list1);
            points += AutogradeCommon.displayMessage(list2.getHead().getData().equals("c") &&
                    list2.getTail().getData().equals("a") && list2.getSize() == 3, 0.2, "not appending an empty list to a nonempty list correctly");

            // append nonempty list to nonempty list
            list1 = new SinglyLinkedHT<>();
            list1.add(0, "x");
            list1.add(0, "y");
            list1.add(0, "z");

            list1.append(list2);
            points += AutogradeCommon.displayMessage(list1.getHead().getData().equals("z") &&
                    list1.getTail().getData().equals("a") && list1.getSize() == 6, 0.2, "not appending a nonempty list to another nonempty list correctly");

        } catch (Exception e) {
            System.err.println(""+e);
        }
        return points;
    }


    public static void main(String[]args) {
        long start = System.currentTimeMillis();
        double score = 0.0;


        score += testAdd();
        score += testRemove();
        score += testAppend();

        System.out.println(score +"/1");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + " ms to grade SA-4");
        System.exit(0);
    }
}
