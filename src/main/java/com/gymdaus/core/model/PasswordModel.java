package com.gymdaus.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordModel {

    private String username;
    private Long gymId;
    private Long gymAddressId;
    private String oldPassword;
    private String newPassword;
    private String newPasswordRepeat;
}
