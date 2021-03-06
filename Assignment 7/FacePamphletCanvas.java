/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;

import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements
		FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {

	}

	/**
	 * This method displays a message string near the bottom of the canvas.
	 * Every time this method is called, the previously displayed message (if
	 * any) is replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		GObject obj = getElementAt(getWidth() / 2, getHeight()
				- BOTTOM_MESSAGE_MARGIN);
		if(obj!=null)
		remove(obj);
		GLabel message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message, (getWidth() - message.getWidth()) / 2, getHeight()
				- BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the
	 * bottom of the screen) and then the given profile is displayed. The
	 * profile display includes the name of the user from the profile, the
	 * corresponding image (or an indication that an image does not exist), the
	 * status of the user, and a list of the user's friends in the social
	 * network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		GLabel name = new GLabel(profile.getName());
		name.setFont(PROFILE_NAME_FONT);
		name.setColor(Color.blue);
		add(name, LEFT_MARGIN, TOP_MARGIN + name.getHeight());
		x = name.getHeight();
		y = name.getY();
		image(profile);
		status(profile);
		GLabel friends = new GLabel("Friends:");
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, (getWidth() - friends.getWidth()) / 2, name.getY()
				+ IMAGE_MARGIN);
		friend(profile);
		z = friends.getX();
		a = friends.getY();
	}

	private void status(FacePamphletProfile profile) {
		if (profile.getStatus() == "") {
			GLabel stat = new GLabel("No current status");
			stat.setFont(PROFILE_STATUS_FONT);
			add(stat, LEFT_MARGIN, TOP_MARGIN + x + IMAGE_MARGIN + IMAGE_HEIGHT
					+ STATUS_MARGIN + stat.getHeight());
		} else {
			GLabel stat = new GLabel(profile.getName() + " is "
					+ profile.getStatus());
			stat.setFont(PROFILE_STATUS_FONT);
			add(stat, LEFT_MARGIN, TOP_MARGIN + x + IMAGE_MARGIN + IMAGE_HEIGHT
					+ STATUS_MARGIN + stat.getHeight());
		}
	}

	private void image(FacePamphletProfile profile) {
		if (profile.getImage() == null) {
			GRect img = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(img, LEFT_MARGIN, y + IMAGE_MARGIN);
			GLabel noimg = new GLabel("No Image");
			noimg.setFont(PROFILE_IMAGE_FONT);
			add(noimg, LEFT_MARGIN + IMAGE_WIDTH / 2 - noimg.getWidth() / 2,
					img.getY() + IMAGE_HEIGHT / 2 + noimg.getHeight() / 4);
		} else {
			GImage img = profile.getImage();
			img.setBounds(LEFT_MARGIN,y+IMAGE_MARGIN,
					IMAGE_WIDTH, IMAGE_HEIGHT);
			add(img);
		}
	}

	private void friend(FacePamphletProfile profile) {
		Iterator<String> itr = profile.getFriends();
		int i = 1;
		while (itr.hasNext()) {
			GLabel frnd = new GLabel(itr.next());
			frnd.setFont(PROFILE_FRIEND_FONT);
			add(frnd, z, a + i * frnd.getHeight());
			i++;
		}
	}
	

	double x;
	double y;
	double z;
	double a;
}
