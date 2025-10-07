/**
 * <h2>Copyright</h2> © 2021 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.NOpsLMultEvents<br>
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
 * ---org.interworldtransport.cladosviewer.NOpsLMultEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerEvents;

import org.interworldtransport.cladosviewer.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 *org.interworldtransport.cladosviewerr.COpsLMultEvents This class manages
 * events relating to a complex operation. Left multiply this Monad by another
 * Monad.
 *
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public class NOpsLMultEvents implements ActionListener {
	protected JMenuItem _control;
	protected NOpsParentEvents _parent;

	/**
	 * This is the default constructor.
	 * 
	 * @param pmniControlled JMenuItem This is a reference to the Menu Item for
	 *                       which this event acts.
	 * @param pParent        COpsEvents This is a reference to the NOpsParentEvents
	 *                       parent event handler
	 */
	public NOpsLMultEvents(JMenuItem pmniControlled, NOpsParentEvents pParent) {
		_control = pmniControlled;
		_control.addActionListener(this);
		_parent = pParent;
	}

	/**
	 * This is the actual action to be performed by this member of the menu.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		int indxNydPnlSlctd = _parent._GUI.appGeometryView.getPaneFocus();
		if (indxNydPnlSlctd < 0 | indxNydPnlSlctd == _parent._GUI.appGeometryView.getNyadListSize() - 1) {
			ErrorDialog.show("No nyad in the focus... or the last one is.\nNothing done.", "Need Nyad In Focus");
			return;
		}

		NyadPanel<?> tSpot = _parent._GUI.appGeometryView.getNyadPanel(indxNydPnlSlctd);
		NyadPanel<?> tSpotPlus = _parent._GUI.appGeometryView.getNyadPanel(indxNydPnlSlctd + 1);

		int indxMndPnlSlctd = tSpot.getPaneFocus();
		if (indxMndPnlSlctd < 0 | indxNydPnlSlctd > tSpotPlus.getMonadListSize()) {
			ErrorDialog.show("Multiplication needs two monads at the same index in a nyad.\nNothing done.",
					"Monads In Focus Issue");
			return;
		}

		MonadPanel<?> temp0 = tSpot.getMonadPanel(indxMndPnlSlctd);
		MonadPanel<?> temp1 = tSpotPlus.getMonadPanel(indxMndPnlSlctd);

		temp0.getMonad().multiplyLeft(temp1.getMonad());
		temp0.setCoefficientDisplay();
	}
}
