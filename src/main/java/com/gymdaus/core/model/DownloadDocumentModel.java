package com.gymdaus.core.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DownloadDocumentModel {

    private int documentKind;
    private Long signatureId;
    private Long enrollmentId;
    private String documentName;

}