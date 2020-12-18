package com.viettel.kttb.gateway.CheckInfoPhone.service;


import com.viettel.kttb.gateway.CheckInfoPhone.domain.model.UserInformation;

public interface UserInfoService {
    UserInformation findUserByIp(String ip);
    UserInformation findUserByName(String name);
}
