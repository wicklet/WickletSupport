/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.arquillianext.maven;

import java.io.File;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

public class ArquillianMavenUtil {

	private ArquillianMavenUtil() {
	}

	public static File[] resolveArtifacts(final File pom, final String...artifacts) {
		return DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom(
			pom.getAbsolutePath()).goOffline().artifacts(artifacts).resolveAsFiles();
	}
}
