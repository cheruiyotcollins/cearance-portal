package com.zetech.clearance.model.graduation;

import com.zetech.clearance.model.department.Department;
import com.zetech.clearance.model.student.Student;

import javax.persistence.*;

@Entity
public class GraduationList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gown_id")
    private Gown gown;

    private String library_clearance;

    private String finance_clearance;

    private String gown_issuance;

    private String gown_clearance;


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

    public Gown getGown() {
        return gown;
    }

    public void setGown(Gown gown) {
        this.gown = gown;
    }

    public String getLibrary_clearance() {
        return library_clearance;
    }

    public void setLibrary_clearance(String library_clearance) {
        this.library_clearance = library_clearance;
    }

    public String getFinance_clearance() {
        return finance_clearance;
    }

    public void setFinance_clearance(String finance_clearance) {
        this.finance_clearance = finance_clearance;
    }

    public String getGown_issuance() {
        return gown_issuance;
    }

    public void setGown_issuance(String gown_issuance) {
        this.gown_issuance = gown_issuance;
    }

    public String getGown_clearance() {
        return gown_clearance;
    }

    public void setGown_clearance(String gown_clearance) {
        this.gown_clearance = gown_clearance;
    }
}
