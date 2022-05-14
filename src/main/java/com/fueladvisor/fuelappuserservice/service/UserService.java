package com.fueladvisor.fuelappuserservice.service;

import com.fueladvisor.fuelappuserservice.model.dto.AboutUserDto;
import com.fueladvisor.fuelappuserservice.model.dto.CarDto;
import com.fueladvisor.fuelappuserservice.model.entity.Car;
import com.fueladvisor.fuelappuserservice.model.entity.User;
import com.fueladvisor.fuelappuserservice.repository.CarRepository;
import com.fueladvisor.fuelappuserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.function.Supplier;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Autowired
    public UserService(UserRepository userRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public AboutUserDto getUserAndCarInfo(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(userNotExistsException(email));
        return mapToAboutUserDto(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(userNotExistsException(email));

        userRepository.delete(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveCar(String email, CarDto carDto) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(userNotExistsException(email));

        if (user.getCar() != null) {
            updateCar(email, carDto);
        } else {
            Car car = Car.builder()
                    .carBrand(carDto.getCarBrand())
                    .carModel(carDto.getCarModel())
                    .fuelConsumption(carDto.getFuelConsumption())
                    .user(user)
                    .build();

            user.setCar(car);
            userRepository.save(user);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCar(String email, CarDto carDto) {
        Car car = carRepository
                .finCardByUserEmail(email)
                .orElseThrow(userNotExistsException(email));

        car.setCarBrand(carDto.getCarBrand());
        car.setCarModel(carDto.getCarModel());
        car.setFuelConsumption(carDto.getFuelConsumption());

        carRepository.save(car);
    }

    private AboutUserDto mapToAboutUserDto(User user) {
        Car car = user.getCar();
        return AboutUserDto.builder()
                .email(user.getEmail())
                .carBrand(car == null ? null : car.getCarBrand())
                .carModel(car == null ? null : car.getCarModel())
                .fuelConsumption(car == null ? null : car.getFuelConsumption())
                .build();
    }

    private Supplier<IllegalStateException> userNotExistsException(String email) {
        return () -> new IllegalStateException("User with email " + email + " doesn't exist");
    }
}
