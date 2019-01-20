/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
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
import java.awt.*;
import javax.swing.*;

/** 
 * The UtilityStatusBar class is intended to be the status bar of a Utility
 * application.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class UtilityStatusBar extends JPanel
 {
	public		JTextArea		stmesgt;
    private		Color			_backColor = new Color(255, 255, 222);
	//private	Color			_unlockColor = new Color(255, 164, 164);

/**
 * The UtilityStatusBar class is intended to be the status bar of the SailAway
 * application.  There is nothing really special about this class.  It can and
 * used to be defined and built within the SailAway application.  For the sake
 * of maintenance, it has been moved to its own class and file.
 */
    public UtilityStatusBar()
    {
    	super();
    	setBackground(_backColor);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());
		
		stmesgt = new JTextArea("Initializing GUI...", 8, 40);
		stmesgt.setFont(new Font("Serif", Font.PLAIN, 8));
		stmesgt.setLineWrap(true);
		stmesgt.setWrapStyleWord(true);
		add(new JScrollPane(stmesgt), "Center");
		// Done with large text area intended for code responses to user
    }

    public void setStatusMsg(String pMsg)
    {
    	stmesgt.append(pMsg);
    }

}
