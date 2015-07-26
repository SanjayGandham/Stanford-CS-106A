/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * Gandham Sanjay
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("Enter values to compute the Pythagorean theorem.");
		double a=readDouble("a: ");
		double b=readDouble("b: ");
		double x=(a*a)+(b*b);
		double c=Math.sqrt(x);
		println("c = "+c+"");
	}
}
