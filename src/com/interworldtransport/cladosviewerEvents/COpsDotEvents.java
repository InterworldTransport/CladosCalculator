/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.COpsDotEvents------------------------------------------
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
---com.interworldtransport.cladosviewer.COpsDotEvents------------------------------------------
*/

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.ViewerMenu;
import com.interworldtransport.cladosFExceptions.*;
import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.COpsDotEvents
 *  This class manages events relating to a complex operation.
 *  Symmetric Multiply this Monad with another Monad.
 *
 * @version 0.80, $Date: 2005/07/25 01:44:25 $
 * @author Dr Alfred W Differ
 */
public class COpsDotEvents implements ActionListener
 {
    protected ViewerMenu		ParentGUIMenu;
    protected JMenuItem 		ControlIt;
    protected COpsEvents 		Parent;

/** This is the default constructor.
 */
    public COpsDotEvents(	ViewerMenu pGUIMenu,
    						JMenuItem pmniControlled,
    						COpsEvents pParent)
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
		MonadPanel temp0=ParentGUIMenu._parentGUI._GeometryDisplay.getNyadPanel(0).getMonadPanel(0);
		MonadPanel temp1=ParentGUIMenu._parentGUI._GeometryDisplay.getNyadPanel(1).getMonadPanel(0);
		MonadRealF Monad0=null;
		MonadRealF Monad1=null;
	
		if (temp0!=null)
			Monad0=temp0.getMonad();
		if (temp1!=null)
			Monad1=temp1.getMonad();
	
		if (Monad0!=null || Monad1!=null)
		{
			try
			{
				Monad0.multiplySymm(Monad1);
				temp0.setCoefficientDisplay();
				ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("Second Monad symm multiplied against the first.\n");
			}
			catch (FieldBinaryException eb)
			{
				ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("FieldBinary error between second and first monads.\n");
				ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("Second Monad not symm multiplied against the first.\n");
			}
			catch (CladosMonadException em)
			{
				ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("General Clados error between second and first monads.\n");
				ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("Second Monad not symm multiplied against the first.\n");
			}
		}
		else
			ParentGUIMenu._parentGUI._StatusBar.setStatusMsg("Second Monad not symm multiplied against the first.\n");
    }
 }
