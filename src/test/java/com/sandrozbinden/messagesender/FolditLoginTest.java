package com.sandrozbinden.messagesender;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.sandrozbinden.messagesender.FolditLogin;
import com.sandrozbinden.messagesender.categories.DevelopmentManually;

public class FolditLoginTest {

    @Test
    @Category(DevelopmentManually.class)
    public void loginTest() throws IOException {
        FolditLogin folditLogin = new FolditLogin();
        folditLogin.login();
        assertFalse(folditLogin.getSessionID().isEmpty());
    }
}
