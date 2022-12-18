package com.clearance.gigster.model.finance;

import com.clearance.gigster.model.student.Student;

import javax.persistence.*;

@Entity
@Table(name="finance_records")
public class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private Double outstandingFees;
    private Double graduationFee;

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

    public Double getOutstandingFees() {
        return outstandingFees;
    }

    public void setOutstandingFees(Double outstandingFees) {
        this.outstandingFees = outstandingFees;
    }

    public Double getGraduationFee() {
        return graduationFee;
    }

    public void setGraduationFee(Double graduationFee) {
        this.graduationFee = graduationFee;
    }
}
