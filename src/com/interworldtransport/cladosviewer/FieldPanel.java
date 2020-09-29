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

 public class FieldPanel extends JPanel implements ActionListener
 {
	public				CladosCalculator				_GUI;
	private		final	Color							_backColor = new Color(230, 255, 255);
	private		final	Color							_nullColor = new Color(255, 230, 255);
	private				String							_repMode;
	private		final	String[]						_valLabels= {"real", "img"};
	private				JPanel							buttons;
	private				JPanel							displays;
	
	private				Dimension						squareLarge=new Dimension(42,42);
	private				Dimension						squareMedium=new Dimension(21,21);
	
	protected			double[]						_valsD = new double[2];
	protected			float[]							_valsF = new float[2];
	protected			JButton							clear;
	protected			JTextField 						fieldDisplay = new JTextField();
	protected			DivFieldType					fieldType;
    protected			JButton							makeComplex;
    protected			JButton							makeDouble;
    protected			JButton							makeFloat;
    protected			JButton							makeReal;
    protected			DivField						repField;
    protected			JButton							syncWithNyad;
    protected			ArrayList<JTextField>	valDisplays;

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
    	valDisplays= new ArrayList<JTextField>(2);
    	_valsD = new double[2];
    	_repMode = DivField.COMPLEXD;
    	setCoefficientDisplay(pIn);
    	createLayout();
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
    	valDisplays= new ArrayList<JTextField>(2);
    	_valsF = new float[2];
    	_repMode = DivField.COMPLEXF;
    	setCoefficientDisplay(pIn);
    	createLayout();
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
    	valDisplays= new ArrayList<JTextField>(1);
    	_valsD = new double[1];
    	_repMode = DivField.REALD;
    	setCoefficientDisplay(pIn);
    	createLayout();
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
    	valDisplays= new ArrayList<JTextField>(1);
    	_valsF = new float[1];
    	_repMode = DivField.REALF;
    	setCoefficientDisplay(pIn);
    	createLayout();
    	registerWithViewer();
    }
    @Override
    public void 	actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	if (command.equals("clearIt"))
    	{
    		if (_repMode == DivField.COMPLEXF | _repMode == DivField.COMPLEXD)
    			valDisplays.get(1).setText("");
    		valDisplays.get(0).setText("");
    	}
    	else if (_GUI._GeometryDisplay.getNyadListSize() == 0)
    	{
	    	valDisplays.clear();
	    	remove(displays);
	    	switch (command)
	    	{
	    		case "makeFloat":	if (_repMode == DivField.REALD)	
	    							{
	    								_repMode = DivField.REALF;
	    								_valsF = new float[1];
	    								_valsD = null;
	    								valDisplays = new ArrayList<JTextField>(1);
	    							}
						    		if (_repMode == DivField.COMPLEXD)	
						    		{
						    			_repMode = DivField.COMPLEXF;
						    			_valsF = new float[2];
						    			_valsD = null;
						    			valDisplays = new ArrayList<JTextField>(2);
						    		}
						    		makeDouble.setEnabled(true);
						    		makeFloat.setEnabled(false);
	    							break;
	    		case "makeDouble":	if (_repMode == DivField.REALF)		
					    			{
					    				_repMode = DivField.REALD;
					    				_valsF = null;
					    				_valsD = new double[1];
					    				valDisplays = new ArrayList<JTextField>(1);
					    			}
						    		if (_repMode == DivField.COMPLEXF)	
						    		{
						    			_repMode = DivField.COMPLEXD;
						    			_valsF = null;
						    			_valsD = new double[2];
						    			valDisplays = new ArrayList<JTextField>(2);
						    		}
						    		makeFloat.setEnabled(true);
					    			makeDouble.setEnabled(false);
									break;
	    		case "makeReal":	if (_repMode == DivField.COMPLEXF)	
					    			{
					    				_repMode = DivField.REALF;
					    				_valsF = new float[1];
					    				_valsD = null;
					    			}
						    		if (_repMode == DivField.COMPLEXD)	
						    		{
						    			_repMode = DivField.REALD;
						    			_valsF = null;
						    			_valsD = new double[1];
						    		}
						    		valDisplays = new ArrayList<JTextField>(1);
						    		makeComplex.setEnabled(true);
					    			makeReal.setEnabled(false);
									break;
	    		case "makeComplex":	if (_repMode == DivField.REALF)	
					    			{
					    				_repMode = DivField.COMPLEXF;
					    				_valsD = null;
					    				_valsF = new float[2];
					    			}
						    		if (_repMode == DivField.REALD)	
						    		{
						    			_repMode = DivField.COMPLEXD;
						    			_valsF = null;
						    			_valsD = new double[2];
						    		}
						    		valDisplays = new ArrayList<JTextField>(2);
						    		makeReal.setEnabled(true);
					    			makeComplex.setEnabled(false);
	    	}
	    	createDisplaysLayout();
	    	add(displays, BorderLayout.CENTER);
	    	add(clear, BorderLayout.LINE_END);
	    	//revalidate();

	    	_GUI.pack();
    	}
    	else _GUI._StatusBar.setStatusMsg("Can't change mode while nyads are displayed.\n");	
    }
  
    public String	getImgText()
    {
    	return valDisplays.get(1).getText();
    }
    public String	getRealText()
    {
    	return valDisplays.get(0).getText();
    }
    public String	getRepMode()
    {
    	return _repMode;
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
	private void createControlLayout()
	{
		buttons = new JPanel();
		buttons.setBackground(_backColor);
		buttons.setLayout(new GridBagLayout());
		buttons.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	
	 	GridBagConstraints c1 = new GridBagConstraints();
    	c1.insets = new Insets(0, 0, 0, 0);
    	c1.fill=GridBagConstraints.BOTH;
    	c1.anchor=GridBagConstraints.WEST;
    	
    	c1.gridx = 0;
    	c1.gridy = 0;
    	c1.weightx=0;
    	c1.weighty=0;
    	
    	makeReal = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Real")));
    	makeReal.setActionCommand("makeReal");
    	makeReal.setToolTipText("use real numbers");
    	makeReal.setPreferredSize(squareMedium);
    	makeReal.setBorder(BorderFactory.createEtchedBorder(0));
    	makeReal.addActionListener(this);
    	buttons.add(makeReal, c1);
    	c1.gridx++;
    	
    	makeComplex = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Complex")));
    	makeComplex.setActionCommand("makeComplex");
    	makeComplex.setToolTipText("use complex numbers");
    	makeComplex.setPreferredSize(squareMedium);
    	makeComplex.setBorder(BorderFactory.createEtchedBorder(0));
    	makeComplex.addActionListener(this);
    	buttons.add(makeComplex, c1);
    	c1.gridx = 0;
    	c1.gridy++;

    	makeFloat = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Float")));
    	makeFloat.setActionCommand("makeFloat");
    	makeFloat.setToolTipText("use floating precision");
    	makeFloat.setPreferredSize(squareMedium);
    	makeFloat.setBorder(BorderFactory.createEtchedBorder(0));
    	makeFloat.addActionListener(this);
    	buttons.add(makeFloat, c1);
    	c1.gridx++;
    	
    	makeDouble = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Double")));
    	makeDouble.setActionCommand("makeDouble");
    	makeDouble.setToolTipText("use double precision");
    	makeDouble.setPreferredSize(squareMedium);
    	makeDouble.setBorder(BorderFactory.createEtchedBorder(0));
    	makeDouble.addActionListener(this);
    	buttons.add(makeDouble, c1);
	}
	private void createDisplaysLayout()
	{
		displays = new JPanel();
		displays.setBackground(_backColor);
		displays.setLayout(new GridBagLayout());
		//displays.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	
	 	GridBagConstraints c2 = new GridBagConstraints();
    	c2.insets = new Insets(0, 0, 0, 0);
    	c2.fill=GridBagConstraints.BOTH;
    	c2.anchor=GridBagConstraints.EAST;
    	
    	c2.gridx = 0;
    	c2.gridy = 0;
    	c2.weightx=1;
    	c2.weighty=1;
    	
    	
    	displays.add(new JLabel("name", SwingConstants.CENTER), c2);
    	c2.gridx++;
    	//c2.weightx=0;
    	//c2.weighty=0;
    	
    	switch (_repMode)
    	{
    		case DivField.REALF:	for (short m=0; m<_valsF.length; m++)
									{
							    		displays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
							    		c2.gridx++;
									}
    								makeComplex.setEnabled(true);
    								makeReal.setEnabled(false);
    								break;
    		case DivField.REALD:	for (short m=0; m<_valsD.length; m++)
									{
    									displays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
							    		c2.gridx++;
									}
									makeComplex.setEnabled(true);
									makeReal.setEnabled(false);
									break;
    		case DivField.COMPLEXF:	for (short m=0; m<_valsF.length; m++)
									{
    									displays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
							    		c2.gridx++;
									}
									makeComplex.setEnabled(false);
									makeReal.setEnabled(true);
									break;
    		case DivField.COMPLEXD:	for (short m=0; m<_valsD.length; m++)
									{
    									displays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
							    		c2.gridx++;
									}
									makeComplex.setEnabled(false);
									makeReal.setEnabled(true);
    	}

    	c2.gridx=0;
    	c2.gridy++;
    	
    	fieldDisplay.setColumns(20);
    	fieldDisplay.setFont(new Font("Serif", Font.PLAIN, 14));
    	fieldDisplay.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    	displays.add(fieldDisplay, c2);
		c2.gridx++;
		
		//NumberFormat amountFormat = NumberFormat.getNumberInstance();
		//amountFormat.setGroupingUsed(false);
		short m;
		JTextField tSpot;
		switch (_repMode)
		{
			case DivField.REALF: 	for (m=0; m<_valsF.length; m++)
									{
										tSpot = new JTextField();
										tSpot.setColumns(FieldDisplayArea.FLOATSIZE);
							    		tSpot.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
							    		tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
							    		valDisplays.add(m, tSpot);
							    		displays.add(tSpot, c2);
							    		c2.gridx++;
									}
									makeFloat.setEnabled(false);
									makeDouble.setEnabled(true);
									break;
			case DivField.REALD: 	for (m=0; m<_valsD.length; m++)
									{
										tSpot = new JTextField();
										tSpot.setColumns(FieldDisplayArea.DOUBLESIZE);
							    		tSpot.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
							    		tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
							    		valDisplays.add(m, tSpot);
							    		displays.add(tSpot, c2);
							    		c2.gridx++;
									}
									makeFloat.setEnabled(true);
									makeDouble.setEnabled(false);
									break;
			case DivField.COMPLEXF:	for (m=0; m<_valsF.length; m++)
									{
										tSpot = new JTextField();
										tSpot.setColumns(FieldDisplayArea.FLOATSIZE);
							    		tSpot.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
							    		tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
							    		valDisplays.add(m, tSpot);
							    		displays.add(tSpot, c2);
							    		c2.gridx++;
									}
									makeFloat.setEnabled(false);
									makeDouble.setEnabled(true);
									break;
			case DivField.COMPLEXD:	for (m=0; m<_valsD.length; m++)
									{
										tSpot = new JTextField();
										tSpot.setColumns(FieldDisplayArea.DOUBLESIZE);
							    		tSpot.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
							    		tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
							    		valDisplays.add(m, tSpot);
							    		displays.add(tSpot, c2);
							    		c2.gridx++;
									}
									makeFloat.setEnabled(true);
									makeDouble.setEnabled(false);
		}
		
	}
	
    private void createLayout()
    {
    	setBackground(_backColor);
		//setLayout(new GridBagLayout());
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	
    	createControlLayout();
    	add(buttons, BorderLayout.LINE_START);
    	
    	createDisplaysLayout();
    	add(displays, BorderLayout.CENTER);
    	
    	clear = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Remove")));
    	clear.setActionCommand("clearIt");
    	clear.setPreferredSize(squareLarge);
    	clear.setBorder(BorderFactory.createEtchedBorder(0));
    	clear.addActionListener(this);
    	add(clear, BorderLayout.LINE_END);
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
