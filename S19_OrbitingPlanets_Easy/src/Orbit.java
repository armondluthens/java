import javax.swing.*;
import java.awt.*;

/**
 * This class contains methods that allows the 'run' method to create a thread to 
 * continually update position of the planet and repaint the GUI to make the planet 
 * graphic appear as though it is orbiting around the star.
 * @author armondluthens
 *
 */
public class Orbit extends JPanel implements Runnable{
	
	private final int starDiameter=80; //diameter in pixels of star which planet orbits around
	private final int starPosition=260; //fixed position from which the star always stays
	private final int starCenter=300; //origin of the star
	private final int planetDiameter=50; //diameter of moving planet
	private final int planetRadius=25;
	private final int planetDistance=225; //distance from center of star to center of planet
	private final int orbitSpeed=5; //orbit speed (thread sleep time i.e. lower=faster)
	
	private final int planetStartingX=500; //x position the planet starts in
	private final int planetStartingY=275; //y position the planet start in
	private int planetX; //x coordinate of the planet that changes
	private int planetY; //y coordinate of the planet that changes
	private double startingAngle= 0.0; //angle at which the planet starts
	private double angle; //current angle of planet from star
	
	/**
	 * Method to set the x and y coordinates on the JFrame of the planet as it orbits
	 * @param x
	 * @param y
	 */
	private void setPlanetCoords(int x, int y){
		this.planetX=x;
		this.planetY=y;
	}
	/**
	 * Method that returns the current x coordinate on the JFrame of of the planet
	 * @return integer of the the current x coordinate of planet
	 */
	private int getPlanetXCoord(){
		return planetX;
	}
	/**
	 * Method that returns the current y coordinate on the JFrame of of the planet
	 * @return integer of the the current y coordinate of planet
	 */
	private int getPlanetYCoord(){
		return planetY;
	}
	/**
	 * Method to set the angle relative to the star of the planet orbiting
	 * @param currentAngle
	 */
	private void setAngle(double currentAngle){
		this.angle= currentAngle;
	}
	/**
	 * Method to return the current angle of the planet relative to the star it is orbiting around
	 * @return double of the angle
	 */
	private double getAngle(){
		return angle;
	}
	
	/**
	 * Executor service method that runs a new thread for a runnable object. This thread continually moves and repaints
	 * the GUI to make the planet move and orbit around a star
	 */
	public void run(){
		int coords[]; //array holds the returned x and y coordinates from the computeNewCoords Method
		setAngle(startingAngle); //sets the starting angle the first time through
		double currentAngle= getAngle();
		setPlanetCoords(planetStartingX, planetStartingY); //sets the coordinates equal to the starting coords the first time through
		repaint(); //updates the GUI
		
		while(true){
			try{
				Thread.sleep(orbitSpeed); //used to make the thread sleep between repainting the GUI (controls speed of orbit)
			}
			catch(InterruptedException exception){
				Thread.currentThread().interrupt(); //re-interrupt the thread
			}
			currentAngle++; //increment the angle
			setAngle(currentAngle); //reset the new angle
			coords= computeNewCoords(); //compute the new coordinates of the new angle
			setPlanetCoords(coords[0], coords[1]); //set the new coordinates
			repaint(); //update the GUI
		}
	}
	
	/**
	 * Method that uses the the mathematical methods x=a*r*cos(angle) and y=b*r*sin(angle) in which
	 * (a, b) is the origin of the circle and r is the radius.  In this case, the radius is the distance
	 * from the center of the star to center of the planet. The planet radius is subtracted after each
	 * calculation because the JFrame measure from the left and top of a shape and not from the center
	 * @return
	 */
	private int[] computeNewCoords(){
		double currentAngle= getAngle();
		int newCoords[]= new int[2];
		double angleInRadians = Math.toRadians(currentAngle);
		double x; //double of the new calculated x coordinate
		int xInt;
		double y; //double of the new calculated y coordinate
		int yInt;
		x= (starCenter+planetDistance*Math.cos(angleInRadians)) - planetRadius; //finds new x using x=a*r*cos(angle) in which a is the origin x coord
		xInt= (int)x; //cast the double as an integer for coordinate simplicity
		y= (starCenter+planetDistance*Math.sin(angleInRadians)) - planetRadius; //finds new x using y=b*r*sin(angle) in which b is the origin y coord
		yInt= (int)y; //cast the double as an integer for coordinate simplicity
			
		newCoords[0]= xInt; //store the new x-coord
		newCoords[1]= yInt; //store the new y-coord
			
		return newCoords;
	}
	
	/**
	 * Method that uses the abstract Graphics class and its methods to draw the shapes on the JFrame
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.BLACK); //sets background color
		g.setColor(Color.WHITE); //sets color of the star that the planet orbits around
		g.fillOval(starPosition, starPosition, starDiameter, starDiameter); //sets position of the star on the JFrame
		g.setColor(Color.ORANGE); //sets color of the planet that orbits
		g.fillOval(getPlanetXCoord(), getPlanetYCoord(), planetDiameter, planetDiameter); //sets position of the planet on the JFrame
	}
}


