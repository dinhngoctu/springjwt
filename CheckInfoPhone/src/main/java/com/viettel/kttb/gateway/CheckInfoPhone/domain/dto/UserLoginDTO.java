package com.viettel.kttb.gateway.CheckInfoPhone.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
    private String userName;
    private String password;

    public UserLoginDTO() {
    }
}
