/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.FileEvents<br>
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
 * ---org.interworldtransport.cladosviewer.FileEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerEvents;

import javax.swing.JFileChooser;

import org.interworldtransport.cladosG.Nyad;
import org.interworldtransport.cladosviewer.CladosCalculator;
import org.interworldtransport.cladosviewer.NyadPanel;
import org.interworldtransport.cladosviewer.ViewerMenu;

/**
 * This class groups the event listeners associated with the File menu. It may
 * be used in the future to act on events associated with the entire File menu
 * by having it register as a Listener with all of its controlled listeners. The
 * controlled listeners will create an event or call their parent. It could also
 * register with all the components to which its listeners register..maybe.
 * [None of this is done yet, of course.]
 *
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public class FileEvents {
	/**
	 * This reference points at the Open portion of the event model. 
	 * It's open for later possibilities for scripted ations.
	 */
	protected FileOpenEvents fo;
	/**
	 * This reference points at the Save portion of the event model. 
	 * It's open for later possibilities for scripted ations.
	 */
	protected FileSaveEvents sp;
	/**
	 * This reference points at the Save As portion of the event model. 
	 * It's open for later possibilities for scripted ations.
	 */	
	protected FileSaveAsEvents sa;
	/**
	 * This reference points at the Exit portion of the event model. 
	 * It's open for later possibilities for scripted ations.
	 */
	protected FileExitEvents ex;
	/**
	 * This reference points at the File Chooser. 
	 * It's open for later possibilities for scripted ations.
	 */
	public JFileChooser fc;
	/**
	 * This reference points to the top level object that is the menu bar. It gets used for
	 * navigating to other elments in the menus
	 */
	protected ViewerMenu _GUIMenu;
	/**
	 * This reference points at the top level object that IS the application. It gets used
	 * for navigating to other elements of the UI.
	 */
	protected CladosCalculator _GUI;

	/**
	 * This is the default constructor. The event structure of the File menu starts
	 * here and finishes with the child menu items.
	 * 
	 * @param pTheGUIMenu ViewerMenu This is a reference to the owner menu
	 *                    containing this one.
	 */
	public FileEvents(ViewerMenu pTheGUIMenu) {
		_GUIMenu = pTheGUIMenu;
		_GUI = _GUIMenu._parentGUI;

		fo = new FileOpenEvents(_GUIMenu.mniOpen, this);
		sp = new FileSaveEvents(_GUIMenu.mniSave, this);
		sa = new FileSaveAsEvents(_GUIMenu.mniSaveAs, this);
		ex = new FileExitEvents(_GUIMenu.mniExit, this);

		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}

	/**
	 * This is really just an internal utility method so the code that builds XML
	 * output is kept in one place. It's not that it has to be HERE, but that's
	 * where it is right now..
	 * 
	 * @return String XMLAsString representing the nyad list currently open in the
	 *         calculator.
	 */
	public String makeSnapshotContent() {
		if (_GUI.appGeometryView.getNyadListSize() == 0)
			return "Nothing in panels to save.";

		StringBuffer content = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		content.append("<NyadList ");
		content.append("xmlns=\"https://interworldtransport.org\" ");
		content.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
		content.append("xsi:schemaLocation=\"https://interworldtransport.org http://interworldtransport.org/clados.xsd\" ");
		content.append("size=\"");
		content.append(_GUI.appGeometryView.getNyadListSize()).append("\">\r\n");

		switch (_GUI.IniProps.getProperty("Desktop.File.Snapshot.FullXML")) {
		case "true" -> {
			for (NyadPanel<?> tempNPN : _GUI.appGeometryView.getNyadPanels()) {
				content.append(Nyad.toXMLFullString(tempNPN.getNyad(), ""));
			}
		}
		case "false" -> {
			for (NyadPanel<?> tempNPN : _GUI.appGeometryView.getNyadPanels()) {
				content.append(Nyad.toXMLString(tempNPN.getNyad(), ""));
			}
		}
		default -> content.append("\n<Empty />\n");
		}
		content.append("</NyadList>\r\n");
		return content.toString();
	}
}