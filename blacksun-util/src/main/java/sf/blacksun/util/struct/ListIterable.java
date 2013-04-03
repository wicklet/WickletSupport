/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.struct;

import java.util.Iterator;
import java.util.List;

public class ListIterable<T> implements Iterable<T>, Iterator<T> {

	private List<T> list;
	private int start;
	private int end;

	public ListIterable(List<T> list, int start, int end) {
		this.list = list;
		this.start = start;
		this.end = end;
	}

	public Iterator<T> iterator() {
		return this;
	}

	public boolean hasNext() {
		return start < end;
	}

	public T next() {
		return list.get(start++);
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
