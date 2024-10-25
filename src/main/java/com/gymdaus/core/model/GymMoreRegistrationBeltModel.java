package com.gymdaus.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymMoreRegistrationBeltModel {

    private Long id;
    /*
    * Los cinturones se guardan en GymBelt (donde estarán todos los
    * colores de todas las artes marciales) y al generar un torneo
    * se seleccionan, de todos los cinturones existentes para este
    * gimnasio, los cinturones que sean acordes a este torneo (y
    * esta arte marcial). Con lo cual el color se extrae del
    * objeto GymBelt
    */
    private GymBeltModel gymBeltModel;
    private int position;
    private GymMoreRegistrationModel gymMoreRegistrationModel;

}