package org.example.springbootdemo.controller;

import org.example.springbootdemo.dto.StudentResponse;
import org.example.springbootdemo.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;



import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    private StudentResponse response;

    @BeforeEach
    void setup(){
        response = StudentResponse.builder()
                .id(1L)
                .name("Sandipan")
                .email("sandipan@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Should return student by id")
    void getStudentById() throws Exception {

        when(studentService.getStudentById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/students/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Sandipan"));
    }

    @Test
    @DisplayName("Should return all students")
    void getAllStudents() throws Exception {

        when(studentService.getAllStudents()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/students").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sandipan"));
    }
}