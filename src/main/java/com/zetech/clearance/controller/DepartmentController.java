package com.zetech.clearance.controller;

import com.zetech.clearance.model.department.Department;
import com.zetech.clearance.payload.FinanceRequest;
import com.zetech.clearance.payload.FinanceResponse;
import com.zetech.clearance.repository.department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/department")
@CrossOrigin
public class DepartmentController {

    @Autowired
    DepartmentRepository departmentRepository;
//    @PostMapping("/save")
//    public void addDepartment(@RequestBody FinanceRequest request){
//        financeService.addRecord(request);
//
//    }
    @GetMapping("/list")
    public List<Department> getAllRecords(){
        return departmentRepository.findAll();

    }
//    @GetMapping("/findbyid/{id}")
//    public FinanceResponse findById(@PathVariable Long id){
//
//        return financeService.findRecordById(id);
//    }
//    @GetMapping("/clear/{id}")
//    public void clearStudent(@PathVariable Long id){
//
//        financeService.clearStudent(id);
//    }
}
