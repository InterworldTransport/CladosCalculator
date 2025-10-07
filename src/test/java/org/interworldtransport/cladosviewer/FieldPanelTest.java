package org.interworldtransport.cladosviewer;

import static org.junit.jupiter.api.Assertions.*;

import org.interworldtransport.cladosF.FBuilder;
import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosF.ComplexD;
import org.interworldtransport.cladosF.ComplexF;
import org.interworldtransport.cladosF.RealD;
import org.interworldtransport.cladosF.RealF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldPanelTest {

	private FieldPanel<?> testDisplay;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testRealF() {
		RealF tInput = (RealF) FBuilder.REALF.createONE("My cardinal");
		testDisplay = new FieldPanel<>(null, tInput);
		assertNotNull(testDisplay);
		assertTrue(testDisplay.getRepMode() == CladosField.REALF);
		
		tInput.setReal(10.0049f);
		testDisplay.setCoefficientDisplay(tInput);
		RealF tBack = (RealF) testDisplay.repNumber;
		assertEquals(Math.round(tBack.getReal()*1000), 10005);
		
		testDisplay.btnMakeDouble.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.REALD);
		testDisplay.btnMakeComplex.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.COMPLEXD);
		testDisplay.btnMakeFloat.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.COMPLEXF);
	}

	@Test
	void testRealD() {
		RealD tInput = (RealD) FBuilder.REALD.createONE("My cardinal");
		testDisplay = new FieldPanel<>(null, tInput);
		assertNotNull(testDisplay);
		assertTrue(testDisplay.getRepMode() == CladosField.REALD);
		
		tInput.setReal(10.0049d);
		testDisplay.setCoefficientDisplay(tInput);
		RealD tBack = (RealD) testDisplay.repNumber;
		assertEquals(Math.round(tBack.getReal()*1000), 10005);
		
		testDisplay.btnMakeFloat.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.REALF);
		testDisplay.btnMakeComplex.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.COMPLEXF);
		testDisplay.btnMakeDouble.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.COMPLEXD);
	}
	
	@Test
	void testComplexF() {
		ComplexF tInput = (ComplexF) FBuilder.COMPLEXF.createONE("My cardinal");
		testDisplay = new FieldPanel<>(null, tInput);
		assertNotNull(testDisplay);
		assertTrue(testDisplay.getRepMode() == CladosField.COMPLEXF);
		
		tInput.setReal(10.0049f);
		tInput.setImg(1.0f);
		testDisplay.setCoefficientDisplay(tInput);
		ComplexF tBack = (ComplexF) testDisplay.repNumber;
		assertEquals(Math.round(tBack.getReal()*1000), 10005);
		assertEquals(tBack.getImg(), 1.0f);
		
		testDisplay.btnMakeDouble.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.COMPLEXD);
		testDisplay.btnMakeReal.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.REALD);
		testDisplay.btnMakeFloat.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.REALF);
	}
	
	@Test
	void testComplexD() {
		ComplexD tInput = (ComplexD) FBuilder.COMPLEXD.createONE("My cardinal");
		testDisplay = new FieldPanel<>(null, tInput);
		assertNotNull(testDisplay);
		assertTrue(testDisplay.getRepMode() == CladosField.COMPLEXD);
		
		tInput.setReal(10.0049d);
		tInput.setImg(1.0d);
		testDisplay.setCoefficientDisplay(tInput);
		ComplexD tBack = (ComplexD) testDisplay.repNumber;
		assertEquals(Math.round(tBack.getReal()*1000), 10005);
		assertEquals(tBack.getImg(), 1.0d);
		
		testDisplay.btnMakeFloat.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.COMPLEXF);
		testDisplay.btnMakeReal.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.REALF);
		testDisplay.btnMakeDouble.doClick();
		assertTrue(testDisplay.getRepMode() == CladosField.REALD);
	}
}
