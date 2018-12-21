/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.ViewerPanel<br>
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
 * ---com.interworldtransport.cladosviewer.ViewerPanel<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer ;

import com.interworldtransport.cladosF.RealF;
import com.interworldtransport.cladosF.DivFieldType;
import com.interworldtransport.cladosG.NyadRealF;
import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

/** com.interworldtransport.cladosviewer.ViewerPanel
 * The ViewerPanel class is intended to be the main panel of the Calculator.  It
 * holds the tabbed pane that holds Nyads.  It is also aware enough to manage
 * the nyads a bit with respect to stack operations.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class ViewerPanel extends JPanel implements ActionListener
{
	public		CladosCalculator		_GUI;
    public		JTabbedPane				nyadPanes;
    private		ImageIcon				tabIcon;
    private		JPanel					_controlBar;
    private		JButton					copyNyad;
    private		JButton					newNyad;
    protected	ArrayList<NyadPanel>	nyadPanelList;
    protected	JButton					removeNyad;
    private		JButton					swapAbove;
    private		JButton					swapBelow;  
    private		Color					_backColor=new Color(255, 255, 220);
    private final	Dimension 			square = new Dimension(25,25);

/**
 * The ViewerPanel class is intended to be a tabbed pane that displays all
 * the Monad Panels.  ViewerPanel must be smart enough to
 * know what it holds and adjust the tabs when push and pop operations are
 * performed.
 */
    public 	ViewerPanel(CladosCalculator pGUI) 
    	throws 		UtilitiesException, BadSignatureException
    {
    	super();
    	
    	_GUI=pGUI;
    	setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    	setBackground(_backColor);
    	setLayout(new BorderLayout());
    	
  	    createControlLayout();
  	    createLayout();
    }

    @Override
	public void actionPerformed(ActionEvent event) 
	{
		String command = event.getActionCommand();
		
		if (command.equals("push"))
    		push();		//Swaps the currently selected nyad with the one below it
    	
    	if (command.equals("pop"))
    		pop();		//Swaps the currently selected nyad with the one above it
		
    	if (command.equals("copy"))
	    	copyNyadCommand();			//Clone the selected nyad and place it at the end of the stack
    	
    	if (command.equals("erase"))
    		eraseNyadCommand();			//Remove the selected nyad from the stack
    	
    	if (command.equals("create"))
    		createCommand();			//Create a new monad for the selected nyad OR a whole new nyad
	}
    
    public	int			getNyadListSize()
    {
	    return nyadPanelList.size();
    }
    
    public	NyadPanel	getNyadPanel(int pInd)
    {
	    if (pInd<nyadPanelList.size() && pInd >=0)
		    return nyadPanelList.get(pInd);
	  
	    return null;
    }
    
    public	ArrayList<NyadPanel> getNyadPanels()
    {
	    return nyadPanelList;
    }

    public	int			getPaneFocus()
    {
	    return nyadPanes.getSelectedIndex();
    }

    private void createControlLayout()
    {
    	_controlBar=new JPanel();
  	    _controlBar.setLayout(new GridBagLayout());
  	    _controlBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
  	    _controlBar.setBackground(_backColor);
  	    
    	GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);
		cn.anchor=GridBagConstraints.NORTH;
		
		cn.gridx = 0;
		cn.gridy = 0;
		
		cn.weightx=0;
		cn.weighty=0;
		cn.gridheight=1;
		cn.gridwidth=1;
    	swapBelow=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Push")));
    	swapBelow.setActionCommand("push");
    	swapBelow.setToolTipText("push nyad down on stack");
    	swapBelow.setPreferredSize(square);
    	swapBelow.setBorder(BorderFactory.createEtchedBorder(0));
    	swapBelow.addActionListener(this);
    	_controlBar.add(swapBelow, cn);
    	cn.gridy++;
    	
    	swapAbove=new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Pop")));
    	swapAbove.setActionCommand("pop");
    	swapAbove.setToolTipText("pop nyad up on stack");
    	swapAbove.setPreferredSize(square);
    	swapAbove.setBorder(BorderFactory.createEtchedBorder(0));
    	swapAbove.addActionListener(this);
    	_controlBar.add(swapAbove, cn);
		cn.gridy++;
    	
    	copyNyad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Copy")));
    	copyNyad.setActionCommand("copy");
    	copyNyad.setToolTipText("copy nyad to end of stack");
    	copyNyad.setPreferredSize(square);
    	copyNyad.setBorder(BorderFactory.createEtchedBorder(0));
    	copyNyad.addActionListener(this);
    	_controlBar.add(copyNyad, cn);
    	cn.gridy++;
    	
    	removeNyad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Remove")));
    	removeNyad.setActionCommand("erase");
    	removeNyad.setToolTipText("remove nyad from stack");
    	removeNyad.setPreferredSize(square);
    	removeNyad.setBorder(BorderFactory.createEtchedBorder(0));
    	removeNyad.addActionListener(this);
    	_controlBar.add(removeNyad, cn);
    	cn.gridy++;
    	
    	newNyad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Create")));
    	newNyad.setActionCommand("create");
    	newNyad.setToolTipText("create new nyad");
    	newNyad.setPreferredSize(square);
    	newNyad.setBorder(BorderFactory.createEtchedBorder(0));
    	newNyad.addActionListener(this);
    	_controlBar.add(newNyad, cn);
    	cn.gridy++;
    	
    	cn.weighty=1;
    	_controlBar.add(new JLabel(), cn);
    	
    	add(_controlBar,"East");
    }
    
    private void copyNyadCommand()
    {
    	if (getNyadListSize()>0)
		{
			try
			{
				NyadRealF focusNyad=getNyadPanel(getPaneFocus()).getNyad();
				String buildName=new StringBuffer(focusNyad.getName()).append("_c").toString();
				NyadRealF newNyadCopy=new NyadRealF(buildName, focusNyad);
				addNyadTab(newNyadCopy);
			}
			catch (UtilitiesException e)
			{
				_GUI._StatusBar.setStatusMsg("\t\tcould not create copy from toolbar.\n");
			}
			catch (BadSignatureException es)
			{
				_GUI._StatusBar.setStatusMsg("\t\tcould not create copy from toolbar due to signature issue.\n");
			} 
			catch (CladosNyadException e) 
			{
				_GUI._StatusBar.setStatusMsg("\t\tcould not create copy from toolbar because nyad was malformed.\n");
			}
		}
    }
    
    private void createCommand()
    {
    	try
		{
			CreateDialog.createNyad(_GUI);	
		}
		catch (UtilitiesException e)
		{
			//Do nothing.  Exception implies user doesn't get to create
			//a new Monad, so nothing is the correct action.
			System.out.println("\t\tCouldn't construct create dialog.");
		}
		catch (BadSignatureException es)
		{
			//Do nothing.  Exception implies user doesn't get to create
			//a new Monad, so nothing is the correct action.
			System.out.println("\\t\\tCouldn't construct create dialog.");
		}
		catch (CladosMonadException em)
		{
			//Do nothing.  Exception implies user doesn't get to create
			//a new Monad, so nothing is the correct action.
			System.out.println("\\t\\tCouldn't construct create dialog.");
		}
    }
    
    private 	void 		createLayout()
    throws 		UtilitiesException, BadSignatureException
    {
    	//Get the nyad tab image for the nyad panes being constructed
    	tabIcon = new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.TabN"));
    	
    	//The Viewer contains NyadPanels displayed as a JTabbedPanes containing 
    	//JScrollPanes containing a NyadPanel each. We initiate the JTabbedPanel here
    	nyadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
    	nyadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    	nyadPanes.addChangeListener(new ChangeListener() 
    	{
    		@Override
            public void stateChanged(ChangeEvent e) 
            {
    			if (nyadPanes.getTabCount()>0)
    				_GUI._StatusBar.setFieldType(nyadPanelList.get(nyadPanes.getSelectedIndex()).getNyad().protoOne.getFieldType());
    			else
    				_GUI._StatusBar.setFieldType(null);
            }
        }							);
 
    	//Look in the conf file and determine how many nyads to initiate
    	int intCount=Integer.parseInt(_GUI.IniProps.getProperty("Desktop.Default.Count"));
    	nyadPanelList=new ArrayList<NyadPanel>(intCount);
    	//Note that we initialize the NyadPanelList, but don't create a NyadPanel for it yet
    	
    	//Look in the conf file and determine how many monads in each nyad get initiated
    	int intOrd=Integer.parseInt(_GUI.IniProps.getProperty("Desktop.Default.Order"));
    	
    	
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
	    				_GUI.IniProps.getProperty("Desktop.Default.AlgebraName"),
	    				_GUI.IniProps.getProperty("Desktop.Default.FrameName"),
	    				_GUI.IniProps.getProperty("Desktop.Default.FootName"),
	    				_GUI.IniProps.getProperty("Desktop.Default.Sig"),
	    				new RealF(new DivFieldType(_GUI.IniProps.getProperty("Desktop.Default.FieldType")), 1.0f)
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
    									_GUI.IniProps.getProperty("Desktop.Default.Sig")
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
    		nyadPanelList.add(j, new NyadPanel(_GUI, aNyad));
    		//JScrollPane tempPane=new JScrollPane(NyadPanelList.get(j));
    		
    		if (tabIcon != null)
    			nyadPanes.addTab(cnt, tabIcon, new JScrollPane(nyadPanelList.get(j)));
    		else
    			nyadPanes.addTab(cnt, new JScrollPane(nyadPanelList.get(j)));

    		j++;
    	}
    	//and now we finally add the JTabbedPane in the center of the Viewer
    	add(nyadPanes, "Center");
    }

    private void eraseNyadCommand()
	{
		if (nyadPanes.getTabCount()>0)
		{
			int point = nyadPanes.getSelectedIndex();
			removeNyadPanel(point);
		}
	}

	private	void		pop()
    {
	    int where=nyadPanes.getSelectedIndex();
	    if (where>0)
	    {
		    String otherTitle=new String(nyadPanes.getTitleAt(where-1));
		    JScrollPane otherPane=new JScrollPane((JPanel)nyadPanelList.get(where-1));

		    String thisTitle=new String(nyadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)nyadPanelList.get(where));

		    nyadPanes.setTitleAt(where, otherTitle);
		    nyadPanes.setComponentAt(where, otherPane);

		    nyadPanes.setTitleAt(where-1, thisTitle);
		    nyadPanes.setComponentAt(where-1, thisPane);

		    NyadPanel tempPanel=(NyadPanel)nyadPanelList.remove(where-1);
		    nyadPanelList.add(where, tempPanel);

		    revalidate();
	    }
    }
	
	/**
     * This method pushes the selected Nyad downward on the stack if possible.
     * It does NOT create any new slots in the stack. 
     */
    private	void		push()
    {
	    int size=nyadPanes.getTabCount();
	    int where=nyadPanes.getSelectedIndex();
	    if (where<size-1)
	    {
		    String otherTitle=new String(nyadPanes.getTitleAt(where+1));
		    JScrollPane otherPane=new JScrollPane((JPanel)nyadPanelList.get(where+1));

		    String thisTitle=new String(nyadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)nyadPanelList.get(where));

		    nyadPanes.setTitleAt(where, otherTitle);
		    nyadPanes.setComponentAt(where, otherPane);

		    nyadPanes.setTitleAt(where+1, thisTitle);
		    nyadPanes.setComponentAt(where+1, thisPane);

		    NyadPanel tempPanel=(NyadPanel)nyadPanelList.remove(where);
		    nyadPanelList.add(where+1, tempPanel);

		    revalidate();
	    }
    }
   
    protected	void		addNyadPanel(NyadPanel newP)
    {
    	int next=nyadPanes.getTabCount();
	    String cnt=new StringBuffer().append(next).toString();
	    nyadPanelList.ensureCapacity(next+1);
	    boolean test=nyadPanelList.add(newP);
	    if (test)
	    	if (tabIcon != null)
	    		nyadPanes.addTab(cnt, tabIcon, new JScrollPane(newP));
	    	else
	    		nyadPanes.addTab(cnt, new JScrollPane(newP));
	    
	    _GUI.pack();
    }
    
    protected	void		addNyadTab(NyadRealF pN)
    	throws BadSignatureException, UtilitiesException
    {
    	NyadPanel newP=new NyadPanel(_GUI, pN);
    	addNyadPanel(newP);
    }
    
	protected void removeNyadPanel(int pInd)
    {
    	if (pInd<nyadPanelList.size() && pInd>=0)
    	{
    		nyadPanelList.remove(pInd);
    		nyadPanes.remove(pInd);
    	}
    	_GUI.pack();
    } 
}