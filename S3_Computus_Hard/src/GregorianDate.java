
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GregorianDate {
	private int year;
	GregorianDate(int year){
		year= this.year;
	}
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
     
        return dateList;
    }

    /**
     * Method to compute how many times easter repeats on the same calendar date
     * in a given time period
     * @param year takes the year to start from
     * @return a map of dates as the key and the number of times they repeat in a cycle as the value
     */
    public Map<String, Integer> computeSeries(int year) {
        //map key= date string
        //map value= integer of the count of repeats for that particular date key
        Map<String, Integer> numberOfDateRepeatsMap = new HashMap<>();
        String dateString;
        String monthString;
        String fullDate;
        int currentYear = year;
        int yearAtEndOfCycle = currentYear + 5700000;
        boolean keyExists;

        for (int i = currentYear; i < yearAtEndOfCycle; i++) {
            
            ArrayList<String> dateList= computeDateOfEaster(i);  
            monthString = dateList.get(0); //gets the first digit of the date string to get the month
            dateString = dateList.get(1);//converts the date integer into a string

            //takes the month number and changes it to the month name (display purposes)
            switch (monthString) {
                case "03":
                    monthString = "March ";
                    break;
                case "04":
                    monthString = "April ";
                    break;
            }
            fullDate = monthString + dateString; //concats the month and the day to get the full date string

            keyExists = numberOfDateRepeatsMap.containsKey(fullDate); //checks to see if the full date string already exists as a key in the map
            if (keyExists == true) {
                //if key already exists, just increment the date count by 1
                numberOfDateRepeatsMap.put(fullDate, numberOfDateRepeatsMap.get(fullDate) + 1);
            } 
            else {
                //if the key did not already exist, add it to the map and set the date count to 1
                numberOfDateRepeatsMap.put(fullDate, 1);
            }
        }

        return numberOfDateRepeatsMap;
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
