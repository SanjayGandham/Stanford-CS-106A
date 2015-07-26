/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.io.*;

public class Hangman extends ConsoleProgram {
	word x=new word();
	String word=x.newword();
	private int turns = 8;
	String dash;
	String guess;
	int num = word.length();
	char g;
	String w = word;
	private HangmanCanvas canvas;
	

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		canvas.reset();
		setup();
		while (num != 0 && turns != 0) {
			guess();
		}
		println("The word is:" + w);
	}

	private void setup() {
		println("Welcome to Hangman!");
		dash = "-";
		while (dash.length() < word.length()) {
			String c = dash + "-";
			dash = c;
		}
		canvas.displayWord(dash);
		println("The word now looks like this:" + dash);
		println("You have " + turns + " guesses left.");
		guess = readLine("Your guess:");
	}

	private void reveal(char a, int c) {
		String temp = dash.substring(0, c) + a
				+ dash.substring(c + 1, dash.length());
		dash = temp;
		canvas.displayWord(dash);
		println("The word now looks like this:" + dash);
		println("You have " + turns + " guesses left.");
	}

	private void guess() {
		int count = 0;
		checkformat();
		g = guess.charAt(0);
		g = Character.toUpperCase(g);
		for (int i = 0; i < word.length(); i++) {
			if (g == word.charAt(i)) {
				correctGuess(i);

				break;
			} else {
				count++;
			}
		}
		if (num == 0) {
			println("You guessed it correctly.You win!");
		}
		if (count == word.length()) {
			wrongGuess(g);
		}
	}

	private void checkformat() {
		while (guess.length() != 1) {
			println("ILLEGAL FORMAT.Type a single letter");
			String a = readLine("Your guess:");
			guess = a;
		}
	}

	private void correctGuess(int i) {
		while (true) {
			String tem = word.substring(0, i) + '1'
					+ word.substring(i + 1, word.length());
			reveal(word.charAt(i), i);
			word = tem;
			num--;
			if (num == 0) {
				break;
			}
			guess = readLine("Your guess:");
			break;
		}
	}

	private void wrongGuess(char letter) {

		while (true) {
			println("There are no " + g + "'s in the word.");
			turns--;
			canvas.noteIncorrectGuess(letter);
			if (turns == 0) {
				println("You lose");
				break;
			}
			println("The word now looks like this:" + dash);
			println("You have " + turns + " guesses left.");
			guess = readLine("Your guess:");
			break;
		}
	}
}
