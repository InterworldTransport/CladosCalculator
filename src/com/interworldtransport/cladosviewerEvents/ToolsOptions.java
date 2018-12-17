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

package com.interworldtransport.cladosviewerEvents;
import javax.swing.*;

import com.interworldtransport.cladosviewer.OptionsDialog;

import java.awt.event.*;

/** com.interworldtransport.cladosviewer.ToolsOptions
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
 */
    public ToolsOptions(JMenuItem pOpt, ToolsEvents pParent)
    {
		_control=pOpt;
		_parent=pParent;
		_control.addActionListener(this);
		
		//TODO
		//Construct the panels that show the configuration key pairs.
		//There should be file readers and writers that enable a re-read 
		//of was and a save of what is shown.
		//Skip the part of the GUI's INIProps that contain the system props?
    }

/** 
 * This is the actual action to be performed by this menu item.
 */
    public void actionPerformed(ActionEvent evt)
    {
	//Show the Configuration file;

	StringBuffer content = new StringBuffer();

	content.append("Fill Here with Configuration File ");

	String contentstring = new String(content);

	//OptionsDialog opt1 = 
			new OptionsDialog(_parent._GUI, contentstring);
    }
}