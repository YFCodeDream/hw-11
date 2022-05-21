import java.util.LinkedList;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * A program for reading words from a file. Basic usage:
 * <p>
 * - create a WordReader for a file "some-text.txt" using
 * WordReader wr = new WordReader("some-text.txt")
 * Note that that "some-text.txt" should be in the root directory
 * for your project.
 * <p>
 * - To read the words in the text file, use the nextWord()
 * method. For example, the first call to `wr.nextWord()` will
 * return the first word in the text, and the the second callto
 * `wr.nextWord()` will return the second word, etc.
 * <p>
 * The words returned by nextWord() will all be lower-case and without
 * punctuation.
 */

public class WordReader {

    //store words in Linked list for O(1) insertion and removal
    LinkedList<String> words;
    String file;

    public WordReader(String file) {
        //Initialize the word reader and read in the list of words
        this.file = file;
        this.words = new LinkedList<String>();
        try {
            // Open the file
            FileInputStream fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                //Split the line into words
                String[] words = strLine.split(" ");
                for (String w : words) {
                    //Strip punctuation
                    String toAdd = removePunctuations(w);
                    //remove the lines that are blank after we cleaned them
                    if (toAdd.length() > 0) {
                        this.words.add(toAdd);
                    }
                }
            }
            fstream.close();
        } catch (Exception e) {


        }

    }

//Close the input stream

    public String removePunctuations(String s) {
        //build using string builder for better efficiency
        StringBuilder res = new StringBuilder();
        for (Character c : s.toCharArray()) {
            //add the chars in the word if they are letters or digits
            if (Character.isLetterOrDigit(c))
                res.append(c);
        }
        //make it lowercase before we return
        return res.toString().toLowerCase();
    }

    public String nextWord() {
        return this.words.isEmpty() ? null : this.words.removeFirst();
    }

}
