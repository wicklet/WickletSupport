/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;




/**
 * CharRange range inside a char[], indexing is relative to start of the input char[].
 * It maintain the start, end index of the range in the input char[].
 */
public class CharRange implements ICharSequence {

	private char[] text;
	private int start;
	private int end;

	public CharRange(char[] text) {
		this(text, 0, text.length);
	}

	public CharRange(char[] text, int start) {
		this(text, start, text.length);
	}

	public CharRange(char[] text, int start, int end) {
		this.text = text;
		this.start = start;
		this.end = end;
	}


	public int length() {
		return end - start;
	}


	public char charAt(int index) {
		index += this.start;
		if (index < this.start || index > this.end)
			throw new IndexOutOfBoundsException("Start=" + start + ", end=" + end + ", index=" + index);
		return text[index];
	}

	public CharSequence subSequence(int start, int end) {
		return new CharRange(text, start + this.start, end + this.start);
	}

	public String toString() {
		return new String(text, start, end - start);
	}
}
