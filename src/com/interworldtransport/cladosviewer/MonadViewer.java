/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.MonadViewer<br>
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
 * ---com.interworldtransport.cladosviewer.MonadViewer<br>
 * ------------------------------------------------------------------------ <br>
 */
package com.interworldtransport.cladosviewer;

import com.interworldtransport.cladosF.*;
import com.interworldtransport.cladosFExceptions.*;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.CantGetIniException;
import com.interworldtransport.cladosviewerExceptions.CantGetSaveException;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
/**
 * The MonadViewer class will display Nyads and Monads and allow the user to
 * manipulate them via methods similar to old four-function calculators
 *
 * @version  0.85
 * @author   Dr Alfred W Differ
 * @since clados 1.0
 */
public class MonadViewer extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 2888655434121808487L;
/**
 * The in-window Menu for the application.
 */
    public		ViewerMenu			_MenuBar;
/**
 * The EventModel for the application.
 */
    public		ViewerEventModel	_EventModel;
/**
 * The Center Display Panel for the application.
 * Located in the center of the GUI and intended for display panels.
 */
    public		ViewerPanel			_GeometryDisplay;
/**
 * The Status Display Panel for the application.
 * Located at the bottom of the GUI and intended for status information.
 */
    public		UtilityStatusBar	_StatusBar;
    
/**
 * The global button display panel for the application.
 * Located at the top of the GUI and intended for nyad management.
 */
    public		JPanel			_HeaderBar;
/**
 * Buttons for use in the header bar for nyad management.    
 */
    public		JButton			swapBelow;
    public		JButton			swapAbove;
    public		JButton			copyNyad;
    public		JButton			removeNyad;
    public		JButton			newNyad;
    //public	JButton			addNyads;
    //public	JButton			lmultNyads;
    //public	JButton			rmultNyads;
    public		JButton			scaleNyad;

    public		Properties		IniProps;
/** This FileWriter points to the actual save file for historical snapshot data
 */
    public		FileWriter		to;
    //public		File 			fIni;
    private		JFileChooser 	fc;
/**
 * This is the main constructor the the Monad Viewer
 */
    public MonadViewer(	String pTitle,
    					String pConfig)
    throws 		UtilitiesException,
    			BadSignatureException,
    			CantGetIniException
    {
    	super(pTitle);
    	addWindowListener( new WindowAdapter()
    		{
	    		public void windowClosing(WindowEvent e)
	    		{
	    			System.exit(0);
	    		}
    		}
    	);
    	getConfigProps(pConfig);
    	Container cp=getContentPane();
    	
    	_MenuBar=new ViewerMenu(this);
    	setJMenuBar(_MenuBar);	//The Menu Bar is an element of the parent class JFrame
    	_EventModel=new ViewerEventModel(_MenuBar);
    	
    	_StatusBar=new UtilityStatusBar();
    	cp.add(_StatusBar, "South");
    	
    	_HeaderBar=new JPanel();
    	_HeaderBar.setLayout(new GridBagLayout());
    	_HeaderBar.setBorder(new EmptyBorder(0, 0, 0, 0));
       	_HeaderBar.setBackground(new Color(255, 255, 222));
       	fillNyadControls();
    	cp.add(_HeaderBar,"West");
    	
    	_GeometryDisplay=new ViewerPanel(this);
    	cp.add(_GeometryDisplay, "Center");
    	
    	fc = new JFileChooser();
    	fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    	
    	setLocation(100, 10);
    	_StatusBar.setStatusMsg(" ...complete\n");
    	
    }

    public void actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	
    	if (command.equals("erase"))
    		removeCommand();
    	
    	if (command.equals("create"))
    		createCommand();
    	
    	if (command.equals("add"))
    		addCommand();
    		
    	if (command.equals("scale"))
    		scaleCommand();
    	
    	if (command.equals(">mult"))
    		leftMultiplyCommand();
    	
    	if (command.equals("mult<"))
	    	rightMultiplyCommand();
    	
    	if (command.equals("push"))
    		_GeometryDisplay.push();	//Swaps the currently selected nyad with the one below it
    	
    	if (command.equals("pop"))
    		_GeometryDisplay.pop();		//Swaps the currently selected nyad with the one above it
    	
    	if (command.equals("copy"))
	    	copyNyadCommand();
    }
    private void addCommand()
    {
    	MonadPanel temp0=_GeometryDisplay.getNyadPanel(0).getMonadPanel(0);
		MonadPanel temp1=_GeometryDisplay.getNyadPanel(1).getMonadPanel(0);
		MonadRealF Monad0=null;
		MonadRealF Monad1=null;
		
		if (temp0!=null)
			Monad0=temp0.getMonad();
		if (temp1!=null)
			Monad1=temp1.getMonad();
		
		if (Monad0!=null || Monad1!=null)
		{
			try
			{
				Monad0.add(Monad1);
				temp0.setCoefficientDisplay();
				_StatusBar.setStatusMsg("Second Monad added to the first.\n");
			}
			catch (CladosMonadBinaryException e)
			{
				_StatusBar.setStatusMsg("Reference Match error between second and first monads.\n");
				_StatusBar.setStatusMsg("Second Monad not added to the first.\n");
			}
			catch (FieldBinaryException efb)
			{
				_StatusBar.setStatusMsg("Second Monad not added to the first due to field binary exception.\n");
			}
		}
		else
		{
			_StatusBar.setStatusMsg("Second Monad not added to the first.\n");
		}
    }
    private void copyNyadCommand()
    {
    	if (_GeometryDisplay.getNyadListSize()>0)
		{
			try
			{
				NyadRealF focusNyad=_GeometryDisplay.getNyadPanel(_GeometryDisplay.getPaneFocus()).getNyad();
				String buildName=new StringBuffer(focusNyad.getName()).append("_c").toString();
				NyadRealF newNyadCopy=new NyadRealF(buildName, focusNyad);
				_GeometryDisplay.addNyadPanel(newNyadCopy);
			}
			catch (UtilitiesException e)
			{
				_StatusBar.setStatusMsg("Could not create copy from toolbar.\n");
			}
			catch (BadSignatureException es)
			{
				_StatusBar.setStatusMsg("Could not create copy from toolbar due to signature issue.\n");
			}
		}
    }
    private void createCommand()
    {
    	try
		{
    		_StatusBar.setStatusMsg("CreateDialog opening...");
			CreateDialog create = new CreateDialog(this);
			if (create != null)
				_StatusBar.setStatusMsg(" ... and dialog is now closing.\n");
			
		}
		catch (UtilitiesException e)
		{
			//Do nothing.  Exception implies user doesn't get to create
			//a new Monad, so nothing is the correct action.
			System.out.println("Couldn't construct create dialog.");
		}
		catch (BadSignatureException es)
		{
			//Do nothing.  Exception implies user doesn't get to create
			//a new Monad, so nothing is the correct action.
			System.out.println("Couldn't construct create dialog.");
		}
		catch (CladosMonadException em)
		{
			//Do nothing.  Exception implies user doesn't get to create
			//a new Monad, so nothing is the correct action.
			System.out.println("Couldn't construct create dialog.");
		}
    }
    private void fillNyadControls()
    {
    	GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);
		//cn.fill=GridBagConstraints.HORIZONTAL;
		cn.anchor=GridBagConstraints.NORTH;
		
		cn.gridx = 0;
		cn.gridy = 0;
		cn.weightx=0;
		cn.weighty=1;
		cn.gridheight=2;
    	_HeaderBar.add(new JLabel(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.HeaderImage2"))),cn);
		cn.gridy++;
		cn.gridy++;
		
		cn.gridheight=1;
    	swapBelow=new JButton("push", new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.PushImage")));
    	swapBelow.setVerticalTextPosition(SwingConstants.BOTTOM);
    	swapBelow.setHorizontalTextPosition(SwingConstants.CENTER);
    	swapBelow.setPreferredSize(new Dimension(50,50));
    	swapBelow.addActionListener(this);
    	_HeaderBar.add(swapBelow, cn);
    	cn.gridx++;
    	
    	swapAbove=new JButton("pop", new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.PopImage")));
    	swapAbove.setVerticalTextPosition(SwingConstants.BOTTOM);
    	swapAbove.setHorizontalTextPosition(SwingConstants.CENTER);
    	swapAbove.setPreferredSize(new Dimension(50,50));
    	swapAbove.addActionListener(this);
    	_HeaderBar.add(swapAbove, cn);
    	cn.gridx = 0;
		cn.gridy++;
    	
    	copyNyad = new JButton("copy", new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.CopyImage")));
    	copyNyad.setVerticalTextPosition(SwingConstants.BOTTOM);
    	copyNyad.setHorizontalTextPosition(SwingConstants.CENTER);
    	copyNyad.setPreferredSize(new Dimension(50,50));
    	copyNyad.addActionListener(this);
    	_HeaderBar.add(copyNyad, cn);
    	cn.gridx++;
    	
    	removeNyad = new JButton("erase", new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.RemoveImage")));
    	removeNyad.setVerticalTextPosition(SwingConstants.BOTTOM);
    	removeNyad.setHorizontalTextPosition(SwingConstants.CENTER);
    	removeNyad.setPreferredSize(new Dimension(50,50));
    	removeNyad.addActionListener(this);
    	_HeaderBar.add(removeNyad, cn);
    	cn.gridx = 0;
		cn.gridy++;
    	
    	newNyad = new JButton("create", new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.CreateImage")));
    	newNyad.setVerticalTextPosition(SwingConstants.BOTTOM);
    	newNyad.setHorizontalTextPosition(SwingConstants.CENTER);
    	newNyad.setPreferredSize(new Dimension(50,50));
    	newNyad.addActionListener(this);
    	_HeaderBar.add(newNyad, cn);
    	cn.gridy++;
    	
    	scaleNyad = new JButton("scale", new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.ScaleImage")));
    	scaleNyad.setVerticalTextPosition(SwingConstants.BOTTOM);
    	scaleNyad.setHorizontalTextPosition(SwingConstants.CENTER);
    	scaleNyad.setPreferredSize(new Dimension(50,50));
    	scaleNyad.addActionListener(this);
    	_HeaderBar.add(scaleNyad, cn);
    
    	//lmultNyads = new JButton(">mult");
    	//lmultNyads.addActionListener(this);
    	//_HeaderBar.add(lmultNyads, cn);
 
    	//addNyads = new JButton("add");
    	//addNyads.addActionListener(this);
    	//_HeaderBar.add(addNyads, cn);
    	//cn.gridx++;
    	
    	//rmultNyads = new JButton("mult<");
    	//rmultNyads.addActionListener(this);
    	//_HeaderBar.add(rmultNyads, cn);
    	
    }
    private void leftMultiplyCommand()
    {
    	MonadPanel temp0=_GeometryDisplay.getNyadPanel(0).getMonadPanel(0);
		MonadPanel temp1=_GeometryDisplay.getNyadPanel(1).getMonadPanel(0);
		MonadRealF Monad0=null;
		MonadRealF Monad1=null;
		
		if (temp0!=null)
			Monad0=temp0.getMonad();
		if (temp1!=null)
			Monad1=temp1.getMonad();
		
		if (Monad0!=null || Monad1!=null)
		{
			try
			{
				Monad0.multiplyLeft(Monad1);
				temp0.setCoefficientDisplay();
				_StatusBar.setStatusMsg("Second Monad muliplied against the first from the left.\n");
			}
			catch (CladosMonadBinaryException e)
			{
				_StatusBar.setStatusMsg("Reference Match error between second and first monads.\n");
				_StatusBar.setStatusMsg("Second Monad not muliplied against the first from the left.\n");
			}
			catch (FieldBinaryException efb)
			{
				_StatusBar.setStatusMsg("Second Monad not multiplied to the first due to field binary exception.\n");
			}
		}
		else
			_StatusBar.setStatusMsg("Second Monad not muliplied against the first from the left.\n");
		
    }
    private void removeCommand()
    {
		if (_GeometryDisplay.NyadPanes.getTabCount()>1)
		{
			int point = _GeometryDisplay.NyadPanes.getSelectedIndex();
			_GeometryDisplay.removeNyadPanel(point);
		}
    }
    private void rightMultiplyCommand()
    {
    	MonadPanel temp0=_GeometryDisplay.getNyadPanel(0).getMonadPanel(0);
		MonadPanel temp1=_GeometryDisplay.getNyadPanel(1).getMonadPanel(0);
		MonadRealF Monad0=null;
		MonadRealF Monad1=null;
		
		if (temp0!=null)
			Monad0=temp0.getMonad();
		if (temp1!=null)
			Monad1=temp1.getMonad();
		
		if (Monad0!=null || Monad1!=null)
		{
			try
			{
				Monad0.multiplyRight(Monad1);
				temp0.setCoefficientDisplay();
				_StatusBar.setStatusMsg("Second Monad muliplied against the first from the right.\n");
			}
			catch (CladosMonadBinaryException e)
			{
				_StatusBar.setStatusMsg("Reference Match error between second and first monads.\n");
				_StatusBar.setStatusMsg("Second Monad not muliplied against the first from the right.\n");
			}
			catch (FieldBinaryException efb)
			{
				_StatusBar.setStatusMsg("Second Monad not multiplied to the first due to field binary exception.\n");
			}
		}
		else
			_StatusBar.setStatusMsg("Second Monad not muliplied against the first from the right.\n");
    }
    
    private void scaleCommand()
    {
    	try
		{
    		//Find the selected nyad and monad panels
    		
    		NyadPanel tNSpotPnl = _GeometryDisplay.getNyadPanel(_GeometryDisplay.getPaneFocus());
			MonadPanel tMSpotPnl=tNSpotPnl.getMonadPanel(tNSpotPnl.getPaneFocus());
			
			//Now point to the monad to be scaled
			MonadRealF tMonad=tMSpotPnl.getMonad();
			
			//...and scale it
			tMonad.scale(new RealF(	tMonad.getCoeff((short) 0).getFieldType(), 
									new Float(_StatusBar.stRealIO.getText()).floatValue()));
			
			//redraw the UI's Monad Panel to show the rescaled Monad there
			tMSpotPnl.setCoefficientDisplay();
			_StatusBar.setStatusMsg(" monad has been rescaled by | ");
			_StatusBar.setStatusMsg(_StatusBar.stRealIO.getText()+"\n");
		}
		catch (FieldBinaryException efb)
		{
			_StatusBar.setStatusMsg(" monad has NOT been rescaled due to field binary exception.\n");
		}
    }
/**
 * This method does the initial file handling for the configuration file.
 * It doesn't do anything fancy... just get it and load it into IniProps.
 */
    protected void getConfigProps(String pConfName) throws CantGetIniException
	{
    	File fIni=null;
    	boolean bIni = false;
    	try
    	{
    		fIni=new File(pConfName);
    		bIni=fIni.exists();
    		if (bIni) bIni=fIni.isFile();
    		if (bIni) bIni=fIni.canRead();
    		if (!bIni) 
    			throw new CantGetIniException("The configuration file is not present or ready.");
    		//Getting here implied the configuration file has been found and readable
    		//System.out.println("No issue getting config file");

    		IniProps=new Properties(System.getProperties());
    		IniProps.load(new BufferedInputStream(new FileInputStream(fIni)));
    	}
    	catch(Exception e)
    	{
    		System.out.println("IO Problem:  Incomplete Access to Associated INI files.");
    		throw new CantGetIniException("No Access to INI file.");
    	}
	} 

/**
 * This method does the initial file handling for the snapshot save file.
 * It doesn't do much right now except check to see if the file is there.
 */
    protected void getSaveFile(String pSaveName)
    throws 	IOException, 
    		CantGetSaveException
    {
    	String SaveName=null;
    	if (pSaveName==null)
    		SaveName=IniProps.getProperty("MonadViewer.Desktop.Snapshot");
    	else
    		SaveName=pSaveName;
    	
    	File fSave=null;
    	boolean bSave=false;
    	
    	fSave=new File(SaveName);
    	bSave=fSave.exists();
    	if (bSave) bSave=fSave.isFile();
    	if (bSave) bSave=fSave.canWrite();
    	if (!bSave)
    		throw new CantGetSaveException("No access to snapshot save file.");
    	
    	//Getting here implies the Save file has been found and is writeable
    	to=new FileWriter(fSave);
    	if (to!=null) 
    		System.out.println("Write file is not null");
    	else 
    		System.out.println("Write file is null");
    }

/**
 * This method saves snapshot data to the save file.
 */
    public void saveSnapshot(String pType)
    	throws 	IOException, 
    			CantGetSaveException
    {
    	String SaveName=IniProps.getProperty("MonadViewer.Desktop.Snapshot");
    	if (pType==null)
    	{
    		if (to==null)
    		{
    			File fIni=null;
    	    	boolean bSave=false;
    	    	
    	    	fIni=new File(SaveName);
    	    	bSave=fIni.exists();
    	    	if (bSave) bSave=fIni.isFile();
    	    	if (bSave) bSave=fIni.canWrite();
    	    	if (!bSave)
    	    		throw new CantGetSaveException("No access to snapshot save file.");
    	    	
    	    	//Getting here implies the Save file has been found and is writeable
    	    	to=new FileWriter(fIni, true);    	    	
    		}
    	}
    	else
    	{
    		int returnVal = fc.showSaveDialog(this);
    		if (returnVal == JFileChooser.APPROVE_OPTION) 
    		{
    			File fIni = fc.getSelectedFile();
    			SaveName=fIni.getName();
    			//This is where a real application would open the file.
    			boolean bSave=false;
    			bSave=fIni.exists();
    			if (bSave) bSave=fIni.isFile();
    			if (bSave) bSave=fIni.canWrite();
    			if (!bSave)
    				throw new CantGetSaveException("No access to snapshot save file.");
    			
    			//Getting here implies the Save file has been found and is writeable
    			to=new FileWriter(fIni, true);
    			//Change the Snapshot property so it can be used for 'Save' next
    			//time.  No chooser dialog should be needed then.
    			IniProps.setProperty("MonadViewer.Desktop.Snapshot", SaveName);
    		} 
    	}
	to.write(makeSnapshotContent());
	to.write("\r\n");
	to.flush();
	_StatusBar.setStatusMsg("Snapshot of stack is saved.\n");

    }

    private String makeSnapshotContent()
    {
    	StringBuffer content=new StringBuffer();

    	content.append("<Application Name=\"clados Calculator\", ");
    	content.append("Rights=\"Copyright 2018 Alfred Differ\", ");
    	content.append("Licensee=\" ");
    	content.append(IniProps.getProperty("MonadViewer.User.Name"));
    	content.append("\" />");
    	
    	content.append("\r\n");

    	int listsize=_GeometryDisplay.getNyadListSize();
    	content.append("<NyadCount size=\"");
    	content.append(new StringBuffer().append(listsize).toString());
    	content.append("\" />");

    	content.append("\r\n");

    	for (int j=0; j<listsize; j++)
    	{
    		
    		NyadPanel tempNp=_GeometryDisplay.getNyadPanel(j);
    		    		
    		content.append("<Nyad");
    		
    		content.append(" Name=\"");
    		content.append(tempNp.getNyad().getName());
    		content.append("\", ");
    		
    		content.append(" Order=\"");
    		content.append(tempNp.getNyad().getNyadOrder());
    		content.append("\", ");
    		
    		content.append(" Foot=\"");
    		content.append(tempNp.getNyad().getFootPoint().getFootName());
    		
    		content.append("\">\r\n");
    		
    		
    		//Start another loop here when more than one monad is in a nyad
    		for (int m=0; m<tempNp.getNyad().getNyadOrder(); m++)
    		{
    			MonadPanel tempMp=tempNp.getMonadPanel(m);
    			
    			content.append("<Monad");

        		content.append(" name=\"");
        		content.append(tempMp.name.getText());
        		content.append("\", ");

        		content.append("algebra=\"");
        		content.append(tempMp.aname.getText());
        		content.append("\", ");
        		
        		content.append("signature=\"");
        		content.append(tempMp.sig.getText());
        		content.append("\", ");

        		content.append("frame=\"");
        		content.append(tempMp.frame.getText());
        		content.append("\", ");

        		content.append("foot=\"");
        		content.append(tempMp.foot.getText());
        		content.append("\">");
        		content.append("\r\n");

        		
        		MonadRealF temp=tempMp.getMonad();
        		RealF[] tc=temp.getCoeff();
        		content.append("\t<Coefficients>");
        		content.append("\r\n");
        		for (int k=0; k<temp.getAlgebra().getGProduct().getBladeCount(); k++)
        		{
        		    content.append("\t\t<Item>");
        		    content.append(new StringBuffer().append(tc[k].getReal()).toString());
        		    content.append("</Item>");
        		    content.append("\r\n");
        		}
        		content.append("\t</Coefficients>");
        		content.append("\r\n");
        		content.append("</Monad>");
        		content.append("\r\n");
    		}
    		content.append("</Nyad>");
    		content.append("\r\n");
    	}

    	String contentstring = new String(content);
    	return contentstring;
    }
    
    protected void terminateModel()
    {
		to=null;
		System.exit(0);
    }

    public static void main(String[] args)
	{
		 String TitleName="Monad Viewer Utility";
		 String ConfName="conf/MonadViewer.conf";
	
		 if (args.length%2==1)
		 {
			  System.out.println("Usage: MonadViewer [-t TitleString -c ConfigFile] ");
			  System.exit(0);
		 }
	
		 for (int i=0; i<args.length; i=i+2)
		 {
			  if (args[i].equals("-t")) TitleName=args[i+1];
			  if (args[i].equals("-c")) ConfName=args[i+1];
		 }
		 try
		 {
			 JFrame fr = new MonadViewer(TitleName, ConfName);
			 fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 fr.pack();
			 fr.setVisible(true);
		 }
		 catch (BadSignatureException be)
		 {
		 	 System.out.println("Bad Signature Exception occured while constructing a Monad");
		 	 System.exit(0);
		 }
		 catch (UtilitiesException e)
		 {
		 	 System.out.println("Exception occured while constructing main GUI");
		 	 System.exit(0);
		 }
	}
} 
