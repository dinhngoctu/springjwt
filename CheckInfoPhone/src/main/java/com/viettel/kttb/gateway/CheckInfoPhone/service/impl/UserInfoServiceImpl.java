package com.viettel.kttb.gateway.CheckInfoPhone.service.impl;

import com.viettel.kttb.gateway.CheckInfoPhone.domain.model.UserInformation;
import com.viettel.kttb.gateway.CheckInfoPhone.repository.UserInfoRepo;
import com.viettel.kttb.gateway.CheckInfoPhone.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoRepo userInformationRepo;

    public UserInformation findUserByIp(String ip) {
        return userInformationRepo.findByIpAddress(ip);
    }

    @Override
    public UserInformation findUserByName(String name) {
        return userInformationRepo.findByUserName(name);
    }
}
