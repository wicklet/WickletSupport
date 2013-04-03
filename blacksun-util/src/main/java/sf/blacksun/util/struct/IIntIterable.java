/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.struct;

//TEMPLATE_BEGIN



/** IIntIterable is a read only, iterable sequence of integers. */
public interface IIntIterable {
	int size();
	int[] toArray();
}
