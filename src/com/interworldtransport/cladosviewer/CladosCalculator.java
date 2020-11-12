/**
 * <h2>Copyright</h2> © 2020 Alfred Differ.<br>
 * ------------------------------------------------------------------------ <br>
 * ---com.interworldtransport.cladosviewer.CladosCalculator<br>
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
 * ---com.interworldtransport.cladosviewer.CladosCalculator<br>
 * ------------------------------------------------------------------------ <br>
 */
package com.interworldtransport.cladosviewer;

import com.interworldtransport.cladosF.CladosFBuilder;
import com.interworldtransport.cladosG.AlgebraComplexD;
import com.interworldtransport.cladosG.AlgebraComplexF;
import com.interworldtransport.cladosG.AlgebraRealD;
import com.interworldtransport.cladosG.AlgebraRealF;
import com.interworldtransport.cladosviewerExceptions.CantGetIniException;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * The MonadViewer class will display Nyads and Monads and allow the user to
 * manipulate them via methods similar to old four-function calculators
 *
 * @version 0.85
 * @author Dr Alfred W Differ
 * @since clados 1.0
 */
public class CladosCalculator extends JFrame implements ActionListener {

	private static final long serialVersionUID = 2693054448133487057L;
	private static final Color _backColor = new Color(255, 255, 222);

	public static void main(String[] args) {
		// default string entries in case someone starts this without any arg's at all
		String TitleName = "Clados Calculator Utility";
		String ConfName = "conf/CladosCalculator.xml";

		for (int i = 0; i < args.length; i = i + 2) {
			if (args[i].equals("-t"))
				TitleName = args[i + 1];
			if (args[i].equals("-c"))
				ConfName = args[i + 1];
		}
		JFrame fr = new CladosCalculator(TitleName, ConfName);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.pack();
		fr.setVisible(true);
	}

	/**
	 * The EventModel for the application.
	 */
	public ViewerEventModel appEventModel;
	/**
	 * The Field Display Panel for the application. Located at the top of the GUI
	 * and intended for numeric inputs.
	 */
	public FieldPanel appFieldBar;
	/**
	 * The Center Display Panel for the application. Located in the center of the
	 * GUI and intended for display panels.
	 */
	public ViewerPanel appGeometryView;
	/**
	 * The Status Display Panel for the application. Located at the bottom of the
	 * GUI and intended for status information.
	 */
	public UtilityStatusBar appStatusBar;

	private ViewerMenu appMenuBar;
	private JButton btnHasGrade;
	private JButton btnHasNyadAlgebra;
	private JButton btnIsGrade;
	private JButton btnIsIdempotent;
	private JButton btnIsMultiGrade;
	private JButton btnIsNilpotent;
	private JButton btnIsNyadEqual;
	private JButton btnIsNyadPScalarAt;
	private JButton btnIsNyadScalarAt;
	private JButton btnIsNyadStrgRefMatch;
	private JButton btnIsNyadWeakRefMatch;
	private JButton btnIsScaleIdempotent;
	private JButton btnIsZero;
	private JButton btnWhatGrade;
	private JButton btnWhatMagn;
	private JButton btnWhatSQMagn;
	private JPanel pnlControlBar; // global button display for easy menu access

	/*
	 * This is the properties object containing key/value pairs from the config file
	 * and any system settings that might be useful.
	 */
	public Properties IniProps;

	/**
	 * This is the main constructor the the Monad Viewer
	 * 
	 * @param pTitle  String This is the title string placed at the top of the
	 *                viewer
	 * @param pConfig String This is the path/filename for the configuration file
	 *                for the viewer
	 */
	public CladosCalculator(String pTitle, String pConfig) {
		super(pTitle);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.gc();
				System.exit(0);
			}
		});
		try {
			getConfigProps(pConfig);
		} catch (CantGetIniException e) {
			System.out.println("Can't find the configuration file while constructing main GUI");
			System.gc();
			System.exit(0);
		}
		Container cp = getContentPane();

		appMenuBar = new ViewerMenu(this);
		setJMenuBar(appMenuBar); // The Menu Bar is an element of the parent class JFrame
		appEventModel = new ViewerEventModel(appMenuBar); // EventModel relies on existance of appMenuBar

		appStatusBar = new UtilityStatusBar(); // Next up because errors have to be reported somewhere.
		cp.add(appStatusBar, "South");

		appGeometryView = new ViewerPanel(this); // ViewerPanel next to display stuff from nyads and monads
		appGeometryView.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		cp.add(appGeometryView, "Center");
		// FieldBar MUST follow ViewerPanel to make use of protonumbers
		int indxNPanelSelected = appGeometryView.getPaneFocus();
		if (indxNPanelSelected >= 0) {
			NyadPanel tSpot = appGeometryView.getNyadPanel(indxNPanelSelected);
			int indexedMonad = tSpot.getPaneFocus();
			if (indexedMonad >= 0) {
				switch (appGeometryView.getNyadPanel(indxNPanelSelected).getRepMode()) {
				case REALF -> {
					AlgebraRealF tSpotRF = tSpot.getNyadRF().getMonadList(indexedMonad).getAlgebra();
					appFieldBar = new FieldPanel(this, AlgebraRealF.shareProtoNumber(tSpotRF));
					cp.add(appFieldBar, "North");
					tSpotRF = null;
				}
				case REALD -> {
					AlgebraRealD tSpotRD = tSpot.getNyadRD().getMonadList(indexedMonad).getAlgebra();
					appFieldBar = new FieldPanel(this, AlgebraRealD.shareProtoNumber(tSpotRD));
					cp.add(appFieldBar, "North");
					tSpotRD = null;
				}

				case COMPLEXF -> {
					AlgebraComplexF tSpotCF = tSpot.getNyadCF().getMonadList(indexedMonad).getAlgebra();
					appFieldBar = new FieldPanel(this, AlgebraComplexF.shareProtoNumber(tSpotCF));
					cp.add(appFieldBar, "North");
					tSpotCF = null;
				}

				case COMPLEXD -> {
					AlgebraComplexD tSpotCD = tSpot.getNyadCD().getMonadList(indexedMonad).getAlgebra();
					appFieldBar = new FieldPanel(this, AlgebraComplexD.shareProtoNumber(tSpotCD));
					cp.add(appFieldBar, "North");
					tSpotCD = null;
				}
				}
			}
			tSpot = null;
		} else {
			// This catches the possibility that no NyadPanel was created upon
			// initialization
			appFieldBar = new FieldPanel(this, CladosFBuilder.createRealFZERO("PlaceHolder"));
			cp.add(appFieldBar, "North");
		}

		pnlControlBar = new JPanel();
		pnlControlBar.setLayout(new GridBagLayout());
		pnlControlBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		pnlControlBar.setBackground(_backColor);
		createTestControls();
		cp.add(pnlControlBar, "West");

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 4 - this.getSize().width / 4, dim.height / 4 - this.getSize().height / 4);
	}

	/**
	 * Action Listeners trigger this method on ActionEvents. This particular one
	 * covers the actions listed as buttons on the left panel that apply to
	 * potentially more than one nyad or to specific monads in a way that is
	 * indifferent to the containing nyad.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "strong ref match" -> appEventModel.NOpsParts.strgrmatch.actionPerformed(event);
		case "weak ref match" -> appEventModel.NOpsParts.weakrmatch.actionPerformed(event);
		case "algebra detect" -> appEventModel.NOpsParts.hasalgebra.actionPerformed(event);
		case "equal" -> appEventModel.NOpsParts.equal.actionPerformed(event);
		case "zero" -> appEventModel.NOpsParts.zero.actionPerformed(event);
		case "scalar at" -> appEventModel.NOpsParts.scalarAtAlg.actionPerformed(event);
		case "pscalar at" -> appEventModel.NOpsParts.pscalarAtAlg.actionPerformed(event);
		case "nilpotent" -> appEventModel.MOpsParts.nilp.actionPerformed(event);
		case "idempotent" -> appEventModel.MOpsParts.idemp.actionPerformed(event);
		case "scaled idempotent" -> appEventModel.MOpsParts.midemp.actionPerformed(event);
		case "is findgrade" -> appEventModel.MOpsParts.grade.actionPerformed(event);
		case "is mgrade" -> appEventModel.MOpsParts.mgrade.actionPerformed(event);
		case "is findgrade!" -> appEventModel.MOpsParts.findgrade.actionPerformed(event);
		case "has findgrade" -> appEventModel.MOpsParts.hasgrade.actionPerformed(event);
		case "magnitude of" -> appEventModel.MOpsParts.mag.actionPerformed(event);
		case "sqmagnitude of" -> appEventModel.MOpsParts.sqmag.actionPerformed(event);
		default -> ErrorDialog.show("No detectable command processed.", "Action At Viewer Attempted");
		}
	}

	public void terminateModel() {
		appEventModel.FileParts.fc = null;
		System.exit(0);
	}

	private void createTestControls() {
		Dimension square = new Dimension(44, 44);
		GridBagConstraints cn = new GridBagConstraints();
		cn.insets = new Insets(0, 0, 0, 0);

		cn.anchor = GridBagConstraints.NORTH;

		cn.gridx = 0;
		cn.gridy = 0;
		// cn.fill=GridBagConstraints.HORIZONTAL;

		cn.weightx = 1;
		cn.weighty = 1;
		cn.gridheight = 2;
		cn.gridwidth = 2;
		cn.fill = GridBagConstraints.BOTH;
		pnlControlBar.add(new JLabel(new ImageIcon(this.getClass().getResource("/icons/clados_56.png"))), cn);
		cn.gridx = 0;
		cn.gridy += 2;
		cn.fill = GridBagConstraints.HORIZONTAL;

		cn.weightx = 0;
		cn.weighty = 0;
		cn.gridheight = 1;
		cn.gridwidth = 1;

		// button double
		btnIsNyadStrgRefMatch = new JButton(new ImageIcon(this.getClass().getResource("/icons/match.png")));
		btnIsNyadStrgRefMatch.setActionCommand("strong ref match");
		btnIsNyadStrgRefMatch.setToolTipText("Strong Reference Match [Nyad]?");
		btnIsNyadStrgRefMatch.setPreferredSize(square);
		btnIsNyadStrgRefMatch.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnIsNyadStrgRefMatch.addActionListener(this);
		pnlControlBar.add(btnIsNyadStrgRefMatch, cn);
		cn.gridx++;

		btnIsNyadWeakRefMatch = new JButton(new ImageIcon(this.getClass().getResource("/icons/matchweak.png")));
		btnIsNyadWeakRefMatch.setActionCommand("weak ref match");
		btnIsNyadWeakRefMatch.setToolTipText("Weak reference Match [Nyad]?");
		btnIsNyadWeakRefMatch.setPreferredSize(square);
		btnIsNyadWeakRefMatch.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnIsNyadWeakRefMatch.addActionListener(this);
		pnlControlBar.add(btnIsNyadWeakRefMatch, cn);
		cn.gridx = 0;
		cn.gridy++;

		btnHasNyadAlgebra = new JButton(new ImageIcon(this.getClass().getResource("/icons/hasAlgebra.png")));
		btnHasNyadAlgebra.setActionCommand("algebra detect");
		btnHasNyadAlgebra.setToolTipText("Next Nyad Has Algebra?");
		btnHasNyadAlgebra.setPreferredSize(square);
		btnHasNyadAlgebra.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnHasNyadAlgebra.addActionListener(this);
		pnlControlBar.add(btnHasNyadAlgebra, cn);
		cn.gridx++;

		btnIsNyadEqual = new JButton(new ImageIcon(this.getClass().getResource("/icons/equal.png")));
		btnIsNyadEqual.setActionCommand("equal");
		btnIsNyadEqual.setToolTipText("strong Equality Nyad Test");
		btnIsNyadEqual.setPreferredSize(square);
		btnIsNyadEqual.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnIsNyadEqual.addActionListener(this);
		pnlControlBar.add(btnIsNyadEqual, cn);
		cn.gridx = 0;
		cn.gridy++;

		btnIsNyadScalarAt = new JButton(new ImageIcon(this.getClass().getResource("/icons/isScalarAt.png")));
		btnIsNyadScalarAt.setActionCommand("scalar at");
		btnIsNyadScalarAt.setToolTipText("Next Nyad Is Scalar At?");
		btnIsNyadScalarAt.setPreferredSize(square);
		btnIsNyadScalarAt.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnIsNyadScalarAt.addActionListener(this);
		pnlControlBar.add(btnIsNyadScalarAt, cn);
		cn.gridx++;

		btnIsNyadPScalarAt = new JButton(new ImageIcon(this.getClass().getResource("/icons/isPScalarAt.png")));
		btnIsNyadPScalarAt.setActionCommand("pscalar at");
		btnIsNyadPScalarAt.setToolTipText("Next Nyad Is PScalar At?");
		btnIsNyadPScalarAt.setPreferredSize(square);
		btnIsNyadPScalarAt.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnIsNyadPScalarAt.addActionListener(this);
		pnlControlBar.add(btnIsNyadPScalarAt, cn);
		cn.gridx = 0;
		cn.gridy++;

		cn.gridwidth = 2;
		cn.fill = GridBagConstraints.BOTH;
		pnlControlBar.add(new JLabel(new ImageIcon(this.getClass().getResource("/icons/bar.png"))), cn);
		cn.fill = GridBagConstraints.HORIZONTAL;
		cn.gridwidth = 1;
		cn.gridx = 0;
		cn.gridy++;

		// button double
		btnIsZero = new JButton(new ImageIcon(this.getClass().getResource("/icons/zero.png")));
		btnIsZero.setActionCommand("zero");
		btnIsZero.setToolTipText("additive Identity (Zero) Monad Test");
		btnIsZero.setPreferredSize(square);
		btnIsZero.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnIsZero.addActionListener(this);
		pnlControlBar.add(btnIsZero, cn);
		cn.gridx++;

		btnIsNilpotent = new JButton(new ImageIcon(this.getClass().getResource("/icons/nilp.png")));
		btnIsNilpotent.setActionCommand("nilpotent");
		btnIsNilpotent.setToolTipText("is nilpotent at power N?");
		btnIsNilpotent.setPreferredSize(square);
		btnIsNilpotent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnIsNilpotent.addActionListener(this);
		pnlControlBar.add(btnIsNilpotent, cn);
		cn.gridx = 0;
		cn.gridy++;

		// button double
		btnIsIdempotent = new JButton(new ImageIcon(this.getClass().getResource("/icons/idmp.png")));
		btnIsIdempotent.setActionCommand("idempotent");
		btnIsIdempotent.setToolTipText("is idempotent?");
		btnIsIdempotent.setPreferredSize(square);
		btnIsIdempotent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnIsIdempotent.addActionListener(this);
		pnlControlBar.add(btnIsIdempotent, cn);
		cn.gridx++;

		btnIsScaleIdempotent = new JButton(new ImageIcon(this.getClass().getResource("/icons/midmp.png")));
		btnIsScaleIdempotent.setActionCommand("scaled idempotent");
		btnIsScaleIdempotent.setToolTipText("is scaled idempotent?");
		btnIsScaleIdempotent.setPreferredSize(square);
		btnIsScaleIdempotent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnIsScaleIdempotent.addActionListener(this);
		pnlControlBar.add(btnIsScaleIdempotent, cn);
		cn.gridx = 0;
		cn.gridy++;

		// button double
		btnWhatMagn = new JButton(new ImageIcon(this.getClass().getResource("/icons/magn.png")));
		btnWhatMagn.setActionCommand("magnitude of");
		btnWhatMagn.setToolTipText("discover magnitude");
		btnWhatMagn.setPreferredSize(square);
		btnWhatMagn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnWhatMagn.addActionListener(this);
		pnlControlBar.add(btnWhatMagn, cn);
		cn.gridx++;

		btnWhatSQMagn = new JButton(new ImageIcon(this.getClass().getResource("/icons/sqmagn.png")));
		btnWhatSQMagn.setActionCommand("sqmagnitude of");
		btnWhatSQMagn.setToolTipText("discover magnitude^2");
		btnWhatSQMagn.setPreferredSize(square);
		btnWhatSQMagn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnWhatSQMagn.addActionListener(this);
		pnlControlBar.add(btnWhatSQMagn, cn);
		cn.gridx = 0;
		cn.gridy++;

		// button double
		btnIsGrade = new JButton(new ImageIcon(this.getClass().getResource("/icons/grade.png")));
		btnIsGrade.setActionCommand("is findgrade");
		btnIsGrade.setToolTipText("has this unique grade?");
		btnIsGrade.setPreferredSize(square);
		btnIsGrade.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnIsGrade.addActionListener(this);
		pnlControlBar.add(btnIsGrade, cn);
		cn.gridx++;

		btnIsMultiGrade = new JButton(new ImageIcon(this.getClass().getResource("/icons/mgrade.png")));
		btnIsMultiGrade.setActionCommand("is mgrade");
		btnIsMultiGrade.setToolTipText("is multigrade?");
		btnIsMultiGrade.setPreferredSize(square);
		btnIsMultiGrade.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnIsMultiGrade.addActionListener(this);
		pnlControlBar.add(btnIsMultiGrade, cn);
		cn.gridx = 0;
		cn.gridy++;

		btnHasGrade = new JButton(new ImageIcon(this.getClass().getResource("/icons/hasgrade.png")));
		btnHasGrade.setActionCommand("has findgrade");
		btnHasGrade.setToolTipText("has this grade?");
		btnHasGrade.setPreferredSize(square);
		btnHasGrade.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnHasGrade.addActionListener(this);
		pnlControlBar.add(btnHasGrade, cn);
		cn.gridx++;

		btnWhatGrade = new JButton(new ImageIcon(this.getClass().getResource("/icons/whatgrade.png")));
		btnWhatGrade.setActionCommand("is findgrade!");
		btnWhatGrade.setToolTipText("what unique grade?");
		btnWhatGrade.setPreferredSize(square);
		btnWhatGrade.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		btnWhatGrade.addActionListener(this);
		pnlControlBar.add(btnWhatGrade, cn);
	}

	/**
	 * This method does the initial file handling for the configuration file. It
	 * doesn't do anything fancy... just get it and load it into IniProps.
	 * 
	 * @param pConfName String This string holds the path and filename pointing to
	 *                  the configuration file
	 * @throws CantGetIniException This exception gets thrown when IO issues occur
	 *                             blocking access to writes to the configuration
	 *                             file.
	 */
	protected void getConfigProps(String pConfName) throws CantGetIniException {
		if (pConfName == null)
			throw new CantGetIniException("The configuration file is not provided.");

		File fIni = new File(pConfName);
		if (!(fIni.exists() & fIni.isFile() & fIni.canWrite()))
			throw new CantGetIniException("The configuration file is not valid.");

		try (FileInputStream tempSpot = new FileInputStream(fIni);
				BufferedInputStream tSpot = new BufferedInputStream(tempSpot)) {
			IniProps = new Properties();
			// IniProps=new Properties(System.getProperties());
			// IniProps.load(tSpot); // This loads the standard key/pair properties file
			// format.
			IniProps.loadFromXML(tSpot); // This loads an XML formatted key/pair properties file.
			tSpot.close();
			tempSpot.close();
		} catch (IOException e) {
			System.out.println("IO Problem:  Incomplete Access to associated INI files.");
			throw new CantGetIniException("No Access to INI file.");
		}
	}

}
