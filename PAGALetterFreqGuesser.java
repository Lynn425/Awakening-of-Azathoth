package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    public Map<Character, Integer> getFreqMapThatMatchesPattern(String pattern, List<Character> guesses) {
        int patternlen = pattern.length();
        Map<Character, Integer> FreqMapThatMatchesPattern = new TreeMap<>();
        List<String> ListBeforeRemove = new ArrayList<>();
        List<String> ListThatMatchesPattern = new ArrayList<>();
        int size = words.size();
        for (int i = 0; i < size; i++) {
            if (words.get(i).length() == patternlen) {
                String currentword = words.get(i);
                for (int j = 0; j < patternlen; j++) {
                    if (pattern.charAt(j) == currentword.charAt(j)) {
                        ListThatMatchesPattern.add(currentword);
                        ListBeforeRemove.add(currentword);
                    }
                }
            }
        }
        for (int i = 0; i < ListBeforeRemove.size(); i++) {
            String currentword = ListBeforeRemove.get(i);
            for (int j = 0; j < patternlen; j++) {
                if (pattern.charAt(j) != '-' && pattern.charAt(j) != currentword.charAt(j)) {
                    ListThatMatchesPattern.remove(currentword);
                }
                else if(pattern.charAt(j) == '-' && guesses.contains(currentword.charAt(j))){
                    ListThatMatchesPattern.remove(currentword);
                }
            }
        }
        int guess_size=guesses.size();
        List<Character> allchars = new ArrayList<>();
        if (ListThatMatchesPattern.size()==0 && guess_size!=0 ){
            for(int i=0;i<size;i++){
                if(words.get(i).length()==patternlen){
                    String currentword=words.get(i);
                    boolean flag=true;
                    for(int j=0;j<guess_size;j++){
                        String currentguess=String.valueOf(guesses.get(j));
                        if(currentword.contains(currentguess)){
                            flag=false;
                            break;
                        }
                    }
                    if(flag){
                        ListThatMatchesPattern.add(currentword);
                    }
                }
            }
        }
        if(ListThatMatchesPattern.size()==0 && guess_size==0){
            for(int i=0;i<size;i++){
                if(words.get(i).length()==patternlen){
                    ListThatMatchesPattern.add(words.get(i));
                }
            }
        }
        int sizeThatMatchesPattern = ListThatMatchesPattern.size();
        for (int i = 0; i < sizeThatMatchesPattern; i++) {
            String currentword = ListThatMatchesPattern.get(i);
            for (int j = 0; j < patternlen; j++) {
                if (!allchars.contains(currentword.charAt(j))) {
                    allchars.add(currentword.charAt(j));
                }
            }
        }
        int allcharsize = allchars.size();
        for (int k = 0; k < allcharsize; k++) {
            int count = 0;
            char currentchar = allchars.get(k);
            for (int i = 0; i < sizeThatMatchesPattern; i++) {
                String currentword = ListThatMatchesPattern.get(i);
                for (int j = 0; j < patternlen; j++) {
                    if (currentword.charAt(j) == currentchar) {
                        count++;
                    }
                }
            }
            FreqMapThatMatchesPattern.put(currentchar, count);
        }
        return FreqMapThatMatchesPattern;
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        Map<Character, Integer> FrequencyMap = getFreqMapThatMatchesPattern(pattern,guesses);
        int size = guesses.size();
        for (int i = 0; i < size; i++) {
            char currentguesses = guesses.get(i);
            if (!FrequencyMap.containsKey(currentguesses)) {
                FrequencyMap.put(currentguesses, 0);
            } else {
                FrequencyMap.remove(currentguesses);
                FrequencyMap.put(currentguesses, 0);
            }
        }
        int max = 0;
        for (Character key : FrequencyMap.keySet()) {
            int value = FrequencyMap.get(key);
            if (max < value) {
                max = value;
            }
        }
        List<Character> guessList = new ArrayList<>();
        for (char key : FrequencyMap.keySet()) {
            int value = FrequencyMap.get(key);
            if (value == max) {
                guessList.add(key);
            }
        }
        if (guessList.size() == 1) {
            return guessList.get(0);
        } else {
            char min = guessList.get(0);
            for (int i = 0; i < guessList.size(); i++) {
                if (guessList.get(i) < min) {
                    min = guessList.get(i);
                }
            }
            return min;
        }
    }


    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/sorted_scrabble.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
