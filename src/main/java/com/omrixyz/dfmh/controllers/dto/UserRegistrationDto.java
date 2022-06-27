package com.omrixyz.dfmh.controllers.dto;

import com.omrixyz.dfmh.model.Role;
import com.omrixyz.dfmh.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserRegistrationDto implements UserDetails {

	public User user;
	
	public UserRegistrationDto(User user){
		this.user = user;
	}
	
	public String getFirstName() {
		return user.getFirstName();
	}
	public String getLastName() {
		return user.getLastName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<Role> roles = user.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}
}
