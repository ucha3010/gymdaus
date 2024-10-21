package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "user_document_manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDocumentManager {

    @Id
    @SequenceGenerator(name = "userDocumentManagerGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userDocumentManagerGenerator")
    private Long id;
    @Column(nullable = false)
    private Date creationDate;
    private Date deleteDate;
    @Column(nullable = false, length = Constants.FILE_EXTENSION)
    private String extension;
    @Column(nullable = false, length = Constants.FILE_NAME)
    private String filename;
    @Column(nullable = false, length = Constants.FILE_PATH)
    private String path;
    @Column(nullable = false, length = Constants.USERNAME_OR_ID_CARD)
    private String username;
    @Column(length = Constants.FILE_SECTION)
    private String name; //(campo de escritura abierto para poder poner un nombre entendible a los archivos)

}