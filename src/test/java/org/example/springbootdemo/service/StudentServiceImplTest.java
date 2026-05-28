package org.example.springbootdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.example.springbootdemo.dto.StudentRequest;
import org.example.springbootdemo.dto.StudentResponse;
import org.example.springbootdemo.entity.Student;
import org.example.springbootdemo.exception.DuplicateResourceException;
import org.example.springbootdemo.exception.ResourceNotFoundException;
import org.example.springbootdemo.repository.StudentRepository;
import org.example.springbootdemo.serviceImpl.StudentServiceImpl;
import org.junit.jupiter.api.*;
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
public class StudentServiceImplTest {

    @Mock // Creates fake dependency
    private StudentRepository studentRepository;

    @InjectMocks //Injects mocks into class under test
    private StudentServiceImpl studentService;

    private Student student;
    private StudentRequest request;


    private StudentRequest badRequest;

    @BeforeEach // Runs before every test
    void setup(){
        student = Student.builder()
                .id(1L)
                .name("Sandipan")
                .email("sandipanjha3@gmail.com")
                .build();

        request = new StudentRequest();
        request.setName("Sandipan");
        request.setEmail("sandipan@gmail.com");
    }

    @Test // Marks test method
    void shouldCreateStudent(){

        when(studentRepository
                .existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(studentRepository.save(any(Student.class)))
                .thenReturn(student);

        StudentResponse response = studentService.createStudent(request);

        assertNotNull(response);

        assertEquals(1L, response.getId());
        assertEquals("Sandipan" , response.getName());

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void shouldGetStudentById(){

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentResponse response = studentService.getStudentById(1L);
        assertEquals(1L, response.getId());

        verify(studentRepository).findById(1L);

    }

    @Test
    void shouldReturnAllStudents(){

        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<StudentResponse> responses = studentService.getAllStudents();

        assertEquals(1 , responses.size());

        verify(studentRepository).findAll();
    }

    void shouldUpdateStudent(){

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentResponse response = studentService.updateStudent(1L , request );

        assertEquals("Sandipan" , request.getName());

        verify(studentRepository).save(any(Student.class));

    }

    @Test
    void shouldDeleteStudent(){

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1L);

        verify(studentRepository).delete(student);
    }

    @Test
    void shouldThrowDuplicateEmailException(){

        badRequest = new StudentRequest();
        badRequest.setName("Test");
        badRequest.setEmail("sandipanjha3@gmail.com");

        when(studentRepository.existsByEmail(badRequest.getEmail()))
                .thenReturn(true);

        DuplicateResourceException ex = assertThrows(DuplicateResourceException.class , ()-> studentService.createStudent(badRequest));

        verify(studentRepository,never()).save(any());

        log.error("[DuplicateResourceException] : {}" , ex.getMessage());

    }

    @Test
    void shouldThrowWhenStudentNotFound() {

        when(studentRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class, () -> studentService.getStudentById(1L));

        log.error("[ResourceNotFoundException] : {}" , ex.getMessage());

    }

}
