package com.example.recrutationtask.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    @ManyToOne
    private Parent parent;
    @ManyToOne
    private School school;
    @OneToMany(mappedBy = "child")
    private Set<Attendance> attendances;

}
