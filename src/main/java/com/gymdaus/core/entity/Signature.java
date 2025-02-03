package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "signature")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long operationId; //(id de la operación que sea como una inscripción, una subida de documento, etc)
    @Column(nullable = false, length = Constants.OPERATION_NAME)
    private String operationName;
    @Column(length = Constants.USERNAME_OR_ID_CARD)
    private String username;
    private Long gymId;
    private boolean signed;
    private Date registrationDate;
    @Column(length = Constants.SIGNATURE_CODE)
    private String code;
    private int attempts;
    private Date expirationDate;
    private int sentCodeAttempts;
    private boolean signatureLocked;
    @Column(nullable = false, length = Constants.TABLE_TO_SEARCH)
    private String tableToSearch;
    private String language;

}