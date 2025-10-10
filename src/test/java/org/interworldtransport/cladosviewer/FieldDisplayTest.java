package org.interworldtransport.cladosviewer;

import static org.junit.jupiter.api.Assertions.*;

import org.interworldtransport.cladosF.FBuilder;
import org.interworldtransport.cladosF.ComplexD;
import org.interworldtransport.cladosF.ComplexF;
import org.interworldtransport.cladosF.RealD;
import org.interworldtransport.cladosF.RealF;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldDisplayTest {
	
	private FieldDisplay<?> testDisplay;

	@Test
	public void testRealF() {
		testDisplay = new FieldDisplay<>((RealF) FBuilder.REALF.createONE("My cardinal"), null);
		assertNotNull(testDisplay);
		assertEquals(testDisplay.getRows(), 1);
		assertEquals(testDisplay.getColumns(), 10);
		testDisplay.displayContents();
		assertEquals(testDisplay.getText(), "[R]1.0");
		testDisplay.setText("[R]10.0049");
		testDisplay.saveContents();
		float realPart = ((RealF) testDisplay.fieldCopy).getReal();
		assertEquals(Math.round(realPart*1000), 10005);
		RealF newThing = (RealF) FBuilder.REALF.createZERO(testDisplay.fieldCopy.getCardinal());
		testDisplay.updateField(newThing);
		realPart =  ((RealF) testDisplay.fieldCopy).getReal();
		assertEquals(realPart, 0.0f);
	}
	
	@Test
	public void testRealD() {
		testDisplay = new FieldDisplay<>((RealD) FBuilder.REALD.createONE("My cardinal"), null);
		assertNotNull(testDisplay);
		assertEquals(testDisplay.getRows(), 1);
		assertEquals(testDisplay.getColumns(), 16);
		testDisplay.displayContents();
		assertEquals(testDisplay.getText(), "[R]1.0");
		testDisplay.setText("[R]10.0049");
		testDisplay.saveContents();
		double realPart = ((RealD) testDisplay.fieldCopy).getReal();
		assertEquals(Math.round(realPart*1000), 10005);
		RealD newThing = (RealD) FBuilder.REALD.createZERO(testDisplay.fieldCopy.getCardinal());
		testDisplay.updateField(newThing);
		realPart =  ((RealD) testDisplay.fieldCopy).getReal();
		assertEquals(realPart, 0.0d);
	}
	
	@Test
	public void testComplexF() {
		testDisplay = new FieldDisplay<>((ComplexF) FBuilder.COMPLEXF.createONE("My cardinal"), null);
		assertNotNull(testDisplay);
		assertEquals(testDisplay.getRows(), 2);
		assertEquals(testDisplay.getColumns(), 10);
		testDisplay.displayContents();
		assertEquals(testDisplay.getText(), "[R]1.0\n[I]0.0");
		testDisplay.setText("[R]10.0049\n[I]1.0");
		testDisplay.saveContents();
		float realPart = ((ComplexF) testDisplay.fieldCopy).getReal();
		float imgPart = ((ComplexF) testDisplay.fieldCopy).getImg();
		assertEquals(Math.round(realPart*1000), 10005);
		assertEquals(imgPart, 1.0f);
		ComplexF newThing = (ComplexF) FBuilder.COMPLEXF.createZERO(testDisplay.fieldCopy.getCardinal());
		testDisplay.updateField(newThing);
		realPart =  ((ComplexF) testDisplay.fieldCopy).getReal();
		assertEquals(realPart, 0.0f);
	}
	
	@Test
	public void testComplexD() {
		testDisplay = new FieldDisplay<>((ComplexD) FBuilder.COMPLEXD.createONE("My cardinal"), null);
		assertNotNull(testDisplay);
		assertEquals(testDisplay.getRows(), 2);
		assertEquals(testDisplay.getColumns(), 16);
		testDisplay.displayContents();
		assertEquals(testDisplay.getText(), "[R]1.0\n[I]0.0");
		testDisplay.setText("[R]10.0049\n[I]1.0");
		testDisplay.saveContents();
		double realPart = ((ComplexD) testDisplay.fieldCopy).getReal();
		double imgPart = ((ComplexD) testDisplay.fieldCopy).getImg();
		assertEquals(Math.round(realPart*1000), 10005);
		assertEquals(imgPart, 1.0f);
		ComplexD newThing = (ComplexD) FBuilder.COMPLEXD.createZERO(testDisplay.fieldCopy.getCardinal());
		testDisplay.updateField(newThing);
		realPart =  ((ComplexD) testDisplay.fieldCopy).getReal();
		assertEquals(realPart, 0.0f);
	}

}
