package com.atm.wallet.controller;

import com.atm.wallet.dto.LoginDto;
import com.atm.wallet.dto.LoginResponseDto;
import com.atm.wallet.service.AuthService;
import com.atm.wallet.util.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Response<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto){
        LoginResponseDto response = authService.login(loginDto);
        return new Response<>(true, "User login successful", response);

    }
}
