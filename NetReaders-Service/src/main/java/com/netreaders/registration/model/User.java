package com.netreaders.registration.model;

import java.util.Collection;
import java.util.List;

public class User {
	
	private int id;
	
	private String userName;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	
	private boolean enabled;
	
	private String email;
	
	private Collection<Role> roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Collection<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	

	public User() {
		super();
        this.enabled=false;
	}

	public User(int id, String email, String firstName, String lastName, String userName, String password, boolean enabled,
			Collection<Role> roles) {
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", enabled=" + enabled + ", roles=" + roles + "]";
	}

	
}
