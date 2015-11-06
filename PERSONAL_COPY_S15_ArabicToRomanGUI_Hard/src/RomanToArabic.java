
import java.awt.FlowLayout; // specifies how components are arranged
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.util.HashMap;

//import java.awt.*;

//disregard serializable error
public class RomanToArabic extends JFrame {
	private final JTextArea textAreaRoman; //variable to create a JTextArea
	private final JTextArea textAreaArabic; //variable to create a JTextArea
	private final JLabel romanLabel; //label for Roman JTextArea
	private final JLabel arabicLabel; //label for Arabic JTextArea
	private final static HashMap<String, Integer> romanNumeralMappedToNumbersMap= new HashMap<>();
	private final static HashMap<Integer, String> numbersMappedToRomanNumeralsMap= new HashMap<>(); 
	
	/**
	 * Default constructor of the RomanToArabic JFrame
	 */
	public RomanToArabic(){
		super("Roman To Arabic Converter");
		setLayout(new FlowLayout());
		constructRomanToNumbersMap();
		constructNumbersToRomanMap();
		
		romanLabel= new JLabel("Roman Numeral:\n");
		add(romanLabel);
		//Area for user to enter Roman Numeral
		textAreaRoman= new JTextArea(2, 20);
		add(textAreaRoman);

		arabicLabel= new JLabel("Arabic Number:\n");
		add(arabicLabel);
		//Area where Arabic number is displayed after being converted from a Roman numeral
		textAreaArabic= new JTextArea(2, 20);
		add(textAreaArabic);

		//register event handler for Roman Text Area
		RomanKeyListen romanKey = new RomanKeyListen();
		textAreaRoman.addKeyListener(romanKey);
		
		//register event handler for Roman Text Area
		ArabicKeyListen arabicKey = new ArabicKeyListen();
		textAreaArabic.addKeyListener(arabicKey);
	} //end of RomanToArabic default constructor

	/**
	 * Class that listens for a key to be entered (assumed to be valid) in 
	 * the Roman Numeral JTextArea and when a Roman Numeral is entered, it will be 
	 * dynamically converted and displayed as an Arabic Number in the Arabic JTextArea
	 */
	private class RomanKeyListen implements KeyListener {
		@Override
		//will dynamically compute conversion and display
		public void keyTyped(KeyEvent keyListenerEvent) {}

		@Override
		//will compute conversion when user presses 'Enter' button on keyboard
		public void keyPressed(KeyEvent keyListenerEvent){}

		@Override
		//key released method needed from abstract method
		public void keyReleased(KeyEvent keyListenerEvent){
			String textEnteredByUserInRoman = textAreaRoman.getText(); 
			String arabicNumber= convertRomanNumeralToArabic(textEnteredByUserInRoman); 
			textAreaArabic.setText(arabicNumber);
		}
	}
	
	/**
	 * Class that listens for a key to be entered (assumed to be valid) in 
	 * the Arabic JTextArea and when a number is entered, it will be dynamically
	 * converted and displayed as a Roman Numeral in the Roman Numeral JTextArea
	 */
	private class ArabicKeyListen implements KeyListener {
		@Override
		//will dynamically compute conversion and display
		public void keyTyped(KeyEvent keyListenerEvent) {}

		@Override
		//will compute conversion when user presses 'Enter' button on keyboard
		public void keyPressed(KeyEvent keyListenerEvent){}

		@Override
		//key released method needed from abstract method
		public void keyReleased(KeyEvent keyListenerEvent){
			String textEnteredByUserInArabic = textAreaArabic.getText(); 
			String romanNumeral= convertArabicToRomanNumeral(textEnteredByUserInArabic); 
			textAreaRoman.setText(romanNumeral);
		}
	}
	
/*************************************************************************************
*					ROMAN TO ARABIC
*************************************************************************************

		/**
		 * Method to build a map of the Roman numerals in which:
		 * Key: String of the Roman Numeral value
		 * Value: Integer value of the Roman Numeral
		*/
		private static void constructRomanToNumbersMap(){
			romanNumeralMappedToNumbersMap.put("I", 1);
			romanNumeralMappedToNumbersMap.put("IV", 4);
			romanNumeralMappedToNumbersMap.put("V", 5);
			romanNumeralMappedToNumbersMap.put("IX", 9);
			romanNumeralMappedToNumbersMap.put("X", 10);
			romanNumeralMappedToNumbersMap.put("XL", 40);
			romanNumeralMappedToNumbersMap.put("L", 50);
			romanNumeralMappedToNumbersMap.put("XC", 90);
			romanNumeralMappedToNumbersMap.put("C", 100);
			romanNumeralMappedToNumbersMap.put("CD", 400);
			romanNumeralMappedToNumbersMap.put("D", 500);
			romanNumeralMappedToNumbersMap.put("CM", 900);
			romanNumeralMappedToNumbersMap.put("M", 1000);
		} //end of constructRomanToNumbersMap method

		/**
		 * Method to convert a Roman Numeral input String to an Arabic Number
		 * @param romanNumeralToConvert user entered
		 * @return String of the converted Arabic Number
		 */
		private static String convertRomanNumeralToArabic(String romanNumeralToConvert){
			int romanNumeralLength= romanNumeralToConvert.length();
			String currentDigitString; //gets the string value of the current char
			int numberForCurrentDigit=0; //gets the number of the the letter that is representing it in the map
			int numberForNextDigit; 
			String nextDigitString;
			int j; //placeholder variable
			int countForConstructingRomanNumeral=0; //adding the numbers throughout method to get to final result
			String twoLetterNumeral; //if a numeral is determined to be a two letter numeral (i.e. IV, IX, etc.)

			for(int i=0; i<romanNumeralLength; i++){
				j=i+1;
				//check that the roman numeral is greater than one letter and it is not the last character in entered string
				if(i != romanNumeralLength-1 && romanNumeralLength>1){
					currentDigitString= romanNumeralToConvert.substring(i, j); //gets the current digit as a string
					nextDigitString= romanNumeralToConvert.substring(j, j+1); //gets the next digit as a string

					if(romanNumeralMappedToNumbersMap.containsKey(currentDigitString)  && romanNumeralMappedToNumbersMap.containsKey(nextDigitString)){
						numberForCurrentDigit= romanNumeralMappedToNumbersMap.get(currentDigitString); //gets integer for the current number from map
						numberForNextDigit= romanNumeralMappedToNumbersMap.get(nextDigitString); // gets integer for the next number from map

						//if the next number is greater than the first, it MUST be a two character numeral
						if(numberForNextDigit > numberForCurrentDigit){
							twoLetterNumeral= currentDigitString+nextDigitString; //concats current and next
							numberForCurrentDigit= romanNumeralMappedToNumbersMap.get(twoLetterNumeral); //gets integer value
							countForConstructingRomanNumeral+= numberForCurrentDigit; //adds to the addition
							i+=1; //must add one to compensate for the two letter numeral
						}
						else{
							countForConstructingRomanNumeral+= numberForCurrentDigit; //not a two letter roman numeral
						}
					}
				}
				else{
					currentDigitString= romanNumeralToConvert.substring(i); //last character in string
					if(romanNumeralMappedToNumbersMap.containsKey(currentDigitString)){
						countForConstructingRomanNumeral+= romanNumeralMappedToNumbersMap.get(currentDigitString);
					}
				}
			}

			String arabicNumber = Integer.toString(countForConstructingRomanNumeral); //turn Arabic number to a string
			return arabicNumber;
		} //end of convertRomanNumeralToArabic Method
		
		
/*************************************************************************************
*					ARABIC TO ROMAN
*************************************************************************************
		/**
	     * Method to build a map mapping integers to Roman Numerals
	     * Key: Integer of numbers specific to properties of Roman Numerals
	     * Value: Roman Numeral strings corresponding to integer key
	    */
	    private static void constructNumbersToRomanMap(){
	    	numbersMappedToRomanNumeralsMap.put(1, "I");
	    	numbersMappedToRomanNumeralsMap.put(4, "IV");
	    	numbersMappedToRomanNumeralsMap.put(5,"V");
	    	numbersMappedToRomanNumeralsMap.put(9,"IX");
	    	numbersMappedToRomanNumeralsMap.put(10, "X");
	    	numbersMappedToRomanNumeralsMap.put(40, "XL");
	    	numbersMappedToRomanNumeralsMap.put(50, "L");
	    	numbersMappedToRomanNumeralsMap.put(90, "XC");
	    	numbersMappedToRomanNumeralsMap.put(100, "C");
	    	numbersMappedToRomanNumeralsMap.put(400, "CD");
	    	numbersMappedToRomanNumeralsMap.put(500, "D");
	    	numbersMappedToRomanNumeralsMap.put(900, "CM");
	    	numbersMappedToRomanNumeralsMap.put(1000, "M");
	    } //end of constructNumbersToRomanMap method
	    
	    /**
	     * Method to convert an Arabic Number input into a Roman Numeral
	     * @param inputString a valid integer string or the Arabic number to be converted into a Roman Numeral
	     * @return a string of the Roman Numeral that the Arabic Number was converted to
	     */
		private static String convertArabicToRomanNumeral(String inputString){
	        int input = Integer.parseInt(inputString);
	        String romanNumeralChunk;
	        String romanNumeralString="";

	        while(input>0){
	            if(input >= 1000){
	                romanNumeralChunk= getRomanNumeralChunk(1000);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=1000;
	            }

	            else if(input >= 900 && input < 1000){
	                romanNumeralChunk= getRomanNumeralChunk(900);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=900;
	            }
	            else if(input >= 500 && input < 900){
	                romanNumeralChunk= getRomanNumeralChunk(500);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=500;
	            }
	            else if(input >= 400 && input < 500){
	                romanNumeralChunk= getRomanNumeralChunk(400);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=400;
	            }
	            else if(input >= 100 && input < 400){
	                romanNumeralChunk= getRomanNumeralChunk(100);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=100;
	            }
	            else if(input >= 90 && input < 100){
	                romanNumeralChunk= getRomanNumeralChunk(90);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=90;
	            }
	            else if(input >= 50 && input < 90){
	                romanNumeralChunk= getRomanNumeralChunk(50);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=50;
	            }
	            if(input >= 40 && input < 50){
	                romanNumeralChunk= getRomanNumeralChunk(40);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=40;
	            }
	            if(input >= 10 && input < 40){
	                romanNumeralChunk= getRomanNumeralChunk(10);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=10;
	            }
	            if(input == 9){
	                romanNumeralChunk= getRomanNumeralChunk(9);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=9;
	            }
	            if(input >= 5 && input < 9){
	                romanNumeralChunk= getRomanNumeralChunk(5);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=5;
	            }
	            if(input == 4){
	                romanNumeralChunk= getRomanNumeralChunk(4);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=4;
	            }
	            else if(input> 0 && input < 4){
	                romanNumeralChunk= getRomanNumeralChunk(1);
	                romanNumeralString= romanNumeralString.concat(romanNumeralChunk);
	                input-=1;
	            }
	        }  //end while loop
	        
	        return romanNumeralString;
	    } //end arabicToRomanNumeral Method
	    
		/**
		 * Method that takes in an integer and returns its Roman Numeral if it is valid
		 * @param numberToBeConverted an integer between 0 and 4000
		 * @return a string of the Roman Numeral corresponding to the integer that was passed in
		 */
	    private static String getRomanNumeralChunk(int numberToBeConverted){
	       String romanNumeralChunk="";
	       if(numbersMappedToRomanNumeralsMap.containsKey(numberToBeConverted)){
	           romanNumeralChunk=numbersMappedToRomanNumeralsMap.get(numberToBeConverted);
	       }
	       return romanNumeralChunk;
	    }
	    

	} //end of public class RomanToArabic
