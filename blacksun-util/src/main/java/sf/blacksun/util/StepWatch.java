/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util;

public class StepWatch implements IStopWatch {

	////////////////////////////////////////////////////////////////////////

	private long startTime = -1;
	private long elapsed;
	private long total;

	// Constructors ////////////////////////////////////////////////////////

	public StepWatch() {
	}

	public StepWatch(boolean start) {
		if (start)
			start();
	}

	// Instance methods ////////////////////////////////////////////////////

	public StepWatch start() {
		startTime = System.currentTimeMillis();
		return this;
	}


	/** @return Time elapsed since start() in sec. */
	public long elapsedInMs() {
		if (startTime < 0)
			return elapsed;
		return elapsed + (System.currentTimeMillis() - startTime);
	}

	public String toString() {
		float time = delta();
		return String.format("%8.2f/%8.2f (sec)", time, total / 1000f);
	}

	public String toString(String msg) {
		float time = delta();
		return String.format("%-32s: %8.2f/%8.2f (sec)", msg, time, total / 1000f);
	}


	public float delta() {
		long e = elapsedInMs();
		long time = e - total;
		total = e;
		return time / 1000f;
	}
}
