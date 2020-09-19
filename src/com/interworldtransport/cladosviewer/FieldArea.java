/**
 * <h2>Copyright</h2> © 2019 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.FieldArea<br>
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
 * ---com.interworldtransport.cladosviewer.FieldArea<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer;

import java.awt.Font;

import javax.swing.*;
import javax.swing.border.BevelBorder;

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
public class FieldArea extends JTextArea 
{
	/*
	 * The repField is a cladosF magnitude from inside something like a cladosG monad.
	 * That means keeping this reference here breaks encapsulation which makes this 
	 * semi-dangerous. That's why the reference is kept private.
	 */
	private				DivField						repField;
	/**
	 * The displayField is a copy of the cladosF magnitude that can be safely displayed
	 * and manipulated without harming the cladosG object using the original magnitude.
	 */
	protected			DivField						displayField;

	
	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @throws UtilitiesException when no cladosF field is passed to the constructor
	 */
	public FieldArea(DivField pField) throws UtilitiesException
	{
		super();
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid DivField on construction.");
	    
	    repField=pField;
	    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	    this.setColumns(8);
		this.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
	    resetDisplay();
	}
	/*
	 * This is the private method for determining how many rows the text area
	 * should have and for making a safe copy of the represented cladosF magnitude.
	 */
	private void resetDisplay()
	{
		if (repField instanceof RealF)
	    {
	    	displayField = RealF.copyOf((RealF) repField);
	    	this.setRows(1);
	    }
	    if (repField instanceof RealD)
	    {
	    	displayField = RealD.copyOf((RealD) repField);
	    	this.setRows(1);
	    }
	    if (repField instanceof ComplexF)
	    {
	    	displayField = ComplexF.copyOf((ComplexF) repField);
	    	this.setRows(2);
	    }
	    if (repField instanceof ComplexD)
	    {
	    	displayField = ComplexD.copyOf((ComplexD) repField);
	    	this.setRows(2);
	    }
	}
	
	public void displayContents()
	{
		if (repField instanceof RealF)
			this.setText(new StringBuffer().append(((RealF) displayField).getReal()).toString());
		
		if (repField instanceof RealD)
			this.setText(new StringBuffer().append(((RealD) displayField).getReal()).toString());
		
		if (repField instanceof ComplexF)
		{
			StringBuffer buildString = new StringBuffer().append(((ComplexF) displayField).getReal());
			buildString.append("\n");
			buildString.append(((ComplexF) displayField).getImg());
			buildString.append(" i\n");
			this.setText(buildString.toString());
		}
		if (repField instanceof ComplexD)
		{
			StringBuffer buildString = new StringBuffer().append(((ComplexD) displayField).getReal());
			buildString.append("\n");
			buildString.append(((ComplexD) displayField).getImg());
			buildString.append(" i\n");
			this.setText(buildString.toString());
		}
	}
	
	public void updateField(DivField pField) throws UtilitiesException
	{
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid DivField on update.");
		repField = pField;
		resetDisplay();
	}
	    
}
