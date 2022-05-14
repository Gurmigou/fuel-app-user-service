package com.fueladvisor.fuelappuserservice.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String carBrand;
    private String carModel;

    @Column(nullable = false)
    private Double fuelConsumption;

    @OneToOne(mappedBy = "car", fetch = FetchType.LAZY)
    private User user;
}
