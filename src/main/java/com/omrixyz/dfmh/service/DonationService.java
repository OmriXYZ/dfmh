package com.omrixyz.dfmh.service;

import java.util.List;

import com.omrixyz.dfmh.model.Donation;


public interface DonationService {

	public List<Donation> findAll();
	
	public Donation findById(int theId);
	
	public void save(Donation theDonation);
	
	public void deleteById(int theId);

	public List<Donation> searchByEmail(String userEmail);
	
	public void deleteAllByEmail(String email);

	public List<Donation> searchByDepartment(String departmentName);
	

	
}
