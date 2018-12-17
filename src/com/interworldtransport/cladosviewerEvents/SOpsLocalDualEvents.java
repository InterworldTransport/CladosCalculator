/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewerEvents.SOpsLocalDualEvents<br>
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
 * ---com.interworldtransport.cladosviewerEvents.SOpsLocalDualEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;

import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.NyadPanel;

import java.awt.event.*;
import javax.swing.*;

/** 
 *  This class manages events relating to a simple operation...
 *  Take the dual of this Monad.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class SOpsLocalDualEvents implements ActionListener
{
    protected JMenuItem 		_control;
    protected SOpsEvents 		_parent;

/** 
 * This is the default constructor.
 */
    public SOpsLocalDualEvents(	JMenuItem pmniControlled,
								SOpsEvents pParent)
    {
		_control=pmniControlled;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	if (_parent._GUI._GeometryDisplay.getPaneFocus()<0) return;
    	String command = evt.getActionCommand();
  
    	NyadPanel tNSpotPnl = _parent._GUI._GeometryDisplay.getNyadPanel(_parent._GUI._GeometryDisplay.getPaneFocus());
    	MonadPanel tMSpotPnl=tNSpotPnl.getMonadPanel(tNSpotPnl.getPaneFocus());
    	
    	if (command.equals("dual left"))
    	{
    		tMSpotPnl.getMonad().dualLeft();
    		_parent._GUI._StatusBar.setStatusMsg("\tselected monad has been 'dualed' from the left.\n");
    	}
    	if (command.equals("dual right"))
    	{
    		tMSpotPnl.getMonad().dualRight();
    		_parent._GUI._StatusBar.setStatusMsg("\tselected monad has been 'dualed' from the right.\n");
    	}
    	tMSpotPnl.setCoefficientDisplay();
    	
    }
 }
