package com.example.pesaFix.service;

import com.example.pesaFix.dao.GeneralResponse;
import com.example.pesaFix.model.Customer;
import com.example.pesaFix.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service

public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    GeneralResponse generalResponse;

    public Object saveOrUpdate (Customer customer){
        try{
            System.out.println("entered try block");
        return customerRepository.save(customer);

        }catch (Exception e){
            generalResponse.status= "failed";
            generalResponse.description="Customer not saved";
            System.out.println("entered catch block");
            return  generalResponse;
        }
    }


    public Object findById (Long id){
        generalResponse = new GeneralResponse();
        try{
            return customerRepository.findById(id).get();

        }catch (Exception e){
            generalResponse.status="failed";
            generalResponse.description="customer not found";
            return generalResponse;
        }

    }
    public List<Customer> findAll(){

        return customerRepository.findAll();
    }
    public GeneralResponse deleteById(Long id){
        generalResponse = new GeneralResponse();
        try {
            customerRepository.deleteById(id);
            generalResponse.status="success";
            generalResponse.description="customer deleted successfully";

        }catch (Exception e){
            generalResponse.status="failed";
            generalResponse.description="customer deletion failed";

        }
        return generalResponse;
    }
}
