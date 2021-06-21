/**
 * This class is a mastermind for a game of JOTTO.  It keeps a hidden word and
 * gives clues to guesses asked of it.
 */
 public class Mastermind {
    /** The hidden JOTTO word for a game of JOTTO */
    private String hiddenWord;
    
    /**
     * CONSTRUCTOR that takes in a String as the hidden word
     * @param word The hidden word for this game of JOTTO
     */
    public Mastermind (String word) {
        hiddenWord = word;
        
        // Test print statement
        System.out.println("Word is " + hiddenWord);
    }
    
    /**
     * CONSTRUCTOR that picks a random word from the dictionary
     * @param dictionary All of possible words for the Mastermind
     */
    public Mastermind (String [] dictionary) {
        int randomIndex = (int) (Math.random()*dictionary.length);
        hiddenWord = dictionary [randomIndex];
        
        
        System.out.println("Word is " + hiddenWord);
    }
    
    /**
     * CONSTRUCTOR that picks a random word from the dictionary
     * @param dictionary All of possible words for the Mastermind
     * @param showOutput Logs the hidden word in the console
     */
    public Mastermind (String [] dictionary, boolean showOutput) {
        int randomIndex = (int) (Math.random()*dictionary.length);
        hiddenWord = dictionary [randomIndex];
        
        if (showOutput)
            System.out.println("Word is " + hiddenWord);
    }
    
    /**
     * ACCESSOR Gets the number of letters in common between
     * the guess and hiddenWord.
     * @param guess The guess from the player for a round of JOTTO
     * @return the clue that goes along with the guess for the hiddenWord 
     */
    public int getClue (String guess) {
        return Jotto.lettersInCommon(hiddenWord, guess);
    }

}