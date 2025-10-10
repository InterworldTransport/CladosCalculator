/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.EntryRegister<br>
 * -------------------------------------------------------------------- <br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.<br>
 * 
 * Use of this code or executable objects derived from it by the Licensee 
 * states their willingness to accept the terms of the license. <br> 
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.<br> 
 * 
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.EntryRegister<br>
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosF.ComplexD;
import org.interworldtransport.cladosF.ComplexF;
import org.interworldtransport.cladosF.Field;
import org.interworldtransport.cladosF.Normalizable;
import org.interworldtransport.cladosF.RealD;
import org.interworldtransport.cladosF.RealF;
import org.interworldtransport.cladosF.ProtoN;
import org.interworldtransport.cladosFExceptions.FieldException;

/**
 * The EntryRegister class is intended to be the the numeric input panel for the calculator.
 * <br>
 * RPN calculators have a register at the bottom of the stack. In this calculator 
 * there is no single stack, but this register acts as if it was for whatever function
 * is called.
 * <br>
 * This register can have more than one portion. Two are required for complex numbers.
 * <br>
 * @param <T> ProtoN descendent implementing Field and Normalizable but not the one for Units 
 * because that is covered in ProtoN.
 * @version 1.0
 * @author Dr Alfred W Differ
 */
public class EntryRegister<T extends ProtoN & Field & Normalizable> extends JPanel
		implements ActionListener, FocusListener {
	private static final long serialVersionUID = 3684491842491909996L;
	private static final int _DOUBLESIZE = 16;
	private static final int _FLOATSIZE = 10;
	private static final String _IMAGINARY = "[I]";
	private static final String _REAL = "[R]";
	private static final Color clrBackColor = new Color(230, 255, 255);
	private static final Color clrNullColor = new Color(255, 230, 255);
	private static final int FONTSIZE = 12;
	private static final Font PLAINFONT = new Font(Font.SERIF, Font.PLAIN, FONTSIZE);
	private static final Dimension squareLarge = new Dimension(42, 42);
	private static final Dimension squareMedium = new Dimension(21, 21);
	
	private CladosCalculator _GUI;
	private final String[] _valLabels = { _REAL, _IMAGINARY };
	private JPanel pnlDisplays;
	private CladosField repMode;
	private ArrayList<JTextField> valDisplays;
	
	/**
	 * A Jbutton to be used to shift the panel being used to represent complex numbers
	 */
	protected JButton btnMakeComplex;
	/**
	 * A Jbutton to be used to shift the panel being used to represent double precision floats
	 */
	protected JButton btnMakeDouble;
	/**
	 * A Jbutton to be used to shift the panel being used to represent single precision floats
	 */
	protected JButton btnMakeFloat;
	/**
	 * A Jbutton to be used to shift the panel being used to represent real numbers
	 */
	protected JButton btnMakeReal;
	/**
	 * This is the ProtoN child object that is the cladosF number represented by the register.
	 */
	protected T repNumber;

	/**
	 * The EntryRegister class is intended to contain a cladosF Field in much the
	 * same way as Monad and Nyad panels represent their contents to the calculator.
	 * <br>
	 * @param pGUI CladosCalculator This parameter references the owning
	 *             application. It's there to offer a way to get error messages to
	 *             the GUI.
	 * @param pIn  CladosF number This parameter offers one of the CladosF numbers
	 *             to display so the panel doesn't make one of its own.
	 */
	public EntryRegister(CladosCalculator pGUI, T pIn) {
		super();
		_GUI = pGUI;
		
		repNumber = pIn;
		setRepMode(null);

		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		add(createControlLayout(), BorderLayout.LINE_START);
		add(createDisplaysLayout(), BorderLayout.CENTER);
		resetDisplay();
		
		if (pIn == null) {
			setBackground(clrNullColor);
		} else if (pIn.getCardinal() == null)
			setBackground(clrNullColor);
		else
			setBackground(clrBackColor);
		
		if (_GUI != null)
			_GUI.appGeometryView.registerEntryRegister(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "clearIt" -> {
			if (repMode == CladosField.COMPLEXF | repMode == CladosField.COMPLEXD)
				setImgText("");
			setRealText("");
		}
		case "conjugate" -> {
			try {
				resetRepNumber();
				setCoefficientDisplay((T) repNumber.conjugate());
			} catch (NumberFormatException en) {
				ErrorDialog.show("Conjugation action halted.\nProbably a parsing error when reading FieldBar.\n"
						+ en.getMessage(), "Number Format Exception");
			}
		}
		case "mainInvolution" -> {
			try {
				resetRepNumber();
				setCoefficientDisplay((T) repNumber.invert());
			} catch (FieldException e) {
				ErrorDialog.show("Field Exception prevented inversion.\nCardinal=" + e.getSource().getCardinalString()
						+ "\nSource Message=" + e.getSourceMessage(), "Field Exception");
			} catch (NumberFormatException en) {
				ErrorDialog.show("Inversion action halted.\nProbably a parsing error when reading FieldBar.\n",
						"Number Format Exception");
			}
		}
		case "makeFloat" -> {
			if (_GUI != null)
				if (_GUI.appGeometryView.getNyadListSize() != 0) {
					ErrorDialog.show("Can't change DivField descendent while any nyads are displayed.",
							"DivField Change");
					break;
				}
			valDisplays.clear();
			remove(pnlDisplays);
			if (repMode == CladosField.REALD) {
				repMode = CladosField.REALF;
				if (repNumber == null) // TODO No. Should not be using iniProp cardinal. Should keep a local one
					repNumber = (T) RealF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			} else if (repMode == CladosField.COMPLEXD) {
				repMode = CladosField.COMPLEXF;
				if (repNumber == null) // TODO No. Should not be using iniProp cardinal. Should keep a local one
					repNumber = (T) ComplexF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			}
			btnMakeDouble.setEnabled(true);
			btnMakeFloat.setEnabled(false);
			repaint();
			if (_GUI != null)
				_GUI.appGeometryView.setRepMode(repMode);
			add(createDisplaysLayout(), BorderLayout.LINE_END);
			if (_GUI != null)
				_GUI.pack();
		}
		case "makeDouble" -> {
			if (_GUI != null)
				if (_GUI.appGeometryView.getNyadListSize() != 0) {
					ErrorDialog.show("Can't change DivField descendent while any nyads are displayed.",
							"DivField Change");
					break;
				}
			valDisplays.clear();
			remove(pnlDisplays);
			if (repMode == CladosField.REALF) {
				repMode = CladosField.REALD;
				if (repNumber == null) // TODO No. Should not be using iniProp cardinal. Should keep a local one
					repNumber = (T) RealD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			} else if (repMode == CladosField.COMPLEXF) {
				repMode = CladosField.COMPLEXD;
				if (repNumber == null) // TODO No. Should not be using iniProp cardinal. Should keep a local one
					repNumber = (T) ComplexD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			}
			btnMakeFloat.setEnabled(true);
			btnMakeDouble.setEnabled(false);
			repaint();
			if (_GUI != null)
				_GUI.appGeometryView.setRepMode(repMode);
			add(createDisplaysLayout(), BorderLayout.LINE_END);
			if (_GUI != null)
				_GUI.pack();
		}
		case "makeReal" -> {
			if (_GUI != null)
				if (_GUI.appGeometryView.getNyadListSize() != 0) {
					ErrorDialog.show("Can't change DivField descendent while any nyads are displayed.",
							"DivField Change");
					break;
				}
			valDisplays.clear();
			remove(pnlDisplays);
			if (repMode == CladosField.COMPLEXF) {
				repMode = CladosField.REALF;
				if (repNumber == null) // TODO No. Should not be using iniProp cardinal. Should keep a local one
					repNumber = (T) RealF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			} else if (repMode == CladosField.COMPLEXD) {
				repMode = CladosField.REALD;
				if (repNumber == null) // TODO No. Should not be using iniProp cardinal. Should keep a local one
					repNumber = (T) RealD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			}
			btnMakeComplex.setEnabled(true);
			btnMakeReal.setEnabled(false);
			repaint();
			if (_GUI != null)
				_GUI.appGeometryView.setRepMode(repMode);
			add(createDisplaysLayout(), BorderLayout.LINE_END);
			if (_GUI != null)
				_GUI.pack();
		}
		case "makeComplex" -> {
			if (_GUI != null)
				if (_GUI.appGeometryView.getNyadListSize() != 0) {
					ErrorDialog.show("Can't change DivField descendent while any nyads are displayed.",
							"DivField Change");
					break;
				}
			valDisplays.clear();
			remove(pnlDisplays);
			if (repMode == CladosField.REALF) {
				repMode = CladosField.COMPLEXF;
				if (repNumber == null)
					repNumber = (T) ComplexF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			} else if (repMode == CladosField.REALD) {
				repMode = CladosField.COMPLEXD;
				if (repNumber == null)
					repNumber = (T) ComplexD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.Cardinal"));
			}
			btnMakeReal.setEnabled(true);
			btnMakeComplex.setEnabled(false);
			repaint();
			if (_GUI != null)
				_GUI.appGeometryView.setRepMode(repMode);
			add(createDisplaysLayout(), BorderLayout.LINE_END);
			if (_GUI != null)
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
		resetDisplay();
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
		resetRepNumber();
	}
	/**
	 * This is a simple gettor that returns a string representation of the register's 
	 * imaginary value.
	 * <br>
	 * @return String that represents the imaginary part of the register's text
	 */
	public String getImgText() {
		return valDisplays.get(1).getText();
	}
	/**
	 * This is a simple gettor that returns a string representation of the register's 
	 * real value.
	 * <br>
	 * @return String that represents the real part of the register's text
	 */
	public String getRealText() {
		return valDisplays.get(0).getText();
	}

	/**
	 * Simple gettor method ment to hide the private data element so it can't be easily reset. 
	 * RepMode itself can't be finalized because this register is supposed to remain flexible, 
	 * so it gets hidden a bit.
	 * @return  CladosField enumeration representing the type of ProtoN the register currently 
	 * 			has in it.
	 */
	public CladosField getRepMode() {
		return repMode;
	}
	/**
	 * This is a simple settor that takes a string representation of the register's 
	 * imaginary value and sets it.
	 * <br>
	 * @param pIn String that represents the imaginary part of the register's text
	 */
	public void setImgText(String pIn) {
		valDisplays.get(1).setText(pIn);
	}
	/**
	 * This is a simple settor that takes a string representation of the register's 
	 * real value and sets it.
	 * <br>
	 * @param pIn String that represents the real part of the register's text
	 */
	public void setRealText(String pIn) {
		valDisplays.get(0).setText(pIn);
	}

	/**
	 * This 'set' function simply sets the displayed number (a double) into the
	 * first imaginary display text field.
	 * <br>
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
	 * <br>
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
	 * <br>
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
	 * <br>
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
	 * <br>
	 * I'm not sure why this method is protected and its siblings are public.
	 * <br>
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
	 * <br>
	 * I'm not sure why this method is protected and its siblings are public.
	 * <br>
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

		btnMakeReal = new JButton(new ImageIcon(this.getClass().getResource("/img/real.png")));
		btnMakeReal.setActionCommand("makeReal");
		btnMakeReal.setToolTipText("use real numbers");
		btnMakeReal.setPreferredSize(squareMedium);
		btnMakeReal.setBorder(BorderFactory.createEtchedBorder(0));
		btnMakeReal.addActionListener(this);
		pnlButtons.add(btnMakeReal, c1);
		c1.gridy++;

		btnMakeComplex = new JButton(new ImageIcon(this.getClass().getResource("/img/complex.png")));
		btnMakeComplex.setActionCommand("makeComplex");
		btnMakeComplex.setToolTipText("use complex numbers");
		btnMakeComplex.setPreferredSize(squareMedium);
		btnMakeComplex.setBorder(BorderFactory.createEtchedBorder(0));
		btnMakeComplex.addActionListener(this);
		pnlButtons.add(btnMakeComplex, c1);
		c1.gridy = 0;
		c1.gridx++;

		btnMakeFloat = new JButton(new ImageIcon(this.getClass().getResource("/img/float.png")));
		btnMakeFloat.setActionCommand("makeFloat");
		btnMakeFloat.setToolTipText("use floating precision");
		btnMakeFloat.setPreferredSize(squareMedium);
		btnMakeFloat.setBorder(BorderFactory.createEtchedBorder(0));
		btnMakeFloat.addActionListener(this);
		pnlButtons.add(btnMakeFloat, c1);
		c1.gridy++;

		btnMakeDouble = new JButton(new ImageIcon(this.getClass().getResource("/img/double.png")));
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

		JButton btnClear = new JButton(new ImageIcon(this.getClass().getResource("/img/clearIt.png")));
		btnClear.setActionCommand("clearIt");
		btnClear.setPreferredSize(squareLarge);
		btnClear.setBorder(BorderFactory.createEtchedBorder(0));
		btnClear.addActionListener(this);
		pnlButtons.add(btnClear, c1);
		c1.gridx += 2;

		JButton btnmainInvolution = new JButton(new ImageIcon(this.getClass().getResource("/img/maininvolution.png")));
		btnmainInvolution.setActionCommand("mainInvolution");
		btnmainInvolution.setPreferredSize(squareLarge);
		btnmainInvolution.setBorder(BorderFactory.createEtchedBorder(0));
		btnmainInvolution.addActionListener(this);
		pnlButtons.add(btnmainInvolution, c1);
		c1.gridx += 2;

		JButton btnConjugate = new JButton(new ImageIcon(this.getClass().getResource("/img/conjugate.png")));
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

		switch (repMode) {
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
		switch (repMode) {
		case REALF -> {
			valDisplays = new ArrayList<JTextField>(1);
			for (m = 0; m < 1; m++) {
				tSpot = new JTextField();
				tSpot.setColumns(EntryRegister._FLOATSIZE);
				tSpot.setFont(PLAINFONT);
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
				tSpot.setColumns(EntryRegister._DOUBLESIZE);
				tSpot.setFont(PLAINFONT);
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
				tSpot.setColumns(EntryRegister._FLOATSIZE);
				tSpot.setFont(PLAINFONT);
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
				tSpot.setColumns(EntryRegister._DOUBLESIZE);
				tSpot.setFont(PLAINFONT);
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
		repNumber = null;
	}

	/**
	 * This method does what it says. It makes the register non-editable.
	 */
	protected void makeNotWritable() {
		if (valDisplays != null)
			for (JTextField point : valDisplays)
				point.setEditable(false);
	}

	/**
	 * This method does what it says. It makes the register editable.
	 */
	protected void makeWritable() {
		if (valDisplays != null)
			for (JTextField point : valDisplays)
				point.setEditable(true);
	}

	/**
	 * This 'set' function simply adjusts the displayed complex number by accepting
	 * an input and then pushing those parts to values stored in the panel.
	 * @param <D> is the ProtoN child
	 * @param pIn CladosF number for use when resetting display elements
	 */
	@SuppressWarnings("unchecked")
	protected <D extends ProtoN & Field & Normalizable> void setCoefficientDisplay(D pIn) {
		if (pIn == null) {
			setBackground(clrNullColor);
			return;
		} else if (pIn.getCardinal() == null)
			setBackground(clrNullColor);
		else
			setBackground(clrBackColor);
		
		repNumber = (T) pIn;
		setRepMode(pIn);
		resetDisplay();
	}

	@SuppressWarnings("unchecked")
	private <D extends ProtoN & Field & Normalizable> void setRepMode(D pIn) {
		D testIt = (pIn != null ) ? pIn : (D) repNumber;

		if (testIt instanceof RealF) {
			repMode = CladosField.REALF;
		} else if (testIt instanceof RealD) {
			repMode = CladosField.REALD;
		} else if (testIt instanceof ComplexF) {
			repMode = CladosField.COMPLEXF;
		} else if (testIt instanceof ComplexD) {
			repMode = CladosField.COMPLEXD;
		} else
			return;
	}

	private void resetDisplay() {
		if (repNumber == null)
			return;
		switch (repMode) {
		case REALF -> {
			setRealText(Float.valueOf(((RealF) repNumber).getReal()).toString());
		}
		case REALD -> {
			setRealText(Double.valueOf(((RealD) repNumber).getReal()).toString());
		}
		case COMPLEXF -> {
			setRealText(Float.valueOf(((ComplexF) repNumber).getReal()).toString());
			setImgText(Float.valueOf(((ComplexF) repNumber).getImg()).toString());
		}
		case COMPLEXD -> {
			setRealText(Double.valueOf(((ComplexD) repNumber).getReal()).toString());
			setImgText(Double.valueOf(((ComplexD) repNumber).getImg()).toString());
		}
		}
	}

	private void resetRepNumber() {
		if (repNumber == null)
			return;
		try {
			switch (repMode) {
			case REALF -> {
				((RealF) repNumber).setReal(Float.parseFloat(getRealText()));
			}
			case REALD -> {
				((RealD) repNumber).setReal(Double.parseDouble(getRealText()));
			}
			case COMPLEXF -> {
				((ComplexF) repNumber).setReal(Float.parseFloat(getRealText()));
				((ComplexF) repNumber).setImg(Float.parseFloat(getImgText()));
			}
			case COMPLEXD -> {
				((ComplexD) repNumber).setReal(Double.parseDouble(getRealText()));
				((ComplexD) repNumber).setImg(Double.parseDouble(getImgText()));
			}
			}
		} catch (NumberFormatException en) {
			ErrorDialog.show("Couldn't parse FieldBar.\nNo action taken setting the Number it represents.",
					"Parse Issue");
		}
	}
}