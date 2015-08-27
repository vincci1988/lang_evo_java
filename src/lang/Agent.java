package lang;

public class Agent {
	
	Agent(int position, int weight) {
		this.position = position;
		this.weight = weight;
	}
	
	/*
	 * send a two-bit msg (i.e. 0 - 3) to the environment
	 */
	public int send(){
		return 0;
	}
	
	/*
	 * receive a two-bit msg (i.e. 0 -3) from the environment
	 */
	public void receive(int msg) {
		receiver = msg;
	}
	
	/*
	 * returns the action given current position, weight, and msg
	 * def: 0 = no action, 1 = move, 2 = mate, 3 = move & mate
	 */
	public int act() {
		return 0;
	}

	int position;
	int weight;
	int receiver; // a record of msg received from a partner

}
