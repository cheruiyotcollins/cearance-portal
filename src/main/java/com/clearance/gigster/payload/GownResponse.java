package com.clearance.gigster.payload;

public class GownResponse {
    private String regNo;
    private String  studentName;
    private String  gownNumber;
    private String  issuedAt;
    private String expectedReturnDate;

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

    public String getGownNumber() {
        return gownNumber;
    }

    public void setGownNumber(String gownNumber) {
        this.gownNumber = gownNumber;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(String expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }
}
