/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.HelpSupportEvents<br>
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
 * ---org.interworldtransport.cladosviewer.HelpSupportEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package org.interworldtransport.cladosviewerEvents;
import javax.swing.*;

import org.interworldtransport.cladosviewer.SupportDialog;

import java.awt.event.*;

/** org.interworldtransport.cladosviewer.HelpSupportEvents
 * This class shows the support dialog box and its related information.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class HelpSupportEvents implements ActionListener
{

    protected JMenuItem			_control;
    protected HelpEvents		_parent;

/** 
 * This is the default constructor.
 * @param pHelp
 *  JMenuItem
 * This is a reference to the 'Help' Menu parent
 * @param pParent
 * 	HelpEvents
 * This is a reference to the HelpEvent parent event handler
 */
    public HelpSupportEvents(JMenuItem pHelp, HelpEvents pParent)
    {
		_parent=pParent;
		_control=pHelp;
		_control.addActionListener(this);
    }

/** This is the default action to be performed by all members of the Help menu.
 *  It will be overridden by specific members of the menu.
 */
    @Override
	public void actionPerformed(ActionEvent evt)
    {
		new SupportDialog(_parent._GUI);
    }
}
