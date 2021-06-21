import java.util.ArrayList;
/**
 * Main JOTTO class for a game of JOTTO.
 */
public class Jotto {
    /** The player for this game of JOTTO */
    private Player player;
    /** The hidden word Mastermind for this game of JOTTO */
    private Mastermind mind;
    /** All of the guesses and clues for this game of JOTTO so far */
    private ArrayList<Round> history;
    /** All the valid UPPERCASE words in alphabetical order allowed in this game of JOTTO */
    private String [] dictionary;
    /** Deturmines if the program should show any output */
    private boolean showOutput;
    
    /** 
     * CONSTRUCTOR for starting a game of JOTTO, using the default dictionary 
     */
    public Jotto () {
        player = new Player ();
        history = new ArrayList<Round> ();
        dictionary = Dictionary.getDictionary("JOTTOWords.txt");
        mind = new Mastermind (dictionary);
        showOutput = true;
    }

    /** 
     * CONSTRUCTOR for starting a game of JOTTO, using the default dictionary 
     * @param isAi Weather or not the player is a human or AI
     * @param silent Weather or not the program should run silently
     */
    public Jotto (boolean isAi, boolean silent) {
        player = new Player (isAi);
        history = new ArrayList<Round> ();
        dictionary = Dictionary.getDictionary("JOTTOWords.txt");
        mind = new Mastermind (dictionary, !silent);
        showOutput = !silent;
    }

    /** 
     * CONSTRUCTOR for starting a game of JOTTO, using the default dictionary 
     * @param isAi Weather or not the player is a human or AI
     * @param silent Weather or not the program should run silently
     * @param dictionary The dictionary for the game to use.
     * @param masterWord The word for the mastermind to use.
     */
    public Jotto (boolean isAi, boolean silent, String[] dictionary, String masterWord) {
        player = new Player (isAi);
        history = new ArrayList<Round> ();
        this.dictionary = dictionary;
        mind = new Mastermind (masterWord, !silent);
        showOutput = !silent;
    }
    
    /**
     * Returns the number of rounds played in the game.
     * @return The number of rounds played in the game.
     */
    public int getRoundCount() {
        return history.size();
    }

    /**
     * Plays an entire game of JOTTO until the hidden word is guessed
     */
    public void playGame () {
        int clue = 0;
        while (clue != 5) { 
            String guess = player.getValidGuess (dictionary, history);
            clue = mind.getClue (guess);
            history.add (new Round (guess, clue));
            if (showOutput)
                System.out.println (history.get(history.size()-1));
        }
    }
    
    /**
     * Checks if the guess is within dictionar
     * @param guess The JOTTO guess
     * @param dictionary All the valid words
     * @return true if dictionary contains guess, false otherwise
     */
    public static boolean isValidWord (String guess, String [] dictionary) {
        boolean valid = false;
        int leftIndex = 0;
        int rightIndex = dictionary.length - 1;
        
        while(!(valid || (leftIndex > rightIndex)) ) {
            // Get the new middle each time
            int middleIndex = ((rightIndex - leftIndex) / 2) + leftIndex;
            
            // Value of the comparison between the guess and the current middle word
            int compare = guess.compareTo(dictionary[middleIndex]);
            
            // Compare and update accordingly
            if (compare == 0) {
                valid = true;
            }
            else if (compare > 0)
                leftIndex = middleIndex + 1;
            else
                rightIndex = middleIndex - 1;
        }
        
        return valid;
    }
    
    /**
     * Returns the # of letters used by both a and b
     * @param a The first string to compare
     * @param b The second string to compare
     * @returns the # of letters used by both a and b
     */
    public static int lettersInCommon (String a, String b) {
        int common = 0;
        for (int i = 0; i < b.length(); i++) {
            if (a.indexOf(b.substring(i, i+1)) > -1)
                common++;
        }
        return common;
    }
    
    /**
     * Checks if guess is constrained properly by history
     * @param guess the JOTTO guess
     * @param history All the previous rounds of JOTTO
     * @return true if the guess has the same letters in common with all previous 
     * rounds guesses as the clues for those rounds
     */
    public static boolean isValidGuess (String guess, ArrayList<Round> history) {
        boolean stillValid = true;
        for (int i = 0; i < history.size() && stillValid; i++) {
            Round round = history.get(i);
            int inCommon = Jotto.lettersInCommon(guess, round.guess);
            // System.out.println("guess: "+guess+", in common: "+inCommon+", roundGuess: "+round.guess+", roundClue: "+round.clue);
            if (round.clue != inCommon)
                stillValid = false;
        }
        return stillValid;
    }
}