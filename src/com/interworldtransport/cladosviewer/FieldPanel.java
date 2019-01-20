/**
 * <h2>Copyright</h2> © 2019 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.FieldPanel<br>
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
 * ---com.interworldtransport.cladosviewer.FieldPanel<br>
 * ------------------------------------------------------------------------ <br>
 */

package com.interworldtransport.cladosviewer ;
import com.interworldtransport.cladosF.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.BevelBorder;

/** 
 * The FieldPanel class is intended to be the the numeric input panel
 * for the calculator
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class FieldPanel extends JPanel
 {
	public				CladosCalculator				_GUI;
	private		final	Color							_backColor = new Color(230, 255, 255);
	private		final	Color							_nullColor = new Color(255, 230, 255);
	private		final	String[]						_valLabels= {"real", "i", "j", "k"};
	protected	final	String[]						_fields= {	"RealF", "RealD", 
																	"ComplexF", "ComplexD",
																	"QuatF", "QuatD"};
	protected			JTextField 						fieldDisplay = new JTextField();
   
	protected			DivFieldType					fieldType;
	protected			DivField						repField;
    protected			ArrayList<JFormattedTextField>	valDisplays;
    protected			float[]							vals;

/**
 * The FieldPanel class is intended to be the contain a cladosF Field in much
 * the same way as Monad and Nyad panels represent their contents to the calculator
 */
    public FieldPanel(CladosCalculator pGUI)
    {
    	super();
    	_GUI=pGUI;
    	valDisplays= new ArrayList<JFormattedTextField>(4);
    	vals = new float[4];
    	createLayout();
    	registerWithViewer();
    }
    public FieldPanel(CladosCalculator pGUI, RealF pIn)
    {
    	super();
    	_GUI=pGUI;
    	valDisplays= new ArrayList<JFormattedTextField>(1);
    	vals = new float[1];
    	setCoefficientDisplay(pIn);
    	createLayout();
    	registerWithViewer();
    }
    public FieldPanel(ComplexF pIn)
    {
    	super();
    	valDisplays= new ArrayList<JFormattedTextField>(2);
    	vals = new float[2];
    	setCoefficientDisplay(pIn);
    	createLayout();
    	registerWithViewer();
    }
    
    public String	getImgText()
    {
    	return valDisplays.get(1).getText();
    }
    
    public String	getRealText()
    {
    	return valDisplays.get(0).getText();
    }
    
    public void 	setCoefficientDisplay(ComplexF pIn)
    {
    	setField(pIn);
	    setFieldType(pIn.getFieldType());
	    vals[0]=pIn.getReal();
	    vals[1]=pIn.getImg();
    }
    
    public void 	setCoefficientDisplay(RealF pIn)
    {
    	setField(pIn);
	    setFieldType(pIn.getFieldType());
	    vals[0]=pIn.getReal();
    }
    
    public void setField(DivField pField)
	{
	    repField=pField;
	    fieldType=pField.getFieldType();
	    	
	    if (repField!=null)
	    {
	    	setBackground(_backColor);
	    	setFieldType(repField.getFieldType());
	    }
	    else
	    {
	    	setBackground(_nullColor);
	    	setFieldType(null);
	    }
	}
	 
    public void setWhatDouble(double pWhat)
	{
		valDisplays.get(0).setText((new StringBuffer().append(pWhat)).toString());
	}
    
    public void setWhatFloat(float pWhat)
	{
		valDisplays.get(0).setText((new StringBuffer().append(pWhat)).toString());
	}
	    
	private void createLayout()
    {
    	setBackground(_backColor);
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	
	 	GridBagConstraints cn = new GridBagConstraints();
    	Insets tGeneric = new Insets(0, 0, 0, 0);
    	cn.insets = tGeneric;
    	cn.fill=GridBagConstraints.BOTH;
    	cn.anchor=GridBagConstraints.EAST;
    	
    	cn.gridx = 0;
    	cn.gridy = 0;
    	cn.weightx=1;
    	cn.weighty=1;
    	add(new JLabel(), cn);
    	
    	cn.gridx++;
    	cn.weightx=0;
    	cn.weighty=0;
    	
    	add(new JLabel("Field", SwingConstants.CENTER), cn);
    	cn.gridx++;
    	
    	for (short m=0; m<vals.length; m++)
		{
    		add(new JLabel(_valLabels[m], SwingConstants.CENTER), cn);
        	cn.gridx++;
		}
    	
    	cn.gridx=0;
    	cn.gridy++;
    	
    	cn.weightx=1;
    	cn.weighty=1;
    	add(new JLabel(), cn);
    	cn.weightx=0;
    	cn.weighty=0;
    	cn.gridx++;
    	
    	fieldDisplay.setColumns(20);
    	fieldDisplay.setFont(new Font("Serif", Font.PLAIN, 12));
    	fieldDisplay.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		add(fieldDisplay, cn);
		cn.gridx++;
		
		NumberFormat amountFormat = NumberFormat.getNumberInstance();
		for (short m=0; m<vals.length; m++)
		{
			JFormattedTextField tSpot = new JFormattedTextField(amountFormat);
			tSpot.setColumns(8);
    		tSpot.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
    		tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    		valDisplays.add(m, tSpot);
			add(tSpot, cn);
			cn.gridx++;
		}
    }

	protected void registerWithViewer()
	{
		_GUI._GeometryDisplay.registerFieldPanel(this);
	}
	
	protected void setFieldType(DivFieldType pField)
	{
	    fieldType=pField;
	    if (fieldType != null)
	    {
	    	setBackground(_backColor);
	    	fieldDisplay.setText(pField.getType());
	    }
	    else
	    {
	    	setBackground(_nullColor);
	    	fieldDisplay.setText("null");
	    }
	}
	
	protected void setWhatInt(int pWhat)
	{
		valDisplays.get(0).setText((new StringBuffer(pWhat)).toString());
	}
}
