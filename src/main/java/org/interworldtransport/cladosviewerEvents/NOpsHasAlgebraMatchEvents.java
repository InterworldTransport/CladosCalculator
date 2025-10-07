/**
 * <h2>Copyright</h2> © 2021 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.NOpsHasAlgebraMatchEvents<br>
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
 * ---org.interworldtransport.cladosviewer.NOpsHasAlgebraMatchEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package org.interworldtransport.cladosviewerEvents;

import org.interworldtransport.cladosG.Nyad;

import org.interworldtransport.cladosviewer.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * This class manages events relating to the answering of a boolean question. Is
 * the selected monad's algebra used by the nyad following on the stack?
 *
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public class NOpsHasAlgebraMatchEvents implements ActionListener {
	protected JMenuItem _control;
	protected NOpsParentEvents _parent;

	/**
	 * This is the default constructor.
	 * 
	 * @param pmniControlled JMenuItem This is a reference to the Menu Item for
	 *                       which this event acts.
	 * @param pParent        NOpsParentEvents This is a reference to the
	 *                       NOpsParentEvents parent event handler
	 */
	public NOpsHasAlgebraMatchEvents(JMenuItem pmniControlled, NOpsParentEvents pParent) {
		_control = pmniControlled;
		_control.addActionListener(this);
		_parent = pParent;
	}

	/**
	 * This is the actual action to be performed by this member of the menu. Nyad
	 * algebra match test is checked between focus monad and the next nyad in the
	 * stack.
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
			ErrorDialog.show("HasAlgebra Test needs one monad in focus.\nNothing done.", "Need Monad In Focus");
			return;
		}
		// MonadPanel<?> tSpot = panelNyadSelected.getMonadPanel(indxMndPnlSlctd);

		NyadPanel<?> panelNyadNext = _parent._GUI.appGeometryView.getNyadPanel(tNyadIndex + 1);

		if (panelNyadSelected.getRepMode() != panelNyadNext.getRepMode()) {
			ErrorDialog.show("Nyads using different DivFields.", "Nyad DivField Mismatch");
			return;
		}

		boolean test = Nyad.hasAlgebra(panelNyadNext.getNyad(),
				panelNyadSelected.getMonadPanel(indxMndPnlSlctd).getMonad().getAlgebra());
	
		if (test)
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad's algebra HAS presence in the next nyad.\n");
		else
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad's algebra HAS NO presence in the next nyad.\n");
	}
}