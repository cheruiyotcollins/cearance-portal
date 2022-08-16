package com.zetech.clearance.payload;

public class GownRequest {
    private Long studentId;
    private String gownId;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getGownId() {
        return gownId;
    }

    public void setGownId(String gownId) {
        this.gownId = gownId;
    }
}
