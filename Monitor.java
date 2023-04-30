import java.util.Arrays;

public class Monitor {
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	private boolean[] chopsticks;
	private boolean isTalking = false;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers) {
		chopsticks = new boolean[piNumberOfPhilosophers];
		Arrays.fill(chopsticks, true);
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID) {

		/*
		 * The leftChopstickIndex is calculated as piTID - 1, and then adjusted 
		 * by taking the modulo chopsticks.length.
		 * The right chopstick index is similarly calculated as piTID % chopsticks.length, 
		 * which gives the index of the chopstick to the right of the philosopher.
		 */
		int leftChopstickIndex = piTID - 1;
		int rightChopstickIndex = piTID % chopsticks.length;

		while (!chopsticks[leftChopstickIndex] || !chopsticks[rightChopstickIndex]) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		chopsticks[leftChopstickIndex] = false;
		chopsticks[rightChopstickIndex] = false;
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID) {
		/*
		 * The leftChopstickIndex is calculated as piTID - 1, and then adjusted 
		 * by taking the modulo chopsticks.length.
		 * The right chopstick index is similarly calculated as piTID % chopsticks.length, 
		 * which gives the index of the chopstick to the right of the philosopher.
		 */
		int leftChopstickIndex = piTID - 1;
		int rightChopstickIndex = piTID % chopsticks.length;
		
		chopsticks[leftChopstickIndex] = true;
		chopsticks[rightChopstickIndex] = true;
		
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk() {
		while (isTalking) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//Setting the boolean to true so no other philosophers can talk
		isTalking = true;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk() {
		//Setting the boolean to false so other philosphers can request to talk
		isTalking = false;
		notifyAll();
	}
}

// EOF
