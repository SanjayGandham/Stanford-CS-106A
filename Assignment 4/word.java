import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import acm.util.RandomGenerator;
import acm.util.*;

public class word {
	int lexicon_size = 121806;
	private RandomGenerator rgen = RandomGenerator.getInstance();

	public String newword() {
		try {
			BufferedReader rd = new BufferedReader(new FileReader(
					"HangmanLexicon.txt"));
			String[] list = new String[lexicon_size];
				for (int i = 1; i < lexicon_size; i++) {
					String line = rd.readLine();
					list[i] = line;
				}
				String w = list[(int) (rgen.nextDouble() * lexicon_size)];
				rd.close();
				return w;
			}catch (IOException ex) {
			HangmanLexicon h = new HangmanLexicon();
			String word = h
					.getWord((int) (rgen.nextDouble() * h.getWordCount()));

			return word;
		}
	}
}
