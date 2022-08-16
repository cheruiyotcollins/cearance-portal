package com.zetech.clearance.controller;

import com.zetech.clearance.payload.FinanceRequest;
import com.zetech.clearance.payload.FinanceResponse;
import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/finance")
@CrossOrigin
public class FinanceController {
    @Autowired
    FinanceService financeService;


    @PostMapping("/save")
    public void addRecord(@RequestBody FinanceRequest request){
        financeService.addRecord(request);

    }
    @GetMapping("/list")
    public List<FinanceResponse> getAllRecords(){
        return financeService.getAllRecords();

    }
    @GetMapping("/findbyid/{id}")
    public FinanceResponse findById(@PathVariable Long id){

        return financeService.findRecordById(id);
    }
    @GetMapping("/clearance/{id}")
    public GeneralResponse clearStudent(@PathVariable Long id){

        return financeService.clearStudent(id);
    }
}
