package com.fueladvisor.fuelappuserservice.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutUserDto {
    private String email;
    private String carBrand;
    private String carModel;
    private Double fuelConsumption;
}
