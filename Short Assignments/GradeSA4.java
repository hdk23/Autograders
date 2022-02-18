/**
 * autogrades short assignment 4 in approximately 0.1 seconds
 * should add getters for head, tail, and size before running the autograder
 * @author Henry Kim, Dartmouth CS 10 TA, Winter 2022
 */

public class GradeSA4 {
    public static double testAdd() {
        double points = 0.0;
        System.out.println("TESTING ADD");
        SinglyLinkedHT<String> list1 = new SinglyLinkedHT<>();
        try {
            System.out.println("ADDING A ZERO INDEX ELEMENT\t");
            list1.add(0, "a");

            points += AutogradeCommon.displayMessage(list1.getHead().getData().equals("a"), 0.05, "incorrectly updating the head when adding at index 0");
            points += AutogradeCommon.displayMessage(list1.getTail().getData().equals("a"), 0.05, "incorrectly updating the tail when adding at index 0");
            points += AutogradeCommon.displayMessage(list1.getSize() == 1, 0.05, "incorrectly updating the size when adding at index 0");

        }
        catch (NullPointerException e) {
            System.out.println("NullPointerException when adding at index 0");
        } catch (Exception e) {
            System.out.println("Exception other than NullPointer caught in add: " + e);
        }

        try {
            System.out.println("\nADDING A NONZERO INDEX ELEMENT");
            list1.add(0, "c");
            list1.add(1, "b");

            points += AutogradeCommon.displayMessage(list1.getHead().getData().equals("c"), 0.05, "incorrectly updating the head when adding at index > 0");
            points += AutogradeCommon.displayMessage(list1.getTail().getData().equals("a"), 0.05, "incorrectly updating the tail when adding at index > 0");
            points += AutogradeCommon.displayMessage(list1.getSize() == 3, 0.05, "incorrectly updating the size when adding at index > 0");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException when adding at index > 0");
        } catch (Exception e) {
            System.out.println("Exception other than NullPointer caught in add: " + e);
        }

        if (points == 0.3)
            System.out.println("No errors in the add method :)");
        System.out.println("\n");
        return points;
    }

    public static double testRemove() {
        double points = 0.0;
        System.out.println("TESTING REMOVE");
        SinglyLinkedHT<String> list1 = new SinglyLinkedHT<>();
        try {
            System.out.println("REMOVING A NONZERO INDEX ELEMENT");
            list1.add(0, "a");
            list1.add(0, "b");

            list1.remove(1);
            points += AutogradeCommon.displayMessage(list1.getHead().getData().equals("b"), 0.05, "incorrectly updating the head when removing at index > 0");
            points += AutogradeCommon.displayMessage(list1.getTail().getData().equals("b"), 0.05, "incorrectly updating the tail when removing at index > 0");
            points += AutogradeCommon.displayMessage(list1.getSize() == 1, 0.05, "incorrectly updating the size when removing at index > 0");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException when removing a nonzero index element");
        } catch (Exception e) {
            System.out.println("Exception other than NullPointer caught when removing a nonzero index element: " + e);
        }

        try {
            System.out.println("\nREMOVING A ZERO INDEX ELEMENT");
            list1.remove(0);
            points += AutogradeCommon.displayMessage(list1.getHead() == null, 0.05, "incorrectly updating the head when removing at index 0");
            points += AutogradeCommon.displayMessage(list1.getTail() == null, 0.05, "incorrectly updating the tail when removing at index 0");
            points += AutogradeCommon.displayMessage(list1.getSize() == 0, 0.05, "incorrectly updating the size when removing at index 0");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException when removing a zero index element");
        } catch (Exception e) {
            System.out.println("Exception other than NullPointer caught when removing a zero index element: " + e);
        }
        if (points == 0.3)
            System.out.println("No errors in the remove method :)");
        System.out.println("\n");
        return points;
    }

    public static double testAppend() {
        double points = 0.0;
        System.out.println("TESTING APPEND");
        SinglyLinkedHT<String> list1 = new SinglyLinkedHT<>();
        SinglyLinkedHT<String> list2 = new SinglyLinkedHT<>();

        try {
            System.out.println("APPENDING A NONEMPTY LIST TO AN EMPTY LIST");
            list2.add(0, "a");
            list2.add(0, "b");
            list2.add(0, "c");
            list1.append(list2);

            points += AutogradeCommon.displayMessage(list1.getHead().getData().equals("c"), 0.05, "incorrectly updating the head when appending a nonempty list to an empty list");
            points += AutogradeCommon.displayMessage(list1.getTail().getData().equals("a"), 0.05, "incorrectly updating the tail when appending a nonempty list to an empty list");
            points += AutogradeCommon.displayMessage(list1.getSize() == 3, 0.05, "incorrectly updating the size when adding appending a nonempty list to an empty list");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException when appending a nonempty list to an empty list");
        } catch (Exception e) {
            System.out.println("Exception other than NullPointer caught in append: " + e);
        }

        list1 = new SinglyLinkedHT<>();
        try {
            System.out.println("\nAPPENDING AN EMPTY LIST TO A NONEMPTY LIST");
            list2.append(list1);

            points += AutogradeCommon.displayMessage(list2.getHead().getData().equals("c"), 0.05, "incorrectly updating the head when appending an empty list to a nonempty list");
            points += AutogradeCommon.displayMessage(list2.getTail().getData().equals("a"), 0.05, "incorrectly updating the tail when appending an empty list to a nonempty list");
            points += AutogradeCommon.displayMessage(list2.getSize() == 3, 0.05, "incorrectly updating the size when adding appending an empty list to a nonempty list");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException when appending an empty list to a nonempty list");
        } catch (Exception e) {
            System.out.println("Exception other than NullPointer caught in append: " + e);
        }

        try {
            list1 = new SinglyLinkedHT<>();
            list1.add(0, "x");
            list1.add(0, "y");
            list1.add(0, "z");
            list1.append(list2);

            System.out.println("\nAPPENDING A NONEMPTY LIST TO ANOTHER NONEMPTY LIST");

            points += AutogradeCommon.displayMessage(list1.getHead().getData().equals("z"), 0.05, "incorrectly updating the head when appending a nonempty list to a nonempty list");
            points += AutogradeCommon.displayMessage(list1.getTail().getData().equals("a"), 0.05, "incorrectly updating the tail when appending a nonempty list to a nonempty list");
            points += AutogradeCommon.displayMessage(list1.getSize() == 6, 0.05, "incorrectly updating the size when adding appending a nonempty list to a nonempty list");

        } catch (NullPointerException e) {
            System.out.println("NullPointerException when appending a nonempty list to a nonempty list");
        } catch (Exception e) {
            System.out.println("Exception other than NullPointer caught in append: " + e);
        }
        if (points > 0.449)
            System.out.println("No errors in the append method :)");
        System.out.println();
        return points;
    }


    public static void main(String[]args) {
        long start = System.currentTimeMillis();
        double score = 0.0;

        System.out.println("Code Score");
        score += testAdd();
        score += testRemove();
        score += testAppend();
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Score truncated to nearest half point");
        System.out.println((int)(score*2)/2.0 +"/1");
        if (score == 1)
            System.out.println("Great work!");
        System.out.println(timeElapsed + " ms to grade SA-4");
        System.exit(0);
    }
}
