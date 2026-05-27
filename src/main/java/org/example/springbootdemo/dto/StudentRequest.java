package org.example.springbootdemo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentRequest {

    @NotBlank(message = "Name is required")
    private String  name;

    @NotBlank(message = "Invalid Email")
    private String email;
}
