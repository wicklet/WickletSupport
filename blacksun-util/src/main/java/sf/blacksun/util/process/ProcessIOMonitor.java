/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import sf.blacksun.util.io.StringPrintWriter;

public class ProcessIOMonitor<T extends IProcessMonitorListener> {

	static final int BUFSIZE = 2048;
	private static int nameCount = 0;
	private String name;
	protected T listener;
	protected Thread inThread;
	protected Thread outThread;
	protected Thread errThread;

	public ProcessIOMonitor(T listener) {
		if (listener == null)
			throw new AssertionError("ASSERT: listener != null");
		this.listener = listener;
	}


	public synchronized String getName() {
		if (name == null)
			name = getClass().getSimpleName() + "-" + nameCount++;
		return name;
	}

	protected Thread startInMonitor(OutputStream s) {
		inThread = new InputMonitorThread(listener.getInput(), s, getName() + "-in") {
			@Override
			protected void error(Throwable e) {
				ProcessIOMonitor.this.error(e);
			}
		};
		inThread.start();
		return inThread;
	}

	protected Thread startOutMonitor(InputStream s) {
		outThread = new OutputMonitorThread(s, getName() + "-out") {
			@Override
			protected void append(char[] buf, int end) {
				listener.out(buf, 0, end);
			}
			@Override
			protected void error(Throwable e) {
				ProcessIOMonitor.this.error(e);
			}
		};
		outThread.start();
		return outThread;
	}

	protected Thread startErrMonitor(InputStream s) {
		errThread = new OutputMonitorThread(s, getName() + "-err") {
			@Override
			protected void append(char[] buf, int end) {
				listener.err(buf, 0, end);
			}
			@Override
			protected void error(Throwable e) {
				ProcessIOMonitor.this.error(e);
			}
		};
		errThread.start();
		return errThread;
	}

	protected void joinIO() throws Throwable {
		Throwable ex = null;
		if (inThread != null) {
			try {
				inThread.join();
			} catch (Throwable e) {
				ex = e;
			} finally {
				inThread = null;
		}}
		if (errThread != null) {
			try {
				errThread.join();
			} catch (Throwable e) {
				ex = e;
			} finally {
				errThread = null;
		}}
		if (outThread != null) {
			try {
				outThread.join();
			} catch (Throwable e) {
				ex = e;
			} finally {
				outThread = null;
		}}
		//		ProcessUtils.assertion(!inThread.isAlive(), "inThread");
		//		ProcessUtils.assertion(!outThread.isAlive(), "outThread");
		//		ProcessUtils.assertion(!errThread.isAlive(), "errThread");
		if (ex != null)
			throw ex;
	}

	void error(Throwable e) {
		StringPrintWriter w = new StringPrintWriter();
		try {
			w.print(e.getMessage());
			e.printStackTrace(w);
			w.flush();
			listener.err(w.getBuffer());
		} finally {
			w.close();
	}}

	protected abstract static class InputMonitorThread extends Thread {
		private Reader reader;
		private Writer writer;
		protected abstract void error(Throwable e);
		public InputMonitorThread(Reader r, OutputStream os, String name) {
			this.reader = r;
			this.writer = new BufferedWriter(new OutputStreamWriter(os));
			if (name != null)
				setName(name);
		}
		@Override
		public void run() {
			try {
				if (reader != null) {
					char[] a = new char[BUFSIZE];
					for (int nread; (nread = reader.read(a)) >= 0;) {
						if (nread > 0)
							writer.write(a, 0, nread);
			}}} catch (IOException e) {
				error(e);
			} finally {
				// TODO Is it neccessary to close the process stream?
				try {
					writer.close();
				} catch (IOException e) {
					error(e);
				}
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						error(e);
		}}}}
	}

	protected static abstract class OutputMonitorThread extends Thread {
		protected Reader out;
		public OutputMonitorThread(InputStream s, String name) {
			this.out = new BufferedReader(new InputStreamReader(s));
			if (name != null)
				setName(name);
		}
		protected abstract void append(char[] buf, int end);
		protected abstract void error(Throwable e);
		@Override
		public void run() {
			try {
				char[] buf = new char[BUFSIZE];
				int nread = 0;
				do {
					nread = out.read(buf);
					if (nread > 0)
						append(buf, nread);
				} while (nread >= 0);
			} catch (IOException e) {
				error(e);
			} finally {
				// TODO Is it neccessary to close the process stream?
				try {
					out.close();
				} catch (IOException e) {
					error(e);
		}}}
	}
}
