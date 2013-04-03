/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.process;

public interface IProcessListener {

	int DEF_ERROR = -2;
	int TIMEOUT_ERROR = -3;

	void terminated(int exitcode, Throwable e);
	int exitValue();
}
