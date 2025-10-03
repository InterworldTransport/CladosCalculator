/**
 * <h2>Copyright</h2> © 2021 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.FieldDisplay<br>
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
 * ---org.interworldtransport.cladosviewer.FieldDisplay<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewer;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import org.interworldtransport.cladosF.FBuilder;
import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosF.ComplexD;
import org.interworldtransport.cladosF.ComplexF;
import org.interworldtransport.cladosF.Field;
import org.interworldtransport.cladosF.Normalizable;
import org.interworldtransport.cladosF.RealD;
import org.interworldtransport.cladosF.RealF;
import org.interworldtransport.cladosF.ProtoN;

/**
 * The FieldArea class extends JTextArea slightly to keep the cladosF object
 * displayed in the text area close by. Referencing them and keeping them in
 * sync is made easier this way.
 * 
 * @param <D>
 * 
 * @version 1.0
 * @author Dr Alfred W Differ
 * 
 */
public class FieldDisplay<D extends ProtoN & Field & Normalizable> extends JTextArea implements FocusListener {
	private static final int _COMPLEXROWS = 2;
	private static final int _DOUBLESIZE = 16;
	private static final int _FLOATSIZE = 10;
	private static final int _FONTSIZE = 12;
	private static final String _IMAGINARY = "[I]";
	private static final String _REAL = "[R]";
	private static final int _REALROWS = 1;
	private static final Font ITALICFONT = new Font(Font.SERIF, Font.ITALIC, _FONTSIZE);
	private static final Font PLAINFONT = new Font(Font.SERIF, Font.PLAIN, _FONTSIZE);
	private static final long serialVersionUID = 7705233831398982801L;

	private MonadPanel<D> _parent;
	private CladosField repMode;
	/**
	 * The displayField is a copy of the cladosF magnitude that can be safely
	 * displayed and manipulated without harming the cladosG object using the
	 * original magnitude.
	 */
	protected D displayField;

	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the
	 * calculator
	 * 
	 * @param pField  D This is the CladosF number to be displayed in the text area
	 *                presented by this panel.
	 * @param pParent MonadPanel This is a reference to the owning MonadPanel
	 */
	public FieldDisplay(D pField, MonadPanel<D> pParent) {
		_parent = pParent;
		if (pField != null) {
			if (pField instanceof ComplexD) {
				this.setRows(_COMPLEXROWS);
				this.setColumns(_DOUBLESIZE);
				repMode = CladosField.COMPLEXD;
			} else if (pField instanceof ComplexF) {
				this.setRows(_COMPLEXROWS);
				this.setColumns(_FLOATSIZE);
				repMode = CladosField.COMPLEXF;
			} else if (pField instanceof RealD) {
				this.setRows(_REALROWS);
				this.setColumns(_DOUBLESIZE);
				repMode = CladosField.REALD;
			} else if (pField instanceof RealF) {
				this.setRows(_REALROWS);
				this.setColumns(_FLOATSIZE);
				repMode = CladosField.REALF;
			} else
				throw new IllegalArgumentException("Offered number isn't a known ProtoN child.");
			setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			setFont(PLAINFONT);
			displayField = pField;
			addFocusListener(this);
		} else {
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			setFont(ITALICFONT);
			ErrorDialog.show("Null in FieldDisplay.", "Function Parameter Issue");
			this.setText("null field");
		}

	}

	/**
	 * 
	 */
	public void displayContents() {
		switch (repMode) {
		case REALF -> setText(
				new StringBuilder().append(FieldDisplay._REAL).append(((RealF) displayField).getReal()).toString());
		case REALD -> setText(
				new StringBuilder().append(FieldDisplay._REAL).append(((RealD) displayField).getReal()).toString());
		case COMPLEXF -> setText(
				new StringBuilder().append(FieldDisplay._REAL).append(((ComplexF) displayField).getReal())
						.append("\n" + FieldDisplay._IMAGINARY).append(((ComplexF) displayField).getImg()).toString());
		case COMPLEXD -> setText(
				new StringBuilder().append(FieldDisplay._REAL).append(((ComplexD) displayField).getReal())
						.append("\n" + FieldDisplay._IMAGINARY).append(((ComplexD) displayField).getImg()).toString());
		default -> setText("null mode");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void focusGained(FocusEvent e) {
		if (_parent._editMode) // Only do this when parent MonadPanel is in edit mode.
		{
			displayField = FBuilder.copyOf((D) _parent._GUI.appFieldBar.repNumber);
			displayContents();
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void saveContents() {
		try {
			StringBuilder strB = new StringBuilder(getText());
			int tBufferLength = strB.length();
			if (tBufferLength == 0)
				return; // Nothing to save, so surrender.
			int indexOfR = strB.indexOf(_REAL) + _REAL.length();
			int indexOfI = strB.indexOf(_IMAGINARY) + _IMAGINARY.length();

			switch (repMode) {
			case REALF -> {
				displayField = (D) CladosField.REALF.createZERO(displayField);
				((RealF) displayField).setReal(Float.parseFloat(strB.substring(indexOfR, tBufferLength)));
			}
			case REALD -> {
				displayField = (D) CladosField.REALD.createZERO(displayField);
				((RealD) displayField).setReal(Double.parseDouble(strB.substring(indexOfR, tBufferLength)));
			}
			case COMPLEXF -> {
				displayField = (D) CladosField.COMPLEXF.createZERO(displayField);
				((ComplexF) displayField)
						.setReal(Float.parseFloat(strB.substring(indexOfR, indexOfI - _IMAGINARY.length() - 1)));
				((ComplexF) displayField).setImg(Float.parseFloat(strB.substring(indexOfI, tBufferLength)));
			}
			case COMPLEXD -> {
				displayField = (D) CladosField.COMPLEXD.createZERO(displayField);
				((ComplexD) displayField)
						.setReal(Double.parseDouble(strB.substring(indexOfR, indexOfI - _IMAGINARY.length() - 1)));
				((ComplexD) displayField).setImg(Double.parseDouble(strB.substring(indexOfI, tBufferLength)));
			}
			}
			setFont(PLAINFONT);
			displayContents();
		} catch (NumberFormatException e) {
			setFont(ITALICFONT);
			ErrorDialog.show("FieldDisplay must contain parse-able text.\nLook for bad lengths or number formats.",
					"Parse Issue");
		}

	}

	/**
	 * When a new cladosF number is to be displayed, it is passed in through this
	 * method.
	 * 
	 * @param pField D This is the ComplexD to be displayed in the text area
	 *               presented by this panel.
	 */
	@SuppressWarnings("unchecked")
	public <T extends ProtoN & Field & Normalizable> void updateField(T pField) {
		if (pField != null) {
			displayField = (D) FBuilder.copyOf(pField);
			displayContents();
		} else {
			setFont(ITALICFONT);
			this.setText("null display field");
		}
	}
}
