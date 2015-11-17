package lang;

public abstract class CtrlUnit {

	abstract CtrlUnit mutateAndMate(CtrlUnit other);	
	abstract int decide(int input);	
	abstract void update(double reward);
	
	int input_length;
	int output_length;
}
