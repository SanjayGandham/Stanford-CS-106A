/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.awt.*;
import java.util.Arrays;

import acm.graphics.*;
import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	public void run() {
		setupPlayers();
		initDisplay();
		playGame();
	}

	/**
	 * Prompts the user for information about the number of players, then sets
	 * up the players array and number of players.
	 */
	private void setupPlayers() {
		nPlayers = chooseNumberOfPlayers();

		/* Set up the players array by reading names for each player. */
		playerNames = new String[nPlayers];
		for (int i = 0; i < nPlayers; i++) {
			/*
			 * IODialog is a class that allows us to prompt the user for
			 * information as a series of dialog boxes. We will use this here to
			 * read player names.
			 */
			IODialog dialog = getDialog();
			playerNames[i] = dialog
					.readLine("Enter name for player " + (i + 1));

		}
	}

	/**
	 * Prompts the user for a number of players in this game, reprompting until
	 * the user enters a valid number.
	 * 
	 * @return The number of players in this game.
	 */
	private int chooseNumberOfPlayers() {
		/* See setupPlayers() for more details on how IODialog works. */
		IODialog dialog = getDialog();

		while (true) {
			/* Prompt the user for a number of players. */
			int result = dialog.readInt("Enter number of players");

			/* If the result is valid, return it. */
			if (result > 0 && result <= MAX_PLAYERS)
				return result;

			dialog.println("Please enter a valid number of players.");
		}
	}

	/**
	 * Sets up the YahtzeeDisplay associated with this game.
	 */
	private void initDisplay() {
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
	}

	/**
	 * Actually plays a game of Yahtzee. This is where you should begin writing
	 * your implementation.
	 */
	private void playGame() {
		int[] upscore = new int[nPlayers];
		int[] dscore = new int[nPlayers];
		check = new int[nPlayers * 17];
		x = 0;
		while (turns != 13 * nPlayers) {
			if (x > nPlayers - 1) {
				x = 0;
			}
			notify = playerNames[x]
					+ "'s turn! Click \"Roll Dice\" button to roll dice. ";
			add(label(0), 35, APPLICATION_HEIGHT - 45);
			display.waitForPlayerToClickRoll(x);
			display.displayDice(dice());
			for (int i = 0; i < 2; i++) {
				add(label(1), 35, APPLICATION_HEIGHT - 45);
				display.waitForPlayerToSelectDice();
				if (selected()) {
					display.displayDice(rollSelect(dice));
				}
			}
			add(label(2), 35, APPLICATION_HEIGHT - 45);
			int category = display.waitForPlayerToSelectCategory();

			while (true) {
				if (!isClicked(category, x)) {
					boolean p = applicable(dice, category);
					if (!p)
						display.updateScorecard(category, x, 0);
					if (p) {
						display.updateScorecard(category, x, score(category));
						if (category <= 6) {
							upscore[x] = score(category) + upscore[x];
						} else {
							dscore[x] = score(category) + dscore[x];
						}

					}
					break;
				} else {
					add(label(3), 35, APPLICATION_HEIGHT - 45);
					category = display.waitForPlayerToSelectCategory();
				}
			}
			turns++;
			display.updateScorecard(TOTAL, x, upscore[x] + dscore[x]);
			x++;
		}
		x = 0;
		displayResults(upscore, dscore);
	}

	private int[] dice() {
		for (int i = 0; i < N_DICE; i++) {
			int x = rgen.nextInt(1, 6);
			dice[i] = x;
		}
		return dice;
	}

	public int[] rollSelect(int[] x) {
		for (int i = 0; i < N_DICE; i++) {
			if (display.isDieSelected(i)) {
				x[i] = rgen.nextInt(1, 6);
			}
		}
		return x;
	}

	public boolean selected() {
		for (int i = 0; i < N_DICE; i++) {
			if (display.isDieSelected(i)) {
				return true;
			}
		}
		return false;
	}

	public int score(int x) {
		switch (x) {
		case 0: {
			int one = 0;
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == 1) {
					one++;
				}

			}
			return one;
		}
		case 1: {
			int two = 0;
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == 2) {
					two++;
				}

			}
			return 2 * two;
		}
		case 2: {
			int three = 0;
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == 3) {
					three++;
				}

			}
			return 3 * three;
		}
		case 3: {
			int four = 0;
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == 4) {
					four++;
				}

			}
			return 4 * four;
		}
		case 4: {
			int five = 0;
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == 5) {
					five++;
				}

			}
			return 5 * five;
		}
		case 5: {
			int six = 0;
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == 6) {
					six++;
				}

			}
			return 6 * six;
		}
		case 8: {
			int temp = 0;
			for (int i = 0; i < N_DICE; i++) {
				int count = 0;
				temp = dice[i];
				for (int j = 0; j < N_DICE; j++) {
					if (temp == dice[j])
						count++;
				}
				if (count >= 3)
					break;
			}
			return temp * 3;
		}
		case 9: {
			int temp = 0;
			for (int i = 0; i < N_DICE; i++) {
				int count = 0;
				temp = dice[i];
				for (int j = 0; j < N_DICE; j++) {
					if (temp == dice[j])
						count++;
				}
				if (count == 4)
					break;
			}
			return temp * 4;
		}
		case 10:
			return 25;
		case 11:
			return 30;
		case 12:
			return 40;
		case 13:
			return 50;
		case 14: {
			int sum = 0;
			for (int i = 0; i < N_DICE; i++) {
				sum = sum + dice[i];
			}
			return sum;
		}
		}
		return 0;
	}

	private GLabel label(int a) {
		GObject obj;
		switch (a) {
		case 0:
			obj = getElementAt(35, APPLICATION_HEIGHT - 45);
			if (obj != null)
				remove(obj);
			not = new GLabel(notify);
			not.setColor(Color.WHITE);
			return not;
		case 1:
			obj = getElementAt(35, APPLICATION_HEIGHT - 45);
			if (obj != null)
				remove(obj);
			not = new GLabel(
					"Select the dice you wish  to re-roll and click \"Roll Again\".");
			not.setColor(Color.WHITE);
			return not;
		case 2:
			obj = getElementAt(35, APPLICATION_HEIGHT - 45);
			if (obj != null)
				remove(obj);
			not = new GLabel("Select a category for this roll.");
			not.setColor(Color.WHITE);
			return not;
		case 3:
			obj = getElementAt(35, APPLICATION_HEIGHT - 45);
			if (obj != null)
				remove(obj);
			not = new GLabel(
					"Category already selected.Select another category");
			not.setColor(Color.WHITE);
			return not;
		}
		return null;
	}

	private int maxScore(int[] a, int[] b) {
		int val = 0;
		int cmax = 0;
		cmax = a[0] + b[0];
		for (int i = 1; i < nPlayers; i++) {
			int temp = a[i] + b[i];
			if (cmax < temp) {
				val = i;
				cmax = temp;
			}
		}
		return val;
	}

	private void displayResults(int[] upscore, int[] dscore) {
		while (x != nPlayers) {
			display.updateScorecard(UPPER_SCORE, x, upscore[x]);
			if (upscore[x] >= 63) {
				display.updateScorecard(UPPER_BONUS, x, 35);
				upscore[x] += 35;
			} else {
				display.updateScorecard(UPPER_BONUS, x, 0);
			}
			display.updateScorecard(LOWER_SCORE, x, dscore[x]);
			x++;
		}
		GObject obj;
		obj = getElementAt(35, APPLICATION_HEIGHT - 45);
		remove(obj);
		not = new GLabel("Congratulations, "
				+ playerNames[maxScore(upscore, dscore)]
				+ ", you're the winner with a total score of "
				+ (upscore[maxScore(upscore, dscore)] + dscore[maxScore(
						upscore, dscore)]) + ".");
		not.setColor(Color.WHITE);
		add(not, 35, APPLICATION_HEIGHT - 45);
	}

	private boolean applicable(int[] dice, int category) {
		switch (category) {
		case 8: {
			for (int i = 0; i < N_DICE; i++) {
				int count = 0;
				for (int j = 0; j < N_DICE; j++) {
					if (dice[i] == dice[j])
						count++;
				}
				if (count >= 3)
					return true;
			}
			return false;
		}
		case 9: {
			for (int i = 0; i < N_DICE; i++) {
				int count = 0;
				for (int j = 0; j < N_DICE; j++) {
					if (dice[i] == dice[j])
						count++;
				}
				if (count >= 4)
					return true;
			}
			return false;
		}
		case 10: {
			int y = 0;
			int lol = 0;
			for (int i = 0; i < N_DICE; i++) {
				int count = 0;
				for (int j = 0; j < N_DICE; j++) {
					if (dice[i] == dice[j])
						count++;
					if (count == 3) {
						y++;
						lol = dice[i];
						break;
					}
				}
				if (y == 1)
					break;
			}
			for (int i = 0; i < N_DICE; i++) {
				int count = 0;
				for (int j = 0; j < N_DICE; j++) {
					if (dice[i] == dice[j] && dice[i] != lol)
						count++;
					if (count == 2) {
						y++;
					}

					if (y == 2)
						return true;
				}
			}
			return false;
		}
		case 11: {
			int[] arr = new int[4];

			for (int i = 0; i < 4; i++) {
				arr[i] = i;
			}
			for (int i = 0; i < N_DICE; i++) {
				int[] temp = new int[4];
				for (int j = 0; j < N_DICE; j++) {
					if (dice[j] == dice[i])
						temp[0] = 0;
					if (dice[j] == dice[i] + 1)
						temp[1] = 1;
					if (dice[j] == dice[i] + 2)
						temp[2] = 2;
					if (dice[j] == dice[i] + 3)
						temp[3] = 3;
				}
				if (Arrays.equals(arr, temp))
					return true;
			}
			return false;
		}
		case 12: {
			int[] arr = new int[5];

			for (int i = 0; i < 5; i++) {
				arr[i] = i;
			}
			for (int i = 0; i < N_DICE; i++) {
				int[] temp = new int[5];
				for (int j = 0; j < N_DICE; j++) {
					if (dice[j] == dice[i])
						temp[0] = 0;
					if (dice[j] == dice[i] + 1)
						temp[1] = 1;
					if (dice[j] == dice[i] + 2)
						temp[2] = 2;
					if (dice[j] == dice[i] + 3)
						temp[3] = 3;
					if (dice[j] == dice[i] + 4)
						temp[4] = 4;
				}
				if (Arrays.equals(arr, temp))
					return true;
			}
			return false;
		}
		case 13: {
			int y = 0;
			for (int i = 0; i < N_DICE; i++) {
				if (dice[0] == dice[i]) {
					y++;
				}
			}
			if (y == N_DICE)
				return true;
			return false;
		}
		default:
			return true;
		}
	}

	private boolean isClicked(int cat, int ex) {
		if (check[cat + 16 * ex] == 1) {
			return true;
		} else {
			check[cat + 16 * ex] = 1;
		}
		return false;
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	int[] dice = new int[N_DICE];
	int[] check;

	int turns = 0;
	int x;
	String notify;
	GLabel not;
}
