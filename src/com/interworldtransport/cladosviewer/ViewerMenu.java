/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
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
    //public	JMenuItem	mniOpen;
    public	JMenuItem		mniSave;
    public	JMenuItem		mniSaveAs;
    public	JMenuItem		mniExit;

/**
 * The Nyad Operations Parent Menu for the application.
 */
    public	JMenu			mnuNyadOps;
    public	JMenu			mnuNyadBooleanOps;
    public	JMenuItem		mniisEqual;
    public	JMenuItem		mniisZero;
    public	JMenuItem		mniisStrgReferenceMatch;
    public	JMenuItem		mniisWeakReferenceMatch;
    
    public	JMenu			mnuOneInputNyadOps;
    public	JMenuItem		mnihasAlgebra;
    
    public	JMenu			mnuTwoInputNyadOps;
    public	JMenuItem		mniAdd;
    public	JMenuItem		mniSubtract;
    public	JMenuItem		mniLeftMultiply;
    public	JMenuItem		mniRightMultiply;
    public	JMenuItem		mniSymm;
    public	JMenuItem		mniASymm;
    
/**
 * The Monad Operations Parent Menu for the application.
 */
    public	JMenu			mnuMonadOps;
    public	JMenu			mnuMonadBooleanOps;
    public	JMenuItem		mniisMultiGrade;
    public	JMenuItem		mniisGrade;
    public	JMenuItem		mniisNilpotent;
    public	JMenuItem		mniisIdempotent;
    public	JMenuItem		mniisScaledIdempotent;
    
    public	JMenu			mnuOneInputMonadOps;
    public	JMenuItem		mniisSGrade;
    public	JMenuItem		mnihasGrade;
    public	JMenuItem		mniMagnitudeOf;
    public	JMenuItem		mniSQMagnitudeOf;
    public	JMenuItem		mniNormalize;
    public	JMenuItem		mniDualLeft;
    public	JMenuItem		mniDualRight;
    public	JMenuItem		mniInvert;
    public	JMenuItem		mniReverse;
    public	JMenuItem		mniScale;
    public	JMenuItem		mniGradeCrop;
    public	JMenuItem		mniGradeCut;
    
    public	JMenu			mnuTwoInputMonadOps;

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
 * @param pParentGUI
 *		CladosCalculator
 * 	This parameter references the owning application. Nothing spectacular.
 */
    public	ViewerMenu(CladosCalculator pParentGUI)
    {
    	super();
    	_parentGUI=pParentGUI;
    	
    	mnuFile=new JMenu("File");
    	add(mnuFile);
	    	mniSave=new JMenuItem("Save");
	    	mnuFile.add(mniSave);
	    	mniSaveAs=new JMenuItem("Save As");
	    	mnuFile.add(mniSaveAs);
	    	mniExit=new JMenuItem("Exit");
	    	mnuFile.add(mniExit);
	    	
    	mnuNyadOps=new JMenu("Nyad Ops");
    	add(mnuNyadOps);
    	
	    	mnuNyadBooleanOps=new JMenu("Boolean");
	    	mnuNyadOps.add(mnuNyadBooleanOps);
	    	   	mniisStrgReferenceMatch=new JMenuItem("is Strong Reference Match?");
		    	mnuNyadBooleanOps.add(mniisStrgReferenceMatch);
		    	mniisWeakReferenceMatch=new JMenuItem("is Weak Reference Match?");
		    	mnuNyadBooleanOps.add(mniisWeakReferenceMatch);
		    	mniisZero=new JMenuItem("is Zero?");
		    	mnuNyadBooleanOps.add(mniisZero);
		    	mniisEqual=new JMenuItem("is Equal?");
		    	mnuNyadBooleanOps.add(mniisEqual);
		    	
		    mnuOneInputNyadOps=new JMenu("One Input");
			mnuNyadOps.add(mnuOneInputNyadOps);
				mnihasAlgebra=new JMenuItem("has Algebra?");
				mnuOneInputNyadOps.add(mnihasAlgebra);
		    	
		    mnuTwoInputNyadOps=new JMenu("Two Inputs");
		    mnuNyadOps.add(mnuTwoInputNyadOps);
			   	mniAdd=new JMenuItem("Add");
			   	mnuTwoInputNyadOps.add(mniAdd);
			   	mniSubtract=new JMenuItem("Subtract");
			   	mnuTwoInputNyadOps.add(mniSubtract);
			   	mniLeftMultiply=new JMenuItem("Left Multiply");
			   	mnuTwoInputNyadOps.add(mniLeftMultiply);
			   	mniRightMultiply=new JMenuItem("Right Multiply");
			   	mnuTwoInputNyadOps.add(mniRightMultiply);
			   	mniSymm=new JMenuItem("Symm Multiply");
			   	mnuTwoInputNyadOps.add(mniSymm);
			   	mniASymm=new JMenuItem("ASymm Multiply");
			   	mnuTwoInputNyadOps.add(mniASymm);
		
		mnuMonadOps=new JMenu("Monad Ops");
		add(mnuMonadOps);
		
			mnuMonadBooleanOps=new JMenu("Boolean");
	    	mnuMonadOps.add(mnuMonadBooleanOps);
		    	mniisGrade=new JMenuItem("is Grade?");
		    	mnuMonadBooleanOps.add(mniisGrade);
		    	mniisMultiGrade=new JMenuItem("is Multigrade?");
		    	mnuMonadBooleanOps.add(mniisMultiGrade);
		    	mniisNilpotent=new JMenuItem("is Nilpotent?");
		    	mnuMonadBooleanOps.add(mniisNilpotent);
		    	mniisIdempotent=new JMenuItem("is Idempotent?");
		    	mnuMonadBooleanOps.add(mniisIdempotent);
		    	mniisScaledIdempotent=new JMenuItem("is Scaled Idempotent?");
		    	mnuMonadBooleanOps.add(mniisScaledIdempotent);
	    	
	    	mnuOneInputMonadOps=new JMenu("One Input");
	    	mnuMonadOps.add(mnuOneInputMonadOps);
		    	mniisSGrade=new JMenuItem("unique grade?");
		    	mnuOneInputMonadOps.add(mniisSGrade);
		    	mnihasGrade=new JMenuItem("has grade?");
		    	mnuOneInputMonadOps.add(mnihasGrade);
		    	mniMagnitudeOf=new JMenuItem("magnitude?");
		    	mnuOneInputMonadOps.add(mniMagnitudeOf);
		    	mniSQMagnitudeOf=new JMenuItem("sq magnitude?");
		    	mnuOneInputMonadOps.add(mniSQMagnitudeOf);
		    	mniNormalize=new JMenuItem("normalize");
		    	mnuOneInputMonadOps.add(mniNormalize);
		    	mniDualLeft=new JMenuItem("dual>");
		    	mnuOneInputMonadOps.add(mniDualLeft);
		    	mniDualRight=new JMenuItem("<dual");
		    	mnuOneInputMonadOps.add(mniDualRight);
		    	mniInvert=new JMenuItem("invert");
		    	mnuOneInputMonadOps.add(mniInvert);
		    	mniReverse=new JMenuItem("reverse");
		    	mnuOneInputMonadOps.add(mniReverse);
		    	mniScale=new JMenuItem("scale");
		    	mnuOneInputMonadOps.add(mniScale);
		    	mniGradeCrop=new JMenuItem("grade crop");
		    	mnuOneInputMonadOps.add(mniGradeCrop);
		    	mniGradeCut=new JMenuItem("grade cut");
		    	mnuOneInputMonadOps.add(mniGradeCut);
		    	
	    	mnuTwoInputMonadOps=new JMenu("Two Inputs");
	    	mnuMonadOps.add(mnuTwoInputMonadOps);
    		
    	mnuTools=new JMenu("Tools");
    	add(mnuTools);
	    	mniCreate=new JMenuItem("create");
	    	mnuTools.add(mniCreate);
	    	mniOptions=new JMenuItem("options");
	    	mnuTools.add(mniOptions);
    	
    	mnuHelp=new JMenu("Help");
    	add(mnuHelp);
	    	mniSupport=new JMenuItem("support");
	    	mnuHelp.add(mniSupport);
	    	mniAbout=new JMenuItem("about");
	    	mnuHelp.add(mniAbout);
    	//Help Menu items added
    }
    
}
