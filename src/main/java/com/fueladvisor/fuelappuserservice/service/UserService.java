package com.fueladvisor.fuelappuserservice.service;

import com.fueladvisor.fuelappuserservice.model.dto.LoginDto;
import com.fueladvisor.fuelappuserservice.model.dto.RegistrationDto;
import com.fueladvisor.fuelappuserservice.model.dto.TokenDto;
import com.fueladvisor.fuelappuserservice.model.entity.User;
import com.fueladvisor.fuelappuserservice.repository.UserRepository;
import com.fueladvisor.fuelappuserservice.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegistrationDto registrationDto) {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new IllegalStateException(String.format(
                    "Password and confirm password are not equal: %s != %s",
                    registrationDto.getPassword(),
                    registrationDto.getConfirmPassword()));
        }

        if (isUserExistsByEmail(registrationDto.getEmail())) {
            throw new IllegalStateException(String.format(
                    "User with email %s already exists", registrationDto.getEmail()));
        }

        User user = createNewUser(registrationDto);
        return saveUser(user);
    }

    public TokenDto loginUser(LoginDto loginDto) {
        User user = findUserByEmail(loginDto.getEmail())
                        .orElseThrow(() -> new UsernameNotFoundException(
                        "User with email " + loginDto.getEmail() + " does not exist."));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
            throw new BadCredentialsException("");

        String jwtToken = jwtProvider.generateToken(user.getEmail());
        return new TokenDto(jwtToken);
    }

    public TokenDto generateToken(String email) {
        return new TokenDto(jwtProvider.generateToken(email));
    }

    private Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private boolean isUserExistsByEmail(String email) {
        return findUserByEmail(email).isPresent();
    }

    private User saveUser(User user) {
        return userRepository.save(user);
    }

    private User createNewUser(RegistrationDto registrationDto) {
        return User.builder()
                .email(registrationDto.getEmail())
                .password(encodePassword(registrationDto.getPassword()))
                .registrationTimestamp(LocalDateTime.now())
                .build();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
