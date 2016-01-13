
import java.util.ArrayList;
import java.util.Scanner;

public class BarcodeTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String selectedBarcodeString;
        int selectionInteger;
        Scanner input = new Scanner(System.in);
        
        do {
            System.out.println("Select Barcode Type");
            System.out.println("1. Encode a zip code to a POSNET barcode");
            System.out.println("2. Decode a POSNET barcode to a zip code");
            System.out.println("3. Encode a Product Code to a UPC-A barcode");
            System.out.println("4. Decode a UPC-A barcode to a Product code");
            //System.out.println("5. Generate QRC Code");
            System.out.println("5. Exit Program.");
            System.out.print("Choice: ");
            selectedBarcodeString= input.next();
            boolean validUserInput = validUserInputFromMenu(selectedBarcodeString);
            
            //User input error handling
            while(validUserInput==false){
                System.out.println("INVALID INPUT. ENTER A VALID OPTION");
                System.out.print("Choice: ");
                selectedBarcodeString= input.next();
                validUserInput = validUserInputFromMenu(selectedBarcodeString);
            }
            System.out.println();
            selectionInteger = Integer.parseInt(selectedBarcodeString);

            //error handling to make sure input integer is within bounds
            if(selectionInteger != 5){
                String stringEnteredCode; //user input string
                boolean validityCheck;
                BarcodeParent barcodeObject= createObject(selectionInteger); //barcode type object created
                ArrayList<String> encodeOrDecodeMenu= barcodeObject.encodeOrDecodeMenu(selectionInteger); //gets proper menu
                String tempString;
                
                //will keep repeating until valid string is entered
                do{
                    for(int i=0; i<encodeOrDecodeMenu.size(); i++){
                        tempString=encodeOrDecodeMenu.get(i);
                        System.out.println(tempString);
                    }
                    System.out.print("Entry: ");
                    stringEnteredCode = input.next();
                    validityCheck= barcodeObject.isValidEncodeOrDecode(stringEnteredCode, selectionInteger);
                    if(validityCheck==false){
                        System.out.println("INVALID INPUT.");
                    }
                } while(validityCheck == false);
                
                String returnLabel = barcodeObject.returnLabel(selectionInteger);
                String userOutput= barcodeObject.beginProcess(stringEnteredCode, selectionInteger);
                System.out.println("\n" + returnLabel + userOutput + "\n\n");
                
            }
        } while (selectionInteger != 5);
        input.close();
        System.out.println("\nGoodbye.");
    }

    /**
     * Method to ensure the user input was valid (either a "1" or a "2")
     * @param selectedBarcodeString
     * @return boolean variable indicating if the input was valid or not
     */
    public static boolean validUserInputFromMenu(String selectedBarcodeString){
        boolean validityCheck=false;
        if(selectedBarcodeString.equals("1") || selectedBarcodeString.equals("2") || 
           selectedBarcodeString.equals("3") || selectedBarcodeString.equals("4") || 
           //selectedBarcodeString.equals("5") || selectedBarcodeString.equals("6")){
           selectedBarcodeString.equals("5")){
            validityCheck=true;
        }
       
        return validityCheck;
    }
    
    /**
     * Method to create either a POSNET, UPC-A, or QRC object based on user input
     * @param selectedBarcodeInteger indicates what class to make a bar-code object for
     * @return BarcodePar
     */
    public static BarcodeParent createObject(int selectedBarcodeInteger){
        BarcodeParent barcodeObject;
     
        if(selectedBarcodeInteger == 1 || selectedBarcodeInteger == 2){
            barcodeObject = new Posnet();
        }
        //else if(selectedBarcodeInteger == 3 || selectedBarcodeInteger == 4){
        else{
            barcodeObject = new Upc();
        }
        /*
         * Qrc Not Yet complete
          */
        //else{
        //    barcodeObject = new Qrc();
        //}
       
        return barcodeObject;
    }
}
