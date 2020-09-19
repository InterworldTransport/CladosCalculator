/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.COpsEvents<br>
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
 * ---com.interworldtransport.cladosviewer.COpsEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;
import java.awt.event.*;

import com.interworldtransport.cladosviewer.CladosCalculator;
import com.interworldtransport.cladosviewer.ViewerMenu;

/** com.interworldtransport.cladosviewer.COpsEvents
 * This class groups the event listeners associated with the Boolean Operations
 * menu.  It may be used in the future to act on events associated with the
 * entire menu by having it register as a Listener with all of its controlled
 * listeners.  The controlled listeners will create an event or call their
 * parent.  It could also register with all the components to which its
 * listeners register..maybe.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class COpsEvents implements ActionListener
{
    protected COpsAddEvents			add;
    protected COpsSubtractEvents	sub;
    protected COpsLMultEvents		lmult;
    protected COpsRMultEvents		rmult;
    protected COpsDotEvents			symm;
    protected COpsWedgeEvents		asymm;
    //protected COpsRotateEvents	rot;
    //protected COpsTranslateEvents	trans;

    protected ViewerMenu 		_GUIMenu;
    protected CladosCalculator		_GUI;

/** This is the default constructor.  The event structure of the
 *  menu starts here and finishes with the child menu items.
 *  @param pTheGUIMenu
 *  ViewerMenu
 * This is a reference to the owner menu containing this one.
 */
    public COpsEvents(ViewerMenu pTheGUIMenu)
    {
		_GUIMenu=pTheGUIMenu;
		_GUI=_GUIMenu._parentGUI;

		add = new COpsAddEvents(		_GUIMenu.mniAdd, this);
		sub = new COpsSubtractEvents(	_GUIMenu.mniSubtract, this);
		lmult = new COpsLMultEvents(	_GUIMenu.mniLeftMultiply, this);
		rmult = new COpsRMultEvents(	_GUIMenu.mniRightMultiply, this);
		symm = new COpsDotEvents(		_GUIMenu.mniSymm, this);
		asymm = new COpsWedgeEvents(	_GUIMenu.mniASymm, this);
    }

/** This is the default action to be performed by all members of the menu.
 *  It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
	;
    }//end of action performed method.

    }//end of COpsEvents class
