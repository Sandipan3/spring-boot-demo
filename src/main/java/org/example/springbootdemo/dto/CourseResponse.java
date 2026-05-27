package org.example.springbootdemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseResponse {

    private Long id;
    private String title;
    private int duration;
    private Long studentId;
    private String studentName;

}
