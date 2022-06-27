package com.omrixyz.dfmh.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "token")
public class TokenRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "token")
	private String tokenID;

	@Column(name = "createdate")
	private Instant createdate;

	public TokenRegister() {

	}

	public TokenRegister(String email) {
		this.email = email;
		this.tokenID = UUID.randomUUID().toString();
		this.createdate = Instant.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	public Instant getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Instant createdate) {
		this.createdate = createdate;
	}
	
	

}
