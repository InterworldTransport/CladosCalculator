/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.BevelBorder;

/** 
 * The FieldPanel class is intended to be the the numeric input panel for the calculator.
 * <p>
 * This is the entry register. On a RPN calculator, it is the bottom of the stack.
 * In this calculator it can't quite be, though. There is no single stack here, so this 
 * register is more of a feeder to functions that require a field scalar to combine with 
 * elements of the stacks.
 * <p>
 * This about how scalar multiplication works in vector spaces. This is where the scalar is
 * stored for the function to use. That 'scalar' is 'repField'.
 * <p>
 * @version 0.85
 * @author Dr Alfred W Differ
 */

 public class FieldPanel extends JPanel implements ActionListener, FocusListener
 {
	public				CladosCalculator				_GUI;
	private		final	Color							_backColor = new Color(230, 255, 255);
	private		final	Color							_nullColor = new Color(255, 230, 255);
	private		final	String[]						_valLabels= {"real", "img"};
	private				Dimension						squareMedium=new Dimension(25,25);
	protected	final	String[]						_fields= {	"RealF", "RealD", 
																	"ComplexF", "ComplexD"};
	
	protected			double[]						_valsD;
	protected			float[]							_valsF;
	
	protected			JButton							clear;
	protected			JTextField 						fieldDisplay = new JTextField();
	protected			DivFieldType					fieldType;
    protected			JButton							makeReal;
    protected			JButton							makeComplex;
    protected			JButton							makeFloat;
    protected			JButton							makeDouble;
    protected			DivField						repField;
    protected			JButton							syncWithNyad;
    protected			ArrayList<JFormattedTextField>	valDisplays;

    /**
     * The FieldPanel class is intended to be contain a cladosF Field in much
     * the same way as Monad and Nyad panels represent their contents to the calculator
     * 
     * @param pGUI
     *		CladosCalculator
     * This parameter references the owning application. It's there to offer a way
     * to get error messages to the GUI.
     * @param pIn
     * 	ComplexD
     * This parameter offers one of the DivField objects to display so the panel 
     * doesn't make one of its own.
     */
    public FieldPanel(CladosCalculator pGUI, ComplexD pIn)
    {
    	super();
    	_GUI=pGUI;
    	valDisplays= new ArrayList<JFormattedTextField>(2);
    	_valsD = new double[2];
    	setCoefficientDisplay(pIn);
    	createLayout(pIn);
    	registerWithViewer();
    }
    /**
     * The FieldPanel class is intended to be contain a cladosF Field in much
     * the same way as Monad and Nyad panels represent their contents to the calculator
     * 
     * @param pGUI
     *		CladosCalculator
     * This parameter references the owning application. It's there to offer a way
     * to get error messages to the GUI.
     * @param pIn
     * 	ComplexF
     * This parameter offers one of the DivField objects to display so the panel 
     * doesn't make one of its own.
     */
    public FieldPanel(CladosCalculator pGUI, ComplexF pIn)
    {
    	super();
    	_GUI=pGUI;
    	valDisplays= new ArrayList<JFormattedTextField>(2);
    	_valsF = new float[2];
    	setCoefficientDisplay(pIn);
    	createLayout(pIn);
    	registerWithViewer();
    }
    /**
     * The FieldPanel class is intended to be contain a cladosF Field in much
     * the same way as Monad and Nyad panels represent their contents to the calculator
     * 
     * @param pGUI
     *		CladosCalculator
     * This parameter references the owning application. It's there to offer a way
     * to get error messages to the GUI.
     * @param pIn
     * 	RealD
     * This parameter offers one of the DivField objects to display so the panel 
     * doesn't make one of its own.
     */
    public FieldPanel(CladosCalculator pGUI, RealD pIn)
    {
    	super();
    	_GUI=pGUI;
    	valDisplays= new ArrayList<JFormattedTextField>(1);
    	_valsD = new double[1];
    	setCoefficientDisplay(pIn);
    	createLayout(pIn);
    	registerWithViewer();
    }
    /**
     * The FieldPanel class is intended to be contain a cladosF Field in much
     * the same way as Monad and Nyad panels represent their contents to the calculator
     * 
     * @param pGUI
     *		CladosCalculator
     * This parameter references the owning application. It's there to offer a way
     * to get error messages to the GUI.
     * @param pIn
     * 	RealF
     * This parameter offers one of the DivField objects to display so the panel 
     * doesn't make one of its own.
     */
    public FieldPanel(CladosCalculator pGUI, RealF pIn)
    {
    	super();
    	_GUI=pGUI;
    	valDisplays= new ArrayList<JFormattedTextField>(1);
    	_valsF = new float[1];
    	setCoefficientDisplay(pIn);
    	createLayout(pIn);
    	registerWithViewer();
    }
    @Override
    public void 	actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	System.out.println("FieldPanel says "+command);
    	
    	// the idea here is to manage the displays with action buttons to be added here soon.
    }
    @Override
    public void focusGained(FocusEvent e) 
    {
    	// This could potentially be something to check the related field type isn't null
    	// or to enable some kind of edit feature so the field bar type matches something else
    	
    	//JTextArea tSpot = (JTextArea) e.getComponent();
    	//_GUI._FieldBar.setWhatFloat0(Float.valueOf(tSpot.getText()));
    }
    
    @Override
	public void focusLost(FocusEvent e) 
    {
    	// this could potentially be something that checks the related field type for something?
    	;
	}
  
    public String	getImgText()
    {
    	return valDisplays.get(1).getText();
    }
    public String	getRealText()
    {
    	return valDisplays.get(0).getText();
    }
    /**
     * This 'set' function simply adjusts the displayed complex number by accepting an input
     * and then pushing those parts to values stored in the panel. 
     * @param pIn
     * 	ComplexD
     * When resetting the complex number to be displayed, feed the ComplexF in with this parameter.
     */
    public void 	setCoefficientDisplay(ComplexD pIn)
    {
    	setField(pIn);
	    setFieldType(pIn.getFieldType());
	    _valsD[0]=pIn.getReal();
	    _valsD[1]=pIn.getImg();
    }
    /**
     * This 'set' function simply adjusts the displayed complex number by accepting an input
     * and then pushing those parts to values stored in the panel. 
     * @param pIn
     * 	ComplexF
     * When resetting the complex number to be displayed, feed the ComplexF in with this parameter.
     */
    public void 	setCoefficientDisplay(ComplexF pIn)
    {
    	setField(pIn);
	    setFieldType(pIn.getFieldType());
	    _valsF[0]=pIn.getReal();
	    _valsF[1]=pIn.getImg();
    }
    /**
     * This 'set' function simply adjusts the displayed complex number by accepting an input
     * and then pushing those part(s) to values stored in the panel. 
     * @param pIn
     * 	RealD
     * When resetting the real number to be displayed, feed the RealF in with this parameter.
     */
    public void 	setCoefficientDisplay(RealD pIn)
    {
    	setField(pIn);
	    setFieldType(pIn.getFieldType());
	    _valsD[0]=pIn.getReal();
    }
    /**
     * This 'set' function simply adjusts the displayed complex number by accepting an input
     * and then pushing those part(s) to values stored in the panel. 
     * @param pIn
     * 	RealF
     * When resetting the real number to be displayed, feed the RealF in with this parameter.
     */
    public void 	setCoefficientDisplay(RealF pIn)
    {
    	setField(pIn);
	    setFieldType(pIn.getFieldType());
	    _valsF[0]=pIn.getReal();
    }
    /**
     * This 'set' function simply accepts a DivField used as context for the division field being displayed.
     * <p>
     * @param pField
     * 	DivField
     * Provide the cladosF DivField here so reference match requirements are met on later function calls.
     */
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
    /**
     * This 'set' function simply sets the displayed number (a double) into the first imaginary display text field.
     * <p>
     * @param pWhat
     * 	double
     * Provide the primitive here to adjust what real number is displayed.
     */
    public void setWhatDoubleI(double pWhat)
	{
		valDisplays.get(1).setText((new StringBuffer().append(pWhat)).toString());
	}
    /**
     * This 'set' function simply sets the displayed number (a double) into the real number display text field.
     * <p>
     * @param pWhat
     * 	double
     * Provide the primitive here to adjust what real number is displayed.
     */
    public void setWhatDoubleR(double pWhat)
	{
		valDisplays.get(0).setText((new StringBuffer().append(pWhat)).toString());
	}
    /**
     * This 'set' function simply sets the displayed number (a float) into the first imaginary number display text field.
     * <p>
     * @param pWhat
     * 	float
     * Provide the primitive here to adjust what real number is displayed.
     */
    public void setWhatFloatI(float pWhat)
	{
		valDisplays.get(1).setText((new StringBuffer().append(pWhat)).toString());
	}
    /**
     * This 'set' function simply sets the displayed number (a float) into the real number display text field.
     * <p>
     * @param pWhat
     * 	float
     * Provide the primitive here to adjust what real number is displayed.
     */
    public void setWhatFloatR(float pWhat)
	{
		valDisplays.get(0).setText((new StringBuffer().append(pWhat)).toString());
	}
	/**
     * This 'set' function simply sets the displayed number (an int) into the first imaginary display text field.
     * <p>
     * I'm not sure why this method is protected and its siblings are public.
     * <p>
     * @param pWhat
     * 	int
     * Provide the primitive here to adjust what real number is displayed.
     */
	public void setWhatIntI(int pWhat)
	{
		valDisplays.get(1).setText((new StringBuffer(pWhat)).toString());
	}

	/**
     * This 'set' function simply sets the displayed number (an int) into the real number display text field.
     * <p>
     * I'm not sure why this method is protected and its siblings are public.
     * <p>
     * @param pWhat
     * 	int
     * Provide the primitive here to adjust what real number is displayed.
     */
	public void setWhatIntR(int pWhat)
	{
		valDisplays.get(0).setText((new StringBuffer(pWhat)).toString());
	}
    private void createLayout(DivField pIn)
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
    	cn.weightx=0;
    	cn.weighty=0;
    	
    	syncWithNyad = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.TabN")));
    	syncWithNyad.setActionCommand("sync");
    	syncWithNyad.setToolTipText("sync Field with Nyad");
    	syncWithNyad.setPreferredSize(squareMedium);
    	syncWithNyad.setBorder(BorderFactory.createEtchedBorder(0));
    	syncWithNyad.addActionListener(this);
    	add(syncWithNyad, cn);
    	cn.gridx++;
    	
    	makeReal = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Real")));
    	makeReal.setActionCommand("makeReal");
    	makeReal.setToolTipText("use real numbers");
    	makeReal.setPreferredSize(squareMedium);
    	makeReal.setBorder(BorderFactory.createEtchedBorder(0));
    	makeReal.addActionListener(this);
    	add(makeReal, cn);
    	cn.gridx++;
    	
    	makeComplex = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Complex")));
    	makeComplex.setActionCommand("makeComplex");
    	makeComplex.setToolTipText("use complex numbers");
    	makeComplex.setPreferredSize(squareMedium);
    	makeComplex.setBorder(BorderFactory.createEtchedBorder(0));
    	makeComplex.addActionListener(this);
    	add(makeComplex, cn);
    	cn.gridx++;
    	
    	cn.weightx=1;
    	cn.weighty=1;
    	add(new JLabel(), cn);
    	
    	cn.gridx++;
    	cn.weightx=0;
    	cn.weighty=0;
    	
    	add(new JLabel("name", SwingConstants.CENTER), cn);
    	cn.gridx++;
    	
    	if (pIn instanceof RealF | pIn instanceof ComplexF)
    	{
    		for (short m=0; m<_valsF.length; m++)
			{
	    		add(new JLabel(_valLabels[m], SwingConstants.CENTER), cn);
	        	cn.gridx++;
			}
    	}
    	if (pIn instanceof RealD | pIn instanceof ComplexD)
    	{
    		for (short m=0; m<_valsD.length; m++)
			{
	    		add(new JLabel(_valLabels[m], SwingConstants.CENTER), cn);
	        	cn.gridx++;
			}
    	}
    	cn.gridx=0;
    	cn.gridy++;
    	cn.weightx=0;
    	cn.weighty=0;
    	
    	clear = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Remove")));
    	clear.setActionCommand("clear");
    	clear.setToolTipText("set vals = 0");
    	clear.setPreferredSize(squareMedium);
    	clear.setBorder(BorderFactory.createEtchedBorder(0));
    	clear.addActionListener(this);
    	add(clear, cn);
    	cn.gridx++;
    	
    	makeFloat = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Float")));
    	makeFloat.setActionCommand("makeFloat");
    	makeFloat.setToolTipText("use floating precision");
    	makeFloat.setPreferredSize(squareMedium);
    	makeFloat.setBorder(BorderFactory.createEtchedBorder(0));
    	makeFloat.addActionListener(this);
    	add(makeFloat, cn);
    	cn.gridx++;
    	
    	makeDouble = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Double")));
    	makeDouble.setActionCommand("makeDouble");
    	makeDouble.setToolTipText("use double precision");
    	makeDouble.setPreferredSize(squareMedium);
    	makeDouble.setBorder(BorderFactory.createEtchedBorder(0));
    	makeDouble.addActionListener(this);
    	add(makeDouble, cn);
    	cn.gridx++;
    	
    	
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
		
    	if (pIn instanceof RealF | pIn instanceof ComplexF)
    	{
			for (short m=0; m<_valsF.length; m++)
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
    	if (pIn instanceof RealD | pIn instanceof ComplexD)
    	{
			for (short m=0; m<_valsD.length; m++)
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
    }
    protected void registerWithViewer()
	{
		_GUI._GeometryDisplay.registerFieldPanel(this);
	}
    /**
     * This 'set' function simply accepts a DivFieldType used as context for the division field being displayed.
     * <p>
     * @param pType
     * 	DivFieldType
     * Provide the cladosF DivFieldType here so reference match requirements are met on later function calls.
     */
	protected void setFieldType(DivFieldType pType)
	{
	    fieldType=pType;
	    if (fieldType != null)
	    {
	    	setBackground(_backColor);
	    	fieldDisplay.setText(pType.getType());
	    }
	    else
	    {
	    	setBackground(_nullColor);
	    	fieldDisplay.setText("null");
	    }
	}
}
