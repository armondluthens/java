import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
* Server class that runs the server portion of the client server interaction program.  This class creates
* a server that accepts incoming client connections requesting files.  the filenames are stored within this
* class and if the filename passed in exists then the file will be read and send back to the client. The server
* created is multi-threaded to allow multiple clients to join. A new server thread is spawned for each new client
* coming in.
* @author armondluthens
*
*/
public class Server extends JFrame{
	private JTextArea textDisplayArea; // display information to user
	private ExecutorService executor; // will execute the run method in the private inner class
	private ServerSocket server; // server socket
	private SocketServer[] sockServerList; // Array of objects to be threaded
	private int connectionNumber = 1; //number of connections that grows as more clients come in
	private final ArrayList<String> files = new ArrayList<>(Arrays.asList("file1.txt", "file2.txt", "file3.txt")); //arraylist that holds the filenames in project
	private final int PORT= 23555;
	private final int BACKLOG=100; //requested maximum length of the queue of incoming connections
	private final int numberOfThreads=10;
	
	/**
	 * Constructor of the Server class that builds the GUI and creates an array of socket objects to be
	 * threaded by a private inner class. A variable of type executor service is also created to run the
	 * run method on each new thread created
	 */
	public Server(){
		//create GUI
		super("Server");
		textDisplayArea = new JTextArea(); //create text area to display text
		textDisplayArea.setEditable(false);
		add( new JScrollPane( textDisplayArea ), BorderLayout.CENTER );
		setSize(500, 500); // set size of window
		setVisible(true); // show window
		
		sockServerList= new SocketServer[numberOfThreads]; // allocate array for up to 100 server threads
		executor = Executors.newFixedThreadPool(numberOfThreads); //create thread pool
	}

	/**
	 * Method that runs the server and only accepts an incoming client connection. A runnable object of
	 *  the private inner class socketServer is created and waits for a client to come in and try to connect
	 *  to it. Each time a new client comes in and creates a connection, a new server thread is spawned
	 *  so that each client runs a separate server thread. 
	 */
	public void runServer(){
		try{
			server = new ServerSocket(PORT, BACKLOG); //create ServerSocket that binds a port number with a backlog number
			boolean serverIsRunning=true;
			while (serverIsRunning==true){
				try{
					//create a new runnable object to serve the next client to call in
					sockServerList[connectionNumber] = new SocketServer(connectionNumber); //creates an object for a new thread and passes in the connection current number 	
					sockServerList[connectionNumber].waitForConnection(); //calls method that waits for a client and attempts to connect a client and server
					executor.execute(sockServerList[connectionNumber]); //executes object onto new thread to run as its own server
				}
				catch (EOFException eofException){
					displayMessage( "\nServer terminated connection" );
				}
				finally{
					connectionNumber++; //increment connection number after thread has been made
				} // end finally
			} 
		}
		catch (IOException ioException){
			System.out.println("IO Exception in runServer Method");
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
	 * Inner class that implements the Runnable interface to create a new server thread each time
	 * a new client joins so that a server thread is always serving one client
	 */
	private class SocketServer implements Runnable{
	   private ObjectOutputStream serverOutputStream; // output stream to client
	   private ObjectInputStream serverInputStream; // input stream from client
	   private Socket serverSocket; //Server socket to create connection to client socket
	   private int connectionNum;
	   private boolean serverRunning= false; //indicates if server is running or not
	   
	   /**
	    * constructor for SocketServer private inner class that sets the current connection number to know which
	    * server is running (each server runs on a new thread for each client)
	    * @param connectionNumberCopy
	    */
	   public SocketServer(int connectionNumberCopy){
		   connectionNum = connectionNumberCopy;
	   }
	   /**
	    * Method of the runnable interface that executes code for each thread that is created
	    */
	   public void run(){
		   try{
			   serverRunning= true; //server is now running
			   try{
				   getStreams(); //calls method to get the input and output streams of the client
				   handleConnection(); //calls method to handle the connection when it is alive
			   } 
			   finally{
				   closeConnection(); //close connection
			   }
		   }
		   catch(IOException ioException){
			   System.out.println("IO Exception in run method");
		   }
	   }

	   /**
	    * Method in which the sever listens indefinitely for a client connection attempt to me made and
	    * when a client comes in it connects to it, and returns a socket when the connection is successful
	    * @throws IOException
	    */
	   private void waitForConnection() throws IOException{
	      displayMessage( "Waiting for connection" + connectionNum + "...\n" );
	      serverSocket = server.accept(); //serverSocket accepts the incoming client connection request            
	   }
	   
	   
	   /**
		* Method to get the input and output streams to read data in and write data to then
		* be communicated between server and client (send/receive functionality)
		* @throws IOException
		*/
	   	private void getStreams() throws IOException{
	   		serverOutputStream = new ObjectOutputStream(serverSocket.getOutputStream()); //get sequence of data to be output
			serverOutputStream.flush(); //pushes out all current data in buffer to output location
			serverInputStream = new ObjectInputStream(serverSocket.getInputStream()); //get sequence of data coming in
		}
		
	   	/**
		 * Method that keeps the connection alive between server and client and stays open until
		 * connection is terminated
		 * @throws IOException
		 */
	   	private void handleConnection() throws IOException{
	   		String message= "Connection " + connectionNum + " successful";
	   		sendData(message, false); //send connection successful message
	   		boolean serverIsRunning=true; //continues to run until server crashes or client logs off
	   		String fileToRead=""; //path of file client passed to server 
	   		String filename=""; //name of only the file client passed to server
	   		
	   		//continually keep the connection open and read the messages from the client until the connection is closed
	   		do{ 
	   			try{
	   				//readObject is used to read an object from the stream and then casted as a string
	   				message= (String)serverInputStream.readObject();
	   				displayMessage( "\n" + connectionNum + message); // display message
	   				filename= message.substring(8); //cuts the message label of "CLIENT:" off to get the actual message content (the filename)
	   				boolean fileExists= searchForFile(filename); //call method to see if file exists (on the server) or not
	            
	   				if(fileExists==true){
	   					fileToRead= "src/"+filename; //path to send in to read file method
	   					ArrayList<String> fileContents= readFile(fileToRead); //call method to read the file
	   					int fileContentsSize= fileContents.size();
	   					String currentLine;
	            	
	   					sendData("Contents of " + filename, false); //data sent to method to be displayed to text area
	   					for(int i=0; i<fileContentsSize; i++){
	   						currentLine= fileContents.get(i);
	   						sendData(currentLine, true); //data sent to method to be displayed to text area
	   					}
	   				}
	   				else{
	   					sendData("This file DOES NOT EXIST", false); //if filename entered by client does not exist on server
	   				}    
	   			}
	   			//no definition for the readObject class was found
	   			catch(ClassNotFoundException classNotFoundException){
	   				displayMessage( "\nUnknown object type received" );
	   			}
	   		}while(serverIsRunning==true); //infinite while server is running(never changes value)
	   	}
	   	
	   	/**
	   	 * Method that takes in the string of an entered filename, and searches through an ArrayList
	   	 * that holds the names of all of the files held on the server. If the filename passed in matches
	   	 * the name of a file stored on the server, a boolean of true is returned otherwise false is returned
	   	 * @param filename name of file to be searched for
	   	 * @return boolean value indicating whether the file exists on the server or not
	   	 */
	   	private boolean searchForFile(String filename){
	   		boolean fileExists=false;
			int length = files.size(); //length of ArrayList containing all filenames held on server
			String currentFile;
			//loop through files array
			for(int i=0; i<length; i++){
				currentFile= files.get(i);
				if(currentFile.equals(filename)){
					fileExists=true;
					i=length; //break from loop
				}
			}
			return fileExists;
	   	}
	   	
	   	/**
	   	 * Method to read a given file line by line and store each line as a string in an ArrayList.  After the
	   	 * file has been read and the contents of it has been stored in the string ArrayList, the ArrayList is
	   	 * returned
	   	 * @param file name of file to be read
	   	 * @return an ArrayList of strings of the contents of the file
	   	 */
	   	private ArrayList<String> readFile(String file){
	   		ArrayList<String> fileContents= new ArrayList<>(); //ArrayList to be returned of contents of file
	   		String currentLine="";
	   		try{
	   			FileReader fileReader = new FileReader(file); //fileReader object to read streams of characters
	   			BufferedReader bufferedReader = new BufferedReader(fileReader); //reads text from stream
	   			//loops through file and reads line by line
	   			while((currentLine = bufferedReader.readLine()) != null) {
	   				fileContents.add(currentLine);
	   			}   
	   			bufferedReader.close();            
	   		}
	   		catch(Exception e){
	   			System.out.println("Error retrieving file");
	   		}
	   		return fileContents;
	   	}
	   
	   	/**
		 * Method that uses the socket to send data from the server to the client using the ObjectOutputStream variable.
		 * The parameters that it takes in are the string of the message to be sent, and a boolean variable indicating
		 * whether or not the content being sent is content from a file being read. If so (boolean is true), only the file 
		 * contents will be sent without any headers
		 * @param message writingFile
		 */
	   	private void sendData(String message, boolean writingFile){
	   		try{ // send object to client
	   			if(writingFile==false){
	   				serverOutputStream.writeObject( "SERVER" + connectionNum + ": " + message );
	   				serverOutputStream.flush(); //flush output to client
	   				displayMessage( "\nSERVER" + connectionNum + ": " + message );
	   			}
	   			else{
	   				serverOutputStream.writeObject(message);
	   				serverOutputStream.flush(); //flush output to client
	   				displayMessage("\n" + message);
	   			}   
	   		} 
	   		catch (IOException ioException){
	   			System.out.println("IO Exception in sendData Method");
	   		} 
	   	} 
	   	
	   	/**
		 * Method to close the connection between the server and the client. Input and output streams that were
		 * opened by the server are closed, and the socket through which the client and server were using to is
		 * closed as well.
		 */
	   	private void closeConnection(){
	   		displayMessage( "\nTerminating connection " + connectionNum + "\n" );
	   		try{
	   			serverOutputStream.close(); // close output stream
	   			serverInputStream.close(); // close input stream
	   			serverSocket.close(); // close socket
	   		} // end try
	   		catch(IOException ioException){
	   			System.out.println("IO Exception in close connection");
	   		}
	   	}	  
	} //END PRIVATE INNERCLASS 'SOCKETSERVER'

} //END CLASS 'SERVER'
