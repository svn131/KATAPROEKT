package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "регистрационные данные")
public class UserRegistrationDto {
    @Parameter(description = "Имя")
    @NotEmpty
    private String firstName;
    @Parameter(description = "Фамилия")
    @NotEmpty
    private String lastName;
    @Parameter(description = "электронная почта")
    @NotEmpty
    private String email;
    @Parameter(description = "пароль")
    @NotEmpty
    private String password;
}

