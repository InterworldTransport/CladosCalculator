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

import com.interworldtransport.cladosF.DivField;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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

 public class NyadPanel extends JPanel implements ActionListener, FocusListener
{
	public					CladosCalculator		_GUI;
	public					JTabbedPane				monadPanes;
	public					NyadComplexD			repNyadCD;
	public					NyadComplexF			repNyadCF;
	public					NyadRealD				repNyadD;
	public					NyadRealF				repNyadF;
	private			final	Color					_backColor = new Color(212, 200, 212);
	private					JPanel 					_controlPanel;
	private					JPanel 					_controlPanel2;
	private					JPanel 					_refPanel;
	private			final	Color					_unlockColor = new Color(255, 192, 192);
	private					JButton					copyButton;
	private					JButton					editButton;
	private					JButton					newMonad;
	private					JLabel					nyadFoot=new JLabel();
	private					JTextField				nyadName=new JTextField(20);
	private					JLabel					nyadOrder=new JLabel();
	private					JTextField				protoXML=new JTextField(40);
	private					JButton					removeButton;
	private					JButton					restoreButton;
	private					JButton					saveButton;
	private 		final	Dimension 				square = new Dimension(25,25);
	private					JButton					swapAbove;
	private					JButton					swapBelow;
	private					ImageIcon				tabIcon;
	protected				String					_repMode; 
    protected				ArrayList<MonadPanel>	monadPanelList;
    
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
   	    repNyadCD=pN;
   		_repMode=DivField.COMPLEXD;
   		 
   		setReferences();
   			 
   		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
   		setBackground(_backColor);
   		setLayout(new BorderLayout());

   		tabIcon = new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.TabM"));
   			 
   		createEditLayout();
   		createStackLayout();
   		createReferenceLayout();
   			 
   		monadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
   		monadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
   		monadPanelList=new ArrayList<MonadPanel>(repNyadCD.getMonadList().size());
   			 
   		for (short j=0; j<repNyadCD.getMonadList().size(); j++)
   		{
   			String count =new StringBuffer().append(j).toString();
   			monadPanelList.add(j, new MonadPanel(_GUI, repNyadCD.getMonadList(j)));
   			JScrollPane tempPane=new JScrollPane(monadPanelList.get(j));
   			tempPane.setWheelScrollingEnabled(true);
   				 
   			monadPanes.addTab(	count, 
   						 		tabIcon, 
   						 		tempPane,
   						 		repNyadCD.getName()+" | "+monadPanelList.get(j).getMonadCD().getName()
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
   	    repNyadCF=pN;
   		_repMode=DivField.COMPLEXF;
   		 
   		setReferences();
   			 
   		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
   		setBackground(_backColor);
   		setLayout(new BorderLayout());

   		tabIcon = new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.TabM"));
   			 
   		createEditLayout();
   		createStackLayout();
   		createReferenceLayout();
   			 
   		monadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
   		monadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
   		monadPanelList=new ArrayList<MonadPanel>(repNyadCF.getMonadList().size());
   			 
   		for (short j=0; j<repNyadCF.getMonadList().size(); j++)
   		{
   			String count =new StringBuffer().append(j).toString();
   			monadPanelList.add(j, new MonadPanel(_GUI, repNyadCF.getMonadList(j)));
   			JScrollPane tempPane=new JScrollPane(monadPanelList.get(j));
   			tempPane.setWheelScrollingEnabled(true);
   				 
   			monadPanes.addTab(	count, 
   						 		tabIcon, 
   						 		tempPane,
   						 		repNyadCF.getName()+" | "+monadPanelList.get(j).getMonadCF().getName()
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
   	    repNyadD=pN;
   		_repMode=DivField.REALD;
   		 
   		setReferences();
   			 
   		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
   		setBackground(_backColor);
   		setLayout(new BorderLayout());

   		tabIcon = new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.TabM"));
   			 
   		createEditLayout();
   		createStackLayout();
   		createReferenceLayout();
   			 
   		monadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
   		monadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
   		monadPanelList=new ArrayList<MonadPanel>(repNyadD.getMonadList().size());
   			 
   		for (short j=0; j<repNyadD.getMonadList().size(); j++)
   		{
   			String count =new StringBuffer().append(j).toString();
   			monadPanelList.add(j, new MonadPanel(_GUI, repNyadD.getMonadList(j)));
   			JScrollPane tempPane=new JScrollPane(monadPanelList.get(j));
   			tempPane.setWheelScrollingEnabled(true);
   				 
   			monadPanes.addTab(	count, 
   						 		tabIcon, 
   						 		tempPane,
   						 		repNyadD.getName()+" | "+monadPanelList.get(j).getMonadRD().getName()
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
	    repNyadF=pN;
		_repMode=DivField.REALF;
		 
		setReferences();
			 
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setBackground(_backColor);
		setLayout(new BorderLayout());

		tabIcon = new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.TabM"));
			 
		createEditLayout();
		createStackLayout();
		createReferenceLayout();
			 
		monadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
		monadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		monadPanelList=new ArrayList<MonadPanel>(repNyadF.getMonadList().size());
			 
		for (short j=0; j<repNyadF.getMonadList().size(); j++)
		{
			String count =new StringBuffer().append(j).toString();
			monadPanelList.add(j, new MonadPanel(_GUI, repNyadF.getMonadList(j)));
			JScrollPane tempPane=new JScrollPane(monadPanelList.get(j));
			tempPane.setWheelScrollingEnabled(true);
				 
			monadPanes.addTab(	count, 
						 		tabIcon, 
						 		tempPane,
						 		repNyadF.getName()+" | "+monadPanelList.get(j).getMonadRF().getName()
						 		);
		}
			 
		add(monadPanes, "Center");
	 }

    public 	void 		actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	
    	if (command.equals("push"))
    		push();		//Swaps the currently selected nyad with the one below it
    	
    	if (command.equals("pop"))
    		pop();		//Swaps the currently selected nyad with the one above it
    	
    	if (command=="save")
    	{
    		switch (_repMode)
    		{
	    		case DivField.REALF:	if (nyadName.getText() != repNyadF.getName()) repNyadF.setName(nyadName.getText());
	    								break;
	    		case DivField.REALD:	if (nyadName.getText() != repNyadD.getName()) repNyadD.setName(nyadName.getText());
										break;
	    		case DivField.COMPLEXF:	if (nyadName.getText() != repNyadCF.getName()) repNyadCF.setName(nyadName.getText());
										break;
	    		case DivField.COMPLEXD:	if (nyadName.getText() != repNyadCD.getName()) repNyadCD.setName(nyadName.getText());
    		}
    		command=".edit.";
    	}
    	if (command=="abort")
    	{
    		setReferences();
    		command=".edit.";
    	}
    	
    	if (command=="copy")
    		copyMonadCommand();

    	if (command=="erase")
    		removeMonadCommand();
    	
    	if (command.equals("create"))
    		CreateDialog.createMonad(_GUI, _repMode);			//Create a new monad for the selected nyad OR a whole new nyad
    	
    	if (command=="edit")
    	{
    		editButton.setActionCommand(".edit.");
    		editButton.setToolTipText("end edits w/o save");
    		saveButton.setEnabled(true);
        	restoreButton.setEnabled(true);
    		makeWritable();
    	}
    	
    	if (command==".edit.")
    	{
    		editButton.setActionCommand("edit");
        	editButton.setToolTipText("start edits");
    		saveButton.setEnabled(false);
        	restoreButton.setEnabled(false);
    		makeNotWritable();
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
	    	case DivField.REALF:	monadPanes.addTab((	new StringBuffer()).append(next).toString(), 
	    												tabIcon, 
	    												new JScrollPane(pMP),
	    												repNyadF.getName()+" | "+pMP.getMonadRF().getName());
	    							break;
	    	case DivField.REALD:	monadPanes.addTab((	new StringBuffer()).append(next).toString(), 
														tabIcon, 
														new JScrollPane(pMP),
														repNyadD.getName()+" | "+pMP.getMonadRD().getName());
	    							break;
	    	case DivField.COMPLEXF:	monadPanes.addTab((	new StringBuffer()).append(next).toString(), 
														tabIcon, 
														new JScrollPane(pMP),
														repNyadCF.getName()+" | "+pMP.getMonadCF().getName());
									break;
	    	case DivField.COMPLEXD:	monadPanes.addTab((	new StringBuffer()).append(next).toString(), 
														tabIcon, 
														new JScrollPane(pMP),
														repNyadCD.getName()+" | "+pMP.getMonadCD().getName());			
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
    /**
     * This method is overridden to allow the NyadPanel with the focus to update the FieldBar with 
     * the DivFieldType of the protonumber within the represented nyad.
     * This is similar to what a monad panel does when it receives focus and updates the values 
     * in the FieldBar.
     */
	@Override
	public void focusGained(FocusEvent e) 
	{
		if (e.getComponent() instanceof NyadPanel)
		{
			switch (((NyadPanel)e.getComponent()).getRepMode())
			{
				case DivField.REALF:	_GUI._FieldBar.setFieldType((((NyadPanel) e.getComponent()).getNyadRF().getProto()).getFieldType());;
										break;
				case DivField.REALD:	_GUI._FieldBar.setFieldType((((NyadPanel) e.getComponent()).getNyadRD().getProto()).getFieldType());;
										break;
				case DivField.COMPLEXF:	_GUI._FieldBar.setFieldType((((NyadPanel) e.getComponent()).getNyadCF().getProto()).getFieldType());;
										break;
				case DivField.COMPLEXD:	_GUI._FieldBar.setFieldType((((NyadPanel) e.getComponent()).getNyadCD().getProto()).getFieldType());;
			}
		}
		else _GUI._StatusBar.setStatusMsg("\n\nNot sure what got focus on the Nyad Panel, but it did.");
	}

    @Override
	public void focusLost(FocusEvent e) 
	{
		;
	}
    public	int			getMonadListSize()
    {
	    return monadPanelList.size();
    }
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
	    return repNyadCD;
    }
    public NyadComplexF	getNyadCF()
    {
	    return repNyadCF;
    }
    public NyadRealD 	getNyadRD()
    {
	    return repNyadD;
    }
    public NyadRealF 	getNyadRF()
    {
	    return repNyadF;
    }
    public int 			getOrder()
    {
    	return monadPanelList.size();
    }
    
    public	int			getPaneFocus()
    {
	    return monadPanes.getSelectedIndex();
    }
    public String		getRepMode()
    {
    	return _repMode;
    }
    public 	void 		makeNotWritable()
    {
    	if (_refPanel!=null)
    		_refPanel.setBackground(_backColor);
    	nyadName.setEditable(false);
    	//_order.setEditable(false);
    	//_foot.setEditable(false);
    }

    /**
     * This method adjusts the JTextArea elements contained on the panel to allow for edits.
     */
    public 	void 		makeWritable()
    {
    	if (_refPanel!=null)
    		_refPanel.setBackground(_unlockColor);
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
				case DivField.REALF:	MonadRealF focusMonadRF=getMonadPanel(getPaneFocus()).getMonadRF();
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
										repNyadF.appendMonad(newMonadCopyRF);
										addMonadPanel(newMonadCopyRF);
										break;
				case DivField.REALD:	MonadRealD focusMonadRD=getMonadPanel(getPaneFocus()).getMonadRD();
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
										repNyadD.appendMonad(newMonadCopyRD);
										addMonadPanel(newMonadCopyRD);
										break;
				case DivField.COMPLEXF:	MonadComplexF focusMonadCF=getMonadPanel(getPaneFocus()).getMonadCF();
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
										repNyadCF.appendMonad(newMonadCopyCF);
										addMonadPanel(newMonadCopyCF);
										break;
				case DivField.COMPLEXD:	MonadComplexD focusMonadCD=getMonadPanel(getPaneFocus()).getMonadCD();
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
										repNyadCD.appendMonad(newMonadCopyCD);
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
    	_controlPanel=new JPanel();
    	_controlPanel.setBackground(_backColor);
    	//if ((_GUI.IniProps.getProperty("Desktop.Default.Object")).equals("Monad")) return;
    	
    	_controlPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	_controlPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn = new GridBagConstraints();
    	cn.insets = new Insets(0, 0, 0, 0);
    	//cn.fill=GridBagConstraints.HORIZONTAL;
    	cn.anchor=GridBagConstraints.NORTH;
    	makeNotWritable();
    	
   	 	saveButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Save")));
   	 	saveButton.setActionCommand("save");
   	 	saveButton.setToolTipText("save edits to nyad");
   	 	saveButton.setBorder(BorderFactory.createEtchedBorder(0));
   	 	saveButton.setEnabled(false);
	 	saveButton.setPreferredSize(square);
   	 	
   	 	editButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Edit")));
    	editButton.setActionCommand("edit");
    	editButton.setToolTipText("start edits on nyad");
    	editButton.setBorder(BorderFactory.createEtchedBorder(0));
	 	editButton.setPreferredSize(square);
	 	
	 	restoreButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Restore")));
    	restoreButton.setActionCommand("abort");
    	restoreButton.setToolTipText("abandon edits to nyad");
    	restoreButton.setBorder(BorderFactory.createEtchedBorder(0));
    	restoreButton.setEnabled(false);
	 	restoreButton.setPreferredSize(square);
    	
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=0;
    	cn.weighty=0;
    	
    	editButton.addActionListener(this);
    	_controlPanel.add(editButton, cn);
    	cn.gridy++;
    	
    	saveButton.addActionListener(this);
    	_controlPanel.add(saveButton, cn);
    	cn.gridy++;
    	
    	restoreButton.addActionListener(this);
    	_controlPanel.add(restoreButton, cn);
    	cn.gridy++;
    
    	cn.weighty=1;
    	_controlPanel.add(new JLabel(), cn);
    	
    	add(_controlPanel, "West");
    }
    
    private 	void		createReferenceLayout()
    {
    	_refPanel=new JPanel();
    	_refPanel.setBackground(_backColor);
    	
    	_refPanel.setBorder(BorderFactory.createTitledBorder("N"));
    	_refPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn0 = new GridBagConstraints();
    	cn0.anchor=GridBagConstraints.WEST;

    	cn0.gridx = 0;
    	cn0.gridy = 0;
    	cn0.weightx=0;
    	cn0.weighty=0;
    	
    	_refPanel.add(new JLabel("Name ", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	nyadName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
    	cn0.weightx=1;
    	_refPanel.add(nyadName, cn0);	
    	cn0.gridx++;
    	
    	protoXML.setFont(new Font(Font.SERIF, Font.PLAIN, 8));
    	protoXML.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	_refPanel.add(protoXML, cn0);
    	cn0.gridx++;
    	
    	cn0.weightx=0;
    	cn0.ipadx=20;
    	_refPanel.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Foot"))), cn0);
    	cn0.gridx++;
    	nyadFoot.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	nyadFoot.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	_refPanel.add(nyadFoot, cn0);
    	cn0.gridx++;
    	
    	_refPanel.add(new JLabel("Order ", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	//_order.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	nyadOrder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	_refPanel.add(nyadOrder, cn0);
    	
    	add(_refPanel, "South");
    }
    
    private void createStackLayout()
    {
    	_controlPanel2=new JPanel();
    	_controlPanel2.setLayout(new GridBagLayout());
    	_controlPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	_controlPanel2.setBackground(_backColor);
  	    
    	GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);
		cn.anchor=GridBagConstraints.NORTH;
		
		cn.gridx = 0;
		cn.gridy = 0;
		
		cn.weightx=0;
		cn.weighty=0;
		cn.gridheight=1;
		cn.gridwidth=1;
    	swapBelow=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Push")));
    	swapBelow.setActionCommand("push");
    	swapBelow.setToolTipText("push monad down on stack");
    	swapBelow.setPreferredSize(square);
    	swapBelow.setBorder(BorderFactory.createEtchedBorder(0));
    	swapBelow.addActionListener(this);
    	_controlPanel2.add(swapBelow, cn);
    	cn.gridy++;
    	
    	swapAbove=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Pop")));
    	swapAbove.setActionCommand("pop");
    	swapAbove.setToolTipText("pop monad up on stack");
    	swapAbove.setPreferredSize(square);
    	swapAbove.setBorder(BorderFactory.createEtchedBorder(0));
    	swapAbove.addActionListener(this);
    	_controlPanel2.add(swapAbove, cn);
		cn.gridy++;
    	
		copyButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Copy")));
   	 	copyButton.setActionCommand("copy");
   	 	copyButton.setToolTipText("copy monad to end of stack");
   	 	copyButton.setPreferredSize(square);
   	 	copyButton.setBorder(BorderFactory.createEtchedBorder(0));
   	 	copyButton.addActionListener(this);
   	 	_controlPanel2.add(copyButton, cn);
    	cn.gridy++;
    	
    	removeButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Remove")));
   	 	removeButton.setActionCommand("erase");
   	 	removeButton.setToolTipText("remove monad from stack");
   	 	removeButton.setPreferredSize(square);
   	 	removeButton.setBorder(BorderFactory.createEtchedBorder(0));
   	 	removeButton.addActionListener(this);
    	_controlPanel2.add(removeButton, cn);
    	cn.gridy++;
    	
    	newMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Create")));
    	newMonad.setActionCommand("create");
    	newMonad.setToolTipText("create new monad");
    	newMonad.setPreferredSize(square);
    	newMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	newMonad.addActionListener(this);
    	_controlPanel2.add(newMonad, cn);
    	cn.gridy++;
    	
    	cn.weighty=1;
    	_controlPanel2.add(new JLabel(), cn);
    	
    	add(_controlPanel2,"East");
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
					case DivField.REALF: 	repNyadF.removeMonad(point);
											break;
					case DivField.REALD: 	repNyadD.removeMonad(point);
											break;
					case DivField.COMPLEXF:	repNyadCF.removeMonad(point);
											break;
					case DivField.COMPLEXD:	repNyadCD.removeMonad(point);
				}
				removeMonadTab(point);
			} 
			catch (CladosNyadException e) 
			{
				e.printStackTrace();
			}
		}
		else	// The only way to get here is if the monad to be removed is the last one in the nyad.
		{		// That causes the entire nyad to be removed.
			_GUI._GeometryDisplay.removeNyad.doClick();
		}
    }

	private 	void 		setReferences()
    {
    	switch (_repMode)
    	{
    		case DivField.REALF:	nyadName.setText(repNyadF.getName());
    								protoXML.setText(repNyadF.getProto().toXMLString());
	    							nyadOrder.setText((new StringBuffer().append(repNyadF.getMonadList().size())).toString());
	    							nyadFoot.setText(repNyadF.getFootPoint().getFootName());
	    							break;
    		case DivField.REALD:	nyadName.setText(repNyadD.getName());
    								protoXML.setText(repNyadD.getProto().toXMLString());
	    							nyadOrder.setText((new StringBuffer().append(repNyadD.getMonadList().size())).toString());
	    							nyadFoot.setText(repNyadD.getFootPoint().getFootName());
	    							break;
    		case DivField.COMPLEXF:	nyadName.setText(repNyadCF.getName());
    								protoXML.setText(repNyadCF.getProto().toXMLString());
	    							nyadOrder.setText((new StringBuffer().append(repNyadCF.getMonadList().size())).toString());
	    							nyadFoot.setText(repNyadCF.getFootPoint().getFootName());
	    							break;
    		case DivField.COMPLEXD: nyadName.setText(repNyadCD.getName());
    								protoXML.setText(repNyadCD.getProto().toXMLString());
	    							nyadOrder.setText((new StringBuffer().append(repNyadCD.getMonadList().size())).toString());
	    							nyadFoot.setText(repNyadCD.getFootPoint().getFootName());
    	}
    }
}
