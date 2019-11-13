package com.netreaders.genres.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import com.netreaders.genres.dao.interfaces.GenreDataStore;
import com.netreaders.models.*;
import com.netreaders.services.ResponseMessagePrepearer;

@Service
public class GenreService {
	private final GenreDataStore genreRepository;

	public GenreService(GenreDataStore genreRepository) {
		this.genreRepository = genreRepository;
	}
	
	public ResponseMessage<Collection<Genre>> getAll() {
		ResponseMessage<Collection<Genre>> message = new ResponseMessage<>();
		try{
			message.setObj(genreRepository.getAll());
		} catch(Exception e) {
			ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
		} 
		return message;
	}
	
	public ResponseMessage<Genre> getById(String id) {
		ResponseMessage<Genre> message = new ResponseMessage<>();
		
		try{
			int numericId = Integer.parseInt(id);
			message.setObj(genreRepository.getById(numericId));
		} catch(NumberFormatException e) {
			ResponseMessagePrepearer.prepareMessage(message, "Invalid Genre id");
		} catch(Exception e) {
			ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
		} 
		return message;
	}
}
