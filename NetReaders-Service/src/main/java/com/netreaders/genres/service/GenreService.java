package com.netreaders.genres.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import com.netreaders.genres.dao.interfaces.GenreDataStore;
import com.netreaders.models.*;

@Service
public class GenreService {
	private final GenreDataStore genreRepository;

	public GenreService(GenreDataStore genreRepository) {
		this.genreRepository = genreRepository;
	}
	
	public Collection<Genre> getAll() {
		return genreRepository.getAll();
	}
	
	public ResponseMessage<Genre> getById(String id) {
		ResponseMessage<Genre> message = new ResponseMessage<>();
		
		try{
			int numericId = Integer.parseInt(id);
			message.setObj(genreRepository.getById(numericId));
		} catch(NumberFormatException e) {
			message.setSuccessful(false);
			message.setErrorMessage(e.getMessage());
		}
		return message;
	}
}
