/**
 * <h2>Copyright</h2> © 2021 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.FileSaveEvents<br>
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
 * ---org.interworldtransport.cladosviewer.FileSaveEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerEvents;

import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import org.interworldtransport.cladosviewer.ErrorDialog;

/**
 * This class manages all events relating to the saving of the current state of
 * the application.
 *
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public class FileSaveEvents implements ActionListener {
	protected JMenuItem _control;
	protected FileEvents _parent;

	/**
	 * This is the default constructor.
	 * 
	 * @param pExit   JMenuItem This is a reference to the 'File' Menu parent
	 * @param pParent HelpEvents This is a reference to the FileEvents parent event
	 *                handler
	 */
	public FileSaveEvents(JMenuItem pExit, FileEvents pParent) {
		_control = pExit;
		_control.addActionListener(this);
		_parent = pParent;
	}

	/**
	 * This is the actual action to be performed by this member of the File menu.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (_parent._GUI.IniProps.getProperty("Desktop.File.Snapshot") != null) // save to file described in conf
																				// setting
		{
			File fIni = new File(_parent._GUI.IniProps.getProperty("Desktop.File.Snapshot"));
			if (!(fIni.exists() & fIni.isFile() & fIni.canWrite())) {
				_parent.sa.actionPerformed(evt); // Defer to Save As event
				return;
			}
			try {
				FileWriter saveItTo = new FileWriter(fIni, false);
				saveItTo.write(_parent.makeSnapshotContent());
				saveItTo.write("\r\n");
				saveItTo.flush();
				saveItTo.close();
				_parent._GUI.appStatusBar.setStatusMsg("-->Stack Snapshot SAVED.\n");
			} catch (IOException e) {
				ErrorDialog.show("Stack Snapshot NOT saved. IO Exception at Save menu.", "IO Exception");
			}
		} else
			_parent.sa.actionPerformed(evt); // Defer to Save As event
	}
}