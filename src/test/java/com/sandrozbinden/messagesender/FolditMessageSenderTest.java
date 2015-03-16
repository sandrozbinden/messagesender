package com.sandrozbinden.messagesender;

import java.io.IOException;

import org.junit.Test;

import com.sandrozbinden.messagesender.FolditLogin;
import com.sandrozbinden.messagesender.FolditMessageSender;
import com.sandrozbinden.messagesender.FolditUser;
import com.sandrozbinden.messagesender.Message;

public class FolditMessageSenderTest {

	@Test
	public void sendMessage() throws IOException {
		FolditLogin login = new FolditLogin();
		login.login();
		
		FolditMessageSender messageSender = new FolditMessageSender();
		FolditUser user = new FolditUser("732651", "heritchan");
		Message message = new Message();
		message.getMessage(user.getUserName());
		
		messageSender.sendMessage(message.getMessage(user.getUserName()), user, login.getSessionID());
	}
}
