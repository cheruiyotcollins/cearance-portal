package com.zetech.clearance.service;

import com.zetech.clearance.model.hod.GraduationList;
import com.zetech.clearance.model.student.Confirmation;
import com.zetech.clearance.model.student.ConfirmationStatus;
import com.zetech.clearance.model.student.Student;
import com.zetech.clearance.payload.*;
import com.zetech.clearance.repository.department.CourseRepository;
import com.zetech.clearance.repository.department.DepartmentRepository;
import com.zetech.clearance.repository.graduation.GraduationListRepository;
import com.zetech.clearance.repository.student.ConfirmationRepository;
import com.zetech.clearance.repository.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    FinanceService financeService;

    @Autowired
    ConfirmationRepository confirmationRepository;
    @Autowired
    GraduationListRepository graduationListRepository;

    @Autowired
    EmailService emailSender;

    GeneralResponse generalResponse;
    Student student;
    GetStudentResponse getStudentResponse;

    public Object saveOrUpdate(SaveStudentRequest saveStudentRequest) {
        generalResponse = new GeneralResponse();

        System.out.println(saveStudentRequest.getCourse());
        System.out.println(saveStudentRequest.getDepartmentId());
        System.out.println(saveStudentRequest.getName());
        System.out.println(saveStudentRequest.getRegNo());


        try {
            System.out.println("entered try block");
            student = new Student();
            student.setCourse(courseRepository.findById(saveStudentRequest.getCourse()).get());
            student.setName(saveStudentRequest.getName());
            student.setRegNo(saveStudentRequest.getRegNo());
            student.setDepartment(departmentRepository.findById(saveStudentRequest.getDepartmentId()).get());



            Student newStudent = studentRepository.save(student);
            financeService.addRecord(newStudent);
            return newStudent;

        } catch (Exception e) {
            generalResponse.status = "failed";
            generalResponse.description = "Error Saving ";
            System.out.println("entered catch block");
            return generalResponse;
        }
    }


    public ResponseEntity<?>  findByRoleId(Long id) {
        generalResponse = new GeneralResponse();


            getStudentResponse = new GetStudentResponse();
            Student student=studentRepository.findByUserId(id);
            Confirmation confirmation =confirmationRepository.findById(student.getId()).get();
            getStudentResponse.setNameConfirmation(confirmation.getGraduationNameConfirmation().name());
            getStudentResponse.setCourse(student.getCourse().getCourseName());
            getStudentResponse.setId(student.getId());
            getStudentResponse.setName(student.getName());
            getStudentResponse.setRegNo(student.getRegNo());
            getStudentResponse.setDepartment(student.getDepartment().getDepartmentName());
            return new ResponseEntity(getStudentResponse,HttpStatus.ACCEPTED);



    }
    public Object findById(Long id) {
        generalResponse = new GeneralResponse();
        try {
            return setStudentResponse(studentRepository.findById(id).get());

        } catch (Exception e) {
            generalResponse.status = "failed";
            generalResponse.description = "student not found";
            return generalResponse;
        }

    }

    public List<GetStudentResponse> findAll() {

        List<GetStudentResponse> allStudents = new ArrayList<>();
        studentRepository.findAll().forEach(student -> {

            allStudents.add(setStudentResponse(student));

        });

        return allStudents;

    }



    public GetStudentResponse setStudentResponse(Student student) {
        getStudentResponse = new GetStudentResponse();

        getStudentResponse.setCourse(student.getCourse().getCourseName());
        getStudentResponse.setId(student.getId());
        getStudentResponse.setName(student.getName());
        getStudentResponse.setRegNo(student.getRegNo());
        getStudentResponse.setDepartment(student.getDepartment().getDepartmentName());
        return getStudentResponse;
    }
    public GraduationListResponse graduationReports(Long id) {
          GraduationList graduation= graduationListRepository.findByStudent(studentRepository.findByUserId(id));

        GraduationListResponse response = new GraduationListResponse();
        response.setRegNo(graduation.getStudent().getRegNo());
        response.setLibraryClearance(graduation.getLibraryClearance().name());
        response.setStudentName(graduation.getStudent().getName());
        response.setFinanceClearance(graduation.getFinanceClearance().name());
        response.setGownClearance(graduation.getGownClearance().name());
        response.setGownIssuance(graduation.getGownIssuance().name());
        response.setCertificateIssuance(graduation.getCertificateClearance().name());
        response.setId(graduation.getId());
        return response;
    }
    public ResponseEntity<?> deleteById(Long id) {
        generalResponse = new GeneralResponse();
        try {
            studentRepository.deleteById(id);
            generalResponse.status = "Success";
            generalResponse.description = "student deleted successfully";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            generalResponse.status = "failed";
            generalResponse.description = "student deletion failed";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);

        }

    }

    public ResponseEntity<?>  gownIssuanceStatus(Long id){

        GownConfirmation gownConfirmation= new GownConfirmation();
        Confirmation confirm = confirmationRepository.findById(studentRepository.findByUserId(id).getId()).get();
        gownConfirmation.setConfirmationStatus(confirm.getGownIssuanceConfirmation().name());
        gownConfirmation.setName(studentRepository.findByUserId(id).getName());
        gownConfirmation.setId(id);

        return new ResponseEntity(gownConfirmation, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?>  gownClearanceStatus(Long id){

        GownConfirmation gownConfirmation= new GownConfirmation();
        Confirmation confirm = confirmationRepository.findById(studentRepository.findByUserId(id).getId()).get();
        gownConfirmation.setConfirmationStatus(confirm.getGownClearanceConfirmation().name());
        gownConfirmation.setName(studentRepository.findByUserId(id).getName());
        gownConfirmation.setId(id);

        return new ResponseEntity(gownConfirmation, HttpStatus.ACCEPTED);
    }



    public ResponseEntity<?> confirmGownIssuance(Long id) {
        generalResponse = new GeneralResponse();
        try {
            Confirmation confirm = confirmationRepository.findById(studentRepository.findByUserId(id).getId()).get();
            confirm.setGownIssuanceConfirmationRequest(0);
            confirm.setGownIssuanceConfirmation(ConfirmationStatus.CONFIRMED);
            confirmationRepository.save(confirm);

            GownConfirmation gownConfirmation= new GownConfirmation();
            gownConfirmation.setConfirmationStatus("CONFIRMED");
            gownConfirmation.setName(studentRepository.findByUserId(id).getName());
            gownConfirmation.setId(id);

            generalResponse.status = "Success";
            generalResponse.description = "Gown Issuance Confirmation Success";
            return new ResponseEntity(gownConfirmation, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            generalResponse.status = "Fail";
            generalResponse.description = "Something went wrong";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);

        }

    }

    public ResponseEntity<?> confirmGraduationName(ConfirmGraduationNameRequest confirm) {
        generalResponse = new GeneralResponse();
        try {
            Student student = studentRepository.findByUserId(confirm.getId());
            student.setName(confirm.getName());
            studentRepository.save(student);
            Confirmation conf= confirmationRepository.findById(student.getId()).get();
            conf.setGraduationNameConfirmation(ConfirmationStatus.CONFIRMED);
            confirmationRepository.save(conf);
               generalResponse.status = "Success";
            generalResponse.description = "Student Name Confirmation Success";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            generalResponse.status = "Fail";
            generalResponse.description = "Something went wrong";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);

        }

    }


    public ResponseEntity<?> confirmGownClearance(Long id) {
        generalResponse = new GeneralResponse();
        try {
            Confirmation confirm = confirmationRepository.findById(studentRepository.findByUserId(id).getId()).get();
            confirm.setGownClearanceConfirmationRequest(0);
            confirm.setGownClearanceConfirmation(ConfirmationStatus.CONFIRMED);
            confirmationRepository.save(confirm);
            GownConfirmation gownConfirmation= new GownConfirmation();
            gownConfirmation.setConfirmationStatus("CONFIRMED");
            gownConfirmation.setName(studentRepository.findByUserId(id).getName());
            gownConfirmation.setId(id);
            generalResponse.status = "Success";
            generalResponse.description = "Gown Clearance Success";
            return new ResponseEntity(gownConfirmation, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            generalResponse.status = "Fail";
            generalResponse.description = "Something Went Wrong";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);

        }

    }


}
