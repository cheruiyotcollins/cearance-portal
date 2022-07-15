package com.example.pesaFix.controller;

import com.example.pesaFix.dao.GeneralResponse;
import com.example.pesaFix.model.Customer;
import com.example.pesaFix.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/save")
    public Object  saveOrUpdate (@RequestBody Customer customer){
        return customerService.saveOrUpdate(customer);
    }
    @GetMapping("/findbyid/{id}")
    public Object findById (@PathVariable Long id){
        return customerService.findById(id);
    }
    @GetMapping("/listall")
    public List<Customer> findAll(){
        return customerService.findAll();
    }
    @DeleteMapping("/deletebyid/{id}")
    public GeneralResponse deleteById(@PathVariable Long id){
         return  customerService.deleteById(id);
    }



}
