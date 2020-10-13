/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.NOpsHasAlgebraMatchEvents<br>
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
 * ---com.interworldtransport.cladosviewer.NOpsHasAlgebraMatchEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package com.interworldtransport.cladosviewerEvents;

import com.interworldtransport.cladosF.DivField;
import com.interworldtransport.cladosG.NyadComplexD;
import com.interworldtransport.cladosG.NyadComplexF;
import com.interworldtransport.cladosG.NyadRealD;
import com.interworldtransport.cladosG.NyadRealF;
import com.interworldtransport.cladosviewer.NyadPanel;

import java.awt.event.*;
import javax.swing.*;

/** 
 *  This class manages events relating to the answering of a boolean question.
 *  Is the selected monad's algebra used by the nyad following on the stack?
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class NOpsHasAlgebraMatchEvents implements ActionListener
 {
    protected JMenuItem 		_control;
    protected NOpsParentEvents 	_parent;

/** 
 * This is the default constructor.
 * @param pmniControlled
 *  JMenuItem
 * This is a reference to the Menu Item for which this event acts.
 * @param pParent
 * 	NOpsParentEvents
 * This is a reference to the NOpsParentEvents parent event handler
 */
    public NOpsHasAlgebraMatchEvents(	JMenuItem 			pmniControlled,
    									NOpsParentEvents 	pParent)
    {
		_control=pmniControlled;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the menu.
 * Nyad algebra match test is checked between focus monad and the next nyad in the stack.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	int tNyadIndex=_parent._GUI._GeometryDisplay.getPaneFocus();
    	if (tNyadIndex<0 | tNyadIndex>=_parent._GUI._GeometryDisplay.getNyadListSize()-1) 
    	{
    		_parent._GUI._StatusBar.setStatusMsg("No nyad in the focus... or the last one is.\n");
    		return;	
    	}
    	
    	NyadPanel panelNyadSelected = _parent._GUI._GeometryDisplay.getNyadPanel(tNyadIndex);
    	int indxMndPnlSlctd = panelNyadSelected.getPaneFocus();
    	if (indxMndPnlSlctd<0) 
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nHas Algebra Test must have a monad in focus. Nothing done.\n");
    		return;
    	}
    	//MonadPanel tSpot = panelNyadSelected.getMonadPanel(indxMndPnlSlctd);
    	
    	NyadPanel panelNyadNext = _parent._GUI._GeometryDisplay.getNyadPanel(tNyadIndex+1);
    	
    	if (panelNyadSelected.getRepMode() != panelNyadNext.getRepMode())
    	{
    		_parent._GUI._StatusBar.setStatusMsg("Nyads using different DivFields.\n");
    		return;	
    	}
    	
    	boolean test = false;
    	switch (panelNyadSelected.getRepMode())
    	{
    		case DivField.REALF: 	test = NyadRealF.hasAlgebra(panelNyadNext.getNyadRF(), 
    															panelNyadSelected.getMonadPanel(indxMndPnlSlctd).getMonadRF().getAlgebra());
    								break;
    		case DivField.REALD: 	test = NyadRealD.hasAlgebra(panelNyadNext.getNyadRD(), 
    															panelNyadSelected.getMonadPanel(indxMndPnlSlctd).getMonadRD().getAlgebra());
									break;
    		case DivField.COMPLEXF: test = NyadComplexF.hasAlgebra(	panelNyadNext.getNyadCF(), 
    																panelNyadSelected.getMonadPanel(indxMndPnlSlctd).getMonadCF().getAlgebra());
    								break;
    		case DivField.COMPLEXD: test = NyadComplexD.hasAlgebra(	panelNyadNext.getNyadCD(), 
    																panelNyadSelected.getMonadPanel(indxMndPnlSlctd).getMonadCD().getAlgebra());
    	}
    	if (test)
			_parent._GUI._StatusBar.setStatusMsg("-->Selected monad's algebra HAS presence in the next nyad.\n");
		else
			_parent._GUI._StatusBar.setStatusMsg("-->Selected monad's algebra HAS NO presence in the next nyad.\n");
    }
 }