package org.example.springbootdemo.service;

import org.example.springbootdemo.dto.CourseRequest;
import org.example.springbootdemo.dto.CourseResponse;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(Long studentId , CourseRequest request);
    List<CourseResponse> getAllCourses();
    CourseResponse getCourseById(Long id);
    CourseResponse updateCourse(Long id , CourseRequest request);
    String deleteCourse(Long id);

}
