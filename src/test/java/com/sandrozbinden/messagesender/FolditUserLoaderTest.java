package com.sandrozbinden.messagesender;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sandrozbinden.foldit.categories.DevelopmentManually;
import com.sandrozbinden.messagesender.FolditUser;
import com.sandrozbinden.messagesender.FolditUserLoader;

public class FolditUserLoaderTest {

	private static final Logger logger = LoggerFactory.getLogger(FolditUserLoaderTest.class);
	
	@Test
	@Category(DevelopmentManually.class)
	public void testLoadingUser() {
		FolditUserLoader folditUserLoader = new FolditUserLoader();
		FolditUser firstFolditUser = folditUserLoader.next();
		assertEquals("BitSpawn", firstFolditUser.getUserName());
		for (int i = 0; i < 100; i++) {
			FolditUser next = folditUserLoader.next();
			logger.info(next.getUserName());
		}
	}
}
