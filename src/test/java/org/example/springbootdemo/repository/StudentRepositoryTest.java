package org.example.springbootdemo.repository;

import org.example.springbootdemo.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;


    @BeforeEach
    void setUp() {
        student = Student.builder()
                .name("Sandipan")
                .email("sandipan@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Should save student")
    void shouldSaveStudent(){

        Student saved = studentRepository.save(student);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Sandipan" , saved.getName());
    }

    @Test
    @DisplayName("Should find student by email")
    void shouldFindByEmail() {

        studentRepository.save(student);
        Optional<Student> result = studentRepository.findByEmail("sandipan@gmail.com");

        assertTrue(result.isPresent());
        assertEquals("Sandipan" , result.get().getName());
    }

    @Test
    @DisplayName("Should check if email exists")
    void shouldCheckEmailExists() {

        studentRepository.save(student);

        boolean exists = studentRepository.existsByEmail("sandipan@gmail.com");

        assertTrue(exists);
    }

    @Test
    @DisplayName("Should get student by id")
    void getStudentById() {
        Student saved = studentRepository.save(student);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Sandipan" , saved.getName());
    }
}