package com.netreaders.authors.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.netreaders.authors.dao.interfaces.AuthorDataStore;
import com.netreaders.models.Author;
import com.netreaders.models.ResponseMessage;

@Service
public class AuthorService {
	private final AuthorDataStore authorRepository;
	
	public AuthorService(AuthorDataStore authorRepository) {
		this.authorRepository = authorRepository;
	}
	
	public Collection<Author> getAll() {
		return authorRepository.getAll();
	}
	
	public ResponseMessage<Author> getById(String id) {
		ResponseMessage<Author> message = new ResponseMessage<>();
		
		try{
			int numericId = Integer.parseInt(id);
			message.setObj(authorRepository.getById(numericId));
		} catch(NumberFormatException e) {
			message.setSuccessful(false);
			message.setErrorMessage(e.getMessage());
		}
		return message;
	}
	
}
