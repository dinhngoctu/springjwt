package com.viettel.kttb.gateway.CheckInfoPhone.service.impl;

import com.viettel.kttb.gateway.CheckInfoPhone.domain.model.AppAddress;
import com.viettel.kttb.gateway.CheckInfoPhone.repository.AppAddressRepo;
import com.viettel.kttb.gateway.CheckInfoPhone.service.AppAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppAddressServiceImpl implements AppAddressService {
    @Autowired
    AppAddressRepo appAddressRepo;
    public AppAddress findByAppCode(String appCode) {
        return appAddressRepo.findByApplicationCode(appCode);
    }
}
