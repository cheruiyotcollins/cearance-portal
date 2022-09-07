package com.zetech.clearance.payload;

public class GetStudentResponse {

    private Long id;
    private String regNo;
    private String name;
    private String course;
    private String department;
    private String nameConfirmation;

    public String getNameConfirmation() {
        return nameConfirmation;
    }

    public void setNameConfirmation(String nameConfirmation) {
        this.nameConfirmation = nameConfirmation;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
