/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.SOpsSQMagnitudeEvents------------------------------------------
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
---com.interworldtransport.cladosviewer.SOpsSQMagnitudeEvents------------------------------------------
*/

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosF.*;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.ViewerMenu;

import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.SOpsSQMagnitudeEvents
 *  This class manages events relating to the answering of a simple question.
 *  What is the squared magnitude of this Monad?
 *
 * @version 0.80, $Date: 2005/07/25 01:44:25 $
 * @author Dr Alfred W Differ
 */
public class SOpsSQMagnitudeEvents implements ActionListener
 {
    protected ViewerMenu		ParentGUIMenu;
    protected JMenuItem 		ControlIt;
    protected SOpsEvents 		Parent;

/** This is the default constructor.
 */
    public SOpsSQMagnitudeEvents(	ViewerMenu pGUIMenu,
    					JMenuItem pmniControlled,
					SOpsEvents pParent)
    {
		ParentGUIMenu=pGUIMenu;
		ControlIt=pmniControlled;
		ControlIt.addActionListener(this);
		Parent=pParent;

    }

/** This is the actual action to be performed by this member of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	MonadPanel MP0=ParentGUIMenu._parentGUI._GeometryDisplay.getNyadPanel(0).getMonadPanel(0);
    	MonadRealF Monad0=MP0.getMonad();
    	RealF scale = null;
    	try 
    	{
    		scale = Monad0.sqMagnitude();
    	} 
    	catch (CladosMonadException e) 
    	{
    		// This should not ever happen, but a field conflicted Monad might do it.
    		e.printStackTrace();
    	}
    	MP0.setCoefficientDisplay();
    	if (scale != null)
    	{
    		ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("First Monad magnitude squared is: \n");
			ParentGUIMenu._parentGUI._StatusBar.setWhatFloat(scale.getModulus());
    	}
    	else
    	{
    		ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("First Monad couldn't be squared. \n");
    	}

    }
 }
