
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ComputusTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String yearEnteredByUserString;
        String userContinueOrQuitString;
        String getNumberOfRepeatsString;
        int yearEnteredByUser;
        boolean validation;
        Scanner input = new Scanner(System.in);
        int userContinueOrQuit; //user entry at end of program
        do{
            
            System.out.println("Please enter the year you would like to learn the date of easter");
            System.out.print("Year: ");
           
            do{
                yearEnteredByUserString= input.next();
                validation= inputValidation(yearEnteredByUserString);
                if(validation==false){
                    System.out.print("INVALID INPUT. ENTER A VALID YEAR: ");
                }
            } while (validation== false);
            yearEnteredByUser= Integer.parseInt(yearEnteredByUserString);
            
            //instantiating GregorianDate object with year entered in constructor
            GregorianDate gregorian= new GregorianDate(yearEnteredByUser);
            
            //calling computeDateOfEaster method for object
            String pastOrFutureTense="";
            if(yearEnteredByUser<= 2015){
                pastOrFutureTense= " was ";
            }
            else{
                pastOrFutureTense= " will be ";
            }
            ArrayList<String> easterDateArray= gregorian.computeDateOfEaster(yearEnteredByUser);
            
            System.out.println("\nGregorian date of Easter in " + yearEnteredByUser + pastOrFutureTense + easterDateArray.get(0) + "/" 
                    + easterDateArray.get(1) + "/" + easterDateArray.get(2) + "\n");
            
            //computing how many times Easter lands on each date during 5,700,000 year period
            System.out.println("The order of the dates of Easter will repeat every 5,700,000 years");
            System.out.println("Would you like to know the number of times Easter occurs on each calendar day during a period?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Choice: ");
            do{
            	getNumberOfRepeatsString= input.next();
                validation= inputValidationOfTwoChoices(getNumberOfRepeatsString);
                if(validation==false){
                    System.out.print("PLEASE ENTER A VALID INPUT: ");
                }
            } while(validation == false);
            int getNumberOfRepeats= Integer.parseInt(getNumberOfRepeatsString);
            
            System.out.println();
            if(getNumberOfRepeats == 1){
            	System.out.println("the number of times Easter occurs on each calendar day during a period is:");
                
                //Map<String, Integer> numberOfDateRepeatsMap = new HashMap<>();
                Map<String, Integer> numberOfDateRepeatsMap= gregorian.computeSeries(yearEnteredByUser);
                //iterate through the map that has been built
                for (Map.Entry<String, Integer> entry : numberOfDateRepeatsMap.entrySet()) {
                    //print each date (key) and the count of its repeats (value)
                    System.out.println("------------------------------------------------");
                    System.out.println("Date: " + entry.getKey() + ", Easter Occurences: " + entry.getValue());
                }
                System.out.println("------------------------------------------------\n\n");
            }

            //menu to exit program
            System.out.println("Exit Program?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Choice: ");
            do{
                userContinueOrQuitString= input.next();
                validation= inputValidationOfTwoChoices(userContinueOrQuitString);
                if(validation==false){
                    System.out.print("PLEASE ENTER A VALID INPUT: ");
                }
            } while(validation == false);
            userContinueOrQuit= Integer.parseInt(userContinueOrQuitString);

            //determines if program repeats again
            System.out.println();
            
        } while(userContinueOrQuit != 1);
        
        input.close();
        System.out.println("\n\nGoodbye.");
        
    }
    
    /**
     * Error handling method to make sure user enters a valid input for the year
     * @param userInput string of the year input by the user
     * @return boolean value of 'true' for a valid input or 'false' for an invalid input
     */
    private static boolean inputValidation(String userInput){
        boolean validationCheck=false;
        try{
            Integer.parseInt(userInput); //checks to make sure input is an integer
            validationCheck= true;
        }
        catch(Exception ex){
        }
        return validationCheck;
    }
    
    /**
     * Error handling method to make sure user enters a valid input for continuing the program
     * @param userInput string of the year input by the user
     * @return boolean value of 'true' for a valid input or 'false' for an invalid input
     */
    private static boolean inputValidationOfTwoChoices(String userInput){
        boolean validationCheck=false;
        if(userInput.equals("1") || userInput.equals("2")){
            validationCheck=true;
        }
        
        return validationCheck;
    }
}
