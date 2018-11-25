/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.BOpsMIdempotentEvents------------------------------------------
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
Use of this code or executable objects derived from it by the Licensee states their
willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
---com.interworldtransport.cladosviewer.BOpsMIdempotentEvents------------------------------------------
*/

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewer.ViewerMenu;
import com.interworldtransport.cladosFExceptions.*;
import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.BOpsMIdempotentEvents
 *  This class manages events relating to the answering of a boolean question.
 *  Is the Monad a multiple of an idempotent?
 *
 * @version 0.80, $Date: 2005/07/25 01:44:25 $
 * @author Dr Alfred W Differ
 */
public class BOpsMIdempotentEvents implements ActionListener
 {
    protected ViewerMenu		ParentGUIMenu;
    protected JMenuItem 		ControlIt;
    protected BOpsEvents 		Parent;

/** This is the default constructor.
 */
    public BOpsMIdempotentEvents(	ViewerMenu pGUIMenu,
    					JMenuItem pmniControlled,
					BOpsEvents pParent)
    {
	this.ParentGUIMenu=pGUIMenu;
	this.ControlIt=pmniControlled;
	this.ControlIt.addActionListener(this);
	this.Parent=pParent;

    }//end of BOpsMIdempotentEvents constructor

/** This is the actual action to be performed by this member of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
	MonadRealF Monad0=ParentGUIMenu._parentGUI._GeometryDisplay.getNyadPanel(0).getMonadPanel(0).getMonad();
	boolean test=false;
	try
	{
		test=MonadRealF.isIdempotentMultiple(Monad0);
	}
	catch (CladosMonadException e)
	{
		ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("CladosMonadException Triggered.\n");
	}
	catch (FieldBinaryException eb)
	{
		ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("FieldBinaryException Triggered.\n");
	}
	catch (FieldException eb)
	{
		ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("FieldException Triggered.\n");
	}
	
	if (test)
	{
		ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("Monad is judged as idempotent multiple.\n");
	}
	else
	{
		ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("Monad is judged as not idempotent multiple.\n");
	}
    }//end of action performed method.

 }//end of BOpsMIdempotentEvents class
