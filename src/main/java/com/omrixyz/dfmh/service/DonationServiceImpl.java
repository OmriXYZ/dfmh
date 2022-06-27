package com.omrixyz.dfmh.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omrixyz.dfmh.model.Donation;
import com.omrixyz.dfmh.repository.DonationRepository;


@Service("donationService")
public class DonationServiceImpl implements DonationService {

	private DonationRepository donationRepository;
	
	@Autowired
	public DonationServiceImpl(DonationRepository theDonationRepository) {
		donationRepository = theDonationRepository;
	}
	
	@Override
	public List<Donation> findAll() {
		return donationRepository.findAllByOrderByDateAsc();
	}

	@Override
	public Donation findById(int theId) {
		return donationRepository.getDonationById(theId);
	}

	@Override
	public void save(Donation theDonation) {
		donationRepository.save(theDonation);
	}

	@Override
	public void deleteById(int theId) {
		donationRepository.deleteById(theId);
	}

	@Override
	public List<Donation> searchByEmail(String userEmail) {
		
		List<Donation> results = null;
		
		if (userEmail != null && (userEmail.trim().length() > 0)) {
			results = donationRepository.findByUser(userEmail);
		}
		else {
			results = findAll();
		}
		
		return results;
	}

	@Override
	public void deleteAllByEmail(String email) {
		donationRepository.deleteAllByUser(email);
	}

	@Override
	public List<Donation> searchByDepartment(String department) {
		return donationRepository.findByDepartment(department);
	}



}






