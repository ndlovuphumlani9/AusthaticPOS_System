/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.customer.dao;

import com.mycompany.carol_boutique.customer.exception.EmailExistsException;
import com.mycompany.carol_boutique.customer.model.Method;
import com.mycompany.carol_boutique.customer.model.Review;
import com.mycompany.carol_boutique.ibt.model.Store;

/**
 *
 * @author Mrqts
 */
public interface CustomerDao {
    boolean addReview(Review review);
    String subscribe(String name, String number, String email, Method method) throws EmailExistsException;
}
