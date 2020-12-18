package com.viettel.kttb.service.impl;

import com.viettel.kttb.domain.model.AppAddress;
import com.viettel.kttb.repository.AppAddressRepo;
import com.viettel.kttb.service.AppAddressService;
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
