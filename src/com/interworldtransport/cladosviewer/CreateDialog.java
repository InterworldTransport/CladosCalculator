/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.CreateDialog<br>
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
 * ---com.interworldtransport.cladosviewer.CreateDialog<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer;
import com.interworldtransport.cladosF.CladosField;
import com.interworldtransport.cladosF.ComplexD;
import com.interworldtransport.cladosF.ComplexF;
//import com.interworldtransport.cladosF.DivField;
import com.interworldtransport.cladosF.RealD;
import com.interworldtransport.cladosF.RealF;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**  com.interworldtransport.cladosviewer.CreateDialog
 * The Create Dialog window is supposed to show a window that would allow a user
 * to add a new Monad to the stack in the Monad Viewer.
 * 
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class CreateDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 9020826163491617137L;

	/**
	* This is a factory method for creating a new monad to add to the selected nyad's stack
	* 
	* @param pGUI	CladosCalculator
	* This parameter references the owning application. Nothing spectacular.
	* @param pMode	String
	* This string holds the representation model of the calling widget.
	* It will be a DivField static string.
	* @return 
	*  CreateDialog 
	*  This method returns a CreateDialog instance
	*  The point of this being static is to enable making the regular constructor private later.
	*/
	public static final CreateDialog createMonad(CladosCalculator pGUI, CladosField pMode) 
	{
		CreateDialog tCD = null;
		try
    	{
    		tCD= new CreateDialog(pGUI, false, pMode);
    	}
    	catch (UtilitiesException e)
    	{
    		//Do nothing.  Exception implies user doesn't get to create
    		//anything, so nothing is the correct action.
    		System.out.println("Couldn't construct create dialog.");
    	}
    	catch (BadSignatureException es)
    	{
    		//Do nothing.  Exception implies user doesn't get to create
    		//anything, so nothing is the correct action.
    		System.out.println("Couldn't construct create dialog.");
    	}
    	catch (CladosMonadException em)
    	{
    		//Do nothing.  Exception implies user doesn't get to create
    		//anything, so nothing is the correct action.
    		System.out.println("Couldn't construct create dialog.");
    	}
		
		return tCD;
	}
	/**
	* This is a factory method for creating a new nyad to add to the stack
	* 
	* @param pGUI	CladosCalculator
	* This parameter references the owning application. Nothing spectacular.
	* @param pMode	String
	* This string holds the representation model of the calling widget.
	* It will be a DivField static string.
	* @return 
	*  CreateDialog 
	*  This method returns a CreateDialog instance
	*  The point of this being static is to enable making the regular constructor private later.
	*/
	public static final CreateDialog createNyad(CladosCalculator pGUI, CladosField pMode) 
	{
		CreateDialog tCD = null;
		try
    	{
    		tCD= new CreateDialog(pGUI, true, pMode);
    	}
    	catch (UtilitiesException e)
    	{
    		//Do nothing.  Exception implies user doesn't get to create
    		//anything, so nothing is the correct action.
    		System.out.println("Couldn't construct create dialog.");
    	}
    	catch (BadSignatureException es)
    	{
    		//Do nothing.  Exception implies user doesn't get to create
    		//anything, so nothing is the correct action.
    		System.out.println("Couldn't construct create dialog.");
    	}
    	catch (CladosMonadException em)
    	{
    		//Do nothing.  Exception implies user doesn't get to create
    		//anything, so nothing is the correct action.
    		System.out.println("Couldn't construct create dialog.");
    	}
		return tCD;
	}
	
	private			CladosCalculator	_GUI;
	private	final 	Color				_monadColor = new Color(212, 212, 192);
	private	final 	Color				_nyadColor = new Color(212, 200, 212);
	private			CladosField			_repMode;
	private			JButton				closeButton;
	
	private 		AlgebraComplexD		copyAlgTargetCD;
	private 		AlgebraComplexF		copyAlgTargetCF;
	private 		AlgebraRealD		copyAlgTargetRD;
	private 		AlgebraRealF		copyAlgTargetRF;
	private 		Foot				copyFootTarget;

	private			JButton				getAlgebraButton;
	private			JButton				getFootButton;
	private			MonadPanel			mainPane;
	private			JButton				saveButton;
	private	final 	Dimension			square = new Dimension(30,30);

	/**
 	* The constructor sets up the options dialog box and displays it.
 	* It will be made into a private constructor at some point.
 	* @param mainWindow
	*  CladosCalculator
 	* This parameter references the calling/owning application
 	* @param makeNyad
 	*  boolean
 	* The same dialog is reused to create monads and nyads.
 	* We get away with this because at the top/reference level, both classes are similar.
 	* This 'create' feature essentially creates a place holder for a zero monad if a monad is created
 	* or an order-0 nyad with no monad in it to start.
 	* @param pDivMode	String
	* This string holds the representation model of the calling widget.
	* It will be a DivField static string.
 	* @throws UtilitiesException
 	* This is the general exception. Could be any miscellaneous issue. Ready the message to see.
 	* @throws BadSignatureException
 	* This exception is thrown when one of the monad panels can't accept the string signature offered.
 	* That happens when something other than '+' or '-' is used... or maybe when signature is too long.
 	* Remember that blade count is currently tracked with a short integer. {--****
 	* @throws CladosMonadException
 	* This exception gets thrown when there is a general issue constructing a monad besides the exceptions
 	* for which specific ones have been written. Read the contained message.
 	*/
	private CreateDialog(CladosCalculator mainWindow, boolean makeNyad, CladosField pDivMode)
	throws 		UtilitiesException, BadSignatureException, CladosMonadException
	{
		
		super(mainWindow, (makeNyad ? "Create Nyad Panel": "Create Monad Panel"), false);
		_GUI=mainWindow;
		_repMode=pDivMode;
		
		JPanel centerPanel=new JPanel(new BorderLayout());
		centerPanel.setBorder(BorderFactory.createTitledBorder("DivField | "+_repMode));
		centerPanel.setBackground(makeNyad ? _nyadColor : _monadColor);
		
		mainPane = new MonadPanel(_GUI);
		mainPane.makeWritable();
		
		centerPanel.add(new JScrollPane(mainPane), BorderLayout.CENTER);
		setContentPane(centerPanel);
		
		// Create Lower button panel
		JPanel dialogControlPane = new JPanel(new FlowLayout());
		dialogControlPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialogControlPane.setBackground(makeNyad ? _nyadColor : _monadColor);
		
		saveButton = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Save")));
		saveButton.setActionCommand(makeNyad ? "Save Nyad": "Save Monad");
		saveButton.setToolTipText(makeNyad ? 	"Create new nyad. Algebra/Foot or just Foot can be referenced.": 
												"Create new monad. Algebra/Foot can be referenced, but nyad Foot better match.");
		saveButton.setPreferredSize(square);
		saveButton.setBorder(BorderFactory.createEtchedBorder(0));
		saveButton.addActionListener(this);
		dialogControlPane.add(saveButton);
		
		closeButton = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Close")));
		closeButton.setActionCommand("close");
		closeButton.setToolTipText("Close the dialog. No further changes.");
		closeButton.setPreferredSize(square);
		closeButton.setBorder(BorderFactory.createEtchedBorder(0));
		closeButton.addActionListener(this);
		dialogControlPane.add(closeButton);
		
		centerPanel.add(dialogControlPane, BorderLayout.PAGE_END);
		
		// Create Upper button panel
		JPanel dialogGetPane = new JPanel(new FlowLayout());
		dialogGetPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialogGetPane.setBackground(makeNyad ? _nyadColor : _monadColor);
		
		getFootButton = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Foot")));
		getFootButton.setActionCommand("Get Foot");
		getFootButton.setToolTipText("Reference Foot in selected nyad.");
		getFootButton.setPreferredSize(square);
		getFootButton.setBorder(BorderFactory.createEtchedBorder(0));
		getFootButton.addActionListener(this);
		dialogGetPane.add(getFootButton);
		
		getAlgebraButton = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Alg")));
		getAlgebraButton.setActionCommand("Get Algebra");
		getAlgebraButton.setPreferredSize(square);
		getAlgebraButton.setToolTipText("Reference Algebra in selected monad.");
		getAlgebraButton.setBorder(BorderFactory.createEtchedBorder(0));
		getAlgebraButton.addActionListener(this);
		dialogGetPane.add(getAlgebraButton);
		
		centerPanel.add(dialogGetPane, BorderLayout.PAGE_START);
		
		// Set the size of the window
		pack();
		Point tP = _GUI.getLocation();
		Rectangle tR = _GUI.getBounds();
		// Center the window on the parent window.
		setLocation(tP.x + ((tR.width - getWidth()) / 2), tP.y + ((tR.height - getHeight()) / 2));
		
		// Display window
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	if (command.equals("close"))
    	{
    		dispose();
    	}
    	
    	if (command.equals("Get Foot"))
    	{    		
	    	int tSpot=_GUI._GeometryDisplay.getPaneFocus();
	    	if (tSpot<0) return; //No nyad chosen to get Foot
	    	
	    	//NyadPanel tSpotNPanel = _GUI._GeometryDisplay.getNyadPanel(tSpot);
	    	switch ((_GUI._GeometryDisplay.getNyadPanel(tSpot)).getRepMode())
	    	{
	    		case REALF:	copyFootTarget=_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadRF().getFootPoint();
	    								break;
	    		case REALD:	copyFootTarget=_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadRD().getFootPoint();
										break;
	    		case COMPLEXF:	copyFootTarget=_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadCF().getFootPoint();
										break;
	    		case COMPLEXD:	copyFootTarget=_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadCD().getFootPoint();
	    	}
	    	mainPane.foot.setText(copyFootTarget.getFootName());
	    	return;
    	}
    	if (command.equals("Get Algebra"))
    	{
	    	int tSpot=_GUI._GeometryDisplay.getPaneFocus();
	    	if (tSpot<0) return; //No nyad chosen to get Foot
	    		
	    	NyadPanel tSpotNPanel = _GUI._GeometryDisplay.getNyadPanel(tSpot);
	    	int tSpotMonadIndex = tSpotNPanel.getPaneFocus();
	    	if (tSpotMonadIndex<0) return; //No monad in the focus to get its algebra
	    	
	    	switch (tSpotNPanel.getRepMode())
	    	{
	    		case REALF:	copyAlgTargetRF=(tSpotNPanel.getMonadPanel(tSpotMonadIndex)).getMonadRF().getAlgebra();
						    			mainPane.aname.setText(copyAlgTargetRF.getAlgebraName());
							    		mainPane.foot.setText(copyAlgTargetRF.getFoot().getFootName());
							    		mainPane.sig.setText(copyAlgTargetRF.getGProduct().getSignature());
	    								break;
	    		case REALD:	copyAlgTargetRD=(tSpotNPanel.getMonadPanel(tSpotMonadIndex)).getMonadRD().getAlgebra();
						    			mainPane.aname.setText(copyAlgTargetRD.getAlgebraName());
							    		mainPane.foot.setText(copyAlgTargetRD.getFoot().getFootName());
							    		mainPane.sig.setText(copyAlgTargetRD.getGProduct().getSignature());
										break;
	    		case COMPLEXF:	copyAlgTargetCF=(tSpotNPanel.getMonadPanel(tSpotMonadIndex)).getMonadCF().getAlgebra();
						    			mainPane.aname.setText(copyAlgTargetCF.getAlgebraName());
							    		mainPane.foot.setText(copyAlgTargetCF.getFoot().getFootName());
							    		mainPane.sig.setText(copyAlgTargetCF.getGProduct().getSignature());
										break;
	    		case COMPLEXD:	copyAlgTargetCD=(tSpotNPanel.getMonadPanel(tSpotMonadIndex)).getMonadCD().getAlgebra();
						    			mainPane.aname.setText(copyAlgTargetCD.getAlgebraName());
							    		mainPane.foot.setText(copyAlgTargetCD.getFoot().getFootName());
							    		mainPane.sig.setText(copyAlgTargetCD.getGProduct().getSignature());
	    	}
	    	return;
    	}
    	
    	if (command.equals("Save Nyad"))
    	{	// Switch on _repMode since a new nyad is being built
    		try
	    	{
	    		switch (_repMode)
	    		{
	    			case REALF:	boolean testRF = appendNyadRF();
							    			if (!testRF)
												_GUI._StatusBar.setStatusMsg("\n\nSave new nyadRF failed at createDialog...");
	    									break;
	    			case REALD:	boolean testRD = appendNyadRD();
							    			if (!testRD)
												_GUI._StatusBar.setStatusMsg("\n\nSave new nyadRD failed at createDialog...");
											break;
	    			case COMPLEXF:	boolean testCF = appendNyadCF();
							    			if (!testCF)
												_GUI._StatusBar.setStatusMsg("\n\nSave new nyadCF failed at createDialog...");
											break;
	    			case COMPLEXD:	boolean testCD = appendNyadCD();
							    			if (!testCD)
												_GUI._StatusBar.setStatusMsg("\n\nSave new nyadCD failed at createDialog...");
				}
	    	}
			catch (UtilitiesException e)
			{
				_GUI._StatusBar.setStatusMsg("Could not create nyad from Create|Save with general utilities exception\n");
				_GUI._StatusBar.setStatusMsg(e.getSourceMessage());
			}
			catch (BadSignatureException es)
			{
				_GUI._StatusBar.setStatusMsg("Could not create nyad from Create|Save with bad signature\n");
				_GUI._StatusBar.setStatusMsg(es.getSourceMessage());
			}
			catch (CladosMonadException em)
			{
				_GUI._StatusBar.setStatusMsg("Could not create nyad from Create|Save with general monad error\n");
				_GUI._StatusBar.setStatusMsg(em.getSourceMessage());
			}
    		catch (CladosNyadException en)
			{
				_GUI._StatusBar.setStatusMsg("Could not create nyad from Create|Save with general monad error\n");
				_GUI._StatusBar.setStatusMsg(en.getSourceMessage());
			}
    		return;
    	}
    	
    	if (command.equals("Save Monad"))
    	{	//get the focus nyad to use to create this next monad

    		int tSpot=_GUI._GeometryDisplay.getPaneFocus();
    		if (tSpot<0) return; //No nyad present for appending this
    		
    		//Switch on the NyadPanel's repMode since we are appending to that Nyad
    		switch (_GUI._GeometryDisplay.getNyadPanel(tSpot).getRepMode())
    		{
    			case REALF:	boolean testRF = appendMonadRF(	_GUI._GeometryDisplay.getNyadPanel(tSpot), 
    																	_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadRF());
    									if (!testRF) _GUI._StatusBar.setStatusMsg("\n\nSave new monadRF on old nyad failed at createDialog...");
    									break;
    			case REALD:	boolean testRD = appendMonadRD(	_GUI._GeometryDisplay.getNyadPanel(tSpot), 
																		_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadRD());
    									if (!testRD) _GUI._StatusBar.setStatusMsg("\n\nSave new monadRD on old nyad failed at createDialog...");
    									break;
    			case COMPLEXF:	boolean testCF = appendMonadCF(	_GUI._GeometryDisplay.getNyadPanel(tSpot), 
																		_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadCF());
    									if (!testCF) _GUI._StatusBar.setStatusMsg("\n\nSave new monadCF on old nyad failed at createDialog...");
										break;
    			case COMPLEXD:	boolean testCD = appendMonadCD(	_GUI._GeometryDisplay.getNyadPanel(tSpot), 
    																	_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadCD());
    									if (!testCD) _GUI._StatusBar.setStatusMsg("\n\nSave new monadCF on old nyad failed at createDialog...");
    		}
    		return;
    	}
    }
	private boolean appendMonadCD(	NyadPanel tNSpotP, NyadComplexD tNSpot)
	{
		boolean test = false;
		try
		{
			MonadComplexD rep = null;
			if (copyAlgTargetCD != null)
			{
				if (copyAlgTargetCD.getFoot() != tNSpot.getFootPoint())
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra had different foot from nyad. No monad added.");
					return false;
				}
				//Foot reference match ensured. Algebra reference mismatch ensured. Moving on.
				
				// Check for algebra uniqueness within nyad. If non-unique Nyad becomes weak or frame
				if(NyadComplexD.hasAlgebra(tNSpot, copyAlgTargetCD))
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra already present in nyad. Weakening nyad.");
				}
				
				ComplexD[] tC = new ComplexD[copyAlgTargetCD.getGProduct().getBladeCount()];
				for (short m=0; m<tC.length; m++)
					tC[m] = ComplexD.copyZERO(tNSpot.getProto());
				
				rep=new MonadComplexD(	mainPane.name.getText(),
										copyAlgTargetCD,
										mainPane.frame.getText(),
										tC);
				tNSpot.appendMonad(rep);	// TODO Nyad weakness. This might fail because alg uniqueness (Nyad Strong) is enforced right now.
				tNSpotP.addMonadPanel(rep);
				test = true;
			}
			else //Testing foot doesn't matter. Nyad already has unique one.
			{
    			tNSpot.createMonad(	mainPane.name.getText(), 
    								mainPane.aname.getText(), 
    								mainPane.frame.getText(), 
    								mainPane.sig.getText());
    			rep = tNSpot.getMonadList(tNSpot.getNyadOrder()-1);
    			tNSpotP.addMonadPanel(rep);
    			test = true;
			}	
		}
		catch (UtilitiesException e)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with general utilities exception\n");
			_GUI._StatusBar.setStatusMsg(e.getSourceMessage());
		}
		catch (BadSignatureException es)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with bad signature\n");
			_GUI._StatusBar.setStatusMsg(es.getSourceMessage());
		}
		catch (CladosMonadException em)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with general error\n");
			_GUI._StatusBar.setStatusMsg(em.getSourceMessage());
		}
		catch (CladosNyadException en)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not append monad | "+en.getMessage());	
			_GUI._StatusBar.setStatusMsg(en.getSourceMessage());
		}
		return test;
	}
	private boolean appendMonadCF(	NyadPanel tNSpotP, NyadComplexF tNSpot)
	{
		boolean test = false;
		try
		{
			MonadComplexF rep = null;
			if (copyAlgTargetCF != null)
			{
				if (copyAlgTargetCF.getFoot() != tNSpot.getFootPoint())
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra had different foot from nyad. No monad added.");
					return false;
				}
				//Foot reference match ensured. Algebra reference mismatch ensured. Moving on.
				
				// Check for algebra uniqueness within nyad. If non-unique Nyad becomes weak or frame
				if(NyadComplexF.hasAlgebra(tNSpot, copyAlgTargetCF))
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra already present in nyad. Weakening nyad.");
				}
				
				ComplexF[] tC = new ComplexF[copyAlgTargetCF.getGProduct().getBladeCount()];
				for (short m=0; m<tC.length; m++)
					tC[m] = ComplexF.copyZERO(tNSpot.getProto());
				
				rep=new MonadComplexF(	mainPane.name.getText(),
										copyAlgTargetCF,
										mainPane.frame.getText(),
										tC);
				tNSpot.appendMonad(rep);	// TODO Nyad weakness. This might fail because alg uniqueness (Nyad Strong) is enforced right now.
				tNSpotP.addMonadPanel(rep);
				test = true;
			}
			else //Testing foot doesn't matter. Nyad already has unique one.
			{
    			tNSpot.createMonad(	mainPane.name.getText(), 
    								mainPane.aname.getText(), 
    								mainPane.frame.getText(), 
    								mainPane.sig.getText());
    			rep = tNSpot.getMonadList(tNSpot.getNyadOrder()-1);
    			tNSpotP.addMonadPanel(rep);
    			test = true;
			}	
		}
		catch (UtilitiesException e)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with general utilities exception\n");
			_GUI._StatusBar.setStatusMsg(e.getSourceMessage());
		}
		catch (BadSignatureException es)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with bad signature\n");
			_GUI._StatusBar.setStatusMsg(es.getSourceMessage());
		}
		catch (CladosMonadException em)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with general error\n");
			_GUI._StatusBar.setStatusMsg(em.getSourceMessage());
		}
		catch (CladosNyadException en)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not append monad | "+en.getMessage());	
			_GUI._StatusBar.setStatusMsg(en.getSourceMessage());
		}
		return test;
	}
	private boolean appendMonadRD(	NyadPanel tNSpotP, NyadRealD tNSpot)
	{
		boolean test = false;
		try
		{
			MonadRealD rep = null;
			if (copyAlgTargetRD != null)
			{
				if (copyAlgTargetRD.getFoot() != tNSpot.getFootPoint())
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra had different foot from nyad. No monad added.");
					return false;
				}
				//Foot reference match ensured. Algebra reference mismatch ensured. Moving on.
				
				// Check for algebra uniqueness within nyad. If non-unique Nyad becomes weak or frame
				if(NyadRealD.hasAlgebra(tNSpot, copyAlgTargetRD))
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra already present in nyad. Weakening nyad.");
				}
				
				RealD[] tC = new RealD[copyAlgTargetRF.getGProduct().getBladeCount()];
				for (short m=0; m<tC.length; m++)
					tC[m]=RealD.copyZERO(tNSpot.getProto());
				
				rep=new MonadRealD(		mainPane.name.getText(),
										copyAlgTargetRD,
										mainPane.frame.getText(),
										tC);
				tNSpot.appendMonad(rep);	// TODO Nyad weakness. This might fail because alg uniqueness (Nyad Strong) is enforced right now.
				tNSpotP.addMonadPanel(rep);
				test = true;
			}
			else //Testing foot doesn't matter. Nyad already has unique one.
			{
    			tNSpot.createMonad(	mainPane.name.getText(), 
    								mainPane.aname.getText(), 
    								mainPane.frame.getText(), 
    								mainPane.sig.getText());
    			rep = tNSpot.getMonadList(tNSpot.getNyadOrder()-1);
    			tNSpotP.addMonadPanel(rep);
    			test = true;
			}	
		}
		catch (UtilitiesException e)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with general utilities exception\n");
			_GUI._StatusBar.setStatusMsg(e.getSourceMessage());
		}
		catch (BadSignatureException es)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with bad signature\n");
			_GUI._StatusBar.setStatusMsg(es.getSourceMessage());
		}
		catch (CladosMonadException em)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with general error\n");
			_GUI._StatusBar.setStatusMsg(em.getSourceMessage());
		}
		catch (CladosNyadException en)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not append monad | "+en.getMessage());	
			_GUI._StatusBar.setStatusMsg(en.getSourceMessage());
		}
		return test;
	}
	
	private boolean appendMonadRF(	NyadPanel tNSpotP, NyadRealF tNSpot)
	{
		boolean test = false;
		try
		{
			MonadRealF rep = null;
			if (copyAlgTargetRF != null)
			{
				if (copyAlgTargetRF.getFoot() != tNSpot.getFootPoint())
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra had different foot from nyad. No monad added.");
					return false;
				}
				//Foot reference match ensured. Algebra reference mismatch ensured. Moving on.
				
				// Check for algebra uniqueness within nyad. If non-unique Nyad becomes weak or frame
				if(NyadRealF.hasAlgebra(tNSpot, copyAlgTargetRF))
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra already present in nyad. Weakening nyad.");
				}
				
				RealF[] tC = new RealF[copyAlgTargetRF.getGProduct().getBladeCount()];
				for (short m=0; m<tC.length; m++)
					tC[m]=RealF.copyZERO(tNSpot.getProto());
				
				rep=new MonadRealF(		mainPane.name.getText(),
										copyAlgTargetRF,
										mainPane.frame.getText(),
										tC);
				tNSpot.appendMonad(rep);	// TODO Nyad weakness. This might fail because alg uniqueness (Nyad Strong) is enforced right now.
				tNSpotP.addMonadPanel(rep);
				test = true;
			}
			else //Testing foot doesn't matter. Nyad already has unique one.
			{
    			tNSpot.createMonad(	mainPane.name.getText(), 
    								mainPane.aname.getText(), 
    								mainPane.frame.getText(), 
    								mainPane.sig.getText());
    			rep = tNSpot.getMonadList(tNSpot.getNyadOrder()-1);
    			tNSpotP.addMonadPanel(rep);
    			test = true;
			}	
		}
		catch (UtilitiesException e)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with general utilities exception\n");
			_GUI._StatusBar.setStatusMsg(e.getSourceMessage());
		}
		catch (BadSignatureException es)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with bad signature\n");
			_GUI._StatusBar.setStatusMsg(es.getSourceMessage());
		}
		catch (CladosMonadException em)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with general error\n");
			_GUI._StatusBar.setStatusMsg(em.getSourceMessage());
		}
		catch (CladosNyadException en)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not append monad | "+en.getMessage());
			_GUI._StatusBar.setStatusMsg(en.getSourceMessage());
		}
		return test;
	}
	private boolean appendNyadCD() throws UtilitiesException, BadSignatureException, CladosMonadException, CladosNyadException
	{
		boolean test=false;
		if (copyAlgTargetCD != null) // Algebra's foot dominates separately chosen Foot
		{
			ComplexD tZero=new ComplexD(copyAlgTargetCF.getFoot().getNumberType(), 0.0D, 0.0D);
			ComplexD[] tC = new ComplexD[copyAlgTargetCF.getGProduct().getBladeCount()];
			for (short m=0; m<tC.length; m++)	tC[m]=ComplexD.copyZERO(tZero);
			MonadComplexD rep=new MonadComplexD(mainPane.name.getText(),
												copyAlgTargetCD,
												mainPane.frame.getText(),
												tC);
			NyadComplexD rep2=new NyadComplexD("New", rep);
			test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		else if (copyFootTarget != null)
		{	
			MonadComplexD rep=new MonadComplexD(mainPane.name.getText(),
												mainPane.aname.getText(),
												mainPane.frame.getText(),
												copyFootTarget,
												mainPane.sig.getText(),
												new ComplexD(copyFootTarget.getNumberType()));
			NyadComplexD rep2=new NyadComplexD("New", rep);
    		_GUI._GeometryDisplay.addNyad(rep2);
    		test = true;
		}
		else
		{
			MonadComplexD rep=new MonadComplexD(mainPane.name.getText(),
    											mainPane.aname.getText(),
    											mainPane.frame.getText(),
    											mainPane.foot.getText(),
    											mainPane.sig.getText(),
    											ComplexD.newZERO(mainPane.aname.getText()));
			NyadComplexD rep2=new NyadComplexD("New", rep);
    		test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		return test;
	}
	private boolean appendNyadCF() throws UtilitiesException, BadSignatureException, CladosMonadException, CladosNyadException
	{
		boolean test=false;
		if (copyAlgTargetCF != null) // Algebra's foot dominates separately chosen Foot
		{
			ComplexF tZero=new ComplexF(copyAlgTargetCF.getFoot().getNumberType(), 0.0f, 0.0f);
			ComplexF[] tC = new ComplexF[copyAlgTargetCF.getGProduct().getBladeCount()];
			for (short m=0; m<tC.length; m++)	tC[m]=ComplexF.copyZERO(tZero);
			MonadComplexF rep=new MonadComplexF(mainPane.name.getText(),
												copyAlgTargetCF,
												mainPane.frame.getText(),
												tC);
			NyadComplexF rep2=new NyadComplexF("New", rep);
			test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		else if (copyFootTarget != null)
		{	
			MonadComplexF rep=new MonadComplexF(mainPane.name.getText(),
												mainPane.aname.getText(),
												mainPane.frame.getText(),
												copyFootTarget,
												mainPane.sig.getText(),
												new ComplexF(copyFootTarget.getNumberType()));
			NyadComplexF rep2=new NyadComplexF("New", rep);
    		_GUI._GeometryDisplay.addNyad(rep2);
    		test = true;
		}
		else
		{
			MonadComplexF rep=new MonadComplexF(mainPane.name.getText(),
    											mainPane.aname.getText(),
    											mainPane.frame.getText(),
    											mainPane.foot.getText(),
    											mainPane.sig.getText(),
    											ComplexF.newZERO(mainPane.aname.getText()));
    		NyadComplexF rep2=new NyadComplexF("New", rep);
    		test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		return test;
	}
	private boolean appendNyadRD() throws UtilitiesException, BadSignatureException, CladosMonadException, CladosNyadException
	{
		boolean test=false;
		if (copyAlgTargetRD != null) // Algebra's foot dominates separately chosen Foot
		{
			RealD tZero=new RealD(copyAlgTargetRD.getFoot().getNumberType(), 0.0D);
			RealD[] tC = new RealD[copyAlgTargetRD.getGProduct().getBladeCount()];
			for (short m=0; m<tC.length; m++)	tC[m]=RealD.copyZERO(tZero);
			MonadRealD rep=new MonadRealD(	mainPane.name.getText(),
											copyAlgTargetRD,
											mainPane.frame.getText(),
											tC);
			NyadRealD rep2=new NyadRealD("New", rep);
			test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		else if (copyFootTarget != null)
		{	
			MonadRealD rep=new MonadRealD(	mainPane.name.getText(),
											mainPane.aname.getText(),
											mainPane.frame.getText(),
											copyFootTarget,
											mainPane.sig.getText(),
											new RealD(copyFootTarget.getNumberType()));
			NyadRealD rep2=new NyadRealD("New", rep);
    		_GUI._GeometryDisplay.addNyad(rep2);
    		test = true;
		}
		else
		{
    		MonadRealD rep=new MonadRealD(	mainPane.name.getText(),
    										mainPane.aname.getText(),
    										mainPane.frame.getText(),
    										mainPane.foot.getText(),
    										mainPane.sig.getText(),
    										RealD.newZERO(mainPane.aname.getText()));
    		NyadRealD rep2=new NyadRealD("New", rep);
    		test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		return test;
	}
	
    private boolean appendNyadRF() throws UtilitiesException, BadSignatureException, CladosMonadException, CladosNyadException
	{
		boolean test=false;
		if (copyAlgTargetRF != null) // Algebra's foot dominates separately chosen Foot
		{
			RealF tZero=new RealF(copyAlgTargetRF.getFoot().getNumberType(), 0.0f);
			RealF[] tC = new RealF[copyAlgTargetRF.getGProduct().getBladeCount()];
			for (short m=0; m<tC.length; m++)	tC[m]=RealF.copyZERO(tZero);
			MonadRealF rep=new MonadRealF(	mainPane.name.getText(),
											copyAlgTargetRF,
											mainPane.frame.getText(),
											tC);
			NyadRealF rep2=new NyadRealF("New", rep);
			test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		else if (copyFootTarget != null)
		{	
			MonadRealF rep=new MonadRealF(	mainPane.name.getText(),
											mainPane.aname.getText(),
											mainPane.frame.getText(),
											copyFootTarget,
											mainPane.sig.getText(),
											new RealF(copyFootTarget.getNumberType()));
			NyadRealF rep2=new NyadRealF("New", rep);
    		_GUI._GeometryDisplay.addNyad(rep2);
    		test = true;
		}
		else
		{
    		MonadRealF rep=new MonadRealF(	mainPane.name.getText(),
    										mainPane.aname.getText(),
    										mainPane.frame.getText(),
    										mainPane.foot.getText(),
    										mainPane.sig.getText(),
    										RealF.newZERO(mainPane.aname.getText()));
    		NyadRealF rep2=new NyadRealF("New", rep);
    		test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		return test;
	}
}