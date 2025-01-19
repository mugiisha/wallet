package com.atm.wallet.service;

import com.atm.wallet.dto.LoginDto;
import com.atm.wallet.dto.LoginResponseDto;
import com.atm.wallet.dto.UserDto;
import com.atm.wallet.exception.BadRequestException;
import com.atm.wallet.exception.NotFoundException;
import com.atm.wallet.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

public LoginResponseDto login(LoginDto loginDto){
        log.info("logging in user with email: {}", loginDto.getEmail());

        User user = userService.getUserByEmail(loginDto.getEmail());

        if(user == null){
            throw new NotFoundException("User with email " + loginDto.getEmail() + " does not exist");
        }

        if(!passwordEncoder.matches(loginDto.getPin(), user.getPin())){
            throw new BadRequestException("Invalid PIN");
        }

        String token = jwtService.generateToken(user);

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        return LoginResponseDto.builder()
                .user(userDto)
                .token(token)
                .build();
}
}
