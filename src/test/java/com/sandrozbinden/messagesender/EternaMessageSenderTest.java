package com.sandrozbinden.messagesender;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.sandrozbinden.messagesender.categories.DevelopmentManually;

public class EternaMessageSenderTest {

    @Test
    @Category(DevelopmentManually.class)
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
