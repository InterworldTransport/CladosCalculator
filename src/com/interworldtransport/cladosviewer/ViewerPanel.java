/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
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

package com.interworldtransport.cladosviewer;

import com.interworldtransport.cladosF.CladosField;
import com.interworldtransport.cladosF.RealF;
import com.interworldtransport.cladosF.RealD;
import com.interworldtransport.cladosF.ComplexF;
import com.interworldtransport.cladosF.ComplexD;

import com.interworldtransport.cladosG.MonadRealF;
import com.interworldtransport.cladosG.NyadAbstract;
import com.interworldtransport.cladosG.MonadRealD;
import com.interworldtransport.cladosG.MonadComplexF;
import com.interworldtransport.cladosG.MonadComplexD;
import com.interworldtransport.cladosG.AlgebraComplexD;
import com.interworldtransport.cladosG.AlgebraComplexF;
import com.interworldtransport.cladosG.AlgebraRealD;
import com.interworldtransport.cladosG.AlgebraRealF;
import com.interworldtransport.cladosG.CladosGNyad;
import com.interworldtransport.cladosG.NyadRealF;
import com.interworldtransport.cladosG.NyadRealD;
import com.interworldtransport.cladosG.NyadComplexF;
import com.interworldtransport.cladosG.NyadComplexD;

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

/**
 * com.interworldtransport.cladosviewer.ViewerPanel The ViewerPanel class is
 * intended to be the main panel of the Calculator. It holds the tabbed pane
 * that holds Nyads. It is also aware enough to manage the nyads a bit with
 * respect to stack operations.
 * <p>
 * 
 * @version 0.85
 * @author Dr Alfred W Differ
 */

public class ViewerPanel extends JPanel implements ActionListener {
	private static final Color clrBackColor = new Color(255, 255, 220);
	private static final Dimension square = new Dimension(25, 25);
	public CladosCalculator _GUI;
	private CladosField _repMode;
	private JButton btnCopyNyad;
	private JButton btnNewNyad;
	private JButton btnRemoveNyad;
	private JButton btnSwapAbove;
	private JButton btnSwapBelow;
	private JPanel pnlControlBar;
	private ImageIcon tabIcon;
	protected ArrayList<NyadPanel> nyadPanelList;
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
		tabIcon = new ImageIcon(this.getClass().getResource("/icons/N.png"));
		// _GUI._StatusBar.setStatusMsg(tabIcon.getIconHeight()+",
		// "+tabIcon.getIconWidth()+" | "+tabIcon.getDescription());

		_repMode = validateInitialDivField();

		// The Viewer contains NyadPanels displayed as a JTabbedPanes containing
		// JScrollPanes containing a NyadPanel each. We initiate the JTabbedPane here
		nyadPanes = new JTabbedPane(JTabbedPane.RIGHT, JTabbedPane.WRAP_TAB_LAYOUT);
		nyadPanes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		// JScrollPanes are used only when a nyadPanel with nyadPanes.addTab()
		add(nyadPanes, JTabbedPane.CENTER);

		nyadPanelList = new ArrayList<NyadPanel>(0);

		initObjects(); // This is the old initializer. TODO change to an XML reader
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "push":
			push(); // Swaps the currently selected nyad with the one below it
			break;
		case "pop":
			pop(); // Swaps the currently selected nyad with the one above it
			break;
		case "copy":
			copyNyadCommand(); // Clone the selected nyad and place it at the end of the stack
			break;
		case "erase":
			eraseNyadCommand(); // Remove the selected nyad from the stack
			break;
		case "create":
			CreateDialog.createNyad(_GUI, _repMode); // Create a new monad for the selected nyad OR a whole new nyad
			break;
		default:
			ErrorDialog.show("No detectable command given at ViewerPanel. No action.", "That's Odd");
		}
	}

	public int getNyadListSize() {
		return nyadPanelList.size();
	}

	public NyadPanel getNyadPanel(int pInd) {
		if (pInd < nyadPanelList.size() && pInd >= 0)
			return nyadPanelList.get(pInd);

		return null;
	}

	public ArrayList<NyadPanel> getNyadPanels() {
		return nyadPanelList;
	}

	public int getPaneFocus() {
		return nyadPanes.getSelectedIndex();
	}

	public CladosField getRepMode() {
		return _repMode;
	}

	private NyadComplexD buildANyadCD(short pWhich, short monadCount) {
		NyadComplexD aNyad = null;
		try {
			String cnt = new StringBuffer("N").append(pWhich).toString();
			MonadComplexD aMonad = new MonadComplexD("M", _GUI.IniProps.getProperty("Desktop.Default.AlgebraName"),
					_GUI.IniProps.getProperty("Desktop.Default.FrameName"),
					_GUI.IniProps.getProperty("Desktop.Default.FootName"),
					_GUI.IniProps.getProperty("Desktop.Default.Sig"),
					ComplexD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal")));
			aNyad = new NyadComplexD(cnt, aMonad, true);
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

	private NyadComplexF buildANyadCF(short pWhich, short monadCount) {
		NyadComplexF aNyad = null;
		try {
			String cnt = new StringBuffer("N").append(pWhich).toString();
			MonadComplexF aMonad = new MonadComplexF("M", _GUI.IniProps.getProperty("Desktop.Default.AlgebraName"),
					_GUI.IniProps.getProperty("Desktop.Default.FrameName"),
					_GUI.IniProps.getProperty("Desktop.Default.FootName"),
					_GUI.IniProps.getProperty("Desktop.Default.Sig"),
					ComplexF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal")));
			aNyad = new NyadComplexF(cnt, aMonad, true);
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

	private NyadRealD buildANyadRD(short pWhich, short monadCount) {
		NyadRealD aNyad = null;
		try {
			String cnt = new StringBuffer("N").append(pWhich).toString();
			MonadRealD aMonad = new MonadRealD("M", _GUI.IniProps.getProperty("Desktop.Default.AlgebraName"),
					_GUI.IniProps.getProperty("Desktop.Default.FrameName"),
					_GUI.IniProps.getProperty("Desktop.Default.FootName"),
					_GUI.IniProps.getProperty("Desktop.Default.Sig"),
					RealD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal")));
			aNyad = new NyadRealD(cnt, aMonad, true);
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

	private NyadRealF buildANyadRF(short pWhich, short monadCount) {
		NyadRealF aNyad = null;
		try {
			String cnt = new StringBuffer("N").append(pWhich).toString();
			MonadRealF aMonad = new MonadRealF("M", _GUI.IniProps.getProperty("Desktop.Default.AlgebraName"),
					_GUI.IniProps.getProperty("Desktop.Default.FrameName"),
					_GUI.IniProps.getProperty("Desktop.Default.FootName"),
					_GUI.IniProps.getProperty("Desktop.Default.Sig"),
					RealF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal")));
			aNyad = new NyadRealF(cnt, aMonad, true);
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

		NyadPanel newP;
		int endPlus = 0;
		if (nyadPanes.getTabCount() > 0)
			endPlus = Integer.valueOf(nyadPanes.getTitleAt(nyadPanes.getTabCount() - 1)) + 1;
		nyadPanelList.ensureCapacity(endPlus + 1);
		String buildName = "copied";
		try {
			switch (getNyadPanel(getPaneFocus()).getRepMode()) {
			case REALF:
				newP = new NyadPanel(_GUI,
						(NyadRealF) CladosGNyad.REALF.copyRename(getNyadPanel(getPaneFocus()).getNyadRF(), buildName));
				break;
			case REALD:
				newP = new NyadPanel(_GUI,
						(NyadRealD) CladosGNyad.REALD.copyRename(getNyadPanel(getPaneFocus()).getNyadRD(), buildName));
				break;
			case COMPLEXF:
				newP = new NyadPanel(_GUI, (NyadComplexF) CladosGNyad.COMPLEXF
						.copyRename(getNyadPanel(getPaneFocus()).getNyadCF(), buildName));

				return;
			case COMPLEXD:
				newP = new NyadPanel(_GUI, (NyadComplexD) CladosGNyad.COMPLEXD
						.copyRename(getNyadPanel(getPaneFocus()).getNyadCD(), buildName));
				break;
			default:
				newP = null;
			}
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
		pnlControlBar = new JPanel();
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
		btnSwapBelow = new JButton(new ImageIcon(this.getClass().getResource("/icons/push.png")));
		btnSwapBelow.setActionCommand("push");
		btnSwapBelow.setToolTipText("push nyad down on stack");
		btnSwapBelow.setPreferredSize(square);
		btnSwapBelow.setBorder(BorderFactory.createEtchedBorder(0));
		btnSwapBelow.addActionListener(this);
		pnlControlBar.add(btnSwapBelow, cn);
		cn.gridy++;

		btnSwapAbove = new JButton(new ImageIcon(this.getClass().getResource("/icons/pop.png")));
		btnSwapAbove.setActionCommand("pop");
		btnSwapAbove.setToolTipText("pop nyad up on stack");
		btnSwapAbove.setPreferredSize(square);
		btnSwapAbove.setBorder(BorderFactory.createEtchedBorder(0));
		btnSwapAbove.addActionListener(this);
		pnlControlBar.add(btnSwapAbove, cn);
		cn.gridy++;

		btnCopyNyad = new JButton(new ImageIcon(this.getClass().getResource("/icons/copy.png")));
		btnCopyNyad.setActionCommand("copy");
		btnCopyNyad.setToolTipText("copy nyad to end of stack");
		btnCopyNyad.setPreferredSize(square);
		btnCopyNyad.setBorder(BorderFactory.createEtchedBorder(0));
		btnCopyNyad.addActionListener(this);
		pnlControlBar.add(btnCopyNyad, cn);
		cn.gridy++;

		btnRemoveNyad = new JButton(new ImageIcon(this.getClass().getResource("/icons/remove.png")));
		btnRemoveNyad.setActionCommand("erase");
		btnRemoveNyad.setToolTipText("remove nyad from stack");
		btnRemoveNyad.setPreferredSize(square);
		btnRemoveNyad.setBorder(BorderFactory.createEtchedBorder(0));
		btnRemoveNyad.addActionListener(this);
		pnlControlBar.add(btnRemoveNyad, cn);
		cn.gridy++;

		btnNewNyad = new JButton(new ImageIcon(this.getClass().getResource("/icons/create.png")));
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
			int point = nyadPanes.getSelectedIndex();
			switch (getNyadPanel(point).getRepMode()) {
			case REALF:
				_GUI.appFieldBar._repRealF = null;
				_GUI.appFieldBar.setRealText("");
				break;
			case REALD:
				_GUI.appFieldBar._repRealD = null;
				_GUI.appFieldBar.setRealText("");
				break;
			case COMPLEXF:
				_GUI.appFieldBar._repComplexF = null;
				_GUI.appFieldBar.setRealText("");
				_GUI.appFieldBar.setImgText("");
				break;
			case COMPLEXD:
				_GUI.appFieldBar._repComplexD = null;
				_GUI.appFieldBar.setRealText("");
				_GUI.appFieldBar.setImgText("");
			}
			removeNyadPanel(point);
		} else {
			_GUI.appFieldBar.makeNotWritable();
		}
	}

	private void initObjects() {
		// Look in the conf file and determine how many nyads to initiate and init
		// NyadPanelList
		int intCount = validateInitialNyadCount();
		nyadPanelList = new ArrayList<NyadPanel>(intCount);

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
			nyadPanelList = new ArrayList<NyadPanel>(0);
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
			switch (_repMode) {
			case REALF:
				NyadRealF aNyadRF = buildANyadRF(j, intOrd); // the NyadRF bootstrapper
				try // Here we finally initiate the NyadPanel because the Nyad is actually filled at
					// this point.
				{
					if (aNyadRF != null) {
						nyadPanelList.add(j, new NyadPanel(_GUI, aNyadRF));
						JScrollPane tempPane = new JScrollPane(nyadPanelList.get(j));
						tempPane.setWheelScrollingEnabled(true);
						nyadPanes.addTab(new StringBuffer().append(j).toString(), tabIcon, tempPane);
					} else
						_GUI.appStatusBar.setStatusMsg("... null NyadRealF for new NyadPanel avoided.\n");
				} catch (UtilitiesException eutil) {
					ErrorDialog.show("Could not create new NyadPanel.\n" + eutil.getSourceMessage() + "\n"
							+ eutil.getStackTrace().toString(), "Utilities Exception");
				} catch (BadSignatureException e) {
					ErrorDialog.show("Could not create a nyad due to signature issue.\n" + e.getSourceMessage() + "\n"
							+ e.getStackTrace().toString(), "Bad Signature Exception");
				}
				break;
			case REALD:
				NyadRealD aNyadRD = buildANyadRD(j, intOrd); // the NyadRD bootstrapper
				try // Here we finally initiate the NyadPanel because the Nyad is actually filled at
					// this point.
				{
					if (aNyadRD != null) {
						nyadPanelList.add(j, new NyadPanel(_GUI, aNyadRD));
						JScrollPane tempPane = new JScrollPane(nyadPanelList.get(j));
						tempPane.setWheelScrollingEnabled(true);
						nyadPanes.addTab(new StringBuffer().append(j).toString(), tabIcon, tempPane);
					} else
						_GUI.appStatusBar.setStatusMsg("... null NyadRealD for new NyadPanel avoided.\n");
				} catch (UtilitiesException eutil) {
					ErrorDialog.show("Could not create new NyadPanel.\n" + eutil.getSourceMessage() + "\n"
							+ eutil.getStackTrace().toString(), "Utilities Exception");
				} catch (BadSignatureException e) {
					ErrorDialog.show("Could not create a nyad due to signature issue.\n" + e.getSourceMessage() + "\n"
							+ e.getStackTrace().toString(), "Bad Signature Exception");
				}
				break;
			case COMPLEXF:
				NyadComplexF aNyadCF = buildANyadCF(j, intOrd); // the NyadCF bootstrapper
				try // Here we finally initiate the NyadPanel because the Nyad is actually filled at
					// this point.
				{
					if (aNyadCF != null) {
						nyadPanelList.add(j, new NyadPanel(_GUI, aNyadCF));
						JScrollPane tempPane = new JScrollPane(nyadPanelList.get(j));
						tempPane.setWheelScrollingEnabled(true);
						nyadPanes.addTab(new StringBuffer().append(j).toString(), tabIcon, tempPane);
					} else
						_GUI.appStatusBar.setStatusMsg("... null NyadComplexF for new NyadPanel avoided.\n");
				} catch (UtilitiesException eutil) {
					ErrorDialog.show("Could not create new NyadPanel.\n" + eutil.getSourceMessage() + "\n"
							+ eutil.getStackTrace().toString(), "Utilities Exception");
				} catch (BadSignatureException e) {
					ErrorDialog.show("Could not create a nyad due to signature issue.\n" + e.getSourceMessage() + "\n"
							+ e.getStackTrace().toString(), "Bad Signature Exception");
				}
				break;
			case COMPLEXD:
				NyadComplexD aNyadCD = buildANyadCD(j, intOrd); // the NyadCD bootstrapper
				try // Here we finally initiate the NyadPanel because the Nyad is actually filled at
					// this point.
				{
					if (aNyadCD != null) {
						nyadPanelList.add(j, new NyadPanel(_GUI, aNyadCD));
						JScrollPane tempPane = new JScrollPane(nyadPanelList.get(j));
						tempPane.setWheelScrollingEnabled(true);
						nyadPanes.addTab(new StringBuffer().append(j).toString(), tabIcon, tempPane);
					} else
						_GUI.appStatusBar.setStatusMsg("... null NyadComplexD for new NyadPanel avoided.\n");
				} catch (UtilitiesException eutil) {
					ErrorDialog.show("Could not create new NyadPanel.\n" + eutil.getSourceMessage() + "\n"
							+ eutil.getStackTrace().toString(), "Utilities Exception");
				} catch (BadSignatureException e) {
					ErrorDialog.show("Could not create a nyad due to signature issue.\n" + e.getSourceMessage() + "\n"
							+ e.getStackTrace().toString(), "Bad Signature Exception");
				}
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

			NyadPanel tempPanel = (NyadPanel) nyadPanelList.remove(where - 1);
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

			NyadPanel tempPanel = (NyadPanel) nyadPanelList.remove(where);
			nyadPanelList.add(where + 1, tempPanel);

			revalidate();
		}
	}

	private CladosField validateInitialDivField() {
		// CladosField nField;
		try {
			String sType = _GUI.IniProps.getProperty("Desktop.Default.DivField");
			switch (sType) {
			case "RealF":
				return CladosField.REALF;
			case "RealD":
				return CladosField.REALD;
			case "ComplexF":
				return CladosField.COMPLEXF;
			case "ComplexD":
				return CladosField.COMPLEXD;
			default:
				return null;
			}

		} catch (NullPointerException eNull) {
			_GUI.appStatusBar
					.setStatusMsg("\nDesktop.Default.DivField from the configuration file appears to be null.\n");
			_GUI.appStatusBar.setStatusMsg("No nyad will be initialized.\n");
		} catch (NumberFormatException eFormat) {
			_GUI.appStatusBar.setStatusMsg(
					"\nDesktop.Default.DivField from the configuration file appears to be non-parse-able.\n");
			_GUI.appStatusBar.setStatusMsg("No nyad will be initialized.\n");
		}
		return null;
	}

	private int validateInitialNyadCount() {
		int nCount = 0;
		try {
			nCount = Integer.parseInt(_GUI.IniProps.getProperty("Desktop.Default.Count"));
			nyadPanelList = new ArrayList<NyadPanel>(nCount);
		} catch (NullPointerException eNull) {
			_GUI.appStatusBar.setStatusMsg(
					"\nDesktop.Default.Count from the configuration file appears to be null. Set to Zero.\n");
			nyadPanelList = new ArrayList<NyadPanel>(0);
		} catch (NumberFormatException eFormat) {
			_GUI.appStatusBar.setStatusMsg(
					"\nDesktop.Default.Count from the configuration file appears to be non-parse-able. Set to Zero.\n");
			nyadPanelList = new ArrayList<NyadPanel>(0);
		}
		return nCount;
	}

	private short validateInitialNyadOrder() {
		short nOrd = 1;
		try {
			nOrd = Short.parseShort(_GUI.IniProps.getProperty("Desktop.Default.Order"));
		} catch (NullPointerException eNull) {
			_GUI.appStatusBar.setStatusMsg(
					"\nDesktop.Default.Order from the configuration file appears to be null. Set to ONE.\n");
		} catch (NumberFormatException eFormat) {
			_GUI.appStatusBar.setStatusMsg(
					"\nDesktop.Default.Order from the configuration file appears to be non-parse-able. Set to ONE.\n");
		}
		return nOrd;
	}

	protected void addNyad(CladosField pRep, NyadAbstract pN) {
		NyadPanel newP;
		int endPlus = 0;
		if (nyadPanes.getTabCount() > 0)
			endPlus = Integer.valueOf(nyadPanes.getTitleAt(nyadPanes.getTabCount() - 1)) + 1;
		nyadPanelList.ensureCapacity(endPlus + 1);
		try {
			switch (pRep) {
			case REALF:
				newP = new NyadPanel(_GUI, (NyadRealF) pN);
				break;
			case REALD:
				newP = new NyadPanel(_GUI, (NyadRealD) pN);
				break;
			case COMPLEXF:
				newP = new NyadPanel(_GUI, (NyadComplexF) pN);
				break;
			case COMPLEXD:
				newP = new NyadPanel(_GUI, (NyadComplexD) pN);
				break;
			default:
				newP = null;
			}
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
	 * @param pFieldPanel FieldPanel In the owning app, this is the FieldBar object
	 *                    that allows for top-level numeric input on the calculator.
	 *                    The Field Panel offered is registered with this Viewer
	 *                    Panel so change events can be routed.
	 */
	protected void registerFieldPanel(FieldPanel pFieldPanel) {
		nyadPanes.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// _GUI._StatusBar.setStatusMsg("ChangeEvent for
				// "+e.getSource().getClass().getName()+".\n");
				if (nyadPanes.getTabCount() > 0) {
					if (nyadPanes.getSelectedIndex() >= 0) {
						if (nyadPanelList.get(nyadPanes.getSelectedIndex()).monadPanes.getTabCount() > 0) {
							int j = nyadPanelList.get(nyadPanes.getSelectedIndex()).monadPanes.getSelectedIndex();
							MonadPanel tSpot = nyadPanelList.get(nyadPanes.getSelectedIndex()).getMonadPanel(j);
							switch (tSpot.getRepMode()) {
							case REALF:
								pFieldPanel.setField(AlgebraRealF.shareProtoNumber(tSpot.getMonadRF().getAlgebra()));
								break;
							case REALD:
								pFieldPanel.setField(AlgebraRealD.shareProtoNumber(tSpot.getMonadRD().getAlgebra()));
								break;
							case COMPLEXF:
								pFieldPanel.setField(AlgebraComplexF.shareProtoNumber(tSpot.getMonadCF().getAlgebra()));
								break;
							case COMPLEXD:
								pFieldPanel.setField(AlgebraComplexD.shareProtoNumber(tSpot.getMonadCD().getAlgebra()));
							}
							_GUI.appFieldBar.makeWritable();
						}
					}
				} else
					pFieldPanel.clearFieldType();
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