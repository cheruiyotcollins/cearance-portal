package com.zetech.clearance.controller;

import com.zetech.clearance.model.hod.GraduationList;
import com.zetech.clearance.payload.*;
import com.zetech.clearance.repository.student.StudentRepository;
import com.zetech.clearance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/api/student")
@CrossOrigin
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/save")
public Object  saveOrUpdate (@Valid @RequestBody SaveStudentRequest saveStudentRequest){
    return studentService.saveOrUpdate(saveStudentRequest);
}


    @GetMapping("/findbyid/{id}")
    public Object findById (@PathVariable Long id){
        return studentService.findById(id);
    }
    @GetMapping("/findbyroleid/{id}")
    public ResponseEntity<?> findbyroleid (@PathVariable Long id){
        return studentService.findByRoleId(id);
    }


    @GetMapping("/list")
    public List<GetStudentResponse> findAll(){
        return studentService.findAll();
    }

    @GetMapping("/confirm/gown/issuance/{id}")
    public ResponseEntity<?> confirmGownIssuance(@PathVariable Long id){

        return  studentService.confirmGownIssuance(id);
    }

    @GetMapping("/confirm/gown/clearance/{id}")
    public ResponseEntity<?>  confirmGownClearance(@PathVariable Long id){

        return  studentService.confirmGownClearance(id);
    }
    @GetMapping("/gown/clearance/status/{id}")
    public ResponseEntity<?>  gownClearanceStatus(@PathVariable Long id){

        return  studentService.gownClearanceStatus(id);

    }

    @GetMapping("/gown/issuance/status/{id}")
    public ResponseEntity<?>  gownIssuanceStatus(@PathVariable Long id){

        return  studentService.gownIssuanceStatus(id);
    }

   @GetMapping("/reports/{id}")
    public GraduationListResponse graduationReports(@PathVariable Long id){

        return  studentService.graduationReports(id);
    }


    @PostMapping("/confirm/graduation/name")
    public Object  confirmGraduationName (@Valid @RequestBody ConfirmGraduationNameRequest confirm){
        return studentService.confirmGraduationName(confirm);
    }




    @GetMapping("/checkstudent")
    public UserIdentityAvailability checkStudentAvailability(@RequestParam(value = "regNo") String regNo) {

        Boolean isAvailable = !studentRepository.existsByRegNo(regNo);
        return new UserIdentityAvailability(isAvailable);
    }
}
