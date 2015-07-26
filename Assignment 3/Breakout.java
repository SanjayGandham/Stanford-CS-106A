/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1)
			* BRICK_SEP)
			/ NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private double vx, vy = 5.0;

	private RandomGenerator rgen = RandomGenerator.getInstance();

	double rem = WIDTH
			- ((NBRICKS_PER_ROW * BRICK_WIDTH) + ((NBRICKS_PER_ROW - 1) * BRICK_SEP));
	int x = NTURNS;
	int j = 0;
	GRect paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
	int y = HEIGHT - PADDLE_Y_OFFSET;
	GOval ball;
	GObject obj;
	GLabel turns = new GLabel("Number of turns left= " + getTurns());
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	int brickCount = NBRICKS_PER_ROW * NBRICK_ROWS;
	int score = 0;
	GLabel points = new GLabel("SCORE=" + score);

	public void run() {
		setup();
		addMouseListeners();
		play();
	}

	private void setup() {
		while (j < NBRICK_ROWS) {
			makeRow();
			j++;
		}
		makePaddleBall();
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5))
			vx = -vx;

	}

	private void makePaddleBall() {
		paddle.setFilled(true);
		paddle.setFillColor(Color.black);
		ball = new GOval(getWidth() / 2 - BALL_RADIUS, getHeight() / 2
				- BALL_RADIUS, BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}

	private void makeRow() {
		int i = 0;
		while (i < NBRICKS_PER_ROW) {
			GRect Brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
			add(Brick, (rem / 2) + (i * (BRICK_WIDTH + BRICK_SEP)), rem / 2
					+ BRICK_Y_OFFSET + (j * (BRICK_HEIGHT + BRICK_SEP)));
			Brick.setFilled(true);
			if (((int) (j / 2)) == 0)
				Brick.setFillColor(Color.red);
			else if (((int) (j / 2)) == 1)
				Brick.setFillColor(Color.orange);
			else if (((int) (j / 2)) == 2)
				Brick.setFillColor(Color.yellow);
			else if (((int) (j / 2)) == 3)
				Brick.setFillColor(Color.green);
			else if (((int) (j / 2)) == 4)
				Brick.setFillColor(Color.cyan);
			i++;
		}
	}

	public void mouseMoved(MouseEvent e) {
		add(paddle, e.getX(), HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
	}

	private void play() {
		while (ball.getX() > 0) {

			remove(turns);
			remove(points);
			points = new GLabel("Points:" + score);
			turns = new GLabel("Number of turns left= " + getTurns());
			add(turns, WIDTH - turns.getWidth(), BRICK_Y_OFFSET);
			add(points, 0, BRICK_Y_OFFSET);

			if (brickCount == 0) {
				gameWon();
				break;
			}
			if (getTurns() == 0) {
				gameOver();
				break;
			}
			while (brickCount != 0 && getTurns() != 0) {
				ball.move(vx, vy);
				collisionWall();
				checkForCollision();
				pause(50);
			}

		}
		if (ball.getX() <= 0) {
			vx = -vx;
			while (ball.getX() <= 0) {
				ball.move(vx, vy);
				collisionWall();
				pause(.1);
			}
			play();
		}

	}

	private void collisionWall() {
		if (ball.getY() > HEIGHT - (2 * BALL_RADIUS)) {
			x--;
			remove(ball);
			add(ball, getWidth() / 2 - BALL_RADIUS, getHeight() / 2);
			pause(1000);
			vx = rgen.nextDouble(1.0, 3.0);
			if (rgen.nextBoolean(0.5))
				vx = -vx;
			play();
		}
		if (ball.getY() < 0) {
			vy = -vy;
		}

		if (ball.getX() > WIDTH - (2 * BALL_RADIUS)) {
			vx = -vx;
		}
		if (ball.getX() < 0) {
			vx = -vx;
		}
	}

	private void checkForCollision() {
		obj = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		if (getCollidingObject() != null) {
			remove(getCollidingObject());

			brickCount--;
			score = score + 10;
			if (vy > 0)
				vy = vy + 0.1;
			if (vy < 0)
				vy = vy - 0.1;
			if (vx > 0)
				vx = vx + 0.1;
			if (vx < 0)
				vx = vx - 0.1;
			vy = -vy;
			play();
		}
		obj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2
				* BALL_RADIUS);
		if (getCollidingObject() != null) {
			remove(getCollidingObject());

			brickCount--;
			score = score + 10;
			if (vy > 0)
				vy = vy + 0.1;
			if (vy < 0)
				vy = vy - 0.1;
			if (vx > 0)
				vx = vx + 0.1;
			if (vx < 0)
				vx = vx - 0.1;
			vy = -vy;
			play();
		}
		obj = getElementAt(ball.getX(), ball.getY());
		if (getCollidingObjectm() != null) {
			remove(getCollidingObjectm());
			brickCount--;
			score = score + 10;
			if (vy > 0)
				vy = vy + 0.1;
			if (vy < 0)
				vy = vy - 0.1;
			if (vx > 0)
				vx = vx + 0.1;
			if (vx < 0)
				vx = vx - 0.1;
			vy = -vy;
			play();
		}
		obj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		if (getCollidingObjectm() != null) {
			remove(getCollidingObjectm());
			brickCount--;
			score = score + 10;
			if (vy > 0)
				vy = vy + 0.1;
			if (vy < 0)
				vy = vy - 0.1;
			if (vx > 0)
				vx = vx + 0.1;
			if (vx < 0)
				vx = vx - 0.1;
			vy = -vy;
			play();
		}

	}

	private GObject getCollidingObject() {
		if (obj == paddle) {
			vy = -vy;
			bounceClip.play();
			play();
		}
		if (obj == turns || obj == points) {
		} else {
			return (obj);
		}
		return (null);
	}

	private GObject getCollidingObjectm() {
		if (obj == paddle) {
			x--;
			remove(ball);
			add(ball, getWidth() / 2 - BALL_RADIUS, getHeight() / 2);
			pause(1000);
			vx = rgen.nextDouble(1.0, 3.0);
			if (rgen.nextBoolean(0.5))
				vx = -vx;
			play();
		}
		if (obj == turns || obj == points) {
		} else {
			return (obj);
		}
		return (null);
	}

	private int getTurns() {
		return x;
	}

	private void gameOver() {
		GLabel over = new GLabel("GAME OVER");
		over.setFont("SansSerif-60");
		add(over, (WIDTH - over.getWidth()) / 2,
				(HEIGHT - over.getHeight()) / 2);
	}

	private void gameWon() {
		GLabel over = new GLabel("YOU WIN!");
		over.setFont("SansSerif-60");
		add(over, (WIDTH - over.getWidth()) / 2,
				(HEIGHT - over.getHeight()) / 2);
	}
}
