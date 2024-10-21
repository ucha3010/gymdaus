package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private int attempts;
    private Long gymId;
    private boolean signed;

}