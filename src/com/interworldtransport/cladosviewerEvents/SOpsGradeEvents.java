/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.SOpsGradeEvents<br>
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
 * ---com.interworldtransport.cladosviewer.SOpsGradeEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosF.DivField;
import com.interworldtransport.cladosviewer.MonadPanel;
import com.interworldtransport.cladosviewer.NyadPanel;

import java.awt.event.*;
import javax.swing.*;

/**
 *  This class manages events relating to the answering of a simple question.
 *  What is the grade of the selected monad?
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class SOpsGradeEvents implements ActionListener
 {
    protected JMenuItem 		_control;
    protected SOpsEvents 		_parent;

/** 
 * This is the default constructor.
 * @param pmniControlled
 *  JMenuItem
 * This is a reference to the Menu Item for which this event acts.
 * @param pParent
 * 	SOpsEvents
 * This is a reference to the BOpsEvents parent event handler
 */
    public SOpsGradeEvents(	JMenuItem pmniControlled,
    						SOpsEvents pParent)
    {
		_control=pmniControlled;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** 
 * This is the actual action to be performed by this member of the menu.
 * Find the log of the gradeKey of the selected Monad and see if it is an integer.
 * If so, monad is of a single grade... so show the log of the gradeKey = monad grade
 * 
 * A future version of the  method must use the grade represented in 
 * the reference frame instead. Fourier decomposition is done against that frame 
 * and not the canonical one most of the time. That means the getGradeKey() method
 * will channel through the ReferenceFrame of the monad.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	int indexNyadPanelSelected = _parent._GUI._GeometryDisplay.getPaneFocus();
    	if (indexNyadPanelSelected<0) 
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nNo nyad in the focus.\n");
    		return;	
    	}
    	
    	NyadPanel panelNyadSelected=_parent._GUI._GeometryDisplay.getNyadPanel(indexNyadPanelSelected);
    	int indxMndPnlSlctd = panelNyadSelected.getPaneFocus();
    	if (indxMndPnlSlctd<0) 
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nGrade Test needs one monad in focus. Nothing done.\n");
    		return;
    	}
    	
    	MonadPanel tSpot = panelNyadSelected.getMonadPanel(indxMndPnlSlctd);
    	double logGradeKey = -1.0D;
    	
    	switch(tSpot.getRepMode())
    	{
    		case DivField.REALF:	logGradeKey=Math.log10(tSpot.getMonadRF().getGradeKey());
    								break;
    		case DivField.REALD:	logGradeKey=Math.log10(tSpot.getMonadRD().getGradeKey());
									break;
    		case DivField.COMPLEXF:	logGradeKey=Math.log10(tSpot.getMonadCF().getGradeKey());
									break;
    		case DivField.COMPLEXD:	logGradeKey=Math.log10(tSpot.getMonadCD().getGradeKey());
									break;
    	}
    	
    	if (logGradeKey < 0) 
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\nDivField not recognized by action handler.\n");
    		return;
    	}
    	
    	if (logGradeKey != Math.floor(logGradeKey))
    		_parent._GUI._StatusBar.setStatusMsg("\t\tselected monad IS NOT a single grade.\n");
    	else
    	{
    		_parent._GUI._StatusBar.setStatusMsg("\tselected monad IS single grade.\n");
    		_parent._GUI._FieldBar.setWhatDoubleR(logGradeKey);
    	}
    }
 }
