package com.sandrozbinden.messagesender;


import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;

public class Message {

	public static final String MESSAGE_FILE ="message/message.txt";
	public static final String MESSAGE_SUBJECT_FILE ="message/message_subject.txt";
	
	private String message;
	
	public Message() {
		try {
			message = Joiner.on("\n").join(Files.readLines(ConfigDirectoy.getFile(MESSAGE_FILE), Charsets.UTF_8));
		} catch (IOException e) {
			throw new IllegalStateException("Can't read the message file:" + MESSAGE_FILE, e);
		}
	}
	
	public String getMessage(String userName) {
		return message.replace("${username}", userName);
	}
	
	public String getSubject() {
		try {
			return Files.readLines(ConfigDirectoy.getFile(MESSAGE_SUBJECT_FILE), Charsets.UTF_8).iterator().next();
		} catch (IOException e) {
			throw new IllegalStateException("Can't read subject title file", e);
		}
		
	}
}
