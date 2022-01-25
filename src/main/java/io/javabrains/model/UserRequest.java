package io.javabrains.model;

public class UserRequest {
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserRequest(String username) {
		super();
		this.username = username;
	}
	
	public UserRequest() {}

}
