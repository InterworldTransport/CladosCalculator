package org.interworldtransport.cladosviewer;

import static org.junit.jupiter.api.Assertions.*;

import org.interworldtransport.cladosF.FBuilder;
import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosF.Field;
import org.interworldtransport.cladosF.Normalizable;
import org.interworldtransport.cladosF.ProtoN;
import org.interworldtransport.cladosF.RealF;
//import org.interworldtransport.cladosF.RealD;
//import org.interworldtransport.cladosF.ComplexF;
//import org.interworldtransport.cladosF.ComplexD;
import org.interworldtransport.cladosG.Blade;
import org.interworldtransport.cladosG.GBuilder;
import org.interworldtransport.cladosG.Monad;
import org.interworldtransport.cladosGExceptions.BadSignatureException;
import org.interworldtransport.cladosGExceptions.CladosMonadException;
import org.interworldtransport.cladosGExceptions.GeneratorRangeException;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MonadPanelTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testSetEditSyncRealF() throws BadSignatureException, CladosMonadException, GeneratorRangeException {
		Monad targetRF = GBuilder.createMonadZero(FBuilder.REALF.createZERO("card1"), "monad1 name",
					"algebra1 name", "frame name", "foot name", "-+++");
		MonadPanel<?> testPanelRF = new MonadPanel<>(null, targetRF);
		assertNotNull(testPanelRF);
		assertEquals(testPanelRF.getRepMode(), CladosField.REALF);
		assertEquals(testPanelRF.getJCoeffs().keySet().size(), 16);
		assertEquals(testPanelRF.getJCoeffs().values().size(), 16);
		Monad retrieve = testPanelRF.getMonad();
		assertTrue(targetRF == retrieve); // Literally the same monad
		assertEquals("1", testPanelRF.gradeKey.getText());

		Blade tSBlade = testPanelRF.getJCoeffs().firstKey();
		Blade tPSBlade = testPanelRF.getJCoeffs().lastKey();
		assertTrue(Blade.isScalar(tSBlade));
		assertTrue(Blade.isPScalar(tPSBlade));
		
		FieldDisplay<RealF> tSDisplayRF = (FieldDisplay<RealF>) testPanelRF.getJCoeffs().get(tSBlade);
		FieldDisplay<RealF> tPSDisplayRF = (FieldDisplay<RealF>) testPanelRF.getJCoeffs().get(tPSBlade);

		assertTrue(testPanelRF.btnEdit.getActionCommand()=="edit");
		testPanelRF.btnEdit.doClick();
		assertTrue(testPanelRF.btnEdit.getActionCommand()==".edit.");

		tSDisplayRF.setText("[R]1.0");
		tPSDisplayRF.setText("[R]1.0");

		assertTrue(testPanelRF.btnSync.getActionCommand()=="save");
		testPanelRF.btnSync.doClick();
		assertTrue(testPanelRF.btnSync.getActionCommand()=="save");
		//System.out.println("GradeKeyInternal: "+testPanelRF.getMonad().getGradeKey());
		//System.out.println("GradeKeyPanel   : "+testPanelRF.gradeKey.getText());

		assertEquals("10001", testPanelRF.gradeKey.getText());

		testPanelRF.btnEdit.doClick();
		tSDisplayRF.setText("[R]0.0");
		testPanelRF.btnSync.doClick();
		assertEquals("10000", testPanelRF.gradeKey.getText());

		tPSDisplayRF.setText("[R]0.0"); // not in edit mode, so should fail quietly
		assertEquals("10000", testPanelRF.gradeKey.getText());
	}

	@Test
	public void testSetEditSyncRealD() throws BadSignatureException, CladosMonadException, GeneratorRangeException {
		Monad targetRD = GBuilder.createMonadZero(FBuilder.REALD.createZERO("card2"), "monad2 name",
				"algebra2 name", "frame name", "foot name", "-+++");
		MonadPanel<?> testPanelRD = new MonadPanel<>(null, targetRD);
		assertNotNull(testPanelRD);
		assertEquals(testPanelRD.getRepMode(), CladosField.REALD);
		assertEquals(testPanelRD.getJCoeffs().keySet().size(), 16);
		assertEquals(testPanelRD.getJCoeffs().values().size(), 16);
		Monad retrieve = testPanelRD.getMonad();
		assertTrue(targetRD == retrieve); // Literally the same monad

		assertEquals("1", testPanelRD.gradeKey.getText());

		Blade tSBlade = testPanelRD.getJCoeffs().firstKey();
		Blade tPSBlade = testPanelRD.getJCoeffs().lastKey();
		assertTrue(Blade.isScalar(tSBlade));
		assertTrue(Blade.isPScalar(tPSBlade));
		FieldDisplay<?> tSDisplay = testPanelRD.getJCoeffs().get(tSBlade);
		FieldDisplay<?> tPSDisplay = testPanelRD.getJCoeffs().get(tPSBlade);

		testPanelRD.btnEdit.doClick();
		tSDisplay.setText("[R]1.0");
		tPSDisplay.setText("[R]1.0");
		testPanelRD.btnSync.doClick();
		assertEquals("10001", testPanelRD.gradeKey.getText());

		testPanelRD.btnEdit.doClick();
		tSDisplay.setText("[R]0.0");
		testPanelRD.btnSync.doClick();
		assertEquals("10000", testPanelRD.gradeKey.getText());

		tPSDisplay.setText("[R]0.0"); // not in edit mode, so should fail quietly
		assertEquals("10000", testPanelRD.gradeKey.getText());
	}

	@Test
	public void testComplexF() throws BadSignatureException, CladosMonadException, GeneratorRangeException {
		Monad targetCF = GBuilder.createMonadZero(FBuilder.COMPLEXF.createZERO("card3"), "monad3 name",
				"algebra3 name", "frame name", "foot name", "-+++");
		MonadPanel<?> testPanelCF = new MonadPanel<>(null, targetCF);
		assertNotNull(testPanelCF);
		assertEquals(testPanelCF.getRepMode(), CladosField.COMPLEXF);
		assertEquals(testPanelCF.getJCoeffs().keySet().size(), 16);
		assertEquals(testPanelCF.getJCoeffs().values().size(), 16);
		Monad retrieve = testPanelCF.getMonad();
		assertTrue(targetCF == retrieve); // Literally the same monad

		assertEquals(testPanelCF.gradeKey.getText(), "1");

		Blade tSBlade = testPanelCF.getJCoeffs().firstKey();
		Blade tPSBlade = testPanelCF.getJCoeffs().lastKey();
		assertTrue(Blade.isScalar(tSBlade));
		assertTrue(Blade.isPScalar(tPSBlade));
		FieldDisplay<?> tSDisplay = testPanelCF.getJCoeffs().get(tSBlade);
		FieldDisplay<?> tPSDisplay = testPanelCF.getJCoeffs().get(tPSBlade);

		testPanelCF.btnEdit.doClick();
		tSDisplay.setText("[R]1.0\n[I]0.0");
		tPSDisplay.setText("[R]1.0\n[I]0.0");

		testPanelCF.btnSync.doClick();
		assertEquals("10001", testPanelCF.gradeKey.getText());

		testPanelCF.btnEdit.doClick();
		tSDisplay.setText("[R]0.0\n[I]0.0");
		testPanelCF.btnSync.doClick();
		assertEquals("10000", testPanelCF.gradeKey.getText());

		tPSDisplay.setText("[R]0.0\n[I]0.0"); // not in edit mode, so should fail quietly
		assertEquals("10000", testPanelCF.gradeKey.getText());
	}

	@Test
	public void testComplexD() throws BadSignatureException, CladosMonadException, GeneratorRangeException {
		Monad targetCD = GBuilder.createMonadZero(FBuilder.COMPLEXD.createZERO("card4"), "monad4 name",
				"algebra4 name", "frame name", "foot name", "-+++");
		MonadPanel<?> testPanelCD = new MonadPanel<>(null, targetCD);
		assertNotNull(testPanelCD);
		assertEquals(testPanelCD.getRepMode(), CladosField.COMPLEXD);
		assertEquals(testPanelCD.getJCoeffs().keySet().size(), 16);
		assertEquals(testPanelCD.getJCoeffs().values().size(), 16);
		Monad retrieve = testPanelCD.getMonad();
		assertTrue(targetCD == retrieve); // Literally the same monad

		assertEquals(testPanelCD.gradeKey.getText(), "1");

		Blade tSBlade = testPanelCD.getJCoeffs().firstKey();
		Blade tPSBlade = testPanelCD.getJCoeffs().lastKey();
		assertTrue(Blade.isScalar(tSBlade));
		assertTrue(Blade.isPScalar(tPSBlade));
		FieldDisplay<?> tSDisplay = testPanelCD.getJCoeffs().get(tSBlade);
		FieldDisplay<?> tPSDisplay = testPanelCD.getJCoeffs().get(tPSBlade);

		testPanelCD.btnEdit.doClick();
		tSDisplay.setText("[R]1.0\n[I]0.0");
		tPSDisplay.setText("[R]1.0\n[I]0.0");
		testPanelCD.btnSync.doClick();
		assertEquals("10001", testPanelCD.gradeKey.getText());

		testPanelCD.btnEdit.doClick();
		tSDisplay.setText("[R]0.0\n[I]0.0");
		testPanelCD.btnSync.doClick();
		assertEquals("10000", testPanelCD.gradeKey.getText());

		tPSDisplay.setText("[R]0.0\n[I]0.0"); // not in edit mode, so should fail quietly
		assertEquals("10000", testPanelCD.gradeKey.getText());
	}

	@Test
	public <T extends ProtoN & Field & Normalizable> void testEditMode() throws BadSignatureException, CladosMonadException, GeneratorRangeException {
		Monad targetRF = GBuilder.createMonadZero(FBuilder.REALF.createZERO("card1"), "monad1 name",
					"algebra1 name", "frame name", "foot name", "-+++");
		MonadPanel<T> testPanel = new MonadPanel<>(null, targetRF);
		assertNotNull(testPanel);

		testPanel.btnEdit.doClick();
		assertTrue(testPanel._editMode);
		testPanel.btnRestore.doClick();
		assertFalse(testPanel._editMode);
		testPanel.btnEdit.doClick();
		assertTrue(testPanel._editMode);
		testPanel.btnSync.doClick();
		assertFalse(testPanel._editMode);
	}
}
