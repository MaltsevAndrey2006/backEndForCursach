package com.dev.backendforcursach.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserDto {
    private Long id;

    private String login;

    private String email;

    private String address;

    private BigDecimal balance;

    private String role;
}
