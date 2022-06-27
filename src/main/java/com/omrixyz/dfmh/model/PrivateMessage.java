package com.omrixyz.dfmh.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="privateMessage")
public class PrivateMessage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="fuser")
	private String fromUser;
	
	@Column(name="tuser")
	private String toUser;
	
	@Column(name="msg")
	private String msg;
	
	@Column(name="date")
	@CreationTimestamp
	private LocalDateTime date;
	
	private String dateFormat;
	
	public PrivateMessage() {
		
	}
	

	public PrivateMessage(int id, String fromUser, String toUser, String msg, LocalDateTime date) {
		this.id = id;
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.msg = msg;
		this.date = date;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getDateFormat() {
		if (date != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			String formattedLocalDate = date.format(formatter);
			this.dateFormat = formattedLocalDate;
		}
		
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public void addDonationNameForMsg(String donationName) {
		this.msg = "About \"" + donationName + "\"," + msg;
	}

	
	
	
}
