package com.svalero.komorebiApi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name= "projects")
@Table(name= "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private int ods;
    @Column
    private boolean active;
    @Column(name= "start_date")
    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name= "school_id")
    @JsonBackReference(value = "schools_projects")
    private School school;


}
