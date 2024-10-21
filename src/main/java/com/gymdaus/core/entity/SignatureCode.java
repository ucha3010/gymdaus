package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "signature_code")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SignatureCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = Constants.SIGNATURE_CODE)
    private String code;
    @Column(nullable = false, length = Constants.USERNAME_OR_ID_CARD)
    private String username;
    @Column(nullable = false)
    private Date registrationDate;
    @Column(nullable = false)
    private Date expirationDate;
    private Long operationId;
    @Column(nullable = false, length = Constants.OPERATION_NAME)
    private String operationName;
    @Column(nullable = false, length = Constants.URL)
    private String signedOkPage;
    private Long gymId;

}
