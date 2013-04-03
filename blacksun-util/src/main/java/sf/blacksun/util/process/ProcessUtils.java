/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.process;

import java.io.File;
import java.io.PrintWriter;
import java.io.Reader;










public class ProcessUtils {

	////////////////////////////////////////////////////////////////////////

	private ProcessUtils() {
	}


	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// Ignore
	}}


	////////////////////////////////////////////////////////////////////////

	/** An IProcessMonitorListener specialized for backtick operation. */
	public static class BacktickAdapter<D> extends ProcessListener implements IProcessMonitorListener {

		private Reader input;
		private StringBuilder outBuf = new StringBuilder();
		private StringBuilder errBuf = new StringBuilder();
		private PrintWriter errStream;
		private D data;

		public BacktickAdapter() {
			this(null, null);
		}
		public BacktickAdapter(D data) {
			this(data, null);
		}
		public BacktickAdapter(D data, Reader in) {
			this.data = data;
			this.input = in;
		}
		public BacktickAdapter(D data, Reader in, PrintWriter err) {
			this.data = data;
			this.input = in;
			this.errStream = err;
		}


		////////////////////////////////////////////////////////////////////////

		@Override
		public void out(char[] s, int start, int end) {
			outBuf.append(s, start, end);
		}

		@Override
		public void err(char[] s, int start, int end) {
			if (errStream != null)
				errStream.write(s, start, end);
			else
				errBuf.append(s, start, end);
		}
		@Override
		public void err(CharSequence s) {
			if (errStream != null)
				errStream.append(s);
			else
				errBuf.append(s);
		}
		@Override
		public Reader getInput() {
			return input;
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static class BacktickRunner<T> extends ProcessRunner<BacktickAdapter<T>> {
		public BacktickRunner(BacktickAdapter<T> listener, File workdir, String...cmdline) {
			super(listener, workdir, cmdline);
		}
	}

	////////////////////////////////////////////////////////////////////////
}
