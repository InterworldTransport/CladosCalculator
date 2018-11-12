/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.UtilityStatusBar---------------------------------------
<p>
Interworld Transport grants you ("Licensee") a license to this software
under the terms of the GNU General Public License.<br>
A full copy of the license can be found bundled with this package or code file.
<p>
If the license file has become separated from the package, code file, or binary
executable, the Licensee is still expected to read about the license at the
following URL before accepting this material.
<blockquote><code>http://www.opensource.org/gpl-license.html</code></blockquote>
<p>
Use of this code or executable objects derived from it by the Licensee states
their willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
---com.interworldtransport.cladosviewer.UtilityStatusBar---------------------------------------
 */

package com.interworldtransport.cladosviewer ;

import java.awt.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.UtilityStatusBar
 * The UtilityStatusBar class is intended to be the status bar of a Utility
 * application.
 * <p>
 * @version 0.80, $Date: 2005/07/28 16:57:25 $
 * @author Dr Alfred W Differ
 */

 public class UtilityStatusBar extends JPanel
{

	private static final long serialVersionUID = -4893753957995222179L;
	public	JTextArea	stmesgt;
    public	JTextField 	stview;

/**
 * The UtilityStatusBar class is intended to be the status bar of the SailAway
 * application.  There is nothing really special about this class.  It can and
 * used to be defined and built within the SailAway application.  For the sake
 * of maintenance, it has been moved to its own class and file.
 */
    public UtilityStatusBar()
    {
	super();
	this.setBackground(Color.white);
	this.setLayout(new BorderLayout());
	this.setBorder(BorderFactory.createEtchedBorder());
	this.stmesgt = new JTextArea("Initializing GUI and Monads\n", 3, 30);
	this.stmesgt.setFont(new Font("Serif", Font.PLAIN, 10));
	this.stmesgt.setLineWrap(true);
	this.stmesgt.setWrapStyleWord(true);
	JScrollPane tempPane=new JScrollPane(this.stmesgt);
	this.add(tempPane, "Center");

	this.stview = new JTextField(" ", 18);
	this.add(stview, "East");
    }

    public void setStatusMsg(String pMsg)
    {
	this.stmesgt.append(pMsg);
    }

    public void setWhere()
    {
	this.stview.setText("");
    }

    public void setWhere(String pWhere)
    {
	this.stview.setText(pWhere);
    }

    public void setWhere(int pWhat)
    {
	this.stview.setText(new Integer(pWhat).toString());
    }

    public void setWhere(double pWhat)
    {
	this.stview.setText(new Double(pWhat).toString());
    }
}
