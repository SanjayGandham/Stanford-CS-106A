/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void run() {
		while (frontIsClear()) {
			putBeeper();
			move();
		}
		findmidpoint();
	}

	private void findmidpoint() {
		turnAround();
		movetowall();
		turnAround();
		while (frontIsClear()) {
			doit();
		}
	}

	private void movetowall() {
		while (frontIsClear()) {
			move();
		}
	}

	private void doit() {
		if (beepersPresent()) {
			move();
			if (beepersPresent()) {
				turnAround();
				move();
				pickBeeper();
				findmidpoint();
			} else {
				turnAround();
				move();
				turnRight();
			}
		} else {
			move();
			doit();
		}

	}
}
