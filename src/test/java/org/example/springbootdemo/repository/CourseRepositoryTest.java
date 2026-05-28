package org.example.springbootdemo.repository;

import org.example.springbootdemo.entity.Course;
import org.example.springbootdemo.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;


    private Student student;
    private Course course;

    @Test
    @DisplayName("Should save course")
    void shouldSaveCourse() {

        Student student = Student.builder()
                .name("Sandipan")
                .email("sandipan@gmail.com")
                .build();

        Student savedStudent =
                studentRepository.save(student);

        Course course = Course.builder()
                .title("Spring Boot")
                .duration(30)
                .student(savedStudent)
                .build();

        Course savedCourse = courseRepository.save(course);

        assertNotNull(savedCourse);

        assertNotNull(savedCourse.getId());

        assertEquals("Spring Boot", savedCourse.getTitle());
    }
}