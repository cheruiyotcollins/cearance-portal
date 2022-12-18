package com.clearance.gigster.controller;

import com.clearance.gigster.payload.GraduationListResponse;
import com.clearance.gigster.payload.IssuedGownsResponse;
import com.clearance.gigster.payload.PaymentRequest;
import com.clearance.gigster.service.GraduationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/graduation")
@CrossOrigin
public class GraduationListController {

    @Autowired
    GraduationService graduationService;

    @GetMapping("/generate")
    public void generateGraduation(){
        graduationService.generateGraduationList();
    }

    @GetMapping("/list")
    public List<GraduationListResponse> getGraduationList(){
        graduationService.getGraduationList().forEach(a->{

        });
        return graduationService.getGraduationList();
    }

    @GetMapping("/gown/issued/list")
    public List<IssuedGownsResponse> studentsIssuedGowns(){
       return graduationService.getIssuedGowns();
    }

    @GetMapping("/gown/cleared/list")
    public List<IssuedGownsResponse> clearedGowns(){
        return graduationService. getReturnedGowns();
    }


    @GetMapping("/findbyid/{id}")
    public Object getGraduationById(@PathVariable Long id){
        return graduationService.getGraduationRecordById(id);
    }

    @GetMapping("/gown/issuance/{id}")
    public ResponseEntity<?> issueGown(@PathVariable Long id){
       return graduationService.gownIssuance(id);
    }
    @GetMapping("/gown/clearance/{id}")
    public ResponseEntity<?> clearGown(@PathVariable Long id){
         return graduationService.gownClearance(id);
    }

     @GetMapping("/certificate/issuance/{id}")
    public ResponseEntity<?> certificateClearance(@PathVariable Long id){

          return graduationService.certificateClearance(id);
    }

    @PostMapping("/gown/fine/payment")
    public ResponseEntity<?> gownFinePayment(@RequestBody PaymentRequest payment){

        return graduationService.gownFinePayment(payment);
    }
    @GetMapping("/certificate/cleared/list")
    public List<IssuedGownsResponse> getIssuedCertificates(){
        return graduationService.getIssuedCertificates();
    }


    @GetMapping("/student/cleared/list")
    public List<IssuedGownsResponse> getStudentsClearedForGraduation(){
        return graduationService.getStudentsCleared();
    }






}
