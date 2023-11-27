package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern="";

    public RandomChooser(int wordLength, String dictionaryFile) {
        if (wordLength < 1) {
            throw new IllegalArgumentException();
        }
        else{
            List<String> words = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
            if (words.size()==0) {
                throw new IllegalStateException();
            }
            else{
                int numWords = words.size();
                int randomlyChosenWordNumber = StdRandom.uniform(numWords);
                chosenWord = words.get(randomlyChosenWordNumber);
                for(int i=0;i<wordLength;i++){
                    pattern+="-";
                }
            }
        }
    }


    @Override
    public int makeGuess(char letter) {
        int count=0;
        for(int i=0;i<chosenWord.length();i++){
            if(chosenWord.charAt(i)==letter){
                if(i!=0){
                    pattern=pattern.substring(0,i)+letter+pattern.substring(i+1);
                }
                else if(i!=chosenWord.length()-1){
                    pattern=letter+pattern.substring(1);
                }
                else{
                    pattern=pattern.substring(0,i)+letter;
                }
                count++;
            }
        }
        return count;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }
}
