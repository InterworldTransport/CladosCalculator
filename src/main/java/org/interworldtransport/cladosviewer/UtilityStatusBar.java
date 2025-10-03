/**
 * <h2>Copyright</h2> © 2021 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.UtilityStatusBar<br>
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
 * ---org.interworldtransport.cladosviewer.UtilityStatusBar<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The UtilityStatusBar class is intended to be the status bar of a Utility
 * application.
 * <p>
 * 
 * @version 1.0
 * @author Dr Alfred W Differ
 */

public class UtilityStatusBar extends JPanel implements ActionListener {
	private static final long serialVersionUID = -238727771750034691L;
	private static final int _FONTSIZE = 10;
	private static final Color _backColor = new Color(255, 255, 222);
	private static final Dimension square = new Dimension(25, 25);
	private JTextArea stmesgt;

	/**
	 * The UtilityStatusBar class is intended to be the status bar of the SailAway
	 * application. There is nothing really special about this class. It can and
	 * used to be defined and built within the SailAway application. For the sake of
	 * maintenance, it has been moved to its own class and file.
	 */
	public UtilityStatusBar() {
		super();
		setBackground(_backColor);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());

		JButton clearIt = new JButton(new ImageIcon(this.getClass().getResource("/resources/remove.png")));
		clearIt.setActionCommand("clear");
		clearIt.setToolTipText("clear the message stack");
		clearIt.setPreferredSize(square);
		clearIt.setBorder(BorderFactory.createEtchedBorder(0));
		clearIt.addActionListener(this);
		add(clearIt, BorderLayout.LINE_START);

		stmesgt = new JTextArea(10, 40);
		stmesgt.setFont(new Font(Font.SERIF, Font.PLAIN, _FONTSIZE));
		stmesgt.setLineWrap(true);
		stmesgt.setWrapStyleWord(true);
		add(new JScrollPane(stmesgt), BorderLayout.CENTER);
	}

	/**
	 * This is the message setting method that practically all other display panels
	 * use when trying to report issues and results to the GUI.
	 * 
	 * @param pMsg String Typical use of this is to construct a string with the
	 *             message by appending other strings in a string buffer... and then
	 *             converting it at the end to String.
	 */
	public void setStatusMsg(String pMsg) {
		stmesgt.append(pMsg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		stmesgt.setText("");
	}
}
