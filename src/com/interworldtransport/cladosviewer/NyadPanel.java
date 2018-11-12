/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.NyadPanel------------------------------------------
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
---com.interworldtransport.cladosviewer.NyadPanel------------------------------------------
 */

package com.interworldtransport.cladosviewer ;
import com.interworldtransport.clados.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;


/** com.interworldtransport.cladosviewer.NyadPanel
 * The ViewerPanel class is intended to be the main panel of the Monad Viewer
 * utility.
 * <p>
 * @version 0.80, $Date: 2005/08/25 06:36:13 $
 * @author Dr Alfred W Differ
 */

 public class NyadPanel extends JPanel implements ActionListener
{
	 public		MonadViewer		TheGUI;
	 public		Nyad			rep;
	 private	JPanel 			controls;
	 private	JPanel 			refPanel;
	 protected	JButton			syncButton=new JButton("Save");
	 protected	JButton			editButton=new JButton("Edit");
	 protected	JButton			copyButton=new JButton("Copy");
	 protected	JButton			restoreButton=new JButton("Restore");
	 protected	JButton			removeButton=new JButton("Remove");
	 public		JTextField		name=new JTextField(10);
	 public		JTextField		aname=new JTextField(10);
	 public		JTextField		foot=new JTextField(10);
	 public		JTextField		order=new JTextField(10);
	 public		ImageIcon		tabicon;
	 public		JTabbedPane		MonadPanes;
	 protected	ArrayList		MonadPanelList;

/**
 * The ViewerPanel class is intended to be a tabbed pane that displays all
 * the Monad Panels.  ViewerPanel must be smart enough to
 * know what it holds and adjust the tabs when push and pop operations are
 * performed.
 */
/*	 public 		NyadPanel(MonadViewer pGUI, String pTitle, String pSig)
	 throws 		UtilitiesException, BadSignatureException
	 {
		 super();
		 if (pGUI!=null)
		 {
			 this.TheGUI=pGUI;
			 this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			 this.setBackground(new Color(192, 128, 192));
			 this.setLayout(new BorderLayout());

			 this.createLayout(pTitle, pSig);
		 }
		 else
		 {
			 System.out.println("A GUI must be passed to the StatusLine");
			 System.exit(0);
		 }
		 
	 }
*/	 
/**
 * This constructor is the copy one used when a Monad alread exists
 */
	 public NyadPanel(	MonadViewer pGUI,
			 			Nyad pN)
	 throws 	UtilitiesException, BadSignatureException
	 {
		 super();
		 if (pGUI!=null || pN!=null)
		 {
			 this.TheGUI=pGUI;
			 this.rep=pN;
			 this.setTopFields();
			 
			 this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			 this.setBackground(new Color(192, 128, 192));
			 this.setLayout(new BorderLayout());
			 
			 String logoFile=TheGUI.IniProps.getProperty("MonadViewer.Desktop.TabImage");
			 this.tabicon = createImageIcon(logoFile);
			 
			 createControlLayout();
			 this.add(controls, "North");
			 createReferenceLayout();
			 this.add(refPanel, "South");
			 
			 this.MonadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
			 this.MonadPanelList=new ArrayList(rep.getOrder());
			 
			 for (int j=0; j<rep.getOrder(); j++)
			 {
				 String count=new Integer(j).toString();
				 MonadPanelList.add(j, new MonadPanel(TheGUI, rep.getMList(j)));
				 JScrollPane tempPane=new JScrollPane((JPanel)MonadPanelList.get(j));
				 tempPane.setWheelScrollingEnabled(true);
				 MonadPanes.addTab(count, tabicon, tempPane);
			 }
			 
			 this.add(MonadPanes, "Center");
		 }
		 else
		 {
			 System.out.println("A GUI and Nyad must be passed to this constructor");
			 throw new UtilitiesException("A GUI and Nyad must be passed to this constructor");
		 }
	 }

    protected ImageIcon createImageIcon(String path)
    {
    	ImageIcon temp = new ImageIcon(path);
    	if (temp != null) 
    	{
    		return temp;
    	} 
    	else 
    	{
    		TheGUI.StatusLine.setStatusMsg("(Viewer)Could not get: "+path+"\n");
    		return null;
    	}
    }

    public 	void 		setTopFields()
    {
    	this.name.setText(rep.getName());
    	this.aname.setText(rep.getAlgebraName());
    	this.order.setText(new Integer(rep.getOrder()).toString());
    	this.foot.setText(rep.getFootName());
    }

    public 	void		createControlLayout()
    {
    	controls=new JPanel();
    	controls.setBackground(new Color(192, 128, 192));
    	String temp = TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Object");
    	if (temp.equals("Monad")) return;
    	
    	controls.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
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
    	removeButton.addActionListener(this);
    	controls.add(removeButton, cn);
    	cn.gridx++;
    	copyButton.addActionListener(this);
    	controls.add(copyButton, cn);
    	copyButton.setEnabled(false);
    	cn.gridx++;
    	editButton.addActionListener(this);
    	controls.add(editButton, cn);
    }
    
    public 	void		createReferenceLayout()
    {
    	refPanel=new JPanel();
    	refPanel.setBackground(new Color(192, 128, 192));
    	String temp = TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Object");
    	if (temp.equals("Monad")) return;
    	
    	refPanel.setBorder(BorderFactory.createTitledBorder("Nyad Reference"));
    	refPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn0 = new GridBagConstraints();
    	cn0.insets = new Insets(2, 2, 2, 2);
    	cn0.fill=GridBagConstraints.HORIZONTAL;
    	cn0.anchor=GridBagConstraints.NORTHWEST;
    	
    	
    	
    	cn0.gridx = 0;
    	cn0.gridy = 0;
    	cn0.weightx=1;
    	cn0.weighty=1;
    	
    	refPanel.add(new JLabel("Order", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	refPanel.add(order, cn0);
    	cn0.gridx++;
    	refPanel.add(new JLabel("Algebra Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	refPanel.add(aname, cn0);
    	cn0.gridx++;
    	refPanel.add(new JLabel("Foot Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	refPanel.add(foot, cn0);
    	cn0.gridx++;
    	refPanel.add(new JLabel("Nyad Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	refPanel.add(name, cn0);
    	
    	
    }
/*
    public 	void 		createLayout(String pName, String pSg)
    throws 		UtilitiesException, BadSignatureException
    {
    	String logoFile=TheGUI.IniProps.getProperty("MonadViewer.Desktop.TabImage");
		this.tabicon = createImageIcon(logoFile);
    	
    	createControlLayout();
    	this.add(controls, "North");
    	createReferenceLayout();
    	this.add(refPanel, "South");
    	
    	String tempOrder=TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Order");
    	int intOrder=new Integer(tempOrder).intValue();
    	
    	this.MonadPanes=new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.WRAP_TAB_LAYOUT);
    	this.MonadPanelList=new ArrayList(intOrder);
    	
    	for (int j=0; j<intOrder; j++)
    	{
    		String count=new Integer(j).toString();
    		MonadPanelList.add(j, new MonadPanel(TheGUI, pName, "test",  "basic", "basic", pSg));
    		JScrollPane tempPane=new JScrollPane((JPanel)MonadPanelList.get(j));
    		tempPane.setWheelScrollingEnabled(true);
    		MonadPanes.addTab(count, tabicon, tempPane);
    	}
    	
    	this.add(MonadPanes, "Center");

    }
 */
    public	void		push()
    {
	    int size=MonadPanes.getTabCount();
	    int where=MonadPanes.getSelectedIndex();
	    if (where<size)
	    {
		    String otherTitle=new String(MonadPanes.getTitleAt(where+1));
		    JScrollPane otherPane=new JScrollPane((JPanel)MonadPanelList.get(where+1));

		    String thisTitle=new String(MonadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)MonadPanelList.get(where));

		    MonadPanes.setTitleAt(where, otherTitle);
		    MonadPanes.setComponentAt(where, otherPane);

		    MonadPanes.setTitleAt(where+1, thisTitle);
		    MonadPanes.setComponentAt(where+1, thisPane);

		    MonadPanel tempPanel=(MonadPanel)MonadPanelList.remove(where);
		    MonadPanelList.add(where+1, tempPanel);

		    //this.setSelectedIndex(where+1);
		    //this.revalidate();

	    }
    }

    public	void		pop()
    {
	    int where=MonadPanes.getSelectedIndex();
	    if (where>0)
	    {
		    String otherTitle=new String(MonadPanes.getTitleAt(where-1));
		    JScrollPane otherPane=new JScrollPane((JPanel)MonadPanelList.get(where-1));

		    String thisTitle=new String(MonadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)MonadPanelList.get(where));

		    MonadPanes.setTitleAt(where, otherTitle);
		    MonadPanes.setComponentAt(where, otherPane);

		    MonadPanes.setTitleAt(where-1, thisTitle);
		    MonadPanes.setComponentAt(where-1, thisPane);

		    MonadPanel tempPanel=(MonadPanel)MonadPanelList.remove(where-1);
		    MonadPanelList.add(where, tempPanel);

		    //this.setSelectedIndex(where-1);
		    //this.revalidate();
	    }
    }

    public Nyad 	getNyad()
    {
	    return this.rep;
    }
    
    public int 		getOrder()
    {
    	return MonadPanelList.size();
    }
    
    public	MonadPanel	getMonadPanel(int pInd)
    {
	    int limit=MonadPanelList.size();
	    if (pInd<limit)
	    {
		    MonadPanel temp = (MonadPanel)MonadPanelList.get(pInd);
		    return temp;
	    }
	    else return null;
    }

    //public	void		removeTab(int pInd)
    //{
    //	MonadPanes.remove(pInd);
    //	MonadPanel temp=(MonadPanel)MonadPanelList.remove(pInd);
    //	temp=null;
    //}

    public	void		addSTab(MonadPanel pMP)
    {
	    int next=MonadPanes.getTabCount();
	    String count=new Integer(next).toString();
	    MonadPanelList.ensureCapacity(next+1);
	    boolean test=MonadPanelList.add(pMP);
	    if (test)
	    {
	    	JScrollPane tempPane=new JScrollPane((JPanel)MonadPanelList.get(next));
	    	MonadPanes.addTab(count, tabicon, tempPane);
	    }
    }
    
    public	void		addSTab(Monad pM)
    throws BadSignatureException, UtilitiesException
    {
    	MonadPanel pMP=new MonadPanel(TheGUI, pM);
	    int next=MonadPanes.getTabCount();
	    String count=new Integer(next).toString();
	    MonadPanelList.ensureCapacity(next+1);
	    boolean test=MonadPanelList.add(pMP);
	    if (test)
	    {
	    	JScrollPane tempPane=new JScrollPane((JPanel)MonadPanelList.get(next));
	    	MonadPanes.addTab(count, tabicon, tempPane);
	    }
    }
    
    public	void		removeTab(int pInd)
    {
	    MonadPanes.remove(pInd);
	    MonadPanel temp=(MonadPanel)MonadPanelList.remove(pInd);
	    temp=null;
    }
    
    public 	void 		makeWritable()
    {
    	name.setEditable(true);
    	order.setEditable(false);
    	foot.setEditable(true);
    	aname.setEditable(true);
    }
    
    public 	void 		makeNotWritable()
    {
    	name.setEditable(false);
    	order.setEditable(false);
    	foot.setEditable(false);
    	aname.setEditable(false);
    }
    
    public 	void 		actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	if (command=="Restore")
    	{
    		if (!name.getText().equals("Create"))
    		{
    			this.setTopFields();
    			getMonadPanel(0).restoreButton.doClick(100);
    			TheGUI.StatusLine.setStatusMsg("Nyad reset to stored values.\n");
    		}
    	}
    	
    	if (command=="Copy")
    	{
    		TheGUI.StatusLine.setStatusMsg("Nothing here yet.\n");
    	}
    	
    	if (command=="Remove")
    	{
    		int temp=MonadPanes.getTabCount();
    		if (temp>1)
    		{
    			this.removeTab(MonadPanes.getSelectedIndex());
    			rep.removeMonad(MonadPanes.getSelectedIndex());
    		}
    		TheGUI.StatusLine.setStatusMsg("Monad removed from list.\n");
    	}
    	
    	if (command=="Edit")
    	{
    		if (name.isEditable())
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
    		getMonadPanel(0).syncButton.doClick(100);
    		/*
    		try
    		{
    			
    			for (int j=1; j<rep.getLinearDimension()+1; j++)
    			{
    				JTextField temp= (JTextField)jcoeffs.get(j);
    				coeffs[j]= new Double(temp.getText()).doubleValue();
    			}
    			
    			this.rep=new Monad(	name.getText(),
    					"test",
    					frame.getText(),
    					foot.getText(),
    					sig.getText(), coeffs);
    			setBottomFields();
    			TheGUI.StatusLine.setStatusMsg("Changes saved.\n");
    			
    			
    		}
    		catch (UtilitiesException e)
    		{
    			System.out.println("Could not create monad copy from Save");
    		}
    		catch (BadSignatureException es)
    		{
    			System.out.println("Could not create monad copy from Save");
    		}
    		*/
    	}
    }
}
