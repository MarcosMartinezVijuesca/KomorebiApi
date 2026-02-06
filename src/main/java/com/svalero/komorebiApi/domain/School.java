package com.svalero.komorebiApi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "School")
@Table(name = "schools")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column
    private String city;

    @Column
    @Min(value = 1)
    private int students;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column(name = "register_date")
    private LocalDate registerDate;

    /*@OneToMany(mappedBy = "project")
    @JsonBackReference
    private List<Project> projects;*/
}
