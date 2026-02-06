package com.svalero.komorebiApi.domain.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInDto {
    @NotNull(message= "name is mandatory and cannot be empty")
    private String name;
    private String description;
    @Min(value = 1)
    private int ods;
    private boolean active;
    private LocalDate startDate;
}
