/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.io;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringPrintWriter extends PrintWriter implements CharSequence {

	////////////////////////////////////////////////////////////////////

	private final StringWriter stringWriter;

	////////////////////////////////////////////////////////////////////

	public StringPrintWriter() {
		super(new StringWriter());
		stringWriter = (StringWriter)out;
	}

	public StringPrintWriter(final int initsize) {
		super(new StringWriter(initsize));
		stringWriter = (StringWriter)out;
	}

	public StringPrintWriter(final StringWriter w) {
		super(w);
		stringWriter = w;
	}

	////////////////////////////////////////////////////////////////////

	public StringBuffer getBuffer() {
		return stringWriter.getBuffer();
	}

	@Override
	public String toString() {
		return stringWriter.toString();
	}

	////////////////////////////////////////////////////////////////////

	@Override
	public char charAt(final int index) {
		return stringWriter.getBuffer().charAt(index);
	}

	@Override
	public int length() {
		return stringWriter.getBuffer().length();
	}

	@Override
	public CharSequence subSequence(final int start, final int end) {
		return stringWriter.getBuffer().subSequence(start, end);
	}

	////////////////////////////////////////////////////////////////////
}
