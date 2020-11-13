package org.interworldtransport.cladosviewer;

import javax.swing.JOptionPane;

public class ErrorDialog {
	/**
	 * Shows an error message. That's it.
	 * No constructor because it's not really an object to instantiate.
	 * It's just a free floating method.
	 * 
	 * @param message  The Message to display in the center pane
	 * @param title The Title of the dialog
	 */
	public final static void show(String message, String title) {
		JOptionPane.showMessageDialog(null, message, "Error | " + title, JOptionPane.ERROR_MESSAGE);
	}

}