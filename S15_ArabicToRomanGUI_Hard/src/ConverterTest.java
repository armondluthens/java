

import javax.swing.JFrame;

public class ConverterTest {
	public static void main(String[] args){
		Converter romanToArabicObject = new Converter(); 
		romanToArabicObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		romanToArabicObject.setSize(280, 180); 
		romanToArabicObject.setResizable(false); //user can't resize window
		romanToArabicObject.setVisible(true); 
	}
}