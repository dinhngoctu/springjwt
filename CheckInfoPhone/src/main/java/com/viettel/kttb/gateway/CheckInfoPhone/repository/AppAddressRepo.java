package com.viettel.kttb.gateway.CheckInfoPhone.repository;

import com.viettel.kttb.gateway.CheckInfoPhone.domain.model.AppAddress;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppAddressRepo extends PagingAndSortingRepository<AppAddress,Long> {
    AppAddress findByApplicationCode(String appCode);
}
