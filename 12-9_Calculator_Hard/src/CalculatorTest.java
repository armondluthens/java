
import javax.swing.JFrame;

/**
 * Test class for Calculator that holds the main method and creates the initial GUI. Creates a new
 * object of type Calculator that runs the methods of the calculator class
 * @author aluthens
 *
 */
public class CalculatorTest {
	public static void main(String[] args) {
        CalculatorFrame calculator = new CalculatorFrame();
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calculator.setSize(300, 175);
        calculator.setResizable(false); //user not allowed to resize calculator window
        calculator.setLocationRelativeTo(null); //makes GUI pop up in center of screen
        calculator.setVisible(true);
    }
}
