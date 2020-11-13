/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.MOpsMagnitudeEvents<br>
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
 * ---org.interworldtransport.cladosviewer.MOpsMagnitudeEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerEvents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.interworldtransport.cladosGExceptions.CladosMonadException;
import org.interworldtransport.cladosviewer.*;

/**
 * This class manages events relating to the answering of a simple question.
 * What is the magnitude of this Monad?
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class MOpsMagnitudeEvents implements ActionListener {
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
	public MOpsMagnitudeEvents(JMenuItem pmniControlled, MOpsParentEvents pParent) {
		_control = pmniControlled;
		_control.addActionListener(this);
		_parent = pParent;
	}

	/**
	 * This is the actual action to be performed by this member of the menu.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		int indexNyadPanelSelected = _parent._GUI.appGeometryView.getPaneFocus();
		if (indexNyadPanelSelected < 0) {
			ErrorDialog.show("No nyad in the focus.\nNothing done.", "Need Nyad In Focus");
			return;
		}

		NyadPanel tNSpotPnl = _parent._GUI.appGeometryView.getNyadPanel(indexNyadPanelSelected);

		int indxMndPnlSlctd = tNSpotPnl.getPaneFocus();
		if (indxMndPnlSlctd < 0) {
			ErrorDialog.show("Magnitude Discovery needs one monad in focus.\nNothing done.", "Need Monad In Focus");
			return;
		}

		MonadPanel tMSpotPnl = tNSpotPnl.getMonadPanel(indxMndPnlSlctd);
		try {
			switch (tMSpotPnl.getRepMode()) {
			case REALF -> _parent._GUI.appFieldBar.setWhatFloatR(tMSpotPnl.getMonadRF().magnitude().getModulus());
			case REALD -> _parent._GUI.appFieldBar.setWhatDoubleR(tMSpotPnl.getMonadRD().magnitude().getModulus());
			case COMPLEXF -> {
				_parent._GUI.appFieldBar.setWhatFloatR(tMSpotPnl.getMonadCF().magnitude().getModulus());
				_parent._GUI.appFieldBar.setWhatFloatI(0.0F);
			}
			case COMPLEXD -> {
				_parent._GUI.appFieldBar.setWhatDoubleR(tMSpotPnl.getMonadCD().magnitude().getModulus());
				_parent._GUI.appFieldBar.setWhatDoubleI(0.0D);
			}
			}
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad magnitude has been computed.\n");
		} catch (CladosMonadException e) {
			ErrorDialog.show("Selected monad has an issue.\nNothing done.\n" + e.getSourceMessage(),
					"Clados Monad Exception");
		}
	}
}