import java.util.ArrayList;

public class BarcodeParent {
    /**
     * Default Method to return the proper menu based on what the user wants 
     * to encode or decode
     * @param encodeOrDecode
     * @return an array list of strings of the proper menu
     */
    public ArrayList<String> encodeOrDecodeMenu(int encodeOrDecode){
        ArrayList<String> list= new ArrayList<>();
        return list;
    }
    
    /**
     * Default method to return the proper menu based on what the user wants 
     * to encode or decode
     * @param encodeOrDecode integer indicating the need to encode or decode
     * @param userEntry string of either a barcode or or code to be validated
     * @return a boolean indicating is the string entered was valid or not
     */
    public boolean isValidEncodeOrDecode(String userEntry, int encodeOrDecode){
        boolean validityCheck=false;
        System.out.println("barcode parent");
        return validityCheck;
    }
    
    /**
     * Method to call the process to either encode a zip/product code or decode a barcode
     * to encode or decode
     * @param encodeOrDecode integer indicating the need to encode or decode
     * @param userEntry string of either a posnet barcode or zip code to be validated
     * @return a string of an encoded zip/product code or decode barcode back to main method
     */
    public String beginProcess(String userEntry, int encodeOrDecode){
        String userOutput="";
        return userOutput;
    }
    
    /**
     * Method to get the label next to the returned barcode or code when printed in main method
     * to encode or decode
     * @param encodeOrDecode integer indicating the need to encode or decode
     * @return a string to display as a label next to the returned barcode or code
     */
    public String returnLabel(int encodeOrDecode){
        String defaultLabel= "";
        return defaultLabel;
    }
}
