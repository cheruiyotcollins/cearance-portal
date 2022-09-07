package com.zetech.clearance.service;

import com.zetech.clearance.model.finance.Finance;
import com.zetech.clearance.model.finance.Payment;
import com.zetech.clearance.model.hod.ClearanceStatus;
import com.zetech.clearance.model.hod.GraduationList;
import com.zetech.clearance.model.student.Student;
import com.zetech.clearance.payload.*;
import com.zetech.clearance.repository.finance.FinanceRepository;
import com.zetech.clearance.repository.finance.PaymentsRepository;
import com.zetech.clearance.repository.graduation.GraduationListRepository;
import com.zetech.clearance.repository.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    PaymentsRepository paymentsRepository;
    @Autowired
    GraduationListRepository graduationListRepository;

    GeneralResponse generalResponse = new GeneralResponse();

    public void addRecord(Student student) {
        Finance finance = new Finance();
        finance.setOutstandingFees(10000.00);
        finance.setGraduationFee(5000.00);
        finance.setStudent(student);

        financeRepository.save(finance);

    }

    public List<FinanceResponse> getAllRecords() {
        List<FinanceResponse> financeRecords = new ArrayList<>();

        financeRepository.findAll().forEach(record -> {
                    financeRecords.add(setRespomse(record));

                }

        );

        return financeRecords;
    }

    public FinanceResponse findRecordById(Long id) {
        return setRespomse(financeRepository.findById(id).get());

    }

    public ResponseEntity<?> findFeeBalanceById(Long id) {
        Finance finance= financeRepository.findByStudentId(studentRepository.findByUserId(id));
        return new ResponseEntity(setRespomse(finance), HttpStatus.ACCEPTED) ;

    }
    public ResponseEntity<?> findStudentPayments(Long id) {
        List<Payment> payments= paymentsRepository.findByStudentId(studentRepository.findByUserId(id).getId());
        return new ResponseEntity(payments, HttpStatus.ACCEPTED) ;

    }


    public FinanceResponse setRespomse(Finance finance) {
        FinanceResponse response = new FinanceResponse();
        response.setGraduationFee(finance.getGraduationFee());
        response.setId(finance.getId());
        response.setName(finance.getStudent().getName());
        response.setRegNo(finance.getStudent().getRegNo());
        response.setOutstandingFee(finance.getOutstandingFees());
        response.setTotal(finance.getOutstandingFees()+finance.getGraduationFee());
        return response;

    }

    public ResponseEntity<?> clearStudent(Long id) {

        try {

            GraduationList graduation = graduationListRepository.findById(id).get();
            if(graduation.getLibraryClearance().equals(ClearanceStatus.PENDING)){
                generalResponse.status = "Fail";
                generalResponse.description = "Please clear with the library first";
                return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
            }else if(financeRepository.findByStudentId(studentRepository.findById(id).get()).getOutstandingFees()>0){
                generalResponse.status = "Fail";
                generalResponse.description = "Please Clear Fees First";
                return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
            }else if(financeRepository.findByStudentId(studentRepository.findById(id).get()).getGraduationFee()>0){
                generalResponse.status = "Fail";
                generalResponse.description = "Please Clear Graduation Fee First";
                return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
            }else if(graduationListRepository.findByStudent(studentRepository.findById(id).get()).getFinanceClearance().equals(ClearanceStatus.CLEARED)){
                generalResponse.status = "Fail";
                generalResponse.description = "Student Already Cleared";
                return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
            }else{
                graduation.setFinanceClearance(ClearanceStatus.CLEARED);
                graduationListRepository.save(graduation);
                generalResponse.status = "Success";
                generalResponse.description = "Student cleared successfully";
                return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
            }

        } catch (Exception e) {
            generalResponse.status = "Fail";
            generalResponse.description = "Something went wrong";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
        }


    }

    public ResponseEntity<?> makePayment(PaymentRequest paymentRequest) {



        Finance financeRecords = financeRepository.findByStudentId(studentRepository.findByRegNo(paymentRequest.getRegNo()));

        if(paymentRequest.getPaymentFor().equals("1")){
            if(paymentRequest.getAmount()>financeRecords.getOutstandingFees()){

                financeRecords.setGraduationFee(financeRecords.getGraduationFee()-(paymentRequest.getAmount()-financeRecords.getOutstandingFees()));
                financeRecords.setOutstandingFees(0.00);
                generalResponse.status = "Success";
                generalResponse.description = "Tuition  Fee  Has Been Paid In Full And the Extra KES "+ (paymentRequest.getAmount()-financeRecords.getOutstandingFees()) + " Has Been Used To Pay Graduation Fee";

            }else{
                financeRecords.setOutstandingFees(financeRecords.getOutstandingFees() - paymentRequest.getAmount());
                generalResponse.status = "Success";
                generalResponse.description = "Tuition Fee Paid Successfully";
            }


        }else if(paymentRequest.getPaymentFor().equals("2")){
            if(financeRecords.getOutstandingFees()>0){
                generalResponse.status = "Fail";
                generalResponse.description = "Please Clear Outstanding Tuition Fee First";
            }else{
                financeRecords.setGraduationFee(financeRecords.getGraduationFee()-paymentRequest.getAmount());
                generalResponse.status = "Success";
                generalResponse.description = "Graduation Fee Paid Successfully";
            }

        }

        financeRepository.save(financeRecords);
        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setStudentId(studentRepository.findByRegNo(paymentRequest.getRegNo()).getId());
        payment.setTransactionId(paymentRequest.getTransactionReference());
        paymentsRepository.save(payment);

        return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
    }

    public List<PaymentResponse> listPayment() {

        List<PaymentResponse> paymentResponseList= new ArrayList<>();
        paymentsRepository.findAll().forEach( payment->{

            PaymentResponse paymentResponse=new PaymentResponse();
            paymentResponse.setAmount(payment.getAmount());
            paymentResponse.setId(payment.getId());
            paymentResponse.setRegNo(studentRepository.findById(payment.getStudentId()).get().getRegNo());
            paymentResponse.setStudentName(studentRepository.findById(payment.getStudentId()).get().getName());
            paymentResponse.setTransactionReference(payment.getTransactionId());
            paymentResponse.setPaidAt(payment.getPaidAt().toString());
            paymentResponseList.add(paymentResponse);
                }

        );
        return paymentResponseList;
    }
}
