/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewerEvents.HelpSupportEvents<br>
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
 * ---com.interworldtransport.cladosviewerEvents.HelpSupportEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package com.interworldtransport.cladosviewerEvents;
import javax.swing.*;

import com.interworldtransport.cladosviewer.SupportDialog;
import com.interworldtransport.cladosviewer.ViewerMenu;

import java.awt.event.*;

/** com.interworldtransport.cladosviewer.HelpSupportEvents
 * This class shows the support dialog box and its related information.
 *
 * @version 0.85
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
		ParentGUIMenu=pGUIMenu;
		Parent=pParent;
		ControlIt=pHelp;
		ControlIt.addActionListener(this);
    }

/** This is the default action to be performed by all members of the Help menu.
 *  It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
		String tempVersion = ParentGUIMenu._parentGUI.IniProps.getProperty("MonadViewer.Desktop.Version");
	
		StringBuffer content = new StringBuffer();
	
		content.append("Monad Viewer ");
		content.append(tempVersion);
		content.append("\n\n");
		content.append("Web Site: https://github.com/InterworldTransport/CladosViewer\n\n");
	
		content.append("For support issues that would help us make a better viewer please visit ");
		content.append("the GitHub home page.  From this page you should be able to find the Viewer's ");
		content.append("associated docs and support features. Please list your support issues there.\n\n");
		content.append("For complex support or licensing issues, please contact Dr Alfred Differ at adiffer@gmail.com");
	
		String contentstring = new String(content);
		new SupportDialog(this.ParentGUIMenu._parentGUI, contentstring);
    }
}
