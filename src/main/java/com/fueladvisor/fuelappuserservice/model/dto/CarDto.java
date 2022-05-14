package com.fueladvisor.fuelappuserservice.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private String carBrand;
    private String carModel;
    private Double fuelConsumption;
}
