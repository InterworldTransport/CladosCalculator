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
import com.interworldtransport.cladosF.DivField;
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
	/**
	* This is a factory method for creating a new nyad to add to the stack
	* 
	* @param pGUI
	*	CladosCalculator
	* This parameter references the owning application. Nothing spectacular.
	* @return 
	*  CreateDialog 
	*  This method returns a CreateDialog instance
	*  The point of this being static is to enable making the regular constructor private later.
	*/
	public static final CreateDialog createNyad(CladosCalculator pGUI) 
	{
		CreateDialog tCD = null;
		try
    	{
    		tCD= new CreateDialog(pGUI, true);
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
	* This is a factory method for creating a new monad to add to the selected nyad's stack
	* 
	* @param pGUI
	*	CladosCalculator
	* This parameter references the owning application. Nothing spectacular.
	* @return 
	*  CreateDialog 
	*  This method returns a CreateDialog instance
	*  The point of this being static is to enable making the regular constructor private later.
	*/
	public static final CreateDialog createMonad(CladosCalculator pGUI) 
	{
		CreateDialog tCD = null;
		try
    	{
    		tCD= new CreateDialog(pGUI, false);
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
	
	
	
	private	CladosCalculator	_GUI;
	private	MonadPanel			mainPane;
	private	JButton				closeButton;
	private	JButton				saveButton;
	private	JButton				getFootButton;
	private	JButton				getAlgebraButton;
	private	final Dimension		square = new Dimension(30,30);
	private	Color				_monadColor = new Color(212, 212, 192);
	private	Color				_nyadColor = new Color(212, 200, 212);
	private AlgebraComplexD		copyAlgTargetCD;
	private AlgebraComplexF		copyAlgTargetCF;
	private AlgebraRealD		copyAlgTargetRD;
	private AlgebraRealF		copyAlgTargetRF;
	private Foot				copyFootTarget;

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
	public CreateDialog(CladosCalculator mainWindow, boolean makeNyad)
	throws 		UtilitiesException, BadSignatureException, CladosMonadException
	{
		
		super(mainWindow, (makeNyad ? "Create Nyad Panel": "Create Monad Panel"), false);
		_GUI=mainWindow;
		
		JPanel centerPanel=new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.setBackground(makeNyad ? _nyadColor : _monadColor);
		
		mainPane = new MonadPanel(_GUI);
		
		//mainPane.syncButton.setEnabled(false);
		mainPane.makeWritable();
		centerPanel.add(new JScrollPane(mainPane), "Center");
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
		
		centerPanel.add(dialogControlPane, "South");
		
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
		
		centerPanel.add(dialogGetPane, "North");
		
		// Set the size of the window
		pack();
		
		// Center the window on the parent window.
		Point parentLocation = mainWindow.getLocation();
		int Xloc = (int) parentLocation.getX() + ((mainWindow.getWidth() - this.getWidth()) / 2);
		int Yloc = (int) parentLocation.getY()+100;
		setLocation(Xloc, Yloc);
		
		// Display window
		setVisible(true);
	}

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
	    		case DivField.REALF:	copyFootTarget=_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadRF().getFootPoint();
	    								break;
	    		case DivField.REALD:	copyFootTarget=_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadRD().getFootPoint();
										break;
	    		case DivField.COMPLEXF:	copyFootTarget=_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadCF().getFootPoint();
										break;
	    		case DivField.COMPLEXD:	copyFootTarget=_GUI._GeometryDisplay.getNyadPanel(tSpot).getNyadCD().getFootPoint();
										break;
	    	}
	    	mainPane.foot.setText(copyFootTarget.getFootName());
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
	    		case DivField.REALF:	copyAlgTargetRF=(tSpotNPanel.getMonadPanel(tSpotMonadIndex)).getMonadRF().getAlgebra();
						    			mainPane.aname.setText(copyAlgTargetRF.getAlgebraName());
							    		mainPane.foot.setText(copyAlgTargetRF.getFoot().getFootName());
							    		mainPane.sig.setText(copyAlgTargetRF.getGProduct().getSignature());
	    								break;
	    		case DivField.REALD:	copyAlgTargetRD=(tSpotNPanel.getMonadPanel(tSpotMonadIndex)).getMonadRD().getAlgebra();
						    			mainPane.aname.setText(copyAlgTargetRD.getAlgebraName());
							    		mainPane.foot.setText(copyAlgTargetRD.getFoot().getFootName());
							    		mainPane.sig.setText(copyAlgTargetRD.getGProduct().getSignature());
										break;
	    		case DivField.COMPLEXF:	copyAlgTargetCF=(tSpotNPanel.getMonadPanel(tSpotMonadIndex)).getMonadCF().getAlgebra();
						    			mainPane.aname.setText(copyAlgTargetCF.getAlgebraName());
							    		mainPane.foot.setText(copyAlgTargetCF.getFoot().getFootName());
							    		mainPane.sig.setText(copyAlgTargetCF.getGProduct().getSignature());
										break;
	    		case DivField.COMPLEXD:	copyAlgTargetCD=(tSpotNPanel.getMonadPanel(tSpotMonadIndex)).getMonadCD().getAlgebra();
						    			mainPane.aname.setText(copyAlgTargetCD.getAlgebraName());
							    		mainPane.foot.setText(copyAlgTargetCD.getFoot().getFootName());
							    		mainPane.sig.setText(copyAlgTargetCD.getGProduct().getSignature());
										break;
	    	}
    	}
    	
    	if (command.equals("Save Nyad"))
    	{	
    		// TODO Fix for other DivFields
    		// this should probably be slipped out to a private method
	    	try
	    	{
	    		if (copyAlgTargetRF != null) // Algebra's foot dominates separately chosen Foot
    			{
    				RealF tZero=new RealF(copyAlgTargetRF.getFoot().getNumberType(), 0.0f);
    				RealF[] tC = new RealF[copyAlgTargetRF.getGProduct().getBladeCount()];
    				
    				for (short m=0; m<tC.length; m++)
    					tC[m]=RealF.copyZERO(tZero);
    				
    				MonadRealF rep=new MonadRealF(	mainPane.name.getText(),
    												copyAlgTargetRF,
    												mainPane.frame.getText(),
    												tC);
    				NyadRealF rep2=new NyadRealF("New", rep);
		    		_GUI._GeometryDisplay.addNyad(rep2);
		    		_GUI._StatusBar.setStatusMsg("\tnew nyad added to stack...");
    			}
	    		else if (copyFootTarget != null && copyAlgTargetRF == null)
	    		{	
	    			MonadRealF rep=new MonadRealF(	mainPane.name.getText(),
													mainPane.aname.getText(),
													mainPane.frame.getText(),
													copyFootTarget,
													mainPane.sig.getText(),
													new RealF(copyFootTarget.getNumberType()));
	    			NyadRealF rep2=new NyadRealF("New", rep);
		    		_GUI._GeometryDisplay.addNyad(rep2);
		    		_GUI._StatusBar.setStatusMsg("\tnew nyad added to stack...");
	    		}
	    		else if (copyFootTarget == null && copyAlgTargetRF == null)
	    		{
		    		MonadRealF rep=new MonadRealF(	mainPane.name.getText(),
		    										mainPane.aname.getText(),
		    										mainPane.frame.getText(),
		    										mainPane.foot.getText(),
		    										mainPane.sig.getText(),
		    										RealF.newZERO(mainPane.aname.getText()));
		    		NyadRealF rep2=new NyadRealF("New", rep);
		    		_GUI._GeometryDisplay.addNyad(rep2);
		    		_GUI._StatusBar.setStatusMsg("\tnew nyad added to stack...");
	    		}
	    		
	    	}
	    	catch (UtilitiesException e)
	    	{
	    		System.out.println("Could not create monad copy from Create|Save with general utilities exception");
	    	}
	    	catch (BadSignatureException es)
	    	{
	    		System.out.println("Could not create monad copy from Create|Save with bad signature");
	    	}
	    	catch (CladosMonadException em)
	    	{
	    		System.out.println("Could not create monad copy from Create|Save with general error");
	    	}
    	}
    	
    	if (command.equals("Save Monad"))
    	{	//get the existing nyad to be used to create this next monad
    		
    		// TODO Fix for other DivFields
    		// this should probably be slipped out to a private method
    		int tSpot=_GUI._GeometryDisplay.getPaneFocus();
    		if (tSpot<0) return; //No nyad present for appending this
    		
    		NyadPanel tNSpotP = _GUI._GeometryDisplay.getNyadPanel(tSpot);
    		
    		NyadRealF tNSpotF = tNSpotP.getNyadRF();
    		
    		try
    		{
    			MonadRealF rep = null;
    			if (copyAlgTargetRF != null)
    			{
    				// Check for algebra uniqueness within nyad. If non-unique, fail this gracefully
    				if(NyadRealF.hasAlgebra(tNSpotF, copyAlgTargetRF))
    				{
    					_GUI._StatusBar.stmesgt.append("\t\tchosen algebra already present in nyad. No monad added.");
    					return;
    				}
    				
    				if (copyAlgTargetRF.getFoot() != tNSpotF.getFootPoint())
    				{
    					_GUI._StatusBar.stmesgt.append("\t\tchosen algebra had different foot from nyad. No monad added.");
    					return;
    				}
    				//Foot reference match ensured. Algebra reference mismatch ensured. Moving on.
    				
    				RealF[] tC = new RealF[copyAlgTargetRF.getGProduct().getBladeCount()];
    				for (short m=0; m<tC.length; m++)
    					tC[m]=RealF.copyZERO(tNSpotF.getProto()); //could use copyAlgTargetRF.protoOne with no difference.
    				
    				// TODO Fix for other DivFields since it is assuming RealF
    				
    				rep=new MonadRealF(		mainPane.name.getText(),
    										copyAlgTargetRF,
    										mainPane.frame.getText(),
    										tC);
    				tNSpotF.appendMonad(rep);	//This won't fail because alg uniqueness was checked
    				tNSpotP.addMonadPanel(rep);
        			_GUI._StatusBar.setStatusMsg("\tnew monad added to selected nyad...");
    			}
    			else if (copyAlgTargetRF == null) //Choosing foot doesn't matter. Nyad already has one.
    			{
	    			tNSpotF.createMonad(	mainPane.name.getText(), 
	    									mainPane.aname.getText(), 
	    									mainPane.frame.getText(), 
	    									mainPane.sig.getText());
	    			rep = tNSpotF.getMonadList(tNSpotF.getNyadOrder()-1);
	    			tNSpotP.addMonadPanel(rep);
	    			_GUI._StatusBar.setStatusMsg("\tnew monad added to selected nyad...");
    			}	
    		}
    		catch (UtilitiesException e)
    		{
    			System.out.println("Could not create monad copy from Create|Save with general utilities exception");
    			e.printStackTrace();
    		}
    		catch (BadSignatureException es)
    		{
    			System.out.println("Could not create monad copy from Create|Save with bad signature");
    			es.printStackTrace();
    		}
    		catch (CladosMonadException em)
    		{
    			System.out.println("Could not create monad copy from Create|Save with general error");
    			em.printStackTrace();
    		}
    		catch (CladosNyadException en)
    		{
    			System.out.println("Could not append monad | "+en.getMessage());	
    		}
    	}
    }
}