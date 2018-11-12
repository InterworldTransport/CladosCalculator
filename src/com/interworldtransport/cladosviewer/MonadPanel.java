/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.MonadPanel------------------------------------------
<p>
Interworld Transport grants you ("Licensee") a license to this software
under the terms of the GNU General Public License.<br>
A full copy of the license can be found bundled with this package or code file.
<p>
If the license file has become separated from the package, code file, or binary
executable, the Licensee is still expected to read about the license at the
following URL before accepting this material.
<blockquote><code>http://www.opensource.org/gpl-license.html</code></blockquote>
<p>
Use of this code or executable objects derived from it by the Licensee states
their willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
---com.interworldtransport.cladosviewer.MonadPanel------------------------------------------
 */

package com.interworldtransport.cladosviewer ;
import com.interworldtransport.clados.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

/** com.interworldtransport.cladosviewer.MonadPanel
 * The MonadPanel class directly handles the gui for a single Monad.
 * <p>
 * @version 0.80, $Date: 2005/08/25 06:36:13 $
 * @author Dr Alfred W Differ
 */

 public class MonadPanel extends JPanel implements ActionListener
{
	 public		MonadViewer		TheGUI;
	 public		Monad			rep;
	 public		JPanel 			controls;
	 public		JPanel 			refPanel;
	 public		JPanel 			coeffPanel;
	 public		JButton			syncButton=new JButton("Save");
	 protected	JButton			editButton=new JButton("Edit");
	 //protected	JButton			copyButton=new JButton("Copy");
	 public		JButton			restoreButton=new JButton("Restore");
	 public		JTextField		name=new JTextField(10);
	 public		JTextField		aname=new JTextField(10);
	 public		JTextField		sig=new JTextField(1);
	 public		JTextField		frame=new JTextField(10);
	 public		JTextField		foot=new JTextField(10);
	 protected	JTextField		grades=new JTextField(10);
	 protected	JTextField		dims=new JTextField(10);
	 protected	JTextField		gens=new JTextField(10);
	 protected	ArrayList		jcoeffs;
	 protected	double[]		coeffs;
	 private	String			orient;

/**
 * The MonadPanel class is intended to hold a single Monad and act as its GUI.
 * This constructor is the base one upon which the others call with their
 * special cases.
 * @param pGUI				MonadViewer
 * @param pName 			String
 * @param pAName 			String
 * @param pFrame 			String
 * @param pFoot 			String
 * @param pSig	 			String
 */
    public MonadPanel(	MonadViewer pGUI,
    					String pName,
    					String pAName,
    					String pFrame,
    					String pFoot, 
    					String pSig)
    throws 		UtilitiesException, BadSignatureException
    {
    	super();
    	
    	if (pGUI!=null)
    	{
    		this.TheGUI=pGUI;
    		
    		if (!pName.equals("Create"))
    		{
    			this.rep=new Monad(pName, pAName, pFrame, pFoot, pSig );
    			this.coeffs=rep.getCoeff();
    			this.setTopFields();
    		}
    		else
    		{
    			this.name.setText(pName);
    			this.aname.setText(pAName);
    			this.frame.setText(pFrame);
    			this.foot.setText(pFoot);
    			this.sig.setText(pSig);
    			
    		}
    		this.setBorder(BorderFactory.createEtchedBorder());
    		this.setBackground(new Color(192, 192, 128));
    		this.setLayout(new BorderLayout());
    		this.orient=TheGUI.IniProps.getProperty("MonadViewer.Desktop.MVRender");
    		this.createLayout();
    		
    	}
    	else
    	{
    		System.out.println("A GUI must be passed to a MonadPanel");
    		throw new UtilitiesException("A GUI must be passed to a MonadPanel");
    	}
    }

/**
 * The MonadPanel class is intended to hold a single Monad and act as its GUI.
 * This constructor is the base one.
 * @param pGUI				MonadViewer
 * @param pM				Monad
 */
    public MonadPanel(	MonadViewer pGUI,
    					Monad pM)
    throws 		UtilitiesException, BadSignatureException
    {
    	super();
    	if (pGUI!=null || pM!=null)
    	{
    		this.TheGUI=pGUI;
    		this.rep=pM;
    		this.coeffs=rep.getCoeff();
    		this.setTopFields();
    		
    		this.setBorder(BorderFactory.createEtchedBorder());
    		this.setBackground(new Color(192, 192, 128));
    		this.setLayout(new BorderLayout());
    		this.orient=TheGUI.IniProps.getProperty("MonadViewer.Desktop.MVRender");
    		this.createLayout();
    	}
    	else
    	{
    		System.out.println("A GUI and Monad must be passed to this MonadPanel constructor");
    		throw new UtilitiesException("A GUI and Monad must be passed to this MonadPanel constructor");
    	}
    }

    public ArrayList getCoeffs()
    {
	    return jcoeffs;
    }

    public void 	setTopFields()
    {
    	this.name.setText(rep.getName());
    	this.aname.setText(rep.getAlgebraName());
    	this.grades.setText(new Integer(rep.getGradeCount()).toString());
    	StringBuffer tempdim=new StringBuffer();
    	tempdim.append(new Integer(rep.getLinearDimension()).toString());
    	tempdim.append(" : ");
    	tempdim.append(new Integer(rep.getGradeRangeF(2)-rep.getGradeRangeF(1)).toString());
    	this.dims.setText(tempdim.toString());
    	//this.gens.setText();
    	this.sig.setText(rep.getSignature());
    	this.frame.setText(rep.getFrameName());
    	this.foot.setText(rep.getFootName());
    }

    public void 	setBottomFields()
    {
	    this.coeffs=rep.getCoeff();
	    for (int j=1; j<rep.getLinearDimension()+1; j++)
	    {
		    JTextField temp= (JTextField)jcoeffs.get(j);
		    temp.setText(new Double(coeffs[j]).toString());
	    }
    }

    public void 	actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	if (command=="Restore")
    	{
    		if (!name.getText().equals("Create"))
    		{
    			this.setTopFields();
    			this.setBottomFields();
    			TheGUI.StatusLine.setStatusMsg("Monad reset to stored values.\n");
    		}
    	}
    	
    	//if (command=="Copy")
    	//{
    	//	TheGUI.StatusLine.setStatusMsg("Internal copy is meant for Nyads.  Not ready yet.\n");
    	//}
    	
    	if (command=="Edit")
    	{
    		if (frame.isEditable())
    		{
    			this.makeNotWritable();
    			TheGUI.StatusLine.setStatusMsg("Monad Headers Locked.\n");
    		}
    		else
    		{
    			this.makeWritable();
    			TheGUI.StatusLine.setStatusMsg("Monad Headers Unlocked.\n");
    		}
    	}
    	
    	if (command=="Save")
    	{
    		try
    		{
    			if (!name.getText().equals("Create"))
    			{
    				for (int j=1; j<rep.getLinearDimension()+1; j++)
    				{
    					JTextField temp= (JTextField)jcoeffs.get(j);
    					coeffs[j]= new Double(temp.getText()).doubleValue();
    				}
    				
    				this.rep=new Monad(	name.getText(),
    						aname.getText(),
    						frame.getText(),
    						foot.getText(),
    						sig.getText(), coeffs);
    				setBottomFields();
    				TheGUI.StatusLine.setStatusMsg("Changes saved.\n");
    			}
    			else
    			{
    				this.rep=new Monad(	name.getText(),
    									aname.getText(),
    									frame.getText(),
    									foot.getText(),
    									sig.getText());
    				Nyad rep2=new Nyad("Copy", rep);
    				TheGUI.CenterAll.addSTab(rep2);
    				TheGUI.StatusLine.setStatusMsg("New nyad added to stack.\n");
    			}
    			
    		}
    		catch (UtilitiesException e)
    		{
    			System.out.println("Could not create monad copy from Save");
    		}
    		catch (BadSignatureException es)
    		{
    			System.out.println("Could not create monad copy from Save");
    		}
    	}
    }

    public Monad 	getMonad()
    {
	    return this.rep;
    }

    public void 	makeWritable()
    {
	    name.setEditable(true);
	    sig.setEditable(true);
	    grades.setEditable(false);
	    frame.setEditable(true);
	    //gens.setEditable(false);
	    foot.setEditable(true);
	    dims.setEditable(false);
	    aname.setEditable(true);
    }
    
    public void 	makeNotWritable()
    {
	    name.setEditable(false);
	    sig.setEditable(false);
	    grades.setEditable(false);
	    frame.setEditable(false);
	    //gens.setEditable(false);
	    foot.setEditable(false);
	    dims.setEditable(false);
	    aname.setEditable(false);
    }

    public void		createControlLayout()
    {
    	controls=new JPanel();
    	controls.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    	controls.setBackground(new Color(192, 192, 128));
    	controls.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn = new GridBagConstraints();
    	cn.insets = new Insets(2, 2, 2, 2);
    	cn.fill=GridBagConstraints.HORIZONTAL;
    	cn.anchor=GridBagConstraints.NORTHWEST;
    	this.makeNotWritable();
    	
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=1;
    	cn.weighty=1;
    	
    	syncButton.addActionListener(this);
    	controls.add(syncButton, cn);
    	cn.gridx++;
    	restoreButton.addActionListener(this);
    	controls.add(restoreButton, cn);
    	cn.gridx++;
    	//copyButton.addActionListener(this);
    	//controls.add(copyButton, cn);
    	//copyButton.setEnabled(false);
    	cn.gridx++;
    	editButton.addActionListener(this);
    	controls.add(editButton, cn);
    }
    
    public void		createReferenceLayout()
    {
    	this.refPanel=new JPanel();
    	refPanel.setBorder(BorderFactory.createTitledBorder("Monad Reference"));
    	refPanel.setBackground(new Color(192, 192, 128));
    	refPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn0 = new GridBagConstraints();
    	cn0.insets = new Insets(2, 2, 2, 2);
    	cn0.fill=GridBagConstraints.HORIZONTAL;
    	cn0.anchor=GridBagConstraints.NORTHWEST;
    	
    	cn0.gridx = 0;
    	cn0.gridy = 0;
    	cn0.weightx=1;
    	cn0.weighty=1;
    	
    	refPanel.add(new JLabel("Grade Count", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	refPanel.add(grades, cn0);
    	cn0.gridx++;
    	refPanel.add(new JLabel("Dimensions (VS:A)", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	refPanel.add(dims, cn0);
    	cn0.gridx++;
    	refPanel.add(new JLabel("Signature", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	refPanel.add(sig, cn0);
    	
    	cn0.gridx = 0;
    	cn0.gridy++;
    	refPanel.add(new JLabel("Algebra Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	refPanel.add(aname, cn0);
    	cn0.gridx++;
    	refPanel.add(new JLabel("Frame Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	refPanel.add(frame, cn0);
    	cn0.gridx++;
    	refPanel.add(new JLabel("Foot Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	refPanel.add(foot, cn0);
    	
    	
    	cn0.gridx = 0;
    	cn0.gridy++;
    	refPanel.add(new JLabel("Monad Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	refPanel.add(name, cn0);
    }
    
    public void 	createLayout()
    {
    	
    	createControlLayout();
    	this.add(controls, "North");

    	createReferenceLayout();

    	if (rep!=null) 
    	{
    		this.jcoeffs=new ArrayList(rep.getLinearDimension()+1);
    		for (int j=0; j<rep.getLinearDimension()+1; j++)
    		{
    			jcoeffs.add(j, new JTextField(" ", 5));
    		}
    		
    		this.coeffPanel=new JPanel();
    		coeffPanel.setBorder(BorderFactory.createTitledBorder("Coefficients"));
    		coeffPanel.setBackground(new Color(192, 192, 128));
    		coeffPanel.setLayout(new GridBagLayout());
    		
    		GridBagConstraints cn1 = new GridBagConstraints();
    		cn1.insets = new Insets(0, 0, 0, 0);
    		cn1.fill=GridBagConstraints.HORIZONTAL;
    		cn1.anchor=GridBagConstraints.NORTHWEST;
    		
    		cn1.gridx = 0;
    		cn1.gridy = 0;
    		cn1.weightx=1;
    		cn1.weighty=0;
    		
    		if (!orient.equals("Vertical"))
    		{
    			for (int j=0; j<rep.getGradeCount(); j++)
    			{
    				for (int k=rep.getGradeRangeF(j); k<rep.getGradeRangeB(j)+1; k++)
    				{
    					coeffPanel.add((JTextField)jcoeffs.get(k), cn1);
    					cn1.gridx++;
    				}
    				cn1.gridx=0;
    				cn1.gridy++;
    			}
    		}
    		else
    		{
    			for (int j=0; j<rep.getGradeCount(); j++)
    			{
    				for (int k=rep.getGradeRangeF(j); k<rep.getGradeRangeB(j)+1; k++)
    				{
    					coeffPanel.add((JTextField)jcoeffs.get(k), cn1);
    					cn1.gridy++;
    				}
    				cn1.gridx++;
    				cn1.gridy=0;
    			}
    		}
    		

        	this.add(refPanel,"South");
    		this.add(coeffPanel, "Center");
    		
    		this.setBottomFields();
    	}
    	else
    	{
    		this.add(refPanel, "Center");
    	}
    }
    	
}