/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.CreateDialog<br>
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
 * ---com.interworldtransport.cladosviewer.CreateDialog<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer;
import com.interworldtransport.cladosF.RealF;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**  com.interworldtransport.cladosviewer.CreateDialog
 * The Create Dialog window is supposed to show a window that would allow a user
 * to add a new Monad to the stack in the Monad Viewer.
 * 
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class CreateDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = -8814503714946803185L;
	private	CladosCalculator	TheGUI;
	private	MonadPanel	mainPane;
	private	JButton		closeButton;
	private	JButton		saveNyadButton;
	private	JButton		saveMonadButton;

/**
 * The constructor sets up the options dialog box and displays it.
 */
	public CreateDialog(CladosCalculator mainWindow)
	throws 		UtilitiesException, BadSignatureException, CladosMonadException
	{
		
		super(mainWindow, "Create Monad Panel", true);
		TheGUI=mainWindow;
		
		JPanel centerPanel=new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		mainPane = new MonadPanel(	TheGUI, 
									"Create", 
									"test", 
									"basic", 
									"basic",
									TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Sig"));
		
		//mainPane.syncButton.setEnabled(false);
		mainPane.makeWritable();
		centerPanel.add(new JScrollPane(mainPane), "Center");
		setContentPane(centerPanel);
		
		// Create button panel
		
		JPanel dialogControlPane = new JPanel(new FlowLayout());
		dialogControlPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		saveNyadButton = new JButton("Save As Nyad", new ImageIcon(TheGUI.IniProps.getProperty("MonadViewer.Desktop.SaveImage")));
		saveNyadButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	 	saveNyadButton.setHorizontalTextPosition(SwingConstants.CENTER);
		saveNyadButton.addActionListener(this);
		dialogControlPane.add(saveNyadButton);
		
		saveMonadButton = new JButton("Save As Monad", new ImageIcon(TheGUI.IniProps.getProperty("MonadViewer.Desktop.SaveImage")));
		saveMonadButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		saveMonadButton.setHorizontalTextPosition(SwingConstants.CENTER);
		saveMonadButton.addActionListener(this);
		dialogControlPane.add(saveMonadButton);
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		dialogControlPane.add(closeButton);
		
		centerPanel.add(dialogControlPane, "South");
		
		// Set the size of the window
		pack();
		
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
    	if (command.equals("Save As Nyad"))
    	{
    		try
    		{
    			RealF tZero=RealF.ZERO(mainPane.aname.getText());
    			MonadRealF rep=new MonadRealF(	mainPane.name.getText(),
    											mainPane.aname.getText(),
    											mainPane.frame.getText(),
    											mainPane.foot.getText(),
    											mainPane.sig.getText(),
    											tZero);
    			NyadRealF rep2=new NyadRealF("New", rep);
    			TheGUI._GeometryDisplay.addNyadPanel(rep2);
    			TheGUI._StatusBar.setStatusMsg(" new nyad added to stack...");
    		}
    		catch (UtilitiesException e)
    		{
    			System.out.println("Could not create monad copy from Create|Save with general utilities exception");
    		}
    		catch (BadSignatureException es)
    		{
    			System.out.println("Could not create monad copy from Create|Save with bad signature");
    		}
    		catch (CladosMonadException em)
    		{
    			System.out.println("Could not create monad copy from Create|Save with general error");
    		}
    	}
    	if (command.equals("Save As Monad"))
    	{
    		//get the existing nyad to be used to create this next monad
    		//nyad.createMonad(String pName, String pAlgebra, String pFrame,String pSig) 
    		NyadPanel tNSpotP = TheGUI._GeometryDisplay.getNyadPanel(TheGUI._GeometryDisplay.getPaneFocus());
    		NyadRealF tNSpotF = tNSpotP.getNyad();
    		
    		
    		try
    		{
    			tNSpotF.createMonad(	mainPane.name.getText(), 
    									mainPane.aname.getText(), 
    									mainPane.frame.getText(), 
    									mainPane.sig.getText());
    			MonadRealF rep = tNSpotF.getMonadList(tNSpotF.getNyadOrder()-1);
    			tNSpotP.addMonadTab(rep);
    			
    			TheGUI._StatusBar.setStatusMsg(" new monad added to selected nyad...");
    		}
    		catch (UtilitiesException e)
    		{
    			System.out.println("Could not create monad copy from Create|Save with general utilities exception");
    			e.printStackTrace();
    		}
    		catch (BadSignatureException es)
    		{
    			System.out.println("Could not create monad copy from Create|Save with bad signature");
    			es.printStackTrace();
    		}
    		catch (CladosMonadException em)
    		{
    			System.out.println("Could not create monad copy from Create|Save with general error");
    			em.printStackTrace();
    		}
    		catch (CladosNyadException en)
    		{
    			System.out.println("Could not create monad copy from Create|Save with general error");
    			System.out.println(en.getSourceMessage());
    			
    		}
    	}
    }
}
