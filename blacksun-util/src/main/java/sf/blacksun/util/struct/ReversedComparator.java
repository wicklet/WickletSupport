/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.struct;

import java.io.Serializable;
import java.util.Comparator;

public class ReversedComparator<T> implements Comparator<T>, Serializable {

	private static final long serialVersionUID = 3290237494982860181L;

	private Comparator<T> comparator;

	public ReversedComparator(Comparator<T> c) {
		this.comparator = c;
	}

	public int compare(T a, T b) {
		return comparator.compare(b, a);
	}
}
