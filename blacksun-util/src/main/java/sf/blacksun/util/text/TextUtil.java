/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;

import java.io.File;

import java.io.Serializable;






import java.util.ArrayList;

import java.util.Comparator;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;





import sf.blacksun.util.struct.IIntList;




/**
 * Static text utility methods.
 *
 * TODO: . Remove synchronized for functions that are classified as synchronized just because of using Perl5Util
 * re.
 */
public class TextUtil {

	////////////////////////////////////////////////////////////////////////

	private static class CharsetInitializer {
	}
	private static char SEP = File.separatorChar;
	private static String LINE_SEP;
	private static Map<String, String> filepathEscapeChars = new Hashtable<String, String>();
	private final static char[] HEX_LOWER = "0123456789abcdef".toCharArray();
	static {
		// ";", "/", "?", ":", "@", "&", "=", "+", "$", ",", # reserved
		filepathEscapeChars.put("~", "^7e");
		filepathEscapeChars.put("!", "^21");
		filepathEscapeChars.put("$", "^24");
		filepathEscapeChars.put("&", "^26");
		filepathEscapeChars.put("*", "^2a");
		filepathEscapeChars.put("(", "^28");
		filepathEscapeChars.put(")", "^29");
		filepathEscapeChars.put("[", "^5b");
		filepathEscapeChars.put("]", "^5d");
		filepathEscapeChars.put("|", "^7c");
		filepathEscapeChars.put("?", "^3f");
		filepathEscapeChars.put(";", "^3b");
		filepathEscapeChars.put("<", "^3c");
		filepathEscapeChars.put(">", "^3e");
		filepathEscapeChars.put("\"", "^22");
		filepathEscapeChars.put("'", "^27");
		filepathEscapeChars.put("^7e", "~");
		filepathEscapeChars.put("^21", "!");
		filepathEscapeChars.put("^24", "$");
		filepathEscapeChars.put("^26", "&");
		filepathEscapeChars.put("^2a", "*");
		filepathEscapeChars.put("^28", "(");
		filepathEscapeChars.put("^29", ")");
		filepathEscapeChars.put("^5b", "[");
		filepathEscapeChars.put("^5d", "]");
		filepathEscapeChars.put("^7c", "|");
		filepathEscapeChars.put("^3f", "?");
		filepathEscapeChars.put("^3b", ";");
		filepathEscapeChars.put("^3c", "<");
		filepathEscapeChars.put("^3e", ">");
		filepathEscapeChars.put("^22", "\"");
		filepathEscapeChars.put("^27", "'");
	}

	protected TextUtil() {
	}


	public static String getLineSeparator() {
		if (LINE_SEP == null)
			LINE_SEP = System.getProperty("line.separator");
		return LINE_SEP;
	}


	public static boolean equals(String a, String b) {
		if (a == null)
			return b == null;
		return a.equals(b);
	}


	public static int compare(CharSequence a, CharSequence b) {
		if (a == null)
			return b == null ? 0 : -1;
		if (b == null)
			return 1;
		int alen = a.length();
		int blen = b.length();
		int len = Math.min(alen, blen);
		char ca, cb;
		for (int i = 0; i < len; ++i) {
			ca = a.charAt(i);
			cb = b.charAt(i);
			if (ca > cb)
				return 1;
			if (cb > ca)
				return -1;
		}
		return (alen > blen) ? 1 : (blen > alen) ? -1 : 0;
	}


	public static String joinln(String...a) {
		return join(getLineSeparator(), a);
	}


	public static List<String> split(StringTokenizer tok) {
		List<String> ret = new ArrayList<String>();
		while (tok.hasMoreTokens())
			ret.add(tok.nextToken());
		return ret;
	}


	public static List<String> splitLines(CharSequence str) {
		return splitLines(str, false);
	}

	public static List<String> splitLines(CharSequence str, boolean esc) {
		if (str == null)
			return null;
		List<String> ret = new ArrayList<String>();
		int start = 0;
		int len = str.length();
		char c;
		for (int i = 0; i < len; ++i) {
			c = str.charAt(i);
			if (esc && c == '\\') {
				++i;
				if (i < len && str.charAt(i) == '\r' && (i + 1 < len) && str.charAt(i + 1) == '\n')
					++i;
				continue;
			}
			if (c == '\r' || c == '\n') {
				ret.add(str.subSequence(start, i).toString());
				if (c == '\r' && i + 1 < len && str.charAt(i + 1) == '\n')
					++i;
				start = i + 1;
		}}
		if (start < str.length())
			ret.add(str.subSequence(start, len).toString());
		return ret;
	}


	public static String removeTail(String s, String tail) {
		if (s != null && s.endsWith(tail))
			return s.substring(0, s.length() - tail.length());
		return s;
	}


	/**
	 * @return -1 on error.
	 */
	public static int hexToByte(char c) {
		if (c < '0')
			return -1;
		if (c <= '9')
			return c - '0';
		if (c < 'A')
			return -1;
		if (c <= 'F')
			return c - 'A' + 10;
		if (c < 'a')
			return -1;
		if (c <= 'f')
			return c - 'a' + 10;
		return -1;
	}

	/**
	 * Convert hex string to int.
	 */
	public static int hexToInt(CharSequence s, int def) {
		int ret = 0;
		int n = 0;
		int len = s.length();
		char c;
		if (len >= 2 && s.charAt(0) == '0') {
			c = s.charAt(1);
			if (c == 'x' || c == 'X')
				n = 2;
		}
		if (len - n > 8) {
			// error("msg.hexToInt(): hex too big for int: " + s);
			return def;
		}
		int v;
		for (; n < len; ++n) {
			if ((v = hexToByte(s.charAt(n))) < 0)
				return def;
			ret = (ret << 4) | v;
		}
		return ret;
	}


	public static String toLowerHex4(int n) {
		return ""
			+ HEX_LOWER[(n >> 12) & 0xf]
			+ HEX_LOWER[(n >> 8) & 0xf]
			+ HEX_LOWER[(n >> 4) & 0xf]
			+ HEX_LOWER[n & 0xf];
	}


	public static String toLowerHex8(int n) {
		return toLowerHex4((short)(n >> 16)) + toLowerHex4((short)(n & 0xffff));
	}


	/** Fixup file path to full path if pwd != null and remove redundant chars. */
	public static String normalizeFilePath(String path, String pwd) {
		int len = path.length();
		StringBuilder b = new StringBuilder();
		if (pwd != null && (len == 0 || path.charAt(0) != SEP)) {
			b.append(pwd);
			if (lastChar(pwd) != SEP)
				b.append(SEP);
		}
		b.append(path);
		cleanupFilePath(b);
		return b.toString();
	}


	/**  Remove duplicated /, /./ and /../ */
	public static void cleanupFilePath(StringBuilder b) {
		char c;
		int last = -1;
		int len = 0;
		int max = b.length();
		for (int i = 0; i < max; ++i) {
			c = b.charAt(i);
			if ((last == SEP || len == 0)
				&& c == '.'
				&& (((i + 1 < max) && b.charAt(i + 1) == SEP) || i + 1 >= max)) {
				// s~^\./~~
				++i;
				continue;
			}
			if (last == SEP && c == SEP) {
				// s~//~/~
				continue;
			}
			if (last == SEP
				&& c == '.'
				&& i + 1 < max
				&& b.charAt(i + 1) == '.'
				&& len >= 2
				&& (i + 2 >= max || b.charAt(i + 2) == SEP)) {
				int index = b.lastIndexOf(File.separator, len - 2);
				if (!"../".equals(b.substring(index + 1, len))) {
					len = index + 1;
					++i;
					continue;
			}}
			b.setCharAt(len++, c);
			last = c;
		}
		b.setLength(len);
	}


	/**
	 * Return dir part of a file path. eg. dir1/dir2/file.java return dir1/dir2
	 */
	public static String dirName(String path) {
		int index = path.lastIndexOf(SEP);
		if (index >= 0)
			return path.substring(0, index);
		return null;
	}

	/**
	 * Return name part of a file path. eg. dir1/dir2/file.java return file.java
	 */
	public static String fileName(String path) {
		int index = path.lastIndexOf(SEP);
		if (index >= 0) {
			path = path.substring(index + 1);
			if (path.length() == 0)
				return null;
		}
		return path;
	}


	/**
	 * Return base name part of a file path. eg. dir1/dir2/file.java return file.
	 */
	public static String baseName(String path) {
		path = fileName(path);
		int index = path.lastIndexOf('.');
		if (index < 0)
			return path;
		return path.substring(0, index);
	}


	////////////////////////////////////////////////////////////////////////

	public static int lastChar(CharSequence s) {
		int len = s.length();
		if (len == 0)
			return -1;
		return s.charAt(len - 1);
	}


	////////////////////////////////////////////////////////////////////////

	//	public static int[] differIgnoringWhitespaces(CharSequence str1, CharSequence str2) {
	//		return differIgnoringWhitespaces(str1, str2, null, false, false);
	//	}
	//
	//	public static int[] differIgnoringWhitespaces(CharSequence str1, CharSequence str2, boolean check_alphanum) {
	//		return differIgnoringWhitespaces(str1, str2, null, check_alphanum, false);
	//	}
	//
	//	public static int[] differIgnoringWhitespaces(
	//		CharSequence str1, CharSequence str2, boolean check_alphanum, boolean ignorecase) {
	//		return differIgnoringWhitespaces(str1, str2, null, check_alphanum, ignorecase);
	//	}
	//
	//	public static int[] differIgnoringWhitespaces(
	//		CharSequence str1, CharSequence str2, IntList offsets, boolean check_alphanum) {
	//		return differIgnoringWhitespaces(str1, str2, offsets, check_alphanum, false);
	//	}

	public static int[] differIgnoringWhitespaces(
		CharSequence str1, CharSequence str2, IIntList offsets, boolean ignorecase) {
		return differIgnoringWhitespaces(
			str1,
			str2,
			offsets,
			ignorecase,
			DefaultNameDetector.getSingleton(),
			DefaultSpaceUtil.getSingleton());
	}

	/**
	 * Find first offset of the difference between the two string ignoring all whitespaces.
	 * @param offsets	If not null, return list of all whitespace boundaries pairs that differ { start1, end1, start2, end2 }.
	 * @return Offsets of the first difference in str1 and str2 respectively, null if strings are same.
	 */
	public static int[] differIgnoringWhitespaces(
		CharSequence str1,
		CharSequence str2,
		IIntList offsets,
		boolean ignorecase,
		INameDetector namer,
		ISpaceDetector spacer) {
		char c1 = 0;
		char c2 = 0;
		int end1 = 0;
		int end2 = 0;
		int wend1 = 0;
		int wend2 = 0;
		int len1 = str1.length();
		int len2 = str2.length();
		int start1, start2;
		boolean a1 = false;
		boolean a2 = false;
		FAIL: {
			for (; end1 < len1 && end2 < len2;) {
				start1 = end1;
				start2 = end2;
				while (end1 < len1 && spacer.isWhitespace(c1 = str1.charAt(end1))) {
					++end1;
				}
				while (end2 < len2 && spacer.isWhitespace(c2 = str2.charAt(end2))) {
					++end2;
				}
				if (offsets != null && (end1 != start1 || end2 != start2)) {
					if (!match(str1, start1, end1, str2, start2, end2)) {
						offsets.add(start1);
						offsets.add(end1);
						offsets.add(start2);
						offsets.add(end2);
				}}
				if (end1 >= len1 || end2 >= len2)
					break;
				wend1 = end1 + 1;
				wend2 = end2 + 1;
				while (wend1 < len1 && !spacer.isWhitespace(str1.charAt(wend1)))
					++wend1;
				int wlen1 = wend1 - end1;
				int len = wlen1;
				while ((--len > 0) && wend2 < len2 && !spacer.isWhitespace(str2.charAt(wend2)))
					++wend2;
				int wlen2 = wend2 - end2;
				if (wlen2 < wlen1) {
					wend1 = end1 + wlen2;
				}
				if (!match(str1, end1, wend1, str2, end2, wend2)) {
					if (!ignorecase)
						break FAIL;
					if (!matchIgnoreCase(str1, end1, wend1, str2, end2, wend2))
						break FAIL;
					if (offsets != null) {
						offsets.add(end1);
						offsets.add(wend1);
						offsets.add(end2);
						offsets.add(wend2);
				}}
				if (namer != null) {
					if (end1 == start1 && end2 != start2) {
						if (a1 && namer.isNamePart(c1))
							break FAIL;
					}
					if (end2 == start2 && end1 != start1) {
						if (a2 && namer.isNamePart(c2))
							break FAIL;
					}
					a1 = namer.isNamePart(str1.charAt(wend1 - 1));
					a2 = namer.isNamePart(str2.charAt(wend2 - 1));
				}
				end1 = wend1;
				end2 = wend2;
				if (wend1 >= len1 || wend2 >= len2) {
					break;
			}}
			// If one of the str ended early, make sure trailing spaces in the other string is ignored.
			start1 = end1;
			start2 = end2;
			end1 = spacer.skipWhitespaces(str1, end1, len1);
			end2 = spacer.skipWhitespaces(str2, end2, len2);
			if (offsets != null && (end1 != start1 || end2 != start2)) {
				if (!match(str1, start1, end1, str2, start2, end2)) {
					offsets.add(start1);
					offsets.add(end1);
					offsets.add(start2);
					offsets.add(end2);
			}}
			if (end1 == len1 && end2 == len2)
				return null;
		}
		return new int[] { end1, end2 };
	}


	public static boolean match(CharSequence str1, int start1, int end1, CharSequence str2, int start2, int end2) {
		int len = end1 - start1;
		if (end2 - start2 != len)
			return false;
		for (int i = len - 1; i >= 0; --i) {
			if (str1.charAt(start1 + i) != str2.charAt(start2 + i))
				return false;
		}
		return true;
	}


	public static boolean matchIgnoreCase(
		CharSequence str1, int start1, int end1, CharSequence str2, int start2, int end2) {
		int len = end1 - start1;
		if (end2 - start2 != len)
			return false;
		char c1, c2;
		for (int i = len - 1; i >= 0; --i) {
			c1 = str1.charAt(start1 + i);
			c2 = str2.charAt(start2 + i);
			if (c1 != c2 && c1 != Character.toLowerCase(c2) && c1 != Character.toUpperCase(c2))
				return false;
		}
		return true;
	}


	/**
	 * @param start Start offset for column 1.
	 * @param end End offfset whose column number is to be determined.
	 * @return 1-based column number of 'end'.
	 */
	public static int columnOf(CharSequence str, int start, int end, int tabwidth) {
		int ret = 0;
		char c;
		for (; start < end; ++start) {
			c = str.charAt(start);
			if (c == '\r' || c == '\n')
				ret = 0;
			else if (c == '\t')
				ret = (ret / tabwidth + 1) * tabwidth;
			else
				++ret;
		}
		return ret + 1;
	}


	public static CharSequence toLowerHex(byte[] a) {
		return toLowerHex(a, 0, a.length);
	}

	public static CharSequence toLowerHex(byte[] a, int start, int end) {
		StringBuilder b = new StringBuilder();
		for (; start < end; ++start) {
			byte v = a[start];
			b.append(HEX_LOWER[(v >>> 4) & 0xf]);
			b.append(HEX_LOWER[v & 0xf]);
		}
		return b;
	}


	public static String toString(Object a) {
		return a == null ? "null" : a.toString();
	}


	public static <T> String sprintArray(T...a) {
		StringBuilder ret = new StringBuilder();
		boolean first = true;
		for (T e: a) {
			if (first)
				first = false;
			else
				ret.append(", ");
			ret.append(toString(e));
		}
		return ret.toString();
	}


	public static <T> String join(String sep, Iterable<T> a) {
		StringBuffer ret = new StringBuffer();
		boolean first = true;
		for (T s: a) {
			if (first)
				first = false;
			else
				ret.append(sep);
			ret.append(s);
		}
		return ret.toString();
	}

	public static <T> String join(String sep, T...a) {
		StringBuffer ret = new StringBuffer();
		boolean first = true;
		for (T s: a) {
			if (first)
				first = false;
			else
				ret.append(sep);
			ret.append(s);
		}
		return ret.toString();
	}

	public static String join(String sep, String[] a, int start, int end) {
		int len = end - start;
		if (len == 0)
			return "";
		if (len == 1)
			return a[0];
		StringBuilder b = new StringBuilder();
		for (int i = start; i < end; ++i) {
			if (i != 0)
				b.append(sep);
			b.append(a[i]);
		}
		return b.toString();
	}

	public static String join(String sep, String...a) {
		return join(sep, a, 0, a.length);
	}


	public static boolean isEmpty(CharSequence s) {
		return s == null || s.length() == 0;
	}


	////////////////////////////////////////////////////////////////////////

	public static class Basename {
		public String dir;
		public String name;
		public String base;
		public String ext;
		public Basename(String dir, String name, String base, String ext) {
			this.dir = dir;
			this.name = name;
			this.base = base;
			this.ext = ext;
		}
	}


	////////////////////////////////////////////////////////////////////////

	public static class ManifestValueScanner implements Iterator<String>, Iterable<String> {
		CharSequence value;
		int length;
		int start;
		int end;
		public ManifestValueScanner(CharSequence value) {
			this.value = value;
			this.length = value.length();
			this.start = 0;
			this.end = 0;
		}
		public Iterator<String> iterator() {
			return this;
		}
		public boolean hasNext() {
			return start < length;
		}
		public String next() {
			while (end < length) {
				char c = value.charAt(end);
				if (c == '"') {
					skipString('"');
					continue;
				} else if (c == ',') {
					String ret = value.subSequence(start, end).toString();
					++end;
					start = end;
					return ret;
				}
				++end;
			}
			String ret = value.subSequence(start, end).toString();
			start = end;
			return ret;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		private void skipString(char delim) {
			for (++end; end < length; ++end) {
				if (value.charAt(end) == delim) {
					++end;
					return;
		}}}
	}

	////////////////////////////////////////////////////////////////////////

	private static class CharSequenceComparator implements Comparator<CharSequence>, Serializable {
		private static final long serialVersionUID = -1;
		public int compare(CharSequence a, CharSequence b) {
			if (a == null)
				return b == null ? 0 : -1;
			int alen = a.length();
			int blen = b.length();
			int len = Math.min(alen, blen);
			for (int i = 0; i < len; ++i) {
				int d = a.charAt(i) - b.charAt(i);
				if (d != 0)
					return d;
			}
			return alen - blen;
		}
	}

	private static class StringComparator implements Comparator<String>, Serializable {
		private static final long serialVersionUID = -4106319796409788626L;
		public int compare(String a, String b) {
			if (a == null)
				return b == null ? 0 : -1;
			return a.compareTo(b);
		}
	}

	private static class StringIgnorecaseComparator implements Comparator<String>, Serializable {
		private static final long serialVersionUID = -1698275111742700972L;
		public int compare(String a, String b) {
			if (a == null)
				return b == null ? 0 : -1;
			return a.compareToIgnoreCase(b);
		}
	}

	////////////////////////////////////////////////////////////////////////
}
