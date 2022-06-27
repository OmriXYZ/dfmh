package com.omrixyz.dfmh.service;

import com.omrixyz.dfmh.controllers.dto.UserRegistrationDto;
import com.omrixyz.dfmh.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto) throws UnsupportedEncodingException, MessagingException;

	public boolean existUser(UserRegistrationDto registrationDto);
	
	public boolean isValidUser(String user);

	public Instant getLastTimeUploadDonation(String user);
	
	public void updateLastTimeUploadDonation(String user, Instant lastDonation);
	
	void sendVerificationEmail(UserRegistrationDto user, String tkn) throws MessagingException, UnsupportedEncodingException;
	
	boolean checkVerification(String user);

	boolean checkToken(String token, String email);
	boolean isExistUserByEmail(String email);
	void setRole(long id, String role);
}
