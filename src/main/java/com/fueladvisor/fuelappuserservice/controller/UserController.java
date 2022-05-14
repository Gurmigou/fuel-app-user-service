package com.fueladvisor.fuelappuserservice.controller;

import com.fueladvisor.fuelappuserservice.model.dto.AboutUserDto;
import com.fueladvisor.fuelappuserservice.model.dto.CarDto;
import com.fueladvisor.fuelappuserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUserAndCarInfo(Principal principal) {
        try {
            AboutUserDto userAndCarInfo = userService.getUserAndCarInfo(principal.getName());
            return ResponseEntity.ok(userAndCarInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(Principal principal) {
        try {
            userService.deleteUser(principal.getName());
            return ResponseEntity.ok("User successfully deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/car")
    public ResponseEntity<?> saveUserCar(Principal principal, @RequestBody CarDto carDto) {
        try {
            userService.saveCar(principal.getName(), carDto);
            return ResponseEntity.ok("Car successfully saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/car")
    public ResponseEntity<?> updateUserCar(Principal principal, @RequestBody CarDto carDto) {
        try {
            userService.updateCar(principal.getName(), carDto);
            return ResponseEntity.ok("Car successfully updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
