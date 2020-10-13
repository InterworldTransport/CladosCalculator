/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.NOpsAntiSymmMultEvents<br>
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
 * ---com.interworldtransport.cladosviewer.NOpsAntiSymmMultEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosF.DivField;
import com.interworldtransport.cladosFExceptions.FieldBinaryException;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.NyadPanel;

import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.COpsWedgeEvents
 *  This class manages events relating to a complex operation.
 *  Antisymmetric Multiply this Monad with another Monad.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class NOpsAntiSymmMultEvents implements ActionListener
 {
    protected JMenuItem 		_control;
    protected NOpsParentEvents 	_parent;

/** 
 * This is the default constructor.
 * @param pmniControlled
 *  JMenuItem
 * This is a reference to the Menu Item for which this event acts.
 * @param pParent
 * 	COpsEvents
 * This is a reference to the NOpsParentEvents parent event handler
 */
    public NOpsAntiSymmMultEvents(	JMenuItem 			pmniControlled,
    								NOpsParentEvents 	pParent)
    {
		_control=pmniControlled;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** This is the actual action to be performed by this member of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	int indxNydPnlSlctd = _parent._GUI._GeometryDisplay.getPaneFocus();
    	if (indxNydPnlSlctd<0 | indxNydPnlSlctd == _parent._GUI._GeometryDisplay.getNyadListSize()-1) 
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nNo nyad in the focus... or the last one is.\n");
    		return;	
    	}
    	
    	NyadPanel tSpot = _parent._GUI._GeometryDisplay.getNyadPanel(indxNydPnlSlctd);
    	NyadPanel tSpotPlus = _parent._GUI._GeometryDisplay.getNyadPanel(indxNydPnlSlctd+1);
    	
    	int indxMndPnlSlctd = tSpot.getPaneFocus();
    	if (indxMndPnlSlctd<0 | indxNydPnlSlctd > tSpotPlus.getMonadListSize()) 
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nMultiplication needs two monads at the same index in a nyad. Nothing done.\n");
    		return;
    	}
    	
    	MonadPanel temp0=tSpot.getMonadPanel(indxMndPnlSlctd);
    	MonadPanel temp1=tSpotPlus.getMonadPanel(indxMndPnlSlctd);
    		
    	try
    	{
    		switch (temp0.getRepMode())
    		{
    			case DivField.REALF:	(temp0.getMonadRF()).multiplyAntisymm(temp1.getMonadRF());
    									break;
    			case DivField.REALD:	(temp0.getMonadRD()).multiplyAntisymm(temp1.getMonadRD());
    									break;
    			case DivField.COMPLEXF:	(temp0.getMonadCF()).multiplyAntisymm(temp1.getMonadCF());
										break;
    			case DivField.COMPLEXD:	(temp0.getMonadCD()).multiplyAntisymm(temp1.getMonadCD());
    		}
    		temp0.setCoefficientDisplay();
    	}
    	catch (FieldBinaryException eb)
		{
			_parent._GUI._StatusBar.setStatusMsg("\nField Binary error between second and first monads. Nothing done.\n");
		}
		catch (CladosMonadException e)
		{
			_parent._GUI._StatusBar.setStatusMsg("\nReference Match error between second and first monads. Nothing done.\n");
		}
    }
 }
