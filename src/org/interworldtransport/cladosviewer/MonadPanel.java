/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.MonadPanel<br>
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
 * ---org.interworldtransport.cladosviewer.MonadPanel<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewer;

import org.interworldtransport.cladosF.CladosFBuilder;
import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosF.Field;
import org.interworldtransport.cladosF.Normalizable;
import org.interworldtransport.cladosF.UnitAbstract;
import org.interworldtransport.cladosG.Blade;
import org.interworldtransport.cladosG.Monad;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import java.util.*;

/**
 * org.interworldtransport.cladosviewer.MonadPanel The MonadPanel class directly
 * handles the gui for a single Monad.
 * 
 * 
 * @version 0.85
 * @author Dr Alfred W Differ
 * @param <T>
 */

public class MonadPanel<T extends UnitAbstract & Field & Normalizable> extends JPanel
		implements ActionListener, FocusListener {
	private static final String _IMAGINARY = "[I]";
	private static final int COEFF_SIZE = 10;
	private static final Font _ITALICFONT = new Font(Font.SERIF, Font.ITALIC, COEFF_SIZE);
	private static final Font _PLAINFONT = new Font(Font.SERIF, Font.PLAIN, COEFF_SIZE);
	private static final String _REAL = "[R]";
	private static final Color clrBackColor = new Color(212, 212, 192);
	private static final Color clrUnlockColor = new Color(255, 192, 192);
	private static final Dimension squareLittle = new Dimension(25, 25);
	private static final Dimension squareMedium = new Dimension(28, 28);

	public CladosCalculator _GUI;
	private JButton btnChangeOrient;
	private JButton btnDualLeft;
	private JButton btnDualRight;
	private JButton btnEdit;
	private JButton btnGradeCrop;
	private JButton btnGradeCut;
	private JButton btnInvertMonad; // This is NOT multiplicative inverse
	private JButton btnNormalizeMonad;
	private JButton btnRestore;
	private JButton btnReverseMonad;
	private JButton btnScaleMonad;
	private JButton btnSync;
	private ImageIcon iconHorizontal;
	private ImageIcon iconVertical;
	private TreeMap<Blade, FieldDisplay<T>> jCoeffs;
	/*
	 * This boolean is for tracking whether the panel knows its monad is an element
	 * in a nyad. This panel is embedded in the nyad create dialog to provide
	 * information on the first monad to create for a new nyad. If the monad being
	 * created is the second one, though, the create dialog should not ask for or
	 * display a Foot. It should force reuse of the nyad's existing Foot.
	 */
	private boolean nyadNotKnown;
	private JPanel pnlMonadAlterControls;
	private JPanel pnlMonadCoeffPanel;
	private JPanel pnlMonadEditControls;
	private JPanel pnlMonadReferences;
	private CladosField repMode;
	private Monad repMonad;
	/*
	 * This boolean is for knowing whether to render the coefficients. This panel
	 * doubles as a monad create dialog where no coefficients can exist until after
	 * a generator signature is given.
	 */
	private boolean useFullPanel;
	protected boolean _editMode;

	protected JTextField aname = new JTextField(16);
	protected JTextField cardname = new JTextField(16);
	protected JTextField foot = new JTextField(16);
	protected JTextField frame = new JTextField(16);
	protected JLabel gradeKey = new JLabel();
	protected JTextField name = new JTextField(16);
	protected JTextField sig = new JTextField(16);

	/**
	 * The MonadPanel class is intended to be contain only the high level parts of a
	 * monad in order to offer its parts for display and manipulation in a 'Create'
	 * dialog.
	 * 
	 * @param pGUI   CladosCalculator
	 * @param noNyad boolean False when the calling object is embedding new monad in
	 *               a known nyad. True when this is to be the first monad in a new
	 *               nyad.
	 */
	public MonadPanel(CladosCalculator pGUI, boolean noNyad) {
		super();
		_GUI = pGUI;
		repMode = _GUI.appFieldBar.getRepMode();
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setBackground(clrBackColor);
		setLayout(new BorderLayout());
		name.setText(_GUI.IniProps.getProperty("Desktop.Default.BaseName"));
		cardname.setText(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
		aname.setText(_GUI.IniProps.getProperty("Desktop.Default.AlgebraName"));
		frame.setText(_GUI.IniProps.getProperty("Desktop.Default.FrameName"));
		foot.setText(_GUI.IniProps.getProperty("Desktop.Default.FootName"));
		sig.setText(_GUI.IniProps.getProperty("Desktop.Default.Sig"));

		useFullPanel = false; // Use this panel in it's small sense
		nyadNotKnown = noNyad;

		pnlMonadReferences = new JPanel();
		pnlMonadReferences.setBackground(clrBackColor);
		pnlMonadReferences.setLayout(new GridBagLayout());

		GridBagConstraints cn0 = new GridBagConstraints();
		cn0.anchor = GridBagConstraints.WEST;
		cn0.gridx = 0;
		cn0.gridy = 0;
		cn0.weightx = 0;
		cn0.weighty = 0;

		pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Cardinal"))), cn0);
		cn0.gridx++;
		cardname.setFont(_ITALICFONT);
		pnlMonadReferences.add(cardname, cn0);
		cn0.gridx = 0;
		cn0.gridy++;

		if (nyadNotKnown) {
			pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Foot"))), cn0);
			cn0.gridx++;
			foot.setFont(_ITALICFONT);
			pnlMonadReferences.add(foot, cn0);
			cn0.gridx = 0;
			cn0.gridy++;
		}

		pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Alg"))), cn0);
		cn0.gridx++;
		aname.setFont(_ITALICFONT);
		pnlMonadReferences.add(aname, cn0);
		cn0.gridx = 0;
		cn0.gridy++;

		pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Sig"))), cn0);
		cn0.gridx++;
		sig.setFont(_ITALICFONT);
		pnlMonadReferences.add(sig, cn0);
		cn0.gridx = 0;
		cn0.gridy++;

		pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Frame"))), cn0);
		cn0.gridx++;
		frame.setFont(_PLAINFONT);
		pnlMonadReferences.add(frame, cn0);
		cn0.gridx = 0;
		cn0.gridy++;

		pnlMonadReferences.add(new JLabel("Name", SwingConstants.RIGHT), cn0);
		cn0.gridx++;
		name.setFont(_PLAINFONT);
		pnlMonadReferences.add(name, cn0);
		cn0.gridx = 0;
		cn0.gridy++;

		add(pnlMonadReferences, "South");
	}

	/**
	 * The MonadPanel class is intended to hold a single Monad and act as its GUI.
	 * This constructor is the base one.
	 * 
	 * @param pGUI CladosCalculator This is just a reference to the owner
	 *             application so error messages can be presented.
	 * @param pM   MonadRealF This is a reference to the monad to be displayed and
	 *             manipulated.
	 */
	public MonadPanel(CladosCalculator pGUI, Monad pM) {
		super();
		useFullPanel = true;
		try {
			_GUI = pGUI;
			repMonad = pM;
			repMode = pM.getMode();

			btnChangeOrient = new JButton();
			btnChangeOrient.setPreferredSize(squareLittle);
			btnChangeOrient.setBorder(BorderFactory.createEtchedBorder(0));
			iconHorizontal = new ImageIcon(this.getClass().getResource("/icons/horiz.png"));
			iconVertical = new ImageIcon(this.getClass().getResource("/icons/vert.png"));

			switch (_GUI.IniProps.getProperty("Desktop.Default.Orient")) {
			case "Vertical" -> {
				btnChangeOrient.setIcon(iconVertical);
				btnChangeOrient.setToolTipText("Monad grades as columns");
			}
			case "Horizontal" -> {
				btnChangeOrient.setIcon(iconHorizontal);
				btnChangeOrient.setToolTipText("Monad grades as rows");
			}
			default -> {
				btnChangeOrient.setIcon(iconVertical);
				btnChangeOrient.setToolTipText("Monad grades as columns");
			}
			}
			btnChangeOrient.setActionCommand("flip");
			btnChangeOrient.addActionListener(this);

			setReferences();

			setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			setBackground(clrBackColor);
			setLayout(new BorderLayout());

			this.add(createCoeffLayout(), "Center");
			setCoefficientDisplay();
		} catch (NullPointerException enull) {
			add(new JPanel(null, false), "Center");
			if (pGUI != null) {
				StringBuffer mess = new StringBuffer(
						"Null Pointer Exception. Something is missing on MPanel construction.\n");
				mess.append(enull.getClass() + "\n");
				mess.append(enull.getMessage() + "\n");
				ErrorDialog.show(mess.toString(), "Null Pointer");
			}
		} finally {
			createReferenceLayout();
			createEditLayout();
			createManagementLayout();
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "findgrade crop" -> _GUI.appEventModel.MOpsParts.gradep.actionPerformed(event);
		case "findgrade cut" -> _GUI.appEventModel.MOpsParts.grades.actionPerformed(event);
		case "scale" -> _GUI.appEventModel.MOpsParts.scale.actionPerformed(event);
		case "normalize" -> _GUI.appEventModel.MOpsParts.norm.actionPerformed(event);
		case "invert" -> _GUI.appEventModel.MOpsParts.invt.actionPerformed(event);
		case "reverse" -> _GUI.appEventModel.MOpsParts.rev.actionPerformed(event);
		case "<dual" -> _GUI.appEventModel.MOpsParts.dualRight.actionPerformed(event);
		case "dual>" -> _GUI.appEventModel.MOpsParts.dualLeft.actionPerformed(event);
		case "flip" -> {
			if (btnChangeOrient.getIcon().equals(iconHorizontal)) {
				btnChangeOrient.setIcon(iconVertical);
				add(createCoeffLayout(), "Center");
				btnChangeOrient.setToolTipText("Monad grades as columns");
			} else {
				btnChangeOrient.setIcon(iconHorizontal);
				add(createCoeffLayout(), "Center");
				btnChangeOrient.setToolTipText("Monad grades as rows");
			}
			validate();
			_GUI.pack();
		}
		case "save" -> {
			setRepMonad(); // Update internal details of the represented Monad
			btnEdit.setActionCommand("edit");
			btnEdit.setToolTipText("start edits");
			btnSync.setEnabled(false);
			btnRestore.setEnabled(false);
			makeNotWritable();
			_editMode = false;
		}
		case "abort" -> {
			setReferences();
			setCoefficientDisplay();
			btnEdit.setActionCommand("edit");
			btnEdit.setToolTipText("start edits");
			btnSync.setEnabled(false);
			btnRestore.setEnabled(false);
			makeNotWritable();
			_editMode = false;
		}
		case ".edit." -> {
			btnEdit.setActionCommand("edit");
			btnEdit.setToolTipText("start edits");
			btnSync.setEnabled(false);
			btnRestore.setEnabled(false);
			makeNotWritable();
			_editMode = false;
		}
		case "edit" -> {
			_editMode = true;
			btnEdit.setActionCommand(".edit.");
			btnEdit.setToolTipText("end edits w/o abort");
			btnSync.setEnabled(true);
			btnRestore.setEnabled(true);
			makeWritable();
		}
		default -> ErrorDialog.show("No detectable command processed.", "Action At MonadPanel Attempted");
		}
	}

	/**
	 * This method is overridden to allow the MonadPanel with the focus to update
	 * the FieldBar with the vales in the FieldArea of the represented monad. This
	 * is similar to what a nyad panel does when it receives focus and updates the
	 * DivFieldType
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void focusGained(FocusEvent e) {
		if (e.getComponent() instanceof FieldDisplay & !_editMode) // Only do this when NOT in edit mode.
		{
			JTextArea tSpot = (JTextArea) e.getComponent();
			StringBuilder strB = new StringBuilder(tSpot.getText());

			int tBufferLength = strB.length();
			if (tBufferLength == 0)
				return; // Nothing to save, so surrender.
			int tR = MonadPanel._REAL.length();
			int tI = MonadPanel._IMAGINARY.length();
			int indexOfR = strB.indexOf(MonadPanel._REAL) + tR;
			int indexOfI = strB.indexOf(MonadPanel._IMAGINARY) + tI;
			_GUI.appFieldBar.setField((((FieldDisplay<T>) e.getComponent()).displayField));
			switch (repMode) {
			case REALF -> {
				//_GUI.appFieldBar.setField((((FieldDisplay<T>) e.getComponent()).displayField));
				_GUI.appFieldBar.setWhatFloatR(Float.parseFloat(strB.substring(indexOfR, tBufferLength)));
			}
			case REALD -> {
				//_GUI.appFieldBar.setField((((FieldDisplay<T>) e.getComponent()).displayField));
				_GUI.appFieldBar.setWhatDoubleR(Double.parseDouble(strB.substring(indexOfR, tBufferLength)));
			}
			case COMPLEXF -> {
				//_GUI.appFieldBar.setField((((FieldDisplay<T>) e.getComponent()).displayField));
				_GUI.appFieldBar.setWhatFloatR(Float.parseFloat(strB.substring(indexOfR, indexOfI - tI - 1)));
				_GUI.appFieldBar.setWhatFloatI(Float.parseFloat(strB.substring(indexOfI, tBufferLength)));
			}
			case COMPLEXD -> {
				//_GUI.appFieldBar.setField((((FieldDisplay<T>) e.getComponent()).displayField));
				_GUI.appFieldBar.setWhatDoubleR(Double.parseDouble(strB.substring(indexOfR, indexOfI - tI - 1)));
				_GUI.appFieldBar.setWhatDoubleI(Double.parseDouble(strB.substring(indexOfI, tBufferLength)));
			}
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		;
	}

	/**
	 * This method provides to the caller an array list of the JTextArea descendents
	 * that hold coefficients for a monad. In this case they are RealF coefficients.
	 * It's just a typical get method, though. Nothing special.
	 * 
	 * @return ArrayList An array list of JTextArea descedents is returned that
	 *         holds the coefficients of a monad.
	 */
	public TreeMap<Blade, FieldDisplay<T>> getJCoeffs() {
		return jCoeffs;
	}

	public Monad getMonad() {
		return repMonad;
	}

	public CladosField getRepMode() {
		return repMode;
	}

	/**
	 * In this method we assume the underlying Monad has changed and the
	 * FieldDisplay's in the panel has to be updated... or the FieldDisplay's
	 * changed and an edit abort occurred. This means working through the Coeff's
	 * for the Monad and updating the FieldDisplays.
	 * 
	 * It is safe enough to call this from anywhere and at any time. The worst that
	 * can happen is the user looses some of their changes on the UI.
	 */
	@SuppressWarnings("unchecked")
	public void setCoefficientDisplay() {
		repMonad.bladeStream().forEach(blade -> {
			jCoeffs.get(blade).updateField((T) repMonad.getScales().get(blade));
			jCoeffs.get(blade).displayContents();
		});
		gradeKey.setText(new StringBuffer().append(repMonad.getGradeKey()).toString());
	}

	private JPanel createCoeffLayout() {
		if (jCoeffs == null) // First time? Create the ArrayList
			initiateCoeffList(); // Listeners get added here the first time and need not be reset
									// because the panels that display them are just containers... not handlers.

		if (pnlMonadCoeffPanel != null)
			remove(pnlMonadCoeffPanel);

		pnlMonadCoeffPanel = new JPanel();
		StringBuffer tB = new StringBuffer();
		tB.append(repMonad.getAlgebra().getFoot().getFootName() + " | ");
		tB.append(repMonad.getAlgebra().getAlgebraName() + "/" + repMonad.getAlgebra().getCardinal().getUnit() + " | ");
		tB.append(repMonad.getAlgebra().getGProduct().signature());
		pnlMonadCoeffPanel.setBorder(BorderFactory.createTitledBorder(tB.toString()));

		pnlMonadCoeffPanel.setBackground(clrBackColor);
		pnlMonadCoeffPanel.setLayout(new GridBagLayout());

		GridBagConstraints cn1 = new GridBagConstraints();
		cn1.insets = new Insets(0, 0, 0, 0);

		cn1.gridx = 0;
		cn1.gridy = 0;
		cn1.weightx = 0;
		cn1.weighty = 0;
		cn1.ipadx = 0;
		cn1.ipady = 0;

		if (btnChangeOrient.getIcon().equals(iconVertical)) {
			repMonad.gradeStream().forEach(grade -> {
				JLabel headLabel2 = new JLabel(grade + "-blades", SwingConstants.CENTER);
				headLabel2.setFont(_PLAINFONT);
				pnlMonadCoeffPanel.add(headLabel2, cn1);
				cn1.gridy++;

				repMonad.getAlgebra().getGBasis().bladeOfGradeStream((byte) grade).forEach(blade -> {
					jCoeffs.get(blade).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					pnlMonadCoeffPanel.add(jCoeffs.get(blade), cn1);
					cn1.gridy++;
				});
				;
				cn1.gridx++;
				cn1.gridy = 0;
			});
		} else if (btnChangeOrient.getIcon().equals(iconHorizontal)) {
			repMonad.gradeStream().forEach(grade -> {
				JLabel headLabel2 = new JLabel(grade + "-blades", SwingConstants.CENTER);
				headLabel2.setFont(_PLAINFONT);
				pnlMonadCoeffPanel.add(headLabel2, cn1);
				cn1.gridx++;

				repMonad.getAlgebra().getGBasis().bladeOfGradeStream((byte) grade).forEach(blade -> {
					jCoeffs.get(blade).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					pnlMonadCoeffPanel.add(jCoeffs.get(blade), cn1);
					cn1.gridx++;
				});
				;
				cn1.gridy++;
				cn1.gridx = 0;
			});
		}
		return pnlMonadCoeffPanel;
	}

	private void createEditLayout() {
		pnlMonadEditControls = new JPanel();
		pnlMonadEditControls.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		pnlMonadEditControls.setBackground(clrBackColor);
		pnlMonadEditControls.setLayout(new GridBagLayout());

		GridBagConstraints cn = new GridBagConstraints();
		makeNotWritable();

		cn.gridx = 0;
		cn.gridy = 0;
		cn.weightx = 0;
		cn.weighty = 0;
		cn.gridwidth = 2;

		btnEdit = new JButton(new ImageIcon(this.getClass().getResource("/icons/edit.png")));
		btnEdit.setActionCommand("edit");
		btnEdit.setToolTipText("start edits");
		btnEdit.setPreferredSize(squareLittle);
		btnEdit.setBorder(BorderFactory.createEtchedBorder(0));
		btnEdit.addActionListener(this);
		pnlMonadEditControls.add(btnEdit, cn);
		cn.gridy++;

		btnSync = new JButton(new ImageIcon(this.getClass().getResource("/icons/save.png")));
		btnSync.setActionCommand("save");
		btnSync.setToolTipText("save edits");
		btnSync.setEnabled(false);
		btnSync.setPreferredSize(squareLittle);
		btnSync.setBorder(BorderFactory.createEtchedBorder(0));
		btnSync.addActionListener(this);
		pnlMonadEditControls.add(btnSync, cn);
		cn.gridy++;

		btnRestore = new JButton(new ImageIcon(this.getClass().getResource("/icons/restore.png")));
		btnRestore.setActionCommand("abort");
		btnRestore.setToolTipText("abandon edits");
		btnRestore.setEnabled(false);
		btnRestore.setPreferredSize(squareLittle);
		btnRestore.setBorder(BorderFactory.createEtchedBorder(0));
		btnRestore.addActionListener(this);
		pnlMonadEditControls.add(btnRestore, cn);
		cn.gridy++;

		cn.gridwidth = 1;
		// btnChangeOrient constructed higher up. Just adding it to a panel here.
		pnlMonadEditControls.add(btnChangeOrient, cn);

		cn.gridy++;
		cn.weighty = 1;
		pnlMonadEditControls.add(new JLabel(), cn);

		add(pnlMonadEditControls, "West");
	}

	private void createManagementLayout() {
		pnlMonadAlterControls = new JPanel();
		pnlMonadAlterControls.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		pnlMonadAlterControls.setBackground(clrBackColor);
		pnlMonadAlterControls.setLayout(new GridBagLayout());

		GridBagConstraints cn = new GridBagConstraints();
		cn.gridx = 0;
		cn.gridy = 0;
		cn.weightx = 0;
		cn.weighty = 0;
		cn.gridwidth = 2;

		btnScaleMonad = new JButton(new ImageIcon(this.getClass().getResource("/icons/scale.png")));
		btnScaleMonad.setActionCommand("scale");
		btnScaleMonad.setToolTipText("scale() the monad");
		btnScaleMonad.setPreferredSize(squareMedium);
		btnScaleMonad.setBorder(BorderFactory.createEtchedBorder(0));
		btnScaleMonad.addActionListener(this);
		pnlMonadAlterControls.add(btnScaleMonad, cn);
		cn.gridy++;

		btnNormalizeMonad = new JButton(new ImageIcon(this.getClass().getResource("/icons/norm.png")));
		btnNormalizeMonad.setActionCommand("normalize");
		btnNormalizeMonad.setToolTipText("normalize THIS Monad");
		btnNormalizeMonad.setPreferredSize(squareMedium);
		btnNormalizeMonad.setBorder(BorderFactory.createEtchedBorder(0));
		btnNormalizeMonad.addActionListener(this);
		pnlMonadAlterControls.add(btnNormalizeMonad, cn);
		cn.gridy++;

		btnInvertMonad = new JButton(new ImageIcon(this.getClass().getResource("/icons/invert.png")));
		btnInvertMonad.setActionCommand("invert");
		btnInvertMonad.setToolTipText("invert [+/-] generators");
		btnInvertMonad.setPreferredSize(squareMedium);
		btnInvertMonad.setBorder(BorderFactory.createEtchedBorder(0));
		btnInvertMonad.addActionListener(this);
		pnlMonadAlterControls.add(btnInvertMonad, cn);
		cn.gridy++;

		btnReverseMonad = new JButton(new ImageIcon(this.getClass().getResource("/icons/reverse.png")));
		btnReverseMonad.setActionCommand("reverse");
		btnReverseMonad.setToolTipText("reverse [ab->ba] blades");
		btnReverseMonad.setPreferredSize(squareMedium);
		btnReverseMonad.setBorder(BorderFactory.createEtchedBorder(0));
		btnReverseMonad.addActionListener(this);
		pnlMonadAlterControls.add(btnReverseMonad, cn);
		cn.gridy++;

		btnDualLeft = new JButton(new ImageIcon(this.getClass().getResource("/icons/dualleft.png")));
		btnDualLeft.setActionCommand("dual>");
		btnDualLeft.setToolTipText("left Dual of the monad using algebra's PS");
		btnDualLeft.setPreferredSize(squareMedium);
		btnDualLeft.setBorder(BorderFactory.createEtchedBorder(0));
		btnDualLeft.addActionListener(this);
		pnlMonadAlterControls.add(btnDualLeft, cn);
		cn.gridy++;

		btnDualRight = new JButton(new ImageIcon(this.getClass().getResource("/icons/dualright.png")));
		btnDualRight.setActionCommand("<dual");
		btnDualRight.setToolTipText("right Dual of the monad using algebra's PS");
		btnDualRight.setPreferredSize(squareMedium);
		btnDualRight.setBorder(BorderFactory.createEtchedBorder(0));
		btnDualRight.addActionListener(this);
		pnlMonadAlterControls.add(btnDualRight, cn);
		cn.gridy++;

		btnGradeCrop = new JButton(new ImageIcon(this.getClass().getResource("/icons/gradecrop.png")));
		btnGradeCrop.setActionCommand("findgrade crop");
		btnGradeCrop.setToolTipText("crop around findgrade()");
		btnGradeCrop.setPreferredSize(squareMedium);
		btnGradeCrop.setBorder(BorderFactory.createEtchedBorder(0));
		btnGradeCrop.addActionListener(this);
		pnlMonadAlterControls.add(btnGradeCrop, cn);
		cn.gridy++;

		btnGradeCut = new JButton(new ImageIcon(this.getClass().getResource("/icons/gradecut.png")));
		btnGradeCut.setActionCommand("findgrade cut");
		btnGradeCut.setToolTipText("cut this findgrade()");
		btnGradeCut.setPreferredSize(squareMedium);
		btnGradeCut.setBorder(BorderFactory.createEtchedBorder(0));
		btnGradeCut.addActionListener(this);
		pnlMonadAlterControls.add(btnGradeCut, cn);
		cn.gridy++;

		cn.weighty = 1;
		pnlMonadAlterControls.add(new JLabel(), cn);

		add(pnlMonadAlterControls, "East");
	}

	private void createReferenceLayout() {
		pnlMonadReferences = new JPanel();

		StringBuffer title = new StringBuffer("Cardinal | ");
		title.append(repMonad.getScales().getCardinal().getUnit());

		TitledBorder tWrap = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title.toString(),
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, _PLAINFONT);

		pnlMonadReferences.setBorder(BorderFactory.createTitledBorder(tWrap));
		pnlMonadReferences.setBackground(clrBackColor);
		pnlMonadReferences.setLayout(new GridBagLayout());

		GridBagConstraints cn0 = new GridBagConstraints();
		cn0.anchor = GridBagConstraints.WEST;

		cn0.gridx = 0;
		cn0.gridy = 0;
		cn0.weightx = 0;
		cn0.weighty = 0;

		pnlMonadReferences.add(new JLabel("Name", SwingConstants.RIGHT), cn0);
		cn0.gridx++;
		name.setFont(_PLAINFONT);
		cn0.weightx = 1;
		pnlMonadReferences.add(name, cn0);
		cn0.weightx = 0.25;
		cn0.gridx++;

		cn0.weightx = 0;

		pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Frame"))), cn0);
		cn0.gridx++;
		frame.setFont(_PLAINFONT);
		cn0.weightx = 1;
		pnlMonadReferences.add(frame, cn0);
		cn0.weightx = 0.25;
		cn0.gridx++;

		pnlMonadReferences.add(new JLabel(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Key"))), cn0);
		cn0.gridx++;
		gradeKey.setFont(_PLAINFONT);
		pnlMonadReferences.add(gradeKey, cn0);

		add(pnlMonadReferences, "South");

	}

	@SuppressWarnings("unchecked")
	private void initiateCoeffList() {
		jCoeffs = new TreeMap<Blade, FieldDisplay<T>>();
		repMonad.bladeStream().forEach(blade -> {
			FieldDisplay<T> tSpot = new FieldDisplay<T>((T) CladosFBuilder.copyOf((T) repMonad.getScales().get(blade)),
					this);
			tSpot.addFocusListener(this);
			jCoeffs.put(blade, tSpot);
		});
	}

	private void makeNotWritable() {
		if (pnlMonadReferences != null)
			pnlMonadReferences.setBackground(clrBackColor);
		if (pnlMonadCoeffPanel != null)
			pnlMonadCoeffPanel.setBackground(clrBackColor);
		name.setEditable(false);

		if (useFullPanel)
			repMonad.bladeStream().forEach(blade -> {
				jCoeffs.get(blade).setEditable(false);
			});
	}

	private void setReferences() {
		name.setText(repMonad.getName());
		aname.setText(repMonad.getAlgebra().getAlgebraName());
		sig.setText(repMonad.getAlgebra().getGProduct().signature());
		frame.setText(repMonad.getFrameName());
		foot.setText(repMonad.getAlgebra().getFoot().getFootName());
		gradeKey.setText(new StringBuffer().append(repMonad.getGradeKey()).toString());
	}

	private void setRepMonad() {
		if (name.getText() != repMonad.getName())
			repMonad.setName(name.getText());

		repMonad.bladeStream().forEach(blade -> {
			FieldDisplay<T> spot = jCoeffs.get(blade); 
			spot.saveContents();
			repMonad.getScales().put(blade, CladosFBuilder.copyOf(spot.displayField));
		});
		gradeKey.setText(String.valueOf(repMonad.getGradeKey()));
	}

	/**
	 * This method adjusts the JTextArea elements contained on the panel to allow
	 * for edits. It has two modes since this panel does too. In one mode the
	 * coefficients are visible and made editable too. In the other, they aren't
	 * visible, so this method skips them.
	 */
	protected void makeWritable() {
		if (pnlMonadReferences != null)
			pnlMonadReferences.setBackground(clrUnlockColor);
		if (pnlMonadCoeffPanel != null)
			pnlMonadCoeffPanel.setBackground(clrUnlockColor);

		name.setEditable(true);
		if (useFullPanel)
			repMonad.bladeStream().forEach(blade -> {
				jCoeffs.get(blade).setEditable(true);
			});

	}
}