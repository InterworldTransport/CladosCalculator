/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.FileExitEvents<br>
 * -------------------------------------------------------------------- <br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.<br>
 * 
 * Use of this code or executable objects derived from it by the Licensee 
 * states their willingness to accept the terms of the license. <br> 
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.<br> 
 * 
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.FileExitEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerEvents;

import java.awt.event.*;
import javax.swing.*;

/**
 * org.interworldtransport.cladosviewer.FileExitEvents This class manages all
 * events relating to the exiting of the applicaiton.
 *
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public class FileExitEvents implements ActionListener {
	/**
	 * This reference points at the controlling object for this part of the event model. 
	 * It's open for later possibilities for scripted ations.
	 */
	protected JMenuItem _control;
	/**
	 * This reference points at the parent menu of the controlling menu item. 
	 * It's open for later possibilities for scripted ations.
	 */
	protected FileEvents _parent;

	/**
	 * This is the default constructor.
	 * 
	 * @param pExit   JMenuItem This is a reference to the 'File' Menu parent
	 * @param pParent HelpEvents This is a reference to the FileEvents parent event
	 *                handler
	 */
	public FileExitEvents(JMenuItem pExit, FileEvents pParent) {
		_control = pExit;
		_control.addActionListener(this);
		_parent = pParent;
	}

	/**
	 * This is the actual action to be performed by this member of the File menu.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		_parent._GUI.terminateModel();
	}
}
