/**
 * <h2>Copyright</h2> © 2021 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.NyadPanel<br>
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
 * ---org.interworldtransport.cladosviewer.NyadPanel<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosF.Field;
import org.interworldtransport.cladosF.Normalizable;
import org.interworldtransport.cladosF.UnitAbstract;
import org.interworldtransport.cladosG.CladosGBuilder;
import org.interworldtransport.cladosG.Monad;
import org.interworldtransport.cladosG.Nyad;
import org.interworldtransport.cladosGExceptions.BadSignatureException;
import org.interworldtransport.cladosGExceptions.CladosMonadException;
import org.interworldtransport.cladosGExceptions.CladosNyadException;
import org.interworldtransport.cladosGExceptions.GeneratorRangeException;
import org.interworldtransport.cladosviewerExceptions.UtilitiesException;

/**
 * The NyadPanel is intended to be the display panel of for a nyad. Simple
 * enough since most of the functions available at the nyad level are stack
 * related or comparisons or tests.
 * <p>
 * The nyad represented by the panel is stored in a private data element.
 * <p>
 * 
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public class NyadPanel<T extends UnitAbstract & Field & Normalizable> extends JPanel implements ActionListener {
	private static final long serialVersionUID = 377115956906661646L;
	public CladosCalculator _GUI;
	private JButton btnEditMonad;
	private JButton btnSaveEdits;
	private JButton btnUndoEdits;
	private final Color clrBackColor = new Color(212, 200, 212);
	private final Color clrUnlockColor = new Color(255, 192, 192);
	private JLabel nyadAlgOrder = new JLabel();
	private JLabel nyadFoot = new JLabel();
	private JTextField nyadName = new JTextField(20);
	private JLabel nyadOrder = new JLabel();
	private JPanel pnlRefPanel;
	private Nyad repNyad;
	private final Dimension square = new Dimension(25, 25);
	private ImageIcon tabIcon;
	protected ArrayList<MonadPanel<T>> monadPanelList;
	protected JTabbedPane monadPanes;
	protected CladosField repMode;

	/**
	 * The NyadPanel class is intended to be contain a cladosG Nyad in order to
	 * offer its parts for display and manipulation by the calculator
	 * 
	 * @param pGUI CladosCalculator This is just a reference to the owner
	 *             application so error messages can be presented.
	 * @param pN   Nyad This is a reference to the nyad to be displayed and
	 *             manipulated.
	 * @throws UtilitiesException    This is the general exception. Could be any
	 *                               miscellaneous issue. Ready the message to see.
	 * @throws BadSignatureException This exception is thrown when one of the monad
	 *                               panels can't accept the string signature
	 *                               offered. That happens when something other than
	 *                               '+' or '-' is used... or maybe when signature
	 *                               is too long. Remember that blade count is
	 *                               currently tracked with a short integer. {--****
	 */
	public NyadPanel(CladosCalculator pGUI, Nyad pN) throws UtilitiesException, BadSignatureException {
		super();
		if (pGUI == null)
			throw new UtilitiesException("A GUI must be passed to a NyadPanel");
		else if (pN == null)
			throw new UtilitiesException("A Nyad must be passed to this NyadPanel constructor");
		_GUI = pGUI;
		repNyad = pN;
		repMode = pN.getMode();

		setReferences();

		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setBackground(clrBackColor);
		setLayout(new BorderLayout());

		tabIcon = new ImageIcon(this.getClass().getResource("/icons/M.png"));

		createEditLayout();
		createStackLayout();
		createReferenceLayout();

		monadPanes = new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
		monadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		monadPanelList = new ArrayList<MonadPanel<T>>(repNyad.getMonadList().size());

		for (Monad point : repNyad.getMonadList()) {
			short j = (short) repNyad.getMonadList().indexOf(point);
			monadPanelList.add(j, new MonadPanel<T>(_GUI, point));
			JScrollPane tempPane = new JScrollPane(monadPanelList.get(j));
			tempPane.setWheelScrollingEnabled(true);
			monadPanes.addTab(Short.toString(j), tabIcon, tempPane);
		}

		add(monadPanes, JTabbedPane.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "copy" -> copyMonadCommand(); // It's a little long
		case "erase" -> removeMonadCommand();
		case "create" -> CreateDialog.createMonad(_GUI, repMode);
		case "push" -> push(); // Swaps the currently selected nyad with the one below it
		case "pop" -> pop(); // Swaps the currently selected nyad with the one above it
		case "save" -> {
			setRepName();
			btnEditMonad.setActionCommand("edit");
			btnEditMonad.setToolTipText("start edits");
			btnSaveEdits.setEnabled(false);
			btnUndoEdits.setEnabled(false);
			makeNotWritable();
		}
		case "abort" -> {
			setReferences();
			btnEditMonad.setActionCommand("edit");
			btnEditMonad.setToolTipText("start edits");
			btnSaveEdits.setEnabled(false);
			btnUndoEdits.setEnabled(false);
			makeNotWritable();
		}
		case "edit" -> {
			btnEditMonad.setActionCommand(".edit.");
			btnEditMonad.setToolTipText("end edits w/o save");
			btnSaveEdits.setEnabled(true);
			btnUndoEdits.setEnabled(true);
			makeWritable();
		}
		case ".edit." -> {
			btnEditMonad.setActionCommand("edit");
			btnEditMonad.setToolTipText("start edits");
			btnSaveEdits.setEnabled(false);
			btnUndoEdits.setEnabled(false);
			makeNotWritable();
		}
		default -> ErrorDialog.show("No detectable command processed.", "Action At NyadPanel Attempted");
		}
	}

	/**
	 * This method supports adding a monad panel to the nyad's list of monads. Be
	 * aware that no tests are performed to prevent the panel being added. That
	 * means the nyad might change from strong to weak without notice by this panel.
	 * <p>
	 * The difference between this one and the other by a similar name is this one
	 * receives a monad and constructs the appropriate panel.
	 * <p>
	 * 
	 * @param pM Monad This is the Monad to use to construct a MonadPanel to be
	 *           appended to this NyadPanel.
	 */
	public void addMonadPanel(Monad pM) {
		MonadPanel<T> pMP = new MonadPanel<>(_GUI, pM);
		addMonadPanel(pMP);
	}

	/**
	 * This method supports adding a monad panel to the nyad's list of monads. Be
	 * aware that no tests are performed to prevent the panel being added. That
	 * means the nyad might change from strong to weak without notice by this panel.
	 * 
	 * @param pMP MonadPanel This is the MonadPanel to be appended to this
	 *            NyadPanel. It is assumed that the monad panel is constructed
	 *            elsewhere.
	 */
	public void addMonadPanel(MonadPanel<T> pMP) {
		int next = Integer.valueOf(monadPanes.getTitleAt(monadPanes.getTabCount() - 1)).intValue() + 1;
		monadPanelList.ensureCapacity(next + 1);
		monadPanelList.add(pMP);
		monadPanes.addTab((new StringBuffer()).append(next).toString(), tabIcon, new JScrollPane(pMP));
		setReferences();
	}

	public int getMonadListSize() {
		return monadPanelList.size();
	}

	/**
	 * 
	 * @param pInd int get the MonadPanel at this integer spot
	 * @return MonadPanel The MonadPanel at the indicated location in the
	 *         monadPanelList
	 */
	public MonadPanel<T> getMonadPanel(int pInd) {
		int limit = monadPanelList.size();
		if (pInd < limit)
			return (MonadPanel<T>) monadPanelList.get(pInd);
		return null;
	}

	public Nyad getNyad() {
		return repNyad;
	}

	public int getOrder() {
		return monadPanelList.size();
	}

	public int getPaneFocus() {
		return monadPanes.getSelectedIndex();
	}

	public CladosField getRepMode() {
		return repMode;
	}

	private void copyMonadCommand() {
		if (getMonadListSize() <= 0)
			return; // Nothing to do here. No monad to be copied.
		Monad tMonad = getMonadPanel(getPaneFocus()).getMonad();
		String buildName = new StringBuffer(tMonad.getName()).append("_c").toString();
		String buildAlgName = new StringBuffer(tMonad.getAlgebra().getAlgebraName()).append("_c").toString();
		String buildFrameName = new StringBuffer(tMonad.getFrameName()).append("_c").toString();
		try {
			Monad newMonad = CladosGBuilder.createMonadWithAlgebra(tMonad.getScales(),
					CladosGBuilder.createAlgebraWithFootPlus(tMonad.getAlgebra().getFoot(),
							tMonad.getAlgebra().getCardinal(), tMonad.getAlgebra().getGProduct(), buildAlgName),
					buildName, buildFrameName);
			repNyad.appendMonad(newMonad);
			addMonadPanel(newMonad);
		} catch (BadSignatureException es) {
			ErrorDialog.show("Could not create copy because monad signature is malformed.", "Malformed Signature?");
		} catch (GeneratorRangeException er) {
			ErrorDialog.show("Could not create copy.\nMonad basis size is unsupported.", "Unsupported Signature?");
		} catch (CladosMonadException e) {
			ErrorDialog.show("Could not create copy because monad signature is malformed.", "Malformed Signature?");
		} catch (CladosNyadException e) {
			ErrorDialog.show("Could not append monad because nyad objected.", "Nyad Objection");
		}

	}

	private void createEditLayout() {
		JPanel pnlControlPanel = new JPanel();
		pnlControlPanel.setBackground(clrBackColor);

		pnlControlPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		pnlControlPanel.setLayout(new GridBagLayout());

		GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);
		cn.anchor = GridBagConstraints.NORTH;
		makeNotWritable();

		btnSaveEdits = new JButton(new ImageIcon(this.getClass().getResource("/icons/save.png")));
		btnSaveEdits.setActionCommand("save");
		btnSaveEdits.setToolTipText("save edits to nyad");
		btnSaveEdits.setBorder(BorderFactory.createEtchedBorder(0));
		btnSaveEdits.setEnabled(false);
		btnSaveEdits.setPreferredSize(square);

		btnEditMonad = new JButton(new ImageIcon(this.getClass().getResource("/icons/edit.png")));
		btnEditMonad.setActionCommand("edit");
		btnEditMonad.setToolTipText("start edits on nyad");
		btnEditMonad.setBorder(BorderFactory.createEtchedBorder(0));
		btnEditMonad.setPreferredSize(square);

		btnUndoEdits = new JButton(new ImageIcon(this.getClass().getResource("/icons/restore.png")));
		btnUndoEdits.setActionCommand("abort");
		btnUndoEdits.setToolTipText("abandon edits to nyad");
		btnUndoEdits.setBorder(BorderFactory.createEtchedBorder(0));
		btnUndoEdits.setEnabled(false);
		btnUndoEdits.setPreferredSize(square);

		cn.gridx = 0;
		cn.gridy = 0;
		cn.weightx = 0;
		cn.weighty = 0;

		btnEditMonad.addActionListener(this);
		pnlControlPanel.add(btnEditMonad, cn);
		cn.gridy++;

		btnSaveEdits.addActionListener(this);
		pnlControlPanel.add(btnSaveEdits, cn);
		cn.gridy++;

		btnUndoEdits.addActionListener(this);
		pnlControlPanel.add(btnUndoEdits, cn);
		cn.gridy++;

		cn.weighty = 1;
		pnlControlPanel.add(new JLabel(), cn);

		add(pnlControlPanel, "West");
	}

	private void createReferenceLayout() {
		pnlRefPanel = new JPanel();
		pnlRefPanel.setBackground(clrBackColor);

		TitledBorder tWrap = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				new StringBuffer("DivField | " + repMode).toString(), TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION,
				new Font(Font.SERIF, Font.PLAIN, 10));
		pnlRefPanel.setBorder(tWrap);
		pnlRefPanel.setLayout(new GridBagLayout());

		GridBagConstraints cn0 = new GridBagConstraints();
		cn0.anchor = GridBagConstraints.WEST;

		cn0.gridx = 0;
		cn0.gridy = 0;
		cn0.weightx = 0;
		cn0.weighty = 0;

		pnlRefPanel.add(new JLabel("Name ", SwingConstants.RIGHT), cn0);
		cn0.gridx++;
		nyadName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		cn0.weightx = 1;
		pnlRefPanel.add(nyadName, cn0);
		cn0.gridx++;

		cn0.weightx = 0;
		cn0.ipadx = 20;
		pnlRefPanel.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Foot"))), cn0);
		cn0.gridx++;
		nyadFoot.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
		nyadFoot.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlRefPanel.add(nyadFoot, cn0);
		cn0.gridx++;

		pnlRefPanel.add(new JLabel("Order ", SwingConstants.RIGHT), cn0);
		cn0.gridx++;
		nyadOrder.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
		nyadOrder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlRefPanel.add(nyadOrder, cn0);

		pnlRefPanel.add(new JLabel("Algebras ", SwingConstants.RIGHT), cn0);
		cn0.gridx++;
		nyadAlgOrder.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
		nyadAlgOrder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlRefPanel.add(nyadAlgOrder, cn0);

		add(pnlRefPanel, "South");
	}

	private void createStackLayout() {
		JPanel pnlControlPanel2 = new JPanel();
		pnlControlPanel2.setLayout(new GridBagLayout());
		pnlControlPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		pnlControlPanel2.setBackground(clrBackColor);

		GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);
		cn.anchor = GridBagConstraints.NORTH;

		cn.gridx = 0;
		cn.gridy = 0;

		cn.weightx = 0;
		cn.weighty = 0;
		cn.gridheight = 1;
		cn.gridwidth = 1;
		JButton btnSwapBelow = new JButton(new ImageIcon(this.getClass().getResource("/icons/push.png")));
		btnSwapBelow.setActionCommand("push");
		btnSwapBelow.setToolTipText("push monad down on stack");
		btnSwapBelow.setPreferredSize(square);
		btnSwapBelow.setBorder(BorderFactory.createEtchedBorder(0));
		btnSwapBelow.addActionListener(this);
		pnlControlPanel2.add(btnSwapBelow, cn);
		cn.gridy++;

		JButton btnSwapAbove = new JButton(new ImageIcon(this.getClass().getResource("/icons/pop.png")));
		btnSwapAbove.setActionCommand("pop");
		btnSwapAbove.setToolTipText("pop monad up on stack");
		btnSwapAbove.setPreferredSize(square);
		btnSwapAbove.setBorder(BorderFactory.createEtchedBorder(0));
		btnSwapAbove.addActionListener(this);
		pnlControlPanel2.add(btnSwapAbove, cn);
		cn.gridy++;

		JButton btnCopyMonad = new JButton(new ImageIcon(this.getClass().getResource("/icons/copy.png")));
		btnCopyMonad.setActionCommand("copy");
		btnCopyMonad.setToolTipText("copy monad to end of stack");
		btnCopyMonad.setPreferredSize(square);
		btnCopyMonad.setBorder(BorderFactory.createEtchedBorder(0));
		btnCopyMonad.addActionListener(this);
		pnlControlPanel2.add(btnCopyMonad, cn);
		cn.gridy++;

		JButton btnRemoveMonad = new JButton(new ImageIcon(this.getClass().getResource("/icons/remove.png")));
		btnRemoveMonad.setActionCommand("erase");
		btnRemoveMonad.setToolTipText("remove monad from stack");
		btnRemoveMonad.setPreferredSize(square);
		btnRemoveMonad.setBorder(BorderFactory.createEtchedBorder(0));
		btnRemoveMonad.addActionListener(this);
		pnlControlPanel2.add(btnRemoveMonad, cn);
		cn.gridy++;

		JButton btnNewMonad = new JButton(new ImageIcon(this.getClass().getResource("/icons/create.png")));
		btnNewMonad.setActionCommand("create");
		btnNewMonad.setToolTipText("create new monad");
		btnNewMonad.setPreferredSize(square);
		btnNewMonad.setBorder(BorderFactory.createEtchedBorder(0));
		btnNewMonad.addActionListener(this);
		pnlControlPanel2.add(btnNewMonad, cn);
		cn.gridy++;

		cn.weighty = 1;
		pnlControlPanel2.add(new JLabel(), cn);

		add(pnlControlPanel2, "East");
	}

	private void makeNotWritable() {
		if (pnlRefPanel != null)
			pnlRefPanel.setBackground(clrBackColor);
		nyadName.setEditable(false);
	}

	/**
	 * This method adjusts the JTextArea elements contained on the panel to allow
	 * for edits.
	 */
	private void makeWritable() {
		if (pnlRefPanel != null)
			pnlRefPanel.setBackground(clrUnlockColor);
		nyadName.setEditable(true);
	}

	private void pop() {
		int where = monadPanes.getSelectedIndex();
		if (where > 0) {
			String otherTitle = new String(monadPanes.getTitleAt(where - 1));
			JScrollPane otherPane = new JScrollPane((JPanel) monadPanelList.get(where - 1));

			String thisTitle = new String(monadPanes.getTitleAt(where));
			JScrollPane thisPane = new JScrollPane((JPanel) monadPanelList.get(where));

			monadPanes.setTitleAt(where, otherTitle);
			monadPanes.setComponentAt(where, otherPane);

			monadPanes.setTitleAt(where - 1, thisTitle);
			monadPanes.setComponentAt(where - 1, thisPane);

			MonadPanel<T> tempPanel = (MonadPanel<T>) monadPanelList.remove(where - 1);
			monadPanelList.add(where, tempPanel);
		}
	}

	private void push() {
		int size = monadPanes.getTabCount();
		int where = monadPanes.getSelectedIndex();
		if (where < size - 1) {
			String otherTitle = new String(monadPanes.getTitleAt(where + 1));
			JScrollPane otherPane = new JScrollPane((JPanel) monadPanelList.get(where + 1));

			String thisTitle = new String(monadPanes.getTitleAt(where));
			JScrollPane thisPane = new JScrollPane((JPanel) monadPanelList.get(where));

			monadPanes.setTitleAt(where, otherTitle);
			monadPanes.setComponentAt(where, otherPane);

			monadPanes.setTitleAt(where + 1, thisTitle);
			monadPanes.setComponentAt(where + 1, thisPane);

			MonadPanel<T> tempPanel = (MonadPanel<T>) monadPanelList.remove(where);
			monadPanelList.add(where + 1, tempPanel);

			revalidate();
		}
	}

	private void removeMonadCommand() {
		if (monadPanes.getTabCount() > 1) {
			try {
				repNyad.removeMonad(monadPanes.getSelectedIndex());
				removeMonadTab(monadPanes.getSelectedIndex());
			} catch (CladosNyadException e) {
				ErrorDialog.show("Could not remove the monad.\n" + e.getSourceMessage(), "Clados Nyad Exception");
			}
		} else {
			// The only way here is if the monad to remove is the last one in the nyad.
			// That causes the entire nyad to be removed.
			_GUI.appGeometryView.removeNyadPanel(0);
			// Don't worry about the tab count being zero or negative. The command to get
			// here wouldn't have been visible.
		}
	}

	/**
	 * This method removes the Monad Panel at the integer index indicated.
	 * 
	 * @param pInd int This the index of the monad panel to remove. No checks are
	 *             made right now for out-of-bounds conditions. This should be
	 *             fixed.
	 */
	private void removeMonadTab(int pInd) {
		if (pInd > monadPanelList.size())
			return; // Index out of bounds. Don't try
		else if (pInd > monadPanes.getTabCount())
			return; // Index out of bounds on the panes. Don't try.

		monadPanes.remove(pInd);
		monadPanelList.remove(pInd);
		setReferences();
	}

	private void setReferences() {
		nyadName.setText(repNyad.getName());
		nyadOrder.setText((new StringBuffer().append(repNyad.getMonadList().size())).toString());
		nyadAlgOrder.setText((new StringBuffer().append(repNyad.getAlgebraList().size())).toString());
		nyadFoot.setText(repNyad.getFoot().getFootName());
	}

	private void setRepName() {
		if (nyadName.getText() != repNyad.getName())
			repNyad.setName(nyadName.getText());
	}
}
