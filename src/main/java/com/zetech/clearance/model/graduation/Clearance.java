package com.zetech.clearance.model.graduation;

import com.zetech.clearance.model.RoleName;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
public class Clearance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private ClearanceStatus clearanceStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClearanceStatus getClearanceStatus() {
        return clearanceStatus;
    }

    public void setClearanceStatus(ClearanceStatus clearanceStatus) {
        this.clearanceStatus = clearanceStatus;
    }
}
