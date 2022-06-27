package com.omrixyz.dfmh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omrixyz.dfmh.model.PrivateMessage;
import com.omrixyz.dfmh.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

	MessageRepository messageRepository;
	
	@Autowired
	public MessageServiceImpl(MessageRepository theMessageRepository) {
		messageRepository = theMessageRepository;
	}
	
	@Override
	public void save(PrivateMessage pm) {
		messageRepository.save(pm);
	}

	@Override
	public void deleteById(int theId) {
		messageRepository.deleteById(theId);
	}

	@Override
	public List<PrivateMessage> findAllByUser(String user) {
		return messageRepository.findByToUserOrderByIdDesc(user);
	}

}
