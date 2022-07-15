package com.example.pesaFix.controller;

import com.example.pesaFix.model.Loan;
import com.example.pesaFix.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/loan/")
public class LoanController {
    @Autowired
    LoanService loanService;

    @PostMapping("save")
    public Loan saveOrUpdate (@RequestBody Loan loan){
        return loanService.saveOrUpdate(loan);
    }
    @GetMapping("findbyid/{id}")
    public Object findById (@PathVariable Long id){
        return loanService.getLoanById(id);
    }
    @GetMapping("listall")
    public List<Loan> listAll (){
        return  loanService.findAll();
    }
    @DeleteMapping("deletebyid/{id}")
    public Object delete(@PathVariable Long id){

        return loanService.deleteById(id);
    }
}
