/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.COpsDotEvents<br>
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
 * ---com.interworldtransport.cladosviewer.COpsDotEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosFExceptions.*;
import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.COpsDotEvents
 *  This class manages events relating to a complex operation.
 *  Symmetric Multiply this Monad with another Monad.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class COpsDotEvents implements ActionListener
 {
    protected JMenuItem 		_control;
    protected COpsEvents 		_parent;

/** This is the default constructor.
 * @param pmniControlled
 *  JMenuItem
 * This is a reference to the Menu Item for which this event acts.
 * @param pParent
 * 	COpsEvents
 * This is a reference to the BOpsEvents parent event handler
 */
    public COpsDotEvents(	JMenuItem pmniControlled,
    						COpsEvents pParent)
    {
		_control=pmniControlled;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
		MonadPanel temp0=_parent._GUI._GeometryDisplay.getNyadPanel(0).getMonadPanel(0);
		MonadPanel temp1=_parent._GUI._GeometryDisplay.getNyadPanel(1).getMonadPanel(0);
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
				_parent._GUI._StatusBar.setStatusMsg("Second Monad symm multiplied against the first.\n");
			}
			catch (FieldBinaryException eb)
			{
				_parent._GUI._StatusBar.setStatusMsg("FieldBinary error between second and first monads.\n");
				_parent._GUI._StatusBar.setStatusMsg("Second Monad not symm multiplied against the first.\n");
			}
			catch (CladosMonadException em)
			{
				_parent._GUI._StatusBar.setStatusMsg("General Clados error between second and first monads.\n");
				_parent._GUI._StatusBar.setStatusMsg("Second Monad not symm multiplied against the first.\n");
			}
		}
		else
			_parent._GUI._StatusBar.setStatusMsg("Second Monad not symm multiplied against the first.\n");
    }
 }
