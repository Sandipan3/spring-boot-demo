package org.example.springbootdemo.repository;

import org.example.springbootdemo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course , Long> {

}
