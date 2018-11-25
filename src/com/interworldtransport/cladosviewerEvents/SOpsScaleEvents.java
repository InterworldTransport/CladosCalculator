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
import com.interworldtransport.cladosF.*;
import com.interworldtransport.cladosFExceptions.*;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.NyadPanel;
import com.interworldtransport.cladosviewer.ViewerMenu;

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
    protected ViewerMenu		_parentMenu;
    protected JMenuItem 		ControlIt;
    protected SOpsEvents 		Parent;

/** 
 * This is the default constructor.
 */
    public SOpsScaleEvents(	ViewerMenu pGUIMenu,
    						JMenuItem pmniControlled,
    						SOpsEvents pParent)
    {
    	_parentMenu=pGUIMenu;
		ControlIt=pmniControlled;
		ControlIt.addActionListener(this);
		Parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	try 
    	{	//Find the selected nyad and monad panels
    		NyadPanel tNSpotPnl = _parentMenu._parentGUI._GeometryDisplay.getNyadPanel(_parentMenu._parentGUI._GeometryDisplay.getPaneFocus());
        	MonadPanel tMSpotPnl=tNSpotPnl.getMonadPanel(tNSpotPnl.getPaneFocus());
        	
        	//Now point to the monad to be scaled
        	MonadRealF Monad0=tMSpotPnl.getMonad();
        	
        	//...and scale it
    		Monad0.scale(	new RealF(	Monad0.getCoeff((short) 0).getFieldType(), 
							new Float(_parentMenu._parentGUI._StatusBar.stRealIO.getText()).floatValue()));
    		
    		//redraw the UI's Monad Panel to show the rescaled Monad there
    		tMSpotPnl.setCoefficientDisplay();
    		_parentMenu._parentGUI._StatusBar.setStatusMsg(" monad has been rescaled by | ");
    		_parentMenu._parentGUI._StatusBar.setStatusMsg(_parentMenu._parentGUI._StatusBar.stRealIO.getText()+"\n");
    	
    	}
    	catch (FieldBinaryException eb)
    	{
    		_parentMenu._parentGUI._StatusBar.setStatusMsg("monad has NOT been rescaled due to field binary exception.\n");
    	}
    }
 }
