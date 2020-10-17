/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.FieldDisplay<br>
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
 * ---com.interworldtransport.cladosviewer.FieldDisplay<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import com.interworldtransport.cladosF.CladosField;
import com.interworldtransport.cladosF.ComplexD;
import com.interworldtransport.cladosF.ComplexF;
import com.interworldtransport.cladosF.RealD;
import com.interworldtransport.cladosF.RealF;

/** 
 * The FieldArea class extends JTextArea slightly to keep the cladosF object
 * displayed in the text area close by. Referencing them and keeping them in sync
 * is made easier this way.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class FieldDisplay extends JTextArea implements FocusListener
{
	private static final long serialVersionUID = 7705233831398982801L;
	private static final int 	_FONTSIZE 			= 12;
	private static final int 	_DOUBLESIZE 		= 16;
	private static final int 	_FLOATSIZE 			= 10;
	private static final Font	_PLAINFONT			= new Font(Font.SERIF, Font.PLAIN, _FONTSIZE);
	private static final Font	_ITALICFONT			= new Font(Font.SERIF, Font.ITALIC, _FONTSIZE);
	private static final String	_IMAGINARY 			= "[I]";
	private static final String _REAL 				= "[R]";
	
	private		MonadPanel	_parent;
	private		CladosField	_repMode;
	/**
	 * The displayField is a copy of the cladosF magnitude that can be safely displayed
	 * and manipulated without harming the cladosG object using the original magnitude.
	 */
	protected	ComplexD	displayFieldCD;
	/**
	 * The displayField is a copy of the cladosF magnitude that can be safely displayed
	 * and manipulated without harming the cladosG object using the original magnitude.
	 */
	protected	ComplexF	displayFieldCF;
	/**
	 * The displayField is a copy of the cladosF magnitude that can be safely displayed
	 * and manipulated without harming the cladosG object using the original magnitude.
	 */
	protected	RealD		displayFieldRD;
	/**
	 * The displayField is a copy of the cladosF magnitude that can be safely displayed
	 * and manipulated without harming the cladosG object using the original magnitude.
	 */
	protected	RealF		displayFieldRF;

	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField	ComplexD
	 * This is the ComplexD to be displayed in the text area presented by this panel.
	 * @param pParent	MonadPanel
	 * This is a reference to the owning MonadPanel
	 */
	public FieldDisplay(ComplexD pField, MonadPanel pParent) 
	{
		super(2, FieldDisplay._DOUBLESIZE); //two rows, extra wide
		_parent=pParent;
		if (pField != null)
		{
			setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			setFont(_PLAINFONT);
			displayFieldCD = pField;
			addFocusListener(this);
		}
		else	
		{
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			setFont(_ITALICFONT);
			_parent._GUI._StatusBar.setStatusMsg("null C|D in FieldDisplay\n");
			this.setText("null field");
		}
		_repMode = CladosField.COMPLEXD;
	}
	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField	ComplexF
	 * This is the ComplexF to be displayed in the text area presented by this panel.
	 * @param pParent	MonadPanel
	 * This is a reference to the owning MonadPanel
	 */
	public FieldDisplay(ComplexF pField, MonadPanel pParent) 
	{
		super(2, FieldDisplay._FLOATSIZE);//two rows, not so wide
		_parent = pParent;
		if (pField != null)
		{
		    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			setFont(_PLAINFONT);
			displayFieldCF = pField;
			addFocusListener(this);
		}
		else	
		{
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			setFont(_ITALICFONT);
			_parent._GUI._StatusBar.setStatusMsg("null C|F in FieldDisplay\n");
			this.setText("null field");
		}
		_repMode = CladosField.COMPLEXF;
	}
	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField	RealD
	 * This is the RealD to be displayed in the text area presented by this panel.
	 * @param pParent	MonadPanel
	 * This is a reference to the owning MonadPanel
	 */
	public FieldDisplay(RealD pField, MonadPanel pParent) 
	{
		super(1, FieldDisplay._DOUBLESIZE);//one row, extra wide
		_parent = pParent;
		if (pField != null)
		{
			setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			setFont(_PLAINFONT);
			displayFieldRD = pField;
			addFocusListener(this);
		}
		else	
		{
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			setFont(_ITALICFONT);
			_parent._GUI._StatusBar.setStatusMsg("null R|D in FieldDisplay\n");
			this.setText("null field");
		}
		_repMode = CladosField.REALD;
	}
	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField	RealF
	 * This is the RealF to be displayed in the text area presented by this panel.
	 * @param pParent	MonadPanel
	 * This is a reference to the owning MonadPanel
	 */
	public FieldDisplay(RealF pField, MonadPanel pParent)
	{
		super(1, FieldDisplay._FLOATSIZE);//one row, not so wide wide
		_parent = pParent;
		if (pField != null)
		{
			setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			setFont(_PLAINFONT);
			displayFieldRF = pField;
			addFocusListener(this);
		}
		else	
		{
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			setFont(_ITALICFONT);
			_parent._GUI._StatusBar.setStatusMsg("null R|F in FieldDisplay\n");
			this.setText("null field");
		}	    
		_repMode = CladosField.REALF;
	}
	
	public void displayContents() 
	{		
		StringBuffer str = new StringBuffer().append(FieldDisplay._REAL);
		switch (_repMode)
		{
			case REALF:		str.append(displayFieldRF.getReal());
							setText(str.toString());
							break;
			case REALD:		str.append(displayFieldRD.getReal());
							setText(str.toString());
							break;
			case COMPLEXF:	str.append(displayFieldCF.getReal()).append("\n"+FieldDisplay._IMAGINARY).append(displayFieldCF.getImg());
							setText(str.toString());
							break;
			case COMPLEXD:	str.append(displayFieldCD.getReal()).append("\n"+FieldDisplay._IMAGINARY).append(displayFieldCD.getImg());
							setText(str.toString());
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) 
	{
		if (_parent._editMode) // Only do this when parent MonadPanel is in edit mode.
		{
			switch (_parent.getRepMode())
			{
				case REALF: 	displayFieldRF = RealF.copyOf(_parent._GUI._FieldBar._repRealF);
								displayContents();
								break;
				case REALD: 	displayFieldRD = RealD.copyOf(_parent._GUI._FieldBar._repRealD);
								displayContents();
								break;
				case COMPLEXF:	displayFieldCF = ComplexF.copyOf(_parent._GUI._FieldBar._repComplexF);
								displayContents();
								break;
				case COMPLEXD:	displayFieldCD = ComplexD.copyOf(_parent._GUI._FieldBar._repComplexD);
								displayContents();
			}
		}		
	}
	
	@Override
	public void focusLost(FocusEvent e) 
	{
		;		
	}
	public void saveContents() //throws UtilitiesException
	{
		try 
		{
			StringBuilder strB = new StringBuilder(getText());
			int tBufferLength = strB.length();
			if (tBufferLength == 0 ) return; // Nothing to save, so surrender.
			int indexOfR = strB.indexOf(_REAL)+_REAL.length();
			int indexOfI = strB.indexOf(_IMAGINARY)+_IMAGINARY.length();
			
			switch (_repMode)
			{
				case REALF:		displayFieldRF = (RealF) CladosField.REALF.createZERO(displayFieldRF.getCardinal());
								displayFieldRF.setReal(Float.parseFloat(strB.substring(indexOfR, tBufferLength)));
								break;
				case REALD:		displayFieldRD = (RealD) CladosField.REALD.createZERO(displayFieldRD.getCardinal());
								displayFieldRD.setReal(Double.parseDouble(strB.substring(indexOfR, tBufferLength)));
								break;
				case COMPLEXF:	displayFieldCF = (ComplexF) CladosField.COMPLEXF.createZERO(displayFieldCF.getCardinal());
								displayFieldCF.setReal(Float.parseFloat(strB.substring(indexOfR, indexOfI-_IMAGINARY.length()-1)));
								displayFieldCF.setImg(Float.parseFloat(strB.substring(indexOfI, tBufferLength)));
								break;
				case COMPLEXD:	displayFieldCD = (ComplexD) CladosField.COMPLEXD.createZERO(displayFieldCD.getCardinal());
								displayFieldCD.setReal(Double.parseDouble(strB.substring(indexOfR, indexOfI-_IMAGINARY.length()-1)));
								displayFieldCD.setImg(Double.parseDouble(strB.substring(indexOfI, tBufferLength)));
			}
			setFont(_PLAINFONT);
			displayContents();
		} 
		catch (NumberFormatException e) 
		{
			setFont(_ITALICFONT);
			_parent._GUI._StatusBar.setStatusMsg("FieldArea must contain parse-able text. Look for bad lengths or number formats.\n");
		}
		
	}
	/**
	 * When a new cladosF number is to be displayed, it is passed in through this method.
	 * @param pField
	 *  ComplexD
	 * This is the ComplexD to be displayed in the text area presented by this panel.
	 */
	public void updateField(ComplexD pField)
	{
		if (pField != null)
		{
			displayFieldCD = ComplexD.copyOf(pField);
			displayContents();
		}
		else	
		{
			setFont(_ITALICFONT);
			this.setText("null C|D field");
		}
	}
	/**
	 * When a new cladosF number is to be displayed, it is passed in through this method.
	 * @param pField
	 *  ComplexF
	 * This is the ComplexF to be displayed in the text area presented by this panel.
	 */
	public void updateField(ComplexF pField) 
	{
		if (pField != null)
		{
			displayFieldCF = ComplexF.copyOf(pField);
			displayContents();
		}
		else	
		{
			setFont(_ITALICFONT);
			this.setText("null C|F field");
		}
	}
	
	/**
	 * When a new cladosF number is to be displayed, it is passed in through this method.
	 * @param pField
	 *  RealD
	 * This is the RealD to be displayed in the text area presented by this panel.
	 */
	public void updateField(RealD pField) 
	{
		if (pField != null)
		{
			displayFieldRD = RealD.copyOf(pField);
			displayContents();
		}
		else	
		{
			setFont(_ITALICFONT);
			this.setText("null R|D field");
		}
	}
	/**
	 * When a new cladosF number is to be displayed, it is passed in through this method.
	 * @param pField
	 *  RealF
	 * This is the RealF to be displayed in the text area presented by this panel.
	 */
	public void updateField(RealF pField) 
	{
		if (pField != null)
		{
			displayFieldRF = RealF.copyOf(pField);
			displayContents();
		}
		else	
		{
			setFont(_ITALICFONT);
			this.setText("null R|F field");
		}
	}
}
