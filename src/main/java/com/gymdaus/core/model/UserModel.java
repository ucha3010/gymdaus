package com.gymdaus.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserModel {

	private String username;
	private String password;
	private boolean enabled;
	private String name;
	private String lastname;
	private String secondLastname;
	private String sex;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthdate;
	private CountryModel country;
	private Date registrationDate;
	private Date modificationDate;
	private String modificationUsername;
	private String email;
	private String addressStreet;
	private String addressNumber;
	private String addressOther;
	private String addressCity;
	private String addressZip;
	private String phone;
	private List<String> userRoles = new ArrayList<>();

	@Override
	public String toString() {
		return "UserModel{" +
				"username='" + username + '\'' +
				", enabled=" + enabled +
				", name='" + name + '\'' +
				", lastname='" + lastname + '\'' +
				", secondLastname='" + secondLastname + '\'' +
				", sex='" + sex + '\'' +
				", birthdate=" + birthdate +
				", country=" + country +
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
				", userRoles=" + userRoles +
				'}';
	}
}
