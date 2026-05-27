package org.example.springbootdemo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.dto.StudentRequest;
import org.example.springbootdemo.dto.StudentResponse;
import org.example.springbootdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/students")

public class StudentController {

    @Autowired
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id){

        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents(){

        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest request){

        return ResponseEntity.ok(studentService.updateStudent(id , request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){

        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

}
