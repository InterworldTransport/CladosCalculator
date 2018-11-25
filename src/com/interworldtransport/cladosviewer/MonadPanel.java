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

import com.interworldtransport.cladosF.*;
//import com.interworldtransport.cladosFExceptions.*;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.*;
import java.util.*;

/** com.interworldtransport.cladosviewer.MonadPanel
 * The MonadPanel class directly handles the gui for a single Monad.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class MonadPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 875676153714673037L;
	public		MonadViewer						theGUI;
	
	public		JPanel 							_monadControls;
	public		JButton							syncButton;
	protected	JButton							editButton;
	public		JButton							restoreButton;
	 
	public		JPanel 							_monadReferences;
	public		JTextField						name=new JTextField(10);
	public		JTextField						foot=new JTextField(10);
	public		JTextField						aname=new JTextField(10);
	public		JTextField						sig=new JTextField(10);
	public		JTextField						frame=new JTextField(10);
	 
	public		MonadRealF						_repMonad;
	protected	RealF[]							_repMonadCoeffs;
	 
	public		JPanel 							_monadCoeffPanel;
	private		String							orient;
	protected	ArrayList<JFormattedTextField>	_jCoeffs;
	 
	private		Color							_backColor = new Color(192, 192, 164);
	private		Color							_unlockColor = new Color(255, 164, 164);

/**
 * The MonadPanel class is intended to hold a single Monad and act as its GUI.
 * This constructor is the base one upon which the others call with their
 * special cases.
 * @param pGUI				MonadViewer
 * @param pName 			String	Name of the Monad to be constructed
 * @param pAName 			String	Name of the Algebra to be constructed
 * @param pFrame 			String	Frame name to be associated with the Monad
 * @param pFoot 			String	Foot name to be used by the Algebra
 * @param pSig	 			String	Signature to be used by the GProduct of the Algebra
 */
    public MonadPanel(	MonadViewer pGUI,
    					String pName,
    					String pAName,
    					String pFrame,
    					String pFoot, 
    					String pSig)
    throws 		UtilitiesException, BadSignatureException, CladosMonadException
    {
    	super();
    	
    	if (pGUI==null)
    		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
    	
    	theGUI=pGUI;
    		
    	if (!pName.equals("Create"))
    	{
    		RealF tZero=RealF.ZERO(pAName);
    		_repMonad=new MonadRealF(pName, pAName, pFrame, pFoot, pSig, tZero );
    		_repMonadCoeffs=_repMonad.getCoeff();
    		setReferences();
    	}
    	else
    	{
    		name.setText(pName);
    		aname.setText(pAName);
    		frame.setText(pFrame);
    		foot.setText(pFoot);
    		sig.setText(pSig);		
    	}
    	setBorder(BorderFactory.createEtchedBorder());
    	setBackground(_backColor);
    	setLayout(new BorderLayout());
    	orient=theGUI.IniProps.getProperty("MonadViewer.Desktop.MVRender");
    	createLayout();
    }

/**
 * The MonadPanel class is intended to hold a single Monad and act as its GUI.
 * This constructor is the base one.
 * @param pGUI				MonadViewer
 * @param pM				MonadRealF	The Monad to be handled in this panel
 */
    public MonadPanel(	MonadViewer pGUI,
    					MonadRealF pM)
    		throws 		UtilitiesException			
    {
    	super();
    	if (pGUI==null)
    		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
    	
    	theGUI=pGUI;
    	
    	if (pM==null)
    		throw new UtilitiesException("A Monad must be passed to this MonadPanel constructor");

    	_repMonad=pM;
        _repMonadCoeffs=_repMonad.getCoeff();
        setReferences();
        		
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(_backColor);
        setLayout(new BorderLayout());
        orient=theGUI.IniProps.getProperty("MonadViewer.Desktop.MVRender");
        createLayout();
    }

    public ArrayList<JFormattedTextField> getJCoeffs()
    {
	    return _jCoeffs;
    }

    public void 	setReferences()
    {
    	name.setText(_repMonad.getName());
    	aname.setText(_repMonad.getAlgebra().getAlgebraName());
    	sig.setText(_repMonad.getAlgebra().getGProduct().getSignature());
    	frame.setText(_repMonad.getFrameName());
    	foot.setText(_repMonad.getAlgebra().getFootPoint().getFootName());
    }

    public void 	setCoefficientDisplay()
    {
	    for (short j=0; j<_repMonad.getAlgebra().getGProduct().getBladeCount(); j++)	
	    {
	    	_jCoeffs.get(j).setValue(new Float(_repMonad.getCoeff(j).getModulus()));
	    	
	    	//String tSpot = (new StringBuffer()).append(rep.getCoeff(j).getReal()).toString();
		    //jcoeffs.get(j).setText(tSpot);
	    }
    }

    public void 	actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	
    	if (command=="save")
    	{
    		try
    		{
    			DivFieldType tFieldType = _repMonadCoeffs[0].getFieldType();
    			for (short j=0; j<_repMonad.getAlgebra().getGProduct().getBladeCount(); j++)
    			{
    				_jCoeffs.get(j).commitEdit();
    				_repMonadCoeffs[j]=new RealF(tFieldType, new Float(_jCoeffs.get(j).getText()).floatValue());
    			}
    			theGUI._StatusBar.setStatusMsg(" changes saved...");
    		}
    		catch (ParseException e) 
    		{
    			theGUI._StatusBar.setStatusMsg("Could not parse at least one of the edited coefficients.\n");
			} 
    	}
    	
    	if (command=="abort")
    	{
    		setReferences();
    		setCoefficientDisplay();
    		theGUI._StatusBar.setStatusMsg("... reset to stored values");
    	}
    	
    	if (command==".edit.")
    	{
    	    editButton.setText("edit");
    	   	makeNotWritable();
    		theGUI._StatusBar.setStatusMsg(" ... and now locked\n");
    	}
    	
    	if (command=="edit")
    	{
    	    editButton.setText(".edit.");
    		makeWritable();
    		theGUI._StatusBar.setStatusMsg("Monad references unlocked...");
    	}
    }

    public MonadRealF 	getMonad()
    {
	    return _repMonad;
    }

    public void 	makeWritable()
    {
    	if (_monadReferences!=null)
    		_monadReferences.setBackground(_unlockColor);
    	if (_monadCoeffPanel!=null)
    		_monadCoeffPanel.setBackground(_unlockColor);
    	
	    name.setEditable(true);
	    aname.setEditable(true);
	    foot.setEditable(true);
	    frame.setEditable(true);
	    
	    if (name.getText().equals("Create"))
	    	sig.setEditable(true);
	    else
	    {
	    	sig.setEditable(false);
		    for (JFormattedTextField point : _jCoeffs)
		    	point.setEditable(true);
	    }    
    }
    
    public void 	makeNotWritable()
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
	    if (!name.getText().equals("Create"))
	    	for (JFormattedTextField point : _jCoeffs)
	    		point.setEditable(false);
    }

    public void		createControlLayout()
    {
    	_monadControls=new JPanel();
    	_monadControls.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    	_monadControls.setBackground(_backColor);
    	_monadControls.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn = new GridBagConstraints();
    	Insets tGeneric = new Insets(1,1,1,1);
    	cn.insets = new Insets(1, 1, 1, 1);
    	//cn.fill=GridBagConstraints.HORIZONTAL;
    	cn.anchor=GridBagConstraints.NORTH;
    	makeNotWritable();
    	
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=1;
    	cn.weighty=1;
    	
    	syncButton=new JButton("save", new ImageIcon(theGUI.IniProps.getProperty("MonadViewer.Desktop.SaveImage")));
    	syncButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	 	syncButton.setHorizontalTextPosition(SwingConstants.CENTER);
	 	syncButton.setMargin(tGeneric);
	 	syncButton.setPreferredSize(new Dimension(50,50));
	 	syncButton.addActionListener(this);
    	_monadControls.add(syncButton, cn);
    	cn.gridy++;
    	
    	restoreButton=new JButton("abort", new ImageIcon(theGUI.IniProps.getProperty("MonadViewer.Desktop.RestoreImage")));
    	restoreButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	 	restoreButton.setHorizontalTextPosition(SwingConstants.CENTER);
	 	restoreButton.setMargin(tGeneric);
	 	restoreButton.setPreferredSize(new Dimension(50,50));
    	restoreButton.addActionListener(this);
    	_monadControls.add(restoreButton, cn);
    	cn.gridy++;
    	
    	editButton=new JButton("edit", new ImageIcon(theGUI.IniProps.getProperty("MonadViewer.Desktop.EditImage")));
    	editButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	 	editButton.setHorizontalTextPosition(SwingConstants.CENTER);
	 	editButton.setMargin(tGeneric);
	 	editButton.setPreferredSize(new Dimension(50,50));
	 	editButton.addActionListener(this);
    	_monadControls.add(editButton, cn);
    }
    
    public void		createReferenceLayout()
    {
    	_monadReferences=new JPanel();
    	_monadReferences.setBorder(BorderFactory.createTitledBorder("Monad"));
    	_monadReferences.setBackground(_backColor);
    	_monadReferences.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn0 = new GridBagConstraints();
    	cn0.insets = new Insets(0, 0, 0, 0);
    	//cn0.fill=GridBagConstraints.HORIZONTAL;
    	cn0.anchor=GridBagConstraints.NORTH;
    	
    	cn0.gridx = 0;
    	cn0.gridy = 0;
    	cn0.weightx=1;
    	cn0.weighty=1;
    	
    	_monadReferences.add(new JLabel("Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	_monadReferences.add(name, cn0);
    	cn0.gridx++;
    	
    	_monadReferences.add(new JLabel("Algebra", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	_monadReferences.add(aname, cn0);
    	cn0.gridx++;
    	
    	_monadReferences.add(new JLabel("Sig", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	_monadReferences.add(sig, cn0);

    	
    	cn0.gridx = 0;
    	cn0.gridy++;
    	
    	_monadReferences.add(new JLabel("Frame", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	_monadReferences.add(frame, cn0);
    	cn0.gridx++;
    	
    	_monadReferences.add(new JLabel("Foot", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	_monadReferences.add(foot, cn0);
    	
    }
    
    public void 	createLayout()
    {
    	createReferenceLayout();
    	add(_monadReferences,"South");
    	
    	if (_repMonad!=null) 
    	{
    		_jCoeffs=new ArrayList<JFormattedTextField>(_repMonad.getAlgebra().getGProduct().getBladeCount());
    		NumberFormat amountFormat = NumberFormat.getNumberInstance();
    		for (int j=0; j<_repMonad.getAlgebra().getGProduct().getBladeCount(); j++)
    		{
    			JFormattedTextField tSpot = new JFormattedTextField(amountFormat);
    			tSpot.setColumns(8);
    			tSpot.setValue(_repMonadCoeffs[j].getReal());
    			_jCoeffs.add(j, tSpot);
    		}
    		
    		_monadCoeffPanel=new JPanel();
    		_monadCoeffPanel.setBorder(BorderFactory.createTitledBorder("Coefficients"));
    		_monadCoeffPanel.setBackground(_backColor);
    		_monadCoeffPanel.setLayout(new GridBagLayout());
    		
    		GridBagConstraints cn1 = new GridBagConstraints();
    		cn1.insets = new Insets(0, 0, 0, 0);
    		cn1.fill=GridBagConstraints.HORIZONTAL;
    		cn1.anchor=GridBagConstraints.NORTH;
    		
    		cn1.gridx = 0;
    		cn1.gridy = 0;
    		cn1.weightx=1;
    		cn1.weighty=0;
    		
    		if (!orient.equals("Vertical"))
    		{
    			for (short j=0; j<_repMonad.getAlgebra().getGProduct().getGradeCount(); j++)
    			{
    				//_monadCoeffPanel.add(new JLabel("Name", SwingConstants.RIGHT), cn1);
    				//cn1.gridx++;
    				short[] tSpot = _repMonad.getAlgebra().getGProduct().getGradeRange(j);
    				for (short k=tSpot[0]; k<tSpot[1]+1; k++)
    				{
    					_monadCoeffPanel.add(_jCoeffs.get(k), cn1);
    					cn1.gridx++;
    				}
    				cn1.gridx=0;
    				cn1.gridy++;
    			}
    		}
    		else
    		{
    			for (short j=0; j<_repMonad.getAlgebra().getGProduct().getGradeCount(); j++)
    			{
    				
    				short[] tSpot = _repMonad.getAlgebra().getGProduct().getGradeRange(j);
    				for (short k=tSpot[0]; k<tSpot[1]+1; k++)
    				{
    					_monadCoeffPanel.add((JTextField)_jCoeffs.get(k), cn1);
    					cn1.gridy++;
    				}
    				cn1.gridx++;
    				cn1.gridy=0;
    			}
    		}
    		add(_monadCoeffPanel, "Center");
    		setCoefficientDisplay();
    		
    		createControlLayout();
        	add(_monadControls, "West");
    	}
    	else
    	{
    		_monadCoeffPanel=new JPanel();
    		_monadCoeffPanel.setBorder(BorderFactory.createTitledBorder("Coefficients"));
    		_monadCoeffPanel.setBackground(_backColor);
    		_monadCoeffPanel.setLayout(new GridBagLayout());
    		add(_monadCoeffPanel, "Center");
    	}
    	
    }
    	
}