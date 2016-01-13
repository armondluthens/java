import java.util.*;

/**
 * Class that holds all of the methods for sorting a one dimensional array.  Upon creation of a BucketSort object
 * the constructor either receives an integer array and sets it equal to the array class instance variable. The test
 * class then invokes the start method which controls the class and calls the necessary methods to sort the integer array.
 * After the array is sorted, a print method prints the unsorted and sorted array to the console.
 */
public class BucketSort {
	private final int[] randomNumberArray;
	
	/**
	 * Constructor with no integer array passed in, which calls the method to generate a random number array
	 */
	BucketSort(){
		randomNumberArray= generateRandomArray(); 
	};
	
	/**
	 * Overloaded constructor for BucketSort object if user passes array in
	 * @param randomArray is an array of integers to be sorted
	 */
	BucketSort(int[] randomArray){
		this.randomNumberArray= randomArray;
	}
	
	/**
	 * Method to set other methods in motion and calls all necessary methods
	 */
	public void start(){
		int numDigits= largestNumberLength(randomNumberArray); //returns the length of the largest number in the array
		int[] gatheringPassArray= randomNumberArray; //sets gathering pass array equal to initial unsorted array
		gatheringPassArray= getFinalGatheringPassArray(numDigits, gatheringPassArray); //calls method to get final gathering pass array
		printArrays(randomNumberArray, gatheringPassArray); //prints unsorted and sorted arrays
	}
	
	/**
	 * Method to keep running the distributionPass method as many times as the length of the greatest integer in the initial array
	 * @param numDigits
	 * @param gatheringPassArray
	 * @return the final sorted integer array 
	 */
	private static int[] getFinalGatheringPassArray(int numDigits, int[] gatheringPassArray){
		//keeps calling distributionPass method as many times as the length of the biggest integer in the array
		for(int i=1; i<= numDigits; i++){
			gatheringPassArray= distributionPass(gatheringPassArray, i);
		}
		return gatheringPassArray;
	}
	
	/**
	 * Generates an array of random integers between 0 and 100 (for this example)
	 * Length of array and range of numbers can be changed (problem statement did not specify)
	 * @return an array of integers that is unsorted
	 */
	private static int[] generateRandomArray(){
		int listSize=6; //size of array to create
		int minimum=1; //minimum number of range
		int max=101; //max number of range
		int[] randomNumberArray = new int[listSize];
		Random randomNum = new Random(); //random number object of 'Random' class
		int currentRandomInt;
		for(int i=0; i<listSize; i++){
			currentRandomInt = randomNum.nextInt((max - minimum) +1) + 1; //generates random numbers from 1 to 100
			randomNumberArray[i]= currentRandomInt; //adds random number to list
		}	
		return randomNumberArray; 
	}
	
	/**
	 * Takes in an array of integers and finds the length (number of digits) of the largest integer
	 * @param randomNumberArray
	 * @return an integer of the number of digits of the largest integer
	 */
	private static int largestNumberLength(int[] randomNumberArray){
		int largestLength=0;
		int largestNum=0;
		String largestNumString;
		//loop to find the largest integer in array
		for(int i=0; i< randomNumberArray.length; i++){
			if(randomNumberArray[i]>= largestNum){
				largestNum= randomNumberArray[i];
			}
		}
		
		largestNumString= Integer.toString(largestNum); //changes largest number integer to a string
		largestLength= largestNumString.length(); //gets length of largest integer
		return largestLength;
	}
	
	/**
	 * DISTRIBUTION PASS (PART A)
	 * Iterates through an array and finds the rightmost digit (that has not already been iterated on)
	 * and places the entire number into the corresponding bucket of the rightmost digit. Method subsequently
	 * passes the 2-dimensional array with buckets filled to the gatheringPass method
	 * @param randomNumberArray
	 * @param iterationNumber
	 * @return an integer array returned from the gatheringPass Method on the current iteration
	 */
	private static int[] distributionPass(int[] randomNumberArray, int iterationNumber){
		int randomNumberArrayLength= randomNumberArray.length;
		int n = randomNumberArrayLength; //number of columns in 2d bucket array
		int[][] bucketArray = new int[10][n];
		int curNum;
		int row=0;
		int col=0;
		int curNumCopy;
		
		for(int i=0; i<randomNumberArrayLength; i++){
			curNum= randomNumberArray[i]; //number at current place in array
			curNumCopy= curNum; //variable copy to be manipulated
			row= curNumCopy % 10; //rightmost digit
			
			//for loop to get successive digits for numbers greater than one digit
			for(int k=1; k<iterationNumber; k++){
				curNumCopy= curNumCopy/10; 
				row= curNumCopy % 10; //produces the correct row (bucket) to place the current number
			}
			//loop through the columns for current row in 2D BucketArray to find an index that is not filled
			for(int j=0; j<n; j++){
				if(bucketArray[row][j]==0){ //index is not filled by a number
					col=j; //stores the free column index
					j=n; //breaks out of loop
				}
			}
			bucketArray[row][col]= curNum; //adds the number to appropriate row and column in 2D bucketArray
		}
		int[] gatheringPassArray= gatheringPass(bucketArray, randomNumberArrayLength); //calls gatheringPass method
		return gatheringPassArray;
	}
	

	/**
	 * GATHERING PASS (PART B)
	 * Passes in current 2D array and the length of the initial integer array and loops through 
	 * the 2D array and gets the number at each index and copies them back to a one dimensional number array
	 * @param bucketArray
	 * @param randomArrayLength
	 * @return an integer array
	 */
	private static int[] gatheringPass(int[][] bucketArray, int randomArrayLength){
		int rowsInBucketArray= bucketArray.length;
		int columnsInBucketArray= randomArrayLength;
		int curNum;
		int[] gatheringPassArray = new int[randomArrayLength];
		int placeInGatheringPassArray= 0;
		
		for(int i=0; i<rowsInBucketArray; i++){
			for(int j=0; j< columnsInBucketArray; j++){
				curNum= bucketArray[i][j]; //gets current number in bucket array
				if(curNum != 0){ //verifies the number is valid and not an unfilled index
					gatheringPassArray[placeInGatheringPassArray]= curNum; //adds number back to array
					placeInGatheringPassArray++; //increments placeholder counter
				}
			}
		}
		return gatheringPassArray;
	}
	
	/**
	 * Method to print the initial unsorted array and the sorted array
	 * @param unsortedArray
	 * @param sortedArray
	 */
	private static void printArrays(int[] unsortedArray, int[] sortedArray){
		int lastElementUnsorted= unsortedArray.length-1; //gets last number in unsorted array for printing purposes
		System.out.println("------------------------------------------------");
		System.out.print("Unsorted Array: [");
		for(int a=0; a<unsortedArray.length; a++){
			if(a != lastElementUnsorted){
				System.out.print(unsortedArray[a] + ", ");
			}
			else{
				System.out.print(unsortedArray[a] + "]");
			}
		}
		System.out.println("\n------------------------------------------------");
		
		int lastElementSorted= sortedArray.length-1; //gets last number in sorted array for printing purposes
		System.out.print("Sorted Array: [");
		for(int b=0; b<sortedArray.length; b++){
			if(b != lastElementSorted){
				System.out.print(sortedArray[b] + ", ");
			}
			else{
				System.out.print(sortedArray[b] + "]");
				System.out.println("\n------------------------------------------------\n\n");
			}
		}
	}
}


