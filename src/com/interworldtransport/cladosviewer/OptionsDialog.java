/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.OptionsDialog<br>
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
 * ---com.interworldtransport.cladosviewer.OptionsDialog<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer;

import java.awt.*;
import java.awt.event.*;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.*;

/**  com.interworldtransport.cladosviewer.OptionsDialog
 * The Optons Dialog window is supposed to show a window that would allow a user
 * to adjust the configuration file from within the Viewer.
 * @version 0.85,
 * @author Dr Alfred W Differ
 */
public class OptionsDialog extends JDialog implements ActionListener
{
	private CladosCalculator	_GUI;
	private JButton 			closeButton;  

/**
 * The constructor sets up the options dialog box and displays it.
 * @param mainWindow
 *  CladosCalculator
 * This is just a reference to the owning application so error messages can be 
 * reported out to the GUI.
 */
    public OptionsDialog(CladosCalculator mainWindow)
    {
		super(mainWindow, "Options Panel for Clados Calculator", true); 
		_GUI=mainWindow;
	
		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPane);
	
		// Create content text area
	
		JTextArea contentArea = new JTextArea();
		contentArea.setBackground(Color.WHITE);
		contentArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentArea.setLineWrap(true);
		contentArea.setWrapStyleWord(true);
		contentArea.setEditable(true);
		contentArea.setText(constructContent());
		mainPane.add(new JScrollPane(contentArea), "Center");
	
		// Create close button panel
	
		JPanel closeButtonPane = new JPanel(new FlowLayout());
		closeButtonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.add(closeButtonPane, "South");
	
		// Create close button
	
		closeButton = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Close")));
		closeButton.setActionCommand("close");
		closeButton.setToolTipText("Close the dialog. No further changes.");
		closeButton.setPreferredSize(new Dimension(30,30));
		closeButton.setBorder(BorderFactory.createEtchedBorder(0));
		closeButton.addActionListener(this);
		closeButtonPane.add(closeButton);
	
		// Set the size of the window
	
		setSize(500, 800);
	
		// Center the window on the parent window.
	
		Point parentLocation = mainWindow.getLocation();
		int Xloc = (int) parentLocation.getX() + ((mainWindow.getWidth() - 300) / 2);
		int Yloc = (int) parentLocation.getY(); //+ ((mainWindow.getHeight() - 400) / 2);
		setLocation(Xloc, Yloc);
	
		// Display window
		setVisible(true);
    }

    public void actionPerformed(ActionEvent event)
    {
		dispose();
    }
    
    private String constructContent()
    {
    	StringBuffer contentBuffer=new StringBuffer();
    	Set<Object> testSet = _GUI.IniProps.keySet();

    	
    	for ( Object key : testSet)
    	{
    		contentBuffer.append((String)key);
    		contentBuffer.append(" | ");
    		contentBuffer.append(_GUI.IniProps.get(key));
    		contentBuffer.append("\n");
    	}
    	return contentBuffer.toString();
    }
}
