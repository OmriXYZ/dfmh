package com.omrixyz.dfmh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omrixyz.dfmh.model.PrivateMessage;

public interface MessageRepository extends JpaRepository<PrivateMessage, Integer> {
	public List<PrivateMessage> findByToUserOrderByIdDesc(String toUser);
	
}
