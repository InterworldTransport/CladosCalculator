/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.MOpsIdempotentEvents<br>
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
 * ---com.interworldtransport.cladosviewer.MOpsIdempotentEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;

import com.interworldtransport.cladosFExceptions.FieldBinaryException;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.NyadPanel;

import java.awt.event.*;
import javax.swing.*;

/** 
 *  This class manages events relating to the answering of a boolean question.
 *  Is the selected monad idempotent?
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class MOpsIdempotentEvents implements ActionListener
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
    public MOpsIdempotentEvents(JMenuItem			pmniControlled,
    							MOpsParentEvents 	pParent)
    {
		_control=pmniControlled;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the menu.
 * The Monad with focus is tested to see if it is idempotent.
 * If it is (or isn't) the test is reported to the StatusBar.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	int indexNyadPanelSelected = _parent._GUI.appGeometryView.getPaneFocus();
    	if (indexNyadPanelSelected<0) 
    	{
    		_parent._GUI.appStatusBar.setStatusMsg("\nNo nyad in the focus.\n");
    		return;	
    	}
    	    	
    	NyadPanel panelNyadSelected=_parent._GUI.appGeometryView.getNyadPanel(indexNyadPanelSelected);
    	int indxMndPnlSlctd = panelNyadSelected.getPaneFocus();
    	if (indxMndPnlSlctd<0) 
    	{
    		_parent._GUI.appStatusBar.setStatusMsg("\nIdempotent Test needs one monad in focus. Nothing done.\n");
    		return;
    	}
    	
    	MonadPanel tSpot = panelNyadSelected.getMonadPanel(indxMndPnlSlctd);
    	boolean test = false;
    	try
    	{
	    	switch (tSpot.getRepMode())
	    	{
		    	case REALF: 	test = MonadRealF.isIdempotent(tSpot.getMonadRF());
								    	break;
		    	case REALD: 	test = MonadRealD.isIdempotent(tSpot.getMonadRD());
								    	break;
		    	case COMPLEXF:	test = MonadComplexF.isIdempotent(tSpot.getMonadCF());
								    	break;
		    	case COMPLEXD:	test = MonadComplexD.isIdempotent(tSpot.getMonadCD());
	    	}
	    	if (test)
				_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad is idempotent.\n");
	    	else
	    		_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad is NOT idempotent.\n");
    	}
		catch (CladosMonadException e)
		{
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad created a CladosMonadException.\n");
			_parent._GUI.appStatusBar.setStatusMsg(e.getSourceMessage());
			_parent._GUI.appStatusBar.setStatusMsg("\n\n");
		}
		catch (FieldBinaryException eb)
		{
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected monad created a FieldBinaryException.\n");
			_parent._GUI.appStatusBar.setStatusMsg(eb.getSourceMessage());
			_parent._GUI.appStatusBar.setStatusMsg("\n\n");
		}
    }
 }