/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.BOpsEvents-----------------------------------------
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
---com.interworldtransport.cladosviewer.BOpsEvents-----------------------------------------
*/

package com.interworldtransport.cladosviewer;
import java.awt.event.*;

/** com.interworldtransport.cladosviewer.BOpsEvents
 * This class groups the event listeners associated with the Boolean Operations
 * menu.  It may be used in the future to act on events associated with the
 * entire menu by having it register as a Listener with all of its controlled
 * listeners.  The controlled listeners will create an event or call their
 * parent.  It could also register with all the components to which its
 * listeners register..maybe.
 *
 * @version 0.80, $Date: 2005/07/25 01:44:25 $
 * @author Dr Alfred W Differ
 */
public class BOpsEvents implements ActionListener
{
    protected BOpsMGradeEvents			mgrade;
    protected BOpsGradeEvents			grade;
    protected BOpsNilpotentEvents		nilpotent;
    protected BOpsIdempotentEvents		idempotent;
    protected BOpsMIdempotentEvents		midempotent;
    protected BOpsEqualEvents			equal;
    protected BOpsZeroEvents			zero;
    protected BOpsRMatchEvents			rmatch;

    protected ViewerMenu 			ParentGUIMenu;

/** This is the default constructor.  The event structure of the
 *  menu starts here and finishes with the child menu items.
 */
    public BOpsEvents(ViewerMenu pTheGUIMenu)
    {
	this.ParentGUIMenu=pTheGUIMenu;

	this.mgrade = new BOpsMGradeEvents(	ParentGUIMenu,
						ParentGUIMenu.mniisMultiGrade,
						this);
	this.grade = new BOpsGradeEvents(	ParentGUIMenu,
						ParentGUIMenu.mniisGrade,
						this);
	this.nilpotent = new BOpsNilpotentEvents(	ParentGUIMenu,
						ParentGUIMenu.mniisNilpotent,
						this);
	this.idempotent = new BOpsIdempotentEvents(	ParentGUIMenu,
						ParentGUIMenu.mniisIdempotent,
						this);
	this.midempotent = new BOpsMIdempotentEvents(	ParentGUIMenu,
						ParentGUIMenu.mniisIdempotentMultiple,
						this);
	this.equal = new BOpsEqualEvents(	ParentGUIMenu,
						ParentGUIMenu.mniisEqual,
						this);
	this.zero = new BOpsZeroEvents(		ParentGUIMenu,
						ParentGUIMenu.mniisZero,
						this);
	this.rmatch = new BOpsRMatchEvents(	ParentGUIMenu,
						ParentGUIMenu.mniisReferenceMatch,
						this);
    }//end of BOpsEvents Menu constructor

/** This is the default action to be performed by all members of the menu.
 *  It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
	;
    }//end of action performed method.

    }//end of BOpsEvents class
