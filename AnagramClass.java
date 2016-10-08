import java.util.Iterator;
import java.util.LinkedList;

/**
* AnagramClass contains words with the same anagram.
*/
public class AnagramClass implements Comparable {

    public static final int NUMBER_OF_ALPHABETS = 26;
    private String classIdentification;
    private char[] charCounter;
    private LinkedList<String> wordList;
    
    /**
    * Construct an AnagramClass.
    * @word a word.
    */
    public AnagramClass(String word) {
        wordList = new LinkedList<>();
        classIdentification = "";
        wordList.add(word);
        charCounter = createCharCounterArray(word);
        classIdentification = createIdentification(word);
    }

    /**
    * Get the list of words in this anagram class.
    * @return a list of words.
    */
    public LinkedList<String> getWordList() {
        return wordList;
    }

    /**
    * Create a string identification of the word by sorting its characters.
    * @word a word.
    * @return an identification string.
    */
    private String createIdentification(String word) {
        String id = "";
        for (int i = 0; i < NUMBER_OF_ALPHABETS; i++) {
            if ((int) charCounter[i] != 0) {
                id += ((char) ('a' + i))
                + "" + Integer.valueOf(charCounter[i]);
            }
        }
        return id;
    }

    /**
    * Create an array that keeps track of number of occurence
    * of alphabets in the given word.
    * @word a word.
    * @return a counter array.
    */
    private char[] createCharCounterArray(String word) {
        char[] counter = new char[NUMBER_OF_ALPHABETS];
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            int pos = (int) word.charAt(i) - (int) 'a';
            counter[pos] += 1;
        }
        return counter;
    }
    
    /**
    * Check whether this anagram class accept the given word as belonging in it.
    * @word a word.
    * @return true if the word belongs; false, otherwise.
    */
    public boolean accept(String word) {
        char[] c = createCharCounterArray(word);
        for (int i = 0; i < NUMBER_OF_ALPHABETS; i++) {
            if (c[i] != charCounter[i]) {
                return false;
            }
        }
        return true;
    }

    /**
    * Add a word into this anagram class.
    * @word a word.
    */
    public void addToAnagramClass(String word) {
        wordList.add(word);
    }

    /**
    * Compare this anagram class to another anagram class.
    * @t another anagram class.
    * @return -1 if less than; 0 if equal; 1 if greater than.
    */
    @Override
    public int compareTo(Object t) {
        if (t instanceof AnagramClass) {
            AnagramClass otherAnagramClass = (AnagramClass) t;
            return this.classIdentification.compareTo(otherAnagramClass.classIdentification);
        }
        return -1;
    }

    /**
    * Return a string description of this anagram class.
    * @return a string contains words belonging to this anagram class.
    */
    @Override
    public String toString() {
        String description = "";
        Iterator<String> iter = this.wordList.iterator();
        while (iter.hasNext()) {
            description += iter.next();
            if (iter.hasNext()) {
                description += " ";
            }
        }
        return description;
    }
}
