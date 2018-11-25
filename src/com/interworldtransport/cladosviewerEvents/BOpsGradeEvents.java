/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.BOpsGradeEvents------------------------------------------
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
---com.interworldtransport.cladosviewer.BOpsGradeEvents------------------------------------------
*/

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosviewer.ViewerMenu;

import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.BOpsGradeEvents
 *  This class manages events relating to the answering of a boolean question.
 *  Is the Monad a particular grade?
 *
 * @version 0.80, $Date: 2005/07/25 01:44:25 $
 * @author Dr Alfred W Differ
 */
public class BOpsGradeEvents implements ActionListener
 {
    protected ViewerMenu		ParentGUIMenu;
    protected JMenuItem 		ControlIt;
    protected BOpsEvents 		Parent;

/** This is the default constructor.
 */
    public BOpsGradeEvents(	ViewerMenu pGUIMenu,
    				JMenuItem pmniControlled,
				BOpsEvents pParent)
    {
		ParentGUIMenu=pGUIMenu;
		ControlIt=pmniControlled;
		ControlIt.addActionListener(this);
		Parent=pParent;

    }//end of BOpsGradeEvents constructor

/** This is the actual action to be performed by this member of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	MonadRealF Monad0=ParentGUIMenu._parentGUI._GeometryDisplay.getNyadPanel(0).getMonadPanel(0).getMonad();
    	boolean test=false;
    	int pTest = new Integer(ParentGUIMenu._parentGUI._StatusBar.stRealIO.getText()).intValue();
    	
    	try
    	{
    		test=MonadRealF.isGrade(Monad0, pTest);
    	}
    	catch (NumberFormatException ef)
    	{
    		test=false;
    	}
    	if (test)
    		ParentGUIMenu._parentGUI._StatusBar.setStatusMsg(
				"Monad is a pure grade match for grade: {"+pTest+"}\n");
    	else
    		ParentGUIMenu._parentGUI._StatusBar.setStatusMsg(
				"Monad is NOT a pure grade match (or exception was thrown) for grade :{ "+pTest+"}\n");
	
    }//end of action performed method.

 }//end of BOpsGradeEvents class
