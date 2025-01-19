package com.atm.wallet.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
@Builder
public class UserDto {
    private UUID id;
    private String name;
    private String email;
}
