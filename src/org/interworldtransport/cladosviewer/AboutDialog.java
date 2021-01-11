/**
 * <h2>Copyright</h2> © 2021 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.AboutDialog<br>
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
 * ---org.interworldtransport.cladosviewer.AboutDialog<br>
 * ------------------------------------------------------------------------ <br>
 */
package org.interworldtransport.cladosviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * The AboutDialog is an information window that is called from the "Help|About"
 * menu on the main Atmosphere application window. It provides information about
 * the application, credit to contributors and the GPL license.
 * 
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public final class AboutDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = -358251200403316601L;
	private static final Color _backColor = new Color(255, 255, 222);
	private static final Color _tblBackColor = new Color(212, 212, 192);
	private CladosCalculator _GUI;

	/**
	 * The constructor sets up the about dialog box and displays it.
	 * 
	 * @param mainWindow CladosCalculator This parameter references the owning
	 *                   application. Nothing spectacular.
	 */
	public AboutDialog(CladosCalculator mainWindow) {
		super(mainWindow, "About Clados Calculator Utility", true); // Use parent's constructor
		_GUI = mainWindow;

		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setBackground(_backColor);
		setContentPane(mainPane);

		// Create Logo panel

		JPanel topspot = new JPanel();
		topspot.setBackground(_backColor);
		// String logoFile=_GUI.IniProps.getProperty("Desktop.Image.Header");
		ImageIcon temp = new ImageIcon(this.getClass().getResource("/resources/clados.png")); // clados.png
		topspot.add(new JLabel(temp));
		mainPane.add(topspot, "North");

		// Create content text area
		constructContent();
		JTextArea contentArea = new JTextArea(constructContent());
		contentArea.setBackground(_tblBackColor);
		contentArea.setBorder(new EmptyBorder(2, 2, 2, 2));
		contentArea.setLineWrap(true);
		contentArea.setWrapStyleWord(true);
		contentArea.setEditable(false);
		mainPane.add(new JScrollPane(contentArea), "Center");

		// Create close button panel

		JPanel closeButtonPane = new JPanel(new FlowLayout());
		closeButtonPane.setBackground(_backColor);
		closeButtonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.add(closeButtonPane, "South");

		// Create close button
		JButton btnClose = new JButton(new ImageIcon(this.getClass().getResource("/resources/close.png")));
		btnClose.setActionCommand("close");
		btnClose.setToolTipText("Close the dialog.");
		btnClose.setPreferredSize(new Dimension(30, 30));
		btnClose.setBorder(BorderFactory.createEtchedBorder(0));
		btnClose.addActionListener(this);
		closeButtonPane.add(btnClose);

		setSize(500, 700);
		Point parentLocation = mainWindow.getLocation();
		int Xloc = (int) parentLocation.getX() + ((mainWindow.getWidth() - 300) / 2);
		int Yloc = (int) parentLocation.getY(); // + ((mainWindow.getHeight() - 400) / 2);
		setLocation(Xloc, Yloc);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		dispose(); // Any action is enough to close the window.
	}

	private String constructContent() {
		String tempVersion = _GUI.IniProps.getProperty("Desktop.Version");
		String tempUserName = _GUI.IniProps.getProperty("User.Name");
		String tempInstitution = _GUI.IniProps.getProperty("User.Institution");

		StringBuffer content = new StringBuffer();

		content.append("Clados Calculator ");
		content.append(tempVersion);
		content.append("\n\n");

		content.append("Copyright 2020 Alfred Differ");
		content.append("\n\n");

		content.append("Web Site: https://github.com/InterworldTransport/CladosCalculator\n\n");

		content.append("Developers:\n");
		content.append("  Dr. Alfred Differ - Physics, Java\n");
		content.append("  Your name could be here! \n\n");

		content.append("Licensed to {");
		content.append(tempUserName);
		content.append("}\n");
		content.append(tempInstitution);
		content.append("\n\n");

		content.append("This program is distributed in the hope that it will be useful, ");
		content.append("it under the terms of the GNU Affero General Public License as ");
		content.append("published by the Free Software Foundation, either version 3 of the ");
		content.append("License, or (at your option) any later version. \n\n");

		content.append("This program is distributed in the hope that it will be useful, ");
		content.append("but WITHOUT ANY WARRANTY; without even the implied warranty of ");
		content.append("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the ");
		content.append("GNU Affero General Public License for more details.\n\n");

		content.append("Use of this code or executable objects derived from it by the Licensee ");
		content.append("states their willingness to accept the terms of the license.\n\n");

		content.append("You should have received a copy of the GNU Affero General Public License ");
		content.append("along with this program.  If not, see <https://www.gnu.org/licenses/>.\n");

		return content.toString();
	}
}