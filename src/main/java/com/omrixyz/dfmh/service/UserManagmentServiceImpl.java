package com.omrixyz.dfmh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omrixyz.dfmh.model.User;
import com.omrixyz.dfmh.repository.UserManagmentRepository;

@Service
public class UserManagmentServiceImpl implements UserManagmentService {
	
	private UserManagmentRepository userManagmentRepository;
	
	@Autowired
	public UserManagmentServiceImpl(UserManagmentRepository theUserManagmentRepository) {
		userManagmentRepository = theUserManagmentRepository;
	}
		

	@Override
	public List<User> findAll() {
		return userManagmentRepository.findAll();
	}

	@Override
	public void deleteById(int theId) {
		userManagmentRepository.deleteById(theId);
	}


	@Override
	public void changeRoleById(int theId, int role) {
		
		
	}

	@Override
	public String findEmailById(int theId) {
		return userManagmentRepository.findById(theId).getEmail();
	}

}
