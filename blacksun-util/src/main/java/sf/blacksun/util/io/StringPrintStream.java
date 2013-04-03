/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.io;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StringPrintStream extends PrintStream {
	public StringPrintStream() {
		super(new ByteArrayOutputStream());
	}

	public String toString() {
		return ((ByteArrayOutputStream)out).toString();
	}
}
