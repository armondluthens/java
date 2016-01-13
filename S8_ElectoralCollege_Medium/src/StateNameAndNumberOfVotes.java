
public class StateNameAndNumberOfVotes {
	private String state;
	private int votes;
	
	StateNameAndNumberOfVotes(String state, int votes){
		this.state=state;
		this.votes=votes;
	}
	
	public String getState(){
		return state;
	}
	public int getVotes(){
		return votes; 
	}
}
