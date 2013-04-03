/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.process;

import static sf.blacksun.util.process.IProcessListener.DEF_ERROR;
import static sf.blacksun.util.process.IProcessListener.TIMEOUT_ERROR;

import java.util.concurrent.TimeoutException;





/**
 * Monitor a running process in a separate thread and notify listeners on
 * process I/O or termination. The current thread is not blocked.
 */
public class AbstractProcessMonitor<T extends IProcessMonitorListener> extends ProcessIOMonitor<T> {

	////////////////////////////////////////////////////////////////////////

	protected Process process; // java.lang.Process being monitored.

	////////////////////////////////////////////////////////////////////////

	protected AbstractProcessMonitor(T listener) {
		super(listener);
	}

	protected AbstractProcessMonitor(Process process, T listener) {
		super(listener);
		this.process = process;
	}


	protected boolean startIOMonitor() {
		try {
			startInMonitor(process.getOutputStream());
			startOutMonitor(process.getInputStream());
			startErrMonitor(process.getErrorStream());
			return true;
		} catch (Throwable e) {
			process.destroy();
			done(DEF_ERROR, e);
			process = null;
			return false;
	}}

	protected void monitorProcess() {
		int rc = DEF_ERROR;
		Throwable ex = null;
		try {
			rc = process.waitFor();
		} catch (InterruptedException e) {
			rc = TIMEOUT_ERROR;
			ex = new TimeoutException();
			process.destroy();
		} catch (Throwable e) {
			ex = e;
			process.destroy();
		} finally {
			try {
				joinIO();
			} catch (Throwable e) {
				rc = DEF_ERROR;
				ex = e;
			}
			done(rc, ex);
			process = null;
	}}


	protected void done(int rc, Throwable e) {
		listener.terminated(rc, e);
	}

	////////////////////////////////////////////////////////////////////////
}
