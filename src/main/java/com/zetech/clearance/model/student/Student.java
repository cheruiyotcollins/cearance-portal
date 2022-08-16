package com.zetech.clearance.model.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zetech.clearance.model.department.Course;
import com.zetech.clearance.model.department.Department;
import com.zetech.clearance.model.finance.Finance;
import com.zetech.clearance.model.graduation.GraduationList;
import com.zetech.clearance.model.library.Library;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="students")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 15)
    private String regNo;

    @NotBlank
    @Size(max = 40)
    private String name;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Course course;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Finance finance;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Library library;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY)

    private GraduationList graduationList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public GraduationList getGraduationList() {
        return graduationList;
    }

    public void setGraduationList(GraduationList graduationList) {
        this.graduationList = graduationList;
    }
}
