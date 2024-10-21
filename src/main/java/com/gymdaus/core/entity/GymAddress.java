package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gym_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymAddress {

    @Id
    @SequenceGenerator(name = "gymAddressGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymAddressGenerator")
    private Long id;
    @Column(nullable = false, length = Constants.GYM_NAME)
    private String name;
    private boolean enabled;
    @Column(nullable = false, length = Constants.EMAIL)
    private String email;
    @Column(length = Constants.EMAIL_HOST)
    private String emailHost;
    @Column(length = Constants.PASSWORD)
    private String emailPassword;
    @Column(length = Constants.EMAIL_PORT)
    private String emailPort;
    @Column(length = Constants.PHONE)
    private String phone;
    @Column(nullable = false, length = Constants.ADDRESS_STREET)
    private String addressStreet;
    @Column(length = Constants.ADDRESS_NUMBER)
    private String addressNumber;
    @Column(length = Constants.ADDRESS_OTHER)
    private String addressOther;
    @Column(nullable = false, length = Constants.ADDRESS_CITY)
    private String addressCity;
    @Column(nullable = false, length = Constants.ADDRESS_ZIP)
    private String addressZip;
    private Long countryId;

}