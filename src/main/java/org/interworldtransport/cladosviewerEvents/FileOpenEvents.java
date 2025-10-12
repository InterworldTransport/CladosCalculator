/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.FileOpenEvents<br>
 * -------------------------------------------------------------------- <br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.<br>
 * 
 * Use of this code or executable objects derived from it by the Licensee 
 * states their willingness to accept the terms of the license. <br> 
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.<br> 
 * 
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.FileOpenEvents<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerEvents;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.TreeMap;

import javax.swing.*;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.interworldtransport.cladosF.Cardinal;
import org.interworldtransport.cladosF.FCache;
import org.interworldtransport.cladosF.CladosField;
import org.interworldtransport.cladosG.Algebra;
import org.interworldtransport.cladosG.GBuilder;
import org.interworldtransport.cladosG.GCache;
import org.interworldtransport.cladosG.Foot;
import org.interworldtransport.cladosG.Monad;
import org.interworldtransport.cladosG.Nyad;
import org.interworldtransport.cladosGExceptions.BadSignatureException;
import org.interworldtransport.cladosGExceptions.GeneratorRangeException;
import org.interworldtransport.cladosviewer.ErrorDialog;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
/**
 * This class implmements the part of the event model that opens data files with monad
 * and nyad information to be parsed. 
 */
public class FileOpenEvents implements ActionListener {

	private final static String path2Algs = "//Algebra";
	//private final static String path2AlgUUIDs = "//Algebra/@UUID";
	private final static String path2AllCardinals = "//Foot/Cardinals/Cardinal/@unit";
	private final static String path2AllSignatures = "//GProduct/Signature/text()";
	//private final static String path2FootCardinals = "//Foot/Name[!]	/Cardinals/Cardinal/@unit"; // re-do this one
	private final static String path2FootNames = "//Algebra/Foot/Name";
	private final static String path2Mode = "//Pair/*";
	//private final static String path2MonadNames = "//Monad/Name/text()";
	//private final static String path2Monads = "//Monad";
	//private final static String path2NyadNames = "//Nyad/Name/text()";
	private final static String path2Nyads = "//Nyad";
	//private final static String path4NyadCount = "count(//Nyad)";

	private final static Optional<Monad> buildAMonad(Node pNode) {

		// For this to work, the Monad child elements must be in order.
		// Name, Algebra, ReferenceFrame, Scales
		// And GProducts, Bases, and Cardinals already exist as objects
		
	//	Node name = pNode.getFirstChild();
	//	String name2Use = name.getTextContent();

	//	Node alg = name.getNextSibling();

		return Optional.empty();
	}

	private final static void buildCardinals(Document pDoc, XPath pX) throws XPathExpressionException {
		XPathExpression expr = pX.compile(path2AllCardinals);
		NodeList cardNodes = (NodeList) expr.evaluate(pDoc, XPathConstants.NODESET);
		for (int k = 0; k < cardNodes.getLength(); k++) {
			Cardinal temp = Cardinal.generate(cardNodes.item(k).getNodeName());
			FCache.INSTANCE.appendCardinal(temp);
		}
	}

	private final static void buildGProducts(Document pDoc, XPath pX)
			throws XPathExpressionException, DOMException, GeneratorRangeException, BadSignatureException {
		XPathExpression expr = pX.compile(path2AllSignatures);
		NodeList sigNodes = (NodeList) expr.evaluate(pDoc, XPathConstants.NODESET);
		for (int k = 0; k < sigNodes.getLength(); k++) {
			GBuilder.createGProduct(sigNodes.item(k).getNodeValue());
		}
	}

	private static final Optional<CladosField> findMode(Document pDoc, XPath pX) throws XPathExpressionException {
		XPathExpression expr = pX.compile(path2Mode);
		NodeList protoNNodes = (NodeList) expr.evaluate(pDoc, XPathConstants.NODESET);

		if ((protoNNodes.getLength() >> 1) == 0)
			return Optional.empty();
		else if (protoNNodes.getLength() % 2 == 1)
			return Optional.empty();

		String first = protoNNodes.item(1).getNodeName();
		for (int k = 3; k < protoNNodes.getLength(); k += 2)
			if (first != protoNNodes.item(k).getNodeName())
				return Optional.empty();

		switch (first) {
		case "RealF" -> {
			return Optional.ofNullable(CladosField.REALF);
		}
		case "RealD" -> {
			return Optional.ofNullable(CladosField.REALD);
		}
		case "ComplexF" -> {
			return Optional.ofNullable(CladosField.COMPLEXF);
		}
		case "ComplexD" -> {
			return Optional.ofNullable(CladosField.COMPLEXD);
		}
		default -> {
			return Optional.empty();
		}
		}
	}
	
	//private ArrayList<String> _monadNames;
	private TreeMap<String, Monad> foundMonads;
	private int _nyadCount;
	//private ArrayList<String> _nyadNames;
	private TreeMap<String, Nyad> foundNyads;
	private TreeMap<String, Algebra> foundAlgebras;

	private TreeMap<String, Foot> foundFeet;

	/**
	 * A CladosField enumeration representing which ProtoN child is being used in monads.
	 */
	private CladosField repMode;
	/**
	 * This reference points at the control object in the menu related to this part of the event model. 
	 * It's open for later possibilities for scripted ations.
	 */
	protected JMenuItem _control;
	/**
	 * This reference points at the parent menu for navigating up and down the object tree. 
	 * It's open for later possibilities for scripted ations.
	 */
	protected FileEvents _parent;

	/**
	 * The FileOpenEvents class is an event handler called when an XML file is to be
	 * parsed as a nyadList for the calculator.
	 * <br>
	 * 
	 * @param _control JMenuItem that activates this event handler
	 * @param _parent  FileEvents object that acts as parent of this event handler.
	 */
	public FileOpenEvents(JMenuItem _control, FileEvents _parent) {
		super();
		this._control = _control;
		_control.addActionListener(this);
		this._parent = _parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// If SaveFile is unknown at this point, present a file chooser.
		// If there are issues with the file pointed to by SaveFile, present a chooser
		File fIni;
		if (_parent._GUI.IniProps.getProperty("Desktop.File.Snapshot") != null)
		// save to file described in conf setting
		{
			fIni = new File(_parent._GUI.IniProps.getProperty("Desktop.File.Snapshot"));
			if (!(fIni.exists() & fIni.isFile() & fIni.canWrite())) {
				int returnVal = _parent.fc.showSaveDialog(_control);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fIni = _parent.fc.getSelectedFile();
				} else
					return;
			}
		} else {
			int returnVal = _parent.fc.showSaveDialog(_control);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				fIni = _parent.fc.getSelectedFile();
			} else
				return;
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		XPathFactory xPathFactory = XPathFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(fIni);
			if (doc == null) {
				ErrorDialog.show("Couldn't parse the inbound XML at the CladosField mode reader.",
						"Import File Parse Error");
				return;
			} else if (doc.getFirstChild().getNodeName() != "NyadList") {
				ErrorDialog.show("Are you sure that was a Nyad list?", "Unexpected First Node");
				return;
			}

			if (findNyadCount(doc, xPathFactory.newXPath()) <= 0)
				return; // STOP if no nyads are to be created
			// ----------------
			// We know how many nyads are involved. Validation done too.
			// ----------------
			Optional<CladosField> test = findMode(doc, xPathFactory.newXPath());
			if (test.isPresent())
				repMode = test.get();
			else {
				ErrorDialog.show("Imported nyads have unexpected CladosF modes or mode conflicts.",
						"Import CladosF Number Parse Error");
				return;
			}

			if (_parent._GUI.appGeometryView.getNyadListSize() > 0
					&& _parent._GUI.appGeometryView.getRepMode() != repMode) {
				ErrorDialog.show("Imported nyads would have CladosF mode conflicts with existing ones.",
						"Mixed CladosF Number Stack");
				return;
			}
			// ----------------
			// We know which CladosF number is involved. Validation done too.
			// ----------------
			buildGProducts(doc, xPathFactory.newXPath());
			// ----------------
			// Bases/GProducts built/stored in GBuilder.INSTANCE. Retrieve later.
			// ----------------
			buildCardinals(doc, xPathFactory.newXPath());
			// ----------------
			// Cardinals built/stored in FBuilder.INSTANCE. Retrieve later.
			// ----------------
			buildTheFeet(doc, xPathFactory.newXPath());
			System.out.println("Made it past Foot building.");
			if (foundFeet == null | foundFeet.size() == 0)
				return; // STOP if no Foot objects created.
			// ----------------
			// Foot objects built/stored locally in foundFeet.
			// [[NOT appending Frames right now as those will change soon.]]
			// ----------------
			buildTheAlgebras(doc, xPathFactory.newXPath());
			System.out.println("Made it past Algebra construction.");
			if (foundAlgebras == null | foundAlgebras.size() == 0)
				return; // STOP if no Algebra objects created.
			// ----------------
			// All DISTINCT algebras present in the file are created
			// One issue, though. If two algebras have the same uuid and different details
			// only the first one encountered will be created.
			// DO NOT duplicate UUID's unless you intend them to be the same.
			// ----------------
			buildTheNyads(doc, xPathFactory.newXPath());
			System.out.println("Made it past Nyad construction.");
			if (foundNyads == null | foundNyads.size() == 0)
				return; // STOP if no Nyad objects created.
			// ----------------
			// All DISTINCT nyads present in the file are created
			// One issue, though. If two nyads have the same name and different details
			// only the first one encountered will be created.
			// DO NOT duplicate nyad names unless you intend them to be the same.
			// ----------------
			
			// TODO parse the XML into the 'defaults' for initiating the calculator.
			// 'Count', 'Order', 'ProtoN', etc.

		} catch (ParserConfigurationException e1) {
			ErrorDialog.show("Couldn't acquire DocumentBuilderFactory instance.\n" + e1.getMessage(),
					"Parser Configuration Exception");
			return;
		} catch (SAXException e1) {
			ErrorDialog.show("Couldn't parse the XML to create a Document.\n" + e1.getMessage(), "SAX Exception");
			return;
		} catch (IOException e1) {
			ErrorDialog.show("Stopped by a general IO issue.\n" + e1.getMessage(), "IO Exception");
			return;
		} catch (NumberFormatException e1) {
			ErrorDialog.show("A number was expected somewhere and couldn't be parsed.\n" + e1.getMessage(),
					"Number Format Exception");
			return;
		} catch (XPathExpressionException e1) {
			ErrorDialog.show("XPath string malformed\nOR\nXML file doesn't contain expected node.\n" + e1.getMessage(),
					"XPath Expression Exception");
			return;
		} catch (DOMException e1) {
			ErrorDialog.show("Couldn't parse the XML to create a Document.\n" + e1.getMessage(), "DOM Exception");
			return;
		} catch (GeneratorRangeException e1) {
			ErrorDialog.show("An unsupported signature length was found in file.", "Generator Range Exception");
			return;
		} catch (BadSignatureException e1) {
			ErrorDialog.show("A malformed (bad characters) signature was found in file.\n" + e1.getSourceMessage(),
					"Bad Signature Exception");
			return;
		}

		// TODO Call builder method in parent Event handler. It will hand back an
		// ArrayList<Nyad>?

		// TODO Validate! If ArrayList length isn't 'Count', inform the user.

		// TODO Deliver nyads one at a time to the Viewer Panel in the calculator to be
		// appended as if created there.

		_parent._GUI.appStatusBar
				.setStatusMsg("File OPEN is not ready yet. Working on XML parser of CladosG objects.\n");
	}

	private void buildTheNyads(Document pDoc, XPath pX) throws XPathExpressionException {
		XPathExpression expr = pX.compile(path2Nyads);
		NodeList nyadNodes = (NodeList) expr.evaluate(pDoc, XPathConstants.NODESET);
		foundNyads = new TreeMap<String, Nyad>();
		
		// For this to work, the Nyad child elements must be in order.
		// Name, Foot, AlgebraList, MonadList
		for (int k = 0; k < _nyadCount; k++) {
			Node name = nyadNodes.item(k).getFirstChild();
		//	String name2Use = name.getTextContent();

			Node foot = name.getNextSibling();
		//	String foot2Use = foot.getFirstChild().getTextContent();

			Node monadList = foot.getNextSibling().getNextSibling();
			NodeList monads = monadList.getChildNodes();
			int nyadOrder = monads.getLength();
			
			foundMonads = new TreeMap<String, Monad>();
			
			for (int j = 0; j < nyadOrder; j++) {
				Optional<Monad> test = buildAMonad(monads.item(j));
				if (test.isPresent())
					foundMonads.put(test.get().getName(), test.get());
			}
			
			// TODO 'put' a new Nyad into its found map
		}
		
	}

	private void buildTheFeet(Document pDoc, XPath pX) throws XPathExpressionException {
		XPathExpression expr = pX.compile(path2FootNames);
		NodeList footNodes = (NodeList) expr.evaluate(pDoc, XPathConstants.NODESET);
		if (footNodes == null) {
			return;
		} else if (footNodes.getLength() == 0) {
			return;
		}

		foundFeet = new TreeMap<String, Foot>();
		for (int k = 0; k < footNodes.getLength(); k++) {
			String footname = footNodes.item(k).getNodeValue();
			if (!foundFeet.keySet().contains(footname))
				foundFeet.put(footname, GBuilder.createFoot(footname, footname));
		}

	}

	private void buildTheAlgebras(Document pDoc, XPath pX) throws XPathExpressionException, BadSignatureException {
		XPathExpression expr = pX.compile(path2Algs);
		NodeList algNodes = (NodeList) expr.evaluate(pDoc, XPathConstants.NODESET);
		
		foundAlgebras = new TreeMap<String, Algebra>();
		
		for (int j = 0; j < algNodes.getLength(); j++) {
			// For this to work, the Alg child elements must be in order.
			// Name, ProtoN, [Frames], Foot, GProduct
			// And GProducts, Bases, and Cardinals already exist as objects
			
			String uuid = algNodes.item(j).getAttributes().getNamedItem("UUID").getTextContent();
			if (foundAlgebras.keySet().contains(uuid))
				continue;
			// -------------------
			// New algebra to create because uuid not found in foundAlgebras
			// -------------------
			Node name = algNodes.item(j).getFirstChild();
			String name2Use = name.getTextContent();
			
			Node proto = name.getNextSibling();
			String card2Use = proto.getAttributes().getNamedItem("cardinal").getTextContent();
			// We look up the Cardinal in the Algebra constructor
			
			Node foot = proto.getNextSibling().getNextSibling(); // skips Frames sibling
			String foot2Use = foot.getFirstChild().getTextContent();
			// We find the pre-created foot in a moment
			
			Node gp = proto.getNextSibling();
			String sig2Use = gp.getFirstChild().getTextContent();
			// We look up the the GProduct in the Algebra constructor

			// FINALLY we build the new algebra and add it to foundAlgebras.
			foundAlgebras.put(uuid, GBuilder.createAlgebraWithFootPlus(foundFeet.get(foot2Use),
					FCache.INSTANCE.findCardinal(card2Use).get(),
					GCache.INSTANCE.findGProductMap(sig2Use).get(), name2Use));
			// Each algebra informs its Foot of the Cardinal in use.
			// That's how a Foot's Cardnal list is rebuilt from the import data.
		}
	}

	private int findNyadCount(Document pDoc, XPath pX) throws XPathExpressionException {
		XPathExpression expr = pX.compile(path2Nyads);
		NodeList nyadNodes = (NodeList) expr.evaluate(pDoc, XPathConstants.NODESET);
		_nyadCount = nyadNodes.getLength();
		return _nyadCount;
	}
}
