package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "gym_photo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymPhoto {

    @Id
    @SequenceGenerator(name = "gymPhotoGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gymPhotoGenerator")
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
    @Column(length = Constants.FILE_SECTION)
    private String section; //(campo de escritura abierto para poder organizar las fotos)

}