package com.sandrozbinden.messagesender;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ResourceLoader {

	
	private static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);
	
	
	public static final File getFile(String fileName) {
		ClassLoader classLoader = getClassLoader();
		logger.info("Try to load file: " + fileName + " from resource");
		return new File(classLoader.getResource(fileName).getFile());
	}


	private static ClassLoader getClassLoader() {
		ClassLoader classLoader = ResourceLoader.class.getClassLoader();
		return classLoader;
	}


	public static void ensureFile(String fileName) {
		if (!fileExists(fileName)) {
			File file = getFile(fileName);
			try {
				Files.write("", file, Charsets.UTF_8);
			} catch (IOException e) {
				throw new IllegalStateException("Can't create file",e);
			}
		}
	}
	
	public static boolean fileExists(String fileName) {
		URL url = getClassLoader().getResource(fileName);
		return url == null ? false : new File(url.getFile()).exists(); 
	}


	public static InputStream getResourceAsStream(String fileName) {
		ClassLoader classLoader = getClassLoader();
		logger.info("Try to load resource as stream: " + fileName + " from resource");
		return classLoader.getResourceAsStream(fileName);
	}
}
