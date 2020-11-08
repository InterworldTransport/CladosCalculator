/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.NOpsRMatchEvents<br>
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
 * ---com.interworldtransport.cladosviewer.NOpsRMatchEvents<br>
 * ------------------------------------------------------------------------ <br>
 */
package com.interworldtransport.cladosviewerEvents;

import com.interworldtransport.cladosG.NyadComplexD;
import com.interworldtransport.cladosG.NyadComplexF;
import com.interworldtransport.cladosG.NyadRealD;
import com.interworldtransport.cladosG.NyadRealF;
import com.interworldtransport.cladosviewer.NyadPanel;
import com.interworldtransport.cladosviewer.ErrorDialog;

import java.awt.event.*;
import javax.swing.*;

/** 
 *  This class manages events relating to the answering of a boolean question.
 *  Is the selected nyad a strong reference match with the one following it on the stack?
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class NOpsRMatchEvents implements ActionListener
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
    public NOpsRMatchEvents(JMenuItem pmniControlled,
    						NOpsParentEvents pParent)
    {
		_control=pmniControlled;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the menu.
 * Nyad strong reference match is checked between focus nyad and the next one in the stack.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	int tNyadIndex=_parent._GUI.appGeometryView.getPaneFocus();
    	if (tNyadIndex<0 | tNyadIndex>=_parent._GUI.appGeometryView.getNyadListSize()-1) 
    	{
    		ErrorDialog.show("No nyad in the focus... or the last one is.\nNothing done.", "Need Nyad In Focus");
    		return;	
    	}
    	
    	NyadPanel panelNyadSelected = _parent._GUI.appGeometryView.getNyadPanel(tNyadIndex);
    	NyadPanel panelNyadNext = _parent._GUI.appGeometryView.getNyadPanel(tNyadIndex+1);
    	if (panelNyadSelected.getRepMode() != panelNyadNext.getRepMode())
    	{
    		ErrorDialog.show("Nyads using different DivFields.", "Nyad DivField Mismatch");
    		return;	
    	}
    	boolean test = false;
    	switch (panelNyadSelected.getRepMode())
    	{
    		case REALF: 	test = NyadRealF.isStrongReferenceMatch(panelNyadSelected.getNyadRF(), panelNyadNext.getNyadRF());
    								break;
    		case REALD: 	test = NyadRealD.isStrongReferenceMatch(panelNyadSelected.getNyadRD(), panelNyadNext.getNyadRD());
									break;
    		case COMPLEXF: test = NyadComplexF.isStrongReferenceMatch(panelNyadSelected.getNyadCF(), panelNyadNext.getNyadCF());
									break;
    		case COMPLEXD: test = NyadComplexD.isStrongReferenceMatch(panelNyadSelected.getNyadCD(), panelNyadNext.getNyadCD());
    	}
    	if (test)
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected nyad and the next are STRONG REF MATCHED.\n");
		else
			_parent._GUI.appStatusBar.setStatusMsg("-->Selected nyad and the next are NOT strong ref matched.\n");
    }
 }