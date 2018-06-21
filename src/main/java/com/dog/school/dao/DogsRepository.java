package com.dog.school.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dog.school.domain.Dogs;

@Repository
public interface DogsRepository extends JpaRepository<Dogs, Integer>{
    
  @Query("SELECT d.id,d.name FROM Dogs d WHERE d.name like %:name%")
  List<Dogs> searchDogs(@Param("name") String name);
  
  List<Dogs> findByNameContaining(@Param("name") String name);
    
}
