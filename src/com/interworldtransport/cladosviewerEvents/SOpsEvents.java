/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.SOpsEvents-----------------------------------------
<p>
Interworld Transport grants you ("Licensee") a license to this software
under the terms of the GNU General Public License.<br>
A full copy of the license can be found bundled with this package or code file.
<p>
If the license file has become separated from the package, code file, or binary
executable, the Licensee is still expected to read about the license at the
following URL before accepting this material.
<blockquote><code>http://www.opensource.org/gpl-license.html</code></blockquote>
<p>
Use of this code or executable objects derived from it by the Licensee states
their willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
---com.interworldtransport.cladosviewer.SOpsEvents-----------------------------------------
*/

package com.interworldtransport.cladosviewerEvents;
import java.awt.event.*;

import com.interworldtransport.cladosviewer.ViewerMenu;

/** com.interworldtransport.cladosviewer.SOpsEvents
 * This class groups the event listeners associated with the Simple Operations
 * menu.  It may be used in the future to act on events associated with the
 * entire menu by having it register as a Listener with all of its controlled
 * listeners.  The controlled listeners will create an event or call their
 * parent.  It could also register with all the components to which its
 * listeners register..maybe.
 *
 * @version 0.80, $Date: 2005/07/25 01:44:25 $
 * @author Dr Alfred W Differ
 */
public class SOpsEvents implements ActionListener
{
    protected SOpsGradeEvents		grade;
    protected SOpsMagnitudeEvents	mag;
    protected SOpsSQMagnitudeEvents	sqmag;
    protected SOpsNormalizeEvents	norm;
    protected SOpsLocalDualEvents	dual;
    protected SOpsInvertEvents		invt;
    protected SOpsReverseEvents		rev;
    protected SOpsScaleEvents		scale;
    protected SOpsGradePartEvents	gradep;
    protected SOpsGradeSupressEvents	grades;

    protected ViewerMenu 		_parentMenu;

/** This is the default constructor.  The event structure of the
 *  menu starts here and finishes with the child menu items.
 */
    public SOpsEvents(ViewerMenu pTheGUIMenu)
    {
		_parentMenu=pTheGUIMenu;

		grade = new SOpsGradeEvents(_parentMenu,
									_parentMenu.mniisSGrade,
									this);
		mag = new SOpsMagnitudeEvents(	_parentMenu,
										_parentMenu.mniMagnitudeOf,
										this);
		sqmag = new SOpsSQMagnitudeEvents(	_parentMenu,
											_parentMenu.mniSQMagnitudeOf,
											this);
		norm = new SOpsNormalizeEvents(	_parentMenu,
										_parentMenu.mniNormalize,
										this);
		dual = new SOpsLocalDualEvents(	_parentMenu,
										_parentMenu.mniLocalDual,
										this);
		invt = new SOpsInvertEvents(_parentMenu,
									_parentMenu.mniInvert,
									this);
		rev = new SOpsReverseEvents(_parentMenu,
									_parentMenu.mniReverse,
									this);
		scale = new SOpsScaleEvents(_parentMenu,
									_parentMenu.mniScale,
									this);
		gradep = new SOpsGradePartEvents(	_parentMenu,
											_parentMenu.mniGradePart,
											this);
		grades = new SOpsGradeSupressEvents(_parentMenu,
											_parentMenu.mniGradeSupress,
											this);
    }

/** This is the default action to be performed by all members of the menu.
 *  It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	;
    }
}
