/*
 * Copyright (c) 2014-2016 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.streichelzoo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesWrapper;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ViewResource;
import javax.faces.context.FacesContext;
import javax.faces.application.Resource;
import org.omnifaces.util.Faces;

/**
 * Resource handler for inclusion of faclets in a directory tree.
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class LocalFileSystemFaceletsHandler
		extends ResourceHandler implements FacesWrapper<ResourceHandler> {

	private final ResourceHandler wrapped;
	private static final Logger LOGGER = Logger.getLogger(LocalFileSystemFaceletsHandler.class.getName());
	private final Path faceletsDir;

	public LocalFileSystemFaceletsHandler(ResourceHandler wrapped) {
		this.wrapped = wrapped;
		ResourceHandlerUtils.getInstance().setFaceletsDir(Faces.getInitParameter(
						ResourceHandlerProperties.FACELETS_DIR));
		faceletsDir = ResourceHandlerUtils.getInstance().getFaceletsDir();
	}

	@Override
	public ResourceHandler getWrapped() {
		return wrapped;
	}

	/**
	 * Create view resource for LFS, return null if file does not exist.
	 *
	 * @param resourceName
	 * @return
	 */
	ViewResource createViewResource(String resourceName) {

		Path resourcePath = Paths.get(resourceName);
		if (resourcePath.isAbsolute()) {
			resourcePath = resourcePath.getRoot().relativize(resourcePath);
		}
		Path absoluteResourcePath = faceletsDir.resolve(resourcePath);
		if (!Files.exists(absoluteResourcePath)) {
			return null;
		}

		return createViewResource(absoluteResourcePath);
	}

	/**
	 * Create view resouce for LFS.
	 *
	 * @param resourcePath
	 * @return
	 */
	ViewResource createViewResource(Path resourcePath) {
		ViewResource viewResource = null;
		if (Files.exists(resourcePath)) {
			try {
				final URL url = resourcePath.toUri().toURL();
				viewResource = new ViewResource() {
					@Override
					public URL getURL() {
						return url;
					}
				};
			} catch (MalformedURLException ex) {
				LOGGER.log(Level.SEVERE, null, ex);
			}
		}
		return viewResource;
	}

	private void logResource(Level level, String prefix, String resourceName, ViewResource viewResource) {
		if (!LOGGER.isLoggable(level)) {
			return;
		}
		String url = "null";
		if (viewResource != null) {
			url = viewResource.getURL().toString();
		}
		if (prefix != null) {
			prefix = prefix + " ";
		}
		LOGGER.log(level, prefix + "Resource Name: " + resourceName + " URL: " + url);
	}

	/**
	 * Create View Resource. Note that if resource is located in a war file,
	 * getResourceAsStream is needed to access it.
	 *
	 * @param context
	 * @param resourceName
	 * @return
	 */
	@Override
	public ViewResource createViewResource(FacesContext context, String resourceName) {
		ViewResource viewResource = getWrapped().createViewResource(context, resourceName);

		if (viewResource == null) {
			viewResource = createViewResource(resourceName);
		}
		logResource(Level.FINE, "View", resourceName, viewResource);
		return viewResource;
	}

	@Override
	public Resource createResource(String resourceName) {
		Resource resource = getWrapped().createResource(resourceName);
		logResource(Level.FINE, "", resourceName, resource);
		return resource;
	}

	@Override
	public Resource createResource(String resourceName, String libraryName) {
		Resource resource = getWrapped().createResource(resourceName, libraryName);
		logResource(Level.FINE, "", resourceName, resource);
		return resource;
	}

	@Override
	public Resource createResource(String resourceName, String libraryName, String contentType) {
		Resource resource = getWrapped().createResource(resourceName, libraryName, contentType);
		logResource(Level.FINE, "", resourceName, resource);
		return resource;
	}

	@Override
	public boolean libraryExists(String libraryName) {
		return getWrapped().libraryExists(libraryName);
	}

	@Override
	public void handleResourceRequest(FacesContext context) throws IOException {
		getWrapped().handleResourceRequest(context);
	}

	@Override
	public boolean isResourceRequest(FacesContext context) {
		return getWrapped().isResourceRequest(context);
	}

	@Override
	public String getRendererTypeForResourceName(String resourceName) {
		String renderer = getWrapped().getRendererTypeForResourceName(resourceName);
		return renderer;
	}
}
