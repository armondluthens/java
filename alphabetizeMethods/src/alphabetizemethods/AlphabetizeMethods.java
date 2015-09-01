/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alphabetizemethods;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileWriter;
import java.io.Writer;
/**
 *
 * @author armondluthens
 */
public class AlphabetizeMethods {

    /**
     * @param args the command line arguments
     */
    public static ArrayList<String> alpha = new ArrayList<>();
    public static final String fileLoc = "/Users/armondluthens/Desktop/Coding/Miscellaneous/alphabetize_words.txt";
    public static void main(String[] args) {
        // TODO code application logic here
       try {
            importWords();
        } catch (FileNotFoundException ex) {
            System.err.println("Words File not found : " + ex);
        }
        sort();
        
        try{
            FileWriter fw = new FileWriter("/Users/armondluthens/Desktop/alphabetized_output.txt");

            Writer output = new BufferedWriter(fw);
            int size = alpha.size();
            for(int i=0; i< size; i++){
                output.write(alpha.get(i) + "\n");
            }
            output.close();
        } catch (Exception e){
            System.out.println("ERROR.");
        }
        
        System.out.println("Success For Mondo.");

    }
    public static void importWords() throws FileNotFoundException{
        Scanner sc = new Scanner(new File(fileLoc)); //Scanner to read in the new file
        alpha = new ArrayList<>();
        
        while (sc.hasNextLine()) { // Reads the file line by line
            alpha.add(sc.nextLine()); // Adds each line into the array
        }
    }
    public static void sort(){
        Collections.sort(alpha);
    }
    
    
}
