/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.FieldAreaRealF<br>
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
 * ---com.interworldtransport.cladosviewer.FieldAreaRealF<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer;

import java.awt.Font;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;

import com.interworldtransport.cladosF.*;

import com.interworldtransport.cladosviewerExceptions.*;

/** 
 * The FieldArea class extends JTextArea slightly to keep the cladosF object
 * displayed in the text area close by. Referencing them and keeping them in sync
 * is made easier this way.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */
public class FieldDisplayArea extends JTextArea 
{
	public static final int FLOATSIZE=11;
	public static final int DOUBLESIZE=15;
	
	//private		String[]	_lines = new String[1];
	
	private		String		_repMode;
	/**
	 * The displayField is a copy of the cladosF magnitude that can be safely displayed
	 * and manipulated without harming the cladosG object using the original magnitude.
	 */
	protected	RealF		displayFieldRF;
	/**
	 * The displayField is a copy of the cladosF magnitude that can be safely displayed
	 * and manipulated without harming the cladosG object using the original magnitude.
	 */
	protected	RealD		displayFieldRD;
	/**
	 * The displayField is a copy of the cladosF magnitude that can be safely displayed
	 * and manipulated without harming the cladosG object using the original magnitude.
	 */
	protected	ComplexF	displayFieldCF;
	/**
	 * The displayField is a copy of the cladosF magnitude that can be safely displayed
	 * and manipulated without harming the cladosG object using the original magnitude.
	 */
	protected	ComplexD	displayFieldCD;

	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField
	 *  RealF
	 * This is the RealF to be displayed in the text area presented by this panel.
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 */
	public FieldDisplayArea(RealF pField) throws UtilitiesException
	{
		super(1, FieldDisplayArea.FLOATSIZE);
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid DivField on construction.");
	    
	    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	    //setColumns(8);
		setFont(new Font(Font.SERIF, Font.PLAIN, 10));
		displayFieldRF = pField;
		//setRows(1);
		_repMode = DivField.REALF;
	}
	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField
	 *  RealD
	 * This is the RealD to be displayed in the text area presented by this panel.
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 */
	public FieldDisplayArea(RealD pField) throws UtilitiesException
	{
		super(1, FieldDisplayArea.DOUBLESIZE);
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid DivField on construction.");
	    
	    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setFont(new Font(Font.SERIF, Font.PLAIN, 10));
		displayFieldRD = pField;
		_repMode = DivField.REALD;
	}
	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField
	 *  ComplexF
	 * This is the ComplexF to be displayed in the text area presented by this panel.
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 */
	public FieldDisplayArea(ComplexF pField) throws UtilitiesException
	{
		super(2, FieldDisplayArea.FLOATSIZE);
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid DivField on construction.");
	    

	    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setFont(new Font(Font.SERIF, Font.PLAIN, 10));
		displayFieldCF = pField;
		_repMode = DivField.COMPLEXF;
	}
	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField
	 *  ComplexD
	 * This is the ComplexD to be displayed in the text area presented by this panel.
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 */
	public FieldDisplayArea(ComplexD pField) throws UtilitiesException
	{
		super(2, FieldDisplayArea.DOUBLESIZE);
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid DivField on construction.");
	    

	    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setFont(new Font(Font.SERIF, Font.PLAIN, 10));
		displayFieldCD = pField;
		_repMode = DivField.COMPLEXD;
	}
	
	public void displayContents() throws BadLocationException
	{		
		StringBuffer str = new StringBuffer().append("[R]");
		switch (_repMode)
		{
			case DivField.REALF:	str.append(displayFieldRF.getReal());
									setText(str.toString());
									break;
			case DivField.REALD:	str.append(displayFieldRD.getReal());
									setText(str.toString());
									break;
			case DivField.COMPLEXF:	str.append(displayFieldCF.getReal()).append("\n[I]").append(displayFieldCF.getImg());
									setText(str.toString());
									break;
			case DivField.COMPLEXD:	str.append(displayFieldCD.getReal()).append("\n[I]").append(displayFieldCD.getImg());
									setText(str.toString());
		}
	}
	
	public void saveContents() throws NumberFormatException, UtilitiesException
	{
		try 
		{
			StringBuilder strB = new StringBuilder(getText());
			int tBufferLength = strB.length();
			if (tBufferLength == 0 ) return; // Nothing to save, so surrender.
			int indexOfR = strB.indexOf("[R]")+3;
			int indexOfI = strB.indexOf("[I]")+3;
			
			switch (_repMode)
			{
				case DivField.REALF:	float tSpotRF = Float.parseFloat(strB.substring(indexOfR, tBufferLength));
										displayFieldRF=new RealF(displayFieldRF.getFieldType(), tSpotRF);
										break;
				case DivField.REALD:	double tSpotRD = Double.parseDouble(strB.substring(indexOfR, tBufferLength));
										displayFieldRD=new RealD(displayFieldRD.getFieldType(), tSpotRD);
										break;
				case DivField.COMPLEXF:	float tSpotCF1 = Float.parseFloat(strB.substring(indexOfR, indexOfI-4));
										float tSpotCF2 = Float.parseFloat(strB.substring(indexOfI, tBufferLength));
										displayFieldCF=new ComplexF(displayFieldCF.getFieldType(), tSpotCF1, tSpotCF2);
										break;
				case DivField.COMPLEXD:	double tSpotCD1 = Double.parseDouble(strB.substring(indexOfR, indexOfI-4));
										double tSpotCD2 = Double.parseDouble(strB.substring(indexOfI, tBufferLength));
										displayFieldCD=new ComplexD(displayFieldCD.getFieldType(), tSpotCD1, tSpotCD2);
			}
			setFont(new Font(Font.SERIF, Font.PLAIN, 10));
			displayContents();
		} 
		catch (NumberFormatException | BadLocationException e) 
		{
			setFont(new Font(Font.SERIF, Font.ITALIC, 10));
			throw new UtilitiesException("FieldArea must contain parse-able text. Look for bad lengths or number formats.");
		}
		
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
}
