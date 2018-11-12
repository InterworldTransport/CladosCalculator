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
import com.interworldtransport.clados.*;

import java.awt.*;
import javax.swing.*;
import java.util.*;

/** com.interworldtransport.cladosviewer.ViewerPanel
 * The ViewerPanel class is intended to be the main panel of the Calculator.  It
 * holds the tabbed pane that holds Nyads.  It is also aware enough to manage
 * the nyads a bit with respect to stack operations.
 * <p>
 * @version 0.80, $Date: 2005/08/25 06:36:13 $
 * @author Dr Alfred W Differ
 */

 public class ViewerPanel extends JPanel
{
    public		ImageIcon		tabicon;
    public		MonadViewer		TheGUI;
    public		JTabbedPane		NyadPanes;
    protected	ArrayList		NyadPanelList;

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
    	if (pGUI!=null)
    	{
    		this.TheGUI=pGUI;
    		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    		this.setBackground(new Color(200, 200, 200));
    		this.setLayout(new BorderLayout());
    		
    		this.createLayout();
    	}
    	else
    	{
    		System.out.println("A GUI must be passed to the StatusLine");
    		System.exit(0);
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

    public 	void 		createLayout()
    throws 		UtilitiesException, BadSignatureException
    {
    	
    	String logoFile=TheGUI.IniProps.getProperty("MonadViewer.Desktop.TabImage2");
    	//TheGUI.StatusLine.setStatusMsg("Tab Icon File(Viewer): "+logoFile+"\n");
    	this.tabicon = createImageIcon(logoFile);
    	
    	this.NyadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
    	//this.NyadPanelList=new ArrayList(2);
    	
    	String tempCount=TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Count");
    	int intCount=new Integer(tempCount).intValue();
    	this.NyadPanelList=new ArrayList(intCount);
    	
    	String tempOrd=TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Order");
    	int intOrd=new Integer(tempOrd).intValue();
    	
    	
    	for (int j=0; j<intCount; j++)
    	{
    		String count=new Integer(j).toString();
    		Monad check=new Monad(	"M0",
									"test0",
									"basic",
									"basic",
									TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Sig"));
    		Nyad check2=new Nyad("N"+count, check);
    		try
    		{
    			if (intOrd>1)
        		{
        			for (int m=1; m<intOrd; m++)
            		{
        				String countm=new Integer(m).toString();
            			check2.addMonad(new Monad(	"M"+countm,
    												"test"+countm,
    												"basic",
    												"basic",
    												TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Sig")
    											)
            							);
            		}
        		}	
    		}
    		catch (ListAppendException e)
    		{
    			//Do nothing.  Let the Nyad be of order 1 for now
    		}
    		NyadPanel tempPanel=new NyadPanel(TheGUI, check2);
    		NyadPanelList.add(j, tempPanel);
    		JScrollPane tempPane=new JScrollPane((JPanel)NyadPanelList.get(j));
    		NyadPanes.addTab(count, tabicon, tempPane);
    	}
    	
    	this.add(NyadPanes, "Center");
    }

    public	void		push()
    {
	    int size=NyadPanes.getTabCount();
	    int where=NyadPanes.getSelectedIndex();
	    if (where<size)
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
	    int limit=NyadPanelList.size();
	    if (pInd<limit)
	    {
		    NyadPanel temp = (NyadPanel)NyadPanelList.get(pInd);
		    return temp;
	    }
	    else return null;
    }

    public	void		removeTab(int pInd)
    {
	    NyadPanes.remove(pInd);
	    NyadPanel temp=(NyadPanel)NyadPanelList.remove(pInd);
	    temp=null;
    }

    public	void		addSTab(Nyad pN)
    	throws BadSignatureException, UtilitiesException
    {
	    int next=NyadPanes.getTabCount();
	    String count=new Integer(next).toString();

	    //Nyad tempN=new Nyad("0", pM);
	    
	    NyadPanel newP=new NyadPanel(TheGUI, pN);
	    NyadPanelList.ensureCapacity(next+1);
	    boolean test=NyadPanelList.add(newP);
	    if (test)
	    {
	    	JScrollPane tempPane=new JScrollPane(newP);
	    	NyadPanes.addTab(count, tabicon, tempPane);
	    }
    }

}