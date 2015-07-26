/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * Gandham Sanjay
 * This file is the starter file for the FindRange problem.
 */
import acm.program.*;

public class FindRange extends ConsoleProgram {
	private static final int SENTINEL = 0;
	public int value;
	public void run() {
		println("This program find the largest and smallest numbers.");
		int currentlymax = readInt("?");
		int currentlymin = currentlymax;
		if (currentlymax != SENTINEL) {
			while (even()) {
				int value = readInt("?");
				if (value == SENTINEL)
					break;
				if (value > currentlymax) {
					currentlymax = value;
				} else {
					if (currentlymin > value) {
						currentlymin = value;
					}
				}
			}
			println("smallest: " + currentlymin);
			println("largest: " + currentlymax);
		} else {
			println("No values have be entered ");
		}
	}

	public boolean even() {
		boolean stat=(value%2==0);
		return stat;
	}
}
