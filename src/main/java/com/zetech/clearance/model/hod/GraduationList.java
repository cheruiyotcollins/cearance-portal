package com.zetech.clearance.model.hod;

import com.zetech.clearance.model.student.Student;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GraduationList {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private ClearanceStatus libraryClearance;

    private ClearanceStatus financeClearance;

    private ClearanceStatus gownIssuance;

    private ClearanceStatus gownClearance;
    private ClearanceStatus certificateClearance;

    private LocalDateTime  certClearedOn;

    public LocalDateTime getCertClearedOn() {
        return certClearedOn;
    }

    public void setCertClearedOn(LocalDateTime certClearedOn) {
        this.certClearedOn = certClearedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClearanceStatus getLibraryClearance() {
        return libraryClearance;
    }

    public void setLibraryClearance(ClearanceStatus libraryClearance) {
        this.libraryClearance = libraryClearance;
    }

    public ClearanceStatus getFinanceClearance() {
        return financeClearance;
    }

    public void setFinanceClearance(ClearanceStatus financeClearance) {
        this.financeClearance = financeClearance;
    }

    public ClearanceStatus getGownIssuance() {
        return gownIssuance;
    }

    public void setGownIssuance(ClearanceStatus gownIssuance) {
        this.gownIssuance = gownIssuance;
    }

    public ClearanceStatus getGownClearance() {
        return gownClearance;
    }

    public void setGownClearance(ClearanceStatus gownClearance) {
        this.gownClearance = gownClearance;
    }

     public ClearanceStatus getCertificateClearance() {
        return certificateClearance;
    }

    public void setCertificateClearance(ClearanceStatus certificateClearance) {
        this.certificateClearance = certificateClearance;
    }


}

