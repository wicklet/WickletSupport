/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;

public class DefaultNameDetector implements INameDetector {

	private static DefaultNameDetector singleton;

	public static DefaultNameDetector getSingleton() {
		if (singleton == null)
			singleton = new DefaultNameDetector();
		return singleton;
	}


	public boolean isNamePart(char c) {
		return Character.isLetterOrDigit(c);
	}
}
