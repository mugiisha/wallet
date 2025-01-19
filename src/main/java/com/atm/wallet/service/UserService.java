package com.atm.wallet.service;

import com.atm.wallet.config.JwtAuthenticationFilter;
import com.atm.wallet.dto.RegisterUserDto;
import com.atm.wallet.dto.UserDto;
import com.atm.wallet.exception.BadRequestException;
import com.atm.wallet.exception.NotFoundException;
import com.atm.wallet.model.User;
import com.atm.wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserDto createUser(RegisterUserDto registerUserDto){

        log.info("creating user with email: {}", registerUserDto.getEmail());

        Optional<User> existingUser = userRepository.findByEmail(registerUserDto.getEmail());

        if(existingUser.isPresent()){
            throw new BadRequestException("User with email already exists");
        }

        User userData = User
                .builder()
                .name(registerUserDto.getName())
                .email(registerUserDto.getEmail())
                .pin(passwordEncoder.encode(registerUserDto.getPin()))
                .build();

       User user = userRepository.save(userData);

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User getUserById(UUID userId){
        log.info("fetching user with id: {}", userId);
        User user = userRepository.findById(userId).orElse(null);

        if(user == null){
          throw new NotFoundException("User not found");
        }

        return user;
    }

    public User getUserByEmail(String email){
        log.info("fetching user with email: {}", email);
        User user = userRepository.findByEmail(email).orElse(null);

        if(user == null){
            throw new NotFoundException("User with email " + email + " does not exist");
        }

        return user;
    }

    public boolean userExistsById(UUID userId){
        log.info("checking if user exists with id: {}", userId);
        return userRepository.existsById(userId);
    }

    public void saveUser(User user){
        log.info("saving user with id: {}", user.getId());
         userRepository.save(user);
    }
}
