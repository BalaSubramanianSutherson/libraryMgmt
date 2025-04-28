package com.example.libraryMgmt.services;

import com.example.libraryMgmt.dto.user.AuthResponseDTO;
import com.example.libraryMgmt.dto.user.LoginRequestDTO;
import com.example.libraryMgmt.dto.user.UserRequestDTO;
import com.example.libraryMgmt.model.Role;
import com.example.libraryMgmt.model.User;
import com.example.libraryMgmt.repositories.UserRepository;
import com.example.libraryMgmt.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO createUser(UserRequestDTO request){
        if (request.getPhoneNumber().isEmpty() || request.getFirstName().isEmpty() || request.getLastName().isEmpty()
                || request.getRoleId().isEmpty()) {
            throw new IllegalArgumentException("Required fields are empty");
        }
        logger.info("Creating new user with phone number: {}", request.getPhoneNumber());

        if (userRepository.existsById(request.getPhoneNumber())){
            throw new IllegalArgumentException("User already exists");
        }

        User user = new User();
        user.setId(request.getPhoneNumber());
        user.setFirstname(request.getFirstName());
        user.setLastname(request.getLastName());
        user.setRole(new Role(request.getRoleId()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedBy("Admin");
        user.setUpdatedBy("Admin");

        User updated = userRepository.save(user);
        logger.info("User created successfully with ID: {}", updated.getId());
        String token = jwtUtil.generateToken(updated.getId());
        logger.info("Generated JWT token for user: {}", token);
        return new AuthResponseDTO(token);

    }

    public AuthResponseDTO loginUser(LoginRequestDTO request) {
        User user = userRepository.findById(request.getPhoneNumber()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        String token = jwtUtil.generateToken(user.getId());
        return new AuthResponseDTO(token);
    }
}

