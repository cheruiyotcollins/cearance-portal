package com.zetech.clearance.service;

import com.zetech.clearance.model.hod.ClearanceStatus;
import com.zetech.clearance.model.hod.GraduationList;
import com.zetech.clearance.model.library.BookStatus;
import com.zetech.clearance.model.library.Library;
import com.zetech.clearance.model.records.Gown;
import com.zetech.clearance.model.student.ConfirmationStatus;
import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.payload.IssueBookRequest;
import com.zetech.clearance.payload.IssueBookResponse;
import com.zetech.clearance.repository.UserRepository;
import com.zetech.clearance.repository.graduation.GraduationListRepository;
import com.zetech.clearance.repository.library.BookRepository;
import com.zetech.clearance.repository.library.LibraryRepository;
import com.zetech.clearance.repository.student.ConfirmationRepository;
import com.zetech.clearance.repository.student.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GraduationService.class);
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    BookRepository bookRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GraduationListRepository graduationListRepository;
    @Autowired
    ConfirmationRepository confirmationRepository;

    GeneralResponse generalResponse;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailSender;

     @Scheduled(fixedDelay = 10000)
    public void isssuedBooksNotification() {
        LocalDateTime todaysDate = LocalDateTime.now();
        LOGGER.info("***Start Running scheduled task, checking defaulted Issued books" + todaysDate);
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Library>  gowns = libraryRepository.findAll();

        gowns.stream().forEach(gown->{

            if(LocalDateTime.now().plusDays(1).isAfter(gown.getExpectedReturnDate())) {
                new Thread(new Runnable() {
                    public void run() {
                        String sendTo = userRepository.findById(gown.getStudent().getUserId()).get().getEmail();
                        String subject = "Library Issued BookNotification";
                        String emailBody = "Dear " + gown.getStudent().getName() + "," + "\n" + "\n" + "Please return a book("+bookRepository.findById(gown.getBookId()).get().getBookName()+") issued to within the next day library department" +
                                " Failure to which will attract a fine of KES 200"
                                + "\n\n" + "Regards" + "\n" + "Zetech University Library Department";

                        emailSender.sendMail(sendTo, subject, emailBody);

                    }
                }).start();


            }
            });

        LOGGER.info("***Completed" + LocalDateTime.now());

    }
    @Async("asyncExecutor")
    public ResponseEntity<?> addLibraryRecord(IssueBookRequest libraryRecordRequest) {
         generalResponse=new GeneralResponse();


        try{
            if(graduationListRepository.findByStudent(studentRepository.findByRegNo(libraryRecordRequest.getRegNo())).getLibraryClearance().equals(ClearanceStatus.CLEARED)){
                generalResponse.status="Failed";
                generalResponse.description="Student has been cleared and cannot be issued a book";
                return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
            }
            Library library = new Library();
            library.setBookId(libraryRecordRequest.getBookId());
            library.setStudent(studentRepository.findByRegNo(libraryRecordRequest.getRegNo()));
            library.setIssuedDate(LocalDateTime.now());
             int d=Integer.valueOf(libraryRecordRequest.getDuration());
             library.setExpectedReturnDate(LocalDateTime.now().plusDays(d));
            libraryRepository.save(library);
            generalResponse.status="Success";
            generalResponse.description="Book Issued Successfully";

            new Thread(new Runnable() {
                public void run() {
                    String sendTo = userRepository.findById(library.getStudent().getUserId()).get().getEmail();
                    String subject = "Book Issuance Confirmation";
                    String emailBody = "Dear " + library.getStudent().getName() + "," + "\n" + "\n" + "You have been issued "+bookRepository.findById(library.getBookId()).get().getBookName()+". Please return it by " + dateTimeFormatter.format(library.getExpectedReturnDate()) +
                            " Failure to which will attract a fine of KES 200"
                            + "\n\n" + "Regards" + "\n" + "Zetech University Library Department";

                    emailSender.sendMail(sendTo, subject, emailBody);

                }
            }).start();
              return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
        }catch (Exception e){

            generalResponse.status="Fail";
            generalResponse.description="Something went wrong";
          return  new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);

        }


    }

    public List<IssueBookResponse> listLibraryRecord() {
        List<IssueBookResponse> libraryRecords = new ArrayList<>();
        libraryRepository.findAll().forEach(record -> {
                    libraryRecords.add(setResponse(record));
                }
        );
        return libraryRecords;
    }





    public ResponseEntity<?> clearIssuedBooks(Long id){

        libraryRepository.deleteById(id);
        generalResponse =new GeneralResponse();

        generalResponse.status="Success";
        generalResponse.description="Book cleared Successfully";

        return  new ResponseEntity(generalResponse,
                HttpStatus.ACCEPTED);
    }
    public IssueBookResponse getLibraryRecordById (Long id){
           Library library= libraryRepository.findByStudent(studentRepository.findById(id).get());

        return  setResponse(library);


    }
    public ResponseEntity<?> getLibraryRecordByStudentId (Long id){
        generalResponse=new GeneralResponse();

        try{

            IssueBookResponse issueBookResponse=  setResponse(libraryRepository.findByStudent(studentRepository.findByUserId(userRepository.findById(id).get().getId())));
            return new ResponseEntity(issueBookResponse, HttpStatus.ACCEPTED);

        }catch (Exception e){
            generalResponse.status="Fail";
            generalResponse.description="No Record found";
            return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
        }
        }





    private IssueBookResponse setResponse(Library record){
        IssueBookResponse library = new IssueBookResponse();

        library.setBookName(bookRepository.findById(record.getBookId()).get().getBookName());
        library.setExpectedReturnDate(record.getExpectedReturnDate().toString());
        library.setId(record.getId());
        library.setIssuedDate(record.getIssuedDate().toString());
        library.setStudentName(record.getStudent().getName());
        library.setRegNo(record.getStudent().getRegNo());

        if (record.getIssuedDate().isBefore(record.getExpectedReturnDate())) {
            library.setStatus(BookStatus.ACTIVE.name());
        } else {
            library.setStatus(BookStatus.DEFAULT.name());

        }

        return library;

    }



    public ResponseEntity<?>  clearStudent(Long id){
        generalResponse=new GeneralResponse();
        try{
            GraduationList graduation= graduationListRepository.findById(id).get();

              if(confirmationRepository.findById(id).get().getGraduationNameConfirmation().name().equals(ConfirmationStatus.NOT_CONFIRMED.name())){
                generalResponse.status="Failed";
                generalResponse.description="Student needs to confirm names as they would like them to appear on certificate first";
                return  new ResponseEntity(generalResponse,
                        HttpStatus.ACCEPTED);
            }
            if(graduation.getLibraryClearance().name().equals(ClearanceStatus.CLEARED.name())){
                generalResponse.status="Failed";
                generalResponse.description="Student already cleared";
                return  new ResponseEntity(generalResponse,
                        HttpStatus.ACCEPTED);
            }

              if(libraryRepository.findByStudent(studentRepository.findById(id).get())!=null){
                generalResponse.status="Failed";
                generalResponse.description="Please clear books issued to student first";
                return  new ResponseEntity(generalResponse,
                        HttpStatus.ACCEPTED);
            }


            else if(libraryRepository.findByStudent(studentRepository.findById(id).get())==null && confirmationRepository.findById(id).get().getGraduationNameConfirmation().name().equals(ConfirmationStatus.CONFIRMED.name())){
                graduation.setLibraryClearance(ClearanceStatus.CLEARED);
                graduationListRepository.save(graduation);
                generalResponse.status="Success";
                generalResponse.description="Student cleared successfully";
                return  new ResponseEntity(generalResponse,
                        HttpStatus.ACCEPTED);
            }

        }catch (Exception e){
            generalResponse.status="Fail";
            generalResponse.description="Something went wrong";
            return  new ResponseEntity(generalResponse,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        generalResponse.status="Unknown";
        generalResponse.description="Something is terribly wrong";

        return new ResponseEntity(generalResponse,
                HttpStatus.INTERNAL_SERVER_ERROR);


    }



}
