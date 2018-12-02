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
import com.interworldtransport.cladosF.*;
import java.awt.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.UtilityStatusBar
 * The UtilityStatusBar class is intended to be the status bar of a Utility
 * application.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class UtilityStatusBar extends JPanel
{

	private static final long serialVersionUID = -4893753957995222179L;
	public		JTextArea		stmesgt;
	public		DivFieldType	stFieldType;
	public		JTextField 		stFieldTypeIO = new JTextField();
    public		JTextField 		stRealIO = new JTextField();
    public		JTextField 		stImgIO = new JTextField();
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
		
		stmesgt = new JTextArea("Initializing GUI...", 20, 40);
		stmesgt.setFont(new Font("Serif", Font.PLAIN, 8));
		stmesgt.setLineWrap(true);
		stmesgt.setWrapStyleWord(true);
		add(new JScrollPane(stmesgt), "Center");
		// Done with large text area intended for code responses to user
		
		
		JPanel nPanel = new JPanel();
		nPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		nPanel.setBackground(_backColor);
		nPanel.setLayout(new GridBagLayout());
		
	 	GridBagConstraints cn = new GridBagConstraints();
    	Insets tGeneric = new Insets(1,1,1,1);
    	cn.insets = tGeneric;
    	cn.fill=GridBagConstraints.HORIZONTAL;
    	cn.anchor=GridBagConstraints.NORTH;
    	
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=0;
    	cn.weighty=0;
    	
    	nPanel.add(new JLabel("field"), cn);
    	cn.gridy++;

    	stFieldTypeIO.setColumns(8);
    	stFieldTypeIO.setFont(new Font("Serif", Font.PLAIN, 12));
		nPanel.add(stFieldTypeIO, cn);
		cn.gridy++;
		
		nPanel.add(new JLabel("real"), cn);
    	cn.gridy++;
    	
		stRealIO.setColumns(8);
		stRealIO.setFont(new Font("Serif", Font.PLAIN, 12));
		nPanel.add(stRealIO, cn);
		cn.gridy++;
		
		nPanel.add(new JLabel("img"), cn);
    	cn.gridy++;
		
		stImgIO.setColumns(8);
		stImgIO.setFont(new Font("Serif", Font.PLAIN, 12));
		nPanel.add(stImgIO, cn);
		cn.gridy++;
		
		cn.weightx=1;
    	cn.weighty=1;
    	nPanel.add(new JLabel(), cn);
		
		add(nPanel, "East");
    }
    
    public void setFieldType(DivFieldType pField)
    {
    	stFieldType=pField;
    	stFieldTypeIO.setText(pField.getType());
    }

    public void setStatusMsg(String pMsg)
    {
    	stmesgt.append(pMsg);
    }

    public void setWhatInt(int pWhat)
    {
		stRealIO.setText((new StringBuffer(pWhat)).toString());
    }
    
    public void setWhatFloat(float pWhat)
    {
		stRealIO.setText((new StringBuffer().append(pWhat)).toString());
    }

    public void setWhatDouble(double pWhat)
    {
		stRealIO.setText((new StringBuffer().append(pWhat)).toString());
    }
}
