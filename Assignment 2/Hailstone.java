/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * GANDHAM SANJAY
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		int num=readInt("Enter a number: ");
		int steps=0;
		while(num!=1){
			if(num%2==0){
				println(num+" is even, so I take half: "+(num/2));
				num=num/2;
			}else{
				println(num+" is odd, so I take 3n+1: "+((3*num)+1)); 
				num=(3*num)+1;
			}
			steps++;
		}
		println("The process took "+steps+" steps to reach 1");
	}	
}
