package org.interworldtransport.cladosviewer;

import javax.swing.JOptionPane;
/**
 * This is just a simple object that spins up a JOptionPane to display an error message.
 * <br>
 * There are no constructors, so instance members or methods, and only the one class method 
 * for constructing the message dialog.
 */
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

	/**
	 * This empty statement for the constructor is present only for halting the creation of actual
	 * instances of this class. Instances aren't needed for anything other than presenting JOptionPane objects
	 */
	protected ErrorDialog() {}
}