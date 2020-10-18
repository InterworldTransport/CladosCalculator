/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.FileSaveAsEvents<br>
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
 * ---com.interworldtransport.cladosviewer.FileSaveAsEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package com.interworldtransport.cladosviewerEvents;

import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

/** 
 *  This class manages events relating to the saving of the current state
 *  of the application.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class FileSaveAsEvents implements ActionListener
 {
    protected JMenuItem 		_control;
    protected FileEvents 		_parent;

/** 
 * This is the default constructor.
 * @param pExit
 *  JMenuItem
 * This is a reference to the 'File' Menu parent
 * @param pParent
 * 	HelpEvents
 * This is a reference to the FileEvents parent event handler
 */
    public FileSaveAsEvents(	JMenuItem pExit,
    							FileEvents pParent)
    {
		_control=pExit;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the File menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
	    int returnVal = _parent.fc.showSaveDialog(_control);
	    if (returnVal == JFileChooser.APPROVE_OPTION) 
	    {
	    	File fIni = _parent.fc.getSelectedFile();
	    	try 
	    	{
	    		FileWriter saveItTo=new FileWriter(fIni, false);
	    		saveItTo.write(_parent.makeSnapshotContent());
	    		saveItTo.write("\r\n");
	    		saveItTo.flush();
	    		saveItTo.close();
	    		saveItTo = null;
	    		_parent._GUI._StatusBar.setStatusMsg("-->Stack Snapshot SAVED AS "+fIni.getPath()+"\n");
	    		_parent._GUI.IniProps.setProperty("Desktop.Snapshot", fIni.getName());
	    	}
	    	catch (IOException e)
	    	{
	    		_parent._GUI._StatusBar.setStatusMsg("-->Stack Snapshot NOT saved. IO Exception at SaveAs menu.\n");
	    	}
	    	finally
	    	{
	    		fIni = null;
	    	}
	    } 
	  
    }
 }