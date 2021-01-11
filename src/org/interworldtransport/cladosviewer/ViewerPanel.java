/**
 * <h2>Copyright</h2> © 2021 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.ViewerPanel<br>
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
 * ---org.interworldtransport.cladosviewer.ViewerPanel<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewer;

import org.interworldtransport.cladosF.CladosFBuilder;
import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosF.Field;
import org.interworldtransport.cladosF.Normalizable;
//import org.interworldtransport.cladosF.RealF;
import org.interworldtransport.cladosF.UnitAbstract;
import org.interworldtransport.cladosG.Monad;
import org.interworldtransport.cladosG.Nyad;
import org.interworldtransport.cladosG.CladosGBuilder;

import org.interworldtransport.cladosGExceptions.*;

import org.interworldtransport.cladosviewerExceptions.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.*;

/**
 * The ViewerPanel class is intended to be the center panel of the Calculator.
 * It holds the tabbed pane that holds Nyads. It is also aware enough to manage
 * the nyads a bit with respect to stack operations.
 * <p>
 * 
 * @version 1.0
 * @author Dr Alfred W Differ
 */

public class ViewerPanel<T extends UnitAbstract & Field & Normalizable> extends JPanel implements ActionListener {
	private static final Color clrBackColor = new Color(255, 255, 220);
	private static final Dimension square = new Dimension(25, 25);
	/**
	 * This is just a reference back to the parent frame of the application.
	 */
	public CladosCalculator _GUI;
	private CladosField _repMode;
	private ImageIcon tabIcon;
	protected ArrayList<NyadPanel<T>> nyadPanelList;
	protected JTabbedPane nyadPanes;

	/**
	 * The ViewerPanel class is intended to be a tabbed pane that displays all the
	 * Nyad Panels. ViewerPanel must be smart enough to know what it holds and
	 * adjust the tabs when push and pop operations are performed.
	 * 
	 * @param pGUI CladosCalculator This parameter references the owning
	 *             application. Nothing spectacular.
	 * 
	 */
	public ViewerPanel(CladosCalculator pGUI) {
		super();

		_GUI = pGUI;
		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		setBackground(clrBackColor);
		setLayout(new BorderLayout());

		createStackLayout();

		// Get the nyad tab image for the nyad panes being constructed
		tabIcon = new ImageIcon(this.getClass().getResource("/resources/N.png"));
		_repMode = validateInitialDivField();
		// The Viewer contains NyadPanels displayed as a JTabbedPanes containing
		// JScrollPanes containing a NyadPanel each. We initiate the JTabbedPane here
		nyadPanes = new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
		nyadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		// JScrollPanes are used only when a nyadPanel with nyadPanes.addTab()
		add(nyadPanes, JTabbedPane.CENTER);
		nyadPanelList = new ArrayList<NyadPanel<T>>(0);

		initObjects(); // This is the old initializer.
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "push" -> push(); // Swaps the currently selected nyad with the one below it
		case "pop" -> pop(); // Swaps the currently selected nyad with the one above it
		case "copy" -> copyNyadCommand(); // Clone the selected nyad and place it at the end of the stack
		case "erase" -> eraseNyadCommand(); // Remove the selected nyad from the stack
		case "create" -> CreateDialog.createNyad(_GUI, _repMode); // Create a monad for the nyad OR a whole new nyad
		default -> ErrorDialog.show("No detectable command given at ViewerPanel. No action.", "That's Odd");
		}
	}

	public int getNyadListSize() {
		return nyadPanelList.size();
	}

	public NyadPanel<T> getNyadPanel(int pInd) {
		if (pInd < nyadPanelList.size() && pInd >= 0)
			return nyadPanelList.get(pInd);

		return null;
	}

	public ArrayList<NyadPanel<T>> getNyadPanels() {
		return nyadPanelList;
	}

	public int getPaneFocus() {
		return nyadPanes.getSelectedIndex();
	}

	public CladosField getRepMode() {
		return _repMode;
	}

	@SuppressWarnings("unchecked")
	private Nyad buildANyad(short pWhich, short monadCount) {
		Nyad aNyad = null;
		T keyNumber = null;
		try {
			String cnt = new StringBuffer("N").append(pWhich).toString();
			switch (_GUI.IniProps.getProperty("Desktop.Default.DivField")) {
			case "RealF" -> keyNumber = (T) CladosFBuilder.REALF
					.createZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			case "RealD" -> keyNumber = (T) CladosFBuilder.REALD
					.createZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			case "ComplexF" -> keyNumber = (T) CladosFBuilder.COMPLEXF
					.createZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			case "ComplexD" -> keyNumber = (T) CladosFBuilder.COMPLEXD
					.createZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			}

			Monad aMonad = new Monad("M", _GUI.IniProps.getProperty("Desktop.Default.AlgebraName"),
					_GUI.IniProps.getProperty("Desktop.Default.FrameName"),
					_GUI.IniProps.getProperty("Desktop.Default.FootName"),
					_GUI.IniProps.getProperty("Desktop.Default.Sig"), keyNumber);
			aNyad = new Nyad(cnt, aMonad, true);
			// Now bootstrap the others using the first
			short m = 1;
			while (m < monadCount) {
				String nextMonadName = (new StringBuffer(aMonad.getName()).append(m)).toString();
				String nextAlgebraName = (new StringBuffer(aMonad.getAlgebra().getAlgebraName()).append(m)).toString();
				String nextFrameName = (new StringBuffer(aMonad.getFrameName()).append(m)).toString();

				aNyad.createMonad(nextMonadName, nextAlgebraName, nextFrameName,
						_GUI.IniProps.getProperty("Desktop.Default.Sig"),
						_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
				m++;
			}
		} catch (BadSignatureException es) {
			ErrorDialog.show("Could not construct a monad due to signature issue.\n" + es.getSourceMessage(),
					"Bad Signature Exception");
			return null;
		} catch (GeneratorRangeException e) {
			ErrorDialog.show("Cannot construct a monad due to unsupported signature size.\n" + e.getSourceMessage(),
					"Generator Range Exception");
			return null;
		} catch (CladosMonadException em) {
			ErrorDialog.show("Could not construct first part of the Viewer Panel because monad was malformed.\n"
					+ em.getSourceMessage(), "Clados Monad Exception");
			return null;
		} catch (CladosNyadException en) {
			ErrorDialog.show("Could not add Nyad to the Viewer Panel.\n" + en.getSourceMessage(),
					"Clados Nyad Exception");
			return null;
		}
		return aNyad;
	}

	private void copyNyadCommand() {
		if (getNyadListSize() <= 0)
			return; // Nothing to do since nyad list is empty. Nothing to copy.

		int endPlus = 0;
		if (nyadPanes.getTabCount() > 0)
			endPlus = Integer.valueOf(nyadPanes.getTitleAt(nyadPanes.getTabCount() - 1)).intValue() + 1;
		nyadPanelList.ensureCapacity(endPlus + 1);
		String buildName = "copied";
		try {
			NyadPanel<T> newP = new NyadPanel<T>(_GUI,
					CladosGBuilder.INSTANCE.copyOfNyad(getNyadPanel(getPaneFocus()).getNyad(), buildName));
			nyadPanelList.add(newP);
			nyadPanes.addTab((new StringBuffer().append(endPlus)).toString(), tabIcon, new JScrollPane(newP));
			_GUI.pack();

		} catch (UtilitiesException e) {
			ErrorDialog.show("Could not create copy from toolbar.\n" + e.getSourceMessage(), "Utilities Exception");
		} catch (BadSignatureException es) {
			ErrorDialog.show("Could not create copy from toolbar due to signature issue.\n" + es.getSourceMessage(),
					"Bad Signature Exception");
		} catch (CladosMonadException e) {
			ErrorDialog.show("Could not create copy from toolbar because monad was malformed.\n" + e.getSourceMessage(),
					"Clados Monad Exception");
		} catch (CladosNyadException e) {
			ErrorDialog.show("Could not create copy from toolbar because nyad was malformed.\n" + e.getSourceMessage(),
					"Clados Nyad Exception");
		}
	}

	private void createStackLayout() {
		JPanel pnlControlBar = new JPanel();
		pnlControlBar.setLayout(new GridBagLayout());
		pnlControlBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		pnlControlBar.setBackground(clrBackColor);

		GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);
		cn.anchor = GridBagConstraints.NORTH;

		cn.gridx = 0;
		cn.gridy = 0;

		cn.weightx = 0;
		cn.weighty = 0;
		cn.gridheight = 1;
		cn.gridwidth = 1;
		JButton btnSwapBelow = new JButton(new ImageIcon(this.getClass().getResource("/resources/push.png")));
		btnSwapBelow.setActionCommand("push");
		btnSwapBelow.setToolTipText("push nyad down on stack");
		btnSwapBelow.setPreferredSize(square);
		btnSwapBelow.setBorder(BorderFactory.createEtchedBorder(0));
		btnSwapBelow.addActionListener(this);
		pnlControlBar.add(btnSwapBelow, cn);
		cn.gridy++;

		JButton btnSwapAbove = new JButton(new ImageIcon(this.getClass().getResource("/resources/pop.png")));
		btnSwapAbove.setActionCommand("pop");
		btnSwapAbove.setToolTipText("pop nyad up on stack");
		btnSwapAbove.setPreferredSize(square);
		btnSwapAbove.setBorder(BorderFactory.createEtchedBorder(0));
		btnSwapAbove.addActionListener(this);
		pnlControlBar.add(btnSwapAbove, cn);
		cn.gridy++;

		JButton btnCopyNyad = new JButton(new ImageIcon(this.getClass().getResource("/resources/copy.png")));
		btnCopyNyad.setActionCommand("copy");
		btnCopyNyad.setToolTipText("copy nyad to end of stack");
		btnCopyNyad.setPreferredSize(square);
		btnCopyNyad.setBorder(BorderFactory.createEtchedBorder(0));
		btnCopyNyad.addActionListener(this);
		pnlControlBar.add(btnCopyNyad, cn);
		cn.gridy++;

		JButton btnRemoveNyad = new JButton(new ImageIcon(this.getClass().getResource("/resources/remove.png")));
		btnRemoveNyad.setActionCommand("erase");
		btnRemoveNyad.setToolTipText("remove nyad from stack");
		btnRemoveNyad.setPreferredSize(square);
		btnRemoveNyad.setBorder(BorderFactory.createEtchedBorder(0));
		btnRemoveNyad.addActionListener(this);
		pnlControlBar.add(btnRemoveNyad, cn);
		cn.gridy++;

		JButton btnNewNyad = new JButton(new ImageIcon(this.getClass().getResource("/resources/create.png")));
		btnNewNyad.setActionCommand("create");
		btnNewNyad.setToolTipText("create new nyad");
		btnNewNyad.setPreferredSize(square);
		btnNewNyad.setBorder(BorderFactory.createEtchedBorder(0));
		btnNewNyad.addActionListener(this);
		pnlControlBar.add(btnNewNyad, cn);
		cn.gridy++;

		cn.weighty = 1;
		pnlControlBar.add(new JLabel(), cn);

		add(pnlControlBar, "East");
	}

	private void eraseNyadCommand() {
		if (nyadPanes.getTabCount() > 0) {
			_GUI.appFieldBar.repNumber = null;

			int point = nyadPanes.getSelectedIndex();

			switch (getNyadPanel(point).getRepMode()) {
			case REALF:
			case REALD:
				_GUI.appFieldBar.setRealText("");
				break;
			case COMPLEXF:
			case COMPLEXD:
				_GUI.appFieldBar.setRealText("");
				_GUI.appFieldBar.setImgText("");
			}
			removeNyadPanel(point);
		} else {
			_GUI.appFieldBar.makeNotWritable();
		}
	}

	private void initObjects() { // TODO change to an XML reader
		// Look in the conf file and determine how many nyads to initiate and init
		// NyadPanelList
		int intCount = validateInitialNyadCount();
		nyadPanelList = new ArrayList<NyadPanel<T>>(intCount);

		// Look in the conf file and determine how many monads in each nyad get
		// initiated
		short intOrd = validateInitialNyadOrder();
		// Nothing to initialize with this. It gets used when building intial nyads in
		// 'buildANyad' methods.

		// Look in the conf file and determine the DivField to use during initiation
		// String sType = validateInitialDivField();
		if (_repMode == null) // No valid DivField found, so don't construct a nyad
		{
			intOrd = 0;
			intCount = 0;
			nyadPanelList = new ArrayList<NyadPanel<T>>(0);
		}
		// Note that we re-nitialized the NyadPanelList if no valid DivField is found
		// for initialization
		// sType is the switch mode determining which monad types are used in the
		// calculator.

		// the j counter covers the number of nyads to be initiated. {max = intCount}
		// {Could be ZERO}
		// the m counter covers the number of monads in each nyad are to be initiated.
		// {max = intOrder}
		short j = 0;
		while (j < intCount) {
			Nyad aNyad = buildANyad(j, intOrd); // the Nyad bootstrapper
			try // Here we finally initiate the NyadPanel because the Nyad is actually exists.
			{
				if (aNyad != null) {
					nyadPanelList.add(j, new NyadPanel<T>(_GUI, aNyad));
					JScrollPane tempPane = new JScrollPane(nyadPanelList.get(j));
					tempPane.setWheelScrollingEnabled(true);
					nyadPanes.addTab(new StringBuffer().append(j).toString(), tabIcon, tempPane);
				} else
					ErrorDialog.show("Null NyadRealF for new NyadPanel avoided in initialization step.", "Init Failed");
			} catch (UtilitiesException eutil) {
				ErrorDialog.show("Could not create new NyadPanel.\n" + eutil.getSourceMessage() + "\n"
						+ eutil.getStackTrace().toString(), "Utilities Exception");
			} catch (BadSignatureException e) {
				ErrorDialog.show("Could not create a nyad due to signature issue.\n" + e.getSourceMessage() + "\n"
						+ e.getStackTrace().toString(), "Bad Signature Exception");
			}
			j++;
		}
	}

	private void pop() {
		int where = nyadPanes.getSelectedIndex();
		if (where > 0) {
			String otherTitle = new String(nyadPanes.getTitleAt(where - 1));
			JScrollPane otherPane = new JScrollPane((JPanel) nyadPanelList.get(where - 1));

			String thisTitle = new String(nyadPanes.getTitleAt(where));
			JScrollPane thisPane = new JScrollPane((JPanel) nyadPanelList.get(where));

			nyadPanes.setTitleAt(where, otherTitle);
			nyadPanes.setComponentAt(where, otherPane);

			nyadPanes.setTitleAt(where - 1, thisTitle);
			nyadPanes.setComponentAt(where - 1, thisPane);

			NyadPanel<T> tempPanel = (NyadPanel<T>) nyadPanelList.remove(where - 1);
			nyadPanelList.add(where, tempPanel);

			revalidate();
		}
	}

	/**
	 * This method pushes the selected Nyad downward on the stack if possible. It
	 * does NOT create any new slots in the stack.
	 */
	private void push() {
		int size = nyadPanes.getTabCount();
		int where = nyadPanes.getSelectedIndex();
		if (where < size - 1) {
			String otherTitle = new String(nyadPanes.getTitleAt(where + 1));
			JScrollPane otherPane = new JScrollPane((JPanel) nyadPanelList.get(where + 1));

			String thisTitle = new String(nyadPanes.getTitleAt(where));
			JScrollPane thisPane = new JScrollPane((JPanel) nyadPanelList.get(where));

			nyadPanes.setTitleAt(where, otherTitle);
			nyadPanes.setComponentAt(where, otherPane);

			nyadPanes.setTitleAt(where + 1, thisTitle);
			nyadPanes.setComponentAt(where + 1, thisPane);

			NyadPanel<T> tempPanel = (NyadPanel<T>) nyadPanelList.remove(where);
			nyadPanelList.add(where + 1, tempPanel);

			revalidate();
		}
	}

	private CladosField validateInitialDivField() {
		// CladosField nField;
		try {
			String sType = _GUI.IniProps.getProperty("Desktop.Default.DivField");
			switch (sType) {
			case "RealF" -> {
				return CladosField.REALF;
			}
			case "RealD" -> {
				return CladosField.REALD;
			}
			case "ComplexF" -> {
				return CladosField.COMPLEXF;
			}
			case "ComplexD" -> {
				return CladosField.COMPLEXD;
			}
			default -> {
				return null;
			}
			}
		} catch (NullPointerException eNull) {
			ErrorDialog.show(
					"Desktop.Default.DivField from the configuration file appears to be null.\nNo nyad will be initialized.",
					"Null Pointer Exception");
		} catch (NumberFormatException eFormat) {
			ErrorDialog.show(
					"Desktop.Default.DivField from the configuration file appears to be non-parse-able.\nNo nyad will be initialized.",
					"Number Format Exception");
		}
		return null;
	}

	private int validateInitialNyadCount() {
		try {
			int nCount = Integer.parseInt(_GUI.IniProps.getProperty("Desktop.Default.Count"));
			nyadPanelList = new ArrayList<NyadPanel<T>>(nCount);
			return nCount;
		} catch (NullPointerException eNull) {
			ErrorDialog.show("Desktop.Default.Count from the configuration file appears to be null.\nSet to Zero.",
					"Null Pointer Exception");
			nyadPanelList = new ArrayList<NyadPanel<T>>(0);
		} catch (NumberFormatException eFormat) {
			ErrorDialog.show(
					"Desktop.Default.Count from the configuration file appears to be non-parse-able.\nSet to Zero.",
					"Null Pointer Exception");
			nyadPanelList = new ArrayList<NyadPanel<T>>(0);
		}
		return 0;
	}

	private short validateInitialNyadOrder() {
		try {
			return Short.parseShort(_GUI.IniProps.getProperty("Desktop.Default.Order"));
		} catch (NullPointerException eNull) {
			ErrorDialog.show("Desktop.Default.Order from the configuration file appears to be null.\nSet to One.",
					"Null Pointer Exception");
		} catch (NumberFormatException eFormat) {
			ErrorDialog.show(
					"Desktop.Default.Count from the configuration file appears to be non-parse-able.\nSet to One.",
					"Null Pointer Exception");
		}
		return 1;
	}

	protected void addNyad(Nyad pN) {
		int endPlus = 0;
		if (nyadPanes.getTabCount() > 0)
			endPlus = Integer.valueOf(nyadPanes.getTitleAt(nyadPanes.getTabCount() - 1)).intValue() + 1;
		nyadPanelList.ensureCapacity(endPlus + 1);
		try {
			NyadPanel<T> newP = new NyadPanel<T>(_GUI, pN);
			nyadPanelList.add(newP);
			nyadPanes.addTab((new StringBuffer().append(endPlus)).toString(), tabIcon, new JScrollPane(newP));
			_GUI.pack();
		} catch (UtilitiesException e) {
			ErrorDialog.show("Could not create copy from toolbar.\n" + e.getSourceMessage(), "Utilities Exception");
		} catch (BadSignatureException es) {
			ErrorDialog.show("Could not create copy from toolbar due to signature issue.\n" + es.getSourceMessage(),
					"Bad Signature Exception");
		}

	}

	/**
	 * This is a big deal. By registering the FieldPanel with the ViewerPanel,
	 * change events on monad and nyad panels can register their proto numbers with
	 * the field panel. This goes on behind the scenes allowing the UI to adjust
	 * cladosF references on the app's FieldBar so the user need only pay attention
	 * to the numbers displayed. The string for the Cardinal IS displayed on a
	 * monad, though, and not the Field Bar.
	 * 
	 * @param fieldPanel FieldPanel In the owning app, this is the FieldBar object
	 *                   that allows for top-level numeric input on the calculator.
	 *                   The Field Panel offered is registered with this Viewer
	 *                   Panel so change events can be routed.
	 */
	protected <D extends UnitAbstract & Field & Normalizable> void registerFieldPanel(FieldPanel<D> fieldPanel) {
		nyadPanes.addChangeListener(new ChangeListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void stateChanged(ChangeEvent e) {
				if (nyadPanes.getTabCount() > 0) {
					if (nyadPanes.getSelectedIndex() >= 0) {
						if (nyadPanelList.get(nyadPanes.getSelectedIndex()).monadPanes.getTabCount() > 0) {
							int j = nyadPanelList.get(nyadPanes.getSelectedIndex()).monadPanes.getSelectedIndex();
							MonadPanel<T> tSpot = nyadPanelList.get(nyadPanes.getSelectedIndex()).getMonadPanel(j);
							fieldPanel.setField((D) CladosFBuilder.copyOf(tSpot.getMonad().getScales().getScalar()));
							_GUI.appFieldBar.makeWritable();
						}
					}
				} else
					fieldPanel.clearFieldType();
			}
		});
	}

	protected void removeNyadPanel(int pInd) {
		if (pInd < nyadPanelList.size() && pInd >= 0) {
			nyadPanelList.remove(pInd);
			nyadPanes.remove(pInd);
		}
		_GUI.pack();
	}

	protected void setRepMode(CladosField pIn) {
		_repMode = pIn;
	}
}