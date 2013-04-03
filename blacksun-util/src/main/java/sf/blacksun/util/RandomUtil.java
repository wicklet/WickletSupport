/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util;

import java.util.Random;

public class RandomUtil {

	////////////////////////////////////////////////////////////////////////

	private static class Initializer {
		static RandomUtil singleton = new RandomUtil();
	}
	private Random random;

	public static RandomUtil getSingleton() {
		return Initializer.singleton;
	}

	////////////////////////////////////////////////////////////////////////

	RandomUtil() {
		this(new Random());
	}

	public RandomUtil(Random r) {
		this.random = r;
	}


	public synchronized int getInt(int max) {
		if (max == 0)
			return 0;
		return random.nextInt(max);
	}

	public synchronized int getInt(int min, int max) {
		return getInt(max - min) + min;
	}


	public synchronized byte[] get(byte[] ret) {
		random.nextBytes(ret);
		return ret;
	}


	////////////////////////////////////////////////////////////////////////

	public synchronized char getASCII() {
		return (char)getInt(' ', 'z');
	}

	public synchronized char getLetter() {
		char c = 0;
		while (!Character.isLetter(c))
			c = getASCII();
		return c;
	}


	public synchronized char getLetterOrDigit() {
		char c = 0;
		while (!Character.isLetterOrDigit(c))
			c = getASCII();
		return c;
	}


	public synchronized String getString(int minlength, int maxlength) {
		int len = getInt(minlength, maxlength);
		char[] ret = new char[len];
		for (int i = 0; i < len; ++i)
			ret[i] = getASCII();
		return new String(ret);
	}


	public synchronized String getWord(int minlength, int maxlength) {
		int len = getInt(minlength, maxlength);
		char[] ret = new char[len];
		for (int i = 0; i < len; ++i) {
			ret[i] = (i == 0) ? getLetter() : getLetterOrDigit();
		}
		return new String(ret);
	}


	////////////////////////////////////////////////////////////////////////
}
