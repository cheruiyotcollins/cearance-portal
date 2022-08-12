package com.zetech.clearance.model.finance;

import com.zetech.clearance.model.department.Department;
import com.zetech.clearance.model.student.Student;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="finance_records")
public class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotNull
    private Double outstandingFees;

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
}
