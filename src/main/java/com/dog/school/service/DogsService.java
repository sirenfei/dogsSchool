package com.dog.school.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dog.school.domain.Dogs;

@Service
public interface DogsService {
    
	public List<Dogs> findAllDogs();
	public List<Dogs> findDogsByName(String name);
	public Dogs detail(int id);
	public Dogs addDogs(Dogs dog);
	public Boolean removeDogs(int dogId);
	
	
}
