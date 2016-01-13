import javax.swing.JFrame;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * This is the test method that contains the main method and creates a new JFrame. In the main method, an object
 * of type Orbit is created. Additionally, an object of type executor service is created and executed so that 
 * the code in Orbit contains a run method which creates a new thread to allow the planet to move around the GUI.
 * @author armondluthens
 *
 */
public class OrbitTest {
	public static void main(String args[]){
		Orbit planet = new Orbit(); 
		JFrame frame = new JFrame(); 
		frame.add(planet);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(600, 600);
		
		ExecutorService executorService = Executors.newCachedThreadPool(); //declare thread pools in which pools are created as needed
		executorService.execute(planet); //start executing task on the thread
	    executorService.shutdown(); // shut down ExecutorService
	}
}


