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
import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.util.*;

/** com.interworldtransport.cladosviewer.MonadPanel
 * The MonadPanel class directly handles the gui for a single Monad.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class MonadPanel extends JPanel implements ActionListener
{
	public		MonadRealF						_repMonad;
	protected	JTextField						aname=new JTextField(10);
	protected	JTextField						foot=new JTextField(10);
	protected	JTextField						frame=new JTextField(20);
	protected	JTextField						gradeKey=new JTextField(10);
	protected	JTextField						name=new JTextField(20);
	protected	JTextField						sig=new JTextField(10);
	public		CladosCalculator				_GUI;
	private		Color							_backColor = new Color(212, 212, 192);
	private		ArrayList<JFormattedTextField>	_jCoeffs;
	private		JPanel 							_monadCoeffPanel;
	private		JPanel 							_monadControls;
	private		JPanel 							_monadReferences;
	private		Color							_unlockColor = new Color(255, 192, 192);
	private		Dimension						square=new Dimension(25,25);
	/*
	 * This boolean is for knowing whether to render the coefficients or not
	 * This panel doubles up as a create dialog when no coefficients array exists.
	 * It can't exist until after the signature is given.
	 */
	private		boolean							useFullPanel;
	protected	RealF[]							_repMonadCoeffs;
	protected	JButton							editButton;
	private		String							orient;
	protected	JButton							changeOrient;
	protected	ImageIcon						horizIcon;
	protected	ImageIcon						vertIcon;
	protected	JButton							restoreButton;
	protected	JButton							syncButton;
	

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
    	horizIcon=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
    	vertIcon=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
    	
    	_repMonad=pM;
        _repMonadCoeffs=_repMonad.getCoeff();
        setReferences();
        		
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        setBackground(_backColor);
        setLayout(new BorderLayout());
       
        createLayout();
        createReferenceLayout();
        createControlLayout();
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
    	horizIcon=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Horiz"));
    	vertIcon=new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Vert"));
    
    	RealF tZero=RealF.newZERO(pAName);
    	_repMonad=new MonadRealF(pName, pAName, pFrame, pFoot, pSig, tZero );
    	_repMonadCoeffs=_repMonad.getCoeff();
    	setReferences();
    	
    	setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	setBackground(_backColor);
    	setLayout(new BorderLayout());
    	
    	createLayout();
    	createReferenceLayout();
    	createControlLayout();
    }

    public void 	actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	//System.out.println("MonadPanel says "+command);
    	
    	if (command == "grdXbld") //Horizontal layout call
    	{
    		orient="Horizontal";
    		createLayout();
    		changeOrient.setIcon(horizIcon);
        	changeOrient.setActionCommand("bldXgrd");
        	changeOrient.setToolTipText("Monad grades as rows");
    		validate();
    		_GUI.pack();
    	}
    	
    	if (command == "bldXgrd") //Vertical layout call
    	{
    		orient="Vertical";
    		createLayout();
    		changeOrient.setIcon(vertIcon);
        	changeOrient.setActionCommand("grdXbld");
        	changeOrient.setToolTipText("Monad grades as columns");
    		validate();
    		_GUI.pack();
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
    				_jCoeffs.get(j).commitEdit();
    				tempCoeffs[j]=new RealF(tFieldType, Float.parseFloat(_jCoeffs.get(j).getText()));
    			}
    			_repMonad.setCoeff(tempCoeffs);
    			gradeKey.setText(new StringBuffer().append(_repMonad.getGradeKey()).toString());

    			_GUI._StatusBar.setStatusMsg(" changes saved...");
    		}
    		catch (ParseException e) 
    		{
    			_GUI._StatusBar.setStatusMsg("Could not parse at least one of the edited coefficients.\n");
			} catch (CladosMonadException e) 
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
    		editButton.setActionCommand("edit");
    		editButton.setToolTipText("start edits");
    		syncButton.setEnabled(false);
        	restoreButton.setEnabled(false);
    	   	makeNotWritable();
    		_GUI._StatusBar.setStatusMsg(" ... and now locked\n");
    	}
    	
    	if (command == "edit")
    	{
    		editButton.setActionCommand(".edit.");
    		editButton.setToolTipText("end edits w/o save");
    		syncButton.setEnabled(true);
        	restoreButton.setEnabled(true);
    		makeWritable();
    		_GUI._StatusBar.setStatusMsg("Monad references unlocked...");
    	}
    }

    public ArrayList<JFormattedTextField> getJCoeffs()
    {
	    return _jCoeffs;
    }
    
    public MonadRealF 	getMonad()
    {
	    return _repMonad;
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
	    	for (JFormattedTextField point : _jCoeffs)
	    		point.setEditable(false);
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
		    for (JFormattedTextField point : _jCoeffs)
		    	point.setEditable(true);
	    }
	    else
	    {
	    	sig.setEditable(true);
	    	foot.setEditable(true);
	    }    
    }

    public void 	setCoefficientDisplay()
    {
	    for (short j=0; j<_repMonad.getAlgebra().getGProduct().getBladeCount(); j++)	
	    	_jCoeffs.get(j).setValue(Float.valueOf(_repMonad.getCoeff(j).getModulus()));
	    
	    gradeKey.setText(new StringBuffer().append(_repMonad.getGradeKey()).toString());
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

    private void		createControlLayout()
    {
    	_monadControls=new JPanel();
    	_monadControls.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	_monadControls.setBackground(_backColor);
    	_monadControls.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn = new GridBagConstraints();
    	makeNotWritable();
    	
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=0;
    	cn.weighty=0;
    	cn.gridwidth=2;
    	
    	editButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Edit")));
    	editButton.setActionCommand("edit");
    	editButton.setToolTipText("start edits");
	 	editButton.setPreferredSize(square);
	 	editButton.setBorder(BorderFactory.createEtchedBorder(0));
	 	editButton.addActionListener(this);
    	_monadControls.add(editButton, cn);
    	cn.gridy++;
    	
    	syncButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Save")));
    	syncButton.setActionCommand("save");
    	syncButton.setToolTipText("save edits");
	 	syncButton.setEnabled(false);
	 	syncButton.setPreferredSize(square);
	 	syncButton.setBorder(BorderFactory.createEtchedBorder(0));
	 	syncButton.addActionListener(this);
    	_monadControls.add(syncButton, cn);
    	cn.gridy++;
    	
    	restoreButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Restore")));
    	restoreButton.setActionCommand("abort");
    	restoreButton.setToolTipText("abandon edits");
    	restoreButton.setEnabled(false);
	 	restoreButton.setPreferredSize(square);
	 	restoreButton.setBorder(BorderFactory.createEtchedBorder(0));
    	restoreButton.addActionListener(this);
    	_monadControls.add(restoreButton, cn);
    	cn.gridy++;
    	
    	cn.gridwidth=1;
    	cn.weighty=1;
    	changeOrient = new JButton();
    	changeOrient.setToolTipText("turn coefficient representation");
    	changeOrient.setPreferredSize(square);
    	changeOrient.setBorder(BorderFactory.createEtchedBorder(0));
    	if (orient.equals("Horizontal"))
    	{
    		changeOrient.setIcon(horizIcon);
    		changeOrient.setActionCommand("bldXgrd");
    		changeOrient.setToolTipText("Monad grades as rows");
    	}
    	else
    	{	
    		changeOrient.setIcon(vertIcon);
    		changeOrient.setActionCommand("grdXbld");
    		changeOrient.setToolTipText("Monad grades as columns");
    	}
    	changeOrient.addActionListener(this);
    	_monadControls.add(changeOrient, cn);

    	
    	add(_monadControls, "West");
    }
    
    private void 	createLayout()
    {		
    	if (_jCoeffs == null)
    	{
	    	_jCoeffs=new ArrayList<JFormattedTextField>(_repMonad.getAlgebra().getGProduct().getBladeCount());
	    	NumberFormat amountFormat = NumberFormat.getNumberInstance();
	    	for (int j=0; j<_repMonad.getAlgebra().getGProduct().getBladeCount(); j++)
	    	{
	    		JFormattedTextField tSpot = new JFormattedTextField(amountFormat);
	    		tSpot.setColumns(8);
	    		tSpot.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
	    		tSpot.setValue(_repMonadCoeffs[j].getReal());
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
    	
}