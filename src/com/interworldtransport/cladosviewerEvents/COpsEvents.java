/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.COpsEvents-----------------------------------------
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
---com.interworldtransport.cladosviewer.COpsEvents-----------------------------------------
*/

package com.interworldtransport.cladosviewer;
import java.awt.event.*;

/** com.interworldtransport.cladosviewer.COpsEvents
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
public class COpsEvents implements ActionListener
{
    protected COpsAddEvents		add;
    protected COpsSubtractEvents	sub;
    protected COpsLMultEvents		lmult;
    protected COpsRMultEvents		rmult;
    protected COpsDotEvents		dot;
    protected COpsWedgeEvents		wedge;
    protected COpsRotateEvents		rot;
    protected COpsTranslateEvents	trans;

    protected ViewerMenu 		ParentGUIMenu;

/** This is the default constructor.  The event structure of the
 *  menu starts here and finishes with the child menu items.
 */
    public COpsEvents(ViewerMenu pTheGUIMenu)
    {
	this.ParentGUIMenu=pTheGUIMenu;

	this.add = new COpsAddEvents(		ParentGUIMenu,
						ParentGUIMenu.mniAdd,
						this);
	this.sub = new COpsSubtractEvents(	ParentGUIMenu,
						ParentGUIMenu.mniSubtract,
						this);
	this.lmult = new COpsLMultEvents(	ParentGUIMenu,
						ParentGUIMenu.mniLeftMultiply,
						this);
	this.rmult = new COpsRMultEvents(	ParentGUIMenu,
						ParentGUIMenu.mniRightMultiply,
						this);
	this.dot = new COpsDotEvents(		ParentGUIMenu,
						ParentGUIMenu.mniDot,
						this);
	this.wedge = new COpsWedgeEvents(	ParentGUIMenu,
						ParentGUIMenu.mniWedge,
						this);
	this.rot = new COpsRotateEvents(	ParentGUIMenu,
						ParentGUIMenu.mniRotate,
						this);
	this.trans = new COpsTranslateEvents(	ParentGUIMenu,
						ParentGUIMenu.mniTranslate,
						this);
    }//end of COpsEvents Menu constructor

/** This is the default action to be performed by all members of the menu.
 *  It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
	;
    }//end of action performed method.

    }//end of COpsEvents class
