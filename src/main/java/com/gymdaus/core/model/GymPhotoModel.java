package com.gymdaus.core.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymPhotoModel {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deleteDate;
    private String extension;
    private String filename;
    private String path;
    private String section; //(campo de escritura abierto para poder organizar las fotos)
    private GymModel gymModel;

}