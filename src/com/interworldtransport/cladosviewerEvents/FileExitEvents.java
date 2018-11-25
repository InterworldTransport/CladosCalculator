/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewerEvents.FileExitEvents<br>
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
 * ---com.interworldtransport.cladosviewerEvents.FileExitEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;
import java.awt.event.*;
import javax.swing.*;

import com.interworldtransport.cladosviewer.ViewerMenu;

/** com.interworldtransport.cladosviewer.FileExitEvents
 *  This class manages all events relating to the exiting of the applicaiton.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class FileExitEvents implements ActionListener
 {
    protected ViewerMenu		ParentGUIMenu;
    protected JMenuItem 		ControlIt;
    protected FileEvents 		Parent;

	/** 
	 * This is the default constructor.
	 */
    public FileExitEvents(	ViewerMenu pGUIMenu,
    						JMenuItem pmniExit,
    						FileEvents pParent)
    {
		ParentGUIMenu=pGUIMenu;
		ControlIt=pmniExit;
		ControlIt.addActionListener(this);
		Parent=pParent;
    }

	/** 
	 * This is the actual action to be performed by this member of the File menu.
	 */
    public void actionPerformed(ActionEvent evt)
    {
    	System.exit(0);
    }
 }
