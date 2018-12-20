/*
<h2>Copyright</h2>
Copyright (c) 2005 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.cladosviewer.CantGetIniException-----------------------------------
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
---com.interworldtransport.cladosviewer.CantGetIniException-----------------------------------
 */

package com.interworldtransport.cladosviewer;

/**
 * This class is thrown when the user interface expects a configuration file and
 * cannot find one.
 *
 * @version 0.80, $Date: 2005/07/28 16:57:25 $
 * @author Dr Alfred W Differ
 *
 */
public class CantGetIniException extends UtilitiesException 
{
	private static final long serialVersionUID = -194690479375721970L;

	public CantGetIniException(String pMessage)
    {
    	super(pMessage);
	}

    } //end of CantGetIniException class


