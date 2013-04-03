/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util;



import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;



import java.io.Reader;
import java.io.Serializable;






import java.util.ArrayList;

import java.util.Collection;

import java.util.Comparator;
import java.util.List;



import java.util.TreeSet;

import java.util.regex.Pattern;






import sf.blacksun.util.struct.Empty;





import sf.blacksun.util.text.MatchUtil;


/**
 * Static file utilities.
 */
public class FileUtil {

	////////////////////////////////////////////////////////////////////

	private static final int BUFSIZE = 16 * 1024;

	protected FileUtil() {
	}

	public interface SafeWriter<T> {
	}


	public static File mkdirs(final File dir) {
		if (dir == null) {
			return null;
		}
		if (!dir.exists() && !dir.mkdirs()) {
			throw new RuntimeException("ERROR: mkdirs: " + dir);
		}
		return dir;
	}


	public static File mkparent(final File file) {
		FileUtil.mkdirs(file.getAbsoluteFile().getParentFile());
		return file;
	}


	public static File format(final String format, final Object...args) {
		return new File(String.format(format, args));
	}

	public static File aformat(final String format, final Object...args) {
		return format(format, args).getAbsoluteFile();
	}


	public static File afile(final String path) {
		File ret = new File(path);
		if (!ret.isAbsolute()) {
			ret = ret.getAbsoluteFile();
		}
		return ret;
	}


	public static String basename(String path) {
		int index = path.lastIndexOf(File.separatorChar);
		if (index >= 0) {
			path = path.substring(index + 1);
		}
		index = path.lastIndexOf('.');
		if (index >= 0) {
			path = path.substring(0, index);
		}
		return path;
	}


	public static boolean isBaseDir(File base, File file) {
		file = file.getAbsoluteFile();
		base = base.getAbsoluteFile();
		for (; file != null; file = file.getParentFile()) {
			if (file.equals(base)) {
				return true;
		}}
		return false;
	}


	public static boolean removeTree(final File dir) {
		if (dir.exists()) {
			final Collection<File> files = FileUtil.findRecursive(
				new TreeSet<File>(ReverseFileComparator.getSingleton()), dir);
			for (final File file: files) {
				if (file.exists() && !file.delete()) {
					return false;
			}}
			if (!dir.delete()) {
				return false;
		}}
		return true;
	}

	/** Like removeTree() but don't remove input directory. */
	public static boolean removeSubTrees(final File dir) {
		if (dir.exists()) {
			final Collection<File> files = FileUtil.findRecursive(
				new TreeSet<File>(ReverseFileComparator.getSingleton()), dir);
			for (final File file: files) {
				if (file.exists() && !file.delete()) {
					return false;
		}}}
		return true;
	}


	/**
	 * @return simple name of files (not directory) directly under the given directory
	 *  that matches the given java regex.
	 */
	public static List<File> files(final File dir, final String include) {
		return FileUtil.files(dir, MatchUtil.compile1(include), null);
	}

	/**
	 * @return simple name of files (not directory) directly under the given directory
	 *  that matches the given java regex.
	 */
	public static List<File> files(final File dir, final Pattern include, final Pattern exclude) {
		final List<File> ret = new ArrayList<File>();
		for (final String name: FileUtil.list(dir)) {
			final File file = new File(dir, name);
			if (file.isFile() && MatchUtil.match(file.getName(), include, exclude)) {
				ret.add(file);
		}}
		return ret;
	}


	public static Collection<File> list(final Collection<File> ret, final File dir, final FileFilter filter) {
		for (final String name: FileUtil.list(dir)) {
			final File file = new File(dir, name);
			if (filter == null || filter.accept(file)) {
				ret.add(file);
		}}
		return ret;
	}


	public static Collection<File> findRecursive(final Collection<File> ret, final File dir) {
		return FileUtil.findRecursive(
			ret,
			dir,
			new FileFilter() {
				@Override
				public boolean accept(final File file) {
					return true;
				}
			});
	}


	/**
	 * @return Files/directories under the given dir that are accepted by the filter.
	 * Recurse regardless directory is accepted by the filter.
	 */
	public static Collection<File> findRecursive(
		final Collection<File> ret, final File base, final FileFilter filter) {
		if (!base.isDirectory()) {
			throw new IllegalArgumentException("Expected directory: " + base.getAbsolutePath());
		}
		for (final String name: FileUtil.list(base)) {
			final File file = new File(base, name);
			if (file.isDirectory()) {
				FileUtil.findRecursive(ret, file, filter);
			}
			if (filter == null || filter.accept(file)) {
				if (ret != null) {
					ret.add(file);
		}}}
		return ret;
	}


	/** Close I/O stream, ignore IOException. */
	public static void close(final Closeable s) {
		if (s == null) {
			return;
		}
		try {
			s.close();
		} catch (final IOException e) {
			// Nothing we can do.
	}}


	/**
	 * Read file content as characters regardless of file format.
	 * To read .gz file, use ungzChars() instead.
	 */
	public static char[] asChars(final File file) throws IOException {
		Reader r = null;
		try {
			return FileUtil.asChars(r = new FileReader(file));
		} finally {
			if (r != null) {
				r.close();
	}}}


	public static char[] asChars(final Reader r) throws IOException {
		final List<char[]> list = new ArrayList<char[]>();
		int size = FileUtil.asChars(list, r);
		final char[] ret = new char[size];
		int offset = 0;
		for (final char[] a: list) {
			System.arraycopy(a, 0, ret, offset, size > FileUtil.BUFSIZE ? FileUtil.BUFSIZE : size);
			offset += FileUtil.BUFSIZE;
			size -= FileUtil.BUFSIZE;
		}
		return ret;
	}


	public static String asString(final File file) throws IOException {
		return String.valueOf(FileUtil.asChars(file));
	}


	public static String asString(final Reader r) throws IOException {
		return String.valueOf(FileUtil.asChars(r));
	}


	public static String getResourceAsString(final String resource) throws IOException {
		final InputStream is = FileUtil.class.getResourceAsStream(resource);
		if (is == null) {
			throw new IOException("Resource not exists: " + resource);
		}
		try {
			return FileUtil.asString(new InputStreamReader(is));
		} finally {
			FileUtil.close(is);
	}}


	////////////////////////////////////////////////////////////////////

	public static void writeFile(final File file, final boolean append, final byte[] content) throws IOException {
		FileUtil.mkparent(file);
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(file, append);
			os.write(content);
		} finally {
			FileUtil.close(os);
	}}


	public static void writeFile(final File file, final boolean append, final CharSequence content)
		throws IOException {
		FileUtil.mkparent(file);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, append);
			writer.append(content);
		} finally {
			if (writer != null) {
				writer.close();
	}}}


	public static void copy(final File dst, final File src) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(src);
			out = new FileOutputStream(dst);
			FileUtil.copy(out, in);
		} finally {
			FileUtil.close(out);
			FileUtil.close(in);
	}}


	public static void copy(final OutputStream out, final InputStream in) throws IOException {
		FileUtil.copy(FileUtil.BUFSIZE, out, in);
	}

	public static void copy(final int bufsize, final OutputStream out, final InputStream in) throws IOException {
		final byte[] b = new byte[bufsize];
		int n;
		while ((n = in.read(b)) >= 0) {
			if (n > 0) {
				out.write(b, 0, n);
	}}}


	////////////////////////////////////////////////////////////////////

	public static class FileComparator implements Comparator<File>, Serializable {
		private static final long serialVersionUID = -7931411981132951772L;
		@Override
		public int compare(final File a, final File b) {
			if (a == null) {
				return b == null ? 0 : -1;
			}
			return a.compareTo(b);
		}
	}

	public static class ReverseFileComparator implements Comparator<File>, Serializable {
		private static final long serialVersionUID = -7931411981132951772L;
		private static ReverseFileComparator singleton;
		public static ReverseFileComparator getSingleton() {
			if (ReverseFileComparator.singleton == null) {
				ReverseFileComparator.singleton = new ReverseFileComparator();
			}
			return ReverseFileComparator.singleton;
		}
		@Override
		public int compare(final File a, final File b) {
			if (b == null) {
				return a == null ? 0 : 1;
			}
			return b.compareTo(a);
		}
	}

	public static class FileIgnorecaseComparator implements Comparator<File>, Serializable {
		private static final long serialVersionUID = 4725680571692221448L;
		@Override
		public int compare(final File a, final File b) {
			if (a == null) {
				return b == null ? 0 : -1;
			}
			return a.getAbsolutePath().compareToIgnoreCase(b.getAbsolutePath());
		}
	}

	public static class FileTimestampComparator implements Comparator<File>, Serializable {
		private static final long serialVersionUID = -7931411981132951772L;
		private static FileTimestampComparator singleton;
		public static FileTimestampComparator getSingleton() {
			if (singleton == null) {
				singleton = new FileTimestampComparator();
			}
			return singleton;
		}
		@Override
		public int compare(final File a, final File b) {
			if (a == null) {
				return b == null ? 0 : -1;
			}
			final long at = a.lastModified();
			final long bt = b.lastModified();
			return at > bt ? 1 : at < bt ? -1 : 0;
		}
	}

	public static class FileOnlyFilter implements FileFilter {
		@Override
		public boolean accept(final File file) {
			return file.isFile();
		}
	}

	public static class DirOnlyFilter implements FileFilter {
		@Override
		public boolean accept(final File file) {
			return file.isDirectory();
		}
	}

	public static class NotDirFilter implements FileFilter {
		@Override
		public boolean accept(final File file) {
			return !file.isDirectory();
		}
	}


	/** @return The total size in characters. */
	private static int asChars(final List<char[]> ret, final Reader r) throws IOException {
		char[] b = new char[FileUtil.BUFSIZE];
		int start = 0;
		int n;
		while ((n = r.read(b, start, FileUtil.BUFSIZE - start)) != -1) {
			start += n;
			if (start == FileUtil.BUFSIZE) {
				ret.add(b);
				b = new char[FileUtil.BUFSIZE];
				start = 0;
		}}
		final int size = ret.size() * FileUtil.BUFSIZE + start;
		if (start > 0) {
			ret.add(b);
		}
		return size;
	}

	private static String[] list(final File dir) {
		final String[] ret = dir.list();
		return ret == null ? Empty.STRING_ARRAY : ret;
	}

	////////////////////////////////////////////////////////////////////
}
