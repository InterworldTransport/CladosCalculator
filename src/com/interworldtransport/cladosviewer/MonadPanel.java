/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
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

import com.interworldtransport.cladosF.DivFieldType;
import com.interworldtransport.cladosF.RealF;
import com.interworldtransport.cladosFExceptions.FieldBinaryException;
import com.interworldtransport.cladosG.AlgebraRealF;
import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import java.awt.event.*;
//import java.text.NumberFormat;
//import java.text.ParseException;

import javax.swing.*;
import javax.swing.border.BevelBorder;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;

import java.util.*;

/** com.interworldtransport.cladosviewer.MonadPanel
 * The MonadPanel class directly handles the gui for a single Monad.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class MonadPanel extends JPanel implements ActionListener, FocusListener
{
	public		CladosCalculator				_GUI;
	private		Color							_backColor = new Color(212, 212, 192);
	private		ArrayList<FieldArea>			_jCoeffs;
	private		JPanel 							_monadCoeffPanel;
	private		JPanel 							_monadReferences;
	private		MonadRealF						_repMonad;
	private		RealF[]							_repMonadCoeffs;
	private		Color							_unlockColor = new Color(255, 192, 192);
	private		JPanel 							monadAlterControls;
	private		JPanel 							monadEditControls;
	private		String							orient;
	private		Dimension						squareLittle=new Dimension(25,25);
	private		Dimension						squareMedium=new Dimension(35,35);
	/*
	 * This boolean is for knowing whether to render the coefficients or not
	 * This panel doubles up as a create dialog when no coefficients array exists.
	 * It can't exist until after the signature is given.
	 */
	private		boolean							useFullPanel;
	protected	JTextField						aname=new JTextField(10);
	protected	JButton							btnEdit;
	protected	JButton							btnRestore;
	protected	JButton							btnSync;
	protected	JButton							changeOrient;
	protected	JButton							dualLeft;
	protected	JButton							dualRight;
	protected	JTextField						foot=new JTextField(10);
	protected	JTextField						frame=new JTextField(20);
	protected	JButton							gradeCrop;
	protected	JButton							gradeCut;
	protected	JTextField						gradeKey=new JTextField(10);
	protected	ImageIcon						iconHorizontal;
	protected	ImageIcon						iconVertical;
	protected	JButton							invertMonad; //This is NOT multiplicative inverse
	protected	JTextField						name=new JTextField(20);
	protected	JButton							normalizeMonad;
	protected	JButton							reverseMonad;
	protected	JButton							scaleMonad;
	protected	JTextField						sig=new JTextField(10);
	

/**
 * 
 * @param pGUI				CladosCalculator
 * @throws UtilitiesException 
 */
   public MonadPanel(CladosCalculator pGUI) throws UtilitiesException
    {
	   super();
	   useFullPanel=false;	// Use this panel in it's small sense
	   if (pGUI==null)
		   throw new UtilitiesException("A GUI must be passed to a MonadPanel");
	   _GUI=pGUI;
	   
	   name.setText("__c");
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
 * @param pGUI				MonadViewer
 * @param pM				MonadRealF	The Monad to be handled in this panel
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

    	orient=_GUI.IniProps.getProperty("Desktop.MVRender");
    	iconHorizontal=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
    	iconVertical=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
    	
    	_repMonad=pM;
        _repMonadCoeffs=_repMonad.getCoeff();
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
 * This constructor is the base one upon which the others call with their
 * special cases.
 * @param pGUI				CladosCalculator
 * @param pName 			String	Name of the Monad to be constructed
 * @param pAName 			String	Name of the Algebra to be constructed
 * @param pFrame 			String	Frame name to be associated with the Monad
 * @param pFoot 			String	Foot name to be used by the Algebra
 * @param pSig	 			String	Signature to be used by the GProduct of the Algebra
 */
    public MonadPanel(	CladosCalculator pGUI,
    					String pName,
    					String pAName,
    					String pFrame,
    					String pFoot, 
    					String pSig)
    throws 		UtilitiesException, BadSignatureException, CladosMonadException
    {
    	super();
    	useFullPanel=true;	// Use this panel in it's full sense
    	if (pGUI==null)
    		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
    	
    	_GUI=pGUI;
    	orient=_GUI.IniProps.getProperty("Desktop.MVRender");
    	iconHorizontal=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
    	iconVertical=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
    
    	RealF tZero=RealF.newZERO(pAName);
    	_repMonad=new MonadRealF(pName, pAName, pFrame, pFoot, pSig, tZero );
    	_repMonadCoeffs=_repMonad.getCoeff();
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
    		try
    		{
    			if (name.getText() != _repMonad.getName())
    				_repMonad.setName(name.getText());
    			
    			if (frame.getText() != _repMonad.getFrameName())
    				_repMonad.setFrameName(frame.getText());
    			
    			DivFieldType tFieldType = _repMonadCoeffs[0].getFieldType();
    			RealF[] tempCoeffs = new RealF[_repMonad.getAlgebra().getGProduct().getBladeCount()];
    			
    			for (short j=0; j<_repMonad.getAlgebra().getGProduct().getBladeCount(); j++)
    			{
    				//_jCoeffs.get(j).commitEdit();
    				tempCoeffs[j]=new RealF(tFieldType, Float.parseFloat(_jCoeffs.get(j).getText()));
    			}
    			_repMonad.setCoeff(tempCoeffs);
    			gradeKey.setText(new StringBuffer().append(_repMonad.getGradeKey()).toString());

    			_GUI._StatusBar.setStatusMsg(" changes saved...");
    		}
    		//catch (ParseException e) 
    		//{
    		//	_GUI._StatusBar.setStatusMsg("Could not parse at least one of the edited coefficients.\n");
			//} 
    		catch (CladosMonadException e) 
    		{
				_GUI._StatusBar.setStatusMsg(e.getSourceMessage()+"\n");
				_GUI._StatusBar.setStatusMsg("Could not set at least one of the edited coefficients.\n");
			} 
    		command=".edit.";
    	}
    	
    	if (command == "abort")
    	{
    		setReferences();
    		setCoefficientDisplay();
    		_GUI._StatusBar.setStatusMsg("... reset to stored values");
    		command=".edit.";
    	}
    	
    	if (command == ".edit.")
    	{
    		btnEdit.setActionCommand("edit");
    		btnEdit.setToolTipText("start edits");
    		btnSync.setEnabled(false);
        	btnRestore.setEnabled(false);
    	   	makeNotWritable();
    		_GUI._StatusBar.setStatusMsg(" ... and now locked\n");
    	}
    	
    	if (command == "edit")
    	{
    		btnEdit.setActionCommand(".edit.");
    		btnEdit.setToolTipText("end edits w/o save");
    		btnSync.setEnabled(true);
        	btnRestore.setEnabled(true);
    		makeWritable();
    		_GUI._StatusBar.setStatusMsg("Monad references unlocked...");
    	}
    	
    	if (command == "grade crop")
    	{
    		short tGrade = Short.parseShort(_GUI._FieldBar.getRealText());
    		_repMonad.gradeCrop(tGrade);
    		setCoefficientDisplay();
    		_GUI._StatusBar.setStatusMsg("\tselected monad has grades cropped around {"+tGrade+"}\n");
    	}
    	
    	if (command == "grade cut")
    	{
    		short tGrade = Short.parseShort(_GUI._FieldBar.getRealText());
    		_repMonad.gradeCut(tGrade);
    		setCoefficientDisplay();
    		_GUI._StatusBar.setStatusMsg("\tselected monad grade suppressed at {"+tGrade+"}\n");
    	}
    	
    	if (command == "scale")
    	{
    		short tScale = Short.parseShort(_GUI._FieldBar.getRealText());
    		try 
    		{
				_repMonad.scale(AlgebraRealF.generateNumber(_repMonad.getAlgebra(), tScale));
				setCoefficientDisplay();
	    		_GUI._StatusBar.setStatusMsg("\tselected monad scaled.\n");
			} 
    		catch (FieldBinaryException e) 
    		{
    			_GUI._StatusBar.setStatusMsg("\t\tfailed to scale monad when turning the scale factor to a cladosF number.\n");
			}
    	}
    	
    	if (command == "normalize")
    	{
    		try 
    		{
				_repMonad.normalize();
				setCoefficientDisplay();
	    		_GUI._StatusBar.setStatusMsg("\tselected monad inverted.\n");
			} 
    		catch (CladosMonadException e) 
    		{
    			_GUI._StatusBar.setStatusMsg("\t\tfailed to normalize monad.\n");
    			_GUI._StatusBar.setStatusMsg(e.getSourceMessage()+"\n");
			}
    	}
    	
    	if (command == "invert")
    	{
    		//short tGrade = Short.parseShort(_GUI._StatusBar.stRealIO.getText());
    		_repMonad.invert();
    		setCoefficientDisplay();
    		_GUI._StatusBar.setStatusMsg("\tselected monad inverted.\n");
    	}
    	
    	if (command == "reverse")
    	{
    		//short tGrade = Short.parseShort(_GUI._StatusBar.stRealIO.getText());
    		_repMonad.reverse();
    		setCoefficientDisplay();
    		_GUI._StatusBar.setStatusMsg("\tselected monad reversed.\n");
    	}
    	
    	if (command == "<dual")
    	{
    		//short tGrade = Short.parseShort(_GUI._StatusBar.stRealIO.getText());
    		_repMonad.dualRight();
    		setCoefficientDisplay();
    		_GUI._StatusBar.setStatusMsg("\tselected monad right dualed.\n");
    	}
    	
    	if (command == "dual>")
    	{
    		//short tGrade = Short.parseShort(_GUI._StatusBar.stRealIO.getText());
    		_repMonad.dualLeft();
    		setCoefficientDisplay();
    		_GUI._StatusBar.setStatusMsg("\tselected monad left dualed.\n");
    	}
    }

    @Override
    public void focusGained(FocusEvent e) 
    {
    	JTextArea tSpot = (JTextArea) e.getComponent();
    	//_GUI._FieldBar.setField  	Can't be done because JTextArea is just display element
    	_GUI._FieldBar.setWhatFloat(Float.valueOf(tSpot.getText()));
        //_GUI._StatusBar.setStatusMsg("Focus gained @"+tSpot.getText());
    }
    
    @Override
	public void focusLost(FocusEvent e) 
    {
    	;
	}

    public ArrayList<FieldArea> getJCoeffs()
    {
	    return _jCoeffs;
    }
    
    public MonadRealF 	getMonad()
    {
	    return _repMonad;
    }

    public void 	setCoefficientDisplay()
    {
	    for (short j=0; j<_repMonad.getAlgebra().getGProduct().getBladeCount(); j++)	
	    	//_jCoeffs.get(j).setText(new StringBuffer().append(_repMonad.getCoeff(j).getReal()).toString());
	    	_jCoeffs.get(j).displayContents();
	    
	    gradeKey.setText(new StringBuffer().append(_repMonad.getGradeKey()).toString());
    }

    private void 	createCoeffLayout() throws UtilitiesException
    {		
    	if (_jCoeffs == null)
    	{
	    	_jCoeffs=new ArrayList<FieldArea>(_repMonad.getAlgebra().getGProduct().getBladeCount());
	    	//NumberFormat amountFormat = NumberFormat.getNumberInstance();
	    	for (int j=0; j<_repMonad.getAlgebra().getGProduct().getBladeCount(); j++)
	    	{
	    		//JTextArea tSpot = new JTextArea(1, 8);
	    		FieldArea tSpot = new FieldArea(_repMonadCoeffs[j]);
	    		//tSpot.setColumns(8);
	    		//tSpot.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
	    		//tSpot.setText(new StringBuffer().append(_repMonadCoeffs[j].getReal()).toString());
	    		tSpot.addFocusListener(this);
	    		_jCoeffs.add(j, tSpot);
	    	}
    	}
    	if (_monadCoeffPanel != null)
    		remove(_monadCoeffPanel);
    		
	    _monadCoeffPanel=new JPanel();
	    _monadCoeffPanel.setBorder(BorderFactory.createTitledBorder("x, y, z, ..."));
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
    		
    	if (orient.equals("Vertical"))
    	{
    		for (short j=0; j<_repMonad.getAlgebra().getGProduct().getGradeCount(); j++)
    		{
    			JLabel headLabel = new JLabel(j+"-blades", SwingConstants.CENTER);
    			headLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    				
    			_monadCoeffPanel.add(headLabel, cn1);
    			cn1.gridy++;
    				
    			short[] tSpot = _repMonad.getAlgebra().getGProduct().getGradeRange(j);
    			for (short k=tSpot[0]; k<tSpot[1]+1; k++)
    			{
    				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    				_monadCoeffPanel.add(_jCoeffs.get(k), cn1);
    				cn1.gridy++;
    			}
    			cn1.gridx++;
    			cn1.gridy=0;
    		}
    	}
    	if (orient.equals("Horizontal"))
    	{
    		for (short j=0; j<_repMonad.getAlgebra().getGProduct().getGradeCount(); j++)
    		{
    			JLabel headLabel = new JLabel(j+"-blades", SwingConstants.RIGHT);
    			headLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    				
    			_monadCoeffPanel.add(headLabel, cn1);
    			cn1.gridx++;
    				
    			short[] tSpot = _repMonad.getAlgebra().getGProduct().getGradeRange(j);
    			for (short k=tSpot[0]; k<tSpot[1]+1; k++)
    			{
    				_jCoeffs.get(k).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    				_monadCoeffPanel.add(_jCoeffs.get(k), cn1);
    				cn1.gridx++;
    			}
    			cn1.gridx=0;
    			cn1.gridy++;
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
    	scaleMonad.setToolTipText("scale() THIS Monad");
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
    	invertMonad.setToolTipText("invert [+/-] Monad generators");
    	invertMonad.setPreferredSize(squareMedium);
    	invertMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	invertMonad.addActionListener(this);
    	monadAlterControls.add(invertMonad, cn);
    	cn.gridy++;
    	
    	reverseMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Reverse")));
    	reverseMonad.setActionCommand("reverse");
    	reverseMonad.setToolTipText("reverse [ab->ba] Monad blades");
    	reverseMonad.setPreferredSize(squareMedium);
    	reverseMonad.setBorder(BorderFactory.createEtchedBorder(0));
    	reverseMonad.addActionListener(this);
    	monadAlterControls.add(reverseMonad, cn);
    	cn.gridy++;
    	
    	dualLeft = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.DualLeft")));
    	dualLeft.setActionCommand("dual>");
    	dualLeft.setToolTipText("left Dual of THIS Monad using algebra's PS");
    	dualLeft.setPreferredSize(squareMedium);
    	dualLeft.setBorder(BorderFactory.createEtchedBorder(0));
    	dualLeft.addActionListener(this);
    	monadAlterControls.add(dualLeft, cn);
    	cn.gridy++;
    	
    	dualRight = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.DualRight")));
    	dualRight.setActionCommand("<dual");
    	dualRight.setToolTipText("right Dual of THIS Monad using algebra's PS");
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
	    	for (FieldArea point : _jCoeffs)
	    		point.setEditable(false);
    }
    
    private void 	setReferences()
    {
    	name.setText(_repMonad.getName());
    	aname.setText(_repMonad.getAlgebra().getAlgebraName());
    	sig.setText(_repMonad.getAlgebra().getGProduct().getSignature());
    	frame.setText(_repMonad.getFrameName());
    	foot.setText(_repMonad.getAlgebra().getFoot().getFootName());
    	gradeKey.setText(new StringBuffer().append(_repMonad.getGradeKey()).toString());
    }

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
		    for (FieldArea point : _jCoeffs)
		    	point.setEditable(true);
	    }
	    else
	    {
	    	sig.setEditable(true);
	    	foot.setEditable(true);
	    }    
    }
    
    protected 	void		registerTextChange(JTextField pTextField)
    {
    	pTextField.addFocusListener(null);						
    }
    	
}