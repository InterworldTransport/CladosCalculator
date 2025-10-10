/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.MOpsGradePartEvents<br>
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
 * ---org.interworldtransport.cladosviewer.MOpsGradePartEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package org.interworldtransport.cladosviewerEvents;

import org.interworldtransport.cladosviewer.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

/**
 * This class manages events relating to a simple requirement Limit this Monad
 * to a particular findgrade.
 *
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public class MOpsGradePartEvents implements ActionListener {
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
	 * @param pParent        NOpsParentEvents This is a reference to the
	 *                       NOpsParentEvents parent event handler
	 */
	public MOpsGradePartEvents(JMenuItem pmniControlled, MOpsParentEvents pParent) {
		_control = pmniControlled;
		_control.addActionListener(this);
		_parent = pParent;
	}

	/**
	 * This is the actual action to be performed by this member of the menu. This is
	 * the classic GradePart method. It is typically used to get scalar parts.
	 * Basically, the monad in focus is cropped around the findgrade that should be
	 * kept as is.
	 * 
	 * A future version of the method must use the findgrade represented in the
	 * reference frame instead. Fourier decomposition is done against that frame and
	 * not the canonical one most of the time. That means the getPart(short) method
	 * will channel through the ReferenceFrame of the monad.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		int indexNyadPanelSelected = _parent._GUI.appGeometryView.getPaneFocus();
		if (indexNyadPanelSelected < 0) {
			ErrorDialog.show("No nyad in the focus.\nNothing done.", "Need Nyad In Focus");
			return;
		}

		NyadPanel<?> tNSpotPnl = _parent._GUI.appGeometryView.getNyadPanel(indexNyadPanelSelected);
		int indxMndPnlSlctd = tNSpotPnl.getPaneFocus();
		if (indxMndPnlSlctd < 0) {
			ErrorDialog.show("GradePart Operation needs one monad in focus.\nNothing done.", "Need Monad In Focus");
			return;
		}

		MonadPanel<?> tMSpotPnl = tNSpotPnl.getMonadPanel(tNSpotPnl.getPaneFocus());

		try {
			byte tGrade = Byte.parseByte(_parent._GUI.appFieldBar.getRealText());
			tMSpotPnl.getMonad().gradePart(tGrade);
			tMSpotPnl.setCoefficientDisplay();
			_parent._GUI.appStatusBar
					.setStatusMsg("-->Selected monad has been cropped around " + tGrade + "-findgrade.\n");
		} catch (NullPointerException eNull) {
			ErrorDialog.show("GradePart Operation must have a real # in the FieldBar.\nNothing done.",
					"Null Pointer Exception");
			return;
		} catch (NumberFormatException eFormat) {
			ErrorDialog.show("GradePart Operation must have a parse-able real # in the FieldBar.\nNothing done.",
					"Number Format Exception");
			return;
		}
	}
}