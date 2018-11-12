/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.CreateDialog--------------------------------------
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
---com.interworldtransport.cladosviewer.CreateDialog--------------------------------------
*/

package com.interworldtransport.cladosviewer;
import com.interworldtransport.clados.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**  com.interworldtransport.cladosviewer.CreateDialog
 * The Create Dialog window is supposed to show a window that would allow a user
 * to add a new Monad to the stack in the Monad Viewer.
 * @version 0.80, $Date: 2005/08/25 06:36:13 $
 * @author Dr Alfred W Differ
 */
public class CreateDialog extends JDialog implements ActionListener
{
	private	MonadViewer	TheGUI;
	private	MonadPanel	mainPane;
	private	JButton		closeButton;
	private	JButton		saveButton;

/**
 * The constructor sets up the options dialog box and displays it.
 */
	public CreateDialog(MonadViewer mainWindow, String pContent)
	throws 		UtilitiesException, BadSignatureException
	{
		
		super(mainWindow, "Create Monad Panel", true);
		TheGUI=mainWindow;
		
		JPanel centerPanel=new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.mainPane = new MonadPanel(	TheGUI, 
										"Create", 
										"test", 
										"basic", 
										"basic",
										TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Sig"));
		mainPane.syncButton.setEnabled(false);
		JScrollPane tempPane=new JScrollPane(mainPane);
		mainPane.makeWritable();
		centerPanel.add(tempPane, "Center");
		setContentPane(centerPanel);
		
		// Create button panel
		
		JPanel ButtonPane = new JPanel(new FlowLayout());
		ButtonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		ButtonPane.add(saveButton);
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		ButtonPane.add(closeButton);
		
		centerPanel.add(ButtonPane, "South");
		
		// Set the size of the window
		this.pack();
		
		// Center the window on the parent window.
		Point parentLocation = mainWindow.getLocation();
		int Xloc = (int) parentLocation.getX() + ((mainWindow.getWidth() - this.getWidth()) / 2);
		int Yloc = (int) parentLocation.getY()+100;
		setLocation(Xloc, Yloc);
		
		// Display window
		setVisible(true);
	}

    public void actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	if (command.equals("Close"))
    	{
    		dispose();
    	}
    	if (command.equals("Save"))
    	{
    		try
    		{
    		Monad rep=new Monad(	mainPane.name.getText(),
    								mainPane.aname.getText(),
    								mainPane.frame.getText(),
    								mainPane.foot.getText(),
    								mainPane.sig.getText());
    		Nyad rep2=new Nyad("New", rep);
    		TheGUI.CenterAll.addSTab(rep2);
    		TheGUI.StatusLine.setStatusMsg("New nyad added to stack.\n");
    		}
    		catch (UtilitiesException e)
    		{
    			System.out.println("Could not create monad copy from Create|Save");
    		}
    		catch (BadSignatureException es)
    		{
    			System.out.println("Could not create monad copy from Create|Save");
    		}
    	}
    }
}
