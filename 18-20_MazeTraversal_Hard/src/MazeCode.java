
import java.util.Scanner;

/**
 * Class that holds a 2D String Array that serves as the initial maze for the program to solve. An object
 * from the test class will call on the 'start()' method which drives the code in the class. Methods will
 * be called to getting maze traversal starting point and then work to solve the maze. The user will be prompted
 * to enter a keyboard input each time the maze position is updated so that they will be able to see the maze visibly 
 * being solved. If the maze can be solved, the final path from start to finish will be printed to the console, otherwise
 * a message will be printed that that maze is not solvable.
 * @author armondluthens
 */
public class MazeCode {
	//initial maze held in the class
	private static String[][] mazeArray= new String[][]{
		{"# ", "# ", "# ", "# ", "# ", "# ", "# ", "# ", "# ", "# ", "# ", "# "}, 
		{"# ", ". ", ". ", ". ", "# ", ". ", ". ", ". ", ". ", ". ", ". ", "# "}, 
		{"S ", ". ", "# ", ". ", "# ", ". ", "# ", "# ", "# ", "# ", ". ", "# "},
		{"# ", "# ", "# ", ". ", "# ", ". ", ". ", ". ", ". ", "# ", ". ", "# "},
		{"# ", ". ", ". ", ". ", ". ", "# ", "# ", "# ", ". ", "# ", ". ", "F "},
		{"# ", "# ", "# ", "# ", ". ", "# ", ". ", "# ", ". ", "# ", ". ", "# "},
		{"# ", ". ", ". ", "# ", ". ", "# ", ". ", "# ", ". ", "# ", ". ", "# "},
		{"# ", "# ", ". ", "# ", ". ", "# ", ". ", "# ", ". ", "# ", ". ", "# "},
		{"# ", ". ", ". ", ". ", ". ", ". ", ". ", ". ", ". ", "# ", ". ", "# "},
		{"# ", "# ", "# ", "# ", "# ", "# ", ". ", "# ", "# ", "# ", ". ", "# "},
		{"# ", ". ", ". ", ". ", ". ", ". ", ". ", "# ", ". ", ". ", ". ", "# "},
		{"# ", "# ", "# ", "# ", "# ", "# ", "# ", "# ", "# ", "# ", "# ", "# "}
		
	};
	
	private static final int mazeWidth= mazeArray.length; //width of original maze that will not change as maze updates
	private static final int mazeHeight= mazeArray.length; //height of original maze that will not change as maze updates
	
	/**
	 * Driver method called on by an object in test class to drive the methods in the Maze class
	 */
	public void start(){
		int[] startingPosition= startingPosition(); //calls method to get starting coordinate points
		int startPositionRow= startingPosition[0]; //starting row position
		int startPositionColumn= startingPosition[1]; //starting column position
		
		String[][] initialMaze= mazeArray; //initial maze to pass into recursive traversal method
		
		//boolean indicating if maze is solvable or not
		boolean mazeBool= traverseMaze(initialMaze, startPositionRow, startPositionColumn); 
		if(mazeBool==true){
			System.out.println("Maze has been solved");
		}
		else{
			System.out.println("Maze cannot be solved");
		}
	}
	
	/**
	 * Method to receive a maze (2D String Array) and prints it out to the console for the user to see
	 * @param maze (2D String Array)
	 */
	private static void printMaze(String[][] maze){
		int length= maze.length;
		for(int i=0; i<length; i++){
			for(int j=0; j<length; j++){
				System.out.print(maze[i][j]); //prints current index
			}
			System.out.println();
		}
		System.out.println("\n\n");
	}
	
	/**
	 * Method to determine the starting postion coordinates of the initial maze (2D Array)
	 * @return an integer array with the starting row position in the 0th index and the starting column position in the 1st index
	 */
	private static int[] startingPosition(){
		int length= mazeArray.length; //length of maze for iteration purposes
		int[] startingPosition= new int[2];
		int startPositionRow=0; //INDEX 0
		int startPositionColumn=0; //INDEX 1
		
		//iterate through maze
		for(int i=0; i<length; i++){
			for(int j=0; j<length; j++){
				if(mazeArray[i][j].equals("S ")){ //looks for index with "S " indicating the starting point
					startPositionRow=i;
					startPositionColumn=j;
					startingPosition[0]=startPositionRow;
					startingPosition[1]=startPositionColumn;
				}
			}
		}
		return startingPosition;
	}
	
	/**
	 * Method that uses recursion to constantly traverse through the maze and check if a move is valid. Will first attempt to
	 * make a valid move through paths that have not been yet traversed, and then will backtrack if a deadend is encountered.
	 * @param maze 2D maze array that is updated with each call to the function
	 * @param currentRow 
	 * @param currentColumn
	 * @return boolean value indicating if the maze is solvable or not
	 */
	private static boolean traverseMaze(String[][] maze, int currentRow, int currentColumn){
		boolean mazeBool=false;
		Scanner input = new Scanner(System.in);
		
		//gets the symbol one unit away from current position in maze in given direction
		String symbolToRight;
		String symbolToLeft;
		String symbolAbove;
		String symbolBelow;
		
		//symbol to right of current position if in bounds
		if(currentColumn<mazeWidth){
			symbolToRight= mazeArray[currentRow][currentColumn+1];
		}
		else{
			symbolToRight="OOB"; //symbol out of bounds of maze
		}
		//symbol to left of current position if in bounds
		if(currentColumn>0){
			symbolToLeft= mazeArray[currentRow][currentColumn-1];
		}
		else{
			symbolToLeft="OOB"; //symbol out of bounds of maze
		}
		//symbol above current position if in bounds
		if(currentRow>0){
			symbolAbove= mazeArray[currentRow-1][currentColumn];
		}
		else{
			symbolAbove="OOB"; //symbol out of bounds of maze
		}
		//symbol below current position if in bounds
		if(currentRow<mazeHeight){
			symbolBelow= mazeArray[currentRow+1][currentColumn];
		}
		else{
			symbolBelow="OOB"; //symbol out of bounds of maze
		}
		
		//BASE CASE
		//checks to see if location is adjacent to finishing point
		if(symbolToRight.equals("F ") || symbolBelow.equals("F ") || symbolAbove.equals("F ") || symbolToRight.equals("F ")){
			maze[currentRow][currentColumn]= "x "; //adds the final step in the maze traversal
			System.out.println("MAZE COMPLETE");
			printFinalPath(maze); //method to print only the correct path from start to finish
			mazeBool= true;
			return mazeBool;
		}
		else{
			printMaze(maze); //prints current maze
			System.out.println("Press any key to continue"); //waits so user can see maze constantly updated
			String continueProgram= input.nextLine();
			
			//move right if path has not been traversed
			if(symbolToRight.equals(". ")){	
				maze[currentRow][currentColumn+1]="x ";
				return traverseMaze(maze, currentRow, currentColumn+1); //recursive call to update current position one unit right
			}
			//move down if path has not been traversed
			if(symbolBelow.equals(". ")){
				maze[currentRow+1][currentColumn]="x ";
				return traverseMaze(maze, currentRow+1, currentColumn); //recursive call to update current position one unit down
			}
			//move move left if path has not been traversed
			if(symbolToLeft.equals(". ")){
				maze[currentRow][currentColumn-1]="x ";
				return traverseMaze(maze, currentRow, currentColumn-1); //recursive call to update current position one unit left
			}
			//move up if path has not been traversed
			if(symbolAbove.equals(". ")){
				maze[currentRow-1][currentColumn]="x ";
				return traverseMaze(maze, currentRow-1, currentColumn); //recursive call to update current position one unit up
			}
			
			//BACKTRACKING FOR PLACES ALREADY TRAVERSED
			if(symbolToRight.equals("x ")){
				maze[currentRow][currentColumn]="0 "; //mark position a dead end
				return traverseMaze(maze, currentRow, currentColumn+1); //updates position to go right
			}
			if(symbolBelow.equals("x ")){
				maze[currentRow][currentColumn]="0 "; //mark position a dead end
				return traverseMaze(maze, currentRow+1, currentColumn); //updates position to go down
			}
			if(symbolToLeft.equals("x ")){
				maze[currentRow][currentColumn]="0 "; //mark position a dead end
				return traverseMaze(maze, currentRow, currentColumn-1); //updates position to go left
			}
			if(symbolAbove.equals("x ")){
				maze[currentRow][currentColumn]="0 "; //mark position a dead end
				return traverseMaze(maze, currentRow-1, currentColumn); //updates position to go up
			}
			else{
				maze[currentRow][currentColumn]="0 "; //mark position a dead end (being back to start)
			}
		}
		return mazeBool;
	}
	
	/**
	 * Method to print only the correct path from start to finish once the maze has been deemed
	 * possible to solve.
	 * @param maze 2D maze array that is updated with each call to the function
	 */
	private static void printFinalPath(String[][] maze){
		int length= maze.length; //length of maze for iterating through
		
		System.out.println("FINAL MAZE PATH");
		for(int i=0; i<length; i++){
			for(int j=0; j<length; j++){
				if(maze[i][j].equals("0 ")){
					maze[i][j]= ". "; //changes backtracked indices of dead-ends back to original "." marker
				}
			}
		}
		printMaze(maze); //calls print maze to print the maze with final path
	}
	
} //END OF CLASS
