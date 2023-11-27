package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;
import java.util.stream.Collectors;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }


    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        int len=words.size();
        List<Character> allchars=new ArrayList<>();
        Map<Character, Integer> FrequencyMap= new TreeMap<>();
        for(int i =0;i<len;i++){
            String currentword=words.get(i);
            int wordsize=currentword.length();
            for(int j=0;j<wordsize;j++){
                if(!allchars.contains(currentword.charAt(j))){
                    allchars.add(currentword.charAt(j));
                }
            }
        }
        int allcharsize= allchars.size();
        for(int k=0;k< allcharsize;k++){
            int count=0;
            char currentchar=allchars.get(k);
            for(int i=0;i<len;i++){
                String currentword=words.get(i);
                int wordsize=currentword.length();
                for(int j=0;j<wordsize;j++){
                    if(currentword.charAt(j)==currentchar){
                        count++;
                    }
                }
            }
            FrequencyMap.put(currentchar,count);
        }
        return FrequencyMap;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        Map<Character,Integer> FrequencyMap=getFrequencyMap();
        int len=guesses.size();
        for(int i=0;i<len;i++){
            char currentguesses=guesses.get(i);
            if(!FrequencyMap.containsKey(currentguesses)){
                FrequencyMap.put(currentguesses,0);
            }
            else{
                FrequencyMap.remove(currentguesses);
                FrequencyMap.put(currentguesses,0);
            }
        }
        int max=0;
        for(Character key:FrequencyMap.keySet()){
            int value=FrequencyMap.get(key);
            if(max<value){
                max=value;
            }
        }
        List<Character> guessList= new ArrayList<>();
        for(char key: FrequencyMap.keySet()){
            int value=FrequencyMap.get(key);
            if(value==max){
                guessList.add(key);
            }
        }
        if(guessList.size()==1){
            return guessList.get(0);
        }
        else{
            char min=guessList.get(0);
            for(int i=0;i<guessList.size();i++){
                if(guessList.get(i)<min){
                    min=guessList.get(i);
                }
            }
            return min;
        }
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());
        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));

    }
}
