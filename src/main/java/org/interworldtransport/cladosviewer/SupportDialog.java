/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.SupportDialog<br>
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
 * ---org.interworldtransport.cladosviewer.SupportDialog<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * The SupportDialog is an information window that is called from the
 * "Help|Support" menu on the main Atmosphere application window. It provides
 * information about support for the application.
 * 
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public final class SupportDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 5321542111551309714L;
	private static final Color _backColor = new Color(255, 255, 222);
	private static final Color _tblBackColor = new Color(212, 212, 192);
	private CladosCalculator _GUI;

	/**
	 * The constructor sets up the support/about dialog box and displays it.
	 * 
	 * @param mainWindow CladosCalculator This is just a reference to the owning
	 *                   application so error messages can be reported out to the
	 *                   GUI.
	 */
	public SupportDialog(CladosCalculator mainWindow) {
		super(mainWindow, "Support for Clados Calculator Utility", true); // Use parent's constructor
		_GUI = mainWindow;

		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setBackground(_backColor);
		setContentPane(mainPane);

		// Create content text area
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

		JButton btnClose = new JButton(new ImageIcon(this.getClass().getResource("/img/close.png")));
		btnClose.setActionCommand("close");
		btnClose.setToolTipText("Close the dialog.");
		btnClose.setPreferredSize(new Dimension(30, 30));
		btnClose.setBorder(BorderFactory.createEtchedBorder(0));
		btnClose.addActionListener(this);
		closeButtonPane.add(btnClose);

		setSize(400, 300);
		Point parentLocation = mainWindow.getLocation();
		int Xloc = (int) parentLocation.getX() + ((mainWindow.getWidth() - 300) / 2);
		int Yloc = (int) parentLocation.getY();// + ((mainWindow.getHeight() - 400) / 2);
		setLocation(Xloc, Yloc);
		setVisible(true);
	}

	private String constructContent() {
		StringBuffer content = new StringBuffer();
		content.append("Clados Calculator ");
		content.append(_GUI.IniProps.getProperty("Desktop.Version") + "\n\n");
		content.append("https://github.com/InterworldTransport/CladosCalculator\n\n");

		content.append("For support issues that might help us make a better calculator please visit ");
		content.append("our GitHub home page. From this page you should be able to find the Calculator's ");
		content.append("associated docs and support features. Please record your support issues there.\n\n");
		content.append("For complex support or licensing issues, please contact Alfred Differ.");

		return content.toString();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		dispose(); // Any action is enough to close the window.
	}
}
