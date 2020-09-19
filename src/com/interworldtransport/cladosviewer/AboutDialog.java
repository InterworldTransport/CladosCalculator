/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.AboutDialog<br>
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
 * ---com.interworldtransport.cladosviewer.AboutDialog<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**  
 * The AboutDialog is an information window that is called from the
 * "Help|About" menu on the main Atmosphere application window.
 * It provides information about the application, credit to contributors
 * and the GPL license.
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public final class AboutDialog extends JDialog implements ActionListener
{
	private 	CladosCalculator	TheGUI;
	private 	JButton 			closeButton;

/**
 * The constructor sets up the about dialog box and displays it.
 */
    public AboutDialog(CladosCalculator mainWindow)
    {
		super(mainWindow, "About Clados Calculator Utility", true); //Use parent's constructor
		TheGUI=mainWindow;
		
		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPane);
		
		//Create Logo panel
		
		JPanel topspot=new JPanel();
		String logoFile=TheGUI.IniProps.getProperty("Desktop.Image.Header");
		ImageIcon temp = new ImageIcon(logoFile);
		topspot.add(new JLabel(temp));
		mainPane.add(topspot, "North");
	
		// Create content text area
		constructContent();
		JTextArea contentArea = new JTextArea(constructContent());
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
	
		setSize(500, 700);
	
		// Center the window on the parent window.
	
		Point parentLocation = mainWindow.getLocation();
		int Xloc = (int) parentLocation.getX() + ((mainWindow.getWidth() - 300) / 2);
		int Yloc = (int) parentLocation.getY(); //+ ((mainWindow.getHeight() - 400) / 2);
		setLocation(Xloc, Yloc);
	
		// Display window
		setVisible(true);
    }
    
    private String constructContent()
    {
    	String tempVersion = TheGUI.IniProps.getProperty("Desktop.Version");
    	String tempUserName = TheGUI.IniProps.getProperty("User.Name");
    	String tempInstitution = TheGUI.IniProps.getProperty("User.Institution");

    	StringBuffer content = new StringBuffer();

    	content.append("Clados Calculator ");
    	content.append(tempVersion);
    	content.append("\n\n");

    	content.append("Copyright 2018 Alfred Differ");
    	content.append("\n\n");

    	content.append("Web Site: https://github.com/InterworldTransport/CladosViewer\n\n");

    	content.append("Developers:\n");
    	content.append("  Dr. Alfred Differ - Physics, Java\n");
    	content.append("  Your name could be here! \n\n");

    	content.append("Licensed to {");
    	content.append(tempUserName);
    	content.append("}\n");
    	content.append(tempInstitution);
    	content.append("\n\n");
    	
    	content.append("This program is distributed in the hope that it will be useful, ");
    	content.append("it under the terms of the GNU Affero General Public License as ");
    	content.append("published by the Free Software Foundation, either version 3 of the ");
    	content.append("License, or (at your option) any later version. \n\n");

    	content.append("This program is distributed in the hope that it will be useful, ");
    	content.append("but WITHOUT ANY WARRANTY; without even the implied warranty of ");
    	content.append("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the ");
    	content.append("GNU Affero General Public License for more details.\n\n");
    	
    	content.append("Use of this code or executable objects derived from it by the Licensee ");
    	content.append("states their willingness to accept the terms of the license.\n\n");
    	
    	content.append("You should have received a copy of the GNU Affero General Public License ");
    	content.append("along with this program.  If not, see <https://www.gnu.org/licenses/>.\n");
    	
    	return content.toString();
    }

    public void actionPerformed(ActionEvent event)
    {
		dispose();	// Any action is enough to close the window.
    }
}