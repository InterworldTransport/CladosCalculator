/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.BOpsEqualEvents<br>
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
 * ---com.interworldtransport.cladosviewer.BOpsEqualEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package com.interworldtransport.cladosviewerEvents;

import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosG.NyadRealF;

import java.awt.event.*;
import javax.swing.*;

/** 
 *  This class manages events relating to the answering of a boolean question.
 *  Is the selected nyad equal to the one following it on the stack?
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class BOpsEqualEvents implements ActionListener
 {
    protected BOpsEvents 		_parent;
    protected JMenuItem 		_control;

/** 
 * This is the default constructor.
 * @param pmniControlled
 *  JMenuItem
 * This is a reference to the Menu Item for which this event acts.
 * @param pParent
 * 	BOpsEvents
 * This is a reference to the BOpsEvents parent event handler
 */
    public BOpsEqualEvents(	JMenuItem pmniControlled,
    						BOpsEvents pParent)
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
    	
    	int tNyadFirstIndex=_parent._GUI._GeometryDisplay.getPaneFocus();
    	if (tNyadFirstIndex<0) return;
    	if (tNyadFirstIndex>=_parent._GUI._GeometryDisplay.getNyadListSize()-1)
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\t\tselected nyad is at the end of the stack so no comparison attempted\n");
    		return;
    	}
    	if (NyadRealF.isMEqual(	_parent._GUI._GeometryDisplay.getNyadPanel(tNyadFirstIndex).getNyad(), 
    							_parent._GUI._GeometryDisplay.getNyadPanel(tNyadFirstIndex+1).getNyad()))
    		_parent._GUI._StatusBar.setStatusMsg("\tselected nyad is equal to the next nyad\n");
    	else
    		_parent._GUI._StatusBar.setStatusMsg("\tselected nyad is NOT equal to the next nyad\n");		
    }
 }