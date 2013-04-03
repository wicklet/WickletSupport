/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;


public interface ISpaceUtil extends ISpaceDetector {

	/** @return true if character is a space or line break character. */
	public abstract boolean isWhitespace(char c);
	public abstract int skipWhitespaces(CharSequence s, int start, int end);
}
