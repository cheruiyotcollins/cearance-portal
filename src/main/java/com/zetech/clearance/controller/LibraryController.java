package com.zetech.clearance.controller;

import com.zetech.clearance.model.library.Book;
import com.zetech.clearance.payload.GeneralResponse;
import com.zetech.clearance.payload.IssueBookRequest;
import com.zetech.clearance.payload.IssueBookResponse;
import com.zetech.clearance.repository.library.BookRepository;
import com.zetech.clearance.service.EmailService;
import com.zetech.clearance.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/library")
@CrossOrigin
public class LibraryController {

    @Autowired
    LibraryService libraryService;
    @Autowired
    BookRepository bookRepository;
    GeneralResponse generalResponse;


    @PostMapping("/book/issue")
    public ResponseEntity<?> issueBook(@RequestBody IssueBookRequest issueBookRequest){


        return libraryService.addLibraryRecord(issueBookRequest);


    }

    @GetMapping("/list")
    public List<IssueBookResponse>  getAllRecords(){

        return libraryService.listLibraryRecord();
    }
    @GetMapping("/book/list")
    public List<Book>  getAllBooks(){

        return bookRepository.findAll();
    }

    @GetMapping("/student/book/{id}")
    public ResponseEntity<?>  getIssuanceByID(@PathVariable Long id) {

        return libraryService.getLibraryRecordByStudentId(id);

    }



    @PostMapping("/book/save")
    public GeneralResponse saveBook(@RequestBody Book book){
         generalResponse=new GeneralResponse();
       try{
            bookRepository.save(book);
            generalResponse.status="Success";
            generalResponse.description="Book Saved Successfully";
        }catch(Exception e ){
           generalResponse.status="Fail";
           generalResponse.description="Something Went Wrong";
        }
        return generalResponse;
    }


         @GetMapping("/findbyid/{id}")
        public IssueBookResponse getLibraryById(@PathVariable Long id){

           return libraryService.getLibraryRecordById(id);
      }

    @GetMapping("/student/clearance/{id}")
    public ResponseEntity<?> clearStudent(@PathVariable Long id){

        return libraryService.clearStudent(id);
    }

    @GetMapping("/student/book/clearance/{id}")
    public ResponseEntity<?>  clearIssuedBooks(@PathVariable Long id){
       return libraryService.clearIssuedBooks(id);
    }
}
