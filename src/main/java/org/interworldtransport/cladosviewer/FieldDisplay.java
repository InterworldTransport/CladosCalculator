/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.FieldDisplay<br>
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
 * The FieldDisplay class extends JTextArea slightly to keep the cladosF object
 * displayed in the text area close by. Referencing them and keeping them in
 * sync is made easier this way.
 * <br>
 * @param <D> A ProtoN child to be displayed in the JTextArea.
 * <br>
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
	/**
	 * Field displays belong to a monad panel. This reference points back to the parent panel.
	 */
	private MonadPanel<D> _parent;
	/**
	 * A CladosField enumeration representing which ProtoN child is being used in monads.
	 */
	private CladosField repMode;
	/**
	 * The fieldCopy is a copy of the cladosF magnitude that can be safely
	 * displayed and manipulated without harming the cladosG object using the
	 * original magnitude.
	 */
	protected D fieldCopy;

	/**
	 * The EntryRegister class is intended to be the contain a cladosF Field in much
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
			fieldCopy = pField;
			addFocusListener(this);
		} else {
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			setFont(ITALICFONT);
			ErrorDialog.show("Null in FieldDisplay.", "Function Parameter Issue");
			this.setText("null field");
		}

	}

	/**
	 * The number to be displayed is peeled open, text extracted, and then set as the text in this JTextArea.
	 * Reals of all precision are presented as [R]###. Complex numbers are similar except on two lines with an 
	 * additional [I]###.
	 */
	public void displayContents() {
		switch (repMode) {
		case REALF -> setText(
				new StringBuilder().append(FieldDisplay._REAL).append(((RealF) fieldCopy).getReal()).toString());
		case REALD -> setText(
				new StringBuilder().append(FieldDisplay._REAL).append(((RealD) fieldCopy).getReal()).toString());
		case COMPLEXF -> setText(
				new StringBuilder().append(FieldDisplay._REAL).append(((ComplexF) fieldCopy).getReal())
						.append("\n" + FieldDisplay._IMAGINARY).append(((ComplexF) fieldCopy).getImg()).toString());
		case COMPLEXD -> setText(
				new StringBuilder().append(FieldDisplay._REAL).append(((ComplexD) fieldCopy).getReal())
						.append("\n" + FieldDisplay._IMAGINARY).append(((ComplexD) fieldCopy).getImg()).toString());
		default -> setText("null mode");
		}
	}

	/**
	 * When focus is gained AND the Monad Panel is in edit mode, the number represented in the field bar
	 * is copied and used to over-write the number this display panel presents. After that the text area
	 * is updated to reflect this.
	 * <br>
	 * The end result is the number in the field bar gets copied to this newly in-focus display panel.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void focusGained(FocusEvent e) {
		if (_parent._editMode) // Only do this when parent MonadPanel is in edit mode.
		{
			fieldCopy = FBuilder.copyOf((D) _parent._GUI.appFieldBar.repNumber);
			displayContents();
		}
	}

	/**
	 * Nothing happens when focus is lost. Not yet. Maybe some day it will change.
	 * Until then this empty method body ensures nothing DOES happen.
	 */
	@Override
	public void focusLost(FocusEvent e) {
		;
	}

	/**
	 * Calls to this method reverse the flow of data setting the represented number to the displayed one.
	 * The represented number is zero'd out, mode gets checked, text gets parsed, and new 'Zero' is updated.
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
				fieldCopy = (D) CladosField.REALF.createZERO(fieldCopy);
				((RealF) fieldCopy).setReal(Float.parseFloat(strB.substring(indexOfR, tBufferLength)));
			}
			case REALD -> {
				fieldCopy = (D) CladosField.REALD.createZERO(fieldCopy);
				((RealD) fieldCopy).setReal(Double.parseDouble(strB.substring(indexOfR, tBufferLength)));
			}
			case COMPLEXF -> {
				fieldCopy = (D) CladosField.COMPLEXF.createZERO(fieldCopy);
				((ComplexF) fieldCopy)
						.setReal(Float.parseFloat(strB.substring(indexOfR, indexOfI - _IMAGINARY.length() - 1)));
				((ComplexF) fieldCopy).setImg(Float.parseFloat(strB.substring(indexOfI, tBufferLength)));
			}
			case COMPLEXD -> {
				fieldCopy = (D) CladosField.COMPLEXD.createZERO(fieldCopy);
				((ComplexD) fieldCopy)
						.setReal(Double.parseDouble(strB.substring(indexOfR, indexOfI - _IMAGINARY.length() - 1)));
				((ComplexD) fieldCopy).setImg(Double.parseDouble(strB.substring(indexOfI, tBufferLength)));
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
	 * When a new cladosF number is to be displayed, it is passed to this method, 
	 * copied, inserted into the internal element, and then displayed.
	 * <br>
	 * @param <T> 	is the ProtoN child type of pField.
	 * @param pField This is the ProtoN child to be displayed in the text area
	 *               presented by this panel.
	 */
	@SuppressWarnings("unchecked")
	public <T extends ProtoN & Field & Normalizable> void updateField(T pField) {
		if (pField != null) {
			fieldCopy = (D) FBuilder.copyOf(pField);
			displayContents();
		} else {
			setFont(ITALICFONT);
			this.setText("null display field");
		}
	}
}
