/**
 * <h2>Copyright</h2> © 2021 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.MOpsScaleEvents<br>
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
 * ---org.interworldtransport.cladosviewer.MOpsScaleEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerEvents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosF.RealF;
import org.interworldtransport.cladosF.RealD;
import org.interworldtransport.cladosF.ComplexF;
import org.interworldtransport.cladosF.ComplexD;
import org.interworldtransport.cladosG.Monad;

import org.interworldtransport.cladosviewer.*;

/**
 * org.interworldtransport.cladosviewer.SOpsScaleEvents This class manages
 * events relating to a simple operation... Rescale this Monad.
 *
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public class MOpsScaleEvents implements ActionListener {
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
	public MOpsScaleEvents(JMenuItem pmniControlled, MOpsParentEvents pParent) {
		_control = pmniControlled;
		_control.addActionListener(this);
		_parent = pParent;
	}

	/**
	 * This is the actual action to be performed by this member of the menu. It
	 * 'scales' the focus monad in the sense of scalar multiplication with the field
	 * suggested in the field bar. However, it only uses the values in the field
	 * bar. No DivFieldType matching occurs.
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
			ErrorDialog.show("Scale Operation needs one monad in focus.\nNothing done.", "Need Monad In Focus");
			return;
		}

		MonadPanel<?> tMSpotPnl = tNSpotPnl.getMonadPanel(tNSpotPnl.getPaneFocus());

		Monad tMonad = tMSpotPnl.getMonad();
		switch (tMSpotPnl.getRepMode()) {
		case REALF -> {
			RealF tFieldRF = (RealF) CladosField.REALF.createZERO(tMonad.getAlgebra().getCardinal());
			tFieldRF.setReal(Float.parseFloat(_parent._GUI.appFieldBar.getRealText()));
			tMonad.scale(tFieldRF);
			_parent._GUI.appStatusBar.setStatusMsg("\tmonad has been rescaled by ");
			_parent._GUI.appStatusBar.setStatusMsg(_parent._GUI.appFieldBar.getRealText() + "\n");
		}
		case REALD -> {
			RealD tFieldRD = (RealD) CladosField.REALD.createZERO(tMonad.getAlgebra().getCardinal());
			tFieldRD.setReal(Double.parseDouble(_parent._GUI.appFieldBar.getRealText()));
			tMonad.scale(tFieldRD);
			_parent._GUI.appStatusBar.setStatusMsg("\tmonad has been rescaled by ");
			_parent._GUI.appStatusBar.setStatusMsg(_parent._GUI.appFieldBar.getRealText() + "\n");
		}
		case COMPLEXF -> {
			ComplexF tFieldCF = (ComplexF) CladosField.COMPLEXF.createZERO(tMonad.getAlgebra().getCardinal());
			tFieldCF.setReal(Float.parseFloat(_parent._GUI.appFieldBar.getRealText()));
			tFieldCF.setImg(Float.parseFloat(_parent._GUI.appFieldBar.getImgText()));
			tMonad.scale(tFieldCF);
			_parent._GUI.appStatusBar.setStatusMsg("\tmonad has been rescaled by (R");
			_parent._GUI.appStatusBar.setStatusMsg(_parent._GUI.appFieldBar.getRealText() + ", I");
			_parent._GUI.appStatusBar.setStatusMsg(_parent._GUI.appFieldBar.getImgText() + ")\n");
		}
		case COMPLEXD -> {
			ComplexD tFieldCD = (ComplexD) CladosField.COMPLEXD.createZERO(tMonad.getAlgebra().getCardinal());
			tFieldCD.setReal(Double.parseDouble(_parent._GUI.appFieldBar.getRealText()));
			tFieldCD.setImg(Double.parseDouble(_parent._GUI.appFieldBar.getImgText()));
			tMonad.scale(tFieldCD);
			_parent._GUI.appStatusBar.setStatusMsg("\tmonad has been rescaled by (R");
			_parent._GUI.appStatusBar.setStatusMsg(_parent._GUI.appFieldBar.getRealText() + ", I");
			_parent._GUI.appStatusBar.setStatusMsg(_parent._GUI.appFieldBar.getImgText() + ")\n");
		}
		}
		tMSpotPnl.setCoefficientDisplay();
	}
}