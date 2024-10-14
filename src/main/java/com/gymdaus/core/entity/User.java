package com.gymdaus.core.entity;

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
	@Column(name = "username", unique = true, nullable = false, length = 45)
	private String username;
	
	@Column(name = "password", nullable = false, length = 60)
	private String password;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@Column(name = "name", length = 60)
	private String name;
	
	@Column(name = "lastname", length = 60)
	private String lastname;
	
	@Column(name = "secondLastname", length = 60)
	private String secondLastname;

	@Column(name = "sex", length = 9)
	private String sex;
	private Date birthdate;
	private int idCountry;
	private Date registrationDate;
	private Date modificationDate;

	@Column(name = "modificationUsername", length = 45)
	private String modificationUsername;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "addressStreet", length = 100)
	private String addressStreet;

	@Column(name = "addressNumber", length = 30)
	private String addressNumber;

	@Column(name = "addressOther", length = 50)
	private String addressOther;

	@Column(name = "addressCity", length = 50)
	private String addressCity;

	@Column(name = "addressZip", length = 10)
	private String addressZip;

	@Column(name = "phone", length = 20)
	private String phone;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserRole> userRole = new HashSet<>();

	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				", enabled=" + enabled +
				", name='" + name + '\'' +
				", lastname='" + lastname + '\'' +
				", secondLastname='" + secondLastname + '\'' +
				", sex='" + sex + '\'' +
				", birthdate=" + birthdate +
				", idCountry=" + idCountry +
				", registrationDate=" + registrationDate +
				", modificationDate=" + modificationDate +
				", modificationUsername='" + modificationUsername + '\'' +
				", email='" + email + '\'' +
				", addressStreet='" + addressStreet + '\'' +
				", addressNumber='" + addressNumber + '\'' +
				", addressOther='" + addressOther + '\'' +
				", addressCity='" + addressCity + '\'' +
				", addressZip='" + addressZip + '\'' +
				", phone='" + phone + '\'' +
				", userRole=" + userRole +
				'}';
	}
}
