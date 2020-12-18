package com.viettel.kttb.service;

import com.viettel.kttb.domain.model.UserInformation;

public interface UserInfoService {
    UserInformation findUserByIp(String ip);
    UserInformation findUserByName(String name);
}
