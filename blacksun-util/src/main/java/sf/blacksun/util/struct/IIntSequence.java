/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.struct;

//TEMPLATE_BEGIN


/**
 * IIntSequence is a read only random accessible sequence of integers.
 */
public interface IIntSequence extends IIntIterable {

	/** Get object from the head at given 0-based index. */
	int get(int index);
	void copyTo(int[] dst, int dststart, int srcstart, int srcend);
}
