package com.sandrozbinden.messagesender;


public class FolditUser implements User {

	private String rank;
	private String score;
	private String userName;
	private String messageHref;
	private String id;
	
	public FolditUser(String id, String userName, String rank, String score) {
		this.id = id;
		this.userName = userName;
		this.rank = rank;
		this.score = score;
	}
	
	public FolditUser(String id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMessageHref() {
		return messageHref;
	}
	public void setMessageHref(String messageHref) {
		this.messageHref = messageHref;
	}
	
	public String getId() {
		return id;
	}
	
	
}
