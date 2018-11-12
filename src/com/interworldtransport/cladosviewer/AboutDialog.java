/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.AboutDialog-------------------------------------------
<p>
Interworld Transport grants you ("Licensee") a license to this software
under the terms of the GNU General Public License.<br>
A full copy of the license can be found bundled with this package or code file.
<p>
If the license file has become separated from the package, code file, or binary
executable, the Licensee is still expected to read about the license at the
following URL before accepting this material.
<blockquote><code>http://www.opensource.org/gpl-license.html</code></blockquote>
<p>
Use of this code or executable objects derived from it by the Licensee states their
willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
---com.interworldtransport.cladosviewer.AboutDialog-------------------------------------------
*/

package com.interworldtransport.cladosviewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**  com.interworldtransport.cladosviewer.AboutDialog
 * The AboutDialog is an information window that is called from the
 * "Help|About" menu on the main Atmosphere application window.
 * It provides information about the application, credit to contributors
 * and the GPL license.
 * @version 0.80, $Date: 2005/07/31 05:00:25 $
 * @author Dr Alfred W Differ
 */
public class AboutDialog extends JDialog implements ActionListener
{
	private 	MonadViewer		TheGUI;
	private 	JButton 		closeButton;

/**
 * The constructor sets up the about dialog box and displays it.
 */
    public AboutDialog(MonadViewer mainWindow, String pContent)
    {

	super(mainWindow, "About Monad Viewer Utility", true); //Use parent's constructor
	TheGUI=mainWindow;
	
	JPanel mainPane = new JPanel(new BorderLayout());
	mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(mainPane);
	
	//Create Logo panel
	
	JPanel topspot=new JPanel();
	String logoFile=TheGUI.IniProps.getProperty("MonadViewer.Desktop.HeaderImage");
	ImageIcon temp = new ImageIcon(logoFile);
	topspot.add(new JLabel(temp));
	mainPane.add(topspot, "North");

	// Create content text area

	JTextArea contentArea = new JTextArea(pContent);
	contentArea.setBackground(Color.lightGray);
	contentArea.setBorder(new EmptyBorder(2, 2, 2, 2));
	contentArea.setLineWrap(true);
	contentArea.setWrapStyleWord(true);
	contentArea.setEditable(false);
	mainPane.add(new JScrollPane(contentArea), "Center");

	// Create close button panel

	JPanel closeButtonPane = new JPanel(new FlowLayout());
	closeButtonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	mainPane.add(closeButtonPane, "South");

	// Create close button

	closeButton = new JButton("Close");
	closeButton.addActionListener(this);
	closeButtonPane.add(closeButton);

	// Set the size of the window

	setSize(400, 700);

	// Center the window on the parent window.

	Point parentLocation = mainWindow.getLocation();
	int Xloc = (int) parentLocation.getX() + ((mainWindow.getWidth() - 300) / 2);
	int Yloc = (int) parentLocation.getY(); //+ ((mainWindow.getHeight() - 400) / 2);
	setLocation(Xloc, Yloc);

	// Display window
	setVisible(true);
    }

	// Implementing ActionListener method

    public void actionPerformed(ActionEvent event)
    {
		dispose();
    }
}//End of AboutDialog class
