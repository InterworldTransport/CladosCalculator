/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.ViewerPanel------------------------------------------
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
---com.interworldtransport.cladosviewer.ViewerPanel------------------------------------------
 */

package com.interworldtransport.cladosviewer ;

import com.interworldtransport.cladosF.*;
//import com.interworldtransport.cladosFExceptions.*;
import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

/** com.interworldtransport.cladosviewer.ViewerPanel
 * The ViewerPanel class is intended to be the main panel of the Calculator.  It
 * holds the tabbed pane that holds Nyads.  It is also aware enough to manage
 * the nyads a bit with respect to stack operations.
 * <p>
 * @version 0.80, $Date: 2005/08/25 06:36:13 $
 * @author Dr Alfred W Differ
 */

 public class ViewerPanel extends JPanel //implements ActionListener
{
	private static final long serialVersionUID = 8067075019491290702L;
	public		ImageIcon				tabicon;
    public		MonadViewer				TheGUI;
    public		JTabbedPane				NyadPanes;
    protected	ArrayList<NyadPanel>	NyadPanelList;

/**
 * The ViewerPanel class is intended to be a tabbed pane that displays all
 * the Monad Panels.  ViewerPanel must be smart enough to
 * know what it holds and adjust the tabs when push and pop operations are
 * performed.
 */
    public 	ViewerPanel(MonadViewer pGUI) 
    	throws 		UtilitiesException, BadSignatureException
    {
    	super();
    	
    	TheGUI=pGUI;
    	setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    	setBackground(new Color(255, 255, 222));
    	setLayout(new BorderLayout());
    		
    	createLayout();
    }

    public 	void 		createLayout()
    throws 		UtilitiesException, BadSignatureException
    {
    	//Get the nyad tab image for the nyad panes being constructed
    	tabicon = new ImageIcon(TheGUI.IniProps.getProperty("MonadViewer.Desktop.TabNImage"));
    	
    	//The Viewer contains NyadPanels displayed as a JTabbedPanes containing 
    	//JScrollPanes containing a NyadPanel each. We initiate the JTabbedPanel here
    	NyadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
    	NyadPanes.addChangeListener(new ChangeListener() 
    	{
    		@Override
            public void stateChanged(ChangeEvent e) 
            {
            	TheGUI._StatusBar.setFieldType(NyadPanelList.get(NyadPanes.getSelectedIndex()).getNyad().protoOne.getFieldType());
            }
        }							);
 
    	//Look in the conf file and determine how many nyads to initiate
    	int intCount=new Integer((String) TheGUI.IniProps.get("MonadViewer.Desktop.Default.Count"));
    	NyadPanelList=new ArrayList<NyadPanel>(intCount);
    	//Note that we initialize the NyadPanelList, but don't create a NyadPanel for it yet
    	
    	//Look in the conf file and determine how many monads in each nyad get initiated
    	int intOrd=new Integer((String) TheGUI.IniProps.get("MonadViewer.Desktop.Default.Order"));
    	
    	
    	// the j counter covers the number of nyads to be initiated.
    	// the m counter covers the number of monads in each nyad are to be initiated.
    	short j=0;
    	while (j < intCount)
    	{
    		NyadRealF aNyad=null;
    		MonadRealF aMonad=null;
    		try
	    	{
	    		aMonad=new MonadRealF("M",
	    				TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.AlgebraName"),
	    				TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.FrameName"),
	    				TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.FootName"),
	    				TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Sig"),
	    				new RealF(new DivFieldType(TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.FieldType")), 1.0f)
	    										);
	    		String cnt =new StringBuffer("N").append(j).toString();
	    		aNyad=new NyadRealF(cnt, aMonad);
    		}
    		catch (CladosMonadException em)
    		{
    			System.out.println("CladosMonad Exception found when constructing first part of the Viewer Panel");
    			System.out.println(em.getSourceMessage());
    			System.exit(-1);
    		} 
    		
    		
    		short m=1;
    		while (m<intOrd)
    		{
    			try
        		{
    				//Maybe this section should make use of Nyad's .createMonad method to ensure Foot re-use the easy way
    				
    				String nextMonadName = (new StringBuffer(aMonad.getName()).append(m)).toString();
    				String nextAlgebraName=(new StringBuffer(aMonad.getAlgebra().getAlgebraName()).append(m)).toString();
    				String nextFrameName=(new StringBuffer(aMonad.getFrameName()).append(m)).toString();
    				
    				aNyad.createMonad(	nextMonadName, 
    									nextAlgebraName, 
    									nextFrameName, 
    									TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Sig")
    								);
        		}
        		catch (CladosMonadException em)
        		{
        			System.out.println("CladosMonad Exception found when constructing the Viewer Panel");
        			System.out.println(em.getSourceMessage());
        		}
        		catch (CladosNyadException en)
        		{
        			System.out.println("CladosNyad Exception found when adding >1 Nyad to the Viewer Panel");
        			System.out.println(en.getSourceMessage());
        		}
    			m++;
    		}
    		String cnt =new StringBuffer().append(j).toString();
    		
    		//Here we finally initiate the NyadPanel because the Nyad is actually filled at this point.
    		NyadPanelList.add(j, new NyadPanel(TheGUI, aNyad));
    		//JScrollPane tempPane=new JScrollPane(NyadPanelList.get(j));
    		
    		if (tabicon != null)
    			NyadPanes.addTab(cnt, tabicon, new JScrollPane(NyadPanelList.get(j)));
    		else
    			NyadPanes.addTab(cnt, new JScrollPane(NyadPanelList.get(j)));

    		j++;
    	}
    	//and now we finally add the JTabbedPane in the center of the Viewer
    	add(NyadPanes, "Center");
    }
    /**
     * This method pushes the selected Nyad downward on the stack if possible.
     * It does NOT create any new slots in the stack. 
     */
    public	void		push()
    {
	    int size=NyadPanes.getTabCount();
	    int where=NyadPanes.getSelectedIndex();
	    if (where<size-1)
	    {
		    String otherTitle=new String(NyadPanes.getTitleAt(where+1));
		    JScrollPane otherPane=new JScrollPane((JPanel)NyadPanelList.get(where+1));

		    String thisTitle=new String(NyadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)NyadPanelList.get(where));

		    NyadPanes.setTitleAt(where, otherTitle);
		    NyadPanes.setComponentAt(where, otherPane);

		    NyadPanes.setTitleAt(where+1, thisTitle);
		    NyadPanes.setComponentAt(where+1, thisPane);

		    NyadPanel tempPanel=(NyadPanel)NyadPanelList.remove(where);
		    NyadPanelList.add(where+1, tempPanel);

		    //this.setSelectedIndex(where+1);
		    //this.revalidate();

	    }
    }

    public	void		pop()
    {
	    int where=NyadPanes.getSelectedIndex();
	    if (where>0)
	    {
		    String otherTitle=new String(NyadPanes.getTitleAt(where-1));
		    JScrollPane otherPane=new JScrollPane((JPanel)NyadPanelList.get(where-1));

		    String thisTitle=new String(NyadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)NyadPanelList.get(where));

		    NyadPanes.setTitleAt(where, otherTitle);
		    NyadPanes.setComponentAt(where, otherPane);

		    NyadPanes.setTitleAt(where-1, thisTitle);
		    NyadPanes.setComponentAt(where-1, thisPane);

		    NyadPanel tempPanel=(NyadPanel)NyadPanelList.remove(where-1);
		    NyadPanelList.add(where, tempPanel);

		    //this.setSelectedIndex(where-1);
		    //this.revalidate();
	    }
    }

    public	int			getNyadListSize()
    {
	    return NyadPanelList.size();
    }
    
    public	int			getPaneFocus()
    {
	    return NyadPanes.getSelectedIndex();
    }
    
    public	NyadPanel	getNyadPanel(int pInd)
    {
	    if (pInd<NyadPanelList.size())
		    return NyadPanelList.get(pInd);
	  
	    return null;
    }
    
    public void removeNyadPanel(int pInd)
    {
    	if (pInd<NyadPanelList.size())
    	{
    		NyadPanes.remove(pInd);
    		NyadPanelList.remove(pInd);
    	}
    }

    public	void		addNyadPanel(NyadRealF pN)
    	throws BadSignatureException, UtilitiesException
    {
    	
	    int next=NyadPanes.getTabCount();
	    String cnt=new StringBuffer().append(next).toString();
	    
	    NyadPanel newP=new NyadPanel(TheGUI, pN);
	    NyadPanelList.ensureCapacity(next+1);
	    boolean test=NyadPanelList.add(newP);
	    if (test)
	    {
	    	if (tabicon != null)
	    		NyadPanes.addTab(cnt, tabicon, new JScrollPane(newP));
	    	else
	    		NyadPanes.addTab(cnt, new JScrollPane(newP));
	    }
    }
}