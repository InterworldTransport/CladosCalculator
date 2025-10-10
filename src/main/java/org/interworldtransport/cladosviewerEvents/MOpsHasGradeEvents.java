/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.MOpsHasGradeEvents<br>
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
 * ---org.interworldtransport.cladosviewer.MOpsHasGradeEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerEvents;

import org.interworldtransport.cladosviewer.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

/**
 * This class manages events relating to the answering of a simple question.
 * What is the findgrade of the selected monad?
 *
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public class MOpsHasGradeEvents implements ActionListener {
	/**
	 * This reference points at the control object in the menu related to this part of the event model. 
	 * It's open for later possibilities for scripted ations.
	 */
	protected JMenuItem _control;
	/**
	 * This reference points at the parent menu for navigating up and down the object tree. 
	 * It's open for later possibilities for scripted ations.
	 */
	protected MOpsParentEvents _parent;

	/**
	 * This is the default constructor.
	 * 
	 * @param pmniControlled JMenuItem This is a reference to the Menu Item for
	 *                       which this event acts.
	 * @param pParent        MOpsParentEvents This is a reference to the
	 *                       NOpsParentEvents parent event handler
	 */
	public MOpsHasGradeEvents(JMenuItem pmniControlled, MOpsParentEvents pParent) {
		_control = pmniControlled;
		_control.addActionListener(this);
		_parent = pParent;
	}

	/**
	 * This is the actual action to be performed by this member of the menu. Find
	 * the log of the gradeKey of the selected Monad and see if it is an integer. If
	 * so, monad is of a single findgrade... so show the log of the gradeKey = monad
	 * findgrade
	 * 
	 * A future version of the method must use the findgrade represented in the
	 * reference frame instead. Fourier decomposition is done against that frame and
	 * not the canonical one most of the time. That means the getGradeKey() method
	 * will channel through the ReferenceFrame of the monad.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		int indexNyadPanelSelected = _parent._GUI.appGeometryView.getPaneFocus();
		if (indexNyadPanelSelected < 0) {
			ErrorDialog.show("No nyad in the focus.\nNothing done.", "Need Nyad In Focus");
			return;
		}

		NyadPanel<?> panelNyadSelected = _parent._GUI.appGeometryView.getNyadPanel(indexNyadPanelSelected);
		int indxMndPnlSlctd = panelNyadSelected.getPaneFocus();
		if (indxMndPnlSlctd < 0) {
			ErrorDialog.show("Has Grade Test needs one monad in focus.\nNothing done.", "Need Monad In Focus");
			return;
		}

		// Production of the findgrade to be tested could fail hard at
		// parseFloat(...getRealText())
		// Hence the need for a try/catch phrase around all this;
		int grade2Test = 0;
		try {
			grade2Test = (int) Float.parseFloat(_parent._GUI.appFieldBar.getRealText());
			if (grade2Test < 0)
				return;
		} catch (NullPointerException eNull) {
			// Catch the empty text 'real number' text field on the FieldBar.
			ErrorDialog.show("Has Grade Test must have a real # in the FieldBar.\nNothing done.",
					"Null Pointer Exception");
			return;
		} catch (NumberFormatException eFormat) {
			// Catch the non-parse-able text 'real number' text field on the FieldBar.
			ErrorDialog.show("Has Grade Test must have a parse-able real # in the FieldBar.\nNothing done.",
					"Number Format Exception");
			return;
		}

		MonadPanel<?> tSpot = panelNyadSelected.getMonadPanel(indxMndPnlSlctd);
		long tempGradeKey = tSpot.getMonad().getGradeKey();
		
		// We have a tempGradeKey AND a grade2Test. If this were a uniqueness test we'd
		// just build key2Test=Math.pow(10,grade2Test) and checkfor equality. It isn't
		// though. What we need here is whether key2Test shows up in the testGradeKey.
		// For example... tempGradeKey = 1000101100 and key2Test = 1000. That should
		// test true because the fourth digit of the tempGradeKey is '1'. Basically,
		// 10^grade2test shows up.

		// Finding the log10 of tempGradeKey and truncating to an integer reveals the
		// largest findgrade in the key. If that is larger than grade2Test, we simply
		// remove that from the key and build a new key (a long integer) based on what's
		// left. Loop through this until the reduced findgrade key has the same number
		// of digits (or less) than key2Test and we are ready to try the actual hasGrade
		// test.

		long reducedGradeKey = tempGradeKey;
		short reducedMaxGrade = (short) Math.log10(reducedGradeKey);
		while (reducedMaxGrade > grade2Test) {
			reducedGradeKey -= Math.pow(10, reducedMaxGrade);
			reducedMaxGrade = (short) Math.log10(reducedGradeKey);
		}
		// At this point, reducedMaxGrade will either BE grade2Test or smaller.
		if (reducedMaxGrade < grade2Test)
			_parent._GUI.appStatusBar
					.setStatusMsg("-->Selected monad does NOT not have findgrade " + grade2Test + ".\n");
		else if (grade2Test > 0) // We know reducedMasGrade == grade2Test
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad HAS findgrade " + grade2Test + ".\n");
		else if (tempGradeKey == 1) // We know grade2Test == 0 and reducedMasGrade == grade2Test
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad HAS scalar findgrade... possibly zero.\n");
		else // We know tempGradeKey > 1 and reducedMasGrade == grade2Test
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad does NOT have scalar findgrade.\n");
		// This last phrase works because monads with higher findgrade blades don't have
		// scalar parts if the scalar coeff is zero. The only time a zero scalar
		// coefficient is acceptable is when no higher blade is contained in the monad.
	}
}
