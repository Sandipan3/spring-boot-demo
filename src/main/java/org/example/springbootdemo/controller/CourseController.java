package org.example.springbootdemo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.dto.CourseRequest;
import org.example.springbootdemo.dto.CourseResponse;
import org.example.springbootdemo.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/student/{studentId}")
    public ResponseEntity<CourseResponse> createCourse(@PathVariable Long studentId, @Valid @RequestBody CourseRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.createCourse(studentId , request));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses(){

        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CourseResponse>> getCourseById(@PathVariable Long id){

        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id , @Valid @RequestBody CourseRequest request ){

        return ResponseEntity.ok(courseService.updateCourse(id,request));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){

        return ResponseEntity.ok(courseService.deleteCourse(id));
    }

}
