package com.fueladvisor.fuelappuserservice.controller;

import com.fueladvisor.fuelappuserservice.model.dto.LoginDto;
import com.fueladvisor.fuelappuserservice.model.dto.RegistrationDto;
import com.fueladvisor.fuelappuserservice.model.dto.Response;
import com.fueladvisor.fuelappuserservice.model.dto.TokenDto;
import com.fueladvisor.fuelappuserservice.model.entity.User;
import com.fueladvisor.fuelappuserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/security")
public class SecurityController {
    private final UserService userService;

    @Autowired
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsingEmail(@RequestBody LoginDto loginDto) {
        try {
            TokenDto tokenDto = userService.loginUser(loginDto);
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response(null, false, "Email or password is incorrect."));
        }
    }

    @PostMapping("/sign up")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationDto registrationDto) {
        try {
            User newUser = userService.registerUser(registrationDto);
            return ResponseEntity.ok(new Response(newUser, true, "You have been successfully registered"));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response(null, false, e.getMessage()));
        }
    }

    @PostMapping("/update-token")
    public ResponseEntity<?> authenticateUsingJwtToken(Principal principal) {
        try {
            TokenDto tokenDto = userService.generateToken(principal.getName());
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response(null, false, "Jwt token is invalid."));
        }
    }
}
