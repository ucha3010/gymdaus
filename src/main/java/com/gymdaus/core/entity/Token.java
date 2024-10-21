package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Token {

    @Id
    @Column(length = Constants.TOKEN_ID)
    private String id;
    private int attempts;
    @Column(nullable = false)
    private Date expiration;
    private Long gymId;
    @Column(nullable = false, length = Constants.USERNAME_OR_ID_CARD)
    private String username;

}