/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
		while (frontIsClear()) {
			turnLeft();
			repairAvenue();
			turnLeft();
			nextAvenue();
		}
		/* Repairs the last avenue */
		turnLeft();
		repairAvenue();
	}

	private void repairAvenue() {
		/* Puts beepers and moves to the wall */
		while (frontIsClear()) {
			if (noBeepersPresent()) {
				putBeeper();
			} else {
				move();
			}
		}
		if (noBeepersPresent()) {
			putBeeper();
		}
		/* Turns around and moves to the wall */
		turnAround();
		while (frontIsClear()) {
			move();
		}

	}

	/* Goes to next avenue */
	private void nextAvenue() {
		move();
		move();
		move();
		move();
	}
}
