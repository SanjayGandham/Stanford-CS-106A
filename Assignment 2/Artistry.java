import java.awt.*;
import acm.program.*;
import acm.graphics.*;

public class Artistry extends GraphicsProgram {
	public void run() {
		int rad = getHeight();
		int wid = getWidth();
		GOval circle = new GOval(0, 0, rad, rad);
		circle.setFilled(true);
		circle.setFillColor(Color.white);
		circle.setColor(Color.GRAY);
		add(circle);
		
		GOval eye1 = new GOval(rad/4,rad/3,40,40);
		eye1.setFilled(true);
		eye1.setFillColor(Color.red);
		add(eye1);
		
		GOval eye2 = new GOval(3*rad/4,rad/3,40,40);
		eye2.setFilled(true);
		eye2.setFillColor(Color.red);
		add(eye2);
		
		GRect nose = new GRect(rad/2+20,rad/2,3,80);
		nose.setFilled(true);
		nose.setFillColor(Color.black);
		add(nose);
		
		GArc mouth=new GArc(3*rad/4,rad/2+150,230,85);
		mouth.setColor(Color.black);
		add(mouth,rad/6,rad/15);
		
		
	
		GLabel name1= new GLabel("Gandham Sanjay");
		double x=name1.getWidth();
		add(name1,wid-x-10,rad-10);
		
	}
}
