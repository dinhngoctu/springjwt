package com.viettel.kttb.gateway.CheckInfoPhone.repository;


import com.viettel.kttb.gateway.CheckInfoPhone.domain.model.UserInformation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepo extends PagingAndSortingRepository<UserInformation,Long> {
    UserInformation findByIpAddress(String ip);
    UserInformation findByUserName(String name);
}
