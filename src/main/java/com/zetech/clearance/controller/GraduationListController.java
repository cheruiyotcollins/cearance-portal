package com.zetech.clearance.controller;

import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.payload.GownRequest;
import com.zetech.clearance.payload.GraduationListResponse;
import com.zetech.clearance.service.GraduationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/findbyid/{id}")
    public Object getGraduationById(@PathVariable Long id){
        return graduationService.getGraduationRecordById(id);
    }

    @GetMapping("/gown/issuance/{id}")
    public GeneralResponse issueGown(@PathVariable Long id){
       return graduationService.gownIssuance(id);
    }
    @GetMapping("/gown/clearance/{id}")
    public GeneralResponse clearGown(@PathVariable Long id){
         return graduationService.gownClearance(id);
    }
}
