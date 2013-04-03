/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.arquillianext;

import java.io.InputStream;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.impl.base.exporter.AbstractStreamExporterImpl;
import org.jboss.shrinkwrap.impl.base.exporter.zip.JdkZipExporterDelegate;

public class ZipEntryExporterImpl extends AbstractStreamExporterImpl implements ZipEntryExporter {

	private static final Logger log = Logger.getLogger(ZipEntryExporterImpl.class.getName());

	private final IZipEntrySelector selector;
	private MyJdkZipExporterDelegate delegate;

	public ZipEntryExporterImpl(Archive<?> archive, IZipEntrySelector selector) {
		super(archive);
		this.selector = selector;
		this.delegate = new MyJdkZipExporterDelegate(archive);
	}

	public final InputStream exportAsInputStream() {
		final Archive<?> archive = getArchive();
		if (log.isLoggable(Level.FINE)) {
			log.fine("Exporting archive - " + archive.getName());
		}
		final Node rootNode = archive.get(ArchivePaths.root());
		for (Node child: rootNode.getChildren()) {
			processNode(child);
		}
		return delegate.getResult();
	}

	/**
	 * Recursive call to process all the node hierarchy
	 * @param node
	 */
	private void processNode(final Node node) {
		processNode(node.getPath(), node);
		Set<Node> children = node.getChildren();
		for (Node child: children) {
			processNode(child);
	}}

	private void processNode(org.jboss.shrinkwrap.api.ArchivePath path, final Node node) {
		// TODO
	}

	private static class MyJdkZipExporterDelegate extends JdkZipExporterDelegate {
		public MyJdkZipExporterDelegate(Archive<?> archive) throws IllegalArgumentException {
			super(archive);
		}
		@Override
		public InputStream getResult() {
			return super.getResult();
		}
	}
}
