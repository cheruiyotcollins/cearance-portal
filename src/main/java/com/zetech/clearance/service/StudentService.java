package com.zetech.clearance.service;

import com.zetech.clearance.model.student.Student;
import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.payload.GetStudentResponse;
import com.zetech.clearance.payload.SaveStudentRequest;
import com.zetech.clearance.repository.department.DepartmentRepository;
import com.zetech.clearance.repository.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    @Autowired
    EmailService emailSender;

    GeneralResponse generalResponse;
    Student student;
    GetStudentResponse getStudentResponse;

    public Object saveOrUpdate (SaveStudentRequest saveStudentRequest){
        generalResponse=new GeneralResponse();
        try{
            System.out.println("entered try block");
            student= new Student();
            student.setCourse(saveStudentRequest.getCourse());
            student.setName(saveStudentRequest.getName());
            student.setRegNo(saveStudentRequest.getRegNo());
            student.setDepartment(departmentRepository.findById(saveStudentRequest.getDepartmentId()).get());
//            emailSender.sendMail("trizahchebet8@gmail.com","Congratulations","You have been successfully onboarded");
            System.out.println("reached here finally");

            return studentRepository.save(student);

        }catch (Exception e){
            generalResponse.status= "failed";
            generalResponse.description="Error Saving ";
            System.out.println("entered catch block");
            return  generalResponse;
        }
    }


    public Object findById (Long id){
        generalResponse = new GeneralResponse();
        try{
            student= new Student();
            student=studentRepository.findById(id).get();
            getStudentResponse= new GetStudentResponse();
            getStudentResponse.setCourse(student.getCourse());
            getStudentResponse.setName(student.getName());
            getStudentResponse.setRegNo(student.getRegNo());
            getStudentResponse.setDepartment(student.getDepartment().getDepartmentName());
            return getStudentResponse;

        }catch (Exception e){
            generalResponse.status="failed";
            generalResponse.description="student not found";
            return generalResponse;
        }

    }
    public List<Student> findAll(){

        return studentRepository.findAll();
    }
    public GeneralResponse deleteById(Long id){
        generalResponse = new GeneralResponse();
        try {
            studentRepository.deleteById(id);
            generalResponse.status="success";
            generalResponse.description="student deleted successfully";

        }catch (Exception e){
            generalResponse.status="failed";
            generalResponse.description="student deletion failed";

        }
        return generalResponse;
    }
}
