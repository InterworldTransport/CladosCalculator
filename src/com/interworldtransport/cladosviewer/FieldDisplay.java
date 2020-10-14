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
import javax.swing.text.BadLocationException;

import com.interworldtransport.cladosF.CladosField;
import com.interworldtransport.cladosF.ComplexD;
import com.interworldtransport.cladosF.ComplexF;
import com.interworldtransport.cladosF.RealD;
import com.interworldtransport.cladosF.RealF;
import com.interworldtransport.cladosviewerExceptions.*;

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
	private static final long 	serialVersionUID = 9025876827548262896L;
	private static final int 	_FONTSIZE = 	12;
	private static final int 	_DOUBLESIZE =	16;
	private static final int 	_FLOATSIZE =	10;
	private static final String	_IMAGINARY =	"[I]";
	private static final String _REAL =			"[R]";
	
	private			MonadPanel	_parent;
	private			CladosField	_repMode;
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
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 */
	public FieldDisplay(ComplexD pField, MonadPanel pParent) throws UtilitiesException
	{
		super(2, FieldDisplay._DOUBLESIZE);
		_parent=pParent;
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid DivField on construction.");
	    

	    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setFont(new Font(Font.SERIF, Font.PLAIN, _FONTSIZE));
		displayFieldCD = pField;
		_repMode = CladosField.COMPLEXD;
		addFocusListener(this);
	}
	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField	ComplexF
	 * This is the ComplexF to be displayed in the text area presented by this panel.
	 * @param pParent	MonadPanel
	 * This is a reference to the owning MonadPanel
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 */
	public FieldDisplay(ComplexF pField, MonadPanel pParent) throws UtilitiesException
	{
		super(2, FieldDisplay._FLOATSIZE);
		_parent = pParent;
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid DivField on construction.");
	    

	    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setFont(new Font(Font.SERIF, Font.PLAIN, _FONTSIZE));
		displayFieldCF = pField;
		_repMode = CladosField.COMPLEXF;
		addFocusListener(this);
	}
	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField	RealD
	 * This is the RealD to be displayed in the text area presented by this panel.
	 * @param pParent	MonadPanel
	 * This is a reference to the owning MonadPanel
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 */
	public FieldDisplay(RealD pField, MonadPanel pParent) throws UtilitiesException
	{
		super(1, FieldDisplay._DOUBLESIZE);
		_parent = pParent;
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid DivField on construction.");
	    
	    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setFont(new Font(Font.SERIF, Font.PLAIN, _FONTSIZE));
		displayFieldRD = pField;
		_repMode = CladosField.REALD;
		addFocusListener(this);
	}
	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField	RealF
	 * This is the RealF to be displayed in the text area presented by this panel.
	 * @param pParent	MonadPanel
	 * This is a reference to the owning MonadPanel
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 */
	public FieldDisplay(RealF pField, MonadPanel pParent) throws UtilitiesException
	{
		super(1, FieldDisplay._FLOATSIZE);
		_parent = pParent;
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid DivField on construction.");
	    
	    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setFont(new Font(Font.SERIF, Font.PLAIN, _FONTSIZE));
		displayFieldRF = pField;
		_repMode = CladosField.REALF;
		addFocusListener(this);
	}
	
	public void displayContents() //throws BadLocationException
	{		
		StringBuffer str = new StringBuffer().append(FieldDisplay._REAL);
		switch (_repMode)
		{
			case REALF:	str.append(displayFieldRF.getReal());
						setText(str.toString());
						break;
			case REALD:	str.append(displayFieldRD.getReal());
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
		//_parent._GUI._StatusBar.setStatusMsg("Parent Edit mode is "+_parent._editMode+".\n");
		if (_parent._editMode) // Only do this when parent MonadPanel is in edit mode.
		{
			switch (_parent.getRepMode())
			{
				case REALF: displayFieldRF = RealF.copyOf(_parent._GUI._FieldBar._repRealF);
							displayContents();
							break;
				case REALD: displayFieldRD = RealD.copyOf(_parent._GUI._FieldBar._repRealD);
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
	public void saveContents() throws UtilitiesException
	{
		try 
		{
			int tR=FieldDisplay._REAL.length();
			int tI=FieldDisplay._IMAGINARY.length();
			
			StringBuilder strB = new StringBuilder(getText());
			int tBufferLength = strB.length();
			if (tBufferLength == 0 ) return; // Nothing to save, so surrender.
			int indexOfR = strB.indexOf(FieldDisplay._REAL)+tR;
			int indexOfI = strB.indexOf(FieldDisplay._IMAGINARY)+tI;
			
			switch (_repMode)
			{
				case REALF:	float tSpotRF = Float.parseFloat(strB.substring(indexOfR, tBufferLength));
							displayFieldRF=new RealF(displayFieldRF.getCardinal(), tSpotRF);
							break;
				case REALD:	double tSpotRD = Double.parseDouble(strB.substring(indexOfR, tBufferLength));
							displayFieldRD=new RealD(displayFieldRD.getCardinal(), tSpotRD);
							break;
				case COMPLEXF:	float tSpotCF1 = Float.parseFloat(strB.substring(indexOfR, indexOfI-tI-1));
								float tSpotCF2 = Float.parseFloat(strB.substring(indexOfI, tBufferLength));
								displayFieldCF=new ComplexF(displayFieldCF.getCardinal(), tSpotCF1, tSpotCF2);
								break;
				case COMPLEXD:	double tSpotCD1 = Double.parseDouble(strB.substring(indexOfR, indexOfI-tI-1));
								double tSpotCD2 = Double.parseDouble(strB.substring(indexOfI, tBufferLength));
								displayFieldCD=new ComplexD(displayFieldCD.getCardinal(), tSpotCD1, tSpotCD2);
			}
			setFont(new Font(Font.SERIF, Font.PLAIN, 10));
			displayContents();
		} 
		catch (NumberFormatException e) 
		{
			setFont(new Font(Font.SERIF, Font.ITALIC, 10));
			throw new UtilitiesException("FieldArea must contain parse-able text. Look for bad lengths or number formats.");
		}
		
	}
	/**
	 * When a new cladosF number is to be displayed, it is passed in through this method.
	 * @param pField
	 *  ComplexD
	 * This is the ComplexD to be displayed in the text area presented by this panel.
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 * @throws BadLocationException 
	 * This exception will get thrown when there is difficulty parsing the text on display
	 * in this field. The format expected is [R]Double\n[I]Double
	 */
	public void updateField(ComplexD pField) throws UtilitiesException, BadLocationException
	{
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid ComplexD on update.");

		displayFieldCD = ComplexD.copyOf(pField);
		displayContents();
	}
	/**
	 * When a new cladosF number is to be displayed, it is passed in through this method.
	 * @param pField
	 *  ComplexF
	 * This is the ComplexF to be displayed in the text area presented by this panel.
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 * @throws BadLocationException 
	 * This exception will get thrown when there is difficulty parsing the text on display
	 * in this field. The format expected is [R]Float\n[I]Float
	 */
	public void updateField(ComplexF pField) throws UtilitiesException, BadLocationException
	{
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid ComplexF on update.");

		displayFieldCF = ComplexF.copyOf(pField);
		displayContents();
	}
	
	/**
	 * When a new cladosF number is to be displayed, it is passed in through this method.
	 * @param pField
	 *  RealD
	 * This is the RealD to be displayed in the text area presented by this panel.
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 * @throws BadLocationException 
	 * This exception will get thrown when there is difficulty parsing the text on display
	 * in this field. The format expected is [R]Double
	 */
	public void updateField(RealD pField) throws UtilitiesException, BadLocationException
	{
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid RealD on update.");

		displayFieldRD = RealD.copyOf(pField);
		displayContents();
	}
	/**
	 * When a new cladosF number is to be displayed, it is passed in through this method.
	 * @param pField
	 *  RealF
	 * This is the RealF to be displayed in the text area presented by this panel.
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 * @throws BadLocationException 
	 * This exception will get thrown when there is difficulty parsing the text on display
	 * in this field. The format expected is [R]Float
	 */
	public void updateField(RealF pField) throws UtilitiesException, BadLocationException
	{
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid RealF on update.");

		displayFieldRF = RealF.copyOf(pField);
		displayContents();
	}
}
