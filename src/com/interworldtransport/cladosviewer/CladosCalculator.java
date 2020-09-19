/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.CladosCalculator<br>
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
 * ---com.interworldtransport.cladosviewer.CladosCalculator<br>
 * ------------------------------------------------------------------------ <br>
 */
package com.interworldtransport.cladosviewer;

import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosG.NyadRealF;
import com.interworldtransport.cladosviewerExceptions.CantGetIniException;
import com.interworldtransport.cladosviewerExceptions.CantGetSaveException;

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
public class CladosCalculator extends JFrame implements ActionListener
{
	public static void main(String[] args)
	{
		//default string entries in case someone starts this without any arg's at all
		 String TitleName="Clados Calculator Utility";
		 String ConfName="conf/CladosCalculator.conf";
	
		 if (args.length%2==1)
		 {
			  System.out.println("Usage: CladosCalculator [-t TitleString -c ConfigFile] ");
			  System.exit(0);
		 }
	
		 for (int i=0; i<args.length; i=i+2)
		 {
			  if (args[i].equals("-t")) TitleName=args[i+1];
			  if (args[i].equals("-c")) ConfName=args[i+1];
		 }
		 
		
		 JFrame fr = new CladosCalculator(TitleName, ConfName);
		 fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 fr.pack();
		 fr.setVisible(true);
	}
	/**
	 * The global button display panel for the application.
	 * Located at the top of the GUI and intended for nyad management.
	 */
    private		JPanel				_ControlBar;
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
	 * The in-window Menu for the application.
	 */
	public		ViewerMenu			_MenuBar;
	/**
	 * The Status Display Panel for the application.
	 * Located at the bottom of the GUI and intended for status information.
	 */
	public		UtilityStatusBar	_StatusBar;
	/**
	 * The Field Display Panel for the application.
	 * Located at the top of the GUI and intended for numeric inputs.
	 */
	public		FieldPanel		_FieldBar;
	

    public		JButton			isEqual;
    public		JButton			isGrade;
    public		JButton			isIdempotent;
    public		JButton			isMIdempotent;
    public		JButton			isMultiGrade;
    public		JButton			isNilpotent;
    /**
	 * Buttons for use in the control bar for nyad management.    
	 */
    public		JButton			isRefMatch;
    public		JButton			isZero;

    public		JButton			whatGrade;
    public		JButton			whatMagn;
    public		JButton			whatSQMagn;
    
    //public		DivFieldType	fieldType;
	//public		JTextField 		fieldTypeIO = new JTextField();
    //public		JTextField 		realIO = new JTextField();
   // public		JTextField 		imgIO = new JTextField();
    //public		JTextField 		img2IO = new JTextField();
    //public		JTextField 		img3IO = new JTextField();
    private		Color			_backColor = new Color(255, 255, 222);
    
    /*
	 * 
	 */
    private		JFileChooser 	fc;
    /*
     * This FileWriter points to the actual save file for historical snapshot data
	 */
    private		FileWriter		saveItTo;
	/*
     * This is the properties object containing key/value pairs from the config file
     * and any system settings that might be useful.
     */
    protected	Properties		IniProps;

    /**
	 * This is the main constructor the the Monad Viewer
	 * @param pTitle
	 * 	String
	 * This is the title string placed at the top of the viewer
	 * @param pConfig
	 * 	String
	 * This is the path/filename for the configuration file for the viewer
	 */
	public CladosCalculator(	String pTitle,
	   							String pConfig)
	   	//throws 	UtilitiesException
	{
	    super(pTitle);
	    addWindowListener( new WindowAdapter()
	    	{
		   		public void windowClosing(WindowEvent e)
		   		{
		   			System.gc();
		   			System.exit(0);
		   		}
	    	}
	    );
	    try
	    {
	    	getConfigProps(pConfig);
	    }
		catch (CantGetIniException e)
		{
		 	 System.out.println("Can't find the configuration file while constructing main GUI");
		 	 System.gc();
		 	 System.exit(0);
		}
	    Container cp=getContentPane();
	    	
	    _MenuBar=new ViewerMenu(this);
	    setJMenuBar(_MenuBar);	//The Menu Bar is an element of the parent class JFrame
	    _EventModel=new ViewerEventModel(_MenuBar);
	    	
	    _StatusBar=new UtilityStatusBar();
	    cp.add(_StatusBar, "South");
	    	
	    _ControlBar=new JPanel();
	    _ControlBar.setLayout(new GridBagLayout());
	    _ControlBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	    _ControlBar.setBackground(_backColor);
	    createTestControls();
	    cp.add(_ControlBar,"West");
	    	
	    _GeometryDisplay=new ViewerPanel(this);
	    _GeometryDisplay.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	    cp.add(_GeometryDisplay, "Center");
	    
	    _FieldBar=new FieldPanel(this, _GeometryDisplay.getNyadPanel(_GeometryDisplay.getPaneFocus()).getNyad().protoOne);
	    cp.add(_FieldBar, "North");
	    	
	    fc = new JFileChooser();
	    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    	
	    setLocation(100, 100);
	    //_StatusBar.setStatusMsg(" ...complete\n");
	}
    
    public void actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	
    	if (command.equals("reference match"))
    		_MenuBar.mniisReferenceMatch.doClick();
    	
    	if (command.equals("equal"))
    		_MenuBar.mniisEqual.doClick();
    	
    	if (command.equals("zero"))
    		_MenuBar.mniisZero.doClick();
    	
    	if (command.equals("nilpotent"))
    		_MenuBar.mniisNilpotent.doClick();
    	
    	if (command.equals("idempotent"))
    		_MenuBar.mniisIdempotent.doClick();
    	
    	if (command.equals("scaled idempotent"))
    		_MenuBar.mniisIdempotentMultiple.doClick();
    	
    	if (command.equals("is grade"))
    		_MenuBar.mniisGrade.doClick();
    	
    	if (command.equals("is mgrade"))
    		_MenuBar.mniisMultiGrade.doClick();
    	
    	if (command.equals("is grade!"))
    		_MenuBar.mniisSGrade.doClick();
    	
    	//if (command.equals("grade cut"))
    	//	_MenuBar.mniGradeCut.doClick();
    	
    	//if (command.equals("grade crop"))
    	//	_MenuBar.mniGradeCrop.doClick();
    	
    	if (command.equals("magnitude of"))
    		_MenuBar.mniMagnitudeOf.doClick();
    	
    	if (command.equals("sqmagnitude of"))
    		_MenuBar.mniSQMagnitudeOf.doClick();
  
    	//if (command.equals("scale"))
    	//	_MenuBar.mniScale.doClick();
    		//scaleCommand();
    	
    	//if (command.equals("normalize"))
    	//	_MenuBar.mniNormalize.doClick();
    	
    	//if (command.equals("invert"))
    	//	_MenuBar.mniInvert.doClick();
    	
    	//if (command.equals("reverse"))
    	//	_MenuBar.mniReverse.doClick();
    	
    	//if (command.equals("dual>"))
    	//	_MenuBar.mniDualLeft.doClick();
    	
    	//if (command.equals("<dual"))
    	//	_MenuBar.mniDualRight.doClick();
    }
    /**
	 * This method saves snapshot data to the save file.
	 * @param pType
	 * 	String
	 * This string alters the mode of the method allowing the use of a cached save file
	 * When it is 'null' we except the cached value from the configuration file.
	 * @throws CantGetSaveException
	 * This exception gets thrown when IO issues occur blocking access to writes to the save file.
	 */
	public void saveSnapshot(String pType)	throws 	CantGetSaveException
	{
		// If pType is null we use the configured save file if it is known.
		
	    String SaveName=IniProps.getProperty("Desktop.Snapshot");
	    if (pType==null) 		// switch setting for 'save' with current snapshot target if known
	    {
	    	if (saveItTo==null)	// but if it is not known, make one up from conf setting
	    	{
	    		File fIni=new File(SaveName);
	    	    if (!(fIni.exists() & fIni.isFile() & fIni.canWrite()))
	    	    	throw new CantGetSaveException("No access to snapshot save file.");
	    	    
	    	    try
	    	    {
	    	    	saveItTo=new FileWriter(fIni, true);
	    	    	saveItTo.write(makeSnapshotContent());
	    	    	saveItTo.write("\r\n");
	    	    	saveItTo.flush();
	    	    	saveItTo.close();
		    		_StatusBar.setStatusMsg("\tsnapshot of stack is saved.\n");
	    	    }
	    	    catch (IOException e)
	    	    {
	    	    	_StatusBar.setStatusMsg("\t\tsnapshot of stack is NOT saved due to IO Exception.\n");
	    	    }
	    	}
	    }
	    else					//pType isn't null... so let user choose graphically and then save the choice as a property.
	    {
	    	int returnVal = fc.showSaveDialog(this);
	    	if (returnVal == JFileChooser.APPROVE_OPTION) 
	    	{
	    		File fIni = fc.getSelectedFile();
	    		SaveName=fIni.getName();
	    		if (!(fIni.exists() & fIni.isFile() & fIni.canWrite()))
		    	    	throw new CantGetSaveException("No access to snapshot save file.");
	  
	    		try 
	    		{
		    		saveItTo=new FileWriter(fIni, true);
		    		saveItTo.write(makeSnapshotContent());
		    		saveItTo.write("\r\n");
		    		saveItTo.flush();
		    		saveItTo.close();
		    		_StatusBar.setStatusMsg("\tsnapshot of stack is saved.\n");
		    		
		    		//Change the Snapshot property so it can be used for 'Save' next
		    		//time.  No chooser dialog should be needed then.
		    		IniProps.setProperty("Desktop.Snapshot", SaveName);
	    		}
	    		catch (IOException e)
	    		{
	    			_StatusBar.setStatusMsg("\t\tsnapshot of stack is NOT saved due to IO Exception.\n");
	    		}
	    	} 
	    }
	}
    
    public void terminateModel() 
    {
		try 
		{
			if (saveItTo != null)
				saveItTo.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Couldn't close the snapshot save file. Resource leakage is occuring.");
			e.printStackTrace();
		}
		finally
		{
			System.exit(0);
		}
    }

    
    
    private void createTestControls()
    {
    	Dimension square = new Dimension(44,44);
    	GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);
		
		cn.anchor=GridBagConstraints.NORTH;
		
		cn.gridx = 0;
		cn.gridy = 0;
		cn.fill=GridBagConstraints.HORIZONTAL;
		cn.weightx=0;
		cn.weighty=0;
		cn.gridheight=1;
		cn.gridwidth=1;

    	// button triple
    	isRefMatch = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.RefMatch")));
    	isRefMatch.setActionCommand("reference match");
    	isRefMatch.setToolTipText("refernce Match Nyad Test");
    	isRefMatch.setPreferredSize(square);
    	isRefMatch.setBorder(BorderFactory.createEtchedBorder(0));
    	isRefMatch.addActionListener(this);
    	_ControlBar.add(isRefMatch, cn);
    	cn.gridx++;
    	
    	isEqual = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Equal")));
    	isEqual.setActionCommand("equal");
    	isEqual.setToolTipText("strong Equality Nyad Test");
    	isEqual.setPreferredSize(square);
    	isEqual.setBorder(BorderFactory.createEtchedBorder(0));
    	isEqual.addActionListener(this);
    	_ControlBar.add(isEqual, cn);
    	cn.gridx++;

    	isZero = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Zero")));
    	isZero.setActionCommand("zero");
    	isZero.setToolTipText("additive Identity (Zero) Monad Test");
    	isZero.setPreferredSize(square);
    	isZero.setBorder(BorderFactory.createEtchedBorder(0));
    	isZero.addActionListener(this);
    	_ControlBar.add(isZero, cn);
    	
    	cn.gridx = 0;
    	cn.gridy++;
    	
    	// button triple
    	isMIdempotent = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.MIdempotent")));
    	isMIdempotent.setActionCommand("scaled idempotent");
    	isMIdempotent.setToolTipText("multiple of Idempotent Monad Test");
    	isMIdempotent.setPreferredSize(square);
    	isMIdempotent.setBorder(BorderFactory.createEtchedBorder(0));
    	isMIdempotent.addActionListener(this);
    	_ControlBar.add(isMIdempotent, cn);
    	cn.gridx++;
    	
    	isIdempotent = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Idempotent")));
    	isIdempotent.setActionCommand("idempotent");
    	isIdempotent.setToolTipText("idempotent Monad Test");
    	isIdempotent.setPreferredSize(square);
    	isIdempotent.setBorder(BorderFactory.createEtchedBorder(0));
    	isIdempotent.addActionListener(this);
    	_ControlBar.add(isIdempotent, cn);
    	cn.gridx++;
    	
    	isNilpotent = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Nilpotent")));
    	isNilpotent.setActionCommand("nilpotent");
    	isNilpotent.setToolTipText("nilpotent Monad Test");
    	isNilpotent.setPreferredSize(square);
    	isNilpotent.setBorder(BorderFactory.createEtchedBorder(0));
    	isNilpotent.addActionListener(this);
    	_ControlBar.add(isNilpotent, cn);
    	
    	cn.gridx = 0;
    	cn.gridy++;
    	//end button triple
    	
    	// button triple
    	isGrade = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Grade")));
    	isGrade.setActionCommand("is grade");
    	isGrade.setToolTipText("is Grade() Monad Test");
    	isGrade.setPreferredSize(square);
    	isGrade.setBorder(BorderFactory.createEtchedBorder(0));
    	isGrade.addActionListener(this);
    	_ControlBar.add(isGrade, cn);
    	cn.gridx++;
    	
    	isMultiGrade = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.MultiGrade")));
    	isMultiGrade.setActionCommand("is mgrade");
    	isMultiGrade.setToolTipText("is MultiGrade Monad Test");
    	isMultiGrade.setPreferredSize(square);
    	isMultiGrade.setBorder(BorderFactory.createEtchedBorder(0));
    	isMultiGrade.addActionListener(this);
    	_ControlBar.add(isMultiGrade, cn);
    	cn.gridx++;
    	
    	whatGrade = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.WhatGrade")));
    	whatGrade.setActionCommand("is grade!");
    	whatGrade.setToolTipText("what Unique Grade Monad Test");
    	whatGrade.setPreferredSize(square);
    	whatGrade.setBorder(BorderFactory.createEtchedBorder(0));
    	whatGrade.addActionListener(this);
    	_ControlBar.add(whatGrade, cn);
    	
    	cn.gridx = 0;
    	cn.gridy++;
    	//end button triple
    	
    	// button triple
    	whatMagn = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Magnitude")));
    	whatMagn.setActionCommand("magnitude of");
    	whatMagn.setToolTipText("discover Monad Magnitude");
    	whatMagn.setPreferredSize(square);
    	whatMagn.setBorder(BorderFactory.createEtchedBorder(0));
    	whatMagn.addActionListener(this);
    	_ControlBar.add(whatMagn, cn);
    	cn.gridx++;
    	
    	whatSQMagn = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.SQMagnitude")));
    	whatSQMagn.setActionCommand("sqmagnitude of");
    	whatSQMagn.setToolTipText("discover Monad Magnitude^2");
    	whatSQMagn.setPreferredSize(square);
    	whatSQMagn.setBorder(BorderFactory.createEtchedBorder(0));
    	whatSQMagn.addActionListener(this);
    	_ControlBar.add(whatSQMagn, cn);
    	//cn.gridx++;
   
    	
    	cn.gridx = 0;
    	cn.gridy++;
    	//end button triple
    	
    	cn.weightx=1;
		cn.weighty=1;
		cn.gridheight=2;
		cn.gridwidth=3;
		cn.fill=GridBagConstraints.BOTH;
    	_ControlBar.add(new JLabel(new ImageIcon(IniProps.getProperty("Desktop.Image.Header2"))),cn);
    }
    
    private String makeSnapshotContent()
	{
		StringBuffer content=new StringBuffer();
	
		content.append("<Application Name=\"clados Calculator\", ");
		content.append("Rights=\"Copyright 2018 Alfred Differ\", ");
		content.append("Licensee=\"");
		content.append(IniProps.getProperty("User.Name"));
		content.append("\" />\r\n");

		content.append("<NyadList size=\"");
		content.append(_GeometryDisplay.getNyadListSize());
		content.append("\">\r\n");

		//for (int j=0; j<_GeometryDisplay.getNyadListSize(); j++)
		for (NyadPanel tempNPN : _GeometryDisplay.getNyadPanels())
		{
			NyadRealF tempNp=tempNPN.getNyad();
			    		
			content.append("<Nyad");
			content.append(" Name=\"");
			content.append(tempNp.getName());
			content.append("\", ");
			
			content.append(" Order=\"");
			content.append(tempNp.getNyadOrder());
			content.append("\", ");
			
			content.append(" Foot=\"");
			content.append(tempNp.getFootPoint().getFootName());
			
			content.append("\">\r\n");
			
			content.append("<MonadList>\r\n");
			//Start another loop here when more than one monad is in a nyad
			for (int m=0; m<tempNp.getNyadOrder(); m++)
				content.append(MonadRealF.toXMLString(tempNp.getMonadList(m)));
			content.append("</MonadList>\r\n");
			content.append("</Nyad>\r\n");
		}
		content.append("</NyadList>\r\n");
		String contentstring = new String(content);
		return contentstring;
	}
	
	/**
	 * This method does the initial file handling for the configuration file.
	 * It doesn't do anything fancy... just get it and load it into IniProps.
	 * @param pConfName
	 * 	String
	 * This string holds the path and filename pointing to the configuration file
	 * @throws CantGetIniException
	 * This exception gets thrown when IO issues occur blocking access to writes to the configuration file.
	 */
	    protected void getConfigProps(String pConfName) throws CantGetIniException
		{
	    	if (pConfName == null)
	    		throw new CantGetIniException("The configuration file is not provided.");
	    		
	    	File fIni=new File(pConfName);
	    	if (!(fIni.exists() & fIni.isFile() & fIni.canWrite()))
    	    	throw new CantGetIniException("The configuration file is not valid.");
    		
	    	try (	FileInputStream tempSpot=new FileInputStream(fIni);
		    		BufferedInputStream tSpot = new BufferedInputStream(tempSpot))
	    	{
	    		IniProps=new Properties(System.getProperties());
	    		IniProps.load(tSpot);
	    		
	    		tSpot.close();
	    		tempSpot.close();
	    	}
	    	catch(IOException e)
	    	{
	    		System.out.println("IO Problem:  Incomplete Access to associated INI files.");
	    		throw new CantGetIniException("No Access to INI file.");
	    	}
		}

    /**
	 * This method does the initial file handling for the snapshot save file.
	 * It doesn't do much right now except check to see if the file is there.
	 * @param pSaveName
	 * 	String
	 * This is the string holding the path and filename for the cached save file
	 * The method tries to read it from the configuration file.
	 * @throws CantGetSaveException
	 * This exception gets thrown when IO issues occur blocking access to writes to the stated save file.
	 * There might be a path issue... or a filename issue... or permissions. Who knows?
	 */
	protected void getSaveFile(String pSaveName)
	    throws 	CantGetSaveException
	{
	    String SaveName=null;
	    if (pSaveName==null)
	    	SaveName=IniProps.getProperty("Desktop.Snapshot");
	    else
	    {
	    	SaveName=pSaveName;
	    	IniProps.setProperty("Desktop.Snapshot", pSaveName);
	    }
	
	    File fSave=new File(SaveName);
	    if (!(fSave.exists() & fSave.isFile() & fSave.canWrite()))
	    	throw new CantGetSaveException("Desktop.Snapshot should be set somewhere in the conf file.\n ...or no access to snapshot save file.");
	    //System.out.println("Desktop.Snapshot should be set somewhere in the conf file.");
	        
	    
	    // Getting here with no exceptions is the objective. 
	    // Success implies we have a valid snapshot target
	    // but there is no need to keep a FileWriter open to it.
	}
	
	
} 
