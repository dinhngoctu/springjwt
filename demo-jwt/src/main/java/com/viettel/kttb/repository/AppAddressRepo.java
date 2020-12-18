package com.viettel.kttb.repository;

import com.viettel.kttb.domain.model.AppAddress;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppAddressRepo extends PagingAndSortingRepository<AppAddress,Long> {
    AppAddress findByApplicationCode(String appCode);
}
