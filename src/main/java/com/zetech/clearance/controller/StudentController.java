package com.zetech.clearance.controller;

import com.zetech.clearance.model.student.Student;
import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.payload.GetStudentResponse;
import com.zetech.clearance.payload.SaveStudentRequest;
import com.zetech.clearance.payload.UserIdentityAvailability;
import com.zetech.clearance.repository.student.StudentRepository;
import com.zetech.clearance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/list")
    public List<GetStudentResponse> findAll(){
        return studentService.findAll();
    }
    @DeleteMapping("/deletebyid/{id}")
    public GeneralResponse deleteById(@PathVariable Long id){
        return  studentService.deleteById(id);
    }

    @GetMapping("/checkstudent")
    public UserIdentityAvailability checkStudentAvailability(@RequestParam(value = "regNo") String regNo) {

        Boolean isAvailable = !studentRepository.existsByRegNo(regNo);
        return new UserIdentityAvailability(isAvailable);
    }
}
