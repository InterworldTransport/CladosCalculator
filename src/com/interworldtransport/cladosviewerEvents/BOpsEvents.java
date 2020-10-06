/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.BOpsEvents<br>
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
 * ---com.interworldtransport.cladosviewer.BOpsEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;
import java.awt.event.*;

import com.interworldtransport.cladosviewer.*;

/** 
 * This class groups the event listeners associated with the Boolean Operations
 * menu.  It may be used in the future to act on events associated with the
 * entire menu by having it register as a Listener with all of its controlled
 * listeners.  The controlled listeners will create an event or call their
 * parent.  It could also register with all the components to which its
 * listeners register..maybe. [None of this is done yet, of course.]
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class BOpsEvents implements ActionListener
{
	protected BOpsZeroEvents		zero;
	protected BOpsEqualEvents		equal;
	protected BOpsRMatchEvents		rmatch;
	protected BOpsGradeEvents		grade;
    protected BOpsMGradeEvents		mgrade;
    protected BOpsNilpotentEvents	nilp;
    protected BOpsIdempotentEvents	idemp;
    protected BOpsMIdempotentEvents	midemp;

    protected ViewerMenu 			_GUIMenu;
    protected CladosCalculator		_GUI;

/** 
 * This is the default constructor.  The event structure of the
 * menu starts here and finishes with the child menu items.
 * @param pTheGUIMenu
 *  ViewerMenu
 * This is a reference to the owner menu containing this one.
 */
    public BOpsEvents(ViewerMenu pTheGUIMenu)
    {
		_GUIMenu=pTheGUIMenu;
		_GUI=_GUIMenu._parentGUI;
	
		mgrade = new BOpsMGradeEvents(		_GUIMenu.mniisMultiGrade, this);
		grade = new BOpsGradeEvents(		_GUIMenu.mniisGrade, this);
		nilp = new BOpsNilpotentEvents(		_GUIMenu.mniisNilpotent, this);
		idemp = new BOpsIdempotentEvents(	_GUIMenu.mniisIdempotent, this);
		midemp = new BOpsMIdempotentEvents(	_GUIMenu.mniisScaledIdempotent, this);
		equal = new BOpsEqualEvents(		_GUIMenu.mniisEqual, this);
		zero = new BOpsZeroEvents(			_GUIMenu.mniisZero,	this);
		rmatch = new BOpsRMatchEvents(		_GUIMenu.mniisReferenceMatch, this);
    }

/** 
 * This is the default action to be performed by all members of the menu.
 * It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	;
    }

}
