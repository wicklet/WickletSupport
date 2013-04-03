/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;

public class JavaNameDetector implements INameDetector {

	private static JavaNameDetector singleton;

	public static JavaNameDetector getSingleton() {
		if (singleton == null) {
			singleton = new JavaNameDetector();
		}
		return singleton;
	}


	@Override
	public boolean isNamePart(final char c) {
		return Character.isJavaIdentifierPart(c);
	}
}
