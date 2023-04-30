import common.BaseThread;

public class Philosopher extends BaseThread {
	/**
	 * Max time an action can take (in milliseconds)
	 */
	public static final long TIME_TO_WASTE = 1000;

	public void eat() {
		try {
			System.out.println("Philosopher " + getTID() + " is eating.");
			Philosopher.yield();
			sleep((long) (Math.random() * TIME_TO_WASTE));
			Philosopher.yield();
			System.out.println("Philosopher " + getTID() + " is finished eating.");
		} catch (InterruptedException e) {
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	public void think() {
		try {
			System.out.println("Philosopher " + getTID() + " is thinking.");
			Philosopher.yield();
			sleep((long) (Math.random() * TIME_TO_WASTE));
			Philosopher.yield();
			System.out.println("Philosopher " + getTID() + " is finished thinking.");
		} catch (InterruptedException e) {
			System.err.println("Philosopher.think():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	public void talk() {
		try {
			System.out.println("Philosopher " + getTID() + " is talking.");
			Philosopher.yield();
			saySomething();
			Philosopher.yield();
			System.out.println("Philosopher " + getTID() + " is finished talking.");
		} catch (Exception e) {
			System.err.println("Philosopher.talk():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * No, this is not the act of running, just the overridden Thread.run()
	 */
	public void run() {
		for (int i = 0; i < DiningPhilosophers.DINING_STEPS; i++) {
			DiningPhilosophers.soMonitor.pickUp(getTID());

			eat();

			DiningPhilosophers.soMonitor.putDown(getTID());

			think();

			/*
			 * The program will randomly decide if the given 
			 * philospher will talk or not. 
			 */
			if (Math.random() < 0.5) {
				DiningPhilosophers.soMonitor.requestTalk();
				talk();
				DiningPhilosophers.soMonitor.endTalk();
			}

			Thread.yield();
		}
	} 
	
	public void saySomething() {
		String[] astrPhrases = {
				"Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
				"You know, true is false and false is true if you think of it",
				"2 + 2 = 5 for extremely large values of 2...",
				"If thee cannot speak, thee must be silent",
				"My number is " + getTID() + ""
		};

		System.out.println(
				"Philosopher " + getTID() + " says: " +
						astrPhrases[(int) (Math.random() * astrPhrases.length)]);
	}
}