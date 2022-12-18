package com.clearance.gigster.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class GraduationListResponse {
    private Long id;
    private String studentName;
    private String regNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String libraryClearance;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String financeClearance;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String gownIssuance;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String gownClearance;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String certificateIssuance;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getLibraryClearance() {
        return libraryClearance;
    }

    public void setLibraryClearance(String libraryClearance) {
        this.libraryClearance = libraryClearance;
    }

    public String getFinanceClearance() {
        return financeClearance;
    }

    public void setFinanceClearance(String financeClearance) {
        this.financeClearance = financeClearance;
    }

    public String getGownIssuance() {
        return gownIssuance;
    }

    public void setGownIssuance(String gownIssuance) {
        this.gownIssuance = gownIssuance;
    }

    public String getGownClearance() {
        return gownClearance;
    }

    public void setGownClearance(String gownClearance) {
        this.gownClearance = gownClearance;
    }

    public String getCertificateIssuance() {
        return certificateIssuance;
    }

    public void setCertificateIssuance(String certificateIssuance) {
        this.certificateIssuance = certificateIssuance;
    }
}
