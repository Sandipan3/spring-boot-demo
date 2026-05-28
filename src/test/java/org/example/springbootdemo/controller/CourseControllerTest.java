package org.example.springbootdemo.controller;

import org.example.springbootdemo.dto.CourseResponse;
import org.example.springbootdemo.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseService courseService;


    @Test
    @DisplayName("Should return all courses")
    void getAllCourses() throws Exception {

        CourseResponse response = CourseResponse.builder()
                .id(1L)
                .title("Spring Boot")
                .duration(30)
                .studentId(1L)
                .studentName("Sandipan")
                .build();

        when(courseService.getAllCourses()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/courses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Spring Boot"));
    }

}