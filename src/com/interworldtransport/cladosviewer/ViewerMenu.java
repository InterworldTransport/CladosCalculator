/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.ViewerMenu<br>
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
 * ---com.interworldtransport.cladosviewer.ViewerMenu<br>
 * ------------------------------------------------------------------------ <br>
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
 * @version 0.85
 * @author Dr Alfred W Differ
 */

public class ViewerMenu extends JMenuBar
{
	private static final long serialVersionUID = 7342710714923838976L;

	public 	CladosCalculator		_parentGUI;

/**
 * The File Parent Menu for the application.
 */
    public	JMenu			mnuFile;
    //public	JMenuItem	mniNew;
    //public	JMenuItem	mniOpen;
    public	JMenuItem		mniSave;
    public	JMenuItem		mniSaveAs;
    //public	JMenuItem	mniPrint;
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
    public	JMenuItem		mniSymm;
    public	JMenuItem		mniASymm;

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
    public	ViewerMenu(CladosCalculator pParentGUI)
    {
    	super();
    	_parentGUI=pParentGUI;
    	
    	mnuFile=new JMenu("File");
    	add(mnuFile);
    	mnuBOperations=new JMenu("Booleans");
    	add(mnuBOperations);
    	mnuSOperations=new JMenu("Unary");
    	add(mnuSOperations);
    	mnuCOperations=new JMenu("Binary");
    	add(mnuCOperations);
    	mnuTools=new JMenu("Tools");
    	add(mnuTools);
    	mnuHelp=new JMenu("Help");
    	add(mnuHelp);  //Primary Menus are added
    	
    	mniSave=new JMenuItem("Save");
    	mnuFile.add(mniSave);
    	mniSaveAs=new JMenuItem("Save As");
    	mnuFile.add(mniSaveAs);
    	mniExit=new JMenuItem("Exit");
    	mnuFile.add(mniExit);
    	
    	mniisZero=new JMenuItem("is Zero?");
    	mnuBOperations.add(mniisZero);
    	mniisEqual=new JMenuItem("is Equal?");
    	mnuBOperations.add(mniisEqual);
    	mniisReferenceMatch=new JMenuItem("is Reference Match?");
    	mnuBOperations.add(mniisReferenceMatch);
    	mniisGrade=new JMenuItem("is Grade?");
    	mnuBOperations.add(mniisGrade);
    	mniisMultiGrade=new JMenuItem("is Multigrade?");
    	mnuBOperations.add(mniisMultiGrade);
    	mniisNilpotent=new JMenuItem("is Nilpotent?");
    	mnuBOperations.add(mniisNilpotent);
    	mniisIdempotent=new JMenuItem("is Idempotent?");
    	mnuBOperations.add(mniisIdempotent);
    	mniisIdempotentMultiple=new JMenuItem("is Idempotent Multiple?");
    	mnuBOperations.add(mniisIdempotentMultiple);
    	
    	
    	mniisSGrade=new JMenuItem("what Grade?");
    	mnuSOperations.add(mniisSGrade);
    	mniMagnitudeOf=new JMenuItem("what Magnitude?");
    	mnuSOperations.add(mniMagnitudeOf);
    	mniSQMagnitudeOf=new JMenuItem("what SQ Magnitude?");
    	mnuSOperations.add(mniSQMagnitudeOf);
    	mniNormalize=new JMenuItem("Normalize");
    	mnuSOperations.add(mniNormalize);
    	mniLocalDual=new JMenuItem("Left Dual");
    	mnuSOperations.add(mniLocalDual);
    	//mniInverse=new JMenuItem("Inverse");
    	//mnuSOperations.add(mniInverse);
    	mniInvert=new JMenuItem("Invert");
    	mnuSOperations.add(mniInvert);
    	mniReverse=new JMenuItem("Reverse");
    	mnuSOperations.add(mniReverse);
    	mniScale=new JMenuItem("reScale");
    	mnuSOperations.add(mniScale);
    	mniGradePart=new JMenuItem("preserve Grade?");
    	mnuSOperations.add(mniGradePart);
    	mniGradeSupress=new JMenuItem("supress Grade?");
    	mnuSOperations.add(mniGradeSupress);
    	
    	mniAdd=new JMenuItem("Add");
    	mnuCOperations.add(mniAdd);
    	mniSubtract=new JMenuItem("Subtract");
    	mnuCOperations.add(mniSubtract);
    	mniLeftMultiply=new JMenuItem("Left Multiply");
    	mnuCOperations.add(mniLeftMultiply);
    	mniRightMultiply=new JMenuItem("Right Multiply");
    	mnuCOperations.add(mniRightMultiply);
    	mniSymm=new JMenuItem("Symm Multiply");
    	mnuCOperations.add(mniSymm);
    	mniASymm=new JMenuItem("ASymm Multiply");
    	mnuCOperations.add(mniASymm);
    	//mniRotate=new JMenuItem("Rotate");
    	//mnuCOperations.add(mniRotate);
    	//mniRotate.setEnabled(false);
    	//mniTranslate=new JMenuItem("Translate");
    	//mnuCOperations.add(mniTranslate);
    	//mniTranslate.setEnabled(false);
    	//Operations Menus items added
    	
    	mniCreate=new JMenuItem("Create Monad");
    	mnuTools.add(mniCreate);
    	//mniCreate.setEnabled(false);
    	mniOptions=new JMenuItem("Options");
    	mnuTools.add(mniOptions);
    	//Options Menu items added
    	
    	mniSupport=new JMenuItem("Support");
    	mnuHelp.add(mniSupport);
    	mniAbout=new JMenuItem("About");
    	mnuHelp.add(mniAbout);
    	//Help Menu items added
    }
    
}
