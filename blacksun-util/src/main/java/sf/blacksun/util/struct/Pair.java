/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.struct;




/**
 * Wrapper class to hold two values.
 */
public class Pair<F, S> {
	public F first;
	public S second;
	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}
	public F first() {
		return first;
	}
	public S second() {
		return second;
	}

	@Override
	public int hashCode() {
		return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
	}
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Pair))
			return false;
		Pair<F, S> p = (Pair<F, S>)o;
		return (first == null && p.first == null || first != null && first.equals(p.first))
			&& (second == null && p.second == null || second != null && second.equals(p.second));
	}
	@Override
	public String toString() {
		return (first == null ? "null," : first.toString() + ',') + second;
	}

	public static class StringPair extends Pair<String, String> {
		public StringPair(String first, String second) {
			super(first, second);
		}
	}
}
