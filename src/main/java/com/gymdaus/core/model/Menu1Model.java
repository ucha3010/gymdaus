package com.gymdaus.core.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Menu1Model {

    private Long id;
    private boolean enabled;
    private String name;
    private int position;
    private List<Menu2Model> menu2ModelList;

}
