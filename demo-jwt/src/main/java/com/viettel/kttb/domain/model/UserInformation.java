package com.viettel.kttb.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_information")
public class UserInformation {
    public UserInformation() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequence", value = "USER_INFO_SEQ")
    })
    private Long id;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "host_name")
    private String hostName;

}
