/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.SOpsGradeSupressEvents------------------------------------------
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
---com.interworldtransport.cladosviewer.SOpsGradeSupressEvents------------------------------------------
*/

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.ViewerMenu;

import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.SOpsGradeSupressEvents
 *  This class manages events relating to a simple.
 *  Limit this Monad to everything except a particular grade.
 *
 * @version 0.80, $Date: 2005/07/25 01:44:25 $
 * @author Dr Alfred W Differ
 */
public class SOpsGradeSupressEvents implements ActionListener
 {
    protected ViewerMenu		ParentGUIMenu;
    protected JMenuItem 		ControlIt;
    protected SOpsEvents 		Parent;

/** This is the default constructor.
 */
    public SOpsGradeSupressEvents(	ViewerMenu pGUIMenu,
    								JMenuItem pmniControlled,
    								SOpsEvents pParent)
    {
		ParentGUIMenu=pGUIMenu;
		ControlIt=pmniControlled;
		ControlIt.addActionListener(this);
		Parent=pParent;

    }//end of SOpsGradeSupressEvents constructor

/** This is the actual action to be performed by this member of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	MonadPanel MP0=ParentGUIMenu._parentGUI._GeometryDisplay.getNyadPanel(0).getMonadPanel(0);
    	MonadRealF Monad0=MP0.getMonad();
    	boolean test=false;
    	short tGrade = new Short(ParentGUIMenu._parentGUI._StatusBar.stRealIO.getText()).shortValue();
	
	
    	try
    	{
    		Monad0.gradeSuppress(tGrade);
    		MP0.setCoefficientDisplay();
    		test=true;
    	}
    	catch (NumberFormatException ef)
    	{
    		test=false;
    	}
    	if (test)
    	{
    		MP0.setCoefficientDisplay();
    		ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("Monad has suppressed grade: {"+tGrade+"}\n");
    	}
    	else
    		ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("Monad has not suppressed grade: {"+tGrade+"}\n");
    	
    }
 }
