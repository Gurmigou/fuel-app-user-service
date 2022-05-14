package com.fueladvisor.fuelappuserservice.repository;

import com.fueladvisor.fuelappuserservice.model.entity.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Integer> {
    @Query("SELECT c FROM Car c " +
           "INNER JOIN User u " +
           "WHERE u.email = ?1")
    Optional<Car> finCardByUserEmail(String email);
}
