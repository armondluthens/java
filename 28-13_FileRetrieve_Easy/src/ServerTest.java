import javax.swing.JFrame;

/**
 * Test class for creating a server that creates on object of type Server and gives the command to exit
 * the jFrame upon closing window, and invokes the runServer method to start the server.
 * @author armondluthens
 */
public class ServerTest{
	public static void main(String[] args){
		Server application = new Server(); //create server
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.runServer(); //run server application
	}
}


