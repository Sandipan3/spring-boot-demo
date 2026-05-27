package org.example.springbootdemo.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootdemo.dto.CourseRequest;
import org.example.springbootdemo.dto.CourseResponse;
import org.example.springbootdemo.entity.Course;
import org.example.springbootdemo.entity.Student;
import org.example.springbootdemo.exception.ResourceNotFoundException;
import org.example.springbootdemo.repository.CourseRepository;
import org.example.springbootdemo.repository.StudentRepository;
import org.example.springbootdemo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    //utility methods
    private CourseResponse mapToResponse(Course course){

        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .duration(course.getDuration())
                .studentId(course.getStudent().getId())
                .studentName(course.getStudent().getName())
                .build();
    }

    private Course getCourseEntity(Long id){
        return courseRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException( "Course not found with id " + id));
    }

    @Override
    public CourseResponse createCourse(Long studentId, CourseRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new ResourceNotFoundException( "Student not found with id " + studentId));

        Course course = Course.builder()
                .title(request.getTitle())
                .duration(request.getDuration())
                .student(student)
                .build();

        course = courseRepository.save(course);

        log.info("[Service]: Course created with id {}", course.getId());

        return mapToResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public CourseResponse getCourseById(Long id) {

        log.info("Service]: Getting course with id {}", id);

        return mapToResponse(getCourseEntity(id));
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest request) {

        log.info("Service]: Updating course with id {}", id);

        Course course = getCourseEntity(id);

        course.setTitle(request.getTitle());
        course.setDuration(request.getDuration());

        course = courseRepository.save(course);

        return mapToResponse(course);
    }

    @Override
    public String deleteCourse(Long id) {

        Course course = getCourseEntity(id);

        courseRepository.delete(course);

        log.info("Service]: Deleted course with id {}", id);
        return "Deleted course with id: " +id;
    }
}
