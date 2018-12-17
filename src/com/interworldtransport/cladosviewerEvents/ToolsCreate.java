/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewerEvents.ToolsCreate<br>
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
 * ---com.interworldtransport.cladosviewerEvents.ToolsCreate<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;

import com.interworldtransport.cladosGExceptions.BadSignatureException;
import com.interworldtransport.cladosGExceptions.CladosMonadException;
import com.interworldtransport.cladosviewer.CreateDialog;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

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

/** This is the default constructor.
 */
    public ToolsCreate(JMenuItem pOpt, ToolsEvents pParent)
    		throws 		UtilitiesException, BadSignatureException
    {
		_control=pOpt;
		_parent=pParent;
		_control.addActionListener(this);
    }

/** This is the actual action to be performed by this menu item.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	try
    	{
    		CreateDialog.createNyad(_parent._GUI);
    	}
    	catch (UtilitiesException e)
    	{
    		//Do nothing.  Exception implies user doesn't get to create
    		//a new Monad, so nothing is the correct action.
    		System.out.println("Couldn't construct create dialog.");
    	}
    	catch (BadSignatureException es)
    	{
    		//Do nothing.  Exception implies user doesn't get to create
    		//a new Monad, so nothing is the correct action.
    		System.out.println("Couldn't construct create dialog.");
    	}
    	catch (CladosMonadException em)
    	{
    		//Do nothing.  Exception implies user doesn't get to create
    		//a new Monad, so nothing is the correct action.
    		System.out.println("Couldn't construct create dialog.");
    	}
    }
}