package lang;

public class Agent {
	
	public Agent(int position, int weight) {
		this.position = position;
		this.weight = weight;
		receiver = 0;
	}
	
	int getAction() {
		return ctrl.decide(encode());
	}
	
	private int encode() {
		int status = (receiver << 2);
		if (position == 1) status |= 1;
		if (weight > 100) status |= 2;
		return status;
	}

	int position;
	int weight;
	private int receiver;
	private Controller ctrl;
}
