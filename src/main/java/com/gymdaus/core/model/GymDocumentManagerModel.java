package com.gymdaus.core.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymDocumentManagerModel {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deleteDate;
    private String extension;
    private String filename;
    private String path;
    private String gymName;
    private boolean signed;
    private String section; //(el tipo de archivo que sea, por ejemplo contrato, ampliación de registros, etc)
    private GymModel gymModel;

}