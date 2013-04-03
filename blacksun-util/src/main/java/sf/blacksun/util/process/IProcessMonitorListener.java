/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.process;

import java.io.Reader;

public interface IProcessMonitorListener extends IProcessListener {

	void out(char[] s, int start, int end);
	void err(char[] s, int start, int end);
	void err(CharSequence s);
	Reader getInput();
}
