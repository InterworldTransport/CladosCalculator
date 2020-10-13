/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.MOpsParentEvents<br>
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
 * ---com.interworldtransport.cladosviewer.MOpsParentEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package com.interworldtransport.cladosviewerEvents;

import com.interworldtransport.cladosviewer.CladosCalculator;
import com.interworldtransport.cladosviewer.ViewerMenu;

/** 
 * This class groups the event listeners associated with the Simple Operations
 * menu.  It may be used in the future to act on events associated with the
 * entire menu by having it register as a Listener with all of its controlled
 * listeners.  The controlled listeners will create an event or call their
 * parent.  It could also register with all the components to which its
 * listeners register..maybe.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class MOpsParentEvents //implements ActionListener
{
    protected 	MOpsFindGradeEvents		grade;
    protected 	MOpsHasGradeEvents		hasgrade;
    protected 	MOpsMagnitudeEvents		mag;
    protected 	MOpsSQMagnitudeEvents	sqmag;
    public	 	MOpsNormalizeEvents		norm;
    public	 	MOpsLocalDualEvents		dualLeft;
    public	 	MOpsLocalDualEvents		dualRight;
    public	 	MOpsInvertEvents		invt;
    public	 	MOpsReverseEvents		rev;
    public		MOpsScaleEvents			scale;
    public	 	MOpsGradePartEvents		gradep;
    public	 	MOpsGradeSupressEvents	grades;

    protected 	ViewerMenu 				_GUIMenu;
    protected 	CladosCalculator		_GUI;

/** 
 * This is the default constructor.  The event structure of the
 * menu starts here and finishes with the child menu items.
 * @param pTheGUIMenu
 *  ViewerMenu
 * This is a reference to the owner menu containing this one.
 */
    public MOpsParentEvents(ViewerMenu pTheGUIMenu)
    {
		_GUIMenu=pTheGUIMenu;
		_GUI=_GUIMenu._parentGUI;

		grade = new MOpsFindGradeEvents(		_GUIMenu.mniisSGrade, this);
		hasgrade = new MOpsHasGradeEvents(		_GUIMenu.mnihasGrade, this);
		mag = new MOpsMagnitudeEvents(			_GUIMenu.mniMagnitudeOf, this);
		sqmag = new MOpsSQMagnitudeEvents(		_GUIMenu.mniSQMagnitudeOf, this);
		norm = new MOpsNormalizeEvents(			_GUIMenu.mniNormalize, this);
		dualLeft = new MOpsLocalDualEvents(		_GUIMenu.mniDualLeft, this);
		dualRight = new MOpsLocalDualEvents(	_GUIMenu.mniDualRight, this);
		invt = new MOpsInvertEvents(			_GUIMenu.mniInvert, this);
		rev = new MOpsReverseEvents(			_GUIMenu.mniReverse, this);
		scale = new MOpsScaleEvents(			_GUIMenu.mniScale, this);
		gradep = new MOpsGradePartEvents(		_GUIMenu.mniGradeCrop, this);
		grades = new MOpsGradeSupressEvents(	_GUIMenu.mniGradeCut, this);
    }

/** 
 * This is the default action to be performed by all members of the menu.
 * It will be overridden by specific members of the menu.
 */
    //public void actionPerformed(ActionEvent evt)
    //{
    //	;	
    //}
}