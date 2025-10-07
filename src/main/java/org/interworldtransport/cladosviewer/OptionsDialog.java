/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.OptionsDialog<br>
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
 * ---org.interworldtransport.cladosviewer.OptionsDialog<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 * The Optons Dialog window is supposed to show a window that would allow a user
 * to adjust the configuration file from within the Viewer.
 * 
 * @version 1.0,
 * @author Dr Alfred W Differ
 */
public class OptionsDialog extends JDialog implements ActionListener, TableModelListener {
	private static final long serialVersionUID = 6839331358239321665L;

	private class PropTblModel extends AbstractTableModel {
		private static final long serialVersionUID = 265869592788704225L;
		private String[] columnNames = { "Key", "Value" };
		private Object[][] data;

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			if (col == 1)
				return true;
			return false;
		}

		/*
		 * Don't need to implement this method unless your table's data can change.
		 */
		@Override
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}

	private static final Color _backColor = new Color(255, 255, 222);
	private static final Color _tblBackColor = new Color(212, 212, 192);
	private CladosCalculator _GUI;
	private JButton btnSave;
	private JPanel mainPane = new JPanel(new BorderLayout());
	private PropTblModel tblModel = new PropTblModel();

	/**
	 * The constructor sets up the options dialog box and displays it.
	 * 
	 * @param mainWindow CladosCalculator This is just a reference to the owning
	 *                   application so error messages can be reported out to the
	 *                   GUI.
	 */
	public OptionsDialog(CladosCalculator mainWindow) {
		super(mainWindow, "Preferences Dialog", true);
		_GUI = mainWindow;
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setBackground(_backColor);
		setContentPane(mainPane);
		createControlButtons();

		JTable propTable = new JTable(tblModel);
		propTable.setBackground(_tblBackColor);
		propTable.setShowHorizontalLines(true);
		propTable.setDragEnabled(false);
		propTable.setGridColor(Color.BLACK);

		Object[] keys = _GUI.IniProps.stringPropertyNames().toArray();
		tblModel.data = new String[keys.length][2];
		for (int j = 0; j < tblModel.getRowCount(); j++) {
			tblModel.setValueAt(keys[j], j, 0);
			tblModel.setValueAt(_GUI.IniProps.get(keys[j]), j, 1);
		}
		mainPane.add(new JScrollPane(propTable), "Center");
		propTable.setFillsViewportHeight(true);
		propTable.setAutoCreateRowSorter(true);
		propTable.getModel().addTableModelListener(this);

		setSize(400, 500);

		Point parentLocation = mainWindow.getLocation();
		int Xloc = (int) parentLocation.getX() + ((mainWindow.getWidth() - 300) / 2);
		int Yloc = (int) parentLocation.getY(); // + ((mainWindow.getHeight() - 400) / 2);
		setLocation(Xloc, Yloc);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "close" -> dispose();
		case "save" -> {
			Properties unload = new Properties();
			for (int j = 0; j < tblModel.getRowCount(); j++)
				unload.put(tblModel.getValueAt(j, 0), tblModel.getValueAt(j, 1));
			saveAll(unload);
			dispose();
		}
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		;
	}

	private void createControlButtons() { // Create button panel
		JPanel controlPanel = new JPanel(new FlowLayout());
		controlPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		controlPanel.setBackground(_backColor);
		mainPane.add(controlPanel, "South");

		// Create buttons
		btnSave = new JButton(new ImageIcon(this.getClass().getResource("/img/save.png")));
		btnSave.setActionCommand("save");
		btnSave.setToolTipText("Save any changes, then close.");
		btnSave.setPreferredSize(new Dimension(30, 30));
		btnSave.setBorder(BorderFactory.createEtchedBorder(0));
		btnSave.addActionListener(this);
		controlPanel.add(btnSave);

		JButton btnClose = new JButton(new ImageIcon(this.getClass().getResource("/img/close.png")));
		btnClose.setActionCommand("close");
		btnClose.setToolTipText("Close the dialog. No further changes.");
		btnClose.setPreferredSize(new Dimension(30, 30));
		btnClose.setBorder(BorderFactory.createEtchedBorder(0));
		btnClose.addActionListener(this);
		controlPanel.add(btnClose);
	}

	private void saveAll(Properties pIn) {
		File fIni = new File(_GUI.IniProps.getProperty("Desktop.File.Properties"));
		if (!(fIni.exists() & fIni.isFile() & fIni.canWrite())) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fc.showSaveDialog(btnSave) == JFileChooser.APPROVE_OPTION)
				fIni = fc.getSelectedFile();
		}
		if (!(fIni.exists() & fIni.isFile() & fIni.canWrite())) {
			ErrorDialog.show("Options NOT saved.\nCan't seem to get a viable file to save them in.\n", "IO Issue");
			return;
		}

		try (FileOutputStream tempSpot = new FileOutputStream(fIni);
				BufferedOutputStream tSpot = new BufferedOutputStream(tempSpot)) {
			pIn.storeToXML(tSpot, "Saved From Clados Calculator Properties Dialog");
			_GUI.appStatusBar.setStatusMsg("-->Options SAVED.\n");
			tSpot.flush();
			tempSpot.flush();
			tSpot.close();
			tempSpot.close();
		} catch (IOException e) {
			ErrorDialog.show("Options NOT saved.\nIO Exception involving Properties target file.\n", "IO Issue");
		} finally {
			_GUI.IniProps = pIn;
			fIni = null;
		}
	}
}
