package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu1")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Menu1 {

    @Id
    @SequenceGenerator(name = "menu1Generator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu1Generator")
    private Long id;
    private boolean enabled;
    @Column(length = Constants.MENU_NAME, nullable = false)
    private String name;
    private int position;

}