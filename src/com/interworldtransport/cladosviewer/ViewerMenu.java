/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.ViewerMenu------------------------------------
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
Use of this code or executable objects derived from it by the Licensee states
their willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
---com.interworldtransport.cladosviewer.ViewerMenu------------------------------------
 */

package com.interworldtransport.cladosviewer;
import javax.swing.*;


/** com.interworldtransport.cladosviewer.ViewerMenu
 * The ViewerMenu class is intended to be the class that encapsulates the menu
 * used in the Viewer class.
 * <p>
 * There is nothing especially important about the layout of this class.  It
 * should not be instantiated except by the MonadViewer application.  Alterations
 * to this class should be made in careful coordination with the classes of the
 * event model.
 * @version 0.80. $Date: 2005/07/31 05:00:25 $
 * @author Dr Alfred W Differ
 */

public class ViewerMenu extends JMenuBar
{

	public 	MonadViewer		ParentGUI;

/**
 * The File Parent Menu for the application.
 */
    public	JMenu			mnuFile;
    //public	JMenuItem		mniNew;
    //public	JMenuItem		mniOpen;
    public	JMenuItem		mniSave;
    public	JMenuItem		mniSaveAs;
    //public	JMenuItem		mniPrint;
    public	JMenuItem		mniExit;

/**
 * The Edit Parent Menu for the application.
 */
    //public	JMenu			mnuEdit;
    //public	JMenuItem		mniCopy;
    //public	JMenuItem		mniPaste;

/**
 * The Boolean Operations Parent Menu for the application.
 */
    public	JMenu			mnuBOperations;
    public	JMenuItem		mniisMultiGrade;
    public	JMenuItem		mniisGrade;
    public	JMenuItem		mniisNilpotent;
    public	JMenuItem		mniisIdempotent;
    public	JMenuItem		mniisIdempotentMultiple;
    public	JMenuItem		mniisEqual;
    public	JMenuItem		mniisZero;
    public	JMenuItem		mniisReferenceMatch;

/**
 * The Simple Operations Parent Menu for the application.
 */
    public	JMenu			mnuSOperations;
    public	JMenuItem		mniisSGrade;
    public	JMenuItem		mniMagnitudeOf;
    public	JMenuItem		mniSQMagnitudeOf;
    public	JMenuItem		mniNormalize;
    public	JMenuItem		mniLocalDual;
    public	JMenuItem		mniInverse;
    public	JMenuItem		mniInvert;
    public	JMenuItem		mniReverse;
    public	JMenuItem		mniScale;
    public	JMenuItem		mniGradePart;
    public	JMenuItem		mniGradeSupress;

/**
 * The Complex Operations Parent Menu for the application.
 */
    public	JMenu			mnuCOperations;
    public	JMenuItem		mniAdd;
    public	JMenuItem		mniSubtract;
    public	JMenuItem		mniLeftMultiply;
    public	JMenuItem		mniRightMultiply;
    public	JMenuItem		mniDot;
    public	JMenuItem		mniWedge;
    public	JMenuItem		mniRotate;
    public	JMenuItem		mniTranslate;

/**
 * The Tools Parent Menu for the application.
 */
    public	JMenu			mnuTools;
    public	JMenuItem		mniOptions;
    public	JMenuItem		mniCreate;

/**
 * The Help Parent Menu for the application.
 */
    public	JMenu			mnuHelp;
    public	JMenuItem		mniSupport;
    public	JMenuItem		mniAbout;

/**
 * The ViewerMenu class is intended to be the class that encapsulates the menu
 * used in the MonadViewer Utility.  For the sake of maintenance
 * this definition is in a separate class and file.
 */
    public	ViewerMenu(MonadViewer pParentGUI)
    {
    	super();
    	this.ParentGUI=pParentGUI;
    	
    	this.mnuFile=new JMenu("File");
    	this.add(this.mnuFile);
    	this.mnuBOperations=new JMenu("Booleans");
    	this.add(this.mnuBOperations);
    	this.mnuSOperations=new JMenu("Simple");
    	this.add(this.mnuSOperations);
    	this.mnuCOperations=new JMenu("Complex");
    	this.add(this.mnuCOperations);
    	this.mnuTools=new JMenu("Tools");
    	this.add(this.mnuTools);
    	this.mnuHelp=new JMenu("Help");
    	this.add(this.mnuHelp);  //Primary Menus are added
    	
    	this.mniSave=new JMenuItem("Save");
    	this.mnuFile.add(this.mniSave);
    	this.mniSaveAs=new JMenuItem("Save As");
    	this.mnuFile.add(this.mniSaveAs);
    	this.mniExit=new JMenuItem("Exit");
    	this.mnuFile.add(this.mniExit);
    	
    	this.mniisZero=new JMenuItem("is Zero?");
    	this.mnuBOperations.add(this.mniisZero);
    	this.mniisEqual=new JMenuItem("is Equal?");
    	this.mnuBOperations.add(this.mniisEqual);
    	this.mniisReferenceMatch=new JMenuItem("is Reference Match?");
    	this.mnuBOperations.add(this.mniisReferenceMatch);
    	this.mniisGrade=new JMenuItem("is Grade?");
    	this.mnuBOperations.add(this.mniisGrade);
    	this.mniisMultiGrade=new JMenuItem("is Multigrade?");
    	this.mnuBOperations.add(this.mniisMultiGrade);
    	this.mniisNilpotent=new JMenuItem("is Nilpotent?");
    	this.mnuBOperations.add(this.mniisNilpotent);
    	this.mniisIdempotent=new JMenuItem("is Idempotent?");
    	this.mnuBOperations.add(this.mniisIdempotent);
    	this.mniisIdempotentMultiple=new JMenuItem("is Idempotent Multiple?");
    	this.mnuBOperations.add(this.mniisIdempotentMultiple);
    	
    	
    	this.mniisSGrade=new JMenuItem("what Grade?");
    	this.mnuSOperations.add(this.mniisSGrade);
    	this.mniMagnitudeOf=new JMenuItem("what Magnitude?");
    	this.mnuSOperations.add(this.mniMagnitudeOf);
    	this.mniSQMagnitudeOf=new JMenuItem("what SQ Magnitude?");
    	this.mnuSOperations.add(this.mniSQMagnitudeOf);
    	this.mniNormalize=new JMenuItem("Normalize");
    	this.mnuSOperations.add(this.mniNormalize);
    	this.mniLocalDual=new JMenuItem("Local Dual");
    	this.mnuSOperations.add(this.mniLocalDual);
    	this.mniInverse=new JMenuItem("Inverse");
    	this.mnuSOperations.add(this.mniInverse);
    	this.mniInvert=new JMenuItem("Invert");
    	this.mnuSOperations.add(this.mniInvert);
    	this.mniReverse=new JMenuItem("Reverse");
    	this.mnuSOperations.add(this.mniReverse);
    	this.mniScale=new JMenuItem("reScale");
    	this.mnuSOperations.add(this.mniScale);
    	this.mniGradePart=new JMenuItem("preserve Grade?");
    	this.mnuSOperations.add(this.mniGradePart);
    	this.mniGradeSupress=new JMenuItem("supress Grade?");
    	this.mnuSOperations.add(this.mniGradeSupress);
    	
    	this.mniAdd=new JMenuItem("Add");
    	this.mnuCOperations.add(mniAdd);
    	this.mniSubtract=new JMenuItem("Subtract");
    	this.mnuCOperations.add(mniSubtract);
    	this.mniLeftMultiply=new JMenuItem("Left Multiply");
    	this.mnuCOperations.add(mniLeftMultiply);
    	this.mniRightMultiply=new JMenuItem("Right Multiply");
    	this.mnuCOperations.add(mniRightMultiply);
    	this.mniDot=new JMenuItem("Dot");
    	this.mnuCOperations.add(mniDot);
    	this.mniWedge=new JMenuItem("Wedge");
    	this.mnuCOperations.add(mniWedge);
    	this.mniRotate=new JMenuItem("Rotate");
    	this.mnuCOperations.add(mniRotate);
    	this.mniRotate.setEnabled(false);
    	this.mniTranslate=new JMenuItem("Translate");
    	this.mnuCOperations.add(mniTranslate);
    	this.mniTranslate.setEnabled(false);
    	//Operations Menus items added
    	
    	this.mniCreate=new JMenuItem("Create Monad");
    	this.mnuTools.add(this.mniCreate);
    	//this.mniCreate.setEnabled(false);
    	this.mniOptions=new JMenuItem("Options");
    	this.mnuTools.add(this.mniOptions);
    	//Options Menu items added
    	
    	this.mniSupport=new JMenuItem("Support");
    	this.mnuHelp.add(this.mniSupport);
    	this.mniAbout=new JMenuItem("About");
    	this.mnuHelp.add(this.mniAbout);
    	//Help Menu items added
    }
    
}
