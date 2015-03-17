package com.sandrozbinden.messagesender;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.sandrozbinden.messagesender.categories.DevelopmentManually;

public class FolditMessageSenderTest {

    @Test
    @Category(DevelopmentManually.class)
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
