package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "gym_more_registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistration {

    @Id
    @SequenceGenerator(name = "gymMoreRegistrationGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymMoreRegistrationGenerator")
    private Long id;
    private Long gymId;
    private Long moreRegistrationId;
    @Column(length = Constants.GYM_MORE_REGISTRATION_NAME)
    private String name;
    @Column(nullable = false)
    private Date registrationDate;
    @Column(nullable = false, length = Constants.USERNAME_OR_ID_CARD)
    private String registrationUser;
    private boolean enabled;
    @Column(length = Constants.ADDRESS_STREET)
    private String addressStreet;
    @Column(length = Constants.ADDRESS_NUMBER)
    private String addressNumber;
    @Column(length = Constants.ADDRESS_OTHER)
    private String addressOther;
    @Column(length = Constants.ADDRESS_CITY)
    private String addressCity;
    @Column(length = Constants.ADDRESS_ZIP)
    private String addressZip;
    private Long countryId;
    private boolean needsPaid;
    private boolean sepa;

}