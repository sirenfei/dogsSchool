package com.dog.school.unit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dog.school.dao.DogsRepository;
import com.dog.school.domain.Dogs;
import com.dog.school.service.DogsService;
import com.google.common.collect.Lists;


@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class DogServiceTest {
    
    @MockBean
    DogsRepository dogsRepository;
    
    @Autowired
    DogsService dogsService;
    
    List<Dogs> list;
    List<Dogs> none = Lists.newArrayList();
    Optional<Dogs> singleDog=Optional.of(new Dogs("littleDog"));
    Dogs doggie=new Dogs("doggie");
    String longStr;
    
    private static final String LITTLE="little";


    @BeforeEach
    void setUp() throws Exception {
        list = Lists.newArrayList(new Dogs("little 1"),new Dogs("little 2"),new Dogs("little 3"));
       
        Mockito.when(dogsRepository.findById(ArgumentMatchers.anyInt())).thenReturn(singleDog);
        
        Mockito.when(dogsRepository.findAll()).thenReturn(list);
        
        Mockito.when(dogsRepository.findByNameContaining(LITTLE)).thenReturn(list);
        
        Mockito.when(dogsRepository.findById(ArgumentMatchers.anyInt())).thenReturn(singleDog);
        
        Mockito.when(dogsRepository.save(ArgumentMatchers.any(Dogs.class))).thenReturn(doggie);
        doggie.setId(11);
        

        Mockito.when(dogsRepository.existsById(ArgumentMatchers.intThat( id->id <=11 || id==Integer.MAX_VALUE))).thenReturn(true);
        Mockito.when(dogsRepository.existsById(ArgumentMatchers.intThat( id->id > 11 && id != Integer.MAX_VALUE))).thenReturn(false);
        
        StringBuilder sb = new StringBuilder();
        for(int i=0;i< 500;i++)
        {
            sb.append("ok");
        }
        longStr = sb.toString();
        
    }
    
    
    @Test
    void testFindAllDogs() {
        List<Dogs> dogs = dogsService.findAllDogs();
        assertNotNull(dogs); 
        assertTrue(dogs.size() ==list.size() );
    }


    @ParameterizedTest
    @ValueSource(strings = { "aa", "", "aaa","aaaaa",Integer.MAX_VALUE *100000+"" })
    void testFindDogsByName(String dogName) {
        List<Dogs> dogs = dogsService.findDogsByName(dogName);
        assertEquals(none, dogs);
    }
    
    @Test
    void testFindDogsByName() {
        List<Dogs> nodog = dogsService.findDogsByName(null);
        assertEquals(none, nodog);
        
        List<Dogs> dogs = dogsService.findDogsByName(LITTLE);
        assertEquals(list, dogs);
    }
    
    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5,Integer.MAX_VALUE })
    void testDetailRight(int id) {
        Dogs detail = dogsService.detail(id);
        assertEquals(singleDog.get(), detail);
    }
    @ParameterizedTest
    @ValueSource(ints = { -1,0,Integer.MAX_VALUE+1 })
    void testDetailWrong(int id) {
        Dogs detail = dogsService.detail(id);
        assertNull(detail.getId());
        assertNull(detail.getName());
    }
    

    @Test
    void testAddDogs() {
        Dogs result = dogsService.addDogs(doggie);
        assertEquals(doggie.getName(), result.getName());
        assertEquals(doggie.getId(), result.getId());
   
        Dogs resultNull = dogsService.addDogs(null);
        assertEquals(null, resultNull);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5, 11, Integer.MAX_VALUE})
    void testRemoveDogsRight(int id) {
        assertTrue(dogsService.removeDogs(id));
    }
    
    @ParameterizedTest
    @ValueSource(ints = { -1, -10000,-Integer.MAX_VALUE, 12, 200, Integer.MAX_VALUE+1})
    void testRemoveDogsWrong(int id) {
        assertFalse(dogsService.removeDogs(id));
    }
   

    
}
