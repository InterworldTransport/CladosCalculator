package org.interworldtransport.cladosviewer;

import static org.junit.jupiter.api.Assertions.*;

import org.interworldtransport.cladosF.CladosFBuilder;
import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosF.Field;
import org.interworldtransport.cladosF.Normalizable;
import org.interworldtransport.cladosF.UnitAbstract;
import org.interworldtransport.cladosG.Blade;
import org.interworldtransport.cladosG.CladosGBuilder;
import org.interworldtransport.cladosG.Monad;
import org.interworldtransport.cladosGExceptions.BadSignatureException;
import org.interworldtransport.cladosGExceptions.CladosMonadException;
import org.interworldtransport.cladosGExceptions.GeneratorRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MonadPanelTest {

	Monad targetRF;
	Monad targetRD;
	Monad targetCF;
	Monad targetCD;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public <T extends UnitAbstract & Field & Normalizable> void setUp()
			throws BadSignatureException, CladosMonadException, GeneratorRangeException {
		targetRF = CladosGBuilder.createMonadZero((T) CladosFBuilder.REALF.createZERO("card1"), "monad1 name",
				"algebra1 name", "frame name", "foot name", "-+++");
		targetRD = CladosGBuilder.createMonadZero((T) CladosFBuilder.REALD.createZERO("card2"), "monad2 name",
				"algebra2 name", "frame name", "foot name", "-+++");
		targetCF = CladosGBuilder.createMonadZero((T) CladosFBuilder.COMPLEXF.createZERO("card3"), "monad3 name",
				"algebra3 name", "frame name", "foot name", "-+++");
		targetCD = CladosGBuilder.createMonadZero((T) CladosFBuilder.COMPLEXD.createZERO("card4"), "monad4 name",
				"algebra4 name", "frame name", "foot name", "-+++");
	}

	@Test
	public <T extends UnitAbstract & Field & Normalizable> void testRealF() {
		MonadPanel<T> testPanel = new MonadPanel<>(null, targetRF);
		assertNotNull(testPanel);
		assertEquals(testPanel.getRepMode(), CladosField.REALF);
		assertEquals(testPanel.getJCoeffs().keySet().size(), 16);
		assertEquals(testPanel.getJCoeffs().values().size(), 16);
		Monad retrieve = testPanel.getMonad();
		assertTrue(targetRF == retrieve); // Literally the same monad

		assertEquals(testPanel.gradeKey.getText(), "1");

		Blade tSBlade = testPanel.getJCoeffs().firstKey();
		Blade tPSBlade = testPanel.getJCoeffs().lastKey();
		assertTrue(Blade.isScalar(tSBlade));
		assertTrue(Blade.isPScalar(tPSBlade));
		FieldDisplay<T> tSDisplay = testPanel.getJCoeffs().get(tSBlade);
		FieldDisplay<T> tPSDisplay = testPanel.getJCoeffs().get(tPSBlade);

		testPanel.btnEdit.doClick();
		tSDisplay.setText("[R]1.0");
		tPSDisplay.setText("[R]1.0");
		testPanel.btnSync.doClick();
		assertEquals(testPanel.gradeKey.getText(), "10001");

		testPanel.btnEdit.doClick();
		tSDisplay.setText("[R]0.0");
		testPanel.btnSync.doClick();
		assertEquals(testPanel.gradeKey.getText(), "10000");

		tPSDisplay.setText("[R]0.0"); // not in edit mode, so should fail quietly
		assertEquals(testPanel.gradeKey.getText(), "10000");
	}

	@Test
	public <T extends UnitAbstract & Field & Normalizable> void testRealD() {
		MonadPanel<T> testPanel = new MonadPanel<>(null, targetRD);
		assertNotNull(testPanel);
		assertEquals(testPanel.getRepMode(), CladosField.REALD);
		assertEquals(testPanel.getJCoeffs().keySet().size(), 16);
		assertEquals(testPanel.getJCoeffs().values().size(), 16);
		Monad retrieve = testPanel.getMonad();
		assertTrue(targetRD == retrieve); // Literally the same monad

		assertEquals(testPanel.gradeKey.getText(), "1");

		Blade tSBlade = testPanel.getJCoeffs().firstKey();
		Blade tPSBlade = testPanel.getJCoeffs().lastKey();
		assertTrue(Blade.isScalar(tSBlade));
		assertTrue(Blade.isPScalar(tPSBlade));
		FieldDisplay<T> tSDisplay = testPanel.getJCoeffs().get(tSBlade);
		FieldDisplay<T> tPSDisplay = testPanel.getJCoeffs().get(tPSBlade);

		testPanel.btnEdit.doClick();
		tSDisplay.setText("[R]1.0");
		tPSDisplay.setText("[R]1.0");
		testPanel.btnSync.doClick();
		assertEquals(testPanel.gradeKey.getText(), "10001");

		testPanel.btnEdit.doClick();
		tSDisplay.setText("[R]0.0");
		testPanel.btnSync.doClick();
		assertEquals(testPanel.gradeKey.getText(), "10000");

		tPSDisplay.setText("[R]0.0"); // not in edit mode, so should fail quietly
		assertEquals(testPanel.gradeKey.getText(), "10000");
	}

	@Test
	public <T extends UnitAbstract & Field & Normalizable> void testEditMode() {
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
