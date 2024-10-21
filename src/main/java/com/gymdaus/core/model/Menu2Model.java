package com.gymdaus.core.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Menu2Model {

    private Long id;
    private Long idMenu1;
    private boolean enabled;
    private String name;
    private int position;
    private String url;
    private String advise;
    private List<GymModel> gymModelList;
    private boolean checked;

}
