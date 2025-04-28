package com.example.libraryMgmt.controllers;


import com.example.libraryMgmt.dto.ApiResponse;
import com.example.libraryMgmt.dto.user.AuthResponseDTO;
import com.example.libraryMgmt.dto.user.LoginRequestDTO;
import com.example.libraryMgmt.dto.user.UserRequestDTO;
import com.example.libraryMgmt.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;


    @Operation(
            summary = "Create a new user",
            description = "This API endpoint is used to create a new user in the system.",
            responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> createUser(@RequestBody UserRequestDTO request) {
        AuthResponseDTO response =  userService.createUser(request);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "User Created", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> loginUser(@RequestBody LoginRequestDTO request) {
        AuthResponseDTO response =  userService.loginUser(request);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "User Created", response));
    }
}
