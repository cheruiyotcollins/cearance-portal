package com.clearance.gigster.model.records;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="gowns")
public class Gown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long studentId;
    @CreationTimestamp
    @CreatedDate
    private LocalDateTime issuedAt;

    private LocalDateTime  returnBy;
    private LocalDateTime  clearedOn;
    private Long Fine;


    public Long getFine() {
        return Fine;
    }

    public void setFine(Long fine) {
        Fine = fine;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getReturnBy() {
        return returnBy;
    }

    public void setReturnBy(LocalDateTime returnBy) {
        this.returnBy = returnBy;
    }

    public LocalDateTime getClearedOn() {
        return clearedOn;
    }

    public void setClearedOn(LocalDateTime clearedOn) {
        this.clearedOn = clearedOn;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
