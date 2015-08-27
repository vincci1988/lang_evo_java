package lang;

import java.lang.reflect.Array;
import java.util.HashMap;

public class Controller {
	
	/*
	 * length refers to the number of bits in inputs and outputs
	 */
	Controller(int input_length, int output_length) {
		this.input_length = input_length;
		this.output_length = output_length;
		policy = new HashMap<Integer, Array>();
	}
	
	int decide(int input) {
		if (policy.containsKey(input)) {
			Array.getDouble(policy.get(input), 0);
		}
		return 0;
	}
	
	private int output_length;
	private int input_length;
	private HashMap<Integer, Array> policy;
}
