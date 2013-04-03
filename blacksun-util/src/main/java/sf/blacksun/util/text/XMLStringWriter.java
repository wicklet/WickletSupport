/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;

import sf.blacksun.util.io.StringPrintWriter;

public class XMLStringWriter extends XMLWriter {
	private final StringPrintWriter stringWriter;
	public XMLStringWriter() {
		super(new StringPrintWriter());
		stringWriter = (StringPrintWriter)writer;
	}
	@Override
    public String toString() {
		close();
		return stringWriter.toString();
	}
}
