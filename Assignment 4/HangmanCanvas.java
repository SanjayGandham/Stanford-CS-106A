/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	int a=0;
	GLabel name;
	String temp ="         ";
	/** Resets the display so that only the scaffold appears */
	public void reset() {
		double y = getHeight();
		double x = getWidth();
		GLine scaf = new GLine(x / 2 - BEAM_LENGTH, y / 2 - BODY_LENGTH / 2 - 2
				* HEAD_RADIUS - ROPE_LENGTH, x / 2 - BEAM_LENGTH, y / 2
				- BODY_LENGTH / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH
				+ SCAFFOLD_HEIGHT);
		GLine beam = new GLine(x / 2 - BEAM_LENGTH, y / 2 - BODY_LENGTH / 2 - 2
				* HEAD_RADIUS - ROPE_LENGTH, x / 2, y / 2 - BODY_LENGTH / 2 - 2
				* HEAD_RADIUS - ROPE_LENGTH);
		GLine rope = new GLine(x / 2, y / 2 - BODY_LENGTH / 2 - 2 * HEAD_RADIUS
				- ROPE_LENGTH, x / 2, y / 2 - BODY_LENGTH / 2 - 2 * HEAD_RADIUS);
		
		add(scaf);
		add(beam);
		add(rope);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		 name= new GLabel(word);
		name.setFont("SansSerif-24");
		GObject obj = getElementAt(getWidth() / 2 - BEAM_LENGTH, getHeight()
				/ 2 - BODY_LENGTH / 2 - 2 * HEAD_RADIUS - ROPE_LENGTH
				+ SCAFFOLD_HEIGHT + name.getHeight());
		if (obj != null) {
			remove(obj);
		}
		add(name, getWidth() / 2 - BEAM_LENGTH,
				getHeight() / 2 - BODY_LENGTH / 2 - 2 * HEAD_RADIUS
						- ROPE_LENGTH + SCAFFOLD_HEIGHT + name.getHeight());
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {
		GObject obj=getElementAt(name.getX()+name.getWidth(),name.getY());
		if(obj!=null)remove(obj);
		temp=temp+letter;
		GLabel wrong = new GLabel(temp);
		wrong.setFont("SansSerif-24");
		add(wrong,name.getX()+name.getWidth(),name.getY());
		a++;
		double y = getHeight();
		double x = getWidth();
		GOval head = new GOval(x / 2 - HEAD_RADIUS, y / 2 - BODY_LENGTH / 2 - 2
				* HEAD_RADIUS, 2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		GLine body = new GLine(head.getX() + HEAD_RADIUS, head.getY() + 2
				* HEAD_RADIUS, head.getX() + HEAD_RADIUS, head.getY() + 2
				* HEAD_RADIUS + BODY_LENGTH);
		GLine luarm = new GLine(body.getX(),
				body.getY() + ARM_OFFSET_FROM_HEAD, body.getX()
						- UPPER_ARM_LENGTH, body.getY() + ARM_OFFSET_FROM_HEAD);
		GLine llarm = new GLine(body.getX() - UPPER_ARM_LENGTH, body.getY()
				+ ARM_OFFSET_FROM_HEAD, body.getX() - UPPER_ARM_LENGTH,
				body.getY() + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		GLine ruarm = new GLine(body.getX(),
				body.getY() + ARM_OFFSET_FROM_HEAD, body.getX()
						+ UPPER_ARM_LENGTH, body.getY() + ARM_OFFSET_FROM_HEAD);
		GLine rlarm = new GLine(body.getX() + UPPER_ARM_LENGTH, body.getY()
				+ ARM_OFFSET_FROM_HEAD, body.getX() + UPPER_ARM_LENGTH,
				body.getY() + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		GLine lthigh = new GLine(body.getX(), body.getY() + BODY_LENGTH,
				body.getX() - HIP_WIDTH, body.getY() + BODY_LENGTH);
		GLine rthigh = new GLine(body.getX(), body.getY() + BODY_LENGTH,
				body.getX() + HIP_WIDTH, body.getY() + BODY_LENGTH);
		GLine lleg = new GLine(x / 2 - HIP_WIDTH, lthigh.getY(), x / 2
				- HIP_WIDTH, lthigh.getY() + LEG_LENGTH);
		GLine rleg = new GLine(x / 2 + HIP_WIDTH, lthigh.getY(), x / 2
				+ HIP_WIDTH, lthigh.getY() + LEG_LENGTH);
		GLine lfoot = new GLine(lleg.getX(), lleg.getY() + LEG_LENGTH,
				lleg.getX() - FOOT_LENGTH, lleg.getY() + LEG_LENGTH);
		GLine rfoot = new GLine(rleg.getX(), rleg.getY() + LEG_LENGTH,
				rleg.getX() + FOOT_LENGTH, lleg.getY() + LEG_LENGTH);
		if(a==1)add(head);
		if(a==2)add(body);
		if(a==3){
			add(luarm);
			add(llarm);
		}
		if(a==4){
			add(ruarm);
			add(rlarm);
		}
		if(a==5){
			add(lthigh);
			add(lleg);
		}
		if(a==6){
			add(rthigh);
			add(rleg);
		}
		if(a==7)add(lfoot);
		if(a==8)add(rfoot);
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
