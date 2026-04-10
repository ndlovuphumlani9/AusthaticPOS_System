/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.manager.service;

import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.manager.dao.TellerDao;
//import com.mycompany.carol_boutique.manager.model.Teller;
import com.mycompany.carol_boutique.util.GuestMail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class TellerServiceImpl implements TellerService {
    private TellerDao tellerDao;

    public TellerServiceImpl(TellerDao tellerDao) {
        this.tellerDao = tellerDao;
    }

//    @Override
//    public boolean registerTeller(Teller teller) {
//        String hashedPassword = doHashing(teller.getPassword());
//        teller.setPassword(hashedPassword);
//        
//        boolean success = tellerDao.addTeller(teller);
//        
//        if (success) {
//            // Send registration email
//            sendRegistrationEmail(teller.getEmail(), teller.getFirstName());
//        }
//        
//        return success;
//    }

//    @Override
//    public Teller authenticate(int username, String password) {
//        Teller teller = tellerDao.getTellerByUsername(username);
//        if (teller != null && teller.getPassword().equals(doHashing(password))) {
//            return teller;
//        }
//        throw new RuntimeException("Authentication failed");
//    }
//
//    private String doHashing(String password) {
//        try {
//            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = messageDigest.digest(password.getBytes());
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hash) {
//                hexString.append(String.format("%02x", b));
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
    
    private void sendRegistrationEmail(String toEmail, String firstName) {
        String subject = "Position Notification";
        String body = "Dear " + firstName + ",\n\nWelcome to the Carol's Boutique store. "
                    + "You've been accepted as a Teller."
                    + "\n\nYou start Monday."
                    + "\n\nRegards,\n"
                    + "Carol's Boutique Team";

        GuestMail.sendMail(toEmail, subject, body);
    }

    @Override
    public List<Employee> getAllTellers() {
        return tellerDao.getAllTellers();
    }
}
