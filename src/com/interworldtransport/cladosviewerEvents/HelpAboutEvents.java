/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.HelpAboutEvents---------------------------------------
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
---com.interworldtransport.cladosviewer.HelpAboutEvents---------------------------------------
*/

<<<<<<< HEAD
package com.interworldtransport.cladosviewer;
import javax.swing.*;
=======
package com.interworldtransport.cladosviewerEvents;
import javax.swing.*;

import com.interworldtransport.cladosviewer.AboutDialog;

>>>>>>> 59d0d998ca7f6aa42603ccf96a8be5d0fb0731c7
import java.awt.event.*;

/** com.interworldtransport.cladosviewer.HelpAboutEvents
 * This class shows the About dialog box and its related information.
 *
<<<<<<< HEAD
 * @version 0.80, $Date: 2005/07/25 01:44:25 $
=======
 * @version 0.85
>>>>>>> 59d0d998ca7f6aa42603ccf96a8be5d0fb0731c7
 * @author Dr Alfred W Differ
 */
public class HelpAboutEvents implements ActionListener
{
<<<<<<< HEAD

    protected HelpEvents		Parent;
    protected ViewerMenu		ParentGUIMenu;
    protected JMenuItem			ControlIt;

/** This is the default constructor.
 */
    public HelpAboutEvents(ViewerMenu pGUIMenu, JMenuItem pHelp, HelpEvents pParent)
    {
	this.ParentGUIMenu=pGUIMenu;
	this.ControlIt=pHelp;
	this.Parent=pParent;
	this.ControlIt.addActionListener(this);

    }//end of HelpAboutEvents Menu constructor
=======
   
    protected JMenuItem			_control;
    protected HelpEvents		_parent;

/** This is the default constructor.
 */
    public HelpAboutEvents(JMenuItem pHelp, HelpEvents pParent)
    {
		_control=pHelp;
		_parent=pParent;
		_control.addActionListener(this);
    }
>>>>>>> 59d0d998ca7f6aa42603ccf96a8be5d0fb0731c7

/** This is the actual action to be performed by this menu item.
 */
    public void actionPerformed(ActionEvent evt)
    {
<<<<<<< HEAD
	//Show the about feature;
	String tempVersion = ParentGUIMenu.ParentGUI.IniProps.getProperty("MonadViewer.Desktop.Version");
	String tempUserName = ParentGUIMenu.ParentGUI.IniProps.getProperty("MonadViewer.User.Name");

	StringBuffer content = new StringBuffer();

	content.append("Monad Viewer ");
	content.append(tempVersion);
	content.append("\n\n");

	content.append("Copyright 2005 Interworld Transport");
	content.append("\n\n");

	content.append("Web Site: http://www.interworldtransport.com\n\n");

	content.append("Developers:\n");
	content.append("  Dr. Alfred Differ - Physics, Java\n");
	content.append("  Your name could be here! \n\n");

	content.append("Licensed to  ");
	content.append(tempUserName);
	content.append("\n\n");

	content.append("This program is free software; you can redistribute it and/or modify ");
	content.append("it under the terms of the GNU General Public License as published by the Free ");
	content.append("Software Foundation; version 2 of the License, or (at your option) any later version.\n\n");

	content.append("This program is distributed in the hope that it will be useful, ");
	content.append("but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY ");
	content.append("or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.");

	String contentstring = new String(content);

	AboutDialog abt = new AboutDialog(this.ParentGUIMenu.ParentGUI, contentstring);
    }//end of action performed method.

}//end of HelpAboutEvents class
=======
    	new AboutDialog(_parent._GUI);
    }
}
>>>>>>> 59d0d998ca7f6aa42603ccf96a8be5d0fb0731c7
