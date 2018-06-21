package com.dog.school.service.Impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dog.school.dao.DogsRepository;
import com.dog.school.domain.Dogs;
import com.dog.school.service.DogsService;
import com.google.common.collect.Lists;

@Service
public class DogsServiceImpl implements DogsService {

    @Autowired
    private DogsRepository dogsRepository;
    
    @Override
    public List<Dogs> findAllDogs() {
        return dogsRepository.findAll();
    }

    @Override
    public List<Dogs> findDogsByName(String name) {
        if(StringUtils.isBlank(name) || name.length() < 3 || name.length() > 255)
        {
            return Lists.newArrayList();
        }
        List<Dogs> result = dogsRepository.findByNameContaining(name);
        return result;
    }

    @Override
    public Dogs detail(int id) {
        Dogs dog = new Dogs();
        if(id<= 0  || id > Integer.MAX_VALUE)
        {
            return dog;
        }  
        
        return dogsRepository.findById(id).orElse(dog);
    }
    
    @Transactional
    @Override
    public Dogs addDogs(Dogs dog) {
        Dogs result = null;
        if(null != dog && StringUtils.isNotBlank(dog.getName()))
        {
            result = dogsRepository.save(dog);
        }
        return result;
    }
    
    @Transactional
    @Override
    public Boolean removeDogs(int id) {
        if(id<= 0  || id > Integer.MAX_VALUE)
        {
            return false;
        }  
        if (this.dogsRepository.existsById(id)) {
            this.dogsRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

}
