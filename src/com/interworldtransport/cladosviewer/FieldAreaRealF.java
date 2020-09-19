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
public class FieldAreaRealF extends JTextArea 
{
	/**
	 * The displayField is a copy of the cladosF magnitude that can be safely displayed
	 * and manipulated without harming the cladosG object using the original magnitude.
	 */
	protected			RealF					displayField;

	/**
	 * The FieldPanel class is intended to be the contain a cladosF Field in much
	 * the same way as Monad and Nyad panels represent their contents to the calculator
	 * @param pField
	 *  RealF
	 * This is the RealF to be displayed in the text area presented by this panel.
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 */
	public FieldAreaRealF(RealF pField) throws UtilitiesException
	{
		super();
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid DivField on construction.");
	    

	    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	    this.setColumns(8);
		this.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
		displayField = pField;
		this.setRows(1);
	}
	
	public void displayContents()
	{
		this.setText(new StringBuffer().append(displayField.getReal()).toString());
		
		/*
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
		*/
	}
	
	public void saveContents()
	{
		displayField=new RealF(displayField.getFieldType(), Float.parseFloat(this.getText()));
		displayContents();
	}
	
	/**
	 * When a new cladosF number is to be displayed, it is passed in through this method.
	 * @param pField
	 *  RealF
	 * This is the RealF to be displayed in the text area presented by this panel.
	 * @throws UtilitiesException 
	 * Most likely means no cladosF field is passed to the constructor
	 */
	public void updateField(RealF pField) throws UtilitiesException
	{
		if (pField == null)
			throw new UtilitiesException("FieldArea must receive valid RealF on update.");

		displayField = RealF.copyOf(pField);
	    this.setRows(1);
	}
	    
}
