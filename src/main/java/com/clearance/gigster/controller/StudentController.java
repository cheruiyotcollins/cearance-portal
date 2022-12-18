package com.clearance.gigster.controller;

import com.clearance.gigster.payload.*;
import com.clearance.gigster.repository.student.StudentRepository;
import com.clearance.gigster.security.CurrentUser;
import com.clearance.gigster.security.UserPrincipal;
import com.clearance.gigster.service.StudentService;

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

   @GetMapping("/reports")
    public GraduationListResponse graduationReports(@CurrentUser UserPrincipal currentUser){

        return  studentService.graduationReports(currentUser.getId());
    }

    @GetMapping("/view/graduation/names")
    public GraduationListResponse viewNamesOnGraduation(@CurrentUser UserPrincipal currentUser){

        return  studentService.viewGraduationNames(currentUser.getId());
    }




    @PostMapping("/confirm/graduation/name")
    public Object  confirmGraduationName (@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ConfirmGraduationNameRequest confirm){
        return studentService.confirmGraduationName(currentUser,confirm);
    }




    @GetMapping("/checkstudent")
    public UserIdentityAvailability checkStudentAvailability(@RequestParam(value = "regNo") String regNo) {

        Boolean isAvailable = !studentRepository.existsByRegNo(regNo);
        return new UserIdentityAvailability(isAvailable);
    }
}
