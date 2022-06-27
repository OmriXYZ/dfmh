package com.omrixyz.dfmh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omrixyz.dfmh.model.User;

public interface UserManagmentRepository extends JpaRepository<User, Integer> {

	public List<User> findAllByOrderByIdAsc();
	
	public User findById(int id);
	
}
