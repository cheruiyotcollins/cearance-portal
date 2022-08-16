package com.zetech.clearance.payload;

public class FinanceRequest {
    private Long studentId;
    private Double amount;
    private Double graduationFee;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
}

    public Double getGraduationFee() {
        return graduationFee;
    }

    public void setGraduationFee(Double graduationFee) {
        this.graduationFee = graduationFee;
    }
}
