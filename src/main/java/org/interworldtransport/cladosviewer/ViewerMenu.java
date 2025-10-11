/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.ViewerMenu<br>
 * -------------------------------------------------------------------- <br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.<br>
 * 
 * Use of this code or executable objects derived from it by the Licensee 
 * states their willingness to accept the terms of the license. <br> 
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.<br> 
 * 
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.ViewerMenu<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewer;

import javax.swing.*;

/**
 * org.interworldtransport.cladosviewer.ViewerMenu The ViewerMenu class is
 * intended to be the class that encapsulates the menu used in the Viewer class.
 * <br>
 * There is nothing especially important about the layout of this class. It
 * should not be instantiated except by the Calculator application. Alterations
 * to this class should be made in careful coordination with the classes of the
 * event model.
 * 
 * @version 1.0
 * @author Dr Alfred W Differ
 */

public class ViewerMenu extends JMenuBar {
	private static final long serialVersionUID = -9157005844074113468L;
	/**
	 * The controlling application.
	 */
	public CladosCalculator _parentGUI;
	/**
	 * The File Parent Menu for the application.
	 */
	public JMenu mnuFile;
	/**
	 * The menu item for Open File.
	 */
	public JMenuItem mniOpen;
	/**
	 * The menu item for Save File.
	 */
	public JMenuItem mniSave;
	/**
	 * The menu item for Save As File.
	 */
	public JMenuItem mniSaveAs;
	/**
	 * The menu item for Exit.
	 */
	public JMenuItem mniExit;

	/**
	 * The Nyad Operations Parent Menu for the application.
	 */
	public JMenu mnuNyadOps;
	/**
	 * The menu for all Nyad boolean operations.
	 */
	public JMenu mnuNyadBooleanOps;
	/**
	 * The menu item for nyad check that can be phrased as "Are they equal?".
	 */
	public JMenuItem mniisEqual;
	/**
	 * The menu item for nyad check that can be phrased as "Is it Zero?".
	 */
	public JMenuItem mniisZero;
	/**
	 * The menu item for nyad check that can be phrased as "Are they a strong match?".
	 */
	public JMenuItem mniisStrgReferenceMatch;
	/**
	 * The menu item for nyad check that can be phrased as "Are they a weak match?".
	 */
	public JMenuItem mniisWeakReferenceMatch;
	/**
	 * The menu for nyad operations that require one input.
	 */
	public JMenu mnuOneInputNyadOps;
	/**
	 * The menu item for nyad check that can be phrased as "Does it use this Algebra?".
	 */
	public JMenuItem mnihasAlgebra;
	/**
	 * The menu item for nyad check that can be phrased as "Is it a scalar at this Algebra?".
	 */
	public JMenuItem mniisScalarAt;
	/**
	 * The menu item for nyad check that can be phrased as "Is it a pscalar at this Algebra?".
	 */
	public JMenuItem mniisPScalarAt;
	/**
	 * The menu for nyad operations that require two inputs.
	 */
	public JMenu mnuTwoInputNyadOps;
	/**
	 * The menu item for the nyad addition operation.
	 */
	public JMenuItem mniAdd;
	/**
	 * The menu item for the nyad subtraction operation.
	 */
	public JMenuItem mniSubtract;
	/**
	 * The menu item for the nyad left multiply operation.
	 */
	public JMenuItem mniLeftMultiply;
	/**
	 * The menu item for the nyad right multiply operation.
	 */
	public JMenuItem mniRightMultiply;
	/**
	 * The menu item for the nyad symmetric multiply operation.
	 */
	public JMenuItem mniSymm;
	/**
	 * The menu item for the nyad antisymmetric multiply operation.
	 */
	public JMenuItem mniASymm;

	/**
	 * The Monad Operations Parent Menu for the application.
	 */
	public JMenu mnuMonadOps;
	/**
	 * The menu for all monad-in-a-nyad boolean operations.
	 */
	public JMenu mnuMonadBooleanOps;
	/**
	 * The menu item for checking whether a monad is multigrade.
	 */
	public JMenuItem mniisMultiGrade;
	/**
	 * The menu item for checking whether a monad is of the grade being checked.
	 */
	public JMenuItem mniisGrade;
	/**
	 * The menu item for checking whether a monad is nilpotent.
	 */
	public JMenuItem mniisNilpotent;
	/**
	 * The menu item for checking whether a monad is idempotent.
	 */
	public JMenuItem mniisIdempotent;
	/**
	 * The menu item for checking whether a monad is a scaled idepmpotent.
	 */
	public JMenuItem mniisScaledIdempotent;

	/**
	 * The parent menu for all monad operations requiring one input.
	 */
	public JMenu mnuOneInputMonadOps;
	/**
	 * The menu item for checking whether a monad is scalar grade.
	 */
	public JMenuItem mniisSGrade;
	/**
	 * The menu item for checking whether a monad has the requested grade within.
	 */
	public JMenuItem mnihasGrade;
	/**
	 * The menu item for generating from the monad its magnitude.
	 */
	public JMenuItem mniMagnitudeOf;
	/**
	 * The menu item for generating from the monad its square magnitude.
	 */
	public JMenuItem mniSQMagnitudeOf;
	/**
	 * The menu item for normalizing the monad.
	 */
	public JMenuItem mniNormalize;
	/**
	 * The menu item for generating from dual of the monad from the left.
	 */
	public JMenuItem mniDualLeft;
	/**
	 * The menu item for generating from dual of the monad from the right.
	 */
	public JMenuItem mniDualRight;
	/**
	 * The menu item for performing the main involution on the monad. Generator sign flip.
	 */
	public JMenuItem mniInvert;
	/**
	 * The menu item for performing the second involution on the monad. Product order flip.
	 */
	public JMenuItem mniReverse;
	/**
	 * The menu item for scaling the monad.
	 */
	public JMenuItem mniScale;
	/**
	 * The menu item for cropping the monad to a particular grade.
	 */
	public JMenuItem mniGradeCrop;
	/**
	 * The menu item for cropping OUT of the monad a particular grade.
	 */
	public JMenuItem mniGradeCut;

	/**
	 * The parent menu for all monad operations requiring one input.
	 */
	public JMenu mnuTwoInputMonadOps;

	/**
	 * The Tools Parent Menu for the application.
	 */
	public JMenu mnuTools;
	/**
	 * The menu item for showing the Options dialog. This displays the configuration.
	 */
	public JMenuItem mniOptions;
	/**
	 * The menu item for creating a new Nyad.
	 */
	public JMenuItem mniCreateNyad;
	/**
	 * The menu item for creating a new Monad in the focused Nyad.
	 */
	public JMenuItem mniCreateMonad;

	/**
	 * The Help Parent Menu for the application.
	 */
	public JMenu mnuHelp;
	/**
	 * The menu item for showing the dialog desicribing how to get support.
	 */
	public JMenuItem mniSupport;
	/**
	 * The menu item for showing the About dialog.
	 */
	public JMenuItem mniAbout;

	/**
	 * The ViewerMenu class is intended to be the class that encapsulates the menu
	 * used in the Calculator. For the sake of maintenance this definition
	 * is in a separate class and file.
	 * 
	 * @param pParentGUI CladosCalculator This parameter references the owning
	 *                   application. Nothing spectacular.
	 */
	public ViewerMenu(CladosCalculator pParentGUI) {
		super();
		_parentGUI = pParentGUI;

		mnuFile = new JMenu("File");
		add(mnuFile);
		mniOpen = new JMenuItem("Open");
		mnuFile.add(mniOpen);
		mniSave = new JMenuItem("Save");
		mnuFile.add(mniSave);
		mniSaveAs = new JMenuItem("Save As");
		mnuFile.add(mniSaveAs);
		mniExit = new JMenuItem("Exit");
		mnuFile.add(mniExit);

		mnuNyadOps = new JMenu("List Ops");
		add(mnuNyadOps);
		mniCreateNyad = new JMenuItem("create nyad");
		mnuNyadOps.add(mniCreateNyad);
		mnuNyadBooleanOps = new JMenu("Boolean(this)");
		mnuNyadOps.add(mnuNyadBooleanOps);
		mniisStrgReferenceMatch = new JMenuItem("is Strong Reference Match?");
		mnuNyadBooleanOps.add(mniisStrgReferenceMatch);
		mniisWeakReferenceMatch = new JMenuItem("is Weak Reference Match?");
		mnuNyadBooleanOps.add(mniisWeakReferenceMatch);
		mniisZero = new JMenuItem("is Zero?");
		mnuNyadBooleanOps.add(mniisZero);
		mniisEqual = new JMenuItem("is Equal?");
		mnuNyadBooleanOps.add(mniisEqual);

		mnuOneInputNyadOps = new JMenu("OneOtherInput(this)");
		mnuNyadOps.add(mnuOneInputNyadOps);
		mnihasAlgebra = new JMenuItem("has Algebra?");
		mnuOneInputNyadOps.add(mnihasAlgebra);
		mniisScalarAt = new JMenuItem("is Scalar At?");
		mnuOneInputNyadOps.add(mniisScalarAt);
		mniisPScalarAt = new JMenuItem("is PScalar At?");
		mnuOneInputNyadOps.add(mniisPScalarAt);

		mnuTwoInputNyadOps = new JMenu("TwoFullInputs");
		mnuNyadOps.add(mnuTwoInputNyadOps);
		mniAdd = new JMenuItem("Add");
		mnuTwoInputNyadOps.add(mniAdd);
		mniSubtract = new JMenuItem("Subtract");
		mnuTwoInputNyadOps.add(mniSubtract);
		mniLeftMultiply = new JMenuItem("Left Multiply");
		mnuTwoInputNyadOps.add(mniLeftMultiply);
		mniRightMultiply = new JMenuItem("Right Multiply");
		mnuTwoInputNyadOps.add(mniRightMultiply);
		mniSymm = new JMenuItem("Symm Multiply");
		mnuTwoInputNyadOps.add(mniSymm);
		mniASymm = new JMenuItem("ASymm Multiply");
		mnuTwoInputNyadOps.add(mniASymm);

		mnuMonadOps = new JMenu("Multivector Ops");
		add(mnuMonadOps);
		mniCreateMonad = new JMenuItem("create monad");
		mnuMonadOps.add(mniCreateMonad);
		mnuMonadBooleanOps = new JMenu("Boolean(this)");
		mnuMonadOps.add(mnuMonadBooleanOps);
		mniisGrade = new JMenuItem("is Grade?");
		mnuMonadBooleanOps.add(mniisGrade);
		mniisMultiGrade = new JMenuItem("is Multigrade?");
		mnuMonadBooleanOps.add(mniisMultiGrade);
		mniisNilpotent = new JMenuItem("is Nilpotent?");
		mnuMonadBooleanOps.add(mniisNilpotent);
		mniisIdempotent = new JMenuItem("is Idempotent?");
		mnuMonadBooleanOps.add(mniisIdempotent);
		mniisScaledIdempotent = new JMenuItem("is Scaled Idempotent?");
		mnuMonadBooleanOps.add(mniisScaledIdempotent);

		mnuOneInputMonadOps = new JMenu("OneOtherInput(this)");
		mnuMonadOps.add(mnuOneInputMonadOps);
		mniisSGrade = new JMenuItem("unique findgrade?");
		mnuOneInputMonadOps.add(mniisSGrade);
		mnihasGrade = new JMenuItem("has findgrade?");
		mnuOneInputMonadOps.add(mnihasGrade);
		mniMagnitudeOf = new JMenuItem("magnitude?");
		mnuOneInputMonadOps.add(mniMagnitudeOf);
		mniSQMagnitudeOf = new JMenuItem("sq magnitude?");
		mnuOneInputMonadOps.add(mniSQMagnitudeOf);
		mniNormalize = new JMenuItem("normalize");
		mnuOneInputMonadOps.add(mniNormalize);
		mniDualLeft = new JMenuItem("dual>");
		mnuOneInputMonadOps.add(mniDualLeft);
		mniDualRight = new JMenuItem("<dual");
		mnuOneInputMonadOps.add(mniDualRight);
		mniInvert = new JMenuItem("invert");
		mnuOneInputMonadOps.add(mniInvert);
		mniReverse = new JMenuItem("reverse");
		mnuOneInputMonadOps.add(mniReverse);
		mniScale = new JMenuItem("scale");
		mnuOneInputMonadOps.add(mniScale);
		mniGradeCrop = new JMenuItem("findgrade crop");
		mnuOneInputMonadOps.add(mniGradeCrop);
		mniGradeCut = new JMenuItem("findgrade cut");
		mnuOneInputMonadOps.add(mniGradeCut);

		mnuTwoInputMonadOps = new JMenu("TwoFullInputs");
		mnuMonadOps.add(mnuTwoInputMonadOps);

		mnuTools = new JMenu("Tools");
		add(mnuTools);
		mniOptions = new JMenuItem("options");
		mnuTools.add(mniOptions);

		mnuHelp = new JMenu("Help");
		add(mnuHelp);
		mniSupport = new JMenuItem("support");
		mnuHelp.add(mniSupport);
		mniAbout = new JMenuItem("about");
		mnuHelp.add(mniAbout);
	}
}
