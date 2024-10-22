package com.gymdaus.core.model;

import com.gymdaus.core.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerParameterModel {

    private Long id;
    private String email;
    private String emailHost;
    private String emailPort;
    private String hostPageName;
    private String password;

    @Override
    public String toString() {
        return "ManagerParameter{" +
                "id=" + id +
                ", hostPageName='" + hostPageName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + Utils.ofuscar(password) + '\'' +
                ", emailHost='" + emailHost + '\'' +
                ", emailPort='" + emailPort + '\'' +
                '}';
    }
}
