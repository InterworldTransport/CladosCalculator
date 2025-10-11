/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.NOpsIsScalarAtEvents<br>
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
 * ---org.interworldtransport.cladosviewer.NOpsIsScalarAtEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package org.interworldtransport.cladosviewerEvents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.interworldtransport.cladosviewer.ErrorDialog;
import org.interworldtransport.cladosviewer.NyadPanel;

/**
 * This class manages events relating to the answering of a boolean question. Is
 * the next nyad in the stack a scalar at the currently selected monad's
 * algebra?
 * 
 * This test involves a search for the algebra in the next nyad first. If that
 * fails, this test results in a 'false' response. If that succeeds ONCE, that
 * monad is tested to see if it is pure scalar grade. If it succeeds MORE THAN
 * ONCE, this test checks them all and reports 'false' if any of them fail to be
 * pure scalar grade.
 *
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public class NOpsIsScalarAtEvents implements ActionListener {
	/**
	 * This reference points at the control object in the menu related to this part of the event model. 
	 * It's open for later possibilities for scripted ations.
	 */
	protected JMenuItem _control;
	/**
	 * This reference points at the parent menu for navigating up and down the object tree. 
	 * It's open for later possibilities for scripted ations.
	 */
	protected NOpsParentEvents _parent;

	/**
	 * This is the default constructor.
	 * 
	 * @param pmniControlled JMenuItem This is a reference to the Menu Item for
	 *                       which this event acts.
	 * @param pParent        NOpsParentEvents This is a reference to the
	 *                       NOpsParentEvents parent event handler
	 */
	public NOpsIsScalarAtEvents(JMenuItem pmniControlled, NOpsParentEvents pParent) {
		_control = pmniControlled;
		_control.addActionListener(this);
		_parent = pParent;
	}

	/**
	 * This is the actual action to be performed here in the menu. Nyad algebra
	 * match test is checked between focus monad and the next nyad in the stack.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		int tNyadIndex = _parent._GUI.appGeometryView.getPaneFocus();
		if (tNyadIndex < 0 | tNyadIndex >= _parent._GUI.appGeometryView.getNyadListSize() - 1) {
			ErrorDialog.show("No nyad in the focus... or the last one is.\nNothing done.", "Need Nyad In Focus");
			return;
		}

		NyadPanel<?> panelNyadSelected = _parent._GUI.appGeometryView.getNyadPanel(tNyadIndex);
		int indxMndPnlSlctd = panelNyadSelected.getPaneFocus();
		if (indxMndPnlSlctd < 0) {
			ErrorDialog.show("IsScalarAt Test needs one monad in focus.\nNothing done.", "Need Monad In Focus");
			return;
		}

		NyadPanel<?> panelNyadNext = _parent._GUI.appGeometryView.getNyadPanel(tNyadIndex + 1);

		if (panelNyadSelected.getRepMode() != panelNyadNext.getRepMode()) {
			ErrorDialog.show("Nyads using different ProtoNs.", "Nyad ProtoN Mismatch");
			return;
		}
		boolean test = panelNyadNext.getNyad()
				.isScalarAt(panelNyadSelected.getMonadPanel(indxMndPnlSlctd).getMonad().getAlgebra());
		if (test)
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad's algebra IS SCALAR in the next nyad.\n");
		else
			_parent._GUI.appStatusBar
					.setStatusMsg("-->Selected monad's algebra IS NOT PURELY SCALAR in the next nyad.\n");
	}

}