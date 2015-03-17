package com.sandrozbinden.messagesender;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.sandrozbinden.messagesender.categories.DevelopmentManually;

public class EternaLoginTest {

    @Test
    @Category(DevelopmentManually.class)
    public void testEterna() {
        EternaLogin eternaLogin = new EternaLogin();
        eternaLogin.login();
    }
}
