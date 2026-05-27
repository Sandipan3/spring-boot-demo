package org.example.springbootdemo.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootdemo.dto.StudentRequest;
import org.example.springbootdemo.dto.StudentResponse;
import org.example.springbootdemo.entity.Student;
import org.example.springbootdemo.exception.DuplicateResourceException;
import org.example.springbootdemo.exception.ResourceNotFoundException;
import org.example.springbootdemo.repository.StudentRepository;
import org.example.springbootdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    //utility method
    private StudentResponse mapToResponse(Student student){
        return  StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .build();
    }

    private Student getStudentEntity(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }


    @Override
    public StudentResponse createStudent(StudentRequest request) {

        if(studentRepository.existsByEmail(request.getEmail())){
            throw new DuplicateResourceException("Email already exists");
        }

        Student student = Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        student = studentRepository.save(student);

        log.info("[Service]: Student created with id {}", student.getId()
        );

        return  mapToResponse(student);

    }

    @Override
    public List<StudentResponse> getAllStudents() {
        log.info("[Service]: Fetching Students");

        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentResponse  getStudentById(Long id) {
        log.info("[Service]: Fetching student {}", id);

        return  mapToResponse(getStudentEntity(id));
    }

    @Override
    public StudentResponse  updateStudent(Long id, StudentRequest request) {

        log.info("[Service]: Updating student {}", id);

        Student student = getStudentEntity(id);

        student.setName(request.getName());
        student.setEmail(request.getName());

        student = studentRepository.save(student);

        return mapToResponse(student);
    }

    @Override
    public String deleteStudent(Long id) {

        Student student = getStudentEntity(id);

        studentRepository.delete(student);

        log.info("[Service]: Student deleted with id {}", id);
        return "Student deleted with id: " + id ;
    }
}
