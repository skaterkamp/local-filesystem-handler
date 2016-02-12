/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.streichelzoo;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.omnifaces.util.Faces;

/**
 * Common utilities and configurations.
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class ResourceHandlerUtils {
	
	private static ResourceHandlerUtils instance = null;
	static final String FACELETS_DIR_DEFAULT = "/var/www/faces";
	private Path faceletsDir;

	private ResourceHandlerUtils() {
		String dirName = Faces.getInitParameter(ResourceHandlerProperties.FACELETS_DIR);
		if (dirName != null && !dirName.isEmpty()) {
			faceletsDir = Paths.get(dirName);
		} else {
			faceletsDir = Paths.get(FACELETS_DIR_DEFAULT);
		}
	}

	public static ResourceHandlerUtils getInstance() {
		if (instance == null) {
			instance = new ResourceHandlerUtils();
		}
		return instance;
	}

	public Path getFaceletsDir() {
		return faceletsDir;
	}

	public void setFaceletsDir(Path faceletsDir) {
		this.faceletsDir = faceletsDir;
	}
}
