import javax.swing.JFrame;

/**
 * Test class that holds the main method for the for the number converter. The main
 * method creates a JFrame, and instantiates an object of Converter class.
 * @author aluthens
 *
 */
public class ConverterTest {
	public static void main(String[] args){
		Converter romanToArabicObject = new Converter(); 
		romanToArabicObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		romanToArabicObject.setSize(280, 180); 
		romanToArabicObject.setResizable(false); //user can't resize window
		romanToArabicObject.setVisible(true); 
	}
}