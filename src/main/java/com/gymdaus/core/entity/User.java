package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(unique = true, nullable = false, length = Constants.USERNAME_OR_ID_CARD)
    private String username;
    @Column(nullable = false, length = Constants.PASSWORD)
    private String password;
    @Column(nullable = false)
    private boolean enabled;
    @Column(nullable = false, length = Constants.NAME)
    private String name;
    @Column(nullable = false, length = Constants.LASTNAME)
    private String lastname;
    @Column(length = Constants.SECOND_LASTNAME)
    private String secondLastname;
    @Column(length = Constants.SEX)
    private String sex;
    @Column(nullable = false)
    private Date birthdate;
    @Column(nullable = false, length = Constants.EMAIL)
    private String email;
    @Column(length = Constants.PHONE)
    private String phone;
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
    @Column(nullable = false)
    private Date registrationDate;
    private Date modificationDate;
    @Column(length = Constants.USERNAME_OR_ID_CARD)
    private String modificationUsername;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<UserRole> userRole = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "username='" + username +
                ", enabled=" + enabled +
                ", name='" + name +
                ", lastname='" + lastname +
                ", secondLastname='" + secondLastname +
                ", sex='" + sex +
                ", birthdate=" + birthdate +
                ", countryId=" + countryId +
                ", registrationDate=" + registrationDate +
                ", modificationDate=" + modificationDate +
                ", modificationUsername='" + modificationUsername +
                ", email='" + email +
                ", addressStreet='" + addressStreet +
                ", addressNumber='" + addressNumber +
                ", addressOther='" + addressOther +
                ", addressCity='" + addressCity +
                ", addressZip='" + addressZip +
                ", phone='" + phone +
                ", userRole=" + userRole +
                '}';
    }

}