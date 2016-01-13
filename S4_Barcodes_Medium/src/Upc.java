
import java.util.ArrayList;

public class Upc extends BarcodeParent {
	private final static String[] upcLeftBinaryRepresentations = new String[10];
    private final static String[] upcRightBinaryRepresentations = new String[10];
    
    private final static ArrayList<String> upcLeftBarcodeRepresentations= new ArrayList<>();
    private final static ArrayList<String> upcRightBarcodeRepresentations= new ArrayList<>();
 
    Upc(){
        createBinaryRepresentations();
        createBarcodeRepresentations();
    }
    
    /**
     * Method that creates binary representations of numbers upon upc object instantiation
     */
    private static void createBinaryRepresentations(){
        upcLeftBinaryRepresentations[0]= "0001101";
        upcLeftBinaryRepresentations[1]= "0011001";
        upcLeftBinaryRepresentations[2]= "0010011";
        upcLeftBinaryRepresentations[3]= "0111101";
        upcLeftBinaryRepresentations[4]= "0100011";
        upcLeftBinaryRepresentations[5]= "0110001";
        upcLeftBinaryRepresentations[6]= "0101111";
        upcLeftBinaryRepresentations[7]= "0111011";
        upcLeftBinaryRepresentations[8]= "0110111";
        upcLeftBinaryRepresentations[9]= "0001011";
        
        upcRightBinaryRepresentations[0]= "1110010";
        upcRightBinaryRepresentations[1]= "1100110";
        upcRightBinaryRepresentations[2]= "1101100";
        upcRightBinaryRepresentations[3]= "1000010";
        upcRightBinaryRepresentations[4]= "1011100";
        upcRightBinaryRepresentations[5]= "1001110";
        upcRightBinaryRepresentations[6]= "1010000";
        upcRightBinaryRepresentations[7]= "1000100";
        upcRightBinaryRepresentations[8]= "1001000";
        upcRightBinaryRepresentations[9]= "1110100";
    }
    
    /**
    * Method that creates the list of barcode representations of digits
    */
    private static void createBarcodeRepresentations(){
        upcLeftBarcodeRepresentations.add("...||.|");
        upcLeftBarcodeRepresentations.add("..||..|");
        upcLeftBarcodeRepresentations.add("..|..||");
        upcLeftBarcodeRepresentations.add(".||||.|");
        upcLeftBarcodeRepresentations.add(".|...||");
        upcLeftBarcodeRepresentations.add(".||...|");
        upcLeftBarcodeRepresentations.add(".|.||||");
        upcLeftBarcodeRepresentations.add(".|||.||");
        upcLeftBarcodeRepresentations.add(".||.|||");
        upcLeftBarcodeRepresentations.add("...|.||");
        
        upcRightBarcodeRepresentations.add("|||..|.");
        upcRightBarcodeRepresentations.add("||..||.");
        upcRightBarcodeRepresentations.add("||.||..");
        upcRightBarcodeRepresentations.add("|....|.");
        upcRightBarcodeRepresentations.add("|.|||..");
        upcRightBarcodeRepresentations.add("|..|||.");
        upcRightBarcodeRepresentations.add("|.|....");
        upcRightBarcodeRepresentations.add("|...|..");
        upcRightBarcodeRepresentations.add("|..|...");
        upcRightBarcodeRepresentations.add("|||.|..");
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
        encodeMenu.add("Please enter an 11 digit product code.");
        encodeMenu.add("Please enter the integers only.");
        
        ArrayList<String> decodeMenu= new ArrayList<>();
        decodeMenu.add("Please enter a valid UPC-A barcode.");
        if(encodeOrDecodeSelection == 3){
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
     * @param userEntry string of either a upc barcode or product code to be validated
     * @return a boolean indicating is the string entered was valid or not
     */
    @Override
    public boolean isValidEncodeOrDecode(String userEntry, int encodeOrDecode){
        boolean validityCheck;
        if(encodeOrDecode==3){
            validityCheck= isValidProductCode(userEntry);
        }
        else{
            validityCheck= isValidUpcBarcode(userEntry);
        }
        return validityCheck;
    }
    
    /**
     * Method to call the process to either encode a product code or decode a barcode
     * to encode or decode
     * @param encodeOrDecode integer indicating the need to encode or decode
     * @param userEntry string of either a upc barcode or product code to be validated
     * @return a string of an encoded product code or decode barcode back to main method
     */
    @Override
    public String beginProcess(String userEntry, int encodeOrDecode){
        String userOutput;
        if(encodeOrDecode==3){
            userOutput= beginEncode(userEntry);
        }
        else{
            userOutput= beginDecode(userEntry);
        }
        return userOutput;
    }
    
    /**
     * Method to get the label next to the returned barcode or product code when printed in main method
     * to encode or decode
     * @param encodeOrDecode integer indicating the need to encode or decode
     * @return a string to display as a label next to the returned barcode or product code
     */
    @Override
    public String returnLabel(int encodeOrDecode){
        String upcEncodeLabel= "UPC-A Bar Code: ";
        String originalProductCode= "Original Product Code: ";
        if(encodeOrDecode==3){
            return upcEncodeLabel;
        }
        else{
            return originalProductCode;
        }
    }
    
    /**
     * Method that is the driver to encode a user entered product code into a barcode
     * @param stringEnteredProductCode
     * @return string of barcode that was generated
     */
    private static String beginEncode(String stringEnteredProductCode) {
        String binaryString= productCodeToBinaryString(stringEnteredProductCode);
        String barcode= generateBarcode(binaryString);
        
        return barcode;
    }
    
    /**
     * Method to determine if the user entered product code input is valid
     * @param stringEnteredProductCode product code entered by user
     * @return boolean back to beginEncode() method if product code was valid or invalid
     */
    private static boolean isValidProductCode(String stringEnteredProductCode){
        boolean validityCheck=false;
        int prodCodeLength= stringEnteredProductCode.length();
        if(prodCodeLength==11){
            try{
                //if parse works then string is clearly an integer
                Long.parseLong(stringEnteredProductCode);
                validityCheck= true;
            }
            catch(Exception ex){
            }
        }
        
        return validityCheck;
    }

    /**
     * Method to break the product code string into digits and put the digits into an ArrayList
     * and then calls the method to compute the check digit, which then calls the method
     * to turn the product decimal string into a binary string 
     * @param stringEnteredProductCode string of valid digits for a product code
     * @return a string of the binary number representation of the product code back beginEncode()
     */
    private static String productCodeToBinaryString(String stringEnteredProductCode){
        ArrayList<Integer> upcArray= new ArrayList<>();
        int zipLength = stringEnteredProductCode.length();
        Character tempChar;
        int tempInt;
        
        for(int i=0; i<zipLength; i++){
            tempChar= stringEnteredProductCode.charAt(i);
            if(tempChar != '+'){
                tempInt = Character.getNumericValue(stringEnteredProductCode.charAt(i));
                upcArray.add(tempInt);
            }
        }
        int checkDigit= checkDigit(upcArray); //calls check digit method to get the check digit integer
        upcArray.add(checkDigit); //adds check digit integer to upc Array list 
        
        //calls the 'generateBinaryString(upcArray)' method that returns the product code in a binary string
        String binaryString= generateBinaryString(upcArray); 
        
        return binaryString; //returns a binary string back to the 'beginEncode()' method
    }
    
    /**
     * Method that takes in the upc Arraylist of digits in the product code
     * and computes an integer that is used as a check digit on the product code
     * @param upcArray
     * @return an integer of the checkDigit to add onto the end of the product code
     */
    private static int checkDigit(ArrayList<Integer> upcArray){
        int checkDigit;
        int curInt;
        int checkDigitComputationStep1=0;
        int checkDigitComputationStep2=0;
        int checkDigitComputationStep3;
        
        for(int i=0; i<upcArray.size(); i++){
            curInt=upcArray.get(i);
            //check if digit is even or odd
            if(i%2==0){
                checkDigitComputationStep1+=curInt;
            }
            else{
                checkDigitComputationStep2+=curInt;
            }
        }
        checkDigitComputationStep1= checkDigitComputationStep1 * 3;
        checkDigitComputationStep3= checkDigitComputationStep1 + checkDigitComputationStep2;
        checkDigitComputationStep3= checkDigitComputationStep3 % 10;
        
        if(checkDigitComputationStep3 !=0){
            checkDigit= 10-checkDigitComputationStep3;
        }
        else{
            checkDigit=checkDigitComputationStep3;
        }
        return checkDigit;
    }
    
    /**
     * Method that takes in a list of digits of an entered upc product code plus its
     * check digit and turns each digit into a binary string.
     * @param upcDigitArray
     * @return a string of the binary representation of the product code string
     */
    private static String generateBinaryString(ArrayList<Integer> upcDigitArray){
        String tempBinaryRep;
        String finalBinaryString= "101"; //guard bar on left side
        int curNumInUpcDigitArray;
        
        for(int i=0; i<upcDigitArray.size(); i++){
            curNumInUpcDigitArray= upcDigitArray.get(i);
            //generating left side of the barcode
            if(i<6){
                tempBinaryRep = upcLeftBinaryRepresentations[curNumInUpcDigitArray];
                finalBinaryString= finalBinaryString.concat(tempBinaryRep);
            }
            //generating the center bridge of barcode
            else if(i==6){
                finalBinaryString= finalBinaryString.concat("01010");
                tempBinaryRep = upcRightBinaryRepresentations[curNumInUpcDigitArray];
                finalBinaryString= finalBinaryString.concat(tempBinaryRep);
            }
            //generating the right side of the barcode
            else{
                tempBinaryRep = upcRightBinaryRepresentations[curNumInUpcDigitArray];
                finalBinaryString= finalBinaryString.concat(tempBinaryRep);
            }
        }
        
        finalBinaryString= finalBinaryString.concat("101"); //adding guard bar on right side
        return finalBinaryString;
    }
    
    /**
     * Method that takes in the binary string and changes 1's to '|' and 0's to '.'
     * @param finalBinaryString binary string to be encoded
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
     * Method that is the driver to decode a user entered barcode into a product code
     * @param stringEnteredBarcode
     * @return a string of the decoded product code
     */
    private static String beginDecode(String stringEnteredBarcode){
        String decodedProductCode= decodedProductCode(stringEnteredBarcode);
        return decodedProductCode;
    }
    
    /**
     * Method that takes in the user input barcode and checks to see if it
     * is a valid upc barcode or not
     * @param upcEnteredBarcodeString String of user input upc barcode
     * @return boolean value indicating true for a valid barcode or false for an invalid barcode
     */
    private static boolean isValidUpcBarcode(String upcEnteredBarcodeString){
        boolean validityBool=true; //boolean value that gets returned
        int barcodeLength= upcEnteredBarcodeString.length(); 
        int checkFlag=0; //flag that checks for validity different places in the method
        String leftGuardBar= upcEnteredBarcodeString.substring(0,3);
        String middleGuardBar= upcEnteredBarcodeString.substring(45,50);
        String rightGuardBar= upcEnteredBarcodeString.substring(92,95);
        int beginBreak;
        int endBreak;
        String chunkToValidate;
        boolean validChunk;

        if(barcodeLength==95){
            if(leftGuardBar.equals("|.|") && middleGuardBar.equals(".|.|.") && rightGuardBar.equals("|.|")){
                String leftBarcode= upcEnteredBarcodeString.substring(3, 45);
                String rightBarcode= upcEnteredBarcodeString.substring(50, 92);
                
                for(int i=0; i< leftBarcode.length(); i++){
                    if(i>0 && i%7==0){
                        beginBreak= i-7;
                        endBreak=i;
                        chunkToValidate= leftBarcode.substring(beginBreak, endBreak);
                        validChunk= upcLeftBarcodeRepresentations.contains(chunkToValidate);
                        if(validChunk==false){
                            checkFlag++;
                        }    
                    }
                }
                //String lastLeftChunk= leftBarcode.substring(35, leftBarcode.length());
                String lastLeftChunk= leftBarcode.substring(leftBarcode.length()-7, leftBarcode.length());
                validChunk= upcLeftBarcodeRepresentations.contains(lastLeftChunk);
                if(validChunk==false){
                            checkFlag++;
                }    
                
                for(int i=0; i< rightBarcode.length(); i++){
                    if(i>0 && i%7==0){
                        beginBreak= i-7;
                        endBreak=i;
                        chunkToValidate= rightBarcode.substring(beginBreak, endBreak);
                        validChunk= upcRightBarcodeRepresentations.contains(chunkToValidate);
                        if(validChunk==false){
                            checkFlag++;
                        }     
                    }
                }
                //String lastRightChunk= rightBarcode.substring(35, rightBarcode.length());
                String lastRightChunk= rightBarcode.substring(rightBarcode.length()-7, rightBarcode.length());
                validChunk= upcRightBarcodeRepresentations.contains(lastRightChunk);
                if(validChunk==false){
                    checkFlag++;
                }
            }
            else{
                checkFlag++;
            }
            //check to see if any part of the input was invalid
            if(checkFlag>0){
                validityBool=false;
            }
        }
        else{
            validityBool=false;
        }
        
        //true if input was valid or false if invalid
        return validityBool;
    }
    
    /**
     * Method to convert barcode into a string of digits
     * @param upcEnteredBarcodeString
     * @return string of the original product code of the entered barcode
     */
    private static String decodedProductCode(String upcEnteredBarcodeString){
        String decodedProductCode="";
        ArrayList<Integer> decodedProductCodeArray= new ArrayList<>();
        int beginBreak;
        int endBreak;
        String chunkToValidate;
        int decodedNumber;
        
        String leftBarcode = upcEnteredBarcodeString.substring(3, 45);
        String rightBarcode = upcEnteredBarcodeString.substring(50, 92);

        for (int i = 0; i < leftBarcode.length(); i++) {
            if (i > 0 && i % 7 == 0) {
                beginBreak = i - 7;
                endBreak = i;
                chunkToValidate = leftBarcode.substring(beginBreak, endBreak);
                
                decodedNumber = upcLeftBarcodeRepresentations.indexOf(chunkToValidate);
                decodedProductCodeArray.add(decodedNumber);
            }
        }
        
        String lastLeftChunk = leftBarcode.substring(35, leftBarcode.length());
        decodedNumber = upcLeftBarcodeRepresentations.indexOf(lastLeftChunk);
        decodedProductCodeArray.add(decodedNumber);

        for (int i = 0; i < rightBarcode.length(); i++) {
            if (i > 0 && i % 7 == 0) {
                beginBreak = i - 7;
                endBreak = i;
                chunkToValidate = rightBarcode.substring(beginBreak, endBreak);
                
                decodedNumber = upcRightBarcodeRepresentations.indexOf(chunkToValidate);
                decodedProductCodeArray.add(decodedNumber);
            }
        }
        
        String curString;
        for(int i=0; i<decodedProductCodeArray.size(); i++){
            curString= Integer.toString(decodedProductCodeArray.get(i));
            decodedProductCode= decodedProductCode.concat(curString);
        }

        return decodedProductCode;
    }
}
