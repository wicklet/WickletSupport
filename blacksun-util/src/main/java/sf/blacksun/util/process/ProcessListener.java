/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.process;

import java.util.concurrent.CountDownLatch;

public class ProcessListener implements IProcessListener {

	////////////////////////////////////////////////////////////////////////

	private int exitValue;
	private Throwable throwable;
	private CountDownLatch terminated = new CountDownLatch(1);

	////////////////////////////////////////////////////////////////////////

	public ProcessListener() {
	}

	////////////////////////////////////////////////////////////////////////

	public synchronized void terminated(int exitcode, Throwable e) {
		if (false)
			new Throwable().printStackTrace(System.out);
		this.exitValue = exitcode;
		this.throwable = e;
		this.terminated.countDown();
		if (exitcode == 0 && e != null)
			throw new AssertionError("ERROR: no exception is expected when exitcode is 0");
	}


	@Override
	public synchronized int exitValue() {
		return exitValue;
	}


	////////////////////////////////////////////////////////////////////////
}
