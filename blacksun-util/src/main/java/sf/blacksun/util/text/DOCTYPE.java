/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;

public enum DOCTYPE {
	HTML40(
		"HTML",
		"PUBLIC",
		"\"-//W3C//DTD HTML 4.0 Transitional//EN\"",
		"\"http://www.w3.org/TR/REC-html40/loose.dtd\""),
	HTML401("HTML", "PUBLIC", "\"-//W3C//DTD HTML 4.01//EN\"", "\"http://www.w3.org/TR/html4/strict.dtd\""),
	HTML401Transitional(
		"HTML",
		"PUBLIC",
		"\"-//W3C//DTD HTML 4.01 Transitional//EN\"",
		"\"http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd\""),
	HTML401Frameset(
		"HTML",
		"PUBLIC",
		"\"-//W3C//DTD HTML 4.01 Frameset//EN\"",
		"\"http://www.w3.org/TR/1999/REC-html401-19991224/frameset.dtd\""),
	XHTML10(
		"html",
		"PUBLIC",
		"\"-//W3C//DTD XHTML 1.0 Strict//EN\"",
		"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\""),
	XHTML10Transitional(
		"html",
		"PUBLIC",
		"\"-//W3C//DTD XHTML 1.0 Transitional//EN\"",
		"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\""),
	XHTML10Frameset(
		"html",
		"PUBLIC",
		"\"-//W3C//DTD XHTML 1.0 Frameset//EN\"",
		"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\""),
	XHTML11(
		"html", "PUBLIC", "\"-//W3C//DTD XHTML 1.1 Strict//EN\"", "\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\""),
	HTML5("html");

	private String[] content;
	private DOCTYPE(String...content) {
		this.content = content;
	}
	public String[] content() {
		return content.clone();
	}
	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append("<!DOCTYPE");
		for (String s: content) {
			ret.append(' ');
			ret.append(s);
		}
		ret.append(">");
		return ret.toString();
	}
}
