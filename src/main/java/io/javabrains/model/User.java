package io.javabrains.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class User {
	
	@Id
	private int id;
	private String username;
	private String password;
	private String cpassword;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String occupation;
	private String dob; //enter or submit dob in "yyyy-mm-dd" format
	private boolean active;
	private String roles;
	private Address_data address_data;
	
	public User(int id, String username, String password, String cpassword, String firstName, String lastName,
			String email, String phone, String occupation, String dob, boolean active, String roles,
			Address_data address_data) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.cpassword = cpassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.occupation = occupation;
		this.dob = dob;
		this.active = active;
		this.roles = roles;
		this.address_data = address_data;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getCpassword() {
		return cpassword;
	}


	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getOccupation() {
		return occupation;
	}


	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public String getRoles() {
		return roles;
	}


	public void setRoles(String roles) {
		this.roles = roles;
	}


	public Address_data getAddress_data() {
		return address_data;
	}


	public void setAddress_data(Address_data address_data) {
		this.address_data = address_data;
	}


	public User() {} 
	
}
