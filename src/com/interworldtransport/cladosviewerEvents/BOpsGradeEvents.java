/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewerEvents.BOpsGradeEvents<br>
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
 * ---com.interworldtransport.cladosviewerEvents.BOpsGradeEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;
import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosviewer.NyadPanel;

import java.awt.event.*;
import javax.swing.*;

/** 
 *  This class manages events relating to the answering of a boolean question.
 *  Is the Monad a particular grade?
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class BOpsGradeEvents implements ActionListener
 {
    protected JMenuItem 		_control;
    protected BOpsEvents 		_parent;

/** 
 * This is the default constructor.
 */
    public BOpsGradeEvents(	JMenuItem pmniControlled,
							BOpsEvents pParent)
    {
		//ParentGUIMenu=pGUIMenu;
		_control=pmniControlled;
		_control.addActionListener(this);
		_parent=pParent;
    }

/** This is the actual action to be performed by this member of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	int indexNyadPanelSelected=_parent._GUI._GeometryDisplay.getPaneFocus();
    	NyadPanel panelNyadSelected=_parent._GUI._GeometryDisplay.getNyadPanel(indexNyadPanelSelected);
    	MonadRealF monadSelected = panelNyadSelected.getMonadPanel(panelNyadSelected.getPaneFocus()).getMonad();
    	
    	int grade2Test = new Integer(_parent._GUI._StatusBar.stRealIO.getText()).intValue();
    
    	if (MonadRealF.isGrade(monadSelected, grade2Test))
    		_parent._GUI._StatusBar.setStatusMsg(" selected monad is a pure ["+grade2Test+"] grade match\n");
    	else
    		_parent._GUI._StatusBar.setStatusMsg("selected monad is NOT a pure ["+grade2Test+"] grade match\n");
    	
    	panelNyadSelected=null;
    	monadSelected=null;
    }
 }