/**
 * A helper class for JOTTO keeping track of a round of guess and clue
 */
public class Round {
    /** The guess from this round */
    public final String guess;
    
    /** The corresponding clue from this round */
    public final int clue;
    
    /**
     * CONSTRUCTOR that permanently sets a guess and clue
     * @param guess The guess for this round
     * @param clue The corresponding clue for this round
     */
    public Round (String guess, int clue) {
        this.guess = guess;
        this.clue = clue;
    }
    
    /**
     * Create a string in the form GUESS + " " + CLUE
     * @returns a String representation of this round
     */
    public String toString () {
        return guess + " "+ clue;
    }

}