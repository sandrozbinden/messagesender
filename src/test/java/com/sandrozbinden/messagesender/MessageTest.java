package com.sandrozbinden.messagesender;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sandrozbinden.messagesender.Message;

public class MessageTest {

	@Test
	public void messageTest () {
		Message message = new Message();
		assertTrue(message.getMessage("heinz").contains("heinz"));
	}
	
}
