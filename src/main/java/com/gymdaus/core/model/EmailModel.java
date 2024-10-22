package com.gymdaus.core.model;

import lombok.*;

import java.io.File;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailModel {

    private String fromEmailAddress;
    private String toEmailAddress;
    private String messageSubject;
    private String bodyText;
    private List<File> files;
    private String host;
    private String port;
    private String password;

}