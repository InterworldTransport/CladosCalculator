/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.MOpsGradeSupressEvents<br>
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
 * ---com.interworldtransport.cladosviewer.MOpsGradeSupressEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosF.DivField;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.NyadPanel;

import java.awt.event.*;
import javax.swing.*;

/**
 *  This class manages events relating to a simple requirement
 *  Limit this Monad to everything except a particular findgrade.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class MOpsGradeSupressEvents implements ActionListener
 {	
    protected JMenuItem 		_control;
    protected MOpsParentEvents 		_parent;

/** 
 * This is the default constructor.
 * @param pmniControlled
 *  JMenuItem
 * This is a reference to the Menu Item for which this event acts.
 * @param pParent
 * 	NOpsParentEvents
 * This is a reference to the NOpsParentEvents parent event handler
 */
    public MOpsGradeSupressEvents(	JMenuItem pmniControlled,
    								MOpsParentEvents pParent)
    {
		_control=pmniControlled;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the menu.
 * This is the complement of the GradePart method.
 * Basically, the monad in focus has a particular findgrade sliced out (set to zero) while others are kept as is.
 * 
 * A future version of the  method must use the findgrade represented in 
 * the reference frame instead. Fourier decomposition is done against that frame 
 * and not the canonical one most of the time. That means the getSuppress(short) method
 * will channel through the ReferenceFrame of the monad.
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
    		_parent._GUI._StatusBar.setStatusMsg("\nGradeSuppress Operation must have a monad in focus. Nothing done.\n");
    		return;
    	}
    	
    	MonadPanel tMSpotPnl=tNSpotPnl.getMonadPanel(tNSpotPnl.getPaneFocus());
    	
    	try
    	{
    		short tGrade = (short) Float.parseFloat(_parent._GUI._FieldBar.getRealText());
        	switch (tMSpotPnl.getRepMode())
        	{
    	    	case DivField.REALF: 	tMSpotPnl.getMonadRF().gradeSuppress(tGrade);
    							    	break;
    	    	case DivField.REALD: 	tMSpotPnl.getMonadRD().gradeSuppress(tGrade);
    							    	break;
    	    	case DivField.COMPLEXF:	tMSpotPnl.getMonadCF().gradeSuppress(tGrade);
    							    	break;
    	    	case DivField.COMPLEXD:	tMSpotPnl.getMonadCD().gradeSuppress(tGrade);	
        	}
        	tMSpotPnl.setCoefficientDisplay();
	    	_parent._GUI._StatusBar.setStatusMsg("-->Selected monad has been cut at "+tGrade+"-findgrade.\n");
    	}
    	catch (NullPointerException eNull)
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nGradeSuppress Operation must have a real # in the FieldBar. Nothing done.\n");
    		return;
    	}
    	catch (NumberFormatException eFormat)
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nGradeSuppress Operation must have a parse-able real # in the FieldBar. Nothing done.\n");
    		return;
    	}
    }
 }