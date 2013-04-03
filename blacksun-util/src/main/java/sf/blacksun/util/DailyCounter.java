/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util;

/**
 * A simple class that keep a counter for today that is reset at start of each new day.
 * First counter value is 1.
 */
public class DailyCounter {

	private long counter = 0;
	private final String dateformat;
	private final String format;
	private String date;

	/** Use default date format: "%1$tY%1$tm%1$td" and default output format: "%1$s-%2$04d" */
	public DailyCounter() {
		this("%1$tY%1$tm%1$td", "%1$s-%2$04d");
	}

	/**
	 * Use default output format: "%1$s-%2$04d".
	 * @param dateformat Format string for today.
	 */
	public DailyCounter(final String dateformat) {
		this(dateformat, "%1$s-%2$04d");
	}

	/**
	 * @param dateformat Format string for today.
	 * @param format The format string for get().
	 */
	public DailyCounter(final String dateformat, final String format) {
		this.dateformat = dateformat;
		this.format = format;
		date = formatdate(dateformat);
	}

	public String get() {
		return format(dateformat, format);
	}

	public String getDate() {
		return date;
	}

	/**
	 * Use the given date format and output format instead of the one from constructors.
	 * @param format Format string where first argument is date and second argument is counter,
	 * eg. "%1$s-%04d.txt"
	 */
	private synchronized String format(final String dateformat, final String format) {
		final String d = formatdate(dateformat);
		if (date.equals(d)) {
			++counter;
		} else {
			date = d;
			counter = 1;
		}
		return String.format(format, date, counter);
	}

	protected String formatdate(final String format) {
		return String.format(format, System.currentTimeMillis());
	}
}
