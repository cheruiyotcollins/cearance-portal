package com.zetech.clearance.service;


import com.zetech.clearance.model.hod.ClearanceStatus;
import com.zetech.clearance.model.hod.GraduationList;
import com.zetech.clearance.model.records.Gown;
import com.zetech.clearance.model.student.Confirmation;
import com.zetech.clearance.model.student.ConfirmationStatus;
import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.payload.GraduationListResponse;
import com.zetech.clearance.payload.IssuedGownsResponse;
import com.zetech.clearance.payload.PaymentRequest;
import com.zetech.clearance.repository.UserRepository;
import com.zetech.clearance.repository.graduation.GraduationListRepository;
import com.zetech.clearance.repository.records.GownRepository;
import com.zetech.clearance.repository.student.ConfirmationRepository;
import com.zetech.clearance.repository.student.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class GraduationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GraduationService.class);

    @Autowired
    GraduationListRepository graduationListRepository;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GownRepository gownRepository;

    GeneralResponse generalResponse;

    @Autowired
    ConfirmationRepository confirmationRepository;
    @Autowired
    EmailService emailSender;
    @Autowired
    UserRepository userRepository;


    @Scheduled(fixedDelay = 10000)
//    @Scheduled(cron = "0 38 21 * * ?")
    public void CheckDefaultedGowns() {
        LocalDateTime todaysDate = LocalDateTime.now();
        LOGGER.info("***Start**Running scheduled task, checking defaulted gowns" + todaysDate);
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Gown> gowns = gownRepository.findAll();

        gowns.stream().forEach(gown -> {

            if (LocalDateTime.now().isAfter(gown.getReturnBy())) {
                if (gown.getFine() == 0) {
                    gown.setFine(1000L);
                    gownRepository.save(gown);
                }


            } else if (LocalDateTime.now().plusDays(3).isAfter(gown.getReturnBy())) {
                 new Thread(new Runnable() {
                    public void run() {
                        String sendTo = userRepository.findById(studentRepository.findById(gown.getStudentId()).get().getUserId()).get().getEmail();
                        String subject = "Gown Clearance  Notice";
                        String emailBody = "Dear " + studentRepository.findById(gown.getStudentId()).get().getName() + "," + "\n" + "\n" + "Please clear the gown issued to you within the next two days with the records department" +
                                "Failure to which will attract a fine of KES 1000"
                                + "\n\n" + "Regards" + "\n" + "Zetech University Records Department";

                        emailSender.sendMail(sendTo, subject, emailBody);

                    }
                }).start();

            }
        });

        LOGGER.info("***Completed" + LocalDateTime.now());

    }


    public void generateGraduationList() {
        GraduationListResponse graduationListResponse = new GraduationListResponse();


        studentRepository.findAll().forEach(student -> {
                    GraduationList graduation = new GraduationList();
                    if (!graduationListRepository.existsById(student.getId())) {
                        graduation.setStudent(student);
                        graduation.setFinanceClearance(ClearanceStatus.PENDING);
                        graduation.setGownClearance(ClearanceStatus.PENDING);
                        graduation.setGownIssuance(ClearanceStatus.NOT_ISSUED);
                        graduation.setLibraryClearance(ClearanceStatus.PENDING);
                        graduation.setCertificateClearance(ClearanceStatus.NOT_ISSUED);
                        graduation.setId(student.getId());


                        Confirmation confirmation = new Confirmation();
                        confirmation.setStudentId(student.getId());
                        confirmation.setGownClearanceConfirmation(ConfirmationStatus.NOT_CONFIRMED);
                        confirmation.setGraduationNameConfirmation(ConfirmationStatus.NOT_CONFIRMED);
                        confirmation.setGownIssuanceConfirmation(ConfirmationStatus.NOT_CONFIRMED);
                        confirmation.setGownClearanceConfirmationRequest(1);
                        confirmation.setGownIssuanceConfirmationRequest(1);
                        confirmation.setGraduationNameConfirmationRequest(0);


                        confirmationRepository.save(confirmation);

                        graduationListRepository.save(graduation);
                    }


                }

        );
    }

    public List<GraduationListResponse> getGraduationList() {
        List<GraduationListResponse> graduationListResponse = new ArrayList<>();
        generateGraduationList();

        graduationListRepository.findAll().forEach(graduation -> {

                    graduationListResponse.add(setGraduationResponse(graduation));
                }


        );

        return graduationListResponse;

    }

    public GraduationListResponse getGraduationRecordById(Long id) {
        return setGraduationResponse(graduationListRepository.findById(id).get());
    }

    private GraduationListResponse setGraduationResponse(GraduationList graduation) {
        GraduationListResponse response = new GraduationListResponse();
        response.setRegNo(graduation.getStudent().getRegNo());
        response.setLibraryClearance(graduation.getLibraryClearance().name());
        response.setStudentName(graduation.getStudent().getName());
        response.setFinanceClearance(graduation.getFinanceClearance().name());
        response.setGownClearance(graduation.getGownClearance().name());
        response.setGownIssuance(graduation.getGownIssuance().name());
        response.setCertificateIssuance(graduation.getCertificateClearance().name());
        response.setId(graduation.getId());

        return response;

    }

    public ResponseEntity<?> gownIssuance(Long id) {
        generalResponse = new GeneralResponse();
        try {
            GraduationList graduation = graduationListRepository.findById(id).get();
            Confirmation studentConfirmation = confirmationRepository.findById(id).get();
            if (graduation.getFinanceClearance().equals(ClearanceStatus.CLEARED)) {
                Gown gown = new Gown();
                studentConfirmation.setGownIssuanceConfirmationRequest(0);
                confirmationRepository.save(studentConfirmation);

                new Thread(new Runnable() {
                    public void run() {
                        String sendTo = userRepository.findById(studentRepository.findById(graduation.getId()).get().getUserId()).get().getEmail();
                        String subject = "Gown Issuance  Confirmation";
                        String emailBody = "Dear " + studentRepository.findById(id).get().getName() + "," + "\n" + "\n" + "You have been issued a gown by the records department. Kindly log in to Zetech ERP portal to confirm receipt.\n" +
                                "Please note that your are suppose to return the gown within the next two weeks, failure to which will attract a fine of KES 1000"
                                + "\n\n" + "Regards" + "\n" + "Zetech University Records Department";

                        emailSender.sendMail(sendTo, subject, emailBody);

                    }
                }).start();

                graduation.setGownIssuance(ClearanceStatus.ISSUED);
                graduationListRepository.save(graduation);

                gown.setIssuedAt(LocalDateTime.now());
                gown.setReturnBy(LocalDateTime.now().plusDays(14));
                gown.setStudentId(id);
                gown.setFine(0L);

                gownRepository.save(gown);

                generalResponse.status = "Success";
                generalResponse.description = "Gown Issued Successfully";
            } else {
                generalResponse.status = "Fail";
                generalResponse.description = "Please Do Finance Clearance First";
            }
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);

        } catch (Exception e) {

            generalResponse.status = "Fail";
            generalResponse.description = "Something went wrong";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
        }


    }

    public ResponseEntity<?> gownClearance(Long id) {
        generalResponse = new GeneralResponse();

        GraduationList graduationList = graduationListRepository.findById(id).get();
        System.out.println(id);
        System.out.println(graduationList.getGownIssuance().name());

        if (graduationList.getGownIssuance().equals(ClearanceStatus.PENDING)) {
            generalResponse.status = "Fail";
            generalResponse.description = "Student Not Issued A Gown";
        } else if (graduationList.getGownClearance().equals(ClearanceStatus.CLEARED)) {
            generalResponse.status = "Fail";
            generalResponse.description = "Gown Already Cleared";

        } else if (confirmationRepository.findById(id).get().getGownIssuanceConfirmation().equals(ConfirmationStatus.NOT_CONFIRMED)) {
            generalResponse.status = "Fail";
            generalResponse.description = "Student Needs to Confirm Gown Issuance First";

        } else if (gownRepository.findByStudentId(id).getFine() > 0) {

            generalResponse.status = "Fail";
            generalResponse.description = "Student did not clear within a period of two weeks, which has attracted a fine of 1000. Please pay the fine first";
        } else {
            Confirmation studentConfirmation = confirmationRepository.findById(id).get();
            studentConfirmation.setGownClearanceConfirmationRequest(0);
            Gown gown = gownRepository.findByStudentId(id);
            graduationList.setGownClearance(ClearanceStatus.CLEARED);
            graduationListRepository.save(graduationList);
            gown.setClearedOn(LocalDateTime.now());
            gownRepository.save(gown);

            generalResponse.status = "Success";
            generalResponse.description = "Gown Cleared Successfully";
             new Thread(new Runnable() {
                public void run() {
                    String sendTo = userRepository.findById(graduationList.getStudent().getUserId()).get().getEmail();
                    String subject = "Gown Clearance  Confirmation";
                    String emailBody = "Dear " + studentRepository.findById(id).get().getName() + "," + "\n" + "\n" + "A gown issued to you has been cleared, kindly log in to Zetech graduation portal to confirm clearance.\n"
                            + "\n\n" + "Regards" + "\n" + "Zetech University Records Department";

                    emailSender.sendMail(sendTo, subject, emailBody);

                }
            }).start();




        }
        return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);

   }

    public List<IssuedGownsResponse> getIssuedGowns() {
        List<IssuedGownsResponse> issuedGowns = new ArrayList<>();
        graduationListRepository.findAll().forEach(issuance -> {

                    if (issuance.getGownIssuance().name().equals("ISSUED") && issuance.getGownClearance().name().equals("PENDING")) {
                        IssuedGownsResponse gownIssued = new IssuedGownsResponse();
                        gownIssued.setIssuedDate(gownRepository.findByStudentId(issuance.getStudent().getId()).getIssuedAt().toString());
                        gownIssued.setReturnDate(gownRepository.findByStudentId(issuance.getStudent().getId()).getReturnBy().toString());
                        gownIssued.setRegNo(issuance.getStudent().getRegNo());
                        gownIssued.setStudentName(issuance.getStudent().getName());
                        issuedGowns.add(gownIssued);
                    }

                }
        );
        return issuedGowns;

    }

    public List<IssuedGownsResponse> getReturnedGowns() {
        List<IssuedGownsResponse> issuedGowns = new ArrayList<>();
        graduationListRepository.findAll().forEach(issuance -> {

                    if (issuance.getGownClearance().name().equals("CLEARED")) {
                        IssuedGownsResponse gownIssued = new IssuedGownsResponse();
                        gownIssued.setIssuedDate(gownRepository.findByStudentId(issuance.getStudent().getId()).getIssuedAt().toString());
                        gownIssued.setRegNo(issuance.getStudent().getRegNo());
                        gownIssued.setStudentName(issuance.getStudent().getName());
                        gownIssued.setClearedOn(gownRepository.findByStudentId(issuance.getStudent().getId()).getClearedOn().toString());

                        issuedGowns.add(gownIssued);
                    }

                }
        );
        return issuedGowns;

    }

    public ResponseEntity<?> certificateClearance(Long id) {
        generalResponse = new GeneralResponse();
        try {
            GraduationList graduationList = graduationListRepository.findById(id).get();
            if (graduationList.getCertificateClearance().equals(ClearanceStatus.CLEARED)) {
                generalResponse.status = "Fail";
                generalResponse.description = "Student Already Issued Certificate";
                return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
            } else if (graduationList.getGownClearance().equals(ClearanceStatus.PENDING)) {
                generalResponse.status = "Fail";
                generalResponse.description = "Please Clear Gown with Records Department First";
                return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);

            } else {
                graduationList.setCertificateClearance(ClearanceStatus.ISSUED);
                graduationList.setCertClearedOn(LocalDateTime.now());
                graduationListRepository.save(graduationList);

                generalResponse.status = "Success";
                generalResponse.description = "Certificate Issued Successfully";
                return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
                //Todo
//            email
            }

        } catch (Exception e) {
            generalResponse.status = "Failed";
            generalResponse.description = "Something went wrong";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
        }

    }

    public List<IssuedGownsResponse> getIssuedCertificates() {
        List<IssuedGownsResponse> issuedGowns = new ArrayList<>();
        graduationListRepository.findAll().forEach(issuance -> {

                    if (issuance.getCertificateClearance().name().equals("ISSUED")) {
                        IssuedGownsResponse gownIssued = new IssuedGownsResponse();
                        gownIssued.setCertIssuedOn(issuance.getCertClearedOn().toString());
                        gownIssued.setRegNo(issuance.getStudent().getRegNo());
                        gownIssued.setStudentName(issuance.getStudent().getName());


                        issuedGowns.add(gownIssued);
                    }

                }
        );
        return issuedGowns;

    }

    public List<IssuedGownsResponse> getStudentsCleared() {
        List<IssuedGownsResponse> issuedGowns = new ArrayList<>();
        graduationListRepository.findAll().forEach(issuance -> {

                    if (issuance.getFinanceClearance().name().equals("CLEARED") && issuance.getLibraryClearance().name().equals("CLEARED")) {
                        IssuedGownsResponse gownIssued = new IssuedGownsResponse();
                        gownIssued.setRegNo(issuance.getStudent().getRegNo());
                        gownIssued.setStudentName(issuance.getStudent().getName());

                        issuedGowns.add(gownIssued);
                    }

                }
        );
        return issuedGowns;

    }

    public ResponseEntity<?> gownFinePayment(PaymentRequest payment) {
        Gown gown = gownRepository.findByStudentId(studentRepository.findByRegNo(payment.getRegNo()).getId());
        GeneralResponse generalResponse = new GeneralResponse();
        if (gown.getFine() > 0) {
            gown.setFine(gown.getFine() - payment.getAmount());
            gown.setReturnBy(LocalDateTime.now().plusYears(5));
            gownRepository.save(gown);

            generalResponse.status = "Success";
            generalResponse.description = "Fine Paid Successfully";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
        } else {
            generalResponse.status = "Fail";
            generalResponse.description = "No fine Found";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
        }


    }


}
