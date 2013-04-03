/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;





import java.util.regex.Pattern;

/**
 * Utility class that make use of java.util.regex classes
 */
public class MatchUtil {

	private MatchUtil() {
	}

	public static interface MatchTransformer {
	}

	public static class CharSequenceFilter implements ICharSequenceFilter {

		private Iterable<Pattern> includes;
		private Iterable<Pattern> excludes;
		private CharSequenceFilter(Iterable<Pattern> includes, Iterable<Pattern> exculdes) {
			this.includes = includes;
			this.excludes = exculdes;
		}
	}


	public static Pattern compile1(String pat) {
		if (pat == null)
			return null;
		return Pattern.compile(pat);
	}


	public static boolean match(CharSequence input, Pattern includes, Pattern excludes) {
		if (includes != null) {
			if (!includes.matcher(input).matches())
				return false;
		}
		if (excludes != null) {
			if (excludes.matcher(input).matches())
				return false;
		}
		return true;
	}
}
