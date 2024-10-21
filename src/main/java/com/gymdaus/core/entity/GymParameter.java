package com.gymdaus.core.entity;

import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.Utils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@IdClass(ParameterId.class)
@Table(name = "gym_parameter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GymParameter {

    @Id
    @Column(nullable = false, length = Constants.KEY)
    private String keyData;
    @Column(nullable = false, length = Constants.VALUE)
    private String value;
    @Id
    private Long gymId;
    @Column(nullable = false)
    private Date modificationDate;
    @Column(nullable = false, length = Constants.USERNAME_OR_ID_CARD)
    private String modificationUsername;

    @Override
    public String toString() {
        return "Util{" +
                "keyData='" + keyData +
                ", value='" + Utils.ofuscar(value) +
                ", gymId=" + gymId +
                ", modificationDate=" + modificationDate +
                ", modificationUsername=" + modificationUsername +
                '}';
    }

}