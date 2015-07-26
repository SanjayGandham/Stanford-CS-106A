/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */
import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants,
		ComponentListener {
	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
	
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		removeAll();
		a = 0;
		ent = new NameSurferEntry[4];
		name = null;
		update();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note
	 * that this method does not actually draw the graph, but simply stores the
	 * entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		if (a < 4) {
			ent[a] = entry;
			name = entry.getName();
			
			update();
			a++;
		} else {
			removeAll();
			GLabel err = new GLabel(
					"You have exceded the number of names per graph. Please click \"Clear\" button an continue");
			add(err, (getWidth() - err.getWidth()) / 2,
					(getHeight() - err.getHeight()) / 2);
		}
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of
	 * entries. Your application must call update after calling either clear or
	 * addEntry; update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		fillLines();
		if (name != null) {
			for (int i = 0; i <= a; i++) {
				addData(i);
			}
		}
	}

	private void fillLines() {
		double col = getWidth() / NDECADES;
		GLine hor1 = new GLine(0, 40, getWidth(), 40);
		GLine hor2 = new GLine(0, getHeight() - 40, getWidth(),
				getHeight() - 40);
		add(hor2);
		add(hor1);

		for (int i = 0; i < NDECADES; i++) {
			GLine ver = new GLine(i * col, 0, i * col, getHeight());
			GLabel dec = new GLabel((START_DECADE + i * 10) + "");
			add(dec, i * col, getHeight() - 40 + dec.getHeight());
			add(ver);
		}
	}

	private void addData(int i) {
		double col = getWidth() / NDECADES;
		GLabel[] names = new GLabel[NDECADES];
		double y = ((double) getHeight() - 80.0) / 1000.0;
		for (int j = 0; j < NDECADES; j++) {
			if (ent[i].getRank(j) == 0) {
				names[i] = new GLabel(ent[i].getName() + "*", j * col,
						getHeight() - 40);
				coy[j] = getHeight() - 40;

			} else {
				names[i] = new GLabel(ent[i].getName() + " "
						+ ent[i].getRank(j), j * col, 40.0 + y
						* ent[i].getRank(j));
				coy[j] = 40.0 + y * ent[i].getRank(j);

			}
			setColor(i, names[i]);
			add(names[i]);
		}
		for (int j = 0; j < NDECADES - 1; j++) {
			GLine connect = new GLine(j * col, coy[j], (j + 1) * col,
					coy[j + 1]);
			setColor(i, connect);
			add(connect);
		}

	}

	NameSurferEntry[] ent = new NameSurferEntry[4];
	String name = null;
	int a = 0;
	double[] coy = new double[NDECADES * 4];

	private void setColor(int i, GObject obj) {
		if (i%4 == 0)
			obj.setColor(Color.blue);
		if (i%4 == 1)
			obj.setColor(Color.RED);
		if (i%4 == 2)
			obj.setColor(Color.MAGENTA);
		if (i%4 == 3)
			obj.setColor(Color.black);
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		if (a < 4)
			update();
		else {
			addEntry(null);
		}
	}

	public void componentShown(ComponentEvent e) {
	}
}