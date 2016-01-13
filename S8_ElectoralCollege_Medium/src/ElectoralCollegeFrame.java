
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ElectoralCollegeFrame extends JFrame {
	private JFrame frame= new JFrame();
	private StateNameAndNumberOfVotes[] electoralCollegeObjectsList = new StateNameAndNumberOfVotes[56];
	
	//All radio buttons in panel
	JRadioButton[] democratRadio= new JRadioButton[56]; 
	JRadioButton[] republicanRadio= new JRadioButton[56];
	JRadioButton[] independentRadio= new JRadioButton[56];
	
	//JPanels of 4 columns
	private JPanel parentPanel = new JPanel();
	private JPanel column1 = new JPanel();
	private JPanel column2 = new JPanel();
	private JPanel column3 = new JPanel();
	private JPanel column4 = new JPanel();
	
	//Array of each individual state panel for the 4 respective columns
	private JPanel[] statePanel1 = new JPanel[14];
	private JPanel[] statePanel2 = new JPanel[14];
	private JPanel[] statePanel3 = new JPanel[14];
	private JPanel[] statePanel4 = new JPanel[14];
	
	private int democratVoteTotal=0; //running democrat vote
	private int republicanVoteTotal=0; //running republican vote
	private int independentVoteTotal=0; //running independent vote
	private int position; //grand position of all 56 panels
	
	/**
	 * Constructor for ElectoralCollegeFrame that sets panel layouts, and creates individual panels for each
	 * district and adds them at their intended location.
	 */
	ElectoralCollegeFrame(){
		super("VOTE DEMOCRAT 2016");
		createColumnArrays();
		parentPanel.setLayout(new GridLayout(1, 4));
		column1.setLayout(new GridLayout(14, 1));
		column2.setLayout(new GridLayout(14, 1));
		column3.setLayout(new GridLayout(14, 1));
		column4.setLayout(new GridLayout(14, 1));
		
		StateNameAndNumberOfVotes electoralCollegeObject;
		//loops through electoral college object array and gets one object at a time
		for(int i=0; i<56; i++){
			position=i;
			electoralCollegeObject = electoralCollegeObjectsList[position];
			if(i<14){
				//creates the panel with functional radios and adds to column1 panel
				statePanel1[i] = createStatePanel(electoralCollegeObject, position); 
				column1.add(statePanel1[i]);
			}
			else if(i> 13 && i<28){
				//creates the panel with functional radios and adds to column2 panel
				statePanel2[i-14] = createStatePanel(electoralCollegeObject, position);
				column2.add(statePanel2[i-14]);
			}
			else if(i> 27 && i<42){
				//creates the panel with functional radios and adds to column3 panel
				statePanel3[i-28] = createStatePanel(electoralCollegeObject, position);
				column3.add(statePanel3[i-28]);
			}
			else if(i>41 && i<56){
				//creates the panel with functional radios and adds to column4 panel
				statePanel4[i-42] = createStatePanel(electoralCollegeObject, position);
				column4.add(statePanel4[i-42]);
			}
		}
		//add all columns to parent panel
		parentPanel.add(column1);
		parentPanel.add(column2);
		parentPanel.add(column3);
		parentPanel.add(column4);
		add(parentPanel); //add parent panel to frame
	}
	
	/**
	 * Method to create a panel for each individual district.  This method takes in its
	 * grand list postion and the object it is creating a panel for.  This method will implement
	 * radio buttons to each panel and make them funcitonal and personalized for each panel.
	 * @param electoralObject which contains the state name and the number of votes that state has
	 * @param position grand position of all 56 objects
	 * @return a JPanel of an individual district
	 */
	private JPanel createStatePanel(StateNameAndNumberOfVotes electoralObject, int position){
		JPanel individualDistrictPanel = new JPanel();
		individualDistrictPanel.setLayout(new GridLayout(1,0));
		int numberOfStateVotes= electoralObject.getVotes(); //amount of states particular district gets
		String stateName= electoralObject.getState();
		JLabel jstateName= new JLabel(stateName);
		
		//create radio buttons specific to place in list
		democratRadio[position]= new JRadioButton("DEM.", false);
		republicanRadio[position]= new JRadioButton("REP.", false);
		independentRadio[position]= new JRadioButton("IND.", false);
		
		//add label and radio buttons to JPanel
		individualDistrictPanel.add(jstateName, BorderLayout.WEST);
		individualDistrictPanel.add(democratRadio[position], BorderLayout.EAST);
		individualDistrictPanel.add(republicanRadio[position], BorderLayout.EAST);
		individualDistrictPanel.add(independentRadio[position], BorderLayout.EAST);
		
		//Group radio buttons
		ButtonGroup politicalParties = new ButtonGroup();
		politicalParties.add(democratRadio[position]);
		politicalParties.add(republicanRadio[position]);
		politicalParties.add(independentRadio[position]);
		
		// register events for JRadioButtons
		RadioButtonHandler handler = new RadioButtonHandler(numberOfStateVotes, position);
		democratRadio[position].addItemListener(handler);
		republicanRadio[position].addItemListener(handler);
		independentRadio[position].addItemListener(handler);
	      
		return individualDistrictPanel;
	}
	
	
	// private inner class to handle radio button events
	/**
	 * Private class to handle radio buttons and keep the total count of votes for all parties
	 * to determine a winner
	 */
	private class RadioButtonHandler implements ItemListener {
		private int votes;
		private int decrementVotes;
		private int position;
		
		//constructor receiving the amount of votes particular district has and the position in grand list
		public RadioButtonHandler(int numberOfVotes, int i) {
			votes = numberOfVotes;
			decrementVotes= -1*votes;
			position=i;
		}

		// handle radio button events
		@Override
		public void itemStateChanged(ItemEvent event) {
			
			//Democrat radio button selected
			if(event.getSource() == democratRadio[position]){
				if(event.getStateChange() == ItemEvent.DESELECTED){
					addToDemocratVoteTotal(decrementVotes);
				}
				else if(event.getStateChange()== ItemEvent.SELECTED){
					addToDemocratVoteTotal(votes);
				}
			}
			
			//Republican radio button selected
			else if(event.getSource() == republicanRadio[position]){
				if(event.getStateChange() == ItemEvent.DESELECTED){
					addToRepublicanVoteTotal(decrementVotes);
				}
				else if(event.getStateChange()== ItemEvent.SELECTED){
					addToRepublicanVoteTotal(votes);
				}
			}
			//Independent radio button selected
			else if(event.getSource() == independentRadio[position]){	
				if(event.getStateChange() == ItemEvent.DESELECTED){
					addToIndependentVoteTotal(decrementVotes);
				}
				
				else if(event.getStateChange()== ItemEvent.SELECTED){
					addToIndependentVoteTotal(votes);
				}
			}
		}
	}
	
	/**
	 * Method take in an integer to add to the running total of democratic votes 
	 * @param numberOfVotes int of amount of votes to add
	 */
	private void addToDemocratVoteTotal(int numberOfVotes){
		democratVoteTotal+=numberOfVotes;
		//System.out.println("Dem Total: " + democratVoteTotal);
		if(democratVoteTotal>= 270){
			JOptionPane.showMessageDialog(frame, "Democrats have won the election!");
		}
	}

	/**
	 * Method take in an integer to add to the running total of democratic votes 
	 * @param numberOfVotes int of amount of votes to add
	 */
	private void addToRepublicanVoteTotal(int numberOfVotes){
		republicanVoteTotal+=numberOfVotes;
		//System.out.println("Repub Total: " + republicanVoteTotal);
		if(republicanVoteTotal>= 270){
			JOptionPane.showMessageDialog(frame, "Democrats have won the election.");
		}
	}

	/**
	 * Method take in an integer to add to the running total of democratic votes 
	 * @param numberOfVotes int of amount of votes to add
	 */
	private void addToIndependentVoteTotal(int numberOfVotes){
		independentVoteTotal+=numberOfVotes;
		//System.out.println("Ind Total: " + independentVoteTotal);
		if(independentVoteTotal>= 270){
			JOptionPane.showMessageDialog(frame, "Independent party has won the election.");
		}
	}	
	
	/**
	 * Method to create the grand electoral college objects list
	 */
	private void createColumnArrays(){
		electoralCollegeObjectsList[0] = new StateNameAndNumberOfVotes("Alabama", 9);
		electoralCollegeObjectsList[1] = new StateNameAndNumberOfVotes("Alaska", 3);
		electoralCollegeObjectsList[2] = new StateNameAndNumberOfVotes("Arizona", 11);
		electoralCollegeObjectsList[3] = new StateNameAndNumberOfVotes("Arkanasas", 6);
		electoralCollegeObjectsList[4] = new StateNameAndNumberOfVotes("California", 55);
		electoralCollegeObjectsList[5] = new StateNameAndNumberOfVotes("Colorado", 9);
		electoralCollegeObjectsList[6] = new StateNameAndNumberOfVotes("Connecticut", 7);
		electoralCollegeObjectsList[7] = new StateNameAndNumberOfVotes("Delaware", 3);
		electoralCollegeObjectsList[8] = new StateNameAndNumberOfVotes("Florida", 29);
		electoralCollegeObjectsList[9] = new StateNameAndNumberOfVotes("Georgia", 16);
		electoralCollegeObjectsList[10] = new StateNameAndNumberOfVotes("Hawaii", 4);
		electoralCollegeObjectsList[11] = new StateNameAndNumberOfVotes("Idaho", 4);
		electoralCollegeObjectsList[12] = new StateNameAndNumberOfVotes("Illinois", 20);
		electoralCollegeObjectsList[13] = new StateNameAndNumberOfVotes("Indiana", 6);
		
		//Column 2
		electoralCollegeObjectsList[14] = new StateNameAndNumberOfVotes("Iowa", 6);
		electoralCollegeObjectsList[15] = new StateNameAndNumberOfVotes("Kansas", 6);
		electoralCollegeObjectsList[16] = new StateNameAndNumberOfVotes("Kentucky", 8);
		electoralCollegeObjectsList[17] = new StateNameAndNumberOfVotes("Louisiana", 8);
		electoralCollegeObjectsList[18] = new StateNameAndNumberOfVotes("Maine_1st", 1);
		electoralCollegeObjectsList[19] = new StateNameAndNumberOfVotes("Maine_2nd", 1);
		electoralCollegeObjectsList[20] = new StateNameAndNumberOfVotes("Maine_Popular", 2);
		electoralCollegeObjectsList[21] = new StateNameAndNumberOfVotes("Maryland", 10);
		electoralCollegeObjectsList[22] = new StateNameAndNumberOfVotes("Massachusetts", 11);
		electoralCollegeObjectsList[23] = new StateNameAndNumberOfVotes("Michigan", 16);
		electoralCollegeObjectsList[24] = new StateNameAndNumberOfVotes("Minnesota", 10);
		electoralCollegeObjectsList[25] = new StateNameAndNumberOfVotes("Mississippi", 6);
		electoralCollegeObjectsList[26] = new StateNameAndNumberOfVotes("Missouri", 10);
		electoralCollegeObjectsList[27] = new StateNameAndNumberOfVotes("Montana", 3);
		
		//Column 3
		electoralCollegeObjectsList[28] = new StateNameAndNumberOfVotes("Nebraska_1st", 1);
		electoralCollegeObjectsList[29] = new StateNameAndNumberOfVotes("Nebraska_2nd", 1);
		electoralCollegeObjectsList[30] = new StateNameAndNumberOfVotes("Nebraska_3rd", 1);
		electoralCollegeObjectsList[31] = new StateNameAndNumberOfVotes("Nebraska_Popular", 2);
		electoralCollegeObjectsList[32] = new StateNameAndNumberOfVotes("Nevada", 6);
		electoralCollegeObjectsList[33] = new StateNameAndNumberOfVotes("New_Hampshire", 4);
		electoralCollegeObjectsList[34] = new StateNameAndNumberOfVotes("New_Jersey", 14);
		electoralCollegeObjectsList[35] = new StateNameAndNumberOfVotes("New_Mexico", 5);
		electoralCollegeObjectsList[36] = new StateNameAndNumberOfVotes("New_York", 29);
		electoralCollegeObjectsList[37] = new StateNameAndNumberOfVotes("North_Carolina", 15);
		electoralCollegeObjectsList[38] = new StateNameAndNumberOfVotes("North_Dakota", 3);
		electoralCollegeObjectsList[39] = new StateNameAndNumberOfVotes("Ohio", 18);
		electoralCollegeObjectsList[40] = new StateNameAndNumberOfVotes("Oklahoma", 7);
		electoralCollegeObjectsList[41] = new StateNameAndNumberOfVotes("Oregon", 7);
				
		//Column4
		electoralCollegeObjectsList[42] = new StateNameAndNumberOfVotes("Pennsylvania", 20);
		electoralCollegeObjectsList[43] = new StateNameAndNumberOfVotes("Rhode_Island", 4);
		electoralCollegeObjectsList[44] = new StateNameAndNumberOfVotes("South_Carolina", 9);
		electoralCollegeObjectsList[45] = new StateNameAndNumberOfVotes("South_Dakota", 3);
		electoralCollegeObjectsList[46] = new StateNameAndNumberOfVotes("Tennessee", 11);
		electoralCollegeObjectsList[47] = new StateNameAndNumberOfVotes("Texas", 38);
		electoralCollegeObjectsList[48] = new StateNameAndNumberOfVotes("Utah", 6);
		electoralCollegeObjectsList[49] = new StateNameAndNumberOfVotes("Vermont", 3);
		electoralCollegeObjectsList[50] = new StateNameAndNumberOfVotes("Virginia", 13);
		electoralCollegeObjectsList[51] = new StateNameAndNumberOfVotes("Washington", 12);
		electoralCollegeObjectsList[52] = new StateNameAndNumberOfVotes("West_Virginia", 5);
		electoralCollegeObjectsList[53] = new StateNameAndNumberOfVotes("Wisconsin", 10);
		electoralCollegeObjectsList[54] = new StateNameAndNumberOfVotes("Wyoming", 3);
		electoralCollegeObjectsList[55] = new StateNameAndNumberOfVotes("Washington_D.C.", 3);
	}
}
