package com.zetech.clearance.model.graduation;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="gowns")
public class Gown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String serialNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
