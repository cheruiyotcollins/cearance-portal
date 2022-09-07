package com.zetech.clearance.payload;

public class IssuedGownsResponse {

    private String regNo;
    private String studentName;
    private String IssuedDate;
    private String returnDate;

    private String clearedOn;
    private String certIssuedOn;

    public String getCertIssuedOn() {
        return certIssuedOn;
    }

    public void setCertIssuedOn(String certIssuedOn) {
        this.certIssuedOn = certIssuedOn;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIssuedDate() {
        return IssuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        IssuedDate = issuedDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getClearedOn() {
        return clearedOn;
    }

    public void setClearedOn(String clearedOn) {
        this.clearedOn = clearedOn;
    }
}
