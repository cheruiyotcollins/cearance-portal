package com.zetech.clearance.service;


import com.zetech.clearance.model.graduation.ClearanceStatus;
import com.zetech.clearance.model.graduation.GraduationList;
import com.zetech.clearance.model.student.Student;
import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.payload.GownRequest;
import com.zetech.clearance.payload.GraduationListResponse;
import com.zetech.clearance.repository.graduation.GraduationListRepository;
import com.zetech.clearance.repository.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GraduationService {

@Autowired
    GraduationListRepository graduationListRepository;

    @Autowired
    StudentRepository studentRepository;

    GeneralResponse generalResponse;


    public void generateGraduationList() {
    GraduationListResponse graduationListResponse = new GraduationListResponse();


    studentRepository.findAll().forEach(student->{
        GraduationList graduation=new GraduationList();
        if(!graduationListRepository.existsById(student.getId())){
            graduation.setStudent(student);
            graduation.setFinanceClearance(ClearanceStatus.PENDING);
            graduation.setGownClearance(ClearanceStatus.PENDING);
            graduation.setGownIssuance(ClearanceStatus.NOT_ISSUED);
            graduation.setLibraryClearance(ClearanceStatus.PENDING);
            graduation.setId(student.getId());
            graduationListRepository.save(graduation);
        }



        }

    );
}

    public List<GraduationListResponse> getGraduationList() {
        List<GraduationListResponse> graduationListResponse= new ArrayList<>();


        graduationListRepository.findAll().forEach( graduation->{

            graduationListResponse.add(setGraduationResponse(graduation));
                }


        );

        return graduationListResponse;

    }
    public GraduationListResponse getGraduationRecordById(Long id){
    return setGraduationResponse(graduationListRepository.findById(id).get());
    }

    private GraduationListResponse setGraduationResponse(GraduationList graduation){
        GraduationListResponse response = new GraduationListResponse();
        response.setRegNo(graduation.getStudent().getRegNo());
        response.setLibraryClearance(graduation.getLibraryClearance().name());
        response.setStudentName(graduation.getStudent().getName());
        response.setFinanceClearance(graduation.getFinanceClearance().name());
        response.setGownClearance(graduation.getGownClearance().name());
        response.setGownIssuance(graduation.getGownIssuance().name());
        response.setId(graduation.getId());

        return response;

    }

    public GeneralResponse gownIssuance(Long id){
       generalResponse= new GeneralResponse();
       try{
           GraduationList graduation= graduationListRepository.findById(id).get();
           graduation.setGownIssuedAt(LocalDateTime.now());
           graduation.setGownReturnDate(LocalDateTime.now().plusDays(14));

           graduation.setGownIssuance(ClearanceStatus.ISSUED);
           graduationListRepository.save(graduation);
           generalResponse.status="Success";
           generalResponse.description="Gown Cleared Successfully";
       }catch (Exception e){

           generalResponse.status="Failed";
           generalResponse.description="Something went wrong";
       }

       return generalResponse;


    }

    public GeneralResponse gownClearance(Long  id){
        generalResponse=new GeneralResponse();
        try{
            GraduationList graduationList= graduationListRepository.findById(id).get();
            graduationList.setGownClearance(ClearanceStatus.CLEARED);
            graduationListRepository.save(graduationList);
            generalResponse.status="Success";
            generalResponse.description="Gown Cleared Successfully";
        }catch(Exception e){
            generalResponse.status="Failed";
            generalResponse.description="Something went wrong";

        }
        return generalResponse;
    }


}
