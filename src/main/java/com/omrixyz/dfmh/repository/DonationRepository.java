package com.omrixyz.dfmh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.omrixyz.dfmh.model.Donation;


@Repository("donationRepository")
public interface DonationRepository extends JpaRepository<Donation, Integer> {

	// that's it ... no need to write any code LOL!
	
	// add a method to sort by last name
	public List<Donation> findAllByOrderByNameAsc();
	
	public List<Donation> findAllByOrderByDateAsc();
	
	// search by name
	public List<Donation> findByNameContainsOrDepartmentContainsAllIgnoreCase(String name, String department);
	
	public List<Donation> findByUser(String user);

	@Transactional
	public void deleteAllByUser(String user);
	
	public List<Donation> findByDepartment(String department);

	public Donation getDonationById(int id);


}
