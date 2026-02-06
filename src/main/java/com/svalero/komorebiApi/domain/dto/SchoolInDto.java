package com.svalero.komorebiApi.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolInDto {
    @NotNull(message = "name is mandatory and cannot be empty")
    private String name;
    private String city;
    @Min(value = 1)
    private Long students;
    private boolean publicSchool;
    private LocalDate registerDate;
}
