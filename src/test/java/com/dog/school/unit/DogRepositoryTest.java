package com.dog.school.unit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dog.school.dao.DogsRepository;
import com.dog.school.domain.Dogs;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataJpaTest
class DogRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DogsRepository dogsRepository;

    Dogs alex;

    @BeforeEach
    void setUp() throws Exception {
        // given
        alex = new Dogs("alex");
        entityManager.persist(alex);
        entityManager.flush();
    }

    @Test
    void searchDogsTest() {
        // when
        List<Dogs> found = dogsRepository.findByNameContaining(alex.getName());
        List<Dogs> notFound = dogsRepository.findByNameContaining(alex.getName() + alex.getName());

        List<Dogs> none = Lists.newArrayList();
        assertAll("searchResults", 
                () -> assertNotNull(found), 
                () -> assertTrue(found.size() > 0),
                () -> assertEquals(none, notFound),
                () -> assertThrows(InvalidDataAccessApiUsageException.class, () -> {dogsRepository.findByNameContaining(null);}, "parameter must no be null")
                );
    }

}
