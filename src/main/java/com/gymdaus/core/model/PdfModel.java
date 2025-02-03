package com.gymdaus.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PdfModel {

    private String authorizerName; //Nombre Autorizador Apellido1dor Apellido2dor
    private String authorizerIdCard; //22222222A
    private String authorizerBirthdate; //30/10/1960
    private String address; //calle Mayor 150
    private String city; //Madrid (28003) - España
    private String phone; //654654654
    private String email; //pepe@pepe.com
    private String gymName; //Championdo
    private String gymAddress; //Av Viñuelas 30 Tres Cantos (28760)
    private String gymEmail; //gym@gym.com
    private String moreRegistrationName; //CAMPEONATO DE TRES CANTOS
    private String activityName; //TAEKWONDO
    private String moreRegistrationDate; //20/12/2022
    private String moreRegistrationAddress; //Polideportivo La Luz
    private String enrollmentAs; //padre
    private String minorName; //Nombre Autorizado Apellido1do Apellido2do
    private String minorIdCard; //01234567A
    private String minorBirthdate; //30/10/1960
    private boolean own;
    private boolean minor;
    private boolean inclusive;
    //private String cinturonActual; //Amarillo Naranja
    private Long enrollmentId;
    //private String categoria;
    //private String poomsae;
    private Long gymId;
    private boolean sepaDirectDebit;
    private String sepaAccountNumber;
    private String sepaAccountPerson;
    private String swift;
    private boolean signed;
    private String signedDate; //30/10/1960
}
