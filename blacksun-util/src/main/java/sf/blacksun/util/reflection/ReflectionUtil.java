/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.reflection;




public class ReflectionUtil {


	private ReflectionUtil() {
	}


	/** @return Object value of a static field in the given class. */
	@SuppressWarnings("unchecked")
	public static <T> T getStaticFieldValue(Class<?> c, String fieldname, T def) {
		try {
			return (T)c.getField(fieldname).get(null);
		} catch (IllegalArgumentException e) {
			return def;
		} catch (IllegalAccessException e) {
			return def;
		} catch (NoSuchFieldException e) {
			return def;
		} catch (SecurityException e) {
			return def;
	}}
}
