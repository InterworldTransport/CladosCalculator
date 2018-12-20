/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.SOpsGradePartEvents------------------------------------------
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
---com.interworldtransport.cladosviewer.SOpsGradePartEvents------------------------------------------
*/

package com.interworldtransport.cladosviewer;
import com.interworldtransport.clados.*;
import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.SOpsGradePartEvents
 *  This class manages events relating to a simple.
 *  Limit this Monad to a particular grade.
 *
 * @version 0.80, $Date: 2005/07/25 01:44:25 $
 * @author Dr Alfred W Differ
 */
public class SOpsGradePartEvents implements ActionListener
 {
    protected ViewerMenu		ParentGUIMenu;
    protected JMenuItem 		ControlIt;
    protected SOpsEvents 		Parent;

/** This is the default constructor.
 */
    public SOpsGradePartEvents(	ViewerMenu pGUIMenu,
    				JMenuItem pmniControlled,
				SOpsEvents pParent)
    {
	this.ParentGUIMenu=pGUIMenu;
	this.ControlIt=pmniControlled;
	this.ControlIt.addActionListener(this);
	this.Parent=pParent;

    }//end of SOpsGradePartEvents constructor

/** This is the actual action to be performed by this member of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
	MonadPanel MP0=ParentGUIMenu.ParentGUI.CenterAll.getNyadPanel(0).getMonadPanel(0);
	Monad Monad0=MP0.getMonad();
	boolean test=false;
	try
	{

		Monad0.GradePart(new Integer(ParentGUIMenu.ParentGUI.StatusLine.stview.getText()).intValue());
		test=Monad0.isGrade(new Integer(ParentGUIMenu.ParentGUI.StatusLine.stview.getText()).intValue());
	}
	catch (NoDefinedGradeException eg)
	{
		test=false;
	}
	catch (BladeOutOfRangeException er)
	{
		test=false;
	}
	catch (NumberFormatException ef)
	{
		test=false;
	}
	if (test)
	{
		MP0.setBottomFields();
		ParentGUIMenu.ParentGUI.StatusLine.setStatusMsg("First Monad has all grades suppressed except: {"+
			ParentGUIMenu.ParentGUI.StatusLine.stview.getText()+"}\n");
	}
	else
	{
		ParentGUIMenu.ParentGUI.StatusLine.setStatusMsg("First Monad could not have all other grades supressed except: {"+
			ParentGUIMenu.ParentGUI.StatusLine.stview.getText()+"}\n");
	}
    }//end of action performed method.

 }//end of SOpsGradePartEvents class
