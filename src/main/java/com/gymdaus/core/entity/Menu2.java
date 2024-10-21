package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu2")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Menu2 {

    @Id
    @SequenceGenerator(name = "menu2Generator", sequenceName = "CHANGE_LOW_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu2Generator")
    private Long id;
    private Long menu1Id;
    private boolean enabled;
    @Column(length = Constants.MENU_NAME, nullable = false)
    private String name;
    private int position;
    @Column(length = Constants.URL, nullable = false)
    private String url;
    @Column(length = Constants.ADVISE)
    private String advise;

}