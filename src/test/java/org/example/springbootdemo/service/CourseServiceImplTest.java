package org.example.springbootdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.example.springbootdemo.dto.CourseRequest;
import org.example.springbootdemo.dto.CourseResponse;
import org.example.springbootdemo.entity.Course;
import org.example.springbootdemo.entity.Student;
import org.example.springbootdemo.exception.ResourceNotFoundException;
import org.example.springbootdemo.repository.CourseRepository;
import org.example.springbootdemo.repository.StudentRepository;
import org.example.springbootdemo.serviceImpl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Student student;
    private Course course;
    private CourseRequest request;

    @BeforeEach
    void setUp() {

        student = Student.builder()
                .id(1L)
                .name("Sandipan")
                .email("sandipan@gmail.com")
                .build();

        course = Course.builder()
                .id(1L)
                .title("Spring Boot")
                .duration(30)
                .student(student)
                .build();

        request = new CourseRequest();
        request.setTitle("Spring Boot");
        request.setDuration(30);
    }

    @Test
    void shouldCreateCourse() {

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseResponse response = courseService.createCourse(1L, request);

        assertNotNull(response);

        assertEquals("Spring Boot", response.getTitle());

        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void shouldGetCourseById() {

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        CourseResponse response = courseService.getCourseById(1L);

        assertEquals("Spring Boot", response.getTitle());
    }

    @Test
    void shouldReturnAllCourses() {

        when(courseRepository.findAll()).thenReturn(List.of(course));

        List<CourseResponse> responses = courseService.getAllCourses();

        assertEquals(1, responses.size());
    }

    @Test
    void shouldUpdateCourse() {

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseResponse response = courseService.updateCourse(1L, request);

        assertEquals("Spring Boot", response.getTitle());

    }

    @Test
    void shouldDeleteCourse() {

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        courseService.deleteCourse(1L);

        verify(courseRepository).delete(course);
    }

    @Test
    void shouldThrowWhenCourseNotFound() {

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> courseService.getCourseById(1L));

        log.error("[ResourceNotFoundException] : {}",ex.getMessage());
    }

}
