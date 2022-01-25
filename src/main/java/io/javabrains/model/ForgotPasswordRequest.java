package io.javabrains.model;

public class ForgotPasswordRequest {
	
	private String username;
	private String phone;
	private String otp;
	private String npassword;
	private String cnpassword;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getNpassword() {
		return npassword;
	}

	public void setNpassword(String npassword) {
		this.npassword = npassword;
	}

	public String getCnpassword() {
		return cnpassword;
	}

	public void setCnpassword(String cnpassword) {
		this.cnpassword = cnpassword;
	}

	public ForgotPasswordRequest(String username, String phone, String otp, String npassword, String cnpassword) {
		super();
		this.username = username;
		this.phone = phone;
		this.otp = otp;
		this.npassword = npassword;
		this.cnpassword = cnpassword;
	}

	public ForgotPasswordRequest() {}

}
