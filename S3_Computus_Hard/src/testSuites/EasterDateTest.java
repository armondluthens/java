package testSuites;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class EasterDateTest {
	
	/**
	 * Test Method to verify that dates are being computed right. Method to compute Easter Date
	 * is called and returns date.  This date is then checked against a list of the known date
	 * from that year.  If all dates match, then the test is successful, otherwise the test will
	 * fail.
	 */
	@Test
	public void test() {
		//test object
		ComputusJUnitTestSuite test = new ComputusJUnitTestSuite();
		
		/**
		 * KNOWN LIST OF TEST CASES TAKEN FROM: http://aa.usno.navy.mil/faq/docs/easter.php
		 * 20 Entries (1985-2004):
		 */
		ArrayList<String> knownDates = new ArrayList<>();
		knownDates.add("04/07/1985");
		knownDates.add("03/30/1986");
		knownDates.add("04/19/1987");
		knownDates.add("04/03/1988");
		knownDates.add("03/26/1989");
		knownDates.add("04/15/1990");
		knownDates.add("03/31/1991");
		knownDates.add("04/19/1992");
		knownDates.add("04/11/1993");
		knownDates.add("04/03/1994");
		knownDates.add("04/16/1995");
		knownDates.add("04/07/1996");
		knownDates.add("03/30/1997");
		knownDates.add("04/12/1998");
		knownDates.add("04/04/1999");
		knownDates.add("04/23/2000");
		knownDates.add("04/15/2001");
		knownDates.add("03/31/2002");
		knownDates.add("04/20/2003");
		knownDates.add("04/11/2004");
		
		ArrayList<String> easterDateArray= new ArrayList<>();
		String fullDate;
		int testCaseNumber;
		int year=1985;
		for(int i=0; i<20; i++){
			easterDateArray= test.computeDateOfEaster(year);
			fullDate= easterDateArray.get(0) + "/" + easterDateArray.get(1) + "/" + easterDateArray.get(2);
			
			testCaseNumber= i+1;
			System.out.println("Test Case #" + testCaseNumber);
			System.out.println("Known Date: " + knownDates.get(i) + "  |  Computed Date: " + fullDate + "\n");
			
			assertEquals(knownDates.get(i), fullDate);
			year++;
		}
	}
}
