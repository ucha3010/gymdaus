package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "gym_document_manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymDocumentManager {

    @Id
    @SequenceGenerator(name = "gymDocumentManagerGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymDocumentManagerGenerator")
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
    private Long gymId;
    @Column(length = Constants.GYM_NAME)
    private String gymName;
    private boolean signed;
    @Column(length = Constants.FILE_SECTION)
    private String section; //(el tipo de archivo que sea, por ejemplo contrato, ampliación de registros, etc)

}