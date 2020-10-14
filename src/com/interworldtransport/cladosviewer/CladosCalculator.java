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

import com.interworldtransport.cladosF.CladosFBuilder;

import com.interworldtransport.cladosG.MonadComplexD;
import com.interworldtransport.cladosG.MonadComplexF;
import com.interworldtransport.cladosG.MonadRealD;
import com.interworldtransport.cladosG.MonadRealF;

import com.interworldtransport.cladosG.NyadRealF;
import com.interworldtransport.cladosG.NyadRealD;
import com.interworldtransport.cladosG.NyadComplexF;
import com.interworldtransport.cladosG.NyadComplexD;

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
	private static final long 		serialVersionUID = -6389087013344440745L;
	private	static final Color		_backColor = new Color(255, 255, 222);
	
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
	 * The EventModel for the application.
	 */
    public		ViewerEventModel	_EventModel;
	/**
	 * The Field Display Panel for the application.
	 * Located at the top of the GUI and intended for numeric inputs.
	 */
	public		FieldPanel			_FieldBar; 
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
	private		ViewerMenu			_MenuBar;
	private		JButton				btnHasGrade;
	private		JButton				btnHasNyadAlgebra;
	private		JButton				btnIsGrade;
	private		JButton				btnIsIdempotent;
	private		JButton				btnIsMultiGrade;
	private		JButton				btnIsNilpotent;
	private		JButton				btnIsNyadEqual;
	private		JButton				btnIsNyadPScalarAt;
	private		JButton				btnIsNyadScalarAt;
	private		JButton				btnIsNyadStrgRefMatch;
	private		JButton				btnIsNyadWeakRefMatch;
	private		JButton				btnIsScaleIdempotent;
	private		JButton				btnIsZero;
	private		JButton				btnWhatGrade;
	private		JButton				btnWhatMagn;
	private		JButton				btnWhatSQMagn;
    private		JFileChooser 		fc;
    private		JPanel				pnlControlBar; // global button display for easy menu access
    private		FileWriter			saveItTo;
	/*
     * This is the properties object containing key/value pairs from the config file
     * and any system settings that might be useful.
     */
    protected	Properties			IniProps;

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
	    setJMenuBar(_MenuBar);						//The Menu Bar is an element of the parent class JFrame
	    _EventModel=new ViewerEventModel(_MenuBar);	//EventModel relies on existance of _MenuBar
	    	
	    _StatusBar=new UtilityStatusBar(this);		//Next up because errors have to be reported somewhere.
	    cp.add(_StatusBar, "South");
	    	

	    	
	    _GeometryDisplay=new ViewerPanel(this);		//ViewerPanel next to display stuff from nyads and monads
	    _GeometryDisplay.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	    cp.add(_GeometryDisplay, "Center");
	    											//FieldBar MUST follow ViewerPanel to make use of protonumbers
	    int indxNPanelSelected = _GeometryDisplay.getPaneFocus();
    	if (indxNPanelSelected>=0) 
    	{
    		NyadPanel tSpot = _GeometryDisplay.getNyadPanel(indxNPanelSelected);
    		switch (_GeometryDisplay.getNyadPanel(indxNPanelSelected).getRepMode())
    		{
    			case REALF:	_FieldBar=new FieldPanel(this, tSpot.getNyadRF().getProto());
    						cp.add(_FieldBar, "North");
    						break;
    			case REALD:	_FieldBar=new FieldPanel(this, tSpot.getNyadRD().getProto());
							cp.add(_FieldBar, "North");
							break;
    			case COMPLEXF:	_FieldBar=new FieldPanel(this, tSpot.getNyadCF().getProto());
								cp.add(_FieldBar, "North");
								break;
				case COMPLEXD:	_FieldBar=new FieldPanel(this, tSpot.getNyadCD().getProto());
								cp.add(_FieldBar, "North");
    		}
    		tSpot = null;
    	}
    	else	// This catches the possibility that no NyadPanel was created upon initialization
    	{
    		_FieldBar=new FieldPanel(this, CladosFBuilder.createRealFZERO("PlaceHolder"));
			cp.add(_FieldBar, "North");
    	}
	    
	    pnlControlBar=new JPanel();
	    pnlControlBar.setLayout(new GridBagLayout());
	    pnlControlBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	    pnlControlBar.setBackground(_backColor);
	    createTestControls();
	    cp.add(pnlControlBar,"West");
	    	
	    fc = new JFileChooser();
	    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation(dim.width/4-this.getSize().width/4, dim.height/4-this.getSize().height/4);
	}
    
    public void actionPerformed(ActionEvent event)
    {
    	switch (event.getActionCommand())
    	{
	    	case "strong ref match":	_MenuBar.mniisStrgReferenceMatch.doClick();
	    								break;
	    	case "weak ref match":		_MenuBar.mniisWeakReferenceMatch.doClick();
	    								break;
	    	case "algebra detect":		_MenuBar.mnihasAlgebra.doClick();
	    								break;
	    	case "equal":				_MenuBar.mniisEqual.doClick();
	    								break;
	    	case "scalar at":			_MenuBar.mniisScalarAt.doClick();
	    								break;
	    	case "pscalar at":			_MenuBar.mniisPScalarAt.doClick();
	    								break;
	    	case "zero":				_MenuBar.mniisZero.doClick();
										break;
	    	case "nilpotent":   		_MenuBar.mniisNilpotent.doClick();
	    								break;
	    	case "idempotent":   		_MenuBar.mniisIdempotent.doClick();
	    								break;
	    	case "scaled idempotent": 	_MenuBar.mniisScaledIdempotent.doClick();
										break;
    		case "is findgrade":		_MenuBar.mniisGrade.doClick();
										break;
    		case "is mgrade":    		_MenuBar.mniisMultiGrade.doClick();
    									break;
    		case "is findgrade!":   	_MenuBar.mniisSGrade.doClick();
        								break;
    		case "has findgrade":    	_MenuBar.mnihasGrade.doClick();
    									break;
    		case "magnitude of":    	_MenuBar.mniMagnitudeOf.doClick();
    									break;
    		case "sqmagnitude of":    	_MenuBar.mniSQMagnitudeOf.doClick();
    									break;
    		default: 					_StatusBar.setStatusMsg("No detectable command processed.\n");
    	}
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
		    		_StatusBar.setStatusMsg("Snapshot of stack is saved.\n");
		    		
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
		cn.weightx=1;
		cn.weighty=1;
		cn.gridheight=2;
		cn.gridwidth=2;
		
		cn.fill=GridBagConstraints.BOTH;
    	pnlControlBar.add(new JLabel(new ImageIcon(IniProps.getProperty("Desktop.Image.Header2"))),cn);
    	cn.gridx = 0;
    	cn.gridy += 2;
    	
    	cn.weightx=0;
		cn.weighty=0;
		cn.gridheight=1;
		cn.gridwidth=1;

    	// button double
    	btnIsNyadStrgRefMatch = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.RefMatch")));
    	btnIsNyadStrgRefMatch.setActionCommand("strong ref match");
    	btnIsNyadStrgRefMatch.setToolTipText("Strong Reference Match [Nyad]?");
    	btnIsNyadStrgRefMatch.setPreferredSize(square);
    	btnIsNyadStrgRefMatch.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnIsNyadStrgRefMatch.addActionListener(this);
    	pnlControlBar.add(btnIsNyadStrgRefMatch, cn);
    	cn.gridx++;
    	
    	btnIsNyadWeakRefMatch = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.WeakRefMatch")));
    	btnIsNyadWeakRefMatch.setActionCommand("weak ref match");
    	btnIsNyadWeakRefMatch.setToolTipText("Weak reference Match [Nyad]?");
    	btnIsNyadWeakRefMatch.setPreferredSize(square);
    	btnIsNyadWeakRefMatch.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnIsNyadWeakRefMatch.addActionListener(this);
    	pnlControlBar.add(btnIsNyadWeakRefMatch, cn);
    	cn.gridx = 0;
    	cn.gridy++;
    	
    	btnHasNyadAlgebra = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.HasAlgebra")));
    	btnHasNyadAlgebra.setActionCommand("algebra detect");
    	btnHasNyadAlgebra.setToolTipText("Next Nyad Has Algebra?");
    	btnHasNyadAlgebra.setPreferredSize(square);
    	btnHasNyadAlgebra.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnHasNyadAlgebra.addActionListener(this);
    	pnlControlBar.add(btnHasNyadAlgebra, cn);
    	cn.gridx++;
    	
    	btnIsNyadEqual = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Equal")));
    	btnIsNyadEqual.setActionCommand("equal");
    	btnIsNyadEqual.setToolTipText("strong Equality Nyad Test");
    	btnIsNyadEqual.setPreferredSize(square);
    	btnIsNyadEqual.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnIsNyadEqual.addActionListener(this);
    	pnlControlBar.add(btnIsNyadEqual, cn);
    	cn.gridx = 0;
    	cn.gridy++;
    	
    	btnIsNyadScalarAt = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.IsScalarAt")));
    	btnIsNyadScalarAt.setActionCommand("scalar at");
    	btnIsNyadScalarAt.setToolTipText("Next Nyad Has Scalar At?");
    	btnIsNyadScalarAt.setPreferredSize(square);
    	btnIsNyadScalarAt.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnIsNyadScalarAt.addActionListener(this);
    	pnlControlBar.add(btnIsNyadScalarAt, cn);
    	cn.gridx++;
    	
    	btnIsNyadPScalarAt = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.IsPScalarAt")));
    	btnIsNyadPScalarAt.setActionCommand("pscalar at");
    	btnIsNyadPScalarAt.setToolTipText("Next Nyad Has PScalar At?");
    	btnIsNyadPScalarAt.setPreferredSize(square);
    	btnIsNyadPScalarAt.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnIsNyadPScalarAt.addActionListener(this);
    	pnlControlBar.add(btnIsNyadPScalarAt, cn);
    	cn.gridx = 0;
    	cn.gridy++;

    	// button double
    	btnIsZero = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Zero")));
    	btnIsZero.setActionCommand("zero");
    	btnIsZero.setToolTipText("additive Identity (Zero) Monad Test");
    	btnIsZero.setPreferredSize(square);
    	btnIsZero.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnIsZero.addActionListener(this);
    	pnlControlBar.add(btnIsZero, cn);
    	cn.gridx++;
    	
    	btnIsNilpotent = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Nilpotent")));
    	btnIsNilpotent.setActionCommand("nilpotent");
    	btnIsNilpotent.setToolTipText("nilpotent Monad Test");
    	btnIsNilpotent.setPreferredSize(square);
    	btnIsNilpotent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnIsNilpotent.addActionListener(this);
    	pnlControlBar.add(btnIsNilpotent, cn);
    	cn.gridx = 0;
    	cn.gridy++;
    	
    	// button double
    	btnIsIdempotent = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Idempotent")));
    	btnIsIdempotent.setActionCommand("idempotent");
    	btnIsIdempotent.setToolTipText("idempotent Monad Test");
    	btnIsIdempotent.setPreferredSize(square);
    	btnIsIdempotent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnIsIdempotent.addActionListener(this);
    	pnlControlBar.add(btnIsIdempotent, cn);  
    	cn.gridx++;
    	
    	btnIsScaleIdempotent = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.MIdempotent")));
    	btnIsScaleIdempotent.setActionCommand("scaled idempotent");
    	btnIsScaleIdempotent.setToolTipText("multiple of Idempotent Monad Test");
    	btnIsScaleIdempotent.setPreferredSize(square);
    	btnIsScaleIdempotent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnIsScaleIdempotent.addActionListener(this);
    	pnlControlBar.add(btnIsScaleIdempotent, cn);
    	cn.gridx = 0;
    	cn.gridy++;
    	   	
    	// button double
    	btnWhatMagn = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Magnitude")));
    	btnWhatMagn.setActionCommand("magnitude of");
    	btnWhatMagn.setToolTipText("discover Monad Magnitude");
    	btnWhatMagn.setPreferredSize(square);
    	btnWhatMagn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnWhatMagn.addActionListener(this);
    	pnlControlBar.add(btnWhatMagn, cn);
    	cn.gridx++;
    	
    	btnWhatSQMagn = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.SQMagnitude")));
    	btnWhatSQMagn.setActionCommand("sqmagnitude of");
    	btnWhatSQMagn.setToolTipText("discover Monad Magnitude^2");
    	btnWhatSQMagn.setPreferredSize(square);
    	btnWhatSQMagn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnWhatSQMagn.addActionListener(this);
    	pnlControlBar.add(btnWhatSQMagn, cn);
    	cn.gridx = 0;
    	cn.gridy++;

    	// button double
    	btnIsGrade = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.Grade")));
    	btnIsGrade.setActionCommand("is findgrade");
    	btnIsGrade.setToolTipText("is Grade() Monad Test");
    	btnIsGrade.setPreferredSize(square);
    	btnIsGrade.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnIsGrade.addActionListener(this);
    	pnlControlBar.add(btnIsGrade, cn);
    	cn.gridx++;
    	
    	btnIsMultiGrade = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.MultiGrade")));
    	btnIsMultiGrade.setActionCommand("is mgrade");
    	btnIsMultiGrade.setToolTipText("is MultiGrade Monad Test");
    	btnIsMultiGrade.setPreferredSize(square);
    	btnIsMultiGrade.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnIsMultiGrade.addActionListener(this);
    	pnlControlBar.add(btnIsMultiGrade, cn);
       	cn.gridx = 0;
    	cn.gridy++;
    	
    	btnHasGrade = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.HasGrade")));
    	btnHasGrade.setActionCommand("has findgrade");
    	btnHasGrade.setToolTipText("Has Grade Monad Test");
    	btnHasGrade.setPreferredSize(square);
    	btnHasGrade.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnHasGrade.addActionListener(this);
    	pnlControlBar.add(btnHasGrade, cn);
    	cn.gridx++;
    	
    	btnWhatGrade = new JButton(new ImageIcon(IniProps.getProperty("Desktop.Image.WhatGrade")));
    	btnWhatGrade.setActionCommand("is findgrade!");
    	btnWhatGrade.setToolTipText("what Unique Grade Monad Test");
    	btnWhatGrade.setPreferredSize(square);
    	btnWhatGrade.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    	btnWhatGrade.addActionListener(this);
    	pnlControlBar.add(btnWhatGrade, cn);
    	
    	

    	
    }
    
    private String makeSnapshotContent()
	{
    	if (_GeometryDisplay.getNyadListSize() == 0) return "Nothing in panels to save.";
    	
    	// TODO There should be two versions of this. One gives full XML strings. 
    	// The second does the light weight version.
    	
		StringBuffer content=new StringBuffer("<Application Name=\"Clados Calculator\", ");
		content.append("Licensee=\""+IniProps.getProperty("User.Name")+"\" />\r\n");
		content.append("<NyadList size=\""+_GeometryDisplay.getNyadListSize()+"\">\r\n");

		for (NyadPanel tempNPN : _GeometryDisplay.getNyadPanels())
		{
			switch(tempNPN.getRepMode())
			{
				case REALF:	NyadRealF tempNF=tempNPN.getNyadRF();
							content.append("<Nyad Name=\""+tempNF.getName()+"\", ");
							content.append("Order=\""+tempNF.getNyadOrder()+"\", ");
							content.append("Foot=\""+tempNF.getFootPoint().getFootName()+"\">\r\n");
							content.append("<MonadList>\r\n");
							for (int m=0; m<tempNF.getNyadOrder(); m++)
								content.append(MonadRealF.toXMLFullString(tempNF.getMonadList(m)));
							break;
				case REALD:	NyadRealD tempND=tempNPN.getNyadRD();
							content.append("<Nyad Name=\""+tempND.getName()+"\", ");
							content.append("Order=\""+tempND.getNyadOrder()+"\", ");
							content.append("Foot=\""+tempND.getFootPoint().getFootName()+"\">\r\n");
							content.append("<MonadList>\r\n");
							for (int m=0; m<tempND.getNyadOrder(); m++)
								content.append(MonadRealD.toXMLFullString(tempND.getMonadList(m)));
								break;
				case COMPLEXF:	NyadComplexF tempNCF=tempNPN.getNyadCF();
								content.append("<Nyad Name=\""+tempNCF.getName()+"\", ");
								content.append("Order=\""+tempNCF.getNyadOrder()+"\", ");
								content.append("Foot=\""+tempNCF.getFootPoint().getFootName()+"\">\r\n");
								content.append("<MonadList>\r\n");
								for (int m=0; m<tempNCF.getNyadOrder(); m++)
									content.append(MonadComplexF.toXMLFullString(tempNCF.getMonadList(m)));
								break;
				case COMPLEXD:	NyadComplexD tempNCD=tempNPN.getNyadCD();
								content.append("<Nyad Name=\""+tempNCD.getName()+"\", ");
								content.append("Order=\""+tempNCD.getNyadOrder()+"\", ");
								content.append("Foot=\""+tempNCD.getFootPoint().getFootName()+"\">\r\n");
								content.append("<MonadList>\r\n");
								for (int m=0; m<tempNCD.getNyadOrder(); m++)
									content.append(MonadComplexD.toXMLFullString(tempNCD.getMonadList(m)));
			}
			content.append("</MonadList>\r\n");
			content.append("</Nyad>\r\n");
		}
		content.append("</NyadList>\r\n");
		return content.toString();
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
