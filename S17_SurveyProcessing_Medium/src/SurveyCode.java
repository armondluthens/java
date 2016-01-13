
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * This class contains the logic for all user prompts and taking in user entries, reading in
 * survey data, creating an array of objects to store the data, and for filtering the data and
 * writing to a location and file for the user
 * @author armondluthens
 */
public class SurveyCode {
	
	/**
	 * Method that drives the methods of Survey class. This method calls for all the user inputs, and then
	 * calls the methods to read the data in, and the method the write the data to the output location.
	 */
	public void start(){
		String filename= getFileName();
		//String filename= "/Users/armondluthens/Desktop/S17-SurveyResults.csv"; //Local Test
		//String filename= "src/SurveyResults.txt"; //Project Directory File
		ArrayList<SurveyObjects> surveyTable= readDataIn(filename);
		
		String filter= getFilter();
		String demographic= getDemographic();
		
		int[] filteredData= filterData(surveyTable, filter, demographic);
		
		
		String outputFile = getOutputFile();
		//String outputFile = "/Users/armondluthens/Desktop/ArmondTest.csv"; //Local Output
		//String outputFile= "src/TestOutput.csv";
		writeOutputFile(outputFile, filteredData);
	}
	
	/**
	 * Method that prompts user to enter a location/filename to import survey data from
	 * @return string of a location/filename to get survey data from
	 */
	private static String getFileName(){
		Scanner input = new Scanner( System.in );
		String filename;
		boolean pathExists=false;
		do{
			System.out.println("Please provide the input file of source data: ");
			filename = input.nextLine();
			File pathCheck= new File(filename); //creates new file with input path
			pathExists= pathCheck.exists(); //checks to see if file path is valid
			if(pathExists==false){
				System.out.println("FILE PATH DOES NOT EXIST");
			}
		}while(pathExists==false);
		return filename;
	}
	
	/**
	 * Method that prompts user to enter a  product to filter data on
	 * @return string of a location/filename to get survey data from
	 */
	private static String getFilter(){
		Scanner input = new Scanner( System.in );
		System.out.println("Please enter the product to filter on: ");
		String filter = input.nextLine();
		filter= filter.toLowerCase();
		
		return filter;
	}
	
	/**
	 * Method that prompts user to enter a  demographic to filter data on
	 * @return string of a location/filename to get survey data from
	 */
	private static String getDemographic(){
		Scanner input = new Scanner( System.in );
		System.out.println("Please enter a demographic attribute to filter on. Enter a value contained in any"
				+ "of the following columns [Gender | Age Group | State of Residence]:");
		String demographic = input.nextLine();
		demographic= demographic.toLowerCase();
		
		return demographic;
	}
	
	/**
	 * Method that prompts user to enter a location/filename to write filtered data results to
	 * @return string of a location/filename to get survey data from
	 */
	private static String getOutputFile(){
		Scanner input = new Scanner( System.in );
		String outputFile=null;
		boolean valid=false;
		do{
			System.out.println("Provide the output csv location: ");
			outputFile = input.nextLine();
			Path path = Paths.get(outputFile); //create object of type Path
			if(Files.exists(path)==true){ //check to make sure path is valid file location
				valid=true;
			}
			else{
				System.out.println("Invalid output location");
			}
		} while(valid==false);
		

		return outputFile;
	}
	
	/**
	 * Method to read data in from input file and create an array of objects of the survey information
	 * @param filepath
	 * @return an ArrayList of objects containing the survey data
	 */
	private static ArrayList<SurveyObjects> readDataIn(String filepath){
		String currentLine;
		String delimiter= ",";
		ArrayList<SurveyObjects> surveyTable= new ArrayList<>();
		int index=0;
		
		try {
			//create BufferedReader object that reads blocks of characters from the FileReader of the input path
			FileReader fileReader= new FileReader(filepath);
			BufferedReader buffReader = new BufferedReader(fileReader); 
			
			//reads all data in line by line
			while ((currentLine = buffReader.readLine()) != null) {
				//create string array for each line of the data file, with a "," distinguishing each new index
				String[] fields = currentLine.split(delimiter); 
				fields[0]= fields[0].toLowerCase(); //changing the string in the gender field to lower-case 
				
				//pass each value of the headings array in as parameters of the survey object constructor
				SurveyObjects individual= new SurveyObjects(fields[0], fields[1], fields[2], fields[3], fields[4]);
				//System.out.println(fields[0] + " " + fields[1] + " " + fields[2] + " " + fields[3] + " " + fields[4]);
				if(index>0){
					//adds all lines except the headings in line 0 to surveyTable arraylist
					surveyTable.add(individual);
				}
				index++;
			}
			System.out.println("Successfully read in file");
			buffReader.close();
		}
		
		catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND, BAD PATH");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IO ERROR");
			System.exit(0);
		} 
		
		return surveyTable;
	}
	
	/**
	 * Takes in the object array, product, and demographic to filter data on and returns an
	 * integer array of the product rating and the frequency of each rating by the particular
	 * demographic
	 * @param surveyTable
	 * @param filter
	 * @param demographic
	 * @return integer array
	 */
	private static int[] filterData(ArrayList<SurveyObjects> surveyTable, String filter, String demographic){
		int size= surveyTable.size();
		int[] freqPerRating = new int[5]; //array to hold product rating the frequency of the rating
		String gender;
		String state;
		String age;
		String product; 
		int frequency=0;
		String ratingString;
		int ratingInteger=0;
		SurveyObjects curPerson=null;
		
		for(int i=0; i<size; i++){
			curPerson= surveyTable.get(i); //object at current index of object arraylist
			age= curPerson.getAgeGroup();
			gender= curPerson.getGender();
			state= curPerson.getStateOfResidence();
			product= curPerson.getProduct();
			
			//check to see if the product matches the product of the current object
			if(filter.equals(product)){
				//check to see if the demographic matches the current object demographic
				if(demographic.equals(age)||demographic.equals(state)||demographic.equals(gender)){
					ratingString= curPerson.getRating(); //get rating of object
					ratingInteger= Integer.parseInt(ratingString); //change rating from string to integer
					ratingInteger -= 1; //subtract to match the index of the array (ratings: 1-5 and indices: 0-4)
					frequency=freqPerRating[ratingInteger];
					frequency++;
					freqPerRating[ratingInteger]= frequency; //increment frequency of rating of current product
				}
			}
		}
		
		return freqPerRating;
	}
	
	/**
	 * Writes the output file to the output file location the user entered
	 * @param outputFile
	 * @param freqPerRating
	 */
	private static void writeOutputFile(String outputFile, int[] freqPerRating){
        String header="Rating,Frequency\n";
        int rating=0;
       
        try{
        	File file= new File(outputFile); //making output file path an object of type File
        	FileWriter writer= new FileWriter(file); //creating FileWriter object for newly created file
        	BufferedWriter buffWriter = new BufferedWriter(writer); //opening BufferedWriter
        	buffWriter.write(header); //write header string to file
        	for(int i=0; i<freqPerRating.length; i++){
        		rating=i+1;
        		buffWriter.write(rating +"," + freqPerRating[i] + "\n"); //writes rest of file
        	}
        	buffWriter.close();
        }
        catch(Exception e){
        	System.out.println("ERROR");
        }
        
	}
}
