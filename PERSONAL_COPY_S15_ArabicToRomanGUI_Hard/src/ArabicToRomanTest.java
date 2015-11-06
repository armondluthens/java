
import javax.swing.JFrame;

public class ArabicToRomanTest {
	public static void main(String[] args){
		RomanToArabic romanToArabicObject = new RomanToArabic(); 
		romanToArabicObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		romanToArabicObject.setSize(280, 180); 
		romanToArabicObject.setResizable(false); //user can't resize window
		romanToArabicObject.setVisible(true); 
	}
}
