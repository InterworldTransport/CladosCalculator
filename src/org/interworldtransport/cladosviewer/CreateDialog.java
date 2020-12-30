/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.CreateDialog<br>
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
 * ---org.interworldtransport.cladosviewer.CreateDialog<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewer;

import org.interworldtransport.cladosF.CladosFListBuilder;
import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosF.ComplexD;
import org.interworldtransport.cladosF.ComplexF;
import org.interworldtransport.cladosF.UnitAbstract;
import org.interworldtransport.cladosG.Algebra;
import org.interworldtransport.cladosG.CladosGBuilder;
import org.interworldtransport.cladosG.Monad;
import org.interworldtransport.cladosG.Nyad;
import org.interworldtransport.cladosGExceptions.BadSignatureException;
import org.interworldtransport.cladosGExceptions.CladosMonadException;
import org.interworldtransport.cladosGExceptions.CladosNyadException;
import org.interworldtransport.cladosGExceptions.GeneratorRangeException;
import org.interworldtransport.cladosviewerExceptions.UtilitiesException;
import org.interworldtransport.cladosG.Foot;
import org.interworldtransport.cladosF.RealD;
import org.interworldtransport.cladosF.RealF;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

/**
 * org.interworldtransport.cladosviewer.CreateDialog The Create Dialog window is
 * supposed to show a window that would allow a user to add a new Monad to the
 * stack in the Monad Viewer.
 * 
 * @version 0.86
 * @author Dr Alfred W Differ
 */
public class CreateDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -7986167685794915609L;
	private static final Font _ITALICFONT = new Font(Font.SERIF, Font.ITALIC, 10);
	private final static Color _monadColor = new Color(212, 212, 192);
	private final static Color _nyadColor = new Color(212, 200, 212);
	private static final Font _PLAINFONT = new Font(Font.SERIF, Font.PLAIN, 10);
	private final static Dimension square = new Dimension(30, 30);

	/**
	 * This is a factory method for creating a new monad to add to the selected
	 * nyad's stack
	 * 
	 * @param pGUI  CladosCalculator This parameter references the owning
	 *              application. Nothing spectacular.
	 * @param pMode String This string holds the representation model of the calling
	 *              widget. It will be a DivField static string.
	 * @return CreateDialog This method returns a CreateDialog instance The point of
	 *         this being static is to enable making the regular constructor private
	 *         later.
	 */
	public static final CreateDialog createMonad(CladosCalculator pGUI, CladosField pMode) {
		CreateDialog tCD = null;
		try {
			tCD = new CreateDialog(pGUI, false, pMode);
		} catch (UtilitiesException e) {
			ErrorDialog.show("Couldn't construct create dialog.", "Utilities Exception");
		} catch (BadSignatureException es) {
			ErrorDialog.show("Couldn't construct create dialog.", "Bad Signatures Exception");
		} catch (CladosMonadException em) {
			ErrorDialog.show("Couldn't construct create dialog.", "Clados Monad Exception");
		}
		return tCD;
	}

	/**
	 * This is a factory method for creating a new nyad to add to the stack
	 * 
	 * @param pGUI  CladosCalculator This parameter references the owning
	 *              application. Nothing spectacular.
	 * @param pMode String This string holds the representation model of the calling
	 *              widget. It will be a DivField static string.
	 * @return CreateDialog This method returns a CreateDialog instance The point of
	 *         this being static is to enable making the regular constructor private
	 *         later.
	 */
	public static final CreateDialog createNyad(CladosCalculator pGUI, CladosField pMode) {
		CreateDialog tCD = null;
		try {
			tCD = new CreateDialog(pGUI, true, pMode);
		} catch (UtilitiesException e) {
			ErrorDialog.show("Couldn't construct create dialog.", "Utilities Exception");
		} catch (BadSignatureException es) {
			ErrorDialog.show("Couldn't construct create dialog.", "Bad Signatures Exception");
		} catch (CladosMonadException em) {
			ErrorDialog.show("Couldn't construct create dialog.", "Clados Monad Exception");
		}
		return tCD;
	}

	private CladosCalculator _GUI;
	private CladosField repMode;
	private JButton btnClose;
	private JButton btnGetAlgebra;
	private JButton btnGetFoot;
	private JButton btnSave;
	private Algebra copyAlg;
	private Foot copyFoot;
	private MonadPanel monadShort;

	/**
	 * The constructor sets up the options dialog box and displays it. It will be
	 * made into a private constructor at some point.
	 * 
	 * @param mainWindow CladosCalculator This parameter references the
	 *                   calling/owning application
	 * @param makeNyad   boolean The same dialog is reused to create monads and
	 *                   nyads. We get away with this because at the top/reference
	 *                   level, both classes are similar. This 'create' feature
	 *                   essentially creates a place holder for a zero monad if a
	 *                   monad is created or an order-0 nyad with no monad in it to
	 *                   start.
	 * @param pDivMode   String This string holds the representation model of the
	 *                   calling widget. It will be a DivField static string.
	 * @throws UtilitiesException    This is the general exception. Could be any
	 *                               miscellaneous issue. Ready the message to see.
	 * @throws BadSignatureException This exception is thrown when one of the monad
	 *                               panels can't accept the string signature
	 *                               offered. That happens when something other than
	 *                               '+' or '-' is used... or maybe when signature
	 *                               is too long. Remember that blade count is
	 *                               currently tracked with a short integer. {--****
	 * @throws CladosMonadException  This exception gets thrown when there is a
	 *                               general issue constructing a monad besides the
	 *                               exceptions for which specific ones have been
	 *                               written. Read the contained message.
	 */
	private CreateDialog(CladosCalculator mainWindow, boolean makeNyad, CladosField pDivMode)
			throws UtilitiesException, BadSignatureException, CladosMonadException {
		super(mainWindow, (makeNyad ? "Create Nyad Panel" : "Create Monad Panel"), false);
		_GUI = mainWindow;
		if (pDivMode == null)
			throw new UtilitiesException("CladosField undefined at dialog creation");
		repMode = pDivMode;

		// Create the Dialog's main stage
		JPanel primaryStage = new JPanel(new BorderLayout());
		primaryStage.setBorder(BorderFactory.createTitledBorder("DivField | " + repMode));
		primaryStage.setBackground(makeNyad ? _nyadColor : _monadColor);
		setContentPane(primaryStage);

		// Create Upper button panel
		JPanel dialogGets = new JPanel(new FlowLayout());
		dialogGets.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialogGets.setBackground(makeNyad ? _nyadColor : _monadColor);

		btnGetFoot = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Foot")));
		btnGetFoot.setActionCommand("getfoot");
		btnGetFoot.setToolTipText("Get Referenced Foot");
		btnGetFoot.setPreferredSize(square);
		btnGetFoot.setBorder(BorderFactory.createEtchedBorder(0));

		if (makeNyad)
			btnGetFoot.addActionListener(this);
		else
			btnGetFoot.setEnabled(false);

		dialogGets.add(btnGetFoot);

		btnGetAlgebra = new JButton(new ImageIcon(_GUI.IniProps.getProperty("Desktop.Image.Alg")));
		btnGetAlgebra.setActionCommand("getalg");
		btnGetAlgebra.setPreferredSize(square);
		btnGetAlgebra.setToolTipText("Reference Algebra in selected monad.");
		btnGetAlgebra.setBorder(BorderFactory.createEtchedBorder(0));
		btnGetAlgebra.addActionListener(this);
		dialogGets.add(btnGetAlgebra);
		primaryStage.add(dialogGets, BorderLayout.PAGE_START); // and put it on stage

		// Create monad for center panel
		monadShort = new MonadPanel(_GUI, makeNyad);
		monadShort.makeWritable();
		primaryStage.add(new JScrollPane(monadShort), BorderLayout.CENTER); // and put it on stage

		// Create Lower button panel
		JPanel dialogControls = new JPanel(new FlowLayout());
		dialogControls.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialogControls.setBackground(makeNyad ? _nyadColor : _monadColor);

		btnSave = new JButton(new ImageIcon(this.getClass().getResource("/icons/save.png")));
		btnSave.setActionCommand(makeNyad ? "Save Nyad" : "Save Monad");
		btnSave.setToolTipText(makeNyad ? "Create new nyad. Algebra/Foot or just Foot can be referenced."
				: "Create new monad. Algebra/Foot can be referenced, but nyad Foot better match.");
		btnSave.setPreferredSize(square);
		btnSave.setBorder(BorderFactory.createEtchedBorder(0));
		btnSave.addActionListener(this);
		dialogControls.add(btnSave);

		btnClose = new JButton(new ImageIcon(this.getClass().getResource("/icons/close.png")));
		btnClose.setActionCommand("close");
		btnClose.setToolTipText("Close the dialog. No further changes.");
		btnClose.setPreferredSize(square);
		btnClose.setBorder(BorderFactory.createEtchedBorder(0));
		btnClose.addActionListener(this);
		dialogControls.add(btnClose);
		primaryStage.add(dialogControls, BorderLayout.PAGE_END); // and put it on stage

		// Center the window on the parent window.
		pack();
		Point tP = _GUI.getLocation();
		Rectangle tR = _GUI.getBounds();
		setLocation(tP.x + ((tR.width - getWidth()) / 2), tP.y + ((tR.height - getHeight()) / 2));
		setVisible(true); // Display window
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		int tSpot = 0;
		switch (event.getActionCommand()) {
		case "close" -> dispose();
		case ".getfoot." -> {
			btnGetFoot.setActionCommand("getfoot");
			btnGetFoot.setToolTipText("Get Referenced Foot");
			copyFoot = null;
			monadShort.foot.setEditable(true);
			monadShort.foot.setFont(_ITALICFONT);

			copyAlg = null;
			monadShort.aname.setFont(_ITALICFONT);
			monadShort.aname.setEditable(true);
			monadShort.cardname.setFont(_ITALICFONT);
			monadShort.cardname.setEditable(true);
		}
		case "getfoot" -> {
			if (getNyadPaneFocus() < 0)
				return; // No nyad chosen to get Foot
			btnGetFoot.setActionCommand(".getfoot.");
			btnGetFoot.setToolTipText("Release Referenced Foot");
			copyFoot = getNyadPanelFocus().getNyad().getFoot();
			monadShort.foot.setText(copyFoot.getFootName());
			monadShort.foot.setFont(_PLAINFONT);
			monadShort.foot.setEditable(false);

			copyAlg = null;
			monadShort.aname.setFont(_ITALICFONT);
			monadShort.aname.setEditable(true);
			monadShort.cardname.setEditable(true);
			monadShort.sig.setEditable(true);
		}
		case ".getalg." -> {
			btnGetAlgebra.setActionCommand("getalg");
			btnGetAlgebra.setToolTipText("Get Referenced Algebra");
			copyAlg = null;
			monadShort.aname.setFont(_ITALICFONT);
			monadShort.aname.setEditable(true);
			monadShort.cardname.setFont(_ITALICFONT);
			monadShort.cardname.setEditable(true);
			monadShort.sig.setFont(_ITALICFONT);
			monadShort.sig.setEditable(true);
			monadShort.foot.setFont(_ITALICFONT);
			monadShort.foot.setEditable(true);
		}
		case "getalg" -> {
			if (getNyadPaneFocus() < 0)
				return; // No nyad chosen to get Foot
			btnGetAlgebra.setActionCommand(".getalg.");
			btnGetAlgebra.setToolTipText("Release Referenced Algebra");
			if (getMonadPaneFocus() < 0)
				return; // No monad in the focus to get its algebra
			copyAlg = getMonadPanelFocus().getMonad().getAlgebra();
			monadShort.cardname.setText(copyAlg.getCardinal().getUnit());
			monadShort.aname.setText(copyAlg.getAlgebraName());
			monadShort.foot.setText(copyAlg.getFoot().getFootName());
			monadShort.sig.setText(copyAlg.getGProduct().signature());
			monadShort.cardname.setFont(_PLAINFONT);
			monadShort.cardname.setEditable(false);
			monadShort.aname.setFont(_PLAINFONT);
			monadShort.aname.setEditable(false);
			monadShort.foot.setFont(_PLAINFONT);
			monadShort.foot.setEditable(false);
			monadShort.sig.setFont(_PLAINFONT);
			monadShort.sig.setEditable(false);
		}
		case "Save Nyad" -> {
			try {
				if (copyAlg != null)
					appendNyadUsingAlg(repMode);
				else if (copyFoot != null)
					appendNyadUsingFoot(repMode);
				else
					appendNyad(repMode);
			} catch (BadSignatureException es) {
				ErrorDialog.show("Could not save new nyad./n" + es.getSourceMessage(), "Bad Signature Exception.");
			} catch (GeneratorRangeException e) {
				ErrorDialog.show("Could not save new nyad./n" + e.getSourceMessage(), "Generator Range Exception.");
			} catch (CladosMonadException em) {
				ErrorDialog.show("Could not save new nyad./n" + em.getSourceMessage(), "Clados Monad Exception.");
			} catch (CladosNyadException en) {
				ErrorDialog.show("Could not save new nyad./n" + en.getSourceMessage(), "Clados Nyad Exception.");
			}
		}
		case "Save Monad" -> {
			tSpot = getNyadPaneFocus(); // get the focus nyad to use to create this next monad
			if (tSpot < 0)
				return; // No nyad present for appending this
			NyadPanel tPots = _GUI.appGeometryView.getNyadPanel(tSpot);
			try {
				if (copyAlg != null) {
					appendMonadUsingAlg(tPots, tPots.getNyad());
				} else
					appendMonad(tPots, tPots.getNyad());
				return;
			} catch (BadSignatureException es) {
				ErrorDialog.show("Could not save new monad./n" + es.getSourceMessage(), "Bad Signature Exception.");
			} catch (GeneratorRangeException e) {
				ErrorDialog.show("Could not save new monad./n" + e.getSourceMessage(), "Generator Range Exception.");
			} catch (CladosMonadException em) {
				ErrorDialog.show("Could not save new monad./n" + em.getSourceMessage(), "Clados Monad Exception.");
			} catch (CladosNyadException en) {
				ErrorDialog.show("Could not save new monad./n" + en.getSourceMessage(), "Clados Nyad Exception.");
			}
		}
		}
	}

	private void appendMonad(NyadPanel tNSpotP, Nyad tNSpot)
			throws BadSignatureException, GeneratorRangeException, CladosMonadException, CladosNyadException {
		tNSpot.createMonad(monadShort.name.getText(), monadShort.aname.getText(), monadShort.frame.getText(),
				monadShort.sig.getText(), monadShort.cardname.getText());
		tNSpotP.addMonadPanel(tNSpot.getMonadList(tNSpot.getNyadOrder() - 1));
	}

	private void appendMonadUsingAlg(NyadPanel tNSpotP, Nyad tNSpot)
			throws BadSignatureException, GeneratorRangeException, CladosMonadException, CladosNyadException {

		if (copyAlg.getFoot() != tNSpot.getFoot()) {
			ErrorDialog.show("Chosen algebraRF had different foot from nyad.\nNO monad added.", "Append Monad failed.");
			return; //
		} // Foot reference match ensured now. Algebra existence ensured too. Moving on.
		switch (tNSpot.getMode()) {
		case REALF -> {
			Monad rep = CladosGBuilder.createMonadWithAlgebra(
					CladosFListBuilder.REALF.create(copyAlg.getCardinal(), copyAlg.getBladeCount()), copyAlg,
					monadShort.name.getText(), monadShort.frame.getText());
			tNSpot.appendMonad(rep);
			tNSpotP.addMonadPanel(rep);
		}
		case REALD -> {
			Monad rep = CladosGBuilder.createMonadWithAlgebra(
					CladosFListBuilder.REALD.create(copyAlg.getCardinal(), copyAlg.getBladeCount()), copyAlg,
					monadShort.name.getText(), monadShort.frame.getText());
			tNSpot.appendMonad(rep);
			tNSpotP.addMonadPanel(rep);
		}
		case COMPLEXF -> {
			Monad rep = CladosGBuilder.createMonadWithAlgebra(
					CladosFListBuilder.COMPLEXF.create(copyAlg.getCardinal(), copyAlg.getBladeCount()), copyAlg,
					monadShort.name.getText(), monadShort.frame.getText());
			tNSpot.appendMonad(rep);
			tNSpotP.addMonadPanel(rep);
		}
		case COMPLEXD -> {
			Monad rep = CladosGBuilder.createMonadWithAlgebra(
					CladosFListBuilder.COMPLEXD.create(copyAlg.getCardinal(), copyAlg.getBladeCount()), copyAlg,
					monadShort.name.getText(), monadShort.frame.getText());
			tNSpot.appendMonad(rep);
			tNSpotP.addMonadPanel(rep);
		}
		}
	}

	private void appendNyad(CladosField pRep)
			throws BadSignatureException, GeneratorRangeException, CladosMonadException, CladosNyadException {
		switch (pRep) {
		case REALF -> {
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadZero(
							(RealF) CladosField.REALF.createZERO(monadShort.cardname.getText()),
							monadShort.name.getText(), monadShort.aname.getText(), monadShort.frame.getText(),
							monadShort.foot.getText(), monadShort.sig.getText()), "New"));
		}
		case REALD -> {
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadZero(
							(RealD) CladosField.REALD.createZERO(monadShort.cardname.getText()),
							monadShort.name.getText(), monadShort.aname.getText(), monadShort.frame.getText(),
							monadShort.foot.getText(), monadShort.sig.getText()), "New"));
		}
		case COMPLEXF -> {
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadZero(
							(ComplexF) CladosField.COMPLEXF.createZERO(monadShort.cardname.getText()),
							monadShort.name.getText(), monadShort.aname.getText(), monadShort.frame.getText(),
							monadShort.foot.getText(), monadShort.sig.getText()), "New"));
		}
		case COMPLEXD -> {
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadZero(
							(ComplexD) CladosField.COMPLEXD.createZERO(monadShort.cardname.getText()),
							monadShort.name.getText(), monadShort.aname.getText(), monadShort.frame.getText(),
							monadShort.foot.getText(), monadShort.sig.getText()), "New"));
		}
		}
	}

	private void appendNyadUsingFoot(CladosField pRep)
			throws BadSignatureException, GeneratorRangeException, CladosMonadException, CladosNyadException {
		switch (pRep) {
		case REALF -> {
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadWithFoot(
							(RealF) CladosField.REALF.createZERO(monadShort.cardname.getText()), copyFoot,
							monadShort.name.getText(), monadShort.aname.getText(), monadShort.frame.getText(),
							monadShort.sig.getText()), "New"));
		}
		case REALD -> {
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadWithFoot(
							(RealD) CladosField.REALD.createZERO(monadShort.cardname.getText()), copyFoot,
							monadShort.name.getText(), monadShort.aname.getText(), monadShort.frame.getText(),
							monadShort.sig.getText()), "New"));
		}
		case COMPLEXF -> {
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadWithFoot(
							(ComplexF) CladosField.COMPLEXF.createZERO(monadShort.cardname.getText()), copyFoot,
							monadShort.name.getText(), monadShort.aname.getText(), monadShort.frame.getText(),
							monadShort.sig.getText()), "New"));
		}
		case COMPLEXD -> {
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadWithFoot(
							(ComplexD) CladosField.COMPLEXD.createZERO(monadShort.cardname.getText()), copyFoot,
							monadShort.name.getText(), monadShort.aname.getText(), monadShort.frame.getText(),
							monadShort.sig.getText()), "New"));
		}
		}
	}

	private void appendNyadUsingAlg(CladosField pRep)
			throws BadSignatureException, GeneratorRangeException, CladosMonadException, CladosNyadException {
		UnitAbstract[] tC;
		switch (pRep) {
		case REALF -> {
			tC = CladosFListBuilder.REALF.create(copyAlg.getCardinal(), copyAlg.getBladeCount());
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadWithAlgebra(tC, copyAlg,
							monadShort.name.getText(), monadShort.frame.getText()), "New"));
		}
		case REALD -> {
			tC = CladosFListBuilder.REALD.create(copyAlg.getCardinal(), copyAlg.getBladeCount());
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadWithAlgebra(tC, copyAlg,
							monadShort.name.getText(), monadShort.frame.getText()), "New"));
		}
		case COMPLEXF -> {
			tC = CladosFListBuilder.COMPLEXF.create(copyAlg.getCardinal(), copyAlg.getBladeCount());
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadWithAlgebra(tC, copyAlg,
							monadShort.name.getText(), monadShort.frame.getText()), "New"));
		}
		case COMPLEXD -> {
			tC = CladosFListBuilder.COMPLEXD.create(copyAlg.getCardinal(), copyAlg.getBladeCount());
			_GUI.appGeometryView.addNyad(pRep,
					CladosGBuilder.INSTANCE.createNyadUsingMonad(CladosGBuilder.createMonadWithAlgebra(tC, copyAlg,
							monadShort.name.getText(), monadShort.frame.getText()), "New"));
		}
		}
	}

	private int getMonadPaneFocus() {
		return (_GUI.appGeometryView.getNyadPanel(getNyadPaneFocus()).getPaneFocus());
	}

	private MonadPanel getMonadPanelFocus() {
		return getNyadPanelFocus().getMonadPanel(getMonadPaneFocus());
	}

	private int getNyadPaneFocus() {
		return _GUI.appGeometryView.getPaneFocus();
	}

	private NyadPanel getNyadPanelFocus() {
		return _GUI.appGeometryView.getNyadPanel(getNyadPaneFocus());
	}

}