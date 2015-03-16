package com.sandrozbinden.messagesender;

public class EternaUser {

	private String id;
	private String userName;

	public EternaUser(String id, String userName) {
		this.id = id; 
		this.userName = userName;
	}
	
	public String getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}

}
