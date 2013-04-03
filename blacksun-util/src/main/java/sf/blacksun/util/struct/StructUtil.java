/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.struct;

import java.util.ArrayList;

import java.util.Collection;



import java.util.Iterator;
import java.util.List;

import java.util.Set;

import java.util.TreeSet;

/**
 * Some static utilities for data structure manipulation.
 */
public class StructUtil {


	public static <T> Set<T> toTreeSet(final T...a) {
		final Set<T> ret = new TreeSet<T>();
		for (final T s: a) {
			ret.add(s);
		}
		return ret;
	}


	public static <T> List<T> toList(final T...a) {
		final List<T> ret = new ArrayList<T>(a.length);
		for (final T s: a) {
			ret.add(s);
		}
		return ret;
	}


	////////////////////////////////////////////////////////////////////////

	public static abstract class CollectionGenerator<T> implements Runnable, Iterable<T> {
		protected Collection<T> ret;
		private boolean initialized;
		protected CollectionGenerator(final Collection<T> ret) {
			this.ret = ret;
		}
		@Override
		public Iterator<T> iterator() {
			if (!initialized) {
				run();
			}
			return ret.iterator();
		}
	}

	////////////////////////////////////////////////////////////////////////
}
