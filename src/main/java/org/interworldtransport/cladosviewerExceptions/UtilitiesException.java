/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.UtilitiesException<br>
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
 * ---org.interworldtransport.cladosviewer.UtilitiesException<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerExceptions;

/**
 * This class is the Parent of all Exceptions originating from the viewer
 * package.
 *
 * @version 1.0, 2020/09/17
 * @author Dr Alfred W Differ
 */
public class UtilitiesException extends Exception {

	private static final long serialVersionUID = 6331343927784606625L;
	public String SourceMessage;

	/**
	 * Construct the parent exception and force a record of the source message for
	 * all descendants.
	 * 
	 * @param pMessage String This is the hopefully helpful message to be delivered
	 *                 when things go generically wrong.
	 */
	public UtilitiesException(String pMessage) {
		super();
		this.SourceMessage = pMessage;
	}

	/**
	 * Return the Source Message associated with this Exception.
	 * 
	 * @return String Returns the string message embedded in the object that
	 *         hopefully explains more about why the exception occurred.
	 */
	public String getSourceMessage() {
		return this.SourceMessage;
	}

}
