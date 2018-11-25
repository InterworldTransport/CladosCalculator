/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.FileSaveAsEvents------------------------------------------
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
Use of this code or executable objects derived from it by the Licensee states their
willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
---com.interworldtransport.cladosviewer.FileAsSaveEvents------------------------------------------
*/

package com.interworldtransport.cladosviewerEvents;
import java.awt.event.*;
import javax.swing.*;

import com.interworldtransport.cladosviewer.ViewerMenu;
import com.interworldtransport.cladosviewerExceptions.CantGetSaveException;

import java.io.*;

/** com.interworldtransport.cladosviewer.FileSaveAsEvents
 *  This class manages events relating to the saving of the current state
 *  of the applicaiton.
 *
 * @version 0.80, $Date: 2005/07/31 05:00:25 $
 * @author Dr Alfred W Differ
 */
public class FileSaveAsEvents implements ActionListener
 {
    protected ViewerMenu		ParentGUIMenu;
    protected JMenuItem 		ControlIt;
    protected FileEvents 		Parent;

/** This is the default constructor.
 */
    public FileSaveAsEvents(	ViewerMenu pGUIMenu,
    							JMenuItem pmniExit,
    							FileEvents pParent)
    {
	this.ParentGUIMenu=pGUIMenu;
	this.ControlIt=pmniExit;
	this.ControlIt.addActionListener(this);
	this.Parent=pParent;

    }//end of FileExitEvents constructor

/** This is the actual action to be performed by this member of the File menu.
 */
    public void actionPerformed(ActionEvent evt)

    {
	    try
	    {
		    ParentGUIMenu._parentGUI.saveSnapshot("As");
	    }
	    catch (IOException e)
	    {
		    ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("IO Exception prevented snapshot save.\n");
	    }
	    catch (CantGetSaveException es)
	    {
		    ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("No Save file Exception prevented snapshot save.\n");
	    }
    }

 }
