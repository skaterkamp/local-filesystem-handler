/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.streichelzoo;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.ViewResource;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.mockito.Mockito;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class LocalFileSystemFaceletsHandlerTest {

	public LocalFileSystemFaceletsHandlerTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Before
	public void mockFacesContext() {
	}

	/**
	 * Test of createViewResource method, of class LocalFileSystemFaceletsHandler.
	 */
	@Test
	public void testCreateViewResource() throws IOException {

		System.out.println("createViewResource");
		FacesContext context = ContextMocker.mockFacesContext();
		Map<String, Object> session = new HashMap<>();
		ExternalContext ext = Mockito.
				mock(ExternalContext.class);
		Mockito.when(ext.getSessionMap()).thenReturn(session);
		Mockito.when(context.getExternalContext()).thenReturn(ext);

		LocalFileSystemFaceletsHandler instance = new LocalFileSystemFaceletsHandler(null);
		ResourceHandlerUtils rhu = ResourceHandlerUtils.getInstance();

		Path resourcePath = Files.createTempFile(null, null);
		Path faceletsDir = resourcePath.getParent();
		rhu.setFaceletsDir(faceletsDir);
		String resourceName = resourcePath.getFileName().toString();
		URL expResultURL = resourcePath.toUri().toURL();

		System.out.println("Resource name " + resourceName + " in " + rhu.getFaceletsDir().toString());

		ViewResource result = instance.createViewResource(resourceName);
		Assert.assertNotNull(result);

		Assert.assertEquals(expResultURL, result.getURL());
	}

}
