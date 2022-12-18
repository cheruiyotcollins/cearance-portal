package com.clearance.gigster.model.student;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_confirmation")
public class Confirmation {

    @Id
    private Long studentId;
    private ConfirmationStatus graduationNameConfirmation;
    private int graduationNameConfirmationRequest;
    private ConfirmationStatus gownIssuanceConfirmation;
    private int gownIssuanceConfirmationRequest;

    private ConfirmationStatus gownClearanceConfirmation;
    private int gownClearanceConfirmationRequest;


    public int getGraduationNameConfirmationRequest() {
        return graduationNameConfirmationRequest;
    }

    public void setGraduationNameConfirmationRequest(int graduationNameConfirmationRequest) {
        this.graduationNameConfirmationRequest = graduationNameConfirmationRequest;
    }

    public int getGownIssuanceConfirmationRequest() {
        return gownIssuanceConfirmationRequest;
    }

    public void setGownIssuanceConfirmationRequest(int gownIssuanceConfirmationRequest) {
        this.gownIssuanceConfirmationRequest = gownIssuanceConfirmationRequest;
    }

    public int getGownClearanceConfirmationRequest() {
        return gownClearanceConfirmationRequest;
    }

    public void setGownClearanceConfirmationRequest(int gownClearanceConfirmationRequest) {
        this.gownClearanceConfirmationRequest = gownClearanceConfirmationRequest;
    }

    public ConfirmationStatus getGraduationNameConfirmation() {
        return graduationNameConfirmation;
    }

    public void setGraduationNameConfirmation(ConfirmationStatus graduationNameConfirmation) {
        this.graduationNameConfirmation = graduationNameConfirmation;
    }

    public ConfirmationStatus getGownIssuanceConfirmation() {
        return gownIssuanceConfirmation;
    }

    public void setGownIssuanceConfirmation(ConfirmationStatus gownIssuanceConfirmation) {
        this.gownIssuanceConfirmation = gownIssuanceConfirmation;
    }

    public ConfirmationStatus getGownClearanceConfirmation() {
        return gownClearanceConfirmation;
    }

    public void setGownClearanceConfirmation(ConfirmationStatus gownClearanceConfirmation) {
        this.gownClearanceConfirmation = gownClearanceConfirmation;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
