import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Reader {
    Hashtable<String, String> dict;
    public static void main(String[] args) throws IOException {

        // Create new Reader object
        Reader reader = new Reader();

        // Main loop, get a string input from user
        while(true){
            System.out.println();
            System.out.print("Enter a word to be spellchecked: ");

            Scanner inputReader = new Scanner(System.in);
            String input = inputReader.nextLine();
            ArrayList<String> results = reader.checkWord(input);

            // UI conditionals
            if(results.size() <1){
                System.out.println("No mistakes found");
            } else{

                System.out.println("    Incorrectly spelled word, Corrections:");

                for(String s : results){
                    System.out.println("        " + s);

                }

            }
        }

    }

    private Reader(){

        // Create dictionary as hashtable from .txt file
        try {
            dict = new Hashtable<String, String>();
            System.out.print(new File(".").getAbsolutePath());
            BufferedReader dictFile = new BufferedReader(new FileReader("C:\\Projects\\Alias HW\\linked lists\\src\\words.txt"));

            // Add words line by line until end of file
            while(dictFile.ready()){
                String word = dictFile.readLine();
                dict.put(word, word);
            }
            dictFile.close();

        }catch(IOException e){
            System.out.print("Error on creating dictionary, file not found or bad  file");
        }
    }

    public ArrayList<String> checkWord(String word){
        ArrayList<String> corrections = new ArrayList<String>();

        // If dict has word, it is spelled correctly so return
        if(dict.containsKey(word)){
            return corrections;
        }

        // Get list of all applied edits of the word
        ArrayList<String> edits = possibleEdits(word);

        // Check if edits are correctly spelled words add to a list
        for(String s : edits){
            if(dict.containsKey(s)){
             corrections.add(s);
            }
        }

        // If no corrections exist, add string stating so to list
        if(corrections.size() < 1){
            corrections.add("      No corrections found");
        }

        return corrections;
    }

    private ArrayList<String> possibleEdits(String word){
        ArrayList<String> edits = new ArrayList<String>();


        // Add letter to beginning
        for( int i =0; i<26;i++){
            String letter = Character.toString((char) (97+i));
            edits.add( letter + word );
        }

        // Add letter to end
        for( int i =0; i<26;i++){
            String letter = Character.toString((char) (97+i));
            edits.add( word + letter );
        }

        // Remove character from beginning
        edits.add(word.substring(1,word.length()));

        // Remove character from the end
        edits.add(word.substring(0,word.length()-1));

        // Exchange adjacent characters
        for( int i=0; i< word.length()-1; i++){
            edits.add(word.substring(0,i) + word.substring(i+1,i+2) + word.substring(i,i+1) + word.substring(i+2));
        }

        return edits;

    }
}