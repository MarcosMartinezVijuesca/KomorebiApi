package com.svalero.komorebiApi.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolOutDto {
    private Long id;
    private String name;
    private String city;
    private Long students;
    private boolean publicSchool;
    private LocalDate registerDate;
}
