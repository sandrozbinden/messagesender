package com.sandrozbinden.messagesender;

import java.io.IOException;

import org.junit.Test;

import com.sandrozbinden.messagesender.EternaLogin;
import com.sandrozbinden.messagesender.EternaMessageSender;
import com.sandrozbinden.messagesender.EternaUser;
import com.sandrozbinden.messagesender.Message;

public class EternaMessageSenderTest {

	@Test
	public void sendMessage() throws IOException {
		EternaLogin login = new EternaLogin();
		login.login();
		
		EternaMessageSender messageSender = new EternaMessageSender();
		EternaUser user = new EternaUser("209780", "heritchan");
		Message message = new Message();
		message.getMessage(user.getUserName());
		
		messageSender.sendMessage(message.getMessage(user.getUserName()), user, login.getSessionID());
	}
}
