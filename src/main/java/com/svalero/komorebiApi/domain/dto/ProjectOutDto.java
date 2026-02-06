package com.svalero.komorebiApi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectOutDto {
    private Long id;
    private String name;
    private String description;
    private int ods;
    private boolean active;
    private LocalDate startDate;
    private long schoolId;
}
