package com.viettel.kttb.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table(name = "app_address")
public class AppAddress {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequence", value = "APP_ADDRESS_SEQ")
    })
    private Long id;
    @Column(name="application_name")
    private String applicationName;
    @Column(name="application_code")
    private String applicationCode;
    @Column(name="host_name")
    private String hostName;
    @Column(name="port")
    private String port;
    @Column(name="description")
    private String description;
}
