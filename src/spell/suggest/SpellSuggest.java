import java.io.*;
import java.util.Scanner;
import java.util.HashSet;
import javax.swing.*;
import java.util.TreeSet;
import java.util.Iterator;

public class SpellSuggest {

    public static void main(String[] args) {
    
        Scanner words;
        HashSet<String> dict = new HashSet<String>();
        Scanner userFile;
        
        try {
        
            words = new Scanner(new File("TELEMARKDAT.643"));
        
            while (words.hasNext()) {
                String word = words.next();
                dict.add(word.toLowerCase());
            }
            
            userFile = new Scanner(getInputFileNameFromUser());
                        
            userFile.useDelimiter("[^a-zA-Z]+");
            
            HashSet<String> badWords = new HashSet<String>();
            while (userFile.hasNext()) {
                String userWord = userFile.next();
                userWord = userWord.toLowerCase();
                if (!dict.contains(userWord) && 
                    !badWords.contains(userWord)) {
                    
                    badWords.add(userWord);
                    TreeSet<String> goodWords = new TreeSet<String>();
                    goodWords = corrections(userWord, dict);
                    System.out.print(userWord + ": ");
                    if (goodWords.isEmpty())
                        System.out.println("(no suggestions)");
                    else {
                        int count = 0;
                        for (String goodWord: goodWords) {
                            System.out.print(goodWord);
                            if (count < goodWords.size() - 1)
                                System.out.print(", ");
                            else
                                System.out.print("\n");
                            count++;
                        }
                    }
                    
                }
            
            }
            
        }
        catch (FileNotFoundException e) {
            System.exit(0);
        }   
    } // end main()  
	
    static File getInputFileNameFromUser() {
    
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Input");
        int option = fileDialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION)
            return null;
        else
            return fileDialog.getSelectedFile();   
    } 
    
    
    static TreeSet corrections(String badWord, HashSet dictionary) {
    
        TreeSet<String> possibleWords =  new TreeSet<String>();
        String subStr1, subStr2, possibility;
        
        for (int i = 0; i < badWord.length(); i++) {
                 
            subStr1 = badWord.substring(0, i);
            subStr2 = badWord.substring(i + 1);
                        
            possibility = subStr1 + subStr2;
            if (dictionary.contains(possibility))
                possibleWords.add(possibility);
                          
            for (char ch = 'a'; ch <= 'z'; ch++) {
                possibility = subStr1 + ch + subStr2;
                if (dictionary.contains(possibility))
                    possibleWords.add(possibility);
            }
            
            subStr1 = badWord.substring(0, i);
            subStr2 = badWord.substring(i);
                        
            for (char ch = 'a'; ch <= 'z'; ch++) {
                possibility = subStr1 + ch + subStr2;
                if (dictionary.contains(possibility))
                    possibleWords.add(possibility);
            }            
            
            char ch = ' ';
            possibility = subStr1 + ch + subStr2;
            if (dictionary.contains(subStr1) && dictionary.contains(subStr2))
                      possibleWords.add(possibility);                      
        }        
        
        for (int i = 1; i < badWord.length(); i++) {
            subStr1 = badWord.substring(0, i - 1);
            char ch1 = badWord.charAt(i - 1);
            char ch2 = badWord.charAt(i);
            subStr2 = badWord.substring(i + 1);
            possibility = subStr1 + ch2 + ch1 + subStr2;
            if (dictionary.contains(possibility))
                possibleWords.add(possibility);
        }        
        return possibleWords;    
    } 
}
