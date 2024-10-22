package com.gymdaus.core.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDocumentManagerModel {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deleteDate;
    private String extension;
    private String filename;
    private String path;
    private String username;
    private String name; //(campo de escritura abierto para poder poner un nombre entendible a los archivos)
    private UserModel userModel;

}