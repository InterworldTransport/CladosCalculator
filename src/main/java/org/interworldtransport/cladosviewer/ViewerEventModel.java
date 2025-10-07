/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.ViewerEventModel<br>
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
 * ---org.interworldtransport.cladosviewer.ViewerEventModel<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewer;

import org.interworldtransport.cladosviewerEvents.NOpsParentEvents;
import org.interworldtransport.cladosviewerEvents.FileEvents;
import org.interworldtransport.cladosviewerEvents.HelpEvents;
import org.interworldtransport.cladosviewerEvents.MOpsParentEvents;
import org.interworldtransport.cladosviewerEvents.ToolsEvents;

/** org.interworldtransport.cladosviewer.ViewerEventModel
 *
 *
 * @version 1.0, 2020/09/16
 * @author Dr Alfred W Differ
 */
public class ViewerEventModel
{
/**
 * The GUIParentMenu object maintains a reference to the GUI's Menu to which
 * this EventModel applies
 */
    protected	ViewerMenu			GUIParentMenu;

/**
 * The FileEvents object collects all File Event Handlers in one place in case
 * there are actions they all share.  This object is responsible for
 * constructing all File Event Handlers.
 */
    protected 	FileEvents			FileParts;

/**
 * The NOpsParentEvents object collects all Boolean Operations Event Handlers in one
 * place in case there are actions they all share.  This object is responsible
 * for constructing all related Event Handlers.
 */
    protected	NOpsParentEvents	NOpsParts;

/**
 * The MOpsParentEvents object collects all Simple Operations Event Handlers in one
 * place in case there are actions they all share.  This object is responsible
 * for constructing all related Event Handlers.
 */
    protected	MOpsParentEvents	MOpsParts;

/**
 * The ToolEvents object collects all Tool Event Handlers in one place in case
 * there are actions they all share.  This object is responsible for
 * constructing all related Event Handlers.
 */
    protected	ToolsEvents			ToolParts;

/**
 * The HelpEvents object collects all Help Event Handlers in one place in case
 * there are actions they all share.  This object is responsible for
 * constructing all Help Event Handlers.
 */
    protected 	HelpEvents			HelpParts;


    /** 
    * This is the default constructor.  The event structure of the SailAway
    * application starts here and finishes with the child components that affect the
    * physical model.
    * @param pGUIParentMenu
    *  ViewerMenu
    * This is just a reference so events described as elements in this object can reach
    * back and influence the GUI and the data model contained within it.
    */
    public ViewerEventModel(ViewerMenu pGUIParentMenu)
    {
    	GUIParentMenu=pGUIParentMenu;

    	FileParts = new FileEvents(GUIParentMenu);

    	NOpsParts = new NOpsParentEvents(GUIParentMenu);
    	MOpsParts = new MOpsParentEvents(GUIParentMenu);
	
		ToolParts = new ToolsEvents(GUIParentMenu);
		HelpParts = new HelpEvents(GUIParentMenu);
    }
}
