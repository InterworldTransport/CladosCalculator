/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.HelpSupportEvents-------------------------------------
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
---com.interworldtransport.cladosviewer.HelpSupportEvents-------------------------------------
*/
package com.interworldtransport.cladosviewer;
import javax.swing.*;
import java.awt.event.*;

/** com.interworldtransport.cladosviewer.HelpSupportEvents
 * This class shows the support dialog box and its related information.
 *
 * @version 0.80, $Date: 2005/07/25 01:44:25 $
 * @author Dr Alfred W Differ
 */
public class HelpSupportEvents implements ActionListener
{

    protected JMenuItem			ControlIt;
    protected ViewerMenu		ParentGUIMenu;
    protected HelpEvents		Parent;

/** This is the default constructor.
 */
    public HelpSupportEvents(ViewerMenu pGUIMenu, JMenuItem pHelp, HelpEvents pParent)
    {
	this.ParentGUIMenu=pGUIMenu;
	this.Parent=pParent;
	this.ControlIt=pHelp;
	this.ControlIt.addActionListener(this);

    }//end of HelpSupportEvents Menu constructor

/** This is the default action to be performed by all members of the Help menu.
 *  It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
	//Show the support custom dialog box
	String tempVersion = ParentGUIMenu.ParentGUI.IniProps.getProperty("MonadViewer.Desktop.Version");

	StringBuffer content = new StringBuffer();

	content.append("Monad Viewer ");
	content.append(tempVersion);
	content.append("\n\n");
	content.append("Web Site: http://clados.sf.net\n\n");

	content.append("For support issues that would help us make a better viewer please visit ");
	content.append("the project home page.  From this page you should be able to find the Viewer's ");
	content.append("project site with its associated mailing lists and bulletin boards.  ");
	content.append("Please list your support issues on the appropriate bulletin board and sign up for ");
	content.append("any mailing lists that interest you.\n\n");
	content.append("For complex support or licensing issues, please contact Dr Alfred Differ at adiffer@gmail.com");

	String contentstring = new String(content);

	SupportDialog spt = new SupportDialog(this.ParentGUIMenu.ParentGUI, contentstring);
    }//end of action performed method.


}//end of HelpSupportEvents class
