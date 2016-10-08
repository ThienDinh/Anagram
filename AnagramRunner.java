import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
* AnagramRunner implements the Divide-and-Conquer algorithm
* to produce a list of anagram classes.
*/
public class AnagramRunner {

    /**
    * Main entrance of the program.
    * @args a list of arguments.
    */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 2) {
            System.out.println("You need to follow the command syntax: \"java AnagramRunner input_file output_file\". ");
            return;
        }
        File input = new File(args[0]);
        File output = new File(args[1]);
        Scanner reader = new Scanner(input);
        PrintWriter writer = new PrintWriter(output);
        ArrayList<String> wList = new ArrayList<>();
        while (reader.hasNext()) {
            String word = reader.next();
            wList.add(word);
        }
        //wList = getSample();
        System.out.println("Total words:" + wList.size());
        reader.close();
        LinkedList<AnagramClass> anagramList = conquer(wList);
        int counter = 0;
        for (AnagramClass c : anagramList) {
            writer.println(c);
            counter += c.getWordList().size();
        }
        System.out.println("Total anagram classes:" + anagramList.size());
        System.out.println("Total anagrams:" + counter);
        writer.close();
    }

    /**
    * Divide the list of words into halves.
    * @wordList list of words
    * @firstHalf if true then it returns the first half; otherwise, return second half.
    * @return return the requested half.
    */
    public static ArrayList<String> divideHalves(ArrayList<String> wordList, boolean firstHalf) {
        int start;
        int end;
        if (firstHalf) {
            start = 0;
            end = wordList.size() / 2 - 1;
        } else {
            start = wordList.size() / 2;
            end = wordList.size() - 1;
        }
        ArrayList<String> halfList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            halfList.add(wordList.get(i));
        }
        return halfList;
    }

    /**
     * Conquer the list of words.
     * @param wordList a list of words.
     * @return a list of anagram classes created from the list of words.
     */
    public static LinkedList<AnagramClass> conquer(ArrayList<String> wordList) {
        // If it's too easy to solve, do it.
        if (wordList.size() == 1) {
            LinkedList<AnagramClass> anagramList = new LinkedList<>();
            AnagramClass newAnagramClass = new AnagramClass(wordList.get(0));
            anagramList.add(newAnagramClass);
            return anagramList;
        } // Else break it into smaller chunk.
        else {
            ArrayList<String> firstHalf = divideHalves(wordList, true);
            ArrayList<String> secondHalf = divideHalves(wordList, false);
            LinkedList<AnagramClass> firstAnagramList = conquer(firstHalf);
            LinkedList<AnagramClass> secondAnagramList = conquer(secondHalf);
            LinkedList<AnagramClass> combinationAnagram
            = combine(firstAnagramList, secondAnagramList);
            return combinationAnagram;
        }
    }

    /**
    * Combine two list of anagram classes.
    * @anagramList1 first list of anagram classes.
    * @anagramList2 second list of anagram classes.
    * @return the combination of two lists.
    */
    public static LinkedList<AnagramClass> combine(
        LinkedList<AnagramClass> anagramList1,
        LinkedList<AnagramClass> anagramList2) {
        LinkedList<AnagramClass> combinedAnagramList
        = new LinkedList<>();
        Iterator<AnagramClass> iter1 = anagramList1.iterator();
        Iterator<AnagramClass> iter2 = anagramList2.iterator();
        boolean popClass1 = true;
        boolean popClass2 = true;
        AnagramClass class1 = null;
        AnagramClass class2 = null;
        do {
            if (popClass1) {
                if (iter1.hasNext()) {
                    class1 = iter1.next();
                } else {
                    class1 = null;
                }
            }
            if (popClass2) {
                if (iter2.hasNext()) {
                    class2 = iter2.next();
                } else {
                    class2 = null;
                }
            }
            if (class1 == null && class2 != null) {
                combinedAnagramList.add(class2);
                popClass1 = false;
                popClass2 = true;
            } else if (class1 == null && class2 == null) {
                popClass2 = false;
                popClass1 = false;
            } else if (class1 != null && class2 != null) {
                if (class1.compareTo(class2) < 0) {
                    combinedAnagramList.add(class1);
                    popClass1 = true;
                    popClass2 = false;
                } else if (class1.compareTo(class2) > 0) {
                    combinedAnagramList.add(class2);
                    popClass2 = true;
                    popClass1 = false;
                } else {
                    for (String word : class2.getWordList()) {
                        class1.addToAnagramClass(word);
                    }
                    combinedAnagramList.add(class1);
                    popClass1 = true;
                    popClass2 = true;
                }
            } else if (class1 != null && class2 == null) {
                combinedAnagramList.add(class1);
                popClass1 = true;
                popClass2 = false;
            }
        } while (popClass1 || popClass2);
        return combinedAnagramList;
    }
}
