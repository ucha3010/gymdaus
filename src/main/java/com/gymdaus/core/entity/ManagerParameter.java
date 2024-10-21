package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.Utils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "manager_parameter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerParameter {

    @Id
    @SequenceGenerator(name = "managerParameterGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "managerParameterGenerator")
    private Long id;
    @Column(length = Constants.EMAIL)
    private String email;
    @Column(length = Constants.EMAIL_HOST)
    private String emailHost;
    @Column(length = Constants.EMAIL_PORT)
    private String emailPort;
    @Column(length = Constants.HOST_PAGE_NAME)
    private String hostPageName;
    @Column(length = Constants.PASSWORD)
    private String password;

    @Override
    public String toString() {
        return "ManagerParameter{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", emailHost='" + emailHost + '\'' +
                ", emailPort='" + emailPort + '\'' +
                ", hostPageName='" + hostPageName + '\'' +
                ", password='" + Utils.ofuscar(password) + '\'' +
                '}';
    }

}