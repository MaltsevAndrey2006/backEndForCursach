package com.dev.backendforcursach.model.dto;

import lombok.Data;

@Data
public class UserRequest {

    private String login;
    private String password;
    private String email;

}
