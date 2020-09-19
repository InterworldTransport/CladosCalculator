<<<<<<< HEAD
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
=======
/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewerEvents.ToolsOptions<br>
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
 * ---com.interworldtransport.cladosviewerEvents.ToolsOptions<br>
 * ------------------------------------------------------------------------ <br>
 */
>>>>>>> 59d0d998ca7f6aa42603ccf96a8be5d0fb0731c7

package com.interworldtransport.cladosviewerEvents;
import javax.swing.*;

<<<<<<< HEAD
import com.interworldtransport.cladosviewer.AboutDialog;

import java.awt.event.*;

/** com.interworldtransport.cladosviewer.HelpAboutEvents
=======
import com.interworldtransport.cladosviewer.OptionsDialog;

import java.awt.event.*;

/** com.interworldtransport.cladosviewer.ToolsOptions
>>>>>>> 59d0d998ca7f6aa42603ccf96a8be5d0fb0731c7
 * This class shows the About dialog box and its related information.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
<<<<<<< HEAD
public class HelpAboutEvents implements ActionListener
{
   
    protected JMenuItem			_control;
    protected HelpEvents		_parent;

/** This is the default constructor.
 */
    public HelpAboutEvents(JMenuItem pHelp, HelpEvents pParent)
    {
		_control=pHelp;
=======
public class ToolsOptions implements ActionListener
{

    protected ToolsEvents		_parent;
    protected JMenuItem			_control;

/** 
 * This is the default constructor.
 */
    public ToolsOptions(JMenuItem pOpt, ToolsEvents pParent)
    {
		_control=pOpt;
>>>>>>> 59d0d998ca7f6aa42603ccf96a8be5d0fb0731c7
		_parent=pParent;
		_control.addActionListener(this);
    }

<<<<<<< HEAD
/** This is the actual action to be performed by this menu item.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	new AboutDialog(_parent._GUI);
    }
}
=======
/** 
 * This is the actual action to be performed by this menu item.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	new OptionsDialog(_parent._GUI);
    }
}
>>>>>>> 59d0d998ca7f6aa42603ccf96a8be5d0fb0731c7
