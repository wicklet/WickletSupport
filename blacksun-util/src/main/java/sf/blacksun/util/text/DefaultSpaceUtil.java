/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;

public class DefaultSpaceUtil extends AbstractSpaceUtil {

	private static final char[] WHITESPACES = new char[] { ' ', '\t', '\f', '\n', '\r' };

	private static DefaultSpaceUtil singleton;

	public static DefaultSpaceUtil getSingleton() {
		if (singleton == null)
			singleton = new DefaultSpaceUtil();
		return singleton;
	}

	public boolean isWhitespace(char c) {
		for (int i = WHITESPACES.length - 1; i >= 0; --i) {
			if (c == WHITESPACES[i])
				return true;
		}
		return false;
	}
}
