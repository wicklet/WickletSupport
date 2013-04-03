/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.arquillianext.tomcat;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.TreeSet;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.tomcat.managed_7.TomcatManagedConfiguration;
import org.jboss.arquillian.container.tomcat.managed_7.TomcatManagedContainer;
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.StepWatch;

public class ArquillianTomcatUtil {

	// private static final String BASE_URL = "http://localhost:8080";
	// private static final File tomcatHome = FileUtil.mkdirs(new File("opt/tomcat7"));
	//	private static final File webappsDir = FileUtil.mkdirs(tomcatHome, "webapps");
	//	private static final File workDir = FileUtil.mkdirs(tomcatHome, "work");

	private static TomcatManagedContainer tomcat;

	////////////////////////////////////////////////////////////////////////

	private ArquillianTomcatUtil() {
	}

	////////////////////////////////////////////////////////////////////////

	public enum Debug {
		none, debug, debugserver; 
		public boolean isDebug() {
			return this == debug || this == debugserver;
		}
		public boolean isDebugServer() {
			return this == debugserver;
		}
	}

	////////////////////////////////////////////////////////////////////////

	public static void setup(final Debug debug, final File tomcathome, final String webappname, final File war)
		throws IOException, LifecycleException {
		final File webappsdir = new File(tomcathome, "webapps");
		cleanWebappsDir(webappsdir);
		cleanWorkDirs(tomcathome);
		// Remove existing webapp directory, in case it is not subject to removal in cleanWebappsDir().
		final File webappdir = new File(webappsdir, webappname);
		if (webappdir.exists()) {
			FileUtil.removeTree(webappdir);
		}
		FileUtil.copy(new File(webappsdir, webappname + ".war"), war);
		setup(debug.isDebug(), debug.isDebugServer(), tomcathome);
	}

	public static void teardown(final Debug debug) throws LifecycleException {
		teardown(debug.isDebug());
	}

	public static void setup(final boolean DEBUG, final boolean DEBUG_SERVER, final File tomcatHome)
		throws IOException, LifecycleException {
		final StepWatch timer = new StepWatch(true);
		final TomcatManagedConfiguration conf = new TomcatManagedConfiguration();
		conf.setCatalinaHome(tomcatHome.getAbsolutePath());
		conf.setWorkDir(new File(tomcatHome, "work").getAbsolutePath());
		conf.setUser("manager");
		conf.setPass("tomcat");
		if (DEBUG_SERVER) {
			conf.setJavaVmArguments("-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=y");
		}
		conf.validate();
		tomcat = new TomcatManagedContainer();
		tomcat.setup(conf);
		if (DEBUG) {
			System.out.println(timer.toString("# Server setup"));
		}
		tomcat.start();
		if (DEBUG) {
			System.out.println(timer.toString("# Server start"));
	}}

	public static void teardown(final boolean DEBUG) throws LifecycleException {
		final StepWatch timer = new StepWatch(true);
		if (tomcat != null) {
			tomcat.stop();
		}
		if (DEBUG) {
			System.out.println(timer.toString("# Server stop"));
	}}

	public static void savePage(final String name, final String content) {
		save(new File("logs/pages/" + name), content);
	}

	public static void save(final File outfile, final String content) {
		try {
			FileUtil.writeFile(outfile, false, content);
		} catch (final IOException e) {
			// Ignore
			return;
	}}

	public static void cleanWebappsDir(final File webappsDir) {
		final Collection<File> files = FileUtil.list(new TreeSet<File>(), webappsDir, null);
		for (final File dir: files) {
			if (dir.isDirectory()) {
				final String name = dir.getName();
				if ("manager".equals(name) || "ROOT".equals(name)) {
					continue;
				}
				FileUtil.removeTree(dir);
			} else if (dir.isFile()) {
				if (dir.getName().endsWith(".war")) {
					dir.delete();
	}}}}

	public static void cleanWorkDirs(final File tomcathome) {
		final File workdir = new File(tomcathome, "work");
		final File tempdir = new File(tomcathome, "temp");
		if (workdir.exists()) {
			FileUtil.removeSubTrees(workdir);
		}
		if (tempdir.exists()) {
			FileUtil.removeSubTrees(tempdir);
	}}

	public static void cleanTomcatHome(final File tomcathome) {
		cleanWebappsDir(new File(tomcathome, "webapps"));
		cleanWorkDirs(tomcathome);
	}

	public static boolean checkHttpPortClosed(final URL url) {
		return checkHttpPortClosed(500, 5000, url);
	}

	/**
	 * Check that given url is opened by connecting to it.
	 * @return true if connect success before timeout.
	 */
	public static boolean checkHttpPortOpened(final int timeout, final URL url) {
		if (!"http".equalsIgnoreCase(url.getProtocol())) {
			throw new AssertionError("ASSERT: protocol == http: " + url.getProtocol());
		}
		URLConnection conn;
		try {
			conn = url.openConnection();
		} catch (final IOException e) {
			return false;
		}
		final HttpURLConnection c = (HttpURLConnection)conn;
		c.setConnectTimeout(timeout);
		try {
			c.connect();
		} catch (final IOException e) {
			return false;
		} finally {
			c.disconnect();
		}
		return true;
	}

	/**
	 * Check that given url is opened by connecting to it.
	 * @param min Minimium timeout in ms for each connection attempt.
	 * @param max Max. waiting time in ms if connection is open.
	 * @return true if connect timeout.
	 * @throws IOException If there connection failed for reason other than timeout.
	 */
	public static boolean checkHttpPortClosed(final int min, final int max, final URL url) {
		if (!"http".equalsIgnoreCase(url.getProtocol())) {
			throw new AssertionError("ASSERT: protocol == http: " + url.getProtocol());
		}
		URLConnection conn;
		try {
			conn = url.openConnection();
		} catch (final IOException e1) {
			return true;
		}
		HttpURLConnection c = null;
		final long end = System.currentTimeMillis() + max;
		int remaining = max;
		while (remaining > 0) {
			final long start = System.currentTimeMillis();
			try {
				c = (HttpURLConnection)conn;
				c.setConnectTimeout(min);
				c.connect();
			} catch (final IOException e) {
				// Probably connection refused, ... etc. Assume closed.
				return true;
			} finally {
				if (c != null) {
					c.disconnect();
				}
				c = null;
			}
			// Connect succeeded, wait for min timeout in any case and try again.
			final long wait = min - (System.currentTimeMillis() - start);
			if (wait > 0) {
				try {
					Thread.sleep(wait);
				} catch (final InterruptedException e) {
			}}
			remaining = (int)(end - System.currentTimeMillis());
		}
		return true;
	}

	////////////////////////////////////////////////////////////////////////
}
