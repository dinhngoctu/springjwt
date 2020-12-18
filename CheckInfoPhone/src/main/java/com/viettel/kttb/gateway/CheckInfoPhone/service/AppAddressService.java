package com.viettel.kttb.gateway.CheckInfoPhone.service;


import com.viettel.kttb.gateway.CheckInfoPhone.domain.model.AppAddress;

public interface AppAddressService {
    AppAddress findByAppCode(String appCode);
}
