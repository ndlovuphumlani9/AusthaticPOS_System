/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.customer.service;

import com.mycompany.carol_boutique.customer.dao.CustomerDao;
import com.mycompany.carol_boutique.customer.exception.EmailExistsException;
import com.mycompany.carol_boutique.customer.model.Method;
import com.mycompany.carol_boutique.customer.model.Review;

/**
 *
 * @author Mrqts
 */
public class CustomerServiceImpl implements CustomerService{
    CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public boolean addReview(Review review) {
         return customerDao.addReview(review);
    }

    @Override
    public String subscribe(String name, String number, String email, Method method) throws EmailExistsException {
        return customerDao.subscribe(name, number, email, method);
    }
    
}
