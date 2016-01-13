/**
 * Test Class For Bucket Sort
 * @author armondluthens
 * This class creates objects of type BucketSort in which the BucketSort Class
 * will use its methods to sort an array of integers using a bucket sorting method.
 * Sorting an integer list via a bucket sorting method manipulates the digits of
 * each integer in the list to sort using a 2D Array.  This method takes more memory
 * than a bubble sorting method, but runs faster.
 */
public class BucketSortTest {
	public static void main(String[] args){
		BucketSort sort = new BucketSort(); //BucketSort object with no array passed in
		sort.start(); //calls method in BucketSort to start sorting
		int[] randomNumberArray= {11, 21, 21, 51, 71, 81};
		BucketSort sort2 = new BucketSort(randomNumberArray); //BucketSort object WITH array passed in to overloaded constructor
		sort2.start(); //calls method in BucketSort to start sorting
	}
	
}
