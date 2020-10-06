/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.SOpsScaleEvents<br>
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
 * ---com.interworldtransport.cladosviewer.SOpsScaleEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosF.DivField;
import com.interworldtransport.cladosF.RealF;
import com.interworldtransport.cladosF.RealD;
import com.interworldtransport.cladosF.ComplexF;
import com.interworldtransport.cladosF.ComplexD;
import com.interworldtransport.cladosFExceptions.FieldBinaryException;
import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosG.MonadRealD;
import com.interworldtransport.cladosG.MonadComplexF;
import com.interworldtransport.cladosG.MonadComplexD;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.NyadPanel;

import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.SOpsScaleEvents
 *  This class manages events relating to a simple operation...
 *  Rescale this Monad.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class SOpsScaleEvents implements ActionListener
 {
    protected JMenuItem 		_control;
    protected SOpsEvents 		_parent;

/** 
 * This is the default constructor.
 * @param pmniControlled
 *  JMenuItem
 * This is a reference to the Menu Item for which this event acts.
 * @param pParent
 * 	BOpsEvents
 * This is a reference to the BOpsEvents parent event handler
 */
    public SOpsScaleEvents(	JMenuItem pmniControlled,
    						SOpsEvents pParent)
    {
		_control=pmniControlled;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the menu.
 * It 'scales' the focus monad in the sense of scalar multiplication with the field
 * suggested in the field bar. 
 * However, it only uses the values in the field bar. No DivFieldType matching occurs.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	int indexNyadPanelSelected = _parent._GUI._GeometryDisplay.getPaneFocus();
    	if (indexNyadPanelSelected<0) 
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nNo nyad in the focus.\n");
    		return;	
    	}

		NyadPanel tNSpotPnl = _parent._GUI._GeometryDisplay.getNyadPanel(indexNyadPanelSelected);
		int indxMndPnlSlctd = tNSpotPnl.getPaneFocus();
    	if (indxMndPnlSlctd<0) 
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nScale function must have a monad in focus. Nothing done.\n");
    		return;
    	}
    	
	    MonadPanel tMSpotPnl=tNSpotPnl.getMonadPanel(tNSpotPnl.getPaneFocus());
	    
	    try
	    {
		    switch(tMSpotPnl.getRepMode())
	    	{
	    		case DivField.REALF:	MonadRealF tMonadRF = tMSpotPnl.getMonadRF();
	    								RealF tFieldRF = new RealF(			(tNSpotPnl.getNyadRF()).getProto().getFieldType(), 
	    																	Float.parseFloat(_parent._GUI._FieldBar.getRealText()));
							    		tMonadRF.scale(tFieldRF);
							 		    _parent._GUI._StatusBar.setStatusMsg("\tmonad has been rescaled by ");
							 		    _parent._GUI._StatusBar.setStatusMsg(_parent._GUI._FieldBar.getRealText()+"\n");
	    								break;
	    		case DivField.REALD:	MonadRealD tMonadRD = tMSpotPnl.getMonadRD();
										RealD tFieldRD = new RealD(			(tNSpotPnl.getNyadRD()).getProto().getFieldType(), 
																			Double.parseDouble(_parent._GUI._FieldBar.getRealText()));
										tMonadRD.scale(tFieldRD);
										_parent._GUI._StatusBar.setStatusMsg("\tmonad has been rescaled by ");
										_parent._GUI._StatusBar.setStatusMsg(_parent._GUI._FieldBar.getRealText()+"\n");
										break;
	    		case DivField.COMPLEXF:	MonadComplexF tMonadCF = tMSpotPnl.getMonadCF();
										ComplexF tFieldCF = new ComplexF(	(tNSpotPnl.getNyadCF()).getProto().getFieldType(), 
																			Float.parseFloat(_parent._GUI._FieldBar.getRealText()),
																			Float.parseFloat(_parent._GUI._FieldBar.getImgText()));
										tMonadCF.scale(tFieldCF);
										_parent._GUI._StatusBar.setStatusMsg("\tmonad has been rescaled by (R");
										_parent._GUI._StatusBar.setStatusMsg(_parent._GUI._FieldBar.getRealText()+", I");
										_parent._GUI._StatusBar.setStatusMsg(_parent._GUI._FieldBar.getImgText()+")\n");
										break;
	    		case DivField.COMPLEXD:	MonadComplexD tMonadCD = tMSpotPnl.getMonadCD();
										ComplexD tFieldCD = new ComplexD(	(tNSpotPnl.getNyadCD()).getProto().getFieldType(), 
																			Double.parseDouble(_parent._GUI._FieldBar.getRealText()),
																			Double.parseDouble(_parent._GUI._FieldBar.getImgText()));
										tMonadCD.scale(tFieldCD);
										_parent._GUI._StatusBar.setStatusMsg("\tmonad has been rescaled by (R");
										_parent._GUI._StatusBar.setStatusMsg(_parent._GUI._FieldBar.getRealText()+", I");
										_parent._GUI._StatusBar.setStatusMsg(_parent._GUI._FieldBar.getImgText()+")\n");
	    	}
		    tMSpotPnl.setCoefficientDisplay();
	    }
		catch (FieldBinaryException eb)
		{
		    _parent._GUI._StatusBar.setStatusMsg("-->Monad has NOT been rescaled due to field binary exception.\n");
		}
	    
    }
 }