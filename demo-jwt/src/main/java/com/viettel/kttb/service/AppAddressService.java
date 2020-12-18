package com.viettel.kttb.service;

import com.viettel.kttb.domain.model.AppAddress;

public interface AppAddressService {
    AppAddress findByAppCode(String appCode);
}
