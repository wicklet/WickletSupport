/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.struct;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IterableWrapper {

	private IterableWrapper() {
	}


	public static <T> Iterable<T> wrap(T...a) {
		return a == null ? null : new ArrayWrapper<T>(a, 0, a.length);
	}


	private static class EnumerationIterable<T> implements Iterable<T>, Iterator<T> {
		private Enumeration<?> e;
		public EnumerationIterable(Enumeration<?> e) {
			this.e = e;
		}
		public Iterator<T> iterator() {
			return this;
		}
		public boolean hasNext() {
			return e.hasMoreElements();
		}
		@SuppressWarnings("unchecked")
		public T next() {
			return (T)e.nextElement();
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private static class IteratorWrapper<T> implements Iterable<T>, Iterator<T> {
		private Iterator<? extends T> it;
		public IteratorWrapper(Iterator<? extends T> it) {
			this.it = it;
		}
		public Iterator<T> iterator() {
			return this;
		}
		public boolean hasNext() {
			return it.hasNext();
		}
		public T next() {
			return it.next();
		}
		public void remove() {
			it.remove();
		}
	}

	private static class ArrayWrapper<T> implements Iterable<T>, Iterator<T> {
		protected T[] a;
		protected int start;
		protected int end;
		public ArrayWrapper(T[] a, int start, int end) {
			this.a = a;
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
			if (start >= end)
				throw new NoSuchElementException();
			return a[start++];
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private static class ArrayCastWrapper<T> implements Iterable<T>, Iterator<T> {
		private Object[] a;
		private int start;
		private int end;
		public ArrayCastWrapper(Object[] a, int start, int end) {
			this.a = a;
			this.start = start;
			this.end = end;
		}
		public Iterator<T> iterator() {
			return this;
		}
		public boolean hasNext() {
			return start < end;
		}
		@SuppressWarnings("unchecked")
		public T next() {
			if (start >= end)
				throw new NoSuchElementException();
			return (T)a[start++];
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private static class CountableArrayWrapper<T> extends ArrayWrapper<T> implements ICountableIterable<T> {
		private int size;
		public CountableArrayWrapper(T[] a, int start, int end) {
			super(a, start, end);
			this.size = end - start;
		}
	}
}
