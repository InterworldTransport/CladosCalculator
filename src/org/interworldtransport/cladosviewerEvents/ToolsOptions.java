/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.ToolsOptions<br>
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
 * ---org.interworldtransport.cladosviewer.ToolsOptions<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerEvents;
import javax.swing.*;

import org.interworldtransport.cladosviewer.OptionsDialog;

import java.awt.event.*;

/**
 * This class shows the About dialog box and its related information.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class ToolsOptions implements ActionListener
{

    protected ToolsEvents		_parent;
    protected JMenuItem			_control;

/** 
 * This is the default constructor.
 * @param pOpt
 *  JMenuItem
 * This is a reference to the 'Tools' Menu parent
 * @param pParent
 * 	ToolsEvents
 * This is a reference to the ToolsEvent parent event handler
 */
    public ToolsOptions(JMenuItem pOpt, ToolsEvents pParent)
    {
		_control=pOpt;
		_parent=pParent;
		_control.addActionListener(this);
    }

/** 
 * This is the actual action to be performed by this menu item.
 */
    @Override
	public void actionPerformed(ActionEvent evt)
    {
    	new OptionsDialog(_parent._GUI);
    }
}