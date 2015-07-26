/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in
	 * the application, and taking care of any other initialization that needs
	 * to be performed.
	 */
	public void init() {
		JLabel name = new JLabel("Name");
		add(name, NORTH);
		jname = new JTextField(TEXT_FIELD_SIZE);
		add(jname, NORTH);
		Add = new JButton("Add");
		add(Add, NORTH);
		delete = new JButton("Delete");
		add(delete, NORTH);
		lookup = new JButton("Lookup");
		add(lookup, NORTH);
		jstatus = new JTextField(TEXT_FIELD_SIZE);
		add(jstatus, WEST);
		status = new JButton("Change Status");
		add(status, WEST);
		JLabel space = new JLabel(EMPTY_LABEL_TEXT);
		add(space, WEST);
		jpicture = new JTextField(TEXT_FIELD_SIZE);
		add(jpicture, WEST);
		picture = new JButton("Change Picture");
		add(picture, WEST);
		space = new JLabel(EMPTY_LABEL_TEXT);
		add(space, WEST);
		jfriend = new JTextField(TEXT_FIELD_SIZE);
		add(jfriend, WEST);
		friend = new JButton("Add Friend");
		add(friend, WEST);
		space = new JLabel(EMPTY_LABEL_TEXT);
		add(space, WEST);
		addActionListeners();
		jname.addActionListener(this);
		jpicture.addActionListener(this);
		jstatus.addActionListener(this);
		jfriend.addActionListener(this);
		add(canvas);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == status || e.getSource() == jstatus) {
			if (prof != null) {
				prof.setStatus(jstatus.getText());
				canvas.displayProfile(prof);
				canvas.showMessage("Status updated to: " + jstatus.getText());
			} else {
				canvas.showMessage("Please select a profile to change status");
			}

		}
		if (e.getSource() == friend || e.getSource() == jfriend) {
			FacePamphletProfile temp;
			if (prof != null) {
				temp = data.getProfile(jfriend.getText());
				if (temp != null) {
					if (temp.addFriend(prof.getName())) {
						prof.addFriend(temp.getName());
						canvas.displayProfile(prof);
						canvas.showMessage(temp.getName()
								+ " added as a friend");
					} else {
						canvas.showMessage(prof.getName()+" already has "+temp.getName()+ " as a friend");
					}
				} else {
					canvas.showMessage(jfriend.getText()+ " does not exist");
				}
			} else {
				canvas.showMessage("Please select a profile to add friend");
			}
		}
		if (e.getSource() == picture || e.getSource() == jpicture) {
			if (prof != null) {
				try {
					image = new GImage(jpicture.getText());
					prof.setImage(image);
					canvas.displayProfile(prof);
					canvas.showMessage("Picture updated");

				} catch (ErrorException ex) {
					canvas.showMessage("Unable to open image file: "
							+ jpicture.getText());
				}
			} else {
				canvas.showMessage("Please select a profile to change picture");
			}

		}
		if (e.getSource() == Add) {
			prof = data.getProfile(jname.getText());
			if (prof == null) {
				prof = new FacePamphletProfile(jname.getText());
				data.addProfile(prof);
				canvas.displayProfile(prof);
				canvas.showMessage("New profile created.");
			} else {
				canvas.displayProfile(prof);
				canvas.showMessage("A profile with the name " + prof.getName()
						+ " already exists.");
			}
		}
		if (e.getSource() == delete) {
			prof = data.getProfile(jname.getText());
			if (prof != null) {
				data.deleteProfile(prof.getName());
				prof = null;
				canvas.removeAll();
				canvas.showMessage("Profile of " + jname.getText() + " deleted.");
			} else {
				prof = null;
				canvas.removeAll();
				canvas.showMessage("A profile with the name " + jname.getText()
						+ " does not exist.");
			}
		}
		if (e.getSource() == lookup) {
			prof = data.getProfile(jname.getText());
			if (prof != null) {
				canvas.displayProfile(prof);
				canvas.showMessage("Displaying " + prof.getName());
			} else {
				canvas.removeAll();
				canvas.showMessage("A profile with the name " + jname.getText()
						+ " does not exist.");
			}
		}
	}

	GImage image;
	JButton status;
	JButton friend;
	JButton picture;
	JTextField jfriend;
	JTextField jstatus;
	JTextField jpicture;
	JTextField jname;
	JButton Add;
	JButton delete;
	JButton lookup;
	FacePamphletProfile prof;
	FacePamphletDatabase data = new FacePamphletDatabase();
	private FacePamphletCanvas canvas = new FacePamphletCanvas();
}
