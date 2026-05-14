package com.svalero.komorebiApi.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDto {

    @NotBlank(message = "username is mandatory")
    private String username;

    @NotBlank(message = "password is mandatory")
    private String password;

    // Si no se envía, se asigna ROLE_USER por defecto
    private String role;
}
