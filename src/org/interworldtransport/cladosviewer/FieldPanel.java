/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.FieldPanel<br>
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
 * ---org.interworldtransport.cladosviewer.FieldPanel<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewer;

import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosF.ComplexD;
import org.interworldtransport.cladosF.ComplexF;
import org.interworldtransport.cladosF.UnitAbstract;
import org.interworldtransport.cladosF.RealD;
import org.interworldtransport.cladosF.RealF;
import org.interworldtransport.cladosFExceptions.FieldException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 * The FieldPanel class is intended to be the the numeric input panel for the
 * calculator.
 * <p>
 * This is the entry register. On a RPN calculator, it is the bottom of the
 * stack. In this calculator it can't quite be, though. There is no single stack
 * here, so this register is more of a feeder to functions that require a field
 * scalar to combine with elements of the stacks.
 * <p>
 * This about how scalar multiplication works in vector spaces. This is where
 * the scalar is stored for the function to use. That 'scalar' is 'repField'.
 * <p>
 * 
 * @version 0.85
 * @author Dr Alfred W Differ
 */

public class FieldPanel extends JPanel implements ActionListener, FocusListener {
	private static final int FONTSIZE = 12;
	private static final int _DOUBLESIZE = 16;
	private static final int _FLOATSIZE = 10;
	private static final String _IMAGINARY = "[I]";
	private static final String _REAL = "[R]";
	private static final Font _PLAINFONT = new Font(Font.SERIF, Font.PLAIN, FONTSIZE);
	private static final Color clrBackColor = new Color(230, 255, 255);
	private static final Color clrNullColor = new Color(255, 230, 255);
	private static final Dimension squareLarge = new Dimension(42, 42);
	private static final Dimension squareMedium = new Dimension(21, 21);

	private CladosCalculator _GUI;
	private CladosField _repMode;
	private final String[] _valLabels = { _REAL, _IMAGINARY };
	private JButton btnClear;
	private JButton btnConjugate;
	private JButton btnInverse;
	private JButton btnMakeComplex;
	private JButton btnMakeDouble;
	private JButton btnMakeFloat;
	private JButton btnMakeReal;
	private JPanel pnlDisplays;
	private ArrayList<JTextField> valDisplays;
	protected ComplexD _repComplexD;
	protected ComplexF _repComplexF;
	protected RealD _repRealD;
	protected RealF _repRealF;

	/**
	 * The FieldPanel class is intended to be contain a cladosF Field in much the
	 * same way as Monad and Nyad panels represent their contents to the calculator
	 * 
	 * @param pGUI CladosCalculator This parameter references the owning
	 *             application. It's there to offer a way to get error messages to
	 *             the GUI.
	 * @param pIn  ComplexD This parameter offers one of the DivField objects to
	 *             display so the panel doesn't make one of its own.
	 */
	public FieldPanel(CladosCalculator pGUI, ComplexD pIn) {
		super();
		_GUI = pGUI;
		_repMode = CladosField.COMPLEXD;
		_repComplexD = pIn;
		setBackground(clrBackColor);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		add(createControlLayout(), BorderLayout.LINE_START);
		add(createDisplaysLayout(), BorderLayout.CENTER);
		setCoefficientDisplay(pIn);
		_GUI.appGeometryView.registerFieldPanel(this);
	}

	/**
	 * The FieldPanel class is intended to be contain a cladosF Field in much the
	 * same way as Monad and Nyad panels represent their contents to the calculator
	 * 
	 * @param pGUI CladosCalculator This parameter references the owning
	 *             application. It's there to offer a way to get error messages to
	 *             the GUI.
	 * @param pIn  ComplexF This parameter offers one of the DivField objects to
	 *             display so the panel doesn't make one of its own.
	 */
	public FieldPanel(CladosCalculator pGUI, ComplexF pIn) {
		super();
		_GUI = pGUI;
		_repMode = CladosField.COMPLEXF;
		_repComplexF = pIn;
		setBackground(clrBackColor);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		add(createControlLayout(), BorderLayout.LINE_START);
		add(createDisplaysLayout(), BorderLayout.CENTER);
		setCoefficientDisplay(pIn);
		_GUI.appGeometryView.registerFieldPanel(this);
	}

	/**
	 * The FieldPanel class is intended to be contain a cladosF Field in much the
	 * same way as Monad and Nyad panels represent their contents to the calculator
	 * 
	 * @param pGUI CladosCalculator This parameter references the owning
	 *             application. It's there to offer a way to get error messages to
	 *             the GUI.
	 * @param pIn  RealD This parameter offers one of the DivField objects to
	 *             display so the panel doesn't make one of its own.
	 */
	public FieldPanel(CladosCalculator pGUI, RealD pIn) {
		super();
		_GUI = pGUI;
		_repMode = CladosField.REALD;
		_repRealD = pIn;
		setBackground(clrBackColor);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		add(createControlLayout(), BorderLayout.LINE_START);
		add(createDisplaysLayout(), BorderLayout.CENTER);
		setCoefficientDisplay(pIn);
		_GUI.appGeometryView.registerFieldPanel(this);
	}

	/**
	 * The FieldPanel class is intended to be contain a cladosF Field in much the
	 * same way as Monad and Nyad panels represent their contents to the calculator
	 * 
	 * @param pGUI CladosCalculator This parameter references the owning
	 *             application. It's there to offer a way to get error messages to
	 *             the GUI.
	 * @param pIn  RealF This parameter offers one of the DivField objects to
	 *             display so the panel doesn't make one of its own.
	 */
	public FieldPanel(CladosCalculator pGUI, RealF pIn) {
		super();
		_GUI = pGUI;
		_repMode = CladosField.REALF;
		_repRealF = pIn;
		setBackground(clrBackColor);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		add(createControlLayout(), BorderLayout.LINE_START);
		add(createDisplaysLayout(), BorderLayout.CENTER);
		setCoefficientDisplay(pIn);
		_GUI.appGeometryView.registerFieldPanel(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "clearIt" -> {
			if (_repMode == CladosField.COMPLEXF | _repMode == CladosField.COMPLEXD)
				setImgText("");
			setRealText("");
		}
		case "conjugate" -> {
			try {
				switch (_repMode) {
				case REALF:
					_repRealF = (RealF) CladosField.REALF.createZERO(_repRealF);
					_repRealF.setReal(Float.parseFloat(getRealText()));
					setCoefficientDisplay(_repRealF.conjugate());
					break;
				case REALD:
					_repRealD = (RealD) CladosField.REALD.createZERO(_repRealD);
					_repRealD.setReal(Double.parseDouble(getRealText()));
					setCoefficientDisplay(_repRealD.conjugate());
					break;
				case COMPLEXF:
					_repComplexF = (ComplexF) CladosField.COMPLEXF.createZERO(_repComplexF);
					_repComplexF.setReal(Float.parseFloat(getRealText()));
					_repComplexF.setImg(Float.parseFloat(getImgText()));
					setCoefficientDisplay(_repComplexF.conjugate());
					break;
				case COMPLEXD:
					_repComplexD = (ComplexD) CladosField.COMPLEXD.createZERO(_repComplexD);
					_repComplexD.setReal(Double.parseDouble(getRealText()));
					_repComplexD.setImg(Double.parseDouble(getImgText()));
					setCoefficientDisplay(_repComplexD.conjugate());
				}
			} catch (NumberFormatException en) {
				ErrorDialog.show("Conjugation action halted.\nProbably a parsing error when reading FieldBar.\n"
						+ en.getMessage(), "Number Format Exception");
			}
		}
		case "inverse" -> {
			try {
				switch (_repMode) {
				case REALF:
					_repRealF = (RealF) CladosField.REALF.createZERO(_repRealF);
					_repRealF.setReal(Float.parseFloat(getRealText()));
					setCoefficientDisplay(_repRealF.invert());
					break;
				case REALD:
					_repRealD = (RealD) CladosField.REALD.createZERO(_repRealD);
					_repRealD.setReal(Double.parseDouble(getRealText()));
					setCoefficientDisplay(_repRealD.invert());
					break;
				case COMPLEXF:
					_repComplexF = (ComplexF) CladosField.COMPLEXF.createZERO(_repComplexF);
					_repComplexF.setReal(Float.parseFloat(getRealText()));
					_repComplexF.setImg(Float.parseFloat(getImgText()));
					setCoefficientDisplay(_repComplexF.invert());
					break;
				case COMPLEXD:
					_repComplexD = (ComplexD) CladosField.COMPLEXD.createZERO(_repComplexD);
					_repComplexD.setReal(Double.parseDouble(getRealText()));
					_repComplexD.setImg(Double.parseDouble(getImgText()));
					setCoefficientDisplay(_repComplexD.invert());
				}
			} catch (FieldException e) {
				ErrorDialog.show("Field Exception prevented inversion.\nCardinal=" + e.getSource().getCardinalString()
						+ "\nSource Message=" + e.getSourceMessage(), "Field Exception");
			} catch (NumberFormatException en) {
				ErrorDialog.show("Inversion action halted.\nProbably a parsing error when reading FieldBar.\n",
						"Number Format Exception");
			}
		}
		case "makeFloat" -> {
			if (_GUI.appGeometryView.getNyadListSize() != 0) {
				ErrorDialog.show("Can't change DivField descendent while any nyads are displayed.", "DivField Change");
				break;
			}
			valDisplays.clear();
			remove(pnlDisplays);
			if (_repMode == CladosField.REALD) {
				_repMode = CladosField.REALF;
				if (_repRealF == null)
					_repRealF = RealF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			} else if (_repMode == CladosField.COMPLEXD) {
				_repMode = CladosField.COMPLEXF;
				if (_repComplexF == null)
					_repComplexF = ComplexF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			}
			btnMakeDouble.setEnabled(true);
			btnMakeFloat.setEnabled(false);
			repaint();
			_GUI.appGeometryView.setRepMode(_repMode);
			add(createDisplaysLayout(), BorderLayout.LINE_END);
			_GUI.pack();
		}
		case "makeDouble" -> {
			if (_GUI.appGeometryView.getNyadListSize() != 0) {
				ErrorDialog.show("Can't change DivField descendent while any nyads are displayed.", "DivField Change");
				break;
			}
			valDisplays.clear();
			remove(pnlDisplays);
			if (_repMode == CladosField.REALF) {
				_repMode = CladosField.REALD;
				if (_repRealD == null)
					_repRealD = RealD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			} else if (_repMode == CladosField.COMPLEXF) {
				_repMode = CladosField.COMPLEXD;
				if (_repComplexD == null)
					_repComplexD = ComplexD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			}
			btnMakeFloat.setEnabled(true);
			btnMakeDouble.setEnabled(false);
			repaint();
			_GUI.appGeometryView.setRepMode(_repMode);
			add(createDisplaysLayout(), BorderLayout.LINE_END);
			_GUI.pack();
		}
		case "makeReal" -> {
			if (_GUI.appGeometryView.getNyadListSize() != 0) {
				ErrorDialog.show("Can't change DivField descendent while any nyads are displayed.", "DivField Change");
				break;
			}
			valDisplays.clear();
			remove(pnlDisplays);
			if (_repMode == CladosField.COMPLEXF) {
				_repMode = CladosField.REALF;
				if (_repRealF == null)
					_repRealF = RealF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			} else if (_repMode == CladosField.COMPLEXD) {
				_repMode = CladosField.REALD;
				if (_repRealD == null)
					_repRealD = RealD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			}
			btnMakeComplex.setEnabled(true);
			btnMakeReal.setEnabled(false);
			repaint();
			_GUI.appGeometryView.setRepMode(_repMode);
			add(createDisplaysLayout(), BorderLayout.LINE_END);
			_GUI.pack();
		}
		case "makeComplex" -> {
			if (_GUI.appGeometryView.getNyadListSize() != 0) {
				ErrorDialog.show("Can't change DivField descendent while any nyads are displayed.", "DivField Change");
				break;
			}
			valDisplays.clear();
			remove(pnlDisplays);
			if (_repMode == CladosField.REALF) {
				_repMode = CladosField.COMPLEXF;
				if (_repComplexF == null)
					_repComplexF = ComplexF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			} else if (_repMode == CladosField.REALD) {
				_repMode = CladosField.COMPLEXD;
				if (_repComplexD == null)
					_repComplexD = ComplexD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			}
			btnMakeReal.setEnabled(true);
			btnMakeComplex.setEnabled(false);
			repaint();
			_GUI.appGeometryView.setRepMode(_repMode);
			add(createDisplaysLayout(), BorderLayout.LINE_END);
			_GUI.pack();
		}
		default -> ErrorDialog.show("No detectable command processed.", "Action At FieldBar Attempted");
		}
	}

	/**
	 * This method is called when focus is gained on the FieldBar. It covers for the
	 * possibility that the underlying DivField is out of sync with displays, so the
	 * display content is updated
	 * 
	 * Any earlier parsing difficulty will get overwritten when focus returns, so
	 * this is also a reset feature.
	 */
	@Override
	public void focusGained(FocusEvent e) {
		switch (_repMode) {
		case REALF -> {
			if (_repRealF != null)
				setRealText(Float.valueOf(_repRealF.getReal()).toString());
		}
		case REALD -> {
			if (_repRealD != null)
				setRealText(Double.valueOf(_repRealD.getReal()).toString());
		}
		case COMPLEXF -> {
			if (_repComplexD != null) {
				setRealText(Float.valueOf(_repComplexF.getReal()).toString());
				setImgText(Float.valueOf(_repComplexF.getImg()).toString());
			}
		}
		case COMPLEXD -> {
			if (_repComplexD != null) {
				setRealText(Double.valueOf(_repComplexD.getReal()).toString());
				setImgText(Double.valueOf(_repComplexD.getImg()).toString());
			}
		}
		}
	}

	/**
	 * This method is called when focus is lost on the FieldBar. The actions
	 * attempted assume that change might have occurred while focus was present.
	 * Changes might leave the underlying DivField out of sync with what is
	 * displayed, so the display content is copied down to the DivField.
	 * 
	 * Any parsing difficulty results in an exception that simply stops the update.
	 * That CAN leave the represented DivField out of sync with the display. In that
	 * event, just bring focus back to the FieldBar and fix things so the numbers
	 * can be parsed successfully.
	 */
	@Override
	public void focusLost(FocusEvent e) {
		try {
			switch (_repMode) {
			case REALF -> {
				if (_repRealF != null)
					_repRealF.setReal(Float.parseFloat(getRealText()));
			}
			case REALD -> {
				if (_repRealD != null)
					_repRealD.setReal(Double.parseDouble(getRealText()));
			}
			case COMPLEXF -> {
				if (_repComplexF != null) {
					_repComplexF.setReal(Float.parseFloat(getRealText()));
					_repComplexF.setImg(Float.parseFloat(getImgText()));
				}
			}
			case COMPLEXD -> {
				if (_repComplexD != null) {
					_repComplexD.setReal(Double.parseDouble(getRealText()));
					_repComplexD.setImg(Double.parseDouble(getImgText()));
				}
			}
			}
		} catch (NumberFormatException en) {
			ErrorDialog.show("Couldn't parse FieldBar.\nNo action taken setting the Div Field it represents.",
					"Parse Issue");
		}

	}

	public String getImgText() {
		return valDisplays.get(1).getText();
	}

	public String getRealText() {
		return valDisplays.get(0).getText();
	}

	public CladosField getRepMode() {
		return _repMode;
	}

	public void setImgText(String pIn) {
		valDisplays.get(1).setText(pIn);
	}

	public void setRealText(String pIn) {
		valDisplays.get(0).setText(pIn);
	}

	/**
	 * This 'set' function simply sets the displayed number (a double) into the
	 * first imaginary display text field.
	 * <p>
	 * 
	 * @param pWhat double Provide the primitive here to adjust what real number is
	 *              displayed.
	 */
	public void setWhatDoubleI(double pWhat) {
		setImgText((new StringBuffer().append(pWhat)).toString());
	}

	/**
	 * This 'set' function simply sets the displayed number (a double) into the real
	 * number display text field.
	 * <p>
	 * 
	 * @param pWhat double Provide the primitive here to adjust what real number is
	 *              displayed.
	 */
	public void setWhatDoubleR(double pWhat) {
		setRealText((new StringBuffer().append(pWhat)).toString());
	}

	/**
	 * This 'set' function simply sets the displayed number (a float) into the first
	 * imaginary number display text field.
	 * <p>
	 * 
	 * @param pWhat float Provide the primitive here to adjust what real number is
	 *              displayed.
	 */
	public void setWhatFloatI(float pWhat) {
		setImgText((new StringBuffer().append(pWhat)).toString());
	}

	/**
	 * This 'set' function simply sets the displayed number (a float) into the real
	 * number display text field.
	 * <p>
	 * 
	 * @param pWhat float Provide the primitive here to adjust what real number is
	 *              displayed.
	 */
	public void setWhatFloatR(float pWhat) {
		setRealText((new StringBuffer().append(pWhat)).toString());
	}

	/**
	 * This 'set' function simply sets the displayed number (an int) into the first
	 * imaginary display text field.
	 * <p>
	 * I'm not sure why this method is protected and its siblings are public.
	 * <p>
	 * 
	 * @param pWhat int Provide the primitive here to adjust what real number is
	 *              displayed.
	 */
	public void setWhatIntI(int pWhat) {
		setImgText((new StringBuffer(pWhat)).toString());
	}

	/**
	 * This 'set' function simply sets the displayed number (an int) into the real
	 * number display text field.
	 * <p>
	 * I'm not sure why this method is protected and its siblings are public.
	 * <p>
	 * 
	 * @param pWhat int Provide the primitive here to adjust what real number is
	 *              displayed.
	 */
	public void setWhatIntR(int pWhat) {
		setRealText((new StringBuffer(pWhat)).toString());
	}

	private JPanel createControlLayout() {
		JPanel pnlButtons = new JPanel();
		pnlButtons.setBackground(clrBackColor);
		pnlButtons.setLayout(new GridBagLayout());
		pnlButtons.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		GridBagConstraints c1 = new GridBagConstraints();
		c1.insets = new Insets(0, 0, 0, 0);
		c1.fill = GridBagConstraints.BOTH;
		c1.anchor = GridBagConstraints.WEST;

		c1.gridx = 0;
		c1.gridy = 0;
		c1.weightx = 0;
		c1.weighty = 0;

		btnMakeReal = new JButton(new ImageIcon(this.getClass().getResource("/icons/real.png")));
		btnMakeReal.setActionCommand("makeReal");
		btnMakeReal.setToolTipText("use real numbers");
		btnMakeReal.setPreferredSize(squareMedium);
		btnMakeReal.setBorder(BorderFactory.createEtchedBorder(0));
		btnMakeReal.addActionListener(this);
		pnlButtons.add(btnMakeReal, c1);
		c1.gridy++;

		btnMakeComplex = new JButton(new ImageIcon(this.getClass().getResource("/icons/complex.png")));
		btnMakeComplex.setActionCommand("makeComplex");
		btnMakeComplex.setToolTipText("use complex numbers");
		btnMakeComplex.setPreferredSize(squareMedium);
		btnMakeComplex.setBorder(BorderFactory.createEtchedBorder(0));
		btnMakeComplex.addActionListener(this);
		pnlButtons.add(btnMakeComplex, c1);
		c1.gridy = 0;
		c1.gridx++;

		btnMakeFloat = new JButton(new ImageIcon(this.getClass().getResource("/icons/float.png")));
		btnMakeFloat.setActionCommand("makeFloat");
		btnMakeFloat.setToolTipText("use floating precision");
		btnMakeFloat.setPreferredSize(squareMedium);
		btnMakeFloat.setBorder(BorderFactory.createEtchedBorder(0));
		btnMakeFloat.addActionListener(this);
		pnlButtons.add(btnMakeFloat, c1);
		c1.gridy++;

		btnMakeDouble = new JButton(new ImageIcon(this.getClass().getResource("/icons/double.png")));
		btnMakeDouble.setActionCommand("makeDouble");
		btnMakeDouble.setToolTipText("use double precision");
		btnMakeDouble.setPreferredSize(squareMedium);
		btnMakeDouble.setBorder(BorderFactory.createEtchedBorder(0));
		btnMakeDouble.addActionListener(this);
		pnlButtons.add(btnMakeDouble, c1);
		c1.gridy = 0;
		c1.gridx++;

		c1.gridheight = 2;
		c1.gridwidth = 2;

		btnClear = new JButton(new ImageIcon(this.getClass().getResource("/icons/clearIt.png")));
		btnClear.setActionCommand("clearIt");
		btnClear.setPreferredSize(squareLarge);
		btnClear.setBorder(BorderFactory.createEtchedBorder(0));
		btnClear.addActionListener(this);
		pnlButtons.add(btnClear, c1);
		c1.gridx += 2;

		btnInverse = new JButton(new ImageIcon(this.getClass().getResource("/icons/inverse.png")));
		btnInverse.setActionCommand("inverse");
		btnInverse.setPreferredSize(squareLarge);
		btnInverse.setBorder(BorderFactory.createEtchedBorder(0));
		btnInverse.addActionListener(this);
		pnlButtons.add(btnInverse, c1);
		c1.gridx += 2;

		btnConjugate = new JButton(new ImageIcon(this.getClass().getResource("/icons/conjugate.png")));
		btnConjugate.setActionCommand("conjugate");
		btnConjugate.setPreferredSize(squareLarge);
		btnConjugate.setBorder(BorderFactory.createEtchedBorder(0));
		btnConjugate.addActionListener(this);
		pnlButtons.add(btnConjugate, c1);

		return pnlButtons;
	}

	private JPanel createDisplaysLayout() {
		pnlDisplays = new JPanel();
		pnlDisplays.setBackground(clrBackColor);
		pnlDisplays.setLayout(new GridBagLayout());

		GridBagConstraints c2 = new GridBagConstraints();
		c2.insets = new Insets(0, 0, 0, 0);
		c2.fill = GridBagConstraints.BOTH;
		c2.anchor = GridBagConstraints.EAST;

		c2.gridx = 0;
		c2.gridy = 0;
		c2.weightx = 1;
		c2.weighty = 1;

		switch (_repMode) {
		case REALF -> {
			for (short m = 0; m < 1; m++) {
				pnlDisplays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
				c2.gridy++;
			}
			btnMakeComplex.setEnabled(true);
			btnMakeReal.setEnabled(false);
		}
		case REALD -> {
			for (short m = 0; m < 1; m++) {
				pnlDisplays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
				c2.gridy++;
			}
			btnMakeComplex.setEnabled(true);
			btnMakeReal.setEnabled(false);
		}
		case COMPLEXF -> {
			for (short m = 0; m < 2; m++) {
				pnlDisplays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
				c2.gridy++;
			}
			btnMakeComplex.setEnabled(false);
			btnMakeReal.setEnabled(true);
		}
		case COMPLEXD -> {
			for (short m = 0; m < 2; m++) {
				pnlDisplays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
				c2.gridy++;
			}
			btnMakeComplex.setEnabled(false);
			btnMakeReal.setEnabled(true);
		}
		}

		c2.gridy = 0;
		c2.gridx++;

		int m;
		JTextField tSpot;
		switch (_repMode) {
		case REALF -> {
			valDisplays = new ArrayList<JTextField>(1);
			for (m = 0; m < 1; m++) {
				tSpot = new JTextField();
				tSpot.setColumns(FieldPanel._FLOATSIZE);
				tSpot.setFont(_PLAINFONT);
				tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				tSpot.addFocusListener(this);
				valDisplays.add(m, tSpot);
				pnlDisplays.add(tSpot, c2);
				c2.gridy++;
			}
			btnMakeFloat.setEnabled(false);
			btnMakeDouble.setEnabled(true);
		}
		case REALD -> {
			valDisplays = new ArrayList<JTextField>(1);
			for (m = 0; m < 1; m++) {
				tSpot = new JTextField();
				tSpot.setColumns(FieldPanel._DOUBLESIZE);
				tSpot.setFont(_PLAINFONT);
				tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				tSpot.addFocusListener(this);
				valDisplays.add(m, tSpot);
				pnlDisplays.add(tSpot, c2);
				c2.gridy++;
			}
			btnMakeFloat.setEnabled(true);
			btnMakeDouble.setEnabled(false);
		}
		case COMPLEXF -> {
			valDisplays = new ArrayList<JTextField>(2);
			for (m = 0; m < 2; m++) {
				tSpot = new JTextField();
				tSpot.setColumns(FieldPanel._FLOATSIZE);
				tSpot.setFont(_PLAINFONT);
				tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				tSpot.addFocusListener(this);
				valDisplays.add(m, tSpot);
				pnlDisplays.add(tSpot, c2);
				c2.gridy++;
			}
			btnMakeFloat.setEnabled(false);
			btnMakeDouble.setEnabled(true);
		}
		case COMPLEXD -> {
			valDisplays = new ArrayList<JTextField>(2);
			for (m = 0; m < 2; m++) {
				tSpot = new JTextField();
				tSpot.setColumns(FieldPanel._DOUBLESIZE);
				tSpot.setFont(_PLAINFONT);
				tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				tSpot.addFocusListener(this);
				valDisplays.add(m, tSpot);
				pnlDisplays.add(tSpot, c2);
				c2.gridy++;
			}
			btnMakeFloat.setEnabled(true);
			btnMakeDouble.setEnabled(false);
		}
		}
		return pnlDisplays;
	}

	/**
	 * This clearing function wipes the slate within the Field panel. No represented
	 * Fields should remain.
	 */
	protected void clearFieldType() {
		_repRealF = null;
		_repRealD = null;
		_repComplexF = null;
		_repComplexD = null;
	}

	protected void makeNotWritable() {
		if (valDisplays != null)
			for (JTextField point : valDisplays)
				point.setEditable(false);
	}

	protected void makeWritable() {
		if (valDisplays != null)
			for (JTextField point : valDisplays)
				point.setEditable(true);
	}

	/**
	 * This 'set' function simply adjusts the displayed complex number by accepting
	 * an input and then pushing those parts to values stored in the panel.
	 * 
	 * @param pIn ComplexD When resetting the complex number to be displayed, feed
	 *            the ComplexF in with this parameter.
	 */
	protected void setCoefficientDisplay(ComplexD pIn) {
		// TODO re-write these four setCoefficientDisplay methods to switch on the
		// _repMode and set on the class type of the DivField offered.
		if (_repMode == CladosField.COMPLEXD)
			if (pIn != null) {
				setRealText(Double.valueOf(pIn.getReal()).toString());
				setImgText(Double.valueOf(pIn.getImg()).toString());
				setField(pIn);
			}
	}

	/**
	 * This 'set' function simply adjusts the displayed complex number by accepting
	 * an input and then pushing those parts to values stored in the panel.
	 * 
	 * @param pIn ComplexF When resetting the complex number to be displayed, feed
	 *            the ComplexF in with this parameter.
	 */
	protected void setCoefficientDisplay(ComplexF pIn) {
		if (_repMode == CladosField.COMPLEXF)
			if (pIn != null) {
				setRealText(Float.valueOf(pIn.getReal()).toString());
				setImgText(Float.valueOf(pIn.getImg()).toString());
				setField(pIn);
			}
	}

	/**
	 * This 'set' function simply adjusts the displayed complex number by accepting
	 * an input and then pushing those part(s) to values stored in the panel.
	 * 
	 * @param pIn RealD When resetting the real number to be displayed, feed the
	 *            RealF in with this parameter.
	 */
	protected void setCoefficientDisplay(RealD pIn) {
		if (_repMode == CladosField.REALD)
			if (pIn != null) {
				setRealText(Double.valueOf(pIn.getReal()).toString());
				setField(pIn);
			}
	}

	/**
	 * This 'set' function simply adjusts the displayed complex number by accepting
	 * an input and then pushing those part(s) to values stored in the panel.
	 * 
	 * @param pIn RealF When resetting the real number to be displayed, feed the
	 *            RealF in with this parameter.
	 */
	protected void setCoefficientDisplay(RealF pIn) {
		if (_repMode == CladosField.REALF)
			if (pIn != null) {
				setRealText(Float.valueOf(pIn.getReal()).toString());
				setField(pIn);
			}
	}

	/**
	 * This 'set' function accepts a DivField used as context for the division field
	 * being displayed.
	 * 
	 * @param pField DivField Provide the cladosF DivField here so reference match
	 *               requirements are met on later function calls. The DivField
	 *               child will be determined and then the FieldBar assigned.
	 */
	@SuppressWarnings("null")
	protected void setField(UnitAbstract pField) {
		if (pField == null)
			return;
		CladosField test = null;
		if (pField instanceof RealF)
			test = CladosField.REALF;
		else if (pField instanceof RealD)
			test = CladosField.REALD;
		else if (pField instanceof ComplexF)
			test = CladosField.COMPLEXF;
		else if (pField instanceof ComplexD)
			test = CladosField.COMPLEXD;

		// test will NOT be null at this point because there are only four DivField
		// descendants. IF CladosField is ever extended to others, though, this WILL
		// fail.
		switch (test) {
		case REALF -> {
			_repRealF = (RealF) pField;
			_repRealD = null;
			_repComplexF = null;
			_repComplexD = null;
			if (pField.getCardinal() != null)
				setBackground(clrBackColor);
			else
				setBackground(clrNullColor);
		}
		case REALD -> {
			_repRealF = null;
			_repRealD = (RealD) pField;
			_repComplexF = null;
			_repComplexD = null;
			if (pField.getCardinal() != null)
				setBackground(clrBackColor);
			else
				setBackground(clrNullColor);
		}
		case COMPLEXF -> {
			_repRealF = null;
			_repRealD = null;
			_repComplexF = (ComplexF) pField;
			_repComplexD = null;
			if (pField.getCardinal() != null)
				setBackground(clrBackColor);
			else
				setBackground(clrNullColor);
		}
		case COMPLEXD -> {
			_repRealF = null;
			_repRealD = null;
			_repComplexF = null;
			_repComplexD = (ComplexD) pField;
			if (pField.getCardinal() != null)
				setBackground(clrBackColor);
			else
				setBackground(clrNullColor);
		}
		default -> {
			_repRealF = null;
			_repRealD = null;
			_repComplexF = null;
			_repComplexD = null;
			setBackground(clrNullColor);
		}
		}
	}
}