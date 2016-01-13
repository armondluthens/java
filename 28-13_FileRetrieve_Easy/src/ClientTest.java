import javax.swing.JFrame;

/**
 * Test class for creating a client that creates on object of type Client and gives the command to exit
 * the jFrame upon closing window, and invokes the startClient method to start the client.
 * @author armondluthens
 */
public class ClientTest {	
	private static final String LOCALHOST="127.0.0.1";
	
	public static void main( String[] args )
	{
		Client application = new Client(LOCALHOST); //connect to localhost
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		application.startClient(); // run client application
	}
}
