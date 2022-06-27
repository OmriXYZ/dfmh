package com.omrixyz.dfmh.service;

import java.util.List;

import com.omrixyz.dfmh.model.User;

public interface UserManagmentService {
	
	public List<User> findAll();
	
	public void deleteById(int theId);
	
	public void changeRoleById(int theId, int role);
	
	public String findEmailById(int theId);
	
}
