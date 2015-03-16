package com.sandrozbinden.messagesender;

import org.junit.Test;

import com.sandrozbinden.messagesender.EternaLogin;

public class EternaLoginTest {

	@Test
	public void testEterna() {
		EternaLogin eternaLogin = new EternaLogin();
		eternaLogin.login();
	}
}
