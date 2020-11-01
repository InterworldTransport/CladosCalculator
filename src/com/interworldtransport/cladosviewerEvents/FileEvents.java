/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.FileEvents<br>
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
 * ---com.interworldtransport.cladosviewer.FileEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewerEvents;
import java.awt.event.*;

import javax.swing.JFileChooser;

import com.interworldtransport.cladosG.NyadComplexD;
import com.interworldtransport.cladosG.NyadComplexF;
import com.interworldtransport.cladosG.NyadRealD;
import com.interworldtransport.cladosG.NyadRealF;
import com.interworldtransport.cladosviewer.CladosCalculator;
import com.interworldtransport.cladosviewer.NyadPanel;
import com.interworldtransport.cladosviewer.ViewerMenu;

/** 
 * This class groups the event listeners associated with the File menu.  It may
 * be used in the future to act on events associated with the entire File menu
 * by having it register as a Listener with all of its controlled listeners.
 * The controlled listeners will create an event or call their parent.  It could
 * also register with all the components to which its listeners register..maybe.
 * [None of this is done yet, of course.]
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class FileEvents implements ActionListener
{
    protected 	FileSaveEvents		sp;
    protected 	FileSaveAsEvents	sa;
    protected 	FileExitEvents		ex;

    public		JFileChooser 		fc;
    protected 	ViewerMenu 			_GUIMenu;
    protected 	CladosCalculator	_GUI;

/** 
 * This is the default constructor.  The event structure of the File
 * menu starts here and finishes with the child menu items.
 * @param pTheGUIMenu
 *  ViewerMenu
 * This is a reference to the owner menu containing this one.
 */
    public FileEvents(ViewerMenu pTheGUIMenu)
    {
    	_GUIMenu=pTheGUIMenu;
    	_GUI=_GUIMenu._parentGUI;
    	
    	sp = new FileSaveEvents(	_GUIMenu.mniSave, this);
    	sa = new FileSaveAsEvents(	_GUIMenu.mniSaveAs, this);
    	ex = new FileExitEvents(	_GUIMenu.mniExit, this);
    	
    	fc = new JFileChooser();
	    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }

/** 
 * This is the default action to be performed by all members of the File menu.
 * It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt)
    {
    	;
    }
    
    public String makeSnapshotContent()
	{
    	if (_GUI.appGeometryView.getNyadListSize() == 0) return "Nothing in panels to save.";
    	
    	StringBuffer content=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
    	content.append("<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">\n");
    	content.append("<NyadList size=\""+_GUI.appGeometryView.getNyadListSize()+"\">\r\n");
    	
    	switch (_GUI.IniProps.getProperty("Desktop.File.Snapshot.FullXML"))
    	{
	    	case "true":	for (NyadPanel tempNPN : _GUI.appGeometryView.getNyadPanels())
							{
								switch(tempNPN.getRepMode())
								{
									case REALF:		
										NyadRealF tempNF=tempNPN.getNyadRF();
										content.append(NyadRealF.toXMLFullString(tempNF));			
										//content.append("\t<Nyad name=\""+tempNF.getName()+"\" ");
										//content.append("order=\""+tempNF.getNyadOrder()+"\" ");
										//content.append("foot=\""+tempNF.getFoot().getFootName()+"\">\r\n");
										//content.append("\t\t<MonadList>\r\n");
										//for (int m=0; m<tempNF.getNyadOrder(); m++)
										//	content.append(MonadRealF.toXMLFullString(tempNF.getMonadList(m)));
										break;
									case REALD:		
										NyadRealD tempND=tempNPN.getNyadRD();
										content.append(NyadRealD.toXMLFullString(tempND));	
										//content.append("\t<Nyad name=\""+tempND.getName()+"\" ");
										//content.append("order=\""+tempND.getNyadOrder()+"\" ");
										//content.append("foot=\""+tempND.getFoot().getFootName()+"\">\r\n");
										//content.append("\t\t<MonadList>\r\n");
										//for (int m=0; m<tempND.getNyadOrder(); m++)
										//	content.append(MonadRealD.toXMLFullString(tempND.getMonadList(m)));
										break;
									case COMPLEXF:	
										NyadComplexF tempNCF=tempNPN.getNyadCF();
										content.append(NyadComplexF.toXMLFullString(tempNCF));
										//content.append("\t<Nyad name=\""+tempNCF.getName()+"\" ");
										//content.append("order=\""+tempNCF.getNyadOrder()+"\" ");
										//content.append("foot=\""+tempNCF.getFoot().getFootName()+"\">\r\n");
										//content.append("\t\t<MonadList>\r\n");
										//for (int m=0; m<tempNCF.getNyadOrder(); m++)
										//	content.append(MonadComplexF.toXMLFullString(tempNCF.getMonadList(m)));
										break;
									case COMPLEXD:	
										NyadComplexD tempNCD=tempNPN.getNyadCD();
										content.append(NyadComplexD.toXMLFullString(tempNCD));
										//content.append("\t<Nyad name=\""+tempNCD.getName()+"\" ");
										//content.append("order=\""+tempNCD.getNyadOrder()+"\" ");
										//content.append("foot=\""+tempNCD.getFoot().getFootName()+"\">\r\n");
										//content.append("\t\t<MonadList>\r\n");
										//for (int m=0; m<tempNCD.getNyadOrder(); m++)
										//	content.append(MonadComplexD.toXMLFullString(tempNCD.getMonadList(m)));
								}
								//content.append("\t\t</MonadList>\r\n");
								//content.append("\t</Nyad>\r\n");
							}
	    					break;
	    	case "false":	for (NyadPanel tempNPN : _GUI.appGeometryView.getNyadPanels())
							{
								switch(tempNPN.getRepMode())
								{
									case REALF:		
										NyadRealF tempNF=tempNPN.getNyadRF();
										content.append(NyadRealF.toXMLString(tempNF));	
										//content.append("\t<Nyad name=\""+tempNF.getName()+"\" ");
										//content.append("order=\""+tempNF.getNyadOrder()+"\" ");
										//content.append("foot=\""+tempNF.getFoot().getFootName()+"\">\r\n");
										//content.append("\t\t<MonadList>\r\n");
										//for (int m=0; m<tempNF.getNyadOrder(); m++)
										//	content.append(MonadRealF.toXMLString(tempNF.getMonadList(m)));
										break;
									case REALD:		
										NyadRealD tempND=tempNPN.getNyadRD();
										content.append(NyadRealD.toXMLString(tempND));
										//content.append("\t<Nyad name=\""+tempND.getName()+"\" ");
										//content.append("order=\""+tempND.getNyadOrder()+"\" ");
										//content.append("foot=\""+tempND.getFoot().getFootName()+"\">\r\n");
										//content.append("\t\t<MonadList>\r\n");
										//for (int m=0; m<tempND.getNyadOrder(); m++)
										//	content.append(MonadRealD.toXMLString(tempND.getMonadList(m)));
										break;
									case COMPLEXF:	
										NyadComplexF tempNCF=tempNPN.getNyadCF();
										content.append(NyadComplexF.toXMLString(tempNCF));
										//content.append("\t<Nyad name=\""+tempNCF.getName()+"\" ");
										//content.append("order=\""+tempNCF.getNyadOrder()+"\" ");
										//content.append("foot=\""+tempNCF.getFoot().getFootName()+"\">\r\n");
										//content.append("\t\t<MonadList>\r\n");
										//for (int m=0; m<tempNCF.getNyadOrder(); m++)
										//	content.append(MonadComplexF.toXMLString(tempNCF.getMonadList(m)));
										break;
									case COMPLEXD:	
										NyadComplexD tempNCD=tempNPN.getNyadCD();
										content.append(NyadComplexD.toXMLString(tempNCD));
										//content.append("\t<Nyad name=\""+tempNCD.getName()+"\" ");
										//content.append("order=\""+tempNCD.getNyadOrder()+"\" ");
										//content.append("foot=\""+tempNCD.getFoot().getFootName()+"\">\r\n");
										//content.append("\t\t<MonadList>\r\n");
										//for (int m=0; m<tempNCD.getNyadOrder(); m++)
										//	content.append(MonadComplexD.toXMLString(tempNCD.getMonadList(m)));
								}
								//content.append("\t\t</MonadList>\r\n");
								//content.append("\t</Nyad>\r\n");
							}
	    					break;
    		default:		content.append("\n<Empty />\n");
    	}
    	content.append("</NyadList>\r\n");
		return content.toString();
	}
}