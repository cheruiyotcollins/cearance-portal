package com.zetech.clearance.controller;

import com.zetech.clearance.model.department.Course;
import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.payload.GetStudentResponse;
import com.zetech.clearance.payload.SaveStudentRequest;
import com.zetech.clearance.repository.department.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value="/api/course")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;
//
//    @PostMapping("/save")
//    public Object  saveOrUpdate (@Valid @RequestBody SaveStudentRequest saveStudentRequest){
//        return studentService.saveOrUpdate(saveStudentRequest);
//    }
//    @GetMapping("/findbyid/{id}")
//    public Object findById (@PathVariable Long id){
//        return studentService.findById(id);
//    }
    @GetMapping("/list")
    public List<Course> findAll(){
        return courseRepository.findAll();
    }
//    @DeleteMapping("/deletebyid/{id}")
//    public GeneralResponse deleteById(@PathVariable Long id){
//        return  studentService.deleteById(id);
//    }
}


