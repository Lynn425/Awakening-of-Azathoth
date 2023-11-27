package aoa.guessers;

import java.util.List;
import java.util.Map;

public interface Guesser {
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */


    public char getGuess(String pattern, List<Character> guesses);
}
