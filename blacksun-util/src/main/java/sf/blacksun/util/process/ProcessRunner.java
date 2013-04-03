/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.process;

import static sf.blacksun.util.process.IProcessListener.DEF_ERROR;

import java.io.File;

/**
 * Similar to ProcessExecutor but not spawn a separate thread to wait for process termination.
 * Use this when process is being executed on a worker thread instead of the main thread.
 */
public class ProcessRunner<T extends IProcessMonitorListener>
	extends AbstractProcessMonitor<T> implements IProcessCallable<T> {

	////////////////////////////////////////////////////////////////////////

	private File workdir;
	private String[] env;
	protected String[] cmdline;

	////////////////////////////////////////////////////////////////////////

	public ProcessRunner(T listener, File workdir, String...cmdline) {
		super(listener);
		this.workdir = workdir;
		this.cmdline = cmdline.clone();
	}


	////////////////////////////////////////////////////////////////////////

	public int exec() {
		return exec(cmdline);
	}


	////////////////////////////////////////////////////////////////////////

	protected int exec(String[] cmdline) {
		if (this.process != null) {
			IllegalStateException e = new IllegalStateException("ERROR: process already started");
			listener.terminated(DEF_ERROR, e);
			throw e;
		}
		try {
			this.process = Runtime.getRuntime().exec(cmdline, env, workdir);
		} catch (Throwable e) {
			listener.terminated(DEF_ERROR, e);
			return DEF_ERROR;
		}
		if (startIOMonitor()) {
			monitorProcess();
		}
		//		// join() may not be neccessary
		//		// as all execution paths should have called listener.terminated() synchronously.
		//		listener.join();
		return listener.exitValue();
	}

	@Override
	public T call() throws Exception {
		exec();
		return listener;
	}

	////////////////////////////////////////////////////////////////////////
}
