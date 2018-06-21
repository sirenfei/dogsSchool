package com.dog.school.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.dog.school.controller.DogSchoolController;
import com.dog.school.domain.Dogs;
import com.dog.school.dto.Results;
import com.dog.school.service.DogsService;
import com.dog.school.utils.JsonUtil;
import com.google.common.collect.Lists;

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(DogSchoolController.class)
class DogSchoolControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DogsService dogsService;
    
    List<Dogs> list = Lists.newArrayList(new Dogs("little 1"), new Dogs("little 2"), new Dogs("little 3"));
    List<Dogs> none = Lists.newArrayList();
    Optional<Dogs> singleDog = Optional.of(new Dogs("littleDog"));
    
    private static final String DOGGIE = "doggie";
    
    Dogs doggie = new Dogs(DOGGIE);
    
    Results<Dogs> success;
    Results<Dogs> failed;
    Results<Dogs> tooLong;
    
    @BeforeEach
    void setUp() throws Exception {
        doggie.setId(11);
        
        given(dogsService.addDogs(any(Dogs.class))).willReturn(doggie);
        given(dogsService.findAllDogs()).willReturn(list);
        
        given(dogsService.detail( intThat(id -> id>=1 && id <=Integer.MAX_VALUE)   )).willReturn(doggie);
        given(dogsService.detail( intThat(id -> id<1 && id >Integer.MAX_VALUE)   )).willReturn(new Dogs());
        
        given(dogsService.removeDogs( intThat( id -> id>=1 && id <=Integer.MAX_VALUE )  )).willReturn(true);
        given(dogsService.removeDogs( intThat( id -> id<1 && id >Integer.MAX_VALUE )  )).willReturn(false);
        
        given(dogsService.findDogsByName(DOGGIE)).willReturn(list);

        
        success = new Results<Dogs>(Results.SUCC, doggie);
        failed = new Results<Dogs>(Results.FAIL, null);
        tooLong = new Results<Dogs>(Results.TOO_LONG, null);
        
       
    }



    @ParameterizedTest
    @ValueSource(strings = { " ", ""})
    void testRegisterWrong(String dogName) throws Exception{
        mvc.perform(post("/register")
                .param("dogName", dogName))
                .andExpect(status().isOk())
                .andDo(print()) 
                .andExpect(content().string(JsonUtil.toJson(failed)));
    }
    
    @Test
    void testRegisterLong() throws Exception{
        StringBuilder sb = new StringBuilder();
        for(int i=0;i< 500;i++)
        {
            sb.append("ok");
        }

        String longStr = sb.toString();
        
        mvc.perform(post("/register")
                .param("dogName", longStr))
        .andExpect(status().isOk())
        .andDo(print()) 
        .andExpect(content().string(JsonUtil.toJson(tooLong)));
    }
    
    
    
    @ParameterizedTest
    @ValueSource(strings = { DOGGIE,"baby baby" , "    baby baby "})
    void testRegister(String dogName) throws Exception{
        mvc.perform(post("/register")
                .param("dogName", dogName))
        .andExpect(status().isOk())
        .andDo(print()) 
        .andExpect(content().string(JsonUtil.toJson(success)));
        
        mvc.perform(get("/register"))
        .andExpect(status().is4xxClientError())
        .andDo(print());
    }
    
    

    @Test
    void testFindall() throws Exception {
        mvc.perform(get("/findall"))
        .andExpect(status().isOk())
        .andDo(print()) 
        .andExpect(content().string(JsonUtil.toJson(list)));
        
        mvc.perform(post("/findall"))
        .andExpect(status().is4xxClientError())
        .andDo(print());
    }
    

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5,Integer.MAX_VALUE })
    void testDetail(int id) throws Exception{
        mvc.perform(get("/detail/"+id))
        .andExpect(status().isOk())
        .andDo(print()) 
        .andExpect(content().string(JsonUtil.toJson(doggie)));
    }
    
    @ParameterizedTest
    @ValueSource(ints = { -1, 0, Integer.MAX_VALUE+1, -Integer.MAX_VALUE, -Integer.MAX_VALUE+1 -1 })
    void testDetailWrong(int id) throws Exception{
        mvc.perform(get("/detail/"+id))
        .andExpect(status().isOk())
        .andDo(print()) 
        .andExpect(content().string(JsonUtil.toJson(new Dogs())));
    }

    
    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5, 11, Integer.MAX_VALUE})
    void testDeregister(int id) throws Exception{
        mvc.perform(get("/deregister/"+id))
        .andExpect(status().isOk())
        .andDo(print()) 
        .andExpect(content().string("OK"));
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-1, -10000,-Integer.MAX_VALUE, Integer.MAX_VALUE+1})
    void testDeregisterWrong(int id) throws Exception{
        mvc.perform(get("/deregister/"+id))
        .andExpect(status().isOk())
        .andDo(print()) 
        .andExpect(content().string("Failed"));
    }
    

    @ParameterizedTest
    @ValueSource(strings = { "aa", "", "    ","aaaaa",Integer.MAX_VALUE *100000+"" })
    void testSearch(String dogName) throws Exception{
        mvc.perform(post("/search")
                .param("dogName", dogName))
                .andExpect(status().isOk())
                .andDo(print()) 
                .andExpect(content().string(JsonUtil.toJson(none)));
        
        mvc.perform(post("/search")
                .param("dogName", DOGGIE))
        .andExpect(status().isOk())
        .andDo(print()) 
        .andExpect(content().string(JsonUtil.toJson(list)));
    }

}
