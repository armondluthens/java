
import java.util.ArrayList;

public class Posnet extends BarcodeParent {
	private final static String[] binaryRepresentations = new String[10]; //array holding binary representations of digits 0-9
    private final static ArrayList<String> barcodeRepresentations= new ArrayList<>();
    
    Posnet(){
        createBinaryRepresentations(); //calls method to create string array of binary representations of digits 0-9
        createBarcodeRepresentations();
    }
    
    /**
     * Method that creates binary representations of numbers upon Posnet object instantiation
     */
    private static void createBinaryRepresentations(){
        binaryRepresentations[0]= "11000";
        binaryRepresentations[1]= "00011";
        binaryRepresentations[2]= "00101";
        binaryRepresentations[3]= "00110";
        binaryRepresentations[4]= "01001";
        binaryRepresentations[5]= "01010";
        binaryRepresentations[6]= "01100";
        binaryRepresentations[7]= "10001";
        binaryRepresentations[8]= "10010";
        binaryRepresentations[9]= "10100";
    }
    
    /**
    * Method that creates the list of barcode representations of digits
    */
    private static void createBarcodeRepresentations(){
        barcodeRepresentations.add("||..."); //0
        barcodeRepresentations.add("...||"); //1
        barcodeRepresentations.add("..|.|"); //2
        barcodeRepresentations.add("..||."); //3
        barcodeRepresentations.add(".|..|"); //4
        barcodeRepresentations.add(".|.|."); //5
        barcodeRepresentations.add(".||.."); //6
        barcodeRepresentations.add("|...|"); //7
        barcodeRepresentations.add("|..|."); //8
        barcodeRepresentations.add("|.|.."); //9
    }
    
    /**
     * Method to return the proper menu based on what the user wants 
     * to encode or decode
     * @param encodeOrDecodeSelection integer indicating the need to encode or decode
     * @return an array list of strings of the proper menu
     */
    @Override
    public ArrayList<String> encodeOrDecodeMenu(int encodeOrDecodeSelection){
        ArrayList<String> encodeMenu= new ArrayList<>();
        encodeMenu.add("Please enter a ZIP, a ZIP+4, or a ZIP+4+delivery.");
        encodeMenu.add("Please enter the integers only.");
        
        ArrayList<String> decodeMenu= new ArrayList<>();
        decodeMenu.add("Please enter a valid POSNET barcode.");
        if(encodeOrDecodeSelection == 1){
            return encodeMenu;
        }
        else{
            return decodeMenu;
        }
    }
    
     /**
     * Method to return the proper menu based on what the user wants 
     * to encode or decode
     * @param encodeOrDecode integer indicating the need to encode or decode
     * @param userEntry string of either a posnet barcode or zip code to be validated
     * @return a boolean indicating is the string entered was valid or not
     */
    @Override
    public boolean isValidEncodeOrDecode(String userEntry, int encodeOrDecode){
        boolean validityCheck;
        if(encodeOrDecode==1){
            validityCheck= isValidZipCode(userEntry);
        }
        else{
            validityCheck= isValidBarcode(userEntry);
        }
        return validityCheck;
    }
    
    /**
     * Method to call the process to either encode a zip or decode a barcode
     * to encode or decode
     * @param encodeOrDecode integer indicating the need to encode or decode
     * @param userEntry string of either a posnet barcode or zip code to be validated
     * @return a string of an encoded zip code or decode barcode back to main method
     */
    @Override
    public String beginProcess(String userEntry, int encodeOrDecode){
        String userOutput;
        if(encodeOrDecode==1){
            userOutput= beginEncode(userEntry);
        }
        else{
            userOutput= beginDecode(userEntry);
        }
        return userOutput;
    }
    
    /**
     * Method to get the label next to the returned barcode or zip code when printed in main method
     * to encode or decode
     * @param encodeOrDecode integer indicating the need to encode or decode
     * @return a string to display as a label next to the returned barcode or zip code
     */
    @Override
    public String returnLabel(int encodeOrDecode){
        String posnetEncodeLabel= "POSNET Bar Code: ";
        String originalZipCode= "Original Zip Code: ";
        if(encodeOrDecode==1){
            return posnetEncodeLabel;
        }
        else{
            return originalZipCode;
        }
    }
    
    /**
     * Method that is the driver to encode a user entered zip code into a barcode
     * @param stringEnteredZipCode
     * @return string of the complete barcode back to the main method
     */
    private static String beginEncode(String stringEnteredZipCode){
        //integer arraylist that holds all digits of the entered zip code in individual indexes
        ArrayList<Integer> zipCodeDigitsArray= breakZipCodeIntoDigits(stringEnteredZipCode);
        int sumOfZipCodeDigits = zipSum(zipCodeDigitsArray); //sum of all the digits of the zip code
        int numToEncodeOnZipCode= numToEncode(sumOfZipCodeDigits);
        zipCodeDigitsArray.add(numToEncodeOnZipCode); //adding the check digit to the zip code arraylist
        
        String finalBinaryString= generateBinaryString(zipCodeDigitsArray); //binary string with check digit included
        String barcode= generateBarcode(finalBinaryString); //barcode generated from binary string
        
        return barcode;
    }

    /**
     * Method to determine if the user entered zip code input is valid
     * @param stringEnteredZipCode zip code entered by user
     * @return boolean back to beginEncode() method if zip was valid or invalid
     */
    private static boolean isValidZipCode(String stringEnteredZipCode){
        boolean validityCheck=false; //will return false for invalid zip, and true for a valid zip
        int zipLength = stringEnteredZipCode.length();
        
        if(zipLength==5 || zipLength==9){
            try{
                //if parse works then string is clearly an integer
                Integer.parseInt(stringEnteredZipCode);
                validityCheck= true;
            }
            catch(Exception ex){  
            }
        }
        //if user enters zip+4+delivery then number must be of type long
        else if(zipLength==11){
            try{
                Long.parseLong(stringEnteredZipCode);
                validityCheck= true;
            }
            catch(Exception ex){
            }
        }
       return validityCheck;  
    }
    
    /**
     * Method to break the zip code string into digits and put the digits into an ArrayList
     * @param enteredZipCode string of valid digits for a zip code
     * @return an ArrayList of Integers of each digit at a different index back beginEncode()
     */
    private static ArrayList<Integer> breakZipCodeIntoDigits(String enteredZipCode){
        ArrayList<Integer> zipArray= new ArrayList<>();
        int zipLength = enteredZipCode.length();
        //char tempChar;
        int tempInt;
        
        for(int i=0; i<zipLength; i++){
            //tempChar= enteredZipCode.charAt(i);
            //getNumericValue function to change the character to an integer
            tempInt = Character.getNumericValue(enteredZipCode.charAt(i)); 
            zipArray.add(tempInt);
        }
        return zipArray;
    }
    
    /**
     * Method that takes in a list of integers and adds them and returns the sum
     * @param zipArray
     * @return an integer of the sum of all the digits in the list
     */
    private static int zipSum(ArrayList<Integer> zipArray){
        int zipSum=0;
        int curInt;
        for(int i=0; i<zipArray.size(); i++){
            curInt=zipArray.get(i);
            zipSum+=curInt;
        }
        return zipSum;
    }
    
    /**
     * Method that takes in the integer of the sum of digits in the zip code
     * and computes an integer that is used as a check digit on the zip
     * @param zipSum
     * @return an integer of the checkDigit for the zip that was computed
     */
    private static int numToEncode(int zipSum){
        int originalZipSum = zipSum;
        int remainder=zipSum%10;
        
        while(remainder!= 0){
            zipSum++;
            remainder=zipSum%10;
        }
        
        int numToEncode = zipSum-originalZipSum;
        return numToEncode;
    }
    
    /**
     * Method that takes in a list of digits of an entered zip code plus its
     * check digit and turns each digit into a binary string.
     * @param zipArray
     * @return a string of the binary representation of the zipCode+checkDigit string
     */
    private static String generateBinaryString(ArrayList<Integer> zipArray){
        String tempBinaryRep;
        String finalBinaryString= "1"; //starts with '1' representing the first guard bar
        int tempInZipArray;
        
        for(int i=0; i<zipArray.size(); i++){
            tempInZipArray=zipArray.get(i); //gets current number of the zip code
            tempBinaryRep = binaryRepresentations[tempInZipArray]; //gets the binary representation string of that digit
            finalBinaryString= finalBinaryString.concat(tempBinaryRep); //concatenates this string until barcode is complete
        }
        finalBinaryString= finalBinaryString.concat("1"); //final guard bar digit is added to barcode
       
        return finalBinaryString; //complete binary string ready to be changed into '|' and '.'
    }
    
    /**
     * Method that takes in the binary string and changes 1's to '|' and 0's to '.'
     * @param finalBinaryString
     * @return a string of the completed barcode
     */
    private static String generateBarcode(String finalBinaryString){
        int stringLength= finalBinaryString.length();
        String barcode = "";
        Character tempChar;
        
        for(int i=0; i<stringLength; i++){
            tempChar= finalBinaryString.charAt(i);
            if(tempChar== '1'){
                barcode = barcode.concat("|");
            }
            else{
               barcode = barcode.concat(".");
            }
        }
        return barcode;
    }
    
    /**
     * Method that is the driver to decode a user entered barcode into a zip code
     * @param stringEnteredBarcode
     * @return a string of the original zip code back to the main method
     */
    private static String beginDecode(String stringEnteredBarcode){
        String binaryString= binaryStringFromBarcode(stringEnteredBarcode);
        String originalZipCode= getOriginalZipCode(binaryString);

        return originalZipCode;
    }
    
    /**
     * Method to determine if the user entered barcode is valid
     * @param stringEnteredBarcode
     * @return boolean back to beginEncode() method if zip was valid or invalid
     */
    private static boolean isValidBarcode(String stringEnteredBarcode){
        boolean validityBool=true;
        int barcodeLength= stringEnteredBarcode.length();
        int beginBreak;
        int endBreak;
        String chunkToValidate;
        boolean validChunk;
        int checkFlag=0;
        String barcodeWithoutGuardBars= stringEnteredBarcode.substring(1,barcodeLength-1);
        Character firstCharacter= stringEnteredBarcode.charAt(0);
        Character lastCharacter= stringEnteredBarcode.charAt(barcodeLength-1);
        
        if(barcodeLength==32 || barcodeLength==52 || barcodeLength==62){
            if(firstCharacter == '|' && lastCharacter == '|'){
                for(int i=0; i< barcodeWithoutGuardBars.length(); i++){
                    if(i>0 && i%5==0){
                        beginBreak= i-5;
                        endBreak=i;
                        chunkToValidate= barcodeWithoutGuardBars.substring(beginBreak, endBreak);
                        validChunk= barcodeRepresentations.contains(chunkToValidate);
                        if(validChunk==false){
                            checkFlag++;
                        }    
                    }
                }
                String lastLeftChunk= barcodeWithoutGuardBars.substring(barcodeWithoutGuardBars.length()-5, barcodeWithoutGuardBars.length());
                validChunk= barcodeRepresentations.contains(lastLeftChunk);
                if(validChunk==false){
                    checkFlag++;
                }    
            }
        }
        else{
            checkFlag++;
        }
        
        if(checkFlag>0){
            validityBool=false;
        }

        return validityBool;
    }
    
    /**
     * Method to get the binary string (1s & 0s) from the barcode using '|' and '.'
     * @param barcode String of the barcode entered by the user
     * @return String of the binary string decrypted from the barcode
     */
    private static String binaryStringFromBarcode(String barcode){
        int barcodeLength= barcode.length();
        int indexToRemoveLastGuardBar = barcodeLength-1;
        
        //get the barcode without guard bars
        String originalBarcode = barcode.substring(1, indexToRemoveLastGuardBar);
        String originalBinaryString="";
        Character tempChar;
        
        for(int i=0; i<originalBarcode.length(); i++){
            tempChar= originalBarcode.charAt(i);
            if(tempChar== '|'){
                originalBinaryString = originalBinaryString.concat("1");
            }
            else{
               originalBinaryString = originalBinaryString.concat("0");
            }
        }
        //barcode has now been decoded from '|' and '.' to '1' and '0'
        return originalBinaryString; 
    }
    
    /**
     * Method that decoded the original barcode and returns the string of its decimal number representation
     * @param barcode user entered barcode
     * @return String of the original zip code of the barcode
     */
    private static String getOriginalZipCode(String barcode){  
        String curBinaryString;
        String stringChunk;
        String reversedZipString= "";
        String individualZipDigit;
        int strLength= barcode.length();
        int beginBreak;
        int endBreak;
        
        for(int i=0; i<strLength; i++){
            //this if statement will ensure that the check digit falls off because final index won't be divisible by 5
            if(i>0 && i%5==0){
                beginBreak= i-5; //beginning of a 5 digit string
                endBreak=i; //end of a five digit string
                stringChunk= barcode.substring(beginBreak, endBreak); //get binary chunk to convert to a digit
                
                for(int j=0; j<binaryRepresentations.length; j++){
                    curBinaryString= binaryRepresentations[j];
                    //if the binary chunk matches the digit representation then get that digit
                    if(stringChunk.equals(curBinaryString)){
                        individualZipDigit = Integer.toString(j);
                        reversedZipString= reversedZipString.concat(individualZipDigit); //concat decoded digit
                    }
                }
            }
        }
        return reversedZipString;
    }
}
