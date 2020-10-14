/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.NyadPanel<br>
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
 * ---com.interworldtransport.cladosviewer.NyadPanel<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer ;

import com.interworldtransport.cladosF.CladosField;
//import com.interworldtransport.cladosF.DivField;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.util.*;


/** com.interworldtransport.cladosviewer.NyadPanel
 * The ViewerPanel class is intended to be the main panel of the Monad Viewer
 * utility.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class NyadPanel extends JPanel implements ActionListener
{

	private static final long 						serialVersionUID = -1379147617356173590L;
	public					CladosCalculator		_GUI;
	private					NyadComplexD			_repNyadCD;
	private					NyadComplexF			_repNyadCF;
	private					NyadRealD				_repNyadD;
	private					NyadRealF				_repNyadF;
	private					JButton					btnCopyMonad;
	private					JButton					btnEditMonad;
	private					JButton					btnNewMonad;
	private					JButton					btnRemoveMonad;
	private					JButton					btnSaveEdits;
	private					JButton					btnSwapAbove;
	private					JButton					btnSwapBelow;
	private					JButton					btnUndoEdits;
	private			final	Color					clrBackColor = new Color(212, 200, 212);
	private			final	Color					clrUnlockColor = new Color(255, 192, 192);
	private					JLabel					nyadFoot=new JLabel();
	private					JTextField				nyadName=new JTextField(20);
	private					JLabel					nyadOrder=new JLabel();
	private					JPanel 					pnlControlPanel;
	private					JPanel 					pnlControlPanel2;
	private					JPanel 					pnlRefPanel;
	private					JLabel					protoXML=new JLabel();
	private 		final	Dimension 				square = new Dimension(25,25);
	private					ImageIcon				tabIcon;
	protected				CladosField				_repMode;
	protected				ArrayList<MonadPanel>	monadPanelList; 
    protected				JTabbedPane				monadPanes;
    
    /**
     * The NyadPanel class is intended to be contain a cladosG Nyad in order to offer its parts
     * for display and manipulation by the calculator
     * 
     * @param pGUI			
     * 	CladosCalculator
     * This is just a reference to the owner application so error messages can be presented.
     * @param pN
     * 	NyadComplexD
     * This is a reference to the nyad to be displayed and manipulated.
     * @throws UtilitiesException
     * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
     * @throws BadSignatureException
     * This exception is thrown when one of the monad panels can't accept the string signature offered.
     * That happens when something other than '+' or '-' is used... or maybe when signature is too long.
     * Remember that blade count is currently tracked with a short integer. {--****
     */
   	 public NyadPanel(	CladosCalculator pGUI,
   			 			NyadComplexD pN)
   			 	throws 	UtilitiesException, BadSignatureException
   	 {
   		super();
   		if (pGUI==null)
   	      		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
   		_GUI=pGUI;
   		 
   	    if (pN==null)
   	      		throw new UtilitiesException("A Nyad must be passed to this MonadPanel constructor");
   	    _repNyadCD=pN;
   		_repMode=CladosField.COMPLEXD;
   		 
   		setReferences();
   			 
   		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
   		setBackground(clrBackColor);
   		setLayout(new BorderLayout());

   		tabIcon = new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.TabM"));
   			 
   		createEditLayout();
   		createStackLayout();
   		createReferenceLayout();
   			 
   		monadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
   		monadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
   		monadPanelList=new ArrayList<MonadPanel>(_repNyadCD.getMonadList().size());
   			 
   		for (short j=0; j<_repNyadCD.getMonadList().size(); j++)
   		{
   			String count =new StringBuffer().append(j).toString();
   			monadPanelList.add(j, new MonadPanel(_GUI, _repNyadCD.getMonadList(j)));
   			JScrollPane tempPane=new JScrollPane(monadPanelList.get(j));
   			tempPane.setWheelScrollingEnabled(true);
   				 
   			monadPanes.addTab(	count, 
   						 		tabIcon, 
   						 		tempPane,
   						 		_repNyadCD.getName()+" | "+monadPanelList.get(j).getMonadCD().getName()
   						 		);
   		}
   			 
   		add(monadPanes, "Center");
   	 }
    
    /**
     * The NyadPanel class is intended to be contain a cladosG Nyad in order to offer its parts
     * for display and manipulation by the calculator
     * 
     * @param pGUI			
     * 	CladosCalculator
     * This is just a reference to the owner application so error messages can be presented.
     * @param pN
     * 	NyadComplexF
     * This is a reference to the nyad to be displayed and manipulated.
     * @throws UtilitiesException
     * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
     * @throws BadSignatureException
     * This exception is thrown when one of the monad panels can't accept the string signature offered.
     * That happens when something other than '+' or '-' is used... or maybe when signature is too long.
     * Remember that blade count is currently tracked with a short integer. {--****
     */
   	 public NyadPanel(	CladosCalculator pGUI,
   			 			NyadComplexF pN)
   			 	throws 	UtilitiesException, BadSignatureException
   	 {
   		super();
   		if (pGUI==null)
   	      		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
   		_GUI=pGUI;
   		 
   	    if (pN==null)
   	      		throw new UtilitiesException("A Nyad must be passed to this MonadPanel constructor");
   	    _repNyadCF=pN;
   		_repMode=CladosField.COMPLEXF;
   		 
   		setReferences();
   			 
   		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
   		setBackground(clrBackColor);
   		setLayout(new BorderLayout());

   		tabIcon = new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.TabM"));
   			 
   		createEditLayout();
   		createStackLayout();
   		createReferenceLayout();
   			 
   		monadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
   		monadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
   		monadPanelList=new ArrayList<MonadPanel>(_repNyadCF.getMonadList().size());
   			 
   		for (short j=0; j<_repNyadCF.getMonadList().size(); j++)
   		{
   			String count =new StringBuffer().append(j).toString();
   			monadPanelList.add(j, new MonadPanel(_GUI, _repNyadCF.getMonadList(j)));
   			JScrollPane tempPane=new JScrollPane(monadPanelList.get(j));
   			tempPane.setWheelScrollingEnabled(true);
   				 
   			monadPanes.addTab(	count, 
   						 		tabIcon, 
   						 		tempPane,
   						 		_repNyadCF.getName()+" | "+monadPanelList.get(j).getMonadCF().getName()
   						 		);
   		}
   			 
   		add(monadPanes, "Center");
   	 }
    
    /**
     * The NyadPanel class is intended to be contain a cladosG Nyad in order to offer its parts
     * for display and manipulation by the calculator
     * 
     * @param pGUI			
     * 	CladosCalculator
     * This is just a reference to the owner application so error messages can be presented.
     * @param pN
     * 	NyadRealD
     * This is a reference to the nyad to be displayed and manipulated.
     * @throws UtilitiesException
     * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
     * @throws BadSignatureException
     * This exception is thrown when one of the monad panels can't accept the string signature offered.
     * That happens when something other than '+' or '-' is used... or maybe when signature is too long.
     * Remember that blade count is currently tracked with a short integer. {--****
     */
   	 public NyadPanel(	CladosCalculator pGUI,
   			 			NyadRealD pN)
   			 	throws 	UtilitiesException, BadSignatureException
   	 {
   		super();
   		if (pGUI==null)
   	      		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
   		_GUI=pGUI;
   		 
   	    if (pN==null)
   	      		throw new UtilitiesException("A Nyad must be passed to this MonadPanel constructor");
   	    _repNyadD=pN;
   		_repMode=CladosField.REALD;
   		 
   		setReferences();
   			 
   		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
   		setBackground(clrBackColor);
   		setLayout(new BorderLayout());

   		tabIcon = new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.TabM"));
   			 
   		createEditLayout();
   		createStackLayout();
   		createReferenceLayout();
   			 
   		monadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
   		monadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
   		monadPanelList=new ArrayList<MonadPanel>(_repNyadD.getMonadList().size());
   			 
   		for (short j=0; j<_repNyadD.getMonadList().size(); j++)
   		{
   			String count =new StringBuffer().append(j).toString();
   			monadPanelList.add(j, new MonadPanel(_GUI, _repNyadD.getMonadList(j)));
   			JScrollPane tempPane=new JScrollPane(monadPanelList.get(j));
   			tempPane.setWheelScrollingEnabled(true);
   				 
   			monadPanes.addTab(	count, 
   						 		tabIcon, 
   						 		tempPane,
   						 		_repNyadD.getName()+" | "+monadPanelList.get(j).getMonadRD().getName()
   						 		);
   		}
   			 
   		add(monadPanes, "Center");
   	 }
    
  /**
  * The NyadPanel class is intended to be contain a cladosG Nyad in order to offer its parts
  * for display and manipulation by the calculator
  * 
  * @param pGUI			
  * 	CladosCalculator
  * This is just a reference to the owner application so error messages can be presented.
  * @param pN
  * 	NyadRealF
  * This is a reference to the nyad to be displayed and manipulated.
  * @throws UtilitiesException
  * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
  * @throws BadSignatureException
  * This exception is thrown when one of the monad panels can't accept the string signature offered.
  * That happens when something other than '+' or '-' is used... or maybe when signature is too long.
  * Remember that blade count is currently tracked with a short integer. {--****
  */
	 public NyadPanel(	CladosCalculator pGUI,
			 			NyadRealF pN)
			 	throws 	UtilitiesException, BadSignatureException
	 {
		super();
		if (pGUI==null)
	      		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
		_GUI=pGUI;
		 
	    if (pN==null)
	      		throw new UtilitiesException("A Nyad must be passed to this MonadPanel constructor");
	    _repNyadF=pN;
		_repMode=CladosField.REALF;
		 
		setReferences();
			 
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setBackground(clrBackColor);
		setLayout(new BorderLayout());

		tabIcon = new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.TabM"));
			 
		createEditLayout();
		createStackLayout();
		createReferenceLayout();
			 
		monadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
		monadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		monadPanelList=new ArrayList<MonadPanel>(_repNyadF.getMonadList().size());
			 
		for (short j=0; j<_repNyadF.getMonadList().size(); j++)
		{
			String count =new StringBuffer().append(j).toString();
			monadPanelList.add(j, new MonadPanel(_GUI, _repNyadF.getMonadList(j)));
			JScrollPane tempPane=new JScrollPane(monadPanelList.get(j));
			tempPane.setWheelScrollingEnabled(true);
				 
			monadPanes.addTab(	count, 
						 		tabIcon, 
						 		tempPane,
						 		_repNyadF.getName()+" | "+monadPanelList.get(j).getMonadRF().getName()
						 		);
		}
			 
		add(monadPanes, "Center");
	 }

    public 	void 		actionPerformed(ActionEvent event)
    {
    	switch (event.getActionCommand())
    	{
	    	case "copy":	copyMonadCommand();	// It's a little long
	    					break;
	    	case "erase":	removeMonadCommand();
	    	 				break;
	    	case "create": 	CreateDialog.createMonad(_GUI, _repMode);	//Create a new monad for the selected nyad OR a whole new nyad
	    					break;
	    	case "push":    push();		//Swaps the currently selected nyad with the one below it
	    					break;
	        case "pop":    	pop();		//Swaps the currently selected nyad with the one above it
	        				break;
	        case "save":	setRepName();
							btnEditMonad.setActionCommand("edit");
					    	btnEditMonad.setToolTipText("start edits");
							btnSaveEdits.setEnabled(false);
					    	btnUndoEdits.setEnabled(false);
							makeNotWritable();
							break;
			case "abort":	setReferences();
				    		btnEditMonad.setActionCommand("edit");
				        	btnEditMonad.setToolTipText("start edits");
				    		btnSaveEdits.setEnabled(false);
				        	btnUndoEdits.setEnabled(false);
				    		makeNotWritable();
				    		break;
			case "edit":	btnEditMonad.setActionCommand(".edit.");
				    		btnEditMonad.setToolTipText("end edits w/o save");
				    		btnSaveEdits.setEnabled(true);
				        	btnUndoEdits.setEnabled(true);
				    		makeWritable();
				    		break;
			case ".edit.":	btnEditMonad.setActionCommand("edit");
				        	btnEditMonad.setToolTipText("start edits");
				    		btnSaveEdits.setEnabled(false);
				        	btnUndoEdits.setEnabled(false);
				    		makeNotWritable();
				    		break;
			default: 		_GUI._StatusBar.setStatusMsg("No Detectable Command at the Nyad Panel. No action.\n");
    	}
    }
    /**
     * This method supports adding a monad panel to the nyad's list of monads.
     * Be aware that no tests are performed to prevent the panel being added.
     * That means the nyad might change from strong to weak without notice by this panel.
     * <p>
     * The difference between this one and the other by a similar name is this one receives
     * a monad and constructs the appropriate panel.
     * <p>
     * @param pM
     * 	MonadComplexD
     * This is the MonadRealF to use to construct a MonadPanel to be appended to this NyadPanel.
     * @throws UtilitiesException
     * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
     */
    public	void		addMonadPanel(MonadComplexD pM) throws UtilitiesException
    {
    	MonadPanel pMP=new MonadPanel(_GUI, pM);
    	addMonadPanel(pMP); 
    }
    /**
     * This method supports adding a monad panel to the nyad's list of monads.
     * Be aware that no tests are performed to prevent the panel being added.
     * That means the nyad might change from strong to weak without notice by this panel.
     * <p>
     * The difference between this one and the other by a similar name is this one receives
     * a monad and constructs the appropriate panel.
     * <p>
     * @param pM
     * 	MonadComplexF
     * This is the MonadRealF to use to construct a MonadPanel to be appended to this NyadPanel.
     * @throws UtilitiesException
     * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
     */
    public	void		addMonadPanel(MonadComplexF pM) throws UtilitiesException
    {
    	MonadPanel pMP=new MonadPanel(_GUI, pM);
    	addMonadPanel(pMP); 
    }
    /**
     * This method supports adding a monad panel to the nyad's list of monads.
     * Be aware that no tests are performed to prevent the panel being added.
     * That means the nyad might change from strong to weak without notice by this panel.
     * @param pMP
     * 	MonadPanel
     * This is the MonadPanel to be appended to this NyadPanel. It is assumed that 
     * the monad panel is constructed elsewhere.
     */
    public	void		addMonadPanel(MonadPanel pMP)
    {
	    int next=monadPanes.getTabCount();
	    monadPanelList.ensureCapacity(next+1);
	    monadPanelList.add(pMP);

	    switch (pMP.getRepMode())
	    {
	    	case REALF:	monadPanes.addTab((	new StringBuffer()).append(next).toString(), 
	    												tabIcon, 
	    												new JScrollPane(pMP),
	    												_repNyadF.getName()+" | "+pMP.getMonadRF().getName());
	    							break;
	    	case REALD:	monadPanes.addTab((	new StringBuffer()).append(next).toString(), 
														tabIcon, 
														new JScrollPane(pMP),
														_repNyadD.getName()+" | "+pMP.getMonadRD().getName());
	    							break;
	    	case COMPLEXF:	monadPanes.addTab((	new StringBuffer()).append(next).toString(), 
														tabIcon, 
														new JScrollPane(pMP),
														_repNyadCF.getName()+" | "+pMP.getMonadCF().getName());
									break;
	    	case COMPLEXD:	monadPanes.addTab((	new StringBuffer()).append(next).toString(), 
														tabIcon, 
														new JScrollPane(pMP),
														_repNyadCD.getName()+" | "+pMP.getMonadCD().getName());			
	    }
	    nyadOrder.setText((new StringBuffer()).append(next+1).toString());
    }
    /**
     * This method supports adding a monad panel to the nyad's list of monads.
     * Be aware that no tests are performed to prevent the panel being added.
     * That means the nyad might change from strong to weak without notice by this panel.
     * <p>
     * The difference between this one and the other by a similar name is this one receives
     * a monad and constructs the appropriate panel.
     * <p>
     * @param pM
     * 	MonadRealD
     * This is the MonadRealD to use to construct a MonadPanel to be appended to this NyadPanel.
     * @throws UtilitiesException
     * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
     */
    public	void		addMonadPanel(MonadRealD pM) throws UtilitiesException
    {
    	MonadPanel pMP=new MonadPanel(_GUI, pM);
    	addMonadPanel(pMP); 
    }
    /**
     * This method supports adding a monad panel to the nyad's list of monads.
     * Be aware that no tests are performed to prevent the panel being added.
     * That means the nyad might change from strong to weak without notice by this panel.
     * <p>
     * The difference between this one and the other by a similar name is this one receives
     * a monad and constructs the appropriate panel.
     * <p>
     * @param pM
     * 	MonadRealF
     * This is the MonadRealF to use to construct a MonadPanel to be appended to this NyadPanel.
     * @throws UtilitiesException
     * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
     */
    public	void		addMonadPanel(MonadRealF pM) throws UtilitiesException
    {
    	MonadPanel pMP=new MonadPanel(_GUI, pM);
    	addMonadPanel(pMP); 
    }
   
    public	int			getMonadListSize()
    {
	    return monadPanelList.size();
    }
    /**
     * 
     * @param pInd int
     * get the MonadPanel at this integer spot
     * @return MonadPanel
     * The MonadPanel at the indicated location in the monadPanelList
     */
    public	MonadPanel	getMonadPanel(int pInd)
    {
	    int limit=monadPanelList.size();
	    if (pInd<limit)
	    {
		    MonadPanel temp = (MonadPanel)monadPanelList.get(pInd);
		    return temp;
	    }
	    return null;
    }
    public NyadComplexD getNyadCD()
    {
	    return _repNyadCD;
    }
    public NyadComplexF	getNyadCF()
    {
	    return _repNyadCF;
    }
    public NyadRealD 	getNyadRD()
    {
	    return _repNyadD;
    }
    public NyadRealF 	getNyadRF()
    {
	    return _repNyadF;
    }
    public int 			getOrder()
    {
    	return monadPanelList.size();
    }
    
    public	int			getPaneFocus()
    {
	    return monadPanes.getSelectedIndex();
    }
    public CladosField		getRepMode()
    {
    	return _repMode;
    }
    public 	void 		makeNotWritable()
    {
    	if (pnlRefPanel!=null)
    		pnlRefPanel.setBackground(clrBackColor);
    	nyadName.setEditable(false);
    }

    /**
     * This method adjusts the JTextArea elements contained on the panel to allow for edits.
     */
    public 	void 		makeWritable()
    {
    	if (pnlRefPanel!=null)
    		pnlRefPanel.setBackground(clrUnlockColor);
    	nyadName.setEditable(true);
    }
    
    /**
     * This method removes the Monad Panel at the integer index indicated.
     * @param pInd
     *  int
     * This the index of the monad panel to remove.
     * No checks are made right now for out-of-bounds conditions.
     * This should be fixed.
     */
    public	void		removeMonadTab(int pInd)
    {
    	if(pInd>monadPanelList.size()) return;	//Index out of bounds. Don't try
    	if(pInd>monadPanes.getTabCount()) return; //Index out of bounds on the panes. Don't try.
    	
    	int newOrder=monadPanes.getTabCount()-1;
	    monadPanes.remove(pInd);
	    monadPanelList.remove(pInd);
	    nyadOrder.setText(new StringBuffer().append(newOrder).toString());
    }
    
    private void		copyMonadCommand()
    {
    	if (getMonadListSize()<=0) return;	// Nothing to do here. No monad to be copied.
    	String buildName="";
    	String buildAlgName="";
    	String buildFrameName="";
		try
		{
			switch (_repMode)
			{
				case REALF:	MonadRealF focusMonadRF=getMonadPanel(getPaneFocus()).getMonadRF();
										buildName=new StringBuffer(focusMonadRF.getName()).append("_c").toString();
										buildAlgName =new StringBuffer(focusMonadRF.getAlgebra().getAlgebraName()).append("_c").toString();
										buildFrameName = new StringBuffer(focusMonadRF.getFrameName()).append("_c").toString();
											
										AlgebraRealF buildAlgRF = new AlgebraRealF(	buildAlgName, 
																					focusMonadRF.getAlgebra().getFoot(),
																					focusMonadRF.getAlgebra().getGProduct());
										MonadRealF newMonadCopyRF=new MonadRealF(		buildName, 
																					buildAlgRF,
																					buildFrameName,
																					focusMonadRF.getCoeff());
										_repNyadF.appendMonad(newMonadCopyRF);
										addMonadPanel(newMonadCopyRF);
										break;
				case REALD:	MonadRealD focusMonadRD=getMonadPanel(getPaneFocus()).getMonadRD();
										buildName=new StringBuffer(focusMonadRD.getName()).append("_c").toString();
										buildAlgName =new StringBuffer(focusMonadRD.getAlgebra().getAlgebraName()).append("_c").toString();
										buildFrameName = new StringBuffer(focusMonadRD.getFrameName()).append("_c").toString();
											
										AlgebraRealD buildAlgRD = new AlgebraRealD(	buildAlgName, 
																					focusMonadRD.getAlgebra().getFoot(),
																					focusMonadRD.getAlgebra().getGProduct());
										MonadRealD newMonadCopyRD=new MonadRealD(		buildName, 
																					buildAlgRD,
																					buildFrameName,
																					focusMonadRD.getCoeff());
										_repNyadD.appendMonad(newMonadCopyRD);
										addMonadPanel(newMonadCopyRD);
										break;
				case COMPLEXF:	MonadComplexF focusMonadCF=getMonadPanel(getPaneFocus()).getMonadCF();
										buildName=new StringBuffer(focusMonadCF.getName()).append("_c").toString();
										buildAlgName =new StringBuffer(focusMonadCF.getAlgebra().getAlgebraName()).append("_c").toString();
										buildFrameName = new StringBuffer(focusMonadCF.getFrameName()).append("_c").toString();
											
										AlgebraComplexF buildAlgCF = new AlgebraComplexF(	buildAlgName, 
																							focusMonadCF.getAlgebra().getFoot(),
																							focusMonadCF.getAlgebra().getGProduct());
										MonadComplexF newMonadCopyCF=new MonadComplexF(		buildName, 
																							buildAlgCF,
																							buildFrameName,
																							focusMonadCF.getCoeff());
										_repNyadCF.appendMonad(newMonadCopyCF);
										addMonadPanel(newMonadCopyCF);
										break;
				case COMPLEXD:	MonadComplexD focusMonadCD=getMonadPanel(getPaneFocus()).getMonadCD();
										buildName=new StringBuffer(focusMonadCD.getName()).append("_c").toString();
										buildAlgName =new StringBuffer(focusMonadCD.getAlgebra().getAlgebraName()).append("_c").toString();
										buildFrameName = new StringBuffer(focusMonadCD.getFrameName()).append("_c").toString();
											
										AlgebraComplexD buildAlgCD = new AlgebraComplexD(	buildAlgName, 
																							focusMonadCD.getAlgebra().getFoot(),
																							focusMonadCD.getAlgebra().getGProduct());
										MonadComplexD newMonadCopyCD=new MonadComplexD(		buildName, 
																							buildAlgCD,
																							buildFrameName,
																							focusMonadCD.getCoeff());
										_repNyadCD.appendMonad(newMonadCopyCD);
										addMonadPanel(newMonadCopyCD);
			}
		}
		catch (UtilitiesException e)
		{
			_GUI._StatusBar.setStatusMsg("\t\tcould not create copy from toolbar.\n");
		}
		catch (CladosMonadException e) 
		{
			_GUI._StatusBar.setStatusMsg("\t\tcould not create copy because monad was malformed.\n");
		} 
		catch (CladosNyadException e) 
		{
			_GUI._StatusBar.setStatusMsg("\t\tcould not append monad because nyad objected.\n");
		}
		
    }
    
    private 	void		createEditLayout()
    {
    	pnlControlPanel=new JPanel();
    	pnlControlPanel.setBackground(clrBackColor);
    	//if ((_GUI.IniProps.getProperty("Desktop.Default.Object")).equals("Monad")) return;
    	
    	pnlControlPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	pnlControlPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn = new GridBagConstraints();
    	cn.insets = new Insets(0, 0, 0, 0);
    	//cn.fill=GridBagConstraints.HORIZONTAL;
    	cn.anchor=GridBagConstraints.NORTH;
    	makeNotWritable();
    	
   	 	btnSaveEdits=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Save")));
   	 	btnSaveEdits.setActionCommand("save");
   	 	btnSaveEdits.setToolTipText("save edits to nyad");
   	 	btnSaveEdits.setBorder(BorderFactory.createEtchedBorder(0));
   	 	btnSaveEdits.setEnabled(false);
	 	btnSaveEdits.setPreferredSize(square);
   	 	
   	 	btnEditMonad=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Edit")));
    	btnEditMonad.setActionCommand("edit");
    	btnEditMonad.setToolTipText("start edits on nyad");
    	btnEditMonad.setBorder(BorderFactory.createEtchedBorder(0));
	 	btnEditMonad.setPreferredSize(square);
	 	
	 	btnUndoEdits=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Restore")));
    	btnUndoEdits.setActionCommand("abort");
    	btnUndoEdits.setToolTipText("abandon edits to nyad");
    	btnUndoEdits.setBorder(BorderFactory.createEtchedBorder(0));
    	btnUndoEdits.setEnabled(false);
	 	btnUndoEdits.setPreferredSize(square);
    	
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=0;
    	cn.weighty=0;
    	
    	btnEditMonad.addActionListener(this);
    	pnlControlPanel.add(btnEditMonad, cn);
    	cn.gridy++;
    	
    	btnSaveEdits.addActionListener(this);
    	pnlControlPanel.add(btnSaveEdits, cn);
    	cn.gridy++;
    	
    	btnUndoEdits.addActionListener(this);
    	pnlControlPanel.add(btnUndoEdits, cn);
    	cn.gridy++;
    
    	cn.weighty=1;
    	pnlControlPanel.add(new JLabel(), cn);
    	
    	add(pnlControlPanel, "West");
    }
    
    private 	void		createReferenceLayout()
    {
    	pnlRefPanel=new JPanel();
    	pnlRefPanel.setBackground(clrBackColor);
    	
    	pnlRefPanel.setBorder(BorderFactory.createTitledBorder("N"));
    	pnlRefPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn0 = new GridBagConstraints();
    	cn0.anchor=GridBagConstraints.WEST;

    	cn0.gridx = 0;
    	cn0.gridy = 0;
    	cn0.weightx=0;
    	cn0.weighty=0;
    	
    	pnlRefPanel.add(new JLabel("Name ", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	nyadName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
    	cn0.weightx=1;
    	pnlRefPanel.add(nyadName, cn0);	
    	cn0.gridx++;
    	
    	protoXML.setFont(new Font(Font.SERIF, Font.PLAIN, 8));
    	protoXML.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	pnlRefPanel.add(protoXML, cn0);
    	cn0.gridx++;
    	
    	cn0.weightx=0;
    	cn0.ipadx=20;
    	pnlRefPanel.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Foot"))), cn0);
    	cn0.gridx++;
    	nyadFoot.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
    	nyadFoot.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	pnlRefPanel.add(nyadFoot, cn0);
    	cn0.gridx++;
    	
    	pnlRefPanel.add(new JLabel("Order=", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	nyadOrder.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
    	nyadOrder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	pnlRefPanel.add(nyadOrder, cn0);
    	
    	add(pnlRefPanel, "South");
    }
    
    private void createStackLayout()
    {
    	pnlControlPanel2=new JPanel();
    	pnlControlPanel2.setLayout(new GridBagLayout());
    	pnlControlPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	pnlControlPanel2.setBackground(clrBackColor);
  	    
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
    	btnSwapBelow.setToolTipText("push monad down on stack");
    	btnSwapBelow.setPreferredSize(square);
    	btnSwapBelow.setBorder(BorderFactory.createEtchedBorder(0));
    	btnSwapBelow.addActionListener(this);
    	pnlControlPanel2.add(btnSwapBelow, cn);
    	cn.gridy++;
    	
    	btnSwapAbove=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Pop")));
    	btnSwapAbove.setActionCommand("pop");
    	btnSwapAbove.setToolTipText("pop monad up on stack");
    	btnSwapAbove.setPreferredSize(square);
    	btnSwapAbove.setBorder(BorderFactory.createEtchedBorder(0));
    	btnSwapAbove.addActionListener(this);
    	pnlControlPanel2.add(btnSwapAbove, cn);
		cn.gridy++;
    	
		btnCopyMonad=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Copy")));
   	 	btnCopyMonad.setActionCommand("copy");
   	 	btnCopyMonad.setToolTipText("copy monad to end of stack");
   	 	btnCopyMonad.setPreferredSize(square);
   	 	btnCopyMonad.setBorder(BorderFactory.createEtchedBorder(0));
   	 	btnCopyMonad.addActionListener(this);
   	 	pnlControlPanel2.add(btnCopyMonad, cn);
    	cn.gridy++;
    	
    	btnRemoveMonad=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Remove")));
   	 	btnRemoveMonad.setActionCommand("erase");
   	 	btnRemoveMonad.setToolTipText("remove monad from stack");
   	 	btnRemoveMonad.setPreferredSize(square);
   	 	btnRemoveMonad.setBorder(BorderFactory.createEtchedBorder(0));
   	 	btnRemoveMonad.addActionListener(this);
    	pnlControlPanel2.add(btnRemoveMonad, cn);
    	cn.gridy++;
    	
    	btnNewMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Create")));
    	btnNewMonad.setActionCommand("create");
    	btnNewMonad.setToolTipText("create new monad");
    	btnNewMonad.setPreferredSize(square);
    	btnNewMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	btnNewMonad.addActionListener(this);
    	pnlControlPanel2.add(btnNewMonad, cn);
    	cn.gridy++;
    	
    	cn.weighty=1;
    	pnlControlPanel2.add(new JLabel(), cn);
    	
    	add(pnlControlPanel2,"East");
    }
    
    private	void		pop()
    {
	    int where=monadPanes.getSelectedIndex();
	    if (where>0)
	    {
		    String otherTitle=new String(monadPanes.getTitleAt(where-1));
		    JScrollPane otherPane=new JScrollPane((JPanel)monadPanelList.get(where-1));

		    String thisTitle=new String(monadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)monadPanelList.get(where));

		    monadPanes.setTitleAt(where, otherTitle);
		    monadPanes.setComponentAt(where, otherPane);

		    monadPanes.setTitleAt(where-1, thisTitle);
		    monadPanes.setComponentAt(where-1, thisPane);

		    MonadPanel tempPanel=(MonadPanel)monadPanelList.remove(where-1);
		    monadPanelList.add(where, tempPanel);
	    }
    }
    
    private	void		push()
    {
	    int size=monadPanes.getTabCount();
	    int where=monadPanes.getSelectedIndex();
	    if (where<size-1)
	    {
		    String otherTitle=new String(monadPanes.getTitleAt(where+1));
		    JScrollPane otherPane=new JScrollPane((JPanel)monadPanelList.get(where+1));

		    String thisTitle=new String(monadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)monadPanelList.get(where));

		    monadPanes.setTitleAt(where, otherTitle);
		    monadPanes.setComponentAt(where, otherPane);

		    monadPanes.setTitleAt(where+1, thisTitle);
		    monadPanes.setComponentAt(where+1, thisPane);

		    MonadPanel tempPanel=(MonadPanel)monadPanelList.remove(where);
		    monadPanelList.add(where+1, tempPanel);
		    
		    revalidate();
	    }
    }
    private void		removeMonadCommand()
    {
    	if (monadPanes.getTabCount()>1)
		{
			try 
			{
				int point = monadPanes.getSelectedIndex();
				switch (_repMode)
				{
					case REALF: 	_repNyadF.removeMonad(point);
									break;
					case REALD: 	_repNyadD.removeMonad(point);
									break;
					case COMPLEXF:	_repNyadCF.removeMonad(point);
									break;
					case COMPLEXD:	_repNyadCD.removeMonad(point);
				}
				removeMonadTab(point);
			} 
			catch (CladosNyadException e) 
			{
				_GUI._StatusBar.setStatusMsg("Could not remove the monad. "+e.getSourceMessage()+"\n");
			}
		}
		else	// The only way to get here is if the monad to be removed is the last one in the nyad.
		{		// That causes the entire nyad to be removed.
			_GUI._GeometryDisplay.removeNyadPanel(0);
		}
    }
    
    private		void		setRepName()
    {
    	switch (_repMode)
		{
    		case REALF:		if (nyadName.getText() != _repNyadF.getName()) _repNyadF.setName(nyadName.getText());
    						break;
    		case REALD:		if (nyadName.getText() != _repNyadD.getName()) _repNyadD.setName(nyadName.getText());
							break;
    		case COMPLEXF:	if (nyadName.getText() != _repNyadCF.getName()) _repNyadCF.setName(nyadName.getText());
							break;
    		case COMPLEXD:	if (nyadName.getText() != _repNyadCD.getName()) _repNyadCD.setName(nyadName.getText());
		}
    }

	private 	void 		setReferences()
    {
    	switch (_repMode)
    	{
    		case REALF:	nyadName.setText(_repNyadF.getName());
    					protoXML.setText(_repNyadF.getProto().toXMLString());
	    				nyadOrder.setText((new StringBuffer().append(_repNyadF.getMonadList().size())).toString());
	    				nyadFoot.setText(_repNyadF.getFootPoint().getFootName());
	    				break;
    		case REALD:	nyadName.setText(_repNyadD.getName());
    					protoXML.setText(_repNyadD.getProto().toXMLString());
	    				nyadOrder.setText((new StringBuffer().append(_repNyadD.getMonadList().size())).toString());
	    				nyadFoot.setText(_repNyadD.getFootPoint().getFootName());
	    				break;
    		case COMPLEXF:	nyadName.setText(_repNyadCF.getName());
    						protoXML.setText(_repNyadCF.getProto().toXMLString());
	    					nyadOrder.setText((new StringBuffer().append(_repNyadCF.getMonadList().size())).toString());
	    					nyadFoot.setText(_repNyadCF.getFootPoint().getFootName());
	    					break;
    		case COMPLEXD: nyadName.setText(_repNyadCD.getName());
    						protoXML.setText(_repNyadCD.getProto().toXMLString());
	    					nyadOrder.setText((new StringBuffer().append(_repNyadCD.getMonadList().size())).toString());
	    					nyadFoot.setText(_repNyadCD.getFootPoint().getFootName());
    	}
    }
}
