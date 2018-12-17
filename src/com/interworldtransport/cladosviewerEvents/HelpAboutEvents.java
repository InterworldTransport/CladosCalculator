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

package com.interworldtransport.cladosviewerEvents;
import javax.swing.*;

import com.interworldtransport.cladosviewer.AboutDialog;

import java.awt.event.*;

/** com.interworldtransport.cladosviewer.HelpAboutEvents
 * This class shows the About dialog box and its related information.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class HelpAboutEvents implements ActionListener
{
   
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

/** This is the actual action to be performed by this menu item.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	new AboutDialog(_parent._GUI);
    }
}
