package com.zetech.clearance.payload;

public class FinanceResponse {
    private Long id;
    private String regNo;
    private String name;
    private Double outstandingFee;
    private Double graduationFee;

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

    public Double getOutstandingFee() {
        return outstandingFee;
    }

    public void setOutstandingFee(Double outstandingFee) {
        this.outstandingFee = outstandingFee;
    }

    public Double getGraduationFee() {
        return graduationFee;
    }

    public void setGraduationFee(Double graduationFee) {
        this.graduationFee = graduationFee;
    }
}
