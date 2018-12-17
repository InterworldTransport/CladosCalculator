/**
 * <h2>Copyright</h2> © 2018 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.NyadPanel<br>
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
 * ---com.interworldtransport.cladosviewer.NyadPanel<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer ;

import com.interworldtransport.cladosG.*;
import com.interworldtransport.cladosGExceptions.*;
import com.interworldtransport.cladosviewerExceptions.UtilitiesException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.util.*;


/** com.interworldtransport.cladosviewer.NyadPanel
 * The ViewerPanel class is intended to be the main panel of the Monad Viewer
 * utility.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class NyadPanel extends JPanel implements ActionListener
{
	public		CladosCalculator		_GUI;
	public		JTabbedPane				monadPanes;
	public		NyadRealF				repNyad;
	private		Color					_backColor = new Color(212, 200, 212);
	private		JPanel 					_controlPanel;
	private		JPanel 					_controlPanel2;
	private		JPanel 					_refPanel;
	private		Color					_unlockColor = new Color(255, 192, 192);
	private		JButton					copyButton;
	private		JButton					editButton;
	private	JButton					newMonad;
	private		JButton					removeButton;
	private		JButton					restoreButton;
	private		JButton					saveButton;
	private		ImageIcon				tabIcon;
	protected	ArrayList<MonadPanel>	monadPanelList;
	protected	JLabel					nyadFoot=new JLabel();
	protected	JTextField				nyadName=new JTextField(40);
	protected	JLabel					nyadOrder=new JLabel();
	protected	JButton					swapAbove;
    protected	JButton					swapBelow; 
 
/**
 * This constructor is the copy one used when a Monad alread exists
 */
	 public NyadPanel(	CladosCalculator pGUI,
			 			NyadRealF pN)
	 throws 	UtilitiesException, BadSignatureException
	 {
		 super();
		 if (pGUI!=null || pN!=null)
		 {
			 _GUI=pGUI;
			 repNyad=pN;
			 setReferences();
			 
			 setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			 setBackground(_backColor);
			 setLayout(new BorderLayout());

			 tabIcon = new ImageIcon(_GUI.IniProps.getProperty("MonadViewer.Desktop.TabMImage"));
			 
			 createControlLayout();
			 createControlLayout2();
			 createReferenceLayout();
			 
			 monadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
			 monadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			 monadPanelList=new ArrayList<MonadPanel>(repNyad.getMonadList().size());
			 
			 for (short j=0; j<repNyad.getMonadList().size(); j++)
			 {
				 String count =new StringBuffer().append(j).toString();
				 monadPanelList.add(j, new MonadPanel(_GUI, repNyad.getMonadList(j)));
				 JScrollPane tempPane=new JScrollPane(monadPanelList.get(j));
				 tempPane.setWheelScrollingEnabled(true);
				 if (tabIcon != null)
					 monadPanes.addTab(count, tabIcon, tempPane);
				 else
					 monadPanes.addTab(count, tempPane);
			 }
			 
			 add(monadPanes, "Center");
		 }
		 else
			 throw new UtilitiesException("A GUI and Nyad must be passed to this constructor");
		 
	 }

    public 	void 		actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	
    	if (command.equals("push"))
    		push();		//Swaps the currently selected nyad with the one below it
    	
    	if (command.equals("pop"))
    		pop();		//Swaps the currently selected nyad with the one above it
    	
    	if (command=="save")
    	{
    		if (nyadName.getText() != repNyad.getName())
				repNyad.setName(nyadName.getText());
    		
    		_GUI._StatusBar.setStatusMsg(" changes saved...");
    		command=".edit.";
    	}
    	if (command=="abort")
    	{
    		setReferences();
    		_GUI._StatusBar.setStatusMsg("Nyad reset to stored values.\n");
    		command=".edit.";
    	}
    	
    	if (command=="copy")
    		copyMonadCommand();

    	if (command=="erase")
    		removeMonadCommand();
    	
    	if (command.equals("create"))
    		createCommand();			//Create a new monad for the selected nyad OR a whole new nyad
    	
    	if (command=="edit")
    	{
    		editButton.setActionCommand(".edit.");
    		editButton.setToolTipText("end edits w/o save");
    		saveButton.setEnabled(true);
        	restoreButton.setEnabled(true);
    		makeWritable();
    		_GUI._StatusBar.setStatusMsg("Nyad references unlocked... ");
    	}
    	
    	if (command==".edit.")
    	{
    		editButton.setActionCommand("edit");
        	editButton.setToolTipText("start edits");
    		saveButton.setEnabled(false);
        	restoreButton.setEnabled(false);
    		makeNotWritable();
    		_GUI._StatusBar.setStatusMsg(" ... and now locked\n");
    	}
    }

    public	void		addMonadPanel(MonadPanel pMP)
    {
	    int next=monadPanes.getTabCount();
	    monadPanelList.ensureCapacity(next+1);
	    boolean test=monadPanelList.add(pMP);
	    if (test)
	    {
	    	if (tabIcon != null)
	    		monadPanes.addTab((new StringBuffer()).append(next).toString(), tabIcon, new JScrollPane(pMP));
	    	else
	    		monadPanes.addTab((new StringBuffer()).append(next).toString(), new JScrollPane(pMP));
	    	
	    	nyadOrder.setText((new StringBuffer()).append(next+1).toString());
	    }
    }
    
    public	void		addMonadTab(MonadRealF pM) throws UtilitiesException
    {
    	MonadPanel pMP=new MonadPanel(_GUI, pM);
    	addMonadPanel(pMP); 
    }
    
    public	int			getMonadListSize()
    {
	    return monadPanelList.size();
    }

    public	MonadPanel	getMonadPanel(int pInd)
    {
	    int limit=monadPanelList.size();
	    if (pInd<limit)
	    {
		    MonadPanel temp = (MonadPanel)monadPanelList.get(pInd);
		    return temp;
	    }
	    return null;
    }

    public NyadRealF 	getNyad()
    {
	    return repNyad;
    }
    public int 		getOrder()
    {
    	return monadPanelList.size();
    }
    public	int			getPaneFocus()
    {
	    return monadPanes.getSelectedIndex();
    }
    
    public 	void 		makeNotWritable()
    {
    	if (_refPanel!=null)
    		_refPanel.setBackground(_backColor);
    	nyadName.setEditable(false);
    	//_order.setEditable(false);
    	//_foot.setEditable(false);
    }
    
    public 	void 		makeWritable()
    {
    	if (_refPanel!=null)
    		_refPanel.setBackground(_unlockColor);
    	nyadName.setEditable(true);
    }
    
    private	void		pop()
    {
	    int where=monadPanes.getSelectedIndex();
	    if (where>0)
	    {
		    String otherTitle=new String(monadPanes.getTitleAt(where-1));
		    JScrollPane otherPane=new JScrollPane((JPanel)monadPanelList.get(where-1));

		    String thisTitle=new String(monadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)monadPanelList.get(where));

		    monadPanes.setTitleAt(where, otherTitle);
		    monadPanes.setComponentAt(where, otherPane);

		    monadPanes.setTitleAt(where-1, thisTitle);
		    monadPanes.setComponentAt(where-1, thisPane);

		    MonadPanel tempPanel=(MonadPanel)monadPanelList.remove(where-1);
		    monadPanelList.add(where, tempPanel);
	    }
    }

    private	void		push()
    {
	    int size=monadPanes.getTabCount();
	    int where=monadPanes.getSelectedIndex();
	    if (where<size)
	    {
		    String otherTitle=new String(monadPanes.getTitleAt(where+1));
		    JScrollPane otherPane=new JScrollPane((JPanel)monadPanelList.get(where+1));

		    String thisTitle=new String(monadPanes.getTitleAt(where));
		    JScrollPane thisPane=new JScrollPane((JPanel)monadPanelList.get(where));

		    monadPanes.setTitleAt(where, otherTitle);
		    monadPanes.setComponentAt(where, otherPane);

		    monadPanes.setTitleAt(where+1, thisTitle);
		    monadPanes.setComponentAt(where+1, thisPane);

		    MonadPanel tempPanel=(MonadPanel)monadPanelList.remove(where);
		    monadPanelList.add(where+1, tempPanel);
	    }
    }
    
    public	void		removeMonadTab(int pInd)
    {
    	int newOrder=monadPanes.getTabCount()-1;
	    monadPanes.remove(pInd);
	    monadPanelList.remove(pInd);
	    nyadOrder.setText(new StringBuffer().append(newOrder).toString());
    }
    
    private void		copyMonadCommand()
    {
    	if (getMonadListSize()>0)
		{
			try
			{
				MonadRealF focusMonad=getMonadPanel(getPaneFocus()).getMonad();
				String buildName=new StringBuffer(focusMonad.getName()).append("_c").toString();
				String buildAlgName =new StringBuffer(focusMonad.getAlgebra().getAlgebraName()).append("_c").toString();
				String buildFrameName = new StringBuffer(focusMonad.getFrameName()).append("_c").toString();
				
				AlgebraRealF buildAlg = new AlgebraRealF(	buildAlgName, 
															focusMonad.getAlgebra().getFoot(),
															focusMonad.getAlgebra().getGProduct());
				MonadRealF newMonadCopy=new MonadRealF(	buildName, 
														buildAlg,
														buildFrameName,
														focusMonad.getCoeff());
				repNyad.appendMonad(newMonadCopy);
				addMonadTab(newMonadCopy);
			}
			catch (UtilitiesException e)
			{
				_GUI._StatusBar.setStatusMsg("\t\tcould not create copy from toolbar.\n");
			}
			catch (CladosMonadException e) 
			{
				_GUI._StatusBar.setStatusMsg("\t\tcould not create copy because monad was malformed.\n");
			} 
			catch (CladosNyadException e) 
			{
				_GUI._StatusBar.setStatusMsg("\t\tcould not append monad because nyad objected.\n");
			}
		}
    }
    
    private void createCommand()
    {
    	try
		{
			CreateDialog.createMonad(_GUI);	
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
    
    private 	void		createControlLayout()
    {
    	Dimension square = new Dimension(50,50);
    	_controlPanel=new JPanel();
    	_controlPanel.setBackground(_backColor);
    	if ((_GUI.IniProps.getProperty("MonadViewer.Desktop.Default.Object")).equals("Monad")) return;
    	
    	_controlPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	_controlPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn = new GridBagConstraints();
    	cn.insets = new Insets(0, 0, 0, 0);
    	//cn.fill=GridBagConstraints.HORIZONTAL;
    	cn.anchor=GridBagConstraints.NORTH;
    	makeNotWritable();
    	
   	 	saveButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("MonadViewer.Desktop.SaveImage")));
   	 	saveButton.setActionCommand("save");
   	 	saveButton.setToolTipText("save edits to nyad");
   	 	saveButton.setEnabled(false);
	 	saveButton.setPreferredSize(square);
   	 	
   	 	editButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("MonadViewer.Desktop.EditImage")));
    	editButton.setActionCommand("edit");
    	editButton.setToolTipText("start edits on nyad");
	 	editButton.setPreferredSize(square);
	 	
	 	restoreButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("MonadViewer.Desktop.RestoreImage")));
    	restoreButton.setActionCommand("abort");
    	restoreButton.setToolTipText("abandon edits to nyad");
    	restoreButton.setEnabled(false);
	 	restoreButton.setPreferredSize(square);
    	
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=0;
    	cn.weighty=0;
    	
    	editButton.addActionListener(this);
    	_controlPanel.add(editButton, cn);
    	cn.gridy++;
    	
    	saveButton.addActionListener(this);
    	_controlPanel.add(saveButton, cn);
    	cn.gridy++;
    	
    	restoreButton.addActionListener(this);
    	_controlPanel.add(restoreButton, cn);
    	cn.gridy++;
    
    	cn.weighty=1;
    	_controlPanel.add(new JLabel(), cn);
    	
    	add(_controlPanel, "West");
    }
    
    private void createControlLayout2()
    {
    	_controlPanel2=new JPanel();
    	_controlPanel2.setLayout(new GridBagLayout());
    	_controlPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	_controlPanel2.setBackground(_backColor);
  	    
    	Dimension square = new Dimension(50,50);
    	GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);
		cn.anchor=GridBagConstraints.NORTH;
		
		cn.gridx = 0;
		cn.gridy = 0;
		
		cn.weightx=0;
		cn.weighty=0;
		cn.gridheight=1;
		cn.gridwidth=1;
    	swapBelow=new JButton(new ImageIcon(_GUI.IniProps.getProperty("MonadViewer.Desktop.PushImage")));
    	swapBelow.setActionCommand("push");
    	swapBelow.setToolTipText("push monad down on stack");
    	swapBelow.setPreferredSize(square);
    	swapBelow.addActionListener(this);
    	_controlPanel2.add(swapBelow, cn);
    	cn.gridy++;
    	
    	swapAbove=new JButton(new ImageIcon(_GUI.IniProps.getProperty("MonadViewer.Desktop.PopImage")));
    	swapAbove.setActionCommand("pop");
    	swapAbove.setToolTipText("pop monad up on stack");
    	swapAbove.setPreferredSize(square);
    	swapAbove.addActionListener(this);
    	_controlPanel2.add(swapAbove, cn);
		cn.gridy++;
    	
		copyButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("MonadViewer.Desktop.CopyImage")));
   	 	copyButton.setActionCommand("copy");
   	 	copyButton.setToolTipText("copy monad to end of stack");
   	 	copyButton.setPreferredSize(square);
   	 	copyButton.addActionListener(this);
   	 	_controlPanel2.add(copyButton, cn);
    	cn.gridy++;
    	
    	removeButton=new JButton(new ImageIcon(_GUI.IniProps.getProperty("MonadViewer.Desktop.RemoveImage")));
   	 	removeButton.setActionCommand("erase");
   	 	removeButton.setToolTipText("remove monad from stack");
   	 	removeButton.setPreferredSize(square);
   	 	removeButton.addActionListener(this);
    	_controlPanel2.add(removeButton, cn);
    	cn.gridy++;
    	
    	newMonad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("MonadViewer.Desktop.CreateImage")));
    	newMonad.setActionCommand("create");
    	newMonad.setToolTipText("create new monad");
    	newMonad.setPreferredSize(square);
    	newMonad.addActionListener(this);
    	_controlPanel2.add(newMonad, cn);
    	cn.gridy++;
    	
    	cn.weighty=1;
    	_controlPanel2.add(new JLabel(), cn);
    	
    	add(_controlPanel2,"East");
    }
    
    private 	void		createReferenceLayout()
    {
    	_refPanel=new JPanel();
    	_refPanel.setBackground(_backColor);
    	
    	if ((_GUI.IniProps.getProperty("MonadViewer.Desktop.Default.Object")).equals("Monad")) return;
    	
    	_refPanel.setBorder(BorderFactory.createTitledBorder("nyad"));
    	_refPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn0 = new GridBagConstraints();
    	//cn0.insets = new Insets(5, 5, 5, 5);
    	//cn0.fill=GridBagConstraints.HORIZONTAL;
    	cn0.anchor=GridBagConstraints.WEST;

    	cn0.gridx = 0;
    	cn0.gridy = 0;
    	cn0.weightx=0;
    	cn0.weighty=0;
    	
    	_refPanel.add(new JLabel("Name ", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	nyadName.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	cn0.weightx=1;
    	_refPanel.add(nyadName, cn0);	
    	cn0.gridx++;
    	
    	cn0.weightx=0;
    	cn0.ipadx=20;
    	_refPanel.add(new JLabel("Foot ", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	nyadFoot.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	nyadFoot.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	_refPanel.add(nyadFoot, cn0);
    	cn0.gridx++;
    	
    	_refPanel.add(new JLabel("Order ", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	//_order.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
    	nyadOrder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	_refPanel.add(nyadOrder, cn0);
    	
    	add(_refPanel, "South");
    }
    
    private void		removeMonadCommand()
    {
    	if (monadPanes.getTabCount()>1)
		{
			try 
			{
				int point = monadPanes.getSelectedIndex();
				repNyad.removeMonad(point);
				removeMonadTab(point);
				_GUI._StatusBar.setStatusMsg("\tselected monad removed from list.\n");
			} 
			catch (CladosNyadException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			_GUI._GeometryDisplay.removeNyad.doClick();
			_GUI._StatusBar.setStatusMsg("\tselected monad was last in the stack, so nyad removed.\n");
		}
    }
    
    private 	void 		setReferences()
    {
    	nyadName.setText(repNyad.getName());
    	nyadOrder.setText((new StringBuffer().append(repNyad.getMonadList().size())).toString());
    	nyadFoot.setText(repNyad.getFootPoint().getFootName());
    }
}
