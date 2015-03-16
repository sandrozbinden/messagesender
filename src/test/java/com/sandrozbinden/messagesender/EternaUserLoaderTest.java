package com.sandrozbinden.messagesender;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sandrozbinden.messagesender.EternaUser;
import com.sandrozbinden.messagesender.EternaUserLoader;

public class EternaUserLoaderTest {

	private static final Logger logger = LoggerFactory.getLogger(EternaUserLoaderTest.class);
	
	@Test
	public void testEternaUser() {
		EternaUserLoader eternaUserLoader = new EternaUserLoader();
		EternaUser firstEternaUser = eternaUserLoader.next();
		assertEquals("skyblue", firstEternaUser.getUserName());
		for (int i = 0; i < 100; i++) {
			EternaUser next = eternaUserLoader.next();
			logger.info(next.getUserName() +  " id: " + next.getId());
		}		
	}
}
