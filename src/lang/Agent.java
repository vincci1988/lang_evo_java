package lang;

public class Agent implements Comparable<Agent> {

	public Agent() {
		this.position = 0;
		this.weight = 10;
		age = 0;
		reward = 0;
		receiver = 0;
		ctrl = new Brain(input_length, output_length);
	}
	
	public Agent(int position, int weight) {
		this.position = position;
		this.weight = weight;
		age = 0;
		reward = 0;
		receiver = 0;
		ctrl = new Brain(input_length, output_length);
	}
	
	Agent mate(Agent spouse) {
		Agent baby = new Agent();
		baby.ctrl = ctrl.mutateAndMate(spouse.ctrl);
		return baby;
	}
	
	//act: sig|mat|mov, x11=>x10.
	int getAction() {
		int act = ctrl.decide(encode());
		if ((act & 3) == 3) act &= 6;
		return act;
	}
	
	private int encode() {
		int status = (receiver << 2);
		if (position == 1) status |= 1;
		if (weight >= 100) status |= 2;
		return status;
	}
	
	void grow() {
		weight += growth;
		if (weight > maxWeight) weight = maxWeight;
	}
	
	void consume() {
		weight--;
		if (weight < 10) weight = 10;
	}
	
	void stimulate(double reward) {
		ctrl.update(reward);
		this.reward += reward;
	}
	
	public int compareTo(Agent other) {
		double diff = this.reward - other.reward;
		if (diff == 0) return 0;
		return diff < 0 ? 1 : -1;
	}

	int position;
	int weight;
	int age;
	double reward;
	int receiver;
	CtrlUnit ctrl;
	final int input_length = 3;
	final int output_length = 3;
	final int growth = 10;
	final int maxWeight = 200;
}
