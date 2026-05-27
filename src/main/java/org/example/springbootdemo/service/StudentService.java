package org.example.springbootdemo.service;

import org.example.springbootdemo.dto.StudentRequest;
import org.example.springbootdemo.dto.StudentResponse;
import org.example.springbootdemo.entity.Student;

import java.util.List;

public interface StudentService {

    StudentResponse  createStudent(StudentRequest request);
    List<StudentResponse > getAllStudents();
    StudentResponse  getStudentById(Long id);
    StudentResponse updateStudent(Long id , StudentRequest request);
    String deleteStudent(Long id);
}
