package lang;

public class Brain extends CtrlUnit {
	public Brain(int input_length, int output_length) {
		this.input_length = input_length;
		this.output_length = output_length;
		memory = 0;
	}

	CtrlUnit mutateAndMate(CtrlUnit other) {
		return new Brain(input_length, output_length);
	};

	int decide(int input) {
		if (memory++ % 2 == 0) {
			return (input & 1) == 1 ? 4 : 0;
		} 
		if ((input & 1) == 0) return 1;
		if ((input & 4) != 0) return 5;
		return 0;
	};

	void update(double reward) {
		memory = 0;
	}

	int memory;
}
