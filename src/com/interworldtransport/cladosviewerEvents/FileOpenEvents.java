/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.FileOpenEvents<br>
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
 * ---com.interworldtransport.cladosviewer.FileOpenEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public class FileOpenEvents implements ActionListener {

	protected JMenuItem _control;
	protected FileEvents _parent;

	public FileOpenEvents(JMenuItem _control, FileEvents _parent) {
		super();
		this._control = _control;
		_control.addActionListener(this);
		this._parent = _parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// If SaveFile is unknown at this point, present a file chooser.
		// If there are issues with the file pointed to by SaveFile, present a chooser
		File fIni;
		Document doc;
		if (_parent._GUI.IniProps.getProperty("Desktop.File.Snapshot") != null)
		// save to file described in conf setting
		{
			fIni = new File(_parent._GUI.IniProps.getProperty("Desktop.File.Snapshot"));
			if (!(fIni.exists() & fIni.isFile() & fIni.canWrite())) {
				int returnVal = _parent.fc.showSaveDialog(_control);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fIni = _parent.fc.getSelectedFile();
				} else
					return;
			}
		} else {
			int returnVal = _parent.fc.showSaveDialog(_control);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				fIni = _parent.fc.getSelectedFile();
			} else
				return;
		}
		// TODO Make sure the file points to an XML file somehow. Validate it maybe?
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(fIni);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO parse the XML into the 'defaults' for initiating the calculator.
		// 'Count', 'Order', 'DivField', etc.
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		String path4NyadCount = "";
		String path4NyadOrder = "";
		String path4UsedCladosFiel = "";
		// etc?
		// XPathExpression expr = xpath.compile(<xpath_expression>);
		// expr.evaluate();

		// TODO Validate! If nyads currently visible>0, then Calculator has a
		// representation mode. STOP if opened file conflicts.
		// TODO Validate! If Count and Order (+) don't make sense, just STOP.

		// TODO Call builder method in parent Event handler. It will hand back an
		// ArrayList<NyadAbstract>?

		// TODO Validate! If ArrayList length isn't 'Count', inform the user.

		// TODO Deliver nyads one at a time to the Viewer Panel in the calculator to be
		// appended as if created there.

		_parent._GUI.appStatusBar.setStatusMsg("File OPEN is not ready yet. Working on XML parser of CladosG objects.\n");
	}

}
