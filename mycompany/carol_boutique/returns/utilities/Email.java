/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.returns.utilities;

import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

/**
 *
 * @author Mrqts
 */
public class Email {
    public static void sendRefundEmail(String toEmail, String cardNo) {
        org.simplejavamail.api.email.Email email = EmailBuilder.startingBlank()
                .from("Luxury Leisure Hotel", "azyeki01@outlook.com")
                .to(toEmail)
                .withSubject("Refund Ntotification")
                .withPlainText("Dear User,\n\nYour refund will be sent to " + cardNo + " within 5 business days.\n\nRegards,\nCarol's Boutique")
                .buildEmail();

        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.office365.com", 587, "azyeki01@outlook.com", "Lukhanyo1")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();

        mailer.sendMail(email);
        System.out.println("Email sent successfully to " + toEmail);
    }
}
