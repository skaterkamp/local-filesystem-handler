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
	private Path faceletsDir = null;

	private ResourceHandlerUtils() {
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

	public void setFaceletsDir(String faceletsDirName) {
		System.out.println("FD " + faceletsDir + " FDN " + faceletsDirName);
		if (faceletsDir != null) {
			System.out.println("FD not null");
			return;
		}
		if (faceletsDirName != null && !faceletsDirName.isEmpty()) {
			faceletsDir = Paths.get(faceletsDirName);
		} else {
			faceletsDir = Paths.get(FACELETS_DIR_DEFAULT);
		}
	}
}
