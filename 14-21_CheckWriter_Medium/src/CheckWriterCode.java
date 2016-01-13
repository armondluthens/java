
import java.util.HashMap;
import java.util.Scanner;
/**
 * Class that performs all the logic to create a string for a check from a numerical amount
 * @author armondluthens
 *
 */
public class CheckWriterCode {
	//create static hashmap that will be used for every object.  Map holds strings mapping integers to number strings
	private final static HashMap<String, String> integerToStringMap= new HashMap<String, String>(){
		{
			put("0", "ZERO");
			put("1", "ONE");
			put("2", "TWO");
			put("3","THREE");
			put("4","FOUR");
			put("5", "FIVE");
			put("6", "SIX");
			put("7", "SEVEN");
			put("8", "EIGHT");
			put("9", "NINE");
			put("10", "TEN");
			put("11", "ELEVEN");
			put("12", "TWELVE");
			put("13","THIRTEEN");
			put("14","FOURTEEN");
			put("15", "FIFTEEN");
			put("16", "SIXTEEN");
			put("17", "SEVENTEEN");
			put("18", "EIGHTEEN");
			put("19", "NINETEEN");
			put("20", "TWENTY");
			put("30","THIRTY");
			put("40","FORTY");
			put("50", "FIFTY");
			put("60", "SIXTY");
			put("70", "SEVENTY");
			put("80", "EIGHTY");
			put("90", "NINETY");
		}
	};
	
	/**
	 * Method that drives the class and sets the flow of methods in motion
	 */
	public void start(){
		String checkString= inputCheckAmount(); //calls method to input the numerical amount of the check
		System.out.println("String For Check: " + checkString);
	}
	
	/**
	 * Method that prompts the user to enter the numerical amount of their check
	 * @return String to be written for the amount on the check
	 */
	private static String inputCheckAmount(){
		boolean validInput=false;
		Scanner input = new Scanner(System.in);
		String checkAmount;
		
		do{
			System.out.println("Please provide the check amount less than $1000.00 in the form (xxx.xx): ");
			checkAmount = input.nextLine();
			validInput= checkInputValidation(checkAmount); //send input string to method to determine if input was valid
			if(validInput==false){
				System.out.println("INVALID INPUT. PLEASE TRY AGAIN.");
			}
		} while(validInput==false); //keep prompting user to enter their numeric amount until they enter it correctly
		input.close(); //close scanner
		
		System.out.println("Numerical Check Amount: $" + checkAmount); //print numerical amount of check to console
		String stringForCheck= writeCheck(checkAmount); //call method to write string of amount
		
		return stringForCheck;	
	}
	
	/**
	 * Method to determine if numerical check amount input was valid or not
	 * @param checkAmount
	 * @return boolean variable indicating valid or not
	 */
	private static boolean checkInputValidation(String checkAmount){
		boolean validity=false;
		
		try{
			int amountLength= checkAmount.length();
			int decimalPlace=amountLength-3;
			char Decimal= checkAmount.charAt(decimalPlace); 
			String decimalString= Character.toString(Decimal);
			
			//check to make sure the decimal is in the correct place otherwise whole input is wrong
			if(decimalString.equals(".")){
				checkAmount = checkAmount.replace(".", ""); //take decimal out of string
				int checkAmountInteger= Integer.parseInt(checkAmount); //parse string to make sure it is an integer
				if(checkAmountInteger<= 99999 && checkAmountInteger> 0){ //make sure amount doesn't exceed $1000.00 and is greater than $0.00
					validity=true;
				}
			}
		}
		catch(Exception ex){} //handles all other errors that may occur
		
		return validity;
	}
	
	/**
	 * Method to write the final string for the check amount
	 * @param checkAmount
	 * @return string to be written on check
	 */
	private static String writeCheck(String checkAmount){
		String checkAmountString="";
		int length= checkAmount.length();
		String cents= checkAmount.substring(length-2, length); //break string to get the cents amount
		String dollarAmount= checkAmount.substring(0, length-3); //break string to get the dollar amount
		dollarAmount= getDollarAmount(dollarAmount); //call method to get dollar amount string
		
		checkAmountString= dollarAmount+ " and " + cents + "/100"; //concatenate dollar amount and cents amount string
		
		return checkAmountString; //returns
	}
	
	/**
	 * Amount to get the string for the dollar amount of the string
	 * @param dollarAmount
	 * @return string to be written for the dollar amount back to 'writeCheck'
	 */
	private static String getDollarAmount(String dollarAmount){
		int length= dollarAmount.length();
		String hundredsPlace="";
		String tensPlace="";
		String onesPlace="";
		String twoDigitNum=""; //string after concatenating tens place and ones place
		String stringForCheck;
		
		//amount $100.00 or greater
		if(length==3){
			hundredsPlace= dollarAmount.substring(0, 1); //hundreds place digit
			tensPlace= dollarAmount.substring(1, 2); //tens place digit
			onesPlace= dollarAmount.substring(2); //ones place digit
			if(!hundredsPlace.equals("0")){
				hundredsPlace= integerToStringMap.get(hundredsPlace)+ " hundred"; //gets string for digit from map
			}
			else{
				hundredsPlace=""; //does nothing if '0' is in the hundreds place
			}
			//number case of 10-19
			if(tensPlace.equals("1")){ 
				twoDigitNum= tensPlace+onesPlace; 
				twoDigitNum= integerToStringMap.get(twoDigitNum);
			}
			//case: number in hundred with '0' in tens place
			else if(tensPlace.equals("0")){
				onesPlace= integerToStringMap.get(onesPlace);
				twoDigitNum= onesPlace;
			}
			else{
				tensPlace+="0"; //all other numbers with tens place > 1
				tensPlace= integerToStringMap.get(tensPlace);
				onesPlace= integerToStringMap.get(onesPlace);
				twoDigitNum=tensPlace+"-"+onesPlace; //concatenates the number with tens place name and ones place name
			}
			stringForCheck= hundredsPlace+" "+twoDigitNum; //final dollar amount string
		}
		
		//amount between $100.00 and $0.00
		else if(length==2){
			tensPlace= dollarAmount.substring(0, 1); //tens place digit
			onesPlace= dollarAmount.substring(1); //ones place digit
			//number case of 10-19
			if(tensPlace.equals("1")){
				twoDigitNum= tensPlace+onesPlace;
				twoDigitNum= integerToStringMap.get(twoDigitNum);
			}
			else{
				tensPlace+="0"; //all other numbers with tens place > 1
				tensPlace= integerToStringMap.get(tensPlace);
				onesPlace= integerToStringMap.get(onesPlace);
				twoDigitNum=tensPlace+"-"+onesPlace; //concatenates the number with tens place name and ones place name
			}
			stringForCheck= twoDigitNum; //final dollar amount string
		}
		//amount between $10.00 and $0.00
		else{
			onesPlace= dollarAmount;
			onesPlace= integerToStringMap.get(onesPlace);
			stringForCheck= onesPlace; //final dollar amount string
		}
		
		return stringForCheck; //returns final dollar amount to 'writeCheck'
	}
	
}
