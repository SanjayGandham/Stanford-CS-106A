/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * Gandham Sanjay
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;

import java.awt.*;

public class Target extends GraphicsProgram {	
	public void run() {
		double x=getHeight();
		double y=getWidth();
		GOval outer=new GOval((y/2)-72,(x/2)-72,144,144);
		outer.setFilled(true);
		outer.setFillColor(Color.red);
		outer.setColor(Color.red);
		add(outer);
		
		GOval middle=new GOval((y/2)-46.8,(x/2)-46.8,93.6,93.6);
		middle.setFilled(true);
		middle.setFillColor(Color.white);
		middle.setColor(Color.white);
		add(middle);
		
		GOval inner=new GOval((y/2)-21.6,(x/2)-21.6,43.2,43.2);
		inner.setFilled(true);
		inner.setFillColor(Color.red);
		inner.setColor(Color.red);
		add(inner);
	}
}
