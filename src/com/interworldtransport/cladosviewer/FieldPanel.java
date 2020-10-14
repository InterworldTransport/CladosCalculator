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
import com.interworldtransport.cladosF.CladosField;
import com.interworldtransport.cladosF.ComplexD;
import com.interworldtransport.cladosF.ComplexF;
import com.interworldtransport.cladosF.RealD;
import com.interworldtransport.cladosF.RealF;
import com.interworldtransport.cladosFExceptions.FieldException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
	private static final long 					serialVersionUID = -6988601261409935965L;
	private static final int 					FONTSIZE = 			12;
	private static final int 					_DOUBLESIZE = 		16;
	private static final int 					_FLOATSIZE =	 	10;
	private static final String					_IMAGINARY =		"[I]";
	private static final String 				_REAL =				"[R]";
	private	static final Color					clrBackColor = 		new Color(230, 255, 255);
	private	static final Color					clrNullColor = 		new Color(255, 230, 255);
	private	static final Dimension				squareLarge =		new Dimension(42,42);
	private	static final Dimension				squareMedium =		new Dimension(21,21);
	
	private				CladosCalculator		_GUI;
	private				CladosField				_repMode;
	private		final	String[]				_valLabels= {_REAL, _IMAGINARY};
	private				JButton					btnClear;
	private				JButton					btnConjugate;
	private				JButton					btnInverse;
	private				JButton					btnMakeComplex;
	private				JButton					btnMakeDouble;
	private				JButton					btnMakeFloat;
	private				JButton					btnMakeReal;
	private				JPanel					pnlButtons;
	private				JPanel					pnlDisplays;
	private				ArrayList<JTextField>	valDisplays;
	protected			ComplexD				_repComplexD;
	protected			ComplexF				_repComplexF;
    protected			RealD					_repRealD;
    protected			RealF					_repRealF;

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
    	_repMode = CladosField.COMPLEXD;
    	_repComplexD = pIn;
    	setBackground(clrBackColor);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		createControlLayout();
    	add(pnlButtons, BorderLayout.LINE_START);
    	
    	createDisplaysLayout();
    	add(pnlDisplays, BorderLayout.LINE_END);
    	setCoefficientDisplay(pIn);
    	_GUI._GeometryDisplay.registerFieldPanel(this);
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
    	_repMode = CladosField.COMPLEXF;
    	_repComplexF = pIn;
    	setBackground(clrBackColor);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		createControlLayout();
    	add(pnlButtons, BorderLayout.LINE_START);
    	
    	createDisplaysLayout();
    	add(pnlDisplays, BorderLayout.LINE_END);
    	setCoefficientDisplay(pIn);
    	_GUI._GeometryDisplay.registerFieldPanel(this);
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
    	_repMode = CladosField.REALD;
    	_repRealD = pIn;
    	setBackground(clrBackColor);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		createControlLayout();
    	add(pnlButtons, BorderLayout.LINE_START);
    	
    	createDisplaysLayout();
    	add(pnlDisplays, BorderLayout.LINE_END);
    	setCoefficientDisplay(pIn);
    	_GUI._GeometryDisplay.registerFieldPanel(this);
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
    	_repMode = CladosField.REALF;
    	_repRealF = pIn;
    	setBackground(clrBackColor);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		createControlLayout();
    	add(pnlButtons, BorderLayout.LINE_START);
    	
    	createDisplaysLayout();
    	add(pnlDisplays, BorderLayout.LINE_END);
    	setCoefficientDisplay(pIn);
    	_GUI._GeometryDisplay.registerFieldPanel(this);
    }
    @Override
    public void 	actionPerformed(ActionEvent event)
    {
    	String command = event.getActionCommand();
    	if (command.equals("clearIt"))
    	{	
    		if (_repMode == CladosField.COMPLEXF | _repMode == CladosField.COMPLEXD)
    			setImgText("");
    		setRealText("");
    	}
    	else if (command.equals("conjugate"))
    	{
    		try
    		{
	    		switch (_repMode)
	    		{
	    			case REALF:	_repRealF = new RealF(	_repRealF.getCardinal(), 
	    												Float.parseFloat(getRealText())
	    												);
	    						setCoefficientDisplay(_repRealF.conjugate());
	    						break;
	    			case REALD:	_repRealD = new RealD(	_repRealD.getCardinal(), 
	    												Double.parseDouble(getRealText())
	    												);
								setCoefficientDisplay(_repRealD.conjugate());
								break;
	    			case COMPLEXF:	_repComplexF = new ComplexF(_repComplexF.getCardinal(), 
	    														Float.parseFloat(getRealText()), 
	    														Float.parseFloat(getImgText())
	    														);
	    							setCoefficientDisplay(_repComplexF.conjugate());
									break;
	    			case COMPLEXD:	_repComplexD = new ComplexD(_repComplexD.getCardinal(), 
	    														Double.parseDouble(getRealText()), 
	    														Double.parseDouble(getImgText())
	    														);
									setCoefficientDisplay(_repComplexD.conjugate());
	    		}
    		}
    		catch (NumberFormatException en)
    		{
    			_GUI._StatusBar.setStatusMsg("Number Format Exception prevented inversion.\n");
    			_GUI._StatusBar.setStatusMsg(en.getMessage());
    		}
    	}
    	else if (command.equals("inverse"))
    	{
    		try
    		{
	    		switch (_repMode)
	    		{
	    			case REALF:	_repRealF = new RealF(	_repRealF.getCardinal(),
	    												Float.parseFloat(getRealText())
	    												);
	    						setCoefficientDisplay(_repRealF.invert());
	    						break;
	    			case REALD:	_repRealD = new RealD(	_repRealD.getCardinal(),
	    												Double.parseDouble(getRealText())
	    												);
								setCoefficientDisplay(_repRealD.invert());
								break;
	    			case COMPLEXF:	_repComplexF = new ComplexF(_repComplexF.getCardinal(), 
	    														Float.parseFloat(getRealText()), 
	    														Float.parseFloat(getImgText())
	    														);
	    							setCoefficientDisplay(_repComplexF.invert());
									break;
	    			case COMPLEXD:	_repComplexD = new ComplexD(_repComplexD.getCardinal(),  
	    														Double.parseDouble(getRealText()), 
	    														Double.parseDouble(getImgText())
	    														);
									setCoefficientDisplay(_repComplexD.invert());
	    		}
    		}
    		catch (FieldException e)
    		{
    			_GUI._StatusBar.setStatusMsg("Field Exception prevented inversion.\n");
    			_GUI._StatusBar.setStatusMsg(e.getSourceMessage()+"\n");
    			_GUI._StatusBar.setStatusMsg(e.getSource().getCardinalString()+"\n");
    		}
    		catch (NumberFormatException en)
    		{
    			_GUI._StatusBar.setStatusMsg("Number Format Exception prevented inversion.\n");
    			_GUI._StatusBar.setStatusMsg(en.getMessage()+"\n");
    		}
    	}
    	else if (_GUI._GeometryDisplay.getNyadListSize() == 0)
    	{
	    	valDisplays.clear();
	    	remove(pnlDisplays);
	    	switch (command)
	    	{
	    		case "makeFloat":	if (_repMode == CladosField.REALD)	
	    							{
	    								_repMode = CladosField.REALF;
	    								if (_repRealF == null) 
	    									_repRealF = RealF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"));
	    							}
	    							if (_repMode == CladosField.COMPLEXD)	
	    							{
						    			_repMode = CladosField.COMPLEXF;
						    			if (_repComplexF == null) 
						    				_repComplexF = ComplexF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"));
	    							}
						    		btnMakeDouble.setEnabled(true);
						    		btnMakeFloat.setEnabled(false);
						    		repaint();
	    							break;
	    		case "makeDouble":	if (_repMode == CladosField.REALF)
						    		{
										_repMode = CladosField.REALD;
										if (_repRealD == null) 
											_repRealD = RealD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"));
									}
						    		if (_repMode == CladosField.COMPLEXF)
						    		{
						    			_repMode = CladosField.COMPLEXD;
						    			if (_repComplexD == null) 
						    				_repComplexD = ComplexD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"));
									}
						    		btnMakeFloat.setEnabled(true);
					    			btnMakeDouble.setEnabled(false);
									break;
	    		case "makeReal":	if (_repMode == CladosField.COMPLEXF)	
	    							{
						    			_repMode = CladosField.REALF;
										if (_repRealF == null) 
											_repRealF = RealF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"));
	    							}
						    		if (_repMode == CladosField.COMPLEXD)	
						    		{
										_repMode = CladosField.REALD;
										if (_repRealD == null) 
											_repRealD = RealD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"));
									}
						    		btnMakeComplex.setEnabled(true);
					    			btnMakeReal.setEnabled(false);
									break;
	    		case "makeComplex":	if (_repMode == CladosField.REALF)	
	    							{
						    			_repMode = CladosField.COMPLEXF;
						    			if (_repComplexF == null) 
						    				_repComplexF = ComplexF.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"));
									}
						    		if (_repMode == CladosField.REALD)	
						    		{
						    			_repMode = CladosField.COMPLEXD;
						    			if (_repComplexD == null) 
						    				_repComplexD = ComplexD.newZERO(_GUI.IniProps.getProperty("Desktop.Default.DivFieldType"));
									}
						    		btnMakeReal.setEnabled(true);
					    			btnMakeComplex.setEnabled(false);
	    	}
	    	createDisplaysLayout();
	    	add(pnlDisplays, BorderLayout.LINE_END);

	    	_GUI.pack();
    	}
    	else _GUI._StatusBar.setStatusMsg("Can't change mode while nyads are displayed.\n");	
    }
  
    /**
	 * This method is called when focus is gained on the FieldBar. It covers for the possibility that the 
	 * underlying DivField is out of sync with displays, so the display content is updated
	 * 
	 * Any earlier parsing difficulty will get overwritten when focus returns, so this is also a reset feature.
	 */
	@Override
	public void focusGained(FocusEvent e) 
	{
		switch (_repMode)
		{
			case REALF:	if (_repRealF != null) setRealText(Float.valueOf(_repRealF.getReal()).toString());
						break;
			case REALD:	if (_repRealD != null) setRealText(Double.valueOf(_repRealD.getReal()).toString());
						break;
			case COMPLEXF:	if (_repComplexD != null)
							{
								setRealText(Float.valueOf(_repComplexF.getReal()).toString());
								setImgText(Float.valueOf(_repComplexF.getImg()).toString());
							}
							break;
			case COMPLEXD:	if (_repComplexD != null)
							{
								setRealText(Double.valueOf(_repComplexD.getReal()).toString());
								setImgText(Double.valueOf(_repComplexD.getImg()).toString());
							}
		}
	}
    /**
	 * This method is called when focus is lost on the FieldBar. The actions attempted assume that change might
	 * have occurred while focus was present. Changes might leave the underlying DivField out of sync with 
	 * what is displayed, so the display content is copied down to the DivField.
	 * 
	 * Any parsing difficulty results in an exception that simply stops the update. That CAN leave the 
	 * represented DivField out of sync with the display. In that event, just bring focus back to the FieldBar
	 * and fix things so the numbers can be parsed successfully.
	 */
	@Override
	public void focusLost(FocusEvent e) 
	{
		//_GUI._StatusBar.setStatusMsg("Focus Lost on Field Panel. "+e.getSource().getClass().getName());
		try
		{
			switch (_repMode)
			{
				case REALF:	if (_repRealF != null) _repRealF.setReal(Float.parseFloat(getRealText()));
							break;
				case REALD:	if (_repRealD != null) _repRealD.setReal(Double.parseDouble(getRealText()));
							break;
				case COMPLEXF:	if (_repComplexF != null) 
								{
									_repComplexF.setReal(Float.parseFloat(getRealText()));
									_repComplexF.setImg(Float.parseFloat(getImgText()));
								}
								break;
				case COMPLEXD:	if (_repComplexD != null) 
								{
									_repComplexD.setReal(Double.parseDouble(getRealText()));
									_repComplexD.setImg(Double.parseDouble(getImgText()));
								}
			}
		}
		catch (NumberFormatException en)
		{
			_GUI._StatusBar.setStatusMsg("Couldn't parse FieldBar, so doing nothing to set the Div Field it represents.");
		}

	}
	public String	getImgText()
    {
    	return valDisplays.get(1).getText();
    }
	public String	getRealText()
    {
    	return valDisplays.get(0).getText();
    }
    public CladosField	getRepMode()
    {
    	return _repMode;
    }
    public void	setImgText(String pIn)
    {
    	valDisplays.get(1).setText(pIn);
    }
    public void	setRealText(String pIn)
    {
    	valDisplays.get(0).setText(pIn);
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
    	setImgText((new StringBuffer().append(pWhat)).toString());
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
    	setRealText((new StringBuffer().append(pWhat)).toString());
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
    	setImgText((new StringBuffer().append(pWhat)).toString());
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
    	setRealText((new StringBuffer().append(pWhat)).toString());
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
		setImgText((new StringBuffer(pWhat)).toString());
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
		setRealText((new StringBuffer(pWhat)).toString());
	}
    private void createControlLayout()
	{
		pnlButtons = new JPanel();
		pnlButtons.setBackground(clrBackColor);
		pnlButtons.setLayout(new GridBagLayout());
		pnlButtons.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	
	 	GridBagConstraints c1 = new GridBagConstraints();
    	c1.insets = new Insets(0, 0, 0, 0);
    	c1.fill=GridBagConstraints.BOTH;
    	c1.anchor=GridBagConstraints.WEST;
    	
    	c1.gridx = 0;
    	c1.gridy = 0;
    	c1.weightx=0;
    	c1.weighty=0;
    	
    	btnMakeReal = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Real")));
    	btnMakeReal.setActionCommand("makeReal");
    	btnMakeReal.setToolTipText("use real numbers");
    	btnMakeReal.setPreferredSize(squareMedium);
    	btnMakeReal.setBorder(BorderFactory.createEtchedBorder(0));
    	btnMakeReal.addActionListener(this);
    	pnlButtons.add(btnMakeReal, c1);
    	c1.gridy++;
    	
    	btnMakeComplex = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Complex")));
    	btnMakeComplex.setActionCommand("makeComplex");
    	btnMakeComplex.setToolTipText("use complex numbers");
    	btnMakeComplex.setPreferredSize(squareMedium);
    	btnMakeComplex.setBorder(BorderFactory.createEtchedBorder(0));
    	btnMakeComplex.addActionListener(this);
    	pnlButtons.add(btnMakeComplex, c1);
    	c1.gridy = 0;
    	c1.gridx++;

    	btnMakeFloat = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Float")));
    	btnMakeFloat.setActionCommand("makeFloat");
    	btnMakeFloat.setToolTipText("use floating precision");
    	btnMakeFloat.setPreferredSize(squareMedium);
    	btnMakeFloat.setBorder(BorderFactory.createEtchedBorder(0));
    	btnMakeFloat.addActionListener(this);
    	pnlButtons.add(btnMakeFloat, c1);
    	c1.gridy++;
    	
    	btnMakeDouble = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Double")));
    	btnMakeDouble.setActionCommand("makeDouble");
    	btnMakeDouble.setToolTipText("use double precision");
    	btnMakeDouble.setPreferredSize(squareMedium);
    	btnMakeDouble.setBorder(BorderFactory.createEtchedBorder(0));
    	btnMakeDouble.addActionListener(this);
    	pnlButtons.add(btnMakeDouble, c1);
    	c1.gridy = 0;
    	c1.gridx++;
    	
		c1.gridheight=2;
		c1.gridwidth=2;
		
		btnClear = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.ClearIt")));
    	btnClear.setActionCommand("clearIt");
    	btnClear.setPreferredSize(squareLarge);
    	btnClear.setBorder(BorderFactory.createEtchedBorder(0));
    	btnClear.addActionListener(this);
    	pnlButtons.add(btnClear, c1);
    	c1.gridx += 2;
    	
    	btnInverse = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Inverse")));
    	btnInverse.setActionCommand("inverse");
    	btnInverse.setPreferredSize(squareLarge);
    	btnInverse.setBorder(BorderFactory.createEtchedBorder(0));
    	btnInverse.addActionListener(this);
    	pnlButtons.add(btnInverse, c1);
    	c1.gridx += 2;
    	
    	btnConjugate = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Conjugate")));
    	btnConjugate.setActionCommand("conjugate");
    	btnConjugate.setPreferredSize(squareLarge);
    	btnConjugate.setBorder(BorderFactory.createEtchedBorder(0));
    	btnConjugate.addActionListener(this);
    	pnlButtons.add(btnConjugate, c1);
		
	}

	private void createDisplaysLayout()
	{
		pnlDisplays = new JPanel();
		pnlDisplays.setBackground(clrBackColor);
		pnlDisplays.setLayout(new GridBagLayout());
	
	 	GridBagConstraints c2 = new GridBagConstraints();
    	c2.insets = new Insets(0, 0, 0, 0);
    	c2.fill=GridBagConstraints.BOTH;
    	c2.anchor=GridBagConstraints.EAST;
    	
    	c2.gridx = 0;
    	c2.gridy = 0;
    	c2.weightx=1;
    	c2.weighty=1;
    	
    	
    	//pnlDisplays.add(new JLabel("name", SwingConstants.CENTER), c2);
    	//c2.gridx++;
    	
    	switch (_repMode)
    	{
    		case REALF:	for (short m=0; m<1; m++)
						{
							pnlDisplays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
							c2.gridy++;
						}
    					btnMakeComplex.setEnabled(true);
    					btnMakeReal.setEnabled(false);
    					break;
    		case REALD:	for (short m=0; m<1; m++)
						{
    						pnlDisplays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
							c2.gridy++;
						}
						btnMakeComplex.setEnabled(true);
						btnMakeReal.setEnabled(false);
						break;
    		case COMPLEXF:	for (short m=0; m<2; m++)
							{
    							pnlDisplays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
							  		c2.gridy++;
							}
							btnMakeComplex.setEnabled(false);
							btnMakeReal.setEnabled(true);
							break;
    		case COMPLEXD:	for (short m=0; m<2; m++)
							{
    							pnlDisplays.add(new JLabel(_valLabels[m], SwingConstants.CENTER), c2);
							  		c2.gridy++;
							}
							btnMakeComplex.setEnabled(false);
							btnMakeReal.setEnabled(true);
    	}

    	c2.gridy=0;
    	c2.gridx++;
		
		int m;
		JTextField tSpot;
		switch (_repMode)
		{
			case REALF: 	valDisplays= new ArrayList<JTextField>(1);
							for (m=0; m<1; m++)
							{
								tSpot = new JTextField();
								tSpot.setColumns(FieldPanel._FLOATSIZE);
					    		tSpot.setFont(new Font(Font.SERIF, Font.PLAIN, FONTSIZE));
					    		tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					    		tSpot.addFocusListener(this);
					    		valDisplays.add(m, tSpot);
					    		pnlDisplays.add(tSpot, c2);
					    		c2.gridy++;
							}
							btnMakeFloat.setEnabled(false);
							btnMakeDouble.setEnabled(true);
							break;
			case REALD: 	valDisplays= new ArrayList<JTextField>(1);
							for (m=0; m<1; m++)
							{
								tSpot = new JTextField();
								tSpot.setColumns(FieldPanel._DOUBLESIZE);
					    		tSpot.setFont(new Font(Font.SERIF, Font.PLAIN, FONTSIZE));
					    		tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					    		tSpot.addFocusListener(this);
					    		valDisplays.add(m, tSpot);
					    		pnlDisplays.add(tSpot, c2);
					    		c2.gridy++;
							}
							btnMakeFloat.setEnabled(true);
							btnMakeDouble.setEnabled(false);
							break;
			case COMPLEXF:	valDisplays= new ArrayList<JTextField>(2);
							for (m=0; m<2; m++)
							{
								tSpot = new JTextField();
								tSpot.setColumns(FieldPanel._FLOATSIZE);
					    		tSpot.setFont(new Font(Font.SERIF, Font.PLAIN, FONTSIZE));
					    		tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					    		tSpot.addFocusListener(this);
					    		valDisplays.add(m, tSpot);
					    		pnlDisplays.add(tSpot, c2);
					    		c2.gridy++;
							}
							btnMakeFloat.setEnabled(false);
							btnMakeDouble.setEnabled(true);
							break;
			case COMPLEXD:	valDisplays= new ArrayList<JTextField>(2);
							for (m=0; m<2; m++)
							{
								tSpot = new JTextField();
								tSpot.setColumns(FieldPanel._DOUBLESIZE);
					    		tSpot.setFont(new Font(Font.SERIF, Font.PLAIN, FONTSIZE));
					    		tSpot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					    		tSpot.addFocusListener(this);
					    		valDisplays.add(m, tSpot);
					    		pnlDisplays.add(tSpot, c2);
					    		c2.gridy++;
							}
							btnMakeFloat.setEnabled(true);
							btnMakeDouble.setEnabled(false);
		}
		
	}
	/**
     * This clearing function wipes the slate within the Field panel. No represented Fields should remain.
     */
	protected void clearFieldType()
	{
	    _repRealF = null;
	    _repRealD = null;
	    _repComplexF = null;
	    _repComplexD = null;
	}
	
    protected void 	makeNotWritable()
    {
    	//fieldDisplay.setEditable(false);
    	if (valDisplays != null)
    		for (JTextField point : valDisplays)
    			point.setEditable(false);
    }
    protected void 	makeWritable()
    {
    	//fieldDisplay.setEditable(true);
    	if (valDisplays != null)
    		for (JTextField point : valDisplays)
    			point.setEditable(true);
    }
    /**
     * This 'set' function simply adjusts the displayed complex number by accepting an input
     * and then pushing those parts to values stored in the panel. 
     * @param pIn
     * 	ComplexD
     * When resetting the complex number to be displayed, feed the ComplexF in with this parameter.
     */
    protected void 	setCoefficientDisplay(ComplexD pIn)
    {
    	if (_repMode == CladosField.COMPLEXD & pIn != null)
    	{	
    		setRealText(Double.valueOf(pIn.getReal()).toString());
    		setImgText(Double.valueOf(pIn.getImg()).toString());
    		if (pIn.getCardinal() != null)
		    	setBackground(clrBackColor);
		    else
		    	setBackground(clrNullColor);
    	}
    }
    /**
     * This 'set' function simply adjusts the displayed complex number by accepting an input
     * and then pushing those parts to values stored in the panel. 
     * @param pIn
     * 	ComplexF
     * When resetting the complex number to be displayed, feed the ComplexF in with this parameter.
     */
    protected void 	setCoefficientDisplay(ComplexF pIn)
    {	
    	if (_repMode == CladosField.COMPLEXF & pIn != null)
    	{	
    		setRealText(Float.valueOf(pIn.getReal()).toString());
    		setImgText(Float.valueOf(pIn.getImg()).toString());
    		if (pIn.getCardinal() != null)
		    	setBackground(clrBackColor);
		    else
		    	setBackground(clrNullColor);
    	}
    }
    /**
     * This 'set' function simply adjusts the displayed complex number by accepting an input
     * and then pushing those part(s) to values stored in the panel. 
     * @param pIn
     * 	RealD
     * When resetting the real number to be displayed, feed the RealF in with this parameter.
     */
    protected void 	setCoefficientDisplay(RealD pIn)
    {	
    	if (_repMode == CladosField.REALD & pIn != null)
    	{	
    		setRealText(Double.valueOf(pIn.getReal()).toString());
    		if (pIn.getCardinal() != null)
		    	setBackground(clrBackColor);
		    else
		    	setBackground(clrNullColor);
    	}
    }
    /**
     * This 'set' function simply adjusts the displayed complex number by accepting an input
     * and then pushing those part(s) to values stored in the panel. 
     * @param pIn
     * 	RealF
     * When resetting the real number to be displayed, feed the RealF in with this parameter.
     */
    protected void 	setCoefficientDisplay(RealF pIn)
    {
    	if (_repMode == CladosField.REALF & pIn != null)
    	{	
    		setRealText(Float.valueOf(pIn.getReal()).toString());
    		if (pIn.getCardinal() != null)
		    	setBackground(clrBackColor);
		    else
		    	setBackground(clrNullColor);
    	}
    }
	/**
     * This 'set' function simply accepts a DivField used as context for the division field being displayed.
     * <p>
     * @param pField
     * 	ComplexD
     * Provide the cladosF DivField here so reference match requirements are met on later function calls.
     */
    protected void setField(ComplexD pField)
	{
    	if (_repMode == CladosField.COMPLEXD & pField != null)
    	{
		    _repComplexD = pField;
		    if (pField.getCardinal() != null)
		    	setBackground(clrBackColor);
		    else
		    	setBackground(clrNullColor);
    	}
	}
	/**
     * This 'set' function simply accepts a DivField used as context for the division field being displayed.
     * <p>
     * @param pField
     * 	ComplexF
     * Provide the cladosF DivField here so reference match requirements are met on later function calls.
     */
    protected void setField(ComplexF pField)
	{
    	if (_repMode == CladosField.COMPLEXF & pField != null)
    	{
    		_repComplexF = pField;
    		if (pField.getCardinal() != null)
		    	setBackground(clrBackColor);
		    else
		    	setBackground(clrNullColor);
    	}
	}
    /**
     * This 'set' function simply accepts a DivField used as context for the division field being displayed.
     * <p>
     * @param pField
     * 	RealD
     * Provide the cladosF DivField here so reference match requirements are met on later function calls.
     */
    protected void setField(RealD pField)
	{
    	if (_repMode == CladosField.REALD & pField != null)
    	{
    		_repRealD = pField;
    		if (pField.getCardinal() != null)
		    	setBackground(clrBackColor);
		    else
		    	setBackground(clrNullColor);
    	}
	}
    /**
     * This 'set' function simply accepts a DivField used as context for the division field being displayed.
     * <p>
     * @param pField
     * 	RealF
     * Provide the cladosF DivField here so reference match requirements are met on later function calls.
     */
    protected void setField(RealF pField)
	{
    	if (_repMode == CladosField.REALF & pField != null)
    	{
    		_repRealF = pField;
		    if (pField.getCardinal() != null)
		    	setBackground(clrBackColor);
		    else
		    	setBackground(clrNullColor);
    	}
	}
}
