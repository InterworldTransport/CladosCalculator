/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.ToolsCreate<br>
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
 * ---com.interworldtransport.cladosviewer.ToolsCreate<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;

import com.interworldtransport.cladosF.DivField;
import com.interworldtransport.cladosviewer.CreateDialog;

import javax.swing.*;
import java.awt.event.*;

/** 
 * This class shows the Create Nyad dialog box from the menu.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class ToolsCreate implements ActionListener
{

    protected ToolsEvents		_parent;
    protected JMenuItem			_control;

/** 
 * This is the default constructor.
 * @param pOpt
 *  JMenuItem
 * This is a reference to the 'Tools' Menu parent
 * @param pParent
 * 	ToolsEvents
 * This is a reference to the ToolsEvent parent event handler
 */
    public ToolsCreate(JMenuItem pOpt, ToolsEvents pParent)
    {
		_control=pOpt;
		_parent=pParent;
		_control.addActionListener(this);
    }

/** 
 * This is the actual action to be performed by this menu item.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	String tSpot;
    	if (_parent._GUI._GeometryDisplay.getNyadListSize()>0)
    			tSpot = _parent._GUI._GeometryDisplay.getNyadPanel(_parent._GUI._GeometryDisplay.getPaneFocus()).getRepMode();
    	else 	tSpot = _parent._GUI._FieldBar.getRepMode();
    	
    	CreateDialog.createNyad(_parent._GUI, tSpot);
    	
    	// If no CreateDialog is instantiated, nothing will appear to happen.
    	// If one is, then there will be a link to the parent GUI to report errors.
    	// Thus there is no way to report a failure to the GUI because I'm 
    	// using a static method here.
    	
    	// NOTE that .createMonad isn't an action. That's because a NyadPanel takes
    	// responsibility for it. That means that particular event never reaches
    	// the event model. If there is ever a need to revert to creating orphaned monads, 
    	// this action would be changed to check for which kind of command arrived.
    	// if (evt.getActionCommand().command.equals("blah-blah"))
    	
    	// I don't intend to write a calculator that can stack orphaned monads at the 
    	// same level as nyads. Just use an order=1 nyad.
    }
}