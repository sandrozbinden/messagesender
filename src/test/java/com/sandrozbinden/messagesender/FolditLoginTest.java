package com.sandrozbinden.messagesender;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.sandrozbinden.messagesender.FolditLogin;

public class FolditLoginTest {

	@Test
	public void loginTest() throws IOException {
		FolditLogin folditLogin = new FolditLogin();
		folditLogin.login();
		assertFalse(folditLogin.getSessionID().isEmpty());
	}
}
