
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class builds the JPanel for the calculator and invokes listener methods to give
 * the buttons functionality and methods to make the calculator able to do correct computations.
 * All validation methods are contained in this class as well that checks to make sure all button
 * clicks and functions are valid at runtime.
 * @author aluthens
 */
public class CalculatorFrame extends JFrame {
	private static String[] buttonLabelArray = new String[16]; //button names to display
	private static final JButton[] buttonsArray = new JButton[16]; //array holding actual JButtons
	JTextField numbersTextField;
	private final JPanel numberPanel; //panel to contain all buttons

	private int operatorType;
	private int operatorCount = 0; //error handling variable for operator input
	private int entry1DecimalCount = 0; //error handling variable for entry1 decimal numbers
	private int entry2DecimalCount = 0; //error handling variable for entry2 decimal numbers

	private int Entry1orEntry2 = 0; //tracks whether the number is the first entry or second entry
	private double entry1 = 0; //first number in mathematical operation
	private double entry2 = 0; //second number in mathematical operation

	/**
	 * Constructor for Calculator frame that gives the calculator its GUI display and implements
	 * action listeners to buttons to make the calculator functional
	 */
	public CalculatorFrame() {
		super("Calculator");
		createbuttonNameArray();
		numbersTextField = new JTextField(); //text field for number display
		numbersTextField.setEditable(false); //user can't edit text inside
		numbersTextField.setVisible(true);
		add(numbersTextField, BorderLayout.NORTH); //push to top of frame
		
		//Make all buttons labeled with respective string
		for (int i = 0; i < buttonLabelArray.length; i++) {
			buttonsArray[i] = new JButton(buttonLabelArray[i]);
		}
		
		//Add all buttons to numberPanel
		numberPanel = new JPanel();
		numberPanel.setLayout(new GridLayout(4, 4));
		numberPanel.add(buttonsArray[7]);
		numberPanel.add(buttonsArray[8]);
		numberPanel.add(buttonsArray[9]);
		numberPanel.add(buttonsArray[13]);

		numberPanel.add(buttonsArray[4]);
		numberPanel.add(buttonsArray[5]);
		numberPanel.add(buttonsArray[6]);
		numberPanel.add(buttonsArray[12]);

		numberPanel.add(buttonsArray[1]);
		numberPanel.add(buttonsArray[2]);
		numberPanel.add(buttonsArray[3]);
		numberPanel.add(buttonsArray[11]);

		numberPanel.add(buttonsArray[0]);
		numberPanel.add(buttonsArray[15]);
		numberPanel.add(buttonsArray[14]);
		numberPanel.add(buttonsArray[10]);
		numberPanel.setVisible(true);
		add(numberPanel, BorderLayout.SOUTH); //push numberPanel to bottom of frame

		//give each button an action handler with a number for the constructor
		for (int i = 0; i < buttonsArray.length; i++) {
			buttonsArray[i].addActionListener(new ButtonHandler(i));
		}

	}
	
	
	private class ButtonHandler implements ActionListener {
		private final int number; //number passed in in constructor
		private String numString = ""; //String to convert number to for text field
		String answerString; //answer received after valid calculation

		ButtonHandler(int number) {
			this.number = number;
			numbersTextField.setText(numString);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			if (number >= 0 && number <= 9) {
				numString = numbersTextField.getText();
				String concatNum = Integer.toString(number);
				numString = numString.concat(concatNum);
				numbersTextField.setText(numString);
			}
			// ADDITION
			else if (number == 10) {
				// checks initial text field
				if(!numbersTextField.getText().equals("") && !numbersTextField.getText().equals(".")){		
					if (getOperatorCount() == 0) {
						incrementOperatorCount();
						setOperator(1); //sets operator to addition
						numString = numbersTextField.getText();
						setEntry1(numString);
						numbersTextField.setText(""); //clears text field
					}
				}
			}
			// SUBTRACTION
			else if (number == 11) {
				// checks initial text field
				if(!numbersTextField.getText().equals("") && !numbersTextField.getText().equals(".")){	
					if (getOperatorCount() == 0) {
						incrementOperatorCount();
						setOperator(2); //sets operator to subtraction
						numString = numbersTextField.getText();
						setEntry1(numString);
						numbersTextField.setText(""); //clears text field
					}
				}
			} 
			// MULTIPLICATION
			else if (number == 12) {
				// checks initial text field
				if(!numbersTextField.getText().equals("") && !numbersTextField.getText().equals(".")){	
					if (getOperatorCount() == 0) {
						incrementOperatorCount();
						setOperator(3); //sets operator to multiplication
						numString = numbersTextField.getText();
						setEntry1(numString);
						numbersTextField.setText(""); //clears text field
					}
				}
			} 
			// DIVISION
			else if (number == 13) {
				// checks initial text field and will only act if text field has a valid number
				if(!numbersTextField.getText().equals("") && !numbersTextField.getText().equals(".")){	
					if (getOperatorCount() == 0) {
						incrementOperatorCount();
						setOperator(4); //sets operator to division
						numString = numbersTextField.getText();
						setEntry1(numString);
						numbersTextField.setText(""); //clears text field
					}
				}
			} 
			//CREATING A DECIMAL
			else if (number == 14) {
				if (getEntryNum() == 0) {
					if (getEntry1DecimalCount() == 0) {
						numString = numbersTextField.getText();
						numString = numString.concat(".");
						numbersTextField.setText(numString);
						incrementEntry1DecimalCount(); //increments so only one decimal can be set
					}
				} 
				else {
					if (getEntry2DecimalCount() == 0) {
						numString = numbersTextField.getText();
						numString = numString.concat(".");
						numbersTextField.setText(numString);
						incrementEntry2DecimalCount(); //increments so only one decimal can be set
					}
				}
			} 
			else if (number == 15) {
				double answer;
				int operator;
				
				//case: user selects '=' before entering any numbers
				if(getEntryNum()==0){
					setEntry1("0");
					setEntry2("0");
					answer=0.0;
					answerString = Double.toString(answer);
					numbersTextField.setText(answerString);
				}
				else{
					operator = getOperator();
					numString = numbersTextField.getText();
					
					// checks initial text field and will only act if text field has a valid number
					if (numString.equals("") || numString.equals(".")) {
						setEntry2("0");
					} 
					else {	
						setEntry2(numString);
					}
					numbersTextField.setText(""); //clears text field
					
					//calls addition method and sets answer to text field
					if (operator == 1) {
						answer = addition();
						answerString = Double.toString(answer);
						numbersTextField.setText(answerString);
					} 
					//calls subtraction method and sets answer to text field
					else if (operator == 2) {
						answer = subtraction();
						answerString = Double.toString(answer);
						numbersTextField.setText(answerString);
					} 
					//calls multiplication method and sets answer to text field
					else if (operator == 3) {
						answer = multiplication();
						answerString = Double.toString(answer);
						numbersTextField.setText(answerString);
					} 
					//calls division method and sets answer to text field
					else {
						answer = division();
						answerString = Double.toString(answer);
						numbersTextField.setText(answerString);
					}
					
					decrementOperatorCount(); //allows for more operations to be done
					decrementEntry2DecimalCount(); //resetting entry2
					setEntry2("0"); //resetting entry2
				}
			}
		}
	}
	
	/**
	 * Method to tell code which entry it is currently on
	 * @return integer telling whether the current entry is for entry1 or entry2
	 */
	private int getEntryNum() {
		return Entry1orEntry2;
	}
	
	/**
	 * Sets the double for entry1
	 * @param number taken in as a string and converted to a double and stored
	 */
	private void setEntry1(String number) {
		double temp = Double.parseDouble(number);
		this.entry1 = temp;
		Entry1orEntry2++;
	}
	/**
	 * Sets the double for entry2
	 * @param number taken in as a string and converted to a double and stored
	 */
	private void setEntry2(String number) {
		double temp = Double.parseDouble(number);
		this.entry2 = temp;
	}
	
	/**
	 * @return the double of entry1 to any calling method
	 */
	private double getEntry1() {
		return entry1;
	}
	
	/**
	 * @return the double of entry2 to any calling method
	 */
	private double getEntry2() {
		return entry2;
	}
	
	/**
	 * Sets the operator for action method to know what operation it is going to perform
	 * @param operator of an integer which specifies either addition, subtraction, multiplication, or division
	 */
	private void setOperator(int operator) {
		this.operatorType = operator;
	}

	/**
	 * @return the number associated with the operator type
	 */
	private int getOperator() {
		return operatorType;
	}

	/**
	 * increments the decimal count for entry1 so once a decimal is added, an additional decimal won't be allowed
	 */
	private void incrementEntry1DecimalCount() {
		entry1DecimalCount++;
	}
	
	/**
	 * @return integer of the decimal count for entry1 back to action method
	 */
	private int getEntry1DecimalCount() {
		return entry1DecimalCount;
	}

	/**
	 * increments the decimal count for entry2 so once a decimal is added, an additional decimal won't be allowed
	 */
	private void incrementEntry2DecimalCount() {
		entry2DecimalCount++;
	}
	
	/**
	 * Decrements the decimal count for entry2.  Method called after an answer is computed to allow the program to cycle again.
	 */
	private void decrementEntry2DecimalCount() {
		entry2DecimalCount--;
	}

	/**
	 * @return integer of the decimal count for entry2 back to action method
	 */
	private int getEntry2DecimalCount() {
		return entry2DecimalCount;
	}
	
	/**
	 * Increments operator count after first operator is selected, so no other operator can be selected
	 */
	private void incrementOperatorCount() {
		operatorCount++;
	}
	
	/**
	 * Decrements the operator count to reset to allow the program to cycle through again after an initial answer is computed
	 */
	private void decrementOperatorCount() {
		operatorCount--;
	}
	
	/**
	 * @return integer of the operator count to be used as an error check to make sure only one operator can be entered
	 */
	private int getOperatorCount() {
		return operatorCount;
	}

	/**
	 * Method called to add entry1 and entry2
	 * @return double of the answer of entry1 and entry2 added together
	 */
	private double addition() {
		double answer = getEntry1() + getEntry2();
		return answer;
	}

	/**
	 * Method called to subtract entry2 from entry1
	 * @return double of the answer of entry2 subtracted from entry1
	 */
	private double subtraction() {
		double answer = getEntry1() - getEntry2();
		return answer;
	}

	/**
	 * Method called to multiply entry1 and entry2
	 * @return double of the answer of entry1 and entry2 multiplied together
	 */
	private double multiplication() {
		double answer = getEntry1() * getEntry2();
		return answer;
	}

	/**
	 * Method called to divide entry1 by entry2
	 * @return double of the answer of entry1 divided by entry2
	 */
	private double division() {
		double answer = getEntry1() / getEntry2();
		return answer;
	}

	/**
	 * Method called by the constructor to create an array of the labels respective buttons will have
	 */
	private static void createbuttonNameArray() {
		buttonLabelArray[0] = "0";
		buttonLabelArray[1] = "1";
		buttonLabelArray[2] = "2";
		buttonLabelArray[3] = "3";
		buttonLabelArray[4] = "4";
		buttonLabelArray[5] = "5";
		buttonLabelArray[6] = "6";
		buttonLabelArray[7] = "7";
		buttonLabelArray[8] = "8";
		buttonLabelArray[9] = "9";
		buttonLabelArray[10] = "+";
		buttonLabelArray[11] = "-";
		buttonLabelArray[12] = "*";
		buttonLabelArray[13] = "/";
		buttonLabelArray[14] = ".";
		buttonLabelArray[15] = "=";
	}
}
