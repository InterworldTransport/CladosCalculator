/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.UtilityStatusBar<br>
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
 * ---com.interworldtransport.cladosviewer.UtilityStatusBar<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer ;
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
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class UtilityStatusBar extends JPanel implements ActionListener
 {
	private	static final 	int 			_FONTSIZE =		10;
    private	static final	Color			_backColor = 	new Color(255, 255, 222);
    private static final	Dimension 		square = 		new Dimension(25,25);
	private				CladosCalculator	_GUI;
	private				JTextArea			stmesgt;
	private				JButton				clearIt;


/**
 * The UtilityStatusBar class is intended to be the status bar of the SailAway
 * application.  There is nothing really special about this class.  It can and
 * used to be defined and built within the SailAway application.  For the sake
 * of maintenance, it has been moved to its own class and file.
 * @param pParent	CladosCalculator
 * This reference points back at the parent of the UtilityBar so it may reference
 * properties known by the parent.
 */
    public UtilityStatusBar(CladosCalculator pParent)
    {
    	super();
    	_GUI = pParent;
    	setBackground(_backColor);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());
		
		clearIt=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Remove")));
		clearIt.setActionCommand("push");
		clearIt.setToolTipText("push nyad down on stack");
		clearIt.setPreferredSize(square);
		clearIt.setBorder(BorderFactory.createEtchedBorder(0));
		clearIt.addActionListener(this);
    	add(clearIt, BorderLayout.LINE_START);
		
		stmesgt = new JTextArea(10, 40);
		stmesgt.setFont(new Font("Serif", Font.PLAIN, _FONTSIZE));
		stmesgt.setLineWrap(true);
		stmesgt.setWrapStyleWord(true);
		add(new JScrollPane(stmesgt), BorderLayout.CENTER);
		// Done with large text area intended for code responses to user
    }
    /**
     * This is the message setting method that practically all other display panels
     * use when trying to report issues and results to the GUI.
     * @param pMsg
     *  String
     * Typical use of this is to construct a string with the message by appending
     * other strings in a string buffer... and then converting it at the end to String.
     */
    public void setStatusMsg(String pMsg)
    {
    	stmesgt.append(pMsg);
    }
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		stmesgt.setText("");
	}
}
