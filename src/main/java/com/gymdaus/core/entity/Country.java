package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "country")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Country {

    @Id
    @SequenceGenerator(name = "countryGenerator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countryGenerator")
    private Long id;
    @Column(nullable = false, length = Constants.COUNTRY_NAME)
    private String name; //debe buscar el nombre en un archivo properties
    private int position;

}
