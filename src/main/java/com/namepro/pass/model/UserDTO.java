package com.namepro.pass.model;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;

    private byte[] recording;
    private boolean isPrimary;
}