package com.omrixyz.dfmh.service;

import java.util.List;

import com.omrixyz.dfmh.model.PrivateMessage;

public interface MessageService {

	public void save(PrivateMessage pm);
	
	public void deleteById(int theId);

	public List<PrivateMessage> findAllByUser(String user);


}
