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
	private static final Font				_ITALICFONT = new Font(Font.SERIF, Font.ITALIC, 10);
	private	final static Color				_monadColor = new Color(212, 212, 192);
	private	final static Color				_nyadColor 	= new Color(212, 200, 212);
	private static final Font				_PLAINFONT 	= new Font(Font.SERIF, Font.PLAIN, 10);
	private	final static Dimension			square = new Dimension(30,30);
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
	private			CladosField			_repMode;
	private			JButton				btnClose;
	private			JButton				btnGetAlgebra;
	private			JButton				btnGetFoot;
	private			JButton				btnSave;
	private 		AlgebraComplexD		copiedAlgCD;
	private 		AlgebraComplexF		copiedAlgCF;
	private 		AlgebraRealD		copiedAlgRD;
	private 		AlgebraRealF		copiedAlgRF;
	private 		Foot				copiedFoot;
	private			MonadPanel			monadShort;

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
		if (pDivMode == null) throw new UtilitiesException("CladosField undefined at dialog creation");
		_repMode=pDivMode;
		
		// Create the Dialog's main stage
		JPanel primaryStage=new JPanel(new BorderLayout());
		primaryStage.setBorder(BorderFactory.createTitledBorder("DivField | "+_repMode));
		primaryStage.setBackground(makeNyad ? _nyadColor : _monadColor);
		setContentPane(primaryStage);
		
		// Create Upper button panel
		JPanel dialogGets = new JPanel(new FlowLayout());
		dialogGets.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialogGets.setBackground(makeNyad ? _nyadColor : _monadColor);
				
		btnGetFoot = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Foot")));
		btnGetFoot.setActionCommand("getfoot");
		btnGetFoot.setToolTipText("Get Referenced Foot");
		btnGetFoot.setPreferredSize(square);
		btnGetFoot.setBorder(BorderFactory.createEtchedBorder(0));
		btnGetFoot.addActionListener(this);
		dialogGets.add(btnGetFoot);
				
		btnGetAlgebra = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Alg")));
		btnGetAlgebra.setActionCommand("getalg");
		btnGetAlgebra.setPreferredSize(square);
		btnGetAlgebra.setToolTipText("Reference Algebra in selected monad.");
		btnGetAlgebra.setBorder(BorderFactory.createEtchedBorder(0));
		btnGetAlgebra.addActionListener(this);
		dialogGets.add(btnGetAlgebra);
		primaryStage.add(dialogGets, BorderLayout.PAGE_START); // and put it on stage
		
		// Create monad for center panel
		monadShort = new MonadPanel(_GUI);
		monadShort.makeWritable();
		primaryStage.add(new JScrollPane(monadShort), BorderLayout.CENTER); // and put it on stage
		
		// Create Lower button panel
		JPanel dialogControls = new JPanel(new FlowLayout());
		dialogControls.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialogControls.setBackground(makeNyad ? _nyadColor : _monadColor);
		
		btnSave = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Save")));
		btnSave.setActionCommand(makeNyad ? "Save Nyad": "Save Monad");
		btnSave.setToolTipText(makeNyad ? 	"Create new nyad. Algebra/Foot or just Foot can be referenced.": 
												"Create new monad. Algebra/Foot can be referenced, but nyad Foot better match.");
		btnSave.setPreferredSize(square);
		btnSave.setBorder(BorderFactory.createEtchedBorder(0));
		btnSave.addActionListener(this);
		dialogControls.add(btnSave);
		
		btnClose = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Close")));
		btnClose.setActionCommand("close");
		btnClose.setToolTipText("Close the dialog. No further changes.");
		btnClose.setPreferredSize(square);
		btnClose.setBorder(BorderFactory.createEtchedBorder(0));
		btnClose.addActionListener(this);
		dialogControls.add(btnClose);
		primaryStage.add(dialogControls, BorderLayout.PAGE_END);	// and put it on stage
	
		// Center the window on the parent window.
		pack();
		Point tP = _GUI.getLocation();
		Rectangle tR = _GUI.getBounds();
		setLocation(tP.x + ((tR.width - getWidth()) / 2), tP.y + ((tR.height - getHeight()) / 2));
		setVisible(true);	// Display window
	}
	@Override
	public void actionPerformed(ActionEvent event)
    {
		int tSpot=0;
		switch (event.getActionCommand())
    	{
    		case "close": 		dispose();
    							break;
    		case ".getfoot.":	btnGetFoot.setActionCommand("getfoot");
        						btnGetFoot.setToolTipText("Get Referenced Foot");
        						copiedFoot=null;
        						monadShort.foot.setEditable(true);
        						monadShort.foot.setFont(_ITALICFONT);
        						
        						copiedAlgRF=null;
        						copiedAlgRD=null;
        						copiedAlgCF=null;
        						copiedAlgCD=null;
        						monadShort.aname.setFont(_ITALICFONT);
        						monadShort.aname.setEditable(true);
        						monadShort.cardname.setFont(_ITALICFONT);
        						monadShort.cardname.setEditable(true);
    							break;
    		case "getfoot":		if (getNyadPaneFocus()<0) return; //No nyad chosen to get Foot
					        	btnGetFoot.setActionCommand(".getfoot.");
					        	btnGetFoot.setToolTipText("Release Referenced Foot");
					        	switch ((_GUI._GeometryDisplay.getNyadPanel(tSpot)).getRepMode())
					        	{
					        		case REALF:		copiedFoot=getNyadPanelFocus().getNyadRF().getFootPoint();
					        						break;
					        		case REALD:		copiedFoot=getNyadPanelFocus().getNyadRD().getFootPoint();
					    							break;
					        		case COMPLEXF:	copiedFoot=getNyadPanelFocus().getNyadCF().getFootPoint();
					    							break;
					        		case COMPLEXD:	copiedFoot=getNyadPanelFocus().getNyadCD().getFootPoint();
					        	}
					        	monadShort.foot.setText(copiedFoot.getFootName());
					        	monadShort.foot.setFont(_PLAINFONT);
					        	monadShort.foot.setEditable(false);	
					        	
					        	copiedAlgRF=null;
        						copiedAlgRD=null;
        						copiedAlgCF=null;
        						copiedAlgCD=null;
					        	monadShort.aname.setFont(_ITALICFONT);
					        	monadShort.aname.setEditable(true);					        	
					        	monadShort.cardname.setEditable(true);
					        	monadShort.sig.setEditable(true);
    							break;
    		case ".getalg.":	btnGetAlgebra.setActionCommand("getalg");
        						btnGetAlgebra.setToolTipText("Get Referenced Algebra");
        						copiedAlgRF=null;
        						copiedAlgRD=null;
        						copiedAlgCF=null;
        						copiedAlgCD=null;
        						monadShort.aname.setFont(_ITALICFONT);
        						monadShort.aname.setEditable(true);
        						monadShort.cardname.setFont(_ITALICFONT);
        						monadShort.cardname.setEditable(true);
        						monadShort.sig.setFont(_ITALICFONT);
        						monadShort.sig.setEditable(true);
        						monadShort.foot.setFont(_ITALICFONT);
    							monadShort.foot.setEditable(true);
        						break;
    		case "getalg":		if (getNyadPaneFocus()<0) return; //No nyad chosen to get Foot
					        	btnGetAlgebra.setActionCommand(".getalg.");
					        	btnGetAlgebra.setToolTipText("Release Referenced Algebra");
					        	NyadPanel tSpotNPanel = getNyadPanelFocus();
					        	int tSpotMonadIndex = getMonadPaneFocus();
					        	if (getMonadPaneFocus()<0) return; //No monad in the focus to get its algebra
					        	
					        	switch (tSpotNPanel.getRepMode())
					        	{
					        		case REALF:		copiedAlgRF=getMonadPanelFocus().getMonadRF().getAlgebra();
					        						monadShort.cardname.setText(copiedAlgRF.getFoot().getCardinal().getType());
					        						monadShort.aname.setText(copiedAlgRF.getAlgebraName());
					    							monadShort.foot.setText(copiedAlgRF.getFoot().getFootName());
					    							monadShort.sig.setText(copiedAlgRF.getGProduct().getSignature());
					    							break;
					        		case REALD:		copiedAlgRD=getMonadPanelFocus().getMonadRD().getAlgebra();
					        						monadShort.cardname.setText(copiedAlgRD.getFoot().getCardinal().getType());
					    						    monadShort.aname.setText(copiedAlgRD.getAlgebraName());
					    							monadShort.foot.setText(copiedAlgRD.getFoot().getFootName());
					    							monadShort.sig.setText(copiedAlgRD.getGProduct().getSignature());
					    							break;
					        		case COMPLEXF:	copiedAlgCF=getMonadPanelFocus().getMonadCF().getAlgebra();
					        						monadShort.cardname.setText(copiedAlgCF.getFoot().getCardinal().getType());
					    					    	monadShort.aname.setText(copiedAlgCF.getAlgebraName());
					    						    monadShort.foot.setText(copiedAlgCF.getFoot().getFootName());
					    						    monadShort.sig.setText(copiedAlgCF.getGProduct().getSignature());
					    							break;
					        		case COMPLEXD:	copiedAlgCD=getMonadPanelFocus().getMonadCD().getAlgebra();
					        						monadShort.cardname.setText(copiedAlgCD.getFoot().getCardinal().getType());
					    					    	monadShort.aname.setText(copiedAlgCD.getAlgebraName());
					    						    monadShort.foot.setText(copiedAlgCD.getFoot().getFootName());
					    						    monadShort.sig.setText(copiedAlgCD.getGProduct().getSignature());
					        	}
					        	monadShort.cardname.setFont(_PLAINFONT);
					        	monadShort.cardname.setEditable(false);
    							monadShort.aname.setFont(_PLAINFONT);
    							monadShort.aname.setEditable(false);
    							monadShort.foot.setFont(_PLAINFONT);
    							monadShort.foot.setEditable(false);
    							monadShort.sig.setFont(_PLAINFONT);
    							monadShort.sig.setEditable(false);
    							break;
    		case "Save Nyad":	try
						    	{
						    		switch (_repMode)
						    		{
						    			case REALF:		if (!appendNyadRF()) _GUI._StatusBar.setStatusMsg("\n\nSave new nyadRF failed at createDialog...");
							    						break;
						    			case REALD:		if (!appendNyadRD()) _GUI._StatusBar.setStatusMsg("\n\nSave new nyadRD failed at createDialog...");
														break;
						    			case COMPLEXF:	if (!appendNyadCF()) _GUI._StatusBar.setStatusMsg("\n\nSave new nyadCF failed at createDialog...");
														break;
						    			case COMPLEXD:	if (!appendNyadCD()) _GUI._StatusBar.setStatusMsg("\n\nSave new nyadCD failed at createDialog...");
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
					    		catch (GeneratorRangeException e) 
					    		{
					    			_GUI._StatusBar.setStatusMsg("Could not create a nyad due to unsupported signature size.\n");
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
    							break;
    		case "Save Monad":	tSpot=getNyadPaneFocus(); //get the focus nyad to use to create this next monad
					        	if (tSpot<0) return; //No nyad present for appending this
					        	switch (_GUI._GeometryDisplay.getNyadPanel(tSpot).getRepMode())
					        	{
					        		case REALF:		boolean testRF = appendMonadRF(	_GUI._GeometryDisplay.getNyadPanel(tSpot), 
					        														_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadRF());
					    	    					if (!testRF) _GUI._StatusBar.setStatusMsg("\n\nSave new monadRF on old nyad failed at createDialog...");
					    	    					break;
					        		case REALD:		boolean testRD = appendMonadRD(	_GUI._GeometryDisplay.getNyadPanel(tSpot), 
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
    	}
    }
	private boolean appendMonadCD(	NyadPanel tNSpotP, NyadComplexD tNSpot)
	{
		boolean test = false;
		try
		{
			MonadComplexD rep = null;
			if (copiedAlgCD != null)
			{
				if (copiedAlgCD.getFoot() != tNSpot.getFootPoint())
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra had different foot from nyad. No monad added.");
					return false;
				}	//Foot reference match ensured. Algebra reference mismatch ensured. Moving on.
				
				// Check for algebra uniqueness within nyad. If non-unique Nyad becomes weak or frame
				if(NyadComplexD.hasAlgebra(tNSpot, copiedAlgCD))
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra already present in nyad. Weakening nyad.");
				
				ComplexD[] tC = new ComplexD[copiedAlgCD.getGProduct().getBladeCount()];
				for (short m=0; m<tC.length; m++)
					tC[m] = ComplexD.copyZERO(tNSpot.getProto());
				
				rep=new MonadComplexD(	monadShort.name.getText(),
										copiedAlgCD,
										monadShort.frame.getText(),
										tC);
				tNSpot.appendMonad(rep);	// TODO Nyad weakness. This might fail because alg uniqueness (Nyad Strong) is enforced right now.
				tNSpotP.addMonadPanel(rep);
				test = true;
			}
			else //Testing foot doesn't matter. Nyad already has unique one.
			{
    			tNSpot.createMonad(	monadShort.name.getText(), 
    								monadShort.aname.getText(), 
    								monadShort.frame.getText(), 
    								monadShort.sig.getText());
    			rep = tNSpot.getMonadList(tNSpot.getNyadOrder()-1);
    			tNSpotP.addMonadPanel(rep);
    			test = true;
			}	
		}
		catch (BadSignatureException es)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with bad signature\n");
			_GUI._StatusBar.setStatusMsg(es.getSourceMessage());
		}
		catch (GeneratorRangeException e) 
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("... cannot construct a monad due to unsupported signature size.\n");
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
			if (copiedAlgCF != null)
			{
				if (copiedAlgCF.getFoot() != tNSpot.getFootPoint())
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra had different foot from nyad. No monad added.");
					return false;
				}	//Foot reference match ensured. Algebra reference mismatch ensured. Moving on.
				
				// Check for algebra uniqueness within nyad. If non-unique Nyad becomes weak or frame
				if(NyadComplexF.hasAlgebra(tNSpot, copiedAlgCF))
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra already present in nyad. Weakening nyad.");
				
				ComplexF[] tC = new ComplexF[copiedAlgCF.getGProduct().getBladeCount()];
				for (short m=0; m<tC.length; m++)
					tC[m] = ComplexF.copyZERO(tNSpot.getProto());
				
				rep=new MonadComplexF(	monadShort.name.getText(),
										copiedAlgCF,
										monadShort.frame.getText(),
										tC);
				tNSpot.appendMonad(rep);	// TODO Nyad weakness. This might fail because alg uniqueness (Nyad Strong) is enforced right now.
				tNSpotP.addMonadPanel(rep);
				test = true;
			}
			else //Testing foot doesn't matter. Nyad already has unique one.
			{
    			tNSpot.createMonad(	monadShort.name.getText(), 
    								monadShort.aname.getText(), 
    								monadShort.frame.getText(), 
    								monadShort.sig.getText());
    			rep = tNSpot.getMonadList(tNSpot.getNyadOrder()-1);
    			tNSpotP.addMonadPanel(rep);
    			test = true;
			}	
		}
		catch (BadSignatureException es)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with bad signature\n");
			_GUI._StatusBar.setStatusMsg(es.getSourceMessage());
		}
		catch (GeneratorRangeException e) 
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("... cannot construct a monad due to unsupported signature size.\n");
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
			if (copiedAlgRD != null)
			{
				if (copiedAlgRD.getFoot() != tNSpot.getFootPoint())
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra had different foot from nyad. No monad added.");
					return false;
				}	//Foot reference match ensured. Algebra reference mismatch ensured. Moving on.
				
				// Check for algebra uniqueness within nyad. If non-unique Nyad becomes weak or frame
				if(NyadRealD.hasAlgebra(tNSpot, copiedAlgRD))
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra already present in nyad. Weakening nyad.");
				
				RealD[] tC = new RealD[copiedAlgRD.getGProduct().getBladeCount()];
				for (short m=0; m<tC.length; m++)
					tC[m]=RealD.copyZERO(tNSpot.getProto());
				
				rep=new MonadRealD(		monadShort.name.getText(),
										copiedAlgRD,
										monadShort.frame.getText(),
										tC);
				tNSpot.appendMonad(rep);	// TODO Nyad weakness. This might fail because alg uniqueness (Nyad Strong) is enforced right now.
				tNSpotP.addMonadPanel(rep);
				test = true;
			}
			else //Testing foot doesn't matter. Nyad already has unique one.
			{
    			tNSpot.createMonad(	monadShort.name.getText(), 
    								monadShort.aname.getText(), 
    								monadShort.frame.getText(), 
    								monadShort.sig.getText());
    			rep = tNSpot.getMonadList(tNSpot.getNyadOrder()-1);
    			tNSpotP.addMonadPanel(rep);
    			test = true;
			}	
		}
		catch (BadSignatureException es)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with bad signature\n");
			_GUI._StatusBar.setStatusMsg(es.getSourceMessage());
		}
		catch (GeneratorRangeException e) 
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("... cannot construct a monad due to unsupported signature size.\n");
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
			if (copiedAlgRF != null)
			{
				if (copiedAlgRF.getFoot() != tNSpot.getFootPoint())
				{
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra had different foot from nyad. No monad added.");
					return false;
				}	//Foot reference match ensured. Algebra reference mismatch ensured. Moving on.
				
				// Check for algebra uniqueness within nyad. If non-unique Nyad becomes weak or frame
				if(NyadRealF.hasAlgebra(tNSpot, copiedAlgRF))
					_GUI._StatusBar.setStatusMsg("\t\tchosen algebra already present in nyad. Weakening nyad.");
				
				RealF[] tC = new RealF[copiedAlgRF.getGProduct().getBladeCount()];
				for (short m=0; m<tC.length; m++)
					tC[m]=RealF.copyZERO(tNSpot.getProto());
				
				rep=new MonadRealF(		monadShort.name.getText(),
										copiedAlgRF,
										monadShort.frame.getText(),
										tC);
				tNSpot.appendMonad(rep);	// TODO Nyad weakness. This might fail because alg uniqueness (Nyad Strong) is enforced right now.
				tNSpotP.addMonadPanel(rep);
				test = true;
			}
			else //Testing foot doesn't matter. Nyad already has unique one.
			{
    			tNSpot.createMonad(	monadShort.name.getText(), 
    								monadShort.aname.getText(), 
    								monadShort.frame.getText(), 
    								monadShort.sig.getText());
    			rep = tNSpot.getMonadList(tNSpot.getNyadOrder()-1);
    			tNSpotP.addMonadPanel(rep);
    			test = true;
			}	
		}
		catch (BadSignatureException es)
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("Could not create monad copy from Create|Save with bad signature\n");
			_GUI._StatusBar.setStatusMsg(es.getSourceMessage());
		}
		catch (GeneratorRangeException e) 
		{
			test = false;
			_GUI._StatusBar.setStatusMsg("... cannot construct a monad due to unsupported signature size.\n");
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
	private boolean appendNyadCD() throws UtilitiesException, BadSignatureException, GeneratorRangeException, CladosMonadException, CladosNyadException
	{
		boolean test=false;
		if (copiedAlgCD != null) // Algebra's foot dominates separately chosen Foot
		{
			ComplexD[] tC = new ComplexD[copiedAlgCF.getGProduct().getBladeCount()];
			for (short m=0; m<tC.length; m++)	tC[m]=(ComplexD) CladosField.COMPLEXD.createZERO(copiedAlgCD.getFoot().getCardinal());
			MonadComplexD rep=new MonadComplexD(monadShort.name.getText(),
												copiedAlgCD,
												monadShort.frame.getText(),
												tC);
			NyadComplexD rep2=new NyadComplexD("New", rep);
			test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		else if (copiedFoot != null)
		{	
			MonadComplexD rep=new MonadComplexD(monadShort.name.getText(),
												monadShort.aname.getText(),
												monadShort.frame.getText(),
												copiedFoot,
												monadShort.sig.getText(),
												(ComplexD) CladosField.COMPLEXD.createZERO(monadShort.cardname.getText()));
			NyadComplexD rep2=new NyadComplexD("New", rep);
    		_GUI._GeometryDisplay.addNyad(rep2);
    		test = true;
		}
		else
		{
			MonadComplexD rep=new MonadComplexD(monadShort.name.getText(),
    											monadShort.aname.getText(),
    											monadShort.frame.getText(),
    											monadShort.foot.getText(),
    											monadShort.sig.getText(),
    											(ComplexD) CladosField.COMPLEXD.createZERO(monadShort.cardname.getText()));
			NyadComplexD rep2=new NyadComplexD("New", rep);
    		test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		return test;
	}
	private boolean appendNyadCF() throws UtilitiesException, BadSignatureException, GeneratorRangeException, CladosMonadException, CladosNyadException
	{
		boolean test=false;
		if (copiedAlgCF != null) // Algebra's foot dominates separately chosen Foot
		{
			ComplexF[] tC = new ComplexF[copiedAlgCF.getGProduct().getBladeCount()];
			for (short m=0; m<tC.length; m++)	tC[m]=(ComplexF) CladosField.COMPLEXF.createZERO(copiedAlgCF.getFoot().getCardinal());
			MonadComplexF rep=new MonadComplexF(monadShort.name.getText(),
												copiedAlgCF,
												monadShort.frame.getText(),
												tC);
			NyadComplexF rep2=new NyadComplexF("New", rep);
			test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		else if (copiedFoot != null)
		{	
			MonadComplexF rep=new MonadComplexF(monadShort.name.getText(),
												monadShort.aname.getText(),
												monadShort.frame.getText(),
												copiedFoot,
												monadShort.sig.getText(),
												(ComplexF) CladosField.COMPLEXF.createZERO(monadShort.cardname.getText()));
			NyadComplexF rep2=new NyadComplexF("New", rep);
    		_GUI._GeometryDisplay.addNyad(rep2);
    		test = true;
		}
		else
		{
			MonadComplexF rep=new MonadComplexF(monadShort.name.getText(),
    											monadShort.aname.getText(),
    											monadShort.frame.getText(),
    											monadShort.foot.getText(),
    											monadShort.sig.getText(),
    											(ComplexF) CladosField.COMPLEXF.createZERO(monadShort.cardname.getText()));
    		NyadComplexF rep2=new NyadComplexF("New", rep);
    		test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		return test;
	}
	private boolean appendNyadRD() throws UtilitiesException, BadSignatureException, GeneratorRangeException, CladosMonadException, CladosNyadException
	{
		boolean test=false;
		if (copiedAlgRD != null) // Algebra's foot dominates separately chosen Foot
		{
			RealD[] tC = new RealD[copiedAlgRD.getGProduct().getBladeCount()];
			for (short m=0; m<tC.length; m++)	tC[m]=(RealD) CladosField.REALD.createZERO(copiedAlgRD.getFoot().getCardinal());
			MonadRealD rep=new MonadRealD(	monadShort.name.getText(),
											copiedAlgRD,
											monadShort.frame.getText(),
											tC);
			NyadRealD rep2=new NyadRealD("New", rep);
			test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		else if (copiedFoot != null)
		{	
			MonadRealD rep=new MonadRealD(	monadShort.name.getText(),
											monadShort.aname.getText(),
											monadShort.frame.getText(),
											copiedFoot,
											monadShort.sig.getText(),
											(RealD) CladosField.REALD.createZERO(monadShort.cardname.getText()));
			NyadRealD rep2=new NyadRealD("New", rep);
    		_GUI._GeometryDisplay.addNyad(rep2);
    		test = true;
		}
		else
		{
    		MonadRealD rep=new MonadRealD(	monadShort.name.getText(),
    										monadShort.aname.getText(),
    										monadShort.frame.getText(),
    										monadShort.foot.getText(),
    										monadShort.sig.getText(),
    										(RealD) CladosField.REALD.createZERO(monadShort.cardname.getText()));
    		NyadRealD rep2=new NyadRealD("New", rep);
    		test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		return test;
	}
	
	private boolean appendNyadRF() throws UtilitiesException, BadSignatureException, GeneratorRangeException, CladosMonadException, CladosNyadException
	{
		boolean test=false;
		if (copiedAlgRF != null) // Algebra's foot dominates separately chosen Foot
		{
			RealF[] tC = new RealF[copiedAlgRF.getGProduct().getBladeCount()];
			for (short m=0; m<tC.length; m++)	tC[m]=(RealF) CladosField.REALF.createZERO(copiedAlgRF.getFoot().getCardinal());
			MonadRealF rep=new MonadRealF(	monadShort.name.getText(),
											copiedAlgRF,
											monadShort.frame.getText(),
											tC);
			NyadRealF rep2=new NyadRealF("New", rep);
			test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		else if (copiedFoot != null)
		{	
			MonadRealF rep=new MonadRealF(	monadShort.name.getText(),
											monadShort.aname.getText(),
											monadShort.frame.getText(),
											copiedFoot,
											monadShort.sig.getText(),
											(RealF) CladosField.REALF.createZERO(monadShort.cardname.getText()));
			NyadRealF rep2=new NyadRealF("New", rep);
    		_GUI._GeometryDisplay.addNyad(rep2);
    		test = true;
		}
		else
		{
    		MonadRealF rep=new MonadRealF(	monadShort.name.getText(),
    										monadShort.aname.getText(),
    										monadShort.frame.getText(),
    										monadShort.foot.getText(),
    										monadShort.sig.getText(),
    										(RealF) CladosField.REALF.createZERO(monadShort.cardname.getText()));
    		NyadRealF rep2=new NyadRealF("New", rep);
    		test = true;
    		_GUI._GeometryDisplay.addNyad(rep2);
		}
		return test;
	}
	private int getMonadPaneFocus()
	{
		return (_GUI._GeometryDisplay.getNyadPanel(getNyadPaneFocus()).getPaneFocus());
	}
	private MonadPanel getMonadPanelFocus()
	{
		return getNyadPanelFocus().getMonadPanel(getMonadPaneFocus());
	}
	private int getNyadPaneFocus()
	{
		return _GUI._GeometryDisplay.getPaneFocus();
	}
    private NyadPanel getNyadPanelFocus()
	{
		return _GUI._GeometryDisplay.getNyadPanel(getNyadPaneFocus());
	}
    
}