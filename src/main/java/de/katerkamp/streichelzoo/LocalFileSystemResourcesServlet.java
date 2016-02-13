/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 *
 */
package de.katerkamp.streichelzoo;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.servlet.FileServlet;

/**
 * Serve non jsf files. 
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class LocalFileSystemResourcesServlet extends FileServlet {

	private static final long serialVersionUID = 5029147420134518570L;
	private static final Logger LOGGER = Logger.getLogger(LocalFileSystemResourcesServlet.class.getName());

	private Path faceletsDir;

	@Override
	public void init() throws ServletException {
		ResourceHandlerUtils.getInstance().setFaceletsDir(
			getServletContext().getInitParameter(
					ResourceHandlerProperties.FACELETS_DIR));
		faceletsDir = ResourceHandlerUtils.getInstance().getFaceletsDir();
	}

	@Override
	protected File getFile(HttpServletRequest hsr) throws IllegalArgumentException {
		String pathInfo = hsr.getPathInfo();
		if (pathInfo == null || pathInfo.isEmpty() || "/".equals(pathInfo)) {
			throw new IllegalArgumentException();
		}

		Path servletPath = Paths.get(hsr.getServletPath());
		if (servletPath.isAbsolute()) {
			servletPath = servletPath.getRoot().relativize(servletPath);
		}

		Path resourcePath = Paths.get(pathInfo);
		if (resourcePath.isAbsolute()) {
			resourcePath = resourcePath.getRoot().relativize(resourcePath);
		}

		Path absoluteResourcePath = faceletsDir.resolve(servletPath).resolve(resourcePath);

		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.fine("Servlet FaceletsDir: " + faceletsDir 
					+ " ServletPath: " + servletPath
					+ " ResourcePath: " + resourcePath);
		}

		return absoluteResourcePath.toFile();
	}
}
