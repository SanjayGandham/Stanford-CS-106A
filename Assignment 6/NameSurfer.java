/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the top of the window.
	 */
	public void init() {
		JLabel name = new JLabel("Name:");
		add(name, NORTH);
		jname = new JTextField(20);
		add(jname, NORTH);
		grap = new JButton("Graph");
		add(grap, NORTH);
		clear = new JButton("Clear");
		add(clear, NORTH);
		addActionListeners();
		jname.addActionListener(this);
		graph = new NameSurferGraph();
		add(graph);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so
	 * you will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jname)
			graph.addEntry(lol.findEntry(jname.getText()));
		if (e.getSource() == grap)
			graph.addEntry(lol.findEntry(jname.getText()));
		if (e.getSource() == clear)
			graph.clear();
		
			
	}

	JTextField jname;
	JButton grap;
	JButton clear;
	NameSurferEntry entry;
	NameSurferDataBase lol=new NameSurferDataBase("NAMES_DATA_FILE") ;
	private NameSurferGraph graph;
}
