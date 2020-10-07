/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.ViewerPanel<br>
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
 * ---com.interworldtransport.cladosviewer.ViewerPanel<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer ;

import com.interworldtransport.cladosF.DivField;

import com.interworldtransport.cladosF.RealF;
import com.interworldtransport.cladosF.RealD;
import com.interworldtransport.cladosF.ComplexF;
import com.interworldtransport.cladosF.ComplexD;

import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosG.MonadRealD;
import com.interworldtransport.cladosG.MonadComplexF;
import com.interworldtransport.cladosG.MonadComplexD;

import com.interworldtransport.cladosG.NyadRealF;
import com.interworldtransport.cladosG.NyadRealD;
import com.interworldtransport.cladosG.NyadComplexF;
import com.interworldtransport.cladosG.NyadComplexD;

import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

/** com.interworldtransport.cladosviewer.ViewerPanel
 * The ViewerPanel class is intended to be the main panel of the Calculator.  It
 * holds the tabbed pane that holds Nyads.  It is also aware enough to manage
 * the nyads a bit with respect to stack operations.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class ViewerPanel extends JPanel implements ActionListener
{
	private static final long 				serialVersionUID = 4262057036375030572L;
	public			CladosCalculator		_GUI;
	private			String					_repMode;
    private			JButton					btnCopyNyad;
    private			JButton					btnNewNyad;
    private			JButton					btnRemoveNyad;
    private			JButton					btnSwapAbove;
    private			JButton					btnSwapBelow;
    private	final	Color					clrBackColor=new Color(255, 255, 220);
    private			JPanel					pnlControlBar;
    private final	Dimension 				square = new Dimension(25,25);
    private			ImageIcon				tabIcon;  
    protected		ArrayList<NyadPanel>	nyadPanelList;
    protected		JTabbedPane				nyadPanes;

    /**
    * The ViewerPanel class is intended to be a tabbed pane that displays all
    * the Nyad Panels.  ViewerPanel must be smart enough to
    * know what it holds and adjust the tabs when push and pop operations are
    * performed.
    * @param pGUI
    *		CladosCalculator
    * 	This parameter references the owning application. Nothing spectacular.

    */
    public 	ViewerPanel(CladosCalculator pGUI) 
    {
    	super();
    	
    	_GUI=pGUI;
    	setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    	setBackground(clrBackColor);
    	setLayout(new BorderLayout());
    	
  	    createStackLayout();
  	    
  	    //Get the nyad tab image for the nyad panes being constructed
    	tabIcon = new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.TabN"));
    	_repMode = validateInitialDivField();
    	
    	//The Viewer contains NyadPanels displayed as a JTabbedPanes containing 
    	//JScrollPanes containing a NyadPanel each. We initiate the JTabbedPanel here
    	nyadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
    	nyadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    	//JScrollPanes are used only when a nyadPanel with nyadPanes.addTab()
    	add(nyadPanes, "Center");
    	
    	nyadPanelList=new ArrayList<NyadPanel>(0);
  	    
  	    createObjectsLayout();	//This is the old initializer. TODO change to an XML reader
    }

    @Override
	public void actionPerformed(ActionEvent event) 
	{
		String command = event.getActionCommand();
		
		if (command.equals("push"))
    		push();		//Swaps the currently selected nyad with the one below it
    	
    	if (command.equals("pop"))
    		pop();		//Swaps the currently selected nyad with the one above it
		
    	if (command.equals("copy"))
	    	copyNyadCommand();			//Clone the selected nyad and place it at the end of the stack
    	
    	if (command.equals("erase"))
    		eraseNyadCommand();			//Remove the selected nyad from the stack
    	
    	if (command.equals("create"))
    		_GUI._EventModel.ToolParts.cr.actionPerformed(event);			//Create a new monad for the selected nyad OR a whole new nyad
	}
    
    public	int			getNyadListSize()
    {
	    return nyadPanelList.size();
    }
    
    public	NyadPanel	getNyadPanel(int pInd)
    {
	    if (pInd<nyadPanelList.size() && pInd >=0)
		    return nyadPanelList.get(pInd);
	  
	    return null;
    }
    
    public	ArrayList<NyadPanel> getNyadPanels()
    {
	    return nyadPanelList;
    }

    public	int			getPaneFocus()
    {
	    return nyadPanes.getSelectedIndex();
    }

    private NyadComplexD buildANyadCD(short pWhich, short monadCount)
    {
    	NyadComplexD aNyad=null;
		try
    	{
			String cnt =new StringBuffer("N").append(pWhich).toString();
			MonadComplexD	aMonad=new MonadComplexD(	"M",
									    				_GUI.IniProps.getProperty("Desktop.Default.AlgebraName"),
									    				_GUI.IniProps.getProperty("Desktop.Default.FrameName"),
									    				_GUI.IniProps.getProperty("Desktop.Default.FootName"),
									    				_GUI.IniProps.getProperty("Desktop.Default.Sig"),
									    				ComplexD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"))
		    											);
    		aNyad=new NyadComplexD(cnt, aMonad);
    		// Now bootstrap the others using the first
    		short m=1;
    		while (m<monadCount)
    		{
    			String nextMonadName = 		(new StringBuffer(aMonad.getName()).append(m)).toString();
    			String nextAlgebraName =	(new StringBuffer(aMonad.getAlgebra().getAlgebraName()).append(m)).toString();
    			String nextFrameName=		(new StringBuffer(aMonad.getFrameName()).append(m)).toString();
    			
    			aNyad.createMonad(	nextMonadName, 
    								nextAlgebraName, 
    								nextFrameName, 
    								_GUI.IniProps.getProperty("Desktop.Default.Sig")
    								);
    			m++;
    		}
		}
		catch (BadSignatureException es)
		{
			_GUI._StatusBar.setStatusMsg("... cannot construct a monad due to bad signature.\n");
			return null;
		}
		catch (CladosMonadException em)
		{
			_GUI._StatusBar.setStatusMsg("CladosMonad Exception found when constructing first part of the Viewer Panel.\n");
			return null;
		} 
		catch (CladosNyadException en)
		{
			_GUI._StatusBar.setStatusMsg("CladosNyad Exception found when adding NyadComplexD to the Viewer Panel");
			return null;
		}
		
		return aNyad;
    }
    
    private NyadComplexF buildANyadCF(short pWhich, short monadCount)
    {
    	NyadComplexF aNyad=null;
		try
    	{
			String cnt =new StringBuffer("N").append(pWhich).toString();
			MonadComplexF	aMonad=new MonadComplexF(	"M",
									    				_GUI.IniProps.getProperty("Desktop.Default.AlgebraName"),
									    				_GUI.IniProps.getProperty("Desktop.Default.FrameName"),
									    				_GUI.IniProps.getProperty("Desktop.Default.FootName"),
									    				_GUI.IniProps.getProperty("Desktop.Default.Sig"),
									    				ComplexF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"))
		    											);
    		aNyad=new NyadComplexF(cnt, aMonad);
    		// Now bootstrap the others using the first
    		short m=1;
    		while (m<monadCount)
    		{
    			String nextMonadName = 		(new StringBuffer(aMonad.getName()).append(m)).toString();
    			String nextAlgebraName =	(new StringBuffer(aMonad.getAlgebra().getAlgebraName()).append(m)).toString();
    			String nextFrameName=		(new StringBuffer(aMonad.getFrameName()).append(m)).toString();
    			
    			aNyad.createMonad(	nextMonadName, 
    								nextAlgebraName, 
    								nextFrameName, 
    								_GUI.IniProps.getProperty("Desktop.Default.Sig")
    								);
    			m++;
    		}
		}
		catch (BadSignatureException es)
		{
			_GUI._StatusBar.setStatusMsg("... cannot construct a monad due to bad signature.\n");
			return null;
		}
		catch (CladosMonadException em)
		{
			_GUI._StatusBar.setStatusMsg("CladosMonad Exception found when constructing first part of the Viewer Panel.\n");
			return null;
		} 
		catch (CladosNyadException en)
		{
			_GUI._StatusBar.setStatusMsg("CladosNyad Exception found when adding NyadComplexF to the Viewer Panel");
			return null;
		}
		
		return aNyad;
    }
    
    private NyadRealD buildANyadRD(short pWhich, short monadCount)
    {
    	NyadRealD aNyad=null;
		try
    	{
			String cnt =new StringBuffer("N").append(pWhich).toString();
			MonadRealD	aMonad=new MonadRealD(	"M",
							    				_GUI.IniProps.getProperty("Desktop.Default.AlgebraName"),
							    				_GUI.IniProps.getProperty("Desktop.Default.FrameName"),
							    				_GUI.IniProps.getProperty("Desktop.Default.FootName"),
							    				_GUI.IniProps.getProperty("Desktop.Default.Sig"),
							    				RealD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"))
    											);
    		aNyad=new NyadRealD(cnt, aMonad);
    		// Now bootstrap the others using the first
    		short m=1;
    		while (m<monadCount)
    		{
    			String nextMonadName = 		(new StringBuffer(aMonad.getName()).append(m)).toString();
    			String nextAlgebraName =	(new StringBuffer(aMonad.getAlgebra().getAlgebraName()).append(m)).toString();
    			String nextFrameName=		(new StringBuffer(aMonad.getFrameName()).append(m)).toString();
    			
    			aNyad.createMonad(	nextMonadName, 
    								nextAlgebraName, 
    								nextFrameName, 
    								_GUI.IniProps.getProperty("Desktop.Default.Sig")
    								);
    			m++;
    		}
		}
		catch (BadSignatureException es)
		{
			_GUI._StatusBar.setStatusMsg("... cannot construct a monad due to bad signature.\n");
			return null;
		}
		catch (CladosMonadException em)
		{
			_GUI._StatusBar.setStatusMsg("CladosMonad Exception found when constructing first part of the Viewer Panel.\n");
			return null;
		} 
		catch (CladosNyadException en)
		{
			_GUI._StatusBar.setStatusMsg("CladosNyad Exception found when adding NyadRealD to the Viewer Panel");
			return null;
		}
		
		return aNyad;
    }
    
    private NyadRealF buildANyadRF(short pWhich, short monadCount)
    {
    	NyadRealF aNyad=null;
		try
    	{
			String cnt =new StringBuffer("N").append(pWhich).toString();
			MonadRealF	aMonad=new MonadRealF(	"M",
							    				_GUI.IniProps.getProperty("Desktop.Default.AlgebraName"),
							    				_GUI.IniProps.getProperty("Desktop.Default.FrameName"),
							    				_GUI.IniProps.getProperty("Desktop.Default.FootName"),
							    				_GUI.IniProps.getProperty("Desktop.Default.Sig"),
							    				RealF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"))
    											);
    		aNyad=new NyadRealF(cnt, aMonad);
    		// Now bootstrap the others using the first
    		short m=1;
    		while (m<monadCount)
    		{
    			String nextMonadName = 		(new StringBuffer(aMonad.getName()).append(m)).toString();
    			String nextAlgebraName =	(new StringBuffer(aMonad.getAlgebra().getAlgebraName()).append(m)).toString();
    			String nextFrameName=		(new StringBuffer(aMonad.getFrameName()).append(m)).toString();
    			
    			aNyad.createMonad(	nextMonadName, 
    								nextAlgebraName, 
    								nextFrameName, 
    								_GUI.IniProps.getProperty("Desktop.Default.Sig")
    								);
    			m++;
    		}
		}
		catch (BadSignatureException es)
		{
			_GUI._StatusBar.setStatusMsg("... cannot construct a monad due to bad signature.\n");
			return null;
		}
		catch (CladosMonadException em)
		{
			_GUI._StatusBar.setStatusMsg("CladosMonad Exception found when constructing first part of the Viewer Panel.\n");
			return null;
		} 
		catch (CladosNyadException en)
		{
			_GUI._StatusBar.setStatusMsg("CladosNyad Exception found when adding NyadRealF to the Viewer Panel");
			return null;
		}
		
		return aNyad;
    }
    
    private void copyNyadCommand()
    {
    	if (getNyadListSize()<=0) return; // Nothing to do since nyad list is empty. Nothing to copy.
    	
    	String buildName="";
    	try
		{
			switch(getNyadPanel(getPaneFocus()).getRepMode())
			{
			case DivField.REALF:	NyadRealF focusNyadRF=getNyadPanel(getPaneFocus()).getNyadRF();
									buildName=new StringBuffer(focusNyadRF.getName()).append("_c").toString();
									NyadRealF newNyadCopyRF=new NyadRealF(buildName, focusNyadRF);
									addNyad(newNyadCopyRF);
									break;
			case DivField.REALD:	NyadRealD focusNyadRD=getNyadPanel(getPaneFocus()).getNyadRD();
									buildName=new StringBuffer(focusNyadRD.getName()).append("_c").toString();
									NyadRealD newNyadCopyRD=new NyadRealD(buildName, focusNyadRD);
									addNyad(newNyadCopyRD);
									break;
			case DivField.COMPLEXF:	NyadComplexF focusNyadCF=getNyadPanel(getPaneFocus()).getNyadCF();
									buildName=new StringBuffer(focusNyadCF.getName()).append("_c").toString();
									NyadComplexF newNyadCopyCF=new NyadComplexF(buildName, focusNyadCF);
									addNyad(newNyadCopyCF);
									break;
			case DivField.COMPLEXD:	NyadComplexD focusNyadCD=getNyadPanel(getPaneFocus()).getNyadCD();
									buildName=new StringBuffer(focusNyadCD.getName()).append("_c").toString();
									NyadComplexD newNyadCopyCD=new NyadComplexD(buildName, focusNyadCD);
									addNyad(newNyadCopyCD);
			}
		}
		catch (UtilitiesException e)
		{
			_GUI._StatusBar.setStatusMsg("\t\tcould not create copy from toolbar.\n");
		}
		catch (BadSignatureException es)
		{
			_GUI._StatusBar.setStatusMsg("\t\tcould not create copy from toolbar due to signature issue.\n");
		} 
		catch (CladosNyadException e) 
		{
			_GUI._StatusBar.setStatusMsg("\t\tcould not create copy from toolbar because nyad was malformed.\n");
		}
		
    }
    
    private void createObjectsLayout()
    {
    	//Look in the conf file and determine how many nyads to initiate and init NyadPanelList
    	int intCount = validateInitialNyadCount();
    	nyadPanelList=new ArrayList<NyadPanel>(intCount);
    	    	
    	//Look in the conf file and determine how many monads in each nyad get initiated
      	short intOrd = validateInitialNyadOrder();
      	//Nothing to initialize with this. It gets used when building intial nyads in 'buildANyad' methods.
    	    	
    	//Look in the conf file and determine the DivField to use during initiation
    	//String sType = validateInitialDivField();
		if (_repMode == "") // No valid DivField found, so don't construct a nyad
		{
			intOrd = 0;
			intCount = 0;
			nyadPanelList=new ArrayList<NyadPanel>(0);
		}
    	//Note that we re-nitialized the NyadPanelList if no valid DivField is found for initialization
    	//sType is the switch mode determining which monad types are used in the calculator.

    	// the j counter covers the number of nyads to be initiated. {max = intCount} {Could be ZERO}
    	// the m counter covers the number of monads in each nyad are to be initiated. {max = intOrder}
    	short j=0;
    	while (j < intCount)
    	{
    		switch (_repMode)
    		{
    			case DivField.REALF:	NyadRealF aNyadRF = buildANyadRF(j, intOrd); // the NyadRF bootstrapper
    						    		try	//Here we finally initiate the NyadPanel because the Nyad is actually filled at this point.
    						    		{
    						    			if(aNyadRF != null)
    						    			{
	    						    			nyadPanelList.add(j, new NyadPanel(_GUI, aNyadRF));
	    						    			nyadPanes.addTab(	new StringBuffer().append(j).toString(), 
				    												tabIcon, 
				    												new JScrollPane(nyadPanelList.get(j)),
				    												aNyadRF.getName()
				    												);
    						    			}
    						    			else _GUI._StatusBar.setStatusMsg("... null NyadRealF for new NyadPanel avoided.\n");
    						    		}
    						    		catch (UtilitiesException eutil)
    						    		{
    						    			_GUI._StatusBar.setStatusMsg("... cannot create the new NyadPanel\n");
    						    			_GUI._StatusBar.setStatusMsg(eutil.getStackTrace().toString());
    						    		} 
    						    		catch (BadSignatureException e)
    						    		{
    						    			_GUI._StatusBar.setStatusMsg("... NyadPanel constructor encountered a BadSignatureException.\n");
    						    			_GUI._StatusBar.setStatusMsg(e.getStackTrace().toString());
										}
    									break;
    			case DivField.REALD:	NyadRealD aNyadRD = buildANyadRD(j, intOrd); // the NyadRD bootstrapper
							    		try	//Here we finally initiate the NyadPanel because the Nyad is actually filled at this point.
							    		{
							    			if(aNyadRD != null)
							    			{
								    			nyadPanelList.add(j, new NyadPanel(_GUI, aNyadRD));
								    			nyadPanes.addTab(	new StringBuffer().append(j).toString(), 
																	tabIcon, 
																	new JScrollPane(nyadPanelList.get(j)),
																	aNyadRD.getName()
																	);
							    			}
							    			else _GUI._StatusBar.setStatusMsg("... null NyadRealD for new NyadPanel avoided.\n");
							    		}
							    		catch (UtilitiesException eutil)
							    		{
							    			_GUI._StatusBar.setStatusMsg("... cannot create the new NyadPanel\n");
							    			_GUI._StatusBar.setStatusMsg(eutil.getStackTrace().toString());
							    		}
							    		catch (BadSignatureException e)
    						    		{
    						    			_GUI._StatusBar.setStatusMsg("... NyadPanel constructor encountered a BadSignatureException.\n");
    						    			_GUI._StatusBar.setStatusMsg(e.getStackTrace().toString());
										}
										break;						
    			case DivField.COMPLEXF:	NyadComplexF aNyadCF = buildANyadCF(j, intOrd); // the NyadCF bootstrapper
							    		try	//Here we finally initiate the NyadPanel because the Nyad is actually filled at this point.
							    		{
							    			if(aNyadCF != null)
							    			{
								    			nyadPanelList.add(j, new NyadPanel(_GUI, aNyadCF));
								    			nyadPanes.addTab(	new StringBuffer().append(j).toString(), 
																	tabIcon, 
																	new JScrollPane(nyadPanelList.get(j)),
																	aNyadCF.getName()
																	);
							    			}
							    			else _GUI._StatusBar.setStatusMsg("... null NyadComplexF for new NyadPanel avoided.\n");
							    		}
							    		catch (UtilitiesException eutil)
							    		{
							    			_GUI._StatusBar.setStatusMsg("... cannot create the new NyadPanel\n");
							    			_GUI._StatusBar.setStatusMsg(eutil.getStackTrace().toString());
							    		}
							    		catch (BadSignatureException e)
    						    		{
    						    			_GUI._StatusBar.setStatusMsg("... NyadPanel constructor encountered a BadSignatureException.\n");
    						    			_GUI._StatusBar.setStatusMsg(e.getStackTrace().toString());
										}
										break;	
    			case DivField.COMPLEXD:	NyadComplexD aNyadCD = buildANyadCD(j, intOrd); // the NyadCD bootstrapper
							    		try	//Here we finally initiate the NyadPanel because the Nyad is actually filled at this point.
							    		{
							    			if(aNyadCD != null)
							    			{
								    			nyadPanelList.add(j, new NyadPanel(_GUI, aNyadCD));
								    			nyadPanes.addTab(	new StringBuffer().append(j).toString(), 
																	tabIcon, 
																	new JScrollPane(nyadPanelList.get(j)),
																	aNyadCD.getName()
																	);
							    			}
							    			else _GUI._StatusBar.setStatusMsg("... null NyadComplexD for new NyadPanel avoided.\n");
							    		}
							    		catch (UtilitiesException eutil)
							    		{
							    			_GUI._StatusBar.setStatusMsg("... cannot create the new NyadPanel\n");
							    			_GUI._StatusBar.setStatusMsg(eutil.getStackTrace().toString());
							    		}
							    		catch (BadSignatureException e)
    						    		{
    						    			_GUI._StatusBar.setStatusMsg("... NyadPanel constructor encountered a BadSignatureException.\n");
    						    			_GUI._StatusBar.setStatusMsg(e.getStackTrace().toString());
										}
    		}
    		j++;
    	} 	

    }
    
    private void createStackLayout()
    {
    	pnlControlBar=new JPanel();
  	    pnlControlBar.setLayout(new GridBagLayout());
  	    pnlControlBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
  	    pnlControlBar.setBackground(clrBackColor);
  	    
    	GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);
		cn.anchor=GridBagConstraints.NORTH;
		
		cn.gridx = 0;
		cn.gridy = 0;
		
		cn.weightx=0;
		cn.weighty=0;
		cn.gridheight=1;
		cn.gridwidth=1;
    	btnSwapBelow=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Push")));
    	btnSwapBelow.setActionCommand("push");
    	btnSwapBelow.setToolTipText("push nyad down on stack");
    	btnSwapBelow.setPreferredSize(square);
    	btnSwapBelow.setBorder(BorderFactory.createEtchedBorder(0));
    	btnSwapBelow.addActionListener(this);
    	pnlControlBar.add(btnSwapBelow, cn);
    	cn.gridy++;
    	
    	btnSwapAbove=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Pop")));
    	btnSwapAbove.setActionCommand("pop");
    	btnSwapAbove.setToolTipText("pop nyad up on stack");
    	btnSwapAbove.setPreferredSize(square);
    	btnSwapAbove.setBorder(BorderFactory.createEtchedBorder(0));
    	btnSwapAbove.addActionListener(this);
    	pnlControlBar.add(btnSwapAbove, cn);
		cn.gridy++;
    	
    	btnCopyNyad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Copy")));
    	btnCopyNyad.setActionCommand("copy");
    	btnCopyNyad.setToolTipText("copy nyad to end of stack");
    	btnCopyNyad.setPreferredSize(square);
    	btnCopyNyad.setBorder(BorderFactory.createEtchedBorder(0));
    	btnCopyNyad.addActionListener(this);
    	pnlControlBar.add(btnCopyNyad, cn);
    	cn.gridy++;
    	
    	btnRemoveNyad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Remove")));
    	btnRemoveNyad.setActionCommand("erase");
    	btnRemoveNyad.setToolTipText("remove nyad from stack");
    	btnRemoveNyad.setPreferredSize(square);
    	btnRemoveNyad.setBorder(BorderFactory.createEtchedBorder(0));
    	btnRemoveNyad.addActionListener(this);
    	pnlControlBar.add(btnRemoveNyad, cn);
    	cn.gridy++;
    	
    	btnNewNyad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Create")));
    	btnNewNyad.setActionCommand("create");
    	btnNewNyad.setToolTipText("create new nyad");
    	btnNewNyad.setPreferredSize(square);
    	btnNewNyad.setBorder(BorderFactory.createEtchedBorder(0));
    	btnNewNyad.addActionListener(this);
    	pnlControlBar.add(btnNewNyad, cn);
    	cn.gridy++;
    	
    	cn.weighty=1;
    	pnlControlBar.add(new JLabel(), cn);
    	
    	add(pnlControlBar,"East");
    }
    
    private void eraseNyadCommand()
	{
		if (nyadPanes.getTabCount()>0)
		{
			int point = nyadPanes.getSelectedIndex();
			switch (getNyadPanel(point).getRepMode())
			{
				case DivField.REALF: 	_GUI._FieldBar._repRealF = null;
										_GUI._FieldBar.setRealText("");
										break;
				case DivField.REALD: 	_GUI._FieldBar._repRealD = null;
										_GUI._FieldBar.setRealText("");
										break;
				case DivField.COMPLEXF:	_GUI._FieldBar._repComplexF = null;
										_GUI._FieldBar.setRealText("");
										_GUI._FieldBar.setImgText("");
										break;
				case DivField.COMPLEXD: _GUI._FieldBar._repComplexD = null;
										_GUI._FieldBar.setRealText("");
										_GUI._FieldBar.setImgText("");
			}
			removeNyadPanel(point);
		}
		else
		{
			_GUI._FieldBar.makeNotWritable();
		}
	}
    
    private	void pop()
    {
	    int where=nyadPanes.getSelectedIndex();
	    if (where>0)
	    {
		    String otherTitle=new String(nyadPanes.getTitleAt(where-1));
		    JScrollPane otherPane=new JScrollPane((JPanel)nyadPanelList.get(where-1));

		    String thisTitle=new String(nyadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)nyadPanelList.get(where));

		    nyadPanes.setTitleAt(where, otherTitle);
		    nyadPanes.setComponentAt(where, otherPane);

		    nyadPanes.setTitleAt(where-1, thisTitle);
		    nyadPanes.setComponentAt(where-1, thisPane);

		    NyadPanel tempPanel=(NyadPanel)nyadPanelList.remove(where-1);
		    nyadPanelList.add(where, tempPanel);

		    revalidate();
	    }
    }
    
    /**
     * This method pushes the selected Nyad downward on the stack if possible.
     * It does NOT create any new slots in the stack. 
     */
    private	void		push()
    {
	    int size=nyadPanes.getTabCount();
	    int where=nyadPanes.getSelectedIndex();
	    if (where<size-1)
	    {
		    String otherTitle=new String(nyadPanes.getTitleAt(where+1));
		    JScrollPane otherPane=new JScrollPane((JPanel)nyadPanelList.get(where+1));

		    String thisTitle=new String(nyadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)nyadPanelList.get(where));

		    nyadPanes.setTitleAt(where, otherTitle);
		    nyadPanes.setComponentAt(where, otherPane);

		    nyadPanes.setTitleAt(where+1, thisTitle);
		    nyadPanes.setComponentAt(where+1, thisPane);

		    NyadPanel tempPanel=(NyadPanel)nyadPanelList.remove(where);
		    nyadPanelList.add(where+1, tempPanel);

		    revalidate();
	    }
    }

    private String validateInitialDivField()
    {
    	String nField="";
    	try
    	{
    		String sType = _GUI.IniProps.getProperty("Desktop.Default.DivField");
    		switch (sType)
    		{
    			case "RealF": 		return DivField.REALF;
    			case "RealD": 		return DivField.REALD;
    			case "ComplexF": 	return DivField.COMPLEXF;			
    			case "ComplexD": 	return DivField.COMPLEXD;
    		}

    	}
    	catch (NullPointerException eNull)
    	{
    		_GUI._StatusBar.setStatusMsg("\nDesktop.Default.DivField from the configuration file appears to be null.\n");
    		_GUI._StatusBar.setStatusMsg("No nyad will be initialized.\n");
    	}
    	catch (NumberFormatException eFormat)
    	{
    		_GUI._StatusBar.setStatusMsg("\nDesktop.Default.DivField from the configuration file appears to be non-parse-able.\n");
    		_GUI._StatusBar.setStatusMsg("No nyad will be initialized.\n");
    	}
    	return nField;
    }

	private int validateInitialNyadCount()
    {
    	int nCount=0;
    	try
    	{
    		nCount=Integer.parseInt(_GUI.IniProps.getProperty("Desktop.Default.Count"));
    		nyadPanelList=new ArrayList<NyadPanel>(nCount);
    	}
    	catch (NullPointerException eNull)
    	{
    		_GUI._StatusBar.setStatusMsg("\nDesktop.Default.Count from the configuration file appears to be null. Set to Zero.\n");
    		nyadPanelList=new ArrayList<NyadPanel>(0);
    	}
    	catch (NumberFormatException eFormat)
    	{
    		_GUI._StatusBar.setStatusMsg("\nDesktop.Default.Count from the configuration file appears to be non-parse-able. Set to Zero.\n");
    		nyadPanelList=new ArrayList<NyadPanel>(0);
    	}
    	return nCount;
    }
	
	private short validateInitialNyadOrder()
    {
    	short nOrd=1;
    	try
    	{
    		nOrd=Short.parseShort(_GUI.IniProps.getProperty("Desktop.Default.Order"));
    	}
    	catch (NullPointerException eNull)
    	{
    		_GUI._StatusBar.setStatusMsg("\nDesktop.Default.Order from the configuration file appears to be null. Set to ONE.\n");
    	}
    	catch (NumberFormatException eFormat)
    	{
    		_GUI._StatusBar.setStatusMsg("\nDesktop.Default.Order from the configuration file appears to be non-parse-able. Set to ONE.\n");
    	}
    	return nOrd;
    }
   
    protected	void		addNyad(NyadComplexD pN)
    	    throws 				BadSignatureException, UtilitiesException
    {
    	NyadPanel newP=new NyadPanel(_GUI, pN);
    	addNyadPanel(newP);
    }
    
    protected	void		addNyad(NyadComplexF pN)
    	    throws 				BadSignatureException, UtilitiesException
    {
    	NyadPanel newP=new NyadPanel(_GUI, pN);
    	addNyadPanel(newP);
    }
    protected	void		addNyad(NyadRealD pN)
    	    throws 				BadSignatureException, UtilitiesException
    {
    	NyadPanel newP=new NyadPanel(_GUI, pN);
    	addNyadPanel(newP);
    }
    protected	void		addNyad(NyadRealF pN)
    		throws 				BadSignatureException, UtilitiesException
    {
    	NyadPanel newP=new NyadPanel(_GUI, pN);
    	addNyadPanel(newP);
    }
    protected	void		addNyadPanel(NyadPanel newP)
    {
    	int next=nyadPanes.getTabCount();
	    String cnt=new StringBuffer().append(next).toString();
	    nyadPanelList.ensureCapacity(next+1);
	    nyadPanelList.add(newP);
	    
	    switch (newP.getRepMode())
	    {
	    	case DivField.REALF: 	nyadPanes.addTab(	cnt, 
														tabIcon, 
														new JScrollPane(newP),
														newP.getNyadRF().getName()
														);
	    							break;
	    	case DivField.REALD: 	nyadPanes.addTab(	cnt, 
														tabIcon, 
														new JScrollPane(newP),
														newP.getNyadRD().getName()
														);
									break;
	    	case DivField.COMPLEXF: nyadPanes.addTab(	cnt, 
														tabIcon, 
														new JScrollPane(newP),
														newP.getNyadCF().getName()
														);
									break;
	      	case DivField.COMPLEXD: nyadPanes.addTab(	cnt, 
														tabIcon, 
														new JScrollPane(newP),
														newP.getNyadCD().getName()
														);
	    }
	    _GUI.pack();
    }
    
    /**
     * This is a big deal. By registering the FieldPanel with the ViewerPanel, change events on monad and nyad
     * panels can register their proto numbers with the field panel. This goes on behind the scenes allowing
     * the UI to adjust cladosF references on the app's FieldBar so the user need only pay attention to the 
     * numbers displayed. The string for the DivFieldType IS displayed, though. In a physical simulation, this is a 
     * terrible idea, but on a calculator where the user cannot reference objects when pointing to a DivField
     * they want to re-use, it must be done.
     * @param pFieldPanel	FieldPanel
     * In the owning app, this is the FieldBar object that allows for top-level numeric input on the calculator.
     * The Field Panel offered is registered with this Viewer Panel so change events can be routed.
     */
    protected 	void		registerFieldPanel(FieldPanel pFieldPanel)
    {
    	nyadPanes.addChangeListener(	
    			new ChangeListener() 
			    	{
			    		@Override
			            public void stateChanged(ChangeEvent e) 
			            {	//_GUI._StatusBar.setStatusMsg("ChangeEvent for "+e.getSource().getClass().getName()+".\n");
			    			if (nyadPanes.getTabCount()>0)
			    			{
			    				int j = nyadPanes.getSelectedIndex();
			    				switch (nyadPanelList.get(j).getRepMode())
			    				{
			    					case DivField.REALF:	pFieldPanel.setField(nyadPanelList.get(j).getNyadRF().getProto());
			    											break;
			    					case DivField.REALD:	pFieldPanel.setField(nyadPanelList.get(j).getNyadRD().getProto());
															break;
			    					case DivField.COMPLEXF:	pFieldPanel.setField(nyadPanelList.get(j).getNyadCF().getProto());
															break;
			    					case DivField.COMPLEXD:	pFieldPanel.setField(nyadPanelList.get(j).getNyadCD().getProto());
			    				}
			    				_GUI._FieldBar.makeWritable();
			    			}
			    			else
			    				pFieldPanel.clearFieldType();
			            }
			        }				);
    }
    
	protected void removeNyadPanel(int pInd)
    {
    	if (pInd<nyadPanelList.size() && pInd>=0)
    	{
    		nyadPanelList.remove(pInd);
    		nyadPanes.remove(pInd);
    	}
    	_GUI.pack();
    } 
}