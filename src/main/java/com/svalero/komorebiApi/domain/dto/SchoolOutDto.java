package com.svalero.komorebiApi.domain.dto;

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
    private String name;
    private String city;
    private Long Students;
    private Boolean isPublic;
    private LocalDate registerDate;
}
