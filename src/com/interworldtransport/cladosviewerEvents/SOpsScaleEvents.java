/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewerEvents.SOpsScaleEvents<br>
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
 * ---com.interworldtransport.cladosviewerEvents.SOpsScaleEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosF.RealF;
import com.interworldtransport.cladosFExceptions.FieldBinaryException;
import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.NyadPanel;

import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.cladosviewer.SOpsScaleEvents
 *  This class manages events relating to a simple operation...
 *  Rescale this Monad.
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class SOpsScaleEvents implements ActionListener
 {
    protected JMenuItem 		_control;
    protected SOpsEvents 		_parent;

/** 
 * This is the default constructor.
 */
    public SOpsScaleEvents(	JMenuItem pmniControlled,
    						SOpsEvents pParent)
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
    	if (_parent._GUI._GeometryDisplay.getPaneFocus()<0) return;
    	try 
    	{	//Find the selected nyad and monad panels
    		NyadPanel tNSpotPnl = _parent._GUI._GeometryDisplay.getNyadPanel(_parent._GUI._GeometryDisplay.getPaneFocus());
        	MonadPanel tMSpotPnl=tNSpotPnl.getMonadPanel(tNSpotPnl.getPaneFocus());
        	
        	//Now point to the monad to be scaled
        	MonadRealF tMonad=tMSpotPnl.getMonad();
        	
        	//...and scale it
        	/*
        	 * TODO
        	 * Uh oh. This scale command builds a DivFieldType based on the selected Monad
        	 * Should it not be using the Nyad's proto number?
        	 * Looks like the Utility Status Bar actually points at the active DivFieldType
        	 * at some point. I don't know if it resets based on a selected monad or nyad's proto number.
        	 */
    		tMonad.scale(new RealF(	tMonad.getCoeff((short) 0).getFieldType(), 
    								Float.parseFloat(_parent._GUI._FieldBar.getRealText())));
    		
    		//redraw the UI's Monad Panel to show the rescaled Monad there
    		tMSpotPnl.setCoefficientDisplay();
    		_parent._GUI._StatusBar.setStatusMsg("\tmonad has been rescaled by | ");
    		_parent._GUI._StatusBar.setStatusMsg(_parent._GUI._FieldBar.getRealText()+"\n");
    	}
    	catch (FieldBinaryException eb)
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\t\tmonad has NOT been rescaled due to field binary exception.\n");
    	}
    }
 }