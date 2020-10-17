/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.MonadPanel<br>
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
 * ---com.interworldtransport.cladosviewer.MonadPanel<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer ;

import com.interworldtransport.cladosF.CladosFBuilder;
import com.interworldtransport.cladosF.CladosField;
import com.interworldtransport.cladosF.ComplexD;
import com.interworldtransport.cladosF.ComplexF;
import com.interworldtransport.cladosF.RealD;
import com.interworldtransport.cladosF.RealF;

import com.interworldtransport.cladosG.MonadComplexD;
import com.interworldtransport.cladosG.MonadComplexF;
import com.interworldtransport.cladosG.MonadRealD;
import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosGExceptions.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import java.util.*;

/** com.interworldtransport.cladosviewer.MonadPanel
 * The MonadPanel class directly handles the gui for a single Monad.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class MonadPanel extends JPanel implements ActionListener, FocusListener
{
	private static final 	long 						serialVersionUID = 7012167884382905369L;
	private static final 	String 						_IMAGINARY = 		"[I]";
	private static final 	String						_REAL = 			"[R]";
	private	static final	Color						clrBackColor = 		new Color(212, 212, 192);
	private	static final	Color						clrUnlockColor = 	new Color(255, 192, 192);
	private static final	int							COEFF_SIZE = 		10;
	private	static final	Dimension					squareLittle =		new Dimension(25,25);
	private	static final	Dimension					squareMedium =		new Dimension(28,28);
	private static final	Font						_PLAINFONT = 		new Font(Font.SERIF, Font.PLAIN, COEFF_SIZE);
	//private static final	Font						_ITALICFONT = 		new Font(Font.SERIF, Font.ITALIC, COEFF_SIZE);
	
	private					ArrayList<FieldDisplay>		_jCoeffs;
	private					CladosField					_repMode;
	private					MonadComplexD				_repMonadCD;
	private					MonadComplexF				_repMonadCF;
	private					MonadRealD					_repMonadD;
	private					MonadRealF					_repMonadF;
	private					JButton						btnChangeOrient;
	private					JButton						btnDualLeft;
	private					JButton						btnDualRight;
	private					JButton						btnEdit;
	private					JButton						btnGradeCrop;
	private					JButton						btnGradeCut;
	private					JButton						btnInvertMonad; //This is NOT multiplicative inverse
	private					JButton						btnNormalizeMonad;
	private					JButton						btnRestore;
	private					JButton						btnReverseMonad;
	private					JButton						btnScaleMonad;
	private					JButton						btnSync;
	private					ImageIcon					iconHorizontal;
	private					ImageIcon					iconVertical;
	private					JPanel 						pnlMonadAlterControls;
	private					JPanel 						pnlMonadCoeffPanel;
	private					JPanel 						pnlMonadEditControls;
	private					JPanel 						pnlMonadReferences;
	/*
	 * This boolean is for knowing whether to render the coefficients.
	 * This panel doubles as a monad create dialog where no coefficients can exist
	 * until after a generator signature is given.
	 */
	private					boolean						useFullPanel;
	protected				boolean						_editMode;
	public					CladosCalculator			_GUI;
	
	protected				JTextField					aname=new JTextField(16);
	protected				JTextField					foot=new JTextField(16);
	protected				JTextField					frame=new JTextField(16);
	protected				JLabel						gradeKey=new JLabel();
	protected				JTextField					name=new JTextField(16);
	protected				JTextField					sig=new JTextField(16);

  /**
  * The MonadPanel class is intended to be contain only the high level parts of a monad 
  * in order to offer its parts for display and manipulation in a 'Create' dialog.
  * 
  * @param pGUI				CladosCalculator
  */
   public MonadPanel(CladosCalculator pGUI)
   {
	   super();
	   _GUI=pGUI;
	   _repMode=_GUI._FieldBar.getRepMode();
	   setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	   setBackground(clrBackColor);
	   setLayout(new BorderLayout());
	   name.setText("tablePlace");
	   aname.setText(_GUI.IniProps.getProperty("Desktop.Default.AlgebraName"));
	   frame.setText(_GUI.IniProps.getProperty("Desktop.Default.FrameName"));
	   foot.setText(_GUI.IniProps.getProperty("Desktop.Default.FootName"));
	   sig.setText(_GUI.IniProps.getProperty("Desktop.Default.Sig"));
	   
	   useFullPanel=false;	// Use this panel in it's small sense
	   pnlMonadReferences=new JPanel();
	   pnlMonadReferences.setBackground(clrBackColor);
	   pnlMonadReferences.setLayout(new GridBagLayout());
	   	
	   GridBagConstraints cn0 = new GridBagConstraints();
	   cn0.anchor=GridBagConstraints.WEST;
	   cn0.gridx = 0;
	   cn0.gridy = 0;
	   cn0.weightx=0;
	   cn0.weighty=0;
	   	
	   pnlMonadReferences.add(new JLabel("Name", SwingConstants.RIGHT), cn0);
	   cn0.gridx++;
	   name.setFont(_PLAINFONT);
	   pnlMonadReferences.add(name, cn0);
	   cn0.gridx=0;
	   cn0.gridy++;
	   	
	   pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Foot"))), cn0);
	   cn0.gridx++;
	   foot.setFont(_PLAINFONT);
	   pnlMonadReferences.add(foot, cn0);
	   cn0.gridx=0;
	   cn0.gridy++;
	   	
	   pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Sig"))), cn0);
	   cn0.gridx++;
	   sig.setFont(_PLAINFONT);
	   pnlMonadReferences.add(sig, cn0);
	   cn0.gridx = 0;
	   cn0.gridy++;
	   	
	   pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Alg"))), cn0);
	   cn0.gridx++;
	   aname.setFont(_PLAINFONT);
	   pnlMonadReferences.add(aname, cn0);
	   cn0.gridx=0;
	   cn0.gridy++;
	   	
	   pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Frame"))), cn0);
	   cn0.gridx++;
	   frame.setFont(_PLAINFONT);
	   pnlMonadReferences.add(frame, cn0);
	   	
	   add(pnlMonadReferences,"South");
   }

   /**
   * The MonadPanel class is intended to hold a single Monad and act as its GUI.
   * This constructor is the base one.
   * @param pGUI	CladosCalculator
   * This is just a reference to the owner application so error messages can be presented.
   * @param pM		MonadComplexD
   * This is a reference to the monad to be displayed and manipulated.
   */
     public MonadPanel(		CladosCalculator pGUI, MonadComplexD pM)
     {
    	super();
       	useFullPanel=true;
       	try
	    {
	       	_GUI=pGUI;
	       	_repMonadCD=pM;
	       	_repMode=CladosField.COMPLEXD;
	       	
	       	btnChangeOrient = new JButton();
	    	btnChangeOrient.setPreferredSize(squareLittle);
	    	btnChangeOrient.setBorder(BorderFactory.createEtchedBorder(0));
	    	iconHorizontal=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
	    	iconVertical=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
	    	
	    	switch (_GUI.IniProps.getProperty("Desktop.MVRender"))
	    	{
	    		case "Vertical":	btnChangeOrient.setIcon(iconVertical);
		    						btnChangeOrient.setToolTipText("Monad grades as columns");
		    						break;
		    	case "Horizontal":	btnChangeOrient.setIcon(iconHorizontal);
		    						btnChangeOrient.setToolTipText("Monad grades as rows");
		    						break;
		    	default: 			btnChangeOrient.setIcon(iconVertical);
		    						btnChangeOrient.setToolTipText("Monad grades as columns");
	    	}
	    	btnChangeOrient.setActionCommand("flip");
	    	btnChangeOrient.addActionListener(this);
	    
	        setReferences();
	           		
	        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	        setBackground(clrBackColor);
	        setLayout(new BorderLayout());          
	    
	        this.add(createCoeffLayout(), "Center");
	    	setCoefficientDisplay();
	    }
    	catch (NullPointerException enull)
    	{
    		add(new JPanel(null, false), "Center");
    		if (pGUI != null) 
 	    	{
 	    		_GUI._StatusBar.setStatusMsg("Null Pointer Exception. Something is missing on MPanel construction.\n");
 	    		_GUI._StatusBar.setStatusMsg(enull.getClass()+"\n");
 	    		_GUI._StatusBar.setStatusMsg(enull.getMessage()+"\n");
 	    	}
    	}
    	finally
    	{
    		createReferenceLayout();
	        createEditLayout();
	        createManagementLayout();
    	}
     }
    /**
        * The MonadPanel class is intended to hold a single Monad and act as its GUI.
        * This constructor is the base one.
        * @param pGUI	CladosCalculator
        * This is just a reference to the owner application so error messages can be presented.
        * @param pM		MonadComplexF
        * This is a reference to the monad to be displayed and manipulated.
        */
          public MonadPanel(	CladosCalculator pGUI, MonadComplexF pM)
          {
   	       	super();
   	       	useFullPanel=true;
   	       	try
	 	    {
	   	       	_GUI=pGUI;
	   	       	_repMonadCF=pM;
	   	       	_repMode=CladosField.COMPLEXF;
	   	       	
	   	       	btnChangeOrient = new JButton();
		    	btnChangeOrient.setPreferredSize(squareLittle);
		    	btnChangeOrient.setBorder(BorderFactory.createEtchedBorder(0));
		    	iconHorizontal=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
		    	iconVertical=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
		    	
		    	switch (_GUI.IniProps.getProperty("Desktop.MVRender"))
		    	{
		    		case "Vertical":	btnChangeOrient.setIcon(iconVertical);
			    						btnChangeOrient.setToolTipText("Monad grades as columns");
			    						break;
			    	case "Horizontal":	btnChangeOrient.setIcon(iconHorizontal);
			    						btnChangeOrient.setToolTipText("Monad grades as rows");
			    						break;
			    	default: 			btnChangeOrient.setIcon(iconVertical);
			    						btnChangeOrient.setToolTipText("Monad grades as columns");
		    	}
		    	btnChangeOrient.setActionCommand("flip");
		    	btnChangeOrient.addActionListener(this);
	        
	   	        setReferences();
	   	           		
	   	        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	   	        setBackground(clrBackColor);
	   	        setLayout(new BorderLayout());
	   	    
	 	        this.add(createCoeffLayout(), "Center");
	 	    	setCoefficientDisplay();
	 	    }
	    	catch (NullPointerException enull)
	    	{
	    		add(new JPanel(null, false), "Center");
	    		if (pGUI != null) 
	 	    	{
	 	    		_GUI._StatusBar.setStatusMsg("Null Pointer Exception. Something is missing on MPanel construction.\n");
	 	    		_GUI._StatusBar.setStatusMsg(enull.getClass()+"\n");
	 	    		_GUI._StatusBar.setStatusMsg(enull.getMessage()+"\n");
	 	    	}
	    	}
	    	finally
	    	{
	    		createReferenceLayout();
		        createEditLayout();
		        createManagementLayout();
	    	}
          }
       /**
     * The MonadPanel class is intended to hold a single Monad and act as its GUI.
     * This constructor is the base one.
     * @param pGUI	CladosCalculator
	 * This is just a reference to the owner application so error messages can be presented.
     * @param pM	MonadRealD
     * This is a reference to the monad to be displayed and manipulated.
     */
       public MonadPanel(	CladosCalculator pGUI, MonadRealD pM)
       {
	       	super();
	       	useFullPanel=true;
	       	try
	 	    {
		       	_GUI=pGUI;
		       	_repMonadD=pM;
		       	_repMode=CladosField.REALD;
		       	
		       	btnChangeOrient = new JButton();
		    	btnChangeOrient.setPreferredSize(squareLittle);
		    	btnChangeOrient.setBorder(BorderFactory.createEtchedBorder(0));
		    	iconHorizontal=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
		    	iconVertical=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
		    	
		    	switch (_GUI.IniProps.getProperty("Desktop.MVRender"))
		    	{
		    		case "Vertical":	btnChangeOrient.setIcon(iconVertical);
			    						btnChangeOrient.setToolTipText("Monad grades as columns");
			    						break;
			    	case "Horizontal":	btnChangeOrient.setIcon(iconHorizontal);
			    						btnChangeOrient.setToolTipText("Monad grades as rows");
			    						break;
			    	default: 			btnChangeOrient.setIcon(iconVertical);
			    						btnChangeOrient.setToolTipText("Monad grades as columns");
		    	}
		    	btnChangeOrient.setActionCommand("flip");
		    	btnChangeOrient.addActionListener(this);
	     
		        setReferences();
		           		
		        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		        setBackground(clrBackColor);
		        setLayout(new BorderLayout());
	          
	 	        this.add(createCoeffLayout(), "Center");
	 	    	setCoefficientDisplay();
	 	    }
	    	catch (NullPointerException enull)
	    	{
	    		add(new JPanel(null, false), "Center");
	    		if (pGUI != null) 
	 	    	{
	 	    		_GUI._StatusBar.setStatusMsg("Null Pointer Exception. Something is missing on MPanel construction.\n");
	 	    		_GUI._StatusBar.setStatusMsg(enull.getClass()+"\n");
	 	    		_GUI._StatusBar.setStatusMsg(enull.getMessage()+"\n");
	 	    	}
	    	}
	    	finally
	    	{
	    		createReferenceLayout();
		        createEditLayout();
		        createManagementLayout();
	    	}
       }
          /**
		    * The MonadPanel class is intended to hold a single Monad and act as its GUI.
		    * This constructor is the base one.
		    * @param pGUI	CladosCalculator
		    * This is just a reference to the owner application so error messages can be presented.
		    * @param pM		MonadRealF
		    * This is a reference to the monad to be displayed and manipulated.
		    */
		    public MonadPanel(	CladosCalculator pGUI, MonadRealF pM)
		    {
		    	super();
		    	useFullPanel=true;    	
		    	try
		 	    {
			    	_GUI=pGUI;
			    	_repMonadF=pM;
			    	_repMode=CladosField.REALF;
			    	
			    	btnChangeOrient = new JButton();
			    	btnChangeOrient.setPreferredSize(squareLittle);
			    	btnChangeOrient.setBorder(BorderFactory.createEtchedBorder(0));
			    	iconHorizontal=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
			    	iconVertical=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
			    	
			    	switch (_GUI.IniProps.getProperty("Desktop.MVRender"))
			    	{
			    		case "Vertical":	btnChangeOrient.setIcon(iconVertical);
				    						btnChangeOrient.setToolTipText("Monad grades as columns");
				    						break;
				    	case "Horizontal":	btnChangeOrient.setIcon(iconHorizontal);
				    						btnChangeOrient.setToolTipText("Monad grades as rows");
				    						break;
				    	default: 			btnChangeOrient.setIcon(iconVertical);
				    						btnChangeOrient.setToolTipText("Monad grades as columns");
			    	}
			    	btnChangeOrient.setActionCommand("flip");
			    	btnChangeOrient.addActionListener(this);
			    	
			        setReferences();
			        		
			        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			        setBackground(clrBackColor);
			        setLayout(new BorderLayout());
			        
		 	        this.add(createCoeffLayout(), "Center");
		 	    	setCoefficientDisplay();
		 	    }
		    	catch (NullPointerException enull)
		    	{
		    		add(new JPanel(null, false), "Center");
		    		if (pGUI != null) 
		 	    	{
		 	    		_GUI._StatusBar.setStatusMsg("Null Pointer Exception. Something is missing on MPanel construction.\n");
		 	    		_GUI._StatusBar.setStatusMsg(enull.getClass()+"\n");
		 	    		_GUI._StatusBar.setStatusMsg(enull.getMessage()+"\n");
		 	    	}
		    	}
		    	finally
		    	{
		    		createReferenceLayout();
			        createEditLayout();
			        createManagementLayout();
		    	}
		    }
    
    @Override
    public void 	actionPerformed(ActionEvent event)
    {
    	switch (event.getActionCommand())
    	{
	    	case "findgrade crop":	_GUI._EventModel.MOpsParts.gradep.actionPerformed(event);
	    							break;
	    	case "findgrade cut":	_GUI._EventModel.MOpsParts.grades.actionPerformed(event);
	    							break;
	    	case "scale":			_GUI._EventModel.MOpsParts.scale.actionPerformed(event);
									break;
	    	case "normalize":		_GUI._EventModel.MOpsParts.norm.actionPerformed(event);
									break;
	    	case "invert":			_GUI._EventModel.MOpsParts.invt.actionPerformed(event);
									break;
	    	case "reverse":			_GUI._EventModel.MOpsParts.rev.actionPerformed(event);
									break;
	    	case "<dual":			_GUI._EventModel.MOpsParts.dualRight.actionPerformed(event);
									break;
	    	case "dual>":			_GUI._EventModel.MOpsParts.dualLeft.actionPerformed(event);
									break;
	    	case "flip": 			if (btnChangeOrient.getIcon().equals(iconHorizontal))
							    	{
	    								btnChangeOrient.setIcon(iconVertical);
							    		add(createCoeffLayout(), "Center");
									    btnChangeOrient.setToolTipText("Monad grades as columns");
							    	}
							    	else
							    	{
							    		btnChangeOrient.setIcon(iconHorizontal);
							    		add(createCoeffLayout(), "Center");
									    btnChangeOrient.setToolTipText("Monad grades as rows");
							    	}
	    							validate();
	    							_GUI.pack();
	    							break;
	    	case "save":	    	setRepMonad();	//Update internal details of the represented Monad
	    					    	btnEdit.setActionCommand("edit");
	    						    btnEdit.setToolTipText("start edits");
	    						    btnSync.setEnabled(false);
	    						    btnRestore.setEnabled(false);
	    						    makeNotWritable();
	    						    _editMode=false;
	    					    	break;
	    	case  "abort":			setReferences();
							    	setCoefficientDisplay();
							    	btnEdit.setActionCommand("edit");
						    		btnEdit.setToolTipText("start edits");
						    		btnSync.setEnabled(false);
						        	btnRestore.setEnabled(false);
						    	   	makeNotWritable();
							    	_editMode=false;
							    	break;
	    	case ".edit.":    		btnEdit.setActionCommand("edit");
							    	btnEdit.setToolTipText("start edits");
							    	btnSync.setEnabled(false);
							        btnRestore.setEnabled(false);
							    	makeNotWritable();
							    	_editMode=false;
							    	break;
	    	case "edit":     		_editMode=true;
						    		btnEdit.setActionCommand(".edit.");
						    		btnEdit.setToolTipText("end edits w/o abort");
						    		btnSync.setEnabled(true);
						        	btnRestore.setEnabled(true);
						    		makeWritable();
						    		break;
	    	default: 				_GUI._StatusBar.setStatusMsg("No Detectable Command at the Monad Panel. No action.\n");
    	}
    }
    /**
     * This method is overridden to allow the MonadPanel with the focus to update the FieldBar with 
     * the vales in the FieldArea of the represented monad.
     * This is similar to what a nyad panel does when it receives focus and updates the DivFieldType
     */
    @Override
    public void focusGained(FocusEvent e) 
    {
    	if (e.getComponent() instanceof FieldDisplay & !_editMode) // Only do this when NOT in edit mode.
    	{
    		JTextArea tSpot = (JTextArea) e.getComponent();
    		StringBuilder strB = new StringBuilder(tSpot.getText());
    		
			int tBufferLength = strB.length();
			if (tBufferLength == 0 ) return; // Nothing to save, so surrender.
			int tR=MonadPanel._REAL.length();
			int tI=MonadPanel._IMAGINARY.length();
			int indexOfR = strB.indexOf(MonadPanel._REAL)+tR;
			int indexOfI = strB.indexOf(MonadPanel._IMAGINARY)+tI;
			
			switch (_repMode)
			{
				case REALF:	_GUI._FieldBar.setField((((FieldDisplay) e.getComponent()).displayFieldRF));;
							float tSpotRF = Float.parseFloat(strB.substring(indexOfR, tBufferLength));
							_GUI._FieldBar.setWhatFloatR(tSpotRF);
							break;
				case REALD:	_GUI._FieldBar.setField((((FieldDisplay) e.getComponent()).displayFieldRD));;
							double tSpotRD = Double.parseDouble(strB.substring(indexOfR, tBufferLength));
							_GUI._FieldBar.setWhatDoubleR(tSpotRD);
							break;
				case COMPLEXF:	_GUI._FieldBar.setField((((FieldDisplay) e.getComponent()).displayFieldCF));;
								float tSpotCF1 = Float.parseFloat(strB.substring(indexOfR, indexOfI-tI-1));
								float tSpotCF2 = Float.parseFloat(strB.substring(indexOfI, tBufferLength));
								_GUI._FieldBar.setWhatFloatR(tSpotCF1);
								_GUI._FieldBar.setWhatFloatI(tSpotCF2);
								break;
				case COMPLEXD:	_GUI._FieldBar.setField((((FieldDisplay) e.getComponent()).displayFieldCD));;
								double tSpotCD1 = Double.parseDouble(strB.substring(indexOfR, indexOfI-tI-1));
								double tSpotCD2 = Double.parseDouble(strB.substring(indexOfI, tBufferLength));
								_GUI._FieldBar.setWhatDoubleR(tSpotCD1);
								_GUI._FieldBar.setWhatDoubleI(tSpotCD2);
			}
    	}
    }
    
    @Override
	public void focusLost(FocusEvent e) 
    {
    	;
	}
    /**
     * This method provides to the caller an array list of the JTextArea descendents that hold 
     * coefficients for a monad. In this case they are RealF coefficients. It's just a typical
     * get method, though. Nothing special.
     * @return ArrayList
     * An array list of JTextArea descedents is returned that holds the coefficients of a monad.
     */
    public ArrayList<FieldDisplay> getJCoeffs()
    {	
    	return _jCoeffs;
    }
    
    /**
     * This method provides to the caller a MonadComplexD held in this panel. It's just a typical
     * get method, though. Nothing special.
     * @return MonadRealCD
     */
    public MonadComplexD 	getMonadCD()
    {
	    return _repMonadCD;
    }
    /**
     * This method provides to the caller a MonadComplexF held in this panel. It's just a typical
     * get method, though. Nothing special.
     * @return MonadRealCF
     */
    public MonadComplexF 	getMonadCF()
    {
	    return _repMonadCF;
    }
    /**
     * This method provides to the caller a MonadRealD held in this panel. It's just a typical
     * get method, though. Nothing special.
     * @return MonadRealD
     */
    public MonadRealD 	getMonadRD()
    {
	    return _repMonadD;
    }
    /**
     * This method provides to the caller a MonadRealF held in this panel. It's just a typical
     * get method, though. Nothing special.
     * @return MonadRealF
     */
    public MonadRealF 	getMonadRF()
    {
	    return _repMonadF;
    }
    
    public CladosField		getRepMode()
    {
    	return _repMode;
    }
/**
 * In this method we assume the underlying Monad has changed and the FieldDisplay's in the panel
 * has to be updated... or the FieldDisplay's changed and an edit abort occurred. 
 * This means working through the Coeff's for the Monad and updating the FieldDisplays.
 * 
 * It is safe enough to call this from anywhere and at any time. The worst that can happen is
 * the user looses some of their changes on the UI.
 */
    public void 	setCoefficientDisplay()
    {
    	short j=0;
		switch (_repMode)
		{	
    		case REALF: 	for (j=0; j<_repMonadF.getAlgebra().getGProduct().getBladeCount(); j++)
						    {
				    			_jCoeffs.get(j).updateField(_repMonadF.getCoeff(j));	//	fodder for the update
								_jCoeffs.get(j).displayContents();
						    }
    						gradeKey.setText(new StringBuffer().append(_repMonadF.getGradeKey()).toString());
    						break;
    		case REALD: 	for (j=0; j<_repMonadD.getAlgebra().getGProduct().getBladeCount(); j++)
						    {
								_jCoeffs.get(j).updateField(_repMonadD.getCoeff(j));	//	fodder for the update
								_jCoeffs.get(j).displayContents();
						    }
							gradeKey.setText(new StringBuffer().append(_repMonadD.getGradeKey()).toString());
    						break;
    		case COMPLEXF:	for (j=0; j<_repMonadCF.getAlgebra().getGProduct().getBladeCount(); j++)
						    {
								_jCoeffs.get(j).updateField(_repMonadCF.getCoeff(j));	//	fodder for the update
								_jCoeffs.get(j).displayContents();
						    }
							gradeKey.setText(new StringBuffer().append(_repMonadCF.getGradeKey()).toString());
							break;
			case COMPLEXD:	for (j=0; j<_repMonadCD.getAlgebra().getGProduct().getBladeCount(); j++)
						    {
								_jCoeffs.get(j).updateField(_repMonadCD.getCoeff(j));	//	fodder for the update
								_jCoeffs.get(j).displayContents();
						    }
							gradeKey.setText(new StringBuffer().append(_repMonadCD.getGradeKey()).toString());
		}
    }

    private JPanel 	createCoeffLayout()
    {		
    	if (_jCoeffs == null)			// First time? Create the ArrayList
    		initiateCoeffList();		// Listeners get added here the first time and need not be reset
    									// because the panels that display them are just containers... not handlers.
    	
    	if (pnlMonadCoeffPanel != null)
    		remove(pnlMonadCoeffPanel);
    		
	    pnlMonadCoeffPanel=new JPanel();
	    StringBuffer tB = new StringBuffer();
	    switch (_repMode)
	    {
	    	case REALF:		tB.append(_repMonadF.getAlgebra().getFoot().getFootName()+" | ");
		    				tB.append(_repMonadF.getAlgebra().getAlgebraName()+" | ");
		    				tB.append(_repMonadF.getAlgebra().getGProduct().getSignature());
		    				pnlMonadCoeffPanel.setBorder(BorderFactory.createTitledBorder(tB.toString()));
		    				break;
	    	case REALD:		tB.append(_repMonadD.getAlgebra().getFoot().getFootName()+" | ");
							tB.append(_repMonadD.getAlgebra().getAlgebraName()+" | ");
							tB.append(_repMonadD.getAlgebra().getGProduct().getSignature());
							pnlMonadCoeffPanel.setBorder(BorderFactory.createTitledBorder(tB.toString()));
							break;
	    	case COMPLEXF:	tB.append(_repMonadCF.getAlgebra().getFoot().getFootName()+" | ");
							tB.append(_repMonadCF.getAlgebra().getAlgebraName()+" | ");
							tB.append(_repMonadCF.getAlgebra().getGProduct().getSignature());
							pnlMonadCoeffPanel.setBorder(BorderFactory.createTitledBorder(tB.toString()));
							break;
	    	case COMPLEXD:	tB.append(_repMonadCD.getAlgebra().getFoot().getFootName()+" | ");
							tB.append(_repMonadCD.getAlgebra().getAlgebraName()+" | ");
							tB.append(_repMonadCD.getAlgebra().getGProduct().getSignature());
							pnlMonadCoeffPanel.setBorder(BorderFactory.createTitledBorder(tB.toString()));
	    }
	   
	    pnlMonadCoeffPanel.setBackground(clrBackColor);
	    pnlMonadCoeffPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn1 = new GridBagConstraints();
    	cn1.insets = new Insets(0, 0, 0, 0);
    		
    	cn1.gridx = 0;
    	cn1.gridy = 0;
    	cn1.weightx=0;
    	cn1.weighty=0;
    	cn1.ipadx=0;
    	cn1.ipady=0;
    		
    	if (btnChangeOrient.getIcon().equals(iconVertical))
    	{
    		short j=0;
    		short k=0;
    		JLabel headLabel;
    		short[] tSpot;
    		switch (_repMode)
    		{
    			case REALF:		for (j=0; j<_repMonadF.getAlgebra().getGProduct().getGradeCount(); j++)
							    {
				    				headLabel = new JLabel(j+"-blades", SwingConstants.CENTER);
				        			headLabel.setFont(_PLAINFONT);
				        			pnlMonadCoeffPanel.add(headLabel, cn1);
				        			cn1.gridy++;
				        			
				        			tSpot = _repMonadF.getAlgebra().getGProduct().getGradeRange(j);
				        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
				        			{
				        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				        				pnlMonadCoeffPanel.add(_jCoeffs.get(k), cn1);
				        				cn1.gridy++;
				        			}
				        			cn1.gridx++;
				        			cn1.gridy=0;
							    }
	    						break;
    			case REALD:		for (j=0; j<_repMonadD.getAlgebra().getGProduct().getGradeCount(); j++)
							    {
				    				headLabel = new JLabel(j+"-blades", SwingConstants.CENTER);
				        			headLabel.setFont(_PLAINFONT);
				        			pnlMonadCoeffPanel.add(headLabel, cn1);
				        			cn1.gridy++;
				        			
				        			tSpot = _repMonadD.getAlgebra().getGProduct().getGradeRange(j);
				        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
				        			{
				        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				        				pnlMonadCoeffPanel.add(_jCoeffs.get(k), cn1);
				        				cn1.gridy++;
				        			}
				        			cn1.gridx++;
				        			cn1.gridy=0;
							    }
	    						break;
    			case COMPLEXF:	for (j=0; j<_repMonadCF.getAlgebra().getGProduct().getGradeCount(); j++)
						        {
				    				headLabel = new JLabel(j+"-blades", SwingConstants.CENTER);
				        			headLabel.setFont(_PLAINFONT);
				        			pnlMonadCoeffPanel.add(headLabel, cn1);
				        			cn1.gridy++;
				        			
				        			tSpot = _repMonadCF.getAlgebra().getGProduct().getGradeRange(j);
				        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
				        			{
				        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				        				pnlMonadCoeffPanel.add(_jCoeffs.get(k), cn1);
				        				cn1.gridy++;
				        			}
				        			cn1.gridx++;
				        			cn1.gridy=0;
						        }
    							break;
    			case COMPLEXD:	for (j=0; j<_repMonadCD.getAlgebra().getGProduct().getGradeCount(); j++)
						        {
				    				headLabel = new JLabel(j+"-blades", SwingConstants.CENTER);
				        			headLabel.setFont(_PLAINFONT);
				        			pnlMonadCoeffPanel.add(headLabel, cn1);
				        			cn1.gridy++;
				        			
				        			tSpot = _repMonadCD.getAlgebra().getGProduct().getGradeRange(j);
				        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
				        			{
				        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				        				pnlMonadCoeffPanel.add(_jCoeffs.get(k), cn1);
				        				cn1.gridy++;
				        			}
				        			cn1.gridx++;
				        			cn1.gridy=0;
						        }
    		}
    		
    	}
    	if (btnChangeOrient.getIcon().equals(iconHorizontal))
    	{
    		short j=0;
    		short k=0;
    		JLabel headLabel;
    		short[] tSpot;
    		switch (_repMode)
    		{
    			case REALF:		for (j=0; j<_repMonadF.getAlgebra().getGProduct().getGradeCount(); j++)
				    			{
				        			headLabel = new JLabel(j+"-blades", SwingConstants.RIGHT);
				        			headLabel.setFont(_PLAINFONT);
				        			pnlMonadCoeffPanel.add(headLabel, cn1);
				        			cn1.gridx++;
				        			
				        			tSpot = _repMonadF.getAlgebra().getGProduct().getGradeRange(j);
				        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
				        			{
				        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				        				pnlMonadCoeffPanel.add(_jCoeffs.get(k), cn1);
				        				cn1.gridx++;
				        			}
				        			cn1.gridx=0;
				        			cn1.gridy++;
				        		}
								break;
    			case REALD:		for (j=0; j<_repMonadD.getAlgebra().getGProduct().getGradeCount(); j++)
				    			{
				        			headLabel = new JLabel(j+"-blades", SwingConstants.RIGHT);
				        			headLabel.setFont(_PLAINFONT);
				        			pnlMonadCoeffPanel.add(headLabel, cn1);
				        			cn1.gridx++;
				        			
				        			tSpot = _repMonadD.getAlgebra().getGProduct().getGradeRange(j);
				        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
				        			{
				        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				        				pnlMonadCoeffPanel.add(_jCoeffs.get(k), cn1);
				        				cn1.gridx++;
				        			}
				        			cn1.gridx=0;
				        			cn1.gridy++;
				        		}
								break;
    			case COMPLEXF:	for (j=0; j<_repMonadCF.getAlgebra().getGProduct().getGradeCount(); j++)
				    			{
				        			headLabel = new JLabel(j+"-blades", SwingConstants.RIGHT);
				        			headLabel.setFont(_PLAINFONT);
				        			pnlMonadCoeffPanel.add(headLabel, cn1);
				        			cn1.gridx++;
				        			
				        			tSpot = _repMonadCF.getAlgebra().getGProduct().getGradeRange(j);
				        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
				        			{
				        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				        				pnlMonadCoeffPanel.add(_jCoeffs.get(k), cn1);
				        				cn1.gridx++;
				        			}
				        			cn1.gridx=0;
				        			cn1.gridy++;
				        		}
								break;
    			case COMPLEXD:	for (j=0; j<_repMonadCD.getAlgebra().getGProduct().getGradeCount(); j++)
				    			{
				        			headLabel = new JLabel(j+"-blades", SwingConstants.RIGHT);
				        			headLabel.setFont(_PLAINFONT);
				        			pnlMonadCoeffPanel.add(headLabel, cn1);
				        			cn1.gridx++;
				        			
				        			tSpot = _repMonadCD.getAlgebra().getGProduct().getGradeRange(j);
				        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
				        			{
				        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				        				pnlMonadCoeffPanel.add(_jCoeffs.get(k), cn1);
				        				cn1.gridx++;
				        			}
				        			cn1.gridx=0;
				        			cn1.gridy++;
				        		}
    		}
    	}
    	return pnlMonadCoeffPanel;
    }
    
    private void		createEditLayout()
    {
    	pnlMonadEditControls=new JPanel();
    	pnlMonadEditControls.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	pnlMonadEditControls.setBackground(clrBackColor);
    	pnlMonadEditControls.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn = new GridBagConstraints();
    	makeNotWritable();
    	
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=0;
    	cn.weighty=0;
    	cn.gridwidth=2;
    	
    	btnEdit=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Edit")));
    	btnEdit.setActionCommand("edit");
    	btnEdit.setToolTipText("start edits");
	 	btnEdit.setPreferredSize(squareLittle);
	 	btnEdit.setBorder(BorderFactory.createEtchedBorder(0));
	 	btnEdit.addActionListener(this);
    	pnlMonadEditControls.add(btnEdit, cn);
    	cn.gridy++;
    	
    	btnSync=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Save")));
    	btnSync.setActionCommand("save");
    	btnSync.setToolTipText("save edits");
	 	btnSync.setEnabled(false);
	 	btnSync.setPreferredSize(squareLittle);
	 	btnSync.setBorder(BorderFactory.createEtchedBorder(0));
	 	btnSync.addActionListener(this);
    	pnlMonadEditControls.add(btnSync, cn);
    	cn.gridy++;
    	
    	btnRestore=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Restore")));
    	btnRestore.setActionCommand("abort");
    	btnRestore.setToolTipText("abandon edits");
    	btnRestore.setEnabled(false);
	 	btnRestore.setPreferredSize(squareLittle);
	 	btnRestore.setBorder(BorderFactory.createEtchedBorder(0));
    	btnRestore.addActionListener(this);
    	pnlMonadEditControls.add(btnRestore, cn);
    	cn.gridy++;
    	
    	cn.gridwidth=1;
    	// btnChangeOrient constructed higher up. Just adding it to a visible panel here.
    	pnlMonadEditControls.add(btnChangeOrient, cn);
    	
    	cn.gridy++;
    	cn.weighty=1;
    	pnlMonadEditControls.add(new JLabel(), cn);
    	
    	add(pnlMonadEditControls, "West");
    }

    private void	initiateCoeffList()
    {
    	short j=0;
    	FieldDisplay tSpot;
    	switch (_repMode)
    	{
    		case REALF: 	_jCoeffs=new ArrayList<FieldDisplay>(_repMonadF.getAlgebra().getGProduct().getBladeCount());
							for (j=0; j<_repMonadF.getAlgebra().getGProduct().getBladeCount(); j++)
							{
							    tSpot = new FieldDisplay(CladosFBuilder.copyOf(_repMonadF.getCoeff(j)), this);
							    tSpot.addFocusListener(this);
							    _jCoeffs.add(j, tSpot);
							}
							break;
    		case REALD:		_jCoeffs=new ArrayList<FieldDisplay>(_repMonadD.getAlgebra().getGProduct().getBladeCount());
						    for (j=0; j<_repMonadD.getAlgebra().getGProduct().getBladeCount(); j++)
						    {
						    	tSpot = new FieldDisplay(CladosFBuilder.copyOf(_repMonadD.getCoeff(j)), this);
						    	tSpot.addFocusListener(this);
						    	_jCoeffs.add(j, tSpot);
						    }
    						break;
    		case COMPLEXF: 	_jCoeffs=new ArrayList<FieldDisplay>(_repMonadCF.getAlgebra().getGProduct().getBladeCount());
						    for (j=0; j<_repMonadCF.getAlgebra().getGProduct().getBladeCount(); j++)
						    {
						    	tSpot = new FieldDisplay(CladosFBuilder.copyOf(_repMonadCF.getCoeff(j)), this);
						    	tSpot.addFocusListener(this);
						    	_jCoeffs.add(j, tSpot);
						    }
    						break;
    		case COMPLEXD: _jCoeffs=new ArrayList<FieldDisplay>(_repMonadCD.getAlgebra().getGProduct().getBladeCount());
						    for (j=0; j<_repMonadCD.getAlgebra().getGProduct().getBladeCount(); j++)
						    {
						    	tSpot = new FieldDisplay(CladosFBuilder.copyOf(_repMonadCD.getCoeff(j)), this);
						    	tSpot.addFocusListener(this);
						    	_jCoeffs.add(j, tSpot);
						    }
    	}	
    }
    
    private void 	createManagementLayout()
    {
    	pnlMonadAlterControls=new JPanel();
    	pnlMonadAlterControls.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	pnlMonadAlterControls.setBackground(clrBackColor);
    	pnlMonadAlterControls.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn = new GridBagConstraints();
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=0;
    	cn.weighty=0;
    	cn.gridwidth=2;
    	
    	btnScaleMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Scale")));
    	btnScaleMonad.setActionCommand("scale");
    	btnScaleMonad.setToolTipText("scale() the monad");
    	btnScaleMonad.setPreferredSize(squareMedium);
    	btnScaleMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	btnScaleMonad.addActionListener(this);
    	pnlMonadAlterControls.add(btnScaleMonad, cn);
    	cn.gridy++;
    	
    	btnNormalizeMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Norm")));
    	btnNormalizeMonad.setActionCommand("normalize");
    	btnNormalizeMonad.setToolTipText("normalize THIS Monad");
    	btnNormalizeMonad.setPreferredSize(squareMedium);
    	btnNormalizeMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	btnNormalizeMonad.addActionListener(this);
    	pnlMonadAlterControls.add(btnNormalizeMonad, cn);
    	cn.gridy++;
    	
    	btnInvertMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Invert")));
    	btnInvertMonad.setActionCommand("invert");
    	btnInvertMonad.setToolTipText("invert [+/-] generators");
    	btnInvertMonad.setPreferredSize(squareMedium);
    	btnInvertMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	btnInvertMonad.addActionListener(this);
    	pnlMonadAlterControls.add(btnInvertMonad, cn);
    	cn.gridy++;
    	
    	btnReverseMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Reverse")));
    	btnReverseMonad.setActionCommand("reverse");
    	btnReverseMonad.setToolTipText("reverse [ab->ba] blades");
    	btnReverseMonad.setPreferredSize(squareMedium);
    	btnReverseMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	btnReverseMonad.addActionListener(this);
    	pnlMonadAlterControls.add(btnReverseMonad, cn);
    	cn.gridy++;
    	
    	btnDualLeft = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.DualLeft")));
    	btnDualLeft.setActionCommand("dual>");
    	btnDualLeft.setToolTipText("left Dual of the monad using algebra's PS");
    	btnDualLeft.setPreferredSize(squareMedium);
    	btnDualLeft.setBorder(BorderFactory.createEtchedBorder(0));
    	btnDualLeft.addActionListener(this);
    	pnlMonadAlterControls.add(btnDualLeft, cn);
    	cn.gridy++;
    	
    	btnDualRight = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.DualRight")));
    	btnDualRight.setActionCommand("<dual");
    	btnDualRight.setToolTipText("right Dual of the monad using algebra's PS");
    	btnDualRight.setPreferredSize(squareMedium);
    	btnDualRight.setBorder(BorderFactory.createEtchedBorder(0));
    	btnDualRight.addActionListener(this);
    	pnlMonadAlterControls.add(btnDualRight, cn);	
    	cn.gridy++;
    	
    	btnGradeCrop = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.GradeCrop")));
    	btnGradeCrop.setActionCommand("findgrade crop");
    	btnGradeCrop.setToolTipText("crop around findgrade()");
    	btnGradeCrop.setPreferredSize(squareMedium);
    	btnGradeCrop.setBorder(BorderFactory.createEtchedBorder(0));
    	btnGradeCrop.addActionListener(this);
    	pnlMonadAlterControls.add(btnGradeCrop, cn);
    	cn.gridy++;
    	
    	btnGradeCut = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.GradeCut")));
    	btnGradeCut.setActionCommand("findgrade cut");
    	btnGradeCut.setToolTipText("cut this findgrade()");
    	btnGradeCut.setPreferredSize(squareMedium);
    	btnGradeCut.setBorder(BorderFactory.createEtchedBorder(0));
    	btnGradeCut.addActionListener(this);
    	pnlMonadAlterControls.add(btnGradeCut, cn);
    	cn.gridy++;
    	
    	cn.weighty=1;
    	pnlMonadAlterControls.add(new JLabel(), cn);
    	
    	add(pnlMonadAlterControls,"East");
    }
    
    private void		createReferenceLayout()
    {
    	pnlMonadReferences=new JPanel();
    	
    	StringBuffer title = new StringBuffer("Cardinal | ");
    	switch (_repMode)
    	{
    		case REALF:		title.append(_repMonadF.getAlgebra().getFoot().getNumberType().getType());
    						break;
    		case REALD:		title.append(_repMonadD.getAlgebra().getFoot().getNumberType().getType());
							break;	
    		case COMPLEXF:	title.append(_repMonadCF.getAlgebra().getFoot().getNumberType().getType());
							break;
    		case COMPLEXD:	title.append(_repMonadCD.getAlgebra().getFoot().getNumberType().getType());
    	}
    	
    	TitledBorder tWrap = BorderFactory.createTitledBorder(	BorderFactory.createEtchedBorder(), 
																title.toString(), 
																TitledBorder.LEFT, 
																TitledBorder.DEFAULT_POSITION, 
																_PLAINFONT);
    	
    	pnlMonadReferences.setBorder(BorderFactory.createTitledBorder(tWrap));
    	pnlMonadReferences.setBackground(clrBackColor);
    	pnlMonadReferences.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn0 = new GridBagConstraints();
    	cn0.anchor=GridBagConstraints.WEST;
    	
    	cn0.gridx = 0;
    	cn0.gridy = 0;
    	cn0.weightx=0;
    	cn0.weighty=0;
    	
    	pnlMonadReferences.add(new JLabel("Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	name.setFont(_PLAINFONT);
    	cn0.weightx=1;
    	pnlMonadReferences.add(name, cn0);
    	cn0.weightx=0.25;
    	cn0.gridx++;
    	
    	cn0.weightx=0;
    	
    	pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Frame"))), cn0);
    	cn0.gridx++;
    	frame.setFont(_PLAINFONT);
    	cn0.weightx=1;
    	pnlMonadReferences.add(frame, cn0);
    	cn0.weightx=0.25;
    	cn0.gridx++;
    	
    	pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Key"))), cn0);
    	cn0.gridx++;
    	gradeKey.setFont(_PLAINFONT);
    	pnlMonadReferences.add(gradeKey, cn0);
    	
    	add(pnlMonadReferences,"South");
    	
    }
    
    private void 	makeNotWritable()
    {
    	if (pnlMonadReferences!=null)
    		pnlMonadReferences.setBackground(clrBackColor);
    	if (pnlMonadCoeffPanel!=null)
    		pnlMonadCoeffPanel.setBackground(clrBackColor);
	    name.setEditable(false);
	    
	    if (useFullPanel)
	    	for (FieldDisplay point : _jCoeffs)
	    		point.setEditable(false);
    }
    
    private void	setRepMonad()
    {
    	try
		{	
    		switch (_repMode)
    		{
    			case REALF:		if (name.getText() != _repMonadF.getName())			_repMonadF.setName(name.getText());
	    						RealF[] _repMonadCoeffsF = new RealF[_repMonadF.getAlgebra().getGProduct().getBladeCount()];
	    						for (short j=0; j<_repMonadF.getAlgebra().getGProduct().getBladeCount(); j++)
	    						{
	    							_jCoeffs.get(j).saveContents();
	    						    _repMonadCoeffsF[j]=CladosFBuilder.copyOf(_jCoeffs.get(j).displayFieldRF);
	    						}
	    						_repMonadF.setCoeff(_repMonadCoeffsF);
	    						gradeKey.setText(new StringBuffer().append(_repMonadF.getGradeKey()).toString());
	    						break;
    			case REALD:		if (name.getText() != _repMonadD.getName())			_repMonadD.setName(name.getText());
								RealD[] _repMonadCoeffsD = new RealD[_repMonadD.getAlgebra().getGProduct().getBladeCount()];
								for (short k=0; k<_repMonadD.getAlgebra().getGProduct().getBladeCount(); k++)
								{
									_jCoeffs.get(k).saveContents();
								    _repMonadCoeffsD[k]=CladosFBuilder.copyOf(_jCoeffs.get(k).displayFieldRD);
								}
								_repMonadD.setCoeff(_repMonadCoeffsD);
								gradeKey.setText(new StringBuffer().append(_repMonadD.getGradeKey()).toString());
								break;
    			case COMPLEXF:	if (name.getText() != _repMonadCF.getName())		_repMonadCF.setName(name.getText());
							    ComplexF[] _repMonadCoeffsCF = new ComplexF[_repMonadCF.getAlgebra().getGProduct().getBladeCount()];
							    for (short i=0; i<_repMonadCF.getAlgebra().getGProduct().getBladeCount(); i++)
							    {
							        _jCoeffs.get(i).saveContents();
							        _repMonadCoeffsCF[i]=CladosFBuilder.copyOf(_jCoeffs.get(i).displayFieldCF);
							    }
							    _repMonadCF.setCoeff(_repMonadCoeffsCF);
							    gradeKey.setText(new StringBuffer().append(_repMonadCF.getGradeKey()).toString());
							    break;
    			case COMPLEXD:	if (name.getText() != _repMonadCD.getName())		_repMonadCD.setName(name.getText());
							    ComplexD[] _repMonadCoeffsCD = new ComplexD[_repMonadCD.getAlgebra().getGProduct().getBladeCount()];
							    for (short m=0; m<_repMonadCD.getAlgebra().getGProduct().getBladeCount(); m++)
							    {
							        _jCoeffs.get(m).saveContents();
							        _repMonadCoeffsCD[m]=CladosFBuilder.copyOf(_jCoeffs.get(m).displayFieldCD);
							    }
							    _repMonadCD.setCoeff(_repMonadCoeffsCD);
							    gradeKey.setText(new StringBuffer().append(_repMonadCD.getGradeKey()).toString());
    		}
		}
    	catch (CladosMonadException e) 
    	{
			_GUI._StatusBar.setStatusMsg("Could not set at least one of the edited coefficients.\n");
			_GUI._StatusBar.setStatusMsg(e.getSourceMessage()+"\n");
		}     		
    }
    
    private void 	setReferences()
    {
    	switch (_repMode)
    	{
    	case REALF:		name.setText(_repMonadF.getName());
				    	aname.setText(_repMonadF.getAlgebra().getAlgebraName());
				    	sig.setText(_repMonadF.getAlgebra().getGProduct().getSignature());
				    	frame.setText(_repMonadF.getFrameName());
				    	foot.setText(_repMonadF.getAlgebra().getFoot().getFootName());
				    	gradeKey.setText(new StringBuffer().append(_repMonadF.getGradeKey()).toString());
				    	break;
    	case REALD:		name.setText(_repMonadD.getName());
				    	aname.setText(_repMonadD.getAlgebra().getAlgebraName());
				    	sig.setText(_repMonadD.getAlgebra().getGProduct().getSignature());
				    	frame.setText(_repMonadD.getFrameName());
				    	foot.setText(_repMonadD.getAlgebra().getFoot().getFootName());
				    	gradeKey.setText(new StringBuffer().append(_repMonadD.getGradeKey()).toString());
				    	break;
    	case COMPLEXF:	name.setText(_repMonadCF.getName());
				    	aname.setText(_repMonadCF.getAlgebra().getAlgebraName());
				    	sig.setText(_repMonadCF.getAlgebra().getGProduct().getSignature());
				    	frame.setText(_repMonadCF.getFrameName());
				    	foot.setText(_repMonadCF.getAlgebra().getFoot().getFootName());
				    	gradeKey.setText(new StringBuffer().append(_repMonadCF.getGradeKey()).toString());
				    	break;
    	case COMPLEXD:	name.setText(_repMonadCD.getName());
				    	aname.setText(_repMonadCD.getAlgebra().getAlgebraName());
				    	sig.setText(_repMonadCD.getAlgebra().getGProduct().getSignature());
				    	frame.setText(_repMonadCD.getFrameName());
				    	foot.setText(_repMonadCD.getAlgebra().getFoot().getFootName());
				    	gradeKey.setText(new StringBuffer().append(_repMonadCD.getGradeKey()).toString());
    	}
    }
    /**
     * This method adjusts the JTextArea elements contained on the panel to allow for edits.
     * It has two modes since this panel does too. In one mode the coefficients are visible 
     * and made editable too. In the other, they aren't visible, so this method skips them.
     */
    protected void 	makeWritable()
    {
    	if (pnlMonadReferences!=null)
    		pnlMonadReferences.setBackground(clrUnlockColor);
    	if (pnlMonadCoeffPanel!=null)
    		pnlMonadCoeffPanel.setBackground(clrUnlockColor);
    	
	    name.setEditable(true);
	    if (useFullPanel)
		    for (FieldDisplay point : _jCoeffs)
		    	point.setEditable(true);
	    
    }    	
}