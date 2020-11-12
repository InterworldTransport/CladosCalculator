/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.MOpsGradeEvents<br>
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
 * ---com.interworldtransport.cladosviewer.MOpsGradeEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;

import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosG.MonadRealD;
import com.interworldtransport.cladosG.MonadComplexF;
import com.interworldtransport.cladosG.MonadComplexD;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.NyadPanel;
import com.interworldtransport.cladosviewer.ErrorDialog;

import java.awt.event.*;
import javax.swing.*;

/**
 * This class manages events relating to the answering of a boolean question. Is
 * the selected monad a particular findgrade?
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class MOpsGradeEvents implements ActionListener {
	protected JMenuItem _control;
	protected MOpsParentEvents _parent;

	/**
	 * This is the default constructor.
	 * 
	 * @param pmniControlled JMenuItem This is a reference to the Menu Item for
	 *                       which this event acts.
	 * @param pParent        NOpsParentEvents This is a reference to the
	 *                       NOpsParentEvents parent event handler
	 */
	public MOpsGradeEvents(JMenuItem pmniControlled, MOpsParentEvents pParent) {
		_control = pmniControlled;
		_control.addActionListener(this);
		_parent = pParent;
	}

	/**
	 * This is the actual action to be performed by this member of the menu. The
	 * Monad with focus is tested to see if it is k-findgrade with k coming from the
	 * real part of FieldBar. If it is (or isn't) the test is reported to the
	 * StatusBar.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		int indexNyadPanelSelected = _parent._GUI.appGeometryView.getPaneFocus();
		if (indexNyadPanelSelected < 0) {
			ErrorDialog.show("No nyad in the focus.\nNothing done.", "Need Nyad In Focus");
			return;
		}

		NyadPanel panelNyadSelected = _parent._GUI.appGeometryView.getNyadPanel(indexNyadPanelSelected);
		int indxMndPnlSlctd = panelNyadSelected.getPaneFocus();
		if (indxMndPnlSlctd < 0) {
			ErrorDialog.show("Grade Test needs one monad in focus.\nNothing done.", "Need Monad In Focus");
			return;
		}

		try {
			// Production of the findgrade to be tested could fail hard at
			// parseFloat(...getRealText())
			// Hence the need for a try/catch phrase around all this
			int grade2Test = (int) Float.parseFloat(_parent._GUI.appFieldBar.getRealText());
			MonadPanel tSpot = panelNyadSelected.getMonadPanel(indxMndPnlSlctd);
			boolean test = switch (tSpot.getRepMode()) {
			case REALF -> MonadRealF.isGrade(tSpot.getMonadRF(), grade2Test);
			case REALD -> MonadRealD.isGrade(tSpot.getMonadRD(), grade2Test);
			case COMPLEXF -> MonadComplexF.isGrade(tSpot.getMonadCF(), grade2Test);
			case COMPLEXD -> MonadComplexD.isGrade(tSpot.getMonadCD(), grade2Test);
			};
			if (test)
				_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad is a pure " + grade2Test + "-findgrade.\n");
			else
				_parent._GUI.appStatusBar
						.setStatusMsg("-->Selected monad is NOT a pure " + grade2Test + "-findgrade.\n");
		} catch (NullPointerException eNull) {
			// Catch the empty text 'real number' text field on the FieldBar.
			ErrorDialog.show("Grade Test must have a real # in the FieldBar.\nNothing done.", "Null Pointer Exception");
			return;
		} catch (NumberFormatException eFormat) {
			// Catch the non-parse-able text 'real number' text field on the FieldBar.
			ErrorDialog.show("Grade Test must have a parse-able real # in the FieldBar.\nNothing done.",
					"Number Format Exception");
			return;
		}
	}
}