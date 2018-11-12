/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.ViewerEventModel------------------------
<p>
Interworld Transport grants you ("Licensee") a license to this software
under the terms of the GNU General Public License.<br>
A full copy of the license can be found bundled with this package or code file.
<p>
If the license file has become separated from the package, code file, or binary
executable, the Licensee is still expected to read about the license at the
following URL before accepting this material.
<blockquote><code>http://www.opensource.org/gpl-license.html</code></blockquote>
<p>
Use of this code or executable objects derived from it by the Licensee states their
willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
---com.interworldtransport.cladosviewer.ViewerEventModel------------------------
 */

package com.interworldtransport.cladosviewer;
import com.interworldtransport.clados.*;

/** com.interworldtransport.cladosviewer.ViewerEventModel
 *
 *
 * @version 0.80, $Date: 2005/07/25 01:44:25 $
 * @author Dr Alfred W Differ
 */
public class ViewerEventModel
{
/**
 * The GUIParentMenu object maintains a reference to the GUI's Menu to which
 * this EventModel applies
 */
    protected	ViewerMenu		GUIParentMenu;

/**
 * The FileEvents object collects all File Event Handlers in one place in case
 * there are actions they all share.  This object is responsible for
 * constructing all File Event Handlers.
 */
    protected 	FileEvents		FileParts;

/**
 * The BOpsEvents object collects all Boolean Operations Event Handlers in one
 * place in case there are actions they all share.  This object is responsible
 * for constructing all related Event Handlers.
 */
    protected	BOpsEvents		BOpsParts;

/**
 * The SOpsEvents object collects all Simple Operations Event Handlers in one
 * place in case there are actions they all share.  This object is responsible
 * for constructing all related Event Handlers.
 */
    protected	SOpsEvents		SOpsParts;

/**
 * The COpsEvents object collects all Complex Operations Event Handlers in one
 * place in case there are actions they all share.  This object is responsible
 * for constructing all related Event Handlers.
 */
    protected	COpsEvents		COpsParts;

/**
 * The ToolEvents object collects all Tool Event Handlers in one place in case
 * there are actions they all share.  This object is responsible for
 * constructing all related Event Handlers.
 */
    protected	ToolsEvents		ToolParts;

/**
 * The HelpEvents object collects all Help Event Handlers in one place in case
 * there are actions they all share.  This object is responsible for
 * constructing all Help Event Handlers.
 */
    protected 	HelpEvents		HelpParts;


/** This is the default constructor.  The event structure of the SailAway
 *  application starts here and finishes with the child components that affect the
 *  physical model.
 */
    public ViewerEventModel(ViewerMenu pGUIParentMenu)
        	throws 		UtilitiesException, BadSignatureException
    {
	//super(pGUIParentMenu);
	this.GUIParentMenu=pGUIParentMenu;

	this.FileParts = new FileEvents(this.GUIParentMenu);

	this.BOpsParts = new BOpsEvents(this.GUIParentMenu);
	this.SOpsParts = new SOpsEvents(this.GUIParentMenu);
	this.COpsParts = new COpsEvents(this.GUIParentMenu);

	this.ToolParts = new ToolsEvents(this.GUIParentMenu);
	this.HelpParts = new HelpEvents(this.GUIParentMenu);

    }//end of ViewerEventModel constructor


    }//end of ViewerEventModel class
