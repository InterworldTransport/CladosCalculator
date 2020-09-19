/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.SOpsInverseEvents<br>
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
 * ---com.interworldtransport.cladosviewer.SOpsInverseEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;

import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.SOpsInverseEvents
 *  This class manages events relating to a simple operation...
 *  Take the inverse of this Monad.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class SOpsInverseEvents implements ActionListener
 {
    //protected ViewerMenu		ParentGUIMenu;
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
    public SOpsInverseEvents(	//ViewerMenu pGUIMenu,
    							JMenuItem pmniControlled,
    							SOpsEvents pParent)
    {
    	//this.ParentGUIMenu=pGUIMenu;
    	_control=pmniControlled;
    	_control.addActionListener(this);
    	_parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
    //Nothing to do here for now
    /*	
	MonadPanel MP0=ParentGUIMenu.ParentGUI.CenterAll.getNyadPanel(0).getMonadPanel(0);
	Monad Monad0=MP0.getMonad();
	try
	{
		Monad0.Inverse();
	}
	catch (NoInverseException i)
	{
		;
	}
	catch (NoInverseCalculationMethodException c)
	{
		;
	}
	catch (CladosException e)
	{
		;
	}
	MP0.setBottomFields();
	ParentGUIMenu.ParentGUI.StatusLine.setStatusMsg("First Monad has been changed to its inverse if possible.\n");
	*/
    	
    	
    }//end of action performed method.

 }//end of SOpsInverseEvents class
