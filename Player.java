import java.util.ArrayList;
import java.util.Scanner;

/**
 * All the functionality for a Player in a game of JOTTO
 */
public class Player {
    
    /** The name of this player */
    private String name;
    
    /** Whether or not the player is an AI */
    private boolean isAi;

    /**
     * ACCESSOR that returns a word from the dictionary that meets all the constraints
     * of the previous guesses and clues in the History
     * @param dictionary all the valid words for guessing
     * @param history all the previous guesses and clues
     * @return The valid guess for this round of JOTTO
     */
    public String getValidGuess (String [] dictionary, ArrayList<Round> history) {
        if (isAi)
           return getValidTwoStepAiGuess(dictionary, history);
        else
            return getValidUserGuess (dictionary, history);
    }
    
    /**
     * Returns a valid guess from user input.
     * The guess is both in the dictionary works against the given history.
     * @param dictionary The words that the User is allowed to guess.
     * @param history The history of the game the User is guessing for.
     * @return A valid guess that the User produced with the
     * given history and dictionary.
     */
    private String getValidUserGuess (String [] dictionary, ArrayList<Round> history) {
        // User input guess code
        Scanner input = new Scanner(System.in);
        
        System.out.println();
        System.out.print("Please enter a guess: ");
        String guess = input.nextLine().toUpperCase();
        
        while (!(Jotto.isValidWord(guess, dictionary) && Jotto.isValidGuess(guess, history))) {
            System.out.println("That guess is not valid!");
            System.out.print("Please enter a guess: ");
            guess = input.nextLine().toUpperCase();
        }
        
        input.close();

        System.out.println("Nice guess!");
        System.out.println();
        
        return guess;
    }
    
    /**
     * Returns a valid guess that the computer produced with the
     * given history and dictionary. This AI preforms a single step lookahead.
     * Kept for testing/comparison.
     * @param dictionary The words that the AI is allowed to guess.
     * @param history The history of the game the AI is guessing for.
     * @return A valid guess that the computer produced with the
     * given history and dictionary.
     */
    private String getValidAiGuess(String [] dictionary, ArrayList<Round> history) {
        String[] validWords;
        // On first guess return BREAD
        if (history.size() == 0) {
            // validWords = dictionary;
            return "BREAD";
        }
        // Otherwise set the valid words
        else
            validWords = allValidWords(dictionary, history);
        
        // Get the word with the lowest score
        String bestWord = validWords[0];
        double bestScore = getWordScore(bestWord, validWords);
        for (int i = 1; i < validWords.length; i++) {
            String word = validWords[i];
            double wordScore = getWordScore(word, validWords);
            if (wordScore < bestScore) {
                bestWord = word;
                bestScore = wordScore;
            }
        }

        // Return the word with the lowest score
        return bestWord;
    }

    /**
     * Returns a valid guess that the computer produced with the
     * given history and dictionary. This AI preforms a two step lookahead.
     * @param dictionary The words that the AI is allowed to guess.
     * @param history The history of the game the AI is guessing for.
     * @return A valid guess that the computer produced with the
     * given history and dictionary.
     */
    private String getValidTwoStepAiGuess(String [] dictionary, ArrayList<Round> history) {

        String[] validWords;
        // On first guess, return ARCED
        if (history.size() == 0) {
            // validWords = dictionary;
            return "ARCED";
        }
        // Otherwise set the valid words
        else
            validWords = allValidWords(dictionary, history);
        
        
        // First step (get the top 5 first level words)
        ArrayList<String> topWords = getTopWords(5, validWords);
        
        // for (String word : topWords) {
        //     System.out.println(word);
        // }

        // Second step (Return the best of those words based on their second guesses)
        return getBestWord(topWords, validWords);
    }

    /**
     * Returns the top words from the given dictionary based on their score.
     * Makes it the given length unless the given dictionary is shorter.
     * @param arrayLength The maximum length of the array that is to be returned.
     * @param dictionary The dictionary the top words are to be from.
     * @return The top words from the given dictionary based on their score.
     */
    private ArrayList<String> getTopWords(int arrayLength, String [] dictionary) {
        ArrayList<String> topWords = new ArrayList<String>();
        ArrayList<Double> topScores = new ArrayList<Double>();
        
        int topWordLength = arrayLength;
        if (dictionary.length < topWordLength)
        topWordLength = dictionary.length;
            
        for (int i = 0; i < topWordLength; i++) {
            topWords.add(dictionary[i]);
            topScores.add(Double.MAX_VALUE);
            // topScores.add(getWordScore(dictionary[i], dictionary));
        }
        
        // Calculate the rest of the array
        
        // if there are more words than in the existing top words
        if (dictionary.length > topWords.size()) {
            // for each word that is left
            for (int i = 0; i < dictionary.length; i++) {
                // Get the score
                double thisWordScore = getWordScore(dictionary[i], dictionary);
                // if the score is less than the lowest score in the array
                if (thisWordScore < topScores.get(topScores.size() - 1)) {
                    // get the index of where we are in the top words
                    int index = topScores.size() - 1;
                    // while the index is bigger than 0
                    while (index >= 0) {
                        // if the score is less than the next score and index not 0
                        if (index != 0 && thisWordScore < topScores.get(index - 1))
                            index--;
                        // else put the score and word into the array
                        else {
                            topScores.add(index, thisWordScore);
                            topWords.add(index, dictionary[i]);
                            topScores.remove(topScores.size() - 1);
                            topWords.remove(topWords.size() - 1);
                            index = -1; // break the loop
                        }
                    }
                } 
            }
        }

        return topWords;
    }

    /**
     * Returns the best word from the given top scores.
     * @param topWords The words that are to be looked at for their second step.
     * @param dictionary The dictionary the second guesses are from.
     * @return The best word from the given top words.
     */
    private String getBestWord(ArrayList<String> topWords, String[] dictionary) {
        String bestWord = topWords.get(0);
        double bestScore = Integer.MAX_VALUE;
        
        // for the top words
        for (String topWord : topWords) {
            // go through each possible clue scenerio (not 5)
            for (int i=0; i < 5; i++) {
                // make a new valid word list with the new word accounted for (for each scenero of 0-5)
                ArrayList<Round> fakeHistory = new ArrayList<Round>();
                fakeHistory.add(new Round(topWord, i));
                String[] newValidWords = allValidWords(dictionary, fakeHistory);
                
                // get the best score for each word in the new valid words
                for (String word : newValidWords) {
                    double wordScore = getWordScore(word, newValidWords);
                    if (wordScore < bestScore) {
                        bestWord = topWord;
                        bestScore = wordScore;
                    }
                }
            }
        }

        return bestWord;
    }
    
    /**
     * Returns the score of each word based on how well it splits up the dictionary.
     * @param word The word that is to be scored.
     * @param dictionary A string array which the word is to be scored against.
     * @return The score of each word based on how well it splits up the dictionary.
     */
    private double getWordScore (String word, String[] dictionary) {
        // An array of all of the possible ammounts of letters in common
        int[] counts = new int[] {0, 0, 0, 0, 0, 0};
        
        // go through each of the other words
        for (String dictWord : dictionary) {
            // Depending on the matching letters, add one to that count
            counts[Jotto.lettersInCommon(word, dictWord)]++;
        }
        
        return getSD(counts);
    }

    /**
     * Returns the standard deviation of the given integer array 
     * @param nums An integer array of numbers to get the standard deviation for.
     */
    private double getSD(int[] nums) {
        // Get the mean
        double mean = 0;
        for (int num : nums)
            mean += num;
        mean /= nums.length;
        
        // Get the mean of the nums minus the mean squared
        double squaredNums = 0;
        for (int num : nums) {
            squaredNums += Math.pow((double) num - mean, 2);
        }
        squaredNums /= nums.length;
        
        // Return the suqre root of the thing above
        return Math.sqrt(squaredNums);
    }
    
    /**
     * Returns all of the remaining valid words in the given dictionary
     * when given the game history.
     * @param dictionary A string array that will be filtered using the history which is passed in.
     * @param history An Array List of Round objects which will be used to filter the passed dictionary.
     * @return A string array of valid words
     */
    private String[] allValidWords(String [] dictionary, ArrayList<Round> history) {
        // Make valid word ArrayList
       ArrayList<String> validWords = new ArrayList<String>();
        for (String word : dictionary) {
            if (Jotto.isValidGuess(word, history))
                validWords.add(word);
        }
        
        // Convert the valid word Array List to a String array
        String[] words = new String[validWords.size()];
        for (int i = 0; i < words.length; i++)
            words[i] = validWords.get(i);
        return words;
    }
    
    /**
     * CONSTRUCTOR Sets the name for this Player and sets it as a non-AI player
     */
    public Player () {
        name = "AwesomeBot 8000";
        isAi = false;
    }
    
    /**
     * CONSTRUCTOR Sets the name for this Player and sets its AI status.
     * @param isAi Determines if the Player is an AI
     */
    public Player (boolean isAi) {
        name = "AwesomeBot 8000";
        this.isAi = isAi;
    }
     
     /**
      * Returns the name of the Player
      * @return The name of the player
      */
     public String getName () {
         return name;
     }
}