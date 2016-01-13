import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Client class that runs the client portion of the client server interaction program.  This class creates
 * a client that requests a connection and upon connection opens incoming and outgoing streams. The GUI allows
 * the user to pass a filename to the server. If the file exists on the server, the file contents are output
 * to the textArea window and if the file does not exist the server will send a message indicating that.
 * @author armondluthens
 *
 */
public class Client extends JFrame{
	private JPanel panel; //panel that sits on frame made to make layout easier
	private JLabel labelPrompt; // tells user to enter a file to search for
	private JTextField inputFileField; //allows user to enter filename and enters filename from user
	private JTextArea textDisplayArea; //displays text between client and server
	private String messageFromServer = ""; // message from server
	private String clientAddress; // host server for this application
	private final int port=23555; //port number for the client server to use
	private Socket clientSocket; // socket to communicate with server
	private ObjectOutputStream clientOutputStream; //output stream from client to server
	private ObjectInputStream clientInputStream; //input stream from server
	
	
	/**
	 * Constructor of Client class which holds the parameter of the host ip address. The constructor is
	 * primarily responsible for building the client application GUI, which contains a Jlabel, JTextfield,
	 * and scrollable JTextArea. Lastly, the constructor sets the ip address passed in to the class variable
	 * 'clientAddress'
	 * @param host (IP address of host passed in by user)
	 */
	public Client(String host)
	{
		//CREATE GUI
		super("Client");
		panel = new JPanel(); //new jpanel to format more easily
		labelPrompt = new JLabel("Please enter a file to retrieve"); //create labelPrompt
		panel.add(labelPrompt, BorderLayout.PAGE_START); //add label to jpanel
		inputFileField = new JTextField(); // create inputFileField
		inputFileField.setEditable(true);
		
		inputFileField.addActionListener(new ActionListener() { //anonymous inner class because the inputFileField only handles one event
			public void actionPerformed(ActionEvent event){
				sendData(event.getActionCommand()); //listens for an input to the textField and then sends the string to 'sendData'
				inputFileField.setText(""); //resets the textField after data has been sent
			} // end method actionPerformed
		} // end anonymous inner class
		); // end call to addActionListener
		inputFileField.setPreferredSize(new Dimension(400, 50));
		panel.add(inputFileField, BorderLayout.CENTER); //add field to jpanel
	
		textDisplayArea = new JTextArea(); //create text area to display text between server and client
		textDisplayArea.setEditable(false); 
		textDisplayArea.setLineWrap(true); //make text wrap if line exceeds text area width
		textDisplayArea.setWrapStyleWord(true); //wraps text based on word not character
		JScrollPane scrollPane = new JScrollPane(textDisplayArea); //makes text area scrollable
		scrollPane.setPreferredSize(new Dimension(800, 250));
		panel.add(scrollPane, BorderLayout.PAGE_END); //adds text area to pannel
		panel.setVisible(true);
		add(panel); //add jpanel to jframe
		setSize(900, 400); // set size of window
		setResizable(false);
		setVisible(true); // show window
	
		clientAddress= host; //set client IP address as the host
	}

	/**
	 * Method to drive the Client class by calling methods that create a socket and connect to
	 * a server, get input and output streams for the client and server to communicate back
	 * and forth with, and to process the connection and data sent to the client from the server
	 */
	public void startClient(){
		try{ 
		   connectToServer(); //calls method to create socket to connect client and server
		   getStreams(); //calls method to get the input and output streams of the client
		   handleConnection(); //calls method to handle the connection when it is alive
		}
		//any exception in other methods will invoke the close connection method
		catch(Exception e){ 
			System.out.println("Exception in startClient Method");
		}
		finally{
			closeConnection(); //close connection to host server
		} 
	}

	/**
	 * Method to create a socket to connect the client to the server
	 * @throws IOException
	 */
	private void connectToServer() throws IOException{      
		displayMessage("Attempting connection...\n");
	
		InetAddress ipAddress= InetAddress.getByName(clientAddress); //object that holds the ip address of the client
		clientSocket = new Socket(ipAddress, port); // create Socket to make connection to server
		
		displayMessage("Successful Connection\n"); //successful connection message
	}

	/**
	 * Method to get the input and output streams to read data in and write data to then
	 * be communicated between server (send/receive functionality)
	 * @throws IOException
	 */
	private void getStreams() throws IOException{
		clientOutputStream = new ObjectOutputStream(clientSocket.getOutputStream()); //get sequence of data to be output
		clientOutputStream.flush(); //pushes out all current data in buffer to output location

		clientInputStream = new ObjectInputStream(clientSocket.getInputStream()); //get sequence of data coming in
	}

	/**
	 * Method that keeps the connection alive between client and server and stays open until connection is terminated
	 * @throws IOException
	 */
	private void handleConnection() throws IOException{
		boolean clientIsAlive=true;
		//continually keep the connection open and read the messages from the server until the connection is closed
		do{
			try{
				//readObject is used to read an object from the stream and then casted as a string
				messageFromServer = (String)clientInputStream.readObject();
				displayMessage( "\n" + messageFromServer ); // display message
			}
			//no definition for the readObject class was found
			catch(ClassNotFoundException classNotFoundException){
				System.out.println("Class Not found exception in handleConnection");
			} 
		}while(clientIsAlive==true); //keep going until connection terminated (Infinite until exit/Server closes)
	}
	
	/**
	 * Method that uses the socket to send data from the client to the server using the ObjectOutputStream variable
	 * @param message
	 */
	private void sendData(String message){
		try{		 
			clientOutputStream.writeObject( "CLIENT: " + message ); //writes message to stream to be sent to server
			clientOutputStream.flush(); //clears buffer
			displayMessage( "\nCLIENT: " + message);
	   }
	   catch (IOException ioException){
		   System.out.println("IO Exception in sendData Method");
	   } 
	} 

	/**
	 * Method to take in the message to be shown in the text area and use the event dispatch thread to modify
	 * the GUI safely. The invokeLater method is used to create a new runnable object each time the GUI needs
	 * to be updated and this will schedule the code to run when it can be done safely.
	 * @param messageForTextAreaDisplay
	 */
	private void displayMessage(String messageForTextAreaDisplay){
		//use invoke later method to create a new runnable each time to update GUI
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				textDisplayArea.append(messageForTextAreaDisplay); //adds the text onto the text area
			} 
		}  
		); 
	}
	
	/**
	 * Method to close the connection between the client and the server. Input and output streams that were
	 * opened by the client are closed, and the socket through which the client and server were using to is
	 * closed as well.
	 */
	private void closeConnection(){
		displayMessage( "\nAttempting to close connection..." );
		try{
			clientOutputStream.close(); //close client output stream
			clientInputStream.close(); //close client input stream
			clientSocket.close(); //close socket
			displayMessage("Connection successfully closed");
		}
		catch(IOException ioException){
			System.out.println("IO Exception in closeConnection Method");
		}
	}
	
}//END CLIENT CLASS


