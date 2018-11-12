/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.ToolsCreate---------------------------------------
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
---com.interworldtransport.cladosviewer.ToolsCreate---------------------------------------
*/

package com.interworldtransport.cladosviewer;
import com.interworldtransport.clados.*;
import javax.swing.*;
import java.awt.event.*;

/** com.interworldtransport.cladosviewer.ToolsCreate
 * This class shows the Create Monad dialog box.
 *
 * @version 0.80, $Date: 2005/08/25 06:36:13 $
 * @author Dr Alfred W Differ
 */
public class ToolsCreate implements ActionListener
{

    protected ToolsEvents		Parent;
    protected ViewerMenu		ParentGUIMenu;
    protected JMenuItem			ControlIt;

/** This is the default constructor.
 */
    public ToolsCreate(ViewerMenu pGUIMenu, JMenuItem pOpt, ToolsEvents pParent)
    		throws 		UtilitiesException, BadSignatureException
    {
	this.ParentGUIMenu=pGUIMenu;
	this.ControlIt=pOpt;
	this.Parent=pParent;
	this.ControlIt.addActionListener(this);

    }//end of ToolsCreate Menu constructor

/** This is the actual action to be performed by this menu item.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	StringBuffer content = new StringBuffer();
    	content.append(" ");
    	String contentstring = new String(content);
    	try
    	{
    		CreateDialog create = new CreateDialog(this.ParentGUIMenu.ParentGUI, contentstring);
    	}
    	catch (UtilitiesException e)
    	{
    		//Do nothing.  Exception implies user doesn't get to create
    		//a new Monad, so nothing is the correct action.
    		System.out.println("Couldn't construct create dialog.");
    	}
    	catch (BadSignatureException es)
    	{
    		//Do nothing.  Exception implies user doesn't get to create
    		//a new Monad, so nothing is the correct action.
    		System.out.println("Couldn't construct create dialog.");
    	}
    	
    }
    
}
