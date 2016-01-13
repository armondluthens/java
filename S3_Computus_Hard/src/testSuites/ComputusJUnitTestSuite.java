package testSuites;

import java.util.ArrayList;

/**
 * JUnit testing suite to test the computed date against a list of known dates
 * to ensure that the date is being computed correctly
 */
public class ComputusJUnitTestSuite {
	/**
     * Method to compute the Gregorian Date of Easter for any given year
     * @param year to compute the date of easter
     * @return an arraylist of strings consisting of the month, day, and year
     */
    public ArrayList<String> computeDateOfEaster(int year) {
        //initialize variables used in method
        String dayString;
        String monthString;
        String yearString = Integer.toString(year);
        ArrayList<Integer> computedDayAndMonthArray = easterComputationAlgorithm(year); //calls easter algorithm
        int month= computedDayAndMonthArray.get(0);
        int day= computedDayAndMonthArray.get(1);

        if (day < 10) {
            //changes day Integer to a String
            dayString = "0" + Integer.toString(day);
        } else {
            //changes day Integer to a String
            dayString = Integer.toString(day);
        }

        //changes month Integer to a String
        monthString = "0" + Integer.toString(month);

        ArrayList<String> dateList= new ArrayList<>();
        dateList.add(monthString);
        dateList.add(dayString);
        dateList.add(yearString);
        
        //String fullDate= (monthString + "/" + dayString + "/" + yearString);
        //return fullDate;
        return dateList;
    }
    
    
    /**
     * Disclaimer: I DID NOT CREATE THIS ALGORITHM IN THIS METHOD
     * Creator of Algorithm: J.M.
     * Oudin Website URL: http://aa.usno.navy.mil/faq/docs/easter.php 
     * Algorithm for computing easter date. All variables (a, b, c, d, e, and f) 
     * are simply placeholder variables used in the computation and hold no meaning
     * anywhere else in the code. This formula is implemented in the exact same
     * way as it was given on the stated website.
     * @param yearToCompute
     * @return an ArrayList that holds the month integer and the date of the month integer
     */
    private static ArrayList<Integer> easterComputationAlgorithm(int yearToCompute) {
        int a, b, c, d, e, f; //placeholder variables without meaning to program
        int day;
        int month;
        //int myYear= getYear();
        ArrayList<Integer> computedDayAndMonthArray = new ArrayList<>();

        a = yearToCompute / 100;
        b = yearToCompute - (19 * (yearToCompute/ 19));
        c = (a - 17) / 25;
        d = a - a / 4 - (a - c) / 3 + 19 * b + 15;
        d = d - 30 * (d / 30);
        d = d - (d / 28) * (1 - (d / 28) * (29 / (d + 1)) * ((21 - b) / 11));
        e = yearToCompute + yearToCompute / 4 + d + 2 - a + a / 4;
        e = e - 7 * (e / 7);
        f = d - e;
        month = 3 + (f + 40) / 44;
        day = f + 28 - 31 * (month / 4);

        computedDayAndMonthArray.add(month);
        computedDayAndMonthArray.add(day);

        return computedDayAndMonthArray;
    }
}
