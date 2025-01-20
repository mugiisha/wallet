package com.atm.wallet.controller;

import com.atm.wallet.dto.RegisterUserDto;
import com.atm.wallet.dto.UserDto;
import com.atm.wallet.model.User;
import com.atm.wallet.service.UserService;
import com.atm.wallet.util.Response;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Response<UserDto> registerUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
        UserDto user = userService.createUser(registerUserDto);
        return new Response<>(true, "User registered successfully", user);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{userId}")
    public Response<User> getUser(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return new Response<>(true, "User details retrieved successfully", user);
    }
}
