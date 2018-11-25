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
import com.interworldtransport.cladosviewer.ViewerMenu;

import java.awt.event.*;

/** com.interworldtransport.cladosviewer.HelpAboutEvents
 * This class shows the About dialog box and its related information.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class HelpAboutEvents implements ActionListener
{
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

/** This is the actual action to be performed by this menu item.
 */
    public void actionPerformed(ActionEvent evt)
    {
	//Show the about feature;
	String tempVersion = ParentGUIMenu._parentGUI.IniProps.getProperty("MonadViewer.Desktop.Version");
	String tempUserName = ParentGUIMenu._parentGUI.IniProps.getProperty("MonadViewer.User.Name");

	StringBuffer content = new StringBuffer();

	content.append("Monad Viewer ");
	content.append(tempVersion);
	content.append("\n\n");

	content.append("Copyright 2018 Alfred Differ");
	content.append("\n\n");

	content.append("Web Site: https://github.com/InterworldTransport/CladosViewer\n\n");

	content.append("Developers:\n");
	content.append("  Dr. Alfred Differ - Physics, Java\n");
	content.append("  Your name could be here! \n\n");

	content.append("Licensed to {");
	content.append(tempUserName);
	content.append("}\n\n");
	
	content.append("This program is distributed in the hope that it will be useful, ");
	content.append("it under the terms of the GNU Affero General Public License as ");
	content.append("published by the Free Software Foundation, either version 3 of the ");
	content.append("License, or (at your option) any later version. \n\n");

	content.append("This program is distributed in the hope that it will be useful, ");
	content.append("but WITHOUT ANY WARRANTY; without even the implied warranty of ");
	content.append("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the ");
	content.append("GNU Affero General Public License for more details.\n\n");
	
	content.append("Use of this code or executable objects derived from it by the Licensee ");
	content.append("states their willingness to accept the terms of the license.\n\n");
	
	content.append("You should have received a copy of the GNU Affero General Public License ");
	content.append("along with this program.  If not, see <https://www.gnu.org/licenses/>.\n");
	
	String contentstring = new String(content);
	new AboutDialog(this.ParentGUIMenu._parentGUI, contentstring);
    }
}
