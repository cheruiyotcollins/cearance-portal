package com.zetech.clearance.service;

import com.zetech.clearance.model.finance.Finance;
import com.zetech.clearance.model.graduation.ClearanceStatus;
import com.zetech.clearance.model.graduation.GraduationList;
import com.zetech.clearance.payload.FinanceRequest;
import com.zetech.clearance.payload.FinanceResponse;
import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.repository.finance.FinanceRepository;
import com.zetech.clearance.repository.graduation.GraduationListRepository;
import com.zetech.clearance.repository.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinanceService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FinanceRepository financeRepository;
    @Autowired
    GraduationListRepository graduationListRepository;

    public void addRecord(FinanceRequest request){
        Finance finance= new Finance();
        finance.setOutstandingFees(request.getAmount());
        finance.setGraduationFee(request.getGraduationFee());
        finance.setStudent(studentRepository.findById(request.getStudentId()).get());

        financeRepository.save(finance);

    }
    public List<FinanceResponse> getAllRecords(){
        List<FinanceResponse> financeRecords =new ArrayList<>();

        financeRepository.findAll().forEach(record->{
            financeRecords.add(setRespomse(record));

                }

        );

        return financeRecords;
    }

    public FinanceResponse findRecordById(Long id){
       return setRespomse(financeRepository.findById(id).get());

    }

    public FinanceResponse setRespomse(Finance finance){
        FinanceResponse response= new FinanceResponse();
        response.setGraduationFee(finance.getGraduationFee());
        response.setId(finance.getId());
        response.setName(finance.getStudent().getName());
        response.setRegNo(finance.getStudent().getRegNo());
        response.setOutstandingFee(finance.getOutstandingFees());
        return response;

    }
    public GeneralResponse clearStudent(Long id){
        GeneralResponse generalResponse=new GeneralResponse();
        try{
            GraduationList graduation= graduationListRepository.findById(id).get();
            graduation.setFinanceClearance(ClearanceStatus.CLEARED);
            graduationListRepository.save(graduation);
            generalResponse.status="Success";
            generalResponse.description="Student cleared successfully";
        }catch (Exception e){
            generalResponse.status="Fail";
            generalResponse.description="Something went wrong";
           }

return generalResponse;
    }
}
