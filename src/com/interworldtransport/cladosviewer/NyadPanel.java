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
	private static final long serialVersionUID = -2756670949416830884L;
	public		MonadViewer		TheGUI;

	private		JPanel 			_controlPanel;
	protected	JButton			saveButton;
	protected	JButton			editButton;
	protected	JButton			copyButton;
	protected	JButton			restoreButton;
	protected	JButton			removeButton;
	 
	private		JPanel 			_refPanel;
	public		JTextField		_name=new JTextField(10);
	public		JLabel			_foot=new JLabel();
	public		JLabel			_order=new JLabel();
	 
	private		Color			_backColor = new Color(192, 164, 192);
	private		Color			_unlockColor = new Color(255, 164, 164);
	public		ImageIcon		tabicon;
	
	public		JTabbedPane				MonadPanes;
	protected	ArrayList<MonadPanel>	MonadPanelList;
	public		NyadRealF				_repNyad;
 
/**
 * This constructor is the copy one used when a Monad alread exists
 */
	 public NyadPanel(	MonadViewer pGUI,
			 			NyadRealF pN)
	 throws 	UtilitiesException, BadSignatureException
	 {
		 super();
		 if (pGUI!=null || pN!=null)
		 {
			 TheGUI=pGUI;
			 _repNyad=pN;
			 setReferences();
			 
			 setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			 setBackground(_backColor);
			 setLayout(new BorderLayout());

			 tabicon = new ImageIcon(TheGUI.IniProps.getProperty("MonadViewer.Desktop.TabMImage"));
			 
			 createControlLayout();
			 add(_controlPanel, "West");
			 createReferenceLayout();
			 add(_refPanel, "South");
			 
			 MonadPanes=new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
			 MonadPanelList=new ArrayList<MonadPanel>(_repNyad.getMonadList().size());
			 
			 for (short j=0; j<_repNyad.getMonadList().size(); j++)
			 {
				 String count =new StringBuffer().append(j).toString();
				 MonadPanelList.add(j, new MonadPanel(TheGUI, _repNyad.getMonadList(j)));
				 JScrollPane tempPane=new JScrollPane(MonadPanelList.get(j));
				 tempPane.setWheelScrollingEnabled(true);
				 if (tabicon != null)
					 MonadPanes.addTab(count, tabicon, tempPane);
				 else
					 MonadPanes.addTab(count, tempPane);
			 }
			 
			 add(MonadPanes, "Center");
		 }
		 else
			 throw new UtilitiesException("A GUI and Nyad must be passed to this constructor");
		 
	 }

    public 	void 		setReferences()
    {
    	_name.setText(_repNyad.getName());
    	_order.setText((new StringBuffer().append(_repNyad.getMonadList().size())).toString());
    	_foot.setText(_repNyad.getFootPoint().getFootName());
    }

    public 	void		createControlLayout()
    {
    	_controlPanel=new JPanel();
    	_controlPanel.setBackground(_backColor);
    	if ((TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Object")).equals("Monad")) return;
    	
    	_controlPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    	_controlPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn = new GridBagConstraints();
    	cn.insets = new Insets(0, 0, 0, 0);
    	//cn.fill=GridBagConstraints.HORIZONTAL;
    	cn.anchor=GridBagConstraints.NORTH;
    	makeNotWritable();
    	
   	 	saveButton=new JButton("save", new ImageIcon(TheGUI.IniProps.getProperty("MonadViewer.Desktop.SaveImage")));
   	 	saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	 	saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
	 	saveButton.setPreferredSize(new Dimension(50,50));
   	 	
   	 	editButton=new JButton("edit", new ImageIcon(TheGUI.IniProps.getProperty("MonadViewer.Desktop.EditImage")));
   	 	editButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	 	editButton.setHorizontalTextPosition(SwingConstants.CENTER);
	 	editButton.setPreferredSize(new Dimension(50,50));
   	 	
   	 	copyButton=new JButton("copy", new ImageIcon(TheGUI.IniProps.getProperty("MonadViewer.Desktop.CopyImage")));
   	 	copyButton.setVerticalTextPosition(SwingConstants.BOTTOM);
   	 	copyButton.setHorizontalTextPosition(SwingConstants.CENTER);
   	 	copyButton.setPreferredSize(new Dimension(50,50));
   	 	
   	 	restoreButton=new JButton("abort", new ImageIcon(TheGUI.IniProps.getProperty("MonadViewer.Desktop.RestoreImage")));
   	 	restoreButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	 	restoreButton.setHorizontalTextPosition(SwingConstants.CENTER);
	 	restoreButton.setPreferredSize(new Dimension(50,50));
   	 	
   	 	removeButton=new JButton("erase", new ImageIcon(TheGUI.IniProps.getProperty("MonadViewer.Desktop.RemoveImage")));
   	 	removeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
   	 	removeButton.setHorizontalTextPosition(SwingConstants.CENTER);
   	 	removeButton.setPreferredSize(new Dimension(50,50));
    	
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=1;
    	cn.weighty=1;
    	
    	saveButton.addActionListener(this);
    	_controlPanel.add(saveButton, cn);
    	cn.gridy++;
    	
    	restoreButton.addActionListener(this);
    	_controlPanel.add(restoreButton, cn);
    	cn.gridy++;
    	
    	copyButton.addActionListener(this);
    	_controlPanel.add(copyButton, cn);
    	//copyButton.setEnabled(false);
    	cn.gridy++;
    	
    	removeButton.addActionListener(this);
    	_controlPanel.add(removeButton, cn);
    	cn.gridy++;
    	
    	editButton.addActionListener(this);
    	_controlPanel.add(editButton, cn);
    }
    
    public 	void		createReferenceLayout()
    {
    	_refPanel=new JPanel();
    	_refPanel.setBackground(_backColor);
    	
    	if ((TheGUI.IniProps.getProperty("MonadViewer.Desktop.Default.Object")).equals("Monad")) return;
    	
    	_refPanel.setBorder(BorderFactory.createTitledBorder("Nyad"));
    	_refPanel.setLayout(new GridBagLayout());
    	
    	GridBagConstraints cn0 = new GridBagConstraints();
    	cn0.insets = new Insets(0, 0, 0, 0);
    	//cn0.fill=GridBagConstraints.HORIZONTAL;
    	cn0.anchor=GridBagConstraints.NORTH;

    	cn0.gridx = 0;
    	cn0.gridy = 0;
    	cn0.weightx=1;
    	cn0.weighty=1;
    	
    	_refPanel.add(new JLabel("Name", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	_refPanel.add(_name, cn0);	
    	cn0.gridx++;
    	
    	_refPanel.add(new JLabel("Foot", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	_refPanel.add(_foot, cn0);
    	cn0.gridx++;
    	
    	_refPanel.add(new JLabel("Order", SwingConstants.RIGHT), cn0);
    	cn0.gridx++;
    	_refPanel.add(_order, cn0);
    }

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

    public NyadRealF 	getNyad()
    {
	    return _repNyad;
    }
    
    public int 		getOrder()
    {
    	return MonadPanelList.size();
    }
    
    public	int			getPaneFocus()
    {
	    return MonadPanes.getSelectedIndex();
    }
    
    public	MonadPanel	getMonadPanel(int pInd)
    {
	    int limit=MonadPanelList.size();
	    if (pInd<limit)
	    {
		    MonadPanel temp = (MonadPanel)MonadPanelList.get(pInd);
		    return temp;
	    }
	    return null;
    }

    public	void		addMonadPanel(MonadPanel pMP)
    {
	    int next=MonadPanes.getTabCount();
	    MonadPanelList.ensureCapacity(next+1);
	    boolean test=MonadPanelList.add(pMP);
	    if (test)
	    {
	    	if (tabicon != null)
	    		MonadPanes.addTab((new StringBuffer()).append(next).toString(), tabicon, new JScrollPane(pMP));
	    	else
	    		MonadPanes.addTab((new StringBuffer()).append(next).toString(), new JScrollPane(pMP));
	    	
	    	_order.setText((new StringBuffer()).append(next+1).toString());
	    }
    }
    
    public	void		addMonadTab(MonadRealF pM)
    throws BadSignatureException, UtilitiesException
    {
    	MonadPanel pMP=new MonadPanel(TheGUI, pM);
    	addMonadPanel(pMP); 
    }
    
    public	void		removeMonadTab(int pInd)
    {
    	int newOrder=MonadPanes.getTabCount()-1;
	    MonadPanes.remove(pInd);
	    MonadPanelList.remove(pInd);
	    _order.setText(new StringBuffer().append(newOrder).toString());
    }
    
    public 	void 		makeWritable()
    {
    	if (_refPanel!=null)
    		_refPanel.setBackground(_unlockColor);
    	_name.setEditable(true);
    	//_order.setEditable(false);
    	//_foot.setEditable(false);
    }
    
    public 	void 		makeNotWritable()
    {
    	if (_refPanel!=null)
    		_refPanel.setBackground(_backColor);
    	_name.setEditable(false);
    	//_order.setEditable(false);
    	//_foot.setEditable(false);
    }
    
    public 	void 		actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	if (command=="abort")
    	{
    		if (!_name.getText().equals("Create"))
    		{
    			setReferences();
    			getMonadPanel(0).restoreButton.doClick(100);
    			TheGUI._StatusBar.setStatusMsg("Nyad reset to stored values.\n");
    		}
    	}
    	
    	if (command=="copy")
    	{
    		TheGUI._StatusBar.setStatusMsg("Nothing here yet.\n");
    	}
    	
    	if (command=="erase")
    	{
    		if (MonadPanes.getTabCount()>1)
    		{
    			try 
    			{
    				int point = MonadPanes.getSelectedIndex();
    				removeMonadTab(point);
					_repNyad.removeMonad(point);
				} 
    			catch (CladosNyadException e) 
    			{
					e.printStackTrace();
				}
    		}
    		TheGUI._StatusBar.setStatusMsg("Monad removed from list.\n");
    	}
    	
    	if (command=="edit")
    	{
    		editButton.setText(".edit.");
    		makeWritable();
    		TheGUI._StatusBar.setStatusMsg("Nyad references unlocked... ");
    	}
    	
    	if (command==".edit.")
    	{
    		editButton.setText("edit");
    		makeNotWritable();
    		TheGUI._StatusBar.setStatusMsg(" ... and now locked\n");
    	}
    	
    	if (command=="save")
    	{
    		_repNyad.setName(_name.getText());
    	}
    }
}
