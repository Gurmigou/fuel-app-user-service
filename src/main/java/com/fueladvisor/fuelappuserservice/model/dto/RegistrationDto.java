package com.fueladvisor.fuelappuserservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    @Email
    private String email;
    @Min(6)
    private String password;
    @Min(6)
    private String confirmPassword;
}
