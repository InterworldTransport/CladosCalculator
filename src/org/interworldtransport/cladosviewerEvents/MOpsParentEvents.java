/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.MOpsParentEvents<br>
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
 * ---org.interworldtransport.cladosviewer.MOpsParentEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package org.interworldtransport.cladosviewerEvents;

import org.interworldtransport.cladosviewer.CladosCalculator;
import org.interworldtransport.cladosviewer.ViewerMenu;

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
    public	 	MOpsFindGradeEvents		findgrade;
    public	 	MOpsHasGradeEvents		hasgrade;
    public	 	MOpsMagnitudeEvents		mag;
    public	 	MOpsSQMagnitudeEvents	sqmag;
    public	 	MOpsNormalizeEvents		norm;
    public	 	MOpsLocalDualEvents		dualLeft;
    public	 	MOpsLocalDualEvents		dualRight;
    public	 	MOpsInvertEvents		invt;
    public	 	MOpsReverseEvents		rev;
    public		MOpsScaleEvents			scale;
    public	 	MOpsGradePartEvents		gradep;
    public	 	MOpsGradeSuppressEvents	grades;
    
	public	 	MOpsGradeEvents				grade;
	public	 	MOpsMultGradeEvents			mgrade;
    public	 	MOpsNilpotentEvents			nilp;
    public	 	MOpsIdempotentEvents		idemp;
    public	 	MOpsScaledIdempotentEvents	midemp;

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

		findgrade = new MOpsFindGradeEvents(	_GUIMenu.mniisSGrade, this);
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
		grades = new MOpsGradeSuppressEvents(	_GUIMenu.mniGradeCut, this);
		
		mgrade = new MOpsMultGradeEvents(		_GUIMenu.mniisMultiGrade, this);
		grade = new MOpsGradeEvents(			_GUIMenu.mniisGrade, this);
		nilp = new MOpsNilpotentEvents(			_GUIMenu.mniisNilpotent, this);
		idemp = new MOpsIdempotentEvents(		_GUIMenu.mniisIdempotent, this);
		midemp = new MOpsScaledIdempotentEvents(_GUIMenu.mniisScaledIdempotent, this);
    }

}