/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barcodes;

import java.util.Scanner;
import java.util.*;
/**
 *
 * @author armondluthens
 */
public class Barcodes {
    public static String[] binaryRepresentations = new String[10];
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        createBinaryRepresentations();
        String stringEnteredZipCode;
        int numToEncode;
        boolean validityCheck;
        Scanner input = new Scanner(System.in);

        do{
            System.out.println("Please enter a ZIP, a ZIP+4, or a ZIP+4+delivery.");
            System.out.println("Please enter the integers only.");
            System.out.print("Entry: ");
            stringEnteredZipCode = input.next();
            validityCheck = isValidZip(stringEnteredZipCode);
        } while(validityCheck==false);
        
        ArrayList<Integer> zipArray=new ArrayList<>();
        zipArray= zipParse(stringEnteredZipCode);
        
        int zipSum = zipSum(zipArray);
        System.out.println("Zip sum= " + zipSum);
        
        numToEncode= numToEncode(zipSum);
        System.out.println("numToEncode: " + numToEncode);
        zipArray.add(numToEncode);
        
        String finalBinaryString= generateBinaryString(zipArray);
        String barcode= generateBarcode(finalBinaryString);
        System.out.println("Barcode: " + barcode);
        
        String originalBinaryString= binaryStringFromBarcode(barcode);
        System.out.println("\nThe original binary string was: " + originalBinaryString);
        
        String originalZipCode= getOriginalZipCode(originalBinaryString);
    }
    
    public static boolean isValidZip(String stringEnteredZipCode){
        boolean validityCheck=false;
        int zipLength = stringEnteredZipCode.length();
        if(zipLength==5 || zipLength==9){
            try{
                Integer.parseInt(stringEnteredZipCode);
                validityCheck= true;
            }
            catch(Exception ex){
                System.out.println("PLEASE ENTER A VALID ZIP CODE\n");
            }
        }
        else if(zipLength==11){
            try{
                Long.parseLong(stringEnteredZipCode);
                validityCheck= true;
            }
            catch(Exception ex){
                System.out.println("PLEASE ENTER A VALID ZIP CODE\n");
            }
        }
        else{
            System.out.println("PLEASE ENTER A VALID ZIP CODE\n");
        }
       return validityCheck;
    }
    
    public static ArrayList<Integer> zipParse(String enteredZipCode){
        ArrayList<Integer> zipArray= new ArrayList<>();
        int zipSum=0;
        int zipLength = enteredZipCode.length();
        Character tempChar;
        int tempInt;
        
        for(int i=0; i<zipLength; i++){
            tempChar= enteredZipCode.charAt(i);
            if(tempChar != '+'){
                tempInt = Character.getNumericValue(enteredZipCode.charAt(i));
                zipArray.add(tempInt);
            }
        }
        return zipArray;
    }
    
    public static int zipSum(ArrayList<Integer> zipArray){
        int zipSum=0;
        int curInt;
        
        for(int i=0; i<zipArray.size(); i++){
            curInt=zipArray.get(i);
            zipSum+=curInt;
        }
        return zipSum;
    }
    
    public static int numToEncode(int zipSum){
        int originalZipSum = zipSum;
        int remainder=zipSum%10;
        
        while(remainder!= 0){
            zipSum++;
            remainder=zipSum%10;
        }
        
        int numToEncode = zipSum-originalZipSum;
        return numToEncode;
    }
    
    public static void createBinaryRepresentations(){
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
    
    public static String generateBinaryString(ArrayList<Integer> zipArray){
        String tempBinaryRep;
        String finalBinaryString= "1";
        int tempInZipArray;
        
        for(int i=0; i<zipArray.size(); i++){
            tempInZipArray=zipArray.get(i);
            tempBinaryRep = binaryRepresentations[tempInZipArray];
            finalBinaryString= finalBinaryString.concat(tempBinaryRep);
        }
        
        finalBinaryString= finalBinaryString.concat("1");
        System.out.println("Final Binary String: " + finalBinaryString);
        
        return finalBinaryString;
    }
    
    public static String generateBarcode(String finalBinaryString){
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
    
    
    //REVERSING FROM BARCODE TO ZIP
    public static String binaryStringFromBarcode(String barcode){
        int barcodeLength= barcode.length();
        int indexToRemoveLastGuardBar = barcodeLength-1;
        String originalBarcode = barcode.substring(1, indexToRemoveLastGuardBar);
        String originalBinaryString= "";
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
        
        return originalBinaryString;
    }
    public static String getOriginalZipCode(String barcode){  
        String curBinaryString;
        String stringChunk;
        String finalChunk;
        String reversedZipString= "";
        String individualZipDigit;
        int strLength= barcode.length();
        int finalChunkEnd = strLength-1;
        int finalChunkBegin= finalChunkEnd-5;
        int beginBreak;
        int endBreak;
        
        for(int i=0; i<strLength; i++){
            if(i>0 && i%5==0){
                beginBreak= i-5;
                endBreak=i;
                stringChunk= barcode.substring(beginBreak, endBreak);
                
                for(int j=0; j<binaryRepresentations.length; j++){
                    curBinaryString= binaryRepresentations[j];
                    if(stringChunk.equals(curBinaryString)){
                        individualZipDigit = Integer.toString(j);
                        reversedZipString= reversedZipString.concat(individualZipDigit);
                    }
                }
                //System.out.println("String Chunk: " + stringChunk);
            }
        }
        
        finalChunk= barcode.substring(finalChunkBegin, finalChunkEnd);
        //System.out.println("Final Chunk: " + finalChunk);
        System.out.println("reversed zip string is: " + reversedZipString);
        
        return reversedZipString;
    }

}  //End Barcodes class
