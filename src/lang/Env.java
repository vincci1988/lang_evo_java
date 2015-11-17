package lang;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class Env {

	Env() {
		rand = new Random();
	}

	void run() {
		Vector<Agent> agents = new Vector<Agent>();
		for (int i = 0; i < populationSize; i++) {
			agents.add(new Agent());
		}
		for (int i = 0; i < generationNum; i++) {
			generationRun(agents);
			selectAndMate(agents);
		}
	}

	void generationRun(Vector<Agent> agents) {
		// parenting phase
		int seniorCnt = populationSize / 2;
		for (int i = 0, j = seniorCnt; i < seniorCnt; i++, j++) {
			for (int k = 0; k < trialNumeberPerPair; k++) {
				trial(agents.get(j), agents.get(i));
				trial(agents.get(j), agents.get((i + 1) % seniorCnt));
			}
		}
		// socializing phase
		socialize(agents, 0, seniorCnt);
		socialize(agents, seniorCnt, agents.size());
	}

	void trial(Agent a1, Agent a2) {
		a1.receiver = 0;
		a2.receiver = 0;
		a1.position = rand.nextInt(maxDistToJungle) + 1;
		a2.position = rand.nextInt(maxDistToJungle) + 1;

		int step = 0;
		for (step = 0; step < trialLength; step++) {
			// act := (sig)|(mat=2,mov=1,idle=0)
			int act1 = a1.getAction();
			int act2 = a2.getAction();
			a1.receiver = act2 >> 2;
			a2.receiver = act1 >> 2;
			act1 &= 3;
			act2 &= 3;
			if (act1 == move)
				a1.position--;
			if (act2 == move)
				a2.position--;
			if (act1 == 2 && act2 == 2) {
				if (a1.weight >= 100 && a2.weight >= 100) {
					a1.stimulate(a2.weight * 0.1);
					a2.stimulate(a1.weight * 0.1);
				} else {
					if (a1.weight < 100)
						a1.stimulate(failMatePenalty);
					if (a2.weight < 100)
						a2.stimulate(failMatePenalty);
				}
			} else {
				if (act2 == mate)
					a2.stimulate(failMatePenalty);
				if (act1 == mate)
					a1.stimulate(failMatePenalty);
			}
			if (a1.position == 0 && a2.position == 0) {
				a1.stimulate(successHuntReward);
				a2.stimulate(successHuntReward);
				a1.grow();
				a2.grow();
			} else {
				if (a1.position == 0) {
					a1.stimulate(failHuntPenalty);
				}
				if (a2.position == 0) {
					a2.stimulate(failHuntPenalty);
				}
					
			}
			if (a1.position == 0 || a2.position == 0 || act1 == mate || act2 == mate)
				break;

		}
		a1.consume();
		a2.consume();
	}

	void socialize(Vector<Agent> agents, int start, int end) {
		for (int i = start; i < end - 1; i++) {
			for (int j = i + 1; j < end; j++) {
				for (int k = 0; k < trialNumeberPerPair; k++) {
					trial(agents.get(i), agents.get(j));
				}
			}
		}
	}

	void selectAndMate(Vector<Agent> agents) {
		int survivorCnt = populationSize / 2;
		for (int i = 0; i < survivorCnt; i++) {
			if (agents.get(i).age >= maxAge)
				agents.get(i).reward = Double.NEGATIVE_INFINITY;
		}
		Collections.sort(agents);
		printAgents(agents);
		for (int i = 0, j = survivorCnt; i < survivorCnt; i++, j++) {
			agents.get(i).age++; // age is the number of generations in which an
									// agent survives and reproduces
			agents.get(i).reward = 0; // reset reward for the next generation
			agents.set(j, agents.get(i).mate(agents.get((i + 1) % survivorCnt)));
		}
	}

	void printAgents(Vector<Agent> agents) {
		for (int i = 0; i < agents.size(); i++) {
			System.out.print("(" + agents.get(i).age + "," + agents.get(i).reward + "," + agents.get(i).weight + ")\t");
		}
		System.out.println();
	}

	private Random rand;
	final int generationNum = 100;
	final int populationSize = 50; // note: should be an even number
	final int trialNumeberPerPair = 100;
	final int trialLength = 100;
	final int maxAge = 4; // max number of generations to survive and mate
	final int maxDistToJungle = 5;
	final int mate = 2;
	final int move = 1;
	final int idle = 0;
	final double successHuntReward = 10.0;
	final double failHuntPenalty = -5.0;
	final double failMatePenalty = -10.0;
}
