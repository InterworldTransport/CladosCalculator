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

import com.interworldtransport.cladosviewer.CladosCalculator;
import com.interworldtransport.cladosviewer.ViewerMenu;

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
public class NOpsParentEvents
{
	public 		NOpsZeroEvents				zero;
	public		NOpsEqualEvents				equal;
	public 		NOpsRMatchEvents			strgrmatch;
	public		NOpsRWeakMatchEvents		weakrmatch;
	
	public		NOpsHasAlgebraMatchEvents	hasalgebra;
	public		NOpsIsScalarAtEvents		scalarAtAlg;
	public		NOpsIsPScalarAtEvents		pscalarAtAlg;
	
	public		NOpsAddEvents				add;
	public		NOpsSubtractEvents			sub;
	public		NOpsLMultEvents				lmult;
	public		NOpsRMultEvents				rmult;
	public		NOpsSymmMultEvents			symm;
	public		NOpsAntiSymmMultEvents		asymm;

    protected 	ViewerMenu 					_GUIMenu;
    protected 	CladosCalculator			_GUI;

/** 
 * This is the default constructor.  The event structure of the
 * menu starts here and finishes with the child menu items.
 * @param pTheGUIMenu
 *  ViewerMenu
 * This is a reference to the owner menu containing this one.
 */
    public NOpsParentEvents(ViewerMenu pTheGUIMenu)
    {
		_GUIMenu=pTheGUIMenu;
		_GUI=_GUIMenu._parentGUI;
		
		equal = new NOpsEqualEvents(			_GUIMenu.mniisEqual, this);
		zero = new NOpsZeroEvents(				_GUIMenu.mniisZero,	this);
		strgrmatch = new NOpsRMatchEvents(		_GUIMenu.mniisStrgReferenceMatch, this);
		weakrmatch = new NOpsRWeakMatchEvents(	_GUIMenu.mniisWeakReferenceMatch, this);
		
		hasalgebra = new NOpsHasAlgebraMatchEvents(	_GUIMenu.mnihasAlgebra, this);
		scalarAtAlg = new NOpsIsScalarAtEvents(		_GUIMenu.mniisScalarAt, this);
		pscalarAtAlg = new NOpsIsPScalarAtEvents(	_GUIMenu.mniisPScalarAt, this);
		
		add = new NOpsAddEvents(				_GUIMenu.mniAdd, this);
		sub = new NOpsSubtractEvents(			_GUIMenu.mniSubtract, this);
		lmult = new NOpsLMultEvents(			_GUIMenu.mniLeftMultiply, this);
		rmult = new NOpsRMultEvents(			_GUIMenu.mniRightMultiply, this);
		symm = new NOpsSymmMultEvents(			_GUIMenu.mniSymm, this);
		asymm = new NOpsAntiSymmMultEvents(		_GUIMenu.mniASymm, this);
    }
}
