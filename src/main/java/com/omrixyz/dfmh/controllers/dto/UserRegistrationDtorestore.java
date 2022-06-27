package com.omrixyz.dfmh.controllers.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserRegistrationDtorestore implements UserDetails {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Boolean enabled;

	public UserRegistrationDtorestore(){

	}

	public UserRegistrationDtorestore(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.enabled = false;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
