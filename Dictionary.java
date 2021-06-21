import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class Dictionary {
    /**
     * Attempts to return the dictionary from a given file.
     * @param filename The path of where dictionary is located.
     * Words are in reverse alphabetical order, separated by a new line.
     * @return A list of words from the file. If the file cannot be found, a default dictionary is returned.
     */
    public static String [] getDictionary (String filename) {
        File file = new File (filename);
        ArrayList <String> words = new ArrayList<String>();

        try {
            Scanner input = new Scanner (file);
            while (input.hasNextLine()) {
                words.add (0, input.nextLine().toUpperCase());
            }
            input.close();
        } catch (Exception e) {
            words.add ("BRAIN");  
            words.add ("BREAD"); 
            words.add ("CHUNK");     
            words.add ("DREAM");  
            words.add ("DUSTY");   
            words.add ("RAINS");  
            words.add ("RAVED");
            System.out.println("Using Default Dictionary");
        }
        
        String [] ret_words = new String [words.size()];
        for (int i = 0; i < ret_words.length; i++)
            ret_words[i] = words.get(i);
        
        return ret_words;
    }
}