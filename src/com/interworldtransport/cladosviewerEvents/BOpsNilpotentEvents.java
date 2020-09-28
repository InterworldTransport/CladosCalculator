/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.BOpsNilpotentEvents<br>
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
 * ---com.interworldtransport.cladosviewer.BOpsNilpotentEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosF.DivField;
import com.interworldtransport.cladosFExceptions.*;
import com.interworldtransport.cladosG.MonadComplexD;
import com.interworldtransport.cladosG.MonadComplexF;
import com.interworldtransport.cladosG.MonadRealD;
import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.NyadPanel;

import java.awt.event.*;
import javax.swing.*;

/** 
 *  This class manages events relating to the answering of a boolean question.
 *  Is the selected monad nilpotent?
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class BOpsNilpotentEvents implements ActionListener
 {
    protected JMenuItem 		_control;
    protected BOpsEvents 		_parent;

/** 
 * This is the default constructor.
 * @param pmniControlled
 *  JMenuItem
 * This is a reference to the Menu Item for which this event acts.
 * @param pParent
 * 	BOpsEvents
 * This is a reference to the BOpsEvents parent event handler
 */
    public BOpsNilpotentEvents(	JMenuItem pmniControlled,
								BOpsEvents pParent)
    {
		_control=pmniControlled;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the menu.
 * The monad with focus is tested to see if it is nilpotent.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	int indexNyadPanelSelected = _parent._GUI._GeometryDisplay.getPaneFocus();
    	if (indexNyadPanelSelected<0) 
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nNo nyad in the focus.\n");
    		return;	
    	}
    	    	
    	NyadPanel panelNyadSelected=_parent._GUI._GeometryDisplay.getNyadPanel(indexNyadPanelSelected);
    	int indxMndPnlSlctd = panelNyadSelected.getPaneFocus();
    	if (indxMndPnlSlctd<0) 
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nNilpotent Test needs one monad in focus. Nothing done.\n");
    		return;
    	}
    	
    	MonadPanel tSpot = panelNyadSelected.getMonadPanel(indxMndPnlSlctd);
    	boolean test = false;
    	try
    	{
	    	switch (tSpot.getRepMode())
	    	{
		    	case DivField.REALF: 	test = MonadRealF.isNilpotent(tSpot.getMonadRF());
								    	break;
		    	case DivField.REALD: 	test = MonadRealD.isNilpotent(tSpot.getMonadRD());
								    	break;
		    	case DivField.COMPLEXF:	test = MonadComplexF.isNilpotent(tSpot.getMonadCF());
								    	break;
		    	case DivField.COMPLEXD:	test = MonadComplexD.isNilpotent(tSpot.getMonadCD());
	    	}
	    	if (test)
				_parent._GUI._StatusBar.setStatusMsg("\tselected monad is nilpotent.\n");
	    	else
	    		_parent._GUI._StatusBar.setStatusMsg("\tselected monad is NOT nilpotent.\n");
    	}
		catch (CladosMonadException e)
		{
			_parent._GUI._StatusBar.setStatusMsg("\t\tselected monad created a CladosMonadException.\n");
			_parent._GUI._StatusBar.setStatusMsg(e.getSourceMessage());
			_parent._GUI._StatusBar.setStatusMsg("\n\n");
		}
		catch (FieldBinaryException eb)
		{
			_parent._GUI._StatusBar.setStatusMsg("\t\tselected monad created a FieldBinaryException.\n");
			_parent._GUI._StatusBar.setStatusMsg(eb.getSourceMessage());
			_parent._GUI._StatusBar.setStatusMsg("\n\n");
		}
    }
 }