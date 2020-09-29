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

import com.interworldtransport.cladosF.ComplexD;
import com.interworldtransport.cladosF.ComplexF;
import com.interworldtransport.cladosF.DivField;
import com.interworldtransport.cladosF.RealD;
import com.interworldtransport.cladosF.RealF;

import com.interworldtransport.cladosG.MonadComplexD;
import com.interworldtransport.cladosG.MonadComplexF;
import com.interworldtransport.cladosG.MonadRealD;
import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosGExceptions.*;

import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;

import java.util.*;

/** com.interworldtransport.cladosviewer.MonadPanel
 * The MonadPanel class directly handles the gui for a single Monad.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class MonadPanel extends JPanel implements ActionListener, FocusListener
{
	public static final			int								COEFF_SIZE = 10;
	public static final			String							ORIENT_HORIZONTAL = "Horizontal";
	public static final			String							ORIENT_VERTICAL = "Vertical";
	
	public						CladosCalculator				_GUI;
	private		final			Color							_backColor = new Color(212, 212, 192);
	private						ArrayList<FieldDisplayArea>		_jCoeffs;
	private						JPanel 							_monadCoeffPanel;
	private						JPanel 							_monadReferences;
	private						String							_repMode;
	private						MonadComplexD					_repMonadCD;
	private						MonadComplexF					_repMonadCF;
	private						MonadRealD						_repMonadD;
	private						MonadRealF						_repMonadF;
	private		final			Color							_unlockColor = new Color(255, 192, 192);
	private						JPanel 							monadAlterControls;
	private						JPanel 							monadEditControls;
	private						String							orient;
	private		final			Dimension						squareLittle=new Dimension(25,25);
	private		final			Dimension						squareMedium=new Dimension(28,28);
	/*
	 * This boolean is for knowing whether to render the coefficients or not
	 * This panel doubles up as a create dialog when no coefficients array exists.
	 * It can't exist until after the signature is given.
	 */
	private						boolean							useFullPanel;
	protected					JTextField						aname=new JTextField(10);
	protected					JButton							btnEdit;
	protected					JButton							btnRestore;
	protected					JButton							btnSync;
	protected					JButton							changeOrient;
	protected					JButton							dualLeft;
	protected					JButton							dualRight;
	protected					JTextField						foot=new JTextField(10);
	protected					JTextField						frame=new JTextField(16);
	protected					JButton							gradeCrop;
	protected					JButton							gradeCut;
	protected					JTextField						gradeKey=new JTextField(10);
	protected					ImageIcon						iconHorizontal;
	protected					ImageIcon						iconVertical;
	protected					JButton							invertMonad; //This is NOT multiplicative inverse
	protected					JTextField						name=new JTextField(16);
	protected					JButton							normalizeMonad;
	protected					JButton							reverseMonad;
	protected					JButton							scaleMonad;
	protected					JTextField						sig=new JTextField(10);

  /**
  * The MonadPanel class is intended to be contain a cladosG Monad in order to offer its parts
  * for display and manipulation by the calculator
  * 
  * @param pGUI				CladosCalculator
  * @throws UtilitiesException
  * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
  */
   public MonadPanel(CladosCalculator pGUI) throws UtilitiesException
    {
	   super();
	   useFullPanel=false;	// Use this panel in it's small sense
	   if (pGUI==null)
		   throw new UtilitiesException("A GUI must be passed to a MonadPanel");
	   _GUI=pGUI;
	   
	   name.setText("tablePlace");
	   aname.setText(_GUI.IniProps.getProperty("Desktop.Default.AlgebraName"));
	   frame.setText(_GUI.IniProps.getProperty("Desktop.Default.FrameName"));
	   foot.setText(_GUI.IniProps.getProperty("Desktop.Default.FootName"));
	   sig.setText(_GUI.IniProps.getProperty("Desktop.Default.Sig"));	
	   
	   setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	   setBackground(_backColor);
	   setLayout(new BorderLayout());
	   orient=_GUI.IniProps.getProperty("Desktop.MVRender");
	   
	   createMinReferenceLayout();
    }

   /**
   * The MonadPanel class is intended to hold a single Monad and act as its GUI.
   * This constructor is the base one.
   * @param pGUI			
   * 	CladosCalculator
   * This is just a reference to the owner application so error messages can be presented.
   * @param pM
   * 	MonadComplexD
   * This is a reference to the monad to be displayed and manipulated.
   * @throws UtilitiesException
   * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
   */
     public MonadPanel(		CladosCalculator pGUI,
     						MonadComplexD pM)
     			throws 		UtilitiesException			
     {
    	super();
       	useFullPanel=true;
       	
       	if (pGUI==null)
       		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
       	_GUI=pGUI;
       	
       	if (pM==null)
       		throw new UtilitiesException("A Monad must be passed to this MonadPanel constructor");
       	_repMonadCD=pM;
       	_repMode=DivField.COMPLEXD;
       	
       	orient=_GUI.IniProps.getProperty("Desktop.MVRender");
       	iconHorizontal=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
       	iconVertical=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
    
        setReferences();
           		
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        setBackground(_backColor);
        setLayout(new BorderLayout());
          
        createCoeffLayout();
        createReferenceLayout();
        createEditLayout();
        createManagementLayout();
     }
    /**
        * The MonadPanel class is intended to hold a single Monad and act as its GUI.
        * This constructor is the base one.
        * @param pGUI			
        * 	CladosCalculator
        * This is just a reference to the owner application so error messages can be presented.
        * @param pM
        * 	MonadComplexF
        * This is a reference to the monad to be displayed and manipulated.
        * @throws UtilitiesException
        * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
        */
          public MonadPanel(	CladosCalculator pGUI,
          						MonadComplexF pM)
          			throws 		UtilitiesException			
          {
   	       	super();
   	       	useFullPanel=true;
   	       	
   	       	if (pGUI==null)
   	       		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
   	       	_GUI=pGUI;
   	       	
   	       	if (pM==null)
   	       		throw new UtilitiesException("A Monad must be passed to this MonadPanel constructor");
   	       	_repMonadCF=pM;
   	       	_repMode=DivField.COMPLEXF;
   	       	
   	       	orient=_GUI.IniProps.getProperty("Desktop.MVRender");
   	       	iconHorizontal=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
   	       	iconVertical=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
        
   	        setReferences();
   	           		
   	        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
   	        setBackground(_backColor);
   	        setLayout(new BorderLayout());
   	          
   	        createCoeffLayout();
   	        createReferenceLayout();
   	        createEditLayout();
   	        createManagementLayout();
          }
       /**
     * The MonadPanel class is intended to hold a single Monad and act as its GUI.
     * This constructor is the base one.
     * @param pGUI			
     * 	CladosCalculator
     * This is just a reference to the owner application so error messages can be presented.
     * @param pM
     * 	MonadRealD
     * This is a reference to the monad to be displayed and manipulated.
     * @throws UtilitiesException
     * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
     */
       public MonadPanel(	CladosCalculator pGUI,
       						MonadRealD pM)
       			throws 		UtilitiesException			
       {
	       	super();
	       	useFullPanel=true;
	       	
	       	if (pGUI==null)
	       		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
	       	_GUI=pGUI;
	       	
	       	if (pM==null)
	       		throw new UtilitiesException("A Monad must be passed to this MonadPanel constructor");
	       	_repMonadD=pM;
	       	_repMode=DivField.REALD;
	       	
	       	orient=_GUI.IniProps.getProperty("Desktop.MVRender");
	       	iconHorizontal=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
	       	iconVertical=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
     
	        setReferences();
	           		
	        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	        setBackground(_backColor);
	        setLayout(new BorderLayout());
	          
	        createCoeffLayout();
	        createReferenceLayout();
	        createEditLayout();
	        createManagementLayout();
       }
          /**
		    * The MonadPanel class is intended to hold a single Monad and act as its GUI.
		    * This constructor is the base one.
		    * @param pGUI			
		    * 	CladosCalculator
		    * This is just a reference to the owner application so error messages can be presented.
		    * @param pM
		    * 	MonadRealF
		    * This is a reference to the monad to be displayed and manipulated.
		    * @throws UtilitiesException
		    * This is the general exception. Could be any miscellaneous issue. Ready the message to see. 
		    */
		    public MonadPanel(	CladosCalculator pGUI,
		    					MonadRealF pM)
		    		throws 		UtilitiesException			
		    {
		    	super();
		    	useFullPanel=true;
		    	
		    	if (pGUI==null)
		    		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
		    	_GUI=pGUI;
		    	
		    	if (pM==null)
		    		throw new UtilitiesException("A Monad must be passed to this MonadPanel constructor");
		    	_repMonadF=pM;
		    	_repMode=DivField.REALF;
		    	
		    	orient=_GUI.IniProps.getProperty("Desktop.MVRender");
		    	iconHorizontal=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
		    	iconVertical=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
		   
		        setReferences();
		        		
		        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		        setBackground(_backColor);
		        setLayout(new BorderLayout());
		       
		        createCoeffLayout();
		        createReferenceLayout();
		        createEditLayout();
		        createManagementLayout();
		    }
    
    @Override
    public void 	actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	//System.out.println("MonadPanel says "+command);
    	
    	if (command == "grdXbld") //Horizontal layout call
    	{
    		
    		try 
    		{
    			orient="Horizontal";
				createCoeffLayout();
				changeOrient.setIcon(iconHorizontal);
	        	changeOrient.setActionCommand("bldXgrd");
	        	changeOrient.setToolTipText("Monad grades as rows");
	    		validate();
	    		_GUI.pack();
			} 
    		catch (UtilitiesException e) 
    		{
				_GUI._StatusBar.setStatusMsg("\t\tfailed to establish magnitude layout on orientation switch.\n");
			}
    	}
    	
    	if (command == "bldXgrd") //Vertical layout call
    	{
    		try 
    		{
    			orient="Vertical";
				createCoeffLayout();
				changeOrient.setIcon(iconVertical);
	        	changeOrient.setActionCommand("grdXbld");
	        	changeOrient.setToolTipText("Monad grades as columns");
	    		validate();
	    		_GUI.pack();
			} 
    		catch (UtilitiesException e) 
    		{
    			_GUI._StatusBar.setStatusMsg("\t\tfailed to establish magnitude layout on orientation switch.\n");
			}
    	}
    	
    	if (command == "save")
    	{
    		//	Reset internal details of the represented Monad to reflect what shows in the MonadPanel
    		//	This includes all possible changes, but a check is made for everything except the coefficients
    		//	in case changes were NOT made. [It is assumed most changes will be Coefficients.]
  
    		//switch starts here, but pull the gradeKey setter in here. See below.
    		try
    		{	
	    		switch (_repMode)
	    		{
	    			case DivField.REALF:	if (name.getText() != _repMonadF.getName())			_repMonadF.setName(name.getText());
	    						    		if (frame.getText() != _repMonadF.getFrameName())	_repMonadF.setFrameName(frame.getText());
	    						    		RealF[] _repMonadCoeffsF = new RealF[_repMonadF.getAlgebra().getGProduct().getBladeCount()];
	    						    		for (short j=0; j<_repMonadF.getAlgebra().getGProduct().getBladeCount(); j++)
	    						        	{
	    						        		_jCoeffs.get(j).saveContents();
	    						        		_repMonadCoeffsF[j]=RealF.copyOf(_jCoeffs.get(j).displayFieldRF);
	    						        	}
	    						    		_repMonadF.setCoeff(_repMonadCoeffsF);
	    						        	gradeKey.setText(new StringBuffer().append(_repMonadF.getGradeKey()).toString());
	    						        	//_GUI._StatusBar.setStatusMsg(MonadRealF.toXMLFullString(_repMonadF));
	    						    		break;
	    			case DivField.REALD:	if (name.getText() != _repMonadD.getName())			_repMonadD.setName(name.getText());
								    		if (frame.getText() != _repMonadD.getFrameName())	_repMonadD.setFrameName(frame.getText());
								    		RealD[] _repMonadCoeffsD = new RealD[_repMonadD.getAlgebra().getGProduct().getBladeCount()];
								    		for (short k=0; k<_repMonadD.getAlgebra().getGProduct().getBladeCount(); k++)
								        	{
								        		_jCoeffs.get(k).saveContents();
								        		_repMonadCoeffsD[k]=RealD.copyOf(_jCoeffs.get(k).displayFieldRD);
								        	}
								    		_repMonadD.setCoeff(_repMonadCoeffsD);
								        	gradeKey.setText(new StringBuffer().append(_repMonadD.getGradeKey()).toString());
								        	//_GUI._StatusBar.setStatusMsg(MonadRealD.toXMLFullString(_repMonadD));
								    		break;
	    			case DivField.COMPLEXF:	if (name.getText() != _repMonadCF.getName())		_repMonadCF.setName(name.getText());
								    		if (frame.getText() != _repMonadCF.getFrameName())	_repMonadCF.setFrameName(frame.getText());
								    		ComplexF[] _repMonadCoeffsCF = new ComplexF[_repMonadCF.getAlgebra().getGProduct().getBladeCount()];
								    		for (short i=0; i<_repMonadCF.getAlgebra().getGProduct().getBladeCount(); i++)
								        	{
								        		_jCoeffs.get(i).saveContents();
								        		_repMonadCoeffsCF[i]=ComplexF.copyOf(_jCoeffs.get(i).displayFieldCF);
								        	}
								    		_repMonadCF.setCoeff(_repMonadCoeffsCF);
								        	gradeKey.setText(new StringBuffer().append(_repMonadCF.getGradeKey()).toString());
								        	//_GUI._StatusBar.setStatusMsg(MonadComplexF.toXMLFullString(_repMonadCF));
								    		break;
	    			case DivField.COMPLEXD:	if (name.getText() != _repMonadCD.getName())		_repMonadCD.setName(name.getText());
								    		if (frame.getText() != _repMonadCD.getFrameName())	_repMonadCD.setFrameName(frame.getText());
								    		ComplexD[] _repMonadCoeffsCD = new ComplexD[_repMonadCD.getAlgebra().getGProduct().getBladeCount()];
								    		for (short m=0; m<_repMonadCD.getAlgebra().getGProduct().getBladeCount(); m++)
								        	{
								        		_jCoeffs.get(m).saveContents();
								        		_repMonadCoeffsCD[m]=ComplexD.copyOf(_jCoeffs.get(m).displayFieldCD);
								        	}
								    		_repMonadCD.setCoeff(_repMonadCoeffsCD);
								        	gradeKey.setText(new StringBuffer().append(_repMonadCD.getGradeKey()).toString());
								        	//_GUI._StatusBar.setStatusMsg(MonadComplexD.toXMLFullString(_repMonadCD));
	    		}
    		}
        	catch (CladosMonadException e) 
        	{
    			_GUI._StatusBar.setStatusMsg(e.getSourceMessage()+"\n");
    			_GUI._StatusBar.setStatusMsg("Could not set at least one of the edited coefficients.\n");
    		} 
    		catch (UtilitiesException eu) 
        	{
    			_GUI._StatusBar.setStatusMsg(eu.getSourceMessage()+"\n");
    			_GUI._StatusBar.setStatusMsg("Could not set at least one of the edited coefficients.\n");
    		} 
    		
    		command=".edit.";
    	}
    	
    	if (command == "abort")
    	{
    		setReferences();
    		setCoefficientDisplay();
    		command=".edit.";
    	}
    	
    	if (command == ".edit.")
    	{
    		btnEdit.setActionCommand("edit");
    		btnEdit.setToolTipText("start edits");
    		btnSync.setEnabled(false);
        	btnRestore.setEnabled(false);
    	   	makeNotWritable();
    	}
    	
    	if (command == "edit")
    	{
    		btnEdit.setActionCommand(".edit.");
    		btnEdit.setToolTipText("end edits w/o save");
    		btnSync.setEnabled(true);
        	btnRestore.setEnabled(true);
    		makeWritable();
    	}
    	
    	if (command == "grade crop")
    	{
    		_GUI._EventModel.SOpsParts.gradep.actionPerformed(event);
    	}
    	
    	if (command == "grade cut")
    	{
    		_GUI._EventModel.SOpsParts.grades.actionPerformed(event);
    	}
    	
    	if (command == "scale")
    	{
    		_GUI._EventModel.SOpsParts.scale.actionPerformed(event);
    	}
    	
    	if (command == "normalize")
    	{
    		_GUI._EventModel.SOpsParts.norm.actionPerformed(event);
    	}
    	
    	if (command == "invert")
    	{
    		_GUI._EventModel.SOpsParts.invt.actionPerformed(event);
    	}
    	
    	if (command == "reverse")
    	{
    		_GUI._EventModel.SOpsParts.rev.actionPerformed(event);
    	}
    	
    	if (command == "<dual")
    	{
    		_GUI._EventModel.SOpsParts.dualRight.actionPerformed(event);
    	}
    	
    	if (command == "dual>")
    	{
    		_GUI._EventModel.SOpsParts.dualLeft.actionPerformed(event);
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
    	if (e.getComponent() instanceof JTextArea)
    	{
    		JTextArea tSpot = (JTextArea) e.getComponent();
    		StringBuilder strB = new StringBuilder(tSpot.getText());
    		
			int tBufferLength = strB.length();
			if (tBufferLength == 0 ) return; // Nothing to save, so surrender.
			int indexOfR = strB.indexOf("[R]")+3;
			int indexOfI = strB.indexOf("[I]")+3;
			
			switch (_repMode)
			{
				case DivField.REALF:	float tSpotRF = Float.parseFloat(strB.substring(indexOfR, tBufferLength));
										_GUI._FieldBar.setWhatFloatR(tSpotRF);
										break;
				case DivField.REALD:	double tSpotRD = Double.parseDouble(strB.substring(indexOfR, tBufferLength));
										_GUI._FieldBar.setWhatDoubleR(tSpotRD);
										break;
				case DivField.COMPLEXF:	float tSpotCF1 = Float.parseFloat(strB.substring(indexOfR, indexOfI-4));
										float tSpotCF2 = Float.parseFloat(strB.substring(indexOfI, tBufferLength));
										_GUI._FieldBar.setWhatFloatR(tSpotCF1);
										_GUI._FieldBar.setWhatFloatI(tSpotCF2);
										break;
				case DivField.COMPLEXD:	double tSpotCD1 = Double.parseDouble(strB.substring(indexOfR, indexOfI-4));
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
    public ArrayList<FieldDisplayArea> getJCoeffs()
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
    
    public String 		getRepMode()
    {
    	return _repMode;
    }
/**
 * In this method we assume the underlying Monad has changed and the displayField in the panel
 * has to be updated. This means working through the Coeff's for the Monad and updating the
 * displayField in the related JTextArea.
 * 
 * It is safe enough to call this from anywhere and at any time. The worst that can happen is
 * the user looses some of their changes on the UI.
 */
    public void 	setCoefficientDisplay()
    {
    	try 
    	{
    		short j=0;
    		switch (_repMode)
    		{	
	    		case DivField.REALF: 	for (j=0; j<_repMonadF.getAlgebra().getGProduct().getBladeCount(); j++)
							    		{
											_jCoeffs.get(j).updateField(_repMonadF.getCoeff(j));	//	fodder for the update
											_jCoeffs.get(j).displayContents();
							    		}
	    								gradeKey.setText(new StringBuffer().append(_repMonadF.getGradeKey()).toString());
	    								break;
	    		case DivField.REALD: 	for (j=0; j<_repMonadD.getAlgebra().getGProduct().getBladeCount(); j++)
							    		{
											_jCoeffs.get(j).updateField(_repMonadD.getCoeff(j));	//	fodder for the update
											_jCoeffs.get(j).displayContents();
							    		}
										gradeKey.setText(new StringBuffer().append(_repMonadD.getGradeKey()).toString());
	    								break;
	    		case DivField.COMPLEXF:	for (j=0; j<_repMonadCF.getAlgebra().getGProduct().getBladeCount(); j++)
							    		{
											_jCoeffs.get(j).updateField(_repMonadCF.getCoeff(j));	//	fodder for the update
											_jCoeffs.get(j).displayContents();
							    		}
										gradeKey.setText(new StringBuffer().append(_repMonadCF.getGradeKey()).toString());
										break;
				case DivField.COMPLEXD:	for (j=0; j<_repMonadCD.getAlgebra().getGProduct().getBladeCount(); j++)
							    		{
											_jCoeffs.get(j).updateField(_repMonadCD.getCoeff(j));	//	fodder for the update
											_jCoeffs.get(j).displayContents();
							    		}
										gradeKey.setText(new StringBuffer().append(_repMonadCD.getGradeKey()).toString());
    		}
    	}
	    catch (UtilitiesException e) 
	    {
    		_GUI._StatusBar.setStatusMsg(e.getSourceMessage()+"\n");
    		_GUI._StatusBar.setStatusMsg("Could not update at least one of the possibly new coefficients.\n");
		} 
    	catch (BadLocationException e) 
    	{
    		_GUI._StatusBar.setStatusMsg(e.getMessage()+"\n");
    		_GUI._StatusBar.setStatusMsg("Could not parse at least one of the new coefficients.\n");
		}
    }

    private void 	createCoeffLayout() 
    		throws UtilitiesException	// Toss it upstream to the constructor to be handed there.
    {		
    	if (_jCoeffs == null)			// First time? Create the ArrayList
    		createInitCoeffList();		// Listeners get added here the first time and need not be reset
    									// because the panels that display them are just containers... not handlers.
    	
    	if (_monadCoeffPanel != null)
    		remove(_monadCoeffPanel);
    		
	    _monadCoeffPanel=new JPanel();
	    _monadCoeffPanel.setBorder(BorderFactory.createTitledBorder("ct, x, y, z, ..."));
	    _monadCoeffPanel.setBackground(_backColor);
	    _monadCoeffPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn1 = new GridBagConstraints();
    	cn1.insets = new Insets(0, 0, 0, 0);
    		
    	cn1.gridx = 0;
    	cn1.gridy = 0;
    	cn1.weightx=0;
    	cn1.weighty=0;
    	cn1.ipadx=0;
    	cn1.ipady=0;
    		
    	if (orient.equals(MonadPanel.ORIENT_VERTICAL))
    	{
    		short j=0;
    		short k=0;
    		JLabel headLabel;
    		short[] tSpot;
    		switch (_repMode)
    		{
    			case DivField.REALF:	for (j=0; j<_repMonadF.getAlgebra().getGProduct().getGradeCount(); j++)
						        		{
						        			headLabel = new JLabel(j+"-blades", SwingConstants.CENTER);
						        			headLabel.setFont(new Font(Font.SERIF, Font.PLAIN, MonadPanel.COEFF_SIZE));
						        			_monadCoeffPanel.add(headLabel, cn1);
						        			cn1.gridy++;
						        			
						        			tSpot = _repMonadF.getAlgebra().getGProduct().getGradeRange(j);
						        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
						        			{
						        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						        				_monadCoeffPanel.add(_jCoeffs.get(k), cn1);
						        				cn1.gridy++;
						        			}
						        			cn1.gridx++;
						        			cn1.gridy=0;
						        		}
    									break;
    			case DivField.REALD:	for (j=0; j<_repMonadD.getAlgebra().getGProduct().getGradeCount(); j++)
						        		{
						        			headLabel = new JLabel(j+"-blades", SwingConstants.CENTER);
						        			headLabel.setFont(new Font(Font.SERIF, Font.PLAIN, MonadPanel.COEFF_SIZE));
						        			_monadCoeffPanel.add(headLabel, cn1);
						        			cn1.gridy++;
						        			
						        			tSpot = _repMonadD.getAlgebra().getGProduct().getGradeRange(j);
						        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
						        			{
						        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						        				_monadCoeffPanel.add(_jCoeffs.get(k), cn1);
						        				cn1.gridy++;
						        			}
						        			cn1.gridx++;
						        			cn1.gridy=0;
						        		}
    									break;
    			case DivField.COMPLEXF:	for (j=0; j<_repMonadCF.getAlgebra().getGProduct().getGradeCount(); j++)
						        		{
						        			headLabel = new JLabel(j+"-blades", SwingConstants.CENTER);
						        			headLabel.setFont(new Font(Font.SERIF, Font.PLAIN, MonadPanel.COEFF_SIZE));
						        			_monadCoeffPanel.add(headLabel, cn1);
						        			cn1.gridy++;
						        			
						        			tSpot = _repMonadCF.getAlgebra().getGProduct().getGradeRange(j);
						        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
						        			{
						        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						        				_monadCoeffPanel.add(_jCoeffs.get(k), cn1);
						        				cn1.gridy++;
						        			}
						        			cn1.gridx++;
						        			cn1.gridy=0;
						        		}
    									break;
    			case DivField.COMPLEXD:	for (j=0; j<_repMonadCD.getAlgebra().getGProduct().getGradeCount(); j++)
						        		{
						        			headLabel = new JLabel(j+"-blades", SwingConstants.CENTER);
						        			headLabel.setFont(new Font(Font.SERIF, Font.PLAIN, MonadPanel.COEFF_SIZE));
						        			_monadCoeffPanel.add(headLabel, cn1);
						        			cn1.gridy++;
						        			
						        			tSpot = _repMonadCD.getAlgebra().getGProduct().getGradeRange(j);
						        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
						        			{
						        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						        				_monadCoeffPanel.add(_jCoeffs.get(k), cn1);
						        				cn1.gridy++;
						        			}
						        			cn1.gridx++;
						        			cn1.gridy=0;
						        		}
    		}
    		
    	}
    	if (orient.equals(MonadPanel.ORIENT_HORIZONTAL))
    	{
    		short j=0;
    		short k=0;
    		JLabel headLabel;
    		short[] tSpot;
    		switch (_repMode)
    		{
    			case DivField.REALF:	for (j=0; j<_repMonadF.getAlgebra().getGProduct().getGradeCount(); j++)
						        		{
						        			headLabel = new JLabel(j+"-blades", SwingConstants.RIGHT);
						        			headLabel.setFont(new Font(Font.SERIF, Font.PLAIN, MonadPanel.COEFF_SIZE));
						        			_monadCoeffPanel.add(headLabel, cn1);
						        			cn1.gridx++;
						        			
						        			tSpot = _repMonadF.getAlgebra().getGProduct().getGradeRange(j);
						        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
						        			{
						        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						        				_monadCoeffPanel.add(_jCoeffs.get(k), cn1);
						        				cn1.gridx++;
						        			}
						        			cn1.gridx=0;
						        			cn1.gridy++;
						        		}
    									break;
    			case DivField.REALD:	for (j=0; j<_repMonadD.getAlgebra().getGProduct().getGradeCount(); j++)
						        		{
						        			headLabel = new JLabel(j+"-blades", SwingConstants.RIGHT);
						        			headLabel.setFont(new Font(Font.SERIF, Font.PLAIN, MonadPanel.COEFF_SIZE));
						        			_monadCoeffPanel.add(headLabel, cn1);
						        			cn1.gridx++;
						        			
						        			tSpot = _repMonadD.getAlgebra().getGProduct().getGradeRange(j);
						        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
						        			{
						        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						        				_monadCoeffPanel.add(_jCoeffs.get(k), cn1);
						        				cn1.gridx++;
						        			}
						        			cn1.gridx=0;
						        			cn1.gridy++;
						        		}
    									break;
    			case DivField.COMPLEXF:	for (j=0; j<_repMonadCF.getAlgebra().getGProduct().getGradeCount(); j++)
						        		{
						        			headLabel = new JLabel(j+"-blades", SwingConstants.RIGHT);
						        			headLabel.setFont(new Font(Font.SERIF, Font.PLAIN, MonadPanel.COEFF_SIZE));
						        			_monadCoeffPanel.add(headLabel, cn1);
						        			cn1.gridx++;
						        			
						        			tSpot = _repMonadCF.getAlgebra().getGProduct().getGradeRange(j);
						        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
						        			{
						        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						        				_monadCoeffPanel.add(_jCoeffs.get(k), cn1);
						        				cn1.gridx++;
						        			}
						        			cn1.gridx=0;
						        			cn1.gridy++;
						        		}
    									break;
    			case DivField.COMPLEXD:	for (j=0; j<_repMonadCD.getAlgebra().getGProduct().getGradeCount(); j++)
						        		{
						        			headLabel = new JLabel(j+"-blades", SwingConstants.RIGHT);
						        			headLabel.setFont(new Font(Font.SERIF, Font.PLAIN, MonadPanel.COEFF_SIZE));
						        			_monadCoeffPanel.add(headLabel, cn1);
						        			cn1.gridx++;
						        			
						        			tSpot = _repMonadCD.getAlgebra().getGProduct().getGradeRange(j);
						        			for (k=tSpot[0]; k<tSpot[1]+1; k++)
						        			{
						        				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						        				_monadCoeffPanel.add(_jCoeffs.get(k), cn1);
						        				cn1.gridx++;
						        			}
						        			cn1.gridx=0;
						        			cn1.gridy++;
						        		}
    		}
    	}
    	add(_monadCoeffPanel, "Center");
    	setCoefficientDisplay();
    }
    
    private void		createEditLayout()
    {
    	monadEditControls=new JPanel();
    	monadEditControls.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	monadEditControls.setBackground(_backColor);
    	monadEditControls.setLayout(new GridBagLayout());
    	
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
    	monadEditControls.add(btnEdit, cn);
    	cn.gridy++;
    	
    	btnSync=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Save")));
    	btnSync.setActionCommand("save");
    	btnSync.setToolTipText("save edits");
	 	btnSync.setEnabled(false);
	 	btnSync.setPreferredSize(squareLittle);
	 	btnSync.setBorder(BorderFactory.createEtchedBorder(0));
	 	btnSync.addActionListener(this);
    	monadEditControls.add(btnSync, cn);
    	cn.gridy++;
    	
    	btnRestore=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Restore")));
    	btnRestore.setActionCommand("abort");
    	btnRestore.setToolTipText("abandon edits");
    	btnRestore.setEnabled(false);
	 	btnRestore.setPreferredSize(squareLittle);
	 	btnRestore.setBorder(BorderFactory.createEtchedBorder(0));
    	btnRestore.addActionListener(this);
    	monadEditControls.add(btnRestore, cn);
    	cn.gridy++;
    	
    	cn.gridwidth=1;
    	
    	changeOrient = new JButton();
    	changeOrient.setToolTipText("turn coefficient representation");
    	changeOrient.setPreferredSize(squareLittle);
    	changeOrient.setBorder(BorderFactory.createEtchedBorder(0));
    	if (orient.equals("Horizontal"))
    	{
    		changeOrient.setIcon(iconHorizontal);
    		changeOrient.setActionCommand("bldXgrd");
    		changeOrient.setToolTipText("Monad grades as rows");
    	}
    	else
    	{	
    		changeOrient.setIcon(iconVertical);
    		changeOrient.setActionCommand("grdXbld");
    		changeOrient.setToolTipText("Monad grades as columns");
    	}
    	changeOrient.addActionListener(this);
    	monadEditControls.add(changeOrient, cn);
    	cn.gridy++;
    	
    	cn.weighty=1;
    	monadEditControls.add(new JLabel(), cn);
    	
    	add(monadEditControls, "West");
    }

    private void	createInitCoeffList() 
    		throws UtilitiesException // Just toss it upstream to be considered there.
    {
    	
    		short j=0;
    		FieldDisplayArea tSpot;
    		switch (_repMode)
    		{
    			case DivField.REALF: 	_jCoeffs=new ArrayList<FieldDisplayArea>(_repMonadF.getAlgebra().getGProduct().getBladeCount());
						    			for (j=0; j<_repMonadF.getAlgebra().getGProduct().getBladeCount(); j++)
						    	    	{
						    	    		tSpot = new FieldDisplayArea(RealF.copyOf(_repMonadF.getCoeff(j)));
						    	    		tSpot.addFocusListener(this);
						    	    		_jCoeffs.add(j, tSpot);
						    	    	}
    									break;
    			case DivField.REALD:	_jCoeffs=new ArrayList<FieldDisplayArea>(_repMonadD.getAlgebra().getGProduct().getBladeCount());
						    			for (j=0; j<_repMonadD.getAlgebra().getGProduct().getBladeCount(); j++)
						    	    	{
						    	    		tSpot = new FieldDisplayArea(RealD.copyOf(_repMonadD.getCoeff(j)));
						    	    		tSpot.addFocusListener(this);
						    	    		_jCoeffs.add(j, tSpot);
						    	    	}
    									break;
    			case DivField.COMPLEXF: _jCoeffs=new ArrayList<FieldDisplayArea>(_repMonadCF.getAlgebra().getGProduct().getBladeCount());
						    			for (j=0; j<_repMonadCF.getAlgebra().getGProduct().getBladeCount(); j++)
						    	    	{
						    	    		tSpot = new FieldDisplayArea(ComplexF.copyOf(_repMonadCF.getCoeff(j)));
						    	    		tSpot.addFocusListener(this);
						    	    		_jCoeffs.add(j, tSpot);
						    	    	}
    									break;
    			case DivField.COMPLEXD: _jCoeffs=new ArrayList<FieldDisplayArea>(_repMonadCD.getAlgebra().getGProduct().getBladeCount());
						    			for (j=0; j<_repMonadCD.getAlgebra().getGProduct().getBladeCount(); j++)
						    	    	{
						    	    		tSpot = new FieldDisplayArea(ComplexD.copyOf(_repMonadCD.getCoeff(j)));
						    	    		tSpot.addFocusListener(this);
						    	    		_jCoeffs.add(j, tSpot);
						    	    	}
    		}	
    	
    }
    
    private void 	createManagementLayout()
    {
    	monadAlterControls=new JPanel();
    	monadAlterControls.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	monadAlterControls.setBackground(_backColor);
    	monadAlterControls.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn = new GridBagConstraints();
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=0;
    	cn.weighty=0;
    	cn.gridwidth=2;
    	
    	scaleMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Scale")));
    	scaleMonad.setActionCommand("scale");
    	scaleMonad.setToolTipText("scale() the monad");
    	scaleMonad.setPreferredSize(squareMedium);
    	scaleMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	scaleMonad.addActionListener(this);
    	monadAlterControls.add(scaleMonad, cn);
    	cn.gridy++;
    	
    	normalizeMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Norm")));
    	normalizeMonad.setActionCommand("normalize");
    	normalizeMonad.setToolTipText("normalize THIS Monad");
    	normalizeMonad.setPreferredSize(squareMedium);
    	normalizeMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	normalizeMonad.addActionListener(this);
    	monadAlterControls.add(normalizeMonad, cn);
    	cn.gridy++;
    	
    	invertMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Invert")));
    	invertMonad.setActionCommand("invert");
    	invertMonad.setToolTipText("invert [+/-] generators");
    	invertMonad.setPreferredSize(squareMedium);
    	invertMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	invertMonad.addActionListener(this);
    	monadAlterControls.add(invertMonad, cn);
    	cn.gridy++;
    	
    	reverseMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Reverse")));
    	reverseMonad.setActionCommand("reverse");
    	reverseMonad.setToolTipText("reverse [ab->ba] blades");
    	reverseMonad.setPreferredSize(squareMedium);
    	reverseMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	reverseMonad.addActionListener(this);
    	monadAlterControls.add(reverseMonad, cn);
    	cn.gridy++;
    	
    	dualLeft = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.DualLeft")));
    	dualLeft.setActionCommand("dual>");
    	dualLeft.setToolTipText("left Dual of the monad using algebra's PS");
    	dualLeft.setPreferredSize(squareMedium);
    	dualLeft.setBorder(BorderFactory.createEtchedBorder(0));
    	dualLeft.addActionListener(this);
    	monadAlterControls.add(dualLeft, cn);
    	cn.gridy++;
    	
    	dualRight = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.DualRight")));
    	dualRight.setActionCommand("<dual");
    	dualRight.setToolTipText("right Dual of the monad using algebra's PS");
    	dualRight.setPreferredSize(squareMedium);
    	dualRight.setBorder(BorderFactory.createEtchedBorder(0));
    	dualRight.addActionListener(this);
    	monadAlterControls.add(dualRight, cn);	
    	cn.gridy++;
    	
    	gradeCrop = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.GradeCrop")));
    	gradeCrop.setActionCommand("grade crop");
    	gradeCrop.setToolTipText("crop around grade()");
    	gradeCrop.setPreferredSize(squareMedium);
    	gradeCrop.setBorder(BorderFactory.createEtchedBorder(0));
    	gradeCrop.addActionListener(this);
    	monadAlterControls.add(gradeCrop, cn);
    	cn.gridy++;
    	
    	gradeCut = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.GradeCut")));
    	gradeCut.setActionCommand("grade cut");
    	gradeCut.setToolTipText("cut this grade()");
    	gradeCut.setPreferredSize(squareMedium);
    	gradeCut.setBorder(BorderFactory.createEtchedBorder(0));
    	gradeCut.addActionListener(this);
    	monadAlterControls.add(gradeCut, cn);
    	cn.gridy++;
    	
    	cn.weighty=1;
    	monadAlterControls.add(new JLabel(), cn);
    	
    	add(monadAlterControls,"East");
    }
    
    private void		createMinReferenceLayout()
    {
    	_monadReferences=new JPanel();
    	_monadReferences.setBackground(_backColor);
    	_monadReferences.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn0 = new GridBagConstraints();
    	cn0.anchor=GridBagConstraints.WEST;
    	
    	cn0.gridx = 0;
    	cn0.gridy = 0;
    	cn0.weightx=0;
    	cn0.weighty=0;
    	
    	_monadReferences.add(new JLabel("Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	name.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	_monadReferences.add(name, cn0);
    	cn0.gridx=0;
    	cn0.gridy++;
    	
    	_monadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Foot"))), cn0);
    	cn0.gridx++;
    	foot.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	_monadReferences.add(foot, cn0);
    	cn0.gridx=0;
    	cn0.gridy++;
    	
    	_monadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Sig"))), cn0);
    	cn0.gridx++;
    	sig.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	_monadReferences.add(sig, cn0);
    	cn0.gridx = 0;
    	cn0.gridy++;
    	
    	_monadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Alg"))), cn0);
    	cn0.gridx++;
    	aname.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	_monadReferences.add(aname, cn0);
    	cn0.gridx=0;
    	cn0.gridy++;
    	
    	_monadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Frame"))), cn0);
    	cn0.gridx++;
    	frame.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	_monadReferences.add(frame, cn0);
    	
    	add(_monadReferences,"South");
    }
    
    private void		createReferenceLayout()
    {
    	_monadReferences=new JPanel();
    	_monadReferences.setBorder(BorderFactory.createTitledBorder("M"));
    	_monadReferences.setBackground(_backColor);
    	_monadReferences.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn0 = new GridBagConstraints();
    	cn0.anchor=GridBagConstraints.WEST;
    	
    	cn0.gridx = 0;
    	cn0.gridy = 0;
    	cn0.weightx=0;
    	cn0.weighty=0;
    	
    	_monadReferences.add(new JLabel("Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	name.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	cn0.weightx=1;
    	_monadReferences.add(name, cn0);
    	cn0.weightx=0;
    	cn0.gridx++;
    	
    	_monadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Alg"))), cn0);
    	cn0.gridx++;
    	aname.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	_monadReferences.add(aname, cn0);
    	cn0.gridx++;
    	
    	_monadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Sig"))), cn0);
    	cn0.gridx++;
    	sig.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	_monadReferences.add(sig, cn0);

    	
    	cn0.gridx = 0;
    	cn0.gridy++;
    	
    	_monadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Frame"))), cn0);
    	cn0.gridx++;
    	frame.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	cn0.weightx=1;
    	_monadReferences.add(frame, cn0);
    	cn0.weightx=0;
    	cn0.gridx++;
    	
    	_monadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Foot"))), cn0);
    	cn0.gridx++;
    	foot.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	_monadReferences.add(foot, cn0);
    	cn0.gridx++;
    	
    	_monadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Key"))), cn0);
    	cn0.gridx++;
    	gradeKey.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	_monadReferences.add(gradeKey, cn0);
    	
    	add(_monadReferences,"South");
    	
    }
    
    private void 	makeNotWritable()
    {
    	if (_monadReferences!=null)
    		_monadReferences.setBackground(_backColor);
    	if (_monadCoeffPanel!=null)
    		_monadCoeffPanel.setBackground(_backColor);
	    name.setEditable(false);
	    sig.setEditable(false);
	    frame.setEditable(false);
	    foot.setEditable(false);
	    aname.setEditable(false);
	    gradeKey.setEditable(false);
	    
	    if (useFullPanel)
	    	for (FieldDisplayArea point : _jCoeffs)
	    		point.setEditable(false);
    }
    
    private void 	setReferences()
    {
    	switch (_repMode)
    	{
    	case DivField.REALF:	name.setText(_repMonadF.getName());
						    	aname.setText(_repMonadF.getAlgebra().getAlgebraName());
						    	sig.setText(_repMonadF.getAlgebra().getGProduct().getSignature());
						    	frame.setText(_repMonadF.getFrameName());
						    	foot.setText(_repMonadF.getAlgebra().getFoot().getFootName());
						    	gradeKey.setText(new StringBuffer().append(_repMonadF.getGradeKey()).toString());
						    	break;
    	case DivField.REALD:	name.setText(_repMonadD.getName());
						    	aname.setText(_repMonadD.getAlgebra().getAlgebraName());
						    	sig.setText(_repMonadD.getAlgebra().getGProduct().getSignature());
						    	frame.setText(_repMonadD.getFrameName());
						    	foot.setText(_repMonadD.getAlgebra().getFoot().getFootName());
						    	gradeKey.setText(new StringBuffer().append(_repMonadD.getGradeKey()).toString());
						    	break;
    	case DivField.COMPLEXF:	name.setText(_repMonadCF.getName());
						    	aname.setText(_repMonadCF.getAlgebra().getAlgebraName());
						    	sig.setText(_repMonadCF.getAlgebra().getGProduct().getSignature());
						    	frame.setText(_repMonadCF.getFrameName());
						    	foot.setText(_repMonadCF.getAlgebra().getFoot().getFootName());
						    	gradeKey.setText(new StringBuffer().append(_repMonadCF.getGradeKey()).toString());
						    	break;
    	case DivField.COMPLEXD:	name.setText(_repMonadCD.getName());
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
    	if (_monadReferences!=null)
    		_monadReferences.setBackground(_unlockColor);
    	if (_monadCoeffPanel!=null)
    		_monadCoeffPanel.setBackground(_unlockColor);
    	
	    name.setEditable(true);
	    aname.setEditable(true);
	    frame.setEditable(true);
	    gradeKey.setEditable(false);
	    
	    if (useFullPanel)
	    {
	    	sig.setEditable(false);
	    	foot.setEditable(false);
		    for (FieldDisplayArea point : _jCoeffs)
		    	point.setEditable(true);
	    }
	    else
	    {
	    	sig.setEditable(true);
	    	foot.setEditable(true);
	    }    
    }
    /**
     * This method registers a Focus Listener for a JTextField (likely a descedent of one) 
     * so the Monad Panel can respond to events.
     * @param pTextField	JTextField
     */
    protected 	void		registerTextChange(JTextField pTextField)
    {
    	pTextField.addFocusListener(null);						
    }
    	
}