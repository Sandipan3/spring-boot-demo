package org.example.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false , unique = true)
    private String email;

    @OneToMany(mappedBy = "student" , cascade = CascadeType.ALL , orphanRemoval = true ,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Course> courses;
}
