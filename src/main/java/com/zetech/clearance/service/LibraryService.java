package com.zetech.clearance.service;

import com.zetech.clearance.model.graduation.ClearanceStatus;
import com.zetech.clearance.model.graduation.GraduationList;
import com.zetech.clearance.model.library.BookStatus;
import com.zetech.clearance.model.library.Library;
import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.payload.IssueBookRequest;
import com.zetech.clearance.payload.IssueBookResponse;
import com.zetech.clearance.repository.graduation.GraduationListRepository;
import com.zetech.clearance.repository.library.BookRepository;
import com.zetech.clearance.repository.library.LibraryRepository;
import com.zetech.clearance.repository.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GraduationListRepository graduationListRepository;

    GeneralResponse generalResponse;


    public GeneralResponse addLibraryRecord(IssueBookRequest libraryRecordRequest) {
         generalResponse=new GeneralResponse();
        try{
            Library library = new Library();
            library.setBookId(libraryRecordRequest.getBookId());
            library.setStudent(studentRepository.findByRegNo(libraryRecordRequest.getRegNo()));
            library.setIssuedDate(LocalDateTime.now());
             int d=Integer.valueOf(libraryRecordRequest.getDuration());
           System.out.println(d+"::::::::::::::::::::::::::::::::::::::::::::::::::::");
            library.setExpectedReturnDate(LocalDateTime.now().plusDays(d));

            libraryRepository.save(library);
            generalResponse.status="Success";
            generalResponse.description="Record Added Successfully";
        }catch (Exception e){

            generalResponse.status="Fail";
            generalResponse.description="Something went wrong";

        }
        return generalResponse;

    }

    public List<IssueBookResponse> listLibraryRecord() {
        List<IssueBookResponse> libraryRecords = new ArrayList<>();
        libraryRepository.findAll().forEach(record -> {
                    libraryRecords.add(setResponse(record));
                }
        );
        return libraryRecords;
    }


    public IssueBookResponse getLibraryRecordById(Long id){

        return setResponse(libraryRepository.findById(id).get());
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

    public GeneralResponse  clearStudent(Long id){
        generalResponse=new GeneralResponse();
        try{
            generalResponse.status="Success";
            generalResponse.description="Student cleared successfully";
            GraduationList graduation= graduationListRepository.findById(id).get();
            graduation.setLibraryClearance(ClearanceStatus.CLEARED);

            graduationListRepository.save(graduation);
        }catch (Exception e){
            generalResponse.status="Fail";
            generalResponse.description="Something went wrong";
        }

return generalResponse;

    }



}
