/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.FileEvents-----------------------------------------
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
---com.interworldtransport.cladosviewer.FileEvents-----------------------------------------
*/

package com.interworldtransport.cladosviewerEvents;
import java.awt.event.*;

import com.interworldtransport.cladosviewer.ViewerMenu;

/** com.interworldtransport.cladosviewer.FileEvents
 * This class groups the event listeners associated with the File menu.  It may
 * be used in the future to act on events associated with the entire File menu
 * by having it register as a Listener with all of its controlled listeners.
 * The controlled listeners will create an event or call their parent.  It could
 * also register with all the components to which its listeners register..maybe.
 *
 * @version 0.80, $Date: 2005/07/31 05:00:25 $
 * @author Dr Alfred W Differ
 */
public class FileEvents implements ActionListener
{
    protected FileSaveEvents		sp;
    protected FileSaveAsEvents		sa;
    protected FileExitEvents		ex;

    protected ViewerMenu 		ParentGUIMenu;


/** This is the default constructor.  The event structure of the File
 *  menu starts here and finishes with the child menu items.
 */
    public FileEvents(ViewerMenu pTheGUIMenu)
    {
    	this.ParentGUIMenu=pTheGUIMenu;
    	this.sp = new FileSaveEvents(	ParentGUIMenu,
    									ParentGUIMenu.mniSave,
    									this);
    	this.sa = new FileSaveAsEvents(	ParentGUIMenu,
    									ParentGUIMenu.mniSaveAs,
    									this);
    	this.ex = new FileExitEvents(	ParentGUIMenu,
    									ParentGUIMenu.mniExit,
    									this);
    }

/** This is the default action to be performed by all members of the File menu.
 *  It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
	;
    }//end of action performed method.

    }
