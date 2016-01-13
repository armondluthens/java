
/**
 * Class to define objects relevant to the data from the survey results.  Each attribute of an
 * object of type 'SurveyObjects' is a heading in the table of data that was read in. Each object
 * created will be stored in ArrayList for later use.
 * @author armondluthens
 *
 */
public class SurveyObjects {
	private String gender;
	private String ageGroup;
	private String stateOfResidence;
	private String product;
	private String rating;
	
	/**
	 * Constructor containing data from each field read in from the survey results
	 * @param Gender
	 * @param AgeGroup
	 * @param StateOfResidence
	 * @param Product
	 * @param Rating
	 */
	SurveyObjects(String Gender, String AgeGroup, String StateOfResidence, String Product, String Rating){
		this.gender= Gender;
		this.ageGroup= AgeGroup;
		this.stateOfResidence= StateOfResidence;
		this.product= Product;
		this.rating= Rating;
	}
	
	/**
	 * @return the gender of the particular object
	 */
	public String getGender(){
		return gender;
	}
	/**
	 * @return the age of the particular object
	 */
	public String getAgeGroup(){
		return ageGroup;
	}
	/**
	 * @return the state of the particular object
	 */
	public String getStateOfResidence(){
		return stateOfResidence;
	}
	/**
	 * @return the product of the particular object
	 */
	public String getProduct(){
		return product;
	}
	/**
	 * @return the rating of the particular object
	 */
	public String getRating(){
		return rating;
	}
}
