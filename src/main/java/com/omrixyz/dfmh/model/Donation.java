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
@Table(name="donation")
public class Donation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="department")
	private String department;
	
	@Column(name="user")
	private String user;
	
	@Column(name="date")
	@CreationTimestamp
	private LocalDateTime date;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="hphone") //hiding phone from donation
	private Boolean hphone;
	
	@Column(name="hemail") //hiding email from donation
	private Boolean hemail;
	
	private String dateFormat;
	
	public Donation() {
		
	}
	
	public Donation(int id, String name, String department, String user, LocalDateTime date) {
		this.id = id;
		this.name = name;
		this.department = department;
		this.user = user;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	@Override
	public String toString() {
		return "Donation [id=" + id + ", name=" + name + ", department=" + department + ", user=" + user + "]";
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public boolean getHPhone() {
		return hphone;
	}

	public void setHPhone(boolean hphone) {
		this.hphone = hphone;
	}
	
	public boolean getHEmail() {
		return hemail;
	}

	public void setHEmail(boolean hemail) {
		this.hemail = hemail;
	}



	
	

	
	
	
	
}
