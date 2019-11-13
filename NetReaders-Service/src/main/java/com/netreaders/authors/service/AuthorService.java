package com.netreaders.authors.service;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.netreaders.authors.dao.interfaces.AuthorDataStore;
import com.netreaders.models.Author;
import com.netreaders.models.ResponseMessage;
import com.netreaders.services.ResponseMessagePrepearer;

@Service
public class AuthorService {
	private final AuthorDataStore authorRepository;
	
	public AuthorService(AuthorDataStore authorRepository) {
		this.authorRepository = authorRepository;
	}
	
	public ResponseMessage<Collection<Author>> getAll() {
		ResponseMessage<Collection<Author>> message = new ResponseMessage<>();
		try {
			message.setObj(authorRepository.getAll());
		} catch(SQLException e) {
			ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
		}
		return message;
	}
	
	public ResponseMessage<Author> getById(String id) {
		ResponseMessage<Author> message = new ResponseMessage<>();
		
		try{
			int numericId = Integer.parseInt(id);
			message.setObj(authorRepository.getById(numericId));
		} catch(NumberFormatException e) {
			ResponseMessagePrepearer.prepareMessage(message, "Invalid author id");
		} catch(SQLException e) {
			ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
		}
		return message;
	}
	
}
