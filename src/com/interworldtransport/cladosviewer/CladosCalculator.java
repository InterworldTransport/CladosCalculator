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

import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosG.NyadRealF;
import com.interworldtransport.cladosGExceptions.BadSignatureException;

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
public class CladosCalculator extends JFrame implements ActionListener
{
	public static void main(String[] args)
	{
		//default string entries in case someone starts this without any arg's at all
		 String TitleName="Clados Calculator Utility";
		 String ConfName="conf/CladosCalculator.conf";
	
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
			 JFrame fr = new CladosCalculator(TitleName, ConfName);
			 fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 fr.pack();
			 fr.setVisible(true);
		 }
		 catch (BadSignatureException be)
		 {
		 	 System.out.println("Bad Signature Exception occured while constructing a Monad");
		 	 System.gc();
		 	 System.exit(0);
		 }
		 catch (CantGetIniException e)
		 {
		 	 System.out.println("Ini Exception occured while constructing main GUI");
		 	 System.gc();
		 	 System.exit(0);
		 }
		 catch (UtilitiesException e)
		 {
		 	 System.out.println("Exception occured while constructing main GUI");
		 	 System.gc();
		 	 System.exit(0);
		 }
	}
	/**
	 * The global button display panel for the application.
	 * Located at the top of the GUI and intended for nyad management.
	 */
    public		JPanel				_ControlBar;
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
    public		JButton			dualLeft;
    public		JButton			dualRight;
    public		JButton			gradePart;
    public		JButton			gradeSuppress;
    public		JButton			invertMonad; //This is NOT multiplicative inverse
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
    
    public		JButton			normalizeMonad;
    public		JButton			reverseMonad;
    public		JButton			scaleMonad;
    public		JButton			whatGrade;
    
    public		JButton			whatMagn;
    public		JButton			whatSQMagn;
    
    //public	JButton			addNyads;
    //public	JButton			lmultNyads;
    //public	JButton			rmultNyads;
    
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
	 */
	public CladosCalculator(	String pTitle,
	   							String pConfig)
	   	throws 	UtilitiesException,
	  			BadSignatureException,
	   			CantGetIniException
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
	    getConfigProps(pConfig);
	    Container cp=getContentPane();
	    	
	    _MenuBar=new ViewerMenu(this);
	    setJMenuBar(_MenuBar);	//The Menu Bar is an element of the parent class JFrame
	    _EventModel=new ViewerEventModel(_MenuBar);
	    	
	    _StatusBar=new UtilityStatusBar();
	    cp.add(_StatusBar, "South");
	    	
	    _ControlBar=new JPanel();
	    _ControlBar.setLayout(new GridBagLayout());
	    _ControlBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	    _ControlBar.setBackground(new Color(255, 255, 220));
	    constructControls();
	    cp.add(_ControlBar,"West");
	    	
	    _GeometryDisplay=new ViewerPanel(this);
	    _GeometryDisplay.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	    cp.add(_GeometryDisplay, "Center");
	    	
	    fc = new JFileChooser();
	    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    	
	    setLocation(100, 10);
	    _StatusBar.setStatusMsg(" ...complete\n");
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
    	
    	if (command.equals("grade suppress"))
    		_MenuBar.mniGradeSuppress.doClick();
    	
    	if (command.equals("grade part"))
    		_MenuBar.mniGradePart.doClick();
    	
    	if (command.equals("magnitude of"))
    		_MenuBar.mniMagnitudeOf.doClick();
    	
    	if (command.equals("sqmagnitude of"))
    		_MenuBar.mniSQMagnitudeOf.doClick();
  
    	if (command.equals("scale"))
    		_MenuBar.mniScale.doClick();
    		//scaleCommand();
    	
    	if (command.equals("normalize"))
    		_MenuBar.mniNormalize.doClick();
    	
    	if (command.equals("invert"))
    		_MenuBar.mniInvert.doClick();
    	
    	if (command.equals("reverse"))
    		_MenuBar.mniReverse.doClick();
    	
    	if (command.equals("dual>"))
    		_MenuBar.mniDualLeft.doClick();
    	
    	if (command.equals("<dual"))
    		_MenuBar.mniDualRight.doClick();
    }
    /**
	 * This method saves snapshot data to the save file.
	 */
	public void saveSnapshot(String pType)	throws 	CantGetSaveException
	{
		//Looks like arrival strings for pType are 'As' or null
		//That means the app uses this method with an assumed switch.
		
	    String SaveName=IniProps.getProperty("MonadViewer.Desktop.Snapshot");
	    if (pType==null) 	// switch setting for 'save' with current snapshot target if known
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
	    else
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
		    		IniProps.setProperty("MonadViewer.Desktop.Snapshot", SaveName);
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

    private void constructControls()
    {
    	Dimension square = new Dimension(50,50);
    	GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);
		
		cn.anchor=GridBagConstraints.NORTH;
		
		cn.gridx = 0;
		cn.gridy = 0;
		cn.weightx=1;
		cn.weighty=1;
		cn.gridheight=2;
		cn.gridwidth=3;
		cn.fill=GridBagConstraints.BOTH;
    	_ControlBar.add(new JLabel(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.HeaderImage2"))),cn);
		cn.gridy++;
		cn.gridy++;
		cn.fill=GridBagConstraints.HORIZONTAL;
		cn.weightx=0;
		cn.weighty=0;
		cn.gridheight=1;
		cn.gridwidth=1;

    	// button triple
    	isRefMatch = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.RefMatchImage")));
    	isRefMatch.setActionCommand("reference match");
    	isRefMatch.setToolTipText("Refernce Match Nyad Test");
    	isRefMatch.setPreferredSize(square);
    	isRefMatch.addActionListener(this);
    	_ControlBar.add(isRefMatch, cn);
    	cn.gridx++;
    	
    	isEqual = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.EqualImage")));
    	isEqual.setActionCommand("equal");
    	isEqual.setToolTipText("Strong Equality Nyad Test");
    	isEqual.setPreferredSize(square);
    	isEqual.addActionListener(this);
    	_ControlBar.add(isEqual, cn);
    	cn.gridx++;

    	isZero = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.ZeroImage")));
    	isZero.setActionCommand("zero");
    	isZero.setToolTipText("Additive Identity (Zero) Monad Test");
    	isZero.setPreferredSize(square);
    	isZero.addActionListener(this);
    	_ControlBar.add(isZero, cn);
    	
    	cn.gridx = 0;
    	cn.gridy++;
    	
    	// button triple
    	isMIdempotent = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.MIdempotentImage")));
    	isMIdempotent.setActionCommand("scaled idempotent");
    	isMIdempotent.setToolTipText("Multiple of Idempotent Monad Test");
    	isMIdempotent.setPreferredSize(square);
    	isMIdempotent.addActionListener(this);
    	_ControlBar.add(isMIdempotent, cn);
    	cn.gridx++;
    	
    	isIdempotent = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.IdempotentImage")));
    	isIdempotent.setActionCommand("idempotent");
    	isIdempotent.setToolTipText("Idempotent Monad Test");
    	isIdempotent.setPreferredSize(square);
    	isIdempotent.addActionListener(this);
    	_ControlBar.add(isIdempotent, cn);
    	cn.gridx++;
    	
    	isNilpotent = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.NilpotentImage")));
    	isNilpotent.setActionCommand("nilpotent");
    	isNilpotent.setToolTipText("Nilpotent Monad Test");
    	isNilpotent.setPreferredSize(square);
    	isNilpotent.addActionListener(this);
    	_ControlBar.add(isNilpotent, cn);
    	
    	cn.gridx = 0;
    	cn.gridy++;
    	//end button triple
    	
    	// button triple
    	isGrade = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.GradeImage")));
    	isGrade.setActionCommand("is grade");
    	isGrade.setToolTipText("Is Grade() Monad Test");
    	isGrade.setPreferredSize(square);
    	isGrade.addActionListener(this);
    	_ControlBar.add(isGrade, cn);
    	cn.gridx++;
    	
    	isMultiGrade = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.MultiGradeImage")));
    	isMultiGrade.setActionCommand("is mgrade");
    	isMultiGrade.setToolTipText("Is MultiGrade Monad Test");
    	isMultiGrade.setPreferredSize(square);
    	isMultiGrade.addActionListener(this);
    	_ControlBar.add(isMultiGrade, cn);
    	cn.gridx++;
    	
    	whatGrade = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.WhatGradeImage")));
    	whatGrade.setActionCommand("is grade!");
    	whatGrade.setToolTipText("What Unique Grade Monad Test");
    	whatGrade.setPreferredSize(square);
    	whatGrade.addActionListener(this);
    	_ControlBar.add(whatGrade, cn);
    	
    	cn.gridx = 0;
    	cn.gridy++;
    	//end button triple
    	
    	// button triple
    	whatMagn = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.MagnitudeImage")));
    	whatMagn.setActionCommand("magnitude of");
    	whatMagn.setToolTipText("Discover Monad Magnitude");
    	whatMagn.setPreferredSize(square);
    	whatMagn.addActionListener(this);
    	_ControlBar.add(whatMagn, cn);
    	cn.gridx++;
    	
    	whatSQMagn = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.SQMagnitudeImage")));
    	whatSQMagn.setActionCommand("sqmagnitude of");
    	whatSQMagn.setToolTipText("Discover Monad Magnitude^2");
    	whatSQMagn.setPreferredSize(square);
    	whatSQMagn.addActionListener(this);
    	_ControlBar.add(whatSQMagn, cn);
    	//cn.gridx++;
   
    	
    	cn.gridx = 0;
    	cn.gridy++;
    	//end button triple
    	
    	invertMonad = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.InvertImage")));
    	invertMonad.setActionCommand("invert");
    	invertMonad.setToolTipText("Invert [+/-] Monad generators");
    	invertMonad.setPreferredSize(square);
    	invertMonad.addActionListener(this);
    	_ControlBar.add(invertMonad, cn);
    	cn.gridx++;
    	
    	reverseMonad = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.ReverseImage")));
    	reverseMonad.setActionCommand("reverse");
    	reverseMonad.setToolTipText("Reverse [ab->ba] Monad blades");
    	reverseMonad.setPreferredSize(square);
    	reverseMonad.addActionListener(this);
    	_ControlBar.add(reverseMonad, cn);
    	cn.gridx++;
    	
    	gradePart = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.GradePartImage")));
    	gradePart.setActionCommand("grade part");
    	gradePart.setToolTipText("Zero OTHER grades()");
    	gradePart.setPreferredSize(square);
    	gradePart.addActionListener(this);
    	_ControlBar.add(gradePart, cn);
    	
    	cn.gridx=0;
    	cn.gridy++;
    	
    	scaleMonad = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.ScaleImage")));
    	scaleMonad.setActionCommand("scale");
    	scaleMonad.setToolTipText("Scale() THIS Monad");
    	scaleMonad.setPreferredSize(square);
    	scaleMonad.addActionListener(this);
    	_ControlBar.add(scaleMonad, cn);
    	cn.gridx++;
    	
    	normalizeMonad = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.NormImage")));
    	normalizeMonad.setActionCommand("normalize");
    	normalizeMonad.setToolTipText("Normalize THIS Monad");
    	normalizeMonad.setPreferredSize(square);
    	normalizeMonad.addActionListener(this);
    	_ControlBar.add(normalizeMonad, cn);
    	cn.gridx++;
    	
    	gradeSuppress = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.GradeSuppressImage")));
    	gradeSuppress.setActionCommand("grade suppress");
    	gradeSuppress.setToolTipText("Zero THIS grade()");
    	gradeSuppress.setPreferredSize(square);
    	gradeSuppress.addActionListener(this);
    	_ControlBar.add(gradeSuppress, cn);
    	cn.gridx=0;
    	cn.gridy++;
    	
    	dualLeft = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.DualLeftImage")));
    	dualLeft.setActionCommand("dual>");
    	dualLeft.setToolTipText("Left Dual of THIS Monad using algebra's PS");
    	dualLeft.setPreferredSize(square);
    	dualLeft.addActionListener(this);
    	_ControlBar.add(dualLeft, cn);
    	cn.gridx++;
    	
    	dualRight = new JButton(new ImageIcon(IniProps.getProperty("MonadViewer.Desktop.DualRightImage")));
    	dualRight.setActionCommand("<dual");
    	dualRight.setToolTipText("Right Dual of THIS Monad using algebra's PS");
    	dualRight.setPreferredSize(square);
    	dualRight.addActionListener(this);
    	_ControlBar.add(dualRight, cn);	
    }
    
    private String makeSnapshotContent()
	{
		StringBuffer content=new StringBuffer();
	
		content.append("<Application Name=\"clados Calculator\", ");
		content.append("Rights=\"Copyright 2018 Alfred Differ\", ");
		content.append("Licensee=\"");
		content.append(IniProps.getProperty("MonadViewer.User.Name"));
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
	 */
	protected void getSaveFile(String pSaveName)
	    throws 	IOException, CantGetSaveException
	{
	    String SaveName=null;
	    if (pSaveName==null)
	    	SaveName=IniProps.getProperty("MonadViewer.Desktop.Snapshot");
	    else
	    {
	    	SaveName=pSaveName;
	    	IniProps.setProperty("MonadViewer.Desktop.Snapshot", pSaveName);
	    }
	
	    File fSave=new File(SaveName);
	    if (!(fSave.exists() & fSave.isFile() & fSave.canWrite()))
	    {
	    	System.out.println("MonadViewer.Desktop.Snapshot should be set to somewhere in the conf file.");
	        throw new CantGetSaveException("No access to snapshot save file.");
	    }
	    // Getting here with no exceptions is the objective. 
	    // Success implies we have a valid snapshot target
	    // but there is no need to keep a FileWriter open to it.
	}
} 
