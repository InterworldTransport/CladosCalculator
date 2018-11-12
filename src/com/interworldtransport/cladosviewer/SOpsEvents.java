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

package com.interworldtransport.cladosviewer;
import java.awt.event.*;

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
    protected SOpsInverseEvents		invs;
    protected SOpsInvertEvents		invt;
    protected SOpsReverseEvents		rev;
    protected SOpsScaleEvents		scale;
    protected SOpsGradePartEvents	gradep;
    protected SOpsGradeSupressEvents	grades;

    protected ViewerMenu 		ParentGUIMenu;

/** This is the default constructor.  The event structure of the
 *  menu starts here and finishes with the child menu items.
 */
    public SOpsEvents(ViewerMenu pTheGUIMenu)
    {
	this.ParentGUIMenu=pTheGUIMenu;

	this.grade = new SOpsGradeEvents(	ParentGUIMenu,
						ParentGUIMenu.mniisSGrade,
						this);
	this.mag = new SOpsMagnitudeEvents(	ParentGUIMenu,
						ParentGUIMenu.mniMagnitudeOf,
						this);
	this.sqmag = new SOpsSQMagnitudeEvents(	ParentGUIMenu,
						ParentGUIMenu.mniSQMagnitudeOf,
						this);
	this.norm = new SOpsNormalizeEvents(	ParentGUIMenu,
						ParentGUIMenu.mniNormalize,
						this);
	this.dual = new SOpsLocalDualEvents(	ParentGUIMenu,
						ParentGUIMenu.mniLocalDual,
						this);
	this.invs = new SOpsInverseEvents(	ParentGUIMenu,
						ParentGUIMenu.mniInverse,
						this);
	this.invt = new SOpsInvertEvents(	ParentGUIMenu,
						ParentGUIMenu.mniInvert,
						this);
	this.rev = new SOpsReverseEvents(	ParentGUIMenu,
						ParentGUIMenu.mniReverse,
						this);
	this.scale = new SOpsScaleEvents(	ParentGUIMenu,
						ParentGUIMenu.mniScale,
						this);
	this.gradep = new SOpsGradePartEvents(	ParentGUIMenu,
						ParentGUIMenu.mniGradePart,
						this);
	this.grades = new SOpsGradeSupressEvents(	ParentGUIMenu,
						ParentGUIMenu.mniGradeSupress,
						this);

    }//end of SOpsEvents Menu constructor

/** This is the default action to be performed by all members of the menu.
 *  It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
	;
    }//end of action performed method.

    }//end of SOpsEvents class
