/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.MOpsFindGradeEvents<br>
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
 * ---org.interworldtransport.cladosviewer.MOpsFindGradeEvents<br>
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
public class MOpsFindGradeEvents implements ActionListener {
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
	public MOpsFindGradeEvents(JMenuItem pmniControlled, MOpsParentEvents pParent) {
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
			ErrorDialog.show("Grade Test needs one monad in focus.\nNothing done.", "Need Monad In Focus");
			return;
		}

		MonadPanel<?> tSpot = panelNyadSelected.getMonadPanel(indxMndPnlSlctd);
		double logGradeKey =  Math.log10(tSpot.getMonad().getGradeKey());

		if (logGradeKey < 0) {
			ErrorDialog.show("ProtoN not recognized.", "ProtoN Unsupported");
			return;
		}

		if (logGradeKey != Math.floor(logGradeKey))
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad IS NOT a single findgrade.\n");
		else {
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad IS single findgrade.\n");
			_parent._GUI.appFieldBar.setWhatDoubleR(logGradeKey);
		}
	}
}
