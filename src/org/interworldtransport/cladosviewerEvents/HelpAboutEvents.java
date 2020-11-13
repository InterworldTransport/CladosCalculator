/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.HelpAboutEvents<br>
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
 * ---org.interworldtransport.cladosviewer.HelpAboutEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerEvents;
import javax.swing.*;

import org.interworldtransport.cladosviewer.AboutDialog;

import java.awt.event.*;

/** org.interworldtransport.cladosviewer.HelpAboutEvents
 * This class shows the About dialog box and its related information.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class HelpAboutEvents implements ActionListener
{
   
    protected JMenuItem			_control;
    protected HelpEvents		_parent;

/** 
 * This is the default constructor.
 * @param pHelp
 *  JMenuItem
 * This is a reference to the 'Help' Menu parent
 * @param pParent
 * 	ToolsEvents
 * This is a reference to the HelpEvent parent event handler
 */
    public HelpAboutEvents(JMenuItem pHelp, HelpEvents pParent)
    {
		_control=pHelp;
		_parent=pParent;
		_control.addActionListener(this);
    }

/** This is the actual action to be performed by this menu item.
 */
    @Override
	public void actionPerformed(ActionEvent evt)
    {
    	new AboutDialog(_parent._GUI);
    }
}
