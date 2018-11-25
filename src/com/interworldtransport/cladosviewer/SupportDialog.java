/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.SupportDialog<br>
 * -------------------------------------------------------------------- <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.<p>
 * 
 * Use of this code or executable objects derived from it by the Licensee 
 * states their willingness to accept the terms of the license. <p> 
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.<p> 
 * 
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.SupportDialog<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/** com.interworldtransport.cladosviewer.SupportDialog
 * The SupportDialog is an information window that is called from the
 * "Help|Support" menu on the main Atmosphere application window.
 * It provides information about support for the application.
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class SupportDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = -8805846547511967367L;
	private JButton closeButton;  // The close button

/**
 * The constructor sets up the about dialog box and displays it.
 */
    public SupportDialog(MonadViewer mainWindow, String pContent)
    {

	super(mainWindow, "Support for Monad Viewer Utility", true); //Use parent's constructor

	JPanel mainPane = new JPanel(new BorderLayout());
	mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(mainPane);

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

	setSize(300, 400);

	// Center the window on the parent window.

	Point parentLocation = mainWindow.getLocation();
	int Xloc = (int) parentLocation.getX() + ((mainWindow.getWidth() - 300) / 2);
	int Yloc = (int) parentLocation.getY();// + ((mainWindow.getHeight() - 400) / 2);
	setLocation(Xloc, Yloc);

	// Display window

	setVisible(true);
    }

	// Implementing ActionListener method

    public void actionPerformed(ActionEvent event)
    {
		dispose();
    }
}//End of SupportDialog class
