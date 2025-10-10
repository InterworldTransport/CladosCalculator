/**
 * <h2>Copyright</h2> © 2025 Alfred Differ<br>
 * ------------------------------------------------------------------------ <br>
 * ---org.interworldtransport.cladosviewer.CantGetSaveException<br>
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
 * ---org.interworldtransport.cladosviewer.CantGetSaveException<br>
 * ------------------------------------------------------------------------ <br>
 */

package org.interworldtransport.cladosviewerExceptions;

/**
 * org.interworldtransport.cladosviewer.CantGetSaveException This exception is
 * thrown when the interface is unable to save snapshot data. The most likely
 * problem that leads here is a mis-pointed output file. All such problems lead
 * here.
 *
 * @version 0.80, $Date: 2005/07/28 16:57:25 $
 * @author Dr Alfred W Differ
 */
public class CantGetSaveException extends UtilitiesException {

	private static final long serialVersionUID = 3346191904559396286L;

	/**
	 * This is just a constructor method delivering a string to the descendant of a
	 * UtilitiesException
	 * 
	 * @param pMessage String This is the hopefully helpful message to be delivered
	 *                 when things go specifically wrong with access to a
	 *                 save/snapshot file. The app works well enough without it, but
	 *                 not being able to save results is annoying.
	 */
	public CantGetSaveException(String pMessage) {
		super(pMessage);
	}

}
