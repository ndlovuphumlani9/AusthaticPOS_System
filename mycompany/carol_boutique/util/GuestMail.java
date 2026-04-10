/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.util;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;

public class GuestMail {
    public static void sendMail(String toEmail, String subject, String body) {
        Email email = EmailBuilder.startingBlank()
                .from("Carol's Boutique", "motaungtau10@gmail.com")
                .to(toEmail)
                .withSubject(subject)
                .withPlainText(body)
                .buildEmail();

        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "motaungtau10@gmail.com", "drpltzzujywqkjlt")
                .buildMailer();

        mailer.sendMail(email);
        System.out.println("Email sent successfully to " + toEmail);
    }
    
    public static void sendOtpMail(String toEmail, String otpCode) {
        String subject = "OTP Verification Code";
        String body = "Dear User,\n\n"
                + "Your OTP verification code is: " + otpCode + "\n\n"
                + "Regards,\nCarol's Boutique Team";
        sendMail(toEmail, subject, body);
    }

    public static void sendRegistrationMail(String toEmail, String firstName, String role, String password) {
        String subject;
        String body;

        if ("admin".equalsIgnoreCase(role)) {
            subject = "Admin Registration Successful";
            body = "Dear " + firstName + ",\n\n"
                    + "Welcome to Carol's Boutique! You have been successfully registered as an admin.\n\n"
                    + "Regards,\nCarol's Boutique Team";
        } else if ("manager".equalsIgnoreCase(role)) {
            subject = "Manager Registration Successful";
            body = "Dear " + firstName + ",\n\n"
                    + "Welcome to Carol's Boutique! You have been successfully registered as a manager.\n\n"
                    + "Your password for the POS is as follows: "+ password
                    + "\n\nRegards,\nCarol's Boutique Team.";
        } else {
            subject = "Registration Successful";
            body = "Dear " + firstName + ",\n\n"
                    + "Welcome to Carol's Boutique! You have been successfully registered as an employee.\n\n"
                    + "Your password for the POS is as follows: "+ password
                    + "\n\nRegards,\nCarol's Boutique Team";
        }

        sendMail(toEmail, subject, body);
    }
}
