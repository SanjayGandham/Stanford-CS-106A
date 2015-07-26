/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * Gandham Sanjay
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;


public class Pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Height of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 24;
	
	int i=1;
	int x=BRICK_WIDTH;
	int y=BRICK_HEIGHT;
	int j;
	int Bricks=BRICKS_IN_BASE;
	int numBricks=BRICKS_IN_BASE;
	
	public void run() {
		while(numBricks>0){
			if(numBricks%2==0){
				j=0;
				while(Bricks>0){
					insertBrickEve();
					Bricks--;
					j++;
				}	
			}else{
				j=0;
				while(Bricks>0){
					insertBrickOdd();
					Bricks--;
					j++;
				}
			}
			numBricks--;
			Bricks=numBricks;
			i++;
		}
	}
	private void insertBrickEve(){
		
		int width=getWidth();
		int height=getHeight();
		GRect brick=new GRect(x,y);
		add (brick,((width/2)-(numBricks*x/2))+(j*x),height-(i*y));
	}
	
	private void insertBrickOdd(){
		int width=getWidth();
		int height=getHeight();
		GRect brick=new GRect(x,y);
		add (brick,(((width/2)-(x/2))-((numBricks-1)*x/2))+(j*x),height-(i*y));
	}
}

