/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;







import java.util.Iterator;

import java.util.TreeMap;















import org.w3c.dom.Element;

import org.w3c.dom.Node;







/**
 * Static utilities for XML processing.
 */
public class XmlUtil {

	private static final TreeMap<String, Integer> CHARREFS = new TreeMap<String, Integer>();
	static {
		CHARREFS.put("amp", (int)'&');
		CHARREFS.put("lt", (int)'<');
		CHARREFS.put("gt", (int)'>');
		CHARREFS.put("quot", (int)'"');
		CHARREFS.put("apos", (int)'\'');
	}


	/**
	 * Escape XML reserved characters in text.
	 */
	public static CharSequence escTextStrict(final CharSequence value) {
		if (value == null) {
			return "";
		}
		final StringBuffer ret = new StringBuffer();
		for (int i = 0, len = value.length(); i < len; i++) {
			final char c = value.charAt(i);
			switch (c) {
			case '&':
				if (!isXmlEntityRef(value, i)) {
					ret.append("&amp;");
				} else {
					ret.append('&');
				}
				break;
			case '<':
				ret.append("&lt;");
				break;
			case '>':
				ret.append("&gt;");
				break;
			default :
				ret.append(c);
		}}
		return ret;
	}

	/**
	 * Escape XML reserved characters in text without checking for xmlEntityRef.
	 */
	public static CharSequence escXml(final CharSequence value) {
		if (value == null) {
			return "";
		}
		final StringBuilder ret = new StringBuilder();
		for (int i = 0, len = value.length(); i < len; i++) {
			final char c = value.charAt(i);
			switch (c) {
			case '&':
				ret.append("&amp;");
				break;
			case '<':
				ret.append("&lt;");
				break;
			case '>':
				ret.append("&gt;");
				break;
			case '"':
				ret.append("&quot;");
				break;
			case '\'':
				ret.append("&#39;");
				break;
			default :
				ret.append(c);
		}}
		return ret.length() != value.length() ? ret : value;
	}

	public static CharSequence escAttrValue(final CharSequence value) {
		return escAttrValue(value, '"');
	}

	/**
	 * Escape XML reserved characters in attribute values.
	 */
	public static CharSequence escAttrValue(final CharSequence value, final char delim) {
		if (value == null) {
			return "";
		}
		final StringBuilder ret = new StringBuilder();
		escAttrValue(ret, value, delim);
		return ret;
	}

	public static void escAttrValue(final StringBuilder ret, final CharSequence value, final char delim) {
		for (int i = 0, len = value.length(); i < len; i++) {
			final char c = value.charAt(i);
			switch (c) {
			case '&':
				if (isXmlEntityRef(value, i)) {
					ret.append(c);
				} else {
					ret.append("&amp;");
				}
				break;
			case '<':
				ret.append("&lt;");
				break;
			case '>':
				ret.append("&gt;");
				break;
			case '\'':
				if (c == delim) {
					ret.append("&apos;");
				} else {
					ret.append(c);
				}
				break;
			case '"':
				if (c == delim) {
					ret.append("&quot;");
				} else {
					ret.append(c);
				}
				break;
			case '%':
				ret.append("&#37;");
				break;
			default :
				ret.append(c);
	}}}


	/**
	 * @return true if substring started at 'start' is a XML entity reference.
	 */
	public static boolean isXmlEntityRef(final CharSequence str, int start) {
		final int len = str.length();
		if (start >= len || str.charAt(start) != '&') {
			return false;
		}
		if (++start >= len) {
			return false;
		}
		if (str.charAt(start) == '#') {
			return isXmlCharRefPart(str, start + 1);
		}
		int i = start;
		if (!isNameStart(str.charAt(i))) {
			return false;
		}
		for (++i; i < len; ++i) {
			if (!isName(str.charAt(i))) {
				break;
		}}
		return (i > start && i < len && str.charAt(i) == ';');
	}

	/**
	 * NOTE: No well-formness check for the referenced char.
	 *
	 * @param start Index to char after "&#".
	 */
	public static boolean isXmlCharRefPart(final CharSequence str, int start) {
		final int len = str.length();
		if (start >= len) {
			return false;
		}
		char c;
		if (str.charAt(start) == 'x') {
			// &#xhex;
			++start;
			int i = start;
			for (; i < len; ++i) {
				c = str.charAt(i);
				if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) {
					continue;
				}
				break;
			}
			return (i > start && i < len && str.charAt(i) == ';');
		}
		// &#dec;
		int i = start;
		for (; i < len; ++i) {
			c = str.charAt(i);
			if (c < '0' || c > '9') {
				break;
		}}
		return (i > start && i < len && str.charAt(i) == ';');
	}


	////////////////////////////////////////////////////////////////////

	public static boolean isNameStart(final int c) {
		return c == ':'
			|| c >= 'A' && c <= 'Z'
			|| c == '_'
			|| c >= 'a' && c <= 'z'
			|| c >= '\u00C0' && c <= '\u00D6'
			|| c >= '\u00D8' && c <= '\u00F6'
			|| c >= '\u00F8' && c <= '\u02FF'
			|| c >= '\u0370' && c <= '\u037D'
			|| c >= '\u037F' && c <= '\u1FFF'
			|| c >= '\u200C' && c <= '\u200D'
			|| c >= '\u2070' && c <= '\u218F'
			|| c >= '\u2C00' && c <= '\u2FEF'
			|| c >= '\u3001' && c <= '\uD7FF'
			|| c >= '\uF900' && c <= '\uFDCF'
			|| c >= '\uFDF0' && c <= '\uFFFD'
		// | '\x00010000'..'\x000EFFFF'
		;
	}

	public static boolean isName(final int c) {
		return isNameStart(c)
			|| c == '-'
			|| c == '.'
			|| c >= '0' && c <= '9'
			|| c == '\u00B7'
			|| c >= '\u0300' && c <= '\u036F'
			|| c >= '\u203F' && c <= '\u2040';
	}

	////////////////////////////////////////////////////////////////////

	//	private static void replaceAll(StringBuffer buf, String find, String replace) {
	//		for (int index = buf.indexOf(find); index >= 0; index = buf.indexOf(find, index + replace.length())) {
	//			buf.replace(index, index + find.length(), replace);
	//	}}

	//	private static void replaceAll(StringBuilder buf, String find, String replace) {
	//		for (int index = buf.indexOf(find); index >= 0; index = buf.indexOf(find, index + replace.length())) {
	//			buf.replace(index, index + find.length(), replace);
	//	}}

	private static class NodeIterable implements Iterable<Node>, Iterator<Node> {
		private Node prev;
		private Node child;
		public NodeIterable(final Node n) {
			child = n.getFirstChild();
		}
		@Override
		public Iterator<Node> iterator() {
			return this;
		}
		@Override
		public boolean hasNext() {
			return child != null;
		}
		@Override
		public Node next() {
			prev = child;
			child = child.getNextSibling();
			return prev;
		}
		@Override
		public void remove() {
			prev.getParentNode().removeChild(prev);
		}
	}

	private static class ElementIterable implements Iterable<Element>, Iterator<Element> {
		private Element prev;
		private Element child;
		public ElementIterable(final Node n) {
			child = getElement(n.getFirstChild());
		}
		@Override
		public Iterator<Element> iterator() {
			return this;
		}
		@Override
		public boolean hasNext() {
			return child != null;
		}
		@Override
		public Element next() {
			prev = child;
			child = getElement(child.getNextSibling());
			return prev;
		}
		@Override
		public void remove() {
			prev.getParentNode().removeChild(prev);
		}
		private Element getElement(Node c) {
			while (c != null && c.getNodeType() != Node.ELEMENT_NODE) {
				c = c.getNextSibling();
			}
			return (Element)c;
		}
	}

	// HACK: From org.apache.xml.internal.serializer.OutputPropertiesFactory.class
	private static class OutputPropertiesFactory {
	}

	protected static class Attribute implements IAttribute {
		private final String name;
		private final String value;
		public Attribute(final String name, final String value) {
			this.name = name;
			this.value = value;
		}
		@Override
		public String name() {
			return name;
		}
		@Override
		public String value() {
			return value;
		}
	}

	////////////////////////////////////////////////////////////////////
}
