package com.zetech.clearance.controller;

import com.zetech.clearance.model.student.Student;
import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.payload.SaveStudentRequest;
import com.zetech.clearance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/save")
    public Object  saveOrUpdate (@Valid @RequestBody SaveStudentRequest saveStudentRequest){
        return studentService.saveOrUpdate(saveStudentRequest);
    }
    @GetMapping("/findbyid/{id}")
    public Object findById (@PathVariable Long id){
        return studentService.findById(id);
    }
    @GetMapping("/list")
    public List<Student> findAll(){
        return studentService.findAll();
    }
    @DeleteMapping("/deletebyid/{id}")
    public GeneralResponse deleteById(@PathVariable Long id){
        return  studentService.deleteById(id);
    }
}
