package com.fueladvisor.fuelappuserservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    @Email
    private String email;
    @Size(min = 6)
    private String password;
    @Size(min = 6)
    private String confirmPassword;
}
