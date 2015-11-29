/**
 * Test class for survey processing. This class contains the main method and creates the objects of type Survey
 * @author armondluthens
 */
public class SurveyTest {
	public static void main(String[] args){
		SurveyCode survey = new SurveyCode();
		survey.start(); //invokes method to set the logic in Survey in motion
		System.out.println("Program Complete");
	}
}
