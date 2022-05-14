package com.namepro.pass.model;

import lombok.Data;

@Data
public class UserPronunciationDTO {

    private String username;
    private byte[] recording;
    private boolean isPrimary;
}
