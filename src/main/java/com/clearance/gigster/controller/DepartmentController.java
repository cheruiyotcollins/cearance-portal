package com.clearance.gigster.controller;

import com.clearance.gigster.model.department.Department;
import com.clearance.gigster.repository.department.DepartmentRepository;
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
