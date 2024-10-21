package com.gymdaus.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordModel {

    private int id;
    private String username;
    private String oldPassword;
    private String newPassword;
    private String newPasswordRepeat;
}
